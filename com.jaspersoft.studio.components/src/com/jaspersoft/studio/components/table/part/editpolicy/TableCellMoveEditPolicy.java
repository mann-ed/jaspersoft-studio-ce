/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.table.part.editpolicy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import com.jaspersoft.studio.components.table.part.TableCellEditPart;
import com.jaspersoft.studio.editor.gef.parts.editPolicy.AContainerMoveEditPolicy;

/*
 * The Class BandMoveEditPolicy.
 * 
 * @author Chicu Veaceslav
 */
public class TableCellMoveEditPolicy extends AContainerMoveEditPolicy {

	protected Command getResizeCommand(ChangeBoundsRequest request) {
		return CreateResize.createResizeCommand(request,
				(TableCellEditPart) getHost());
	}

}
