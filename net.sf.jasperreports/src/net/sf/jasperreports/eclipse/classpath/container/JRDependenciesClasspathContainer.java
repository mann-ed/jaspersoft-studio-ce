/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.classpath.container;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;
import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.eclipse.util.BundleCommonUtils;

/**
 * The classpath container that provides the references to all the JasperReports
 * dependencies bundles and jars that can be used to compile/run JR code
 * directly inside Jaspersoft Studio.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class JRDependenciesClasspathContainer implements IClasspathContainer {

	public static final IPath ID = new Path("net.sf.jasperreports.JR_DEPENDENCIES_CONTAINER"); //$NON-NLS-1$
	private static final List<String> bundlesList;
	private IPath path;
	private List<IClasspathEntry> entriesList;

	static {
		bundlesList = new ArrayList<>();
		bundlesList.add("org.apache.xalan"); //$NON-NLS-1$
		bundlesList.add("org.apache.commons.io"); //$NON-NLS-1$
		bundlesList.add("groovy"); //$NON-NLS-1$
		bundlesList.add("joda-time"); //$NON-NLS-1$
		bundlesList.add("org.krysalis.barcode4j"); //$NON-NLS-1$
		bundlesList.add("com.jaspersoft.studio.bundles.batik"); //$NON-NLS-1$
		bundlesList.add("org.w3c.dom.svg"); //$NON-NLS-1$
		bundlesList.add("org.jfree.chart-osgi"); //$NON-NLS-1$
		bundlesList.add("org.jfree.jcommon-osgi"); //$NON-NLS-1$
		bundlesList.add("org.apache.servicemix.bundles.jaxen"); //$NON-NLS-1$
		bundlesList.add("org.apache.servicemix.bundles.rhino"); //$NON-NLS-1$
		bundlesList.add("org.apache.servicemix.bundles.xmlbeans"); //$NON-NLS-1$
		bundlesList.add("org.apache.servicemix.bundles.antlr"); //$NON-NLS-1$
		bundlesList.add("org.apache.servicemix.bundles.xalan-serializer"); //$NON-NLS-1$
		bundlesList.add("org.apache.commons.collections"); //$NON-NLS-1$
		bundlesList.add("org.apache.commons.collections4"); //$NON-NLS-1$
		bundlesList.add("org.apache.commons.logging"); //$NON-NLS-1$
		bundlesList.add("com.jaspersoft.studio.bundles.barbecue"); //$NON-NLS-1$
		bundlesList.add("com.jaspersoft.studio.bundles.commons-beanutils"); //$NON-NLS-1$
		bundlesList.add("com.jaspersoft.studio.bundles.commons-digester"); //$NON-NLS-1$
		bundlesList.add("com.jaspersoft.studio.bundles.itext"); //$NON-NLS-1$
		bundlesList.add("com.jaspersoft.studio.bundles.beanshell"); //$NON-NLS-1$
		bundlesList.add("com.jaspersoft.studio.bundles.poi"); //$NON-NLS-1$
		bundlesList.add("com.jaspersoft.studio.bundles.jackson"); //$NON-NLS-1$
		bundlesList.add("org.apache.logging.log4j.api"); //$NON-NLS-1$
		bundlesList.add("org.bouncycastle.bcprov"); //$NON-NLS-1$
	}

	public JRDependenciesClasspathContainer(IPath containerPath) {
		this.path = containerPath;
	}

	@Override
	public IClasspathEntry[] getClasspathEntries() {
		if (entriesList == null) {
			entriesList = new ArrayList<>();
			addRequiredBundles(entriesList);
			addRequiredJars(entriesList);
		}
		return entriesList.toArray(new IClasspathEntry[entriesList.size()]);
	}

	/*
	 * Adds the required bundles to the classpath. References to the plugin jars and
	 * internal jars are added.
	 */
	private void addRequiredBundles(List<IClasspathEntry> entries) {
		for (String id : bundlesList) {
			Bundle bundle = BundleCommonUtils.getBundle(id);
			if (bundle == null) {
				JasperReportsPlugin.getDefault()
						.logError(new RuntimeException(NLS.bind("Unable to find the bundle {0}", id)));
			} else {
				String location = BundleCommonUtils.getBundleAbsoluteLocation(bundle);
				if (location != null) {
					entries.add(JavaCore.newLibraryEntry(new Path(location), null, null));
					List<File> extractedJars = BundleCommonUtils.extractAllJarsFromBundle(bundle);
					for (File f : extractedJars) {
						entries.add(JavaCore.newLibraryEntry(new Path(f.getAbsolutePath()), null, null));
					}
				}
			}
		}
	}

	/*
	 * Adds the jars that are not available as bundles and that are contained inside
	 * Jaspersoft Studio plugins.
	 */
	private void addRequiredJars(List<IClasspathEntry> entries) {
		List<URL> jarURLS = new ArrayList<>();
		// JR plugin jars
		Bundle jrBundle = BundleCommonUtils.getBundle("net.sf.jasperreports"); //$NON-NLS-1$
		jarURLS.add(jrBundle.getEntry("lib/castor-core-1.4.1.jar")); //$NON-NLS-1$
		jarURLS.add(jrBundle.getEntry("lib/castor-xml-1.4.1.jar")); //$NON-NLS-1$
		jarURLS.add(jrBundle.getEntry("lib/spring/spring-core-5.3.18.jar")); //$NON-NLS-1$
		jarURLS.add(jrBundle.getEntry("lib/spring/spring-beans-5.3.18.jar")); //$NON-NLS-1$
		jarURLS.add(jrBundle.getEntry("lib/spring/spring-expression-5.3.18.jar")); //$NON-NLS-1$
		// JSS plugin jars
		Bundle jssBundle = BundleCommonUtils.getBundle("com.jaspersoft.studio"); //$NON-NLS-1$
		jarURLS.add(jssBundle.getEntry("lib/velocity-1.7-dep.jar")); //$NON-NLS-1$
		for (URL url : jarURLS) {
			try {
				URL fileURL = FileLocator.toFileURL(url);
				URI uri = new URI(fileURL.getProtocol(), fileURL.getUserInfo(), fileURL.getHost(), fileURL.getPort(),
						fileURL.getPath(), fileURL.getQuery(), null);
				Path binpath = new Path(new File(uri).getAbsolutePath());
				entries.add(JavaCore.newLibraryEntry(binpath, null, null)); // $NON-NLS-1$
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getDescription() {
		return Messages.JRDependenciesClasspathContainer_Description;
	}

	@Override
	public int getKind() {
		return IClasspathContainer.K_APPLICATION;
	}

	@Override
	public IPath getPath() {
		return path;
	}

}
