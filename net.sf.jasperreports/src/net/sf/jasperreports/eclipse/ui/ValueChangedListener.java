/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui;

/**
 * Event listener added to a {@link JSSTableCombo} that will be called when 
 * the value in the control changes
 */
public abstract class ValueChangedListener {

	public abstract void valueChanged(ValueChangedEvent e);
}

