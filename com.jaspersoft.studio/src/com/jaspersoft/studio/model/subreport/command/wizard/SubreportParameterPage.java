/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.model.subreport.command.wizard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.property.descriptor.expression.ExprUtil;
import com.jaspersoft.studio.property.descriptor.parameter.dialog.ComboInputParameterDialog;
import com.jaspersoft.studio.property.descriptor.parameter.dialog.GenericJSSParameter;
import com.jaspersoft.studio.property.descriptor.parameter.dialog.InputParameterDialog;
import com.jaspersoft.studio.property.descriptor.parameter.dialog.ParameterPage;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.design.JRDesignSubreportParameter;
import net.sf.jasperreports.engine.design.JasperDesign;

public class SubreportParameterPage extends ParameterPage {

	private JRReport subreportJD;

	private JasperDesign mainReportJD;

	@Override
	protected void generateButtons(Composite bGroup) {
		super.generateButtons(bGroup);
		Button bMaster = new Button(bGroup, SWT.PUSH);
		bMaster.setText(Messages.SubreportParameterPage_copyFromMaster);
		bMaster.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (mainReportJD == null)
					return;
				HashSet<String> usedParams = new HashSet<>();
				for (GenericJSSParameter sp : values) {
					usedParams.add(sp.getName());
				}
				for (JRParameter prm : mainReportJD.getMainDataset().getParameters()) {
					if (prm.isSystemDefined())
						continue;
					String name = prm.getName();
					if (!usedParams.contains(name)) {
						JRDesignSubreportParameter param = new JRDesignSubreportParameter();
						param.setName(name);
						param.setExpression(ExprUtil.createExpression("$P{" + name + "}")); //$NON-NLS-1$ //$NON-NLS-2$
						values.add(new GenericJSSParameter(param));
					}
				}
				tableViewer.refresh(true);
			}
		});
	}

	@Override
	protected void fillTable() {
		if (values == null) {
			values = new ArrayList<GenericJSSParameter>();
		}
		if (subreportJD != null) {
			HashSet<String> usedParams = new HashSet<>();
			for (GenericJSSParameter sp : values) {
				usedParams.add(sp.getName());
			}
			boolean hasParameter = false;
			for (JRParameter prm : subreportJD.getMainDataset().getParameters()) {
				if (prm.isSystemDefined())
					continue;
				hasParameter = true;
				String name = prm.getName();
				if (!usedParams.contains(name)) {
					JRDesignSubreportParameter param = new JRDesignSubreportParameter();
					param.setName(name);
					param.setExpression(ExprUtil.createExpression("$P{" + name + "}")); //$NON-NLS-1$ //$NON-NLS-2$
					values.add(new GenericJSSParameter(param));
				}
			}
			if (!hasParameter) {
				setMessage(Messages.SubreportParameterPage_noParametersWarning, IMessageProvider.WARNING);
			} else {
				setMessage(null);
			}
		}
		super.fillTable();
	}

	/**
	 * Return the input of the combo, a list of the parameter names of the
	 * original subreport file
	 * 
	 * @return the list of string displayed in the combo
	 */
	protected List<String> createNameComboInput() {
		List<String> result = new ArrayList<>();
		HashSet<String> usedParams = new HashSet<>();
		for (GenericJSSParameter param : values) {
			usedParams.add(param.getName());
		}
		if (subreportJD != null) {
			for (JRParameter param : subreportJD.getParameters()) {
				if (!usedParams.contains(param.getName())) {
					if (param.getName() != null)
						result.add(param.getName());
				}
			}
		}
		if (mainReportJD != null)
			for (JRParameter param : mainReportJD.getParameters())
				if (!usedParams.contains(param.getName()) && param.getName() != null
						&& !result.contains(param.getName()))
					result.add(param.getName());
		Collections.sort(result);
		return result;
	}

	public void setJasperDesign(JRReport subreportJD, JasperDesign mainReportJD) {
		this.subreportJD = subreportJD;
		this.mainReportJD = mainReportJD;
		fillTable();
	}

	@Override
	protected InputParameterDialog getEditDialog(GenericJSSParameter editedParameter,
			List<GenericJSSParameter> prevParams) {
		return new ComboInputParameterDialog(getShell(), createNameComboInput(), editedParameter, SWT.DROP_DOWN,
				prevParams);
	}
}
