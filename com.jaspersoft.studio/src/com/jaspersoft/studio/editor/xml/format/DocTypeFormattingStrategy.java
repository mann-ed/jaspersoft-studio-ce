/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.xml.format;

public class DocTypeFormattingStrategy extends DefaultFormattingStrategy {

	public String format(String content, boolean isLineStart, String indentation, int[] positions) {
		return lineSeparator + content;
	}

}
