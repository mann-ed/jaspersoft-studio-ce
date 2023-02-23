/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.xlsx;

import java.util.List;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.DataAdapterService;
import net.sf.jasperreports.data.xlsx.XlsxDataAdapterImpl;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignField;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.data.AWizardDataEditorComposite;
import com.jaspersoft.studio.data.Activator;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.DataAdapterEditor;
import com.jaspersoft.studio.data.IWizardDataEditorProvider;
import com.jaspersoft.studio.data.fields.IFieldsProvider;
import com.jaspersoft.studio.data.ui.EmptyWizardDataEditorComposite;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class XLSXDataAdapterDescriptor extends DataAdapterDescriptor
		implements IFieldsProvider, IWizardDataEditorProvider {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	@Override
	public DataAdapter getDataAdapter() {
		if (dataAdapter == null)
			dataAdapter = new XlsxDataAdapterImpl();
		return dataAdapter;
	}

	@Override
	public DataAdapterEditor getEditor() {
		return new XLSXDataAdapterEditor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.data.DataAdapterFactory#getIcon(int)
	 */
	@Override
	public Image getIcon(int size) {
		if (size == 16) {
			return Activator.getDefault().getImage("icons/document-excel.png");
		}
		return null;
	}

	private IFieldsProvider fprovider;

	public List<JRDesignField> getFields(DataAdapterService con, JasperReportsConfiguration jConfig,
			JRDataset reportDataset) throws JRException, UnsupportedOperationException {
		getFieldProvider();
		return fprovider.getFields(con, jConfig, reportDataset);
	}

	public boolean supportsGetFieldsOperation(JasperReportsConfiguration jConfig, JRDataset jDataset) {
		getFieldProvider();
		return fprovider.supportsGetFieldsOperation(jConfig, jDataset);
	}

	private void getFieldProvider() {
		if (fprovider == null)
			fprovider = new XLSXFieldsProvider();
	}

	@Override
	public AWizardDataEditorComposite createDataEditorComposite(Composite parent, WizardPage page) {
		return new EmptyWizardDataEditorComposite(parent, page, this);
	}
}
