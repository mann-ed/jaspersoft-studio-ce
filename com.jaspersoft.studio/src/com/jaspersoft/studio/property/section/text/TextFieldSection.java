/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.property.section.text;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.properties.view.TabbedPropertySheetPage;
import com.jaspersoft.studio.property.section.AbstractRealValueSection;
import com.jaspersoft.studio.property.section.widgets.SPEvaluationTime;

import net.sf.jasperreports.engine.base.JRBaseStyle;
import net.sf.jasperreports.engine.base.JRBaseTextField;
import net.sf.jasperreports.engine.design.JRDesignTextField;

/*
 * The location section on the location tab.
 * 
 * @author Chicu Veaceslav
 */
public class TextFieldSection extends AbstractRealValueSection {

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 * org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);

		parent.setLayout(new GridLayout(2, false));

		createWidget4Property(parent, JRDesignTextField.PROPERTY_EXPRESSION);

		IPropertyDescriptor pd = getPropertyDesriptor(JRDesignTextField.PROPERTY_EVALUATION_TIME);
		IPropertyDescriptor gpd = getPropertyDesriptor(JRDesignTextField.PROPERTY_EVALUATION_GROUP);
		getWidgetFactory().createCLabel(parent, pd.getDisplayName());
		widgets.put(pd.getId(), new SPEvaluationTime(parent, this, pd, gpd));

		GridData gd = new GridData();
		gd.horizontalSpan = 2;

		createWidget4Property(parent, JRBaseTextField.PROPERTY_TEXT_ADJUST);

		createWidget4Property(parent, JRBaseStyle.PROPERTY_BLANK_WHEN_NULL, false).getControl().setLayoutData(gd);

		createWidget4Property(parent, JRBaseStyle.PROPERTY_PATTERN);

		createWidget4Property(parent, JRDesignTextField.PROPERTY_PATTERN_EXPRESSION);
	}

	@Override
	protected void initializeProvidedProperties() {
		super.initializeProvidedProperties();
		addProvidedProperties(JRDesignTextField.PROPERTY_EXPRESSION, Messages.common_expression);
		addProvidedProperties(JRDesignTextField.PROPERTY_EVALUATION_TIME, Messages.common_evaluation_time);
		addProvidedProperties(JRBaseTextField.PROPERTY_TEXT_ADJUST,
				Messages.MTextField_MTextField_text_adjust_description);
		addProvidedProperties(JRBaseStyle.PROPERTY_BLANK_WHEN_NULL, Messages.common_blank_when_null);
		addProvidedProperties(JRBaseStyle.PROPERTY_PATTERN, Messages.common_pattern);
		addProvidedProperties(JRDesignTextField.PROPERTY_PATTERN_EXPRESSION,
				Messages.MTextField_patternExpressionTitle);
	}

}
