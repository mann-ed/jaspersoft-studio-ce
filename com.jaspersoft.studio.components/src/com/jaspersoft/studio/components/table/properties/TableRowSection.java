/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.table.properties;

import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.components.table.messages.Messages;
import com.jaspersoft.studio.properties.view.TabbedPropertySheetPage;
import com.jaspersoft.studio.property.section.AbstractSection;

import net.sf.jasperreports.components.table.StandardRow;

public class TableRowSection extends AbstractSection {
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);

		parent = getWidgetFactory().createSection(parent, Messages.TableRowSection_Title, false, 2);

		createWidget4Property(parent, StandardRow.PROPERTY_PRINT_WHEN_EXPRESSION);
		createWidget4Property(parent, StandardRow.PROPERTY_splitType);
	}

	@Override
	protected void initializeProvidedProperties() {
		super.initializeProvidedProperties();
		addProvidedProperties(StandardRow.PROPERTY_PRINT_WHEN_EXPRESSION, Messages.Common_PrintWhenExpr);
		addProvidedProperties(StandardRow.PROPERTY_splitType, Messages.Common_SplitType);
	}

}
