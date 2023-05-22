/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.ireport.jasperserver.ws;

import org.apache.axis.AxisEngine;
import org.apache.axis.configuration.FileProvider;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ResourceConfigurationProvider.java 9821 2007-08-29 18:17:55Z lucian $
 */
public class ResourceConfigurationProvider extends FileProvider {

	public ResourceConfigurationProvider(String resourceName) {
		super(ResourceConfigurationProvider.class.getResourceAsStream(resourceName));
	}

    public void writeEngineConfig(AxisEngine engine) {
    	// nothing
    }

}
