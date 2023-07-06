/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.descriptor.propexpr;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.help.HelpSystem;
import com.jaspersoft.studio.property.descriptor.text.NTextPropertyDescriptor;
import com.jaspersoft.studio.property.section.AbstractSection;
import com.jaspersoft.studio.property.section.widgets.SPPropertyExpressionButton;

public class JPropertyExpressionsDescriptor extends NTextPropertyDescriptor {
	private boolean forceStandardEditing=false;

	public JPropertyExpressionsDescriptor(Object id, String displayName) {
		this(id, displayName, false);
	}

	public JPropertyExpressionsDescriptor(Object id, String displayName, boolean forceStandardEditing) {
		super(id, displayName);
		this.forceStandardEditing=forceStandardEditing;
	}

	public CellEditor createPropertyEditor(Composite parent) {
		JPropertyExpressionsCellEditor editor = new JPropertyExpressionsCellEditor(parent,true,forceStandardEditing);
		HelpSystem.bindToHelp(this, editor.getControl());
		return editor;
	}

	@Override
	public ILabelProvider getLabelProvider() {
		if (isLabelProviderSet())
			return super.getLabelProvider();
		return new JPropertyExpressionsLabelProvider();
	}

	@Override
	public SPPropertyExpressionButton createWidget(Composite parent, AbstractSection section) {
		return new SPPropertyExpressionButton(parent, section, this, getDisplayName());
	}
}
