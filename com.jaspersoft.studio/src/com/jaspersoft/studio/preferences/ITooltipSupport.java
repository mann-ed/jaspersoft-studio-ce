/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.preferences;

/**
 * Implementors will add support for a tooltip text to show.
 * 
 * @author Massimo Rabbi
 *
 */
public interface ITooltipSupport {

	/**
	 * Sets the tooltip text to show.
	 * 
	 * @param text the tooltip text
	 */
	void setTooltipText(String text);
	
}
