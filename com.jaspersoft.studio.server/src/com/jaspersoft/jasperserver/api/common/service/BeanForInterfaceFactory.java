/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.common.service;

import java.util.Map;

/**
 * @author swood
 *
 */
public interface BeanForInterfaceFactory {

	public boolean hasMapping(Map classToBeanMappings, Class _class);

	public String getBeanName(Map classToBeanMappings, Class _class);

	public Object getBean(Map classToBeanMappings, Class itfClass);

}
