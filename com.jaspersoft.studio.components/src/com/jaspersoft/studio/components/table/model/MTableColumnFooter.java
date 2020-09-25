/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.table.model;

import org.eclipse.babel.editor.util.UIUtils;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.jaspersoft.studio.components.table.TableComponentFactory;
import com.jaspersoft.studio.components.table.TableNodeIconDescriptor;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.model.util.IIconDescriptor;

import net.sf.jasperreports.components.table.BaseColumn;
import net.sf.jasperreports.components.table.StandardBaseColumn;
import net.sf.jasperreports.components.table.StandardRow;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.utils.compatibility.CompatibilityConstants;

public class MTableColumnFooter extends AMFooterHeaderCollection {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	/** The icon descriptor. */
	private static IIconDescriptor iconDescriptor;

	/**
	 * Gets the icon descriptor.
	 * 
	 * @return the icon descriptor
	 */
	public static IIconDescriptor getIconDescriptor() {
		if (iconDescriptor == null)
			iconDescriptor = new TableNodeIconDescriptor("tablecolumnfooter"); //$NON-NLS-1$
		return iconDescriptor;
	}

	/** The descriptors. */
	protected static IPropertyDescriptor[] descriptors;

	public MTableColumnFooter(ANode parent, JRDesignComponentElement jrDataset, String property) {
		super(parent, jrDataset, property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.model.INode#getDisplayText()
	 */
	public String getDisplayText() {
		return getIconDescriptor().getTitle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.model.INode#getImagePath()
	 */
	public ImageDescriptor getImagePath() {
		return getIconDescriptor().getIcon16();
	}

	@Override
	public String getCellEvent() {
		return StandardBaseColumn.PROPERTY_COLUMN_FOOTER;
	}

	@Override
	public void createColumn(ANode mth, BaseColumn bc, int i, int index) {
		TableComponentFactory.createCellColumnFooter(mth, bc, i, index);
	}

	@Override
	public Color getForeground() {
		for (INode child : getChildren()) {
			if (child.getValue() != null && ((StandardBaseColumn) child.getValue()).getColumnFooter() != null)
				return null;
		}
		return UIUtils.getSystemColor(CompatibilityConstants.Colors.COLOR_WIDGET_DISABLED_FOREGROUND);
	}

	@Override
	public void setDescriptors(IPropertyDescriptor[] descriptors1) {
		descriptors = descriptors1;
	}

	@Override
	public IPropertyDescriptor[] getDescriptors() {
		return descriptors;
	}

	@Override
	protected StandardRow getRow(boolean set) {
		JRDesignComponentElement jrElement = getValue();
		StandardTable st = (StandardTable) jrElement.getComponent();
		StandardRow r = (StandardRow) st.getColumnFooter();
		if (set && r == null) {
			r = new StandardRow();
			st.setColumnFooter(r);
		}
		return r;
	}
}
