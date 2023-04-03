/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.descriptor.combo;

import org.eclipse.jface.viewers.LabelProvider;

import com.jaspersoft.studio.property.descriptor.NullEnum;
/*
 * @author Chicu Veaceslav
 * 
 */
public class RWComboBoxLabelProvider extends LabelProvider {
	private NullEnum canBeNull;

	public RWComboBoxLabelProvider(String[] labels, NullEnum canBeNull) {
		super();
		this.canBeNull = canBeNull;
	}

	@Override
	public String getText(Object element) {
		if (element == null || element.equals("")) //$NON-NLS-1$
			return canBeNull.getName();
		return element.toString();
	}

}
