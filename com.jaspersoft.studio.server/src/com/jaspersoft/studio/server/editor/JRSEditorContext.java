/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.editor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jaspersoft.studio.editor.context.AEditorContext;
import com.jaspersoft.studio.server.ServerManager;
import com.jaspersoft.studio.server.model.server.ServerProfile;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.repo.RepositoryService;

public class JRSEditorContext extends AEditorContext {
	@Override
	public String getName() {
		return "JasperReports Server";
	}

	@Override
	protected void configRepositoryPaths(List<RepositoryService> list) {
		Set<String> rset = new HashSet<>();
		if (f.isLinked())
			add(list, rset, f.getRawLocation().toFile().getParentFile().getAbsolutePath());
		if (!f.getParent().isVirtual())
			add(list, rset, f.getParent().getLocation().toFile().getAbsolutePath());

		Path fpath = Paths.get(f.getLocationURI().toASCIIString());
		Path ppath = Paths.get(f.getProject().getLocationURI().toASCIIString());
		for (ServerProfile sp : ServerManager.getServerList()) {
			Path jrsp = Paths.get(sp.getProjectPath());
			if (jrsp.startsWith(ppath) && fpath.startsWith(jrsp)) {
				add(list, rset, sp.getProjectPath());
				break;
			}
		}
		add(list, rset, f.getProject().getLocation().toFile().getAbsolutePath());
	}

	@Override
	public void initClassloader() {
		// here, he should look into report unit, then into JRS (project) classpath
		super.initClassloader();
	}

	@Override
	public boolean needCompilation() {
		return false;
	}

	@Override
	public boolean saveOnPreview() {
		return true;
	}

	@Override
	public String jrVersion() {
		return "any";
	}

	@Override
	public List<DataAdapter> getDataAdapters() {
		return new ArrayList<>();
	}
}
