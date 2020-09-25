/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.crosstab.model.header;

import org.eclipse.babel.editor.util.UIUtils;
import org.eclipse.swt.graphics.Color;

import com.jaspersoft.studio.components.crosstab.messages.Messages;
import com.jaspersoft.studio.components.crosstab.model.cell.MCell;
import com.jaspersoft.studio.model.ANode;

import net.sf.jasperreports.crosstabs.JRCellContents;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.utils.compatibility.CompatibilityConstants;

public class MCrosstabHeaderCell extends MCell {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public MCrosstabHeaderCell() {
		super();
	}

	public MCrosstabHeaderCell(ANode parent, JRCellContents jfRield, int index) {
		super(parent, jfRield, Messages.MCrosstabHeaderCell_crosstab_header, index);
	}

	@Override
	public Color getForeground() {
		if (getValue() == null)
			return UIUtils.getSystemColor(CompatibilityConstants.Colors.COLOR_WIDGET_DISABLED_FOREGROUND);
		return null;
	}

}
