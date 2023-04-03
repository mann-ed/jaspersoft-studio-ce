/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.action.exporter;

/**
 * Simple implementation of an {@link IResourceDefinition}
 * 
 * @author Orlandin Marco
 *
 */
public class BaseResource implements IResourceDefinition {

	private String name;
	
	private Object data = null;
	
	public BaseResource(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	public Object getData(){
		return data;
	}
	
	public void setData(Object data){
		this.data = data;
	}
	
}
