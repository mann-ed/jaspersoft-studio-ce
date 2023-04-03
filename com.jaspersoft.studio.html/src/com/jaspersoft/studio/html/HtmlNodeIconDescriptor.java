/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.html;

import java.util.ResourceBundle;

import com.jaspersoft.studio.model.util.NodeIconDescriptor;
/*
 * The Class HtmlNodeIconDescriptor.
 * 
 * @author Narcis Marcu
 */
public class HtmlNodeIconDescriptor extends NodeIconDescriptor {

	/**
	 * Instantiates a new node icon descriptor.
	 * 
	 * @param name
	 *          the name
	 */
	public HtmlNodeIconDescriptor(String name) {
		super(name, Activator.getDefault());
	}

	/** The resource bundle icons. */
	private static ResourceBundle resourceBundleIcons;

	public ResourceBundle getResourceBundleIcons() {
		return resourceBundleIcons;
	}

	public void setResourceBundleIcons(ResourceBundle resourceBundleIcons) {
		HtmlNodeIconDescriptor.resourceBundleIcons = resourceBundleIcons;
	}
}
