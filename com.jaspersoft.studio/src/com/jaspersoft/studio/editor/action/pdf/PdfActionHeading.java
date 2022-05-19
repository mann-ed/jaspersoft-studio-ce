/*******************************************************************************
 * Copyright (C) 2010 - 2022. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.action.pdf;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchPart;

import com.jaspersoft.studio.JSSCompoundCommand;
import com.jaspersoft.studio.editor.action.CustomSelectionAction;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.model.MGraphicElement;
import com.jaspersoft.studio.property.SetValueCommand;

import net.sf.jasperreports.engine.JRPropertiesMap;

/** 
 * Action for heading tags used by JasperReports for Section 508 PDF export.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public class PdfActionHeading extends CustomSelectionAction {
	
	/* JasperReports property for heading tag */
	public static final String EXPORT_PDF_ACCESSIBILITY_TAG = "net.sf.jasperreports.export.accessibility.tag";
	
	/* Action IDs */
	public static final String ID_TABLE_HEADING_H1 = "PdfAction_Table_Heading_H1";
	public static final String ID_TABLE_HEADING_H2 = "PdfAction_Table_Heading_H2";
	public static final String ID_TABLE_HEADING_H3 = "PdfAction_Table_Heading_H3";
	public static final String ID_TABLE_HEADING_H4 = "PdfAction_Table_Heading_H4";
	public static final String ID_TABLE_HEADING_H5 = "PdfAction_Table_Heading_H5";
	public static final String ID_TABLE_HEADING_H6 = "PdfAction_Table_Heading_H6";
	
	private Heading actionHeading;

	public PdfActionHeading(IWorkbenchPart part, Heading actionHeading) {
		super(part,IAction.AS_CHECK_BOX);
		this.actionHeading=actionHeading;
		PropertiesList.addItem(EXPORT_PDF_ACCESSIBILITY_TAG);
		initUI();
	}
	
	
	protected String getPropertyName() {
		return EXPORT_PDF_ACCESSIBILITY_TAG;
	}

	@Override
	public boolean isChecked() {
		ischecked = true;
		List<Object> graphicalElements = editor.getSelectionCache().getSelectionModelForType(MGraphicElement.class);
		if (graphicalElements.isEmpty()) {
			ischecked = false;
		} else {
			String attributeId = getPropertyName();
			String value = actionHeading.getTextValue();
			for (Object element : graphicalElements) {
				MGraphicElement model = (MGraphicElement) element;
				JRPropertiesMap v = model.getPropertiesMap();
				if (v == null) {
					ischecked = false;
					break;
				} else {
					Object oldValue = v.getProperty(attributeId);
					if (oldValue == null || !oldValue.equals(value)) {
						ischecked = false;
						break;
					}
				}
			}
		}
		return ischecked;
	}
	
	/**
	 * Create the contextual menu with the label
	 */
	protected void initUI() {
		switch (actionHeading) {
		case H1:
			setId(ID_TABLE_HEADING_H1);
			setText(Heading.H1.getName());
			setToolTipText(null);
			setImageDescriptor(null);
			setDisabledImageDescriptor(null);
			break;
		case H2:
			setId(ID_TABLE_HEADING_H2);
			setText(Heading.H2.getName());
			setToolTipText(null);
			setImageDescriptor(null);
			setDisabledImageDescriptor(null);
			break;
		case H3:
			setId(ID_TABLE_HEADING_H3);
			setText(Heading.H3.getName());
			setToolTipText(null);
			setImageDescriptor(null);
			setDisabledImageDescriptor(null);
			break;
		case H4:
			setId(ID_TABLE_HEADING_H4);
			setText(Heading.H4.getName());
			setToolTipText(null);
			setImageDescriptor(null);
			setDisabledImageDescriptor(null);
			break;
		case H5:
			setId(ID_TABLE_HEADING_H5);
			setText(Heading.H5.getName());
			setToolTipText(null);
			setImageDescriptor(null);
			setDisabledImageDescriptor(null);
			break;
		case H6:
			setId(ID_TABLE_HEADING_H6);
			setText(Heading.H6.getName());
			setToolTipText(null);
			setImageDescriptor(null);
			setDisabledImageDescriptor(null);
			break;
		}
	}
	
	/**
	 * Create the command for the selected action
	 * 
	 * @param model Model of the selected item
	 * @return the command to execute
	 */
	public Command createCommand(MGraphicElement model) {
		SetValueCommand cmd = new SetValueCommand();
		cmd.setTarget(model);
		cmd.setPropertyId(APropertyNode.PROPERTY_MAP);
		String name = getPropertyName();
		JRPropertiesMap v = (JRPropertiesMap) model.getPropertyValue(APropertyNode.PROPERTY_MAP);
		Object oldValue = null;
		if (v == null) {
			v = new JRPropertiesMap();
		} else {
			oldValue = v.getProperty(name);
			v.removeProperty(name);
		}
		String value = actionHeading.getTextValue();
		if (value != null && !value.equals(oldValue))
			v.setProperty(name, value);
		cmd.setPropertyValue(v);
		return cmd;
	}

	@Override
	protected Command createCommand() {
		List<Object> graphicalElements = editor.getSelectionCache().getSelectionModelForType(MGraphicElement.class);
		if (graphicalElements.isEmpty())
			return null;
		JSSCompoundCommand command = new JSSCompoundCommand(getText(), null);
		for (Object element : graphicalElements) {
			MGraphicElement grModel = (MGraphicElement) element;
			command.setReferenceNodeIfNull(grModel);
			command.add(createCommand(grModel));
		}
		return command;
	}

	@Override
	public void run() {
		execute(createCommand());
	}	
}
