/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.samples.wizards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import net.sf.jasperreports.eclipse.builder.jdt.JDTUtils;
import net.sf.jasperreports.eclipse.classpath.container.JRDependenciesClasspathContainer;
import net.sf.jasperreports.eclipse.wizard.project.JRProjectPage;
import net.sf.jasperreports.eclipse.wizard.project.JRProjectWizard;
import net.sf.jasperreports.samples.Activator;
import net.sf.jasperreports.samples.messages.Messages;

/**
 * This wizard creates a new Report project that contains all
 * the available contributed samples.
 */
public class SampleNewWizard extends JRProjectWizard {

	/**
	 * Constructor for SampleNewWizard.
	 */
	public SampleNewWizard() {
		super();
		setWindowTitle(Messages.SampleNewWizard_Title);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		init(workbench, selection, new JRProjectPage());
	}

	public void init(IWorkbench workbench, IStructuredSelection selection, JRProjectPage page) {
		step1 = page;
		step1.setTitle(Messages.SampleNewWizard_Title);
		step1.setDescription(Messages.SampleNewWizard_Description);
		step1.setName("Samples");//$NON-NLS-1$
		addPage(step1);
	}

	@Override
	protected void createProject(IProgressMonitor monitor, IProject prj) throws CoreException, FileNotFoundException {
		super.createProject(monitor, prj);
		IJavaProject project = JavaCore.create(prj);
		
		IClasspathEntry[] classpathEntries = project.getRawClasspath();
		IClasspathEntry[] newClasspathEntries = new IClasspathEntry[classpathEntries.length+1];
		System.arraycopy(classpathEntries, 0, newClasspathEntries, 0, classpathEntries.length);
		newClasspathEntries[classpathEntries.length] = JavaCore.newContainerEntry(JRDependenciesClasspathContainer.ID, true);
		project.setRawClasspath(newClasspathEntries, monitor);
		
		Set<File> libraries = Activator.getSamplesManager().getAdditionalLibraries();
		Set<File> sourceFolders = Activator.getSamplesManager().getSourceFolders();
		Set<File> exampleFolders = Activator.getSamplesManager().getExampleFolders();
		
		addSourceFolders(sourceFolders, project, monitor);
		addLibraries(libraries, project, monitor);
		addExampleFolders(exampleFolders, project, monitor);
		
		prj.refreshLocal(IProject.DEPTH_INFINITE, monitor);
		prj.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
		prj.close(monitor);
		prj.open(monitor);

	}

	/*
	 * Adds the folders containing the examples to the project.
	 * Basically it simply copies the folders contents.
	 */
	private static void addExampleFolders(Set<File> exampleFolders, IJavaProject project, IProgressMonitor monitor) throws FileNotFoundException, CoreException {
		if(exampleFolders.isEmpty()){
			return;
		}
		for(File f : exampleFolders) {
			JDTUtils.copyDirectoryToWorkspace(f, project.getProject(), true);
		}
	}

	/*
	 * Adds the libraries to the project.
	 * Basically it adds the different jars into a newly created lib folder inside the project.
	 */
	private static void addLibraries(Set<File> libraries, IJavaProject project, IProgressMonitor monitor) throws FileNotFoundException, CoreException {
		if (libraries.isEmpty()){
			return;
		}
        IFolder libFolder = project.getProject().getFolder(new Path("lib")); //$NON-NLS-1$
        libFolder.create(true, true, null);
		for(File sf : libraries) {
			JDTUtils.copyFileToWorkspace(sf, libFolder);
		}
		IClasspathEntry[] cpentries = project.getRawClasspath();
		IClasspathEntry[] newEntries = new IClasspathEntry[cpentries.length + libraries.size()];
		System.arraycopy(cpentries, 0, newEntries, 0, cpentries.length);
		int i = cpentries.length;
		IResource[] libFiles=libFolder.members();
		for (IResource res : libFiles) {
			newEntries[i] = JavaCore.newLibraryEntry(res.getLocation(), null, null);
			i++;
			if (monitor.isCanceled())
				return;
		}
		project.setRawClasspath(newEntries, monitor);
	}

	/*
	 * Adds the specified folders as source ones to the project.
	 * Basically it copies the folders and modifies the project build-path
	 * in order to have them as source folders.
	 */
	private static void addSourceFolders(Set<File> sourceFolders, IJavaProject project, IProgressMonitor monitor) throws FileNotFoundException, CoreException {
		if (sourceFolders.isEmpty()){
			return;
		}
		for(File sf : sourceFolders) {
			JDTUtils.copyDirectoryToWorkspace(sf, project.getProject(), true);
		}
		IClasspathEntry[] cpentries = project.getRawClasspath();
		IClasspathEntry[] newEntries = new IClasspathEntry[cpentries.length + sourceFolders.size()];
		System.arraycopy(cpentries, 0, newEntries, 0, cpentries.length);
		int i = cpentries.length;
		for (File f : sourceFolders) {
			newEntries[i] = JavaCore.newSourceEntry(project.getProject().getFolder(f.getName()).getFullPath());
			i++;
			if (monitor.isCanceled())
				return;
		}
		project.setRawClasspath(newEntries, monitor);
	}

}
