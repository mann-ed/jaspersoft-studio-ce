/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.viewer.action;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;
import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.eclipse.viewer.IReportViewer;

public class ZoomInAction extends AReportAction {

	public static final String ID = Messages.ZoomInAction_0;
	
	public ZoomInAction(IReportViewer viewer) {
		super(viewer);
		setId(ID);
		setText("Zoom In"); //$NON-NLS-1$
		setToolTipText("Zoom in"); //$NON-NLS-1$
		setImageDescriptor(JasperReportsPlugin.getDefault().getImageDescriptor("icons/zoomin-16.png")); //$NON-NLS-1$
		setDisabledImageDescriptor(JasperReportsPlugin.getDefault().getImageDescriptor("icons/zoomin-16.png")); //$NON-NLS-1$
	}

	@Override
	public void run() {
		rviewer.zoomIn();
	}

	public boolean isActionEnabled() {
		return rviewer.canZoomIn();
	}
}
