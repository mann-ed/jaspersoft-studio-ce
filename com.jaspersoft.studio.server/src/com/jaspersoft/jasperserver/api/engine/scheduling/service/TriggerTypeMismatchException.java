/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/


package com.jaspersoft.jasperserver.api.engine.scheduling.service;

import com.jaspersoft.jasperserver.api.JSException;
import com.jaspersoft.jasperserver.api.JasperServerAPI;

/**
 * Exception type used when a report job is not found for a specified ID.
 * 
 * @author Ivan Chan
 * @version $Id: TriggerTypeMismatchException.java 22122 2012-02-08 23:42:49Z ichan $
 * @since 4.7
 * @see com.jaspersoft.jasperserver.api.engine.scheduling.domain.ReportJob#getId()
 */
@JasperServerAPI
public class TriggerTypeMismatchException extends JSException {

	private final long jobId;

    public static enum TRIGGER_TYPE {
        SIMPLE_TRIGGER,
        CALENDAR_TRIGGER;
    }
    private TRIGGER_TYPE type = TRIGGER_TYPE.SIMPLE_TRIGGER;

	/**
	 * Creates a report job not found exception.
	 *
	 * @param jobId the ID fow which finding a job failed
	 */
	public TriggerTypeMismatchException(long jobId, TRIGGER_TYPE type) {
		super("jsexception.trigger.type.mismatch: expect " + (type == TRIGGER_TYPE.SIMPLE_TRIGGER? "simple trigger " : "calendar trigger ") +
                "for report job ID " + + jobId + " [found " + (type != TRIGGER_TYPE.SIMPLE_TRIGGER? "simple trigger" : "calendar trigger") + "]"
                , new Object[] {new Long(jobId), type});
		this.jobId = jobId;
        this.type = type;
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
