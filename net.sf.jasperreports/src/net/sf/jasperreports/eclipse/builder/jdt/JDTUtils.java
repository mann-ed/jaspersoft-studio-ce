/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.builder.jdt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.collections4.map.ReferenceMap;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.ui.actions.OrganizeImportsAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertyShowInContext;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;
import net.sf.jasperreports.eclipse.builder.JasperReportsNature;
import net.sf.jasperreports.eclipse.builder.Markers;
import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.extensions.DefaultExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsEnvironment;

/**
 * Utility class with JDT related methods.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public final class JDTUtils {
	
	public static final IWorkspaceRoot WS_ROOT = ResourcesPlugin.getWorkspace().getRoot();

	private JDTUtils() {
		// prevent instantiation...
	}
	
	/**
	 * Use the reflection to remove the ExtensionRegistry for cached by JasperReport for a specific report
	 * 
	 * FIXME this use the reflection to mess up with the JR data structure and should be used only for test 
	 * purpose, at least until a proper api is provided
	 * 
	 * @param jConfigClassLoader the classpath of the JasperReportConfiguration of the report where
	 * the registry should be cleared
	 */
	public static void clearJRRegistry(ClassLoader jConfigClassLoader){
		try {
			Field privateStringField =  DefaultExtensionsRegistry.class.getDeclaredField("registrySetCache");  //$NON-NLS-1$
			privateStringField.setAccessible(true);
			ReferenceMap privateObject = (ReferenceMap)privateStringField.get( ExtensionsEnvironment.getExtensionsRegistry());
			privateObject.remove(jConfigClassLoader);
		} catch (Exception e) {
			JasperReportsPlugin.getDefault().logError(Messages.JDTUtils_ErrorRefreshingExtensions, e);
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the current {@link IProject} instance based on the currently
	 * opened editor.
	 */
	public static IProject getCurrentProjectForOpenEditor() {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (activeWorkbenchWindow != null
				&& activeWorkbenchWindow.getActivePage() != null) {
			IEditorPart p = activeWorkbenchWindow.getActivePage()
					.getActiveEditor();
			if (p == null) {
				IWorkbenchPart activePart = activeWorkbenchWindow
						.getActivePage().getActivePart();
				if (activePart instanceof PropertySheet) {
					ShowInContext showInContext = ((PropertySheet) activePart)
							.getShowInContext();
					if (showInContext instanceof PropertyShowInContext) {
						IWorkbenchPart part = ((PropertyShowInContext) showInContext)
								.getPart();
						if (part instanceof IEditorPart) {
							p = (IEditorPart) part;
						} else {
							JasperReportsPlugin
									.getDefault()
									.logWarning(
											Messages.JDTUtils_NoProjectFromCurrentEditorErr);
							return null;
						}
					}
				}
			}
			if (p != null) {
				IEditorInput editorInput = p.getEditorInput();
				IFile file = getFile(editorInput);
				if (file != null) {
					return file.getProject();
				}
			}
		}
		return null;
	}

	/**
	 * @return the IFile corresponding to the given input, or null if none
	 */
	public static IFile getFile(IEditorInput editorInput) {
		IFile file = null;

		if (editorInput instanceof IFileEditorInput) {
			IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
			file = fileEditorInput.getFile();
		} else if (editorInput instanceof IPathEditorInput) {
			IPathEditorInput pathInput = (IPathEditorInput) editorInput;
			IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
			if (wsRoot.getLocation().isPrefixOf(pathInput.getPath())) {
				file = ResourcesPlugin.getWorkspace().getRoot()
						.getFile(pathInput.getPath());
			} else {
				// Can't get an IFile for an arbitrary file on the file system;
				// return null
			}
		} else if (editorInput instanceof IStorageEditorInput) {
			file = null; // Can't get an IFile for an arbitrary
							// IStorageEditorInput
		} else if (editorInput instanceof IURIEditorInput) {
			IURIEditorInput uriEditorInput = (IURIEditorInput) editorInput;
			IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
			URI uri = uriEditorInput.getURI();
			String path = uri.getPath();
			// Bug 526: uri.getHost() can be null for a local file URL
			if (uri.getScheme().equals("file") //$NON-NLS-1$
					&& (uri.getHost() == null || uri.getHost().equals(
							"localhost")) //$NON-NLS-1$
					&& !path.startsWith(wsRoot.getLocation().toOSString())) {
				file = wsRoot.getFile(new Path(path));
			}
		}
		return file;
	}

	/**
	 * Formats the specified compilation unit.
	 * 
	 * @param unit
	 *            the compilation unit to format
	 * @param monitor
	 *            the monitor for the operation
	 * @throws JavaModelException
	 */
	public static void formatUnitSourceCode(ICompilationUnit unit,
			IProgressMonitor monitor) throws JavaModelException {
		CodeFormatter formatter = ToolFactory.createCodeFormatter(null);
		ISourceRange range = unit.getSourceRange();
		TextEdit formatEdit = formatter.format(
				CodeFormatter.K_COMPILATION_UNIT, unit.getSource(),
				range.getOffset(), range.getLength(), 0, null);
		if (formatEdit != null && formatEdit.hasChildren()) {
			unit.applyTextEdit(formatEdit, monitor);
		} else {
			monitor.done();
		}
	}

	/**
	 * Checks if the object is of the specified class type or has an adapter for
	 * it.
	 * 
	 * @param adaptable
	 *            the object to be adapted
	 * @param clazz
	 *            the class type
	 * @return <code>true</code> if it can be adapted, <code>false</code>
	 *         otherwise
	 */
	public static boolean isOrCanAdaptTo(IAdaptable adaptable, Class<?> clazz) {
		Assert.isNotNull(clazz);
		if (clazz.isInstance(adaptable)) {
			return true;
		} else {
			return (adaptable != null && adaptable.getAdapter(clazz) != null);
		}
	}

	/**
	 * Gets the adapted object for the specified class type.
	 * 
	 * @param adaptable
	 *            the object to be adapted
	 * @param clazz
	 *            the class type
	 * @return the adapted object, <code>null</code> if no corresponding adapter
	 *         exists
	 */
	public static <T> T getAdaptedObject(IAdaptable adaptable, Class<T> clazz) {
		Assert.isNotNull(clazz);
		if (clazz.isInstance(adaptable)) {
			return (T) adaptable;
		} else {
			T adaptedObj = null;
			if (adaptable != null) {
				adaptedObj = (T) adaptable.getAdapter(clazz);
			}
			return adaptedObj;
		}
	}

	/**
	 * Run the organize import action on the provided compilation unit
	 * 
	 * @param unit
	 *            the unit where the import must be organized
	 */
	public static void organizeImport(ICompilationUnit unit) {
		OrganizeImportsAction org = new OrganizeImportsAction(PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart().getSite());
		IStructuredSelection selection = new StructuredSelection(unit);
		org.run(selection);
	}

	/**
	 * Format the text and organize the imports of the provided unit
	 * 
	 * @param units
	 *            not null array of compilation unit to format and organize
	 */
	public static void formatAndOrganizeImports(ICompilationUnit[] units) {
		NullProgressMonitor monitor = new NullProgressMonitor();
		for (ICompilationUnit unit : units) {
			organizeImport(unit);
			try {
				formatUnitSourceCode(unit, monitor);
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Format the text and organize the imports of the provided file, by
	 * creating from any of it a compilation unit
	 * 
	 * @param units
	 *            not null array of array to format and organize
	 */
	public static void formatAndOrganizeImports(IFile[] units) {
		NullProgressMonitor monitor = new NullProgressMonitor();
		for (IFile unitFile : units) {
			ICompilationUnit unit = JavaCore
					.createCompilationUnitFrom(unitFile);
			organizeImport(unit);
			try {
				formatUnitSourceCode(unit, monitor);
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Verifies if the specified path subtend to a virtual resource.
	 * 	
	 * @param path the location to be checked
	 * @return <code>true</code> if the path identifies a virtual resource, <code>false</code> otherwise
	 * 
	 * @see IResource#isVirtual()
	 */
	public static boolean isVirtualResource(IPath path) {
		IResource res = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
		return (res!=null && res.isVirtual()) ? true : false;
	}

	private static boolean P_LINKED_RESOURCES_STATUS = false;
	private static boolean LINKED_RESOURCES_STATUS_GUARD = false;
	
	/**
	 * Stores the current status of the "Enable Linked Resources" flag and forces it to disabled.
	 * This solution can be useful when we temporary want to inhibit linked resources usage,
	 * for example in some resource creation wizards (reports, template styles, data adapters,etc.).
	 * <p>
	 * 
	 * Unfortunately there is no way to directly read the internal status of the checkbox for the
	 * {@link WizardNewFileCreationPage}. Since the show/hide of the advanced composite showing the
	 * linking related controls is managed by the {@link ResourcesPlugin#PREF_DISABLE_LINKING} preference
	 * we directly act on this.
	 * 
	 * @see ResourcesPlugin#PREF_DISABLE_LINKING
	 */
	public static synchronized void deactivateLinkedResourcesSupport() {
		if(LINKED_RESOURCES_STATUS_GUARD) {
			//JasperReportsPlugin.getDefault().logWarning(Messages.JDTUtils_LinkedResourcesDeactivationErr);
			return;
		}
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(ResourcesPlugin.PI_RESOURCES);
		P_LINKED_RESOURCES_STATUS = preferences.getBoolean(
				ResourcesPlugin.PREF_DISABLE_LINKING, IPreferenceStore.BOOLEAN_DEFAULT_DEFAULT);
		preferences.putBoolean(ResourcesPlugin.PREF_DISABLE_LINKING,true);
		LINKED_RESOURCES_STATUS_GUARD = true;
	}
	
	/**
	 * Stores the current status of the "Enable Linked Resources" flag and forces it to enabled.
	 * 
	 * @see #deactivateLinkedResourcesSupport()
	 * @see #restoreLinkedResourcesSupport()
	 */
	public static synchronized void activateLinkedResourcesSupport() {
		if(LINKED_RESOURCES_STATUS_GUARD) {
			return;
		}
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(ResourcesPlugin.PI_RESOURCES);
		P_LINKED_RESOURCES_STATUS = preferences.getBoolean(
				ResourcesPlugin.PREF_DISABLE_LINKING, IPreferenceStore.BOOLEAN_DEFAULT_DEFAULT);
		preferences.putBoolean(ResourcesPlugin.PREF_DISABLE_LINKING,false);
		LINKED_RESOURCES_STATUS_GUARD = true;
	}
	
	/**
	 * Restores the original status of the "Enable Linked Resources" flag.
	 * 
	 *  @see ResourcesPlugin#PREF_DISABLE_LINKING
	 */
	public static synchronized void restoreLinkedResourcesSupport() {
		if(!LINKED_RESOURCES_STATUS_GUARD) {
			//JasperReportsPlugin.getDefault().logWarning(Messages.JDTUtils_LinkedResourcesActivationErr);
			return;
		}
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(ResourcesPlugin.PI_RESOURCES);
		preferences.putBoolean(ResourcesPlugin.PREF_DISABLE_LINKING,P_LINKED_RESOURCES_STATUS);
		LINKED_RESOURCES_STATUS_GUARD = false;
	}
	
	/**
	 * Read the status of the "Enable Linked Resources" flag.
	 * 
	 * @return true if the linked resources are allowed, false otherwise
	 * @see ResourcesPlugin#PREF_DISABLE_LINKING
	 */
	public static synchronized boolean isAllowdLinkedResourcesSupport() {
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(ResourcesPlugin.PI_RESOURCES);
		return !preferences.getBoolean(ResourcesPlugin.PREF_DISABLE_LINKING, false);
	}
	
	/**
	 * Set the status of the "Enable Linked Resources" flag.
	 * 
	 * param value true if the linked resources are allowed, false otherwise
	 * @see ResourcesPlugin#PREF_DISABLE_LINKING
	 */
	public static synchronized void setLinkedResourcesSupport(boolean value) {
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(ResourcesPlugin.PI_RESOURCES);
		preferences.putBoolean(ResourcesPlugin.PREF_DISABLE_LINKING,!value);
	}
	
	/**
	 * De-activates or restores the support for the "Enable Linked Resources" flag.
	 * 
	 * @param disableSupport flag that specifies if support of linked resources should be de-activated
	 * 		or restored to its original value
	 * 
	 * @see #deactivateLinkedResourcesSupport()
	 * @see #restoreLinkedResourcesSupport()
	 * 
	 */
	public static synchronized void deactivateLinkedResourcesSupport(boolean disableSupport) {
		if(disableSupport) {
			deactivateLinkedResourcesSupport();
		}
		else {
			restoreLinkedResourcesSupport();
		}
	}
	
	/**
	 * Sets a bunch of properties related to compiler settings to a specific Java version.
	 * Modifications are applied to the workspace settings.
	 * 
	 * 
	 * @param javaVersion the java version that should be set
	 */
	public static synchronized void forceWorkspaceCompilerSettings(String javaVersion) {
		Hashtable currentOptions = JavaCore.getOptions();
		String compilerCompliance = (String) currentOptions.get(JavaCore.COMPILER_COMPLIANCE);
		String compilerCodegenTargetPlatform = (String) currentOptions.get(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM);
		String compilerSource = (String) currentOptions.get(JavaCore.COMPILER_SOURCE);
		
		if(compilerCompliance!=null && JavaCore.VERSION_1_6.compareTo(compilerCompliance)>0) {
			currentOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
		}
		if(compilerSource!=null && JavaCore.VERSION_1_6.compareTo(compilerSource)>0) {
			currentOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
		}
		if(compilerCodegenTargetPlatform!=null && JavaCore.VERSION_1_6.compareTo(compilerCodegenTargetPlatform)>0){
			currentOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
		}
	}
	
	/**
	 * Sets a bunch of properties related to compiler settings to a specific Java version.
	 * Modifications are applied to the specified project settings.
	 * 
	 * @param project the JasperReports project
	 * @param javaVersion the java version that should be set
	 */	
	public static synchronized void forceJRProjectCompilerSettings(IJavaProject project, String javaVersion) {
		Map projectOptions = project.getOptions(false);
		if(projectOptions!=null && !projectOptions.isEmpty()){
			String compilerCompliance = (String) projectOptions.get(JavaCore.COMPILER_COMPLIANCE);
			String compilerCodegenTargetPlatform = (String) projectOptions.get(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM);
			String compilerSource = (String) projectOptions.get(JavaCore.COMPILER_SOURCE);
			
			if(compilerCompliance!=null && JavaCore.VERSION_1_6.compareTo(compilerCompliance)>0) {
				projectOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
			}
			if(compilerSource!=null && JavaCore.VERSION_1_6.compareTo(compilerSource)>0) {
				projectOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);
			}
			if(compilerCodegenTargetPlatform!=null && JavaCore.VERSION_1_6.compareTo(compilerCodegenTargetPlatform)>0){
				projectOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
			}
		}
	}
	
	/**
	 * Copies files/directories from a source to a container inside the workspace (i.e project, folder).
	 * 
	 * @param source the source folder
	 * @param destination the target container inside workspace
	 * @param flag to specify if a folder with the same source name has to be created in the target container
	 * @throws CoreException 
	 * @throws FileNotFoundException 
	 */
	public static void copyDirectoryToWorkspace (File source, IContainer destination, boolean createFolder) throws CoreException, FileNotFoundException {
		if(source==null || !source.isDirectory()){
			return;
		}
		if(createFolder){
            IFolder newFolder = destination.getFolder(new Path(source.getName()));
            newFolder.create(true, true, null);
            destination = newFolder;
		}
	    for (File f: source.listFiles()) {
	        if (f.isDirectory()) {
	            IFolder newFolder = destination.getFolder(new Path(f.getName()));
	            newFolder.create(true, true, null);
	            copyDirectoryToWorkspace(f, newFolder, false);
	        } else {
	            IFile newFile = destination.getFile(new Path(f.getName()));
	            newFile.create(new FileInputStream(f), true, null);
	        }
	    }
	}
	
	/**
	 * Copies the specified file to a container inside the workspace (i.e project, folder).
	 * 
	 * @param source the source file
	 * @param destination the target container inside workspace
	 * @throws FileNotFoundException
	 * @throws CoreException
	 */
	public static void copyFileToWorkspace(File source, IContainer destination) throws FileNotFoundException, CoreException {
		if(source==null || source.isDirectory()) {
			return;
		}
		IFile newFile = destination.getFile(new Path(source.getName()));
		newFile.create(new FileInputStream(source), true, null);
	}
	
	/**
	 * Delete all the JR error markers for the JasperReports projects in the workspace. 
	 */
	public static void deleteAllJRProjectMarkers() {
		for(IProject prj : WS_ROOT.getProjects()) {
			try {
				if(prj.hasNature(JasperReportsNature.NATURE_ID)) {
					prj.deleteMarkers(Markers.MARKER_TYPE, true, IResource.DEPTH_INFINITE);
				}
			} catch (CoreException e) {
				JasperReportsPlugin.getDefault().logError(
						NLS.bind(Messages.JDTUtils_ProblemDeletingErrorMarkers,prj.getName()), e);
			}
		}
	}
}
