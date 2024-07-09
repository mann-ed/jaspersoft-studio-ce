/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse;

import org.eclipse.ui.IStartup;

/**
 * Enables the enhanced console printing (System.out).
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class EnhancedSystemOutStartup implements IStartup {

	@Override
	public void earlyStartup() {
		DebugStream.activate();
	}

}
