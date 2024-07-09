/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.preferences;

import org.eclipse.jface.preference.FieldEditor;

/**
 * Interface to implement into a preference page to define an extensible preference page
 * 
 * @author Orlandin Marco
 *
 */
public interface IPreferenceExtendablePage {

	/**
	 * Create a field on the host page. Remember that the field by default will use
	 * the storage of the host page, if you want to use a different storage override the method
	 * getPreferenceStorage of the add FieldEditor
	 * 
	 * @param editor the field editor to add, must be not null
	 */
	public void addField(FieldEditor editor);
	
	/**
	 * Return the ID of the host page, this must be used by the pages that want to extend the host
	 * page to identify it
	 * 
	 * @return a not null string used as identifier of the page
	 */
	public String getPageId();
}
