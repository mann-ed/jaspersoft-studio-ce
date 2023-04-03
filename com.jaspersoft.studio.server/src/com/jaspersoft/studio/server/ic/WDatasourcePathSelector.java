/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.ic;

import com.jaspersoft.studio.property.dataset.fields.table.widget.AWidget;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.model.datasource.filter.DatasourcesAllFilter;
import com.jaspersoft.studio.server.wizard.resource.page.selector.SelectorDatasource;

public class WDatasourcePathSelector extends WResourcePathSelector {

	public WDatasourcePathSelector(AWidget aw) {
		super(aw);
	}

	@Override
	protected String[] getCompatibleResources() {
		return DatasourcesAllFilter.getTypesArrayRest();
	}

	@Override
	protected boolean isResourceCompatible(AMResource r) {
		return SelectorDatasource.isDatasource(r.getValue());
	}

}
