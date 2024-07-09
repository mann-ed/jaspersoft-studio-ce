/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.secret;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.util.SecretsProvider;
import net.sf.jasperreports.util.SecretsProviderFactory;

/**
 * {@link SecretsProviderFactory} for the generic Jaspersoft Studio sensitive information.
 */
public class EclipseSecretsProviderFactory implements SecretsProviderFactory {
	
	private static final List<String> categories;
	private static EclipseSecretsProviderFactory instance;
	private EclipseSecretsProvider eclipseSecretsProvider;
	
	static {
		categories = new ArrayList<String>(1);
		categories.add(EclipseSecretsProvider.SECRET_NODE_ID);
	}

	private EclipseSecretsProviderFactory() {
	}

	public static EclipseSecretsProviderFactory getInstance() {
		if (instance == null)
			instance = new EclipseSecretsProviderFactory();
		return instance;
	}

	@Override
	public SecretsProvider getSecretsProvider(String category) {
		if(categories.contains(category)) {
			if (eclipseSecretsProvider == null)
				eclipseSecretsProvider = new EclipseSecretsProvider();
			return eclipseSecretsProvider;
		}
		return null;
	}

}
