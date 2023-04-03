/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.wizard.resource.page;

import org.eclipse.core.databinding.DataBindingContext;

import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.server.messages.Messages;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.wizard.resource.page.selector.ATextFileResourcePageContent;

public class ResourceBundlePageContent extends ATextFileResourcePageContent {

	public ResourceBundlePageContent(ANode parent, AMResource resource, DataBindingContext bindingContext) {
		super(parent, resource, bindingContext);
	}

	public ResourceBundlePageContent(ANode parent, AMResource resource) {
		super(parent, resource);
	}

	@Override
	public String getPageName() {
		return "com.jaspersoft.studio.server.page.resourcebundle";
	}

	@Override
	public String getName() {
		return Messages.RDResourceBundlePage_title;
	}

	@Override
	protected String[] getFilter() {
		return new String[] { "*.*", "*.properties" }; //$NON-NLS-1$
	}

}
