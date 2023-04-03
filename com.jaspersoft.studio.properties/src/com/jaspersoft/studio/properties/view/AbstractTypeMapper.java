/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.properties.view;

/**
 * Default implementation of a type mapper.
 * 
 * @author Anthony Hunter
 */
public class AbstractTypeMapper
    implements ITypeMapper {

    public Class<? extends Object> mapType(Object object) {
        return object.getClass();
    }

}
