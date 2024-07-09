/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.classpath.container;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class JRClasspathContainerInitializer extends
		ClasspathContainerInitializer {

	@Override
	public void initialize(IPath containerPath, IJavaProject project)
			throws CoreException {
		JRClasspathContainer container = new JRClasspathContainer(
				containerPath, project);
		JavaCore.setClasspathContainer(containerPath,
				new IJavaProject[] { project },
				new IClasspathContainer[] { container }, null);
	}

}
