/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.viewer.action;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;
import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.eclipse.viewer.IReportViewer;
import net.sf.jasperreports.eclipse.viewer.ReportViewerEvent;
import net.sf.jasperreports.eclipse.viewer.ViewerCanvas;

public class ZoomFitPageWidthAction extends AReportAction {

	public ZoomFitPageWidthAction(IReportViewer viewer) {
		super(viewer);

		setText(Messages.ZoomFitPageWidthAction_actionName);
		setToolTipText(Messages.ZoomFitPageWidthAction_actionTooltip);
		setImageDescriptor(JasperReportsPlugin.getDefault().getImageDescriptor("icons/zoomfitwidth.gif"));//$NON-NLS-1$
		setDisabledImageDescriptor(JasperReportsPlugin.getDefault().getImageDescriptor("icons/zoomfitwidthd.gif"));//$NON-NLS-1$
		update();
	}

	private void update() {
		setChecked(rviewer.getZoomMode() == ViewerCanvas.ZOOM_MODE_FIT_WIDTH);
	}

	@Override
	public void viewerStateChanged(ReportViewerEvent evt) {
		super.viewerStateChanged(evt);
		update();
	}

	@Override
	public void run() {
		rviewer.setZoomMode(ViewerCanvas.ZOOM_MODE_FIT_WIDTH);
		update();
	}

	public boolean isActionEnabled() {
		return rviewer.canChangeZoom();
	}

}
