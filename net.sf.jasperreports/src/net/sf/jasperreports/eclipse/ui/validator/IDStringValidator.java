/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui.validator;

import net.sf.jasperreports.eclipse.messages.Messages;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class IDStringValidator implements IValidator<String> {
	private final static char[] allowed = "_.".toCharArray(); //$NON-NLS-1$

	// private Pattern pattern = Pattern.compile("^[A-Za-z0-9_.//-]{0,100}$");
	// //$NON-NLS-1$

	public IStatus validate(String v) {
		if (v == null || v.isEmpty())
			return ValidationStatus.error(Messages.IDStringValidator_EmptyError);
		if (v.length() > 100)
			return ValidationStatus.error("ID size between 0 and 100");
		for (char c : v.toCharArray()) {
			if (Character.isLetterOrDigit(c))
				continue;
			boolean isAllowed = false;
			for (char a : allowed) {
				if (c == a) {
					isAllowed = true;
					break;
				}
			}
			if (!isAllowed)
				return ValidationStatus.error(Messages.IDStringValidator_InvalidChars);
		}
		return Status.OK_STATUS;
	}

	public static String safeChar(String input) {
		char[] charArray = input.toString().toCharArray();
		StringBuilder result = new StringBuilder();
		for (char c : charArray) {
			Character newc = null;
			if (Character.isLetterOrDigit(c))
				newc = c;
			else
				for (char a : allowed) {
					if (c == a) {
						newc = c;
						break;
					}
				}
			if (newc == null)
				newc = '_';
			result.append(newc);
		}
		return result.toString();
	}
}
