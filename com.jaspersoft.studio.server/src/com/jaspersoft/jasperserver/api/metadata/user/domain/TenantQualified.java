/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/


package com.jaspersoft.jasperserver.api.metadata.user.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;

/**
 * An interface which makes an object be aware of the owning tenant
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: TenantQualified.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 */
@JasperServerAPI
public interface TenantQualified {

    /**
     * Returns tenant id
     * @return
     */
    String getTenantId();

    /**
     * Sets tenant id
     * @param tenantId
     */
    void setTenantId(String tenantId);

}
