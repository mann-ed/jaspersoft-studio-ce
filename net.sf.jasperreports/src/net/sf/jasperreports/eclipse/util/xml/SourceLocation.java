/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util.xml;

/*
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: SourceLocation.java 22 2007-03-08 15:18:26Z lucianc $
 */
public class SourceLocation
{

	private int lineNumber;
	private int columnNumber;
	private String xPath;
	
	public SourceLocation()
	{
	}

	
	public int getColumnNumber()
	{
		return columnNumber;
	}

	
	public void setColumnNumber(int columnNumber)
	{
		this.columnNumber = columnNumber;
	}

	
	public int getLineNumber()
	{
		return lineNumber;
	}

	
	public void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}

	
	public String getXPath()
	{
		return xPath;
	}

	
	public void setXPath(String path)
	{
		xPath = path;
	}
	
}
