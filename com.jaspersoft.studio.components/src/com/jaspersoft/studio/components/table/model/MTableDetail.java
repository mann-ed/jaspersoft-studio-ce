/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.table.model;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.jaspersoft.studio.components.table.TableComponentFactory;
import com.jaspersoft.studio.components.table.TableManager;
import com.jaspersoft.studio.components.table.TableNodeIconDescriptor;
import com.jaspersoft.studio.help.HelpReferenceBuilder;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.DefaultValue;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.model.util.IIconDescriptor;
import com.jaspersoft.studio.property.descriptor.expression.ExprUtil;
import com.jaspersoft.studio.property.descriptor.expression.JRExpressionPropertyDescriptor;

import net.sf.jasperreports.components.table.BaseColumn;
import net.sf.jasperreports.components.table.Row;
import net.sf.jasperreports.components.table.StandardBaseColumn;
import net.sf.jasperreports.components.table.StandardColumn;
import net.sf.jasperreports.components.table.StandardColumnGroup;
import net.sf.jasperreports.components.table.StandardRow;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.eclipse.util.Pair;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.events.CollectionElementAddedEvent;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;

public class MTableDetail extends AMCollection {
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
			iconDescriptor = new TableNodeIconDescriptor("tabledetail"); //$NON-NLS-1$
		return iconDescriptor;
	}

	/** The descriptors. */
	protected static IPropertyDescriptor[] descriptors;

	public MTableDetail(ANode parent, JRDesignComponentElement jrDataset, String property) {
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
		return StandardColumn.PROPERTY_DETAIL;
	}

	@Override
	public void createColumn(ANode mth, BaseColumn bc, int i, int index) {
		TableComponentFactory.createCellDetail(mth, bc, new Pair<Integer, Integer>(i, index));
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(StandardTable.PROPERTY_COLUMNS)) {
			MTable mTable = (MTable) getParent();
			if (evt.getOldValue() == null && evt.getNewValue() != null) {
				// add operation, check if it was on a group or on a section
				if (evt.getSource() instanceof StandardColumnGroup || evt.getSource() instanceof StandardTable) {
					int newIndex = -1;
					if (evt instanceof CollectionElementAddedEvent) {
						newIndex = ((CollectionElementAddedEvent) evt).getAddedIndex();
					}
					StandardBaseColumn bc = (StandardBaseColumn) evt.getNewValue();
					newIndex = TableManager.getAllColumns(mTable).indexOf(bc);
					createColumn(this, bc, -1, newIndex);
				}
			} else if (evt.getOldValue() != null && evt.getNewValue() == null) {
				// delete operation, check if it was on a group or on a section
				HashSet<StandardColumn> deletedColumns = getAllColumns(evt.getOldValue());
				// delete, the detail section need some special code to allow to delete the
				// columns from the detail
				// also if the deleted object was a group
				for (INode n : new ArrayList<INode>(getChildren())) {
					if (deletedColumns.contains(n.getValue())) {
						removeChild((ANode) n);
					}
				}
			} else {
				// changed
				for (INode n : getChildren()) {
					if (n.getValue() == evt.getOldValue())
						n.setValue(evt.getNewValue());
				}
			}
			if (mTable == null) {
				((JRChangeEventsSupport) evt.getSource()).getEventSupport().removePropertyChangeListener(this);
			} else {
				mTable.getTableManager().refresh();
			}
		}
		super.propertyChange(evt);
	}

	/**
	 * Return a list of the column inside the passedParameter, if the column is a
	 * group then recursively return also its content
	 * 
	 * @param currentElement an object, should be a standard column or a standard
	 *                       group column
	 * @return a not null hashset of the standard column inside the current element
	 */
	private HashSet<StandardColumn> getAllColumns(Object currentElement) {
		HashSet<StandardColumn> columns = new HashSet<>();
		if (currentElement instanceof StandardColumnGroup) {
			StandardColumnGroup group = (StandardColumnGroup) currentElement;
			for (BaseColumn column : group.getColumns()) {
				columns.addAll(getAllColumns(column));
			}
		} else if (currentElement instanceof StandardColumn) {
			columns.add((StandardColumn) currentElement);
		}
		return columns;
	}

	/**
	 * Creates the property descriptors.
	 * 
	 * @param desc the desc
	 */
	@Override
	public void createPropertyDescriptors(List<IPropertyDescriptor> desc) {
		JRExpressionPropertyDescriptor printWhenExprD = new JRExpressionPropertyDescriptor(
				StandardRow.PROPERTY_PRINT_WHEN_EXPRESSION, Messages.common_print_when_expression);
		printWhenExprD.setDescription("Print When Expression for Table Row");
		printWhenExprD.setCategory(Messages.MGraphicElement_print_when);
		desc.add(printWhenExprD);
		printWhenExprD.setHelpRefBuilder(new HelpReferenceBuilder(
				"net.sf.jasperreports.doc/docs/schema.reference.html?cp=0_1#printWhenExpression"));
	}

	@Override
	protected Map<String, DefaultValue> createDefaultsMap() {
		Map<String, DefaultValue> defaultsMap = super.createDefaultsMap();
		defaultsMap.put(StandardRow.PROPERTY_PRINT_WHEN_EXPRESSION, new DefaultValue(null, true));
		return defaultsMap;
	}

	@Override
	public Object getPropertyValue(Object id) {
		JRDesignComponentElement jrElement = getValue();
		StandardTable st = (StandardTable) jrElement.getComponent();
		Row r = st.getDetail();
		if (r != null) {
			if (id.equals(StandardRow.PROPERTY_PRINT_WHEN_EXPRESSION))
				return ExprUtil.getExpression(r.getPrintWhenExpression());
		}
		return null;
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		JRDesignComponentElement jrElement = getValue();
		StandardTable st = (StandardTable) jrElement.getComponent();
		StandardRow r = (StandardRow) st.getDetail();
		if (r == null) {
			r = new StandardRow();
			st.setDetail(r);
		}
		if (id.equals(StandardRow.PROPERTY_PRINT_WHEN_EXPRESSION))
			r.setPrintWhenExpression(ExprUtil.setValues(r.getPrintWhenExpression(), value));
	}

	@Override
	public void setDescriptors(IPropertyDescriptor[] descriptors1) {
		descriptors = descriptors1;
	}

	@Override
	public IPropertyDescriptor[] getDescriptors() {
		return descriptors;
	}

}
