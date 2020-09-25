/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.table.model;

import java.beans.PropertyChangeEvent;

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
import net.sf.jasperreports.components.table.StandardColumnGroup;
import net.sf.jasperreports.components.table.StandardRow;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.utils.compatibility.CompatibilityConstants;

public class MTableHeader extends AMFooterHeaderCollection {
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
			iconDescriptor = new TableNodeIconDescriptor("tableheader"); //$NON-NLS-1$
		return iconDescriptor;
	}

	/** The descriptors. */
	protected static IPropertyDescriptor[] descriptors;

	public MTableHeader(ANode parent, JRDesignComponentElement jrDataset, String property) {
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
		return StandardBaseColumn.PROPERTY_TABLE_HEADER;
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		// because MTableDetail do not contains ColumnGroups it will lose the event
		// performed
		// on the columns inside a group, so this will reroute the event also to it.
		if (evt.getPropertyName().equals(StandardTable.PROPERTY_COLUMNS)) {
			if (evt.getSource() instanceof StandardColumnGroup) {
				// redirect the event to the detail section
				for (INode n : getParent().getChildren()) {
					if (n instanceof MTableDetail) {
						n.propertyChange(evt);
						break;
					}
				}
			}
		}
		super.propertyChange(evt);
	}

	@Override
	public void createColumn(ANode mth, BaseColumn bc, int i, int index) {
		TableComponentFactory.createCellTableHeader(mth, bc, i, index);
	}

	@Override
	public Color getForeground() {
		for (INode child : getChildren()) {
			if (child.getValue() != null && ((StandardBaseColumn) child.getValue()).getTableHeader() != null)
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
		StandardRow r = (StandardRow) st.getTableHeader();
		if (set && r == null) {
			r = new StandardRow();
			st.setTableHeader(r);
		}
		return r;
	}
}
