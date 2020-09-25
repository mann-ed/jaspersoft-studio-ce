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
import com.jaspersoft.studio.components.table.messages.Messages;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.model.util.IIconDescriptor;

import net.sf.jasperreports.components.table.BaseColumn;
import net.sf.jasperreports.components.table.Cell;
import net.sf.jasperreports.components.table.StandardBaseColumn;
import net.sf.jasperreports.components.table.StandardRow;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.utils.compatibility.CompatibilityConstants;

public class MTableGroupFooter extends AMFooterHeaderCollection {
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
			iconDescriptor = new TableNodeIconDescriptor("tablegroupfooter"); //$NON-NLS-1$
		return iconDescriptor;
	}

	/** The descriptors. */
	protected static IPropertyDescriptor[] descriptors;

	public MTableGroupFooter(ANode parent, JRDesignComponentElement jrDataset, JRDesignGroup jrDesignGroup,
			String property) {
		super(parent, jrDataset, property);
		this.jrDesignGroup = jrDesignGroup;
	}

	private JRDesignGroup jrDesignGroup;

	public JRDesignGroup getJrDesignGroup() {
		return jrDesignGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.model.INode#getDisplayText()
	 */
	public String getDisplayText() {
		return Messages.MTableGroupFooter_group_footer + ": " + jrDesignGroup.getName(); //$NON-NLS-1$
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
		return StandardBaseColumn.PROPERTY_GROUP_FOOTERS;
	}

	@Override
	public void createColumn(ANode mth, BaseColumn bc, int i, int index) {
		TableComponentFactory.createCellGroupFooter(mth, bc, i, jrDesignGroup.getName(), index);
	}

	@Override
	public Color getForeground() {
		for (INode child : getChildren()) {
			if (child.getValue() != null) {
				StandardBaseColumn currentCol = (StandardBaseColumn) child.getValue();
				Cell footerCell = currentCol.getGroupFooter(jrDesignGroup.getName());
				if (footerCell != null)
					return null;
			}
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
		String name = getJrDesignGroup().getName();
		StandardRow r = (StandardRow) st.getGroupFooter(name);
		if (set && r == null) {
			r = new StandardRow();
			st.setGroupFooter(name, r);
		}
		return r;
	}
}
