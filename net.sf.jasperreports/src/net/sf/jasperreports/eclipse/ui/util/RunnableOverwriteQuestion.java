/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui.util;

import org.eclipse.jface.dialogs.MessageDialog;

import net.sf.jasperreports.eclipse.messages.Messages;

/**
 * Extension of a runnable question to propose the option to overwrite a file, keep both or skip.
 * Using when coping resources
 * 
 * @author Orlandin Marco
 *
 */
public class RunnableOverwriteQuestion extends RunnableQuestion {
	
	public enum RESPONSE_TYPE{OVERWRITE, KEEP_BOTH, SKIP}
	
	/**
	 * The question response
	 */
	private RESPONSE_TYPE response = RESPONSE_TYPE.SKIP;
	
	/**
	 * Flag used to know if the keep both is enabled as action
	 */
	private boolean allowKeepBoth;
	
	protected RunnableOverwriteQuestion(String title, String message,String checkboxMessage, boolean allowKeepBoth) {
		super(title, message, checkboxMessage);
		this.allowKeepBoth = allowKeepBoth;
	}
	
	protected RunnableOverwriteQuestion(String title, String message, boolean allowKeepBoth) {
		super(title, message);
		this.allowKeepBoth = allowKeepBoth;
	}

	/**
	 * Create the dialog and open it. 
	 */
	@Override
	public void run() {
		String buttons[] = null;
		if (allowKeepBoth){
			buttons = new String[] {Messages.RunnableOverwriteQuestion_overwrite, Messages.RunnableOverwriteQuestion_keepBoth, Messages.RunnableOverwriteQuestion_skip};
		} else {
			buttons = new String[] {Messages.RunnableOverwriteQuestion_overwrite, Messages.UIUtils_AnswerCancel};
		}
		dialog = new ExtendedMessageDialog(UIUtils.getShell(), title,null, message, MessageDialog.QUESTION, buttons, 0, checkMessage);
		int returnCode = dialog.open();
		if (returnCode == 0) response = RESPONSE_TYPE.OVERWRITE;
		else if (returnCode == 1 && allowKeepBoth) response = RESPONSE_TYPE.KEEP_BOTH;
		else response = RESPONSE_TYPE.SKIP;
	}
	
	/**
	 * Return the response to the question
	 * 
	 * @return a not null RESPONSE_TYPE, could be YES, NO or CANCEL
	 */
	public RESPONSE_TYPE getResponse(){
		return response;
	}
	
	public static RESPONSE_TYPE showQuestion(String title, String message){
		return showQuestion(title, message, true);
	}
	
	public static RESPONSE_TYPE showQuestion(String title, String message, boolean allowKeepBoth){
		RunnableOverwriteQuestion questionMessage = new RunnableOverwriteQuestion(title, message, allowKeepBoth);
		UIUtils.getDisplay().syncExec(questionMessage);
		return questionMessage.getResponse();
	}
}
