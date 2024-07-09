/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.classpath;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.jar.JarFile;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.JavaRuntime;

import net.sf.jasperreports.eclipse.classpath.container.JRClasspathContainer;
import net.sf.jasperreports.eclipse.util.FileExtension;
import net.sf.jasperreports.eclipse.util.Misc;

public class JavaProjectClassLoader extends ClassLoader {

	public static final String JAVA_PROJECT_CLASS_LOADER_KEY = "javaProjectClassLoader"; //$NON-NLS-1$
	private static final String PROTOCOL_PREFIX = "file:///"; //$NON-NLS-1$
	private static final String FILE_SCHEME = "file"; //$NON-NLS-1$
	private boolean calcURLS = false;
	private static Map<IJavaProject, JavaProjectClassLoader> map = new HashMap<>();

	private URLClassLoader curlLoader;
	private IJavaProject javaProject;
	private IElementChangedListener listener;
	private PropertyChangeSupport events;

	public static ClassLoader instance(IJavaProject project) {
		return instance(project, null);
	}

	public static JavaProjectClassLoader instance(IJavaProject project, ClassLoader classLoader) {
		JavaProjectClassLoader cl = map.get(project);
		if (cl == null) {
			cl = new JavaProjectClassLoader(project, classLoader);
			map.put(project, cl);
		}
		return cl;
	}
	
	/**
	 * Triggers the Java project classpath re-load via the proper event.
	 * 
	 * @param proj the Java project 
	 * @return <code>true</code> if the reload has been triggered, <code>false</code> otherwise
	 */
	public static boolean forceClassPathReload(IJavaProject proj) {
		JavaProjectClassLoader jpcl = map.get(proj);
		if(jpcl!=null && jpcl.events!=null) {
			jpcl.events.firePropertyChange("classpath", false, true);
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the specified project is suitable for a possible classpath re-load.
	 * 
	 * @param proj the Java project to look for
	 * @return <code>true</code> if the classpath project can be reloaded,<code>false</code> otherwise
	 */
	public static boolean canReloadProjectClassPath(IJavaProject proj) {
		return map.get(proj)!=null;
	}
	
	private JavaProjectClassLoader(IJavaProject project) {
		super();
		init(project);
	}

	private JavaProjectClassLoader(IJavaProject project, ClassLoader classLoader) {
		super(classLoader);
		init(project);
	}

	protected void init(IJavaProject project) {
		if (project == null || !project.exists() || !project.getResource().isAccessible())
			throw new IllegalArgumentException("Invalid javaProject");
		this.javaProject = project;
		getURLClassloader();

		try {
			final IPath prjPath = javaProject.getProject().getFullPath();
			final IPath prjOut = javaProject.getOutputLocation();
			IResourceChangeListener plistener = new IResourceChangeListener() {

				private boolean checkInPaths(IResourceDelta rd) {
					IPath fp = rd.getFullPath();
					if (fp != null) {
						if (prjOut.isPrefixOf(fp)) {
							String rpath = rd.getProjectRelativePath().lastSegment();
							if (rpath.endsWith(".class") || rpath.endsWith(".properties")) {
								clean(curlLoader);
								curlLoader = null;
								getURLClassloader();
								if (events != null) {
									// makes sense to trigger the classloader re-build because
									// changes to the classes or property files might indicate
									// new extensions or modified Java code
									System.out.println("Invoking classpath reload");
									events.firePropertyChange("classpath", false, true);
								}
								return true;
							}
						}
						if (fp.matchingFirstSegments(prjOut) <= 0)
							return true;
					}
					return false;
				}

				private void checkResource(IResourceDelta[] children) {
					for (IResourceDelta rd : children) {
						if (!prjPath.isPrefixOf(rd.getFullPath()))
							return;
						if (!Misc.isNullOrEmpty(rd.getAffectedChildren()))
							checkResource(rd.getAffectedChildren());
						else if (checkInPaths(rd))
							return;
					}
				}

				public void resourceChanged(IResourceChangeEvent event) {
					final IResourceDelta delta = event.getDelta();
					if (delta != null)
						checkResource(delta.getAffectedChildren());
				}
			};
			ResourcesPlugin.getWorkspace().addResourceChangeListener(plistener, IResourceChangeEvent.POST_CHANGE);
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		listener = new IElementChangedListener() {

			public void elementChanged(final ElementChangedEvent event) {
				if (!isClasspathReloadNeeded(event.getDelta()))
					return;
				// FIXME should release this classloader
				// what happend with current objects? we have 1 loader per
				// project, maybe se can filter some events? to have less
				// updates
				clean(curlLoader);
				curlLoader=null;
				getURLClassloader();
				if (events != null && !isToIgnore(event.getDelta().getAffectedChildren())) {
					System.out.println("Invoking classpath reload");
					events.firePropertyChange("classpath", false, true);
				}
			}

			private boolean isClasspathReloadNeeded(IJavaElementDelta delta) {
				switch (delta.getElement().getElementType()) {
				case IJavaElement.JAVA_MODEL:
					// It corresponds to the workspace representation, we need to investigate
					// further its children
					for (IJavaElementDelta c : delta.getAffectedChildren())
						if (isClasspathReloadNeeded(c))
							return true;
					break;
				case IJavaElement.JAVA_PROJECT:
					// It corresponds to the project structure. We might detect a direct classpath
					// change
					if (delta.getKind() == IJavaElementDelta.REMOVED) {
						map.remove(JavaProjectClassLoader.this.javaProject);
						JavaCore.removeElementChangedListener(listener);
						return true;
					}
					if (0 != (delta.getFlags()
							& (IJavaElementDelta.F_CLASSPATH_CHANGED | IJavaElementDelta.F_RESOLVED_CLASSPATH_CHANGED
									| IJavaElementDelta.F_CLOSED | IJavaElementDelta.F_REMOVED_FROM_CLASSPATH))) {
						return true;
					}
					// Or we might need to investigate further among its children
					else {
						for (IJavaElementDelta c : delta.getAffectedChildren())
							if (isClasspathReloadNeeded(c))
								return true;
					}
				case IJavaElement.PACKAGE_FRAGMENT_ROOT:
					// It corresponds to an underlying resource which is either a folder, JAR, or
					// zip.
					if ((delta.getFlags()
							& (IJavaElementDelta.F_CONTENT | IJavaElementDelta.F_ARCHIVE_CONTENT_CHANGED)) != 0) {
						if (isElementNameAllowed(delta.getElement().getElementName())) {
							return true;
						}
					} else {
						for (IJavaElementDelta c : delta.getAffectedChildren()) {
							if (isClasspathReloadNeeded(c)) {
								return true;
							}
						}
					}
				case IJavaElement.PACKAGE_FRAGMENT:
					// A package fragment is a portion of the workspace corresponding to an entire
					// package, or to a portion thereof.
					for (IJavaElementDelta c : delta.getAffectedChildren()) {
						if (c.getElement() instanceof IClassFile
								&& ((c.getFlags() & IJavaElementDelta.F_CONTENT) != 0)) {
							if (isElementNameAllowed(c.getElement().getElementName())) {
								return true;
							}
						}
					}
				default:
					break;
				}
				return false;
			}

			private boolean isToIgnore(IJavaElementDelta[] children) {
				if (children == null || children.length == 0)
					return true;
				for (IJavaElementDelta jdelta : children) {
					IResourceDelta[] rd = jdelta.getResourceDeltas();
					if (rd == null || rd.length == 0)
						if (!isToIgnore(jdelta.getAffectedChildren()))
							return false;
					if (rd != null)
						for (IResourceDelta delta : rd)
							if (delta.getResource() instanceof IFile) {
								String fe = delta.getFullPath().getFileExtension();
								if (!(fe.equals( FileExtension.JASPER) || fe.equals( FileExtension.JRXML) || fe.equals( FileExtension.JRTX)
										|| FileExtension.isImage(delta.getFullPath().toOSString())))
									return false;
							} else
								return false;
				}
				return true;
			}

			/*
			 * Checks if the element might be interesting for a possible classpath reload
			 * event.
			 */
			private boolean isElementNameAllowed(String elName) {
				return elName != null
						&& (elName.endsWith(".jar") || elName.endsWith(".class") || elName.endsWith(".properties"));
			}
		};
		JavaCore.addElementChangedListener(listener, ElementChangedEvent.POST_CHANGE);
	}

	public void addClasspathListener(PropertyChangeListener l) {
		if (events == null)
			events = new PropertyChangeSupport(this);
		events.addPropertyChangeListener(l);
	}

	public void removeClasspathListener(PropertyChangeListener l) {
		if (events == null)
			events = new PropertyChangeSupport(this);
		events.removePropertyChangeListener(l);
	}

	@Override
	protected URL findResource(String name) {
		if (name.endsWith(".groovy"))
			return null;
		if (name.endsWith("commons-logging.properties"))
			return null;
//		System.out.println(">>> RESOURCE >>> " + name);
		if (curlLoader != null)
			return curlLoader.getResource(name);
		return null;
	}

	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		if (name.endsWith("commons-logging.properties"))
			return null;
		if (curlLoader != null)
			return curlLoader.getResources(name);
		return null;
	}

	@Override
	public URL getResource(String name) {
		if (name.endsWith("commons-logging.properties"))
			return null;
		return super.getResource(name);
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		if (name.endsWith("commons-logging.properties"))
			return null;
		return super.getResources(name);
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		if (name.endsWith("commons-logging.properties"))
			return null;
		return super.getResourceAsStream(name);
	}

	@Override
	protected Class findClass(String className) throws ClassNotFoundException {
		if (!"net.sf.jasperreports.compilers.GroovyEvaluator".equals(className)) {
			// The code commented would help in excluding other non-existing
			// classes
			// that are tried to be loaded. Root cause seems to be Spring
			// mechanism
			// of looking for BeanInfo classes
			// if((className.endsWith("Customizer") ||
			// className.endsWith("BeanInfo")) &&
			// (className.startsWith("com.jaspersoft") ||
			// className.startsWith("net.sf.jasperreports"))){
			// throw new ClassNotFoundException(className);
			// }
			for(String suffix : ClassLoaderUtil.EXCLUDE_SUFFIXES) {
				if(className.endsWith(suffix)) {
					throw new ClassNotFoundException(className);
				}
			}
			if (ClassLoaderUtil.packages.contains(className))
				throw new ClassNotFoundException(className);
		}
//		System.out.println(">>> CLASS >>> " + className);
		if (curlLoader != null)
			return curlLoader.loadClass(className);
		throw new ClassNotFoundException(className);
	}

	private static URL computeForURLClassLoader(String classpath) throws MalformedURLException {
		if (!classpath.endsWith("/")) {
			File file = new File(classpath);
			if (file.exists() && file.isDirectory())
				classpath = classpath.concat("/");
		}
		return new URL(PROTOCOL_PREFIX + classpath);
	}

	private ClassLoader getURLClassloader() {
		if (curlLoader == null) {
			try {
				if (calcURLS)
					return getParent();
				calcURLS = true;
				Set<URL> urls = buildURLs(javaProject);

				getURLClassloader(urls.toArray(new URL[urls.size()]));
				calcURLS = false;
			} catch (JavaModelException e1) {
				e1.printStackTrace();
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}
		return curlLoader;
	}

	public static Set<URL> buildURLs(IJavaProject javaProject) throws CoreException {
		JRClasspathContainer jrcnt = (JRClasspathContainer) JavaCore.getClasspathContainer(JRClasspathContainer.ID,
				javaProject);
		List<String> jrcntpaths = null;
		if (jrcnt != null) {
			IClasspathEntry[] ces = jrcnt.getAllClasspathEntries();
			if (ces != null && ces.length > 0) {
				jrcntpaths = new ArrayList<>();
				for (IClasspathEntry en : ces)
					jrcntpaths.add(en.getPath().toOSString());
			}
		}
		String[] classPaths = JavaRuntime.computeDefaultRuntimeClassPath(javaProject);
		Set<URL> urls = new LinkedHashSet<>();
		for (int i = 0; i < classPaths.length; i++)
			try {
				if (jrcntpaths != null && jrcntpaths.contains(classPaths[i]))
					continue;
				urls.add(computeForURLClassLoader(classPaths[i]));
			} catch (MalformedURLException e) {
			}

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IClasspathEntry[] entries = javaProject.getResolvedClasspath(true);
		resolveClasspathEntries(urls, root, entries, javaProject);
		return urls;
	}

	public static void resolveClasspathEntries(Set<URL> urls, IWorkspaceRoot root, IClasspathEntry[] entries,
			IJavaProject javaProject) throws JavaModelException {
		for (int i = 0; i < entries.length; i++) {
			IClasspathEntry entry = entries[i];
			if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				IPath path = entry.getPath();
				if (path.segmentCount() >= 2) {
					IFolder sourceFolder = root.getFolder(path);
					try {
						urls.add(sourceFolder.getRawLocation().toFile().toURI().toURL());
					} catch (MalformedURLException e) {
					}
				}
			} else if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				IPath sourcePath = entry.getPath();
				covertPathToUrl(javaProject.getProject(), urls, sourcePath);
				IPath sourceOutputPath = entry.getOutputLocation();
				covertPathToUrl(javaProject.getProject(), urls, sourceOutputPath);
			} else if (entry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
				if (entry.getPath().equals(JRClasspathContainer.ID))
					continue;
				IClasspathContainer cont = JavaCore.getClasspathContainer(entry.getPath(), javaProject);
				resolveClasspathEntries(urls, root, cont.getClasspathEntries(), javaProject);
			}
		}
	}

	private static void addUri(Set<URL> paths, URI uri) {
		try {
			File file = new File(uri);
			if (file.isDirectory())
				paths.add(new URL(uri.toString() + File.separator));
			else
				paths.add(uri.toURL());
		} catch (MalformedURLException e) {
			// ignore error
		}
	}

	private static void covertPathToUrl(IProject project, Set<URL> paths, IPath path) {
		if (path != null && project != null && path.removeFirstSegments(1) != null
				&& project.findMember(path.removeFirstSegments(1)) != null) {

			URI uri = project.findMember(path.removeFirstSegments(1)).getRawLocationURI();

			if (uri != null) {
				String scheme = uri.getScheme();
				if (FILE_SCHEME.equalsIgnoreCase(scheme))
					addUri(paths, uri);
				else if ("sourcecontrol".equals(scheme)) {
					// special case of Rational Team Concert
					IPath sourceControlPath = project.findMember(path.removeFirstSegments(1)).getLocation();
					File sourceControlFile = sourceControlPath.toFile();
					if (sourceControlFile.exists())
						addUri(paths, sourceControlFile.toURI());
				} else {
					IPathVariableManager variableManager = ResourcesPlugin.getWorkspace().getPathVariableManager();
					addUri(paths, variableManager.resolveURI(uri));
				}
			}
		}
	}

	private synchronized ClassLoader getURLClassloader(URL[] urls) {
		if (curlLoader != null) {
			clean(curlLoader);
			curlLoader = null;
		}
		curlLoader = URLClassLoader.newInstance(urls, getParent());
		return curlLoader;
	}

	public static void clean(URLClassLoader cl) {
		if (cl != null)
			try {
				cl.close();
				Field f = URLClassLoader.class.getDeclaredField("ucp");
				f.setAccessible(true);
				Object cp = f.get(cl);
				f = cp.getClass().getDeclaredField("loaders");
				f.setAccessible(true);
				Object loaders = f.get(cp);
				if (loaders != null)
					for (Object l : (java.util.Collection<?>) loaders) {
						try {
							f = l.getClass().getDeclaredField("jar");
							f.setAccessible(true);
							Object jar = f.get(l);
							if (jar instanceof JarFile)
								((JarFile) jar).close();
						} catch (Throwable t) {
						}
					}
				f = URLClassLoader.class.getDeclaredField("nativeLibraries");
				f.setAccessible(true);
				Vector<?> nl = (Vector<?>) f.get(cl);
				if (nl != null)
					for (Object lib : nl) {
						Method finalize = lib.getClass().getDeclaredMethod("finalize", new Class[0]);
						finalize.setAccessible(true);
						finalize.invoke(lib, new Object[0]);
					}
			} catch (Throwable t) {
			}
	}

}
