/*******************************************************************************
 * Copyright (C) 2010 - 2012 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, 
 * the following license terms apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
package com.jaspersoft.studio.data.queryexecutor;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.qe.QueryExecuterDataAdapter;
import net.sf.jasperreports.data.qe.QueryExecuterDataAdapterImpl;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.jaspersoft.studio.data.Activator;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.DataAdapterEditor;

public class QueryExecutorDataAdapterDescriptor extends DataAdapterDescriptor {
	private QueryExecuterDataAdapter queryExecuterDataAdapter = new QueryExecuterDataAdapterImpl();

	@Override
	public DataAdapter getDataAdapter() {
		return queryExecuterDataAdapter;
	}

	@Override
	public void setDataAdapter(DataAdapter dataAdapter) {
		this.queryExecuterDataAdapter = (QueryExecuterDataAdapter) dataAdapter;
	}

	@Override
	public ImageDescriptor getIcon16() {
		return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/QueryExecutorDataAdapterIcon-16.gif");
	}

	@Override
	public DataAdapterEditor getEditor() {
		return new QueryExecutorDataAdapterEditor();
	}

	@Override
	public Image getIcon(int size) {
		if (size == 16) {
			return Activator.getDefault().getImage("icons/receipt.png");
		}
		return null;
	}
}
