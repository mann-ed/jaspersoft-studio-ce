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
import com.jaspersoft.studio.widgets.framework.ui.PixelPropertyDescription;
import com.jaspersoft.studio.widgets.framework.ui.SelectableComboItemPropertyDescription;
import com.jaspersoft.studio.widgets.framework.ui.TextPropertyDescription;

import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.components.map.MapComponent;
import net.sf.jasperreports.components.map.fill.CustomMapControlOrientationEnum;
import net.sf.jasperreports.components.map.fill.CustomMapControlPositionEnum;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;

/**
 * Configuration dialog to setup the "legend" details.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class LegendMapItemDialog extends ItemElementDialog {

	protected LegendMapItemDialog(Shell parentShell, Item itemElement) {
		super(parentShell, itemElement);
		setTitle(Messages.LegendMapItemDialog_Title);
		setDescription(Messages.LegendMapItemDialog_Description);
		setDefaultSize(UIUtils.getScaledWidth(450), UIUtils.getScaledHeight(500));
	}

	@Override
	protected List<ItemPropertyDescription<?>> initItemPropertiesDescriptions() {
		List<ItemPropertyDescription<?>> descriptions=new ArrayList<>();
		
		SelectableComboItemPropertyDescription<Boolean> legendEnabledDesc = 
				new SelectableComboItemPropertyDescription<Boolean>(
						MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_enabled, Messages.LegendMapItemDialog_PropertyEnabledLbl, Messages.LegendMapItemDialog_PropertyEnabledDesc, false, Boolean.FALSE, new String[] {"false","true"}); //$NON-NLS-3$ //$NON-NLS-4$
		legendEnabledDesc.setFallbackValue(Boolean.FALSE);		
		TextPropertyDescription<String> legendLabelDesc = 
				new TextPropertyDescription<String>(MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_label, Messages.LegendMapItemDialog_PropertyLabelLbl, Messages.LegendMapItemDialog_PropertyLabelDesc, true, Messages.LegendMapItemDialog_PropertyLabelDefaultValue);
		legendLabelDesc.setFallbackValue(Messages.LegendMapItemDialog_PropertyLabelDefaultValue);
		SelectableComboItemPropertyDescription<String> legendPositionDesc = 
				new SelectableComboItemPropertyDescription<String>(
						MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_position, Messages.LegendMapItemDialog_PropertyPositionLbl, Messages.LegendMapItemDialog_PropertyPositionDesc, false, 
						CustomMapControlPositionEnum.RIGHT_CENTER.getName(), EnumHelper.getEnumValues(CustomMapControlPositionEnum.class));
		legendPositionDesc.setFallbackValue(CustomMapControlPositionEnum.RIGHT_CENTER.getName());
		SelectableComboItemPropertyDescription<String> legendOrientationDesc = 
				new SelectableComboItemPropertyDescription<String>(
						MapComponent.LEGEND_PROPERTY_orientation, Messages.LegendMapItemDialog_PropertyOrientationLbl, Messages.LegendMapItemDialog_PropertyOrientationDesc, false, 
						CustomMapControlOrientationEnum.VERTICAL.getName(), EnumHelper.getEnumValues(CustomMapControlOrientationEnum.class));
		legendOrientationDesc.setFallbackValue(CustomMapControlOrientationEnum.VERTICAL.getName());
		PixelPropertyDescription legendMaxWidthDesc =
				new PixelPropertyDescription(MapComponent.LEGEND_PROPERTY_legendMaxWidth, Messages.LegendMapItemDialog_PropertyLegendMaxWidthLbl, Messages.LegendMapItemDialog_PropertyLegendMaxWidthDesc, false, "100", -1, -1); //$NON-NLS-3$
		legendMaxWidthDesc.setFallbackValue("100");
		PixelPropertyDescription legendMaxWidthFullScrDesc =
				new PixelPropertyDescription(MapComponent.LEGEND_PROPERTY_legendMaxWidth_fullscreen, Messages.LegendMapItemDialog_PropertyLegendMaxWidthFullLbl, Messages.LegendMapItemDialog_PropertyLegendMaxWidthFullDesc, false, "150", -1, -1); //$NON-NLS-3$
		legendMaxWidthFullScrDesc.setFallbackValue("150");
		PixelPropertyDescription legendMaxHeightDesc =
				new PixelPropertyDescription(MapComponent.LEGEND_PROPERTY_legendMaxHeight, Messages.LegendMapItemDialog_PropertyLegendMaxHeightLbl, Messages.LegendMapItemDialog_PropertyLegendMaxHeightDesc, false, "150", -1, -1); //$NON-NLS-3$
		legendMaxHeightDesc.setFallbackValue("150");
		PixelPropertyDescription legendMaxHeightFullScrDesc =
				new PixelPropertyDescription(MapComponent.LEGEND_PROPERTY_legendMaxHeight_fullscreen, Messages.LegendMapItemDialog_PropertyLegendMaxHeightFullLbl, Messages.LegendMapItemDialog_PropertyLegendMaxHeightFullDesc, false, "300", -1, -1); //$NON-NLS-3$
		legendMaxHeightFullScrDesc.setFallbackValue("300");
		SelectableComboItemPropertyDescription<Boolean> useMarkerIconsDesc = 
				new SelectableComboItemPropertyDescription<Boolean>(
						MapComponent.LEGEND_PROPERTY_useMarkerIcons, Messages.LegendMapItemDialog_PropertyUseMarkerIconsLbl, Messages.LegendMapItemDialog_PropertyUseMarkerIconsDesc, false, Boolean.TRUE, new String[] {"false","true"}); //$NON-NLS-3$ //$NON-NLS-4$
		useMarkerIconsDesc.setFallbackValue(Boolean.TRUE);
		
		descriptions.add(legendEnabledDesc);
		descriptions.add(legendLabelDesc);
		descriptions.add(legendPositionDesc);
		descriptions.add(legendOrientationDesc);
		descriptions.add(legendMaxWidthDesc);
		descriptions.add(legendMaxWidthFullScrDesc);
		descriptions.add(legendMaxHeightDesc);
		descriptions.add(legendMaxHeightFullScrDesc);
		descriptions.add(useMarkerIconsDesc);
		
		return descriptions;
	}

	
	@Override
	protected void createItemPropertiesWidgets() {
		createItemProperty(containerCmp,MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_enabled);
		createItemProperty(containerCmp,MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_label);
		createItemProperty(containerCmp,MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_position);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_orientation);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_legendMaxWidth);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_legendMaxWidth_fullscreen);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_legendMaxHeight);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_legendMaxHeight_fullscreen);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_useMarkerIcons);
	}

}
