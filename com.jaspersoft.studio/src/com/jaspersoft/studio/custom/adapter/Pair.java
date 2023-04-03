/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.custom.adapter;

/**
 * Container to store the full name of a class, so in the form
 * package.classname
 * 
 * @author Orlandin Marco
 *
 */
public class Pair{
	
	/**
	 * The package value
	 */
	private String packageName;

	/**
	 * The simple name of the class
	 */
	private String className;

	/**
	 * Create the container 
	 * 
	 * @param packageName the package value
	 * @param className the simple name of the class
	 */
	public Pair(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}
	
	/**
	 * Return the package name of the class
	 * 
	 * @return the package as string
	 */
	public String getPackageName() {
		return packageName;
	}
	
	/**
	 * Return the simple class name
	 * 
	 * @return the class name, without the package
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Return the complete classname in the form package.classname
	 */
	@Override
	public String toString() {
		return packageName + "." + className;
	}
}
