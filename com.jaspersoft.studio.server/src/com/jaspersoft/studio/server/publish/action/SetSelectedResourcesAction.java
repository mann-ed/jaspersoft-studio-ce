/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.publish.action;

import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.jaspersoft.studio.server.messages.Messages;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.publish.OverwriteEnum;

public class SetSelectedResourcesAction extends Action {
	private TableViewer tableViewer;
	private OverwriteEnum value;

	public SetSelectedResourcesAction(TableViewer tableViewer, OverwriteEnum value) {
		super();
		setText(getLabel(value));
		this.tableViewer = tableViewer;
		this.value = value;
	}

	private String getLabel(OverwriteEnum value) {
		if (value.equals(OverwriteEnum.OVERWRITE))
			return Messages.ResourcesPage_3;
		if (value.equals(OverwriteEnum.IGNORE))
			return Messages.ResourcesPage_5;
		return Messages.ResourcesPage_6;
	}

	@Override
	public void run() {
		StructuredSelection s = (StructuredSelection) tableViewer.getSelection();
		if (s != null) {
			for (Iterator<?> it = s.iterator(); it.hasNext();) {
				AMResource mres = (AMResource) it.next();
				if (mres != null)
					mres.getPublishOptions().setOverwrite(value);
			}
		}
		tableViewer.refresh();
	}
}
