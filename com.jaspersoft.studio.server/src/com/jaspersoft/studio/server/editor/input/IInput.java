/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.editor.input;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public interface IInput {
	public void createControl(Composite parent, int style);

	public void fillControl();

	public void updateInput();

	public Control getControl();
}
