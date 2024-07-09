/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.samples.wizards;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

/**
 * Action to open the wizard for the creation of the Report Sample
 * Project.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class JasperReportsSamplesProjectAction extends Action {

	@Override
	public void run() {
		SampleNewWizard wizard = new SampleNewWizard();
		wizard.init(PlatformUI.getWorkbench(), StructuredSelection.EMPTY);
		WizardDialog dialogToOpen = new WizardDialog(UIUtils.getShell(), wizard);
		dialogToOpen.create();
		dialogToOpen.open();
	}

}
