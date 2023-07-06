/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.sql.widgets.scalar;

import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.data.designer.AQueryDesigner;
import com.jaspersoft.studio.data.sql.model.query.operand.ScalarOperand;
import com.jaspersoft.studio.data.sql.widgets.AOperandWidget;

public abstract class AScalarWidget extends AOperandWidget<ScalarOperand<?>> {

	public AScalarWidget(Composite parent, int style, ScalarOperand<?> operand, AQueryDesigner designer) {
		super(parent, style, operand, designer);
	}

}
