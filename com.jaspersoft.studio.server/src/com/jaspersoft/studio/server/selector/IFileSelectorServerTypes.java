/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.selector;

/**
 * This interface allows clients to properly filter the resources in the 
 * dedicated (server) FileSelector widget.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public interface IFileSelectorServerTypes {

	/**
	 * @return the array of supported types
	 */
	String[] getServerSupportedTypes();

	/**
	 * Checks if the implementor support the selection for the specified type
	 * 
	 * @param resourceType the resource type to check 
	 * @return <code>true</code> if supported, <code>false</code> otherwise
	 */
	boolean isResourceCompatible(String resourceType);
}
