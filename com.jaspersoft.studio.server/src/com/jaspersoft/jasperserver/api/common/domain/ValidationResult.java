/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.common.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;

import java.util.List;

/**
 * The result of a report unit validation.
 * 
 * @author tkavanagh
 * @version $Id: ValidationResult.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 * @since 1.0
 */
//TODO add ValidationDetail to the API
@JasperServerAPI
public interface ValidationResult {

	/**
	 * Validation state used when the validation was successful.
	 * 
	 * @see #getValidationState()
	 */
	String STATE_VALID = "VALID";
	
	/**
	 * Validation state used when errors were detected during the validation.
	 * 
	 * @see #getValidationState()
	 */
	String STATE_ERROR = "ERROR";

	/**
	 * Returns the list of items detected during validation.
	 * 
	 * @return list of <code>ValidationDetail<code> objects
	 */
	public List getResults();
	
	/**
	 * Returns the state of the validation result.
	 * 
	 * @return <code>STATE_ERROR</code> if the validation resulted in errors
	 * @see #STATE_VALID
	 * @see #STATE_ERROR
	 */
	public String getValidationState();
	
}
