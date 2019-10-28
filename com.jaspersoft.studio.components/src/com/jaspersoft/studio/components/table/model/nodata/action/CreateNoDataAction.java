/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.table.model.nodata.action;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.jaspersoft.studio.components.table.model.MTable;
import com.jaspersoft.studio.components.table.model.nodata.MTableNoData;
import com.jaspersoft.studio.components.table.model.nodata.cmd.CreateNoDataCommand;
import com.jaspersoft.studio.components.table.part.editpolicy.JSSCompoundTableCommand;
import com.jaspersoft.studio.editor.action.ACachedSelectionAction;

import net.sf.jasperreports.components.table.WhenNoDataTypeTableEnum;

/**
 * 
 * Generic action to create a column, this action place before and after the
 * commands to create the column the commands to refresh the column number after
 * the execute or the undo
 * 
 * @author Orlandin Marco
 *
 */
public class CreateNoDataAction extends ACachedSelectionAction {

	/** The Constant ID. */
	public static final String ID = "create_table_nodata_cell"; //$NON-NLS-1$

	/**
	 * Constructs a <code>CreateAction</code> using the specified part.
	 * 
	 * @param part The part for this action
	 */
	public CreateNoDataAction(IWorkbenchPart part) {
		super(part);
	}

	/**
	 * Initializes this action's text and images.
	 */
	@Override
	protected void init() {
		super.init();
		setText("Create No Data Cell");
		setToolTipText("Create No Data cell");
		setId(CreateNoDataAction.ID);
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
		setEnabled(false);
	}

	/**
	 * Create the command for the action for each selected column, excluding the
	 * column group
	 */
	@Override
	protected Command createCommand() {
		List<Object> cells = editor.getSelectionCache().getSelectionModelForType(MTableNoData.class);
		if (!cells.isEmpty()) {
			MTable table = ((MTableNoData) cells.get(0)).getMTable();
			if (table.getStandardTable() != null && table.getStandardTable().getWhenNoDataType() != null
					&& table.getStandardTable().getWhenNoDataType().equals(WhenNoDataTypeTableEnum.NO_DATA_CELL)) {
				JSSCompoundTableCommand compundTableCommand = new JSSCompoundTableCommand(table);
				for (Object rawCell : cells) {
					MTableNoData col = (MTableNoData) rawCell;
					if (col.getValue() == null) {
						compundTableCommand.add(new CreateNoDataCommand(col));
					}
				}
				if (!compundTableCommand.isEmpty())
					return compundTableCommand;
			}
		}
		return null;
	}
}