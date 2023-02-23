/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.model;

import org.eclipse.draw2d.geometry.Dimension;

public interface IGraphicElementContainer {
	
	public Integer getTopPadding();

	public Integer getBottomPadding();
	
	public Integer getLeftPadding();
	
	public Integer getRightPadding();
	
	public Integer getPadding();
	
	public Dimension getSize();
}
