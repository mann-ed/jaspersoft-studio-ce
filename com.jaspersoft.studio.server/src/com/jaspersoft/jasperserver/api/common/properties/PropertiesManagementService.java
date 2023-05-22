/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.jasperserver.api.common.properties;

import java.util.Map;
import java.util.Set;

/**
 * PropertiesManagementService
 *
 * This service manages setting, retrieving and storing of
 * configurable properties.  by convention, the property keys
 * should be qualified with dots to avoid namespace conflict.
 *   for example:  mondrian.query.limit
 *
 * @author sbirney (sbirney@users.sourceforge.net)
 * @author udavidovich
 */
public interface PropertiesManagementService {

    /**
     * setProperty
     * @param key should be fully qualified, must not be null
     * @param val must be serializable
     */
    public void setProperty(String key, String val);

    /**
     * getProperty
     * @param key must not be null
     * @return associated value or null
     */
    public String getProperty(String key);

    /**
     * saveProperties
     * call saveProperties after setting one or more properties
     */
    public void saveProperties();

    public void reloadProperties();
    
    public String remove(String key);
    public Map<String, String> removeByValue(String val);
    public int size();
    public Set entrySet();

}
