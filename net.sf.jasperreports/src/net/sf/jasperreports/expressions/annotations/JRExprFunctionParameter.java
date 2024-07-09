/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.expressions.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.jasperreports.functions.annotations.FunctionParameter;

/**
 * Annotations used to describe a parameter associated to a function.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * @deprecated Replaced by {@link FunctionParameter}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JRExprFunctionParameter {
	String name();
	String description();
}
