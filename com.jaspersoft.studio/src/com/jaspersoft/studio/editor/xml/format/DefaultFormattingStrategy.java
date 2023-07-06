/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.xml.format;

import org.eclipse.jface.text.formatter.IFormattingStrategy;

public class DefaultFormattingStrategy implements IFormattingStrategy {
	protected static final String lineSeparator = System.getProperty("line.separator");

	public DefaultFormattingStrategy() {
		super();
	}

	public void formatterStarts(String initialIndentation) {
	}

	public String format(String content, boolean isLineStart, String indentation, int[] positions) {
		return "";
	}

	public void formatterStops() {
	}

}
