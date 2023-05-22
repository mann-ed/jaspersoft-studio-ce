/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/


package com.jaspersoft.jasperserver.api.common.service;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ClassMappingsObjectFactory.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 */
public interface ClassMappingsObjectFactory {

	Object getClassObject(String type);
	
}
