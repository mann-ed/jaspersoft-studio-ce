/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.jface;

import org.eclipse.jface.viewers.ICellEditorValidator;

import com.jaspersoft.studio.messages.Messages;
/*
 * The Class FloatCellEditorValidator.
 * 
 * @author Chicu Veaceslav
 */
public class FloatCellEditorValidator implements ICellEditorValidator {

	/** The instance. */
	private static FloatCellEditorValidator instance;

	/**
	 * Instance.
	 * 
	 * @return the float cell editor validator
	 */
	public static FloatCellEditorValidator instance() {
		if (instance == null)
			instance = new FloatCellEditorValidator();
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ICellEditorValidator#isValid(java.lang.Object)
	 */
	public String isValid(Object value) {
		try {
			if (value instanceof Float)
				return null;
			if (value instanceof String)
				new Float((String) value);
			return null;
		} catch (NumberFormatException exc) {
			return Messages.FloatCellEditorValidator_this_is_not_a_float_number; 
		}
	}

}
