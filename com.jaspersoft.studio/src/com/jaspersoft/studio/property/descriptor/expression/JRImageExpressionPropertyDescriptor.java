/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.descriptor.expression;

import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.property.section.AbstractSection;
import com.jaspersoft.studio.property.section.widgets.ASPropertyWidget;
import com.jaspersoft.studio.property.section.widgets.SPImageExpression;

public class JRImageExpressionPropertyDescriptor extends JRExpressionPropertyDescriptor {

	public JRImageExpressionPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}
	
	public ASPropertyWidget<?> createWidget(Composite parent, AbstractSection section) {
		expEditor = new SPImageExpression(parent, section, this);
		expEditor.setTraverseOnTab(true);
		expEditor.setExpressionContext(expContext);
		return expEditor;
	}
}
