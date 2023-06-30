/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.descriptor.propexpr;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.jaspersoft.studio.property.descriptor.EditableDialogCellEditor;
import com.jaspersoft.studio.property.descriptor.propexpr.dialog.JRPropertyExpressionEditor;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;

public class JPropertyExpressionsCellEditor extends EditableDialogCellEditor {
	private boolean showExpression = true;
	private boolean forceStandardEditing = false;

	public JPropertyExpressionsCellEditor(Composite parent) {
		this(parent, true, false);
	}

	public JPropertyExpressionsCellEditor(Composite parent, boolean showExpression) {
		this(parent,showExpression,false);
	}
	
	public JPropertyExpressionsCellEditor(Composite parent, boolean showExpression, boolean forceStandardEditing) {
		super(parent);
		this.showExpression = showExpression;
		this.forceStandardEditing = forceStandardEditing;
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		JRPropertyExpressionEditor wizard = new JRPropertyExpressionEditor(forceStandardEditing);
		wizard.setShowExpression(showExpression);
		// clone the object to avoid side effect
		wizard.setValue(((PropertyExpressionsDTO) getValue()).clone());
		WizardDialog dialog = new WizardDialog(UIUtils.getShellForWizardDialog(), wizard);
		if (dialog.open() == Dialog.OK)
			return wizard.getValue();
		return null;
	}

	private JPropertyExpressionsLabelProvider labelProvider;

	@Override
	protected void updateContents(Object value) {
		if (getDefaultLabel() == null)
			return;
		if (labelProvider == null)
			labelProvider = new JPropertyExpressionsLabelProvider();
		String text = labelProvider.getText(value);
		getDefaultLabel().setText(text);
	}
}
