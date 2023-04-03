/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/


package com.jaspersoft.jasperserver.api.metadata.xml.domain.impl;

import java.io.Serializable;

import net.sf.jasperreports.engine.JRConstants;

/**
 * 
 * @author gtoffoli
 */
public class ResourceProperty implements Serializable {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	private String name = "";
	private String value = "";

	private java.util.List<ResourceProperty> properties = new java.util.ArrayList<ResourceProperty>();

	/** Creates a new instance of ResourceProperty */
	public ResourceProperty(String name) {
		this(name, null);
	}

	/** Creates a new instance of ResourceProperty */
	public ResourceProperty(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		isDirty = true;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		isDirty = true;
	}

	public java.util.List<ResourceProperty> getProperties() {
		return properties;
	}

	public void setProperties(java.util.List<ResourceProperty> properties) {
		this.properties = properties;
		isDirty = true;
	}

	private boolean isDirty = true;

	public boolean isDirty() {
		if (!isDirty && properties != null)
			for (ResourceProperty p : properties)
				if (p.isDirty())
					return true;
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		if (properties != null)
			for (ResourceProperty p : properties)
				p.setDirty(isDirty);
	}
}
