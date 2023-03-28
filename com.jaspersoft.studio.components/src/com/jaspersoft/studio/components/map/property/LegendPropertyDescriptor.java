/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.map.property;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.jaspersoft.studio.components.map.model.MMap;
import com.jaspersoft.studio.property.descriptor.text.NTextPropertyDescriptor;
import com.jaspersoft.studio.property.section.AbstractSection;
import com.jaspersoft.studio.property.section.widgets.ASPropertyWidget;

import net.sf.jasperreports.components.map.StandardMapComponent;

/**
 * Property descriptor for the "legend" feature of the {@link StandardMapComponent} element.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class LegendPropertyDescriptor extends NTextPropertyDescriptor {
	
	private MMap mapNode;

	public LegendPropertyDescriptor(Object id, String displayName, MMap mapNode) {
		super(id, displayName);
		this.mapNode = mapNode;
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new LegendCellEditor(parent);
	}
	
	@Override
	public ILabelProvider getLabelProvider() {
		return new LegendOrResetMapLabelProvider();
	}
	
	@Override
	public ASPropertyWidget<? extends IPropertyDescriptor> createWidget(Composite parent, AbstractSection section) {
		return new SPLegendMap(parent, section, this);
	}
	
}





