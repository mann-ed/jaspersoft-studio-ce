/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.jface.dialogs;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.jaspersoft.studio.messages.Messages;

/**
 * Dialog proposed when an image needs to be selected.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class StyleTemplateSelectionDialog extends FileSelectionDialog {

	private static final String JRTX_TYPE = "*.jrtx"; //$NON-NLS-1$
	
	/**
	 * Expression that will be shown in the dialog once opened
	 */
	private String initialExpression = null;	
	
	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public StyleTemplateSelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * @return the title for the dialog
	 */
	protected String getDialogTitle() {
		return Messages.StyleTemplateSelectionDialog_Title;
	}

	/**
	 * Returns an array of strings containing the title for the modes section, plus the title of every mode.
	 * <p>
	 * 
	 * Default implementation would return 6 strings, including 1 title and the following 5 modes:
	 * <ol>
	 * <li>workspace resource;</li>
	 * <li>absolute path in filesystem;</li>
	 * <li>URL;</li>
	 * <li>no image;</li>
	 * <li>custom expression</li>
	 * </ol>
	 * 
	 * @return the title and labels for the group of modes
	 */
	@Override
	protected String[] getImageModesAndHeaderTitles() {
		return new String[] { 
				Messages.StyleTemplateSelectionDialog_SectionTitle, 
				Messages.StyleTemplateSelectionDialog_WorkspaceModeTxt,
				Messages.StyleTemplateSelectionDialog_AbsolutePathTxt,
				Messages.StyleTemplateSelectionDialog_RemoteURLTxt,
				Messages.StyleTemplateSelectionDialog_NoStyleTxt,
				Messages.StyleTemplateSelectionDialog_CustomExpressionTxt };
	}

	@Override
	protected String getDefaultResourcesPattern() {
		return JRTX_TYPE;
	}

	@Override
	public String[] getSupportedTypes() {
		return new String[] { JRTX_TYPE, ALL_FILES_TYPE };
	}
	
	@Override
	public String getSupportedTypeName(String type) {
		switch (type) {
		case JRTX_TYPE:
			return Messages.StyleTemplateSelectionDialog_StyleTemplateFilesTxt;
		case ALL_FILES_TYPE:
			return Messages.StyleTemplateSelectionDialog_AllFilesTxt;
		default:
			return super.getSupportedTypeName(type);
		}
	}
	
	/**
	 * Set the expression that will be shown in the dialog 
	 * once opened
	 * 
	 * @param expression the expression text, can be null if 
	 * nothing should be shown
	 */
	public void setInitialExpression(String expression){
		this.initialExpression = expression;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Control control = super.createDialogArea(parent);
		if (initialExpression != null){
			showCustomExpression(initialExpression);	
		}
		return control;
	}
}
