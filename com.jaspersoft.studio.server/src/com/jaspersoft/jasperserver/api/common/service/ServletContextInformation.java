/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/


package com.jaspersoft.jasperserver.api.common.service;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ServletContextInformation.java 8408 2007-05-29 23:29:12Z melih $
 */
public interface ServletContextInformation {

	boolean jspExists(String path);
	
}
