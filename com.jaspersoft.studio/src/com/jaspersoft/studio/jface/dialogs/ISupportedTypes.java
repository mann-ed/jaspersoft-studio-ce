/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.jface.dialogs;

/** 
 * Simple interface used to identify the supported types (i.e file extensions) 
 * in different scenarios. 
 * 
 * <p>
 * Useful when dealing with UI widgets or components involving file selection operations.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public interface ISupportedTypes {
	
	/**
	 * Retrieves the array of all the supported types (i.e file extensions).
	 * 
	 * @return the array of supported types
	 */
	public String[] getSupportedTypes();
	
	/**
	 * Retries the corresponding human readable name for the specified type.
	 * 
	 * @param type the type
	 * @return its corresponding name or the type itself otherwise
	 */
	public String getSupportedTypeName(String type);
	
}
