/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.map.property;

import com.jaspersoft.studio.widgets.framework.IPropertyEditor;

import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.components.items.ItemProperty;
import net.sf.jasperreports.components.items.StandardItem;
import net.sf.jasperreports.components.items.StandardItemProperty;
import net.sf.jasperreports.engine.JRExpression;

/**
 * Property editor for {@link Item} elements.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class ItemElementPropertyEditor implements IPropertyEditor {
	
	private Item item;

	public ItemElementPropertyEditor(Item item) {
		this.item=item;
	}
	
	@Override
	public String getPropertyValue(String propertyName) {
		for (ItemProperty prop : item.getProperties()) {
			if (prop.getName().equals(propertyName)) {
				StandardItemProperty stdProp = (StandardItemProperty) prop;
				return stdProp.getValue();
			}
		}
		return null;
	}

	@Override
	public JRExpression getPropertyValueExpression(String propertyName) {
		for (ItemProperty prop : item.getProperties()) {
			if (prop.getName().equals(propertyName)) {
				StandardItemProperty stdProp = (StandardItemProperty) prop;
				return stdProp.getValueExpression();
			}
		}
		return null;
	}

	@Override
	public void createUpdateProperty(String propertyName, String value, JRExpression valueExpression) {
		boolean found = false;
		for (ItemProperty prop : item.getProperties()) {
			if (prop.getName().equals(propertyName)) {
				StandardItemProperty stdProp = (StandardItemProperty) prop;
				stdProp.setValue(value);
				stdProp.setValueExpression(valueExpression);
				found = true;
				break;
			}
		}
		if (!found) {
			((StandardItem) item).addItemProperty(new StandardItemProperty(propertyName, value, valueExpression));
		}
	}

	@Override
	public void removeProperty(String propertyName) {
		for (ItemProperty prop : item.getProperties()) {
			if (prop.getName().equals(propertyName)) {
				((StandardItem) item).removeItemProperty(prop);
				break;
			}
		}
	}

}
