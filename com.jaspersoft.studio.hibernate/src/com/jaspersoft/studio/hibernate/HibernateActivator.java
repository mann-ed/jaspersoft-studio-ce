package com.jaspersoft.studio.hibernate;

import org.osgi.framework.BundleContext;

import net.sf.jasperreports.eclipse.AbstractJRUIPlugin;

/**
 * The activator class controls the plug-in life cycle
 */
public class HibernateActivator extends AbstractJRUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.jaspersoft.studio.hibernate"; //$NON-NLS-1$

	// The shared instance
	private static HibernateActivator plugin;
	
	/**
	 * The constructor
	 */
	public HibernateActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static HibernateActivator getDefault() {
		return plugin;
	}

	@Override
	public String getPluginID() {
		return PLUGIN_ID;
	}
	
}
