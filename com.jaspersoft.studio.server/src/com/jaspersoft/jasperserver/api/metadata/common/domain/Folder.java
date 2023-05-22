/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.metadata.common.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;

/**
 * Folder is the interface which represents the folder in the JasperServer. It extends
 * {@link com.jaspersoft.jasperserver.api.metadata.common.domain.Resource}
 *
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: Folder.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 * @see com.jaspersoft.jasperserver.api.metadata.common.domain.client.FolderImpl
 */
@JasperServerAPI
public interface Folder extends Resource {
	static final String SEPARATOR = "/";
	static final int SEPARATOR_LENGTH = SEPARATOR.length();
}
