/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.common.properties;

/**
 * PropertyChanger
 *
 * This helper interface is used by PropertiesManagementServiceImpl
 * to apply configuration properties to the different services.
 * 
 * @author udavidovich
 */
public interface PropertyChanger {

    /**
     * setProperty
     * @param key should be fully qualified, must not be null
     * @param val value as String
     */
    public void setProperty(String key, String val);

    /**
     * getProperty
     * @param key must not be null
     * @return associated value or null
     */
    public String getProperty(String key);

    /**
     * Called when property was removed from prop file.
     *
     * @param key should be fully qualified, must not be null
     * @param val value as String
     */
    public void removeProperty(String key, String val);


}
