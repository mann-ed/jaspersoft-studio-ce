/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.table.model;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.jaspersoft.studio.components.table.messages.Messages;
import com.jaspersoft.studio.help.HelpReferenceBuilder;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.DefaultValue;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.property.descriptor.NullEnum;
import com.jaspersoft.studio.property.descriptor.expression.ExprUtil;
import com.jaspersoft.studio.property.descriptor.expression.JRExpressionPropertyDescriptor;
import com.jaspersoft.studio.property.descriptors.NamedEnumPropertyDescriptor;

import net.sf.jasperreports.components.table.StandardBaseColumn;
import net.sf.jasperreports.components.table.StandardRow;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.events.CollectionElementAddedEvent;
import net.sf.jasperreports.engine.design.events.JRChangeEventsSupport;
import net.sf.jasperreports.engine.type.SplitTypeEnum;

/**
 * Class implemented by the header and the footer sections of the table. This is
 * done to share the propertyChange event, common to this sections
 * 
 * @author Orlandin Marco
 *
 */
public abstract class AMFooterHeaderCollection extends AMCollection {

	private static final long serialVersionUID = -1026904833107804569L;
	private static NamedEnumPropertyDescriptor<SplitTypeEnum> splitStyleD;

	public AMFooterHeaderCollection(ANode parent, JRDesignComponentElement jrDataset, String property) {
		super(parent, jrDataset, property);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(StandardTable.PROPERTY_COLUMNS)) {
			if (evt.getSource() instanceof StandardTable) {
				if (evt.getOldValue() == null && evt.getNewValue() != null) {
					int newIndex = -1;
					if (evt instanceof CollectionElementAddedEvent) {
						newIndex = ((CollectionElementAddedEvent) evt).getAddedIndex();
					}
					StandardBaseColumn bc = (StandardBaseColumn) evt.getNewValue();

					createColumn(this, bc, -1, newIndex);

				} else if (evt.getOldValue() != null && evt.getNewValue() == null) {
					// delete
					for (INode n : getChildren()) {
						if (n.getValue() == evt.getOldValue()) {
							removeChild((ANode) n);
							break;
						}
					}
				} else {
					// changed
					for (INode n : getChildren()) {
						if (n.getValue() == evt.getOldValue())
							n.setValue(evt.getNewValue());
					}
				}

				MTable mTable = (MTable) getParent();
				if (mTable == null) {
					((JRChangeEventsSupport) evt.getSource()).getEventSupport().removePropertyChangeListener(this);
				} else {
					mTable.getTableManager().refresh();
				}
			}
		}
		super.propertyChange(evt);
	}

	/**
	 * Creates the property descriptors.
	 * 
	 * @param desc the desc
	 */
	@Override
	public void createPropertyDescriptors(List<IPropertyDescriptor> desc) {
		splitStyleD = new NamedEnumPropertyDescriptor<>(StandardRow.PROPERTY_splitType, Messages.Common_SplitType,
				SplitTypeEnum.PREVENT, NullEnum.NULL);
		splitStyleD.setDescription(Messages.Common_TableRowSplitTypeDesc);
		splitStyleD.setHelpRefBuilder(
				new HelpReferenceBuilder("net.sf.jasperreports.doc/docs/schema.reference.html?cp=0_1#_splitType")); //$NON-NLS-1$
		desc.add(splitStyleD);
		
		JRExpressionPropertyDescriptor printWhenExprD = new JRExpressionPropertyDescriptor(
				StandardRow.PROPERTY_PRINT_WHEN_EXPRESSION, Messages.Common_PrintWhenExpr);
		printWhenExprD.setDescription(Messages.Common_TableRowPrintWhenDesc);
		printWhenExprD.setHelpRefBuilder(new HelpReferenceBuilder(
				"net.sf.jasperreports.doc/docs/schema.reference.html?cp=0_1#printWhenExpression")); //$NON-NLS-1$
		desc.add(printWhenExprD);
	}

	@Override
	protected Map<String, DefaultValue> createDefaultsMap() {
		Map<String, DefaultValue> defaultsMap = super.createDefaultsMap();
		defaultsMap.put(StandardRow.PROPERTY_PRINT_WHEN_EXPRESSION, new DefaultValue(null, true));
		int splitTypeDef = NamedEnumPropertyDescriptor.getIntValue(SplitTypeEnum.PREVENT, NullEnum.NULL,null);
		defaultsMap.put(StandardRow.PROPERTY_splitType, new DefaultValue(splitTypeDef,true));
		return defaultsMap;
	}

	protected abstract StandardRow getRow(boolean set);

	@Override
	public Object getPropertyValue(Object id) {
		StandardRow r = getRow(false);
		if (r != null) {
			if (id.equals(StandardRow.PROPERTY_PRINT_WHEN_EXPRESSION)) {
				return ExprUtil.getExpression(r.getPrintWhenExpression());
			}
			if (id.equals(StandardRow.PROPERTY_splitType)) {
				return splitStyleD.getIntValue(r.getSplitType());
			}
		}
		return null;
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		StandardRow r = getRow(true);
		if (id.equals(StandardRow.PROPERTY_PRINT_WHEN_EXPRESSION)) {
			r.setPrintWhenExpression(ExprUtil.setValues(r.getPrintWhenExpression(), value));
		}
		if (id.equals(StandardRow.PROPERTY_splitType)) {
			r.setSplitType(splitStyleD.getEnumValue(value));
		}
	}

}
