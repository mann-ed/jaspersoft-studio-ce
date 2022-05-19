/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.data.sql.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.data.designer.AQueryDesigner;
import com.jaspersoft.studio.data.sql.model.query.operand.FunctionOperand;

public class FunctionWidget extends AOperandWidget<FunctionOperand> {
	private Text txt;

	public FunctionWidget(Composite parent, FunctionOperand operand, AQueryDesigner designer) {
		super(parent, SWT.NONE, operand, designer);

	}

	@Override
	protected void createWidget(Composite parent) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 2;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		setLayout(layout);

		txt = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		txt.setText(getValue().toSQLString());
		txt.setToolTipText(getValue().toSQLString());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.minimumWidth = 250;
		txt.setLayoutData(gd);
	}

}
