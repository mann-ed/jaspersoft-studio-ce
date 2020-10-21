/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.model;

import org.eclipse.jface.resource.ImageDescriptor;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.util.IIconDescriptor;
import com.jaspersoft.studio.server.Activator;
import com.jaspersoft.studio.server.ServerIconDescriptor;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.design.JasperDesign;

public class MJrxml extends AMJrxmlContainer {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public MJrxml(ANode parent, ResourceDescriptor rd, int index) {
		super(parent, rd, index);
	}

	private static IIconDescriptor iconDescriptor;

	public static IIconDescriptor getIconDescriptor() {
		if (iconDescriptor == null)
			iconDescriptor = new ServerIconDescriptor("jrxml"); //$NON-NLS-1$
		return iconDescriptor;
	}

	@Override
	public IIconDescriptor getThisIconDescriptor() {
		return getIconDescriptor();
	}

	public static ResourceDescriptor createDescriptor(ANode parent) {
		ResourceDescriptor rd = AMResource.createDescriptor(parent);
		rd.setWsType(ResourceDescriptor.TYPE_JRXML);
		return rd;
	}

	@Override
	public ImageDescriptor getImagePath() {
		ResourceDescriptor rd = getValue();
		if (rd != null && rd.isMainReport())
			return Activator.getDefault().getImageDescriptor("icons/jrxml_file_main.png");
		return super.getImagePath();
	}

	@Override
	public String getDefaultFileExtension() {
		return "jrxml";
	}

	private JasperDesign jd;

	public void setJd(JasperDesign jd) {
		this.jd = jd;
	}

	public JasperDesign getJd() {
		return jd;
	}
}
