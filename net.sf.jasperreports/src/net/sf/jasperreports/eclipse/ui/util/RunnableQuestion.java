/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui.util;

import net.sf.jasperreports.eclipse.messages.Messages;

import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Runnable used to show a dialog in a graphic thread and return the result. The
 * dialog can be simple with a yes or no button or also provide a checkbox and
 * the value of it when the dialog is closed. The dialog supports also url links
 * that can be opened inside an external browser
 * 
 * @author Orlandin Marco
 *
 */
public class RunnableQuestion implements Runnable {

	/**
	 * The text of the checkbox. When null no checkbox is shown
	 */
	protected String checkMessage = null;

	/**
	 * The title of the dialog
	 */
	protected String title;

	/**
	 * The message of the dialog
	 */
	protected String message;

	/**
	 * The dialog where the message is shown
	 */
	protected ExtendedMessageDialog dialog = null;

	/**
	 * Create the runnable but dosen't show anything, all is done in the run
	 * method. The resulting dialog will have a message and two buttons
	 * 
	 * @param title
	 *            the title of the dialog
	 * @param message
	 *            the message of the dialog
	 */
	protected RunnableQuestion(String title, String message) {
		this.title = title;
		this.message = message;
	}

	/**
	 * Create the runnable but dosen't show anything, all is done in the run
	 * method. The resulting dialog will have a message, two buttons, and a
	 * checkbox
	 * 
	 * @param title
	 *            the title of the dialog
	 * @param message
	 *            the message of the dialog
	 * @param checkboxMessage
	 *            the text of the checkbox, if null no checkbox is shown and it
	 *            is equivalent to use the two parameters constructor
	 */
	protected RunnableQuestion(String title, String message, String checkboxMessage) {
		this(title, message);
		this.checkMessage = checkboxMessage;
	}

	private int defaultButton = 0;

	public void setDefaultButton(int defaultButton) {
		this.defaultButton = defaultButton;
	}

	/**
	 * Create the dialog and open it.
	 */
	@Override
	public void run() {
		dialog = new ExtendedMessageDialog(UIUtils.getShell(), title, null, message, MessageDialog.QUESTION,
				new String[] { Messages.UIUtils_AnswerYes, Messages.UIUtils_AnswerNo }, defaultButton, checkMessage);
		dialog.open();
	}

	/**
	 * Return the last state of the checkbox
	 * 
	 * @return true if the checkbox is selected, false otherwise. If the
	 *         checkbox was not created this return always false. If this is
	 *         called before to open the dialog trough the run method it return
	 *         always false
	 */
	public boolean getCheckboxResult() {
		return dialog != null ? dialog.getCheckboxResult() : false;
	}

	/**
	 * Return the exit value of the message dialog
	 * 
	 * @return true if the dialog was closed with yes, false if it was closed
	 *         with false. If this is called before to open the dialog trough
	 *         the run method it return always false
	 */
	public boolean getResult() {
		return dialog != null ? dialog.getResult() : false;
	}
}
