/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.builder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import net.sf.jasperreports.data.AbstractClasspathAwareDataAdapterService;
import net.sf.jasperreports.eclipse.IDisposeListener;
import net.sf.jasperreports.eclipse.JasperReportsPlugin;
import net.sf.jasperreports.eclipse.MScopedPreferenceStore;
import net.sf.jasperreports.eclipse.classpath.JavaProjectClassLoader;
import net.sf.jasperreports.eclipse.util.FilePrefUtil;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.eclipse.util.query.EmptyQueryExecuterFactoryBundle;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactoryBundle;
import net.sf.jasperreports.engine.util.CompositeClassloader;
import net.sf.jasperreports.engine.util.MessageProviderFactory;
import net.sf.jasperreports.engine.util.ResourceBundleMessageProviderFactory;
import net.sf.jasperreports.functions.FunctionsBundle;
import net.sf.jasperreports.repo.DefaultRepositoryService;
import net.sf.jasperreports.repo.RepositoryService;

public class JSSReportContext extends SimpleJasperReportsContext {

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private ClasspathListener classpathlistener;
	private PreferenceListener preferenceListener;
	private String qualifier = "com.jaspersoft.studio";

	private boolean refreshMessageProviderFactory = true;
	private boolean refreshFunctionsBundles = true;
	private JavaProjectClassLoader javaclassloader;
	private List<FunctionsBundle> functionsBundles;
	private MessageProviderFactory messageProviderFactory;

	private final class PreferenceListener implements IPropertyChangeListener {

		public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent event) {
			String property = event.getProperty();
			if (property.equals(FilePrefUtil.NET_SF_JASPERREPORTS_JRPROPERTIES)) {
				isPropsCached = false;
				getProperties();
				qExecutors = null;
			} else if (property.startsWith("com.jaspersoft.studio.")) {
				isPropsCached = false;
				getProperties();
			}
			handlePrefChanged(event);
			propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, "preferences", null, event));
		}
	}

	protected void handlePrefChanged(org.eclipse.jface.util.PropertyChangeEvent event) {
	}

	private class ClasspathListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			event.getNewValue();
			refreshMessageProviderFactory = true;
			refreshFunctionsBundles = true;
			functionsBundles = null;
			qExecutors = null;
			messageProviderFactory = null;
			handleClasspathChanged(event);
			propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, "classpath", null, event));
		}
	}

	protected void handleClasspathChanged(PropertyChangeEvent event) {
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	/**
	 * @param parent
	 * @param res
	 */
	public JSSReportContext(JasperReportsContext parent, IResource res) {
		super(parent);
		init(res);
	}

	protected MScopedPreferenceStore pstore;

	public ScopedPreferenceStore getPrefStore() {
		return pstore;
	}

	public void init(IResource r) {
		IFile oldFile = (IFile) get(FileUtils.KEY_FILE);
		if (r instanceof IFile) {
			if (oldFile != null && oldFile == r)
				return;
			initRepositoryService((IFile) r);
		} else if (oldFile != null) {
			remove(FileUtils.KEY_FILE);
			initRepositoryService(null);
		}
		IProject oldPrj = (IProject) get(FileUtils.KEY_IPROJECT);
		if (oldFile == null && r instanceof IProject && oldPrj != null && oldPrj == r)
			return;

		pstore = (MScopedPreferenceStore) JasperReportsPlugin.getDefault().getPreferenceStore(r, qualifier);

		IProject project = null;
		if (r instanceof IProject)
			project = (IProject) r;
		if (r instanceof IFile) {
			put(FileUtils.KEY_FILE, r);
			project = r.getProject();
		}
		// file changed, reset properties
		isPropsCached = false;
		qExecutors = null;
		if (preferenceListener == null) {
			preferenceListener = new PreferenceListener();
			JasperReportsPlugin.getDefault().addPreferenceListener(preferenceListener, r);
		}

		if (project == oldPrj && oldPrj != null)
			return;

		if (project != null)
			put(FileUtils.KEY_IPROJECT, project);
		else
			remove(FileUtils.KEY_IPROJECT);

		initClassloader(project);
		refreshMessageProviderFactory = true;
		refreshFunctionsBundles = true;
		functionsBundles = null;
		messageProviderFactory = null;
		handleClasspathChanged(null);
	}

	protected void initClassloader(IProject project) {
		if (javaclassloader != null && classpathlistener != null) {
			javaclassloader.removeClasspathListener(classpathlistener);
			remove(JavaProjectClassLoader.JAVA_PROJECT_CLASS_LOADER_KEY);
		}
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			if (project != null && project.getNature(JavaCore.NATURE_ID) != null) {
				javaclassloader = JavaProjectClassLoader.instance(JavaCore.create(project), cl);
				put(JavaProjectClassLoader.JAVA_PROJECT_CLASS_LOADER_KEY, javaclassloader);
				classpathlistener = new ClasspathListener();
				javaclassloader.addClasspathListener(classpathlistener);
				cl = javaclassloader;
			}
			cl = insertClassLoader(cl);
			cl = new CompositeClassloader(cl, this.getClass().getClassLoader()) {
				@Override
				protected URL findResource(String name) {
					if (name.endsWith("GroovyEvaluator.groovy"))
						return null;
					return super.findResource(name);
				}

				@Override
				protected Class<?> findClass(String className) throws ClassNotFoundException {
					if (className.endsWith("GroovyEvaluator"))
						throw new ClassNotFoundException(className);
					return super.findClass(className);
				}
			};
			setClassLoader(cl);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	protected ClassLoader insertClassLoader(ClassLoader cl) {
		return cl;
	}

	protected void initRepositoryService(IFile r) {
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
		List<RepositoryService> rs = getExtensions(RepositoryService.class);
		for (RepositoryService r : rs)
			if (r instanceof DefaultRepositoryService)
				((DefaultRepositoryService) r).setClassLoader(classLoader);
		put(AbstractClasspathAwareDataAdapterService.CURRENT_CLASS_LOADER, classLoader);
	}

	public void dispose() {
		JasperReportsPlugin.getDefault().removePreferenceListener(preferenceListener);
		if (javaclassloader != null)
			javaclassloader.removeClasspathListener(classpathlistener);
		for (PropertyChangeListener l : Arrays.asList(propertyChangeSupport.getPropertyChangeListeners()))
			propertyChangeSupport.removePropertyChangeListener(l);
		if (toDispose != null)
			for (IDisposeListener d : toDispose)
				d.dispose();
	}

	private List<IDisposeListener> toDispose;

	public void addDisposeListener(IDisposeListener listener) {
		if (toDispose == null)
			toDispose = new ArrayList<>();
		toDispose.add(listener);
	}

	public void removeDisposeListener(IDisposeListener listener) {
		if (toDispose == null)
			return;
		toDispose.remove(listener);
	}

	public void put(String key, Object value) {
		setValue(key, value);
	}

	public Object get(String key) {
		return getValue(key);
	}

	public void remove(String key) {
		removeValue(key);
	}

	private ClassLoader classLoader;

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key, T def) {
		Object value = get(key);
		if (value != null && def != null && value.getClass().isAssignableFrom(def.getClass()))
			return (T) value;
		return def;
	}

	@Override
	public Map<String, String> getProperties() {
		if (isPropsCached)
			return getPropertiesMap();
		setPropertiesMap(null);
		Map<String, String> smap = super.getProperties();
		Map<String, String> propmap = new HashMap<>();
		if (smap != null && !smap.isEmpty())
			propmap.putAll(smap);
		setPropertiesMap(propmap);
		// get properties from eclipse stored jr properties (eclipse, project,
		// file level)
		Properties props = getJRProperties();
		for (Object key : props.keySet()) {
			if (!(key instanceof String))
				continue;
			String val = props.getProperty((String) key);
			if (val != null)
				propmap.put((String) key, val);
		}
		isPropsCached = true;
		// let's look also into the preferences maybe there are some properties
		pstore.setWithDefault(false);
		for (String key : propmap.keySet()) {
			String val = Misc.nullIfEmpty(pstore.getString(key));
			if (val != null)
				propmap.put(key, val);
		}
		pstore.setWithDefault(true);
		return propmap;
	}

	private boolean isPropsCached = false;
	public static final String PROPERTY_JRPROPERTY_PREFIX = "ireport.jrproperty.";

	private Properties getJRProperties() {
		Properties props = null;
		try {
			pstore.setWithDefault(false);
			props = FileUtils.load(pstore.getString(FilePrefUtil.NET_SF_JASPERREPORTS_JRPROPERTIES));
		} catch (IOException e) {
			e.printStackTrace();
			props = new Properties();
		} finally {
			pstore.setWithDefault(true);
		}
		return props;
	}

	@Override
	public String getProperty(String key) {
		pstore.setWithDefault(false);
		String val = Platform.getPreferencesService().get(key, null, pstore.getPreferenceNodes(true));
		// String val = Misc.nullIfEmpty(pstore.getString(key));
		pstore.setWithDefault(true);
		if (val != null)
			return val;
		return super.getProperty(key);

		// let's try with ireport prefix prefix ? why we need it?
		// val = Misc.nullIfEmpty(pstore.getString(PROPERTY_JRPROPERTY_PREFIX +
		// key));
		// if (val != null)
		// return val;
		// val = props.getProperty(PROPERTY_JRPROPERTY_PREFIX + key);
		// if (val != null)
		// return val;
		// return super.getProperty(PROPERTY_JRPROPERTY_PREFIX + key);
	}

	public String getPropertyDef(String key, String def) {
		String p = getProperty(key);
		if (p == null)
			p = pstore.getDefaultString(key);
		if (p == null)
			p = def;
		return p;
	}

	public String getProperty(String key, String def) {
		String p = getProperty(key);
		if (p == null)
			return def;
		return p;
	}

	public Character getPropertyCharacterDef(String key, Character def) {
		Character p = getPropertyCharacter(key);
		if (p == null) {
			String v = pstore.getDefaultString(key);
			if (v != null && !v.isEmpty())
				return v.charAt(0);
		}
		if (p == null)
			p = def;
		return p;
	}

	public Character getPropertyCharacter(String key, Character def) {
		Character p = getPropertyCharacter(key);
		if (p == null)
			p = def;
		return p;
	}

	public Character getPropertyCharacter(String key) {
		String p = getProperty(key);
		if (p != null && !p.isEmpty())
			return p.charAt(0);
		return null;
	}

	public Boolean getPropertyBoolean(String key) {
		String p = getProperty(key);
		if (p != null)
			return Boolean.parseBoolean(p);
		return null;
	}

	public Boolean getPropertyBoolean(String key, boolean def) {
		Boolean p = getPropertyBoolean(key);
		if (p == null)
			return def;
		return p;
	}

	public Boolean getPropertyBooleanDef(String key, boolean def) {
		Boolean p = getPropertyBoolean(key);
		if (p == null)
			p = def;
		return p;
	}

	public Integer getPropertyInteger(String key) {
		String p = getProperty(key);
		if (p != null)
			return Integer.valueOf(p);
		return null;
	}

	public Integer getPropertyInteger(String key, int def) {
		Integer p = getPropertyInteger(key);
		if (p == null)
			return def;
		return p;
	}

	public Integer getPropertyIntegerDef(String key, int def) {
		Integer p = getPropertyInteger(key);
		if (p == null)
			p = def;
		return p;
	}

	public Long getPropertyLong(String key) {
		String p = getProperty(key);
		if (p != null)
			return Long.valueOf(p);
		return null;
	}

	public Long getPropertyIntegerDef(String key, long def) {
		Long p = getPropertyLong(key);
		if (p == null)
			p = def;
		return p;
	}

	public Float getPropertyFloat(String key) {
		String p = getProperty(key);
		if (p != null)
			return Float.valueOf(p);
		return null;
	}

	public Double getPropertyDouble(String key) {
		String p = getProperty(key);
		if (p != null)
			return Double.valueOf(p);
		return null;
	}

	public Double getPropertyDoubleDef(String key, double def) {
		Double p = getPropertyDouble(key);
		if (p == null)
			p = def;
		return p;
	}

	public Float getPropertyFloat(String key, float def) {
		Float p = getPropertyFloat(key);
		if (p == null)
			return def;
		return p;
	}

	public Float getPropertyFloatDef(String key, float def) {
		Float p = getPropertyFloat(key);
		if (p == null)
			p = def;
		return p;
	}

	/**
	 * Return the functions extension both by resolving the property of the current
	 * project and from the commons extension. If it is available instead of request
	 * the extension from the superclass it search it in the common cache
	 * 
	 * @return a not null functions extension
	 */
	private List<FunctionsBundle> getExtensionFunctions() {
		if (functionsBundles == null || refreshFunctionsBundles) {
			// We need to be sure that the resource bundles are fresh new
			// NOTE: Let's use this for now as quick solution, in case of
			// bad performances we'll have to fix this approach
			ResourceBundle.clearCache(getClassLoader());
			functionsBundles = super.getExtensions(FunctionsBundle.class);
			Set<FunctionsBundle> fBundlesSet = new LinkedHashSet<>(functionsBundles);
			functionsBundles = new ArrayList<>(fBundlesSet);
			refreshFunctionsBundles = false;
		}
		return functionsBundles;
	}

	private List<JRQueryExecuterFactoryBundle> qExecutors;

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getExtensions(Class<T> extensionType) {
		ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
		List<T> result = null;
		try {
			if (classLoader != null) {
				Thread.currentThread().setContextClassLoader(classLoader);
			}

			if (extensionType == FunctionsBundle.class) {
				result = (List<T>) getExtensionFunctions();
			} else if (extensionType == MessageProviderFactory.class) {
				if (messageProviderFactory == null || refreshMessageProviderFactory) {
					messageProviderFactory = new ResourceBundleMessageProviderFactory(getClassLoader());
					refreshFunctionsBundles = false;
				}
				result = (List<T>) Collections.singletonList(messageProviderFactory);
			} else if (extensionType == JRQueryExecuterFactoryBundle.class) {
				try {
					if (qExecutors == null) {
						qExecutors = new ArrayList<>();
						qExecutors.add(EmptyQueryExecuterFactoryBundle.getInstance(this));
					}
					result = (List<T>) qExecutors;
				} catch (Throwable e) {
					e.printStackTrace();
				}
			} else
				result = getExtensionsByType(extensionType);
		} finally {
			Thread.currentThread().setContextClassLoader(oldCL);
		}
		return result;
	}

	protected <T> List<T> getExtensionsByType(Class<T> extensionType) {
		List<T> result = null;
		try {
			result = super.getExtensions(extensionType);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @return a default {@link JSSReportContext} instance, based on the
	 *         {@link DefaultJasperReportsContext}.
	 */
	public static JSSReportContext getNewConfig() {
		return getNewConfig(null);
	}

	public static JSSReportContext getNewConfig(IResource f) {
		return new JSSReportContext(DefaultJasperReportsContext.getInstance(), f);
	}

	private static Map<IResource, JSSReportContext> contexts = new HashMap<>();

	public static JSSReportContext getDefaultInstance(IResource f) {
		JSSReportContext c = contexts.get(f);
		if (c != null)
			return c;
		c = getNewConfig(f);
		contexts.put(f, c);
		// listen for resource close or delete and dispose
		ResourcesPlugin.getWorkspace().addResourceChangeListener(resListener,
				IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE);
		return c;
	}

	private static IResourceChangeListener resListener = new ResourceChangeReporter();

	private static class ResourceChangeReporter implements IResourceChangeListener {
		public void resourceChanged(IResourceChangeEvent event) {
			IResource res = event.getResource();
			JSSReportContext c = contexts.get(res);
			if (c != null) {
				c.dispose();
				contexts.remove(c);
			}
		}
	}

	private static JSSReportContext instance;

	/**
	 * @return a default {@link JSSReportContext} instance, based on the
	 *         {@link DefaultJasperReportsContext}.
	 */
	public static JSSReportContext getDefaultInstance() {
		if (instance == null)
			instance = getNewConfig();
		return instance;
	}

	public void refreshClasspath() {
		classpathlistener.propertyChange(null);
	}

}
