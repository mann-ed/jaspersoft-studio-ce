/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.samples;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import net.sf.jasperreports.eclipse.util.BundleCommonUtils;
import net.sf.jasperreports.samples.messages.Messages;

/**
 * Class that handles the samples available and that contribute to 
 * the creation of the Report Samples project.
 */
public class SamplesManager {

	private List<ISamplesProvider> samplesProviders = new ArrayList<ISamplesProvider>();
	private Set<File> additionalLibraries = null;
	private Set<File> srcFolders = null;
	private Set<File> exampleFolders = null;

	/**
	 * Initialize the manager gathering all the available samples contributors.
	 */
	public void init() {
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(Activator.PLUGIN_ID, "samples"); //$NON-NLS-1$
		for (IConfigurationElement e : config) {
			try {
				Object o = e.createExecutableExtension("resources"); //$NON-NLS-1$
				if (o instanceof ISamplesProvider)
					samplesProviders.add((ISamplesProvider) o);
			} catch (CoreException ex) {
				BundleCommonUtils.logError(Activator.PLUGIN_ID, Messages.SamplesManager_AddSampleProviderError, ex);
			}
		}
	}

	/**
	 * @return the set of additional libraries to be added to the project
	 */
	public Set<File> getAdditionalLibraries() {
		if (additionalLibraries == null) {
			additionalLibraries = new HashSet<File>();
			for (ISamplesProvider sp : samplesProviders) {
				Set<File> libraries = sp.getAdditionalLibraries();
				if (libraries != null) {
					additionalLibraries.addAll(libraries);
				}
			}
		} 
		return additionalLibraries;
	}

	/**
	 * @return the set of source folders for the project
	 */
	public Set<File> getSourceFolders() {
		if (srcFolders == null) {
			srcFolders = new HashSet<File>();
			for (ISamplesProvider sp : samplesProviders) {
				Set<File> sourceFolders = sp.getSourceFolders();
				if (sourceFolders != null) {
					srcFolders.addAll(sourceFolders);
				}
			}
		}
		return srcFolders;
	}

	/**
	 * @return the set of folders with the actual examples
	 */
	public Set<File> getExampleFolders() {
		if (exampleFolders == null) {
			exampleFolders = new HashSet<File>();
			for (ISamplesProvider sp : samplesProviders) {
				Set<File> sampleFolders = sp.getSampleFolders();
				if (sampleFolders != null) {
					exampleFolders.addAll(sampleFolders);
				}
			}
		}
		return exampleFolders;
	}

}
