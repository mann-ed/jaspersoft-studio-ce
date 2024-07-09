/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.samples;

import java.io.File;
import java.util.Set;

/**
 * This interface describes the details for a sample provider 
 * with all the required information in order to correctly 
 * contribute the Report examples to a newly created project.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public interface ISamplesProvider {
	
	/**
	 * Returns a set of folders that should be added to the project
	 * build-path as "Source Folder".
	 * 
	 * @return a set of source folders
	 */
	public Set<File> getSourceFolders();
	
	/**
	 * Returns a set of additional libraries that should be added 
	 * to the project build-path
	 * 
	 * @return a set of additional libraries
	 */
	public Set<File> getAdditionalLibraries();
	
	/**
	 * Returns a set of folders containing samples JRXML files and related resources.
	 * 
	 * @return a set of folders containing samples
	 */
	public Set<File> getSampleFolders();
	
}
