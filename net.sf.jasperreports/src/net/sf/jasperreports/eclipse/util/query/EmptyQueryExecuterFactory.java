/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util.query;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.query.JREmptyQueryExecuter;
import net.sf.jasperreports.engine.query.JRQueryExecuter;
import net.sf.jasperreports.engine.query.QueryExecuterFactory;

/*
 * Empty query executer factory.
 * <p/>
 * The factory creates {@link net.sf.jasperreports.engine.query.JREmptyQueryExecuter JREmptyQueryExecuter}
 * query executers.
 * 
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id$
 */
public class EmptyQueryExecuterFactory implements QueryExecuterFactory {

	public Object[] getBuiltinParameters() {
		return null;
	}

	@Deprecated
	public JRQueryExecuter createQueryExecuter(JRDataset dataset, Map<String, ? extends JRValueParameter> parameters) throws JRException {
		return new JREmptyQueryExecuter();
	}

	public boolean supportsQueryParameterType(String className) {
		return true;
	}

	public JRQueryExecuter createQueryExecuter(JasperReportsContext jasperReportsContext, JRDataset dataset, Map<String, ? extends JRValueParameter> parameters) throws JRException {
		return new JREmptyQueryExecuter();
	}
}
