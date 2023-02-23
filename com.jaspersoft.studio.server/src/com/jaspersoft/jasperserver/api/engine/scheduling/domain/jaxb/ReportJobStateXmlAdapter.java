/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import com.jaspersoft.jasperserver.api.engine.scheduling.domain.ReportJobRuntimeInformation;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportJobStateXmlAdapter.java 22756 2012-03-23 10:39:15Z sergey.prilukin $
 */
public class ReportJobStateXmlAdapter extends XmlAdapter<String, Byte>{

     public enum State {
        UNKNOWN(ReportJobRuntimeInformation.STATE_UNKNOWN),
        NORMAL(ReportJobRuntimeInformation.STATE_NORMAL),
        EXECUTING(ReportJobRuntimeInformation.STATE_EXECUTING),
        PAUSED(ReportJobRuntimeInformation.STATE_PAUSED),
        COMPLETE(ReportJobRuntimeInformation.STATE_COMPLETE),
        ERROR(ReportJobRuntimeInformation.STATE_ERROR);

        private final Byte byteValue;

        private State(Byte byteValue) {
            this.byteValue = byteValue;
        }
    }

    @Override
    public Byte unmarshal(String v) throws Exception {
         Byte result = null;
        if (v != null && !"".equals(v))
            try {
                result = State.valueOf(v).byteValue;
            } catch (IllegalArgumentException e) {
                result = State.UNKNOWN.byteValue;
            }
        return result;
    }

    @Override
    public String marshal(Byte v) throws Exception {
         String result = null;
        for (State state : State.values()) {
            if (state.byteValue.equals(v)) {
                result = state.name();
                break;
            }
        }
        return result != null ? result : State.UNKNOWN.name();
    }
}
