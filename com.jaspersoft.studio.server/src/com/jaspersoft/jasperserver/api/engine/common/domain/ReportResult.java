/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.common.domain;

import net.sf.jasperreports.engine.JRVirtualizer;
import net.sf.jasperreports.web.servlets.JasperPrintAccessor;

/**
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ReportResult.java 30437 2013-03-28 17:54:52Z lchirita $
 */
public interface ReportResult extends Result {
	
	String getRequestId();
	
	JasperPrintAccessor getJasperPrintAccessor();
	
	JRVirtualizer getVirtualizer();

}
