/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.chart.model.theme.paintproviders;

import java.util.Collection;

import org.eclipse.jface.viewers.LabelProvider;

/*
 * @author Chicu Veaceslav
 * 
 */
public class PaintProvidersLabelProvider extends LabelProvider {

	public PaintProvidersLabelProvider() {
		super();
	}

	@Override
	public String getText(Object element) {
		if (element == null)
			return "";
		if (element instanceof Collection)
			return "[Size: " + ((Collection<?>) element).size() + "]";
		return element.toString();
	}

}
