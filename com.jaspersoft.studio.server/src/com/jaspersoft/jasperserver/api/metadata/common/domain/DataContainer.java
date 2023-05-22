/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.jasperserver.api.metadata.common.domain;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author Lucian Chirita
 *
 */
public interface DataContainer extends Serializable {

	boolean hasData();
	
	OutputStream getOutputStream();
	
	int dataSize();
	
	InputStream getInputStream();

	byte[] getData();
	
	void dispose();
	
}
