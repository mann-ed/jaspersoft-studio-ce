/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: DateToStringXmlAdapter.java 38348 2013-09-30 04:57:18Z carbiv $
 */
public class DateToStringXmlAdapter extends XmlAdapter<String, Date>{
    @Override
    public Date unmarshal(String v) throws Exception {
        return DatatypeConverter.parseDateTime(v).getTime();
    }

    @Override
    public String marshal(Date v) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(v);
        return DatatypeConverter.printDateTime(calendar);
    }
}
