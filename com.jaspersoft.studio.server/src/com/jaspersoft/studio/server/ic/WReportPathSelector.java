/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.ic;

import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;
import com.jaspersoft.studio.property.dataset.fields.table.widget.AWidget;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.model.MJrxml;

public class WReportPathSelector extends WResourcePathSelector {

	public WReportPathSelector(AWidget aw) {
		super(aw);
	}

	@Override
	protected String[] getCompatibleResources() {
		return new String[] { ResourceMediaType.FILE_CLIENT_TYPE };
	}

	@Override
	protected boolean isResourceCompatible(AMResource r) {
		return r instanceof MJrxml;
	}

}
