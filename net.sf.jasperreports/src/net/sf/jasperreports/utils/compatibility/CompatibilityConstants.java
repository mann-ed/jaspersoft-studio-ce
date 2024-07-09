/*******************************************************************************
 * Copyright (C) 2010 - 2020. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.utils.compatibility;

import org.eclipse.swt.SWT;

/** 
 * This interface helps keep track of the Eclipse/SWT constants that are not present
 * in older Eclipse installation where we still want to provide our Community plug-in
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public interface CompatibilityConstants {

	public interface Colors {
		
		/**
		 * System color used to paint disabled foreground areas (value is 39).
		 *
		 * @see SWT#COLOR_WIDGET_DISABLED_FOREGROUND
		 */
		public static final int COLOR_WIDGET_DISABLED_FOREGROUND = 39;
		
	}
	
}
