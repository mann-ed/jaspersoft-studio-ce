/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.builder;

/**
 * Definition of a JR, contains the url to download it and it's version. The
 * definitions can be compared on their versions
 * 
 * @author Orlandin Marco
 *
 */
public class JRDefinition implements Comparable<JRDefinition> {

	/**
	 * URL to download the JR zip
	 */
	private String resourceURL;

	/**
	 * Version of the JR that will be downloaded
	 */
	private String version;

	public JRDefinition() {
	}

	/**
	 * Create a new JR definition
	 * 
	 * @param resourceURL
	 *            URL to download the JR zip
	 * @param version
	 *            Version of the JR that will be downloaded
	 */
	public JRDefinition(String resourceURL, String version) {
		this.resourceURL = resourceURL;
		this.version = version;
	}

	public void setResourceURL(String resourceURL) {
		this.resourceURL = resourceURL;
	}

	/**
	 * Return the url of the zip of jr
	 * 
	 * @return an url as string
	 */
	public String getResourceURL() {
		return resourceURL;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Get the version of JR pointed by the url
	 * 
	 * @return a version in the format X.Y.Z
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Compare the version number to know if this version is newer or not than
	 * the passed one
	 */
	@Override
	public int compareTo(JRDefinition o) {
		if (o.getVersion() == null)
			return 1;
		String[] thisParts = version.split("\\.");
		String[] thatParts = o.getVersion().split("\\.");
		int length = Math.max(thisParts.length, thatParts.length);
		for (int i = 0; i < length; i++) {
			int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
			int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
			if (thisPart < thatPart)
				return -1;
			if (thisPart > thatPart)
				return 1;
		}
		return 0;
	}

	/**
	 * Clone the current JRDefinition
	 */
	@Override
	public Object clone() {
		return new JRDefinition(resourceURL, version);
	}
}
