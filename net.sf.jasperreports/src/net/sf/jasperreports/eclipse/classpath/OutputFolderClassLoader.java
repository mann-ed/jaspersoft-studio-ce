/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.classpath;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;

import net.sf.jasperreports.eclipse.util.FileUtils;

/**
 * Custom class loader implementation that try to re-load the specified class
 * using the generated .class file in the output folder either of the java
 * project or of the source folders classpath entries.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
@Deprecated
public class OutputFolderClassLoader extends ClassLoader {

	private IJavaProject javaProject;

	public OutputFolderClassLoader(IJavaProject javaProject) {
		super();
		this.javaProject = javaProject;
	}

	public OutputFolderClassLoader(IJavaProject javaProject, ClassLoader parent) {
		super(parent);
		this.javaProject = javaProject;
	}

	/**
	 * Tries to force the reload of the class looking the the output folder(s).
	 * 
	 * @param className
	 *            the canonical name of the class to reload
	 * @return the Class instance loaded, <code>null</code> if it could not be
	 *         located
	 */
	public Class<?> reloadClass(String className) {
		try {
			byte[] bytes = loadClassData(className);
			if (bytes != null) {
				return defineClass(className, bytes, 0, bytes.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Tries to load the specified class from the output folder(s) of the current
	 * involved java project and its required ones. First of all it searches in the
	 * project default location, if nothing is found it searches potential custom
	 * output locations for every classpath entries.
	 */
	private byte[] loadClassData(String className) throws Exception {
		String[] requiredProjectNames = javaProject.getRequiredProjectNames();
		List<IJavaProject> allProjects = new ArrayList<>();
		allProjects.add(javaProject);
		for (String reqPrjName : requiredProjectNames) {
			IJavaProject prj = javaProject.getJavaModel().getJavaProject(reqPrjName);
			if (prj.exists()) {
				allProjects.add(prj);
			}
		}
		for (IJavaProject prj : allProjects) {
			byte[] classBytes = loadClassData(className, prj);
			if (classBytes != null) {
				return classBytes;
			}
		}
		return null;
	}

	private byte[] loadClassData(String className, IJavaProject javaProject) throws Exception {
		IPath defaultLocationPath = javaProject.getOutputLocation();
		IWorkspaceRoot wsRoot = javaProject.getProject().getWorkspace().getRoot();
		IFolder folder = wsRoot.getFolder(defaultLocationPath);
		String prjDefaultLocation = folder.getLocation().toOSString();
		byte[] classBytes = loadClassFile(prjDefaultLocation + "/" + className.replaceAll("\\.", "/") + ".class");
		if (classBytes == null) {
			// Iterate over the (possible) output locations of the sourcefolder classpath
			// entries
			IClasspathEntry[] classpathEntries = javaProject.getRawClasspath();
			for (IClasspathEntry e : classpathEntries) {
				if (e.getContentKind() == IPackageFragmentRoot.K_SOURCE) {
					IPath entryOutputLocation = e.getOutputLocation();
					if (entryOutputLocation != null) {
						IFolder entryOutputFolder = wsRoot.getFolder(entryOutputLocation);
						classBytes = loadClassFile(
								entryOutputFolder + "/" + className.replaceAll("\\.", "/") + ".class");
						if (classBytes != null)
							break;
					}
				}
			}
		}
		return classBytes;
	}

	/*
	 * Loads the class file bytecode.
	 */
	private byte[] loadClassFile(String classFile) {
		File f = new File(classFile);
		if (f.exists()) {
			try {
				return FileUtils.getBytes(f);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

}
