/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.customvisualization.server;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.studio.server.model.MRCSS;
import com.jaspersoft.studio.server.model.MReportUnit;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class ImpCSS extends AImpResource {
	public ImpCSS(JasperReportsConfiguration jrConfig) {
		super(jrConfig);
	}

	protected ResourceDescriptor createResource(MReportUnit mrunit) {
		return MRCSS.createDescriptor(mrunit);
	}

}
