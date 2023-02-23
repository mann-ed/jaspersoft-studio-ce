/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.engine.scheduling.domain.jaxb;

import com.jaspersoft.jasperserver.api.engine.scheduling.domain.FTPInfo;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * <p></p>
 *
 * @author Yaroslav.Kovalchyk
 * @version $Id: FtpTypeAdapter.java 38348 2013-09-30 04:57:18Z carbiv $
 */
public class FtpTypeAdapter extends XmlAdapter<String, String> {
    private static final String CLIENT_TYPE_FTP = "ftp";
    private static final String CLIENT_TYPE_FTPS = "ftps";
    @Override
    public String unmarshal(String v) throws Exception {
        return CLIENT_TYPE_FTPS.equals(v) ? FTPInfo.TYPE_FTPS : FTPInfo.TYPE_FTP;
    }

    @Override
    public String marshal(String v) throws Exception {
        return FTPInfo.TYPE_FTPS.equals(v) ? CLIENT_TYPE_FTPS : CLIENT_TYPE_FTP;
    }
}
