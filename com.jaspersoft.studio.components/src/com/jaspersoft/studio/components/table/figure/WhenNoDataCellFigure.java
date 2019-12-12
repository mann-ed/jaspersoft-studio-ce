/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.table.figure;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;

import com.jaspersoft.studio.editor.gef.figures.APageFigure;
import com.jaspersoft.studio.editor.gef.figures.ComponentFigure;
import com.jaspersoft.studio.editor.gef.figures.GridPainter;
import com.jaspersoft.studio.editor.gef.util.FigureTextWriter;
import com.jaspersoft.studio.jasper.JSSDrawVisitor;

import net.sf.jasperreports.components.table.DesignBaseCell;
import net.sf.jasperreports.engine.design.JRDesignElement;

public class WhenNoDataCellFigure extends CellFigure {
	private static final String HINT = "If the table will not contain any data, this cell will be printed instead.";
	private FigureTextWriter twriter = new FigureTextWriter();
	private Rectangle2D hintBounds;

	public WhenNoDataCellFigure() {
		super();
		twriter.setText("When No Data Cell");
	}


	@Override
	public void paint(Graphics graphics) {
		Rectangle b = getHandleBounds();

		Graphics2D g = ComponentFigure.getG2D(graphics);
		if (g != null) {
			
			Font currfont = g.getFont();
			g.setFont(currfont.deriveFont(16f));
			if (hintBounds == null) {
				hintBounds = g.getFontMetrics().getStringBounds(HINT, g);
			}
			
			int width = Math.max(b.width , (int) hintBounds.getWidth()) + APageFigure.PAGE_BORDER.left;
			java.awt.Rectangle oldClip = g.getClipBounds();
			g.setClip(oldClip.x, oldClip.y, width, oldClip.height);
			
			graphics.setBackgroundColor(ColorConstants.white);
			graphics.fillRectangle(b.x, b.y, b.width, b.height);

			super.paint(graphics);
			
			twriter.painText(g, this);


			java.awt.Color currColor = g.getColor();
			g.setColor(java.awt.Color.GRAY);

			g.drawString(HINT, b.x, b.y - 15);
			g.setColor(currColor);
			g.setFont(currfont);
			
			if (getParent() instanceof APageFigure) {
				GridPainter grid = ((APageFigure) getParent()).getGrid();
				if (grid.isVisible()) {
					grid.setBounds(b);
					grid.paintGrid(graphics, this);
				}
			}
			if (getBorder() != null) {
				getBorder().paint(this, graphics, APageFigure.PAGE_BORDER);
			}
			g.setClip(oldClip.x, oldClip.y, oldClip.width, oldClip.height);
		}
	}

	@Override
	public Rectangle getHandleBounds() {
		Rectangle r = super.getHandleBounds();
		r.x += APageFigure.PAGE_BORDER.left;
		r.y += 30;
		return r;
	}

	/**
	 * Enables/disables the showing of the band name in background.
	 * 
	 * @param showBandName flag for band name showing.
	 */
	public void setShowBandName(boolean showBandName) {
		twriter.setShowName(showBandName);
	}

	/**
	 * Sets a human-readable text that will be painted in the band background.
	 * Usually it is the band name.
	 * <p>
	 * 
	 * <b>NOTE</b>: the text will be drawn only if the related property <i>"Show
	 * Band names"</i> from the preference page <i>Jaspersoft Studio-&gt;Report
	 * Designer</i> is enabled.
	 * 
	 * @param bandText the band text
	 */
	public void setBandText(String bandText) {
		twriter.setText(bandText);
	}

	@Override
	protected void paintBorder(Graphics graphics) {
		// no border
	}

	private JRDesignElement tbl;

	public void setJRElement(DesignBaseCell cell, JRDesignElement tbl, JSSDrawVisitor drawVisitor) {
		this.cell = cell;
		this.tbl = tbl;
		super.setJRElement(tbl, drawVisitor);
		setSize(getElementWidth() + 3, getElementHeight() + 3);
	}

	@Override
	protected int getElementWidth() {
		return tbl.getWidth();
	}

}
