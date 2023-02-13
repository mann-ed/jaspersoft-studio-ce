/*******************************************************************************
 * Copyright (C) 2010 - 2023. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.action.reportsplitting;

import java.util.List;

import org.eclipse.ui.IWorkbenchPart;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.editor.action.ACachedSelectionAction;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.APropertyNode;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;

/**
 * Action to trigger the opening for the configuration dialog related to report splitting.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public class ReportSplittingAction extends ACachedSelectionAction {
	
	public static final String ID = "ReportSplittingElementAction"; //$NON-NLS-1$
	private APropertyNode element;

	public ReportSplittingAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(false);
	}
	
	@Override
	protected void init() {
		super.init();
		setText(Messages.ReportSplittingAction_Text);
		setToolTipText(Messages.ReportSplittingAction_Tooltip);
		setId(ID);
		setImageDescriptor(JaspersoftStudioPlugin.getInstance().getImageDescriptor("icons/resources/break-16.png")); //$NON-NLS-1$
		setEnabled(false);
	}
	
	@Override
	protected boolean calculateEnabled() {
		List<Object> elements = editor.getSelectionCache().getSelectionModelForType(APropertyNode.class);
		if(elements.size()==1 && isAllowed(elements.get(0))) {
			element=(APropertyNode) elements.get(0);
			return true;
		}
		else {
			element=null;
			return false;
		}
	}
	
	/**
	 * Currently we want to show the menu for any type of element
	 * except frames, subreports, tables and lists.
	 */
	protected boolean isAllowed(Object object) {
		return (object instanceof APropertyNode && 
				((APropertyNode)object).isReportSplittingSupported());
	}
	
	@Override
	public void run() {
		ReportSplittingEditDialog dialog = new ReportSplittingEditDialog(element, element.getJasperConfiguration(), UIUtils.getShell());
		dialog.open();
	}

}
