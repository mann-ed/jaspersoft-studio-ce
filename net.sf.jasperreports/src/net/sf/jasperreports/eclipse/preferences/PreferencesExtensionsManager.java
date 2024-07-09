/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;

/**
 * Utility class to load the extension of existing preference pages
 * 
 * @author Orlandin Marco
 */
public class PreferencesExtensionsManager {
	
	/**
	 * Set of handlers used to existing studio preference pages. It is used as cache for the contributed items
	 */
	private static HashMap<String, List<IPreferencePageExtension>> contributedPreferencePages = null;

	/**
	 * Initialize the hashmap containing all the extensions
	 */
	private static void initContributedPreferencesPages() {
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(JasperReportsPlugin.PLUGIN_ID, "preferencePageExtension");

		contributedPreferencePages = new HashMap<String, List<IPreferencePageExtension>>();
		for (IConfigurationElement el : config) {
			Object defaultSupportClazz;
			try {
				defaultSupportClazz = el.createExecutableExtension("class");
				if (defaultSupportClazz instanceof IPreferencePageExtension) {
					IPreferencePageExtension handler = (IPreferencePageExtension) defaultSupportClazz;
					String extendedPage = el.getAttribute("extendedPageId");
					if (extendedPage != null){
						List<IPreferencePageExtension> pagesExtension = contributedPreferencePages.get(extendedPage);
						if (pagesExtension == null){
							pagesExtension = new ArrayList<IPreferencePageExtension>();
							pagesExtension.add(handler);
							contributedPreferencePages.put(extendedPage, pagesExtension);
						} else {
							pagesExtension.add(handler);
						}
					}
				}
			} catch (CoreException e) {
				JasperReportsPlugin.getDefault().log(new Status(IStatus.ERROR, JasperReportsPlugin.PLUGIN_ID,
						"An error occurred while trying to create the new class.", e));
			}
		}
	}
	
	/**
	 * Return the List of extension of preferences page. This is used to add controls to an 
	 * existing preference page, instead to create a new one
	 * 
	 * @param extendedPageId the ID of the page that will be extended
	 * @return The list of the extensions or null if there are no extension for that page
	 */
	public static List<IPreferencePageExtension> getContributedPreferencePages(String extendedPageId) {
		if (contributedPreferencePages == null) {
			initContributedPreferencesPages();
		}
		List<IPreferencePageExtension> pages = contributedPreferencePages.get(extendedPageId);
		return pages;
	}
	
	/**
	 * Return the List of all the extensions of preferences pages. 
	 * @return The list of the extensions or an empty list
	 */
	public static List<IPreferencePageExtension> getContributedPreferencePages() {
		if (contributedPreferencePages == null) {
			initContributedPreferencesPages();
		}
		List<IPreferencePageExtension> pages = new ArrayList<>();
		for(Entry<String, List<IPreferencePageExtension>> entry : contributedPreferencePages.entrySet()) {
			pages.addAll(entry.getValue());
		}
		return pages;
	}

}
