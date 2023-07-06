/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.properties.view;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Represents a tab to be displayed in the tab list in the tabbed property sheet
 * page.
 * 
 * @author Anthony Hunter
 */
public interface ITabItem {

	/**
	 * Get the icon image for the tab.
	 * 
	 * @return the icon image for the tab.
	 */
	public ImageDescriptor getImage();

	/**
	 * Get the text label for the tab.
	 * 
	 * @return the text label for the tab.
	 */
	public String getText();

	/**
	 * Determine if this tab is selected.
	 * 
	 * @return <code>true</code> if this tab is selected.
	 */
	public boolean isSelected();

	/**
	 * Determine if this tab is indented.
	 * 
	 * @return <code>true</code> if this tab is indented.
	 */
	public boolean isIndented();
}
