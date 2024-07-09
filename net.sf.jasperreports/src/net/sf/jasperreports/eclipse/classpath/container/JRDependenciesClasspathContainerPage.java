/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.classpath.container;

import java.util.ArrayList;

import net.sf.jasperreports.eclipse.messages.Messages;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPage;
import org.eclipse.jdt.ui.wizards.IClasspathContainerPageExtension;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * The wizard page that allows adding the classpath container library
 * <i>JasperReports Library Dependencies</i>.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class JRDependenciesClasspathContainerPage extends WizardPage
		implements IClasspathContainerPage, IClasspathContainerPageExtension {

	private ArrayList<IPath> fUsedPaths;
	private IClasspathEntry containerEntry;

	public JRDependenciesClasspathContainerPage() {
		super(Messages.JRDependenciesClasspathContainerPage_Name);
		setTitle(Messages.JRDependenciesClasspathContainerPage_Title);
		setDescription(Messages.JRDependenciesClasspathContainerPage_Description);
		setPageComplete(true);
		fUsedPaths = new ArrayList<>();
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());

		Label lbl = new Label(composite, SWT.NONE);
		lbl.setText(Messages.JRDependenciesClasspathContainerPage_Message);
		lbl.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));

		setControl(composite);
	}

	@Override
	public boolean finish() {
		return true;
	}

	@Override
	public IClasspathEntry getSelection() {
		if (containerEntry == null) {
			containerEntry = JavaCore.newContainerEntry(JRDependenciesClasspathContainer.ID);
		}
		return containerEntry;
	}

	@Override
	public void setSelection(IClasspathEntry containerEntry) {
		this.containerEntry = containerEntry;
	}

	@Override
	public void initialize(IJavaProject project, IClasspathEntry[] currentEntries) {
		for (int i = 0; i < currentEntries.length; i++) {
			IClasspathEntry curr = currentEntries[i];
			if (curr.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
				fUsedPaths.add(curr.getPath());
			}
		}
	}

}
