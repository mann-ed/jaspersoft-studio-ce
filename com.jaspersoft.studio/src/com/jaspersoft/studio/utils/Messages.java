/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.utils;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.jaspersoft.studio.utils.messages"; //$NON-NLS-1$
	public static String UIUtil_CopyMenuItemText;
	public static String UIUtil_CopyMenuItemTooltip;
	public static String UIUtil_CutMenuItemText;
	public static String UIUtil_CutMenuItemTooltip;
	public static String UIUtil_PasteMenuItemText;
	public static String UIUtil_PasteMenuItemTooltip;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
