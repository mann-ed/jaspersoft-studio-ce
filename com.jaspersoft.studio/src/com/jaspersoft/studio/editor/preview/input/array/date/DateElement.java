/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.preview.input.array.date;

import java.util.Date;

import org.eclipse.nebula.widgets.cdatetime.CDT;

public class DateElement extends ADateElement {

	@Override
	public Class<?> getSupportedType() {
		return Date.class;
	}

	@Override
	protected int getStyle() {
		return CDT.DATE_SHORT;
	}

	@Override
	protected Date getDate() {
		return date.getSelection();
	}

}
