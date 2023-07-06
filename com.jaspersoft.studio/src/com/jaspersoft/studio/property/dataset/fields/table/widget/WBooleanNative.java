/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.dataset.fields.table.widget;

public class WBooleanNative extends WBoolean {
	public WBooleanNative(AWidget aw) {
		super(aw);
	}

	@Override
	protected void handleValueChanged() {
		if (cmb.getSelectionIndex() == 0)
			aw.setValue(true);
		else if (cmb.getSelectionIndex() == 1)
			aw.setValue(false);
		cmb.setToolTipText(aw.getToolTipText());
	}

	@Override
	protected String[] getValues() {
		return new String[] { "true", "false" };
	}
}
