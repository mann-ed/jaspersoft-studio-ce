/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.wizards;

import org.eclipse.jface.wizard.Wizard;

/**
 * This is interface should be implemented by all clients that want to be notified
 * about the status of wizard.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public interface WizardEndingStateListener {

	/**
	 * This method is invoked when the {@link Wizard#performFinish()} method has been called.
	 * It allows to perform additional operations if needed.
	 */
	void performFinishInvoked();
	

	/**
	 * This method is invoked when the {@link Wizard#performCancel()} method has been called.
	 * It allows to perform additional operations if needed.
	 */
	void performCancelInvoked();
	
}
