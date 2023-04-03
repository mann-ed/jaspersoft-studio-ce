/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import com.jaspersoft.jasperserver.api.common.domain.jaxb.AbstractEnumXmlAdapter;
import com.jaspersoft.jasperserver.api.common.domain.jaxb.NamedPropertyHolder;
import com.jaspersoft.jasperserver.api.engine.scheduling.domain.ReportJobSimpleTrigger;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportJobTriggerIntervalUnitXmlAdapter.java 22756 2012-03-23 10:39:15Z sergey.prilukin $
 */
public class ReportJobTriggerIntervalUnitXmlAdapter extends AbstractEnumXmlAdapter<Byte> {
    @Override
    protected NamedPropertyHolder<Byte>[] getEnumConstantsArray() {
        return IntervalUnit.values();
    }

    public enum IntervalUnit implements NamedPropertyHolder<Byte> {
        MINUTE(ReportJobSimpleTrigger.INTERVAL_MINUTE),
        HOUR(ReportJobSimpleTrigger.INTERVAL_HOUR),
        DAY(ReportJobSimpleTrigger.INTERVAL_DAY),
        WEEK(ReportJobSimpleTrigger.INTERVAL_WEEK);

        private final Byte byteValue;

        private IntervalUnit(Byte byteValue) {
            this.byteValue = byteValue;
        }
         public Byte getProperty(){
             return this.byteValue;
         }
    }




}
