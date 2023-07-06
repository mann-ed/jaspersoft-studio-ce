/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.properties.view;

import java.util.List;

import com.jaspersoft.studio.properties.view.validation.ValidationError;

/**
 * An abstract implementation of a section in a tab that overrides the tabs that
 * are provided by the tabbed property registry with a new list of tabs.
 * 
 * @author Anthony Hunter
 * @since 3.4
 */
public class AbstractOverridableTabListPropertySection extends AbstractPropertySection implements IOverridableTabList {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.IOverridableTabList#getTabs()
	 */
	public ITabItem[] getTabs() {
		return new ITabItem[] {};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.views.properties.tabbed.IOverridableTabList#selectTab(int)
	 */
	public void selectTab(int tab) {
		/* no default implementation */
	}

	@Override
	public void resetErrors() {
		// TODO Auto-generated method stub

	}

	@Override
	public void showErrors(List<ValidationError> errors) {
		// TODO Auto-generated method stub
	}
}
