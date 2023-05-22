/*******************************************************************************
 * Copyright (C) 2013 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.widgets.framework.ui;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;

import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.widgets.framework.IWItemProperty;
import com.jaspersoft.studio.widgets.framework.manager.DoubleControlComposite;
import com.jaspersoft.studio.widgets.framework.model.WidgetPropertyDescriptor;
import com.jaspersoft.studio.widgets.framework.model.WidgetsDescriptor;

/**
 * Widget that allows to edit the value for a boolean property.
 * It uses a combo to manage the value changes.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class BooleanComboPropertyDescription extends SelectableComboItemPropertyDescription<Boolean>{
	
	// Constants for true / false values
	// Note: HC understands only these string values
	public static final String TRUE_VALUE = "true";
	public static final String FALSE_VALUE = "false";

	public BooleanComboPropertyDescription() {
		super();
	}
	
	public BooleanComboPropertyDescription(String name, String label, String description, boolean mandatory, Boolean defaultValue, String[][] keyValues) {
		super(name, label, description, mandatory, defaultValue, keyValues);
	}

	@Override
	public void update(Control c, IWItemProperty wip) {
		DoubleControlComposite cmp = (DoubleControlComposite) wip.getControl();
		if (wip.isExpressionMode()) {
			super.update(c, wip);
		} else {
			boolean isFallback = false;
			Combo booleanCombo = (Combo) cmp.getSecondContainer().getData();
			String v = wip.getStaticValue();
			if(verifyValue(v)){
				booleanCombo.setText(v);
			} else {
				booleanCombo.setText(String.valueOf(wip.getFallbackValue()));
				isFallback = true;
			}
			changeFallbackForeground(isFallback, booleanCombo);
			cmp.switchToSecondContainer();
		}
	}

	protected boolean verifyValue(String value) {
		return TRUE_VALUE.equalsIgnoreCase(value) || FALSE_VALUE.equalsIgnoreCase(value);
	}
	
	@Override
	public BooleanComboPropertyDescription clone(){
		BooleanComboPropertyDescription result = new BooleanComboPropertyDescription();
		result.defaultValue = defaultValue;
		result.description = description;
		result.jConfig = jConfig;
		result.label = label;
		result.mandatory = mandatory;
		result.name = name;
		result.readOnly = readOnly;
		result.keyValues = keyValues;
		result.fallbackValue = fallbackValue;
		return result;
	}

	@Override
	public ItemPropertyDescription<?> getInstance(WidgetsDescriptor cd, WidgetPropertyDescriptor cpd, JasperReportsConfiguration jConfig) {
		String[][] i18nOpts = new String[2][2];
		i18nOpts[0][0] = TRUE_VALUE;
		i18nOpts[0][1] = cd.getLocalizedString(i18nOpts[0][0]);
		i18nOpts[1][0] = FALSE_VALUE;
		i18nOpts[1][1] = cd.getLocalizedString(i18nOpts[1][0]);
		Boolean defaultValue = null;
		Boolean fallbackValue = null;
		if (verifyValue(cpd.getDefaultValue())){
			defaultValue = Boolean.parseBoolean(cpd.getDefaultValue());
		}
		if (verifyValue(cpd.getFallbackValue())){
			fallbackValue = Boolean.parseBoolean(cpd.getFallbackValue());
		}
		
		BooleanComboPropertyDescription result = new BooleanComboPropertyDescription(cpd.getName(), cd.getLocalizedString(cpd.getLabel()), cd.getLocalizedString(cpd.getDescription()), cpd.isMandatory(), defaultValue, i18nOpts);
		result.setReadOnly(cpd.isReadOnly());
		result.setFallbackValue(fallbackValue);
		return result;
	}
}
