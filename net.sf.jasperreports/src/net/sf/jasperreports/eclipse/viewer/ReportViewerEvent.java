/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.viewer;

import java.util.EventObject;

public class ReportViewerEvent extends EventObject {

	private static final long serialVersionUID = 3397562694482011109L;
	private boolean isCurrentPage;

	/**
	 * Constructs the event with the specified event source
	 * 
	 * @param source
	 *            the source of the event
	 */
	public ReportViewerEvent(Object source, boolean isCurrentPage) {
		super(source);
		this.isCurrentPage = isCurrentPage;
	}

	public boolean isCurrentPage() {
		return isCurrentPage;
	}
}
