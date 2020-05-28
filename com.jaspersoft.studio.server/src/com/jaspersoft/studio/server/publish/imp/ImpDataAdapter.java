/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.publish.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.DataAdapterManager;
import com.jaspersoft.studio.data.storage.FileDataAdapterStorage;
import com.jaspersoft.studio.server.model.AFileResource;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.model.MContentResource;
import com.jaspersoft.studio.server.model.MRDataAdapter;
import com.jaspersoft.studio.server.model.MRJson;
import com.jaspersoft.studio.server.model.MRSecureFile;
import com.jaspersoft.studio.server.model.MReportUnit;
import com.jaspersoft.studio.server.model.MXmlFile;
import com.jaspersoft.studio.server.preferences.JRSPreferencesPage;
import com.jaspersoft.studio.server.protocol.Version;
import com.jaspersoft.studio.server.publish.OverwriteEnum;
import com.jaspersoft.studio.server.publish.PublishOptions;
import com.jaspersoft.studio.server.publish.PublishOptions.ValueSetter;
import com.jaspersoft.studio.server.publish.PublishUtil;
import com.jaspersoft.studio.server.publish.imp.da.AImpJdbcDataAdapter;
import com.jaspersoft.studio.server.publish.imp.da.ImpBigQueryDataAdapter;
import com.jaspersoft.studio.server.publish.imp.da.ImpMongoDbDataAdapter;
import com.jaspersoft.studio.server.publish.imp.da.ImpSimbaBigQueryDataAdapter;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.DataAdapterParameterContributorFactory;
import net.sf.jasperreports.data.FileDataAdapter;
import net.sf.jasperreports.data.RepositoryDataLocation;
import net.sf.jasperreports.data.StandardRepositoryDataLocation;
import net.sf.jasperreports.data.jdbc.JdbcDataAdapter;
import net.sf.jasperreports.data.json.JsonDataAdapter;
import net.sf.jasperreports.data.xml.XmlDataAdapter;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.ui.validator.IDStringValidator;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.repo.RepositoryUtil;

public class ImpDataAdapter extends AImpObject {
	public ImpDataAdapter(JasperReportsConfiguration jrConfig) {
		super(jrConfig);
	}

	private Map<String, PublishOptions> files = new HashMap<>();

	private class DAValueSetter extends ValueSetter<List<JRDesignDataset>> {
		private boolean removeDA = false;

		public DAValueSetter(PublishOptions publishOptions) {
			this(publishOptions, false);
		}

		public DAValueSetter(PublishOptions publishOptions, boolean removeDA) {
			publishOptions.super(new ArrayList<>());
			this.removeDA = removeDA;
		}

		@Override
		public void setup() {
			for (JRDesignDataset ds : object) {
				if (removeDA)
					ds.getPropertiesMap()
							.removeProperty(DataAdapterParameterContributorFactory.PROPERTY_DATA_ADAPTER_LOCATION);
				else
					ds.setProperty(DataAdapterParameterContributorFactory.PROPERTY_DATA_ADAPTER_LOCATION, getValue());
			}
		}

	}

	public File publish(JRDesignDataset jd, String dpath, MReportUnit mrunit, IProgressMonitor monitor,
			Set<String> fileset, IFile file) throws Exception {
		dpath = preparePath(fileset, dpath);
		if (fileset.contains(dpath)) {
			PublishOptions popt = files.get(dpath);
			((DAValueSetter) popt.getValueSetter()).getObject().add(jd);
			return null;
		}
		if (dpath == null)
			return null;
		File f = findFile(file, dpath);
		if (f != null && f.exists()) {
			fileset.add(dpath);
			PublishOptions popt = createOptions(jrConfig, dpath);
			files.put(dpath, popt);
			popt.setFilePath(dpath);
			AFileResource fr = addResource(monitor, mrunit, fileset, f, popt);
			DAValueSetter vs = popt.getOverwrite().equals(OverwriteEnum.REMOVE) ? new DAValueSetter(popt, true)
					: new DAValueSetter(popt);
			vs.getObject().add(jd);
			popt.setValueSetter(vs);
			if (fr == null)
				return null;
			vs.setValue("repo:" + fr.getValue().getUriString());
		}
		return f;
	}

	@Override
	protected ResourceDescriptor createResource(MReportUnit mrunit) {
		return MRDataAdapter.createDescriptor(mrunit);
	}

	@Override
	public AFileResource publish(JasperDesign jd, JRDesignElement img, MReportUnit mrunit, IProgressMonitor monitor,
			Set<String> fileset, IFile file) throws Exception {
		return null;
	}

	@Override
	protected JRDesignExpression getExpression(JRDesignElement img) {
		return null;
	}

	@Override
	protected AFileResource addResource(IProgressMonitor monitor, MReportUnit mrunit, Set<String> fileset, File f,
			PublishOptions popt) {
		ResourceDescriptor runit = mrunit.getValue();
		String rname = f.getName();
		if (popt.getFilePath() != null)
			rname = FilenameUtils.getBaseName(popt.getFilePath());
		ResourceDescriptor rd = createResource(mrunit);
		rd.setName(IDStringValidator.safeChar(rname));
		rd.setLabel(rname);

		rd.setParentFolder(runit.getParentFolder());
		rd.setUriString(runit.getParentFolder() + "/" + rd.getName());

		final AFileResource mres = new MRDataAdapter(mrunit, rd, -1);
		mres.setFile(f);
		String b = jrConfig.getProperty(JRSPreferencesPage.PUBLISH_REPORT_OVERRIDEBYDEFAULT, "true");
		if (b.equals("true") && rd.getIsNew())
			popt.setOverwrite(OverwriteEnum.OVERWRITE);
		mres.setPublishOptions(popt);

		PublishUtil.loadPreferences(monitor, (IFile) jrConfig.get(FileUtils.KEY_FILE), mres);
		List<AMResource> resourses = PublishUtil.getResources(mrunit, monitor, jrConfig);
		resourses.add(mres);
		FileInputStream is = null;
		try {
			is = new FileInputStream(f);
			final DataAdapterDescriptor dad = FileDataAdapterStorage.readDataADapter(is,
					(IFile) jrConfig.get(FileUtils.KEY_FILE), jrConfig);
			if (dad != null) {
				final DataAdapter da = dad.getDataAdapter();
				if (da.getClass().getName().equals("com.jaspersoft.jasperreports.data.jrs.JrsDataAdapterImpl"))
					popt.setOverwrite(OverwriteEnum.REMOVE);

				Map<String, String> rs = getFileName(da);
				if (rs != null)
					rs.forEach((key, fname) -> {
						InputStream fis = null;
						OutputStream fos = null;
						try {
							fis = RepositoryUtil.getInstance(jrConfig).getInputStreamFromLocation(fname);
							File file = FileUtils.createTempFile("tmp", "");
							fos = new FileOutputStream(file);
							if (fis != null) {
								IOUtils.copy(fis, fos);

								fname = fname.replace("\\", "/");

								int indx = fname.lastIndexOf('/');
								if (indx >= 0 && indx + 1 < fname.length())
									fname = fname.substring(indx + 1);

								ResourceDescriptor rd1 = getResource(da, mrunit);
								rd1.setName(IDStringValidator.safeChar(fname));
								rd1.setLabel(fname);

								rd1.setParentFolder(runit.getParentFolder());
								rd1.setUriString(runit.getParentFolder() + "/" + rd1.getName());

								AFileResource mdaf = null;
								if (da instanceof XmlDataAdapter)
									mdaf = new MXmlFile(mrunit, rd1, -1);
								else if (da instanceof JsonDataAdapter)
									mdaf = new MRJson(mrunit, rd1, -1);
								else if (da instanceof JdbcDataAdapter) {
									for (AImpJdbcDataAdapter d : jdbcFiles) {
										if (d.isHandling((JdbcDataAdapter) da)) {
											rd1.setWsType(d.getResourceType(key));
											if (rd1.getWsType().equals(ResourceDescriptor.TYPE_SECURE_FILE))
												mdaf = new MRSecureFile(mrunit, rd1, -1);
											else
												mdaf = new MContentResource(mrunit, rd1, -1);
											break;
										}
									}
								} else
									mdaf = new MContentResource(mrunit, rd1, -1);
								if (mdaf != null) {
									mdaf.setFile(file);
									PublishOptions fpopt = createOptions(jrConfig, fname);
									if (b.equals("true") && rd1.getIsNew())
										fpopt.setOverwrite(OverwriteEnum.OVERWRITE);
									mdaf.setPublishOptions(fpopt);
									fpopt.setValueSetter(popt.new ValueSetter<DataAdapter>(da) {

										@Override
										public void setup() {
											setFileName(da, value);
											try {
												File f = FileUtils.createTempFile("tmp", "");
												org.apache.commons.io.FileUtils.writeStringToFile(f,
														DataAdapterManager.toDataAdapterFile(dad, jrConfig),
														StandardCharsets.UTF_8);
												mres.setFile(f);
											} catch (IOException e) {
												UIUtils.showError(e);
											}
										}
									});
									fpopt.getValueSetter().setValue("repo:" + rd1.getUriString());

									PublishUtil.loadPreferences(monitor, (IFile) jrConfig.get(FileUtils.KEY_FILE),
											mdaf);
									resourses.add(mdaf);
								}
								File ftmp = FileUtils.createTempFile("tmp", "");
								org.apache.commons.io.FileUtils.writeStringToFile(ftmp,
										DataAdapterManager.toDataAdapterFile(dad, jrConfig), StandardCharsets.UTF_8);
								mres.setFile(ftmp);
							}
						} catch (JRException | IOException e) {
							e.printStackTrace();
						} finally {
							FileUtils.closeStream(fis);
							FileUtils.closeStream(fos);
						}
					});
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			FileUtils.closeStream(is);
		}
		return mres;
	}

	protected ResourceDescriptor getResource(DataAdapter da, MReportUnit parent) {
		if (Version.isGreaterThan(parent.getWsClient().getServerInfo(), "6") && da instanceof JsonDataAdapter)
			return MRJson.createDescriptor(parent);
		return MXmlFile.createDescriptor(parent);
	}

	protected void setFileName(DataAdapter da, String fname) {
		if (da instanceof FileDataAdapter && ((FileDataAdapter) da).getDataFile() instanceof RepositoryDataLocation)
			((StandardRepositoryDataLocation) ((FileDataAdapter) da).getDataFile()).setLocation(fname);
		if (da instanceof JdbcDataAdapter) {
			JdbcDataAdapter jdbcDA = ((JdbcDataAdapter) da);
			if (jdbcDA.getDriver() != null)
				for (AImpJdbcDataAdapter d : jdbcFiles)
					if (d.setFileName(jdbcDA, d.getKeys()[0][0], fname))
						break;
		}
	}

	protected Map<String, String> getFileName(DataAdapter da) {
		if (da instanceof JdbcDataAdapter) {
			JdbcDataAdapter jdbcDA = ((JdbcDataAdapter) da);
			if (jdbcDA.getDriver() != null)
				for (AImpJdbcDataAdapter d : jdbcFiles) {
					Map<String, String> res = d.getFileName(jdbcDA);
					if (!Misc.isNullOrEmpty(res))
						return res;
				}
		}
		if (da instanceof FileDataAdapter && ((FileDataAdapter) da).getDataFile() instanceof RepositoryDataLocation) {
			Map<String, String> res = new HashMap<>();
			res.put("property", ((RepositoryDataLocation) ((FileDataAdapter) da).getDataFile()).getLocation());
			return res;
		}
		return null;
	}

	private static Set<AImpJdbcDataAdapter> jdbcFiles = new HashSet<>();
	static {
		jdbcFiles.add(new ImpBigQueryDataAdapter());
		jdbcFiles.add(new ImpSimbaBigQueryDataAdapter());
		jdbcFiles.add(new ImpMongoDbDataAdapter());
	}

}
