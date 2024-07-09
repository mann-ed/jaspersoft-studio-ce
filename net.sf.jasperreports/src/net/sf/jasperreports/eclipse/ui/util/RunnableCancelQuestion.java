/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui.util;

import net.sf.jasperreports.eclipse.messages.Messages;

import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Extension of a runnable question to add the cancel button
 * 
 * @author Orlandin Marco
 *
 */
public class RunnableCancelQuestion extends RunnableQuestion {
	
	public enum RESPONSE_TYPE{YES, NO, CANCEL}
	
	/**
	 * The question response
	 */
	private RESPONSE_TYPE response = RESPONSE_TYPE.CANCEL;
	
	protected RunnableCancelQuestion(String title, String message,String checkboxMessage) {
		super(title, message, checkboxMessage);
	}
	
	protected RunnableCancelQuestion(String title, String message) {
		super(title, message);
	}


	/**
	 * Create the dialog and open it. 
	 */
	@Override
	public void run() {
		dialog = new ExtendedMessageDialog(UIUtils.getShell(), title,null, message, MessageDialog.QUESTION, new String[] { Messages.UIUtils_AnswerYes, Messages.UIUtils_AnswerNo, Messages.UIUtils_AnswerCancel }, 0, checkMessage);
		int returnCode = dialog.open();
		if (returnCode == 0) response = RESPONSE_TYPE.YES;
		else if (returnCode == 1) response = RESPONSE_TYPE.NO;
		else response = RESPONSE_TYPE.CANCEL;
	}
	
	/**
	 * Return the response to the question
	 * 
	 * @return a not null RESPONSE_TYPE, could be YES, NO or CANCEL
	 */
	public RESPONSE_TYPE getResponse(){
		return response;
	}
	
}
