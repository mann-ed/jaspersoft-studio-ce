/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.remote.services;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Yaroslav.Kovalchyk
 * @version $Id: ReportOutputResource.java 26599 2012-12-10 13:04:23Z ykovalchyk $
 */
@XmlRootElement
public class ReportOutputResource {

    private String contentType;
    private byte[] data;
    private String fileName;

    public ReportOutputResource(){}

    public ReportOutputResource(String contentType, byte[] data){
        this(contentType, data, null);
    }

    public ReportOutputResource(String contentType, byte[] data, String fileName){
        this.contentType = contentType;
        this.data = data;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @XmlTransient
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
