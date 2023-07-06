/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

import com.jaspersoft.studio.properties.view.ITabDescriptor;
import com.jaspersoft.studio.properties.view.ITabDescriptorProvider;
/*
 * The Class PropertyTabDescriptorProvider.
 */
public class PropertyTabDescriptorProvider implements ITabDescriptorProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ITabDescriptorProvider#getTabDescriptors(org.eclipse.ui.IWorkbenchPart,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	public ITabDescriptor[] getTabDescriptors(IWorkbenchPart part, ISelection selection) {
		return new ITabDescriptor[] {};
	}
}
