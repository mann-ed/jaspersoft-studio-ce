/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.wizards.group;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.wizards.ContextHelpIDs;
import com.jaspersoft.studio.wizards.JSSWizard;
import com.jaspersoft.studio.wizards.datasource.StaticWizardDataSourcePage;
import com.jaspersoft.studio.wizards.fields.StaticWizardFieldsPage;

public class StaticWizardFieldsGroupByPage extends StaticWizardFieldsPage {
	
	private Map<String, Object> settings = null;

	public StaticWizardFieldsGroupByPage() {
		super("groupfields"); //$NON-NLS-1$
		setTitle(Messages.WizardFieldsGroupByPage_group_by);
		setDescription(Messages.WizardFieldsGroupByPage_description);
	}

	/**
	 * Return the context name for the help of this page
	 */
	@Override
	protected String getContextName() {
		return ContextHelpIDs.WIZARD_SELECT_GROUP;
	}

	/**
	 * This procedure initialize the dialog page with the list of available objects.
	 * This implementation looks for object set in the map as DATASET_FIELDS
	 * if this is for real just the first time the page is shown.
	 * 
	 */
	public void loadSettings() {
		
		if (getSettings() == null) return;
		
		if (getSettings().containsKey( StaticWizardDataSourcePage.DATASET_FIELDS))
		{
			setAvailableFields( (List<?>)(getSettings().get( StaticWizardDataSourcePage.DATASET_FIELDS )) );
		}
		else
		{
			setAvailableFields(null);
		}
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		// JIRA TIBCO #JSS-3000: Fix helping on BigSur and 
		// https://community.jaspersoft.com/jaspersoft-studio/issues/13296
		// other Mac environments (i.e runtime development)
		Composite checkContainer = new Composite(mainComposite, SWT.NONE);
		checkContainer.setLayout(new GridLayout(2, false));
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false, 4, 1);
		checkContainer.setLayoutData(data);
		Button buttonCreate = new Button(checkContainer, SWT.CHECK);
		GridData labelData = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		Label buttonText = new Label(checkContainer, SWT.WRAP);
		buttonText.setLayoutData(labelData);
		buttonText.setText(Messages.WizardFieldsGroupByPage_createSortfields);
		buttonCreate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean createSortFields = ((Button)e.getSource()).getSelection();
				getLocalSettings().put(StaticWizardDataSourcePage.ORDER_GROUP, createSortFields);
			}
		});
		getLocalSettings().put(StaticWizardDataSourcePage.ORDER_GROUP, false);
	}
	
	private Map<String, Object> getLocalSettings(){
		if (settings == null && getWizard() != null && getWizard() instanceof JSSWizard){
				settings = ((JSSWizard)getWizard()).getSettings();
		}
		return settings;
	}
	
	
	/**
	 * Every time a new selection occurs, the selected fields are stored in the settings map
	 * with the key WizardDataSourcePage.GROUP_FIELDS
	 */
	@Override
	public void storeSettings()
	{
			Map<String, Object> settings = getLocalSettings();
			if (settings == null) return;
			settings.put(StaticWizardDataSourcePage.GROUP_FIELDS,  getSelectedFields() ); // the type is IPath
	}
}
