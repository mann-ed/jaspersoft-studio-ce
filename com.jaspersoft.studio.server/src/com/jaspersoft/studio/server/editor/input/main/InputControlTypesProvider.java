/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.editor.input.main;

import com.jaspersoft.studio.editor.preview.input.IDataInput;
import com.jaspersoft.studio.editor.preview.input.ext.IInputControlTypeProvider;

public class InputControlTypesProvider implements IInputControlTypeProvider {

	@Override
	public IDataInput[] getInputControlTypes() {
		return new IDataInput[] { new UserInput() };
	}

}
