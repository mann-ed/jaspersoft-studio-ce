/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.editor.input;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;

public interface IInputControls {

	public IInputControls getInstance();

	public void createControl(Composite composite, VInputControls icForm);

	public ResourceDescriptor getICContainerUri(String uri);

	public void initICOptions(InputControlsManager icm, ResourceDescriptor rdrepunit, IProgressMonitor monitor);
}
