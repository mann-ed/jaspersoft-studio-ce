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
 * Annotation to describe a list of parameters for a specific {@link JRExprFunction}.
 *  
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * @deprecated Replaced by {@link FunctionParameter}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JRExprFunctionParameters {
	JRExprFunctionParameter[] value();
}
