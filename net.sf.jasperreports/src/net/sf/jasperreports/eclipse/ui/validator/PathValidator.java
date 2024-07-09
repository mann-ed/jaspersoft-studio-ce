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
import net.sf.jasperreports.eclipse.util.Misc;

public class PathValidator implements IValidator<String> {
	private final static char[] allowed = "_./".toCharArray(); //$NON-NLS-1$

	private boolean optional = true;

	// private Pattern pattern = Pattern.compile("^[A-Za-z0-9_.//-]{0,100}$");
	// //$NON-NLS-1$
	public PathValidator(boolean optional) {
		this.optional = optional;
	}

	public IStatus validate(String v) {
		if (optional && Misc.isNullOrEmpty(v))
			return Status.OK_STATUS;
		if (!v.startsWith("/"))
			return ValidationStatus.error("Path is not starting with /");
		String[] m = v.split("/");
		for (String vm : m) {
			if (vm.length() > 100)
				return ValidationStatus.error("ID size between 0 and 100");
			for (char c : vm.toCharArray()) {
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
