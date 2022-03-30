/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.preferences;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.preferences.editor.text.TextFieldEditor;
import com.jaspersoft.studio.preferences.util.FieldEditorOverlayPage;
import com.jaspersoft.studio.server.messages.Messages;
import com.jaspersoft.studio.utils.browser.BrowserInfo;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.ui.validator.IDStringValidator;
import net.sf.jasperreports.eclipse.util.HttpUtils;
import net.sf.jasperreports.eclipse.util.Misc;

/**
 * @author Veaceslav Chicu (schicu@users.sourceforge.net)
 * 
 */
public class JRSPreferencesPage extends FieldEditorOverlayPage {

	public static final String PUBLISH_REPORT_TOJRSONSAVE = "PUBLISH_REPORT_TOJRSONSAVE"; //$NON-NLS-1$
	public static final String PUBLISH_REPORT_OVERRIDEBYDEFAULT = "com.jaspersoft.studio.server.PUBLISH_REPORT_OVERRIDEBYDEFAULT"; //$NON-NLS-1$
	public static final String DEFAULT_MAIN_REPORT_NAME = "com.jaspersoft.studio.default.main.report.name"; //$NON-NLS-1$
	public static final String DEFAULT_MAIN_REPORT_LABEL = "com.jaspersoft.studio.default.main.report.label"; //$NON-NLS-1$

	public JRSPreferencesPage() {
		super(GRID);
		setPreferenceStore(JaspersoftStudioPlugin.getInstance().getPreferenceStore());
		getDefaults(getPreferenceStore());
	}

	/**
	 * Creates the field editors. Field editors are abstractions of the common GUI
	 * blocks needed to manipulate various types of preferences. Each field editor
	 * knows how to save and restore itself.
	 */
	public void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		parent.setLayout(new GridLayout(2, false));
		addField(new BooleanFieldEditor(PUBLISH_REPORT_TOJRSONSAVE,
				com.jaspersoft.studio.server.messages.Messages.JRSPreferencesPage_1, parent));

		Label lbl = new Label(parent, SWT.NONE);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		lbl.setLayoutData(gd);

		addField(new ComboFieldEditor(PUBLISH_REPORT_OVERRIDEBYDEFAULT, Messages.JRSPreferencesPage_2,
				new String[][] { { Messages.JRSPreferencesPage_3, "overwrite" }, // $NON-NLS-2$
						{ Messages.JRSPreferencesPage_5, "true" }, { Messages.JRSPreferencesPage_7, "ignore" } }, //$NON-NLS-2$ //$NON-NLS-4$
				parent) {
			@Override
			public int getNumberOfControls() {
				return 1;
			}
		});

		StringFieldEditor dmrn = new StringFieldEditor(DEFAULT_MAIN_REPORT_LABEL, "Default Main Report Label", parent);
		addField(dmrn);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		Text tlab = dmrn.getTextControl(parent);
		tlab.setLayoutData(gd);
		tlab.setToolTipText("This is the default Report Unit main reports label. Default (Main jrxml)");

		StringFieldEditor dmrl = new StringFieldEditor(DEFAULT_MAIN_REPORT_NAME, "Default Main Report Name", parent);
		addField(dmrl);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		Text tid = dmrl.getTextControl(parent);
		tid.setLayoutData(gd);
		tid.setToolTipText("This is the default Report Unit main reports name. Default (main_jrxml)");

		tlab.addModifyListener(new ModifyListener() {
			private boolean refresh = false;

			@Override
			public void modifyText(ModifyEvent e) {
				if (refresh)
					return;
				try {
					refresh = true;
					tid.setText(IDStringValidator.safeChar(Misc.nvl(tlab.getText())));
				} finally {
					refresh = false;
				}

			}
		});
		tid.addVerifyListener(e -> e.text = IDStringValidator.safeChar(e.text));

		lbl = new Label(parent, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan = 2;
		lbl.setLayoutData(gd);

		TextFieldEditor tf = new TextFieldEditor(HttpUtils.USER_AGENT, Messages.JRSPreferencesPage_9, parent);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 300;
		gd.horizontalSpan = 2;
		final Text txt = tf.getTextControl();
		txt.setLayoutData(gd);
		txt.setToolTipText(Messages.JRSPreferencesPage_10);
		txt.addModifyListener(e -> txt.setToolTipText(Messages.JRSPreferencesPage_10 + "\n\n" + txt.getText())); //$NON-NLS-1$

		lbl = tf.getLabelControl(parent);
		gd = new GridData();
		gd.horizontalSpan = 2;
		lbl.setLayoutData(gd);

		addField(tf);

		Button b = new Button(parent, SWT.PUSH);
		b.setText(Messages.JRSPreferencesPage_12);
		b.setToolTipText(Messages.JRSPreferencesPage_13);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		gd.horizontalSpan = 2;
		b.setLayoutData(gd);
		b.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				b.setEnabled(false);
				Job job = new Job(Messages.JRSPreferencesPage_14) {

					@Override
					protected IStatus run(IProgressMonitor monitor) {
						BrowserInfo.findUserAgent(value -> {
							tf.getTextControl().setText(Misc.nvl(value));
							b.setEnabled(true);
						});
						return Status.OK_STATUS;
					}

				};
				job.setPriority(Job.LONG);
				job.setUser(true);
				job.schedule();
			}
		});
		super.createFieldEditors();
	}

	public static void getDefaults(IPreferenceStore store) {
		store.setDefault(PUBLISH_REPORT_TOJRSONSAVE, true); // $NON-NLS-1$
		store.setDefault(PUBLISH_REPORT_OVERRIDEBYDEFAULT, "true"); // $NON-NLS-1$ //$NON-NLS-1$
		store.setDefault(DEFAULT_MAIN_REPORT_NAME, ""); // $NON-NLS-1$
		store.setDefault(DEFAULT_MAIN_REPORT_LABEL, ""); // $NON-NLS-1$
		store.setDefault(HttpUtils.USER_AGENT, ""); // $NON-NLS-1$ //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		// no initialisation here
	}

	@Override
	public String getPageId() {
		return "com.jaspersoft.studio.server.preferences.JRSPreferencesPage.property"; //$NON-NLS-1$
	}

	public static String getDefaultMainReportName(JasperReportsConfiguration jConfig) {
		String name = jConfig.getProperty(DEFAULT_MAIN_REPORT_NAME);
		if (Misc.isNullOrEmpty(name))
			name = Messages.JrxmlPublishAction_defaultresourcename;
		return name;
	}

	public static String getDefaultMainReportLabel(JasperReportsConfiguration jConfig) {
		String lbl = jConfig.getProperty(DEFAULT_MAIN_REPORT_LABEL);
		if (Misc.isNullOrEmpty(lbl))
			lbl = Messages.JrxmlPublishAction_defaultresourcelabel;
		return lbl;
	}
}
