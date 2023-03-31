/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.map.property;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;

import com.jaspersoft.studio.components.map.messages.Messages;
import com.jaspersoft.studio.utils.EnumHelper;
import com.jaspersoft.studio.widgets.framework.ui.ItemPropertyDescription;
import com.jaspersoft.studio.widgets.framework.ui.SelectableComboItemPropertyDescription;
import com.jaspersoft.studio.widgets.framework.ui.TextPropertyDescription;

import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.components.map.MapComponent;
import net.sf.jasperreports.components.map.fill.CustomMapControlPositionEnum;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;

/**
 * Configuration dialog to setup the reset map details.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class ResetMapItemDialog extends ItemElementDialog {

	protected ResetMapItemDialog(Shell parentShell, Item itemElement) {
		super(parentShell, itemElement);
		setTitle(Messages.ResetMapItemDialog_Title);
		setDescription(Messages.ResetMapItemDialog_Description);
		setDefaultSize(UIUtils.getScaledWidth(450), UIUtils.getScaledHeight(300));
	}

	@Override
	protected List<ItemPropertyDescription<?>> initItemPropertiesDescriptions() {
		List<ItemPropertyDescription<?>> descriptions=new ArrayList<>();
		
		SelectableComboItemPropertyDescription<Boolean> resetMapEnabledDesc = 
				new SelectableComboItemPropertyDescription<Boolean>(
						MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_enabled, Messages.ResetMapItemDialog_PropertyEnabledLbl, Messages.ResetMapItemDialog_PropertyEnabledDesc, false, Boolean.FALSE, new String[] {"false","true"}); //$NON-NLS-3$ //$NON-NLS-4$
		resetMapEnabledDesc.setFallbackValue(Boolean.FALSE);
		
		TextPropertyDescription<String> resetMapLabelDesc = 
				new TextPropertyDescription<String>(MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_label, Messages.ResetMapItemDialog_PropertyLabelLbl, Messages.ResetMapItemDialog_PropertyLabelDesc, true, Messages.ResetMapItemDialog_PropertyLabelDefaultValue);
		resetMapLabelDesc.setFallbackValue(Messages.ResetMapItemDialog_PropertyLabelDefaultValue);
		
		SelectableComboItemPropertyDescription<String> resetMapPositionDesc = 
				new SelectableComboItemPropertyDescription<String>(
						MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_position, Messages.ResetMapItemDialog_PropertyPositionLbl, Messages.ResetMapItemDialog_PropertyPositionDesc, false, 
						CustomMapControlPositionEnum.RIGHT_TOP.getName(), EnumHelper.getEnumValues(CustomMapControlPositionEnum.class));
		resetMapPositionDesc.setFallbackValue(CustomMapControlPositionEnum.RIGHT_TOP.getName());

		descriptions.add(resetMapEnabledDesc);
		descriptions.add(resetMapLabelDesc);
		descriptions.add(resetMapPositionDesc);
		
		return descriptions;
	}

	
	@Override
	protected void createItemPropertiesWidgets() {
		createItemProperty(containerCmp,MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_enabled);
		createItemProperty(containerCmp,MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_label);
		createItemProperty(containerCmp,MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_position);
	}

	
}
