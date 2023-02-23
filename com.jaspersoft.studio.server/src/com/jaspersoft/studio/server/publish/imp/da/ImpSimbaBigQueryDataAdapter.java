/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.publish.imp.da;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;

public class ImpSimbaBigQueryDataAdapter extends AImpJdbcDataAdapter {
	public static final String DNAME = "com.simba.googlebigquery.jdbc41.Driver";

	public ImpSimbaBigQueryDataAdapter() {
		super(DNAME, new String[][] { { "OAuthPvtKeyPath", ResourceDescriptor.TYPE_SECURE_FILE } });
	}

	@Override
	protected String getJdbcPrefix() {
		return "jdbc:bigquery:";
	}

}
