/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.chart.editor;

import java.util.Collections;
import java.util.List;

import net.sf.jasperreports.charts.ChartTheme;
import net.sf.jasperreports.charts.ChartThemeBundle;
import net.sf.jasperreports.chartthemes.simple.ChartThemeSettings;
import net.sf.jasperreports.chartthemes.simple.SimpleChartTheme;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.extensions.ExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsRegistryFactory;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JRCTXExtensionsRegistryFactory implements ExtensionsRegistryFactory, ChartThemeBundle {
	private ChartThemeSettings cts;

	public JRCTXExtensionsRegistryFactory(ChartThemeSettings cts) {
		this.cts = cts;
	}

	public String[] getChartThemeNames() {
		return new String[] { "" };
	}

	private SimpleChartTheme sct;

	public ChartTheme getChartTheme(String themeName) {
		if (sct == null && cts != null)
			sct = new SimpleChartTheme(cts);
		return sct;
	}

	private final ExtensionsRegistry extensionsRegistry = new ExtensionsRegistry() {
		public <T> List<T> getExtensions(Class<T> extensionType) {
			if (ChartThemeBundle.class.equals(extensionType))
				return (List<T>) Collections.singletonList(JRCTXExtensionsRegistryFactory.this);
			return null;
		}
	};

	public ExtensionsRegistry createRegistry(String registryId, JRPropertiesMap properties) {
		return extensionsRegistry;
	}
}
