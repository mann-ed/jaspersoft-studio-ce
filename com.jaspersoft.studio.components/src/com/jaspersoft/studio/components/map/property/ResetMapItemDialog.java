/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.map.property;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;

import com.jaspersoft.studio.utils.EnumHelper;
import com.jaspersoft.studio.widgets.framework.ui.ItemPropertyDescription;
import com.jaspersoft.studio.widgets.framework.ui.SelectableComboItemPropertyDescription;
import com.jaspersoft.studio.widgets.framework.ui.TextPropertyDescription;

import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.components.map.MapComponent;
import net.sf.jasperreports.components.map.fill.CustomMapControlPositionEnum;

/**
 * Configuration dialog to setup the reset map details.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class ResetMapItemDialog extends ItemElementDialog {

	protected ResetMapItemDialog(Shell parentShell, Item itemElement) {
		super(parentShell, itemElement);
		setTitle("Reset Map");
		setDescription("Configure the details related to the reset map feature");
	}

	@Override
	protected List<ItemPropertyDescription<?>> initItemPropertiesDescriptions() {
		List<ItemPropertyDescription<?>> descriptions=new ArrayList<>();
		
		SelectableComboItemPropertyDescription<Boolean> legendEnabledDesc = 
				new SelectableComboItemPropertyDescription<Boolean>(
						MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_enabled, "Enabled", "Flag to decide is the reset button is enabled or not", false, Boolean.FALSE, new String[] {"false","true"});
		
		TextPropertyDescription<String> legendLabelDesc = 
				new TextPropertyDescription<String>(MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_label, "Label", "Label for the reset button", true, "Reset map");
		
		SelectableComboItemPropertyDescription<CustomMapControlPositionEnum> legendPositionDesc = 
				new SelectableComboItemPropertyDescription<CustomMapControlPositionEnum>(
						MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_position, "Position", "Specify the position of the reset button", false, 
						CustomMapControlPositionEnum.RIGHT_TOP, EnumHelper.getEnumLabelsAndValues(CustomMapControlPositionEnum.class));

		descriptions.add(legendEnabledDesc);
		descriptions.add(legendLabelDesc);
		descriptions.add(legendPositionDesc);
		
		return descriptions;
	}

	
	@Override
	protected void createItemPropertiesWidgets() {
		createItemProperty(containerCmp,MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_enabled);
		createItemProperty(containerCmp,MapComponent.ITEM_PROPERTY_MARKER_label);
		createItemProperty(containerCmp,MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_position);
	}

	
}
