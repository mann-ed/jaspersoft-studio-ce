/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.custom.adapter.export;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;

import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.ide.actions.BuildUtilities;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.translation.JarFileUtils;
import com.jaspersoft.studio.wizards.JSSWizard;

/**
 * Wizard to export the data adapter plugin as a jar. It uses a custom store for
 * the setting and a custom page
 * 
 * @author Orlandin Marco
 *
 */
@SuppressWarnings("restriction")
public class ExportAdapterWizard extends JSSWizard implements IExportWizard {

	protected IStructuredSelection fSelection;

	/**
	 * Page where the destination of the jar can be chosen
	 */
	private ExportAdapterWizardPage fPage;

	/**
	 * The settings store id
	 */
	private static final String STORE_SECTION = "AdapterExportWizard"; //$NON-NLS-1$

	public ExportAdapterWizard(IWorkbench workbench, IStructuredSelection selection) {
		super();
		init(workbench, selection);
	}

	/**
	 * Create a custom page instead of the standard one
	 */
	protected ExportAdapterWizardPage createPage1() {
		fPage = new ExportAdapterWizardPage(getSelection());
		return fPage;
	}

	@Override
	public void addPages() {
		fPage = createPage1();
		addPage(fPage);
	}

	/**
	 * Return the settings store id
	 */
	protected String getSettingsSectionName() {
		return STORE_SECTION;
	}

	public IStructuredSelection getSelection() {
		return fSelection;
	}

	public IDialogSettings getSettingsSection(IDialogSettings master) {
		String name = getSettingsSectionName();
		IDialogSettings settings = master.getSection(name);
		if (settings == null)
			settings = master.addNewSection(name);
		return settings;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		fSelection = selection;
	}

	/**
	 * Generate the qualifier for a fragment using the eclipse style. So a
	 * concatenation of the actuals year,month,day,hour and minute
	 * 
	 * @return an eclipse style qualifier
	 */
	public static String generateQualifier() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm"); //$NON-NLS-1$
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * Return the composed message of all the errors to show it on the final
	 * dialog
	 * 
	 * @param singleErrors a not null list of errors project by project
	 * @return a message to show to the user
	 */
	private String getErrorMessage(List<String> singleErrors) {
		String message = Messages.ExportAdapterWizard_projectErrors;
		for (String error : singleErrors) {
			message += error + "\n"; //$NON-NLS-1$
		}
		return message;
	}

	/**
	 * Return the composed message of all the successes to show it on the final
	 * dialog
	 * 
	 * @param singleErrors a not null list of successful operation project by
	 * project
	 * @return a message to show to the user
	 */
	private String getSuccessMessage(List<String> singleSuccesses) {
		String message = Messages.ExportAdapterWizard_projectSucceses;
		for (String success : singleSuccesses) {
			message += success + "\n"; //$NON-NLS-1$
		}
		return message;
	}
	
	@Override
	public boolean canFinish() {
		return fPage.getSelectedItems().size() > 0 && fPage.getDestination().trim().length() > 0;
	}

	/**
	 * Build a project and return if it has error or not
	 * 
	 * @param project project to compile
	 * @return true if the project has not compilation error, false otherwise
	 */
	private boolean buildProject(IProject project) {
		// BuildAction buildAction = new BuildAction(UIUtils.getShellProvider(),
		// IncrementalProjectBuilder.FULL_BUILD);
		// buildAction.selectionChanged(new StructuredSelection(project));
		// buildAction.run();
		// The following is essentially the body of a BuildAction but executed
		// outsie a separated job
		try {
			ArrayList<IProject> projects = new ArrayList<IProject>();
			projects.add(project);
			BuildUtilities.saveEditors(projects);
			List<IBuildConfiguration> buildConfiguration = new ArrayList<IBuildConfiguration>();
			buildConfiguration.add(project.getActiveBuildConfig());
			IBuildConfiguration[] configs = buildConfiguration
					.toArray(new IBuildConfiguration[buildConfiguration.size()]);
			ResourcesPlugin.getWorkspace().build(configs, IncrementalProjectBuilder.FULL_BUILD, true,
					new NullProgressMonitor());
		} catch (CoreException e1) {
			e1.printStackTrace();
			return false;
		}
		try {
			IMarker[] markers = project.findMarkers(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER, true,
					IResource.DEPTH_INFINITE);
			for (IMarker marker : markers) {
				int severity = marker.getAttribute(IMarker.SEVERITY, Integer.MAX_VALUE);
				if (severity == IMarker.SEVERITY_ERROR) {
					return false;
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Generate the jar for the selected projects.
	 */
	@Override
	public boolean performFinish() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		// get location of workspace (java.io.File)
		File workspaceDirectory = workspace.getRoot().getLocation().toFile();
		List<String> successfullyExported = new ArrayList<String>();
		List<String> notExported = new ArrayList<String>();
		for (IProject project : fPage.getSelectedItems()) {
			try {
				File sourceFile = new File(workspaceDirectory, project.getFullPath().toOSString());
				Properties prop = new Properties();
				prop.load(project.getFolder("META-INF").getFile("MANIFEST.MF").getContents());
				// Load the manifest file
				File manifestFile = new File(sourceFile, "META-INF"); //$NON-NLS-1$
				manifestFile = new File(manifestFile, "MANIFEST.MF"); //$NON-NLS-1$
				String manifestContent = JarFileUtils.deserializeString(manifestFile);
	
				String version = "_" + prop.getProperty("Bundle-Version").replace("qualifier", generateQualifier()); //$NON-NLS-1$
				String fileName = prop.getProperty("Bundle-SymbolicName").split(";")[0] + version + ".jar"; //$NON-NLS-1$
				File destinationFile = new File(fPage.getDestination(), fileName);
				if (destinationFile.exists()) {
					destinationFile.delete();
				}
				project.build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
				// project.build(IncrementalProjectBuilder.FULL_BUILD, new
				// NullProgressMonitor());
				boolean compilationSuccess = buildProject(project);
				if (compilationSuccess) {
					JarFileUtils.createPluginJar(fPage.getDestination(), sourceFile, fileName, manifestContent);
					successfullyExported.add(MessageFormat.format(Messages.ExportAdapterWizard_projectSuccess,
							new Object[] { project.getName(), new File(fPage.getDestination(), fileName).toString() }));
				} else {
					notExported.add(MessageFormat.format(Messages.ExportAdapterWizard_projectError,
							new Object[] { project.getName() }));
				}
			} catch (Exception e) {
				notExported.add(MessageFormat.format(Messages.ExportAdapterWizard_projectError,
						new Object[] { project.getName() }));
				e.printStackTrace();
			}
		}
		if (successfullyExported.isEmpty()) {
			MessageDialog.openError(UIUtils.getShell(), Messages.ExportAdapterWizard_allError,
					getErrorMessage(notExported));
		} else if (notExported.size() == 0) {
			MessageDialog.openInformation(UIUtils.getShell(), Messages.ExportAdapterWizard_allSuccess,
					getSuccessMessage(successfullyExported));
		} else {
			MessageDialog.openWarning(UIUtils.getShell(), Messages.ExportAdapterWizard_someErrors,
					getSuccessMessage(successfullyExported) + "\n" + getErrorMessage(notExported)); // $NON-NLS-2$
		}
		return true;
	}

}
