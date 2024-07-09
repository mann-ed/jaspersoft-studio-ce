/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui.validator;

import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

public class URLValidator extends EmptyStringValidator {

	@Override
	public IStatus validate(String value) {
		IStatus st = super.validate(value);
		if (st.isOK() && !((String) value).matches("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"))
			return ValidationStatus.error("URL is wrong");
		return st;
	}
}
