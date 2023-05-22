/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.common.service;

import java.util.List;
import java.util.Map;

/**
 * @author swood
 *
 */
public interface ObjectFactory {

	public Class getImplementationClass(Map classMappings, Class itfClass);

	public Class getImplementationClass(Map classMappings, String id);
	
	public String getImplementationClassName(Map classMappings, Class itfClass);

	public String getImplementationClassName(Map classMappings, String id);

	public Class getInterface(Map classMappings, Class _class);

	public String getIdForClass(Map classMappings, Class _class);

	public String getInterfaceName(Map classMappings, Class _class);

	public Object newObject(Map classMappings, Class itfClass);

	public Object newObject(Map classMappings, String id);
	
	public List getKeys(Map classMappings);
	
}
