/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.widgets.map.support;

import java.net.URLEncoder;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.widgets.map.core.Animation;
import com.jaspersoft.studio.widgets.map.core.LatLng;
import com.jaspersoft.studio.widgets.map.core.MarkerOptions;

import net.sf.jasperreports.components.map.MapComponent;

/**
 * A bunch of utility methods to deal with the map component.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class GMapUtils {

	public static final int OPTIONS_LAT_INDEX = 0;
	public static final int OPTIONS_LNG_INDEX = 1;
	public static final int OPTIONS_DRAGGABLE_INDEX = 2;
	public static final int OPTIONS_ANIMATION_INDEX = 3;
	public static final int OPTIONS_VISIBLE_INDEX = 4;
	public static final int OPTIONS_CLICKABLE_INDEX = 5;

	/**
	 * Returns the marker options extracted from the specified arguments.
	 * 
	 * @param arguments
	 *            the arguments containing the information
	 * @return {@link MarkerOptions} instance
	 */
	public static MarkerOptions getMarkerOptions(Object[] arguments) {
		MarkerOptions options = new MarkerOptions();
		// LatLng
		options.setPosition(new LatLng((Double) arguments[OPTIONS_LAT_INDEX], (Double) arguments[OPTIONS_LNG_INDEX]));
		// Draggable
		options.setDraggable((Boolean) arguments[OPTIONS_DRAGGABLE_INDEX]);
		// Animation
		options.setAnimation(getMarkerAnimation((Double) arguments[OPTIONS_ANIMATION_INDEX]));
		// Visible
		options.setVisible((Boolean) arguments[OPTIONS_VISIBLE_INDEX]);
		// Clickable
		options.setClickable((Boolean) arguments[OPTIONS_CLICKABLE_INDEX]);
		return options;
	}

	/**
	 * Returns the position extracted from the specified arguments.
	 * 
	 * @param arguments
	 *            the arguments containing the information
	 * @return the latitude and longitude information
	 */
	public static LatLng getPosition(Object[] arguments) {
		return new LatLng((Double) arguments[0], (Double) arguments[1]);
	}

	/**
	 * Returns the type of animation given the specific value.
	 * 
	 * @param value
	 *            value representing the marker animation
	 * @return the animation type
	 */
	public static Animation getMarkerAnimation(Double value) {
		if (value != null) {
			switch (value.intValue()) {
			case 1:
				// google.maps.Animation.BOUNCE => 1
				return Animation.BOUNCE;
			case 2:
				// google.maps.Animation.DROP => 2
				return Animation.DROP;
			default:
				return null;
			}
		}
		return null;
	}

	/**
	 * Returns the coordinates of the specified address.
	 * 
	 * @param addressText
	 *            the address to look
	 * @return the latitude and longitude information
	 */
	public static LatLng getAddressCoordinates(String addressText, MapCredentials mapCredentials) {
		LatLng coordinates = null;
		HttpGet locateAddressGET = null;
		String apikeyDetails = "";
		if(mapCredentials!=null) {
			apikeyDetails = "key="+mapCredentials.getApiKey()+"&";
		}
		
		try {
			String addressUrlEncoded = URLEncoder.encode(addressText, "UTF-8");
			locateAddressGET = new HttpGet("https://maps.google.com/maps/api/geocode/json?" + apikeyDetails + "address=" + addressUrlEncoded);
			
			try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
				CloseableHttpResponse response = httpclient.execute(locateAddressGET);
				int httpRetCode = response.getStatusLine().getStatusCode();
				if (httpRetCode == HttpStatus.SC_OK) {
					String responseBodyAsString = EntityUtils.toString(response.getEntity());
					System.out.println(responseBodyAsString);
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
					mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
					mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
					JsonNode jsonRoot = mapper.readTree(responseBodyAsString);
					if (jsonRoot != null && jsonRoot.path("results") != null && jsonRoot.path("results").has(0)
							&& jsonRoot.path("results").get(0).path("geometry") != null) {
						JsonNode location = jsonRoot.path("results").get(0).path("geometry").path("location");
						if (location != null) {
							JsonNode lat = location.get("lat");
							JsonNode lng = location.get("lng");
							coordinates = new LatLng(lat.asDouble(), lng.asDouble());
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (locateAddressGET != null) {
				locateAddressGET.releaseConnection();
			}
		}
		return coordinates;
	}

	/**
	 * Finds the Google Map credentials (api key) information from the report property set.
	 * 
	 * @param jConfig the {@link JasperReportsConfiguration} context
	 * @return the map credentials (api key) details, <code>null</code> if none available
	 */
	public static MapCredentials getMapCredentials(JasperReportsConfiguration jConfig) {
		MapCredentials credentials = null;
		if(jConfig!=null && jConfig.getJasperDesign()!=null) {
			String key = jConfig.getJasperDesign().getProperty(MapComponent.PROPERTY_KEY);
			credentials = new MapCredentials(key);
		}
		return credentials;
	}
}
