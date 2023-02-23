/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api;


/**
 * Runtime Exception for security validation errors
 *
 * @author Anton Fomin
 * @version $Id: JSSecurityException.java 25479 2012-10-25 19:15:37Z dlitvak $
 */
public class JSSecurityException extends JSException {

    public JSSecurityException(String message) {
        super(message);
    }

	public JSSecurityException(String message, Throwable cause) {
		super(message, cause);
	}
}
