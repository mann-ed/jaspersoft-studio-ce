/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.metadata.common.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;



/**
 * The interface represents the property of
 * {@link com.jaspersoft.jasperserver.api.metadata.common.domain.InputControl}
 * which type is list of defined by user values.
 * It extends {@link com.jaspersoft.jasperserver.api.metadata.common.domain.Resource}
 *
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: ListOfValues.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 * @see com.jaspersoft.jasperserver.api.metadata.common.domain.client.ListOfValuesImpl
 */
@JasperServerAPI
public interface ListOfValues extends Resource
{

    /**
     * Returns the list items as array
     *
     * @return array of values
     */
    public ListOfValuesItem[] getValues();

    /**
     * Inserts one more item to the list
     *
     * @param item
     */
	public void addValue(ListOfValuesItem item);
    /**
     * Removes specified item from the list
     *
     * @param item
     */
	public void removeValue(ListOfValuesItem item);
}
