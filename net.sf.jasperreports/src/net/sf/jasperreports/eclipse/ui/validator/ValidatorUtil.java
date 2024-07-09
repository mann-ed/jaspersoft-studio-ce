/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui.validator;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport;
import org.eclipse.jface.databinding.fieldassist.ControlDecorationUpdater;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

public class ValidatorUtil {

	public static void controlDecorator(Binding binding, final Button okButton) {
		ControlDecorationSupport.create(binding, SWT.TOP | SWT.LEFT, null, new ControlDecorationUpdater() {
			@Override
			protected void update(ControlDecoration decoration, IStatus status) {
				super.update(decoration, status);
				if (okButton != null)
					okButton.setEnabled(status.isOK());
			}
		});
	}

}
