/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;
import net.sf.jasperreports.eclipse.builder.JasperReportsNature;
import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.wizard.project.ProjectUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.repo.RepositoryUtil;

public class FileUtils {

	public static final String DEFAULT_PROJECT = "MyReports"; //$NON-NLS-1$
	public static final String DEFAULT_PROJECT_PROPERTY = "com.jaspersoft.studio.myreports.created"; //$NON-NLS-1$
	public static final String KEY_FILE = "ifile"; //$NON-NLS-1$
	public static final String KEY_IPROJECT = "iproject"; //$NON-NLS-1$
	public static final String UTF8_ENCODING = "UTF-8"; //$NON-NLS-1$
	public static final String LATIN1_ENCODING = "ISO-8859-1"; //$NON-NLS-1$
	public static final String TMPCOPY_FILE_PREFIX = "___$$tmpcopy"; //$NON-NLS-1$

	public static void closeStream(Closeable stream) {
		if (stream != null)
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static String findRelativePath(String base, String path) throws IOException {
		if (base == null)
			throw new IOException("NullOriginalPath"); //$NON-NLS-1$

		if (path == null)
			throw new IOException("NullRelativePath"); //$NON-NLS-1$

		//
		// remove ./ if present
		//
		if (path.startsWith("./")) //$NON-NLS-1$
			path = path.substring(2);

		//
		// remove any .. reference by taking off the last section/ of
		// the original path
		//
		if (path.startsWith("../")) { //$NON-NLS-1$
			int slash = base.lastIndexOf('/');
			base = base.substring(0, slash);
			path = path.substring(3);
		}

		int slash = base.lastIndexOf('/');

		if (slash < 0)
			return path;

		String dir = base.substring(0, slash + 1);
		return dir + path;
	}

	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if (!destFile.exists() && destFile.createNewFile())
			org.apache.commons.io.FileUtils.copyFile(sourceFile, destFile);
	}

	public static File createTempFile(String prefix, String sufix) throws IOException {
		File f = File.createTempFile(prefix, sufix);
		f.deleteOnExit();
		return f;
	}

	public static File createTempDir() throws IOException {
		return createTempDir(""); //$NON-NLS-1$
	}

	public static File createTempDir(String prefix) throws IOException {
		final File sysTempDir = new File(System.getProperty("java.io.tmpdir")); //$NON-NLS-1$
		File newTempDir;
		final int maxAttempts = 9;
		int attemptCount = 0;
		do {
			attemptCount++;
			if (attemptCount > maxAttempts)
				throw new IOException(NLS.bind(Messages.FileUtils_ImpossibleToCreateTempDirectory, maxAttempts));
			String dirName = prefix + System.currentTimeMillis();
			newTempDir = new File(sysTempDir, dirName);
		} while (newTempDir.exists());

		if (newTempDir.mkdirs()) {
			newTempDir.deleteOnExit();
			newTempDir.setWritable(true, false);
			newTempDir.setReadable(true, false);
			return newTempDir;
		} else
			throw new IOException(NLS.bind(Messages.FileUtils_UnableToCreateDirectory, newTempDir.getAbsolutePath()));
	}

	/**
	 * Recursively delete file or directory
	 * 
	 * @param fileOrDir
	 *            the file or dir to delete
	 * @return true iff all files are successfully deleted
	 */
	public static boolean recursiveDelete(File fileOrDir) {
		if (fileOrDir.isDirectory()) {
			// recursively delete contents
			for (File innerFile : fileOrDir.listFiles()) {
				if (!recursiveDelete(innerFile)) {
					return false;
				}
			}
		}

		return fileOrDir.delete();
	}

	public static byte[] getBytes(File file) throws IOException {
		return IOUtils.toByteArray(new FileInputStream(file));
	}

	public static byte[] getBytes(IFile file) throws IOException, CoreException {
		return IOUtils.toByteArray(file.getContents());
	}

	public static String readFileAsAString(File file) throws IOException {
		return new String(getBytesFromFile(file));
	}

	/**
	 * Returns the contents of the file in a byte array.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytesFromFile(File file) throws IOException {
		try (InputStream is = new FileInputStream(file);) {

			// Get the size of the file
			long length = file.length();

			// You cannot create an array using a long type.
			// It needs to be an int type.
			// Before converting to an int type, check
			// to ensure that file is not larger than Integer.MAX_VALUE.
			if (length > Integer.MAX_VALUE)
				// File is too large
				throw new IllegalArgumentException(Messages.FileUtils_FileTooLargeError);

			// Create the byte array to hold the data
			byte[] bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length)
				throw new IOException(NLS.bind(Messages.FileUtils_UnableToReadFile, file.getName()));
			return bytes;
		}
	}

	public static File fileRenamed(File file, String strFilename, String ext) throws CoreException {
		return fileRenamed(file, strFilename, ext, true);
	}

	public static File fileRenamed(File file, String strFilename, String ext, boolean showWarning)
			throws CoreException {
		String fname = strFilename + ext;
		if (fname.equals(file.getAbsolutePath()))
			return file;
		deleteFileIfExists(null, fname);

		file.renameTo(new File(fname));
		if (showWarning)
			UIUtils.showWarning(NLS.bind(Messages.FileUtils_DifferentFileTypeWarning, fname));
		return new File(fname);
	}

	public static IFile fileRenamed(IFile file, String strFilename, String ext, boolean showWarning,
			IProgressMonitor monitor) throws CoreException {
		String fname = strFilename + ext;
		if (fname.equals(file.getFullPath().toOSString()))
			return file;
		deleteFileIfExists(monitor, fname);
		try {
			file.move(new Path(fname), true, monitor);
		} catch (Exception ex) {
			// For some reasons on windows the move is able to create the file
			// with the new name, but it is unable to remove the original one
			// (the move works like a copy with new name an remove the original,
			// instead a real rename)
			// It seems a permission issues, even if it regards only the delete
			// procedure, or more probably a "file in use" lock
			// anyway as hotfix we catch the exception and log it
			file.getLocation().toFile().delete();// <-Another try to remove the
													// original file, probably
													// will fail
													// too
			JasperReportsPlugin.getDefault().logWarning(Messages.FileUtils_4, ex);
		}
		if (showWarning)
			UIUtils.showWarning(NLS.bind(Messages.FileUtils_DifferentFileTypeWarning, fname));
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource r = root.findMember(fname);
		if (r != null && r.exists() && r instanceof IFile)
			return (IFile) r;
		return file;
	}

	protected static void deleteFileIfExists(IProgressMonitor monitor, String fname) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource r = root.findMember(fname);
		if (r != null && r.exists())
			r.delete(true, monitor);
	}

	public static String readInputStreamAsString(InputStream in) throws IOException {

		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while (result != -1) {
			byte b = (byte) result;
			buf.write(b);
			result = bis.read();
		}
		return buf.toString();
	}

	public static void writeFile(File f, String content) throws IOException {
		org.apache.commons.io.FileUtils.write(f, content, UTF8_ENCODING);
	}

	public static String getPropertyAsString(Properties prop) {
		StringBuilder str = new StringBuilder();
		for (String key : prop.stringPropertyNames()) {
			str.append(key).append("=").append(stringConvert(prop.getProperty(key), false, true)) //$NON-NLS-1$
					.append("\n"); //$NON-NLS-1$
		}
		return str.toString();
	}

	public static Properties load(String propertiesString) throws IOException {
		Properties properties = new Properties();
		if (propertiesString != null)
			properties.load(new StringReader(propertiesString));
		return properties;
	}

	public static void copyInputStream(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		while (len >= 0) {
			out.write(buffer, 0, len);
			len = in.read(buffer);
		}
		closeStream(in);
		closeStream(out);
	}

	public static IFile getInProjectFile(URI uri, IProgressMonitor monitor) throws CoreException {
		IPath location = new Path(uri.getPath());
		IProject project = getProject(monitor);
		String fileName = location.lastSegment();
		IFile file = project.getFile(fileName);
		if (file.exists()) {
			file = getValidNewIFile(project, fileName);
		}
		file.createLink(location, IResource.REPLACE, monitor);
		return file;
	}

	public static IFolder getInProjectFolder(URI uri, IProgressMonitor monitor) throws CoreException {
		IPath location = new Path(uri.getPath());
		IProject project = getProject(monitor);
		String folderName = location.lastSegment();
		IFolder folder = project.getFolder(folderName);
		if (folder.exists()) {
			folder = getValidNewIFolder(project, folderName);
		}
		folder.createLink(location, IResource.REPLACE, monitor);
		return folder;
	}

	public static void prepareFolder(IFolder folder, IProgressMonitor monitor) throws CoreException {
		IContainer parent = folder.getParent();
		if (parent != null && parent instanceof IFolder)
			prepareFolder((IFolder) parent, monitor);
		if (!folder.exists())
			folder.create(true, true, monitor);
	}

	public static IProject getProject(IProgressMonitor monitor) throws CoreException {
		IProject project = null;
		for (IProject prj : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (prj.isOpen()) {
				if (project == null)
					project = prj;
				else if (prj.getNature(JasperReportsNature.NATURE_ID) != null)
					project = prj;
				if (project.getName().equals(DEFAULT_PROJECT))
					break;
			}
		}
		if (project == null)
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(DEFAULT_PROJECT);
		// Create a project if one doesn't exist and open it.
		if (!project.exists()) {
			project.create(monitor);
			project.open(monitor);
			ProjectUtil.createJRProject(monitor, project);
		}
		if (!project.isOpen())
			project.open(monitor);
		return project;
	}

	public static IEditorInput checkAndConvertEditorInput(IEditorInput editorInput, IProgressMonitor monitor)
			throws PartInitException {
		if (editorInput instanceof FileStoreEditorInput) {
			try {
				FileStoreEditorInput fsei = (FileStoreEditorInput) editorInput;
				IFile file = getInProjectFile(fsei.getURI(), monitor);
				editorInput = new FileEditorInput(file);
			} catch (CoreException e) {
				throw new PartInitException(e.getMessage(), e);
			}
		}
		return editorInput;
	}

	/**
	 * Converts unicodes to encoded &#92;uxxxx and escapes special characters with a
	 * preceding slash
	 */
	public static String stringConvert(String theString, boolean escapeSpace, boolean escapeUnicode) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuilder outBuffer = new StringBuilder(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				if (x == 0 || escapeSpace)
					outBuffer.append('\\');
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\');
				outBuffer.append(aChar);
				break;
			default:
				if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(toHex((aChar >> 12) & 0xF));
					outBuffer.append(toHex((aChar >> 8) & 0xF));
					outBuffer.append(toHex((aChar >> 4) & 0xF));
					outBuffer.append(toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}

	/**
	 * Convert a nibble to a hex character
	 * 
	 * @param nibble
	 *            the nibble to convert.
	 */
	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	/** A table of hex digits */
	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	public static File findFile(IFile file, String str) {
		if (str == null || str.isEmpty())
			return null;
		IContainer parent = file.getParent();
		return resolveFile(str, Arrays.asList(new File(parent.getLocationURI()),
				file.getRawLocation().toFile().getParentFile(), new File(file.getProject().getLocationURI())), true);
	}

	public static File resolveFile(String fileName, List<File> folders, boolean isResolveAbsolutePath) {
		if (fileName != null) {
			for (Iterator<File> it = folders.iterator(); it.hasNext();) {
				File folder = it.next();
				File file = new File(folder, fileName);
				if (file.exists() && file.isFile())
					return file;
			}

			if (isResolveAbsolutePath) {
				File file = new File(fileName);
				if (file.exists() && file.isFile())
					return file;
			}
		}
		return null;
	}

	public static File resolveFileUrl(String fileName, List<File> folders, boolean isResolveAbsolutePath) {
		File fileToBeOpened = resolveFile(fileName, folders, isResolveAbsolutePath);
		// The simple file resolver can't resolve the filename, try to check if it's an
		// url
		if (fileToBeOpened == null) {
			try {
				URL fileURL = new URL(fileName);
				File f = new File(fileURL.toURI());
				if (f.exists())
					fileToBeOpened = f;
			} catch (Exception e) {
				// do not care about it
			}
		}
		return fileToBeOpened;
	}

	public static File findFile(IFile file, String str, JasperReportsContext jrConfig) {
		try {
			InputStream is = RepositoryUtil.getInstance(jrConfig).getInputStreamFromLocation(str);
			if (is != null) {
				File f = getTmpFile(str);
				FileOutputStream fos = new FileOutputStream(f);
				try {
					IOUtils.copy(is, fos);
					return f;
				} finally {
					FileUtils.closeStream(is);
					FileUtils.closeStream(fos);
				}
			}
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
		return FileUtils.findFile(file, str);
	}

	public static File getTmpFile(String str) throws IOException {
		String fname = FilenameUtils.separatorsToSystem(str);
		fname = FilenameUtils.getName(fname);
		File f = File.createTempFile(fname + TMPCOPY_FILE_PREFIX, "");
		f.deleteOnExit();
		return f;
	}

	/**
	 * Check if the passed value is a valid URL
	 * 
	 * @param value
	 *            the value to check
	 * @return true if the value is a valid URL, false otherwise
	 */
	public static boolean isValidURL(String value) {
		try {
			new URL(value);
			return true;
		} catch (Exception ex) {
		}
		return false;
	}

	/**
	 * Check if a filename is valid for the current OS or not
	 * 
	 * @param file
	 *            the file name
	 * @return true if the filename is valid, false otherwise
	 */
	public static boolean isValidFilename(String file) {
		File f = new File(file);
		try {
			f.getCanonicalPath();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Return the the relative path to get the file toRelativize from the position
	 * of the baseFile
	 * 
	 * @param baseFile
	 *            a not null file that will be used as base
	 * @param toRelativize
	 *            a not null file to relativize
	 * @return the path to get the toRelativize file from the position of the
	 *         baseFile
	 */
	public static String getFileRelativePath(IFile baseFile, IFile toRelativize) {
		if (baseFile.getProject().equals(toRelativize.getProject())) {
			// The files are in the same project
			int equalSegments = 0;
			IPath reportPath = baseFile.getFullPath();
			IPath daPath = toRelativize.getFullPath();
			int reportSegments = reportPath.segmentCount() - 1;
			int daFileSegments = daPath.segmentCount() - 1;
			for (int i = 0; i < reportSegments && i < daFileSegments; i++) {
				if (reportPath.segment(i).equals(daPath.segment(i))) {
					equalSegments++;
				} else {
					break;
				}
			}
			if (equalSegments < reportSegments) {
				// the file is on a folder on an upper level of base file
				StringBuilder upperLevel = new StringBuilder();
				int goUpNumber = reportSegments - equalSegments;
				for (int i = 0; i < goUpNumber; i++) {
					upperLevel.append("../"); //$NON-NLS-1$
				}
				for (int i = equalSegments; i < daFileSegments; i++) {
					upperLevel.append(daPath.segment(i) + "/"); //$NON-NLS-1$
				}
				upperLevel.append(daPath.lastSegment());
				return upperLevel.toString();
			} else {
				// The file is on a subfolder or in the same folder of the base
				// file
				StringBuilder upperLevel = new StringBuilder();
				for (int i = equalSegments; i < daFileSegments; i++) {
					upperLevel.append(daPath.segment(i) + "/"); //$NON-NLS-1$
				}
				upperLevel.append(daPath.lastSegment());
				return upperLevel.toString();
			}
		} else {
			// The files are in different projects
			StringBuilder upperLevel = new StringBuilder();
			for (int i = 0; i < baseFile.getFullPath().segmentCount() - 1; i++) {
				upperLevel.append("../"); //$NON-NLS-1$
			}
			upperLevel.append(toRelativize.getProject().getName() + "/"); //$NON-NLS-1$
			upperLevel.append(toRelativize.getProjectRelativePath().toPortableString());
			return upperLevel.toString();
		}
	}

	/**
	 * Stores a property to the specified {@link IFile} instance.
	 * 
	 * @param file
	 *            the file instance
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 */
	public static void storeIFileProperty(IFile file, QualifiedName key, String value) {
		if (file != null && key != null) {
			try {
				file.setPersistentProperty(key, value);
			} catch (CoreException e) {
				JasperReportsPlugin.getDefault().logError(e);
			}
		}
	}

	/**
	 * Returns the associated property to the specified {@link IFile} instance.
	 * 
	 * @param file
	 *            the file instance
	 * @param key
	 *            the property key
	 * @return the property value if any, <code>null</code> otherwise
	 */
	public static String getIFileProperty(IFile file, QualifiedName key) {
		String pValue = null;
		if (file != null && key != null) {
			try {
				pValue = file.getPersistentProperty(key);
			} catch (CoreException e) {
				JasperReportsPlugin.getDefault().logError(e);
			}
		}
		return pValue;
	}

	/**
	 * Removes the specified property from the input {@link IFile} instance.
	 * 
	 * @param file
	 *            the file instance
	 * @param key
	 *            the property key
	 */
	public static void removeIFileProperty(IFile file, QualifiedName key) {
		if (file != null && key != null) {
			try {
				file.setPersistentProperty(key, null);
			} catch (CoreException e) {
				JasperReportsPlugin.getDefault().logError(e);
			}
		}
	}

	/**
	 * Unzip a zip file into the destination folder. If something goes wrong with
	 * the unizp the destinaition folder is deleted
	 * 
	 * @param zipFile
	 *            file to unzip
	 * @param outputFolder
	 *            folder where the unzipped files must be placed
	 * @throws IOException
	 *             exception thrown if something goes wrong
	 */
	public static void unZip(File zipFile, File outputFolder) throws IOException {
		byte[] buffer = new byte[1024];
		if (!outputFolder.exists())
			outputFolder.mkdir();
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			if (ze == null) {
				throw new IOException(Messages.FileUtils_10);
			}
			while (ze != null) {
				if (!ze.isDirectory()) {
					String fileName = ze.getName();
					File newFile = new File(outputFolder, fileName);
					new File(newFile.getParent()).mkdirs();
					try (FileOutputStream fos = new FileOutputStream(newFile);) {
						int len;
						while ((len = zis.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
					}
				}
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
		} catch (IOException ex) {
			org.apache.commons.io.FileUtils.deleteDirectory(outputFolder);
			throw ex;
		}
	}

	public static void unZip(InputStream in, File outputFolder, IProgressMonitor monitor, ZipFilter filter)
			throws IOException {
		byte[] buffer = new byte[1024];
		if (!outputFolder.exists())
			outputFolder.mkdir();
		try (ZipInputStream zis = new ZipInputStream(in);) {
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();
			monitor.setTaskName(Messages.FileUtils_11);
			if (ze == null)
				throw new IOException(Messages.FileUtils_10);
			while (ze != null && !monitor.isCanceled()) {
				if (!ze.isDirectory()) {
					String[] pathComponents = ze.getName().split("/"); //$NON-NLS-1$
					if (filter.isNecessary(pathComponents)) {
						File newFile = new File(outputFolder, pathComponents[pathComponents.length - 1]);
						monitor.setTaskName(Messages.FileUtils_11 + " " + newFile.getName()); //$NON-NLS-1$
						File pfile = newFile.getParentFile();
						if (!pfile.exists())
							pfile.mkdirs();
						try (FileOutputStream fos = new FileOutputStream(newFile);) {
							int len;
							while ((len = zis.read(buffer)) > 0 && !monitor.isCanceled())
								fos.write(buffer, 0, len);
						}
					}
				}
				ze = zis.getNextEntry();
			}
		} catch (IOException ex) {
			org.apache.commons.io.FileUtils.deleteDirectory(outputFolder);
			throw ex;
		}
	}

	/**
	 * Converts a set of {@link File} references to the corresponding set with the
	 * actual absolute file locations.
	 * 
	 * @param fileReferences
	 *            the set of file references
	 * @return the set of locations
	 */
	public static Set<String> convertToLocalPath(Set<File> fileReferences) {
		if (fileReferences != null && !fileReferences.isEmpty()) {
			Set<String> paths = new HashSet<>(fileReferences.size());
			for (File f : fileReferences)
				paths.add(f.getAbsolutePath());
			return paths;
		}
		return new HashSet<>(0);
	}

	/**
	 * Compare the paths of two locations checking if they are the same.
	 * 
	 * @param location1
	 *            first path location
	 * @param location2
	 *            second path location
	 * @return <code>true</code> if the location is the same, <code>false</code>
	 *         otherwise
	 */
	public static boolean isSameLocation(String location1, String location2) {
		File loc1 = new File(location1);
		File loc2 = new File(location2);
		return loc1.compareTo(loc2) == 0;
	}

	/**
	 * Gets a new {@link IFile} instance given the specified information. It
	 * prevents from returning an already existing file instance using a pattern to
	 * try the filename.
	 * <p>
	 * NOTE: used pattern in case of already existing {@link IFile}
	 * &lt;name&gt;_&lt;number&gt;
	 * 
	 * @param project
	 *            the project where will be created the new {@link IFile} instance
	 * @param name
	 *            the suggested name
	 * @return the new {@link IFile} instance
	 */
	public static IFile getValidNewIFile(IProject project, String name) {
		IFile file = project.getFile(name);
		String newName = null;
		int i = 1;
		while (file.exists() && i < 1000) {
			newName = FilenameUtils.removeExtension(name) + "_" + i;
			file = project.getFile(newName + "." + FilenameUtils.getExtension(name));
			i++;
		}
		return file;
	}

	/**
	 * Gets a new {@link IFolder} instance given the specified information. It
	 * prevents from returning an already existing folder instance using a pattern
	 * to try the filename.
	 * <p>
	 * NOTE: used pattern in case of already existing {@link IFolder}
	 * &lt;name&gt;_&lt;number&gt;
	 * 
	 * @param project
	 *            the project where will be created the new {@link IFolder} instance
	 * @param name
	 *            the suggested name
	 * @return the new {@link IFolder} instance
	 */
	public static IFolder getValidNewIFolder(IProject project, String name) {
		IFolder folder = project.getFolder(name);
		String newName = null;
		int i = 1;
		while (folder.exists() && i < 1000) {
			newName = FilenameUtils.removeExtension(name) + "_" + i;
			folder = project.getFolder(newName + "." + FilenameUtils.getExtension(name));
			i++;
		}
		return folder;
	}

	/**
	 * Compares two version strings.
	 * 
	 * Use this instead of String.compareTo() for a non-lexicographical comparison
	 * that works for version strings. e.g. "1.10".compareTo("1.6").
	 * 
	 * @note It does work if "1.10" is supposed to be equal to "1.10.0".
	 * 
	 * @param str1
	 *            a string of ordinal numbers separated by decimal points.
	 * @param str2
	 *            a string of ordinal numbers separated by decimal points.
	 * @return The result is a negative integer if str1 is _numerically_ less than
	 *         str2. The result is a positive integer if str1 is _numerically_
	 *         greater than str2. The result is zero if the strings are
	 *         _numerically_ equal.
	 */
	public static int versionCompare(String str1, String str2) {
		String[] split1 = str1.split("\\.");
		String[] split2 = str2.split("\\.");

		int versionNumbers = Math.max(split1.length, split2.length);

		// Pad the numbers to have the same digits
		String[] vals1 = new String[versionNumbers];
		String[] vals2 = new String[versionNumbers];
		for (int i = 0; i < versionNumbers; i++) {
			if (i < split1.length) {
				vals1[i] = split1[i];
			} else {
				vals1[i] = "0";
			}

			if (i < split2.length) {
				vals2[i] = split2[i];
			} else {
				vals2[i] = "0";
			}
		}

		int i = 0;
		// set index to first non-equal ordinal or length of shortest version string
		while (i < versionNumbers && vals1[i].equals(vals2[i])) {
			i++;
		}
		// compare first non-equal ordinal number
		if (i < versionNumbers) {
			int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
			return Integer.signum(diff);
		}
		// the strings are equal or one string is a substring of the other
		// e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
		return Integer.signum(vals1.length - vals2.length);
	}
	
	/**
	 * Creates the specified resource, and if needed also its parent if it does not exist already.
	 * <br/>
	 * Supported resource types are:
	 * <ul>
	 * 	<li>{@link IResource.FILE}</li> 
	 * 	<li>{@link IResource.FOLDER}</li> 
	 * 	<li>{@link IResource.PROJECT}</li> 
	 * </ul>
	 * 
	 * @param resource the resource to be created
	 * @param monitor the progress monitor
	 * @throws CoreException
	 */
	public static void createResource(final IResource resource, IProgressMonitor monitor) throws CoreException {
		if (resource == null || resource.exists()) {
			return;
		}
		if (!resource.getParent().exists()) {
			createResource(resource.getParent(),monitor);
		}
		switch (resource.getType()) {
			case IResource.FILE:
				((IFile) resource).create(new ByteArrayInputStream(new byte[0]), true, monitor);
				break;
			case IResource.FOLDER:
				((IFolder) resource).create(IResource.NONE, true, monitor);
				break;
			case IResource.PROJECT:
				((IProject) resource).create(monitor);
				((IProject) resource).open(monitor);
				break;
			default:
				throw new RuntimeException("Unsupported use case: supported resource types are 1) file 2) folder 3) project");
		}
	}

	public static void createFolder(final IContainer resource, IProgressMonitor monitor) throws CoreException {
		if (resource == null || resource.exists())
			return;
		if (!resource.getParent().exists())
			createFolder(resource.getParent(), monitor);
		if (resource instanceof IFolder)
			((IFolder) resource).create(IResource.NONE, true, monitor);
	}
}
