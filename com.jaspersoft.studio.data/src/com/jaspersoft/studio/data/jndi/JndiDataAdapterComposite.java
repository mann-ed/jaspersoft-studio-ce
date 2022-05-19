/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.data.jndi;

import org.eclipse.core.databinding.beans.typed.PojoProperties;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.data.ADataAdapterComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.messages.Messages;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.jndi.JndiDataAdapter;
import net.sf.jasperreports.engine.JasperReportsContext;

public class JndiDataAdapterComposite extends ADataAdapterComposite {

	private Text dataSourceName;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public JndiDataAdapterComposite(Composite parent, int style, JasperReportsContext jrContext) {
		super(parent, style, jrContext);
		setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText(Messages.JndiDataAdapterComposite_0);

		dataSourceName = new Text(this, SWT.BORDER);
		dataSourceName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	@Override
	protected void bindWidgets(DataAdapter dataAdapter) {
		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(dataSourceName),
				PojoProperties.value("dataSourceName").observe(dataAdapter)); //$NON-NLS-1$
	}

	public DataAdapterDescriptor getDataAdapter() {
		if (dataAdapterDesc == null)
			dataAdapterDesc = new JndiDataAdapterDescriptor();

		JndiDataAdapter dataAdapter = (JndiDataAdapter) dataAdapterDesc
				.getDataAdapter();

		dataAdapter.setDataSourceName(dataSourceName.getText());

		return dataAdapterDesc;
	}
	
	@Override
	public String getHelpContextId() {
		return PREFIX.concat("adapter_jndi");
	}

}
