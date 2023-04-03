/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.metadata.common.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;



/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id:ResourceLookup.java 2403 2006-03-16 14:08:12Z lucian $
 */
@JasperServerAPI
public interface ResourceLookup extends Resource
{
	void setURI(String uri);
	
	
	void setResourceType(String resourceType);
}
