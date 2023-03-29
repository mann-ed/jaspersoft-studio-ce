/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.map.property;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.components.map.messages.Messages;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.property.section.AbstractSection;
import com.jaspersoft.studio.property.section.widgets.ASPropertyWidget;

import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.components.map.StandardMapComponent;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;

/**
 * Dedicated widget to edit the "reset map" feature of the {@link StandardMapComponent} element.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class SPResetMap extends ASPropertyWidget<ResetMapPropertyDescriptor> {
	
	private Composite cmp;
	private Text infoTxt;
	private Button editBtn;
	private Button delBtn;

	public SPResetMap(Composite parent, AbstractSection section, ResetMapPropertyDescriptor pDescriptor) {
		super(parent, section, pDescriptor);
	}

	@Override
	protected void createComponent(Composite parent) {
		Composite cmp = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(3,false);
		cmp.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
		gl.marginWidth = 0;
		cmp.setLayout(gl);
		
		infoTxt = new Text(cmp, SWT.BORDER | SWT.READ_ONLY);
		GridData infoTxtGD = new GridData(SWT.FILL,SWT.FILL,true,false);
		infoTxt.setLayoutData(infoTxtGD);
		
		editBtn = new Button(cmp,SWT.FLAT);
		editBtn.setText("..."); //$NON-NLS-1$
		editBtn.setToolTipText(Messages.SPResetMap_EditTooltip);
		editBtn.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
		editBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Object currValue = section.getElement().getPropertyValue(getId());
				Item toEdit = null;
				if(currValue instanceof Item) {
					toEdit = (Item) currValue;
				}
				
				ResetMapItemDialog d = new ResetMapItemDialog(UIUtils.getShell(),toEdit);
				int dResult = d.open();
				if(dResult==Dialog.OK) {
					Item modifiedItem = d.getModifiedItem();
					section.changeProperty(StandardMapComponent.PROPERTY_RESET_MAP, modifiedItem);
					delBtn.setEnabled(modifiedItem!=null);
				}
			}
		});
		
		delBtn = new Button(cmp,SWT.FLAT);
		delBtn.setImage(JaspersoftStudioPlugin.getInstance().getImage("/icons/delete_element.gif")); //$NON-NLS-1$
		delBtn.setToolTipText(Messages.SPResetMap_DeleteTooltip);
		delBtn.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
		delBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean reply = MessageDialog.openQuestion(UIUtils.getShell(), Messages.SPResetMap_ConfirmDeleteTitle, Messages.SPResetMap_ConfirmDeleteMsg);
				if(reply) {
					section.changeProperty(StandardMapComponent.PROPERTY_RESET_MAP, null);
					delBtn.setEnabled(false);
				}
			}
		});
		delBtn.setEnabled(section.getElement().getPropertyValue(getId())!=null);
	}

	@Override
	public void setData(APropertyNode pnode, Object value) {
		if(value instanceof Item) {
			infoTxt.setText(LegendOrResetMapLabelProvider.getLabelText((Item) value));
		}
		else {
			infoTxt.setText(Messages.SPResetMap_NotSet);
		}
	}

	@Override
	public Control getControl() {
		return cmp;
	}
	
}