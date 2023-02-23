/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.SortedSet;

/**
 * This class is needed because of bug in JAXB.
 * @XmlElementWrapper doesn't support @XmlJavaTypeAdapter
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: WeekDaysSortedSetWrapper.java 22756 2012-03-23 10:39:15Z sergey.prilukin $
 */
@XmlRootElement
public class WeekDaysSortedSetWrapper {

    private SortedSet<String> days;

    public WeekDaysSortedSetWrapper(){}

    public WeekDaysSortedSetWrapper(SortedSet<String> days){
        this.days = days;
    }
    @XmlElement(name = "day")
    public SortedSet<String> getDays() {
        return days;
    }

    public void setDays(SortedSet<String> days) {
        this.days = days;
    }

}
