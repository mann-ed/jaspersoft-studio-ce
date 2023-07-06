/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.widgets.framework.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.widgets.framework.model.WidgetPropertyDescriptor;
import com.jaspersoft.studio.widgets.framework.model.WidgetsDescriptor;

/**
 * New property description allowing to have a multi-line text with bigger text control. 
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public class MultiLineTextPropertyDescription<T> extends TextPropertyDescription<T> {
	
	private static final int DEFAULT_SUGGESTED_HEIGHT = 80;
	private int heightHint = DEFAULT_SUGGESTED_HEIGHT;

	public MultiLineTextPropertyDescription() {
		super();
	}

	public MultiLineTextPropertyDescription(String name, String description, boolean mandatory) {
		super(name, description, mandatory);
	}

	public MultiLineTextPropertyDescription(String name, String label, String description, boolean mandatory,
			T defaultValue) {
		super(name, label, description, mandatory, defaultValue);
	}

	public MultiLineTextPropertyDescription(String name, String label, String description, boolean mandatory) {
		super(name, label, description, mandatory);
	}
	
	@Override
	protected int getTextControlStyle() {
		return SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP;
	}
	
	@Override
	protected GridData getTextControlGridData() {
		GridData textData = new GridData(SWT.FILL,SWT.FILL,true,false);
		textData.verticalAlignment = SWT.CENTER;
		textData.heightHint = heightHint;
		return textData;		
	}
	
	@Override
	public ItemPropertyDescription<T> clone() {
		MultiLineTextPropertyDescription<T> result = new MultiLineTextPropertyDescription<>();
		result.defaultValue = defaultValue;
		result.description = description;
		result.jConfig = jConfig;
		result.label = label;
		result.mandatory = mandatory;
		result.name = name;
		result.readOnly = readOnly;
		result.fallbackValue = fallbackValue;
		return result;
	}
	
	@Override
	public ItemPropertyDescription<?> getInstance(WidgetsDescriptor cd, WidgetPropertyDescriptor cpd,
			JasperReportsConfiguration jConfig) {
		MultiLineTextPropertyDescription<String> result = new MultiLineTextPropertyDescription<>(cpd.getName(),
				cd.getLocalizedString(cpd.getLabel()), cd.getLocalizedString(cpd.getDescription()), cpd.isMandatory(),
				cpd.getDefaultValue());
		result.setReadOnly(cpd.isReadOnly());
		result.setFallbackValue(cpd.getFallbackValue());
		return result;
	}
}
