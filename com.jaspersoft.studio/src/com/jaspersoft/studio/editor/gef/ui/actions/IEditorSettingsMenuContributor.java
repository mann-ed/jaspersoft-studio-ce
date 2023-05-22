/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.gef.ui.actions;

import java.util.List;

import org.eclipse.gef.ui.actions.ActionRegistry;

import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public interface IEditorSettingsMenuContributor {
	public void registerActions(ActionRegistry actionRegistry, JasperReportsConfiguration jConfig);

	public List<String> getActionIds();
}
