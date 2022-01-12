/*******************************************************************************
 * Copyright (C) 2010 - 2022. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.action.pdf;

/** 
 * Enum for heading tags used by JasperReports for Section 508 PDF export.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public enum Heading {
	H1((byte)1,"H1","h1"), //$NON-NLS-1$ //$NON-NLS-2$
	H2((byte)2,"H2","h2"), //$NON-NLS-1$ //$NON-NLS-2$
	H3((byte)3,"H3","h3"), //$NON-NLS-1$ //$NON-NLS-2$
	H4((byte)4,"H4","h4"), //$NON-NLS-1$ //$NON-NLS-2$
	H5((byte)5,"H5","h5"), //$NON-NLS-1$ //$NON-NLS-2$
	H6((byte)6,"H6","h6"); //$NON-NLS-1$ //$NON-NLS-2$
	
	private byte value;
	private String name;
	private String txtValue;
	
	private Heading(byte value,String name, String txtValue) {
		this.value = value;
		this.name = name;
		this.txtValue = txtValue;
	}
	
	public byte getValue() {
		return value;
	}
	
	public String getTextValue() {
		return this.txtValue;
	}

	public String getName() {
		return this.name;
	}
}
