/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property;

import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertySource;

public interface IPostSetValue {
	public Command postSetValue(IPropertySource target, Object prop, Object newValue, Object oldValue);
}
