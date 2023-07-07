/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.preview.view.report.file;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.jaspersoft.studio.editor.preview.actions.export.AExportAction;
import com.jaspersoft.studio.editor.preview.actions.export.ExportAsTextAction;
import com.jaspersoft.studio.preferences.exporter.TextExporterPreferencePage;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.viewer.ReportViewer;
import net.sf.jasperreports.engine.JasperPrint;

public class TXTViewer extends AFileViewer {
	public TXTViewer(Composite parent, JasperReportsConfiguration jContext) {
		super(parent, jContext);
	}

	@Override
	protected AExportAction createExporter(ReportViewer rptv) {
		return new ExportAsTextAction(rptv, jContext, null);
	}
	
	@Override
	protected Control createControl(Composite parent) {
		Control control = super.createControl(parent);
		// try to get a Monospace font
		getTextControl().setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
		return control;
	}

	@Override
	protected String getExtension() {
		return ".txt";
	}

	@Override
	public void pageGenerated(JasperPrint arg0, int arg1) {

	}

	@Override
	public void pageUpdated(JasperPrint arg0, int arg1) {

	}

	@Override
	public PreferencePage getPreferencePage() {
		return new TextExporterPreferencePage();
	}
}
