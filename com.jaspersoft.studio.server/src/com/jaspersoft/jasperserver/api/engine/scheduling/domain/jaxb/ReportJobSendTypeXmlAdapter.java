/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import com.jaspersoft.jasperserver.api.common.domain.jaxb.AbstractEnumXmlAdapter;
import com.jaspersoft.jasperserver.api.common.domain.jaxb.NamedPropertyHolder;
import com.jaspersoft.jasperserver.api.engine.scheduling.domain.ReportJobMailNotification;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportJobSendTypeXmlAdapter.java 38348 2013-09-30 04:57:18Z carbiv $
 */
public class ReportJobSendTypeXmlAdapter extends AbstractEnumXmlAdapter<Byte> {
    @Override
    protected NamedPropertyHolder<Byte>[] getEnumConstantsArray() {
        return SendType.values();
    }

    public enum SendType implements NamedPropertyHolder<Byte> {
        SEND(ReportJobMailNotification.RESULT_SEND),
        SEND_ATTACHMENT(ReportJobMailNotification.RESULT_SEND_ATTACHMENT),
        SEND_ATTACHMENT_NOZIP(ReportJobMailNotification.RESULT_SEND_ATTACHMENT_NOZIP),
        SEND_EMBED(ReportJobMailNotification.RESULT_SEND_EMBED),
        SEND_ATTACHMENT_ZIP_ALL(ReportJobMailNotification.RESULT_SEND_ATTACHMENT_ZIP_ALL),
        SEND_EMBED_ZIP_ALL_OTHERS(ReportJobMailNotification.RESULT_SEND_EMBED_ZIP_ALL_OTHERS);

        private final Byte byteValue;

        private SendType(Byte byteValue) {
            this.byteValue = byteValue;
        }
         public Byte getProperty(){
             return this.byteValue;
         }
    }




}
