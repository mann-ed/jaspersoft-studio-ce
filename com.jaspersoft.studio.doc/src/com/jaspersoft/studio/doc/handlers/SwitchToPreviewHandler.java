/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.doc.handlers;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;

import com.jaspersoft.studio.editor.JrxmlEditor;

/**
 * 
 * Handler to switch the report editor into the preview tab
 * 
 * @author Oralndin Marco
 *
 */
public class SwitchToPreviewHandler extends Action {
	
		@Override
		public void run() {
			JrxmlEditor editor = (JrxmlEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			editor.setActiveEditor(editor.getEditor(2));
		}
}
