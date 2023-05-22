/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/


package com.jaspersoft.jasperserver.api;

/**
 * This is general approach for display error message without full stackTrace
 * On UI(errorPage.jsp) side user should see friendly message based on this exception.
 *
 * @author Roman Kuziv
 * @version $Id: JSShowOnlyErrorMessage.java 20327 2011-04-08 10:05:27Z roman.kuziv $
 */

@JasperServerAPI
public class JSShowOnlyErrorMessage extends JSException {
    public JSShowOnlyErrorMessage(String message) {
        super(message);
    }

    public JSShowOnlyErrorMessage(String message, Object[] args) {
		super(message);
		this.args = args;
	}
}
