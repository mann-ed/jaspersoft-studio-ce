/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.utils;

import net.sf.jasperreports.util.SecretsUtil;

import com.jaspersoft.studio.server.secret.JRServerSecretsProvider;
import com.jaspersoft.studio.server.secret.keystore.JRKeyStoreSecretsProvider;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class Pass {
	private static SecretsUtil secretsUtil = SecretsUtil.getInstance(JasperReportsConfiguration.getDefaultInstance());

	public static String getPass(String key) {
		return secretsUtil.getSecret(JRServerSecretsProvider.SECRET_NODE_ID, key);
	}

	public static String getPassKeyStore(String key) {
		return secretsUtil.getSecret(JRKeyStoreSecretsProvider.SECRET_NODE_ID, key);
	}
}
