/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.publish.wizard.page;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.outline.ReportTreeContetProvider;
import com.jaspersoft.studio.outline.ReportTreeLabelProvider;
import com.jaspersoft.studio.server.ResourceFactory;
import com.jaspersoft.studio.server.ServerProvider;
import com.jaspersoft.studio.server.action.resource.RefreshResourcesAction;
import com.jaspersoft.studio.server.messages.Messages;
import com.jaspersoft.studio.server.model.AFileResource;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.model.MContentResource;
import com.jaspersoft.studio.server.model.MFolder;
import com.jaspersoft.studio.server.model.MReportUnit;
import com.jaspersoft.studio.server.model.server.MServerProfile;
import com.jaspersoft.studio.server.publish.PublishUtil;
import com.jaspersoft.studio.server.utils.ValidationUtils;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.ui.validator.IDStringValidator;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.eclipse.util.Misc;

public class RFileLocationPage extends AFilesLocationPage {

	private Text ruLabel;
	private Text ruID;
	private Text ruDescription;

	private IFile file;
	protected AFileResource newRes;
	protected AFileResource fileRes;

	public RFileLocationPage(JasperReportsConfiguration jConfig, List<IResource> files) {
		super("serverfilepublish", jConfig, files); //$NON-NLS-1$
		setTitle(Messages.RUnitLocationPage_title);
		setDescription(Messages.RFileLocationPage_0);
	}

	public List<AMResource> getSelectedNodes() {
		List<AMResource> fResources = new ArrayList<>();
		if (fileRes != null) {
			ResourceDescriptor rd = fileRes.getValue();
			String purl = FilenameUtils.getFullPath(rd.getUriString());
			boolean first = true;
			AFileResource fres = fileRes;
			for (IResource f : files) {
				if (!first)
					rd = new ResourceDescriptor();
				String ext = f.getFileExtension() != null ? f.getFileExtension().toLowerCase() : "";
				if (f instanceof IFolder)
					rd.setWsType(ResourceDescriptor.TYPE_FOLDER);
				else if (ext.equalsIgnoreCase("xml"))
					rd.setWsType(ResourceDescriptor.TYPE_XML_FILE);
				else if (ext.equalsIgnoreCase("jar") || ext.equalsIgnoreCase("zip"))
					rd.setWsType(ResourceDescriptor.TYPE_CLASS_JAR);
				else if (ext.equalsIgnoreCase("jrtx"))
					rd.setWsType(ResourceDescriptor.TYPE_STYLE_TEMPLATE);
				else if (ext.equalsIgnoreCase("css"))
					rd.setWsType(ResourceDescriptor.TYPE_CSS_FILE);
				else if (ext.equalsIgnoreCase("json"))
					rd.setWsType(ResourceDescriptor.TYPE_JSON_FILE);
				else if (ext.equalsIgnoreCase("properties"))
					rd.setWsType(ResourceDescriptor.TYPE_RESOURCE_BUNDLE);
				else if (ext.equalsIgnoreCase("ttf") || ext.equalsIgnoreCase("eot") || ext.equalsIgnoreCase("woff"))
					rd.setWsType(ResourceDescriptor.TYPE_FONT);
				else if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("jpg")
						|| ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("bmp") || ext.equalsIgnoreCase("tiff"))
					rd.setWsType(ResourceDescriptor.TYPE_IMAGE);
				else if (fres.getParent() instanceof MReportUnit)
					rd.setWsType(ResourceDescriptor.TYPE_RESOURCE_BUNDLE);
				if (!first) {
					rd.setName(IDStringValidator.safeChar(f.getName()));
					rd.setLabel(f.getName());
					rd.setUriString(purl + rd.getName());
				}
				fres = (AFileResource) ResourceFactory.getResource(fres.getParent(), rd, -1);
				if (f instanceof IFile)
					fres.setFile(new File(f.getRawLocationURI()));
				fResources.add(fres);
			}
		}
		return fResources;
	}

	protected boolean isPageCompleteLogic() {
		boolean isC;
		TreeSelection ts = (TreeSelection) treeViewer.getSelection();
		Object firstElement = ts.getFirstElement();
		isC = firstElement instanceof AFileResource || firstElement instanceof MFolder;
		if (isC && firstElement instanceof MFolder)
			isC = getNewRunit().getParent() != null;
		return isC;
	}

	public void refreshFile() {
		file = (IFile) jConfig.get(FileUtils.KEY_FILE);
		look4SelectedUnit(file);
	}

	public AFileResource getSelectedNode() {
		return fileRes;
	}

	protected AFileResource getNewRunit() {
		if (newRes == null) {
			ResourceDescriptor rd = AFileResource.createDescriptor(null);
			rd.setWsType(ResourceDescriptor.TYPE_CONTENT_RESOURCE);
			rd.setName(null);
			String n = getRunitName();
			PublishUtil.initResourceName(n, rd);
			rd.setLabel(n);
			newRes = new MContentResource(null, rd, -1);
			newRes.setJasperConfiguration(jConfig);
		}
		return newRes;
	}

	/*
	 * Perform validation checks and eventually set the error message.
	 */
	@Override
	protected void performPageChecks() {
		String errorMsg = null;
		errorMsg = ValidationUtils.validateName(ruID.getText());
		if (errorMsg == null)
			errorMsg = ValidationUtils.validateLabel(ruLabel.getText());
		if (errorMsg == null)
			errorMsg = ValidationUtils.validateDesc(ruDescription.getText());
		setErrorMessage(errorMsg);
		setPageComplete(errorMsg == null);
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		setControl(composite);
		composite.setLayout(new GridLayout(2, false));

		treeViewer = new TreeViewer(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 300;
		gd.heightHint = 400;
		gd.horizontalSpan = 2;
		treeViewer.getTree().setLayoutData(gd);
		treeViewer.setContentProvider(new ReportTreeContetProvider() {
			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof MFolder && fileRes != null && fileRes.getValue().getIsNew()) {
					MFolder node = (MFolder) parentElement;
					if (node.getChildren() != null && !node.getChildren().isEmpty()) {
						List<INode> children = new ArrayList<>();
						for (INode n : node.getChildren()) {
							if (n != fileRes)
								children.add(n);
						}
						return children.toArray();
					}
				}
				return super.getChildren(parentElement);
			}
		});
		treeViewer.setLabelProvider(new ReportTreeLabelProvider());
		ColumnViewerToolTipSupport.enableFor(treeViewer);

		// Report Unit shown label (resource descriptor label)
		Label lblRepoUnitName = new Label(composite, SWT.NONE);
		lblRepoUnitName.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		lblRepoUnitName.setText(Messages.AResourcePage_name);
		ruLabel = new Text(composite, SWT.BORDER);
		ruLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ruLabel.addModifyListener(e -> {
			if (isRefresh)
				return;
			isRefresh = true;
			String rtext = ruLabel.getText();
			String validationError = ValidationUtils.validateLabel(rtext);
			setErrorMessage(validationError);
			if (validationError == null) {
				ResourceDescriptor ru = getNewRunit().getValue();
				ru.setLabel(rtext);
				// suggest the ID
				if (canSuggestID) {
					ruID.setText(rtext);
					ru.setName(IDStringValidator.safeChar(rtext));
					ru.setUriString(ru.getParentFolder() + "/" + ru.getName()); //$NON-NLS-1$
				}
			}
			isRefresh = false;
		});

		// Report Unit ID (resource descriptor name)
		Label lblRepoUnitID = new Label(composite, SWT.NONE);
		lblRepoUnitID.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		lblRepoUnitID.setText(Messages.AResourcePage_id);
		ruID = new Text(composite, SWT.BORDER);
		ruID.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		ruID.addModifyListener(e -> {
			if (isRefresh)
				return;
			isRefresh = true;
			String rtext = ruID.getText();
			String validationError = ValidationUtils.validateName(rtext);
			setErrorMessage(validationError);
			if (validationError == null) {
				ResourceDescriptor ru = getNewRunit().getValue();
				ru.setName(rtext);
				ru.setUriString(ru.getParentFolder() + "/" + ru.getName()); //$NON-NLS-1$
			}
			if (!isFillingInput && validationError == null) {
				canSuggestID = false;
			} else {
				canSuggestID = true;
			}
			isRefresh = false;
		});
		// sanitize the text for the id attribute (name)
		// of the repository resource
		ruID.addVerifyListener(e -> e.text = IDStringValidator.safeChar(e.text));

		// Report Unit description
		Label lblRepoUnitDescription = new Label(composite, SWT.NONE);
		GridData descLblGD = new GridData(SWT.FILL, SWT.TOP, false, false);
		lblRepoUnitDescription.setLayoutData(descLblGD);
		lblRepoUnitDescription.setText(Messages.AResourcePage_description);
		ruDescription = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData descGD = new GridData(SWT.FILL, SWT.TOP, true, true);
		descGD.minimumHeight = 50;
		ruDescription.setLayoutData(descGD);
		ruDescription.addModifyListener(e -> {
			if (isRefresh)
				return;
			String rtext = ruDescription.getText();
			ResourceDescriptor ru = getNewRunit().getValue();
			ru.setDescription(rtext);
			setErrorMessage(ValidationUtils.validateDesc(rtext));
		});

		treeViewer.addSelectionChangedListener(event -> {
			TreeSelection ts = (TreeSelection) event.getSelection();
			Object obj = ts.getFirstElement();
			handleSelectionChanged(obj);
		});
		treeViewer.addDoubleClickListener(event -> {
			TreeSelection ts = (TreeSelection) treeViewer.getSelection();
			Object el = ts.getFirstElement();
			if (el instanceof MFolder || el instanceof MServerProfile || el instanceof MReportUnit) {
				if (treeViewer.getExpandedState(el))
					treeViewer.collapseToLevel(el, 1);
				else {
					if (refreshAction == null)
						refreshAction = new RefreshResourcesAction(treeViewer);
					if (refreshAction.isEnabled())
						refreshAction.run();
					treeViewer.expandToLevel(el, 1);
				}
			}
		});
		treeViewer.addTreeListener(new ITreeViewerListener() {
			private ServerProvider serverProvider;

			public void treeExpanded(final TreeExpansionEvent event) {
				if (!skipEvents) {
					try {
						getContainer().run(false, true, new IRunnableWithProgress() {

							public void run(IProgressMonitor monitor)
									throws InvocationTargetException, InterruptedException {
								monitor.beginTask(Messages.Publish2ServerWizard_MonitorName, IProgressMonitor.UNKNOWN);
								try {
									if (serverProvider == null)
										serverProvider = new ServerProvider();
									serverProvider.handleTreeEvent(event, monitor);
								} catch (Exception e) {
									UIUtils.showError(e);
								} finally {
									monitor.done();
								}
							}
						});
					} catch (InvocationTargetException e) {
						UIUtils.showError(e.getCause());
					} catch (InterruptedException e) {
						UIUtils.showError(e.getCause());
					}
				}
			}

			public void treeCollapsed(TreeExpansionEvent event) {
				// noting to do
			}
		});
		fillInput();
	}

	protected String getRunitName() {
		return file != null ? file.getName() : "reportunit";
	}

	@Override
	protected void handleSelectionChanged(Object obj) {
		if (isRefresh)
			return;
		isRefresh = true;

		if (obj instanceof MReportUnit) {
			fileRes = getNewRunit();
			MReportUnit pfolder = (MReportUnit) obj;
			fileRes.setParent(pfolder, -1);
			ResourceDescriptor rd = fileRes.getValue();
			rd.setUriString(pfolder.getValue().getUriString() + "/" + rd.getName()); //$NON-NLS-1$
		} else if (obj instanceof MFolder) {
			fileRes = getNewRunit();
			MFolder pfolder = (MFolder) obj;
			fileRes.setParent(pfolder, -1);
			ResourceDescriptor rd = fileRes.getValue();
			rd.setUriString(pfolder.getValue().getUriString() + "/" + rd.getName()); //$NON-NLS-1$
		} else if (obj instanceof AFileResource)
			fileRes = (AFileResource) obj;
		else
			setPageComplete(false);
		if (fileRes != null) {
			ResourceDescriptor rd = fileRes.getValue();
			ruLabel.setText(Misc.nvl(rd.getLabel()));
			ruID.setText(Misc.nvl(rd.getName()));
			ruDescription.setText(Misc.nvl(rd.getDescription()));
		}
		performPageChecks();
		isRefresh = false;
	}

}
