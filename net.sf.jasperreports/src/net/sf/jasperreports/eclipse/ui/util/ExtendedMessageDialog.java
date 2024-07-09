/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui.util;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Extension of the standard MessageDialog to support text with URL link inside
 * and an additional checkbox control. The URL text must be placed with the <a
 * href="..." tag and the link will be clickable and in this case it will be
 * opened inside the default browser.
 * 
 * The additional checkbox can be shown with it's own message and it's result is
 * stored inside the dialog (even if the dialog is closed with the No button)
 * 
 * @author Orlandin Marco
 *
 */
public class ExtendedMessageDialog extends MessageDialog {

	/**
	 * The text of the checkbox. When null no checkbox is shown
	 */
	private String checkMessage = null;

	/**
	 * The state of the checkbox when the dialog is closed
	 */
	private boolean checkboxResult = false;

	/**
	 * The result of the dialog, true for yes, false for no
	 */
	private boolean result = false;

	/**
	 * Create a message dialog. Note that the dialog will have no visual
	 * representation (no widgets) until it is told to open.
	 * <p>
	 * The labels of the buttons to appear in the button bar are supplied in
	 * this constructor as an array. The <code>open</code> method will return
	 * the index of the label in this array corresponding to the button that was
	 * pressed to close the dialog.
	 * </p>
	 * <p>
	 * <strong>Note:</strong> If the dialog was dismissed without pressing a
	 * button (ESC key, close box, etc.) then {@link SWT#DEFAULT} is returned.
	 * Note that the <code>open</code> method blocks.
	 * </p>
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param dialogTitle
	 *            the dialog title, or <code>null</code> if none
	 * @param dialogTitleImage
	 *            the dialog title image, or <code>null</code> if none
	 * @param dialogMessage
	 *            the dialog message
	 * @param dialogImageType
	 *            one of the following values:
	 *            <ul>
	 *            <li><code>MessageDialog.NONE</code> for a dialog with no
	 *            image</li>
	 *            <li><code>MessageDialog.ERROR</code> for a dialog with an
	 *            error image</li>
	 *            <li><code>MessageDialog.INFORMATION</code> for a dialog with
	 *            an information image</li>
	 *            <li><code>MessageDialog.QUESTION </code> for a dialog with a
	 *            question image</li>
	 *            <li><code>MessageDialog.WARNING</code> for a dialog with a
	 *            warning image</li>
	 *            </ul>
	 * @param dialogButtonLabels
	 *            an array of labels for the buttons in the button bar
	 * @param defaultIndex
	 *            the index in the button label array of the default button
	 * @param checkboxMessage
	 *            the message to show inside the checkbox. Can be null to hide
	 *            the checkbox
	 */
	public ExtendedMessageDialog(Shell parentShell, String dialogTitle, Image dialogTitleImage, String dialogMessage,
			int dialogImageType, String[] dialogButtonLabels, int defaultIndex, String checkboxMessage) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, dialogButtonLabels,
				defaultIndex);
		this.checkMessage = checkboxMessage;
	}

	/**
	 * Create the checkbox control if it is needed
	 */
	@Override
	protected Control createCustomArea(Composite parent) {
		// check if it need to create the checkbox
		if (checkMessage == null)
			return null;
		else {
			Button checkBox = new Button(parent, SWT.CHECK);
			checkBox.setText(checkMessage);
			// add the listener to update the flag
			checkBox.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					checkboxResult = ((Button) e.widget).getSelection();
				}
			});
			return checkBox;
		}
	}

	/**
	 * Check if in the message there is at least one URL external link
	 * 
	 * @param message
	 *            the message of the dialog
	 * @return true if there is an external link, false otherwise
	 */
	protected boolean hasExternalLink(String message) {
		Pattern p = Pattern.compile("href=\"(.*?)\"");
		Matcher m = p.matcher(message);
		return m.find();
	}

	/**
	 * Create the message area, if the text contains a link create a link
	 * control instead of a label one, otherwise executed the standard code to
	 * create a label message area
	 */
	@Override
	protected Control createMessageArea(Composite composite) {
		if (message != null && hasExternalLink(message)) {
			Image image = getImage();
			if (image != null) {
				// Create the image
				imageLabel = new Label(composite, SWT.NULL);
				image.setBackground(imageLabel.getBackground());
				imageLabel.setImage(image);
				GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.BEGINNING).applyTo(imageLabel);
			}
			// Crate the text
			Link link = new Link(composite, getMessageLabelStyle());
			link.setText(message);
			link.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(e.text));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false)
					.hint(convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH), SWT.DEFAULT)
					.applyTo(link);
			return composite;
		} else
			return super.createMessageArea(composite);
	}

	/**
	 * Return the last state of the checkbox
	 * 
	 * @return true if the checkbox is selected, false otherwise. If the
	 *         checkbox was not created this return always false
	 */
	public boolean getCheckboxResult() {
		return checkboxResult;
	}

	/**
	 * Return the exit value of the message dialog
	 * 
	 * @return true if the dialog was closed with yes, false if it was closed
	 *         with cancel.
	 */
	public boolean getResult() {
		return result;
	}

	/**
	 * Open the dialog and store the result
	 */
	@Override
	public int open() {
		setShellStyle(getShellStyle() | SWT.SHEET | SWT.PRIMARY_MODAL | SWT.DIALOG_TRIM);
		setBlockOnOpen(true);
		int exitCode = super.open();
		if (exitCode == SWT.DEFAULT) {
			result = false;
		} else {
			result = exitCode == 0;
		}
		return exitCode;
	}
}
