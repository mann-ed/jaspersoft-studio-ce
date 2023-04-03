/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.widgets.framework.ui;

/**
 * Extension of the {@link TextPropertyDescription}, the only difference is the empty
 * string is treated as and empty string and not null, like the original {@link TextPropertyDescription}
 * 
 * @author Orlandin Marco
 */
public class NotNullableTextPropertyDescription<T> extends TextPropertyDescription<T> {

	public NotNullableTextPropertyDescription() {
	}

	public NotNullableTextPropertyDescription(String name, String description, boolean mandatory) {
		this(name, name, description, mandatory, null);
	}

	public NotNullableTextPropertyDescription(String name, String label, String description, boolean mandatory) {
		this(name, label, description, mandatory, null);
	}

	public NotNullableTextPropertyDescription(String name, String label, String description, boolean mandatory, T defaultValue) {
		super(name, label, description, mandatory, defaultValue);
	}

	@Override
	protected String parseText(String widgetText) {
		return widgetText;
	}
	
}
