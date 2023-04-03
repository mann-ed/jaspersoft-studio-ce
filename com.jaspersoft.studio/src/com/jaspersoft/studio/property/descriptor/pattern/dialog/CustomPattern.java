/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.descriptor.pattern.dialog;

import java.text.Format;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.jaspersoft.studio.messages.Messages;

public class CustomPattern extends APattern {

	public CustomPattern(Composite parent, String pattern, Format formatter, Object sample, String value) {
		super(parent, formatter, sample, value);
		setPattern(pattern);
		setDescription(Messages.CustomPattern_description);
	}

	@Override
	public Control createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		Label l = new Label(container, SWT.NONE);
		l.setText(Messages.CustomPattern_Formats);
		return container;
	}

}
