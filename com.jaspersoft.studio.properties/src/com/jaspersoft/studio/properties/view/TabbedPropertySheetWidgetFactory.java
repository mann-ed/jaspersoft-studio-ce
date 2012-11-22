/*******************************************************************************
 * Copyright (C) 2010 - 2012 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, 
 * the following license terms apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
package com.jaspersoft.studio.properties.view;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.jaspersoft.studio.properties.util.SWTResourceManager;

/**
 * A FormToolkit customized for use by tabbed property sheet page.
 * 
 * @author Anthony Hunter
 */
public class TabbedPropertySheetWidgetFactory extends FormToolkit {

	/**
	 * private constructor.
	 */
	public TabbedPropertySheetWidgetFactory() {
		super(Display.getCurrent());
	}

	/**
	 * Creates the tab folder as a part of the form.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param style
	 *            the tab folder style.
	 * @return the tab folder
	 */
	public CTabFolder createTabFolder(Composite parent, int style) {
		CTabFolder tabFolder = new CTabFolder(parent, style);
		return tabFolder;
	}

	/**
	 * Creates the tab item as a part of the tab folder.
	 * 
	 * @param tabFolder
	 *            the parent.
	 * @param style
	 *            the tab folder style.
	 * @return the tab item.
	 */
	public CTabItem createTabItem(CTabFolder tabFolder, int style) {
		CTabItem tabItem = new CTabItem(tabFolder, style);
		return tabItem;
	}

	/**
	 * Creates the list as a part of the form.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param style
	 *            the list style.
	 * @return the list.
	 */
	public List createList(Composite parent, int style) {
		List list = new org.eclipse.swt.widgets.List(parent, style);
		return list;
	}

	public Composite createComposite(Composite parent, int style) {
		Composite c = super.createComposite(parent, style);
		paintBordersFor(c);
		return c;
	}

	public Composite createComposite(Composite parent) {
		Composite c = createComposite(parent, SWT.NONE);
		return c;
	}

	/**
	 * Creates a plain composite as a part of the form.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param style
	 *            the composite style.
	 * @return the composite.
	 */
	public Composite createPlainComposite(Composite parent, int style) {
		Composite c = super.createComposite(parent, style);
		c.setBackground(parent.getBackground());
		paintBordersFor(c);
		return c;
	}

	/**
	 * Creates a scrolled composite as a part of the form.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param style
	 *            the composite style.
	 * @return the composite.
	 */
	public ScrolledComposite createScrolledComposite(Composite parent, int style) {
		ScrolledComposite scrolledComposite = new ScrolledComposite(parent,
				style);
		return scrolledComposite;
	}

	/**
	 * Creates a combo box as a part of the form.
	 * 
	 * @param parent
	 *            the combo box parent.
	 * @param comboStyle
	 *            the combo box style.
	 * @return the combo box.
	 */
	public CCombo createCCombo(Composite parent, int comboStyle) {
		CCombo combo = new CCombo(parent, comboStyle);
		adapt(combo, true, false);
		// Bugzilla 145837 - workaround for no borders on Windows XP
		if (getBorderStyle() == SWT.BORDER) {
			combo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		}
		return combo;
	}

	/**
	 * Creates a combo box as a part of the form.
	 * 
	 * @param parent
	 *            the combo box parent.
	 * @return the combo box.
	 */
	public CCombo createCCombo(Composite parent) {
		return createCCombo(parent, SWT.FLAT | SWT.READ_ONLY);
	}

	/**
	 * Creates a combo box as a part of the form.
	 * 
	 * @param parent
	 *            the combo box parent.
	 * @param comboStyle
	 *            the combo box style.
	 * @return the combo box.
	 */
	public Combo createCombo(Composite parent, int comboStyle) {
		Combo combo = new Combo(parent, comboStyle);
//		adapt(combo, true, false);
		// Bugzilla 145837 - workaround for no borders on Windows XP
//		if (getBorderStyle() == SWT.BORDER) {
//			combo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
//		}
		return combo;
	}

	/**
	 * Creates a combo box as a part of the form.
	 * 
	 * @param parent
	 *            the combo box parent.
	 * @return the combo box.
	 */
	public Combo createCombo(Composite parent) {
		return createCombo(parent, SWT.FLAT | SWT.READ_ONLY);
	}

	/**
	 * Creates a group as a part of the form.
	 * 
	 * @param parent
	 *            the group parent.
	 * @param text
	 *            the group title.
	 * @return the composite.
	 */
	public Group createGroup(Composite parent, String text) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText(text);
		group.setForeground(getColors().getForeground());
		paintBordersFor(group);
		return group;
	}

	@Override
	public Button createButton(Composite parent, String text, int style) {
		Button button = new Button(parent, style | SWT.FLAT
				| Window.getDefaultOrientation());
		if (text != null)
			button.setText(text);
		return button;
	}

	/**
	 * Creates a flat form composite as a part of the form.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @return the composite.
	 */
	public Composite createFlatFormComposite(Composite parent) {
		Composite composite = createComposite(parent);
		FormLayout layout = new FormLayout();
		layout.marginWidth = ITabbedPropertyConstants.HSPACE + 2;
		layout.marginHeight = ITabbedPropertyConstants.VSPACE;
		layout.spacing = ITabbedPropertyConstants.VMARGIN + 1;
		composite.setLayout(layout);
		return composite;
	}

	/**
	 * Creates a label as a part of the form.
	 * 
	 * @param parent
	 *            the label parent.
	 * @param text
	 *            the label text.
	 * @return the label.
	 */
	public CLabel createCLabel(Composite parent, String text) {
		return createCLabel(parent, text, SWT.NONE);
	}

	/**
	 * Creates a label as a part of the form.
	 * 
	 * @param parent
	 *            the label parent.
	 * @param text
	 *            the label text.
	 * @param style
	 *            the label style.
	 * @return the label.
	 */
	public CLabel createCLabel(Composite parent, String text, int style) {
		final CLabel label = new CLabel(parent, style);
		label.setText(text);
		return label;
	}

	public Composite createSection(Composite parent, String text,
			boolean expandable, int columns) {
		return createSection(parent, text, expandable, columns, 1, SWT.NONE);
	}

	public Composite createSection(Composite parent, String text,
			boolean expandable, int columns, int span) {
		return createSection(parent, text, expandable, columns, span, SWT.NONE);
	}

	public Composite createSectionTitle(Composite parent, String text,
			boolean expandable, int columns, int span) {
		int style = Section.EXPANDED | Section.TITLE_BAR;
		if (expandable)
			style = style | Section.TWISTIE;
		// Section section = createSection(parent, style);
		Section section = new Section(parent, style);
		section.titleBarTextMarginWidth = 0;
		// section.marginWidth = 2;
		section.setTitleBarBorderColor(SWTResourceManager
				.getColor(SWT.COLOR_GRAY));
		section.setTitleBarBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));

		// section.setFont(SWTResourceManager.getBoldFont(section.getFont()));
		if (parent.getLayout() instanceof GridLayout) {
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = span;
			section.setLayoutData(gd);
		}
		section.setText(text);
		// section.setSeparatorControl(new Label(section, SWT.SEPARATOR
		// | SWT.HORIZONTAL));

		parent = createComposite(section, SWT.BORDER);
		GridLayout layout = new GridLayout(columns, false);
		layout.marginHeight = 4;
		layout.marginWidth = 2;
		parent.setLayout(layout);

		section.setClient(parent);
		return parent;
	}

	public Composite createSection(Composite parent, String text,
			boolean expandable, int columns, int span, int style) {
		style = style | Section.EXPANDED;
		if (expandable)
			style = style | Section.TREE_NODE;
		Section section = new Section(parent, style);
		section.titleBarTextMarginWidth = 0;

		section.setFont(SWTResourceManager.getBoldFont(section.getFont()));

		if (parent.getLayout() instanceof GridLayout) {
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = span;
			section.setLayoutData(gd);
		}
		section.setText(text);
		section.setSeparatorControl(new Label(section, SWT.SEPARATOR
				| SWT.HORIZONTAL));

		parent = createComposite(section, SWT.NONE);
		GridLayout layout = new GridLayout(columns, false);
		layout.marginHeight = 4;
		layout.marginWidth = 2;
		parent.setLayout(layout);

		section.setClient(parent);
		return parent;
	}

	public void dispose() {
		if (getColors() != null) {
			super.dispose();
		}
	}
}
