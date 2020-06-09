/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.action.resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.studio.book.BookUtils;
import com.jaspersoft.studio.book.editors.JRBookEditor;
import com.jaspersoft.studio.editor.JrxmlEditor;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.server.ResourceFactory;
import com.jaspersoft.studio.server.ServerManager;
import com.jaspersoft.studio.server.WSClientHelper;
import com.jaspersoft.studio.server.editor.JRSEditorContributor;
import com.jaspersoft.studio.server.export.AExporter;
import com.jaspersoft.studio.server.export.ImageExporter;
import com.jaspersoft.studio.server.export.JrxmlExporter;
import com.jaspersoft.studio.server.messages.Messages;
import com.jaspersoft.studio.server.model.AFileResource;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.model.MJrxml;
import com.jaspersoft.studio.server.model.MReportUnit;
import com.jaspersoft.studio.server.model.server.MServerProfile;
import com.jaspersoft.studio.server.publish.PublishUtil;
import com.jaspersoft.studio.utils.SelectionHelper;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.FileUtils;

public class OpenInEditorAction extends Action {
	private static final String ID = "OPENINEDITOR"; //$NON-NLS-1$
	protected TreeViewer treeViewer;
	private boolean openInEditor = true;

	public OpenInEditorAction(TreeViewer treeViewer, boolean openInEditor) {
		this(treeViewer);
		this.openInEditor = openInEditor;

	}

	public OpenInEditorAction(TreeViewer treeViewer) {
		super();
		setId(ID);
		setText(Messages.OpenInEditorAction_title);
		setDescription(Messages.OpenInEditorAction_desc);
		setToolTipText(Messages.OpenInEditorAction_desc);
		this.treeViewer = treeViewer;
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled() && isDataResource();
	}

	private boolean isDataResource() {
		final TreeSelection s = (TreeSelection) treeViewer.getSelection();
		TreePath[] p = s.getPaths();
		for (int i = 0; i < p.length; i++) {
			if (!isFileResource(p[i].getLastSegment()))
				return false;
		}
		return true;
	}

	protected boolean isFileResource(Object obj) {
		return (obj != null && (obj instanceof AFileResource && !(obj instanceof MReportUnit)));
	}

	protected boolean preDownload(AFileResource fres, IProgressMonitor monitor) {
		INode root = fres.getRoot();
		IFolder ttroot = null;
		try {
			if (root instanceof MServerProfile)
				ttroot = ((MServerProfile) root).getTmpDir(monitor);
			else
				ttroot = FileUtils.getInProjectFolder(FileUtils.createTempDir().toURI(), monitor);
			ResourceDescriptor rd = fres.getValue();
			String f = rd.getParentFolder() + File.separator + rd.getName();
			IFile file = ttroot.getFile(f);
			if (!file.exists()) {
				IPath p = file.getRawLocation();
				if (p == null)
					p = file.getFullPath();
				File nf = p.toFile();
				nf.getParentFile().mkdirs();
				nf.createNewFile();
				file.refreshLocal(1, monitor);
			}
			path = file.getFullPath();
		} catch (IOException | CoreException e) {
			UIUtils.showError(e);
		}
		return true;
	}

	@Override
	public void run() {
		final TreeSelection s = (TreeSelection) treeViewer.getSelection();
		TreePath[] p = s.getPaths();
		for (int i = 0; i < p.length; i++) {
			final Object obj = p[i].getLastSegment();
			if (isFileResource(obj)) {
				if (preDownload((AFileResource) obj, new NullProgressMonitor())) {
					WorkspaceJob job = new WorkspaceJob(Messages.OpenInEditorAction_0) {
						public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
							try {
								monitor.beginTask(Messages.OpenInEditorAction_0, IProgressMonitor.UNKNOWN);
								dorun(obj, monitor);
							} catch (Throwable e) {
								UIUtils.showError(e);
							} finally {
								monitor.done();
							}
							return Status.OK_STATUS;
						}
					};
					job.setUser(true);
					job.schedule();
				}
				break;
			}
		}
	}

	protected IPath path;

	protected void dorun(final Object obj, IProgressMonitor monitor) throws Exception {
		if (isFileResource(obj)) {
			AFileResource res = (AFileResource) obj;
			ResourceDescriptor rd = WSClientHelper.getResource(new NullProgressMonitor(), res, res.getValue());
			ANode parent = res.getParent();
			int index = parent.getChildren().indexOf(res);
			parent.removeChild(res);
			res = (AFileResource) ResourceFactory.getResource(parent, rd, index);
			WSClientHelper.fireResourceChanged(res);

			String fkeyname = ServerManager.getKey(res);
			if (fkeyname == null)
				return;
			String type = rd.getWsType();
			IFile f = null;
			if (type.equals(ResourceDescriptor.TYPE_JRXML)) {
				doExportJrxml(res, rd, fkeyname, monitor);
				return;
			} else if (type.equals(ResourceDescriptor.TYPE_IMAGE))
				f = new ImageExporter(path).exportToIFile(res, rd, fkeyname, monitor);
			else
				f = new AExporter(path).exportToIFile(res, rd, fkeyname, monitor);

			if (f != null) {
				PublishUtil.savePath(f, res);
				openEditor(f, res);
			}
			path = null;
		}
	}

	protected void doExportJrxml(AFileResource res, ResourceDescriptor rd, String fkeyname, IProgressMonitor monitor)
			throws Exception {
		IFile f = new JrxmlExporter(path).exportToIFile(res, rd, fkeyname, monitor);
		if (f != null) {
			JasperReportsConfiguration jrconf = JasperReportsConfiguration.getDefaultJRConfig(f);
			try {
				jrconf.getPrefStore().setValue(JRSEditorContributor.KEY_PUBLISH2JSS_SILENT, true);
				openEditor(f, res);
			} finally {
				jrconf.dispose();
			}
		}
		if (res.getParent() instanceof MReportUnit) {
			MReportUnit runit = (MReportUnit) res.getParent();
			List<INode> children = runit.getChildren();
			for (int i = 0; i < children.size(); i++) {
				INode n = children.get(i);
				if (n == res)
					continue;
				if (n instanceof AFileResource) {
					AFileResource mfile = (AFileResource) n;
					fkeyname = ServerManager.getKey(mfile);
					rd = WSClientHelper.getResource(new NullProgressMonitor(), mfile, mfile.getValue());
					if (rd != null) {
						String pfolder = path != null ? path.toFile().getParentFile().getAbsolutePath() : "";

						IPath p = Path.fromOSString(pfolder + File.separator + rd.getName());
						exportFile(rd, fkeyname, monitor, f, runit, mfile, p);
					}
				}
			}
		}
	}

	private void exportFile(ResourceDescriptor rd, String fkeyname, IProgressMonitor monitor, IFile f,
			MReportUnit runit, AFileResource mfile, IPath p) throws Exception {
		AExporter exp = null;
		if (rd.getWsType().equals(ResourceDescriptor.TYPE_IMAGE))
			exp = new ImageExporter(p);
		else if (rd.getWsType().equals(ResourceDescriptor.TYPE_JRXML))
			exp = new ImageExporter(p);
		else
			exp = new AExporter(p);
		IFile file = exp.exportToIFile(mfile, rd, fkeyname, monitor);
		if (file != null) {
			PublishUtil.savePath(file, mfile);
			if (rd.getReferenceUri() != null || !rd.getUriString().startsWith(runit.getValue().getUriString()))
				createLink(file.getLocation(), rd.getName(), f, monitor);
		}
	}

	private void createLink(IPath path, String name, IFile file, IProgressMonitor monitor) throws CoreException {
		IProject project = file.getProject();
		IFile newFile = project.getFile(file.getParent().getProjectRelativePath() + "/" + name);
		newFile.createLink(path, IResource.REPLACE, monitor);
	}

	protected void openEditor(final IFile f, final AMResource res) {
		// FIXME - temporary fix to handle the case of opening a book from JRS
		BookUtils.checkFileResourceForDefaultEditor(f);
		if (!openInEditor)
			return;
		UIUtils.getDisplay().asyncExec(() -> {
			if (res instanceof MJrxml)
				if (BookUtils.isValidJRBook(f))
					SelectionHelper.openEditorType(f, JRBookEditor.BOOK_EDITOR_ID);
				else
					SelectionHelper.openEditorType(f, JrxmlEditor.JRXML_EDITOR_ID);
			else
				SelectionHelper.openEditor(f);
		});
	}

}
