/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: TimestampToStringXmlAdapter.java 38348 2013-09-30 04:57:18Z carbiv $
 */
public class TimestampToStringXmlAdapter extends XmlAdapter<String, Timestamp>{
    @Override
    public Timestamp unmarshal(String v) throws Exception {
        return new Timestamp(DatatypeConverter.parseDateTime(v).getTimeInMillis());
    }

    @Override
    public String marshal(Timestamp v) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(v);
        return DatatypeConverter.printDateTime(calendar);
    }
}
