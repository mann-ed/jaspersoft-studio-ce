/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * This class is used for serialization because of no ability to use @XmlElementWrapper together with @XmlJavaTypeAdapter.
 * See http://java.net/jira/browse/JAXB-787
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportJobOutputFormatsWrapper.java 22756 2012-03-23 10:39:15Z sergey.prilukin $
 */
@XmlRootElement(name = "outputFormats")
public class ReportJobOutputFormatsWrapper {

    private Set<String> formats;
    @XmlElement(name = "outputFormat")
    public Set<String> getFormats() {
        return formats;
    }

    public void setFormats(Set<String> formats) {
        this.formats = formats;
    }

}
