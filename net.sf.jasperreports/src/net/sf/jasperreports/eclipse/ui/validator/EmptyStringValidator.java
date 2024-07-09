/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui.validator;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import net.sf.jasperreports.eclipse.messages.Messages;

public class EmptyStringValidator implements IValidator<String> {

	public IStatus validate(String value) {
		if (value == null || value.isEmpty())
			return ValidationStatus.error(Messages.EmptyStringValidator_EmptyError);
		return Status.OK_STATUS;
	}

}
