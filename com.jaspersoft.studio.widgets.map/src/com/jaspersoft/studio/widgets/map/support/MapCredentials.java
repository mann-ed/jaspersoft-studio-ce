/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.widgets.map.support;

/**
 * Credentials for Google Map component.
 * This simple class simply holds the api key information to be used for properly render the Map component preview.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class MapCredentials {

	private String apiKey;

	public MapCredentials(String apiKey) {
		this.apiKey=apiKey;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
}
