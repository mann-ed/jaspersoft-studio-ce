/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.components.barcode.model.barcode4j;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.jaspersoft.studio.components.barcode.messages.Messages;
import com.jaspersoft.studio.editor.defaults.DefaultManager;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.DefaultValue;
import com.jaspersoft.studio.property.descriptors.AbstractJSSCellEditorValidator;
import com.jaspersoft.studio.property.descriptors.JSSComboPropertyDescriptor;
import com.jaspersoft.studio.property.descriptors.PixelPropertyDescriptor;

import net.sf.jasperreports.components.barcode4j.DataMatrixComponent;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JasperDesign;

public class MDataMatrix extends MBarcode4j {

	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	private static IPropertyDescriptor[] descriptors;

	public MDataMatrix() {
		super();
	}

	public MDataMatrix(ANode parent, JRDesignComponentElement jrBarcode, int newIndex) {
		super(parent, jrBarcode, newIndex);
	}

	@Override
	public JRDesignComponentElement createJRElement(JasperDesign jasperDesign, boolean applyDefault) {
		JRDesignComponentElement el = new JRDesignComponentElement();
		DataMatrixComponent component = new DataMatrixComponent();
		JRDesignExpression exp = new JRDesignExpression();
		exp.setText("\"123456789\""); //$NON-NLS-1$
		component.setCodeExpression(exp);
		el.setComponent(component);
		el.setComponentKey(
				new ComponentKey("http://jasperreports.sourceforge.net/jasperreports/components", "jr", "DataMatrix")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		if (applyDefault) {
			DefaultManager.INSTANCE.applyDefault(this.getClass(), el);
		}

		return el;
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
		super.createPropertyDescriptors(desc);

		JSSComboPropertyDescriptor shapeD = new JSSComboPropertyDescriptor(DataMatrixComponent.PROPERTY_SHAPE,
				Messages.MDataMatrix_shape, DataMatrixShape.getItems());
		shapeD.setDescription(Messages.MDataMatrix_shape_description);
		desc.add(shapeD);
		shapeD.setCategory(Messages.MDataMatrix_properties_category);

		AbstractJSSCellEditorValidator validator = new AbstractJSSCellEditorValidator() {

			@Override
			public String isValid(Object value) {
				if (value instanceof Integer && ((Integer) value).intValue() < 0)
					return Messages.MDataMatrix_2;
				return null;
			}
		};

		PixelPropertyDescriptor minSymHeight = new PixelPropertyDescriptor(
				DataMatrixComponent.PROPERTY_MIN_SYMBOL_HEIGHT, Messages.MDataMatrix_3);
		minSymHeight.setCategory(Messages.MDataMatrix_properties_category);
		minSymHeight.setDescription(Messages.MDataMatrix_4);
		desc.add(minSymHeight);
		minSymHeight.setValidator(validator);

		PixelPropertyDescriptor maxSymHeight = new PixelPropertyDescriptor(
				DataMatrixComponent.PROPERTY_MAX_SYMBOL_HEIGHT, Messages.MDataMatrix_5);
		maxSymHeight.setCategory(Messages.MDataMatrix_properties_category);
		maxSymHeight.setDescription(Messages.MDataMatrix_6);
		desc.add(maxSymHeight);
		maxSymHeight.setValidator(validator);

		PixelPropertyDescriptor minSymWidth = new PixelPropertyDescriptor(DataMatrixComponent.PROPERTY_MIN_SYMBOL_WIDTH,
				Messages.MDataMatrix_7);
		minSymWidth.setCategory(Messages.MDataMatrix_properties_category);
		minSymWidth.setDescription(Messages.MDataMatrix_8);
		desc.add(minSymWidth);
		minSymWidth.setValidator(validator);

		PixelPropertyDescriptor maxSymWidth = new PixelPropertyDescriptor(DataMatrixComponent.PROPERTY_MAX_SYMBOL_WIDTH,
				Messages.MDataMatrix_9);
		maxSymWidth.setCategory(Messages.MDataMatrix_properties_category);
		maxSymWidth.setDescription(Messages.MDataMatrix_10);
		desc.add(maxSymWidth);
		maxSymWidth.setValidator(validator);
	}

	@Override
	protected Map<String, DefaultValue> createDefaultsMap() {
		Map<String, DefaultValue> defaultsMap = super.createDefaultsMap();
		defaultsMap.put(DataMatrixComponent.PROPERTY_MIN_SYMBOL_HEIGHT, new DefaultValue(null, true));
		defaultsMap.put(DataMatrixComponent.PROPERTY_MAX_SYMBOL_HEIGHT, new DefaultValue(null, true));
		defaultsMap.put(DataMatrixComponent.PROPERTY_MIN_SYMBOL_WIDTH, new DefaultValue(null, true));
		defaultsMap.put(DataMatrixComponent.PROPERTY_MAX_SYMBOL_WIDTH, new DefaultValue(null, true));

		return defaultsMap;
	}

	@Override
	public Object getPropertyValue(Object id) {
		JRDesignComponentElement jrElement = (JRDesignComponentElement) getValue();
		DataMatrixComponent jrList = (DataMatrixComponent) jrElement.getComponent();

		if (id.equals(DataMatrixComponent.PROPERTY_SHAPE))
			return DataMatrixShape.getPos4Shape(jrList.getShape());

		if (id.equals(DataMatrixComponent.PROPERTY_MIN_SYMBOL_HEIGHT))
			return jrList.getMinSymbolHeight();
		if (id.equals(DataMatrixComponent.PROPERTY_MAX_SYMBOL_HEIGHT))
			return jrList.getMaxSymbolHeight();
		if (id.equals(DataMatrixComponent.PROPERTY_MIN_SYMBOL_WIDTH))
			return jrList.getMinSymbolWidth();
		if (id.equals(DataMatrixComponent.PROPERTY_MAX_SYMBOL_WIDTH))
			return jrList.getMaxSymbolWidth();

		return super.getPropertyValue(id);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		JRDesignComponentElement jrElement = (JRDesignComponentElement) getValue();
		DataMatrixComponent jrList = (DataMatrixComponent) jrElement.getComponent();

		if (id.equals(DataMatrixComponent.PROPERTY_SHAPE))
			jrList.setShape(DataMatrixShape.getShape4Pos((Integer) value));
		else if (id.equals(DataMatrixComponent.PROPERTY_MIN_SYMBOL_HEIGHT))
			jrList.setMinSymbolHeight((Integer) value);
		else if (id.equals(DataMatrixComponent.PROPERTY_MAX_SYMBOL_HEIGHT))
			jrList.setMaxSymbolHeight((Integer) value);
		else if (id.equals(DataMatrixComponent.PROPERTY_MIN_SYMBOL_WIDTH))
			jrList.setMinSymbolWidth((Integer) value);
		else if (id.equals(DataMatrixComponent.PROPERTY_MAX_SYMBOL_WIDTH))
			jrList.setMaxSymbolWidth((Integer) value);
		else
			super.setPropertyValue(id, value);
	}

	@Override
	public HashSet<String> generateGraphicalProperties() {
		HashSet<String> properties = super.generateGraphicalProperties();
		properties.add(DataMatrixComponent.PROPERTY_SHAPE);
		return properties;
	}

	@Override
	public void trasnferProperties(JRElement target) {
		super.trasnferProperties(target);

		JRDesignComponentElement jrSourceElement = (JRDesignComponentElement) getValue();
		DataMatrixComponent jrSourceBarcode = (DataMatrixComponent) jrSourceElement.getComponent();

		JRDesignComponentElement jrTargetElement = (JRDesignComponentElement) target;
		DataMatrixComponent jrTargetBarcode = (DataMatrixComponent) jrTargetElement.getComponent();

		jrTargetBarcode.setShape(getStringClone(jrSourceBarcode.getShape()));

		jrTargetBarcode.setMinSymbolHeight(jrSourceBarcode.getMinSymbolHeight());
		jrTargetBarcode.setMaxSymbolHeight(jrSourceBarcode.getMaxSymbolHeight());
		jrTargetBarcode.setMinSymbolWidth(jrSourceBarcode.getMinSymbolWidth());
		jrTargetBarcode.setMaxSymbolWidth(jrSourceBarcode.getMaxSymbolWidth());
	}
}
