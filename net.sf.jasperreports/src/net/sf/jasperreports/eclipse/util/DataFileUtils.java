/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util;

import net.sf.jasperreports.data.DataFile;
import net.sf.jasperreports.data.RepositoryDataLocation;
import net.sf.jasperreports.data.StandardRepositoryDataLocation;
import net.sf.jasperreports.data.http.HttpDataLocation;
import net.sf.jasperreports.data.http.StandardHttpDataLocation;

import org.eclipse.core.runtime.Assert;

/**
 * Utility methods to deal with the {@link DataFile} items and other data adapters related objects.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 * @see DataFile
 * @see HttpDataLocation
 * @see RepositoryDataLocation
 */
public class DataFileUtils {
	
	public static final String HTTP_LOCATION_PREFIX = "http://";
	
	/**
	 * Creates a {@link DataFile} instance based on the locations string.
	 *  
	 * @param location the location for the {@link DataFile} instance
	 * @return the {@link DataFile} instance
	 */
	public static DataFile getDataFile(String location) {
		String str = (location == null ? "" : location);
		if(str.startsWith(HTTP_LOCATION_PREFIX)) {
			StandardHttpDataLocation httpLocation = new StandardHttpDataLocation();
			httpLocation.setUrl(str);
			return httpLocation;
		}
		else {
			StandardRepositoryDataLocation stdLocation = new StandardRepositoryDataLocation();
			stdLocation.setLocation(str);
			return stdLocation;
		}
	}
	
	/**
	 * Tries to get a meaningful location from the {@link DataFile} instance.
	 * 
	 * @param datafile the instance of {@link DataFile}
	 * @return a possibly meaningful location, empty string otherwise
	 */
	public static String getDataFileLocation(DataFile datafile) {
		Assert.isNotNull(datafile);
		if(datafile instanceof RepositoryDataLocation) {
			return ((RepositoryDataLocation) datafile).getLocation();
		}
		else if(datafile instanceof HttpDataLocation) {
			return ((HttpDataLocation) datafile).getUrl();
		}
		else {
			return StringUtils.EMPTY_STRING;
		}
	}
}
