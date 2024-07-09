/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui;

import org.eclipse.swt.widgets.TableItem;

/**
 * Event fired when using the JSSTableCombo and changing its value
 * 
 * @author Orlandin Marco
 */
public class ValueChangedEvent {

	/**
	 * The text of the selected item
	 */
	private String selectedText;
	
	/**
	 * The {@link TableItem} selected in the combo, can be null
	 * if the value was typed and not selected
	 */
	private TableItem selectedItem;
	
	/**
	 * Flag used to know if the value was typed or selected
	 */
	private boolean isTyped;

	public ValueChangedEvent(String text, TableItem selectedItem, boolean isTyped) {
		super();
		this.selectedText = text;
		this.selectedItem = selectedItem;
		this.isTyped = isTyped;
	}

	public String getText() {
		return selectedText;
	}

	public TableItem getSelectedItem() {
		return selectedItem;
	}

	public boolean isTyped() {
		return isTyped;
	}
	
	
	
}
