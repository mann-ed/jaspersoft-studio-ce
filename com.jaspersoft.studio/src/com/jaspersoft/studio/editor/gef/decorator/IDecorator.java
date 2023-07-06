/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.gef.decorator;

import org.eclipse.draw2d.Graphics;

import com.jaspersoft.studio.editor.gef.figures.ComponentFigure;

public interface IDecorator {
	public void paint(Graphics graphics, ComponentFigure fig);
}
