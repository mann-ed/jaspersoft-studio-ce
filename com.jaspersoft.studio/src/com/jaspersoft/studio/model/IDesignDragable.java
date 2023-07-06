/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.model;

import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Identify an element that can be drag and dropped inside the deisgn editor
 * 
 * @author Orlandin Marco
 *
 */
public interface IDesignDragable {

	public Rectangle getJRBounds();
	
}
