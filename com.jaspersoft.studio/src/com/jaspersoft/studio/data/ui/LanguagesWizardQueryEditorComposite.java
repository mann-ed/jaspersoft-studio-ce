/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.data.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.IQueryDesigner;
import com.jaspersoft.studio.data.designer.AQueryDesignerContainer;
import com.jaspersoft.studio.data.designer.AQueryStatus;
import com.jaspersoft.studio.property.dataset.dialog.QDesignerFactory;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignQuery;

public class LanguagesWizardQueryEditorComposite extends SimpleQueryWizardDataEditorComposite {

	public LanguagesWizardQueryEditorComposite(Composite parent, WizardPage page,
			DataAdapterDescriptor dataAdapterDescriptor, String lang, String[] langs) {
		super(parent, page, null, lang, langs);
		this.setDataAdapterDescriptor(dataAdapterDescriptor);
	}

	protected IQueryDesigner designer;
	protected QDesignerFactory qdfactory;
	private Composite cmp;

	@Override
	protected void createCompositeContent() {
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		setLayout(layout);

		new Label(this, SWT.NONE).setText("Language");

		Combo cmb = new Combo(this, SWT.READ_ONLY);
		cmb.setItems(langs);
		cmb.select(0);
		cmb.addModifyListener(e -> {
			qdfactory.dispose();
			String ln = langs[cmb.getSelectionIndex()];
			setQueryLanguage(ln);
			JRDesignDataset ds = getDataset();
			if (ds.getQuery() == null)
				ds.setQuery(new JRDesignQuery());
			((JRDesignQuery) ds.getQuery()).setLanguage(ln);
			buildEditor();
			setDataAdapterDescriptor(getDataAdapterDescriptor());
			LanguagesWizardQueryEditorComposite.this.update();
			LanguagesWizardQueryEditorComposite.this.layout(true);
		});

		buildEditor();
	}

	protected void buildEditor() {
		if (cmp != null)
			cmp.dispose();
		cmp = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cmp.setLayout(layout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		cmp.setLayoutData(gd);

		AQueryDesignerContainer qdc = new AQueryDesignerContainer() {
			protected void createStatusBar(Composite comp) {
				qStatus = new WizardQueryStatus(getPage());
			}

			@Override
			public AQueryStatus getQueryStatus() {
				if (qStatus == null)
					createStatusBar(null);
				return super.getQueryStatus();
			}

			@Override
			public void setParameters(List<JRParameter> params) {

			}

			@Override
			public void setFields(List<JRDesignField> fields) {

			}

			@Override
			public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable)
					throws InvocationTargetException, InterruptedException {
				getPage().getWizard().getContainer().run(fork, cancelable, runnable);
			}

			@Override
			public DataAdapterDescriptor getDataAdapter() {
				return getDataAdapterDescriptor();
			}

			@Override
			public List<JRDesignField> getCurrentFields() {
				return new ArrayList<JRDesignField>();
			}

			@Override
			public int getContainerType() {
				return CONTAINER_WITH_NO_TABLES;
			}

			@Override
			public void doGetFields(IProgressMonitor monitor) {
				monitor.beginTask(com.jaspersoft.studio.messages.Messages.DataQueryAdapters_jobname, -1);

				ClassLoader oldClassloader = Thread.currentThread().getContextClassLoader();
				Thread.currentThread().setContextClassLoader(jConfig.getClassLoader());

				try {
					setFields(readFields());
				} catch (Exception e) {
					if (e.getCause() != null)
						qStatus.showError(e.getCause().getMessage(), e);
					else
						qStatus.showError(e);
				} finally {
					Thread.currentThread().setContextClassLoader(oldClassloader);
					monitor.done();
				}
			}
		};
		qdfactory = new QDesignerFactory(cmp, null, qdc);
		designer = qdfactory.getDesigner(getQueryLanguage());

		designer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	@Override
	public void setDataAdapterDescriptor(DataAdapterDescriptor dataAdapterDescriptor) {
		super.setDataAdapterDescriptor(dataAdapterDescriptor);
		JasperReportsConfiguration jConfig = getJasperReportsConfiguration();
		designer.setQuery(jConfig.getJasperDesign(), getDataset(), jConfig);
		designer.setDataAdapter(dataAdapterDescriptor);
	}

	@Override
	public void dispose() {
		qdfactory.dispose();
		super.dispose();
	}

	public String getQueryString() {
		return designer.getQuery();
	}
}
