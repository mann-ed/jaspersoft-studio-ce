/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.jasperserver.api;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JSException.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 */
@JasperServerAPI
public class JSException extends RuntimeException {

	protected Object[] args;
	private boolean wrapperObject;
	
	public JSException(String message) {
		super(message);
	}

	public JSException(String message, Throwable cause) {
		super(message, cause);
		this.wrapperObject = true;
	}

	public JSException(Throwable cause) {
		super(cause);
		this.wrapperObject = true;
	}

	public JSException(String message, Object[] args) {
		super(message);
		this.args = args;
	}

	/**
	 * @return Returns the args.
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * @param args The args to set.
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}

	/**
	 * @return Returns the wrapperObject.
	 */
	public boolean isWrapperObject() {
		return wrapperObject;
	}

	/**
	 * @param wrapperObject The wrapperObject to set.
	 */
	public void setWrapperObject(boolean wrapperObject) {
		this.wrapperObject = wrapperObject;
	}
}
