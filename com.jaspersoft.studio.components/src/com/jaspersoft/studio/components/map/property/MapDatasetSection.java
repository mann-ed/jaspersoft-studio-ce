/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.map.property;

import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.components.map.messages.Messages;
import com.jaspersoft.studio.properties.view.TabbedPropertySheetPage;
import com.jaspersoft.studio.property.section.AbstractSection;

import net.sf.jasperreports.components.map.StandardMapComponent;

public class MapDatasetSection extends AbstractSection {

	@Override
	public void createControls(final Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		createWidget4Property(parent, StandardMapComponent.PROPERTY_MARKER_CLUSTERING, false);
		createWidget4Property(parent, StandardMapComponent.PROPERTY_MARKER_SPIDERING, false);
		createWidget4Property(parent, StandardMapComponent.PROPERTY_MARKER_DATA_LIST, false);
	}
	
	@Override
	protected void initializeProvidedProperties() {
		super.initializeProvidedProperties();
		addProvidedProperties(StandardMapComponent.PROPERTY_MARKER_CLUSTERING, Messages.MMap_MarkerClustering);
		addProvidedProperties(StandardMapComponent.PROPERTY_MARKER_SPIDERING, Messages.MMap_MarkerSpidering);
	}
}
