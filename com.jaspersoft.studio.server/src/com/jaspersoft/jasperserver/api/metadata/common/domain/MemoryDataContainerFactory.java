/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/


package com.jaspersoft.jasperserver.api.metadata.common.domain;

/**
 * @author Lucian Chirita
 *
 */
public class MemoryDataContainerFactory implements DataContainerFactory {

	public DataContainer createDataContainer() {
		return new MemoryDataContainer();
	}

}
