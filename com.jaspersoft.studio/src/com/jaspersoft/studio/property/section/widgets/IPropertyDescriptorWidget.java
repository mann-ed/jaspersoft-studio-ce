/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.section.widgets;

import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.property.section.AbstractSection;

public interface IPropertyDescriptorWidget {

	/**
	 * Return widget that will be used in the section
	 * 
	 * @param parent
	 * @param section
	 * @return
	 */
	public ASPropertyWidget<?> createWidget(Composite parent, AbstractSection section);
}
