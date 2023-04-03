/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.xml.xml;
 
public class XMLAttribute
{

	private String name;
	private String value;

	public XMLAttribute(String name)
	{
		super();
		this.name = name;
	}

	public XMLAttribute(String name, String value)
	{
		super();
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return name;
	}

	public String getValue()
	{
		return value;
	}
}
