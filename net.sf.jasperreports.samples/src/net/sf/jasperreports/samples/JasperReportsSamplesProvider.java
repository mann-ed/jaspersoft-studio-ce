/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.samples;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import net.sf.jasperreports.eclipse.util.BundleCommonUtils;
import net.sf.jasperreports.samples.messages.Messages;

/**
 * Provider for the Report samples.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 *
 */
public class JasperReportsSamplesProvider implements ISamplesProvider {
	
	private static File tmpJRSamplesLocation = null;
	private Set<File> sourceFolders = null;
	private Set<File> additionalLibraries = null;
	private Set<File> samplesFolders = null;
	
	static {
		try {
			// prepare temp folder for samples
			tmpJRSamplesLocation = new File(FileUtils.getTempDirectory(),"jrSamples" + System.currentTimeMillis()); //$NON-NLS-1$
			tmpJRSamplesLocation.mkdir();
			tmpJRSamplesLocation.deleteOnExit();
			// unzip samples zipped content
			String samplesZipLocation = 
					BundleCommonUtils.getFileLocation(Activator.PLUGIN_ID, "resources/jrsamples.zip"); //$NON-NLS-1$
			net.sf.jasperreports.eclipse.util.FileUtils.unZip(
					new File(samplesZipLocation), tmpJRSamplesLocation);
		} catch (IOException e) {
			BundleCommonUtils.logError(
					Activator.PLUGIN_ID, Messages.JasperReportsSamplesProvider_UnzipSamplesError, e);
		}
	}

	@Override
	public Set<File> getSourceFolders() {
		if(sourceFolders==null && tmpJRSamplesLocation!=null){
			sourceFolders = new HashSet<File>(1);
			sourceFolders.add(new File(tmpJRSamplesLocation,"src")); //$NON-NLS-1$
		}
		return sourceFolders;
	}

	@Override
	public Set<File> getAdditionalLibraries() {
		if(additionalLibraries == null && tmpJRSamplesLocation!=null){
			File libFolder = new File(tmpJRSamplesLocation,"lib"); //$NON-NLS-1$
			File[] jars = libFolder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.endsWith(".jar")) { //$NON-NLS-1$
						return true;
					}
					return false;
				}
			});
			additionalLibraries = new HashSet<File>(Arrays.asList(jars));
		}
		return additionalLibraries;
	}

	@Override
	public Set<File> getSampleFolders() {
		if(samplesFolders == null && tmpJRSamplesLocation!=null){
			File[] files = tmpJRSamplesLocation.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(new File(dir,name).isDirectory()) {
						if("src".equals(name) || "lib".equals(name)) { //$NON-NLS-1$ //$NON-NLS-2$
							return false;
						}
						return true;
					}
					return false;
				}
			});
			samplesFolders = new HashSet<File>(Arrays.asList(files));
		}
		return samplesFolders;
	}

}
