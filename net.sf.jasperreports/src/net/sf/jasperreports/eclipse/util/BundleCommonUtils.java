/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;
import net.sf.jasperreports.eclipse.messages.Messages;

/**
 * Utility class that provides methods to work with plug-ins/bundles.
 * <p>
 * 
 * Among the facilities there are methods to deal with logging and file location
 * resolving.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * @see ResourceManager
 * @see SWTResourceManager
 * 
 */
public final class BundleCommonUtils {

	public final static String REFERENCE_SCHEME = "reference:";
	public final static String REFERENCE_FILE_SCHEMA = "reference:file:";
	private static Map<String, File> JARS_DESTINATION_FOLDERS_MAP = new HashMap<String, File>();

	private BundleCommonUtils() {
		// Do nothing... prevent instantiation
	}

	/**
	 * Get the {@link Bundle} instance referring to the specified bundle ID.
	 * 
	 * @param bundleID
	 *            the bundle ID
	 * @return the bundle instance if a valid one exists, <code>null</code>
	 *         otherwise
	 */
	public static Bundle getBundle(String bundleID) {
		Bundle bundle = Platform.getBundle(bundleID);
		if (bundle == null) {
			return null;
		}
		return bundle;
	}

	// ------------------------------------------- //
	// Files, Images and Locations utility methods
	// ------------------------------------------- //

	/**
	 * Get the full path name for a resource located inside the specified bundle.
	 * 
	 * @param bundleID
	 *            the bundle ID
	 * @param path
	 *            the path of the internal resource
	 * @return the string corresponding to the full path
	 * @throws IOException
	 *             if a problem occurs during conversion
	 */
	public static String getFileLocation(String bundleID, String path) throws IOException {
		Assert.isNotNull(bundleID);
		Assert.isNotNull(path);
		Bundle bundle = getBundle(bundleID);
		if (bundle != null) {
			return FileLocator.toFileURL(bundle.getEntry(path)).getPath();
		} else {
			return null;
		}
	}

	/**
	 * Returns an image descriptor for the image file at the given bundle relative
	 * path.
	 * 
	 * @param bundleID
	 *            the bundle ID
	 * @param path
	 *            the bundle path to look for
	 * @return the image descriptor if any, <code>null</code> otherwise
	 */
	public static ImageDescriptor getImageDescriptor(String bundleID, String path) {
		Assert.isNotNull(bundleID);
		Assert.isNotNull(path);
		return AbstractUIPlugin.imageDescriptorFromPlugin(bundleID, path);
	}

	/**
	 * Returns an SWT image related to the given bundle relative path.
	 * 
	 * @param bundleID
	 *            the bundle ID
	 * @param path
	 *            the bundle path of the image
	 * @return the SWT image instance if any, <code>null</code> otherwise
	 */
	public static Image getImage(String bundleID, String path) {
		Assert.isNotNull(bundleID);
		Assert.isNotNull(path);
		return ResourceManager.getPluginImage(bundleID, path);
	}

	/**
	 * Returns the SWT image related to the specified image descriptor.
	 * 
	 * @param descriptor
	 *            the image descriptor
	 * @return the SWT image
	 */
	public static Image getImage(ImageDescriptor descriptor) {
		return ResourceManager.getImage(descriptor);
	}

	/**
	 * Returns an URL representing the given bundle's installation directory.
	 * 
	 * @param bundleID
	 *            the bundle ID
	 * 
	 * @return the given bundle's installation directory
	 */
	public static URL getInstallUrl(String bundleID) {
		Bundle bundle = getBundle(bundleID);
		if (bundle == null) {
			return null;
		}
		return bundle.getEntry("/"); //$NON-NLS-1$
	}

	// ----------------------- //
	// Logging utility methods
	// ----------------------- //

	/**
	 * Log a trace message for a specific plugin
	 * 
	 * @param bundleID
	 *            the id of the plugin
	 * @param message
	 *            the human-readable message
	 */
	public static void logTrace(String bundleID, String message) {
		Bundle bundle = getBundle(bundleID);
		if (bundle == null) {
			System.err.println(NLS.bind(Messages.BundleCommonUtils_LoggingToStdErr, bundleID));
			System.err.println(Messages.BundleCommonUtils_MessagePrefix + message);
			return;
		}
		Platform.getLog(bundle).log(new Status(IStatus.INFO, bundleID, message));
	}

	/**
	 * Log a trace message for a specific plugin
	 * 
	 * @param bundleID
	 *            the id of the plugin
	 * @param message
	 *            the human-readable message
	 */
	public static void logTrace(String bundleID, Throwable e) {
		Bundle bundle = getBundle(bundleID);
		if (bundle == null) {
			System.err.println(NLS.bind(Messages.BundleCommonUtils_LoggingToStdErr, bundleID));
			System.err.println(Messages.BundleCommonUtils_MessagePrefix + e.getMessage());
			e.printStackTrace();
			return;
		}
		Platform.getLog(bundle).log(new Status(IStatus.INFO, bundleID, e.getMessage(), e));
	}

	/**
	 * Logs an error message and an optional exception for the specified bundle.
	 * 
	 * @param bundleID
	 *            the bundle ID
	 * @param message
	 *            a human-readable message
	 * @param exception
	 *            a low-level exception, or <code>null</code> if not applicable
	 */
	public static void logError(String bundleID, String message, Throwable exception) {
		Bundle bundle = getBundle(bundleID);
		if (bundle == null) {
			System.err.println(NLS.bind(Messages.BundleCommonUtils_LoggingToStdErr, bundleID));
			System.err.println(Messages.BundleCommonUtils_MessagePrefix + message);
			exception.printStackTrace();
			return;
		}
		Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundleID, message, exception));
	}

	/**
	 * Logs a warning message and an optional exception for the specified bundle.
	 * 
	 * @param bundleID
	 *            the bundle ID
	 * @param message
	 *            a human-readable message
	 * @param exception
	 *            a low-level exception, or <code>null</code> if not applicable
	 */
	public static void logWarning(String bundleID, String message, Throwable exception) {
		Bundle bundle = getBundle(bundleID);
		if (bundle == null) {
			System.err.println(NLS.bind(Messages.BundleCommonUtils_LoggingToStdErr, bundleID));
			System.err.println(Messages.BundleCommonUtils_MessagePrefix + message);
			exception.printStackTrace();
			return;
		}
		Platform.getLog(bundle).log(new Status(IStatus.WARNING, bundleID, message, exception));
	}

	/**
	 * Logs an information message for the specified bundle.
	 * 
	 * @param bundleID
	 *            the bundle ID
	 * @param message
	 *            a human-readable message
	 */
	public static void logInfo(String bundleID, String message) {
		Bundle bundle = getBundle(bundleID);
		if (bundle == null) {
			System.out.println(NLS.bind(Messages.BundleCommonUtils_LoggingToStdOut, bundleID));
			System.out.println(Messages.BundleCommonUtils_MessagePrefix + message);
			return;
		}
		Platform.getLog(bundle).log(new Status(IStatus.INFO, bundleID, message));
	}

	/**
	 * Logs a generic status for the specified bundle.
	 * 
	 * @param bundleID
	 *            the bundle ID
	 * @param status
	 *            the status object to be logged
	 */
	public static void logStatus(String bundleID, IStatus status) {
		Bundle bundle = getBundle(bundleID);
		if (bundle == null) {
			return;
		}
		Platform.getLog(bundle).log(status);
	}

	/**
	 * @return the absolute path of the workspace location
	 */
	public static String getWorkspaceLocation() {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		File wsDirLocation = ws.getRoot().getLocation().toFile();
		return wsDirLocation.getAbsolutePath();
	}

	/**
	 * Prints out as string the type of the specified {@link BundleEvent} instance.
	 * 
	 * @param event
	 *            the bundle event
	 * @return the event type as String
	 */
	public static String eventTypeToString(BundleEvent event) {
		switch (event.getType()) {
		case BundleEvent.INSTALLED:
			return "Installed";
		case BundleEvent.RESOLVED:
			return "Resolved";
		case BundleEvent.LAZY_ACTIVATION:
			return "Lazy Activation";
		case BundleEvent.STARTING:
			return "Starting";
		case BundleEvent.STARTED:
			return "Started";
		case BundleEvent.STOPPING:
			return "Stopping";
		case BundleEvent.STOPPED:
			return "Stopped";
		case BundleEvent.UPDATED:
			return "Updated";
		case BundleEvent.UNRESOLVED:
			return "Unresolved";
		case BundleEvent.UNINSTALLED:
			return "Uninstalled";
		default:
			return "<UNDEFINED>";
		}
	}

	/**
	 * Translates the reference:file:// style URL to the underlying file instance
	 * 
	 * @param bundle
	 *            the bundle for which we are looking the location
	 * @return the absolute path of the bundle, <code>null</code> otherwise
	 */
	public static String getBundleAbsoluteLocation(Bundle bundle) {
		Assert.isNotNull(bundle);
		try {
			File bundleFile = FileLocator.getBundleFile(bundle);
			if (bundleFile != null && bundleFile.exists()) {
				return bundleFile.getAbsolutePath();
			}
		} catch (IOException e) {
			JasperReportsPlugin.getDefault().logError(
					NLS.bind(Messages.JRDependenciesClasspathContainer_AddLocationError, bundle.getSymbolicName()),
					null);
		}
		return null;
	}

	/*
	 * Gets the temporary destination folder used to extract the JAR libraries of
	 * the bundles.
	 */
	private static File getTempDirectoryForInnerJars(Bundle bundle) {
		String bundleName = bundle.getSymbolicName();
		File extractedJarsLocation = JARS_DESTINATION_FOLDERS_MAP.get(bundleName);
		if (extractedJarsLocation == null) {
			IPath stateLocation = Platform.getStateLocation(bundle);
			File configFolder = stateLocation.toFile();
			extractedJarsLocation = new File(configFolder, "extractedJars");
			if (extractedJarsLocation.exists()) {
				try {
					FileUtils.deleteDirectory(extractedJarsLocation);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			extractedJarsLocation.mkdirs();
			JARS_DESTINATION_FOLDERS_MAP.put(bundleName, extractedJarsLocation);
		}
		return extractedJarsLocation;
	}

	/**
	 * Extracts all possible jar libraries that builds the bundle classpath.
	 * 
	 * @param bundle
	 *            the bundle to check
	 * @return a list of target file references to the JAR libraries
	 */
	public static List<File> extractAllJarsFromBundle(Bundle bundle) {
		List<File> results = new ArrayList<File>();
		List<String> classpathLibs = getBundleClasspathJars(bundle);
		File jarsDestinationDir = getTempDirectoryForInnerJars(bundle);
		if (jarsDestinationDir != null) {
			String tempDirPath = jarsDestinationDir.getAbsolutePath();
			for (String lib : classpathLibs) {
				File extractedJar = extractJarFromBundle(bundle, lib, tempDirPath + "/" + lib);
				if (extractedJar != null) {
					results.add(extractedJar);
				}
			}
		}
		return results;
	}

	/**
	 * Extracts the specified jar library from the bundle, saving it to the target
	 * location.
	 * 
	 * @param bundle
	 *            the bundle to check
	 * @param libname
	 *            the jar library identifier
	 * @param targeFileLocation
	 *            the jar destination
	 * @return the target file reference to the JAR library, <code>null</code> if
	 *         the something goes wrong with the library extraction
	 */
	public static File extractJarFromBundle(Bundle bundle, String libname, String targeFileLocation) {
		File extractedJar = null;
		String bundleLocation = getBundleAbsoluteLocation(bundle);
		if (bundleLocation != null) {
			JarFile bundleJar = null;
			InputStream in = null;
			FileOutputStream fos = null;
			try {
				File bundleFile = new File(bundleLocation);
				if (!bundleFile.isDirectory()) {
					// bundle is JAR
					bundleJar = new JarFile(bundleFile);
					in = null;
					ZipEntry libEntry = bundleJar.getEntry(libname);
					if (libEntry == null || libEntry.isDirectory()) {
						return null;
					}
					in = bundleJar.getInputStream(libEntry);
				} else {
					// bundle is DIRECTORY
					in = new FileInputStream(bundleFile.getAbsolutePath() + "/" + libname);
				}
				extractedJar = new File(targeFileLocation);
				extractedJar.getParentFile().mkdirs();
				fos = new FileOutputStream(extractedJar);
				IOUtils.copy(in, fos);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bundleJar != null) {
					try {
						bundleJar.close();
					} catch (IOException e) {
					}
				}
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(fos);
			}
		}
		return extractedJar;
	}

	/**
	 * Returns a list of identifiers for the JAR libraries that are packaged inside
	 * the bundle. The "Bundle-ClassPath" header of the plugin MANIFEST.MF is
	 * checked.
	 * 
	 * @param bundle
	 *            the bundle to check
	 * @return a list of jar classpath entries
	 */
	public static List<String> getBundleClasspathJars(Bundle bundle) {
		List<String> entries = new ArrayList<String>();
		String bundleClasspathStr = bundle.getHeaders().get(Constants.BUNDLE_CLASSPATH);
		try {
			ManifestElement[] elements = ManifestElement.parseHeader(Constants.BUNDLE_CLASSPATH, bundleClasspathStr);
			if (elements != null) {
				for (ManifestElement el : elements) {
					String entry = el.getValue();
					if (entry != null && entry.endsWith(".jar")) {
						entries.add(entry);
					}
				}
			}

		} catch (BundleException e) {

		}
		return entries;
	}

	/**
	 * Checks the version for the specified bundle.
	 * 
	 * @param bundleId
	 *            the bundle id
	 * @return the specified bundle version, <code>null</code> if the bundle is not
	 *         found
	 */
	public static String getBundleVersion(String bundleId) {
		Bundle bundle = Platform.getBundle(bundleId);
		if (bundle != null) {
			Version version = bundle.getVersion();
			return version.toString();
		} else {
			return null;
		}
	}
}
