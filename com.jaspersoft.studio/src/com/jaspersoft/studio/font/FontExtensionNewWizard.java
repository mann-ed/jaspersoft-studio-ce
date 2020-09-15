/*******************************************************************************
 * Copyright (C) 2013 - 2018. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.font;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.fonts.SimpleFontExtensionHelper;
import net.sf.jasperreports.engine.fonts.SimpleFontExtensionsContainer;

/**
 * Wizard to create jrio context files.
 */

public class FontExtensionNewWizard extends Wizard implements INewWizard {
	private FontExtensionNewWizardPage page;
	private ISelection selection;

	public FontExtensionNewWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */
	@Override
	public void addPages() {
		page = new FontExtensionNewWizardPage("filepage", (IStructuredSelection) selection); //$NON-NLS-1$
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	@Override
	public boolean performFinish() {
		final IPath path = page.getContainerFullPath();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(path, FontEditor.DEFAULT_FILENAME, monitor);
				} catch (CoreException | JRException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException | InvocationTargetException e) {
			UIUtils.showError(e.getCause());
			return false;
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the file if missing
	 * or just replace its contents, and open the editor on the newly created
	 * file.
	 * 
	 * @throws JRException
	 */

	private void doFinish(IPath path, String fname, IProgressMonitor monitor) throws CoreException, JRException {
		// create a sample file
		monitor.beginTask("Creating fonts extension file: " + fname, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFolder f = root.getFolder(path);
		if (!f.exists())
			FileUtils.createFolder(f, monitor);

		final IFile file = f.getFile(new Path(fname));
		try (InputStream stream = openContentStream();) {
			if (file.exists())
				file.setContents(stream, true, true, monitor);
			else
				file.create(stream, true, monitor);
			IDE.setDefaultEditor(file, FontEditor.ID);
		} catch (IOException e) {
			UIUtils.showError(e);
		}
		monitor.worked(1);
		monitor.setTaskName("Opening the editor");
		getShell().getDisplay().asyncExec(() -> {
			try {
				IWorkbenchPage p = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				if (p != null)
					IDE.openEditor(p, file, FontEditor.ID, true); // $NON-NLS-1$
			} catch (PartInitException e) {
				UIUtils.showError(e);
			}
		});
		monitor.worked(1);
	}

	/**
	 * We will initialize file contents with a sample text.
	 * 
	 * @throws JRException
	 */

	private InputStream openContentStream() throws JRException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		SimpleFontExtensionHelper.writeFontExtensionsXml(os,
				new SimpleFontExtensionsContainer(new ArrayList<>(), new ArrayList<>()));
		return new ByteArrayInputStream(os.toByteArray());
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
}