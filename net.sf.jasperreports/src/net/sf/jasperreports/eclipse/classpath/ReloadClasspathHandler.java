/*******************************************************************************
 * Copyright (C) 2010 - 2022. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.classpath;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;

/**
 * Handler for the the classpath reloading scenarios.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public class ReloadClasspathHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if(sel instanceof IStructuredSelection) {
			Object prj = ((IStructuredSelection) sel).getFirstElement();
			if(prj instanceof IProject) {
				IJavaProject javaproj = JavaCore.create((IProject)prj);
				JavaProjectClassLoader.forceClassPathReload(javaproj);
				MessageDialog.openInformation(
						UIUtils.getShell(), 
						Messages.ReloadClasspathHandler_InfoMsgTitle, 
						Messages.ReloadClasspathHandler_InfoMsgText);
			}
		}
		return null;
	}

}
