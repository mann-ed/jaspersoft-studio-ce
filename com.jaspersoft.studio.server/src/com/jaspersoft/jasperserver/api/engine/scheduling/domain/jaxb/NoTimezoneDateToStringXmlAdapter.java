/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: NoTimezoneDateToStringXmlAdapter.java 38348 2013-09-30 04:57:18Z carbiv $
 */
public class NoTimezoneDateToStringXmlAdapter extends XmlAdapter<String, Date>{

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    @Override
    public Date unmarshal(String v) throws Exception {
        return new SimpleDateFormat(DATE_TIME_PATTERN).parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return new SimpleDateFormat(DATE_TIME_PATTERN).format(v);
    }
}
