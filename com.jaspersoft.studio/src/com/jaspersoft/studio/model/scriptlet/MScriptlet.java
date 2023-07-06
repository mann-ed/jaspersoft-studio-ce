/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.model.scriptlet;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.jaspersoft.studio.help.HelpReferenceBuilder;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.model.ICopyable;
import com.jaspersoft.studio.model.IDragable;
import com.jaspersoft.studio.model.util.IIconDescriptor;
import com.jaspersoft.studio.model.util.NodeIconDescriptor;
import com.jaspersoft.studio.property.descriptor.classname.NClassTypePropertyDescriptor;
import com.jaspersoft.studio.property.descriptor.propexpr.JPropertyExpressionsDescriptor;
import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionDTO;
import com.jaspersoft.studio.property.descriptor.propexpr.PropertyExpressionsDTO;
import com.jaspersoft.studio.property.descriptor.text.NTextPropertyDescriptor;
import com.jaspersoft.studio.utils.ModelUtils;

import net.sf.jasperreports.engine.JRAbstractScriptlet;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRPropertyExpression;
import net.sf.jasperreports.engine.JRScriptlet;
import net.sf.jasperreports.engine.base.JRBaseScriptlet;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignPropertyExpression;
import net.sf.jasperreports.engine.design.JRDesignScriptlet;

/*
 * The Class MScriptlet.
 * 
 * @author Chicu Veaceslav
 */
public class MScriptlet extends APropertyNode implements ICopyable, IDragable {
	
	public static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	/** The icon descriptor. */
	private static IIconDescriptor iconDescriptor;
	
	private static IPropertyDescriptor[] descriptors;

	/**
	 * Gets the icon descriptor.
	 * 
	 * @return the icon descriptor
	 */
	public static IIconDescriptor getIconDescriptor() {
		if (iconDescriptor == null)
			iconDescriptor = new NodeIconDescriptor("scriptlet"); //$NON-NLS-1$
		return iconDescriptor;
	}

	/**
	 * Instantiates a new m scriptlet.
	 */
	public MScriptlet() {
		super();
	}

	/**
	 * Instantiates a new m scriptlet.
	 * 
	 * @param parent
	 *          the parent
	 * @param jfRield
	 *          the jf rield
	 * @param newIndex
	 *          the new index
	 */
	public MScriptlet(ANode parent, JRScriptlet jfRield, int newIndex) {
		super(parent, newIndex);
		setValue(jfRield);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.model.INode#getDisplayText()
	 */
	public String getDisplayText() {
		return ((JRScriptlet) getValue()).getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.model.INode#getImagePath()
	 */
	public ImageDescriptor getImagePath() {
		return getIconDescriptor().getIcon16();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jaspersoft.studio.model.INode#getToolTip()
	 */
	@Override
	public String getToolTip() {
		return getIconDescriptor().getToolTip();
	}

	@Override
	public IPropertyDescriptor[] getDescriptors() {
		return descriptors;
	}

	@Override
	public void setDescriptors(IPropertyDescriptor[] descriptors1) {
		descriptors = descriptors1;
	}

	/**
	 * Creates the property descriptors.
	 * 
	 * @param desc
	 *          the desc
	 */
	@Override
	public void createPropertyDescriptors(List<IPropertyDescriptor> desc) {
		NTextPropertyDescriptor nameD = new NTextPropertyDescriptor(JRDesignScriptlet.PROPERTY_NAME, Messages.common_name);
		nameD.setDescription(Messages.MScriptlet_name_description);
		desc.add(nameD);

		List<Class<?>> clist = new ArrayList<Class<?>>();
		clist.add(JRAbstractScriptlet.class);
		clist.add(JRDefaultScriptlet.class);
		NClassTypePropertyDescriptor classD = new NClassTypePropertyDescriptor(JRDesignScriptlet.PROPERTY_VALUE_CLASS_NAME,
				Messages.common_class);
		classD.setClasses(clist);
		classD.setDescription(Messages.MScriptlet_class_description);
		desc.add(classD);
		classD.setHelpRefBuilder(
				new HelpReferenceBuilder("net.sf.jasperreports.doc/docs/schema.reference.html?cp=0_1#scriptlet_class")); //$NON-NLS-1$

		NTextPropertyDescriptor descriptionD = new NTextPropertyDescriptor(
				JRBaseScriptlet.PROPERTY_DESCRIPTION,Messages.common_description);
		descriptionD.setDescription(Messages.MScriptlet_description_description);
		desc.add(descriptionD);
		descriptionD.setHelpRefBuilder(
				new HelpReferenceBuilder("net.sf.jasperreports.doc/docs/schema.reference.html?cp=0_1#scriptletDescription")); //$NON-NLS-1$
		
		JPropertyExpressionsDescriptor propertiesD = new JPropertyExpressionsDescriptor(
				JRDesignScriptlet.PROPERTY_PROPERTY_EXPRESSIONS, Messages.MScriptlet_PropertiesDescriptorMsg, false);
		propertiesD.setDescription(
				Messages.MScriptlet_PropertiesDescriptorDescription);
		desc.add(propertiesD);

		setHelpPrefix(desc, "net.sf.jasperreports.doc/docs/schema.reference.html?cp=0_1#scriptlet"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
	 */
	public Object getPropertyValue(Object id) {
		JRDesignScriptlet scriptlet = (JRDesignScriptlet) getValue();
		if (id.equals(JRDesignScriptlet.PROPERTY_NAME))
			return scriptlet.getName();
		if (id.equals(JRDesignScriptlet.PROPERTY_VALUE_CLASS_NAME))
			return scriptlet.getValueClassName();
		if (id.equals(JRBaseScriptlet.PROPERTY_DESCRIPTION))
			return scriptlet.getDescription();
		if (id.equals(JRDesignScriptlet.PROPERTY_PROPERTY_EXPRESSIONS)) {
			JRPropertyExpression[] propertyExpressions = scriptlet.getPropertyExpressions();
			if (propertyExpressions != null) {
				propertyExpressions = propertyExpressions.clone();
			}
			return new PropertyExpressionsDTO(propertyExpressions, getPropertiesMapClone(scriptlet), getValue(),
					ModelUtils.getExpressionContext(this));
		}
		return null;
	}
	
	protected JRPropertiesMap getPropertiesMapClone(JRDesignScriptlet scriptlet) {
		JRPropertiesMap propertiesMap = scriptlet.getPropertiesMap();
		if (propertiesMap != null){
			propertiesMap = propertiesMap.cloneProperties();
		}
		return propertiesMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object, java.lang.Object)
	 */
	public void setPropertyValue(Object id, Object value) {
		JRDesignScriptlet scriptlet = (JRDesignScriptlet) getValue();
		if (id.equals(JRDesignScriptlet.PROPERTY_NAME)) {
			if (value instanceof String && !((String) value).isEmpty()) {
				String newName = (String) value;
				String oldName = scriptlet.getName();
				JRDesignDataset d = ModelUtils.getDataset(this);
				if (d != null) {
					Map<String, JRParameter> pmap = d.getParametersMap();
					JRDesignParameter p = (JRDesignParameter) pmap.get(oldName + JRScriptlet.SCRIPTLET_PARAMETER_NAME_SUFFIX);
					if (p != null) {
						p.setName(newName + JRScriptlet.SCRIPTLET_PARAMETER_NAME_SUFFIX);
						pmap.remove(oldName + JRScriptlet.SCRIPTLET_PARAMETER_NAME_SUFFIX);
						pmap.put(newName + JRScriptlet.SCRIPTLET_PARAMETER_NAME_SUFFIX, p);
					}
					scriptlet.setName(newName);
				}
			}
		} else if (id.equals(JRDesignScriptlet.PROPERTY_VALUE_CLASS_NAME)) {
			if (value instanceof String) {
				if (((String) value).isEmpty())
					value = null;
				scriptlet.setValueClassName((String) value);
				JRDesignDataset d = ModelUtils.getDataset(this);
				if (d != null) {
					Map<String, JRParameter> pmap = d.getParametersMap();
					JRDesignParameter p = (JRDesignParameter) pmap
							.get(scriptlet.getName() + JRScriptlet.SCRIPTLET_PARAMETER_NAME_SUFFIX);
					if (p != null)
						p.setValueClassName(scriptlet.getValueClassName());
				}
			}
		} else if (id.equals(JRBaseScriptlet.PROPERTY_DESCRIPTION)) {
			scriptlet.setDescription((String) value);
		} else if (id.equals(JRDesignScriptlet.PROPERTY_PROPERTY_EXPRESSIONS) && value instanceof PropertyExpressionsDTO) {
			PropertyExpressionsDTO dto = (PropertyExpressionsDTO) value;
			JRPropertyExpression[] expr = scriptlet.getPropertyExpressions();
			// Remove the old expression properties if any
			if (expr != null) {
				for (JRPropertyExpression ex : expr)
					scriptlet.removePropertyExpression(ex);
			}
			// Add the new expression properties
			for (PropertyExpressionDTO p : dto.getProperties()) {
				if (p.isExpression()) {
					JRDesignPropertyExpression newExp = new JRDesignPropertyExpression();
					newExp.setName(p.getName());
					newExp.setValueExpression(p.getValueAsExpression());
					scriptlet.addPropertyExpression(newExp);
				}
			}
			// Now change properties, first remove the old ones if any
			JRPropertiesMap originalMap = scriptlet.getPropertiesMap().cloneProperties();
			String[] names = scriptlet.getPropertiesMap().getPropertyNames();
			for (int i = 0; i < names.length; i++) {
				scriptlet.getPropertiesMap().removeProperty(names[i]);
			}
			// Now add the new properties
			for (PropertyExpressionDTO p : dto.getProperties()) {
				if (!p.isExpression()) {
					scriptlet.getPropertiesMap().setProperty(p.getName(), p.getValue());
				}
			}
			// Really important to trigger the event using as source the JR object and not the node.
			// Using the node itself could cause problems with the refresh of the advanced properties view.
			firePropertyChange(
					new PropertyChangeEvent(scriptlet, PROPERTY_MAP, originalMap, scriptlet.getPropertiesMap()));
		} 
	}

	/**
	 * Creates the jr scriptlet.
	 * 
	 * @param jrDataset
	 *          the jr dataset
	 * @return the jR design scriptlet
	 */
	public static JRDesignScriptlet createJRScriptlet(JRDesignDataset jrDataset) {
		JRDesignScriptlet jrScriptlet = new JRDesignScriptlet();
		jrScriptlet.setName(ModelUtils.getDefaultName(jrDataset.getScriptletsMap(), "Scriptlet_")); //$NON-NLS-1$
		jrScriptlet.setValueClass(JRDefaultScriptlet.class);
		return jrScriptlet;

	}

	public ICopyable.RESULT isCopyable2(Object parent) {
		if (parent instanceof MScriptlets)
			return ICopyable.RESULT.COPYABLE;
		return ICopyable.RESULT.CHECK_PARENT;
	}
	
	@Override
	public boolean isCuttable(ISelection currentSelection) {
		return true;
	}
}
