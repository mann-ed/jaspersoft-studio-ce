/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.widgets.map.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.jaspersoft.studio.widgets.map.MapActivator;
import com.jaspersoft.studio.widgets.map.MapWidgetConstants;
import com.jaspersoft.studio.widgets.map.browserfunctions.GMapEnabledFunction;
import com.jaspersoft.studio.widgets.map.browserfunctions.TestJavaCallSupport;
import com.jaspersoft.studio.widgets.map.browserfunctions.UpdateMapCenter;
import com.jaspersoft.studio.widgets.map.browserfunctions.UpdateMapType;
import com.jaspersoft.studio.widgets.map.browserfunctions.UpdateZoomLevel;
import com.jaspersoft.studio.widgets.map.messages.Messages;
import com.jaspersoft.studio.widgets.map.support.BaseJSMapSupport;
import com.jaspersoft.studio.widgets.map.support.BaseJavaMapSupport;
import com.jaspersoft.studio.widgets.map.support.JSMapSupport;
import com.jaspersoft.studio.widgets.map.support.JavaMapSupport;
import com.jaspersoft.studio.widgets.map.support.MapCredentials;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;

/**
 * A tile containing the map widget. The browser can be further customized via
 * protected methods.
 * <p>
 * 
 * This implementation provides basic support for Java/Javascript communication.
 * The browser functions used in here are related to the basic operations like:
 * map type, zoom level and center update.
 * <p>
 * 
 * This is the correct order of invocations:
 * <ol>
 * <li>constructor (object creation);</li>
 * <li>{@link #configureJavaSupport(BaseJavaMapSupport)} (not mandatory);</li>
 * <li>{@link #configureJavascriptSupport(BaseJSMapSupport)} (not mandatory);
 * </li>
 * <li>{@link #configureFunctions(List)} (not mandatory);</li>
 * <li>{@link #activateMapTile()}.</li>
 * </ol>
 * For every non-mandatory method there is a default fallback that is checked in
 * the {@link #activateMapTile()}.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class MapTile {

	private static final String MAP_JS_API_URL = "https://maps.googleapis.com/maps/api/js"; //$NON-NLS-1$
	private List<GMapEnabledFunction> functions;
	private JSMapSupport jsMapSupp;
	private JavaMapSupport javaMapSupp;
	protected Browser mapControl;
	private String mapURL;
	private String apiKey;
	private Path tmpMapDirectory;

	public MapTile(Composite parent, int style, MapCredentials credentials) {
		this(parent, style, MapActivator.getFileLocation("mapfiles/gmaps_library/map.html"), credentials); //$NON-NLS-1$
	}

	public MapTile(Composite parent, int style, String mapURL, MapCredentials credentials) {
		createBrowser(parent, style);
		addListeners();
		this.mapURL = mapURL;
		this.apiKey = credentials!=null ? credentials.getApiKey() : null;
	}

	protected void createBrowser(Composite parent, int style) {
		mapControl = new Browser(parent, style | SWT.BORDER);
		mapControl.addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				event.doit = false;
			}
		});
		if(MapUIUtils.isGoogleMapWidgetDisabled()) {
			String disabledMapURL = MapActivator.getFileLocation("mapfiles/gmaps_library/map_disabled.html"); //$NON-NLS-1$
			mapControl.setUrl(fixMapURL(disabledMapURL));
			mapControl.setJavascriptEnabled(false);
		}
	}

	/**
	 * Add custom listeners to the browser widget containing the map.
	 */
	protected void addListeners() {
	}

	public void configureJavaSupport(BaseJavaMapSupport javaSupport) {
		if (javaMapSupp == null) {
			javaMapSupp = javaSupport;
		} else {
			throw new RuntimeException(Messages.MapTile_JavaSupportAlreadyDefinedError);
		}

	}

	public void configureJavascriptSupport(BaseJSMapSupport jsSupport) {
		if (jsMapSupp == null) {
			jsMapSupp = jsSupport;
		} else {
			throw new RuntimeException(Messages.MapTile_JavascriptSupportAlreadyDefined);
		}
	}

	public void configureFunctions(List<GMapEnabledFunction> functs) {
		functions = new ArrayList<GMapEnabledFunction>(functs);
	}

	public void setLayoutData(Object layoutData) {
		mapControl.setLayoutData(layoutData);
	}

	public JSMapSupport getJavascriptMapSupport() {
		if (this.jsMapSupp == null) {
			this.jsMapSupp = new BaseJSMapSupport(mapControl);
		}
		return this.jsMapSupp;
	}

	public boolean hasJavaMapSupport() {
		return javaMapSupp != null;
	}

	public JavaMapSupport getJavaMapSupport() {
		if (this.javaMapSupp == null) {
			this.javaMapSupp = new BaseJavaMapSupport(mapControl);
		}
		return this.javaMapSupp;
	}

	public List<GMapEnabledFunction> getFunctions() {
		if (this.functions == null) {
			functions = new ArrayList<GMapEnabledFunction>(4);
			functions.add(new TestJavaCallSupport(mapControl, MapWidgetConstants.BROWSER_FUNCTION_TEST_JAVACALL_SUPPORT,
					getJavaMapSupport()));
			functions.add(new UpdateZoomLevel(mapControl, MapWidgetConstants.BROWSER_FUNCTION_UPDATE_ZOOM_LEVEL,
					getJavaMapSupport()));
			functions.add(new UpdateMapCenter(mapControl, MapWidgetConstants.BROWSER_FUNCTION_UPDATE_MAP_CENTER,
					getJavaMapSupport()));
			functions.add(new UpdateMapType(mapControl, MapWidgetConstants.BROWSER_FUNCTION_UPDATE_MAP_TYPE,
					getJavaMapSupport()));
		}
		return this.functions;
	}

	public Browser getMapControl() {
		return this.mapControl;
	}

	public void activateMapTile() {
		// Safe check for default initialization
		getJavaMapSupport();
		getJavascriptMapSupport();
		getFunctions();
		
		if(!MapUIUtils.isGoogleMapWidgetDisabled()) {
			mapControl.addProgressListener(new ProgressAdapter() {
				@Override
				public void completed(ProgressEvent event) {
					mapControl.evaluate("mapSetup();",true); //$NON-NLS-1$
				}
			});
			
			// Sets the URL on the browser
			if(apiKey!=null) {
				try {
					File origMapFile = new File(mapURL);
					tmpMapDirectory = Files.createTempDirectory("gmaplibrary"); //$NON-NLS-1$
					FileUtils.copyDirectory(origMapFile.getParentFile(), tmpMapDirectory.toFile());
					String origMapHtmlCode = Files.readString(origMapFile.toPath());
					File tmpMapFile = Files.createTempFile(tmpMapDirectory, "map", ".html").toFile(); //$NON-NLS-1$ //$NON-NLS-2$
					origMapHtmlCode = origMapHtmlCode.replace(MAP_JS_API_URL, MAP_JS_API_URL+ "?key="+apiKey); //$NON-NLS-1$
					Files.writeString(tmpMapFile.toPath(), origMapHtmlCode);
					mapControl.setUrl(fixMapURL(tmpMapFile.getAbsolutePath()));
				} catch (IOException e) {
					MapActivator.logError(Messages.MapTile_ErrorMsgBadApiKey, e);
					UIUtils.showError(Messages.MapTile_ErrorMsgBadApiKey, e);
					mapControl.setUrl(fixMapURL(mapURL));
				}
			}
			else {
				mapControl.setUrl(fixMapURL(mapURL));
			}
		}
	}
	
	private String fixMapURL(String mapURL) {
		if(UIUtils.isWindows()) {
			mapURL = "file:///" + mapURL.replace("\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return mapURL;
	}
	
	/**
	 * Should be manually invoked when discarding the map tile (i.e dialogs or panels dispose).
	 * It cleans up possible temporary files and more.
	 */
	public void dispose() {
		if(tmpMapDirectory!=null) {
			try {
				FileUtils.deleteDirectory(tmpMapDirectory.toFile());
			} catch (IOException e) {
				MapActivator.logError(Messages.MapTile_ErrorTemporaryDirectoryDelete, e);
			}
		}
		if(mapControl!=null) {
			mapControl.dispose();
		}
	}
}