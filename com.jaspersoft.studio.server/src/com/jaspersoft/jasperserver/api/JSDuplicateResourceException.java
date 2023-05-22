/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.jasperserver.api;

/**
 * @author Ionut Nedelcu (ionutned@users.sourceforge.net)
 * @version $Id
 */
public class JSDuplicateResourceException extends JSException
{
	public JSDuplicateResourceException(Throwable cause)
	{
		super(cause);
	}

	public JSDuplicateResourceException(String message)
	{
		super(message);
	}

	public JSDuplicateResourceException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public JSDuplicateResourceException(String message,  Object[] args)
	{
		super(message, args);
	}

}
