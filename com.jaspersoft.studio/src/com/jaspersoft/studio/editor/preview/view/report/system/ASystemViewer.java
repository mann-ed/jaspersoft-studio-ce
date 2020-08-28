/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.preview.view.report.system;

import java.io.File;

import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;

import com.jaspersoft.studio.editor.preview.actions.export.AExportAction;
import com.jaspersoft.studio.editor.preview.stats.Statistics;
import com.jaspersoft.studio.editor.preview.view.control.ReportController;
import com.jaspersoft.studio.editor.preview.view.report.swt.SWTViewer;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.property.dataset.dialog.DataQueryAdapters;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.engine.JasperPrint;

public abstract class ASystemViewer extends SWTViewer {

	public ASystemViewer(Composite parent, JasperReportsConfiguration jContext) {
		super(parent, jContext);
	}

	protected abstract String getExtension(JasperPrint jrprint);

	@Override
	public void setJRPRint(final Statistics stats, final JasperPrint jrprint, boolean refresh) {
		super.setJRPRint(stats, jrprint, refresh);
		if (jrprint != null) {
			try {
				final String ext = getExtension(jrprint);
				final AExportAction exp = createExporterAction(rptviewer);

				String fname = jrprint.getProperty(DataQueryAdapters.EXPORTER_FILENAME);
				if (Misc.isNullOrEmpty(fname))
					fname = "report";
				final File tmpFile = File.createTempFile(fname, ext); // $NON-NLS-1$
				stats.startCount(ReportController.ST_EXPORTTIME);
				UIUtils.getDisplay().asyncExec(() -> {
					try {
						exp.preview(tmpFile, jrprint, value -> {
							stats.endCount(ReportController.ST_EXPORTTIME);
							stats.setValue(ReportController.ST_REPORTSIZE, tmpFile.length());

							Program p = Program.findProgram(ext);
							if (p != null)
								p.execute(tmpFile.getAbsolutePath());
							else
								// here we can propose a better dialog,
								// like open with...(create association,
								// etc.)
								UIUtils.showWarning(
										String.format(Messages.ASystemViewer_1, ext, tmpFile.getAbsolutePath()));
						});
					} catch (Exception e) {
						UIUtils.showError(e);
					}
				});
			} catch (Exception e) {
				UIUtils.showError(e);
			}
		}
	}
}
