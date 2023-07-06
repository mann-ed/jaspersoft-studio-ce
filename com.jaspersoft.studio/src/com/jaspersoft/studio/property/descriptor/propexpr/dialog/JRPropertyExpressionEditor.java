/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.descriptor.propexpr.dialog;

import org.eclipse.jface.wizard.Wizard;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionsDTO;

public class JRPropertyExpressionEditor extends Wizard {
	private PropertyExpressionsDTO value;
	private JRPropertyExpressionPage page0;
	private boolean showExpression = true;
	private boolean forceStandardEditing = false;

	public void setShowExpression(boolean showExpression) {
		this.showExpression = showExpression;
	}

	public PropertyExpressionsDTO getValue() {
		if (page0 != null)
			return page0.getValue();
		return value;
	}

	public void setValue(PropertyExpressionsDTO value) {
		if (page0 != null)
			page0.setValue(value);
		this.value = value;
	}

	public JRPropertyExpressionEditor() {
		this(false);
	}
	
	public JRPropertyExpressionEditor(boolean forceStandardEditing) {
		super();
		setWindowTitle(Messages.common_properties);
		setNeedsProgressMonitor(false);
		this.forceStandardEditing=forceStandardEditing;
	}

	@Override
	public void addPages() {
		page0 = new JRPropertyExpressionPage("jrproperties", forceStandardEditing); //$NON-NLS-1$
		page0.setValue(value);
		page0.setShowExpression(showExpression);
		addPage(page0);
	}

	@Override
	public boolean performFinish() {
		return true;
	}

}
