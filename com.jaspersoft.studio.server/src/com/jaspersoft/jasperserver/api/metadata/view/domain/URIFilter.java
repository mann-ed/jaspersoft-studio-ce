/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/

package com.jaspersoft.jasperserver.api.metadata.view.domain;

import com.jaspersoft.jasperserver.api.JasperServerAPI;

/**
 * Filter that matches a single resource based on its repository path/URI.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: URIFilter.java 19921 2010-12-11 14:52:49Z tmatyashovsky $
 * @since 2.0.0
 */
@JasperServerAPI
public class URIFilter implements FilterElement {
	
	private String uri;
	
	/**
	 * Creates an empty URI filter.
	 * 
	 * @see #setURI(String)
	 */
	public URIFilter() {
	}
	
	/**
	 * Creates a URI filter for a specific resource.
	 * 
	 * @param uri the repository path of the resource to find
	 */
	public URIFilter(String uri) {
		this.uri = uri;
	}

	/**
	 * @see Filter#applyURIFilter(URIFilter)
	 */
	public void apply(Filter filter) {
		filter.applyURIFilter(this);
	}

	/**
	 * Returns the repository path of the resource to find.
	 * 
	 * @return the repository path of the resource to find
	 */
	public String getURI() {
		return uri;
	}

	/**
	 * Sets the repository path of the resource that this filter will match.
	 * 
	 * @param uri the repository path of the resource to find
	 */
	public void setURI(String uri) {
		this.uri = uri;
	}

	/**
	 * @since 3.5.0
	 */
	public FilterElement cloneFilterElement() {
		return new URIFilter(uri);
	}

}
