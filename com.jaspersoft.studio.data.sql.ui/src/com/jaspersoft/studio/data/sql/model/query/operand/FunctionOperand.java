/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.data.sql.model.query.operand;

import java.util.List;

import com.jaspersoft.studio.data.sql.model.query.expression.AMExpression;

import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.engine.JRConstants;

public class FunctionOperand extends AOperand {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	private String function;
	private List<AOperand> arguments;
	private boolean star = false;
	private String analytical;

	public FunctionOperand(AMExpression<?> mexpr, String function, List<AOperand> arguments) {
		super(mexpr);
		this.function = function;
		this.arguments = arguments;
	}

	public FunctionOperand(AMExpression<?> mexpr, String function, boolean star) {
		super(mexpr);
		this.function = function;
		this.star = star;
	}

	public FunctionOperand(AMExpression<?> mexpr, String function, String analytical) {
		super(mexpr);
		this.function = function;
		this.analytical = analytical;
	}

	@Override
	public String toSQLString() {
		StringBuffer ss = new StringBuffer();
		ss.append(function);
		if (star)
			ss.append("*");
		else if (!Misc.isNullOrEmpty(arguments)) {
			String del = "";
			for (AOperand op : arguments) {
				ss.append(del);
				ss.append(op.toSQLString());
				del = ",";
			}
		} else {
			ss.append("OVER " + analytical);
		}
		ss.append(")");
		return ss.toString();
	}

	@Override
	public String toXString() {
		return toSQLString();
	}

}
