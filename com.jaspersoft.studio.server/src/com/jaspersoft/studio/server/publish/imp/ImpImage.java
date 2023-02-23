/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.publish.imp;

import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.studio.server.model.MRImage;
import com.jaspersoft.studio.server.model.MReportUnit;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class ImpImage extends AImpObject {
	public ImpImage(JasperReportsConfiguration jrConfig) {
		super(jrConfig);
	}

	protected JRDesignExpression getExpression(JRDesignElement img) {
		return (JRDesignExpression) ((JRDesignImage) img).getExpression();
	}

	protected ResourceDescriptor createResource(MReportUnit mrunit) {
		return MRImage.createDescriptor(mrunit);
	}

}
