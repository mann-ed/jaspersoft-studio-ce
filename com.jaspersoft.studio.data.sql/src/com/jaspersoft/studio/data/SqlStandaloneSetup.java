/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class SqlStandaloneSetup extends SqlStandaloneSetupGenerated{

	public static void doSetup() {
		new SqlStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

