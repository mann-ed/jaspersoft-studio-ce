/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.property.dataset.fields.table.widget;

import org.apache.commons.lang.LocaleUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionDTO;
import com.jaspersoft.studio.swt.widgets.WLocale;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.Misc;

public class WLocaleProperty extends AWControl {
	private WLocale cmb;

	public WLocaleProperty(AWidget aw) {
		super(aw);
	}

	protected void createControl(final Composite parent) {
		cmp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cmp.setLayout(layout);
		cmp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		cmb = new WLocale(cmp, !aw.getTColumn().isLabelEditable() ? SWT.BORDER | SWT.READ_ONLY : SWT.BORDER);
		cmb.addModifyListener(e -> {
			if (refresh)
				return;
			UIUtils.getDisplay().asyncExec(() -> cmb.setToolTipText(aw.getToolTipText()));
			aw.setValue(cmb.getLocale().toString());
		});
	}

	protected boolean refresh = false;
	private Composite cmp;

	protected void fillValue() {
		Object obj = aw.getValue();
		String v = "";
		if (obj instanceof PropertyExpressionDTO)
			obj = ((PropertyExpressionDTO) obj).getValue();
		if (obj instanceof String)
			v = (String) obj;
		try {
			refresh = true;
			if (!Misc.isNullOrEmpty(v))
				cmb.setSelection(LocaleUtils.toLocale(v)); // $NON-NLS-1$
		} finally {
			refresh = false;
		}
		cmb.setToolTipText(aw.getToolTipText());
	}

	@Override
	public void addDisposeListener(DisposeListener dlistener) {
		cmb.addDisposeListener(dlistener);
	}

	@Override
	public void setEnabled(boolean en) {
		cmb.setEnabled(en);
	}

	@Override
	public void dispose() {
		super.dispose();
		if (cmp != null && !cmp.isDisposed())
			cmp.dispose();
	}
}