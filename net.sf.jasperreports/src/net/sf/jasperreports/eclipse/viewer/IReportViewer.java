/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.viewer;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRHyperlinkListener;

import org.eclipse.swt.widgets.Control;

public interface IReportViewer {

	public void setReport(JasperPrint document, int page);

	public void setReport(JasperPrint document);

	public boolean hasReport();

	public JasperPrint getReport();

	public void addReportViewerListener(IReportViewerListener listener);

	public void removeReportViewerListener(IReportViewerListener listener);
	
	public Control getControl();

	// page navigation
	public int getPageIndex();

	public void setPageIndex(int pageIndex);

	public void gotoNextPage();

	public boolean canGotoNextPage();

	public void gotoPreviousPage();

	public boolean canGotoPreviousPage();

	public void gotoLastPage();

	public boolean canGotoLastPage();

	public void gotoFirstPage();

	public boolean canGotoFirstPage();

	public void exportImage(String file, int width, int height);

	public String getReportName();

	// Zoom management
	// --------------------------------------------------------------------
	// public float computeZoom();

	public void setZoom(float zoom);

	public float getZoom();

	public boolean canChangeZoom();

	public void setZoomMode(int zoomMode);

	public int getZoomMode();

	public float[] getZoomLevels();

	public void zoomIn();

	public boolean canZoomIn();

	public void zoomOut();

	public boolean canZoomOut();

	// Hyperlinks
	// --------------------------------------------------------------------

	public void addHyperlinkListener(JRHyperlinkListener listener);

	public void removeHyperlinkListener(JRHyperlinkListener listener);

	public JRHyperlinkListener[] getHyperlinkListeners();

	public void fireViewerModelChanged(boolean isCurrentPage);
}
