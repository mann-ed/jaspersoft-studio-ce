/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.section.graphic.borders;

/**
 * Listener added to a border selection widget that is called when one 
 * of the border is selected
 * 
 * @author Orlandin Marco
 *
 */
public interface BorderSelectionListener {

	/**
	 * Method called when a border is selected
	 * 
	 * @param event the event that contains informations on the selection
	 */
	public void borderSelected(BorderSelectionEvent event);
	
}
