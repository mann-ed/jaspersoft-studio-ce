/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api;

import com.jaspersoft.jasperserver.api.common.domain.ValidationErrors;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JSValidationException.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 */
public class JSValidationException extends JSException {
	
	private static final long serialVersionUID = 1L;

	private final ValidationErrors errors;
	
	public JSValidationException(ValidationErrors errors) {
		super(errors.toString());
		
		this.errors = errors;
	}
	
	public ValidationErrors getErrors() {
		return errors;
	}
	
}
