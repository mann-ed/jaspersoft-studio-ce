/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util;

import net.sf.jasperreports.components.map.type.MapTypeEnum;

public class BasicMapInfoData {
	public Double latitude;
	public Double longitude;
	public String address;
	public MapTypeEnum mapType;
	public int zoom;

	public BasicMapInfoData() {
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public MapTypeEnum getMapType() {
		return mapType;
	}

	public void setMapType(MapTypeEnum mapType) {
		this.mapType = mapType;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

}
