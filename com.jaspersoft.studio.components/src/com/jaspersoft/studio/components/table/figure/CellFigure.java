/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.table.figure;

import org.eclipse.draw2d.LineBorder;

import com.jaspersoft.studio.editor.gef.figures.FrameFigure;
import com.jaspersoft.studio.jasper.JSSDrawVisitor;

import net.sf.jasperreports.components.table.DesignBaseCell;
import net.sf.jasperreports.components.table.StandardBaseColumn;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRLineBox;

public class CellFigure extends FrameFigure {
	protected DesignBaseCell cell;
	private StandardBaseColumn column;

	public CellFigure() {
		super();
		setBorder(new LineBorder(1));
	}

	public void setJRElement(DesignBaseCell cell, StandardBaseColumn column, JSSDrawVisitor drawVisitor) {
		this.cell = cell;
		this.column = column;
		super.setJRElement(null, drawVisitor);
		setSize(getElementWidth() + 3, getElementHeight() + 3);
	}

	@Override
	protected JRLineBox getLineBox() {
		JRLineBox box = null;
		if (cell != null) {
			box = cell.getLineBox();
			if (box == null && cell.getStyle() != null)
				box = cell.getStyle().getLineBox();
		}
		return box;
	}

	@Override
	protected int getElementHeight() {
		return cell.getHeight();
	}

	@Override
	protected int getElementWidth() {
		return column.getWidth();
	}

	@Override
	protected void draw(JSSDrawVisitor drawVisitor, JRElement jrElement) {

	}
}
