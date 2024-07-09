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

public class ZoomActualSizeAction extends AReportAction {

	public static final String ID = "PreviewZoomActualAction"; //$NON-NLS-1$
	
	public ZoomActualSizeAction(IReportViewer viewer) {
		super(viewer);
		setId(ID);
		setText(Messages.ZoomActualSizeAction_actionName);
		setToolTipText(Messages.ZoomActualSizeAction_actionTooltip);
		setImageDescriptor(JasperReportsPlugin.getDefault().getImageDescriptor("icons/zoomactual.gif"));//$NON-NLS-1$
		setDisabledImageDescriptor(JasperReportsPlugin.getDefault().getImageDescriptor("icons/zoomactuald.gif"));//$NON-NLS-1$
		update();
	}

	private void update() {
		setChecked(rviewer.getZoomMode() == ViewerCanvas.ZOOM_MODE_ACTUAL_SIZE);
	}

	@Override
	public void viewerStateChanged(ReportViewerEvent evt) {
		super.viewerStateChanged(evt);
		update();
	}

	@Override
	public void run() {
		rviewer.setZoomMode(ViewerCanvas.ZOOM_MODE_ACTUAL_SIZE);
		update();
	}

	public boolean isActionEnabled() {
		return rviewer.canChangeZoom();
	}

}
