/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.property.descriptor.propexpr.dialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jaspersoft.studio.editor.expression.ExpressionContext;
import com.jaspersoft.studio.property.dataset.DatasetUtil;
import com.jaspersoft.studio.property.infoList.ElementDescription;
import com.jaspersoft.studio.property.metadata.PropertyMetadataRegistry;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.annotations.properties.PropertyScope;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.properties.PropertiesMetadataUtil;
import net.sf.jasperreports.properties.PropertyMetadata;

/**
 * Class that define static methods to get the hint properties specific to some
 * type of elements
 */
public class HintsPropertiesList {

	public static String getToolTip(PropertyMetadata pm) {
		if (pm == null)
			return "";
		String tt = pm.getName() + "\n";
		if (pm.isDeprecated())
			tt += "\nDeprecated\n";
		String sv = pm.getSinceVersion();
		if (!Misc.isNullOrEmpty(sv))
			tt += "\nSince Version: " + sv + "\n";
		tt += "Type: " + pm.getValueType();
		if (!Misc.isNullOrEmpty(pm.getDescription())) {
			if (!Misc.isNullOrEmpty(tt))
				tt += "\n\n";
			tt += pm.getDescription();
		}
		return tt;
	}

	public static List<ElementDescription> getElementProperties(Object holder, ExpressionContext eContext) {
		List<ElementDescription> result = new ArrayList<>();
		for (PropertyMetadata pm : getPropertiesMetadata(holder, eContext))
			result.add(new ElementDescription(pm.getName(), pm.getDescription(), true));
		return result;
	}

	public static List<PropertyMetadata> getPropertiesMetadata(Object holder, ExpressionContext eContext) {
		return getPropertiesMetadata(holder, eContext.getJasperReportsConfiguration());
	}

	public static List<PropertyMetadata> getPropertiesMetadata(Object holder, JasperReportsConfiguration jrContext) {
		List<PropertyMetadata> result = new ArrayList<>();
		PropertiesMetadataUtil pmu = PropertiesMetadataUtil.getInstance(jrContext);
		ClassLoader oldCl = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(JRLoader.class.getClassLoader());
			if (holder instanceof JasperDesign)
				getReportProperties((JasperDesign) holder, jrContext, result, pmu);
			else if (holder instanceof JRDesignDataset)
				getDatasetProperties((JRDesignDataset) holder, jrContext, result, pmu);
			else if (holder instanceof JRElement)
				getElementProperties((JRElement) holder, result, pmu);
			else if (holder instanceof JRElementGroup)
				getElementGroupProperties((JRElementGroup) holder, result, pmu);
			else if (holder instanceof JRField)
				getFieldProperties(jrContext, result, pmu);
			else if (holder instanceof JRParameter)
				getParameterProperties(jrContext, result);
			else
				getContextProperties(result, pmu);
		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			Thread.currentThread().setContextClassLoader(oldCl);
		}
		List<PropertyMetadata> r = new ArrayList<>();
		Set<String> set = new HashSet<>();
		for (PropertyMetadata p : result) {
			if (set.contains(p.getName()))
				continue;
			set.add(p.getName());
			r.add(p);
		}
		return r;
	}

	private static void getContextProperties(List<PropertyMetadata> result, PropertiesMetadataUtil pmu) {
		List<PropertyMetadata> eps = pmu.getProperties();
		if (eps != null)
			for (PropertyMetadata pm : eps)
				if (pm.getScopes().contains(PropertyScope.CONTEXT))
					result.add(pm);

		result.addAll(PropertyMetadataRegistry.getPropertiesMetadata(PropertyScope.CONTEXT));
	}

	private static void getParameterProperties(JasperReportsConfiguration jrContext, List<PropertyMetadata> result) {
		Map<String, PropertyMetadata> map = DatasetUtil.getPmap(jrContext);
		if (map == null) {
			DatasetUtil.refreshPropertiesMap(jrContext);
			map = DatasetUtil.getPmap(jrContext);
		}
		for (String key : map.keySet()) {
			PropertyMetadata pm = map.get(key);
			if (pm.getScopes().contains(PropertyScope.PARAMETER))
				result.add(pm);
		}
	}

	private static void getFieldProperties(JasperReportsConfiguration jrContext, List<PropertyMetadata> result,
			PropertiesMetadataUtil pmu) throws JRException {
		List<PropertyMetadata> eps = pmu
				.getQueryExecuterFieldProperties((String) jrContext.get(COM_JASPERSOFT_STUDIO_DATASET_LANGUAGE));
		if (eps != null)
			for (PropertyMetadata pm : eps)
				if (pm.getScopes().contains(PropertyScope.FIELD))
					result.add(pm);
		result.addAll(PropertyMetadataRegistry.getPropertiesMetadata(PropertyScope.FIELD));
	}

	private static void getElementGroupProperties(JRElementGroup holder, List<PropertyMetadata> result,
			PropertiesMetadataUtil pmu) {
		List<PropertyMetadata> eps = pmu.getContainerProperties(holder);
		if (eps != null)
			result.addAll(eps);
		result.addAll(PropertyMetadataRegistry.getPropertiesMetadata(PropertyScope.BAND));
		result.addAll(PropertyMetadataRegistry.getPropertiesMetadata(PropertyScope.FRAME));
		result.addAll(PropertyMetadataRegistry.getPropertiesMetadata(PropertyScope.CROSSTAB_CELL));
		result.addAll(PropertyMetadataRegistry.getPropertiesMetadata(PropertyScope.TABLE_CELL));
		result.addAll(PropertyMetadataRegistry.getPropertiesMetadata(PropertyScope.TABLE_COLUMN));
	}

	private static void getElementProperties(JRElement holder, List<PropertyMetadata> result,
			PropertiesMetadataUtil pmu) {
		List<PropertyMetadata> eps = pmu.getElementProperties(holder);
		if (eps != null)
			result.addAll(eps);
		result.addAll(PropertyMetadataRegistry.getPropertiesMetadata(PropertyScope.ELEMENT));
	}

	private static void getDatasetProperties(JRDesignDataset ds, JasperReportsConfiguration jrContext,
			List<PropertyMetadata> result, PropertiesMetadataUtil pmu) throws JRException {
		List<PropertyMetadata> eps = pmu.getDatasetProperties(ds, DatasetUtil.getDataAdapter(jrContext, ds));
		if (eps != null)
			result.addAll(eps);
		result.addAll(PropertyMetadataRegistry.getPropertiesMetadata(PropertyScope.DATASET));
	}

	private static void getReportProperties(JasperDesign holder, JasperReportsConfiguration jrContext,
			List<PropertyMetadata> result, PropertiesMetadataUtil pmu) throws JRException {
		List<PropertyMetadata> eps = pmu.getReportProperties(holder);
		if (eps != null)
			result.addAll(eps);
		eps = pmu.getDatasetProperties(holder.getMainDesignDataset(),
				DatasetUtil.getDataAdapter(jrContext, holder.getMainDesignDataset()));
		if (eps != null)
			result.addAll(eps);
		result.addAll(PropertyMetadataRegistry.getPropertiesMetadata(PropertyScope.REPORT));
		result.addAll(PropertyMetadataRegistry.getPropertiesMetadata(PropertyScope.DATASET));
	}

	public static final String COM_JASPERSOFT_STUDIO_DATASET_LANGUAGE = "com.jaspersoft.studio.dataset.language";

}
