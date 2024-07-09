/*******************************************************************************
 * Copyright (C) 2010 - 2017. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.utils;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.osgi.util.NLS;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRPropertiesUtil.PropertySuffix;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.util.ClassLoaderResource;
import net.sf.jasperreports.engine.util.ClassUtils;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.ObjectUtils;
import net.sf.jasperreports.extensions.DefaultExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsRegistryFactory;

/**
 * Utility class with accessory methods for handling JasperReports extensions.
 *  
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class JRExtensionsUtils {

	
	/**
	 * Performs the "re-load" of the specified extensions in the classpath as normally performed by JasperReports.
	 * 
	 * @param extensionType the type of extensions to load
	 * @param registryId the registry id for the specific extension
	 * @return the list of extensions
	 * @see DefaultExtensionsRegistry
	 */
	public static <T> List<T> getReloadedExtensions(Class<T> extensionType, String registryId){
		List<T> extensions = new ArrayList<T>();
		Map<URL, JRPropertiesMap> allPropertiesMap = new HashMap<URL, JRPropertiesMap>();
		List<ClassLoaderResource> classLoaderResources = JRLoader.getClassLoaderResources(DefaultExtensionsRegistry.EXTENSION_RESOURCE_NAME);
		for(ClassLoaderResource cr : classLoaderResources){
			JRPropertiesMap properties = JRPropertiesMap.loadProperties(cr.getUrl());
			URL duplicateURL = detectDuplicate(properties, allPropertiesMap);
			if(duplicateURL==null){
				List<PropertySuffix> factoryProps = JRPropertiesUtil.getProperties(properties, DefaultExtensionsRegistry.PROPERTY_REGISTRY_FACTORY_PREFIX);
				for (Iterator<PropertySuffix> it = factoryProps.iterator(); it.hasNext();)
				{
					PropertySuffix factoryProp = it.next();
					String factoryRegistryId = factoryProp.getSuffix();
					String factoryClass = factoryProp.getValue();
					try {
						if(factoryRegistryId.equals(registryId)){
							ExtensionsRegistryFactory factory = (ExtensionsRegistryFactory)	ClassUtils.instantiateClass(factoryClass, ExtensionsRegistryFactory.class);
							ExtensionsRegistry registry = factory.createRegistry(registryId, properties);
							extensions.addAll(registry.getExtensions(extensionType));
						}
					} catch (JRRuntimeException ex) {
						ex.printStackTrace();
						JasperReportsPlugin.getDefault().logError(MessageFormat.format("Unable to load the extension class {0} for registry {1}", new Object[] {factoryClass, registryId}), ex);
					}
				}
				allPropertiesMap.put(cr.getUrl(), properties);
			}
			else {
				JasperReportsPlugin.getDefault().logWarning(
						NLS.bind("Extension resource {0} was found to be a duplicate of {1}", new Object[]{cr.getUrl(),duplicateURL}));
			}
		}
		return extensions;
	}
	
	/*
	 * Detects possible duplicate of the properties file containing the extensions information.
	 */
	private static URL detectDuplicate(JRPropertiesMap properties, Map<URL, JRPropertiesMap> allPropertiesMap) {
		for (URL url : allPropertiesMap.keySet()) {
			JRPropertiesMap entryProperties = allPropertiesMap.get(url);
			if (ObjectUtils.equals(properties, entryProperties)) {
				return url;
			}
		}
		return null;
	}
}
