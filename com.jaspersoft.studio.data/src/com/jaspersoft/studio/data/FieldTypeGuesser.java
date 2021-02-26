/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.eclipse.core.runtime.IProgressMonitor;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.AbstractPoiXlsDataSource;
import net.sf.jasperreports.engine.design.JRDesignField;

public class FieldTypeGuesser {
	public static final int SAMPLESIZE = 1000;
	private static final Class<?>[] types = new Class<?>[] { Date.class, Time.class, Timestamp.class, Integer.class,
			Long.class, BigInteger.class, BigDecimal.class };

	public static void guessTypes(JRDataSource ds, List<JRDesignField> columns, boolean hasNext,
			IProgressMonitor monitor) throws JRException {
		List<JRDesignField> cols = new CopyOnWriteArrayList<>(columns);
		for (int i = 0; i < SAMPLESIZE && hasNext && !cols.isEmpty(); i++) {
			if (monitor != null && monitor.isCanceled())
				return;
			for (JRDesignField f : cols) {
				try {
					Object v = ds.getFieldValue(f);
					if (v == null)
						continue;
					if (f.getValueClass().equals(String.class)) {
						String sv = (String) v;
						if (sv.trim().isEmpty())
							continue;
						findType(types, f, ds, cols, sv);
					}
				} catch (Throwable e1) {
					f.setValueClass(String.class);
					cols.remove(f);
				}
			}
			hasNext = ds.next();
		}
	}
	
	/** 
	 * Dedicated type guessing method for the data sources related to Excel.
	 * <p>
	 * We try to base our detection on the Data Format information set on the cell
	 * and rely on some cases also on the content.<br/>
	 * We do not perform a full deep reading of all the rows, because it would be 
	 * too expensive. We stop once we find a "valid" proposal for the type other than 
	 * the usual fallback String one.
	 * </p>
	 * 
	 * @param xlsDS the Excel type data source
	 * @param columns the list of columns
	 * @param monitor the progress monitor
	 * @throws JRException
	 * 
	 * @see {@link BuiltinFormats}
	 */
	public static void guessTypes(AbstractPoiXlsDataSource xlsDS, List<JRDesignField> columns, IProgressMonitor monitor) throws JRException {
		List<JRDesignField> cols = new CopyOnWriteArrayList<>(columns);
		boolean hasNext = true;
		for(int i=0;i<SAMPLESIZE && hasNext && !cols.isEmpty();i++) {
			if (monitor != null && monitor.isCanceled()) {
				return;
			}
			for(JRDesignField f : cols) {
				try {
					// Let's get the possible data format pattern
					// see org.apache.poi.ss.usermodel.BuiltinFormats
					// Fallback to 0, "General"
					String dataFormat = xlsDS.getFieldFormatPattern(f);
					int dataFormatID = (dataFormat!=null) ? BuiltinFormats.getBuiltinFormat(dataFormat) : 0;
					BuiltinFormats.getBuiltinFormat(dataFormat);
					// Let's get the string from excel and let's try to guess
					String val = xlsDS.getStringFieldValue(f);
					if(!StringUtils.isNotEmpty(val)) {
						continue;
					}
					if(NumberUtils.isParsable(val)) {
						if(dataFormatID==1) {
							f.setValueClass(Long.class);	
						}
						else {
							f.setValueClass(Double.class);
						}
						cols.remove(f);
					}
					else {
						// try handle boolean case
						Boolean boolVal = BooleanUtils.toBooleanObject(val);
						if(boolVal!=null) {
							f.setValueClass(Boolean.class);
							cols.remove(f);
						}
						else {
							// let's try some stuff for the dates
							boolean isDate = false;
							if(dataFormatID >= 0x0E && dataFormatID <= 0x16) {
								isDate=true;
							}
							if(!isDate && xlsDS.getDateFormat()!=null) {
								xlsDS.getDateFormat().parse(val);
								isDate = true;
							}
							if(!isDate && dataFormat!=null) {
								DateUtils.parseDate(val, dataFormat);
								isDate = true;								
							}
							if(isDate) {
								f.setValueClass(java.util.Date.class);
								cols.remove(f);
							}
						}
					}										
				} catch (JRException | ParseException e) {
					f.setValueClass(String.class);
					cols.remove(f);
				}
			}
			hasNext = xlsDS.next();
		}
	}

	private static void findType(Class<?>[] types, JRDesignField f, JRDataSource ds, List<JRDesignField> cols,
			String sv) {
		for (Class<?> type : types)
			try {
				f.setValueClass(type);
				Object v = ds.getFieldValue(f);
				if (v == null)
					f.setValueClass(String.class);
				else {
					if (v instanceof Number) {
						if (!sv.matches("^[+-]?(\\d+(\\.\\d*)?)$")) {
							f.setValueClass(String.class);
							continue;
						} else if (sv.contains(".") && !type.equals(BigDecimal.class))
							continue;
					} else if (v instanceof Date && sv.contains(":"))
						continue;
					else if (v instanceof Time && (sv.contains("-") || sv.contains("//")))
						continue;
				}
				return;
			} catch (Throwable e) {
				f.setValueClass(String.class);
			}
		cols.remove(f);
	}
}
