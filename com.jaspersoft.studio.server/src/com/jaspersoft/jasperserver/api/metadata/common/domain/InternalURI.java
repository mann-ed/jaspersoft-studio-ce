/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.metadata.common.domain;

/**
 * A URI reference to an object in our metadata
 * 
 * @author swood
 * 
 */
public interface InternalURI {

	public String getURI();

	public String getPath();

	public String getProtocol();

	public String getParentURI();

	public String getParentPath();
}
