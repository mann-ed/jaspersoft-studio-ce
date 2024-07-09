/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import net.sf.jasperreports.eclipse.ui.util.PersistentLocationDialog;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;

public class ATitledDialog extends PersistentLocationDialog {

	private String description;
	protected String errormsg;
	protected String title;
	private Label lDesc;
	private CLabel lError;
	private Image errImg;
	private Image warnImg;
	private boolean canClose = true;
	protected Composite tcmp;
	private Map<Control, Boolean> statemap = new HashMap<>();

	protected ATitledDialog(Shell parentShell) {
		super(parentShell);
	}

	protected ATitledDialog(Shell parentShell, boolean saveSettings) {
		super(parentShell);
		setSaveSettings(saveSettings);
	}

	protected ATitledDialog(Shell parentShell, int style, boolean saveSettings) {
		this(parentShell, saveSettings);
		setShellStyle(style);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets
	 * .Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (title != null)
			newShell.setText(title);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		if (this.description != null && this.description.equals(description))
			return;
		this.description = description == null ? "" : description;
		setBackground();
		if (lDesc != null) {
			lDesc.setText(this.description);
			((GridData) lDesc.getLayoutData()).exclude = !(description != null && !description.isEmpty());
			tcmp.getParent().update();
			tcmp.getParent().layout(true);
			// UIUtils.relayoutDialog(getShell(), defwidth, defheight);
		}
	}

	public void setError(String errormsg) {
		if ((this.errormsg == null && errormsg == null)
				|| (this.errormsg != null && this.errormsg.isEmpty() && errormsg == null)
				|| (this.errormsg == null && errormsg.isEmpty())
				|| (this.errormsg != null && errormsg != null && this.errormsg.equals(errormsg)))
			return;
		this.errormsg = errormsg == null ? "" : errormsg;
		setBackground();
		if (lError != null) {
			lError.setImage(errImg);
			lError.setText(this.errormsg);
			((GridData) lError.getLayoutData()).exclude = !(errormsg != null && !errormsg.isEmpty());
			tcmp.getParent().update();
			tcmp.getParent().layout(true);
			UIUtils.relayoutDialogHeight(getShell(), defheight);
		}
	}

	public void setWarning(String errormsg) {
		if ((this.errormsg == null && errormsg == null)
				|| (this.errormsg != null && this.errormsg.isEmpty() && errormsg == null)
				|| (this.errormsg == null && errormsg.isEmpty())
				|| (this.errormsg != null && errormsg != null && this.errormsg.equals(errormsg)))
			return;
		this.errormsg = errormsg == null ? "" : errormsg;
		setBackground();
		if (lError != null) {
			if (warnImg == null) {
				ImageDescriptor id = JFaceResources.getImageRegistry()
						.getDescriptor("org.eclipse.jface.fieldassist.IMG_DEC_FIELD_WARNING");
				if (id != null)
					warnImg = id.createImage();
			}
			lError.setImage(warnImg);
			lError.setText(this.errormsg);
			((GridData) lError.getLayoutData()).exclude = !(errormsg != null && !errormsg.isEmpty());
			tcmp.getParent().update();
			tcmp.getParent().layout(true);
			UIUtils.relayoutDialogHeight(getShell(), defheight);
		}
	}

	private void setBackground() {
		if (tcmp != null) {
			if ((description != null && !description.isEmpty()) || (errormsg != null && !errormsg.isEmpty()))
				tcmp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			else
				tcmp.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		tcmp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 5;
		tcmp.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		tcmp.setLayoutData(gd);
		tcmp.setBackgroundMode(SWT.INHERIT_DEFAULT);
		setBackground();

		lDesc = new Label(tcmp, SWT.WRAP);
		lDesc.setText(description != null ? description : "");
		gd = new GridData(GridData.FILL_BOTH);
		if (defwidth > 0)
			gd.widthHint = defwidth - 10;
		lDesc.setLayoutData(gd);
		((GridData) lDesc.getLayoutData()).exclude = !(description != null && !description.isEmpty());

		lError = new CLabel(tcmp, SWT.WRAP);
		lError.setText(errormsg != null ? errormsg : "");
		gd = new GridData(GridData.FILL_BOTH);
		lError.setLayoutData(gd);

		ImageDescriptor id = JFaceResources.getImageRegistry()
				.getDescriptor("org.eclipse.jface.fieldassist.IMG_DEC_FIELD_ERROR");
		if (id != null) {
			errImg = id.createImage();
			lError.setImage(errImg);
		}
		((GridData) lError.getLayoutData()).exclude = !(errormsg != null && !errormsg.isEmpty());

		return super.createDialogArea(parent);
	}

	@Override
	public boolean close() {
		if (errImg != null)
			errImg.dispose();
		if (warnImg != null)
			warnImg.dispose();
		return super.close();
	}

	public int openChildDialog(Dialog child) {
		UIUtils.setEnabled(dialogArea, statemap, false);

		canClose = false;
		int result = Dialog.CANCEL;
		try {
			result = child.open();
		} finally {
			UIUtils.setEnabled(dialogArea, statemap, true);
			canClose = true;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		if (canClose)
			super.okPressed();
	}

	@Override
	protected void cancelPressed() {
		if (canClose)
			super.cancelPressed();
	}

	@Override
	protected boolean canHandleShellCloseEvent() {
		return canClose;
	}

	public static void canFinish(Dialog d, boolean finish) {
		try {
			Method m = Dialog.class.getDeclaredMethod("getButton", int.class);
			m.setAccessible(true);
			Button b = (Button) m.invoke(d, IDialogConstants.OK_ID);
			if (b != null)
				b.setEnabled(finish);
		} catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
