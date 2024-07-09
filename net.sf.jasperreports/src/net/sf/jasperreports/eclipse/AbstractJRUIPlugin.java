/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;

import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.eclipse.util.BundleCommonUtils;
import net.sf.jasperreports.eclipse.util.ResourceScope;

/**
 * Abstract plug-in superclass that provides methods for logging and other
 * common features.
 * <p>
 * 
 * Most of the methods of this class are wrappers for the utility methods in
 * {@link BundleCommonUtils}.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * @see BundleCommonUtils
 * 
 */
public abstract class AbstractJRUIPlugin extends AbstractUIPlugin {

	/**
	 * Flag used to check if the trace is enabled forthe current plugin
	 */
	private final boolean TRACE_ENABLED = "true".equalsIgnoreCase(Platform.getDebugOption(getPluginID() + "/debug"));

	private SynchronousBundleListener bundleListener = event -> {
		if (event.getBundle() == getBundle() && event.getType() == BundleEvent.STARTED
				&& getBundle().getState() == Bundle.ACTIVE)
			postStartOperations();
	};

	/**
	 * @return the identifier for the current plug-in
	 */
	public abstract String getPluginID();

	/**
	 * We are adding the ability for plugins to run some code just after the bundle
	 * has started.
	 * 
	 * @see #postStartOperations()
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		context.addBundleListener(bundleListener);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		try {
			if (bundleListener != null) {
				context.removeBundleListener(bundleListener);
			}
		} finally {
			super.stop(context);
		}
	}

	/**
	 * This method is invoked after a {@link BundleEvent} is notified advising that
	 * the bundle has been started. It's better not to run complex and time wasting
	 * code inside the {@link #start(BundleContext)} method.
	 * <p>
	 * 
	 * Plugins that want to perform some operations should run inside here.
	 */
	protected void postStartOperations() {
		// to be overridden by clients
	}

	/**
	 * Get the full path name for a resource located inside the plug-in.
	 * 
	 * @param path the path of the internal resource
	 * @return the string corresponding to the full path
	 * @throws IOException if a problem occurs during conversion
	 */
	public String getFileLocation(String path) throws IOException {
		return BundleCommonUtils.getFileLocation(getPluginID(), path);
	}

	private Map<String, ImageDescriptor> map = new HashMap<>();

	/**
	 * Returns an image descriptor for the image file at the given bundle relative
	 * path.
	 * 
	 * @param path the bundle path to look for
	 * @return the image descriptor if any, <code>null</code> otherwise
	 */
	public ImageDescriptor getImageDescriptor(String path) {
		ImageDescriptor id = map.get(path);
		if (id == null)
			id = BundleCommonUtils.getImageDescriptor(getPluginID(), path);
		return id;
	}

	/**
	 * Returns an SWT image related to the given bundle relative path.
	 * 
	 * @param path the bundle path of the image
	 * @return the SWT image instance if any, <code>null</code> otherwise
	 */
	public Image getImage(String path) {
		return BundleCommonUtils.getImage(getImageDescriptor(path));
	}

	/**
	 * Returns the SWT image related to the specified image descriptor.
	 * 
	 * @param descriptor the image descriptor
	 * @return the SWT image
	 */
	public Image getImage(ImageDescriptor descriptor) {
		return BundleCommonUtils.getImage(descriptor);
	}

	/**
	 * Logs an error message and an optional exception.
	 * 
	 * @param message   a human-readable message
	 * @param exception a low-level exception, or <code>null</code> if not
	 *                  applicable
	 */
	public void logError(String message, Throwable exception) {
		BundleCommonUtils.logError(getPluginID(), message, exception);
	}

	/**
	 * Logs an exception with a generic error message.
	 * 
	 * @param exception a low-level exception, can not be <code>null</code>
	 * 
	 */
	public void logError(Throwable exception) {
		Assert.isNotNull(exception);
		logError(Messages.AbstractJRUIPlugin_GenericErrorMsg, exception);
	}

	/**
	 * Logs a warning message and an optional exception.
	 * 
	 * @param message   a human-readable message
	 * @param exception a low-level exception, or <code>null</code> if not
	 *                  applicable
	 */
	public void logWarning(String message, Throwable exception) {
		BundleCommonUtils.logWarning(getPluginID(), message, exception);
	}

	/**
	 * Logs a warning message.
	 * 
	 * @param message a human-readable message
	 */
	public void logWarning(String message) {
		logWarning(message, null);
	}

	/**
	 * Logs an informational message.
	 * 
	 * @param message a human-readable message
	 */
	public void logInfo(String message) {
		BundleCommonUtils.logInfo(getPluginID(), message);
	}

	/**
	 * Return if the trace is enabled for the current plugin
	 * 
	 * @return true if the trace is enabled, false otherwise
	 */
	public boolean isTraceEnabled() {
		return TRACE_ENABLED;
	}

	/**
	 * Log a trace message for the current plugin, but only if the trace is enable
	 * 
	 * @param message the message to log
	 */
	public void logTrace(String message) {
		if (TRACE_ENABLED) {
			BundleCommonUtils.logTrace(getPluginID(), message);
		}
	}

	/**
	 * Log a trace message for the current plugin, but only if the trace is enable
	 * 
	 * @param message the message to log
	 */
	public void logTrace(Throwable e) {
		if (TRACE_ENABLED) {
			BundleCommonUtils.logTrace(getPluginID(), e);
		}
	}

	/**
	 * Logs a generic status object.
	 * 
	 * @param status the status object to be logged
	 */
	public void log(IStatus status) {
		BundleCommonUtils.logStatus(getPluginID(), status);
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		return getPreferenceStore(null, getPluginID());
	}

	private Map<IResource, Map<String, MScopedPreferenceStore>> prefStores = new HashMap<>();

	public ScopedPreferenceStore getPreferenceStore(IResource project, String pageId) {
		MScopedPreferenceStore pstore = null;
		if (project != null) {
			Map<String, MScopedPreferenceStore> pagemap = prefStores.get(project);
			if (pagemap != null) {
				pstore = pagemap.get(pageId);
			} else {
				pagemap = new HashMap<>();
				prefStores.put(project, pagemap);
			}
			if (pstore == null) {
				if (project instanceof IProject) {
					pstore = new MScopedPreferenceStore(new ProjectScope((IProject) project), pageId);
					pstore.setSearchContexts(
							new IScopeContext[] { new ProjectScope((IProject) project), InstanceScope.INSTANCE });
				} else {
					pstore = new MScopedPreferenceStore(new ResourceScope(project), pageId);
					pstore.setSearchContexts(new IScopeContext[] { new ResourceScope(project),
							new ProjectScope(project.getProject()), InstanceScope.INSTANCE });
				}
				for (IPropertyChangeListener pl : listeners)
					pstore.addPropertyChangeListener(pl);
				pstores.add(pstore);
				pagemap.put(pageId, pstore);
			}
		} else {
			if (instStore == null) {
				instStore = new MScopedPreferenceStore(InstanceScope.INSTANCE, pageId);
				instStore.setSearchContexts(new IScopeContext[] { InstanceScope.INSTANCE });
				pstores.add(instStore);
				for (IPropertyChangeListener pl : listeners)
					instStore.addPropertyChangeListener(pl);
			}
			pstore = instStore;
		}
		return pstore;
	}

	private MScopedPreferenceStore instStore;
	private Set<ScopedPreferenceStore> pstores = new CopyOnWriteArraySet<>();
	private Set<IPropertyChangeListener> listeners = new CopyOnWriteArraySet<>();

	public void addPreferenceListener(IPropertyChangeListener plistener, IResource r) {
		if (!listeners.contains(plistener)) {
			listeners.add(plistener);
			getPreferenceStore().addPropertyChangeListener(plistener);
			if (r != null) {
				String id = getPluginID();
				getPreferenceStore(r, id).addPropertyChangeListener(plistener);
				if (r instanceof IFile) {
					getPreferenceStore(r.getProject(), id).addPropertyChangeListener(plistener);
				}
			}
		}
	}

	public void removePreferenceListener(IPropertyChangeListener plistener) {
		if (plistener == null)
			return;
		getPreferenceStore().removePropertyChangeListener(plistener);
		for (ScopedPreferenceStore p : pstores)
			p.removePropertyChangeListener(plistener);
		listeners.remove(plistener);
	}

}
