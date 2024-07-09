/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.viewer.action;

import net.sf.jasperreports.eclipse.viewer.IReportViewer;
import net.sf.jasperreports.eclipse.viewer.IReportViewerListener;
import net.sf.jasperreports.eclipse.viewer.ReportViewerEvent;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.services.IDisposable;

public abstract class AReportAction extends Action implements
		IReportViewerListener, IDisposable {
	protected IReportViewer rviewer;

	public AReportAction(IReportViewer rviewer) {
		super();
		this.rviewer = rviewer;
		rviewer.addReportViewerListener(this);
		setEnabled(isActionEnabled());
	}

	public abstract boolean isActionEnabled();

	public void viewerStateChanged(ReportViewerEvent evt) {
		if (!evt.isCurrentPage())
			setEnabled(isActionEnabled());
	}

	public void dispose() {
		rviewer.removeReportViewerListener(this);
	}
}
