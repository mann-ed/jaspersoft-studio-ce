/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse;

/**
 * Interface for a keyboard event fired from the {@link JasperReportsPlugin} when 
 * a keyboard key is pressed or release
 * 
 * @author Orlandin Marco
 */
public interface IKeyboardEvent {

	/**
	 * Called when a key is pressed
	 * 
	 * @param keycode the code of the pressed key
	 */
	public void keyUp(int keycode);

	/**
	 * Called when a key is released
	 * 
	 * @param keycode the code of the released key. Can be SWT.DEFAULT when this
	 * is triggered from an event that doesen't involve a key (like a focus lost of the application)
	 */
	public void keyDown(int keycode);
	
}
