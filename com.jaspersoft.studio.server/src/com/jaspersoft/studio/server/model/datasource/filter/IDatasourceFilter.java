/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.model.datasource.filter;

import java.util.Set;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;

public interface IDatasourceFilter {
	public boolean isDatasource(ResourceDescriptor r);

	public Set<String> getFilterTypes();
}
