
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

