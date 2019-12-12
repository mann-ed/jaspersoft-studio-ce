/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.table.part.editpolicy;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import com.jaspersoft.studio.components.table.part.TableCellEditPart;
import com.jaspersoft.studio.components.table.part.TableNoDataEditPart;
import com.jaspersoft.studio.editor.gef.figures.ComponentFigure;
import com.jaspersoft.studio.editor.gef.parts.handles.CellResizeHandle2;
import com.jaspersoft.studio.editor.gef.util.GEFUtil;

/*
 * The Class TableCellResizableEditPolicy.
 */
public class TableNoDataResizableEditPolicy extends ResizableEditPolicy {

	public TableNoDataResizableEditPolicy() {
		super();
		setDragAllowed(true);
	}

	@Override
	public TableNoDataEditPart getHost() {
		return (TableNoDataEditPart) super.getHost();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ResizableEditPolicy#createSelectionHandles()
	 */
	@Override
	protected List<Handle> createSelectionHandles() {
		List<Handle> list = new ArrayList<>();

		GraphicalEditPart geditPart = getHost();
		list.add(new CellResizeHandle2(geditPart, PositionConstants.SOUTH)); 

		return list;
	}

	@Override
	protected Command getMoveCommand(ChangeBoundsRequest request) {
		return null;
	}

	@Override
	protected Command getResizeCommand(ChangeBoundsRequest request) {
		return CreateResizeNoData.createResizeCommand(request, getHost());
	}

	@Override
	protected IFigure createDragSourceFeedbackFigure() {
		RectangleFigure r = new FeedbackFigure();
		r.setOpaque(false);
		r.setAlpha(50);
		r.setBackgroundColor(ColorConstants.gray);
		r.setFill(false);
		r.setBorder(new LineBorder(ColorConstants.gray, 1));
		addFeedback(r);
		return r;
	}

	@Override
	protected void showChangeBoundsFeedback(ChangeBoundsRequest request) {
		Point moveDelta = request.getMoveDelta().getCopy();
		Dimension sizeDelta = request.getSizeDelta().getCopy();
		moveDelta.y = 0;
		getFeedbackLayer().translateToParent(moveDelta);
		int delta = moveDelta.x;
		if (request.getType().equals(REQ_MOVE)) {
			request.setMoveDelta(new Point(delta, 0));
			if (delta == 0)
				return;
		}
		if (request.getType().equals(REQ_RESIZE) && sizeDelta.width == 0 && sizeDelta.height == 0)
			return;
		PrecisionRectangle rdelta = new PrecisionRectangle(new Rectangle(moveDelta, sizeDelta));

		FeedbackFigure feedback = (FeedbackFigure) getDragSourceFeedbackFigure();
		IFigure hfig = getHostFigure();
		feedback.setRequest(request);
		if (request.getType().equals(REQ_MOVE)) {
//			double zoom = GEFUtil.getZoom(getHost());
//			movePlace.calcMovePlace((int) Math.floor(moveDelta.x / zoom), hfig.getBounds().getCopy());
//			if (movePlace.x1 != movePlace.xRef)
//				feedback.setInsertLine(movePlace.x1, movePlace.y1, movePlace.y2);
		}

		Rectangle rect = new PrecisionRectangle(getInitialFeedbackBounds().getCopy());
		Dimension contaierSize = getHost().getContaierSize();

		if (request.getType().equals(REQ_RESIZE) && request.getResizeDirection() == PositionConstants.SOUTH) {
			rect.x = TableCellEditPart.X_OFFSET;
			rect.width = contaierSize.width;
		} else if (request.getType().equals(REQ_RESIZE) && request.getResizeDirection() == PositionConstants.NORTH) {
			rect.x = TableCellEditPart.X_OFFSET;
			rdelta.y = -rdelta.height;
			rect.width = contaierSize.width;
		} else if (request.getType().equals(REQ_MOVE)) {
			rect.y = 0;
			rect.height = contaierSize.height + TableCellEditPart.Y_OFFSET * 2;
		} else {
			rect.y = TableCellEditPart.Y_OFFSET;
			rect.height = contaierSize.height + 1;
		}
		hfig.translateToAbsolute(rect);
		rect.translate(rdelta.x, rdelta.y);
		rect.resize(rdelta.width, rdelta.height);
		feedback.translateToRelative(rect);
		feedback.setBounds(rect);
		feedback.validate();
	}

	private final class FeedbackFigure extends RectangleFigure {
		private ChangeBoundsRequest request;

		public void setRequest(ChangeBoundsRequest request) {
			this.request = request;
		}

		private int x1, y1, y2;

		@Override
		public void paintFigure(Graphics g) {
			PrecisionRectangle b = new PrecisionRectangle(getBounds().getCopy());
			if (request.getType().equals(REQ_RESIZE)) {
				super.paintFigure(g);
				Graphics2D gr = ComponentFigure.getG2D(g);
				if (gr != null) {
					gr.fillRect(b.x, b.y, b.width, b.height);
					AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
					gr.setComposite(ac);
				}
			} else if (request.getType().equals(REQ_MOVE)) {
				double zoom = GEFUtil.getZoom(b, getHostFigure());

				Graphics2D gr = ComponentFigure.getG2D(g);
				if (gr != null) {
					gr.setColor(Color.gray);
					AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
					gr.setComposite(ac);

					int h = TableCellEditPart.Y_OFFSET;
					b.y = b.y + (int) Math.floor(h * zoom) - h - 3;
					int height = b.height - (int) Math.floor(h * zoom);

					gr.fillRect(b.x, b.y, b.width, height - b.y);

					// draw handle representation
					ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
					gr.setComposite(ac);
					gr.fillRect(b.x, b.y, b.width, h);
					gr.fillRect(b.x, height + 3, b.width, h);

					// move placer feedback
					gr.setStroke(new BasicStroke(2.0f));
					ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
					gr.setComposite(ac);
					gr.setColor(Color.red);

					PrecisionRectangle r = new PrecisionRectangle(new Rectangle(x1, y1, x1, y2));
					getHostFigure().translateToAbsolute(r);
					getFeedbackLayer().translateToRelative(r);
					gr.drawLine(r.x, r.y, r.x, r.height);
				}
			}
		}
	}
}
