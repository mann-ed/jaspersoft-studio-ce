/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.service;

import com.jaspersoft.jasperserver.api.JasperServerAPI;

/**
 * Listener for report job scheduling execution events.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ReportSchedulerListener.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 * @since 1.0
 * @see ReportJobsScheduler#addReportSchedulerListener(ReportSchedulerListener)
 */
@JasperServerAPI
public interface ReportSchedulerListener {
	
	/**
	 * Called when a job trigger has completed all scheduled executions.
	 * 
	 * @param jobId the ID of the completed job
	 */
	void reportJobFinalized(long jobId);
	
}
