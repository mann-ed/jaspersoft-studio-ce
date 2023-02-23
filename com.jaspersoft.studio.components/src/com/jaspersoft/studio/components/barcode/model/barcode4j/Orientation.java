/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.barcode.model.barcode4j;

import com.jaspersoft.studio.components.barcode.messages.Messages;

public class Orientation {
	public static String[] getItems() {
		return new String[] { Messages.Orientation_up, Messages.Orientation_left, Messages.Orientation_down,
				Messages.Orientation_right };
	}

}
