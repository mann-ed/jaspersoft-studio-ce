/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.table.figure;

import com.jaspersoft.studio.components.table.model.MTable;
import com.jaspersoft.studio.editor.gef.figures.JRComponentFigure;

public class TableFigure extends JRComponentFigure {
	
	/**
	 * Instantiates a new text field figure.
	 */
	public TableFigure(MTable tableModel) {
		super(tableModel);
	}
	
	@Override
	protected boolean allowsFigureDrawCache() {
		return true;
	}
}
