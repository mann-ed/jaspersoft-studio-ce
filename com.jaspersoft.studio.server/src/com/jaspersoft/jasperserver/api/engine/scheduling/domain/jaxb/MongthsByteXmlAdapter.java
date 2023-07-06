/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: MongthsByteXmlAdapter.java 22756 2012-03-23 10:39:15Z sergey.prilukin $
 */
public class MongthsByteXmlAdapter extends XmlAdapter<MonthsSortedSetWrapper, SortedSet<Byte>>{
    @Override
    public SortedSet<Byte> unmarshal(MonthsSortedSetWrapper v) throws Exception {
        SortedSet<Byte> result = null;
        if(v != null && v.getMongths() != null && !v.getMongths().isEmpty()){
            result = new TreeSet<Byte>();
            for(String value : v.getMongths())
                result.add(Byte.valueOf(value));
        }
        return result;
    }

    @Override
    public MonthsSortedSetWrapper marshal(SortedSet<Byte> v) throws Exception {
        MonthsSortedSetWrapper result = null;
        if(v != null && !v.isEmpty()){
            SortedSet<String> strings = new TreeSet<String>();
            for(Byte value : v)
                strings.add(value.toString());
            result = new MonthsSortedSetWrapper(strings);
        }
        return result;
    }
}
