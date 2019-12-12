/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.table.model.nodata.action;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.ui.IWorkbenchPart;

import com.jaspersoft.studio.JSSCompoundCommand;
import com.jaspersoft.studio.components.table.TableComponentFactory;
import com.jaspersoft.studio.components.table.model.nodata.MTableNoData;

/*
 * The Class CreateGroupAction.
 */
public class DeleteNoDataAction extends DeleteAction {

	/** The Constant ID. */
	public static final String ID = "delete_table_nodata_cell"; //$NON-NLS-1$

	/**
	 * Constructs a <code>DeleteColumnAction</code> using the specified part.
	 * 
	 * @param part The part for this action
	 */
	public DeleteNoDataAction(IWorkbenchPart part) {
		super(part);
	}

	/**
	 * Initializes this action's text and images.
	 */
	@Override
	protected void init() {
		super.init();
		setText("Delete No Data Cell");
		setToolTipText("Delete No Data cell");
		setId(DeleteNoDataAction.ID);
	}

	@Override
	public Command createDeleteCommand(List objects) {
		if (objects.isEmpty())
			return null;
		if (!(objects.get(0) instanceof EditPart))
			return null;

		GroupRequest deleteReq = new GroupRequest(RequestConstants.REQ_DELETE);
		deleteReq.setEditParts(objects);

		JSSCompoundCommand compoundCmd = new JSSCompoundCommand(getText(), null);
		for (int i = 0; i < objects.size(); i++) {
			EditPart object = (EditPart) objects.get(i);
			if (object.getModel() instanceof MTableNoData) {
				MTableNoData model = (MTableNoData) object.getModel();
				compoundCmd.setReferenceNodeIfNull(model);
				// The cell of the detail can not be deleted
				Command cmd = TableComponentFactory.getDeleteCellCommand(model.getParent(), model);
				if (cmd != null)
					compoundCmd.add(cmd);
			}
		}
		return compoundCmd;
	}
}
