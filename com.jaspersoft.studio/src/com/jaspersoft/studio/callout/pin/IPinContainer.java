/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.callout.pin;

import net.sf.jasperreports.engine.JRPropertiesHolder;

/**
 * Interface that a node must implement to specify that on it the property
 * of a callout can be stored
 * 
 * @author Orlandin Marco
 */
public interface IPinContainer {
	
	/**
	 * Get the properties holder of the current element
	 * 
	 * @return a not null array of properties holder.
	 */
	public JRPropertiesHolder[] getPropertyHolder();
	
}
