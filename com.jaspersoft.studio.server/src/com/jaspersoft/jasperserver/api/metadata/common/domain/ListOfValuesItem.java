/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.metadata.common.domain;

import java.io.Serializable;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: ListOfValuesItem.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 */
public interface ListOfValuesItem extends Serializable
{

	/**
	 * 
	 */
	public String getLabel();
	
	public void setLabel(String label);

	/**
	 * 
	 */
	public Object getValue();

	public void setValue(Object value);

}
