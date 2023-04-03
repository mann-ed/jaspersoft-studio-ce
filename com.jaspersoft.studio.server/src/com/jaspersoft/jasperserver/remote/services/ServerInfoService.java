/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.jasperserver.remote.services;

import com.jaspersoft.jasperserver.dto.serverinfo.ServerInfo;

/**
 * @author Volodya Sabadosh (vsabadosh@jaspersoft.com)
 * @version $Id $
 */
public interface ServerInfoService {

    ServerInfo getServerInfo();
}
