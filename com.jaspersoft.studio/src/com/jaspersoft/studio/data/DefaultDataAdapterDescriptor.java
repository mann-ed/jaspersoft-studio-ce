/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.engine.JRConstants;

public class DefaultDataAdapterDescriptor extends DataAdapterDescriptor {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	@Override
	public DataAdapter getDataAdapter() {
		return dataAdapter;
	}

}
