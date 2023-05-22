/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.common.service;

/**
 * @author swood
 *
 */
public interface BeanForInterfaceImplementationFactory {

	public String getBeanName(Class itfClass);

	public Object getBean(Class itfClass);

}
