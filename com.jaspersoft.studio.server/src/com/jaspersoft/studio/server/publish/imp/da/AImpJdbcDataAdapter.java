/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.publish.imp.da;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.data.jdbc.JdbcDataAdapter;
import net.sf.jasperreports.eclipse.util.Misc;

public abstract class AImpJdbcDataAdapter {
	private String dname;
	private String[] keys;

	public AImpJdbcDataAdapter(String dname, String[] key) {
		this.dname = dname;
		this.keys = key;
	}

	public String[] getKeys() {
		return keys;
	}

	protected String getProperties(String url) {
		String prefix = getJdbcPrefix();
		if (url != null && url.startsWith(prefix))
			return url.substring(prefix.length());
		return "";
	}

	protected abstract String getJdbcPrefix();

	public Map<String, String> getFileName(JdbcDataAdapter da) {
		if (da.getDriver() != null && da.getDriver().equals(dname)) {
			Map<String, String> res = new HashMap<>();
			if (da.getProperties() != null) {
				for (String s : keys) {
					String t = da.getProperties().get(s);
					if (t != null)
						res.put(s, t);
				}
			}
			if (Misc.isNullOrEmpty(res)) {
				String[] props = getProperties(((JdbcDataAdapter) da).getUrl()).split(";");
				for (String p : props) {
					String[] kv = p.split("=");
					for (String t : keys)
						if (kv[0].equals(t))
							res.put(kv[0], kv[1]);
				}
			}
			return res;
		}
		return null;
	}

	public void setFileName(JdbcDataAdapter da, String key, String fname) {
		if (fname.startsWith("repo:"))
			fname = fname.substring("repo:".length());
		if (da.getDriver() != null && da.getDriver().equals(dname)) {
			if (da.getProperties() != null && da.getProperties().get(key) != null)
				da.getProperties().put(key, fname);
			String prefix = getJdbcPrefix();
			String url = ((JdbcDataAdapter) da).getUrl().substring(prefix.length());
			String[] props = url.split(";");
			String newp = "";
			String del = "";
			for (String p : props) {
				String[] kv = p.split("=");
				if (kv[0].equals(key))
					kv[1] = fname;
				newp += del + kv[0] + "=" + kv[1];
				del = ";";
			}
			da.setUrl(prefix + newp);
		}
	}
}
