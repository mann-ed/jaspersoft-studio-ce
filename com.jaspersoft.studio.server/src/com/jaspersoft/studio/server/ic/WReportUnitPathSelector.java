/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.ic;

import com.jaspersoft.jasperserver.dto.resources.ResourceMediaType;
import com.jaspersoft.studio.property.dataset.fields.table.widget.AWidget;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.model.MReportUnit;

public class WReportUnitPathSelector extends WResourcePathSelector {

	public WReportUnitPathSelector(AWidget aw) {
		super(aw);
	}

	@Override
	protected String[] getCompatibleResources() {
		return new String[] { ResourceMediaType.REPORT_UNIT_CLIENT_TYPE };
	}

	@Override
	protected boolean isResourceCompatible(AMResource r) {
		return r instanceof MReportUnit;
	}

}
