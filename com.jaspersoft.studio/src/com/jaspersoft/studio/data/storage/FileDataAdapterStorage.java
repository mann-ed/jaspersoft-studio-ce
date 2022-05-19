/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.data.storage;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.ide.IDE;
import org.exolab.castor.mapping.Mapping;
import org.xml.sax.InputSource;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.DataAdapterEditorPart;
import com.jaspersoft.studio.data.DataAdapterFactory;
import com.jaspersoft.studio.data.DataAdapterManager;
import com.jaspersoft.studio.data.DataAdapterUtils;
import com.jaspersoft.studio.data.DefaultDataAdapterDescriptor;
import com.jaspersoft.studio.data.customadapters.JSSCastorUtil;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.utils.XMLUtils;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.CastorHelper;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.util.JacksonRuntimException;
import net.sf.jasperreports.util.JacksonUtil;

public class FileDataAdapterStorage extends ADataAdapterStorage {

	private IProject project;

	private static XMLInputFactory inputFactory = XMLInputFactory.newInstance();

	private final class ResourceVisitor implements IResourceProxyVisitor {
		public boolean visit(IResourceProxy proxy) throws CoreException {
			if (proxy.isTeamPrivateMember())
				return false;
			if (proxy.getType() == IResource.FOLDER)
				return true;
			if (proxy.getType() == IResource.FILE) {
				IPath fpath = proxy.requestFullPath();
				if (fpath != null) {
					String fext = fpath.getFileExtension();
					if (fext != null && DataAdapterUtils.isSupportedFileExtension(fext)) //$NON-NLS-1$
						checkFile((IFile) proxy.requestResource());
				}
			}
			return true;
		}
	}

	public FileDataAdapterStorage(IProject project) {
		this.project = project;
		this.daDescriptors = new LinkedHashMap<>();
	}

	@Override
	public void findAll() {
		try {
			if (project.isOpen()) {
				IResource[] members = project.members();
				if (members != null && members.length > 0) {
					Job job = new WorkspaceJob(Messages.FileDataAdapterStorage_1) {
						private IResourceChangeListener rcl;

						public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
							listenWorkspace();
							monitor.beginTask(Messages.FileDataAdapterStorage_2 + project.getName(), 10);
							project.accept(new ResourceVisitor(), IResource.DEPTH_INFINITE,
									IContainer.EXCLUDE_DERIVED | IContainer.DO_NOT_CHECK_EXISTENCE);

							if (monitor.isCanceled())
								return Status.CANCEL_STATUS;
							monitor.internalWorked(10);

							return Status.OK_STATUS;
						}

						protected void listenWorkspace() {
							IWorkspace wspace = ResourcesPlugin.getWorkspace();
							rcl = new IResourceChangeListener() {
								public void resourceChanged(IResourceChangeEvent event) {
									final IResourceDelta delta = event.getDelta();
									if (delta == null)
										return;
									IResourceDelta docDelta = delta.findMember(project.getFullPath());
									if (docDelta == null)
										return;
									if (!(delta.getKind() == IResourceDelta.ADDED
											|| delta.getKind() == IResourceDelta.REMOVED
											|| (delta.getKind() == IResourceDelta.CHANGED && ((delta.getFlags()
													& (IResourceDelta.CONTENT | IResourceDelta.ADDED
															| IResourceDelta.MOVED_TO | IResourceDelta.CHANGED
															| IResourceDelta.COPIED_FROM | IResourceDelta.REPLACED
															| IResourceDelta.SYNC | IResourceDelta.MOVED_FROM)) == 0))))
										return;
									// we get event of file creation but file is
									// empty, so we have to wait 1sec to give
									// the possibility to
									// write into it
									new WorkspaceJob(Messages.FileDataAdapterStorage_1) {
										public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
											monitor.beginTask(Messages.FileDataAdapterStorage_2 + project.getName(),
													10);
											processEvent(delta);
											if (monitor.isCanceled())
												return Status.CANCEL_STATUS;
											monitor.internalWorked(10);

											return Status.OK_STATUS;
										}
									}.schedule(100);
								}

								protected void processEvent(IResourceDelta delta) {
									try {
										delta.accept(new IResourceDeltaVisitor() {
											public boolean visit(IResourceDelta delta) throws CoreException {
												final IResource res = delta.getResource();
												if (res.getType() == IResource.PROJECT && res.equals(project)
														&& delta.getKind() == IResourceDelta.REMOVED) {
													DataAdapterManager.removeProject((IProject) res);
													wspace.removeResourceChangeListener(rcl);
													return false;
												}
												if (res.getType() == IResource.FILE
														&& DataAdapterUtils.isSupportedFileExtension(res.getFileExtension())) //$NON-NLS-1$
													switch (delta.getKind()) {
													case IResourceDelta.ADDED:
														// checkFile((IFile)
														// res);
														break;
													case IResourceDelta.REMOVED:
														UIUtils.getDisplay().asyncExec(() -> {
															DataAdapterDescriptor das = findDataAdapter(
																	res.getProjectRelativePath().toOSString());
															removeDataAdapter(das);
														});
														break;
													case IResourceDelta.CHANGED:
														UIUtils.getDisplay().asyncExec(() -> {
															DataAdapterDescriptor das = findDataAdapter(
																	res.getProjectRelativePath().toOSString());
															if (das != null) {
																FileDataAdapterStorage.super.removeDataAdapter(das);
																try {
																	IFile file = (IFile) res;
																	das = readDataADapter(file.getContents(), file);
																	das.setName(
																			file.getProjectRelativePath().toOSString());
																	FileDataAdapterStorage.super.addDataAdapter(das);
																} catch (CoreException e) {
																	UIUtils.showError(e);
																}
															} else
																try {
																	checkFile((IFile) res);
																} catch (CoreException e) {
																	UIUtils.showError(e);
																}
														});
														break;
													}
												return true;
											}
										});
									} catch (CoreException e) {
										UIUtils.showError(e);
									}
								}
							};
							wspace.addResourceChangeListener(rcl);
						}
					};
					job.schedule();
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean addDataAdapter(DataAdapterDescriptor adapter) {
		boolean result = super.addDataAdapter(adapter);
		if (result) {
			IFile file = project.getFile(adapter.getName());
			JasperReportsConfiguration jConf = JasperReportsConfiguration.getDefaultJRConfig(file);
			try {
				String xml = DataAdapterManager.toDataAdapterFile(adapter, jConf);
				if (file.exists())
					file.setContents(new ByteArrayInputStream(xml.getBytes()), true, true, new NullProgressMonitor());
				else
					file.create(new ByteArrayInputStream(xml.getBytes()), true, new NullProgressMonitor());
				return true;
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				jConf.dispose();
			}
		}
		return false;
	}

	@Override
	public boolean removeDataAdapter(DataAdapterDescriptor da) {
		boolean result = super.removeDataAdapter(da);
		if (result) {
			IFile file = project.getFile(da.getName());
			if (file.exists()) {
				try {
					file.delete(true, new NullProgressMonitor());
					return true;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return false;
	}

	private void checkFile(final IFile file) throws CoreException {
		if (!file.isAccessible() || file.isDerived() || file.isPhantom() || file.isHidden() || file.isVirtual()
				|| !file.exists())
			return;
		String ext = file.getFileExtension();
		if (DataAdapterUtils.isSupportedFileExtension(ext)) { //$NON-NLS-1$
			final DataAdapterDescriptor das = readDataADapter(file.getContents(), file);
			if (das != null) {
				das.setName(file.getProjectRelativePath().toOSString());
				UIUtils.getDisplay().asyncExec(() -> {
					FileDataAdapterStorage.super.addDataAdapter(das);
					IDE.setDefaultEditor(file, DataAdapterEditorPart.ID);
				});
			}
		}
	}

	private static String readXML(InputStream in) {
		try {
			XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
			streamReader.nextTag();
			if (streamReader.getName().getLocalPart().endsWith("DataAdapter"))
				for (int i = 0; i < streamReader.getAttributeCount(); i++)
					if (streamReader.getAttributeName(i).getLocalPart().equals("class")) //$NON-NLS-1$
						return streamReader.getAttributeValue(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static DataAdapterDescriptor readDataADapter(InputStream in, IFile file) {
		JasperReportsConfiguration jConf = null;
		DataAdapterDescriptor da = null;
		try {
			in = new BufferedInputStream(in);
			in.mark(Integer.MAX_VALUE);
			String className = readXML(in);
			if (!Misc.isNullOrEmpty(className)) {
				in.reset();
				jConf = JasperReportsConfiguration.getDefaultJRConfig(file);
				da = readDataADapter(in, file, jConf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jConf != null)
				jConf.dispose();
			FileUtils.closeStream(in);
		}
		return da;
	}

	public static DataAdapterDescriptor readDataADapter(InputStream in, IFile file,
			JasperReportsConfiguration jrConfig) {
		try {
			in = new BufferedInputStream(in);
			in.mark(Integer.MAX_VALUE);
			String className = readXML(in);

			if (!Misc.isNullOrEmpty(className)) {
				in.reset();
				return readDataADapter(in, file, jrConfig, className);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			FileUtils.closeStream(in);
		}
		return null;
	}

	private static DataAdapterDescriptor readDataADapter(InputStream in, IFile file,
			JasperReportsConfiguration jrConfig, String className) {
		DataAdapterDescriptor dad = null;
		try {
			DataAdapterFactory factory = DataAdapterManager.findFactoryByDataAdapterClass(className);
			if (factory == null) {
				IProject project = file.getProject();
				if (project != null) {
					DefaultDataAdapterDescriptor ddad = new DefaultDataAdapterDescriptor();
					Class<?> clazz = jrConfig.getClassLoader().loadClass(className);
					if (clazz != null) {
						InputStream mis = jrConfig.getClassLoader()
								.getResourceAsStream(clazz.getName().replace(".", "/") + ".xml"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						if (mis != null) {
							try {
								Mapping mapping = new Mapping(jrConfig.getClassLoader());
								mapping.loadMapping(new InputSource(mis));

								DataAdapter dataAdapter = (DataAdapter) CastorHelper
										.read(XMLUtils.parseNoValidation(in).getDocumentElement(), mapping);
								if (dataAdapter != null) {
									ddad.setDataAdapter(dataAdapter);
									dad = ddad;
								}
							} finally {
								FileUtils.closeStream(mis);
							}
						}
					}
				} else
					// we should at least log a warning here....
					JaspersoftStudioPlugin.getInstance().getLog()
							.log(new Status(Status.WARNING, JaspersoftStudioPlugin.getUniqueIdentifier(), Status.OK,
									Messages.DataAdapterManager_nodataadapterfound + className, null));
			} else {
				DataAdapterDescriptor dataAdapterDescriptor = factory.createDataAdapter();
				DataAdapter dataAdapter = null;
				try
				{
					dataAdapter = JacksonUtil.getInstance(jrConfig).loadXml(in, DataAdapter.class);
				}
				catch (JacksonRuntimException e)
				{
					// castor fallback; input stream reset is already used prior to this call, so doing the same here
					in.reset();
					dataAdapter = (DataAdapter) JSSCastorUtil.getInstance(jrConfig).read(in);
				}
				dataAdapterDescriptor.setDataAdapter(dataAdapter);
				dad = dataAdapterDescriptor;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dad;
	}

	public IProject getProject() {
		return project;
	}

	@Override
	public String getStorageName() {
		return project.getName();
	}
}
