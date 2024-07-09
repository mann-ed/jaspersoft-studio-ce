/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.expressions.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.jasperreports.functions.annotations.Function;

/**
 * Annotation used to mark a method as function usable inside an expression editor. 
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * @deprecated Replaced by {@link Function}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JRExprFunction {
	String name();
	String description();
}
