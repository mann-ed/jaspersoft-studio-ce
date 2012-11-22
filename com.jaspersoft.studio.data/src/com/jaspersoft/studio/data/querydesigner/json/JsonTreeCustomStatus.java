/*******************************************************************************
 * Copyright (C) 2010 - 2012 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, 
 * the following license terms apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
package com.jaspersoft.studio.data.querydesigner.json;

import org.eclipse.swt.graphics.Image;

import com.jaspersoft.studio.data.Activator;
import com.jaspersoft.studio.data.designer.tree.NodeBoldStyledLabelProvider.CustomStyleStatus;
import com.jaspersoft.studio.data.messages.Messages;
import com.jaspersoft.studio.utils.ResourceManager;

/**
 * Enumeration for custom states of the treeviewer containing
 * the Json document representation.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public enum JsonTreeCustomStatus implements CustomStyleStatus{
	LOADING_JSON(Messages.JsonTreeCustomStatus_LoadingJsonMessage,"icons/waiting.gif"), //$NON-NLS-2$
	ERROR_LOADING_JSON(Messages.JsonTreeCustomStatus_ErrorLoadingMessage, "icons/error.gif"), //$NON-NLS-2$
	FILE_NOT_FOUND(Messages.JsonTreeCustomStatus_NoJsonMessage, "icons/warning.gif"); //$NON-NLS-2$
	
	private String message;
	private String imagePath;
	
	private JsonTreeCustomStatus(String message, String imagePath) {
		this.message=message;
		this.imagePath=imagePath;
	}				
	
	public String getMessage(){
		return this.message;
	}
	
	public Image getImage() {
		return ResourceManager.getPluginImage(Activator.PLUGIN_ID,imagePath);
	}

}
