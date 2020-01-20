package com.jaspersoft.studio.editor.action.pdf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.WordUtils;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.MGraphicElement;
import com.jaspersoft.studio.model.text.MTextElement;
import com.jaspersoft.studio.property.dataset.fields.table.TColumn;
import com.jaspersoft.studio.property.dataset.fields.table.TColumnFactory;
import com.jaspersoft.studio.property.dataset.fields.table.widget.AWidget;
import com.jaspersoft.studio.property.dataset.fields.table.widget.WJRProperty;
import com.jaspersoft.studio.property.descriptor.propexpr.dialog.HintsPropertiesList;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.ui.ATitledDialog;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRPropertyExpression;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.export.JRPdfExporter;
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

	private static Set<String> p = new HashSet<>();
	static {
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
		p.add(JRPdfExporter.PDF_FIELD_VALUE);
	}

	@Override
	protected Set<String> getPropertyNames() {
		return p;
	}

	@Override
	protected Object getPropertyValue(String name) {
		if (dialog != null && dialog.getValue() != null) {
			JRDesignElement dv = dialog.getValue();
			if (dv.getPropertiesMap() != null) {
				String v = dv.getPropertiesMap().getProperty(name);
				if (v != null)
					return v;
			}
			if (dv.getPropertyExpressionsList() != null) {
				for (JRPropertyExpression pe : dv.getPropertyExpressionsList()) {
					if (pe.getName().equals(name))
						return pe;
				}
			}
		}
		return null;
	}

	private FieldDialog dialog;

	@Override
	public void run() {
		dialog = new FieldDialog(UIUtils.getShell());
		if (dialog.open() == Window.OK)
			super.run();
	}

	private class FieldDialog extends ATitledDialog {

		private MGraphicElement m;
		private JRDesignElement eClone;
		private JasperReportsConfiguration jConf;

		private Map<String, WJRProperty> controls = new HashMap<>();

		protected FieldDialog(Shell parentShell) {
			super(parentShell, false);
			setTitle(PdfFieldAction.this.getText());
			setDescription(PdfFieldAction.this.getToolTipText());
			List<Object> graphicalElements = editor.getSelectionCache().getSelectionModelForType(MGraphicElement.class);
			m = (MGraphicElement) graphicalElements.get(0);
			eClone = (JRDesignElement) m.getValue().clone();
			jConf = m.getJasperConfiguration();
			if (props.isEmpty()) {
				List<PropertyMetadata> pms = HintsPropertiesList.getPropertiesMetadata(m.getValue(),
						m.getJasperConfiguration());
				pms.stream().filter(pm -> getPropertyNames().contains(pm.getName()))
						.forEach(pm -> props.put(pm.getName(), pm));
			}
		}

		public JRDesignElement getValue() {
			return eClone;
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite area = (Composite) super.createDialogArea(parent);
			Composite cmp = new Composite(area, SWT.NONE);
			cmp.setLayout(new GridLayout(2, false));
			cmp.setLayoutData(new GridData(GridData.FILL_BOTH));

			TColumn tc = TColumnFactory.getTColumn(props.get(JRPdfExporter.PDF_FIELD_NAME));
			tc.setLabel(Messages.PdfFieldAction_3);
			Control lbl = ((WJRProperty) TColumnFactory.addWidget(tc, cmp, eClone, jConf)).getLabel();
			if (lbl != null) {
				GridData gd = new GridData();
				gd.widthHint = 150;
				lbl.setLayoutData(gd);
			}

			tc = TColumnFactory.getTColumn(props.get(JRPdfExporter.PDF_FIELD_BORDER_STYLE));
			tc.setLabel(Messages.PdfFieldAction_4);
			TColumnFactory.addWidget(tc, cmp, eClone, jConf);

			tc = TColumnFactory.getTColumn(props.get(JRPdfExporter.PDF_FIELD_READ_ONLY));
			tc.setLabel(Messages.PdfFieldAction_5);
			TColumnFactory.addWidget(tc, cmp, eClone, jConf);

			tc = TColumnFactory.getTColumn(props.get(JRPdfExporter.PDF_FIELD_TYPE));
			if (!(m instanceof MTextElement)) {
				Set<PdfFieldTypeEnum> hev = new HashSet<>();
				hev.add(PdfFieldTypeEnum.TEXT);
				tc.setHideEnumValues(hev);
			}

			tc.setLabel(Messages.PdfFieldAction_6);
			TColumnFactory.addWidget(tc, cmp, eClone, jConf);

			final JRPropertiesMap v = eClone.getPropertiesMap();
			v.getEventSupport().addPropertyChangeListener(JRPdfExporter.PDF_FIELD_TYPE, evt -> {
				String t = getType(v);
				if (!(m instanceof MTextElement) && PdfFieldTypeEnum.TEXT.getName().equalsIgnoreCase(t)) {
					UIUtils.showInformation("You can use Text type only with TextField and StaticText");
					return;
				}
				changeType(t, cmp);
				cmp.update();
				cmp.layout(true);
				UIUtils.getDisplay().asyncExec(() -> UIUtils.relayoutDialogHeight(getShell(), defheight));
			});

			changeType(getType(v), cmp);

			return area;
		}

		private String getType(JRPropertiesMap v) {
			String type = v.getProperty(JRPdfExporter.PDF_FIELD_TYPE);
			if (type != null)
				values.put(JRPdfExporter.PDF_FIELD_TYPE, type);
			return type;
		}

		private void changeType(String type, Composite cmp) {
			for (WJRProperty c : controls.values())
				c.dispose();
			controls.clear();
			JRPropertiesMap pm = eClone.getPropertiesMap();
			if (type != null) {
				switch (PdfFieldTypeEnum.getByName(WordUtils.capitalize(type.toLowerCase()))) {
				case TEXT:
					pm.removeProperty(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS);
					pm.removeProperty(JRPdfExporter.PDF_FIELD_CHOICES);
					pm.removeProperty(JRPdfExporter.PDF_FIELD_CHECKED);
					pm.removeProperty(JRPdfExporter.PDF_FIELD_CHECK_TYPE);
					eClone.removePropertyExpression(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS);
					eClone.removePropertyExpression(JRPdfExporter.PDF_FIELD_CHOICES);
					eClone.removePropertyExpression(JRPdfExporter.PDF_FIELD_CHECKED);
					eClone.removePropertyExpression(JRPdfExporter.PDF_FIELD_CHECK_TYPE);
					buildText(cmp);
					break;
				case LIST:
				case COMBO:
					pm.removeProperty(JRPdfExporter.PDF_FIELD_CHECKED);
					pm.removeProperty(JRPdfExporter.PDF_FIELD_CHECK_TYPE);
					pm.removeProperty(JRPdfExporter.PDF_FIELD_TEXT_MULTILINE);
					eClone.removePropertyExpression(JRPdfExporter.PDF_FIELD_CHECKED);
					eClone.removePropertyExpression(JRPdfExporter.PDF_FIELD_CHECK_TYPE);
					eClone.removePropertyExpression(JRPdfExporter.PDF_FIELD_TEXT_MULTILINE);
					buildList(cmp);
					break;
				case RADIO:
				case CHECK:
					pm.removeProperty(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS);
					pm.removeProperty(JRPdfExporter.PDF_FIELD_CHOICES);
					pm.removeProperty(JRPdfExporter.PDF_FIELD_TEXT_MULTILINE);
					pm.removeProperty(JRPdfExporter.PDF_FIELD_VALUE);
					eClone.removePropertyExpression(JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS);
					eClone.removePropertyExpression(JRPdfExporter.PDF_FIELD_CHOICES);
					eClone.removePropertyExpression(JRPdfExporter.PDF_FIELD_TEXT_MULTILINE);
					eClone.removePropertyExpression(JRPdfExporter.PDF_FIELD_VALUE);
					buildCheck(cmp);
					break;
				default:
					UIUtils.showInformation(Messages.PdfFieldAction_7);
				}
			}
		}

		private void buildList(Composite cmp) {
			buildProp(cmp, JRPdfExporter.PDF_FIELD_CHOICES, Messages.PdfFieldAction_8);
			buildProp(cmp, JRPdfExporter.PDF_FIELD_VALUE, "Selected Choice Value");
			buildProp(cmp, JRPdfExporter.PDF_FIELD_CHOICE_SEPARATORS, Messages.PdfFieldAction_9);
		}

		private void buildCheck(Composite cmp) {
			buildProp(cmp, JRPdfExporter.PDF_FIELD_CHECK_TYPE, Messages.PdfFieldAction_10);
			buildProp(cmp, JRPdfExporter.PDF_FIELD_CHECKED, Messages.PdfFieldAction_11);
		}

		private void buildText(Composite cmp) {
			buildProp(cmp, JRPdfExporter.PDF_FIELD_VALUE, "Selected Choice Value");
			buildProp(cmp, JRPdfExporter.PDF_FIELD_TEXT_MULTILINE, Messages.PdfFieldAction_12);
		}

		private void buildProp(Composite cmp, String p, String label) {
			TColumn tc = TColumnFactory.getTColumn(props.get(p));
			if (label != null)
				tc.setLabel(label);
			AWidget w = TColumnFactory.addWidget(tc, cmp, eClone, jConf);
			if (w instanceof WJRProperty)
				controls.put(p, (WJRProperty) w);
		}

	}

}
