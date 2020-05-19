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
	private ListItem listItem;
	private ListItem oldItem;
	private List<ListItem> items;
	private Text vtxt;
	private Text txt;

	public KeyValueDialog(Shell parentShell, ListItem listItem, List<ListItem> items) {
		super(parentShell, false);
		this.listItem = new ListItem(listItem.getLabel(), listItem.getValue());
		this.oldItem = listItem;
		this.items = items;
	}

	public ListItem getListItem() {
		return listItem;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite cmp = (Composite) super.createDialogArea(parent);
		cmp.setLayout(new GridLayout(2, false));

		new Label(cmp, SWT.NONE).setText("Name");

		txt = new Text(cmp, SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 150;
		txt.setLayoutData(gd);
		txt.setText(Misc.nvl(listItem.getLabel()));
		txt.addModifyListener(e -> handleNameChanged(txt.getText()));

		new Label(cmp, SWT.NONE).setText("Value");

		vtxt = new Text(cmp, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 150;
		vtxt.setLayoutData(gd);
		vtxt.setText(Misc.nvl((String) listItem.getValue()));
		vtxt.addModifyListener(e -> handleValueChanged(vtxt.getText()));

		return cmp;
	}

	protected void handleValueChanged(String txt) {
		listItem.setValue(txt);
		validateForm();
	}

	protected void handleNameChanged(String txt) {
		listItem.setLabel(txt);
		validateForm();
	}

	private void validateForm() {
		if (Misc.isNullOrEmpty(vtxt.getText())) {
			setError("Value can't be empty.");
			canFinish(this, false);
		} else if (Misc.isNullOrEmpty(txt.getText())) {
			setError("Name can't be empty.");
			canFinish(this, false);
		} else if (exists(txt.getText())) {
			setError("This name already exists.");
			canFinish(this, false);
		} else {
			setError(null);
			canFinish(this, true);
		}
	}

	private boolean exists(String value) {
		for (ListItem li : items)
			if (li != oldItem && li.getLabel().equals(value))
				return true;
		return false;
	}
}
