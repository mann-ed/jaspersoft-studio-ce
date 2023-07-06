/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.descriptor.jrQuery.dialog;

import org.eclipse.jface.wizard.Wizard;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.MQuery;

public class JRQueryEditor extends Wizard {
	private MQuery mQuery;
	private JRQueryPage page0;

	public MQuery getValue() {
		if (page0 != null)
			return page0.getValue();
		return mQuery;
	}

	public void setValue(MQuery value) {
		if (page0 != null)
			page0.setValue(value);
		this.mQuery = value;
	}

	public JRQueryEditor() {
		super();
		setWindowTitle(Messages.common_query_editor);
	}

	@Override
	public void addPages() {
		page0 = new JRQueryPage("jrquery.editor"); //$NON-NLS-1$
		page0.setValue(mQuery);
		addPage(page0);
	}

	@Override
	public boolean performFinish() {
		return true;
	}

}
