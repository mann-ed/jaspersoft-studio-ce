/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.editor.input.lov;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ListItem;

import net.sf.jasperreports.eclipse.ui.ATitledDialog;
import net.sf.jasperreports.eclipse.util.Misc;

public class KeyValueDialog extends ATitledDialog {
	private String key;
	private String value;
	private List<ListItem> items;

	public KeyValueDialog(Shell parentShell, String key, String value, List<ListItem> items) {
		super(parentShell, false);
		this.key = key;
		this.value = value;
		this.items = items;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite cmp = (Composite) super.createDialogArea(parent);
		cmp.setLayout(new GridLayout(2, false));

		new Label(cmp, SWT.NONE).setText("Name");

		final Text txt = new Text(cmp, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 150;
		txt.setLayoutData(gd);
		txt.setText(Misc.nvl(key));
		txt.addModifyListener(e -> handleNameChanged(txt.getText()));

		new Label(cmp, SWT.NONE).setText("Value");

		final Text vtxt = new Text(cmp, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 150;
		vtxt.setLayoutData(gd);
		vtxt.setText(Misc.nvl(value));
		vtxt.addModifyListener(e -> handleValueChanged(vtxt.getText()));

		return cmp;
	}

	protected void handleValueChanged(String txt) {
		value = txt;
		getButton(OK).setEnabled(!Misc.isNullOrEmpty(value));
	}

	protected void handleNameChanged(String txt) {
		key = txt;
		if (Misc.isNullOrEmpty(txt)) {
			setError("Name can't be empty.");
			canFinish(this, false);
		} else if (exists(txt)) {
			setError("This value already exists.");
			canFinish(this, false);
		} else {
			setError(null);
			canFinish(this, true);
		}
	}

	private boolean exists(String value) {
		for (ListItem li : items)
			if (li.getLabel().equals(value))
				return true;
		return false;
	}
}
