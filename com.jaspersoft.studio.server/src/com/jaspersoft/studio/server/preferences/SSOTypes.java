/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.preferences;

public enum SSOTypes {
	CAS;

	public static String[] getLabels() {
		return new String[] { CAS.name() };
	}

	public static int getIndex(SSOTypes type) {
		switch (type) {
		case CAS:
			return 0;
		}
		return 0;
	}

	public String toString() {
		return name();
	}
}
