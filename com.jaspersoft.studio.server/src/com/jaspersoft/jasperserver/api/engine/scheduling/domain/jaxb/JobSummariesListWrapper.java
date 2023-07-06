/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import com.jaspersoft.jasperserver.api.engine.scheduling.domain.ReportJobSummary;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This class is needed because of bug in JAXB.
 * XmlElementWrapper annotation doesn't support @XmlJavaTypeAdapter
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: JobSummariesListWrapper.java 22756 2012-03-23 10:39:15Z sergey.prilukin $
 */
@XmlRootElement(name = "jobs")
public class JobSummariesListWrapper {

    private List<ReportJobSummary> jobSummaries;

    public JobSummariesListWrapper(){}
    public JobSummariesListWrapper(List<ReportJobSummary> jobSummaries){
        this.jobSummaries = jobSummaries;
    }
    @XmlElement(name = "jobsummary")
    public List<ReportJobSummary> getJobSummaries() {
        return jobSummaries;
    }

    public void setJobSummaries(List<ReportJobSummary> jobSummaries) {
        this.jobSummaries = jobSummaries;
    }

}
