/*******************************************************************************
 * Copyright (C) 2010 - 2022. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.classpath;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * Property tester for the classpath re-loading scenarios.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 * @see ReloadClasspathHandler
 *
 */
public class SupportClasspathReload extends PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		IProject prj = (IProject) receiver;
		if(property.equals("supportClassPathReload")) { //$NON-NLS-1$
			IJavaProject javaproj = JavaCore.create((IProject)prj);
			return (expectedValue.equals("SUPPORTED") && //$NON-NLS-1$
					JavaProjectClassLoader.canReloadProjectClassPath(javaproj));
		}
		else {
			return false;
		}
	}

}
