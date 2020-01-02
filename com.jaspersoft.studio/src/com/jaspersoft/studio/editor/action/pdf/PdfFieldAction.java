package com.jaspersoft.studio.editor.action.pdf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;

import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.model.MGraphicElement;
import com.jaspersoft.studio.property.descriptor.NullEnum;
import com.jaspersoft.studio.utils.EnumHelper;
import com.jaspersoft.studio.utils.UIUtil;

import net.sf.jasperreports.eclipse.ui.ATitledDialog;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.type.PdfFieldBorderStyleEnum;
import net.sf.jasperreports.engine.export.type.PdfFieldCheckTypeEnum;
import net.sf.jasperreports.engine.export.type.PdfFieldTypeEnum;

public class PdfFieldAction extends APdfAction {

	public static final String ID_PDF_FIELD_ACTION = "ID_PDF_FIELD_ACTION";
	private Map<String, String> values = new HashMap<>();

	public PdfFieldAction(IWorkbenchPart part) {
		super(part);
	}

	@Override
	protected void initUI() {
		setId(ID_PDF_FIELD_ACTION);
		setText("Field");
		setToolTipText("Setup PDF field");
		setImageDescriptor(null); // $NON-NLS-1$
		setDisabledImageDescriptor(null); // $NON-NLS-1$
	}

	@Override
	protected boolean calculateEnabled() {
		List<Object> graphicalElements = editor.getSelectionCache().getSelectionModelForType(MGraphicElement.class);
		// here, probably we should limit to TextField, shapes, frame ...
		return graphicalElements.size() == 1;
	}

	@Override
	protected Set<String> getPropertyNames() {
		Set<String> props = new HashSet<>();
		props.add(JRPdfExporter.PDF_FIELD_BORDER_STYLE);
		props.add(JRPdfExporter.PDF_FIELD_CHECK_TYPE);
		props.add(JRPdfExporter.PDF_FIELD_CHECKED);
		props.add(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS);
		props.add(JRPdfExporter.PDF_FIELD_CHOICES);
		props.add(JRPdfExporter.PDF_FIELD_COMBO_EDIT);
		props.add(JRPdfExporter.PDF_FIELD_NAME);
		props.add(JRPdfExporter.PDF_FIELD_READ_ONLY);
		props.add(JRPdfExporter.PDF_FIELD_TEXT_MULTILINE);
		props.add(JRPdfExporter.PDF_FIELD_TYPE);

		return props;
	}

	@Override
	protected String getPropertyValue(String name) {
		return values.get(name);
	}

	@Override
	public void run() {
		FieldDialog dialog = new FieldDialog(UIUtils.getShell());
		if (dialog.open() == Window.OK)
			super.run();
	}

	private class FieldDialog extends ATitledDialog {

		private Combo cType;
		private Text cName;
		private Button cRonly;
		private Combo cBorder;

		private Map<String, Control> controls = new HashMap<>();

		protected FieldDialog(Shell parentShell) {
			super(parentShell);
			setTitle(PdfFieldAction.this.getText());
			setDescription(PdfFieldAction.this.getToolTipText());
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite area = (Composite) super.createDialogArea(parent);
			Composite cmp = new Composite(area, SWT.NONE);
			cmp.setLayout(new GridLayout(4, false));
			cmp.setLayoutData(new GridData(GridData.FILL_BOTH));

			new Label(cmp, SWT.NONE).setText("Name");

			cName = new Text(cmp, SWT.NONE);
			GridData gd = new GridData();
			gd.horizontalSpan = 3;
			gd.widthHint = 300;
			cName.setLayoutData(gd);
			cName.addModifyListener(e -> values.put(JRPdfExporter.PDF_FIELD_NAME, cName.getText()));

			new Label(cmp, SWT.NONE).setText("Border Style");

			cBorder = new Combo(cmp, SWT.READ_ONLY);
			cBorder.setItems(EnumHelper.getEnumNames(PdfFieldBorderStyleEnum.values(), NullEnum.NOTNULL));
			cBorder.addModifyListener(e -> values.put(JRPdfExporter.PDF_FIELD_BORDER_STYLE, cBorder.getText()));

			cRonly = new Button(cmp, SWT.CHECK);
			cRonly.setText("Read Only");
			gd = new GridData();
			gd.horizontalSpan = 2;
			cRonly.setLayoutData(gd);
			cRonly.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					values.put(JRPdfExporter.PDF_FIELD_READ_ONLY, Boolean.toString(cRonly.getSelection()));
				}
			});

			new Label(cmp, SWT.NONE).setText("Field Type");

			cType = new Combo(cmp, SWT.READ_ONLY);
			gd = new GridData();
			gd.horizontalSpan = 3;
			cType.setLayoutData(gd);
			cType.setItems(EnumHelper.getEnumNames(PdfFieldTypeEnum.values(), NullEnum.NOTNULL));

			initWidgets(cmp);
			cType.addModifyListener(e -> {
				String v = cType.getText();
				values.put(JRPdfExporter.PDF_FIELD_TYPE, v);
				changeType(v, cmp);
				UIUtils.getDisplay().asyncExec(() -> UIUtils.relayoutDialogHeight(getShell(), defheight));
			});
			UIUtils.getDisplay().asyncExec(() -> UIUtils.resizeAndCenterShell(getShell(), defwidth, defheight));

			return area;
		}

		private void changeType(String type, Composite cmp) {
			for (Control c : controls.values())
				c.dispose();
			controls.clear();
			switch (PdfFieldTypeEnum.getByName(type)) {
			case TEXT:
				buildText(cmp);
				break;
			case LIST:
			case RADIO:
			case COMBO:
				buildList(cmp);
				break;
			case CHECK:
				buildCheck(cmp);
				break;
			default:
				UIUtils.showInformation("Hmm, type is not supported!");
			}
		}

		private void buildList(Composite cmp) {
			buildLabel(cmp, JRPdfExporter.PDF_FIELD_CHOICES, "Choices");

			Text cChoices = new Text(cmp, SWT.NONE);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 3;
			cChoices.setLayoutData(gd);
			cChoices.addModifyListener(e -> values.put(JRPdfExporter.PDF_FIELD_CHOICES, cChoices.getText()));
			controls.put(JRPdfExporter.PDF_FIELD_CHOICES, cChoices);
			cChoices.setText(Misc.nvl(values.get(JRPdfExporter.PDF_FIELD_CHOICES)));

			buildLabel(cmp, JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS, "Choice Separators");

			Text cChoiceSep = new Text(cmp, SWT.NONE);
			gd = new GridData();
			gd.horizontalSpan = 3;
			cChoiceSep.setLayoutData(gd);
			cChoiceSep.addModifyListener(
					e -> values.put(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS, cChoiceSep.getText()));
			controls.put(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS, cChoiceSep);
			cChoiceSep.setText(Misc.nvl(values.get(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS)));
		}

		private void buildCheck(Composite cmp) {
			buildLabel(cmp, JRPdfExporter.PDF_FIELD_CHECK_TYPE, "Check Type");

			Combo cCheckType = new Combo(cmp, SWT.READ_ONLY);
			cCheckType.setItems(EnumHelper.getEnumNames(PdfFieldCheckTypeEnum.values(), NullEnum.NOTNULL));
			cCheckType.addModifyListener(e -> {
				String v = cCheckType.getText();
				values.put(JRPdfExporter.PDF_FIELD_CHECK_TYPE, v);
			});
			String c = values.get(JRPdfExporter.PDF_FIELD_CHECK_TYPE);
			if (c != null)
				cCheckType.setText(c);
			controls.put(JRPdfExporter.PDF_FIELD_CHECK_TYPE, cCheckType);

			Button cChecked = new Button(cmp, SWT.CHECK);
			cChecked.setText("Checked");
			GridData gd = new GridData();
			gd.horizontalSpan = 2;
			cChecked.setLayoutData(gd);
			cChecked.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					values.put(JRPdfExporter.PDF_FIELD_CHECKED, Boolean.toString(cChecked.getSelection()));
				}
			});
			c = values.get(JRPdfExporter.PDF_FIELD_CHECKED);
			if (c != null)
				cChecked.setSelection(Boolean.parseBoolean(c));
			controls.put(JRPdfExporter.PDF_FIELD_CHECKED, cChecked);
		}

		private void buildText(Composite cmp) {
			Button cMulti = new Button(cmp, SWT.CHECK);
			cMulti.setText("Text Multiline");
			GridData gd = new GridData();
			gd.horizontalSpan = 2;
			cMulti.setLayoutData(gd);
			cMulti.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					values.put(JRPdfExporter.PDF_FIELD_TEXT_MULTILINE, Boolean.toString(cMulti.getSelection()));
				}
			});
			String c = values.get(JRPdfExporter.PDF_FIELD_TEXT_MULTILINE);
			if (c != null)
				cMulti.setSelection(Boolean.parseBoolean(c));
			controls.put(JRPdfExporter.PDF_FIELD_TEXT_MULTILINE, cMulti);
		}

		private void initWidgets(Composite cmp) {
			List<Object> graphicalElements = editor.getSelectionCache().getSelectionModelForType(MGraphicElement.class);
			MGraphicElement m = (MGraphicElement) graphicalElements.get(0);
			JRPropertiesMap v = (JRPropertiesMap) m.getPropertyValue(APropertyNode.PROPERTY_MAP);
			if (v == null)
				v = new JRPropertiesMap();

			String type = v.getProperty(JRPdfExporter.PDF_FIELD_TYPE);
			if (type != null)
				values.put(JRPdfExporter.PDF_FIELD_TYPE, type);
			else
				type = PdfFieldTypeEnum.TEXT.getName();
			cType.setText(type);

			String t = v.getProperty(JRPdfExporter.PDF_FIELD_NAME);
			if (t != null) {
				values.put(JRPdfExporter.PDF_FIELD_NAME, t);
				cName.setText(t);
			}

			t = v.getProperty(JRPdfExporter.PDF_FIELD_READ_ONLY);
			if (t != null) {
				values.put(JRPdfExporter.PDF_FIELD_READ_ONLY, t);
				cRonly.setSelection(Boolean.parseBoolean(t));
			}

			t = v.getProperty(JRPdfExporter.PDF_FIELD_BORDER_STYLE);
			if (t != null) {
				values.put(JRPdfExporter.PDF_FIELD_BORDER_STYLE, t);
				cBorder.setText(t);
			}

			t = v.getProperty(JRPdfExporter.PDF_FIELD_TEXT_MULTILINE);
			if (t != null)
				values.put(JRPdfExporter.PDF_FIELD_TEXT_MULTILINE, t);

			t = v.getProperty(JRPdfExporter.PDF_FIELD_CHECKED);
			if (t != null)
				values.put(JRPdfExporter.PDF_FIELD_CHECKED, t);

			t = v.getProperty(JRPdfExporter.PDF_FIELD_CHECK_TYPE);
			if (t != null)
				values.put(JRPdfExporter.PDF_FIELD_CHECK_TYPE, t);

			changeType(type, cmp);
		}

		private void buildLabel(Composite cmp, String prop, String label) {
			Label lbl = new Label(cmp, SWT.NONE);
			lbl.setText(label);
			controls.put(prop + "label", lbl);
		}

	}

}
