/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.preview.view;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.editor.preview.actions.SwitchViewsAction;
import com.jaspersoft.studio.editor.preview.view.report.file.CSVMetadataViewer;
import com.jaspersoft.studio.editor.preview.view.report.file.CSVViewer;
import com.jaspersoft.studio.editor.preview.view.report.file.JSONMetadataViewer;
import com.jaspersoft.studio.editor.preview.view.report.file.TXTViewer;
import com.jaspersoft.studio.editor.preview.view.report.file.XMLImagesViewer;
import com.jaspersoft.studio.editor.preview.view.report.file.XMLViewer;
import com.jaspersoft.studio.editor.preview.view.report.html.LayeredHTMLViewer;
import com.jaspersoft.studio.editor.preview.view.report.swt.SWTViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.AExporterFactory;
import com.jaspersoft.studio.editor.preview.view.report.system.DocxViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.OdsViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.OdtViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.PdfViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.PowerPointViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.RTFViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.XlsxMetadataViewer;
import com.jaspersoft.studio.editor.preview.view.report.system.XlsxViewer;
import com.jaspersoft.studio.messages.Messages;

public class ViewsFactory extends AViewsFactory {
	public static final String HTML_NO_INTERACTIVITY = Messages.ViewsFactory_HtmlNoInteractiveViewer_LabelKey;
	public static final String VIEWER_JAVA = "Java"; //$NON-NLS-1$
	private static LinkedHashMap<String, Class<? extends APreview>> pcmap = new LinkedHashMap<String, Class<? extends APreview>>();
	static {
		pcmap.put(VIEWER_JAVA, SWTViewer.class);

		pcmap.put("SEPARATOR1", null); //$NON-NLS-1$

		pcmap.put(Messages.ViewsFactory_HtmlViewer_LabelKey, LayeredHTMLViewer.class);
		pcmap.put(Messages.ViewsFactory_PdfViewer_LabelKey, PdfViewer.class);

		pcmap.put("SEPARATOR2", null); //$NON-NLS-1$

		pcmap.put(Messages.ViewsFactory_RtfViewer_LabelKey, RTFViewer.class);
		pcmap.put(Messages.ViewsFactory_DocxViewer_LabelKey, DocxViewer.class);
		pcmap.put(Messages.ViewsFactory_OdtViewer_LabelKey, OdtViewer.class);
		pcmap.put(Messages.ViewsFactory_OdsViewer_LabelKey, OdsViewer.class);
		pcmap.put(Messages.ViewsFactory_PptxViewer_LabelKey, PowerPointViewer.class);
		pcmap.put(Messages.ViewsFactory_TxtViewer_LabelKey, TXTViewer.class);

		pcmap.put("SEPARATOR3", null); //$NON-NLS-1$

		pcmap.put(Messages.ViewsFactory_XlsxViewer_LabelKey, XlsxViewer.class);
		pcmap.put(Messages.ViewsFactory_CsvViewer_LabelKey, CSVViewer.class);
		pcmap.put(Messages.ViewsFactory_XlsxMetadataViewer_LabelKey, XlsxMetadataViewer.class);
		pcmap.put(Messages.ViewsFactory_CsvMetadataViewer_LabelKey, CSVMetadataViewer.class);
		pcmap.put(Messages.ViewsFactory_JsonMetadataViewer_LabelKey, JSONMetadataViewer.class);

		pcmap.put("SEPARATOR4", null); //$NON-NLS-1$

		pcmap.put(Messages.ViewsFactory_XmlViewer_LabelKey, XMLViewer.class);
		pcmap.put(Messages.ViewsFactory_XmlImagesViewer_LabelKey, XMLImagesViewer.class);

		// Load the contributed factories
		int separatorIndex = 5;
		List<AExporterFactory> factories = JaspersoftStudioPlugin.getExtensionManager().getExportersFactories();
		for (AExporterFactory factory : factories) {
			// Check that the name\key is unique
			if (!pcmap.containsKey(factory.getExporterName())) {
				// Check that the key\name and the class are not null
				if (factory.getExporterName() == null || factory.getViewerClass() == null) {
					String currentFactoryClass = pcmap.get(factory.getExporterName()).getName();
					String message = MessageFormat.format(Messages.ViewsFactory_errorExporterNull,
							new Object[] { currentFactoryClass });
					JaspersoftStudioPlugin.getInstance().logWarning(message);
					System.out.println(message);
				} else {
					if (factory.isSeparatorPlacedBefore()) {
						pcmap.put(SwitchViewsAction.SEPARATOR + separatorIndex, null);
						separatorIndex++;
					}
					pcmap.put(factory.getExporterName(), factory.getViewerClass());
				}
			} else {
				String currentFactoryClass = pcmap.get(factory.getExporterName()).getName();
				String message = MessageFormat.format(Messages.ViewsFactory_errorExporterDuplicated,
						new Object[] { factory.getExporterName(), currentFactoryClass });
				JaspersoftStudioPlugin.getInstance().logWarning(message);
				System.out.println(message);
			}
		}
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
