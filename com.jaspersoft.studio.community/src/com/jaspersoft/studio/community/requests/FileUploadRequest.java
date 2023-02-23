/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.community.requests;

/**
 * Request data for file upload to the community tracker.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class FileUploadRequest {
	private String filename;
	private byte[] encodedFileData;
	
	public FileUploadRequest(String filename, byte[] encodedFileData) {
		super();
		this.filename = filename;
		this.encodedFileData = encodedFileData.clone();
	}

	public String getAsJSON(){
		StringBuffer jsonBuf = new StringBuffer();
		jsonBuf.append("{"); //$NON-NLS-1$
		jsonBuf.append("\"file\": \"").append(new String(encodedFileData)).append("\","); //$NON-NLS-1$ //$NON-NLS-2$
		jsonBuf.append("\"filename\": \"").append(filename).append("\" }"); //$NON-NLS-1$ //$NON-NLS-2$
		return jsonBuf.toString();
	}
}
