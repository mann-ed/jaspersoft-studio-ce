/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.preview.stats;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class RecordCountScriptlet extends JRDefaultScriptlet {
	private int counter = 0;

	public int getCounter() {
		return counter;
	}

	@Override
	public void afterDetailEval() throws JRScriptletException {
		counter++;
		super.afterDetailEval();
	}
}
