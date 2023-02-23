/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import net.sf.jasperreports.engine.JRParameter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportJobSourceParametersXmlAdapter.java 28947 2013-02-26 15:02:08Z vsabadosh $
 */
public class ReportJobSourceParametersXmlAdapter extends XmlAdapter<ReportParametersMapWrapper, Map<String, Object>> {
    @Override
    public ReportParametersMapWrapper marshal(Map<String, Object> v) throws Exception {
        HashMap<String, Object> result = null;
        if (v != null && !v.isEmpty()) {
            result = new HashMap<String, Object>();
            for (String currentKey : v.keySet()) {
                Object currentValue = v.get(currentKey);
                if (JRParameter.REPORT_TIME_ZONE.equals(currentKey) && currentValue instanceof TimeZone) {
                    currentValue = ((TimeZone) currentValue).getID();
                }else if(currentValue instanceof Collection){
                    final ValuesCollection collectionWrapper = new ValuesCollection();
                    collectionWrapper.setCollection((Collection<Object>) currentValue);
                    currentValue = collectionWrapper;
                }
                result.put(currentKey, currentValue);
            }
        }
        return result != null ? new ReportParametersMapWrapper(result) : null;
    }

    @Override
    public Map<String, Object> unmarshal(ReportParametersMapWrapper v) throws Exception {
        Map<String, Object> result = null;
        if (v != null) {
            final HashMap<String, Object> parameterValues = v.getParameterValues();
            if (parameterValues != null && !parameterValues.isEmpty()) {
                result = new HashMap<String, Object>();
                for (String currentKey : parameterValues.keySet()) {
                    Object currentValue = parameterValues.get(currentKey);
                    if (JRParameter.REPORT_TIME_ZONE.equals(currentKey) && currentValue instanceof String){
                        currentValue = TimeZone.getTimeZone((String) currentValue);
                    }else if(currentValue instanceof ValuesCollection){
                        currentValue = ((ValuesCollection)currentValue).getCollection();
                    } else if(currentValue instanceof XMLGregorianCalendar){
                        currentValue = ((XMLGregorianCalendar)currentValue).toGregorianCalendar().getTime();
                    }
                    result.put(currentKey, currentValue);
                }
            }
        }
        return result;
    }
}
