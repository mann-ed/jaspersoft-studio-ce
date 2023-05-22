/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.common.domain.jaxb;

/**
 * Implement this interface by enumeration used in AbstractEnumXmlAdapter
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id$
 */
public interface NamedPropertyHolder<PropertyType> {
    PropertyType getProperty();
    public String name();
}
