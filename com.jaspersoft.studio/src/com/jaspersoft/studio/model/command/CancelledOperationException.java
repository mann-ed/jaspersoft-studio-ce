/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.model.command;

/**
 * 
 * This exception could be raised during the execution of a command to  interrupt
 * the command itself. Essentially it is only a marker of a simple exception
 * 
 * @author Orlandin Marco
 *
 */
public class CancelledOperationException extends Exception {
	
	private static final long serialVersionUID = 677645812987466762L;

	public CancelledOperationException(){
		super("cancelled");
	}
}
