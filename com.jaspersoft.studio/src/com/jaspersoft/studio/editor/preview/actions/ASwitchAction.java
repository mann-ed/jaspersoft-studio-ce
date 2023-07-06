/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.preview.actions;

import org.eclipse.jface.action.Action;

import com.jaspersoft.studio.editor.preview.MultiPageContainer;

public abstract class ASwitchAction extends Action {
	protected MultiPageContainer container;
	protected String viewKey;

	public ASwitchAction(MultiPageContainer container, String view) {
		super(view);
		this.container = container;
		this.viewKey = view;
	}

	public ASwitchAction(MultiPageContainer container, String view, int style) {
		super(view, style);
		this.container = container;
		this.viewKey = view;
	}

	@Override
	public void run() {
		if (viewKey != null) {
			container.switchView(viewKey);
		}
	}
}
