/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.publish.wizard.page;

/**
 * Implementors will provide a method that deals with 
 * the datasource selection event.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public interface DatasourceSelectionListener {

	void datasourceSelectionChanged();
	
}
