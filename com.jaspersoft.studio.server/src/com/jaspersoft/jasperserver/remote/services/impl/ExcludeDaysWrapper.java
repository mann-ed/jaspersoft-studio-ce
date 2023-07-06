/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.remote.services.impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This class is needed because of bug in JAXB.
 * XmlElementWrapper annotation doesn't support @XmlJavaTypeAdapter
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: ExcludeDaysWrapper.java 23844 2012-05-22 06:23:41Z ykovalchyk $
 */
@XmlRootElement(name = "excludeDays")
public class ExcludeDaysWrapper {

    private List<String> excludeDays;

    public ExcludeDaysWrapper(){}

    public ExcludeDaysWrapper(List<String> excludeDays){
        this.excludeDays = excludeDays;
    }
    @XmlElement(name = "excludeDay")
    public List<String> getExcludeDays() {
        return excludeDays;
    }

    public void setExcludeDays(List<String> excludeDays) {
        this.excludeDays = excludeDays;
    }
}
