package com.jaspersoft.studio.server.publish.wizard;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.jaspersoft.studio.property.descriptor.NullEnum;
import com.jaspersoft.studio.property.descriptor.checkbox.CheckBoxLabelProvider;
import com.jaspersoft.studio.server.Activator;
import com.jaspersoft.studio.server.messages.Messages;
import com.jaspersoft.studio.server.model.MResource;
import com.jaspersoft.studio.server.publish.action.JrxmlPublishAction;
import com.jaspersoft.studio.swt.widgets.table.ListContentProvider;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

public class ResourcesPage extends WizardPage {
	private JasperReportsConfiguration jConfig;
	private TableViewer tableViewer;

	protected ResourcesPage(JasperReportsConfiguration jConfig) {
		super("serverrespublish"); //$NON-NLS-1$
		setTitle(Messages.ResourcesPage_title);
		setDescription(Messages.ResourcesPage_description);
		this.jConfig = jConfig;
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		setControl(composite);
		composite.setLayout(new GridLayout());

		Table table = new Table(composite, SWT.V_SCROLL | SWT.MULTI
				| SWT.FULL_SELECTION | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_BOTH);
		table.setLayoutData(gd);
		table.setHeaderVisible(true);

		TableColumn[] col = new TableColumn[2];
		col[0] = new TableColumn(table, SWT.NONE);
		col[0].setText(Messages.ResourcesPage_table_resource);

		col[1] = new TableColumn(table, SWT.NONE);
		col[1].setText(Messages.ResourcesPage_table_overwrite);

		TableLayout tlayout = new TableLayout();
		tlayout.addColumnData(new ColumnWeightData(80, false));
		tlayout.addColumnData(new ColumnWeightData(20, false));
		table.setLayout(tlayout);

		for (TableColumn c : col)
			c.pack();

		tableViewer = new TableViewer(table);
		tableViewer.setContentProvider(new ListContentProvider());
		tableViewer.setLabelProvider(new TLabelProvider());

		attachCellEditors(tableViewer, table);

		fillData();
	}

	private void attachCellEditors(final TableViewer viewer, Composite parent) {
		viewer.setCellModifier(new ICellModifier() {
			public boolean canModify(Object element, String property) {
				if (property.equals("VALUE")) //$NON-NLS-1$
					return true;
				return false;
			}

			public Object getValue(Object element, String property) {
				MResource prop = (MResource) element;
				if ("VALUE".equals(property)) //$NON-NLS-1$
					return prop.getPublishOptions().isOverwrite();
				if ("NAME".equals(property)) { //$NON-NLS-1$
					return prop.getDisplayText();
				}
				return ""; //$NON-NLS-1$
			}

			public void modify(Object element, String property, Object value) {
				TableItem tableItem = (TableItem) element;
				MResource data = (MResource) tableItem.getData();
				if ("VALUE".equals(property)) { //$NON-NLS-1$
					data.getPublishOptions().setOverwrite((Boolean) value);
				}
				tableViewer.update(element, new String[] { property });
				tableViewer.refresh();
			}
		});

		viewer.setCellEditors(new CellEditor[] { new TextCellEditor(parent),
				new CheckboxCellEditor(parent) });
		viewer.setColumnProperties(new String[] { "NAME", "VALUE" }); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void fillData() {
		tableViewer.setInput(JrxmlPublishAction.getResources(jConfig));
		tableViewer.refresh();
	}

	class TLabelProvider extends CellLabelProvider implements
			ITableLabelProvider {
		private CheckBoxLabelProvider chLabelProvider = new CheckBoxLabelProvider(
				NullEnum.NOTNULL);

		public Image getColumnImage(Object element, int columnIndex) {
			MResource fr = (MResource) element;
			switch (columnIndex) {
			case 0:
				return Activator.getImage(fr.getThisIconDescriptor()
						.getIcon16());
			case 1:
				return chLabelProvider.getCellEditorImage(fr
						.getPublishOptions().isOverwrite());
			}
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			MResource fr = (MResource) element;
			switch (columnIndex) {
			case 0:
				return fr.getDisplayText();
			case 1:
				return chLabelProvider.getText(fr.getPublishOptions()
						.isOverwrite());
			}
			return ""; //$NON-NLS-1$
		}

		@Override
		public void update(ViewerCell cell) {
			cell.setText(cell.getElement().toString());
		}

		public String getToolTipText(Object element) {
			return ((MResource) element).getToolTip();
		}

		public Point getToolTipShift(Object object) {
			return new Point(5, 5);
		}

		public int getToolTipDisplayDelayTime(Object object) {
			return 2000;
		}

		public int getToolTipTimeDisplayed(Object object) {
			return 5000;
		}

	}

}
