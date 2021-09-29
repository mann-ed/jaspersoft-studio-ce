/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.data.jdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.jaspersoft.studio.data.fields.IFieldsProvider;
import com.jaspersoft.studio.property.dataset.dialog.DataQueryAdapters;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;
import com.jaspersoft.studio.utils.parameter.ParameterUtil;

import net.sf.jasperreports.data.DataAdapterService;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.eclipse.util.StringUtils;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.query.JRJdbcQueryExecuter;
import net.sf.jasperreports.engine.query.JRJdbcQueryExecuterFactory;
import net.sf.jasperreports.engine.query.JRQueryExecuter;
import net.sf.jasperreports.engine.query.QueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRQueryExecuterUtils;

public class JDBCFieldsProvider implements IFieldsProvider {

	public boolean supportsGetFieldsOperation(JasperReportsConfiguration jConfig, JRDataset jDataset) {
		return true;
	}

	public List<JRDesignField> getFields(DataAdapterService con, JasperReportsConfiguration jConfig, JRDataset jDataset)
			throws JRException, UnsupportedOperationException {
		Connection c = null;
		List<JRDesignField> columns = null;
		Map<String, Object> parameters = new HashMap<>();
		con.contributeParameters(parameters);
		try {
			c = (Connection) parameters.get(JRParameter.REPORT_CONNECTION);

			ParameterUtil.setParameters(jConfig, jDataset, parameters);
			parameters.put(JRJdbcQueryExecuterFactory.PROPERTY_JDBC_FETCH_SIZE, 0);
			parameters.put(JRParameter.REPORT_MAX_COUNT, 1);

			// JasperReports query executer instances require
			// REPORT_PARAMETERS_MAP parameter to be defined and not null
			Map<String, JRValueParameter> tmpMap = ParameterUtil.convertMap(parameters, jDataset);

			QueryExecuterFactory queryExecuterFactory = JRQueryExecuterUtils.getInstance(jConfig)
					.getExecuterFactory(jDataset.getQuery().getLanguage());
			JRQueryExecuter qe = queryExecuterFactory.createQueryExecuter(jConfig, jDataset, tmpMap);
			qe.createDatasource();
			if (qe instanceof JRJdbcQueryExecuter) {
				ResultSet rs = ((JRJdbcQueryExecuter) qe).getResultSet();
				if (rs != null) {
					ResultSetMetaData metaData = rs.getMetaData();
					int cc = metaData.getColumnCount();
					Set<String> colset = new HashSet<>();
					columns = new ArrayList<>(cc);
					String driverName = c.getMetaData().getDriverName().toLowerCase();
					for (int i = 1; i <= cc; i++) {
						JRDesignField field = new JRDesignField();
						String name = metaData.getColumnLabel(i);
						field.getPropertiesMap().setProperty(DataQueryAdapters.FIELD_NAME, metaData.getColumnName(i));
						field.getPropertiesMap().setProperty(DataQueryAdapters.FIELD_LABEL, name);
						if (colset.contains(name))
							name = JRResultSetDataSource.INDEXED_COLUMN_PREFIX + i;
						colset.add(name);

						field.setName(StringUtils.xmlEncode(name, null));

						String jdbcTypeClass = getJdbcTypeClass(metaData, i);
						boolean isSlowMetadataDB = !(driverName.contains("simba") || driverName.contains("impala"));
						if (Misc.isNullOrEmpty(jdbcTypeClass) || isSlowMetadataDB)
							try {
								String catalog = metaData.getCatalogName(i);
								String schema = metaData.getSchemaName(i);
								String table = metaData.getTableName(i);
								if (!(Misc.isNullOrEmpty(catalog) && Misc.isNullOrEmpty(schema)
										&& Misc.isNullOrEmpty(table))) {
									ResultSet rsmc = c.getMetaData().getColumns(catalog, schema, table, name);
									while (rsmc.next()) {
										if (Misc.isNullOrEmpty(jdbcTypeClass)) {
											jdbcTypeClass = getColumnType(rsmc.getInt("SQL_DATA_TYPE"));
										}
										if (isSlowMetadataDB) {
											String remarksStr = rsmc.getString("REMARKS");
											if(remarksStr != null && !remarksStr.trim().isEmpty()) {
												field.setDescription(
														StringUtils.xmlEncode(remarksStr, null));
											}
										}
										break;
									}
								}
							} catch (SQLException se) {
								se.printStackTrace();
							}
						if (Misc.isNullOrEmpty(jdbcTypeClass))
							jdbcTypeClass = Object.class.getCanonicalName();
						field.setValueClassName(jdbcTypeClass);

						String tbl = metaData.getTableName(i);
						if (!Misc.isNullOrEmpty(tbl))
							field.getPropertiesMap().setProperty(DataQueryAdapters.FIELD_PATH, tbl);
						columns.add(field);
					}
				}
			}
		} catch (

		SQLException e) {
			throw new JRException(e);
		} finally {
			if (c != null)
				try {
					c.close();
				} catch (SQLException e) {
					throw new JRException(e);
				}
		}
		return columns;
	}

	private static String JAVA_PATTERN = "\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*";
	private static Pattern PATTERN = Pattern.compile(JAVA_PATTERN + "(\\." + JAVA_PATTERN + ")*");

	public static String getJdbcTypeClass(java.sql.ResultSetMetaData rsmd, int t) {
		try {
			String cl = rsmd.getColumnClassName(t);
			if (Misc.isNullOrEmpty(cl) || !PATTERN.matcher(cl).matches())
				return getColumnType(rsmd, t);
			return getJRFieldType(cl);
		} catch (SQLException ex) {
			return getColumnType(rsmd, t);
		}
	}

	protected static String getColumnType(java.sql.ResultSetMetaData rsmd, int ind) {
		try {
			return getColumnType(rsmd.getColumnType(ind));
		} catch (SQLException ex2) {
			ex2.printStackTrace();
		}
		return null;
	}

	protected static String getColumnType(int type) {
		switch (type) {
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.NVARCHAR:
		case Types.LONGVARCHAR:
			return String.class.getCanonicalName();
		case Types.NUMERIC:
			return Number.class.getCanonicalName();
		case Types.DECIMAL:
			return BigDecimal.class.getCanonicalName();
		case Types.BIT:
		case Types.BOOLEAN:
			return Boolean.class.getCanonicalName();
		case Types.TINYINT:
			return Byte.class.getCanonicalName();
		case Types.SMALLINT:
			return Short.class.getCanonicalName();
		case Types.INTEGER:
			return Integer.class.getCanonicalName();
		case Types.BIGINT:
			return BigInteger.class.getCanonicalName();
		case Types.REAL:
			return Float.class.getCanonicalName();
		case Types.FLOAT:
		case Types.DOUBLE:
			return Double.class.getCanonicalName();
		case Types.BINARY:
		case Types.VARBINARY:
		case Types.LONGVARBINARY:
			return byte[].class.getCanonicalName();
		case Types.DATE:
			return Date.class.getCanonicalName();
		case Types.TIME:
		case Types.TIME_WITH_TIMEZONE:
			return Time.class.getCanonicalName();
		case Types.TIMESTAMP:
		case Types.TIMESTAMP_WITH_TIMEZONE:
			return Timestamp.class.getCanonicalName();
		case Types.CLOB:
			return Clob.class.getCanonicalName();
		case Types.NCLOB:
			return NClob.class.getCanonicalName();
		case Types.BLOB:
			return Blob.class.getCanonicalName();
		case Types.ARRAY:
			return Array.class.getCanonicalName();
		case Types.STRUCT:
			return Struct.class.getCanonicalName();
		case Types.ROWID:
			return RowId.class.getCanonicalName();
		case Types.REF:
			return Ref.class.getCanonicalName();
		case Types.DATALINK:
			return URL.class.getCanonicalName();
		case Types.SQLXML:
			return SQLXML.class.getCanonicalName();
		case Types.REF_CURSOR:
			return ResultSet.class.getCanonicalName();
		case Types.JAVA_OBJECT:
		case Types.OTHER:
		default:
			return Object.class.getCanonicalName();
		}
	}

	public static String getJRFieldType(String type) {
		if (type == null)
			return Object.class.getName();
		if (type.equals(boolean.class.getName()))
			return Boolean.class.getName();
		if (type.equals(byte.class.getName()))
			return Byte.class.getName();
		if (type.equals(int.class.getName()))
			return Integer.class.getName();
		if (type.equals(long.class.getName()))
			return Long.class.getName();
		if (type.equals(double.class.getName()))
			return Double.class.getName();
		if (type.equals(float.class.getName()))
			return Float.class.getName();
		if (type.equals(short.class.getName()))
			return Short.class.getName();
		if (type.startsWith("["))
			return Object.class.getName();
		return type;
	}

	private static Map<String, String> types = new HashMap<>();
	static {
		types.put("CHAR", String.class.getCanonicalName());
		types.put("VARCHAR", String.class.getCanonicalName());
		types.put("NVARCHAR", String.class.getCanonicalName());
		types.put("LONGVARCHAR", String.class.getCanonicalName());
		types.put("DECIMAL", BigDecimal.class.getCanonicalName());
		types.put("NUMERIC", Number.class.getCanonicalName());
		types.put("BIT", Boolean.class.getCanonicalName());
		types.put("BOOLEAN", Boolean.class.getCanonicalName());
		types.put("TINYINT", Byte.class.getCanonicalName());
		types.put("SMALLINT", Short.class.getCanonicalName());
		types.put("INTEGER", Integer.class.getCanonicalName());
		types.put("BIGINT", BigInteger.class.getCanonicalName());
		types.put("REAL", Float.class.getCanonicalName());
		types.put("FLOAT", Double.class.getCanonicalName());
		types.put("DOUBLE", Double.class.getCanonicalName());
		types.put("BINARY", byte[].class.getCanonicalName());
		types.put("VARBINARY", byte[].class.getCanonicalName());
		types.put("LONGVARBINARY", byte[].class.getCanonicalName());
		types.put("DATE", Date.class.getCanonicalName());
		types.put("TIME", Time.class.getCanonicalName());
		types.put("TIME_WITH_TIMEZONE", Time.class.getCanonicalName());
		types.put("TIMESTAMP", Timestamp.class.getCanonicalName());
		types.put("TIMESTAMP_WITH_TIMEZONE", Timestamp.class.getCanonicalName());
		types.put("CLOB", Clob.class.getCanonicalName());
		types.put("NCLOB", Clob.class.getCanonicalName());
		types.put("BLOB", Blob.class.getCanonicalName());
		types.put("ARRAY", Array.class.getCanonicalName());
		types.put("DISTINCT", "Mapping of underlying type");
		types.put("ROWID", RowId.class.getCanonicalName());
		types.put("DATALINK", URL.class.getCanonicalName());
		types.put("STRUCT", Struct.class.getCanonicalName());
		types.put("REF", Ref.class.getCanonicalName());
		types.put("JAVA_OBJECT", "Underlying Java class");
		types.put("SQLXML", SQLXML.class.getCanonicalName());
		types.put("REF_CURSOR", ResultSet.class.getCanonicalName());
		types.put("OTHER", Object.class.getCanonicalName());
	}

	public static String getJavaType4SQL(String type) {
		if (type == null)
			return Object.class.getCanonicalName();
		return Misc.nvl(types.get(type.toUpperCase()), "java.lang.String");
	}
}
