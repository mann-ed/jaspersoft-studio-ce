/*******************************************************************************
 * Copyright (C) 2010 - 2023. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.action.reportsplitting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.jaspersoft.studio.editor.expression.ExpressionContext;
import com.jaspersoft.studio.editor.expression.IExpressionContextSetter;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionDTO;
import com.jaspersoft.studio.swt.events.IRefreshableList;

import net.sf.jasperreports.eclipse.util.Misc;

/**
 * This widget allows to manage the properties for the report splitting feature.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public class WReportSplittingPropertiesList extends Composite implements IRefreshableList, IExpressionContextSetter {

	// Widgets
	protected TableViewer tableViewerSplittingProperties;
	protected Button btnAddSplittingProperty;
	protected Button btnModifySplittingProperty;
	protected Button btnRemoveSplittingProperty;
	
	// Model stuff
	protected ReportSplittingPropertyEditor editor;
	protected ExpressionContext expContext;

	
	public WReportSplittingPropertiesList(Composite parent, int style, ReportSplittingPropertyEditor editor) {
		super(parent, style);
		this.editor = editor;
		GridLayout widgetGl = new GridLayout(2, false);
		this.setLayout(widgetGl);
		createWidgetContent();
	}
	
	/*
	 * Creates the widget content.
	 */
	private void createWidgetContent() {
		Composite cmpSplittingPropertiesTableViewer=new Composite(this, SWT.NONE);
		cmpSplittingPropertiesTableViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,3));
		TableColumnLayout splittingPropertiesTableViewerTCL = new TableColumnLayout();
		cmpSplittingPropertiesTableViewer.setLayout(splittingPropertiesTableViewerTCL);
		
		tableViewerSplittingProperties = new TableViewer(cmpSplittingPropertiesTableViewer, SWT.BORDER | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION);
		Table table = tableViewerSplittingProperties.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableViewerColumn tblclmnPropertyName = new TableViewerColumn(tableViewerSplittingProperties, SWT.NONE);
		tblclmnPropertyName.getColumn().setText(getNameColumnLabel());
		tblclmnPropertyName.setLabelProvider(getNameColumnLabelProvider());
		splittingPropertiesTableViewerTCL.setColumnData(tblclmnPropertyName.getColumn(), new ColumnWeightData(1, ColumnWeightData.MINIMUM_WIDTH, true));
			
		TableViewerColumn tblclmnExpression = new TableViewerColumn(tableViewerSplittingProperties, SWT.NONE);
		tblclmnExpression.getColumn().setText(getValueColumnLabel());
		tblclmnExpression.setLabelProvider(getValueColumnLabelProvider());
		splittingPropertiesTableViewerTCL.setColumnData(tblclmnExpression.getColumn(), new ColumnWeightData(2, ColumnWeightData.MINIMUM_WIDTH, true));
		
		tableViewerSplittingProperties.setContentProvider(new ArrayContentProvider());
		tableViewerSplittingProperties.setInput(getFilteredProperties());
		tableViewerSplittingProperties.addDoubleClickListener(event -> {
			modifySelectedProperty();
			btnModifySplittingProperty.setEnabled(false);
			btnRemoveSplittingProperty.setEnabled(false);
		});
		tableViewerSplittingProperties.addSelectionChangedListener(event -> {
			btnModifySplittingProperty.setEnabled(true);
			btnRemoveSplittingProperty.setEnabled(true);
		});
		
		btnAddSplittingProperty = new Button(this, SWT.NONE);
		btnAddSplittingProperty.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnAddSplittingProperty.setText(Messages.WReportSplittingPropertiesList_AddBtn); 
		btnAddSplittingProperty.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addNewProperty();
			}
		});
		
		btnModifySplittingProperty = new Button(this, SWT.NONE);
		btnModifySplittingProperty.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnModifySplittingProperty.setText(Messages.WReportSplittingPropertiesList_ModifyBtn); 
		btnModifySplittingProperty.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				modifySelectedProperty();
			}
		});
		
		btnRemoveSplittingProperty = new Button(this, SWT.NONE);
		btnRemoveSplittingProperty.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnRemoveSplittingProperty.setText(Messages.WReportSplittingPropertiesList_RemoveBtn); 
		btnRemoveSplittingProperty.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removeSelectedProperty();
			}
		});
		
		enableDefaultButtons();
	}
	
	/**
	 * @return the label provider for the column 'name'
	 */
	protected ColumnLabelProvider getNameColumnLabelProvider() {
		return new SplittingPropertyNameLabelProvider();
	}
	
	/**
	 * @return the label provider for the column 'value'
	 */
	protected ColumnLabelProvider getValueColumnLabelProvider() {
		return new SplittingPropertyValueLabelProvider();
	}
	
	/**
	 * @return the label for the name column
	 */
	protected String getNameColumnLabel() {
		return Messages.WReportSplittingPropertiesList_NameCol;
	}
	
	/**
	 * @return the label for the value column
	 */
	protected String getValueColumnLabel() {
		return Messages.WReportSplittingPropertiesList_ValueCol;
	}

	/**
	 * @return the list of filtered properties to populate the table control. 
	 */
	protected List<PropertyExpressionDTO> getFilteredProperties() {
		List<PropertyExpressionDTO> properties = new ArrayList<>();
		for (PropertyExpressionDTO p : editor.getAllProperties()) {
			String name = p.getName();
			if (!name.equals(ReportSplittingEditDialog.PART_NAME_PROPERTY) &&
					!name.equals(ReportSplittingEditDialog.PART_SPLIT_PROPERTY) &&
					!name.equals(ReportSplittingEditDialog.PART_VISIBLE_PROPERTY) &&
					name.startsWith(ReportSplittingEditDialog.PART_PREFIX_PROPERTY)) {
				properties.add(p);
			}
		}
		properties.sort(new Comparator<PropertyExpressionDTO>() {
			@Override
			public int compare(PropertyExpressionDTO p1, PropertyExpressionDTO p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});
		return properties;
	}
	
	/*
	 * Removes the currently selected splitting property.
	 */
	private void removeSelectedProperty() {
		Object selected=((IStructuredSelection)tableViewerSplittingProperties.getSelection()).getFirstElement();
		if (selected instanceof PropertyExpressionDTO){
			editor.removeProperty(((PropertyExpressionDTO)selected).getName());
			refreshList();
		}
		enableDefaultButtons();
	}
	
	/*
	 * Modifies the currently selected splitting property.
	 */
	private void modifySelectedProperty() {
		Object selected=((IStructuredSelection)tableViewerSplittingProperties.getSelection()).getFirstElement();
		if (selected instanceof PropertyExpressionDTO){
			PropertyExpressionDTO propertyCopy=((PropertyExpressionDTO)selected).clone();
			ReportSplittingPropertyDialog editDialog=getPropertyDialog(propertyCopy, false, getShell());
			editDialog.setExpressionContext(expContext);
			if (editDialog.open()==Window.OK){
				PropertyExpressionDTO editedProperty = editDialog.getModifiedSplittingProperty();
				// double check we are not trying to override existing property
				String originalPname = ((PropertyExpressionDTO)selected).getName();
				if(!editedProperty.getName().equals(originalPname) &&
						editor.getProperty(editedProperty.getName())!=null){
					showEditErrorMessage();
				}
				else {
					editor.removeProperty(((PropertyExpressionDTO)selected).getName());
					editor.addProperty(editedProperty);
					refreshList();
				}
			}
		}
		enableDefaultButtons();
	}
	
	/**
	 * @return the dialog to edit the property
	 */
	protected ReportSplittingPropertyDialog getPropertyDialog(
			PropertyExpressionDTO property, boolean nameReadOnly, Shell shell) {
		return new ReportSplittingPropertyDialog(property, nameReadOnly, shell);
	}
	
	/**
	 * Shows an error dialog when something goes wrong on property edit.
	 */
	protected void showEditErrorMessage() {
		MessageDialog.openError(
				getShell(), Messages.WReportSplittingPropertiesList_EditErrorTitle, 
				Messages.WReportSplittingPropertiesList_EditErrorMsg);
	}

	/*
	 * Adds a new splitting property using the dedicated dialog.
	 */
	private void addNewProperty() {
		ReportSplittingPropertyDialog editDialog=getPropertyDialog(null, false, getShell());
		editDialog.setExpressionContext(expContext);
		if (editDialog.open()==Window.OK){
			PropertyExpressionDTO editedProperty = editDialog.getModifiedSplittingProperty();
			PropertyExpressionDTO existingProp = editor.getProperty(editedProperty.getName());
			if(existingProp != null){
				showAddErrorMessage();
			}
			else {
				editor.addProperty(editedProperty);
				refreshList();		
			}
		}
		enableDefaultButtons();
	}
	
	/**
	 * Shows an error dialog when something goes wrong during new property creation.
	 */
	protected void showAddErrorMessage() {
		MessageDialog.openError(
				getShell(), Messages.WReportSplittingPropertiesList_AddErrorTitle, 
				Messages.WReportSplittingPropertiesList_AddErrorMsg);
	}

	/*
	 * As default only Add button is enabled.
	 * Modify and Remove ones should be enabled only
	 * once at least one element is selected.
	 */
	private void enableDefaultButtons(){
		btnAddSplittingProperty.setEnabled(true);
		btnModifySplittingProperty.setEnabled(false);
		btnRemoveSplittingProperty.setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * @see com.jaspersoft.studio.swt.events.IRefreshableList#refreshList()
	 */
	public void refreshList(){
		tableViewerSplittingProperties.setInput(getFilteredProperties());
	}

	/*
	 * (non-Javadoc)
	 * @see com.jaspersoft.studio.editor.expression.IExpressionContextSetter#setExpressionContext(com.jaspersoft.studio.editor.expression.ExpressionContext)
	 */
	public void setExpressionContext(ExpressionContext expContext) {
		this.expContext=expContext;
	}
	
	/*
	 * Label Provider for the column name.
	 */
	private class SplittingPropertyNameLabelProvider extends ColumnLabelProvider {
		@Override
		public String getText(Object element) {
			if(element instanceof PropertyExpressionDTO){
				return ((PropertyExpressionDTO)element).getName();	
			}
			return super.getText(element);
		}
	}
	
	/*
	 * Label Provider for the column value.
	 */
	private class SplittingPropertyValueLabelProvider extends ColumnLabelProvider {
		@Override
		public String getText(Object element) {
			if(element instanceof PropertyExpressionDTO) {
				PropertyExpressionDTO p = (PropertyExpressionDTO)element;
				return Misc.nvl(p.getValue());
			}
			return super.getText(element);
		}
	}

}
