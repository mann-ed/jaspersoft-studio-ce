/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.server.editor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.Argument;
import com.jaspersoft.studio.editor.preview.view.APreview;
import com.jaspersoft.studio.editor.preview.view.AViewsFactory;
import com.jaspersoft.studio.editor.preview.view.report.file.TextFileViewer;
import com.jaspersoft.studio.editor.preview.view.report.html.ABrowserViewer;
import com.jaspersoft.studio.editor.preview.view.report.swt.SWTViewer;
import com.jaspersoft.studio.server.messages.Messages;

public class ReportUnitViewsFactory extends AViewsFactory {
	public static final String DEFAULT = Argument.RUN_OUTPUT_FORMAT_HTML;

	private static LinkedHashMap<String, Class<? extends APreview>> pcmap = new LinkedHashMap<>();
	private static Map<String,String> menuLabelsMap = new HashMap<>(); 
	
	static {
		pcmap.put(Argument.RUN_OUTPUT_FORMAT_JRPRINT, SWTViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_JRPRINT, Messages.ReportUnitViewsFactory_JrprintLabel);

		pcmap.put("SEPARATOR1", null); //$NON-NLS-1$

		pcmap.put(Argument.RUN_OUTPUT_FORMAT_HTML, ABrowserViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_HTML, Messages.ReportUnitViewsFactory_HtmlLabel);
		
		pcmap.put("SEPARATOR1", null); //$NON-NLS-1$
		
		pcmap.put(Argument.RUN_OUTPUT_FORMAT_PDF, ABrowserViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_PDF, Messages.ReportUnitViewsFactory_PdfLabel);

		pcmap.put("SEPARATOR2", null); //$NON-NLS-1$

		pcmap.put(Argument.RUN_OUTPUT_FORMAT_RTF, ABrowserViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_RTF, Messages.ReportUnitViewsFactory_OdtLabel);
		pcmap.put(Argument.RUN_OUTPUT_FORMAT_DOCX, ABrowserViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_DOCX, Messages.ReportUnitViewsFactory_DocxLabel);
		pcmap.put(Argument.RUN_OUTPUT_FORMAT_PPTX, ABrowserViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_PPTX, Messages.ReportUnitViewsFactory_PowerpointLabel);
		
		pcmap.put("SEPARATOR3", null); //$NON-NLS-1$

		pcmap.put(Argument.RUN_OUTPUT_FORMAT_XLSX, ABrowserViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_XLSX, Messages.ReportUnitViewsFactory_XlsxLabel);
		pcmap.put(Argument.RUN_OUTPUT_FORMAT_DATA_XLSX, ABrowserViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_DATA_XLSX, Messages.ReportUnitViewsFactory_XlsxMetadataLabel);
		pcmap.put(Argument.RUN_OUTPUT_FORMAT_ODS, ABrowserViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_ODS, Messages.ReportUnitViewsFactory_OdsLabel);
		pcmap.put(Argument.RUN_OUTPUT_FORMAT_CSV, TextFileViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_CSV, Messages.ReportUnitViewsFactory_CsvLabel);
		pcmap.put(Argument.RUN_OUTPUT_FORMAT_DATA_CSV, TextFileViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_DATA_CSV, Messages.ReportUnitViewsFactory_CsvMetadataLabel);

		pcmap.put("SEPARATOR4", null); //$NON-NLS-1$

		pcmap.put(Argument.RUN_OUTPUT_FORMAT_XML, TextFileViewer.class);
		menuLabelsMap.put(Argument.RUN_OUTPUT_FORMAT_XML, Messages.ReportUnitViewsFactory_XmlLabel);
	}

	@Override
	public String getLabel(String key) {
		return menuLabelsMap.get(key);
	}

	/**
	 * Return the available keys for the preview area, may contains separator
	 */
	public Set<String> getKeys() {
		return pcmap.keySet();
	}

	@Override
	protected LinkedHashMap<String, Class<? extends APreview>> getMap() {
		return pcmap;
	}

}
