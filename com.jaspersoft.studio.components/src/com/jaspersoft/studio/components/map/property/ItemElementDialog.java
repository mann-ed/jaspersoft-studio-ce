/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.components.map.property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.jaspersoft.studio.editor.expression.ExpressionContext;
import com.jaspersoft.studio.editor.expression.IExpressionContextSetter;
import com.jaspersoft.studio.property.itemproperty.desc.ItemPropertyBaseLabelProvider;
import com.jaspersoft.studio.widgets.framework.IPropertyEditor;
import com.jaspersoft.studio.widgets.framework.WItemProperty;
import com.jaspersoft.studio.widgets.framework.events.ItemPropertyModifiedEvent;
import com.jaspersoft.studio.widgets.framework.events.ItemPropertyModifiedListener;
import com.jaspersoft.studio.widgets.framework.ui.ItemPropertyDescription;

import net.sf.jasperreports.components.items.Item;
import net.sf.jasperreports.components.items.ItemProperty;
import net.sf.jasperreports.components.items.StandardItem;
import net.sf.jasperreports.components.items.StandardItemProperty;
import net.sf.jasperreports.eclipse.ui.ATitledDialog;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;

/**
 * Base dialog allowing to configure a generic {@link Item} element with its own associated properties.
 * <p>
 * Clients should extend this abstract class providing the needed details in the essentials methods:
 * <ul>
 * 	 <li>{@link #initItemPropertiesDescriptions()}</li>
 * 	 <li>{@link #createItemPropertiesWidgets()}</li>
 * </ul>
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public abstract class ItemElementDialog extends ATitledDialog implements IExpressionContextSetter {
	
	private Item item;
	private IPropertyEditor propEditor;
	private List<ItemPropertyDescription<?>> descriptions;
	private Map<String, WItemProperty> map = new HashMap<String, WItemProperty>();
	private boolean refresh = false;
	private ExpressionContext expContext;
	protected Composite containerCmp;

	public ItemElementDialog(Shell parentShell, Item itemElement) {
		super(parentShell);
		if(itemElement == null) {
			this.item = new StandardItem();
		}
		else {
			this.item = (Item) itemElement.clone();
		}
		propEditor = createElementEditor();
		descriptions = initItemPropertiesDescriptions();
		setDefaultSize(UIUtils.getScaledWidth(500), UIUtils.getScaledHeight(400));
	}
	
	/**
	 * Initializes and return the list of widget descriptions for the item properties
	 * 
	 * @return the list of item properties descriptions
	 */
	protected abstract List<ItemPropertyDescription<?>> initItemPropertiesDescriptions();

	@Override
	protected Control createDialogArea(Composite parent) {
		containerCmp = (Composite) super.createDialogArea(parent);
		((GridLayout)containerCmp.getLayout()).numColumns = 2;
		
		createItemPropertiesWidgets();
		
		UIUtils.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				fillData();
			}
		});
				
		return containerCmp;
	}
	
	/**
	 * Creates the dedicated property widgets based on the set descriptions. 
	 */
	protected abstract void createItemPropertiesWidgets();

	/*
	 * Fill the widgets with the proper data.
	 * 
	 * FIXME - setup the validation on the widgets.
	 */
	private void fillData() {
		refresh = true;
		try {
			for (String key : map.keySet()) {
				WItemProperty expr = map.get(key);
				expr.setExpressionContext(expContext);
				boolean createNew = true;
				for (ItemProperty ip : item.getProperties()) {
					if (ip != null && ip.getName().equals(key)) {
						expr.setValue(ip.getValue(), ip.getValueExpression());
						createNew = false;
						break;
					}
				}
				if (createNew) {
					StandardItemProperty p = new StandardItemProperty();
					p.setName(key);
					expr.setValue(p.getValue(), p.getValueExpression());
				}
			}
		} finally {
			refresh = false;
		}
		setError(null);
		// validateForm();
		tcmp.getParent().update();
		tcmp.getParent().layout(true);
	}
	
	/**
	 * Create the dedicated label and widget for the specified property of the {@link Item} element.
	 * 
	 * @param cmp parent composite
	 * @param key the property key
	 * 
	 * FIXME - setup the validation on the widgets.
	 */
	protected void createItemProperty(Composite cmp, String key) {
		ItemPropertyDescription<?> ipd = getItemPropertyDescription(key);

		if (ipd == null) {
			// Invalid key
			return;
		}

		Label lbl = new Label(cmp, SWT.NONE);
		lbl.setText(ipd.getLabel());
		lbl.setToolTipText(ipd.getToolTip());

		final WItemProperty expr = new WItemProperty(cmp, SWT.NONE, ipd, getElementEditor());
		expr.setLabelProvider(new ItemPropertyBaseLabelProvider());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		expr.setLayoutData(gd);
		expr.addModifyListener(new ItemPropertyModifiedListener() {

			@Override
			public void itemModified(ItemPropertyModifiedEvent event) {
				if (expr.isRefresh()) {
					//validateForm();
					return;
				}
				if (refresh)
					return;
				//validateForm();
			}
		});
		map.put(key, expr);
	}
	
	/**
	 * Returns the widget description for the specified item property.
	 * 
	 * @param id the property id
	 * @return the widget description
	 */
	public ItemPropertyDescription<?> getItemPropertyDescription(String id) {
		for (ItemPropertyDescription<?> ip : descriptions) {
			if (ip.getName().equals(id)) {
				return ip;
			}
		}
		return null;
	}

	private IPropertyEditor createElementEditor() {
		return new ItemElementPropertyEditor(item);
	}
	
	public IPropertyEditor getElementEditor() {
		if(this.propEditor==null) {
			this.propEditor=createElementEditor();
		}
		return this.propEditor;
	}

	@Override
	public void setExpressionContext(ExpressionContext expContext) {
		this.expContext = expContext;
		// FIXME - might need to verify the proper expression context setup
	}
	
	/**
	 * @return the modified {@link Item} element
	 */
	public Item getModifiedItem() {
		// clean-up "useless" properties
		Predicate<ItemProperty> isEmpty = item -> item.getValue()==null && item.getValueExpression()==null;
		this.item.getProperties().removeIf(isEmpty);
		// return the item only if it contains properties
		return this.item.getProperties().isEmpty() ? null : this.item;
	}
}