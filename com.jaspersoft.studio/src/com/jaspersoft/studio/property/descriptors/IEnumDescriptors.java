/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.descriptors;

import com.jaspersoft.studio.property.descriptor.NullEnum;

public interface IEnumDescriptors {
	public String[] getEnumItems();

	public NullEnum getType();
}
