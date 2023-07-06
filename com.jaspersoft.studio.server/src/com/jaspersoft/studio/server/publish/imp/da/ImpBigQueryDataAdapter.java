/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.publish.imp.da;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;

public class ImpBigQueryDataAdapter extends AImpJdbcDataAdapter {
	public static final String DNAME = "tibcosoftware.jdbc.googlebigquery.GoogleBigQueryDriver";

	public ImpBigQueryDataAdapter() {
		super(DNAME, new String[][] { { "ServiceAccountPrivateKey", ResourceDescriptor.TYPE_SECURE_FILE } });
	}

	@Override
	protected String getJdbcPrefix() {
		return "jdbc:tibcosoftware:googlebigquery:";
	}

}
