/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.builder;

import net.sf.jasperreports.eclipse.util.xml.SourceLocation;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignElement;

import org.eclipse.jdt.core.compiler.IProblem;

/*
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JasperReportErrorHandler.java 23 2007-03-09 14:36:40Z lucianc $
 */
public interface JasperReportErrorHandler {

	void addMarker(Throwable e);

	void addMarker(String message, SourceLocation location);
	
	void addMarker(String message, SourceLocation location, JRDesignElement element);

	void addMarker(IProblem problem, SourceLocation location);

	void addMarker(IProblem problem, SourceLocation location, JRExpression expr);
}
