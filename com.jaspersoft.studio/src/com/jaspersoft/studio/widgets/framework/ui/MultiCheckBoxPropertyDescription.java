/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.widgets.framework.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.widgets.framework.IWItemProperty;
import com.jaspersoft.studio.widgets.framework.manager.DoubleControlComposite;
import com.jaspersoft.studio.widgets.framework.model.WidgetPropertyDescriptor;
import com.jaspersoft.studio.widgets.framework.model.WidgetsDescriptor;

import net.sf.jasperreports.eclipse.util.Misc;

/**
 * Property description representing a multiple checkbox widget.
 * The simple text value of the property will be represented as a command separated list.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public class MultiCheckBoxPropertyDescription<T> extends AbstractExpressionPropertyDescription<T> {
	
	private static final int DEFAULT_CHECKBOXES_COL_NUM = 3;
	private int checkboxesColNum = DEFAULT_CHECKBOXES_COL_NUM;
	private Map<String,String> valuesMap;
	private Map<String,Button> checkboxesMap;


	public MultiCheckBoxPropertyDescription() {
		super();
		this.valuesMap = new HashMap<>();
		this.checkboxesMap = new HashMap<>();
	}

	public MultiCheckBoxPropertyDescription(String name, String label, String description, boolean mandatory,
			T defaultValue, Map<String,String> valuesMap) {
		super(name, label, description, mandatory, defaultValue);
		this.valuesMap = valuesMap;
		this.checkboxesMap = new HashMap<>();
	}	
	
	@Override
	public void handleEdit(Control control, IWItemProperty wiProp) {
		super.handleEdit(control, wiProp);
		// add check for the composite containing the checkboxes (simple mode)
		if(control instanceof Composite) {
			String valueAsText = convertToTextValue();
			wiProp.setValue(valueAsText, null);
		}
	}
	
	@Override
	public Control createControl(IWItemProperty wiProp, Composite parent) {
		DoubleControlComposite cmp = new DoubleControlComposite(parent, SWT.NONE);
		cmp.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));

		lazyCreateExpressionControl(wiProp, cmp);
		
		Composite checkboxesContainer = new Composite(cmp.getSecondContainer(), SWT.NONE);
		cmp.getSecondContainer().setData(checkboxesContainer);
		checkboxesContainer.setLayout(new GridLayout(this.checkboxesColNum,false));
		checkboxesContainer.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));	

		for(Entry<String, String> entry : valuesMap.entrySet()) {
			Button btn = new Button(checkboxesContainer, SWT.CHECK);
			btn.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
			btn.setData(entry.getKey());
			btn.setText(entry.getValue());
			btn.setToolTipText(entry.getValue());
			btn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if(wiProp.isRefresh()) {
						return;
					}
					handleEdit(checkboxesContainer, wiProp);
				}
			});
			checkboxesMap.put(entry.getKey(), btn);
		}
		
		if (isReadOnly()) {
			checkboxesContainer.setEnabled(false);
		} else {
			setupContextMenu(checkboxesContainer, wiProp);
		}
		cmp.switchToSecondContainer();
		return cmp;
	}
	
	@Override
	public void update(Control c, IWItemProperty wip) {
		DoubleControlComposite cmp = (DoubleControlComposite) wip.getControl();
		boolean isFallback = false;
		if (wip.isExpressionMode()) {
			lazyCreateExpressionControl(wip, cmp);
			Text txt = (Text) cmp.getFirstContainer().getData();
			super.update(txt, wip);
			cmp.switchToFirstContainer();
			txt.setToolTipText(getToolTip(wip, txt.getText()));
		} else {
			Composite checkboxesContainer = (Composite) cmp.getSecondContainer().getData();
			String v = wip.getStaticValue();
			if (v == null && wip.getFallbackValue() != null) {
				v = wip.getFallbackValue().toString();
				isFallback = true;
			}
			updateCheckboxesWithValues(Misc.nvl(v));
			// TODO - remember possibly change the foreground of the fallback value
			//changeFallbackForeground(isFallback, combo);
			cmp.switchToSecondContainer();
		}
	}
	
	/*
	 * Converts the status of the checkboxes selection into a proper text value representation.
	 * Returns null if no element is selected.
	 */
	private String convertToTextValue() {
		StringBuilder result = new StringBuilder();
		for(Entry<String,Button> entry : checkboxesMap.entrySet()) {
			if(entry.getValue().getSelection()) {
				result.append(entry.getKey()).append(",");				
			}
		}
		result.setLength(Math.max(result.length()-1,0));
		String textValue = result.toString();
		return textValue.isEmpty() ? null : textValue;
	}
	
	/*
	 * Updates the checkboxes depending on the text property value.
	 */
	private void updateCheckboxesWithValues(String textValue) {
		List<String> selectedValues = Arrays.asList(textValue.split(","));
		for(Entry<String,Button> entry : checkboxesMap.entrySet()) {
			Button btn = entry.getValue();
			Object data = btn.getData();
			boolean found = (data instanceof String) && selectedValues.contains(((String) data).trim());
			btn.setSelection(found);
		}
	}
	
	
	@Override
	public ItemPropertyDescription<?> getInstance(WidgetsDescriptor cd, WidgetPropertyDescriptor cpd,
			JasperReportsConfiguration jConfig) {
		if(cpd.getComboOptions() != null) {
			String[][] opts = cpd.getComboOptions();
			Map<String,String> i18nOptions = new HashMap<>();
			for(int i=0;i<opts.length;i++) {
				i18nOptions.put(opts[i][0], cd.getLocalizedString(opts[i][1]));
			}
			MultiCheckBoxPropertyDescription<String> result = new MultiCheckBoxPropertyDescription<>(cpd.getName(),
					cd.getLocalizedString(cpd.getLabel()), cd.getLocalizedString(cpd.getDescription()),
					cpd.isMandatory(), cpd.getDefaultValue(), i18nOptions);
			result.setReadOnly(cpd.isReadOnly());
			result.setFallbackValue(cpd.getFallbackValue());
			return result;
		}
		else {
			return null;
		}
	}
	
	@Override
	public ItemPropertyDescription<T> clone() {
		MultiCheckBoxPropertyDescription<T> result = new MultiCheckBoxPropertyDescription<>();
		result.defaultValue = defaultValue;
		result.description = description;
		result.jConfig = jConfig;
		result.label = label;
		result.mandatory = mandatory;
		result.name = name;
		result.valuesMap = valuesMap;
		result.fallbackValue = fallbackValue;
		return result;
	}

}
