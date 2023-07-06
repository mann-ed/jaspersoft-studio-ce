/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.publish.imp.da;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;

public class ImpAutoRestDataAdapter extends AImpJdbcDataAdapter {
	public static final String DNAME = "tibcosoftware.jdbc.mongodb.MongoDBDriver";

	public ImpAutoRestDataAdapter() {
		super(DNAME, new String[][] { { "config", ResourceDescriptor.TYPE_CONTENT_RESOURCE } });
	}

	@Override
	protected String getJdbcPrefix() {
		return "jdbc:tibcosoftware:autorest:";
	}

}
