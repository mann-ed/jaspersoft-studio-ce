/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.plugin;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.part.WorkbenchPart;

/**
 * Interface describing factories containing additional actions that need to be displayed in different JSS places (i.e. Outline view)
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public interface ICustomActionsFactory {

	/** Category ID for scriptlets related actions */
	public static final String CATEGORY_SCRIPTLET = "com.jaspersoft.studio.model.scriptlet"; //$NON-NLS-1$
	
	/** Suffix for the extension point */
	public static final String EXTENSION_POINT_SUFFIX = "customStudioActions"; //$NON-NLS-1$
	
	/** Main property name for the extension contribution */ 
	public static final String EXTENSION_PROPERTY_NAME = "classFactory"; //$NON-NLS-1$
	
	/**
	 * @return the ID of the factory
	 */
	public String getFactoryID();
	
	/**
	 * @return the ID of the category to which the factory belongs to
	 */
	public String getFactoryCategoryID();
	
	/**
	 * Retrieves the list of actions for the specified workbench part.
	 * 
	 * @param part the workbench part 
	 * @return list of actions
	 */
	public List<Action> getActions(WorkbenchPart part);

	/**
	 * @return the list of actions IDs
	 */
	public List<String> getActionIDs();
	
}
