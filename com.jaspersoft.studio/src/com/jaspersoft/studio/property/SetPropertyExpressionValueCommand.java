/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.property;

import org.eclipse.gef.commands.Command;

import net.sf.jasperreports.engine.JRPropertyExpression;
import net.sf.jasperreports.engine.design.JRDesignElement;

/**
 * Command used to edit a value of a JRPropertiesExpression
 * 
 * @author Orlandin Marco
 *
 */
public class SetPropertyExpressionValueCommand extends Command {

	private JRDesignElement el;

	private String key;
	private JRPropertyExpression newValue;
	private JRPropertyExpression oldValue;

	/**
	 * Create the command
	 * 
	 * @param element The element, must be not null
	 * @param newValue The new value, if null the value will be removed
	 */
	public SetPropertyExpressionValueCommand(JRDesignElement element, String key, JRPropertyExpression newValue) {
		this.el = element;
		this.key = key;
		this.newValue = newValue;
	}

	@Override
	public void execute() {
		oldValue = el.getPropertyExpressionsList().stream().filter(e -> e.getName().equals(key)).findFirst()
				.orElse(null);

		el.removePropertyExpression(key);
		if (newValue != null)
			el.addPropertyExpression(newValue);
	}

	@Override
	public void undo() {
		el.removePropertyExpression(key);
		if (oldValue != null)
			el.addPropertyExpression(oldValue);
	}
}
