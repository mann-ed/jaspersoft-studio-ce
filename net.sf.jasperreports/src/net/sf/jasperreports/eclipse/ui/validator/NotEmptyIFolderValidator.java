/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui.validator;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class NotEmptyIFolderValidator implements IValidator {

	public IStatus validate(Object value) {
		if (value != null && !((String) value).isEmpty()) {
			// IResource f =
			// ResourcesPlugin.getWorkspace().getRoot().getContainerForLocation(new
			// Path((String) value));
			// if (f == null || !f.exists() || !(f instanceof IFolder))
			// return ValidationStatus.error("The folder does not exists.");
		}
		return Status.OK_STATUS;
	}

}
