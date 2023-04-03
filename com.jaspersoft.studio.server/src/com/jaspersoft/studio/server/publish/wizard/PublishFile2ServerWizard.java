/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.publish.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.FileEditorInput;

import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.server.ServerManager;
import com.jaspersoft.studio.server.WSClientHelper;
import com.jaspersoft.studio.server.messages.Messages;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.model.server.MServerProfile;
import com.jaspersoft.studio.server.publish.PublishUtil;
import com.jaspersoft.studio.server.publish.wizard.page.AFilesLocationPage;
import com.jaspersoft.studio.server.publish.wizard.page.FileSelectionPage;
import com.jaspersoft.studio.server.publish.wizard.page.RFileLocationPage;
import com.jaspersoft.studio.server.publish.wizard.page.RFilesLocationPage;
import com.jaspersoft.studio.utils.SelectionHelper;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;

public class PublishFile2ServerWizard extends Wizard implements IExportWizard {
	private JasperReportsConfiguration jrConfig;
	private int startPage = 0;
	private List<IResource> files;
	private AFilesLocationPage page1;
	private ISelection selection;

	public PublishFile2ServerWizard() {
		super();
		setWindowTitle("Publish File To The JasperReports Server");
		setNeedsProgressMonitor(true);
	}

	public PublishFile2ServerWizard(List<IResource> files, int page) {
		this();
		this.files = files;
		this.startPage = page;
	}

	private void init() {
		if (files.isEmpty() && selection != null && selection instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) selection).getFirstElement();
			if (obj instanceof IFile || obj instanceof IFolder)
				files.add((IResource) obj);
		}
		if (jrConfig == null && files.get(0) instanceof IFile)
			jrConfig = JasperReportsConfiguration.getDefaultJRConfig((IFile) files.get(0));
	}

	@Override
	public void dispose() {
		// it is safe to dispose this context since it can only be created
		// inside this
		// class in the init() method
		if (jrConfig != null)
			jrConfig.dispose();
		super.dispose();
	}

	@Override
	public void addPages() {
		init();
		if (files.isEmpty()) {
			FileSelectionPage page0 = new FileSelectionPage(jrConfig);
			addPage(page0);
		}
		if (files.size() > 1 || files.get(0) instanceof IFolder)
			page1 = new RFilesLocationPage(jrConfig, files);
		else
			page1 = new RFileLocationPage(jrConfig, files);
		addPage(page1);
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		page1.refreshFile();
		return super.getNextPage(page);
	}

	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						monitor.beginTask("Saving", IProgressMonitor.UNKNOWN);
						List<AMResource> fres = page1.getSelectedNodes();
						if (fres != null) {
							List<String> saved = new ArrayList<>();
							for (int i = 0; i < fres.size(); i++) {
								if (files.get(i) instanceof IFile) {
									IFile f = (IFile) files.get(i);
									AMResource fr = fres.get(i);
									monitor.subTask(f.toString());

									WSClientHelper.save(monitor, fr);

									PublishUtil.savePath(f, fr);
									saved.add(fr.getValue().getUriString());
								}
							}
							StringBuilder str = new StringBuilder(Messages.Publish_0);
							for (String mres : saved)
								str.append(mres).append("\n"); //$NON-NLS-1$
							UIUtils.showInformation(str.toString());
							AMResource first = fres.get(0);
							INode n = first.getRoot();
							if (n != null && n instanceof MServerProfile) {
								MServerProfile msp = ServerManager
										.getServerByUrl(((MServerProfile) n).getValue().getUrl());
								ServerManager.selectIfExists(monitor, msp, first);
							}
						}
					} catch (Exception e) {
						throw new InvocationTargetException(e);
					} finally {
						monitor.done();
					}
				}
			});
		} catch (InvocationTargetException e) {
			UIUtils.showError(e.getCause());
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public IWizardPage getStartingPage() {
		return getPages()[Math.min(startPage, getPageCount() - 1)];
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		if (selection instanceof StructuredSelection) {
			if (selection.getFirstElement() instanceof IProject || selection.getFirstElement() instanceof IFile
					|| selection.getFirstElement() instanceof IFolder) {
				this.selection = selection;
				return;
			}
			for (Object obj : selection.toList())
				if (obj instanceof EditPart) {
					IEditorInput ein = SelectionHelper.getActiveJRXMLEditor().getEditorInput();
					if (ein instanceof FileEditorInput) {
						this.selection = new TreeSelection(
								new TreePath(new Object[] { ((FileEditorInput) ein).getFile() }));
						return;
					}
				}
		}
		this.selection = selection;
	}
}
