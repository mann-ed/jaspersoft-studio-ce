/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.descriptor.expression;

import net.sf.jasperreports.engine.JRExpression;

import org.eclipse.jface.viewers.LabelProvider;

/*
 * @author Chicu Veaceslav
 */
public class JRExpressionLabelProvider extends LabelProvider {

	public JRExpressionLabelProvider() {
		super();
	}

	@Override
	public String getText(Object element) {
		if (element != null && element instanceof String)
			return (String) element;
		if (element != null && element instanceof JRExpression) {
			String text = ((JRExpression) element).getText();
			return text;
		}
		return ""; //$NON-NLS-1$
	}

}
