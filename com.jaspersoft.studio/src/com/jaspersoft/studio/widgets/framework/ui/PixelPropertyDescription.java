/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.widgets.framework.ui;

import java.util.HashMap;

import com.jaspersoft.studio.widgets.framework.IWItemProperty;

/**
 * Widget to use pixel measures.<br/>
 * We tricked the normal behavior of storing the property with measure unit information.</br>
 * We use the fallback behavior of defaulting to the pixels unit.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class PixelPropertyDescription extends FixedMeasurePropertyDescription {
	
	private static HashMap<String, String> PIXELS_DEF_MAP;
	
	static {
		 PIXELS_DEF_MAP = new HashMap<>(1);
		 PIXELS_DEF_MAP.put("px", "px"); //$NON-NLS-1$ //$NON-NLS-2$		 
	}
	
	@Override
	protected void setMeasureUnit(String measureUnitKey, String measureUnitName, IWItemProperty wItemProperty) {
		// Preventing the storing of measure units in the model: no additional property with _measureunit
		// The default behavior is using pixels.
	}
	
	public PixelPropertyDescription() {
	}
	
	public PixelPropertyDescription(String name, String label, String description, boolean mandatory, String defaultValue, long min, long max) {
		super(name, label, description, mandatory, defaultValue, min, max, PIXELS_DEF_MAP);
	}

	public PixelPropertyDescription(String name, String label, String description, boolean mandatory, long min, long max) {
		super(name, label, description, mandatory, min, max, PIXELS_DEF_MAP);
	}
	
	@Override
	public ItemPropertyDescription<String> clone() {
		PixelPropertyDescription result = new PixelPropertyDescription();
		result.defaultValue = defaultValue;
		result.description = description;
		result.jConfig = jConfig;
		result.label = label;
		result.mandatory = mandatory;
		result.name = name;
		result.readOnly = readOnly;
		result.min = min;
		result.max = max;
		result.nameKeyUnitsMap = PIXELS_DEF_MAP;
		result.autocompleteValues = autocompleteValues;
		result.fallbackValue = fallbackValue;
		return result;
	}
}
