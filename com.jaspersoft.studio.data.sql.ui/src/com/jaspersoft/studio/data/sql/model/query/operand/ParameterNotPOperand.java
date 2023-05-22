/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.sql.model.query.operand;

import net.sf.jasperreports.engine.JRConstants;

import com.jaspersoft.studio.data.sql.model.query.expression.AMExpression;

public class ParameterNotPOperand extends ParameterPOperand {
	public ParameterNotPOperand(AMExpression<?> mexpr) {
		super(mexpr);
	}

	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	@Override
	public String toSQLString() {
		if (jrParameter != null)
			return "$P!{" + jrParameter.getName() + "}";
		return "$P!{}";
	}

}
