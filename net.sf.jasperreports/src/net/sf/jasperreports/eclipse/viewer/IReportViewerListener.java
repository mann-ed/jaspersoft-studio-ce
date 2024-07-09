/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.viewer;

import java.util.EventListener;

public interface IReportViewerListener extends EventListener {

	public void viewerStateChanged(ReportViewerEvent evt);
}
