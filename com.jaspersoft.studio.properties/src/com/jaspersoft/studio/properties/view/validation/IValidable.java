/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.properties.view.validation;

import java.util.List;

public interface IValidable {
	public List<ValidationError> validate();
}
