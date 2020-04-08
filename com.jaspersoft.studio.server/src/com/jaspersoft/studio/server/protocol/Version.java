/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.protocol;

import java.util.HashMap;
import java.util.Map;

import com.jaspersoft.jasperserver.dto.serverinfo.ServerInfo;
import com.jaspersoft.jasperserver.dto.serverinfo.ServerInfo.ServerEdition;
import com.jaspersoft.studio.compatibility.JRXmlWriterHelper;

public class Version {
	public static boolean isPro(ServerInfo si) {
		return si.getEdition() == ServerEdition.PRO;
	}

	private static final Map<String, String> jrs2jr = new HashMap<>();
	static {
		jrs2jr.put("7.1.0", "6.4.3");
		jrs2jr.put("7.1.1", "6.4.3");
		jrs2jr.put("7.1.2", "6.4.3");
		jrs2jr.put("7.2.0", "6.5.1");
		jrs2jr.put("7.5.0", "6.11.0");
	}

	public static String setJRVersion(ServerInfo si) {
		String v = jrs2jr.get(si.getVersion());
		if (v != null)
			return v;
		for (String av : JRXmlWriterHelper.getVersionsSet()) {
			if (av.equals(si.getVersion()))
				return av;
		}
		return JRXmlWriterHelper.LAST_VERSION;
	}

	public static boolean isEstimated(ServerInfo si) {
		for (String av : JRXmlWriterHelper.getVersionsSet()) {
			if (av.equals(si.getVersion()))
				return false;
		}
		return true;
	}

	public static boolean isDateRangeSupported(ServerInfo si) {
		return si.getVersion().startsWith("5.");
	}

	public static boolean isXMLACoonnectionSupported(IConnection c) {
		if (c.isSupported(Feature.SEARCHREPOSITORY)) {
			ServerInfo si = c.getServerInfo();
			return si.getVersion().startsWith("5.5") || si.getVersion().startsWith("5.6");
		}
		return true;
	}

	public static boolean isGreaterThan(ServerInfo si, String version) {
		return si.getVersion().compareTo(version) >= 0;
	}
}
