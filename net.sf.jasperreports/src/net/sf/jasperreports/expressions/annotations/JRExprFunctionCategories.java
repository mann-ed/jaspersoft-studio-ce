/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.expressions.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.jasperreports.functions.annotations.FunctionCategories;

/**
 * Annotation to be describe the types of categories to which a specific
 * {@link JRExprFunction} belong to. 
 *  
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * @deprecated Replaced by {@link FunctionCategories}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JRExprFunctionCategories {
	String[] value();
}
