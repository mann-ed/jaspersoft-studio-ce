/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.classpath.container;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;

/**
 * interface used in extension point, that adds/removes classpath containers to/from the
 * JasperReports project or during toggling of JR Nature
 * 
 * 
 * @author Veaceslav Chicu
 * 
 */
public interface IClasspathContainerFactory {
	/**
	 * create a classpath container, that will be added to the project
	 * 
	 * @param monitor
	 * @param centries
	 * @param javaProject
	 * @throws JavaModelException
	 */
	public void createJRClasspathContainer(IProgressMonitor monitor, List<IClasspathEntry> centries, IJavaProject javaProject) throws JavaModelException;

	/**
	 * return a set of classpath container IDs which will be removed from the
	 * project
	 * 
	 * @return a set of Classpath Containers IDs
	 */
	public Set<Path> isRemovable();
}
