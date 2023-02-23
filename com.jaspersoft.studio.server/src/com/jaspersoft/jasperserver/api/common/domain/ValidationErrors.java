/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.common.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;

import java.util.List;

/**
 * The result of a validation operation, consisting of a set of validation
 * errors.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: ValidationErrors.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 * @since 1.0
 */
@JasperServerAPI
public interface ValidationErrors {

	/**
	 * Determines whether any error has been detected during the validation.
	 * 
	 * @return <code>true</code> if the validation resulted in error(s)
	 * @see #getErrors()
	 */
	boolean isError();
	
	/**
	 * Returns the list of errors detected during the validation.
	 * 
	 * @return the list of errors detected during the validation, empty if no
	 * errors were found 
	 */
	List getErrors();

	/**
	 * Adds an error to the list of validation errors.
	 * 
	 * @param error the error to add
	 * @since 1.2.0
	 */
	void add(ValidationError error);
	
	/**
	 * Removes any previously added errors that match a specified code and 
	 * field. 
	 * 
	 * @param code the code of the errors to remove
	 * @param field the field for which errors are to be removed
	 * @since 3.0.0
	 */
	void removeError(String code, String field);
	
}
