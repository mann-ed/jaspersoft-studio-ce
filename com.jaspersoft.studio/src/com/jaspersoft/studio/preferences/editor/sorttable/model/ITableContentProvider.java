/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.preferences.editor.sorttable.model;

import org.eclipse.jface.viewers.IStructuredContentProvider;

public interface ITableContentProvider extends IStructuredContentProvider {

	public Object getColumnValue(Object element, int columnIndex);

}
