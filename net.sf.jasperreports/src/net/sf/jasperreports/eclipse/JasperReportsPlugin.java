/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wb.swt.ResourceManager;
import org.osgi.framework.BundleContext;

import net.sf.jasperreports.eclipse.classpath.container.ClasspathContainerManager;

/*
 * The main plugin class to be used in the desktop.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JasperCompileManager.java 1229 2006-04-19 13:27:35 +0300 (Wed, 19 Apr 2006) teodord $
 */
public class JasperReportsPlugin extends AbstractJRUIPlugin {

	// The shared instance.
	private static JasperReportsPlugin plugin;
	// The unique plug-in identifier
	public static final String PLUGIN_ID = "net.sf.jasperreports"; //$NON-NLS-1$

	private static ClasspathContainerManager classpathContainerManager;
	
	/**
	 * Listener on the key press event
	 */
	private static List<IKeyboardEvent> events = new ArrayList<IKeyboardEvent>();
	
	/**
	 * Map to keeping track if a key is held down
	 */
	private static HashSet<Integer> pressedKeys = new HashSet<Integer>();
	
	/**
	 * Map to keeping track of the position where the mouse pointer was when a specific
	 * mouse button was pressed. The key is the index of the button
	 */
	private static HashMap<Integer, Point> mousePressLocation = new HashMap<Integer, Point>();

	/**
	 * Listener called when a key is pressed
	 */
	private static Listener keyDownListener = null;

	/**
	 * Listener called when a key is released
	 */
	private static Listener keyUpListener = null;
	
	/**
	 * Listener when the application loose the focus, clear every key modifier in this case
	 */
	private static Listener focusLostListener = null;
	
	/**
	 * Listener used when a mouse button is pressed, it will register its position
	 */
	private static Listener mousePressListener = null;

	/**
	 * The constructor.
	 */
	public JasperReportsPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * Initialize the listeners, this method need to be called only once and when
	 * the workbench and the display are available. All the calls after the first
	 * one dosen't do anything
	 */
	public static void initializeKeyListener() {
		
		if (keyDownListener == null && keyUpListener == null && focusLostListener == null && mousePressListener == null) {
			keyDownListener = new Listener() {
				public void handleEvent(Event e) {
					pressedKeys.add(e.keyCode);
					fireEvent(true, e.keyCode);
				}
			};

			keyUpListener = new Listener() {
				public void handleEvent(Event e) {
					pressedKeys.remove(e.keyCode);
					fireEvent(false, e.keyCode);
				}
			};
			
			focusLostListener = new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					pressedKeys.clear();
					fireEvent(false, SWT.DEFAULT);
				}
			}; 
			
			mousePressListener = new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					Display display = PlatformUI.getWorkbench().getDisplay();
					Point point = display.getCursorLocation();
					mousePressLocation.put(event.button, point);
				}
			};
			

			PlatformUI.getWorkbench().getDisplay().addFilter(org.eclipse.swt.SWT.KeyDown, keyDownListener);
			PlatformUI.getWorkbench().getDisplay().addFilter(org.eclipse.swt.SWT.KeyUp, keyUpListener);
			PlatformUI.getWorkbench().getDisplay().addFilter(org.eclipse.swt.SWT.FocusOut, focusLostListener);
			PlatformUI.getWorkbench().getDisplay().addFilter(org.eclipse.swt.SWT.MouseDown, mousePressListener);
		}
	}

	/**
	 * Check if a key is held down or not
	 * 
	 * @param keyCode
	 *          an SWT keycode
	 * @return true if the key is held down, otherwise false
	 */
	public static boolean isPressed(int keyCode) {
		return pressedKeys.contains(keyCode);
	}
	
	/**
	 * Return the number of keys currently pressed
	 */
	public static int getPressedKeysNumber() {
		return pressedKeys.size();
	}
	
	/**
	 * Return an array of integer representing the id of the key currently
	 * pressed
	 * 
	 * @return a not null array of integer
	 */
	public static Integer[] getPressedKeys(){
		return pressedKeys.toArray(new Integer[pressedKeys.size()]);
	}
	
	/**
	 * Return the position of the mouse pointer the last time a specific
	 * mouse button was pressed
	 * 
	 * @param mouseButton the index of the mouse button
	 * @return the last position of the pointer when the specified button was pressed
	 * or null if it was never pressed
	 */
	public static Point getLastClickLocation(int mouseButton){
		return mousePressLocation.get(mouseButton);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
		events.clear();
		if (keyDownListener != null && keyUpListener != null && !PlatformUI.getWorkbench().getDisplay().isDisposed()) {
			PlatformUI.getWorkbench().getDisplay().removeFilter(org.eclipse.swt.SWT.KeyDown, keyDownListener);
			PlatformUI.getWorkbench().getDisplay().removeFilter(org.eclipse.swt.SWT.KeyUp, keyUpListener);
			PlatformUI.getWorkbench().getDisplay().removeFilter(org.eclipse.swt.SWT.FocusOut, focusLostListener);
			PlatformUI.getWorkbench().getDisplay().removeFilter(org.eclipse.swt.SWT.MouseDown, mousePressListener);
		}
		// Invoke the dispose of all resources handled by the generic SWT manager
		ResourceManager.dispose();
	}
	
	/**
	 * Add a key listener that will be fired when a keyboard key is pressed
	 * or released
	 * 
	 * @param event the event to add, and event null or already present
	 * will be not added to the list
	 */
	public static void addKeyListener(IKeyboardEvent event) {
		if (event != null && !events.contains(event)) {
			events.add(event);
		}
	}
	
	/**
	 * Remove a key listener that will be fired when a keyboard key is pressed
	 * or released
	 * 
	 * @param event the event to remove
	 * @return true if the event was removed successfully, false otherwise
	 */
	public static boolean removeKeyListener(IKeyboardEvent event) {
		return events.remove(event);
	}
	
	/**
	 * Fire all the keypress event registered
	 * 
	 * @param isKeyDown true if the event was a key down one, false if it
	 * was a key up
	 * @param keycode the code of the pressed key
	 */
	protected static void fireEvent(boolean isKeyDown, int keycode) {
		if(isKeyDown) {
			for(IKeyboardEvent event : events) {
				event.keyDown(keycode);
			}
		} else {
			for(IKeyboardEvent event : events) {
				event.keyUp(keycode);
			}
		}
	}

	/**
	 * Returns the shared instance.
	 */
	public static JasperReportsPlugin getDefault() {
		return plugin;
	}

	@Override
	public String getPluginID() {
		return PLUGIN_ID;
	}

	public static ClasspathContainerManager getClasspathContainerManager() {
		if (classpathContainerManager == null) {
			classpathContainerManager = new ClasspathContainerManager();
			classpathContainerManager.init();
		}
		return classpathContainerManager;
	}
}
