/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.common.service;

import java.util.List;

/**
 * 
 * Maps from interfaces to implementations and back
 * 
 * @author swood
 *
 */
public interface ImplementationObjectFactory {
	public Class getImplementationClass(Class _class);
	public Class getImplementationClass(String id);
	public String getImplementationClassName(Class _class);
	public String getImplementationClassName(String id);
	public Class getInterface(Class _class);
	public String getInterfaceName(Class _class);
	public String getIdForClass(Class _class);
	public Object newObject(Class _class);
	public Object newObject(String id);
	public List getKeys();
}
