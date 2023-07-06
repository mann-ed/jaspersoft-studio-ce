/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.action;


/**
 * Some commands, like the delete command can return a message to indicate if their execution can 
 * have impact on other object. This method can be used from an action to know if a command has 
 * "something to say" about its execution and eventually ask a confirm with a prompt
 * 
 * @author Orlandin Marco
 *
 */
public interface MessageProviderCommand {
	
	/**
	 * Return a message if the command want to notify something or null otherwise
	 * @return  a message if the command want to notify something or null otherwise
	 */
		public CommandMessage getMessage();
		
}
