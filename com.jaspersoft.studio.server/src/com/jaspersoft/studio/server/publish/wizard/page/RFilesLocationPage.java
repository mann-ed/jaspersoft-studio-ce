/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.publish.wizard.page;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.outline.ReportTreeContetProvider;
import com.jaspersoft.studio.outline.ReportTreeLabelProvider;
import com.jaspersoft.studio.server.ResourceFactory;
import com.jaspersoft.studio.server.ServerProvider;
import com.jaspersoft.studio.server.action.resource.RefreshResourcesAction;
import com.jaspersoft.studio.server.messages.Messages;
import com.jaspersoft.studio.server.model.AFileResource;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.model.MFolder;
import com.jaspersoft.studio.server.model.MReportUnit;
import com.jaspersoft.studio.server.model.server.MServerProfile;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.ui.validator.IDStringValidator;

public class RFilesLocationPage extends AFilesLocationPage {

	private TreeViewer bTView;

	public RFilesLocationPage(JasperReportsConfiguration jConfig, List<IResource> files) {
		super("serverfilespublish", jConfig, files); //$NON-NLS-1$
		setTitle(Messages.RUnitLocationPage_title);
		setDescription("Select location where files will be published");
	}

	private List<AMResource> rs;

	@Override
	public List<AMResource> getSelectedNodes() {
		String puri = "/";
		if (pNode instanceof MFolder)
			puri = ((MFolder) pNode).getValue().getUriString() + "/";
		if (pNode instanceof MReportUnit)
			puri = ((MReportUnit) pNode).getValue().getUriString() + "_files/";
		List<AMResource> res = new ArrayList<>();
		for (AMResource f : rs) {
			ResourceDescriptor rd = f.getValue();
			rd.setUriString(puri + rd.getName());
			res.add(getResource(f));
		}
		return res;
	}

	private AMResource getResource(AMResource f) {
		String uri = f.getValue().getUriString();
		for (INode n : pNode.getChildren()) {
			if (n instanceof AFileResource && ((AFileResource) n).getValue().getUriString().equals(uri)
					&& f instanceof AFileResource) {
				AFileResource fr = (AFileResource) n;
				fr.setFile(((AFileResource) f).getFile());
				return fr;
			}
		}
		f.setParent(pNode, -1);
		return f;
	}

	public void refreshFile() {
//		look4SelectedUnit();
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		setControl(composite);
		composite.setLayout(new GridLayout());

		SashForm sashForm = new SashForm(composite, SWT.VERTICAL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		sashForm.setLayoutData(gd);
		createJRSView(sashForm);
		createFilesView(sashForm);

		fillInput();
	}

	private void createJRSView(Composite composite) {
		treeViewer = new TreeViewer(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 300;
		gd.heightHint = 200;
		treeViewer.getTree().setLayoutData(gd);
		treeViewer.setContentProvider(new ReportTreeContetProvider() {
			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof MFolder) {
					MFolder node = (MFolder) parentElement;
					if (node.getChildren() != null && !node.getChildren().isEmpty()) {
						List<INode> children = new ArrayList<>();
						for (INode n : node.getChildren()) {
//							if (n != fileRes)
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
	}

	@Override
	protected void doFillInput() throws CoreException {
		rs = new ArrayList<>();
		for (IResource f : files)
			rs.add(doGetResource(f, null));
		bTView.setInput(rs);
	}

	private AMResource doGetResource(IResource r, AMResource parent) throws CoreException {
		ResourceDescriptor rd = new ResourceDescriptor();
		rd.setName(IDStringValidator.safeChar(r.getName()));
		rd.setLabel(r.getName());
		String ext = r.getFileExtension() != null ? r.getFileExtension().toLowerCase() : "";
		if (r instanceof IFolder) {
			rd.setWsType(ResourceDescriptor.TYPE_FOLDER);
			MFolder mf = (MFolder) ResourceFactory.getResource(parent, rd, -1);
			mf.removeChildren();
			IFolder fd = (IFolder) r;
			for (IResource ff : fd.members())
				doGetResource(ff, mf);
			return mf;
		} else if (ext.equalsIgnoreCase("xml"))
			rd.setWsType(ResourceDescriptor.TYPE_XML_FILE);
		else if (ext.equalsIgnoreCase("jar") || ext.equalsIgnoreCase("zip"))
			rd.setWsType(ResourceDescriptor.TYPE_CLASS_JAR);
		else if (ext.equalsIgnoreCase("jrtx"))
			rd.setWsType(ResourceDescriptor.TYPE_STYLE_TEMPLATE);
		else if (ext.equalsIgnoreCase("css"))
			rd.setWsType(ResourceDescriptor.TYPE_CSS_FILE);
		else if (ext.equalsIgnoreCase("jrxml"))
			rd.setWsType(ResourceDescriptor.TYPE_JRXML);
		else if (ext.equalsIgnoreCase("json"))
			rd.setWsType(ResourceDescriptor.TYPE_JSON_FILE);
		else if (ext.equalsIgnoreCase("properties"))
			rd.setWsType(ResourceDescriptor.TYPE_RESOURCE_BUNDLE);
		else if (ext.equalsIgnoreCase("ttf") || ext.equalsIgnoreCase("eot") || ext.equalsIgnoreCase("woff"))
			rd.setWsType(ResourceDescriptor.TYPE_FONT);
		else if (ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("jpg")
				|| ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("bmp") || ext.equalsIgnoreCase("tiff"))
			rd.setWsType(ResourceDescriptor.TYPE_IMAGE);
		else
			rd.setWsType(ResourceDescriptor.TYPE_CONTENT_RESOURCE);

		AMResource resource = (AMResource) ResourceFactory.getResource(parent, rd, -1);
		if (r instanceof IFile && resource instanceof AFileResource)
			((AFileResource) resource).setFile(((IFile) r).getRawLocation().toFile());
		return resource;
	}

	protected void createFilesView(Composite composite) {
		bTView = new TreeViewer(composite, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		bTView.getTree().setLayoutData(gd);
		bTView.setContentProvider(new ReportTreeContetProvider() {
			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof List)
					return ((List) parentElement).toArray();

//				if (parentElement instanceof MFolder && fileRes != null && fileRes.getValue().getIsNew()) {
//					MFolder node = (MFolder) parentElement;
//					if (node.getChildren() != null && !node.getChildren().isEmpty()) {
//						List<INode> children = new ArrayList<>();
//						for (INode n : node.getChildren()) {
//							if (n != fileRes)
//								children.add(n);
//						}
//						return children.toArray();
//					}
//				}
				return super.getChildren(parentElement);
			}
		});
		bTView.setLabelProvider(new ReportTreeLabelProvider());
		ColumnViewerToolTipSupport.enableFor(bTView);

		bTView.addSelectionChangedListener(event -> {
			TreeSelection ts = (TreeSelection) event.getSelection();
			Object obj = ts.getFirstElement();
			handleSelectionChanged(obj);
		});
		bTView.addDoubleClickListener(event -> {
			TreeSelection ts = (TreeSelection) bTView.getSelection();
			Object el = ts.getFirstElement();

		});
	}

	protected ANode pNode;

	protected void handleSelectionChanged(Object obj) {
		if (isRefresh)
			return;
		isRefresh = true;

		if (obj instanceof MReportUnit || obj instanceof MFolder || obj instanceof AFileResource
				|| obj instanceof MServerProfile)
			pNode = (ANode) obj;
		else
			setPageComplete(false);

		performPageChecks();
		isRefresh = false;
	}

}
