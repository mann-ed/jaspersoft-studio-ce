/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.toolbars;

import com.jaspersoft.studio.components.table.model.column.MCell;
import com.jaspersoft.studio.components.table.model.column.action.CreateColumnAction;
import com.jaspersoft.studio.components.table.model.column.action.CreateColumnAfterAction;

/**
 * Create the toolbar button to add a column to the selected table
 * 
 * @author Orlandin Marco
 *
 */
public class CreateColumnAfterContributionItem extends CreateColumnContributionItem {

	/**
	 * Action that will be executed to add the column
	 */
	@Override
	protected CreateColumnAction getAction(){ 
		return new CreateColumnAfterAction(null);
	}
	
	@Override
	public boolean isVisible() {
		if (!super.isVisible()) return false;
		if (getSelectionForType(MCell.class).size() == 1){
			setEnablement();
			return true;
		}
		return false;
	}

}
