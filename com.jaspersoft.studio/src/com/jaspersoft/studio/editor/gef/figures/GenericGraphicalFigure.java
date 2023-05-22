/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.gef.figures;

import com.jaspersoft.studio.model.MGraphicElement;

/**
 * Generic figure used for the graphical elements, implementing the interface IModelFigure
 * allow the hiding and paint caching of the elements
 * 
 * @author Orlandin Marco
 *
 */
public class GenericGraphicalFigure extends org.eclipse.draw2d.RectangleFigure implements IModelFigure{

	/**
	 * The model of the element
	 */
	private MGraphicElement model;
	
	/**
	 * Create the figure
	 *  
	 * @param model the model of the element
	 */
	public GenericGraphicalFigure(MGraphicElement model){
		this.model = model;
	}
	
	/**
	 * Return the model of the element
	 */
	@Override
	public MGraphicElement getModel() {
		return model;
	}
	
}
