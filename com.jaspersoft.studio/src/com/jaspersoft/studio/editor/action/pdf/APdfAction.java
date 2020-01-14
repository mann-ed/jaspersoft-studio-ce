/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.action.pdf;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchPart;

import com.jaspersoft.studio.JSSCompoundCommand;
import com.jaspersoft.studio.editor.action.CustomSelectionAction;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.model.MGraphicElement;
import com.jaspersoft.studio.property.SetPropertyValueCommand;
import com.jaspersoft.studio.property.SetValueCommand;

import net.sf.jasperreports.engine.JRPropertiesMap;

/**
 * The Class PdfActionAbstract implements common action (most UI related) of the
 * pdf action
 */
public abstract class APdfAction extends CustomSelectionAction {

	/**
	 * Base constructor, construct the inner common object of an action
	 * 
	 * @param part The part for this action
	 */
	public APdfAction(IWorkbenchPart part) {
		super(part, IAction.AS_CHECK_BOX);
		// the property need to be registered
		initUI();
	}

	/**
	 * Abstract method to return the property name
	 * 
	 * @return Property for which one the value must be changed
	 */
	protected abstract Set<String> getPropertyNames();

	/**
	 * Return a string that represent the property value
	 * 
	 * @return a string representing the position value
	 */
	protected abstract String getPropertyValue(String name);

	/**
	 * Create the contextual menu with the label
	 */
	protected abstract void initUI();

	@Override
	public boolean isChecked() {
		ischecked = true;
		List<Object> graphicalElements = editor.getSelectionCache().getSelectionModelForType(MGraphicElement.class);
		if (graphicalElements.isEmpty()) {
			ischecked = false;
		} else {
			for (String attr : getPropertyNames()) {
				String value = getPropertyValue(attr);
				for (Object element : graphicalElements) {
					MGraphicElement model = (MGraphicElement) element;
					JRPropertiesMap v = model.getPropertiesMap();
					if (v == null) {
						ischecked = false;
						break;
					} else {
						Object oldValue = v.getProperty(attr);
						if (oldValue == null || !oldValue.equals(value)) {
							ischecked = false;
							break;
						}
					}
				}
			}
		}
		return ischecked;
	}

	/**
	 * Create the command for the selected action
	 * 
	 * @param model Model of the selected item
	 * @return the command to execute
	 */
	public List<Command> createCommands(MGraphicElement model) {
		List<Command> cmds = new ArrayList<>();
		JRPropertiesMap oldV = (JRPropertiesMap) model.getPropertyValue(APropertyNode.PROPERTY_MAP);
		JRPropertiesMap v = oldV != null ? oldV : new JRPropertiesMap();
		for (String name : getPropertyNames()) {
			Object oldValue = v.getProperty(name);
			String value = getPropertyValue(name);
			if ((value == null && oldValue != null) || (value != null && !value.equals(oldValue)))
				cmds.add(new SetPropertyValueCommand(v, name, value));
		}
		if (!cmds.isEmpty()) {
			SetValueCommand cmd = new SetValueCommand();
			cmd.setTarget(model);
			cmd.setPropertyId(APropertyNode.PROPERTY_MAP);
			cmd.setPropertyValue(v);

			cmds.add(cmd);
		}
		return cmds;
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
			command.addAll(createCommands(grModel));
		}
		return command;
	}

	/**
	 * Performs the create action on the selected objects.
	 */
	@Override
	public void run() {
		execute(createCommand());
	}

}
