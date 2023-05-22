/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.map.property;

import java.util.Optional;

import org.eclipse.jface.viewers.LabelProvider;

import com.jaspersoft.studio.components.map.messages.Messages;

import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.components.items.ItemProperty;
import net.sf.jasperreports.components.map.StandardMapComponent;

/**
 * Simple label provider used by both the "reset map" and "legend" features
 * of the {@link StandardMapComponent} element.<br/>
 * It uses the shared property "label".
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class LegendOrResetMapLabelProvider extends LabelProvider {
	
	public static String getLabelText(Item item) {
		Optional<ItemProperty> found = item.getProperties().stream().filter(
				i -> StandardMapComponent.LEGEND_OR_RESET_MAP_PROPERTY_label.equals(i.getName())).findFirst();
		if(found.isPresent()) {
			ItemProperty labelProperty = found.get();
			String txt = Messages.LegendOrResetMapLabelProvider_WarningInvalidLabel;
			if(labelProperty.getValue()!=null) {
				txt = labelProperty.getValue();
			}						
			else if(labelProperty.getValueExpression()!=null) {
				txt = labelProperty.getValueExpression().getText();
			}
			return txt; 
		}
		else {
			return Messages.LegendOrResetMapLabelProvider_DefaultLabelTxt;
		}
	}
	
	@Override
	public String getText(Object element) {
		if(element instanceof Item) {
			return LegendOrResetMapLabelProvider.getLabelText((Item) element);
		}
		return Messages.LegendOrResetMapLabelProvider_WarningNotSet;
	}	
}