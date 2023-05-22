/*******************************************************************************
 * Copyright (C) 2010 - 2022. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.context;

import org.eclipse.core.resources.IResource;

/**
 * Utility interface that should be implemented by clients willing to provide
 * some additional generic features during the pre and post context switch action phases.
 * <p>
 * 
 * These operations can be related to workbench tasks or even resource specific, 
 * trying to cover operations that are not performed inside the {@link AEditorContext} instance
 * that is usually related to an open JRXML editor.
 * <p>
 * 
 * Example: refreshing the decorators (image/text) containing additional information
 *  
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public interface IResourceContextSwitchUtil {
	
	/**
	 * Operations performed before the actual context switch operation is performed.
	 * 
	 * @param resource the workspace resource on which the context switch has been invoked
	 */
	void preContextSwitchOperations(IResource resource);
	
	/**
	 * Operations performed after the actual context switch operations is performed.
	 * 
	 * @param resource the workspace resource on which the context switch has been invoked
	 */
	void postContextSwitchOperations(IResource resource);
	
	/**
	 * Operations performed all the times a context switch happens.<br/>
	 * Ideally when a context switch happens, all implementors should invoke this method.
	 *  
	 * @param resource the workspace resource on which the context switch has been invoked
	 * @param the new state that has been set with the context switch 
	 */
	void essentialOperations(IResource resource, String newState);

}
