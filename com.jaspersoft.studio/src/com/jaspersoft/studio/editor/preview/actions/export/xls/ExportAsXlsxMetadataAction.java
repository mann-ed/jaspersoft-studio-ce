/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.preview.actions.export.xls;

import java.io.File;

import com.jaspersoft.studio.editor.preview.actions.export.AExportAction;
import com.jaspersoft.studio.editor.preview.actions.export.ExportMenuAction;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.viewer.IReportViewer;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.export.JRExportProgressMonitor;
import net.sf.jasperreports.engine.export.ooxml.XlsxMetadataExporter;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxMetadataReportConfiguration;

/** 
 * Export action for the XLSX metadata format.
 * 
 * @author Massimo Rabbi (mrabbi@tibco.com)
 *
 */
public class ExportAsXlsxMetadataAction extends AExportAction {

	public ExportAsXlsxMetadataAction(IReportViewer viewer, JasperReportsConfiguration jContext, ExportMenuAction parentMenu) {
		super(viewer, jContext, parentMenu);

		setText(Messages.ExportAsXlsxMetadataAction_Text);
		setToolTipText(Messages.ExportAsXlsxMetadataAction_Tooltip);

		setFileExtensions(new String[] { "*.xlsx" }); //$NON-NLS-1$
		setFilterNames(new String[] {Messages.ExportAsXlsxMetadataAction_FilterNameXLSX});
		setDefaultFileExtension("xlsx"); //$NON-NLS-1$
	}

	@Override
	protected JRAbstractExporter<?, ?, ?, ?> getExporter(JasperReportsConfiguration jContext,
			JRExportProgressMonitor monitor, File file) {
		XlsxMetadataExporter exp = new XlsxMetadataExporter(jContext);
		SimpleXlsxMetadataReportConfiguration rconf = new SimpleXlsxMetadataReportConfiguration();
		setupReportConfiguration(rconf, monitor);
		exp.setConfiguration(rconf);
		exp.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
		return exp;
	}
}
