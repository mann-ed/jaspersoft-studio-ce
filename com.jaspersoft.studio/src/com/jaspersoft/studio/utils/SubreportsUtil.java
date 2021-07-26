/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.builder.jdt.JDTUtils;
import net.sf.jasperreports.eclipse.util.FileExtension;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.eclipse.util.StringUtils;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRExpressionUtil;

public class SubreportsUtil {
	
	private SubreportsUtil() {
	}
	
	public static Map<File, IFile> getSubreportFiles(JasperReportsConfiguration jConfig, IFile file, JasperDesign jd,
			IProgressMonitor monitor) {
		Map<File, IFile> fmap = new HashMap<>();
		try {
			List<JRDesignElement> elements = ModelUtils.getAllElements(jd);
			SubMonitor submon = SubMonitor.convert(monitor, elements.size());
			for (JRDesignElement ele : elements) {
				if (ele instanceof JRDesignSubreport)
					addSubreport(jConfig, fmap, submon.split(1), file, jd, (JRDesignSubreport) ele);
			}
		} finally {
			jConfig.init(file);
		}
		return fmap;
	}
	
	/*
	 * Tries to get the file related to the subreport expression.
	 * Simple mode on: you try to locate the file using the simple text expression information
	 * Simple mode off: you try to use a "best-effort" expression evaluation.
	 * 
	 * NOTE: Pay attention that using an Expression evaluation (with proper interpreter) is expensive.
	 * In many cases a "simple" expression referencing the file will be used, maybe with an absolute
	 * or relative path. No need to overkill the computation using the (bsh) interpreter.
	 */
	private static File getSubreportFileItem(
			IFile file, JRExpression expression, JasperReportsConfiguration jConfig, JasperDesign parent, boolean simpleMode) {
		String expr = simpleMode ? JRExpressionUtil.getSimpleExpressionText(expression) : ExpressionUtil.eval(expression, jConfig, parent);
		if (expr == null || expr.isEmpty()) {
			return null;
		}
		if (expr.endsWith(FileExtension.PointJASPER)) {
			expr = StringUtils.replaceAllIns(expr, FileExtension.PointJASPER + "$", FileExtension.PointJRXML);
		}
		expr = expr.replaceFirst("repo:", "");
		File f = FileUtils.findFile(file, expr);
		if(f==null) {
			try {
				f = fallbackFindFile(file, expr);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return f;
	}

	private static void addSubreport(
			JasperReportsConfiguration jConfig, Map<File, IFile> fmap,
			IProgressMonitor monitor, IFile file, JasperDesign parent, JRDesignSubreport ele) {
		jConfig.init(file);
		JRExpression expression = ele.getExpression();
		SubMonitor submon = SubMonitor.convert(monitor, 100);
		// first try the quicker simple mode to get the subreport file
		File f = getSubreportFileItem(file, expression, jConfig, parent, true);
		if(f==null) {
			f = getSubreportFileItem(file, expression, jConfig, parent, false);
			if (f == null) {
				return;
			}
		}
		submon.setWorkRemaining(10);
		if (fmap.containsKey(f)) {
			return;
		}
		if (f != null && f.exists()) {
			IFile[] fs = JDTUtils.WS_ROOT.findFilesForLocationURI(f.toURI());
			if (fs != null && fs.length > 0) {
				IFile ifile = fs[0];
				fmap.put(f, ifile);
				try {
					JasperDesign jd = JRXMLUtils.getJasperDesign(jConfig, ifile.getContents(), ifile.getFileExtension());
					if (jd != null) {
						for (JRDesignElement el : ModelUtils.getAllElements(jd)) {
							if (el instanceof JRDesignSubreport) {
								addSubreport(jConfig, fmap, monitor, ifile, jd, (JRDesignSubreport) el);
							}
							if (monitor.isCanceled()) {
								break;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				fmap.put(f, null);
			}
		}
		submon.setWorkRemaining(0);
	}

	private static File fallbackFindFile(IFile file, String expression) {
		File f;
		try {
			f = new File(expression);
		} catch (IllegalArgumentException e) {
			f = new File(file.getRawLocationURI().getPath(), expression);
		}
		return f;
	}

}
