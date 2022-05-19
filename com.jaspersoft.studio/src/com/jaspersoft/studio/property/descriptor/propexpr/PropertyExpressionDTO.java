/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.property.descriptor.propexpr;

import com.jaspersoft.studio.property.descriptor.properties.dialog.PropertyDTO;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.type.ExpressionTypeEnum;

/**
 * Container for the definition of both a Standard property or an expression property
 * 
 * @author Orlandin Marco
 */
public class PropertyExpressionDTO extends PropertyDTO {

	/**
	 * Flag to know if the property is an expression property or a standard property
	 */
	private boolean isExpression;
	
	/* Flag to modify the type attribute for the propertyExpression element */
	private boolean isSimpleText;

	/**
	 * Create a container for a standard property definition or a expression property definition
	 * 
	 * @param isExpression
	 *          true if it is an expression property, false otherwise
	 * @param name
	 *          The name of the property, must be not null
	 * @param value
	 *          The property value as string
	 * @param isSimpleText
	 * 			The type attribute value that is meaningful only for a propertyExpression
	 */
	public PropertyExpressionDTO(boolean isExpression, String name, String value, boolean isSimpleText) {
		super(name, value);
		this.isExpression = isExpression;
		this.isSimpleText = isSimpleText; 
	}

	/**
	 * Used to know if the property is a standard one or an expression property.
	 * 
	 * @return false if the property is standard, true if it is an expression property
	 */
	@Override
	public boolean isExpression() {
		return isExpression;
	}

	/**
	 * Set if the current property is a standard property or an expression property
	 * 
	 * @param isExpression
	 *          false if the property is standard, true if it is an expression property
	 */
	public void setExpression(boolean isExpression) {
		this.isExpression = isExpression;
	}
	
	/**
	 * Returns the status of the attribute <code>type</code> for a possible <code>propertyExpression</code>.
	 * 
	 * @return <code>true</code> if <i>simpleText</i> is set, <code>false</code> otherwise
	 */
	public boolean isSimpleText() {
		if(isExpression()) {
			return isSimpleText;			
		}
		else { 
			// default use case when a simple (textual) property is used
			return false;
		}
	}

	/**
	 * Sets the status of the attribute <code>type</code> for a possible <code>propertyExpression</code>.
	 * <p>
	 * If the property is a simple one and not an expression, then no operation is performed.
	 * 
	 * @param isSimpleText
	 */
	public void setSimpleText(boolean isSimpleText) {
		if(isExpression()) {
			this.isSimpleText = isSimpleText;			
		}
		else {
			// default behavior, "cleaning" a possible existing value
			this.isSimpleText = false;
		}
	}
	
	@Override
	public JRExpression getValueAsExpression() {
		JRDesignExpression result = new JRDesignExpression(getValue());
		result.setType(isSimpleText() ? ExpressionTypeEnum.SIMPLE_TEXT : null);
		return result;
	}
	
	/**
	 * Clone the current property and return a copy of it
	 * 
	 * @return a not null copy of the current property
	 */
	@Override
	public PropertyExpressionDTO clone() {
		String v = this.getValue();
		PropertyExpressionDTO result = new PropertyExpressionDTO(
						this.isExpression(), new String(this.getName()), 
						v == null ? v : new String(v), this.isSimpleText());
		result.seteContext(geteContext());
		result.setJrElement(getJrElement());
		return result;
	}

	@Override
	public String toString() {
		return getValue();
	}
}
