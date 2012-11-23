/*******************************************************************************
 * Copyright (C) 2010 - 2012 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, 
 * the following license terms apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
package com.jaspersoft.studio.property.descriptor.classname;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.property.section.AbstractSection;
import com.jaspersoft.studio.property.section.widgets.ASPropertyWidget;
import com.jaspersoft.studio.property.section.widgets.IPropertyDescriptorWidget;
import com.jaspersoft.studio.property.section.widgets.SPClassType;

public class NClassTypePropertyDescriptor extends ClassTypePropertyDescriptor implements IPropertyDescriptorWidget {

	/**
	 *  Field to check if the widget should be read only
	 */
	private boolean readOnly;
	
	public NClassTypePropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
		readOnly = false;
	}
	
	public void setReadOnly(boolean value){
		readOnly = value;
	}
	
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new ClassTypeCellEditor(parent);
		editor.setValidator(NClassTypeCellEditorValidator.instance());
		setValidator(NClassTypeCellEditorValidator.instance());
		return editor;
	}

	public ASPropertyWidget createWidget(Composite parent, AbstractSection section) {
		ASPropertyWidget classNameWidget = new SPClassType(parent, section, this);
		classNameWidget.setReadOnly(readOnly);
		return classNameWidget;
	}
}
