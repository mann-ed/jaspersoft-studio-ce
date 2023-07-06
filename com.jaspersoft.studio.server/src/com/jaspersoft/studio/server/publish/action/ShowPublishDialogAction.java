/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.publish.action;

import com.jaspersoft.studio.editor.action.snap.ACheckResourcePrefAction;
import com.jaspersoft.studio.server.editor.JRSEditorContributor;
import com.jaspersoft.studio.server.messages.Messages;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class ShowPublishDialogAction extends ACheckResourcePrefAction {
	public static final String ID = JRSEditorContributor.KEY_PUBLISH2JSS_SILENT;

	public ShowPublishDialogAction(JasperReportsConfiguration jrConfig) {
		super(Messages.ShowPublishDialogAction_1, jrConfig);
		setText(Messages.ShowPublishDialogAction_2);
		setToolTipText(Messages.ShowPublishDialogAction_3);
		setId(ID);
	}

	@Override
	protected String getProperty() {
		return ID;
	}
}
