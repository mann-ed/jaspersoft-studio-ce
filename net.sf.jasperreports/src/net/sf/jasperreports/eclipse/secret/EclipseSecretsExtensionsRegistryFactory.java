/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.secret;

import java.util.Collections;
import java.util.List;

import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.extensions.ExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsRegistryFactory;
import net.sf.jasperreports.util.SecretsProviderFactory;

/*
 * @author veaceslav chicu (schicu@jaspersoft.com)
 * @version $Id: EclipseSecretsExecuterExtensionsRegistryFactory.java 649 2013-05-13 06:56:52Z chicuslavic $
 */
public class EclipseSecretsExtensionsRegistryFactory implements ExtensionsRegistryFactory {
	private static final ExtensionsRegistry defaultExtensionsRegistry = new ExtensionsRegistry() {
		public List getExtensions(Class extensionType) {
			if (SecretsProviderFactory.class.equals(extensionType))
				return Collections.singletonList(EclipseSecretsProviderFactory.getInstance());
			return null;
		}
	};

	public ExtensionsRegistry createRegistry(String registryId, JRPropertiesMap properties) {
		return defaultExtensionsRegistry;
	}
}
