/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.builder.jdt;

import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;

public class CompilationUnit implements ICompilationUnit {
	protected String srcCode;
	protected String className;

	public CompilationUnit(String srcCode, String className) {
		this.srcCode = srcCode;
		this.className = className;
	}

	public char[] getFileName() {
		return className.toCharArray();
	}

	public char[] getContents() {
		return srcCode.toCharArray();
	}

	public char[] getMainTypeName() {
		return className.toCharArray();
	}

	public char[][] getPackageName() {
		return new char[0][0];
	}

	public boolean ignoreOptionalProblems() {
		return false;
	}
}
