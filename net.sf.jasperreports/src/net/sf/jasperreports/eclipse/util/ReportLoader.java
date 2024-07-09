/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util;

import java.io.InputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

/*
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JasperDesignPreviewView.java 27 2009-11-11 12:40:27Z teodord $
 */
public final class ReportLoader {

	public static JRReport loadReport(IFile file) throws JRException {
		JRReport report = null;
		InputStream is = null;
		try {
			is = file.getContents();
			if (FileExtension.JRXML.equalsIgnoreCase(file.getFileExtension())) {
				report = JRXmlLoader.load(is);
			} else if (FileExtension.JASPER.equalsIgnoreCase(file.getFileExtension())) {
				report = (JRReport) JRLoader.loadObject(is);
			}
		} catch (CoreException e) {
			throw new JRException(e);
		} finally {
			FileUtils.closeStream(is);
		}

		return report;
	}

}
