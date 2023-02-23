/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.swt.widgets.table;

import java.util.List;

public interface IEditElement<T> {
	public void editElement(List<T> input, int pos);
}
