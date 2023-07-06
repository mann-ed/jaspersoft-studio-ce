/*******************************************************************************
 * Copyright (C) 2010 - 2023. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.action.reportsplitting;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.editor.expression.ExpressionContext;
import com.jaspersoft.studio.editor.expression.IExpressionContextSetter;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionDTO;
import com.jaspersoft.studio.swt.widgets.WTextExpression;
import com.jaspersoft.studio.utils.GridDataUtil;

import net.sf.jasperreports.eclipse.ui.util.PersistentLocationDialog;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.eclipse.util.StringUtils;
import net.sf.jasperreports.engine.design.JRDesignExpression;

/** 
 * Standard dialog to create or edit a {@link PropertyExpressionDTO} that represents a report splitting property.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public class ReportSplittingPropertyDialog extends PersistentLocationDialog implements IExpressionContextSetter {
	
	private static final Point DEFAULT_DIALOG_SIZE = new Point(450,320);
	
	protected PropertyExpressionDTO splittingProperty;
	protected ExpressionContext expContext;
	protected boolean nameReadOnly;
	
	// Widgets stuff
	protected Text propertyName;
	protected Button useExpressionCheckbox;
	protected Text propertyValue;
	protected WTextExpression propertyValueExpression;
	protected Composite container;

	
	public ReportSplittingPropertyDialog(PropertyExpressionDTO property, boolean nameReadOnly, Shell shell) {
		super(shell);
		this.splittingProperty=property;
		this.nameReadOnly=nameReadOnly;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		container = new Composite(parent,SWT.NONE);

		GridLayout containerLayout = new GridLayout(1, false);
		containerLayout.marginHeight=10;
		containerLayout.marginWidth=10;
		containerLayout.verticalSpacing=10;
		container.setLayout(containerLayout);
		container.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
		
		Label infoMsgLbl = new Label(container, SWT.WRAP);
		infoMsgLbl.setText(NLS.bind(
				Messages.ReportSplittingPropertyDialog_InfoMsgText, ReportSplittingEditDialog.PART_PREFIX_PROPERTY));
		infoMsgLbl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		Label lblPropertyName = new Label(container, SWT.NONE);
		lblPropertyName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lblPropertyName.setText(Messages.ReportSplittingPropertyDialog_PropertyNameLbl);
		propertyName = new Text(container, SWT.BORDER);
		GridData propertyNameGD = new GridData(SWT.FILL,SWT.FILL,true,false);
		propertyName.setLayoutData(propertyNameGD);
		propertyName.setEditable(!this.nameReadOnly);
		
		useExpressionCheckbox = new Button(container, SWT.CHECK);
		useExpressionCheckbox.setText(Messages.ReportSplittingPropertyDialog_UseExprCheckbox);
		useExpressionCheckbox.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
			
		Label lblPropertyValue=new Label(container,SWT.NONE);
		lblPropertyValue.setText(Messages.ReportSplittingPropertyDialog_PropertyValueLbl);
		lblPropertyValue.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
		
		propertyValue = new Text(container,SWT.BORDER | SWT.WRAP);
		GridData propertyValueGD = new GridData(SWT.FILL,SWT.FILL,true,true);
		propertyValueGD.heightHint = 50;
		propertyValue.setLayoutData(propertyValueGD);
		
		propertyValueExpression = new WTextExpression(container, SWT.NONE);
		GridData propertyValueExpressionGD = new GridData(SWT.FILL,SWT.FILL,true,true);
		propertyValueExpressionGD.heightHint = 50;
		propertyValueExpression.setLayoutData(propertyValueExpressionGD);
		propertyValueExpression.setExpressionContext(this.expContext);
		
		container.pack();
		initWidgets();
		addListeners();
		
		return container;
	}
	
	/*
	 * Initialize the content of different widgets.
	 */
	private void initWidgets() {
		if(this.splittingProperty == null){
			this.splittingProperty = new PropertyExpressionDTO(false, StringUtils.EMPTY_STRING, StringUtils.EMPTY_STRING, false);
		}
		if(this.splittingProperty.isExpression()){
			useExpressionCheckbox.setSelection(true);
			propertyName.setText(Misc.nvl(getShortPropertyName()));
			propertyValueExpression.setExpression((JRDesignExpression) splittingProperty.getValueAsExpression());
			propertyValue.setVisible(false);
			propertyValue.setEnabled(false);
			((GridData)propertyValue.getLayoutData()).exclude=true;
		}
		else {
			useExpressionCheckbox.setSelection(false);
			propertyName.setText(Misc.nvl(getShortPropertyName()));
			propertyValue.setText(Misc.nvl(splittingProperty.getValue()));
			propertyValueExpression.setVisible(false);
			propertyValueExpression.setEnabled(false);
			propertyValueExpression.setExpression(null);
			((GridData)propertyValueExpression.getLayoutData()).exclude=true;
		}
	}
	
	/**
	 * The property name that will be stored in the property object.
	 * 
	 * <p>
	 * Clients might want to override in order to provide a custom computed one. 
	 * </p>
	 * 
	 * @param visibleName the current name (i.e in a widget)
	 * @return the real property name
	 */
	protected String computeFullPropertyName(String visibleName) {
		return ReportSplittingEditDialog.PART_PREFIX_PROPERTY + visibleName;
	}
	
	/**
	 * A human readable version of the property. By default it is the actual "real/full" property name.
	 * 
	 * <p>
	 * Clients might want to override in order to provide a custom computed one (i.e properties with shorter name).
	 * </p>
	 * 
	 * @return human readable name
	 */
	protected String getShortPropertyName() {
		return splittingProperty.getName().replace(ReportSplittingEditDialog.PART_PREFIX_PROPERTY, ""); //$NON-NLS-1$
	}
	
	/*
	 * Adds the needed listeners to the elements.
	 */
	private void addListeners() {
		propertyName.addModifyListener(e -> splittingProperty.setName(computeFullPropertyName(propertyName.getText())));
		propertyValue.addModifyListener(e -> {
			splittingProperty.setExpression(false);
			splittingProperty.setValue(propertyValue.getText());	
		});
		propertyValueExpression.addModifyListener(event -> {
			splittingProperty.setExpression(true);
			String textValue = null;
			if(event.modifiedExpression!=null) {
				textValue = event.modifiedExpression.getText();
			}
			splittingProperty.setValue(textValue);
		});
		useExpressionCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(useExpressionCheckbox.getSelection()){
					GridDataUtil.setGridDataExcludeAndVisibility(propertyValue, true);
					GridDataUtil.setGridDataExcludeAndVisibility(propertyValueExpression, false);
				}
				else{
					GridDataUtil.setGridDataExcludeAndVisibility(propertyValueExpression, true);
					GridDataUtil.setGridDataExcludeAndVisibility(propertyValue, false);
				}
				container.layout(new Control[]{propertyValue,propertyValueExpression});
			}
		});
	}

	@Override
	protected Point getInitialSize() {
		return UIUtils.getScaledSize(DEFAULT_DIALOG_SIZE);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}
	
	/** 
	 * Gets the modified standard splitting property.
	 * 
	 * @return the edited property
	 */
	public PropertyExpressionDTO getModifiedSplittingProperty(){
		return splittingProperty;
	}

	@Override
	protected void okPressed() {
		if(propertyName.getText().isEmpty()){
			MessageDialog.openError(getShell(),Messages.ReportSplittingPropertyDialog_ErrorTitle, Messages.ReportSplittingPropertyDialog_ErrorEmptyMsg);
			return;
		}
		splittingProperty.setName(computeFullPropertyName(propertyName.getText()));
		if(useExpressionCheckbox.getSelection()){
			splittingProperty.setExpression(true);
			splittingProperty.setValue(propertyValueExpression.getExpression().getText());
		}
		else {
			splittingProperty.setExpression(false);
			splittingProperty.setValue(propertyValue.getText());
		}
		super.okPressed();
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.ReportSplittingPropertyDialog_Title);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.jaspersoft.studio.editor.expression.IExpressionContextSetter#setExpressionContext(com.jaspersoft.studio.editor.expression.ExpressionContext)
	 */
	public void setExpressionContext(ExpressionContext expContext) {
		this.expContext=expContext;
		if(this.propertyValueExpression!=null){
			this.propertyValueExpression.setExpressionContext(this.expContext);
		}
	}

}
