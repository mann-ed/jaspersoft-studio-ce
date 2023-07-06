/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.widgets.framework.ui;

import java.util.Arrays;
import java.util.TimeZone;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;

import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.widgets.framework.IWItemProperty;
import com.jaspersoft.studio.widgets.framework.manager.DoubleControlComposite;
import com.jaspersoft.studio.widgets.framework.model.WidgetPropertyDescriptor;
import com.jaspersoft.studio.widgets.framework.model.WidgetsDescriptor;

/**
 * Widget that allows to edit the value for a timezone property. It uses a combo
 * to manage the value changes.
 *
 */
public class TimezoneComboPropertyDescription extends SelectableComboItemPropertyDescription<String> {

	private static String[][] tzs = null;

	public TimezoneComboPropertyDescription() {
		super();
	}

	public TimezoneComboPropertyDescription(String name, String label, String description, boolean mandatory,
			String defaultValue) {
		super(name, label, description, mandatory, defaultValue, getTimeZones());
	}

	@Override
	public void update(Control c, IWItemProperty wip) {
		DoubleControlComposite cmp = (DoubleControlComposite) wip.getControl();
		if (wip.isExpressionMode()) {
			super.update(c, wip);
		} else {
			boolean isFallback = false;
			Combo timezoneCombo = (Combo) cmp.getSecondContainer().getData();
			String v = wip.getStaticValue();
			if (v != null) {
				timezoneCombo.setText(v);
			} else if (wip.getFallbackValue() != null) {
				timezoneCombo.setText(String.valueOf(wip.getFallbackValue()));
				isFallback = true;
			} else {
				// The combo#deselectAll() method seems to not behave properly in Windows
				// when the combo box is read only.
				// Forcing the items (re)set, we properly show the empty text combo (no selection).				
				timezoneCombo.removeAll();
				timezoneCombo.setItems(convert2Value(tzs));
			}
			changeFallbackForeground(isFallback, timezoneCombo);
			cmp.switchToSecondContainer();
			timezoneCombo.setToolTipText(getToolTip(wip, timezoneCombo.getText()));
		}
	}

	@Override
	public TimezoneComboPropertyDescription clone() {
		TimezoneComboPropertyDescription result = new TimezoneComboPropertyDescription();
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

	/**
	 * Create the timezone values. Since the value for the combo are handled
	 * like a matrix with n*2 elements (or an array of pair) where the elements
	 * on the first column are the values and the one on the second are the
	 * labels
	 * 
	 * @return a not null n*2 matrix where the first column is the value and the
	 * second one the label associated to each value. In this case they are the
	 * same
	 */
	protected static String[][] getTimeZones() {
		if (tzs == null) {
			String[] tzones = TimeZone.getAvailableIDs();
			Arrays.sort(tzones);
			tzs = new String[tzones.length][2];
			for (int i = 0; i < tzs.length; i++) {
				tzs[i][0] = tzones[i];
				tzs[i][1] = tzones[i];
			}
		}
		return tzs;
	}

	@Override
	public ItemPropertyDescription<?> getInstance(WidgetsDescriptor cd, WidgetPropertyDescriptor cpd,
			JasperReportsConfiguration jConfig) {
		TimezoneComboPropertyDescription result = new TimezoneComboPropertyDescription(cpd.getName(),
				cd.getLocalizedString(cpd.getLabel()), cd.getLocalizedString(cpd.getDescription()), cpd.isMandatory(),
				defaultValue);
		result.setReadOnly(cpd.isReadOnly());
		result.setFallbackValue(fallbackValue);
		return result;
	}
}
