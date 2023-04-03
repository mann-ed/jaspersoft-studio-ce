/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/


package com.jaspersoft.jasperserver.api;

/**
 * This exception should be thrown to indicate that some
 * feature is not implemented yet.
 * On UI side user should see friendly message based on this exception.
 *
 * @author Sergey Prilukin
 * @version $Id: JSNotImplementedException.java 20440 2011-05-03 22:54:35Z asokolnikov $
 */
@JasperServerAPI
public class JSNotImplementedException extends RuntimeException {

    public JSNotImplementedException() {
        super("Feature is not implemented.");
    }

    public JSNotImplementedException(String message) {
        super(message);
    }
}
