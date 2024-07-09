/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.builder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.jasperreports.eclipse.classpath.JavaProjectClassLoader;
import net.sf.jasperreports.eclipse.util.KeyValue;

public class CompatibilityManager {

	private static CompatibilityManager instance;

	public static CompatibilityManager getInstance() {
		if (instance == null)
			instance = new CompatibilityManager();
		return instance;
	}

	private CompatibilityManager() {
		final IPreferenceStore pstore = getStore();
		pstore.addPropertyChangeListener(new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(PROP_JR_VERSIONS)) {
					jrpaths.clear();
					cLoaders.clear();
					readVersions(pstore);
				}
			}
		});
		readVersions(pstore);
	}

	protected void readVersions(IPreferenceStore pstore) {
		List<JRDefinition> defs = getJRDefinitions(pstore);
		for (JRDefinition d : defs)
			jrpaths.put(d.getVersion(), d.getResourceURL());
	}

	private HashMap<String, String> jrpaths = new HashMap<String, String>();
	private HashMap<KeyValue<String, IProject>, ClassLoader> cLoaders = new HashMap<KeyValue<String, IProject>, ClassLoader>();

	public ClassLoader getClassLoader(String v, IProject javaProject) {
		KeyValue<String, IProject> key = new KeyValue<String, IProject>(v, javaProject);
		ClassLoader cl = cLoaders.get(key);
		if (cl == null) {
			File f = new File(jrpaths.get(key.key));
			Collection<File> lf = org.apache.commons.io.FileUtils.listFiles(f, new String[] { "zip", "jar" }, true);
			List<File> files = new ArrayList<File>();
			if (lf != null)
				files.addAll(lf);
			files.add(f);
			try {
				Set<URL> urls = new LinkedHashSet<URL>();
				urls.addAll(
						Arrays.asList(org.apache.commons.io.FileUtils.toURLs(files.toArray(new File[files.size()]))));
				try {
					if (javaProject.hasNature(JavaCore.NATURE_ID)) {
						IJavaProject prj = JavaCore.create(javaProject);
						prj.open(new NullProgressMonitor());
						urls.addAll(JavaProjectClassLoader.buildURLs(prj));
					}
				} catch (JavaModelException e) {
					e.printStackTrace();
				} catch (CoreException e) {
					e.printStackTrace();
				}

				cl = URLClassLoader.newInstance(urls.toArray(new URL[urls.size()]));
				cLoaders.put(key, cl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cl;
	}

	public static final String PROP_JR_VERSIONS = "com.jaspersoft.studio.jr.runtime.versions"; //$NON-NLS-1$

	private static List<JRDefinition> getJRDefinitions(IPreferenceStore pstore) {
		List<JRDefinition> list = new ArrayList<JRDefinition>();
		String versions = pstore.getString(PROP_JR_VERSIONS);
		if (versions != null && !versions.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				list = mapper.readValue(versions, new TypeReference<List<JRDefinition>>() {
				});
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private static IPreferenceStore getStore() {
		try {
			Bundle bundle = Platform.getBundle("com.jaspersoft.studio");
			bundle.getBundleContext();
			String activator = bundle.getHeaders().get(Constants.BUNDLE_ACTIVATOR);
			Class activatorClass = bundle.loadClass(activator);

			Method method = activatorClass.getMethod("getInstance");
			Plugin plugin = (Plugin) method.invoke(null);

			method = plugin.getClass().getMethod("getPreferenceStore");
			return (IPreferenceStore) method.invoke(plugin);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get the fullpath of a JR inside the storage with a specific version
	 * 
	 * @param def
	 *            definition of the requested JR
	 * @return the path to the requested version inside the storage or null if
	 *         it is not available
	 */
	public String getJRPath(String version) {
		return jrpaths.get(version);
	}

}
