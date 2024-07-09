/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;

/**
 * Define an extension for an existing preference page. This is used to contribute
 * controls to a predefined preference page, without creating a new one
 * 
 * @author Orlandin Marco
 *
 */
public interface IPreferencePageExtension {
	
	/**
	 * Create the fields on an existing page
	 * 
	 * @param parent the composite of the target page, not null
	 * @param page the host page where the controls will be created, not null
	 */
	public void createContributedFields(Composite parent, IPreferenceExtendablePage page);
	
	/**
	 * When the apply button is pressed on the host page is pressed, this is called on 
	 * every contributed extensions on that page
	 */
	public void performApply();
	
	/**
	 * When the cancel button is pressed on the host page is pressed, this is called on 
	 * every contributed extensions on that page
	 */
	public void performCancel();
	
	/**
	 * When the defaults button is pressed on the host page is pressed, this is called on 
	 * every contributed extensions on that page
	 */
	public void performDefaults();
	
	/**
	 * This will initialize the default properties, typically it will add properties to the
	 * default JasperReports properties
	 */
	public void initDefaultProperties(IPreferenceStore store);

}
