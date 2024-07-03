/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.widgets.map.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.jaspersoft.studio.widgets.map.messages.Messages;

/**
 * Utility methods for UI related operations.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class MapUIUtils {
	
	public static final String DISABLE_GOOGLEMAP_WIDGET="com.jaspersoft.studio.widgets.googlemap.disabled"; //$NON-NLS-1$

	/**
	 * Creates a warning composite that alerts the user about possible issues
	 * when using the browser widget in Linux.
	 * 
	 * @param parent the parent composite
	 * @return the created warning composite
	 */
	public static Composite createLinuxWarningText(Composite parent) {
		Composite warningCmp = new Composite(parent,SWT.NONE);
		GridLayout cmpL = new GridLayout(2, false);
		warningCmp.setLayout(cmpL);
		Label warningIcon = new Label(warningCmp,SWT.NONE);
		warningIcon.setImage(JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING));
		warningIcon.setLayoutData(new GridData(SWT.FILL, SWT.TOP,false,false));
		Label warningText = new Label(warningCmp,SWT.WRAP);
		warningText.setText(Messages.MapUIUtils_MapLinuxWarningMsg);
		warningText.setToolTipText(Messages.MapUIUtils_MapLinuxWarningTooltip);
		warningText.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
		return warningCmp;
	}
	
	/**
	 * Checks if the Google Map widget support has been disabled.
	 * <p>
	 * If enabled the map dialog will not show the component capable of bidirection Java-Javascript communication.<br/>
	 * This could potentially help in some corner cases, mostly involving the Edge (windows) scenario.
	 * 
	 * @return <code>true</code> if googlemap widget support is disable,<code>false</code> otherwise
	 */
	public static boolean isGoogleMapWidgetDisabled() {
		String wGoogleMapDisabled = System.getProperty(DISABLE_GOOGLEMAP_WIDGET); //$NON-NLS-1$
		return wGoogleMapDisabled != null && wGoogleMapDisabled.equals("true"); //$NON-NLS-1$
	}

}
