/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.map.property;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.jaspersoft.studio.property.descriptor.JSSDialogCellEditor;

import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.components.map.StandardMapComponent;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;

/**
 * Dedicated cell editor for the "Reset Map" feature of the {@link StandardMapComponent}.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class ResetMapCellEditor extends JSSDialogCellEditor {
	
	private LegendOrResetMapLabelProvider labelProvider;
	
	public ResetMapCellEditor(Composite parent) {
		super(parent, true);
	}

	public ResetMapCellEditor(Composite parent, int style) {
		super(parent, style, true);
	}

	@Override
	protected Object openDialogBox(Control cellEditorWindow) {
		Item toEdit = null;
		if(getValue() instanceof Item) {
			toEdit = (Item) getValue();
		}
		ResetMapItemDialog d = new ResetMapItemDialog(UIUtils.getShell(),toEdit);
		if(d.open() == Dialog.OK) {
			return d.getModifiedItem();
		}
		else {
			return getValue();
		}
	}
	
	@Override
	protected void updateContents(Object value) {
		if (getDefaultLabel() == null) {
			return;
		}
		if (labelProvider == null)
			labelProvider = new LegendOrResetMapLabelProvider();
		String text = labelProvider.getText(value);
		getDefaultLabel().setText(text);
	}
}