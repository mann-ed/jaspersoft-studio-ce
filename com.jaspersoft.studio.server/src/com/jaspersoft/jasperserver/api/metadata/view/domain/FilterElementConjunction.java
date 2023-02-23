/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.metadata.view.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;



/**
 * A conjunction of two or more filter elements.
 * 
 * <p>
 * The composing filter elements will be applied using AND as logical operation.
 * </p>
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: FilterElementConjunction.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 * @since 1.0
 */
@JasperServerAPI
public class FilterElementConjunction extends FilterElementCollection implements
		FilterElement {

	/**
	 * Creates an empty element conjunction.
	 * 
	 * @see FilterElementCollection#addFilterElement(FilterElement)
	 */
	public FilterElementConjunction() {
		super();
	}

	/**
	 * @see Filter#applyConjunction(java.util.List)
	 */
	public void apply(Filter filter) {
		filter.applyConjunction(getFilterElements());
	}

	/**
	 * Performs a deep clone of the filter elements that compose this 
	 * conjunction.
	 * 
	 * @since 3.5.0
	 */
	public FilterElement cloneFilterElement() {
		FilterElementConjunction clone = new FilterElementConjunction();
		clone.addClonedElements(getFilterElements());
		return clone;
	}
}
