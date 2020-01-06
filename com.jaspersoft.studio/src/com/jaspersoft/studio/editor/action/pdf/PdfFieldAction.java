package com.jaspersoft.studio.editor.action.pdf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.APropertyNode;
import com.jaspersoft.studio.model.MGraphicElement;
import com.jaspersoft.studio.model.text.MTextElement;
import com.jaspersoft.studio.property.descriptor.NullEnum;
import com.jaspersoft.studio.property.descriptor.propexpr.dialog.HintsPropertiesList;
import com.jaspersoft.studio.utils.EnumHelper;

import net.sf.jasperreports.eclipse.ui.ATitledDialog;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.type.PdfFieldBorderStyleEnum;
import net.sf.jasperreports.engine.export.type.PdfFieldCheckTypeEnum;
import net.sf.jasperreports.engine.export.type.PdfFieldTypeEnum;
import net.sf.jasperreports.properties.PropertyMetadata;

public class PdfFieldAction extends APdfAction {

	public static final String ID_PDF_FIELD_ACTION = "ID_PDF_FIELD_ACTION"; //$NON-NLS-1$
	private static final Map<String, PropertyMetadata> props = new HashMap<>();

	private Map<String, String> values = new HashMap<>();

	public PdfFieldAction(IWorkbenchPart part) {
		super(part);
	}

	@Override
	protected void initUI() {
		setId(ID_PDF_FIELD_ACTION);
		setText(Messages.PdfFieldAction_1);
		setToolTipText(Messages.PdfFieldAction_2);
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
		Set<String> p = new HashSet<>();
		p.add(JRPdfExporter.PDF_FIELD_BORDER_STYLE);
		p.add(JRPdfExporter.PDF_FIELD_CHECK_TYPE);
		p.add(JRPdfExporter.PDF_FIELD_CHECKED);
		p.add(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS);
		p.add(JRPdfExporter.PDF_FIELD_CHOICES);
		p.add(JRPdfExporter.PDF_FIELD_COMBO_EDIT);
		p.add(JRPdfExporter.PDF_FIELD_NAME);
		p.add(JRPdfExporter.PDF_FIELD_READ_ONLY);
		p.add(JRPdfExporter.PDF_FIELD_TEXT_MULTILINE);
		p.add(JRPdfExporter.PDF_FIELD_TYPE);
		return p;
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
		private MGraphicElement m;

		private Map<String, Control> controls = new HashMap<>();

		protected FieldDialog(Shell parentShell) {
			super(parentShell);
			defwidth = 450;
			defheight = 300;
			setTitle(PdfFieldAction.this.getText());
			setDescription(PdfFieldAction.this.getToolTipText());
			List<Object> graphicalElements = editor.getSelectionCache().getSelectionModelForType(MGraphicElement.class);
			m = (MGraphicElement) graphicalElements.get(0);
			if (props.isEmpty()) {
				List<PropertyMetadata> pms = HintsPropertiesList.getPropertiesMetadata(m.getValue(),
						m.getJasperConfiguration());
				pms.stream().filter(pm -> getPropertyNames().contains(pm.getName()))
						.forEach(pm -> props.put(pm.getName(), pm));
			}
		}

		@Override
		protected boolean isResizable() {
			return false;
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite area = (Composite) super.createDialogArea(parent);
			Composite cmp = new Composite(area, SWT.NONE);
			cmp.setLayout(new GridLayout(4, false));
			cmp.setLayoutData(new GridData(GridData.FILL_BOTH));

			Label lbl = buildLabel(cmp, JRPdfExporter.PDF_FIELD_NAME, Messages.PdfFieldAction_3);
			GridData gd = new GridData();
			gd.widthHint = 150;
			lbl.setLayoutData(gd);

			cName = new Text(cmp, SWT.NONE);
			cName.setToolTipText(lbl.getToolTipText());
			gd = new GridData();
			gd.horizontalSpan = 3;
			gd.widthHint = 300;
			cName.setLayoutData(gd);
			cName.addModifyListener(e -> values.put(JRPdfExporter.PDF_FIELD_NAME, cName.getText()));

			lbl = buildLabel(cmp, JRPdfExporter.PDF_FIELD_BORDER_STYLE, Messages.PdfFieldAction_4);

			cBorder = new Combo(cmp, SWT.READ_ONLY);
			cBorder.setToolTipText(lbl.getToolTipText());
			cBorder.setItems(EnumHelper.getEnumNames(PdfFieldBorderStyleEnum.values(), NullEnum.NOTNULL));
			cBorder.addModifyListener(e -> values.put(JRPdfExporter.PDF_FIELD_BORDER_STYLE, cBorder.getText()));

			cRonly = buildButton(cmp, JRPdfExporter.PDF_FIELD_READ_ONLY, Messages.PdfFieldAction_5, SWT.CHECK);
			gd = new GridData();
			gd.horizontalSpan = 2;
			cRonly.setLayoutData(gd);
			cRonly.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					values.put(JRPdfExporter.PDF_FIELD_READ_ONLY, Boolean.toString(cRonly.getSelection()));
				}
			});

			lbl = buildLabel(cmp, JRPdfExporter.PDF_FIELD_TYPE, Messages.PdfFieldAction_6);

			cType = new Combo(cmp, SWT.READ_ONLY);
			cType.setToolTipText(lbl.getToolTipText());
			gd = new GridData();
			gd.horizontalSpan = 3;
			cType.setLayoutData(gd);
			String[] types = EnumHelper.getEnumNames(PdfFieldTypeEnum.values(), NullEnum.NOTNULL);
			if (!(m instanceof MTextElement))
				types = (String[]) ArrayUtils.removeElement(types, PdfFieldTypeEnum.TEXT.getName());
			cType.setItems(types);

			initWidgets(cmp);

			ModifyListener ml = new ModifyListener() {

				@Override
				public void modifyText(ModifyEvent e) {
					String v = cType.getText();
					if (!(m instanceof MTextElement) && v.equals(PdfFieldTypeEnum.TEXT.getName())) {
						UIUtils.showInformation("You can use Text type only with TextField and StaticText");
						cType.removeModifyListener(this);
						cType.setText(values.get(JRPdfExporter.PDF_FIELD_TYPE));
						cType.addModifyListener(this);
						return;
					}
					values.put(JRPdfExporter.PDF_FIELD_TYPE, v);
					changeType(v, cmp);
					UIUtils.getDisplay().asyncExec(() -> UIUtils.relayoutDialogHeight(getShell(), defheight));
				}
			};

			cType.addModifyListener(ml);
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
			case COMBO:
				buildList(cmp);
				break;
			case RADIO:
				buildList(cmp);
				buildCheck(cmp);
				break;
			case CHECK:
				buildCheck(cmp);
				break;
			default:
				UIUtils.showInformation(Messages.PdfFieldAction_7);
			}
		}

		private void buildList(Composite cmp) {
			Label lbl = buildDynamicLabel(cmp, JRPdfExporter.PDF_FIELD_CHOICES, Messages.PdfFieldAction_8);

			Text cChoices = new Text(cmp, SWT.NONE);
			cChoices.setToolTipText(lbl.getToolTipText());
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 3;
			cChoices.setLayoutData(gd);
			cChoices.addModifyListener(e -> values.put(JRPdfExporter.PDF_FIELD_CHOICES, cChoices.getText()));
			controls.put(JRPdfExporter.PDF_FIELD_CHOICES, cChoices);
			cChoices.setText(Misc.nvl(values.get(JRPdfExporter.PDF_FIELD_CHOICES)));

			lbl = buildDynamicLabel(cmp, JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS, Messages.PdfFieldAction_9);

			Text cChoiceSep = new Text(cmp, SWT.NONE);
			cChoiceSep.setToolTipText(lbl.getToolTipText());
			gd = new GridData();
			gd.horizontalSpan = 3;
			cChoiceSep.setLayoutData(gd);
			cChoiceSep.addModifyListener(
					e -> values.put(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS, cChoiceSep.getText()));
			controls.put(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS, cChoiceSep);
			cChoiceSep.setText(Misc.nvl(values.get(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS)));
		}

		private void buildCheck(Composite cmp) {
			Label lbl = buildDynamicLabel(cmp, JRPdfExporter.PDF_FIELD_CHECK_TYPE, Messages.PdfFieldAction_10);

			Combo cCheckType = new Combo(cmp, SWT.READ_ONLY);
			cCheckType.setToolTipText(lbl.getToolTipText());
			cCheckType.setItems(EnumHelper.getEnumNames(PdfFieldCheckTypeEnum.values(), NullEnum.NOTNULL));
			cCheckType.addModifyListener(e -> {
				String v = cCheckType.getText();
				values.put(JRPdfExporter.PDF_FIELD_CHECK_TYPE, v);
			});
			String c = values.get(JRPdfExporter.PDF_FIELD_CHECK_TYPE);
			if (c != null)
				cCheckType.setText(c);
			controls.put(JRPdfExporter.PDF_FIELD_CHECK_TYPE, cCheckType);

			Button cChecked = buildButton(cmp, JRPdfExporter.PDF_FIELD_CHECKED, Messages.PdfFieldAction_11, SWT.CHECK);
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
			Button cMulti = buildButton(cmp, JRPdfExporter.PDF_FIELD_TEXT_MULTILINE, Messages.PdfFieldAction_12,
					SWT.CHECK);
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

			JRPropertiesMap v = (JRPropertiesMap) m.getPropertyValue(APropertyNode.PROPERTY_MAP);
			if (v == null)
				v = new JRPropertiesMap();

			String type = v.getProperty(JRPdfExporter.PDF_FIELD_TYPE);
			if (type != null)
				values.put(JRPdfExporter.PDF_FIELD_TYPE, type);
			else
				type = PdfFieldTypeEnum.CHECK.getName();
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

		private Label buildDynamicLabel(Composite cmp, String prop, String label) {
			Label lbl = buildLabel(cmp, prop, label);
			controls.put(prop + "label", lbl); //$NON-NLS-1$
			return lbl;
		}

		private Label buildLabel(Composite cmp, String prop, String label) {
			Label lbl = new Label(cmp, SWT.NONE);
			PropertyMetadata pm = props.get(prop);
			lbl.setText(Misc.nvl(pm.getLabel(), label));
			lbl.setToolTipText(Misc.nvl(pm.getDescription()));
			return lbl;
		}

		private Button buildButton(Composite cmp, String prop, String label, int style) {
			Button b = new Button(cmp, style);
			PropertyMetadata pm = props.get(prop);
			b.setText(Misc.nvl(pm.getLabel(), label));
			b.setToolTipText(Misc.nvl(pm.getDescription()));
			return b;
		}

	}

}
