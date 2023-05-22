/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.property.color.chooser;

import com.jaspersoft.studio.utils.AlfaRGB;

/**
 * Interface to define a color provider
 * 
 * @author Orlandin Marco
 *
 */
public interface IColorProvider {
	
	/**
	 * Get the color selected by the color provider
	 * 
	 * @return the alfa rgb of the color
	 */
	public AlfaRGB getSelectedColor();
	
	/**
	 * Dispose the color provider
	 */
	public void dispose();
}
