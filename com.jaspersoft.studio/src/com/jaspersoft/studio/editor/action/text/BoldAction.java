/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.action.text;

import net.sf.jasperreports.engine.design.JRDesignStyle;

import org.eclipse.ui.IWorkbenchPart;

public class BoldAction extends ABooleanPropertyAction {
	public static String ID = "com.jaspersoft.studio.editor.action.text.bold";

	public BoldAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
	}
	
	@Override
	protected Object getPropertyName() {
		return JRDesignStyle.PROPERTY_BOLD;
	}
}
