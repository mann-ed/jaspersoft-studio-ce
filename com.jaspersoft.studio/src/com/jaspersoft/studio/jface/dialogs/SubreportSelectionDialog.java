/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.jface.dialogs;

import org.eclipse.swt.widgets.Shell;

import com.jaspersoft.studio.messages.Messages;

/**
 * Dialog proposed when an image needs to be selected.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class SubreportSelectionDialog extends FileSelectionDialog {

	private static final String JRXML_TYPE = "*.jrxml"; //$NON-NLS-1$

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public SubreportSelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected String getDialogTitle() {
		return Messages.SubreportSelectionDialog_Title;
	}

	@Override
	protected String[] getImageModesAndHeaderTitles() {
		return new String[] { 
				Messages.SubreportSelectionDialog_SectionTitle, 
				Messages.SubreportSelectionDialog_WorkspaceModeTxt,
				Messages.SubreportSelectionDialog_AbsolutePathModeTxt,
				Messages.SubreportSelectionDialog_UrlModeTxt,
				Messages.SubreportSelectionDialog_NoSubreportModeTxt,
				Messages.SubreportSelectionDialog_CustomExpressionModeTxt };
	}

	@Override
	protected String getDefaultResourcesPattern() {
		return Messages.SubreportSelectionDialog_JRXMLResourcePattern;
	}

	@Override
	public String[] getSupportedTypes() {
		return new String[] { JRXML_TYPE, ALL_FILES_TYPE }; 
	}
	
	@Override
	public String getSupportedTypeName(String type) {
		switch (type) {
		case JRXML_TYPE:
			return Messages.SubreportSelectionDialog_JasperReportsFilesTypeTxt;
		case ALL_FILES_TYPE:
			return Messages.SubreportSelectionDialog_AllFilesTypeTxt;
		default:
			return super.getSupportedTypeName(type);
		}
	}
}
