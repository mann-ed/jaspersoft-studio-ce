/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.remote.services.impl;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.TimeZone;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id$
 */
public class TimeZoneXmlAdapter extends XmlAdapter<String, TimeZone>{
    @Override
    public TimeZone unmarshal(String v) throws Exception {
        return TimeZone.getTimeZone(v);
    }

    @Override
    public String marshal(TimeZone v) throws Exception {
        return v.getID();
    }
}
