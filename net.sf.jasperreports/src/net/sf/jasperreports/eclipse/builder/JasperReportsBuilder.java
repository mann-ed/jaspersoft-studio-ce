/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.builder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.JavaCore;

import net.sf.jasperreports.data.AbstractClasspathAwareDataAdapterService;
import net.sf.jasperreports.eclipse.builder.jdt.JRErrorHandler;
import net.sf.jasperreports.eclipse.util.FileExtension;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRSaver;

/*
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JasperCompileManager.java 1229 2006-04-19 13:27:35 +0300 (Wed, 19 Apr 2006) teodord $
 */
public class JasperReportsBuilder extends IncrementalProjectBuilder {

	public static final String BUILDER_ID = "net.sf.jasperreports.builder"; //$NON-NLS-1$

	class JRDeltaVisitor implements IResourceDeltaVisitor {
		private IProgressMonitor monitor;

		public JRDeltaVisitor(IProgressMonitor monitor) {
			super();
			this.monitor = monitor;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse
		 * .core.resources.IResourceDelta)
		 */
		public boolean visit(IResourceDelta delta) throws CoreException {
			if (monitor.isCanceled())
				return false;
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
			case IResourceDelta.REMOVED:
			case IResourceDelta.CHANGED:
				compileJRXML(delta.getResource(), monitor);
				break;
			}
			// return true to continue visiting children.
			return true;
		}
	}

	class JRResourceVisitor implements IResourceVisitor {
		private IProgressMonitor monitor;

		public JRResourceVisitor(IProgressMonitor monitor) {
			super();
			this.monitor = monitor;
		}

		public boolean visit(IResource resource) throws CoreException {
			if (monitor.isCanceled())
				return false;
			compileJRXML(resource, monitor);
			return true;
		}
	}

	class JRCleanResourceVisitor implements IResourceVisitor {
		private IProgressMonitor monitor;

		public JRCleanResourceVisitor(IProgressMonitor monitor) {
			super();
			this.monitor = monitor;
		}

		public boolean visit(IResource resource) throws CoreException {
			if (monitor.isCanceled())
				return false;
			String ext = resource.getFileExtension();
			if (resource instanceof IProject) {
				// since we are cleaning it makes sense to remove
				// all sort of markers from the JR project
				Markers.deleteMarkers(resource);
			}
			if (ext!=null && resource.exists()) {
				if (ext.equalsIgnoreCase(FileExtension.JRXML)) {
					// could be redundant since we have already cleaned the prj ones
					Markers.deleteMarkers(resource);
				}
				else if (resource.isDerived() && ext.equalsIgnoreCase(FileExtension.JASPER)) {
					// be sure to get rid of .jasper files
					resource.delete(false, SubMonitor.convert(monitor));
				}
			}
			return true;
		}
	}

	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		IProject currentProject = getProject();
		if (currentProject == null || !currentProject.isAccessible())
			return;
		monitor.subTask("Cleaning");
		long stime = System.currentTimeMillis();
		getProject().accept(new JRCleanResourceVisitor(monitor));
		long etime = System.currentTimeMillis();
		System.out.println("Cleaned in " + (etime - stime) + " ms");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 * java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {
		outmap.clear();
		IProject currentProject = getProject();
		if (currentProject == null)
			return new IProject[0];
		switch (kind) {
		case FULL_BUILD:
			fullBuild(monitor);
			break;
		case CLEAN_BUILD:
			clean(monitor);
			break;
		default:
			IResourceDelta delta = getDelta(getProject());
			if (delta == null)
				fullBuild(monitor);
			else
				incrementalBuild(delta, monitor);
		}
		return new IProject[0];
	}

	private JasperReportCompiler reportCompiler = new JasperReportCompiler();
	private Map<IProject, IPath> outmap = new HashMap<IProject, IPath>();

	/**
	 * Return the JasperReportErrorHandler that will be used by the compilation
	 * process to notify the error
	 * 
	 * @param arguments
	 *            parameters eventually passed by the compilation process
	 * @return a JasperReportErrorHandler
	 */
	protected JasperReportErrorHandler getErrorHandler(IFile resource) {
		return new JRErrorHandler(resource);
	}

	private JSSReportContext jContext = JSSReportContext.getNewConfig();

	public IFile compileJRXML(IResource resource, IProgressMonitor monitor) throws CoreException {
		// reuse context creation, this will reduce extensions loading as much
		// as possible
		jContext.init(resource);
		return compileJRXML(resource, monitor, jContext);
	}

	public IFile compileJRXML(IResource resource, IProgressMonitor monitor, SimpleJasperReportsContext jContext)
			throws CoreException {
		if (!(resource instanceof IFile && resource.exists() && resource.getFileExtension() != null))
			return null;
		if (resource instanceof IFile && resource.getFileExtension().equals(FileExtension.JASPER))
			return null;
		IProject project = resource.getProject();
		IPath outLocation = outmap.get(project);
		if (outLocation != null && project.hasNature(JavaCore.NATURE_ID))
			outLocation = JavaCore.create(project).getOutputLocation();
		if (outLocation != null && outLocation.isPrefixOf(resource.getFullPath()))
			return null;
		if (resource.getFileExtension().equals(FileExtension.JRXML)) {
			long stime = System.currentTimeMillis();
			ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
			IFile destFile = null;
			try {
				if (jContext == null)
					jContext = JSSReportContext.getDefaultInstance(resource);
				monitor.subTask("Compiling " + resource.getFullPath().toOSString());
				Markers.deleteMarkers(resource);
				IFile file = (IFile) resource;
				ClassLoader cl = (ClassLoader) jContext
						.getValue(AbstractClasspathAwareDataAdapterService.CURRENT_CLASS_LOADER);
				if (cl != null)
					Thread.currentThread().setContextClassLoader(cl);
				reportCompiler.setErrorHandler(getErrorHandler(file));
				destFile = FileExtension.getCompiledFile(file);

				JasperReport jasperReport = null;
				// Checks if a potential linked resource really exists
				if (file.getLocation().toFile().exists())
					jasperReport = reportCompiler.compileReport(jContext, file, monitor);
				String str = jContext.getProperty(JasperReportCompiler.JSS_COMPATIBILITY_COMPILER_VERSION);
				if (Misc.isNullOrEmpty(str) || str.equals("last") || str.equals(JasperDesign.class.getPackage().getImplementationVersion())) {
					if (jasperReport == null) {
						if (destFile.exists())
							destFile.delete(true, false, SubMonitor.convert(monitor));
					} else {
						ByteArrayOutputStream bout = new ByteArrayOutputStream();
						ByteArrayInputStream compiledInput = null;
						try {
							JRSaver.saveObject(jasperReport, bout);
							compiledInput = new ByteArrayInputStream(bout.toByteArray());
							if (destFile.exists()) {
								if (file.isLinked() && !destFile.isLinked()) {
									destFile.delete(true, false, SubMonitor.convert(monitor));
									destFile = createDestFile(monitor, project, file, destFile, compiledInput);
								} else
									destFile.setContents(compiledInput, true, false, SubMonitor.convert(monitor));
							} else
								destFile = createDestFile(monitor, project, file, destFile, compiledInput);
							if (!destFile.isDerived())
								destFile.setDerived(true, SubMonitor.convert(monitor));
						} catch (JRException e) {
							throw new RuntimeException(e);// TODO
						} finally {
							FileUtils.closeStream(bout);
							FileUtils.closeStream(compiledInput);
						}
					}
				}
			} finally {
				Thread.currentThread().setContextClassLoader(oldLoader);
			}
			long etime = System.currentTimeMillis();
			System.out.println(resource.getFullPath().toOSString() + " " + (etime - stime) + " ms");
			return destFile;
		} else if (resource.getFileExtension().equals(FileExtension.JASPER)) {
			return compileJRXML(FileExtension.getSourceFile((IFile) resource), monitor);
		}
		return null;
	}

	protected IFile createDestFile(IProgressMonitor monitor, IProject project, IFile file, IFile destFile,
			ByteArrayInputStream compiledInput) throws CoreException {
		if (file.isLinked()) {
			String fpath = file.getLocation().toFile().getAbsolutePath();
			fpath = FileExtension.getCompiledFileName(fpath);
			File f = new File(fpath);
			try {
				f.createNewFile();
				IPath location = new Path(fpath);
				String fileName = FilenameUtils.removeExtension(file.getName());
				destFile = project.getFile(fileName + "." + location.getFileExtension());
				destFile.createLink(location, IResource.REPLACE, SubMonitor.convert(monitor));
				destFile.setContents(compiledInput, true, false, SubMonitor.convert(monitor));
			} catch (IOException e) {
				e.printStackTrace();
				destFile.create(compiledInput, true, SubMonitor.convert(monitor));
			}
		} else
			destFile.create(compiledInput, true, SubMonitor.convert(monitor));
		return destFile;
	}

	protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
		long stime = System.currentTimeMillis();
		getProject().accept(new JRResourceVisitor(monitor));
		long etime = System.currentTimeMillis();
		System.out.println("Full Build in " + (etime - stime) + " ms");
	}

	protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		delta.accept(new JRDeltaVisitor(monitor));
	}
}
