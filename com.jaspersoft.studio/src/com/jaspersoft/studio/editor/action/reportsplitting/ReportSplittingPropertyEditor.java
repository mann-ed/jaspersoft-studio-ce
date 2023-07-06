/*******************************************************************************
 * Copyright (C) 2010 - 2023. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.action.reportsplitting;

import java.util.List;
import java.util.function.Predicate;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionDTO;
import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionsDTO;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.widgets.framework.IPropertyEditor;

import net.sf.jasperreports.engine.JRExpression;

/**
 * Editor for report splitting properties.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public class ReportSplittingPropertyEditor implements IPropertyEditor {
	
	private PropertyExpressionsDTO pExpressionsDTO;
	private JasperReportsConfiguration jconfig;

	public ReportSplittingPropertyEditor(PropertyExpressionsDTO pExpressionsDTO, JasperReportsConfiguration jconfig) {
		this.pExpressionsDTO = pExpressionsDTO;
		this.jconfig = jconfig;
	}

	public PropertyExpressionDTO getProperty(String propertyName) {
		for(PropertyExpressionDTO pExpr : pExpressionsDTO.getProperties()) {
			if(pExpr.getName().equals(propertyName)) {
				return pExpr;
			}
		}
		return null;
	}
	
	public void addProperty(PropertyExpressionDTO newProperty) {
		pExpressionsDTO.getProperties().add(newProperty);
	}
	
	@Override
	public String getPropertyValue(String propertyName) {
		PropertyExpressionDTO property = getProperty(propertyName);
		if(property!=null && !property.isExpression()) {
			return property.getValue();
		}
		return null;
	}

	@Override
	public JRExpression getPropertyValueExpression(String propertyName) {
		PropertyExpressionDTO property = getProperty(propertyName);
		if(property!=null && property.isExpression()) {
			return property.getValueAsExpression();
		}
		return null;
	}
	
	/**
	 * @return the list of all properties to which the editor has access to
	 */
	public List<PropertyExpressionDTO> getAllProperties() {
		return pExpressionsDTO.getProperties();
	}
	
	public PropertyExpressionsDTO getPropertiesDTO() {
		return pExpressionsDTO;
	}
	
	@Override
	public void createUpdateProperty(String propertyName, String value, JRExpression valueExpression) {
		removeProperty(propertyName);
		if(value!=null || valueExpression!=null) {
			this.pExpressionsDTO.addProperty(
					propertyName, getValueForPropertyCreation(value, valueExpression), shouldBeExpression(value, valueExpression), false);
		}
	}

	/*
	 * Gets the text value to be used when creating a new property, depending on the
	 * possibility of being an expression or a simple text one.
	 */
	private String getValueForPropertyCreation(String value, JRExpression valueExpression) {
		if(shouldBeExpression(value, valueExpression)) {
			return valueExpression.getText();
		}
		else {
			return value;
		}
	}
	
	/*
	 * Checks whether a possible new property should be an expression or simple text one.
	 * Only one between value and valueExpression can be not null.
	 */
	private boolean shouldBeExpression(String value, JRExpression valueExpression) {
		if(value==null && valueExpression!=null) {
			return true;
		}
		if(value!=null && valueExpression==null) {
			return false;
		}
		throw new RuntimeException(Messages.ReportSplittingPropertyEditor_RuntimeExceptionMsg);
	}

	@Override
	public void removeProperty(String propertyName) {
		PropertyExpressionDTO existingProperty = getProperty(propertyName);
		if(existingProperty!=null) {
			this.pExpressionsDTO.getProperties().remove(existingProperty);
		}
	}

	/**
	 * Removes all report splitting properties.
	 */
	public void removeAllSplittingProperties() {
		Predicate<PropertyExpressionDTO> isSplittingProperty = item -> item.getName().startsWith(ReportSplittingEditDialog.PART_PREFIX_PROPERTY);
		this.pExpressionsDTO.getProperties().removeIf(isSplittingProperty);
	}

}
