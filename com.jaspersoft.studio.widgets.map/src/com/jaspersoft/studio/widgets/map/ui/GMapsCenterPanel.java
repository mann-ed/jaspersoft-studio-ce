/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.widgets.map.ui;

import org.eclipse.jface.util.Util;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.utils.NumberValidator;
import com.jaspersoft.studio.widgets.map.MapWidgetConstants;
import com.jaspersoft.studio.widgets.map.browserfunctions.GMapEnabledFunction;
import com.jaspersoft.studio.widgets.map.core.LatLng;
import com.jaspersoft.studio.widgets.map.core.MapType;
import com.jaspersoft.studio.widgets.map.support.BaseJavaMapSupport;
import com.jaspersoft.studio.widgets.map.support.GMapUtils;
import com.jaspersoft.studio.widgets.map.support.JavaMapSupport;
import com.jaspersoft.studio.widgets.map.support.MapCredentials;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.Misc;

/**
 * This class implements the support for the Google Map component. The panel
 * that is shown contains:
 * <ul>
 * <li>a browser with the Google Maps component loaded</li>
 * <li>an additional panel with controls that allows the interaction with the
 * Google Maps in the browser</li>
 * </ul>
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 */
public class GMapsCenterPanel {

	protected MapTile map;

	protected LatLng mapCenter = new LatLng(45.439722, 12.331944);
	protected int zoomLevel = 12;
	protected MapType mapType = MapType.ROADMAP;
	protected String address;
	protected Text addressBar;
	protected MapCredentials mapCredentials;

	/**
	 * Creates a new panel containing the controls to work with a Google Maps
	 * component presented inside a browser instance.
	 * 
	 * @param parent
	 *            a composite control which will be the parent of the new instance
	 *            (cannot be null)
	 * @param style
	 *            the style of widget to construct
	 * @param mapCredentials credentials (api key) for the Google Map component
	 */
	public GMapsCenterPanel(Composite parent, int style, MapCredentials mapCredentials) {
		this.mapCredentials = mapCredentials;
		createContent(parent, style);
	}

	protected void createContent(Composite parent, int style) {
		createTop(parent);
		if (Util.isLinux()) {
			Composite warningCmp = MapUIUtils.createLinuxWarningText(parent);
			warningCmp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		}
		createMap(parent);
		UIUtils.getDisplay().asyncExec(() -> map.activateMapTile());
	}

	protected void createMap(Composite parent) {
		map = new MapTile(parent, SWT.NONE, mapCredentials);
		map.configureJavaSupport(new DetailsPanelMapSupport(map.getMapControl()));
		map.getFunctions().add(new InitialConfigurationFunction(map.getMapControl(),
				MapWidgetConstants.BROWSER_FUNCTION_INITIAL_CONFIGURATION, map.getJavaMapSupport()));
		if (parent.getLayout() instanceof GridLayout) {
			map.setLayoutData(new GridData(GridData.FILL_BOTH));
		}
		map.getMapControl().addProgressListener(new ProgressAdapter() {
			@Override
			public void completed(ProgressEvent event) {
				map.getJavascriptMapSupport().evaluateJavascript("MENU_KIND=_MENU_MINIMAL");
			}
		});
	}

	protected void createTop(Composite parent) {
		Composite cmp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(7, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.marginRight = -5;
		cmp.setLayout(layout);

		if (parent.getLayout() instanceof GridLayout) {
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 2;
			cmp.setLayoutData(gd);
		}

		final Text tadr = new Text(cmp, SWT.SEARCH | SWT.ICON_SEARCH | SWT.BORDER);
		tadr.setMessage("Address");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		tadr.setLayoutData(gd);
		tadr.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				doAddressChanged(tadr);
			}
		});
		tadr.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_RETURN || e.keyCode == SWT.CR) {
				doAddressChanged(tadr);
				e.doit = false;
			}
		});
		tadr.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String txt = tadr.getText();
				if (!Misc.isNullOrEmpty(txt) && !txt.equals(address))
					doAddressChanged(tadr);
			}
		});

		Label lbl = new Label(cmp, SWT.NONE);
		lbl.setText("Latitude");

		tlat = new Text(cmp, SWT.BORDER);
		gd = new GridData();
		gd.widthHint = 100;
		tlat.setLayoutData(gd);
		tlat.addVerifyListener(new NumberValidator(Float.parseFloat("-85"), Float.parseFloat("85"), Float.class));
		tlat.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_RETURN || e.keyCode == SWT.CR) {
				doLatChange(tadr);
				e.doit = false;
			}
		});
		tlat.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				String txt = tlat.getText();
				if (!Misc.isNullOrEmpty(txt) && mapCenter != null && mapCenter.getLat() != null
						&& !txt.equals(String.format("%.7f", mapCenter.getLat())))
					doLatChange(tadr);
			}

		});

		lbl = new Label(cmp, SWT.NONE);
		lbl.setText("Longitude");

		tlon = new Text(cmp, SWT.BORDER);
		gd = new GridData();
		gd.widthHint = 100;
		tlon.setLayoutData(gd);
		tlon.addVerifyListener(new NumberValidator(Float.parseFloat("-180"), Float.parseFloat("180"), Float.class));
		tlon.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_RETURN || e.keyCode == SWT.CR) {
				doLonChange(tadr);
				e.doit = false;
			}
		});
		tlon.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				String txt = tlon.getText();
				if (!Misc.isNullOrEmpty(txt) && mapCenter != null && mapCenter.getLng() != null
						&& !txt.equals(String.format("%.7f", mapCenter.getLng())))
					doLonChange(tadr);
			}

		});
	}

	public void initMap() {
		
	}

	/**
	 * Browser function for correctly configuring the initial settings of the map.
	 * 
	 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
	 * 
	 */
	class InitialConfigurationFunction extends GMapEnabledFunction {

		public InitialConfigurationFunction(Browser browser, String name, JavaMapSupport mapSupport) {
			super(browser, name, mapSupport);
		}

		@Override
		public Object function(Object[] arguments) {
			refresh();
			return null;
		}

	}

	private boolean centering = false;

	protected void centerMap(LatLng coords) {
		if (coords == null)
			return;
		LatLng lastCoords = coords;
		if (centering)
			return;
		centering = true;
		try {
			setMapCenter(lastCoords);
			lastCoords = null;
			tlon.setText(String.format("%.7f", mapCenter.getLng()));
			tlat.setText(String.format("%.7f", mapCenter.getLat()));
			map.getJavascriptMapSupport().evaluateJavascript("myMap.panTo(" + mapCenter.toJsString() + "); ");
		} finally {
			centering = false;
			centerMap(lastCoords);
		}
	}

	protected boolean initMarkers = false;
	protected Text tlon;
	protected Text tlat;
	protected boolean refreshing = false;

	protected class DetailsPanelMapSupport extends BaseJavaMapSupport {

		DetailsPanelMapSupport(Browser browser) {
			super(browser);
		}

		@Override
		public void setMapType(MapType mapType) {
			if (initMarkers)
				return;
			super.setMapType(mapType);
			handleMapTypeChanged(mapType);
		}

		@Override
		public void setZoomLevel(int newZoomLevel) {
			if (initMarkers)
				return;
			super.setZoomLevel(newZoomLevel);
			handleMapZoomChanged(newZoomLevel);
		}

		@Override
		public void setMapCenter(LatLng position) {
			if (initMarkers)
				return;
			super.setMapCenter(position);
			handleMapCenterChanged(position);
		}
	}

	protected void handleMapTypeChanged(MapType mapType) {
		// nothing to do here
	}

	protected void handleMapZoomChanged(int newZoomLevel) {
		// nothing to do here
	}

	protected void handleMapCenterChanged(LatLng position) {
		if (refreshing)
			return;
		refreshing = true;
		try {
			tlon.setText(String.format("%.7f", position.getLng()));
			tlat.setText(String.format("%.7f", position.getLat()));
		} finally {
			refreshing = false;
		}
	}

	protected void handleAddressChanged(String address) {
		// nothing to do here
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
		if (addressBar != null)
			addressBar.setText(address);
	}

	public LatLng getMapCenter() {
		return mapCenter;
	}

	public void setMapCenter(LatLng mapCenter) {
		this.mapCenter = mapCenter;
	}

	public MapType getMapType() {
		return mapType;
	}

	public void setMapType(MapType mapType) {
		this.mapType = mapType;
	}

	public int getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(int zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	private boolean initialised = false;

	public void refresh() {
		UIUtils.getDisplay().asyncExec(() -> {
			initMarkers = true;
			try {
				initMap();
				if (!initialised) {
					map.getJavascriptMapSupport().setZoomLevel(getZoomLevel());
					map.getJavascriptMapSupport().setMapType(mapType != null ? mapType : MapType.ROADMAP);
					if ((mapCenter == null || mapCenter.getLat() == null || mapCenter.getLng() == null)) {
						if (address != null && !address.isEmpty()) {
							LatLng coords = GMapUtils.getAddressCoordinates(address, mapCredentials);
							if (coords != null)
								centerMap(coords);
						}
					} else
						centerMap(mapCenter);
					initialised = true;
				}
				postInitMap();
			} finally {
				initMarkers = false;
			}
		});
	}

	protected void postInitMap() {

	}

	protected void doAddressChanged(final Text tadr) {
		UIUtils.getDisplay().asyncExec(() -> {
			refreshing = true;
			try {
				LatLng coords = GMapUtils.getAddressCoordinates(tadr.getText(), mapCredentials);
				if (coords != null)
					centerMap(coords);
			} finally {
				refreshing = false;
			}
		});
	}

	protected void doLatChange(final Text tadr) {
		UIUtils.getDisplay().asyncExec(() -> {
			if (refreshing || centering)
				return;
			String txt = tlat.getText();
			if (txt.isEmpty())
				return;
			refreshing = true;
			try {
				Double d = Double.valueOf(txt);
				centerMap(new LatLng(d, mapCenter.getLng()));
				address = null;
				tadr.setText("");
			} finally {
				refreshing = false;
			}
		});
	}

	protected void doLonChange(final Text tadr) {
		UIUtils.getDisplay().asyncExec(() -> {
			if (refreshing || centering)
				return;
			String txt = tlon.getText();
			if (txt.isEmpty())
				return;
			refreshing = true;
			try {
				Double d = Double.valueOf(txt);
				centerMap(new LatLng(mapCenter.getLat(), d));
				address = null;
				tadr.setText("");
			} finally {
				refreshing = false;
			}
		});
	}
	
	public void dispose() {
		if(map!=null) {
			map.dispose();
		}
	}
}
