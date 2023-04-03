/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.metadata.view.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;


/**
 * Filter criterion interface.
 * 
 * @author Sherman Wood
 * @author Lucian Chirita
 * @version $Id: FilterElement.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 * @since 1.0
 * @see FilterElementCollection
 */
@JasperServerAPI
public interface FilterElement {

	/**
	 * Applies this criterion in a filter implementation.
	 * 
	 * @param filter the filter implementation
	 * @see Filter
	 */
	void apply(Filter filter);
	
	/**
	 * Creates a clone of the filter criterion.
	 * 
	 * @return a clone of this filter
	 * @since 3.5.0
	 */
	FilterElement cloneFilterElement();
	
}
