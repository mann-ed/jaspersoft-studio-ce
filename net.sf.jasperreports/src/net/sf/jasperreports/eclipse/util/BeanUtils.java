/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Utility methods for managing JavaBeans.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class BeanUtils {

	/**
	 * Returns a detailed list of all the property descriptors for the specified class.<br/>
	 * It improves the list of results returned by the {@link PropertyUtils#getPropertyDescriptors(Class)} API.
	 * <p>
	 * The Apache Common method appears to return a truncated list in case the specified {@link Class} instance
	 * is an interface.
	 * 
	 * @param clazz the class instance to inspect
	 * @return an array of property descriptors
	 */
	public static PropertyDescriptor[] getAllPropertyDescriptors(Class<?> clazz) {
		if(clazz.isInterface()){
			return getInterfacePropertyDescriptors(clazz);
		}
		else {
			return PropertyUtils.getPropertyDescriptors(clazz);
		}
	}

	/**
	 * Special handling of interface class when detecting the property descriptors.
	 * See {@link #getAllPropertyDescriptors(Class)}
	 */
	private static PropertyDescriptor[] getInterfacePropertyDescriptors(Class<?> interfaceClazz) {
		List<PropertyDescriptor> interfaceDescriptors = new ArrayList<PropertyDescriptor>();
		interfaceDescriptors.addAll(Arrays.asList(PropertyUtils.getPropertyDescriptors(interfaceClazz)));
		
		Class<?>[] implementedInterfaces = interfaceClazz.getInterfaces();
		if(implementedInterfaces != null){
			for(Class<?> c : implementedInterfaces){
				List<PropertyDescriptor> tmpDescriptors = Arrays.asList(getAllPropertyDescriptors(c));
        for (PropertyDescriptor superPropDescriptor : tmpDescriptors) {
          PropertyDescriptor existingPropDescriptor = findPropDescriptorByName(interfaceDescriptors, superPropDescriptor.getName());
          if (existingPropDescriptor == null) {
          	// brand new descriptor
          	interfaceDescriptors.add(superPropDescriptor);
          } else {
          	// if needed properly fix an existing descriptor
            try {
              if (existingPropDescriptor.getReadMethod() == null) {
                existingPropDescriptor.setReadMethod(superPropDescriptor.getReadMethod());
              }
              if (existingPropDescriptor.getWriteMethod() == null) {
                existingPropDescriptor.setWriteMethod(superPropDescriptor.getWriteMethod());
              }
            } catch (IntrospectionException e) {
              JasperReportsPlugin.getDefault().logError(e);
            }
          }
        }
			}
		}

		return interfaceDescriptors.toArray(new PropertyDescriptor[interfaceDescriptors.size()]);
	}
	
	/**
	 * Finds out a property descriptor using its name.
	 */
  private static PropertyDescriptor findPropDescriptorByName(List<PropertyDescriptor> descriptors, String name) {
    if(descriptors!=null && name!=null){
	    for (PropertyDescriptor pd : descriptors) {
	      if (name.equals(pd.getName())) {
	      	return pd;
	      }
	    }
    }
    return null;
  }
  
}
