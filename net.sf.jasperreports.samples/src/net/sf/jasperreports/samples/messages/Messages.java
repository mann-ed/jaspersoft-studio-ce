/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.samples.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "net.sf.jasperreports.samples.messages.messages"; //$NON-NLS-1$
	public static String JasperReportsSamplesProvider_UnzipSamplesError;
	public static String SampleNewWizard_Description;
	public static String SampleNewWizard_Title;
	public static String SamplesManager_AddSampleProviderError;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
