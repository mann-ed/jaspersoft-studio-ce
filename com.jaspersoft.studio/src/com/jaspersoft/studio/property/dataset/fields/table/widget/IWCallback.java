/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.studio.property.dataset.fields.table.widget;

import com.jaspersoft.studio.property.dataset.fields.table.TColumn;
import com.jaspersoft.studio.widgets.framework.ui.ItemPropertyDescription;

public interface IWCallback {
	public ItemPropertyDescription<?> create(TColumn c);
}
