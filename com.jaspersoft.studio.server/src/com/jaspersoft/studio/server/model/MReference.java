/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.model;

import java.io.UnsupportedEncodingException;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.wb.swt.ResourceManager;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.util.IIconDescriptor;
import com.jaspersoft.studio.server.Activator;
import com.jaspersoft.studio.server.ResourceFactory;
import com.jaspersoft.studio.server.ServerIconDescriptor;
import com.jaspersoft.studio.server.protocol.restv2.WsTypes;

import net.sf.jasperreports.engine.JRConstants;

public class MReference extends AMResource {
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public MReference(ANode parent, ResourceDescriptor rd, int index) {
		super(parent, rd, index);
	}

	private static IIconDescriptor iconDescriptor;
	private static ImageDescriptor lINK_DECORATOR_IMAGE = Activator.getDefault()
			.getImageDescriptor("/icons/link_decorator.png");

	public static IIconDescriptor getIconDescriptor() {
		if (iconDescriptor == null)
			iconDescriptor = new ServerIconDescriptor("link") {
//$NON-NLS-0$
			};
		return iconDescriptor;
	}

	@Override
	public IIconDescriptor getThisIconDescriptor() {
		return getIconDescriptor();
	}

	@Override
	public ImageDescriptor getImagePath() {
		if (getValue() != null) {
			ImageDescriptor img = ResourceFactory
					.getIconImageDescriptor(WsTypes.INST().toRestType(getValue().getReferenceType()));
			if (img != null)
				return ResourceManager.decorateImage(img, lINK_DECORATOR_IMAGE, ResourceManager.BOTTOM_LEFT);
		}
		return super.getImagePath();
	}

	public static ResourceDescriptor createDescriptor(ANode parent) {
		ResourceDescriptor rd = AMResource.createDescriptor(parent);
		rd.setIsReference(true);
		rd.setWsType(ResourceDescriptor.TYPE_REFERENCE);
		return rd;
	}

	@Override
	public String getJRSUrl() throws UnsupportedEncodingException {
		return null;
	}
}
