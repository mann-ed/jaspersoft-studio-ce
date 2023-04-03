/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.remote.exception;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: IllegalParameterValueException.java 30161 2013-03-22 19:20:15Z
 *          inesterenko $
 */
public class IllegalParameterValueException extends RemoteException {
	public static final String ERROR_CODE = "illegal.parameter.value.error";

	public IllegalParameterValueException(String parameterName, String parameterValue) {
		this("Value of parameter " + (parameterName != null ? "'" + parameterName + "'" : "") + " invalid",
				parameterName, parameterValue);
	}

	public IllegalParameterValueException(String message, String... parameters) {
		super(message);
		setErrorDescriptor(
				new ErrorDescriptor().setMessage(message).setErrorCode(ERROR_CODE).setParameters(parameters));
	}

	public IllegalParameterValueException(ErrorDescriptor errorDescriptor) {
		super(errorDescriptor);
	}
}
