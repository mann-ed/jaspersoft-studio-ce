/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.jasperserver.remote.exception;

import com.jaspersoft.jasperserver.dto.common.ErrorDescriptor;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: RemoteException.java 39224 2013-11-01 11:27:34Z ykovalchyk $
 */
public class RemoteException extends RuntimeException {

	private ErrorDescriptor errorDescriptor;

	public RemoteException() {
		super();
		this.errorDescriptor = new ErrorDescriptor();
	}

	public RemoteException(String message) {
		super(message);
		this.errorDescriptor = new ErrorDescriptor().setMessage(message);
	}

	public RemoteException(String message, Throwable cause) {
		super(message, cause);
		this.errorDescriptor = new ErrorDescriptor().setMessage(message);
	}

	public RemoteException(Throwable cause) {
		super(cause);
		this.errorDescriptor = new ErrorDescriptor().setException(cause);
	}

	public RemoteException(ErrorDescriptor errorDescriptor) {
		super(errorDescriptor.getMessage());
		this.errorDescriptor = errorDescriptor;
	}

	public RemoteException(ErrorDescriptor errorDescriptor, Throwable e) {
		super(errorDescriptor.getMessage(), e);
		this.errorDescriptor = errorDescriptor;
	}

	public ErrorDescriptor getErrorDescriptor() {
		return errorDescriptor;
	}

	public void setErrorDescriptor(ErrorDescriptor errorDescriptor) {
		this.errorDescriptor = errorDescriptor;
	}

	public Boolean isUnexpected() {
		return getErrorDescriptor() != null
				&& ErrorDescriptor.ERROR_CODE_UNEXPECTED_ERROR.equals(getErrorDescriptor().getErrorCode());
	}
}
