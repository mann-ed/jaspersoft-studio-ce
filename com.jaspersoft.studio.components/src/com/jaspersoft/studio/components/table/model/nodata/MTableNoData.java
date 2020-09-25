/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.table.model.nodata;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.babel.editor.util.UIUtils;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.jaspersoft.studio.components.crosstab.model.cell.MCell;
import com.jaspersoft.studio.components.table.messages.Messages;
import com.jaspersoft.studio.components.table.model.MTable;
import com.jaspersoft.studio.editor.layout.FreeLayout;
import com.jaspersoft.studio.editor.layout.ILayout;
import com.jaspersoft.studio.editor.layout.LayoutManager;
import com.jaspersoft.studio.editor.outline.part.TreeEditPart;
import com.jaspersoft.studio.help.HelpReferenceBuilder;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.model.DefaultValue;
import com.jaspersoft.studio.model.IContainer;
import com.jaspersoft.studio.model.IContainerEditPart;
import com.jaspersoft.studio.model.IContainerLayout;
import com.jaspersoft.studio.model.IGraphicElement;
import com.jaspersoft.studio.model.IGraphicElementContainer;
import com.jaspersoft.studio.model.IGraphicalPropertiesHandler;
import com.jaspersoft.studio.model.IGroupElement;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.model.IPastable;
import com.jaspersoft.studio.model.IPastableGraphic;
import com.jaspersoft.studio.property.descriptor.NullEnum;
import com.jaspersoft.studio.property.descriptor.combo.RWStyleComboBoxPropertyDescriptor;
import com.jaspersoft.studio.property.descriptor.propexpr.JPropertyExpressionsDescriptor;
import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionDTO;
import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionsDTO;
import com.jaspersoft.studio.property.descriptors.PixelPropertyDescriptor;
import com.jaspersoft.studio.utils.ModelUtils;

import net.sf.jasperreports.components.table.DesignBaseCell;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.engine.JRBoxContainer;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.utils.compatibility.CompatibilityConstants;

public class MTableNoData extends APropertyNode implements IGraphicElement, IPastableGraphic, IGraphicElementContainer,
		IPastable, IGroupElement, IGraphicalPropertiesHandler, IContainer, IContainerLayout, IContainerEditPart {

	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	private static IPropertyDescriptor[] descriptors;

	/**
	 * Instantiates a new m field.
	 */
	public MTableNoData() {
		super();
	}

	/**
	 * Instantiates a new m field.
	 * 
	 * @param parent   the parent
	 * @param jfRield  the jf rield
	 * @param newIndex the new index
	 */
	public MTableNoData(ANode parent, DesignBaseCell cell, int index) {
		super(parent, index);
		setValue(cell);
	}

	private transient PropertyChangeListener tbListener;

	private PropertyChangeListener getTableListener() {
		if (tbListener == null)
			tbListener = evt -> {
				if (evt.getPropertyName().equals(StandardTable.PROPERTY_NO_DATA))
					setValue(evt.getNewValue());
			};
		return tbListener;
	}

	/**
	 * Store the table and section ancestor of this node. When the parent is set to
	 * null the old value are maintained to allow the undo operation to be executed
	 * on this node
	 */
	@Override
	public void setParent(ANode newparent, int newIndex) {
		super.setParent(newparent, newIndex);
		if (containerTable != null)
			containerTable.getStandardTable().getEventSupport().removePropertyChangeListener(getTableListener());
		ANode node = getParent();
		while (node != null) {
			if (node instanceof MTable) {
				containerTable = (MTable) node;
				containerTable.getStandardTable().getEventSupport().addPropertyChangeListener(getTableListener());
				break;
			}
			node = node.getParent();
		}

		// When the node is removed (because maybe a cell was deleted) the value
		// is not
		// set to null, in this case remove this listener from the node
		if (newparent == null && getValue() != null) {
			getValue().getEventSupport().removePropertyChangeListener(this);
		}
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
		EditPart fep = getFigureEditPart();
		if (fep != null)
			fep.refresh();
	}

	@Override
	public EditPart getFigureEditPart() {
		for (PropertyChangeListener o : getPropertyChangeSupport().getPropertyChangeListeners()) {
			if (o instanceof TreeEditPart)
				return (EditPart) o;
		}
		return null;
	}

	@Override
	public DesignBaseCell getValue() {
		return (DesignBaseCell) super.getValue();
	}

	/**
	 * Last valid value for the table containing this node, this reference because
	 * it could be need for some undo operations (SetValueCommand) even after this
	 * node was detached from the table
	 */
	private MTable containerTable;

	public MTable getMTable() {
		return containerTable;
	}

	@Override
	public ILayout getDefaultLayout() {
		return LayoutManager.getLayout(FreeLayout.class.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.model.INode#getDisplayText()
	 */
	public String getDisplayText() {
		return "No Data";
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
	public IPropertyDescriptor[] getDescriptors() {
		return descriptors;
	}

	@Override
	public void setDescriptors(IPropertyDescriptor[] descriptors1) {
		descriptors = descriptors1;
	}

	/**
	 * Creates the property descriptors.
	 * 
	 * @param desc the desc
	 */
	@Override
	public void createPropertyDescriptors(List<IPropertyDescriptor> desc) {
		RWStyleComboBoxPropertyDescriptor styleD = new RWStyleComboBoxPropertyDescriptor(DesignBaseCell.PROPERTY_STYLE,
				Messages.MCell_parent_style, new String[] { "" }, //$NON-NLS-1$
				NullEnum.NULL);
		styleD.setDescription(Messages.MCell_parent_style_description);
		desc.add(styleD);
		styleD.setHelpRefBuilder(new HelpReferenceBuilder(
				"net.sf.jasperreports.doc/docs/schema.reference.html?cp=0_1#reportElement_style"));

		PixelPropertyDescriptor hD = new PixelPropertyDescriptor(DesignBaseCell.PROPERTY_HEIGHT, Messages.MCell_height);
		desc.add(hD);

		JPropertyExpressionsDescriptor propertiesD = new JPropertyExpressionsDescriptor(
				JRDesignElement.PROPERTY_PROPERTY_EXPRESSIONS,
				com.jaspersoft.studio.messages.Messages.MGraphicElement_property_expressions);
		propertiesD.setDescription(
				com.jaspersoft.studio.messages.Messages.MGraphicElement_property_expressions_description);
		desc.add(propertiesD);

		styleD.setCategory(Messages.MCell_cell_properties_category);
		hD.setCategory(Messages.MCell_cell_properties_category);

	}

	@Override
	protected Map<String, DefaultValue> createDefaultsMap() {
		Map<String, DefaultValue> defaultsMap = super.createDefaultsMap();

		defaultsMap.put(DesignBaseCell.PROPERTY_STYLE, new DefaultValue(null, false));

		return defaultsMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java
	 * .lang.Object)
	 */
	@Override
	public Object getPropertyValue(Object id) {
		DesignBaseCell cell = getValue();
		if (cell != null) {
			if (id.equals(DesignBaseCell.PROPERTY_STYLE)) {
				if (cell.getStyleNameReference() != null) {
					return cell.getStyleNameReference();
				}
				JRStyle actualStyle = getActualStyle();
				return actualStyle != null ? actualStyle.getName() : ""; //$NON-NLS-1$
			}

			if (id.equals(DesignBaseCell.PROPERTY_HEIGHT))
				return cell.getHeight();
			if (id.equals(JRDesignElement.PROPERTY_PROPERTY_EXPRESSIONS)) {
				JRPropertiesMap propertiesMap = cell.getPropertiesMap();
				if (propertiesMap != null)
					propertiesMap = propertiesMap.cloneProperties();
				return new PropertyExpressionsDTO(null, propertiesMap, getValue(),
						ModelUtils.getExpressionContext(this));
			}
			if (id.equals(APropertyNode.PROPERTY_MAP)) {
				// return a copy of the map
				return getPropertiesMapClone();
			}
		}
		return null;
	}

	protected JRPropertiesMap getPropertiesMapClone() {
		JRPropertiesMap propertiesMap = null;
		DesignBaseCell cell = getValue();
		if (cell != null) {
			propertiesMap = cell.getPropertiesMap();
			if (propertiesMap != null)
				propertiesMap = propertiesMap.cloneProperties();
		}
		return propertiesMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java
	 * .lang.Object, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(Object id, Object value) {
		DesignBaseCell cell = getValue();
		if (cell != null) {
			if (id.equals(DesignBaseCell.PROPERTY_STYLE)) {
				if (value != null && !((String) value).trim().isEmpty()) {
					if (!value.equals("")) { //$NON-NLS-1$
						JRStyle style = getJasperDesign().getStylesMap().get(value);
						if (style != null) {
							// FIXME: It is important to set a null first the external style, because it is
							// returned first on the
							// getPropertyValue and this raise a lot of events
							cell.setStyleNameReference(null);
							cell.setStyle(style);
						} else {
							cell.setStyleNameReference((String) value);
							// The local style is set to null so the external one will be used
							cell.setStyle(null);
						}
					}
				} else {
					cell.setStyleNameReference(null);
					cell.setStyle(null);
				}
			} else if (id.equals(DesignBaseCell.PROPERTY_HEIGHT)) {
				cell.setHeight((Integer) value);
			} else if (id.equals(APropertyNode.PROPERTY_MAP)) {
				JRPropertiesMap originalMap = cell.getPropertiesMap().cloneProperties();
				JRPropertiesMap v = (JRPropertiesMap) value;
				String[] names = cell.getPropertiesMap().getPropertyNames();
				// clear the old map
				for (int i = 0; i < names.length; i++) {
					cell.getPropertiesMap().removeProperty(names[i]);
				}
				// set the new properties
				names = v.getPropertyNames();
				for (int i = 0; i < names.length; i++) {
					cell.getPropertiesMap().setProperty(names[i], v.getProperty(names[i]));
				}
				// really important to trigger the property with source the JR object and not
				// the node
				// using the node could cause problem with the refresh of the advanced
				// properties view
				this.getPropertyChangeSupport().firePropertyChange(
						new PropertyChangeEvent(cell, PROPERTY_MAP, originalMap, cell.getPropertiesMap()));
			} else if (id.equals(JRDesignElement.PROPERTY_PROPERTY_EXPRESSIONS)) {
				if (value instanceof PropertyExpressionsDTO) {
					PropertyExpressionsDTO dto = (PropertyExpressionsDTO) value;

					// now change properties, first remove the old ones if any
					String[] names = cell.getPropertiesMap().getPropertyNames();
					for (int i = 0; i < names.length; i++) {
						cell.getPropertiesMap().removeProperty(names[i]);
					}
					// now add the new properties
					for (PropertyExpressionDTO p : dto.getProperties()) {
						if (!p.isExpression()) {
							cell.getPropertiesMap().setProperty(p.getName(), p.getValue());
						}
					}
					this.getPropertyChangeSupport().firePropertyChange(APropertyNode.PROPERTY_MAP, false, true);
				}
			}
		}
	}

	public int getDefaultWidth() {
		return 40;
	}

	public int getDefaultHeight() {
		return 40;
	}

	@Override
	public JRDesignElement createJRElement(JasperDesign jasperDesign) {
		return null;
	}

	@Override
	public Color getForeground() {
		if (getValue() == null)
			return UIUtils.getSystemColor(CompatibilityConstants.Colors.COLOR_WIDGET_DISABLED_FOREGROUND);
		return null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		HashSet<String> graphicalProperties = getGraphicalProperties();
		if (graphicalProperties.contains(evt.getPropertyName()))
			setChangedProperty(true);
		super.propertyChange(evt);
	}

	public JRBoxContainer getBoxContainer() {
		return getValue();
	}

	public Integer getTopPadding() {
		DesignBaseCell v = getValue();
		if (v == null)
			return 0;
		return v.getLineBox().getTopPadding();
	}

	public Integer getLeftPadding() {
		DesignBaseCell v = getValue();
		if (v == null)
			return 0;
		return v.getLineBox().getLeftPadding();
	}

	public Integer getBottomPadding() {
		DesignBaseCell v = getValue();
		if (v == null)
			return 0;
		return v.getLineBox().getBottomPadding();
	}

	public Integer getRightPadding() {
		DesignBaseCell v = getValue();
		if (v == null)
			return 0;
		return v.getLineBox().getRightPadding();
	}

	public Integer getPadding() {
		DesignBaseCell v = getValue();
		if (v == null)
			return 0;
		return v.getLineBox().getPadding();
	}

	protected int getTableWidth() {
		ANode parent = getParent();
		while (parent != null) {
			if (parent instanceof MTable) {
				return ((MTable) parent).getValue().getWidth();
			}
			parent = parent.getParent();
		}
		return 100;
	}

	@Override
	public Dimension getSize() {
		DesignBaseCell cell = getValue();
		int h = cell != null && cell.getHeight() != null ? cell.getHeight() : 0;
		return new Dimension(getTableWidth(), h);
	}

	@Override
	public JRElementGroup getJRElementGroup() {
		return getValue();
	}

	@Override
	public JRPropertiesHolder[] getPropertyHolder() {
		return new JRPropertiesHolder[] { getValue() };
	}

	/**
	 * Flag changed when some property that has graphical impact on the element is
	 * changed. This is used to redraw the elemnt only when something graphical is
	 * changed isndie it, all the other times can just be copied
	 */
	private boolean visualPropertyChanged = true;

	/**
	 * True if some graphical property is changed for the element, false otherwise
	 */
	@Override
	public boolean hasChangedProperty() {
		synchronized (this) {
			return visualPropertyChanged;
		}
	}

	/**
	 * Set the actual state of the property change flag
	 */
	@Override
	public void setChangedProperty(boolean value) {
		synchronized (this) {
			if (value) {
				ANode parent = getParent();
				while (parent != null) {
					if (parent instanceof IGraphicalPropertiesHandler) {
						IGraphicalPropertiesHandler handler = (IGraphicalPropertiesHandler) parent;
						handler.setChangedProperty(true);
						// We can exit the cycle since the setChangedProperty on
						// the parent will propagate the
						// refresh on the upper levels
						break;
					} else {
						parent = parent.getParent();
					}
				}
			}
			visualPropertyChanged = value;
		}
	}

	/**
	 * When the style changes a refresh is sent not only to the current node, but
	 * also to the node that are listening on the same JR element. This is done to
	 * propagate the change to every editor where the element is displayed
	 */
	@Override
	public void setStyleChangedProperty() {
// Performance improvement, avoid to send the event more than one time for each
// editor
		HashSet<ANode> refreshedParents = new HashSet<>();
		for (PropertyChangeListener listener : getValue().getEventSupport().getPropertyChangeListeners()) {
			if (listener instanceof MCell) {
				MCell listenerCell = (MCell) listener;
				MTable table = getMTable();
				if (table != null) {
					ANode tableParent = table.getParent();
					if (tableParent != null && !refreshedParents.contains(tableParent)) {
						refreshedParents.add(tableParent);
						listenerCell.setChangedProperty(true);

					}
				}
			}
		}
	}

	private static HashSet<String> cachedGraphicalProperties = null;

	/**
	 * Return the graphical properties for an MGraphicalElement
	 */
	@Override
	public HashSet<String> getGraphicalProperties() {
		if (cachedGraphicalProperties == null) {
			cachedGraphicalProperties = new HashSet<>();
			cachedGraphicalProperties.add(DesignBaseCell.PROPERTY_STYLE);
			cachedGraphicalProperties.add(DesignBaseCell.PROPERTY_HEIGHT);
		}
		return cachedGraphicalProperties;
	}

	/**
	 * Return all the styles of the column
	 */
	@Override
	public Map<String, List<ANode>> getUsedStyles() {
		Map<String, List<ANode>> result = super.getUsedStyles();
		DesignBaseCell cell = getValue();
		if (cell != null)
			addElementStyle(cell.getStyle(), result);

		for (INode node : getChildren())
			if (node instanceof ANode)
				mergeElementStyle(result, ((ANode) node).getUsedStyles());

		return result;
	}

	@Override
	public void setStyle(JRStyle style) {
		DesignBaseCell cell = getValue();
		if (cell != null)
			cell.setStyle(style);
	}

	/**
	 * Return the internal style used. If the internal style is a reference to a
	 * removed style then it is also removed from the element
	 */
	public JRStyle getActualStyle() {
		DesignBaseCell cell = getValue();
		if (cell != null) {
			// Check if the used style is valid otherwise set it to null
			if (cell.getStyle() != null && !getJasperDesign().getStylesMap().containsKey(cell.getStyle().getName()))
				setPropertyValue(DesignBaseCell.PROPERTY_STYLE, null);
			if (cell.getStyle() != null)
				return cell.getStyle();
		}
		return null;
	}

	/**
	 * Style descriptor used by the inheritance view section
	 */
	@Override
	public HashMap<String, Object> getStylesDescriptors() {
		HashMap<String, Object> result = super.getStylesDescriptors();
		if (getValue() == null)
			return result;
		return result;
	}

	@Override
	public Rectangle getAbsoluteBounds() {
		Rectangle b = getBounds();
		return new Rectangle(b.x, b.y + 40, b.width, b.height);
	}

	@Override
	public Rectangle getBounds() {
		MTable tableModel = getMTable();
		DesignBaseCell cell = getValue();
		int h = cell != null && cell.getHeight() != null ? cell.getHeight() + 30 : 0;

		Rectangle tableLocation = tableModel.getAbsoluteBounds();

		int x = 0;
		int y = tableLocation.height + tableLocation.y; // here should go after the last section
		return new Rectangle(x, y + 30, tableModel.getValue().getWidth(), h);
	}

	@Override
	public boolean isEditable() {
		return getValue() != null;
	}
}
