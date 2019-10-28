/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.table.part.editpolicy;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import com.jaspersoft.studio.components.table.model.MTable;
import com.jaspersoft.studio.components.table.model.nodata.MTableNoData;
import com.jaspersoft.studio.components.table.part.TableNoDataEditPart;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.property.SetValueCommand;

import net.sf.jasperreports.components.table.DesignBaseCell;

public class CreateResizeNoData {

	public static Command createResizeCommand(ChangeBoundsRequest request, TableNoDataEditPart editPart) {
		MTableNoData model = editPart.getModel();
		MTableNoData oldmodel = model;
		Dimension sd = request.getSizeDelta();
		if (model == null)
			return null;
		PrecisionRectangle deltaRect = new PrecisionRectangle(new Rectangle(0, 0, sd.width, sd.height));
		editPart.getFigure().translateToRelative(deltaRect);
		MTable table = getTableModel(model);
		JSSCompoundTableCommand c = new JSSCompoundTableCommand(table);
		c.setLayoutTableContent(true);

		if (request.getSizeDelta().height != 0) {
			MTableNoData mc = model;
			int h = deltaRect.height;
			if (request.getResizeDirection() == PositionConstants.NORTH)
				h = -h;
			int height = (Integer) mc.getPropertyValue(DesignBaseCell.PROPERTY_HEIGHT) + h;
			if (height < 0)
				return null;

			SetValueCommand setCommand = new SetValueCommand();
			setCommand.setTarget(model);
			setCommand.setPropertyId(DesignBaseCell.PROPERTY_HEIGHT);
			setCommand.setPropertyValue(height);
			c.add(setCommand);

			if (request.getResizeDirection() == PositionConstants.NORTH) {
				mc = oldmodel;
				h = deltaRect.height;
				height = (Integer) mc.getPropertyValue(DesignBaseCell.PROPERTY_HEIGHT) + h;
				if (height < 0)
					height = 0;

				setCommand = new SetValueCommand();
				setCommand.setTarget(oldmodel);
				setCommand.setPropertyId(DesignBaseCell.PROPERTY_HEIGHT);
				setCommand.setPropertyValue(height);
				c.add(setCommand);
			}
		}
		if (c.isEmpty())
			return null;
		return c;
	}

	/**
	 * Search starting from a node and going up in the hierarchy an MTable
	 * 
	 * @param currentNode a node, should be a node inside a table
	 * @return an MTable if it is in the upper hierarchy of the current node or null
	 */
	private static MTable getTableModel(ANode node) {
		if (node == null)
			return null;
		if (node instanceof MTable) {
			return (MTable) node;
		} else {
			return getTableModel(node.getParent());
		}
	}
}
