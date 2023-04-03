/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.plugin;

import java.util.List;
import java.util.Map;

import org.eclipse.gef.palette.PaletteEntry;

public interface IPaletteContributor {
	public static final String KEY_COMMON_ELEMENTS = "com.jaspersoft.studio.COMMON_ELEMENTS";
	public static final String KEY_COMMON_CONTAINER = "com.jaspersoft.studio.COMMON_CONTAINER";
	public static final String KEY_COMMON_TOOLS = "com.jaspersoft.studio.COMMON_TOOLS";

	public Map<String, List<PaletteEntry>> getPaletteEntries();
}
