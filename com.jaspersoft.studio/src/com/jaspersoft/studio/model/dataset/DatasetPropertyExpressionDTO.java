/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.model.dataset;

import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionDTO;

import net.sf.jasperreports.engine.type.PropertyEvaluationTimeEnum;

public class DatasetPropertyExpressionDTO extends PropertyExpressionDTO {
	private PropertyEvaluationTimeEnum evalTime;

	public DatasetPropertyExpressionDTO(
			boolean isExpression, String name, String value, boolean isSimpleText, PropertyEvaluationTimeEnum evalTime) {
		super(isExpression, name, value, isSimpleText);
		this.evalTime = evalTime;
	}

	public PropertyEvaluationTimeEnum getEvalTime() {
		return evalTime;
	}

	public void setEvalTime(PropertyEvaluationTimeEnum evalTime) {
		this.evalTime = evalTime;
	}

	@Override
	public DatasetPropertyExpressionDTO clone() {
		DatasetPropertyExpressionDTO result = 
				new DatasetPropertyExpressionDTO(this.isExpression(), this.getName(), this.getValue(), this.isSimpleText(), evalTime);
		result.seteContext(geteContext());
		result.setJrElement(getJrElement());
		return result;
	}
}
