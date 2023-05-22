/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.rcp.macosx;

import org.eclipse.swt.internal.cocoa.NSBundle;
import org.eclipse.swt.internal.cocoa.NSDictionary;
import org.eclipse.swt.internal.cocoa.NSNumber;
import org.eclipse.swt.internal.cocoa.NSString;
import org.eclipse.swt.internal.cocoa.NSUserDefaults;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;

/**
 * Startup implementation to provide a fix/workaround for the problem exposed in:
 * - https://community.jaspersoft.com/jaspersoft-studio/issues/11071
 * - https://bugs.eclipse.org/bugs/show_bug.cgi?id=530315
 * - https://bugs.eclipse.org/bugs/show_bug.cgi?id=568749 
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		Display display = UIUtils.getDisplay();
		if(display!=null) {
			NSUserDefaults defaults = NSUserDefaults.standardUserDefaults();
			/** Disable automatic quote & dash substitution for the application by default **/
			defaults.setInteger(0, NSString.stringWith("NSAutomaticQuoteSubstitutionEnabled"));
			defaults.setInteger(0, NSString.stringWith("NSAutomaticDashSubstitutionEnabled"));
		}
		
		// Allows to use http resources in SWT browser to prevent the ATS (Apple Transport Security) error
		// See the bug: https://bugs.eclipse.org/bugs/show_bug.cgi?id=568749
		NSDictionary allowNonHttps = NSDictionary.dictionaryWithObject(
				NSNumber.numberWithBool(true),
				NSString.stringWith("NSAllowsArbitraryLoads"));
		NSBundle.mainBundle().infoDictionary().setValue(
				allowNonHttps, NSString.stringWith("NSAppTransportSecurity"));
	}

}
