/*******************************************************************************
 * Copyright (C) 2013 - 2018. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.font;

import java.util.Iterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.internal.ide.misc.ContainerSelectionGroup;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;

/**
 * JRIO context.xml file creation page.
 */

public class FontExtensionNewWizardPage extends WizardPage {
	private IStructuredSelection selection;
	private ContainerSelectionGroup cmp;

	public FontExtensionNewWizardPage(String pageName, IStructuredSelection selection) {
		super(pageName);
		this.selection = selection;
		setTitle("Fonts Extension");
		setDescription("Select fonts selection file location");
	}

	@Override
	public void createControl(Composite parent) {
		cmp = new ContainerSelectionGroup(parent, event -> {
			String msg = validate();
			setErrorMessage(msg);
			setPageComplete(msg == null);
		}, false, "Font Extension", false);
		initialPopulateContainerNameField();
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(cmp);
	}

	private String validate() {
		setMessage(getDescription());
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		IResource folder = workspace.getRoot().findMember(cmp.getContainerFullPath());
		if (folder == null)
			return null;
		if (!folder.exists() || folder.isVirtual())
			return "You have to select only folders.";
		return null;
	}

	protected void initialPopulateContainerNameField() {
		if (selection != null) {
			Iterator<?> it = selection.iterator();
			if (it.hasNext()) {
				IResource selectedResource = Adapters.adapt(it.next(), IResource.class);
				if (selectedResource != null) {
					if (selectedResource.getType() == IResource.FILE)
						selectedResource = selectedResource.getParent();
					if (selectedResource.isAccessible()) {
						IResource initial = ResourcesPlugin.getWorkspace().getRoot()
								.findMember(selectedResource.getFullPath());
						if (initial != null) {
							if (!(initial instanceof IContainer))
								initial = initial.getParent();
							cmp.setSelectedContainer((IContainer) initial);
						}
					}
				}
			}
		}
		UIUtils.getDisplay().asyncExec(() -> {
			String msg = validate();
			setErrorMessage(msg);
			setPageComplete(msg == null);
		});
	}

	public IPath getContainerFullPath() {
		return cmp.getContainerFullPath();
	}
}