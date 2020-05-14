/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.publish.imp.da;

public class ImpBigQueryDataAdapter extends AImpJdbcDataAdapter {
	public static final String dname = "tibcosoftware.jdbc.googlebigquery.GoogleBigQueryDriver";

	public ImpBigQueryDataAdapter() {
		super(dname, new String[] { "ServiceAccountPrivateKey" });
	}

	@Override
	protected String getJdbcPrefix() {
		return "jdbc:tibcosoftware:googlebigquery:";
	}

}
