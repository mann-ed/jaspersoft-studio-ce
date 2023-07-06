/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.widgets.map.core;

/**
 * Animations that can be played on a marker.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * @see Marker
 * @see {@link https://developers.google.com/maps/documentation/javascript/reference#MarkerOptions}
 *
 */
public enum Animation {
	BOUNCE,	// Marker bounces until animation is stopped.
	DROP	// Marker falls from the top of the map ending with a small bounce.
}
