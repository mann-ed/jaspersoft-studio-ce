/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data;

/**
 * Constants and utility methods related to data adapter stuff.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public class DataAdapterUtils {
	
	/** Default extension for data adapter file(s) */
	public static final String FILE_EXTENSION = "jrdax"; //$NON-NLS-1$
	
	/** Default extension (with dot prefix) for data adater file(s) */
	public static final String DOTTED_FILE_EXTENSION = "." + FILE_EXTENSION; //$NON-NLS-1$
	
	/** Default extension filter for dialog-like windows */
	public static final String FILTER_FILE_EXTENSION = "*." + FILE_EXTENSION; //$NON-NLS-1$
	
	/** Default name for data adapter file */
	public static final String NEW_DATA_ADAPTER_FILENAME = "DataAdapter.jrdax"; //$NON-NLS-1$
	
	/** Deprecated XML extension for data adapter file(s) */
	@Deprecated
	public static final String XML_FILE_EXTENSION = "xml"; //$NON-NLS-1$
	
	/** Deprecated XML extension (with dot prefix) for data adater file(s) */
	@Deprecated
	public static final String DOTTED_XML_FILE_EXTENSION = "." + XML_FILE_EXTENSION; //$NON-NLS-1$
	
	/** Deprecated XML extension filter for dialog-like windows */
	@Deprecated
	public static final String FILTER_XML_FILE_EXTENSION = "*." + XML_FILE_EXTENSION; //$NON-NLS-1$
	
	/** All-files extension filter for dialog-like windows */
	public static final String FILTER_ALLFILES_EXTENSION = "*.*"; //$NON-NLS-1$
	
	/** All allowed extensions as filter */
	public static final String[] FILTER_EXTENSIONS = 
			new String[] {
					FILTER_FILE_EXTENSION,
					FILTER_XML_FILE_EXTENSION,
					FILTER_ALLFILES_EXTENSION
			};
	
	private DataAdapterUtils() {
	}

	/**
	 * Checks if the specified extension is a valid one for Data Adapter files.
	 * 
	 * @param extension the file extension to be checked
	 * @return <code>true</code> if the extension is supported, <code>false</code> otherwise
	 */
	public static boolean isSupportedFileExtension(String extension) {
		return FILE_EXTENSION.equalsIgnoreCase(extension) || XML_FILE_EXTENSION.equalsIgnoreCase(extension);
	}
	
}
