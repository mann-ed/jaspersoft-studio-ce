/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.preview.actions;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.editor.preview.MultiPageContainer;
import com.jaspersoft.studio.editor.preview.view.control.ReportController;
import com.jaspersoft.studio.messages.MessagesByKeys;

public class ViewBookmarksAction extends ASwitchAction {

	public ViewBookmarksAction(MultiPageContainer pContainer) {
		super(pContainer, ReportController.FORM_BOOKMARKS);
		setImageDescriptor(JaspersoftStudioPlugin.getInstance().getImageDescriptor("icons/bookmark.png"));
		setToolTipText(MessagesByKeys.getString(ReportController.FORM_BOOKMARKS));
	}
}
