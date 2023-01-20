/*******************************************************************************
 * Copyright (C) 2010 - 2023. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.action.reportsplitting;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.ResourceManager;

import com.jaspersoft.studio.editor.expression.ExpressionContext;
import com.jaspersoft.studio.editor.expression.IExpressionContextSetter;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.MGraphicElement;
import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionsDTO;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.widgets.framework.WItemProperty;
import com.jaspersoft.studio.widgets.framework.ui.BooleanComboPropertyDescription;
import com.jaspersoft.studio.widgets.framework.ui.TextPropertyDescription;

import net.sf.jasperreports.eclipse.ui.util.PersistentLocationDialog;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.engine.PrintPart;
import net.sf.jasperreports.engine.design.JRDesignElement;

/**
 * Edit dialog to configure the report splitting related properties for the target element.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public class ReportSplittingEditDialog extends PersistentLocationDialog implements IExpressionContextSetter {
	
	public static final String PART_NAME_PROPERTY = PrintPart.ELEMENT_PROPERTY_PART_NAME;
	public static final String PART_SPLIT_PROPERTY = "net.sf.jasperreports.print.part.split"; //$NON-NLS-1$
	public static final String PART_PREFIX_PROPERTY = "net.sf.jasperreports.print.part."; //$NON-NLS-1$
	private static final Point DEFAULT_DIALOG_SIZE = new Point(500,500);
	private static final int REMOVE_ALL_ITEMS_ID = 2000;
	private static final String TRUE_VALUE = BooleanComboPropertyDescription.TRUE_VALUE;
	private static final String FALSE_VALUE = BooleanComboPropertyDescription.FALSE_VALUE;
	
	// Model stuff
	private MGraphicElement element;
	private ExpressionContext expContext;
	private PropertyExpressionsDTO propertiesDTO;
	private ReportSplittingPropertyEditor propertiesEditor;
	
	// Widget stuff
	private WItemProperty partNameProperty;
	private WItemProperty enableSplitProperty;
	private WReportSplittingPropertiesList propertiesList;

	public ReportSplittingEditDialog(MGraphicElement element, JasperReportsConfiguration jconfig, Shell parentShell) {
		super(parentShell);
		this.element=element;
		this.propertiesDTO = 
				((PropertyExpressionsDTO) element.getPropertyValue(JRDesignElement.PROPERTY_PROPERTY_EXPRESSIONS)).clone();
		this.propertiesEditor = new ReportSplittingPropertyEditor(propertiesDTO, jconfig);
		this.expContext = propertiesDTO.geteContext();
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Messages.ReportSplittingEditDialog_Title);
	}
		
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		
		Composite main = new Composite(area,SWT.NONE);
		GridLayout mainGL = new GridLayout(2,false);
		main.setLayout(mainGL);
		main.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));

		Label mcTitle = new Label(main,SWT.NONE);
		mcTitle.setFont(ResourceManager.getBoldFont(mcTitle.getFont()));
		mcTitle.setText(Messages.ReportSplittingEditDialog_EssentialInfoTitle);
		mcTitle.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		
		Label mcSeparator = new Label(main,SWT.SEPARATOR | SWT.HORIZONTAL);
		mcSeparator.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		
		Label partNameLbl = new Label(main,SWT.NONE);
		partNameLbl.setText(Messages.ReportSplittingEditDialog_PartNamePropertyLbl);
		GridData partNameGD = new GridData(SWT.FILL, SWT.TOP, false, false);
		partNameLbl.setLayoutData(partNameGD);
		
		TextPropertyDescription<String> partNameDesc = 
				new TextPropertyDescription<String>(PART_NAME_PROPERTY, Messages.ReportSplittingEditDialog_PartNamePropertyDesc, true);
		partNameProperty = new WItemProperty(main, SWT.NONE, partNameDesc, propertiesEditor);
		partNameProperty.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		partNameProperty.setExpressionContext(expContext);
		partNameProperty.updateWidget();
		
		Label enableSplitLbl = new Label (main,SWT.NONE);
		enableSplitLbl.setText(Messages.ReportSplittingEditDialog_EnableSplitPropertyLbl);
		GridData enableSplitGD = new GridData(SWT.FILL, SWT.TOP, false, false);
		enableSplitLbl.setLayoutData(enableSplitGD);
		
		BooleanComboPropertyDescription enableSplitDesc =
				new BooleanComboPropertyDescription(
						PART_SPLIT_PROPERTY, Messages.ReportSplittingEditDialog_EnableSplitDescLbl, Messages.ReportSplittingEditDialog_EnableSplitDescDescription, false, 
						false, new String[][]{{TRUE_VALUE,"true"},{FALSE_VALUE, "false"}}); //$NON-NLS-1$ //$NON-NLS-2$
		enableSplitDesc.setFallbackValue(false);
		enableSplitProperty = new WItemProperty(main, SWT.NONE, enableSplitDesc, propertiesEditor);
		enableSplitProperty.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		enableSplitProperty.setExpressionContext(expContext);
		enableSplitProperty.updateWidget();
		
		Label opTitle = new Label(main,SWT.NONE);
		opTitle.setFont(ResourceManager.getBoldFont(opTitle.getFont()));
		opTitle.setText(Messages.ReportSplittingEditDialog_AdditionalPropertiesTitle);
		opTitle.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		
		Label opSeparator = new Label(main,SWT.SEPARATOR | SWT.HORIZONTAL);
		opSeparator.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		
		propertiesList = new WReportSplittingPropertiesList(main, SWT.NONE, propertiesEditor);
		propertiesList.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true,2,1));
		
		Label closingSeparator = new Label(main,SWT.SEPARATOR | SWT.HORIZONTAL);
		closingSeparator.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false,2,1));
		
		area.pack();		
		area.layout();

		return area;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, REMOVE_ALL_ITEMS_ID, Messages.ReportSplittingEditDialog_ResetBtn, false);
		super.createButtonsForButtonBar(parent);
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		if(buttonId==REMOVE_ALL_ITEMS_ID) {
			removeAllPropertiesPressed();
		}
		else {
			super.buttonPressed(buttonId);			
		}
	}

	@Override
	public void setExpressionContext(ExpressionContext expContext) {
		this.expContext=expContext;
		if(partNameProperty!=null) {
			partNameProperty.setExpressionContext(expContext);
		}
		if(enableSplitProperty!=null) {
			enableSplitProperty.setExpressionContext(expContext);
		}
	}

	@Override
	protected Point getInitialSize() {
		return UIUtils.getScaledSize(DEFAULT_DIALOG_SIZE);
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}
	
	private void removeAllPropertiesPressed() {
		boolean answer = MessageDialog.openQuestion(getParentShell(), Messages.ReportSplittingEditDialog_ResetDialogTitle, Messages.ReportSplittingEditDialog_ResetDialogMsg);
		if(answer) {
			propertiesEditor.removeAllSplittingProperties();
			propertiesList.refreshList();
			partNameProperty.updateWidget();
			enableSplitProperty.updateWidget();
		}
	}
	
	@Override
	protected void okPressed() {
		element.setPropertyValue(JRDesignElement.PROPERTY_PROPERTY_EXPRESSIONS, propertiesDTO);
		super.okPressed();
	}
}
