/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.preferences.editor.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wb.swt.SWTResourceManager;

import com.jaspersoft.studio.help.TableHelpListener;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.property.dataset.fields.table.TColumn;
import com.jaspersoft.studio.property.dataset.fields.table.TColumnFactory;
import com.jaspersoft.studio.property.dataset.fields.table.widget.AWidget;
import com.jaspersoft.studio.property.descriptor.propexpr.dialog.HintsPropertiesList;
import com.jaspersoft.studio.property.section.widgets.CustomAutoCompleteField;
import com.jaspersoft.studio.swt.widgets.table.ListContentProvider;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.wizards.ContextHelpIDs;

import net.sf.jasperreports.annotations.properties.PropertyScope;
import net.sf.jasperreports.eclipse.ui.util.PersistentLocationDialog;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.FilePrefUtil;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.properties.PropertyMetadata;
import net.sf.jasperreports.properties.StandardPropertyMetadata;
import net.sf.jasperreports.utils.compatibility.CompatibilityConstants;

/**
 * List field editor to edit the JSS properties. The properties are shown as key
 * and value inside an editable table
 * 
 * @author Orlandin Marco
 * 
 */
public class PropertyListFieldEditor extends FieldEditor {

	/**
	 * The table widget
	 */
	protected Table table;

	/**
	 * The viewer for the table
	 */
	protected TableViewer viewer;

	/**
	 * The input of the viewer
	 */
	protected List<Pair> input;

	/**
	 * The button box containing the Add, Remove, edit and clone buttons
	 */
	private Composite buttonBox;

	/**
	 * The Add button.
	 */
	protected Button addButton;

	/**
	 * The Duplicate button.
	 */
	protected Button duplicateButton;

	/**
	 * The Remove button.
	 */
	protected Button removeButton;

	/**
	 * The edit button
	 */
	protected Button editButton;

	/**
	 * The selection listener.
	 */
	protected SelectionListener selectionListener;

	/**
	 * The columns width
	 */
	private final int[] columnWidths;

	/**
	 * The columns names
	 */
	private final String[] columnNames;

	/**
	 * Simple container to store a key and a value String. Used to provide
	 * content to the table
	 * 
	 * @author Orlandin Marco
	 * 
	 */
	public static class Pair {

		/**
		 * A string representing a key, shown on the left column of the table.
		 */
		private String key;

		/**
		 * The value of the key, shown on the right column of the table
		 */
		private String value;

		/**
		 * Create a new container for a key and a value
		 * 
		 */
		public Pair(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			if (this.getClass() != obj.getClass())
				return false;
			Pair p = (Pair) obj;
			return key.equals(p.getKey())
					&& ((value == null && p.getValue() == null) || (value != null && value.equals(p.getValue())));
		}

		@Override
		public int hashCode() {
			int hash = 1;
			hash = hash * 17 + key.hashCode();
			hash = hash * 31 + (value != null ? value.hashCode() : 0);
			return hash;
		}
	}

	/**
	 * Dialog to edit or add a new properties
	 * 
	 * @author Orlandin Marco
	 * 
	 */
	protected class PEditDialog extends PersistentLocationDialog {

		/**
		 * The name of the parameter
		 */
		private String pname;

		/**
		 * The value of the parameter
		 */
		private String pvalue;

		private JRPropertiesMap pmap;

		/**
		 * Build the dialog with empty value, constructor used to create a new
		 * property
		 * 
		 * @param parentShell the shell to open the dialog
		 */
		protected PEditDialog(Shell parentShell) {
			this(parentShell, null, null);
		}

		/**
		 * Open the dialog with prefilled name\value fileds, used to edit an
		 * existing parameter
		 * 
		 * @param parentShell the shell to create the dialog
		 * @param pname the name of the edited parameter
		 * @param pvalue the value of the edit parameter
		 */
		protected PEditDialog(Shell parentShell, String pname, String pvalue) {
			super(parentShell);
			this.pname = pname;
			this.pvalue = pvalue;
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);
			composite.setLayout(new GridLayout(2, false));
			Label label = new Label(composite, SWT.NONE);
			label.setText(Messages.PropertyListFieldEditor_newPropertyName);

			final Text text = new Text(composite, SWT.BORDER);
			text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
			text.setText(Misc.nvl(pname, "net.sf.jasperreports.")); //$NON-NLS-1$
			new CustomAutoCompleteField(text, new TextContentAdapter(), pkeys.toArray(new String[pkeys.size()]));

			text.addModifyListener(e -> {
				pname = text.getText();
				pvalue = getPValue();
				// rebuild bottom
				boolean dispose = false;
				for (Control c : composite.getChildren()) {
					if (dispose)
						c.dispose();
					if (c == text)
						dispose = true;
				}
				refreshBottom(composite);

				composite.layout(true);
				composite.update();
			});

			refreshBottom(composite);
			return composite;
		}

		private void refreshBottom(Composite composite) {
			PropertyMetadata pm = pmMap.get(pname);
			if (pm != null) {
				TColumn tc = TColumnFactory.getTColumn(pm);
				tc.setLabel(Messages.PropertyListFieldEditor_newPropertyValue);
				pmap = new JRPropertiesMap();
				pmap.setProperty(pname, pvalue);

				AWidget w = TColumnFactory.addWidget(tc, composite, pmap,
						JasperReportsConfiguration.getDefaultInstance());
				w.getElement();
			} else {
				Label label = new Label(composite, SWT.NONE);
				label.setText(Messages.PropertyListFieldEditor_newPropertyValue);

				final Text tname = new Text(composite, SWT.BORDER);
				tname.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
				tname.setText(Misc.nvl(pvalue, Messages.PropertyListFieldEditor_exampleValue));
				tname.addModifyListener(e -> pvalue = tname.getText());
			}

		}

		@Override
		protected boolean isResizable() {
			return true;
		}

		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setSize(500, 200);
			newShell.setText(Messages.PropertyListFieldEditor_newPropertyTitle);
		}

		/**
		 * Return the current value of the parameter name
		 * 
		 * @return a not null string
		 */
		public String getPName() {
			return this.pname;
		}

		/**
		 * Return the current value of the parameter value
		 * 
		 * @return a not null string
		 */
		public String getPValue() {
			if (pmap != null)
				return pmap.getProperty(pname);
			return this.pvalue;
		}

	}

	/**
	 * Build the field editor with a table with two column, one with the key and
	 * the other one with the value. The column name, number and width are fixed
	 * inside the constructor
	 * 
	 * @param name the name of the preference this field editor works on
	 * @param text the label text of the field editor
	 * @param parent the parent composite
	 */
	public PropertyListFieldEditor(String name, String text, Composite parent) {
		init(name, text);
		this.columnNames = new String[] { Messages.PropertyListFieldEditor_propertyLabel,
				Messages.PropertyListFieldEditor_valueLabel };
		this.columnWidths = new int[] { 300, 300 };
		createControl(parent);
	}

	/**
	 * Open the dialog to input a new parameter and return the result when it's
	 * closed
	 * 
	 * @return the value of the parameter if it is closed with ok, null if it is
	 * closed with cancel
	 */
	protected Pair getNewInputObject() {
		PEditDialog dialog = new PEditDialog(UIUtils.getShell());
		if (dialog.open() == Window.OK) {
			String name = dialog.getPName();
			for (Pair p : input) {
				if (p.getKey().equals(name)) {
					p.setValue(dialog.getPValue());
					viewer.refresh();
					return null;
				}
			}
			return new Pair(dialog.getPName(), dialog.getPValue());
		}
		return null;
	}

	/**
	 * Store the values of the parameters inside the configuration
	 */
	protected void doStore() {
		storeProperties(input, getPreferenceStore());
	}

	public static void storeProperties(List<Pair> in, IPreferenceStore store) {
		Properties props = new Properties();
		for (Pair item : in) {
			String k = item.getKey();
			String v = item.getValue();

			PropertyMetadata pm = pmMap.get(k);
			if (pm != null && pm.getDefaultValue() != null && pm.getDefaultValue().equals(v))
				v = null;
			if (v != null)
				props.setProperty(k, v);

			if (k.equals("net.sf.jasperreports.default.font.name") //$NON-NLS-1$
					|| k.equals("net.sf.jasperreports.default.font.size")) //$NON-NLS-1$
				setContextProperty(k, v);
		}
		store.setValue(FilePrefUtil.NET_SF_JASPERREPORTS_JRPROPERTIES, FileUtils.getPropertyAsString(props));
	}

	private static void setContextProperty(String k, String v) {
		if (v != null)
			JRPropertiesUtil.getInstance(JasperReportsConfiguration.getDefaultInstance()).setProperty(k, v);
		else
			JRPropertiesUtil.getInstance(JasperReportsConfiguration.getDefaultInstance()).removeProperty(k);
	}

	/**
	 * Load the values of the parameters from the configuration
	 */
	protected void doLoad() {
		if (getTable() != null) {
			try {
				input = loadProperties(getPreferenceStore());
				viewer.setInput(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// Add an help listener to the table
			TableHelpListener.setTableHelp(getTable());
		}
	}

	private static List<PropertyMetadata> pmds = HintsPropertiesList.getPropertiesMetadata(null,
			JasperReportsConfiguration.getDefaultInstance());
	private static Map<String, PropertyMetadata> pmMap = new HashMap<>();
	private static List<String> pkeys;

	private static List<String> getPropertyKeys() {
		if (pkeys == null) {
			pkeys = new ArrayList<>();
			for (PropertyMetadata pm : pmds) {
				pkeys.add(pm.getName());
				pmMap.put(pm.getName(), pm);
			}
			Map<String, String> jrprops = DefaultJasperReportsContext.getInstance().getProperties();
			for (String k : jrprops.keySet()) {
				if (pkeys.contains(k))
					continue;
				StandardPropertyMetadata spm = new StandardPropertyMetadata();
				spm.setName(k);
				List<PropertyScope> scopes = new ArrayList<>();
				scopes.add(PropertyScope.CONTEXT);
				spm.setScopes(scopes);
				spm.setDefaultValue(jrprops.get(k));
				spm.setValueType(String.class.getCanonicalName());
				pkeys.add(k);
				pmMap.put(spm.getName(), spm);
			}

			Collections.sort(pkeys);
		}
		return pkeys;
	}

	public static List<Pair> loadProperties(IPreferenceStore store) throws IOException {
		Properties props = FileUtils.load(store.getString(FilePrefUtil.NET_SF_JASPERREPORTS_JRPROPERTIES));
		List<Pair> input = new ArrayList<>();
		for (String key : getPropertyKeys())
			input.add(new Pair(key, props.getProperty(key)));
		for (String key : props.stringPropertyNames()) {
			boolean found = false;
			for (Pair pair : input)
				if (pair.key.equals(key)) {
					found = true;
					break;
				}
			if (!found)
				input.add(new Pair(key, props.getProperty(key)));
		}

		return input;
	}

	/**
	 * Load the value of the default parameters from the configuration
	 */
	protected void doLoadDefault() {
		if (getTable() != null) {
			getTable().removeAll();
			try {
				Properties props = FileUtils
						.load(getPreferenceStore().getDefaultString(FilePrefUtil.NET_SF_JASPERREPORTS_JRPROPERTIES));

				input = new ArrayList<>();
				for (String key : getPropertyKeys()) {
					String value = props.getProperty(key);
					input.add(new Pair(key, value));
				}
				viewer.setInput(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void createControl(Composite parent) {
		super.createControl(parent);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, ContextHelpIDs.PREFERENCES_PROPERTIES);
	}

	/**
	 * Create the selection listener for the table
	 */
	public void createSelectionListener() {
		selectionListener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Widget widget = event.widget;
				if (widget == addButton) {
					addPressed();
				} else if (widget == duplicateButton) {
					duplicatePressed();
				} else if (widget == removeButton) {
					removePressed();
				} else if (widget == editButton) {
					editPressed();
				} else if (widget == table) {
					selectionChanged();
				}
			}
		};
	}

	/**
	 * Action executed when the edit button is pressed, open the dialog to edit
	 * the parameter and then
	 */
	protected void editPressed() {
		StructuredSelection selection = (StructuredSelection) viewer.getSelection();
		if (!selection.isEmpty()) {
			Pair element = (Pair) selection.getFirstElement();
			int selIdx = input.indexOf(element);
			element = input.get(selIdx);
			String pname = element.getKey();
			String pvalue = element.getValue();
			PEditDialog dialog = new PEditDialog(UIUtils.getShell(), pname, pvalue);
			if (dialog.open() == Window.OK) {
				String newPName = dialog.getPName();
				String newPValue = dialog.getPValue();
				if (!pname.equals(newPName))
					// ensure no duplicates
					for (int i = 0; i < table.getItemCount(); i++)
						if (i != selIdx && newPName.equals(table.getItem(i).getText(0))) {
							MessageDialog.openError(UIUtils.getShell(), Messages.PropertyListFieldEditor_ErrTitle,
									Messages.PropertyListFieldEditor_ErrMsg);
							return;
						}

				element.setKey(newPName);
				element.setValue(newPValue);
				viewer.refresh();
			}
		}
	}

	/**
	 * Enable or disable the table buttons accordingly to the current selection
	 */
	protected void selectionChanged() {
		int index = table.getSelectionIndex();
		int size = table.getItemCount();
		if (duplicateButton != null)
			duplicateButton.setEnabled(index >= 0);
		if (removeButton != null)
			removeButton.setEnabled(index >= 0);
		boolean isMultiSelection = table.getSelectionCount() > 1;
		if (editButton != null)
			editButton.setEnabled(!isMultiSelection && size >= 1 && index >= 0 && index < size);
	}

	protected void createButtons(Composite box) {
		addButton = createPushButton(box, Messages.common_add);
		duplicateButton = createPushButton(box, Messages.PropertyListFieldEditor_duplicateButton);
		removeButton = createPushButton(box, Messages.common_delete);
		editButton = createPushButton(box, Messages.common_edit);
	}

	/**
	 * Return the current table
	 * 
	 * @return return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Return the column names
	 * 
	 * @return array of names for each column
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * Return the Add button.
	 * 
	 * @return the button
	 */
	protected Button getAddButton() {
		return addButton;
	}

	/**
	 * Return the Duplicate button.
	 * 
	 * @return the button
	 */
	protected Button getDuplicateButton() {
		return duplicateButton;
	}

	/**
	 * Return the Remove button.
	 * 
	 * @return the button
	 */
	protected Button getRemoveButton() {
		return removeButton;
	}

	/**
	 * Helper method to create a push button.
	 * 
	 * @param parent the parent control
	 * @param key the resource name used to supply the button's label text
	 * @return Button
	 */
	protected Button createPushButton(Composite parent, String key) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(key);
		button.setFont(parent.getFont());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		int widthHint = convertHorizontalDLUsToPixels(button, IDialogConstants.BUTTON_WIDTH);
		data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
		button.setLayoutData(data);
		button.addSelectionListener(getSelectionListener());
		return button;
	}

	/*
	 * (non-Javadoc) Method declared on FieldEditor.
	 */
	protected void adjustForNumColumns(int numColumns) {
		Control control = getLabelControl();
		((GridData) control.getLayoutData()).horizontalSpan = numColumns;
		((GridData) table.getLayoutData()).horizontalSpan = numColumns - 1;
	}

	/*
	 * (non-Javadoc) Method declared on FieldEditor.
	 */
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		Control control = getLabelControl(parent);
		GridData gd = new GridData();
		gd.horizontalSpan = numColumns;
		control.setLayoutData(gd);

		Composite composite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2;
		gridData.minimumWidth = 500;
		gridData.heightHint = 500;
		composite.setLayoutData(gridData);
		composite.setLayout(new GridLayout(2, false));

		table = getTableControl(composite);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = numColumns - 1;
		gd.grabExcessHorizontalSpace = true;
		for (int width : columnWidths)
			gd.widthHint += width;
		table.setLayoutData(gd);

		buttonBox = getButtonBoxControl(composite);
		gd = new GridData();
		gd.verticalAlignment = GridData.BEGINNING;
		buttonBox.setLayoutData(gd);
	}

	/**
	 * Returns this field editor's button box containing the Add, Remove, Up,
	 * and Down button.
	 * 
	 * @param parent the parent control
	 * @return the button box
	 */
	public Composite getButtonBoxControl(Composite parent) {
		if (buttonBox == null) {
			buttonBox = new Composite(parent, SWT.NULL);
			GridLayout layout = new GridLayout();
			layout.marginWidth = 0;
			buttonBox.setLayout(layout);
			createButtons(buttonBox);
			buttonBox.addDisposeListener(event -> {
				addButton = null;
				duplicateButton = null;
				removeButton = null;
				buttonBox = null;
			});

		} else {
			checkParent(buttonBox, parent);
		}

		selectionChanged();
		return buttonBox;
	}

	/**
	 * Returns this field editor's table control.
	 * 
	 * @param parent the parent control
	 * @return the table control
	 */
	public Table getTableControl(Composite parent) {
		if (table == null) {
			viewer = new TableViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
			table = viewer.getTable();
			table.setFont(parent.getFont());
			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			viewer.setContentProvider(new ListContentProvider());

			ColumnViewerToolTipSupport.enableFor(viewer, ToolTip.NO_RECREATE);

			table.addSelectionListener(getSelectionListener());
			int index = 0;
			for (String columnName : columnNames) {
				TableViewerColumn tcol = new TableViewerColumn(viewer, SWT.NONE);
				tcol.getColumn().setText(columnName);
				tcol.setLabelProvider(new PairColumnLabelProvider(index));
				index++;
			}
			if (columnNames.length > 0) {
				TableLayout layout = new TableLayout();
				if (columnNames.length > 1)
					for (int i = 0; i < (columnNames.length - 1); i++)
						layout.addColumnData(new ColumnWeightData(0, columnWidths[i], false));
				layout.addColumnData(new ColumnWeightData(100, columnWidths[columnNames.length - 1], true));
				table.setLayout(layout);
			}
			// attachCellEditors();

			viewer.addDoubleClickListener(event -> editPressed());
		}
		return table;
	}

	class PairColumnLabelProvider extends ColumnLabelProvider implements IColorProvider {
		private int index;

		public PairColumnLabelProvider(int index) {
			this.index = index;
		}

		@Override
		public String getText(Object element) {
			Pair p = (Pair) element;
			if (index == 0) {
				return p.getKey();
			} else if (index == 1) {
				if (p.getValue() != null)
					return p.getValue();
				PropertyMetadata pm = pmMap.get(p.getKey());
				if (pm != null) {
					String def = pm.getDefaultValue();
					if (def != null)
						return def;
				}
			}
			return null;
		}

		@Override
		public String getToolTipText(Object element) {
			Pair p = (Pair) element;
			String tt = p.key;
			PropertyMetadata pm = pmMap.get(p.getKey());
			if (pm != null)
				tt += HintsPropertiesList.getToolTip(pm);
			return tt;
		}

		@Override
		public Color getForeground(Object element) {
			Pair p = (Pair) element;
			if (p.getValue() == null) {
				PropertyMetadata pm = pmMap.get(p.getKey());
				if (pm.isDeprecated() && index == 0)
					return SWTResourceManager.getColor(CompatibilityConstants.Colors.COLOR_WIDGET_DISABLED_FOREGROUND);
				if (index == 1) {
					String def = pm.getDefaultValue();
					if (def != null)
						return SWTResourceManager
								.getColor(CompatibilityConstants.Colors.COLOR_WIDGET_DISABLED_FOREGROUND);
				}
			}
			return null;
		}

		@Override
		public Font getFont(Object element) {
			Pair p = (Pair) element;
			PropertyMetadata pm = pmMap.get(p.getKey());
			if (pm != null && pm.isDeprecated())
				return FontStyles.getItalicFont();
			return super.getFont(element);
		}

	}

	public static final class FontStyles {
		private static FontData[] italicValues;

		public static Font getItalicFont() {
			if (italicValues == null) {
				final Font originalFont = Display.getCurrent().getSystemFont();
				italicValues = originalFont.getFontData();
				for (int i = 0; i < italicValues.length; i++)
					italicValues[i].setStyle(italicValues[i].getStyle() | SWT.ITALIC);
			}
			return new Font(Display.getCurrent(), italicValues);
		}

	}

	/**
	 * Return the number of columns in the table
	 */
	public int getNumberOfControls() {
		return columnNames.length;
	}

	/**
	 * Returns this field editor's selection listener. The listener is created
	 * if necessary.
	 * 
	 * @return the selection listener
	 */
	protected SelectionListener getSelectionListener() {
		if (selectionListener == null)
			createSelectionListener();
		return selectionListener;
	}

	/**
	 * Returns this field editor's shell.
	 * <p>
	 * This method is internal to the framework; subclassers should not call
	 * this method.
	 * </p>
	 * 
	 * @return the shell
	 */
	protected Shell getShell() {
		if (addButton == null)
			return null;
		return addButton.getShell();
	}

	/**
	 * Notifies that the Add button has been pressed.
	 */
	protected void addPressed() {
		setPresentsDefaultValue(false);
		Pair newInputObject = getNewInputObject();
		if (newInputObject != null) {
			input.add(newInputObject);
			viewer.refresh();
		}
	}

	/**
	 * Notifies that the Add button has been pressed.
	 */
	protected void duplicatePressed() {
		setPresentsDefaultValue(false);
		StructuredSelection sel = (StructuredSelection) viewer.getSelection();
		for (Object obj : sel.toList()) {
			Pair selectedElement = (Pair) obj;
			int index = input.indexOf(obj);
			if (index >= 0) {
				Pair newElement = new Pair(selectedElement.getKey() + "_copy", selectedElement.getValue());
				if (index < (input.size() - 1))
					input.add(index + 1, newElement);
				else
					input.add(newElement);
				viewer.refresh();
			}
		}
		selectionChanged();
	}

	/**
	 * Notifies that the Remove button has been pressed.
	 */
	protected void removePressed() {
		setPresentsDefaultValue(false);
		StructuredSelection sel = (StructuredSelection) viewer.getSelection();
		for (Object obj : sel.toList()) {
			int indx = input.indexOf(obj);
			if (indx > -1) {
				Pair p = input.get(indx);
				if (pmMap.containsKey(p.getKey()))
					p.setValue(null);
				else
					input.remove(indx);
			}
		}
		viewer.refresh();
		selectionChanged();
	}

	/**
	 * If not null set the focus on the table control
	 */
	@Override
	public void setFocus() {
		if (table != null)
			table.setFocus();
	}

	/**
	 * enable or disable all the controls where the user can interact. If the
	 * table wasn't created yet then it is also created as child of the passed
	 * composite parameter
	 * 
	 * @param enabled true if the controls must be enabled, false otherwise
	 * 
	 * @param parent the parent of the table if it need to be created
	 */
	@Override
	public void setEnabled(boolean enabled, Composite parent) {
		super.setEnabled(enabled, parent);
		getTableControl(parent).setEnabled(enabled);
		if (addButton != null)
			addButton.setEnabled(enabled);
		if (duplicateButton != null)
			duplicateButton.setEnabled(enabled);
		if (removeButton != null)
			removeButton.setEnabled(enabled);
		if (editButton != null)
			editButton.setEnabled(enabled);
	}
}
