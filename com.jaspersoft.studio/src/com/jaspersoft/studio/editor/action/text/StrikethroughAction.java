/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.action.text;

import net.sf.jasperreports.engine.design.JRDesignStyle;

import org.eclipse.ui.IWorkbenchPart;

public class StrikethroughAction extends ABooleanPropertyAction {
	public static String ID = "com.jaspersoft.studio.editor.action.text.strike";

	public StrikethroughAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
	}

	@Override
	protected Object getPropertyName() {
		return JRDesignStyle.PROPERTY_STRIKE_THROUGH;
	}
}
