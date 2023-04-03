/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property;

import org.eclipse.jface.viewers.LabelProvider;
/*
 * The Class PropertyLabelProvider.
 */
public class PropertyLabelProvider extends LabelProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element != null)
			return element.toString();
		return "NULL"; //$NON-NLS-1$
	}

}
