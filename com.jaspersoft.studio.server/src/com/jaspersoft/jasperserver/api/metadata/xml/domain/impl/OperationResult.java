/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.metadata.xml.domain.impl;

/**
 * @author tkavanagh
 * @version $Id: OperationResult.java 4724 2006-09-25 08:23:22Z tdanciu $
 */

import java.util.List;
import java.util.ArrayList;

public class OperationResult {

    public static final int SUCCESS = 0;
    
    private int returnCode = 0;
    private String message;
    private List resourceDescriptors = new ArrayList();
    private String version = "1.2.1";
    
    /**
     * Creates a new instance of OperationResult
     */
    public OperationResult() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List getResourceDescriptors() {
        return resourceDescriptors;
    }

    public void setResourceDescriptors(List resourceDescriptors) {
        this.resourceDescriptors = resourceDescriptors;
    }

    public void addResourceDescriptor(ResourceDescriptor descriptor) {
    	resourceDescriptors.add(descriptor);
    }
    
    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
	
}
