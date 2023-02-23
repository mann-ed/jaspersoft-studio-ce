/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.section.obj;

import net.sf.jasperreports.engine.design.JRDesignVariable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.properties.view.TabbedPropertySheetPage;
import com.jaspersoft.studio.property.section.AbstractSection;

public class VariableSystemSection extends AbstractSection {
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		parent.setLayout(new GridLayout(3, false));
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false,2,1);
		createWidget4Property(parent, JRDesignVariable.PROPERTY_NAME).getControl().setLayoutData(gd);
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		createWidget4Property(parent, JRDesignVariable.PROPERTY_VALUE_CLASS_NAME).getControl().setLayoutData(gd);
	}
	
	@Override
	protected void initializeProvidedProperties() {
		super.initializeProvidedProperties();
		addProvidedProperties(JRDesignVariable.PROPERTY_NAME, Messages.common_name);
		addProvidedProperties(JRDesignVariable.PROPERTY_VALUE_CLASS_NAME, Messages.common_value_class_name);
	}
}
