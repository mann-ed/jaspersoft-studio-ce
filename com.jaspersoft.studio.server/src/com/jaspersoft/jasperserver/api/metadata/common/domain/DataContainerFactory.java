/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/


package com.jaspersoft.jasperserver.api.metadata.common.domain;

/**
 * The interface for the factory that creates the DataContainer
 * See {@link com.jaspersoft.jasperserver.api.metadata.common.domain.DataContainer}
 *
 * @author Lucian Chirita
 * @version $Id:
 */
public interface DataContainerFactory {

    /**
     * Creates and returns the new DataContainer
     *
     * @return DataContainer
     */    
	DataContainer createDataContainer();
	
}
