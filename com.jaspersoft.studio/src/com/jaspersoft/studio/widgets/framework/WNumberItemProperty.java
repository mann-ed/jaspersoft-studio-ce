/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.widgets.framework;

import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.utils.ValidatedDecimalFormat;
import com.jaspersoft.studio.widgets.framework.model.WidgetPropertyDescriptor;
import com.jaspersoft.studio.widgets.framework.ui.ItemPropertyDescription;

import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignExpression;

/**
 * This custom version of the standard {@link WItemProperty} is meant to be used
 * with number widgets where it is not possible to rely on the standard value string
 * for the property value.
 * <p>
 * 
 * In this case a custom expression (true/false) is generated whenever the simple
 * mode interface is used to set the property value itself.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class WNumberItemProperty extends WItemProperty {

	public WNumberItemProperty(Composite parent, int style, ItemPropertyDescription<?> widgetDescriptor,
			IPropertyEditor editor) {
		super(parent, style, widgetDescriptor, editor);
	}

	public WNumberItemProperty(Composite parent, int style, WidgetPropertyDescriptor descriptor,
			ItemPropertyDescription<?> widgetDescriptor, IPropertyEditor editor) {
		super(parent, style, descriptor, widgetDescriptor, editor);
	}

	@Override
	public boolean isExpressionMode() {
		if(isCustomSimpleMode()){
			return false;
		}
		else {
			return getPropertyEditor().getPropertyValueExpression(getPropertyName()) != null;			
		}
	}
	
	private boolean isCustomSimpleMode(){
		String customSimpleMode = getPropertyEditor().getPropertyValue(getPropertyName()+WItemProperty.CUSTOM_SIMPLE_MODE_SUFFIX);
		return "true".equals(customSimpleMode);
	}

	@Override
	public String getStaticValue() {
		if(isCustomSimpleMode()){
			JRExpression expressionValue = getExpressionValue();
			if(expressionValue!=null){
				String numText = expressionValue.getText();
				return numText;
//				try{
//					double parsedNum = Double.parseDouble((numText));
//					String parsedNumString = parsedNum+"";
//					if (parsedNumString != null && numText != null) {
//						int decimalPosition = parsedNumString.indexOf(ValidatedDecimalFormat.DECIMAL_SEPARATOR);
//						if (decimalPosition != -1) {
//							int unconvertedDecimal = numText.indexOf(ValidatedDecimalFormat.DECIMAL_SEPARATOR);
//							if (unconvertedDecimal == -1) {
//								parsedNumString = parsedNumString.substring(0, decimalPosition);
//							}
//						} else if (numText.endsWith("0")){
//							parsedNumString = numText;
//						}
//					}
//					return parsedNumString;
//				}
//				catch(NumberFormatException ex) {
//					// silent catch 
//				}
			}
		}
		return super.getStaticValue();
	}
	
	@Override
	public void setValue(String staticValue, JRExpression expressionValue) {
		setRefresh(true);
		try {
			if(staticValue!=null){
				String simpleMode = getPropertyName() + WItemProperty.CUSTOM_SIMPLE_MODE_SUFFIX;
				getPropertyEditor().createUpdateProperty(simpleMode, "true", null);
				expressionValue=new JRDesignExpression(staticValue);
				staticValue=null;
			}
			else {
				getPropertyEditor().removeProperty(getPropertyName() + WItemProperty.CUSTOM_SIMPLE_MODE_SUFFIX);
			}
			getPropertyEditor().createUpdateProperty(getPropertyName(), staticValue, expressionValue);
			updateWidget();
			// Notifies the listeners of the new expression
			fireModifyEvent(staticValue, expressionValue);
		} finally {
			setRefresh(false);
		}
	}
}
