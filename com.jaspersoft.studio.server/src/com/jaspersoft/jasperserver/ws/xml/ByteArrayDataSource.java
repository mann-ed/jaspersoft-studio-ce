/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.ws.xml;
import java.io.*;


/**
 *
 * @author  Administrator
 */
public class ByteArrayDataSource implements javax.activation.DataSource {
    
    private byte[] buffer;
    private String contenType = "application/octet-stream";

    private String name = "";
    /** Creates a new instance of ByteArrayDataSource */
    public ByteArrayDataSource(String name, byte[] buffer) {
        this(name, buffer, "application/octet-stream");
    }
    
    /** Creates a new instance of ByteArrayDataSource */
    public ByteArrayDataSource(byte[] buffer) {
        this(null, buffer, "application/octet-stream");
    }
    
    /** Creates a new instance of ByteArrayDataSource */
    public ByteArrayDataSource(byte[] buffer, String contentType) {
        this(null, buffer, contentType);
    }
    
    /** Creates a new instance of ByteArrayDataSource */
    public ByteArrayDataSource(String name, byte[] buffer, String contentType) {
        if (name != null) this.setName(name);
        this.setBuffer(buffer);
        if (contentType != null) this.setContenType(contentType);
    }
       
    public String getContentType() {
        return getContenType();
    }

    
    public java.io.InputStream getInputStream() throws java.io.IOException {
        return new java.io.ByteArrayInputStream( getBuffer() );
    }
    
    public String getName() {
        return name;
    }
    
    public java.io.OutputStream getOutputStream() throws java.io.IOException {
        throw new java.io.IOException();
    }

    public String getContenType() {
        return contenType;
    }

    public void setContenType(String contenType) {
        this.contenType = contenType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
}

