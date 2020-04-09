/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.publish.wizard.page;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.server.ContextHelpIDs;
import com.jaspersoft.studio.server.ServerManager;
import com.jaspersoft.studio.server.WSClientHelper;
import com.jaspersoft.studio.server.action.resource.RefreshResourcesAction;
import com.jaspersoft.studio.server.messages.Messages;
import com.jaspersoft.studio.server.model.AFileResource;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.model.MFolder;
import com.jaspersoft.studio.server.model.server.MServerProfile;
import com.jaspersoft.studio.server.model.server.MServers;
import com.jaspersoft.studio.server.publish.PublishUtil;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.wizards.JSSHelpWizardPage;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;

public abstract class AFilesLocationPage extends JSSHelpWizardPage {
	protected TreeViewer treeViewer;

	protected RefreshResourcesAction refreshAction;
	protected JasperReportsConfiguration jConfig;
	protected boolean isFillingInput;
	protected boolean canSuggestID;
	protected List<IResource> files;

	public AFilesLocationPage(String id, JasperReportsConfiguration jConfig, List<IResource> files) {
		super(id);
		this.jConfig = jConfig;
		this.files = files;
	}

	public void refreshFile() {
	}

	public abstract List<AMResource> getSelectedNodes();

	/**
	 * Return the context name for the help of this page
	 */
	@Override
	protected String getContextName() {
		return ContextHelpIDs.WIZARD_SELECT_SERVER;
	}

	@Override
	public boolean isPageComplete() {
		return super.isPageComplete() && getErrorMessage() == null && isPageCompleteLogic();
	}

	protected boolean isPageCompleteLogic() {
		TreeSelection ts = (TreeSelection) treeViewer.getSelection();
		Object firstElement = ts.getFirstElement();
		return firstElement instanceof AFileResource || firstElement instanceof MFolder;
	}

	@Override
	public void setErrorMessage(String newMessage) {
		super.setErrorMessage(newMessage);
		setPageComplete(isPageComplete());
	}

	/*
	 * Perform validation checks and eventually set the error message.
	 */
	protected void performPageChecks() {
		String errorMsg = null;
		setErrorMessage(errorMsg);
		setPageComplete(errorMsg == null);
	}

	protected boolean isRefresh = false;

	protected void handleSelectionChanged(Object obj) {

	}

	protected boolean skipEvents = false;
	protected MServers servers;

	public void fillInput() {
		Display.getDefault().asyncExec(() -> {
			isFillingInput = true;
			servers = new MServers(null);
			ServerManager.loadServerProfilesCopy(servers);
			treeViewer.setInput(servers);
			refreshFile();
			try {
				doFillInput();
			} catch (CoreException e) {
				UIUtils.showError(e);
			}
			isFillingInput = false;
		});
	}

	protected void doFillInput() throws CoreException {

	}

	protected void look4SelectedUnit(IFile f) {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					monitor.beginTask(Messages.RFileLocationPage_5, IProgressMonitor.UNKNOWN);
					try {
						List<String[]> paths = PublishUtil.loadPath(monitor, f);
						String suri = null;
						String spath = null;
						String suser = null;
						for (String[] p : paths) {
							if (p[0].startsWith("JRSUSER.")) //$NON-NLS-1$
								suser = p[1];
							else {
								suri = p[0];
								spath = p[1];
							}
						}
						if (suri != null) {
							MServerProfile msp = null;
							for (INode n : servers.getChildren()) {
								if (n instanceof MServerProfile
										&& ((MServerProfile) n).getValue().getUrl().equals(suri)) {
									if (suser != null) {
										String[] usr = suser.split("\\|"); //$NON-NLS-1$
										if (!((MServerProfile) n).getValue().getUser().equals(usr[0]))
											continue;
										if (usr.length > 1
												&& !((MServerProfile) n).getValue().getOrganisation().equals(usr[1]))
											continue;
									}
									msp = (MServerProfile) n;
									break;
								}
							}
							if (msp != null)
								selectResource(msp, spath, monitor);
						}
					} catch (Exception ce) {
						ce.printStackTrace();
					} finally {
						monitor.done();
					}
				}

				private boolean selectResource(MServerProfile msp, String uri, IProgressMonitor monitor)
						throws Exception {
					if (monitor.isCanceled())
						return true;
					ResourceDescriptor rd = new ResourceDescriptor();
					rd.setUriString(uri);
					final AMResource mres = WSClientHelper.findSelected(monitor, rd, msp);
					if (mres == null)
						return false;
					UIUtils.getDisplay().asyncExec(() -> {
						skipEvents = true;
						treeViewer.refresh();
						treeViewer.setSelection(new StructuredSelection(mres), true);
						skipEvents = false;
					});

					return true;
				}
			});
		} catch (InvocationTargetException e) {
			UIUtils.showError(e.getCause());
		} catch (InterruptedException e) {
			UIUtils.showError(e.getCause());
		}
	}
}
