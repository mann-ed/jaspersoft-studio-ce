/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/


package com.jaspersoft.jasperserver.api.engine.scheduling.service;

import com.jaspersoft.jasperserver.api.JasperServerAPI;
import com.jaspersoft.jasperserver.api.JSException;
import com.jaspersoft.jasperserver.api.engine.scheduling.domain.ReportJob;

/**
 * Exception type used when a report job is not found for a specified ID.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ReportJobNotFoundException.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 * @since 1.0
 * @see ReportJob#getId()
 */
@JasperServerAPI
public class ReportJobNotFoundException extends JSException {

	private final long jobId;
	
	/**
	 * Creates a report job not found exception.
	 * 
	 * @param jobId the ID fow which finding a job failed
	 */
	public ReportJobNotFoundException(long jobId) {
		super("jsexception.report.job.not.found", new Object[] {new Long(jobId)});
		
		this.jobId = jobId;
	}
	
	/**
	 * Returns the ID for which a job was not found.
	 * 
	 * @return the ID for which a job was not found
	 */
	public long getJobId() {
		return jobId;
	}
	
}
