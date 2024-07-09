/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.builder;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.xml.sax.SAXParseException;

import net.sf.jasperreports.eclipse.builder.jdt.JRErrorHandler;
import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.engine.JRException;

public class Markers {
	public static final String MARKER_TYPE = "net.sf.jasperreports.jrxmlProblem.marker"; //$NON-NLS-1$

	public static IMarker addMarker(IResource file, Throwable e) throws CoreException {
		if (e instanceof JRException && e.getCause() instanceof SAXParseException)
			e = e.getCause();
		if (e instanceof SAXParseException) {
			SAXParseException se = (SAXParseException) e;
			return addMarker(file, e.getMessage(), se.getLineNumber(), se.getColumnNumber(), IMarker.SEVERITY_ERROR);
		}
		if (e instanceof JRException && e.getCause() != null) {
			addMarker(file, e.getCause());
		} else if (e instanceof JRException && ((JRException) e).getArgs() != null) {
			for (Object arg : ((JRException) e).getArgs()) {
				Set<String> set = new HashSet<String>();
				String[] errors = ((String) arg).split("\\d+\\.");
				for (String er : errors) {
					if (er.isEmpty())
						continue;
					er = StringUtils.normalizeSpace(er);
					int indx = er.indexOf("<-");
					if (indx >= 0)
						er = er.substring(0, er.indexOf("<-"));
					set.add(er);
				}
				IMarker fMarker = null;
				Pattern p = Pattern.compile("//\\$JR_EXPR_ID=\\d+\\$");
				for (String s : set) {
					String msg = s;
					int indx = s.indexOf("//$JR_EXPR_ID=");
					if (indx >= 0)
						msg = msg.substring(0, indx);
					IMarker m = addMarker(file, msg, 0, 0, IMarker.SEVERITY_ERROR);
					Matcher match = p.matcher(s);
					while (match.find()) {
						String gr = match.group();
						gr = gr.substring("//$JR_EXPR_ID=".length());
						m.setAttribute(JRErrorHandler.MARKER_ERROR_JREXPRESSION, gr.substring(0, gr.length() - 1));
					}
					if (fMarker == null)
						fMarker = m;
				}
				if (fMarker != null)
					return fMarker;
			}
		}
		return addMarker(file, e.getMessage() != null ? e.getMessage().replace("\n", "") : e.getClass().getName(), 0, 0,
				IMarker.SEVERITY_ERROR);
	}

	public static IMarker addMarker(IResource file, String message, int lineNumber, int severity) throws CoreException {
		return addMarker(file, message, lineNumber, 0, severity);
	}

	public static IMarker addMarker(IResource file, String message, int lineNumber, int colNumber, int severity)
			throws CoreException {
		IMarker marker = file.createMarker(Markers.MARKER_TYPE);
		marker.setAttribute(IMarker.MESSAGE, message);
		marker.setAttribute(IMarker.SEVERITY, severity);
		marker.setAttribute(IMarker.USER_EDITABLE, false);
		marker.setAttribute(IMarker.LINE_NUMBER, Math.max(0, lineNumber));
		marker.setAttribute(IMarker.CHAR_END, Math.max(0, colNumber));
		// Add a marker on the project folder
		createProjectMarker(file.getProject(), file);
		return marker;
	}

	/**
	 * Add a marker to a project as error for a contained resource
	 * 
	 * @param project
	 *            the project where the marker should be added
	 * @param resource
	 *            the resource with the error
	 */
	public static void createProjectMarker(IProject project, IResource resource) {
		try {
			boolean found = false;
			// Search if an error for the resource was already present, to avoid
			// to double the errors
			for (IMarker marker : resource.getProject().findMarkers(Markers.MARKER_TYPE, false, IResource.DEPTH_ZERO)) {
				if (marker.getAttribute(IMarker.SOURCE_ID, "").equals(resource.getFullPath().toOSString())) { //$NON-NLS-1$
					found = true;
					break;
				}
			}
			if (!found) {
				IMarker marker = project.createMarker(Markers.MARKER_TYPE);
				marker.setAttribute(IMarker.MESSAGE,
						MessageFormat.format(Messages.Markers_projectErrorMarker, new Object[] { resource.getName() }));
				marker.setAttribute(IMarker.SOURCE_ID, resource.getFullPath().toOSString());
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public static void deleteMarkers(IResource resource) throws CoreException {
		resource.deleteMarkers(Markers.MARKER_TYPE, false, IResource.DEPTH_ZERO);
		List<IMarker> projectMarkerToDelete = new ArrayList<IMarker>();
		// Remove the markers also from the project folder
		for (IMarker marker : resource.getProject().findMarkers(Markers.MARKER_TYPE, false, IResource.DEPTH_ZERO)) {
			Object source_id = marker.getAttribute(IMarker.SOURCE_ID);
			// Deleting the markers with source_id null will remove old JSS
			// marker to clean the markers on the workspace
			// when updating
			if (source_id == null || source_id.equals(resource.getFullPath().toOSString())) {
				projectMarkerToDelete.add(marker);
			}
		}
		for (IMarker marker : projectMarkerToDelete) {
			marker.delete();
		}
	}

}
