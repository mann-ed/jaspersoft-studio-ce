/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.jrexpressions;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class JavaJRExpressionStandaloneSetup extends JavaJRExpressionStandaloneSetupGenerated{

	public static void doSetup() {
		new JavaJRExpressionStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

