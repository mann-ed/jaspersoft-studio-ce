/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class MScopedPreferenceStore extends ScopedPreferenceStore {

	private boolean withDefault = true;
	
	protected IScopeContext context;
	
	protected String qualifier;
	
	public MScopedPreferenceStore(IScopeContext context, String qualifier) {
		super(context, qualifier);
		this.context = context;
		this.qualifier = qualifier;
	}

	@Override
	public IEclipsePreferences[] getPreferenceNodes(boolean includeDefault) {
		return super.getPreferenceNodes(withDefault);
	}

	public void setWithDefault(boolean withDefault) {
		this.withDefault = withDefault;
	}
	
	/**
	 * Essentially this method does the same of ScopedPreferenceStore:getStorePreferences(),
	 * but since it has package visibility and can not be accessed this method replace it
	 * to be used from studio
	 */
	public IEclipsePreferences getQualifierStore(){
		return context.getNode(qualifier);
	}
}
