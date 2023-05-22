/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.jasperserver.api.metadata.common.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;

import java.io.InputStream;


/**
 * FileResource is the interface which represents the JasperServer resource containing some files
 * of any type: images, fonts, jrxml, jar, resource bundles, style templates, xml.
 * It extends {@link com.jaspersoft.jasperserver.api.metadata.common.domain.Resource}
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: FileResource.java 32262 2013-05-31 16:05:18Z inesterenko $
 */
@JasperServerAPI
public interface FileResource extends Resource
{
	String TYPE_IMAGE = "img";
	String TYPE_FONT = "font";
	String TYPE_JRXML = "jrxml";
	String TYPE_JAR = "jar";
	String TYPE_RESOURCE_BUNDLE = "prop";
	String TYPE_STYLE_TEMPLATE = "jrtx";
	String TYPE_XML = "xml";
    String TYPE_CSS = "css";
    String TYPE_JSON = "json";
    String TYPE_ACCESS_GRANT_SCHEMA = "accessGrantSchema";

    /**
     * Returns <code>true</code> if the resource has some file.
     *
     * @return <code>true</code> if the resource has some file.
     */
	boolean hasData();

    /**
     * Returns the data stream of the file
     *
     * @return data stream of the file
     */
	InputStream getDataStream();

    /**
     * Sets the data of the file input stream
     *
     * @param is the input stream of file to be set
     */
	void readData(InputStream is);

    /**
     * Returns the data from the file as a byte array
     *
     * @return data
     */
	byte[] getData();

    /**
     * Sets the data of the file
     *
     * @param data
     */
	void setData(byte[] data);

    /**
     * Returns the file type
     *
     * @return file type
     */
	String getFileType();

    /**
     * Sets the file type
     *
     * @param fileType
     */
	void setFileType(String fileType);

    /**
     * Shows if the file resource is only a reference
     *
     * @return <code>true</code> if file resource is a reference
     */
	boolean isReference();

    /**
     * Returns the URI that the resource references to
     *
     * @return reference URI
     */
	String getReferenceURI();

    /**
     * Sets the URI that resource should reference to
     *
     * @param referenceURI
     */
	void setReferenceURI(String referenceURI);
}
