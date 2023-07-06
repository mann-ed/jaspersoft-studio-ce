/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.model;

import java.util.List;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRSubreportReturnValue;
import net.sf.jasperreports.engine.design.JRDesignSubreport;

import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class JReportsDTO {
	private JasperReportsConfiguration jConfig;
	private List<JRSubreportReturnValue> value;
	private JRDesignSubreport subreport;
	private JRDataset subreportDataset;
	
	public void setjConfig(JasperReportsConfiguration jConfig) {
		this.jConfig = jConfig;
	}

	public JasperReportsConfiguration getjConfig() {
		return jConfig;
	}

	public JRDesignSubreport getSubreport() {
		return subreport;
	}
	
	public JRDataset getDataset() {
		return subreportDataset;
	}

	public void setSubreport(JRDesignSubreport subreport, JRDataset dataset) {
		this.subreport = subreport;
		this.subreportDataset = dataset;
	}

	public List<JRSubreportReturnValue> getValue() {
		return value;
	}

	public void setValue(List<JRSubreportReturnValue> value) {
		this.value = value;
	}

}
