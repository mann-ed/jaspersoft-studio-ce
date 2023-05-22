/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.debug;

import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.scriptlets.ScriptletFactory;
import net.sf.jasperreports.extensions.ExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsRegistryFactory;
import net.sf.jasperreports.extensions.SingletonExtensionRegistry;


/**
 * @author Veaceslav Chicu (schicu@tibco.com)
 */
public class TraceGovernorExtensionsRegistryFactory implements ExtensionsRegistryFactory
{
	private static final ExtensionsRegistry governorExtensionsRegistry = 
			new SingletonExtensionRegistry<ScriptletFactory>(
					ScriptletFactory.class, TraceGovernorFactory.getInstance());
	
	public ExtensionsRegistry createRegistry(String registryId, JRPropertiesMap properties) 
	{
		return governorExtensionsRegistry;
	}
}
