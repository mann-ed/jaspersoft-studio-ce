/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.property.descriptor.expression;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;

import com.jaspersoft.studio.editor.JrxmlEditor;
import com.jaspersoft.studio.editor.expression.ExpressionContext;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.property.descriptor.text.NTextPropertyDescriptor;
import com.jaspersoft.studio.property.section.AbstractSection;
import com.jaspersoft.studio.property.section.widgets.ASPropertyWidget;
import com.jaspersoft.studio.property.section.widgets.IPropertyDescriptorWidget;
import com.jaspersoft.studio.property.section.widgets.SPExpression;
import com.jaspersoft.studio.utils.SelectionHelper;


/**
 * The print when property expression is a special kind of widget because it doesn't have
 * a preset expression context, because the context for a print when is always the main
 * dataset
 * 
 * @author Orlandin Marco
 *
 */
public class JRPrintWhenExpressionPropertyDescriptor extends NTextPropertyDescriptor
		implements IPropertyDescriptorWidget {

	protected SPExpression expEditor;

	public JRPrintWhenExpressionPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
		setLabelProvider(new JRExpressionLabelProvider());
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		IEditorPart activeJRXMLEditor = SelectionHelper.getActiveJRXMLEditor();
		ExpressionContext context = null;
		if (activeJRXMLEditor != null && activeJRXMLEditor instanceof JrxmlEditor) {
			JrxmlEditor editor = (JrxmlEditor)activeJRXMLEditor;
			context = new ExpressionContext(editor.getModel().getJasperDesign().getMainDesignDataset(), ((ANode)editor.getModel()).getJasperConfiguration());
		}
		return new JRExpressionCellEditor(parent, context);
	}

	@Override
	public ILabelProvider getLabelProvider() {
		if (isLabelProviderSet()) {
			return super.getLabelProvider();
		}
		return new JRExpressionLabelProvider();
	}

	public ASPropertyWidget<?> createWidget(Composite parent, AbstractSection section) {
		expEditor = new SPExpression(parent, section, this);
		expEditor.setTraverseOnTab(true);
		APropertyNode selectedElement = section.getSelectedElement();
		expEditor.setExpressionContext(new ExpressionContext(selectedElement.getJasperDesign().getMainDesignDataset(), selectedElement.getJasperConfiguration()));
		return expEditor;
	}
}
