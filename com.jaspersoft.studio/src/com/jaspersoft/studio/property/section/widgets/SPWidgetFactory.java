/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.section.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.jaspersoft.studio.property.section.AbstractSection;

public class SPWidgetFactory {
	public static ASPropertyWidget<?> createWidget(Composite parent, AbstractSection section, IPropertyDescriptor pd) {
		if (pd instanceof IPropertyDescriptorWidget)
			return ((IPropertyDescriptorWidget) pd).createWidget(parent, section);

		return null;
	}
}
