/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.designer;

import org.eclipse.swt.widgets.Composite;

import net.sf.jasperreports.engine.design.JRDesignParameter;

public interface IParameterICContributor {
	public void createUI(Composite parent, JRDesignParameter prm, SelectParameterDialog pd, IFilterQuery fq);

	public void refresh(JRDesignParameter prm);
}
