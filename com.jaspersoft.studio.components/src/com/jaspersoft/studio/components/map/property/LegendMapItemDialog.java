/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.map.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.widgets.Shell;

import com.jaspersoft.studio.components.map.messages.Messages;
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
		setTitle(Messages.LegendMapItemDialog_Title);
		setDescription(Messages.LegendMapItemDialog_Description);
		setDefaultSize(UIUtils.getScaledWidth(500), UIUtils.getScaledHeight(600));
	}

	@Override
	protected List<ItemPropertyDescription<?>> initItemPropertiesDescriptions() {
		List<ItemPropertyDescription<?>> descriptions=new ArrayList<>();
		HashMap<String, String> pixelsDefMap = new HashMap<>();
		pixelsDefMap.put("px", "px"); //$NON-NLS-1$ //$NON-NLS-2$
		
		SelectableComboItemPropertyDescription<Boolean> legendEnabledDesc = 
				new SelectableComboItemPropertyDescription<Boolean>(
						MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_enabled, Messages.LegendMapItemDialog_PropertyEnabledLbl, Messages.LegendMapItemDialog_PropertyEnabledDesc, false, Boolean.FALSE, new String[] {"false","true"}); //$NON-NLS-3$ //$NON-NLS-4$
		TextPropertyDescription<String> legendLabelDesc = 
				new TextPropertyDescription<String>(MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_label, Messages.LegendMapItemDialog_PropertyLabelLbl, Messages.LegendMapItemDialog_PropertyLabelDesc, true, Messages.LegendMapItemDialog_PropertyLabelDefaultValue);
		SelectableComboItemPropertyDescription<CustomMapControlPositionEnum> legendPositionDesc = 
				new SelectableComboItemPropertyDescription<CustomMapControlPositionEnum>(
						MapComponent.LEGEND_OR_RESET_MAP_PROPERTY_position, Messages.LegendMapItemDialog_PropertyPositionLbl, Messages.LegendMapItemDialog_PropertyPositionDesc, false, 
						CustomMapControlPositionEnum.RIGHT_CENTER, EnumHelper.getEnumLabelsAndValues(CustomMapControlPositionEnum.class));
		SelectableComboItemPropertyDescription<CustomMapControlOrientationEnum> legendOrientationDesc = 
				new SelectableComboItemPropertyDescription<CustomMapControlOrientationEnum>(
						MapComponent.LEGEND_PROPERTY_orientation, Messages.LegendMapItemDialog_PropertyOrientationLbl, Messages.LegendMapItemDialog_PropertyOrientationDesc, false, 
						CustomMapControlOrientationEnum.VERTICAL, EnumHelper.getEnumLabelsAndValues(CustomMapControlOrientationEnum.class));
		FixedMeasurePropertyDescription legendMaxWidthDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_legendMaxWidth, Messages.LegendMapItemDialog_PropertyLegendMaxWidthLbl, Messages.LegendMapItemDialog_PropertyLegendMaxWidthDesc, false, "100", -1, -1, pixelsDefMap); //$NON-NLS-3$
		FixedMeasurePropertyDescription legendMaxWidthFullScrDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_legendMaxWidth_fullscreen, Messages.LegendMapItemDialog_PropertyLegendMaxWidthFullLbl, Messages.LegendMapItemDialog_PropertyLegendMaxWidthFullDesc, false, "150", -1, -1, pixelsDefMap); //$NON-NLS-3$
		FixedMeasurePropertyDescription seriesMaxWidthDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_seriesMaxWidth, Messages.LegendMapItemDialog_PropertySeriesMaxWidthLbl, Messages.LegendMapItemDialog_PropertySeriesMaxWidthDesc, false, "200", -1, -1, pixelsDefMap); //$NON-NLS-3$
		FixedMeasurePropertyDescription seriesMaxWidthFullScrDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_seriesMaxWidth_fullscreen, Messages.LegendMapItemDialog_PropertySeriesMaxWidthFullLbl, Messages.LegendMapItemDialog_PropertySeriesMaxWidthFullDesc, false, "300", -1, -1, pixelsDefMap); //$NON-NLS-3$
		FixedMeasurePropertyDescription seriesMaxHeightDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_seriesMaxHeight, Messages.LegendMapItemDialog_PropertySeriesMaxHeightLbl, Messages.LegendMapItemDialog_PropertySeriesMaxHeightDesc, false, "150", -1, -1, pixelsDefMap); //$NON-NLS-3$
		
		FixedMeasurePropertyDescription seriesMaxHeightFullScrDesc = 
				new FixedMeasurePropertyDescription(MapComponent.LEGEND_PROPERTY_seriesMaxHeight_fullscreen, Messages.LegendMapItemDialog_PropertySeriesMaxHeightFullLbl, Messages.LegendMapItemDialog_PropertySeriesMaxHeightFullDesc, false, "600", -1, -1, pixelsDefMap); //$NON-NLS-3$
		SelectableComboItemPropertyDescription<Boolean> useMarkerIconsDesc = 
				new SelectableComboItemPropertyDescription<Boolean>(
						MapComponent.LEGEND_PROPERTY_useMarkerIcons, Messages.LegendMapItemDialog_PropertyUseMarkerIconsLbl, Messages.LegendMapItemDialog_PropertyUseMarkerIconsDesc, false, Boolean.TRUE, new String[] {"false","true"}); //$NON-NLS-3$ //$NON-NLS-4$
		
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
