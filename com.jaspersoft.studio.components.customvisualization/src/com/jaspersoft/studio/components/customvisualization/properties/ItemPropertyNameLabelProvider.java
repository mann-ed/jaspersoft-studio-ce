/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.customvisualization.properties;
 

import net.sf.jasperreports.components.items.ItemProperty;

import org.eclipse.jface.viewers.ColumnLabelProvider;

/**
 * Label provider for the column name of a table containing a list of
 * {@link CVItemProperty}.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class ItemPropertyNameLabelProvider extends ColumnLabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof ItemProperty) {
			return ((ItemProperty) element).getName();
		}
		return super.getText(element);
	}

}
