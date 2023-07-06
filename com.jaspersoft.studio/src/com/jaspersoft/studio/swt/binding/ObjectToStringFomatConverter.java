/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.swt.binding;

import java.text.Format;

import org.eclipse.core.databinding.conversion.Converter;

public class ObjectToStringFomatConverter extends Converter {
	private Format formater;

	public ObjectToStringFomatConverter(Object toType, Format formater) {
		super(toType, String.class);
		this.formater = formater;
	}

	@Override
	public Object convert(Object fromObject) {
		if (fromObject == null)
			return "";
		return formater.format(fromObject);
	}

}
