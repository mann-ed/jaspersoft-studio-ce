/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.map.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.widgets.Shell;

import com.jaspersoft.studio.utils.EnumHelper;
import com.jaspersoft.studio.widgets.framework.ui.FixedMeasurePropertyDescription;
import com.jaspersoft.studio.widgets.framework.ui.ItemPropertyDescription;
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
		setTitle("Map Legend");
		setDescription("Configure the details related to the legend feature");
		setDefaultSize(UIUtils.getScaledWidth(500), UIUtils.getScaledHeight(600));
	}

	@Override
	protected List<ItemPropertyDescription<?>> initItemPropertiesDescriptions() {
		List<ItemPropertyDescription<?>> descriptions=new ArrayList<>();
		HashMap<String, String> pixelsDefMap = new HashMap<>();
		pixelsDefMap.put("px", "px");
		
		SelectableComboItemPropertyDescription<Boolean> legendEnabledDesc = 
				new SelectableComboItemPropertyDescription<Boolean>(
						MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_enabled, "Enabled", "Flag to decide if the legend is enabled or not", false, Boolean.FALSE, new String[] {"false","true"});
		TextPropertyDescription<String> legendLabelDesc = 
				new TextPropertyDescription<String>(MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_label, "Label", "Label for the map legend", true, "Legend");
		SelectableComboItemPropertyDescription<CustomMapControlPositionEnum> legendPositionDesc = 
				new SelectableComboItemPropertyDescription<CustomMapControlPositionEnum>(
						MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_position, "Position", "Specify the position of the map legend", false, 
						CustomMapControlPositionEnum.RIGHT_CENTER, EnumHelper.getEnumLabelsAndValues(CustomMapControlPositionEnum.class));
		SelectableComboItemPropertyDescription<CustomMapControlOrientationEnum> legendOrientationDesc = 
				new SelectableComboItemPropertyDescription<CustomMapControlOrientationEnum>(
						MapComponent.LEGEND_PROPERTY_orientation, "Orientation", "Specify the orientation of the map legend", false, 
						CustomMapControlOrientationEnum.VERTICAL, EnumHelper.getEnumLabelsAndValues(CustomMapControlOrientationEnum.class));
		FixedMeasurePropertyDescription legendMaxWidthDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_legendMaxWidth, "Legend Max Width", "Max width for the legend in pixels", false, "100", -1, -1, pixelsDefMap);
		FixedMeasurePropertyDescription legendMaxWidthFullScrDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_legendMaxWidth_fullscreen, "Legend Max Width (Fullscreen)", "Max width for the legend in pixels, when in full screen mode", false, "150", -1, -1, pixelsDefMap);
		FixedMeasurePropertyDescription seriesMaxWidthDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_seriesMaxWidth, "Series Max Width", "Max width for the series in pixels", false, "200", -1, -1, pixelsDefMap);
		FixedMeasurePropertyDescription seriesMaxWidthFullScrDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_seriesMaxWidth_fullscreen, "Series Max Width (Fullscreen)", "Max width for the series in pixels, when in full screen mode", false, "300", -1, -1, pixelsDefMap);
		FixedMeasurePropertyDescription seriesMaxHeightDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_seriesMaxHeight, "Series Max Height", "Max height for the series in pixels", false, "150", -1, -1, pixelsDefMap);
		
		FixedMeasurePropertyDescription seriesMaxHeightFullScrDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_seriesMaxHeight_fullscreen, "Series Max Height (Fullscreen)", "Max height for the series in pixels, when in full screen mode", false, "600", -1, -1, pixelsDefMap);
		SelectableComboItemPropertyDescription<Boolean> useMarkerIconsDesc = 
				new SelectableComboItemPropertyDescription<Boolean>(
						MapComponent.LEGEND_PROPERTY_useMarkerIcons, "User Marker Icons", "Flag to decide if marker icons are enabled", false, Boolean.TRUE, new String[] {"false","true"});
		
		descriptions.add(legendEnabledDesc);
		descriptions.add(legendLabelDesc);
		descriptions.add(legendPositionDesc);
		descriptions.add(legendOrientationDesc);
		descriptions.add(legendMaxWidthDesc);
		descriptions.add(legendMaxWidthFullScrDesc);
		descriptions.add(seriesMaxWidthDesc);
		descriptions.add(seriesMaxWidthFullScrDesc);
		descriptions.add(seriesMaxHeightDesc);
		descriptions.add(seriesMaxHeightFullScrDesc);
		descriptions.add(useMarkerIconsDesc);
		
		return descriptions;
	}

	
	@Override
	protected void createItemPropertiesWidgets() {
		createItemProperty(containerCmp,MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_enabled);
		createItemProperty(containerCmp,MapComponent.ITEM_PROPERTY_MARKER_label);
		createItemProperty(containerCmp,MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_position);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_orientation);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_legendMaxWidth);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_legendMaxWidth_fullscreen);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_seriesMaxWidth);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_seriesMaxWidth_fullscreen);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_seriesMaxHeight);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_seriesMaxHeight_fullscreen);
		createItemProperty(containerCmp, MapComponent.LEGEND_PROPERTY_useMarkerIcons);
	}

}
