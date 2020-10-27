/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.publish.imp.da;

import java.util.HashMap;
import java.util.Map;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;

import net.sf.jasperreports.data.jdbc.JdbcDataAdapter;
import net.sf.jasperreports.eclipse.util.Misc;

public abstract class AImpJdbcDataAdapter {
	private String dname;
	private String[][] keys;

	public AImpJdbcDataAdapter(String dname, String[][] key) {
		this.dname = dname;
		this.keys = key;
	}

	public String[][] getKeys() {
		return keys;
	}

	public String getResourceType(String key) {
		for (int i = 0; i < keys.length; i++)
			if (keys[0][0].equals(key))
				return keys[i][1];
		return ResourceDescriptor.TYPE_CONTENT_RESOURCE;
	}

	protected String getProperties(String url) {
		String prefix = getJdbcPrefix();
		if (url != null && url.startsWith(prefix))
			return url.substring(prefix.length());
		return "";
	}

	protected abstract String getJdbcPrefix();

	public boolean isHandling(JdbcDataAdapter da) {
		return da.getDriver() != null && da.getDriver().equals(dname);
	}

	public Map<String, String> getFileName(JdbcDataAdapter da) {
		if (isHandling(da)) {
			Map<String, String> res = new HashMap<>();
			if (da.getProperties() != null) {
				for (String s : keys[0]) {
					String t = da.getProperties().get(s);
					if (t != null)
						res.put(s, t);
				}
			}
			if (Misc.isNullOrEmpty(res)) {
				String[] props = getProperties(da.getUrl()).split(";");
				for (String p : props) {
					String[] kv = p.split("=");
					for (String t : keys[0])
						if (kv[0].equals(t))
							res.put(kv[0], kv[1]);
				}
			}
			return res;
		}
		return null;
	}

	public boolean setFileName(JdbcDataAdapter da, String key, String fname) {
		if (fname.startsWith("repo:"))
			fname = fname.substring("repo:".length());
		if (isHandling(da)) {
			if (da.getProperties() != null && da.getProperties().get(key) != null)
				da.getProperties().put(key, fname);
			String prefix = getJdbcPrefix();
			String url = da.getUrl().substring(prefix.length());
			String[] props = url.split(";");
			StringBuilder newp = new StringBuilder(prefix);
			String del = "";
			for (String p : props) {
				String[] kv = p.split("=");
				newp.append(del + kv[0]);
				if (kv.length == 2) {
					if (kv[0].equals(key))
						kv[1] = fname;
					newp.append("=" + kv[1]);
				}
				del = ";";
			}
			da.setUrl(newp.toString());
			return true;
		}
		return false;
	}
}
