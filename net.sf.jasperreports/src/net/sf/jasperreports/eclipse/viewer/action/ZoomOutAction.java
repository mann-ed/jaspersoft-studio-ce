/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.viewer.action;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;
import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.eclipse.viewer.IReportViewer;

public class ZoomOutAction extends AReportAction {

	public static final String ID = Messages.ZoomOutAction_0;
	
	public ZoomOutAction(IReportViewer viewer) {
		super(viewer);
		setId(ID);
		setText("Zoom Out"); //$NON-NLS-1$
		setToolTipText("Zoom out"); //$NON-NLS-1$
		setImageDescriptor(JasperReportsPlugin.getDefault().getImageDescriptor("icons/zoomout-16.png")); //$NON-NLS-1$
		setDisabledImageDescriptor(JasperReportsPlugin.getDefault().getImageDescriptor("icons/zoomout-16.png")); //$NON-NLS-1$
	}

	@Override
	public void run() {
		rviewer.zoomOut();
	}

	public boolean isActionEnabled() {
		return rviewer.canZoomOut();
	}
}
