/*******************************************************************************
 * Copyright (C) 2010 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, 
 * the following license terms apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
package com.jaspersoft.studio.components.crosstab.model.nodata;

import net.sf.jasperreports.crosstabs.design.JRCrosstabOrigin;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;

import com.jaspersoft.studio.components.crosstab.CrosstabCell;
import com.jaspersoft.studio.components.crosstab.CrosstabNodeIconDescriptor;
import com.jaspersoft.studio.components.crosstab.messages.Messages;
import com.jaspersoft.studio.components.crosstab.model.MCrosstab;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.IContainerEditPart;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.model.MRoot;
import com.jaspersoft.studio.model.util.IIconDescriptor;

public class MCrosstabWhenNoData extends ANode implements IContainerEditPart {
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
			iconDescriptor = new CrosstabNodeIconDescriptor("cell"); //$NON-NLS-1$
		return iconDescriptor;
	}

	public MCrosstab getCrosstab() {
		INode node = this;
		while (node != null && node.getParent() != null
				&& !(node instanceof MCrosstab) && !(node instanceof MRoot)) {
			node = node.getParent();
		}
		if (node instanceof MCrosstab)
			return (MCrosstab) node;
		return null;
	}

	/**
	 * Instantiates a new m field.
	 */
	public MCrosstabWhenNoData() {
		super();
	}

	/**
	 * Instantiates a new m field.
	 * 
	 * @param parent
	 *            the parent
	 * @param jfRield
	 *            the jf rield
	 * @param newIndex
	 *            the new index
	 */
	public MCrosstabWhenNoData(ANode parent, int index) {
		super(parent, index);
	}

	@Override
	public Color getForeground() {
		return ColorConstants.lightGray;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.model.INode#getDisplayText()
	 */
	public String getDisplayText() {
		return Messages.MCrosstabWhenNoData_when_no_data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.model.INode#getImagePath()
	 */
	public ImageDescriptor getImagePath() {
		return getIconDescriptor().getIcon16();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.model.INode#getToolTip()
	 */
	@Override
	public String getToolTip() {
		return getIconDescriptor().getToolTip();
	}

	private static final CrosstabCell cell = new CrosstabCell(
			JRCrosstabOrigin.TYPE_WHEN_NO_DATA_CELL);

	public Rectangle getBounds() {
		return getCrosstab().getCrosstabManager().getBounds(cell);
	}

	public int getDefaultWidth() {
		return 100;
	}

	public int getDefaultHeight() {
		return 100;
	}

	public JRDesignElement createJRElement(JasperDesign jasperDesign) {
		return null;
	}

}
