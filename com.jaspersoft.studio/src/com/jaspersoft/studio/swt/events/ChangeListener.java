/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.swt.events;


/**
 * A  listener to listen to change events.
 * 
 * @author gtoffoli
 *
 */
public interface ChangeListener {

	/**
	 * The method invoked when a change occurs.
	 * 
	 * @param event
	 */
	public void changed(ChangeEvent event);
	
}
