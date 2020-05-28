/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.publish.imp.da;

import net.sf.jasperreports.data.jdbc.GbqSimbaDataAdapterService;

public class ImpSimbaBigQueryDataAdapter extends AImpJdbcDataAdapter {
	public static final String dname = "com.simba.googlebigquery.jdbc41.Driver";

	public ImpSimbaBigQueryDataAdapter() {
		super(dname, new String[] { GbqSimbaDataAdapterService.GBQ_CONNECTION_PARAMETER_PRIVATE_KEY });
	}

	@Override
	protected String getJdbcPrefix() {
		return "jdbc:bigquery:";
	}

}
