/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.metadata.xml.domain.impl;

import java.io.Serializable;

import net.sf.jasperreports.engine.JRConstants;

/**
 * 
 * @author gtoffoli
 */
public class InputControlQueryDataRow implements Serializable {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	private Object value = null;
	private java.util.List<String> columnValues = null;
	private boolean selected = false;

	/** Creates a new instance of InputControlQueryDataRow */
	public InputControlQueryDataRow() {
		columnValues = new java.util.ArrayList<>();
	}

	public java.util.List<String> getColumnValues() {
		return columnValues;
	}

	public void setColumnValues(java.util.List<String> columnValues) {
		this.columnValues = columnValues;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
}
