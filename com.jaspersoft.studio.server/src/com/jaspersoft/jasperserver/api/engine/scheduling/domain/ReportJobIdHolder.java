/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;
import com.jaspersoft.jasperserver.api.engine.scheduling.service.ReportJobsPersistenceService;

import java.io.Serializable;

/**
 * Wrapper around a report job ID used by some report scheduling API methods.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ReportJobIdHolder.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 * @see ReportJob#getId()
 * @see ReportJobsPersistenceService
 * @since 1.0
 */
@JasperServerAPI
public class ReportJobIdHolder implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final long id;
	
	/**
	 * Wraps a report job ID into an object.
	 * 
	 * @param id the ID to wrap
	 */
	public ReportJobIdHolder(long id) {
		this.id = id;
	}

	/**
	 * Returns the wrapped report job ID.
	 * 
	 * @return the job ID
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Uses the job ID as has code.
	 */
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * Compares wrappers by job IDs.
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof ReportJobIdHolder)) {
			return false;
		}
		
		return id == ((ReportJobIdHolder) obj).id;
	}

}
