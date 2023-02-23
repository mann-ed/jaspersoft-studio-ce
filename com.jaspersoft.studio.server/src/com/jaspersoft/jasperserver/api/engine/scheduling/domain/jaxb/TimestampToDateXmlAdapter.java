/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: TimestampToDateXmlAdapter.java 22583 2012-03-16 11:09:31Z ykovalchyk $
 */
public class TimestampToDateXmlAdapter extends XmlAdapter<Date, Timestamp>{
    @Override
    public Timestamp unmarshal(Date v) throws Exception {
        return new Timestamp(v.getTime());
    }

    @Override
    public Date marshal(Timestamp v) throws Exception {
        return new Date(v.getTime());
    }
}
