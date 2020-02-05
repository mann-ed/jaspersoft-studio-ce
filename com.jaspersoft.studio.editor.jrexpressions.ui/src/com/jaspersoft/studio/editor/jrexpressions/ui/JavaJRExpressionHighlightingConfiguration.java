/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.jrexpressions.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

import com.jaspersoft.studio.utils.UIUtil;

/**
 * Custom class containing the list of available configurations for the JRExpression(s) elements.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class JavaJRExpressionHighlightingConfiguration extends DefaultHighlightingConfiguration {

	public static final String FIELD_TOKEN="Field"; //$NON-NLS-1$
	public static final String PARAM_TOKEN="Parameter"; //$NON-NLS-1$
	public static final String VARIABLE_TOKEN="Variable"; //$NON-NLS-1$
	public static final String FUNCTION_METHOD="Library function"; //$NON-NLS-1$
	public static final String RESOURCE_BUNDLE_KEY="ResourceBundle Key"; //$NON-NLS-1$
	
	public static final String COLOR_DEFINITION_PREFIX = "com.jaspersoft.studio.editor.jrexpressions.ui.colors.";	//$NON-NLS-1$
	public static final String PARAMETER_COLOR_DEFINITION_ID = COLOR_DEFINITION_PREFIX + "PARAMETER_TOKEN";	//$NON-NLS-1$
	public static final String FIELD_COLOR_DEFINITION_ID = COLOR_DEFINITION_PREFIX + "FIELD_TOKEN";	//$NON-NLS-1$
	public static final String VARIABLE_COLOR_DEFINITION_ID = COLOR_DEFINITION_PREFIX + "VARIABLE_TOKEN";	//$NON-NLS-1$
	public static final String FUNCTIONMETHOD_COLOR_DEFINITION_ID = COLOR_DEFINITION_PREFIX + "FUNCTION_METHOD";	//$NON-NLS-1$
	public static final String RBKEY_COLOR_DEFINITION_ID = COLOR_DEFINITION_PREFIX + "RESOURCE_BUNDLE_KEY";	//$NON-NLS-1$
	
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		addElementConfiguration(acceptor, PARAM_TOKEN, UIUtil.getColor(PARAMETER_COLOR_DEFINITION_ID), SWT.BOLD);	//dark theme: same	
		addElementConfiguration(acceptor, VARIABLE_TOKEN, UIUtil.getColor(VARIABLE_COLOR_DEFINITION_ID), SWT.BOLD);	//dark theme: 0 100 255
		addElementConfiguration(acceptor, FIELD_TOKEN, UIUtil.getColor(FIELD_COLOR_DEFINITION_ID), SWT.BOLD);	//dark theme: same
		addElementConfiguration(acceptor, FUNCTION_METHOD, UIUtil.getColor(FUNCTIONMETHOD_COLOR_DEFINITION_ID), SWT.ITALIC);//dark theme: same
		addElementConfiguration(acceptor, RESOURCE_BUNDLE_KEY, UIUtil.getColor(RBKEY_COLOR_DEFINITION_ID), SWT.BOLD);//dark theme: 139 46 153
	}

	public void addElementConfiguration(IHighlightingConfigurationAcceptor acceptor, String s, Color c, int style) {
		TextStyle textStyle = new TextStyle(); 
		textStyle.setColor(c.getRGB());
		textStyle.setStyle(style);
		acceptor.acceptDefaultHighlighting(s, s, textStyle);
	}
}
