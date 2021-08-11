/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.property.color.chooser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.wb.swt.ResourceCache;

import com.jaspersoft.studio.utils.UIUtil;

/**
 * This widget show a square area with aside a rectangle area. The user can
 * click in the square area to select a color and in the rectangle area to
 * change the colors patter inside the squared area. The colors inside the
 * square area and rectangle area are defined from external governor
 * 
 * @author OrlandinMarco
 *
 */
public class ColorsSelectorWidget extends Composite {

	/**
	 * Last point selected by the user in the squared area
	 */
	private Point circlePosition = new Point(0, 0);

	/**
	 * Last point selected by the user in the rectangle area
	 */
	private int sliderPosition = 0;

	/**
	 * Color actually selected in the square area
	 */
	private RGB selectedColorRGB = null;

	/**
	 * Color actually selected in the square area as HSB (to avoid precision
	 * error both rgb and hsb are keep)
	 */
	private float[] selectedColorHSB = new float[3];

	/**
	 * COmposite where the square area is placed
	 */
	private Composite colorComposite;

	/**
	 * Composite where the rectangle area is placed
	 */
	private Composite slider;

	/**
	 * Governor that define the color content of both the square and rectangle
	 * areas
	 */
	private IWidgetGovernor governor;

	/**
	 * Width of the arrows painted in the rectangle area to show the selected
	 * point
	 */
	private int arrowWidth = 5;

	/**
	 * Border color of the rectangle area
	 */
	private Color borderColor = getDisplay().getSystemColor(SWT.COLOR_WIDGET_BORDER);

	/**
	 * Listeners called when the user select a point in the square area
	 */
	private List<SelectionListener> selListeners = new ArrayList<SelectionListener>();
	
	/**
	 * Cache of the color samples images, that will be disposed when all
	 * the other components are disposed
	 */
	private ResourceCache imagesCache = new ResourceCache();
	
	/**
	 * Cache image for the main selector
	 */
	private Image canvasCache = null;
	
	/**
	 * Cache image for the slider
	 */
	private Image sliderCache = null;

	/**
	 * Create a composite with inside all the widgets
	 * 
	 * @param parent parent of the composite
	 * @param style style of the composite
	 * @param governor governor to define the content inside the square and
	 * rectangle areas
	 */
	public ColorsSelectorWidget(Composite parent, int style, IWidgetGovernor governor, RGB initialColor) {
		super(parent, style);
		this.governor = governor;
		selectedColorRGB = initialColor;
		selectedColorHSB = initialColor.getHSB();
		setBackground(UIUtil.getColor(JFacePreferences.INFORMATION_BACKGROUND_COLOR));
		setLayout(new GridLayout(2, false));
		colorComposite = new Canvas(this, SWT.BORDER);
		colorComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		slider = new Canvas(this, SWT.NONE);
		// When the rectangle area is resized its content, and also the
		// rectangle one are refreshed
		slider.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				setSelectedColor(getSelectedColorRGB(), false);
				paintSlider(true);
			}
		});
		colorComposite.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				setSelectedColor(getSelectedColorRGB(), false);
				paintPad(true);
			}
		});

		GridData rangeData = new GridData(SWT.LEFT, SWT.FILL, false, true);
		rangeData.widthHint = 20;
		slider.setLayoutData(rangeData);
		// set on the square area the mouse click listener to select a color
		colorComposite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				padClicked(e);
			}

		});

		// set on the square area the mouse listener to select a color when the
		// mouse is
		// moved with the left key pressed
		colorComposite.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				if ((e.stateMask & SWT.BUTTON1) != 0) {
					padClicked(e);
				}
			}
		});
		colorComposite.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				if (canvasCache != null && !canvasCache.isDisposed()) {
					gc.drawImage(canvasCache, 0, 0);
				    if (circlePosition != null){
				    	gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
				    	gc.setAntialias(SWT.ON);
				    	gc.drawOval(circlePosition.x-2, circlePosition.y-2, 8, 8);
				    	gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
				    	gc.drawOval(circlePosition.x-1, circlePosition.y-1, 6, 6);
				    }
				}
			    gc.dispose();
			}
		});
		// set on the rectangle area the mouse click listener to update the
		// square area
		slider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				sliderClicked(e);
			}
		});
		// set on the rectangle area the mouse listener to update the square
		// area
		// when the mouse is moved with the left key pressed
		/*slider.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				if ((e.stateMask & SWT.BUTTON1) != 0) {
					sliderClicked(e);
				}
			}
		});*/
		slider.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				Rectangle rect = slider.getBounds();
				int width = rect.width;
				GC gc = e.gc;
				if (sliderCache != null && !sliderCache.isDisposed()) {
					gc.drawImage(sliderCache, 0, 0);
					gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
					gc.fillPolygon(new int[] { 0, sliderPosition - 3, arrowWidth, sliderPosition, 0, sliderPosition + 3 });
					gc.fillPolygon(new int[] { width, sliderPosition - 3, width - arrowWidth, sliderPosition, width, sliderPosition + 3 });
				}
				gc.dispose();
			}
		});
	}

	/**
	 * Set the actually selected color using the hsb format. the selection is
	 * reflected also into the square and rectangle area, showing the correct
	 * selected point
	 * 
	 * @param h hue
	 * @param s saturation
	 * @param b brightness
	 * @param callListener true if the selection listeners registered should be
	 * called, false otherwise
	 */
	public void setSelectedColor(float h, float s, float b, boolean callListener) {
		if (h < 0 || h > 360 || s < 0 || s > 1 || b < 0 || b > 1)
			return;
		RGB color = new RGB(h, s, b);
		int[] relativePositions = governor.getXYSlider(new float[] { h, s, b });
		int relativeSlide = relativePositions[2];
		int newSliderPosition = getAbsoluteSliderFromRelative(relativeSlide);
		if (sliderPosition != newSliderPosition) {
			sliderPosition = newSliderPosition;
		}
		int relativeX = relativePositions[0];
		int relativeY = relativePositions[1];
		Point newCirclePosition = new Point(getAbsoluteXFromRelative(relativeX), getAbsoluteYFromRelative(relativeY));
		if (circlePosition != newCirclePosition) {
			circlePosition = new Point(getAbsoluteXFromRelative(relativeX), getAbsoluteYFromRelative(relativeY));
		}
		selectedColorRGB = color;
		selectedColorHSB = new float[] { h, s, b };
		paintSlider(false);
		paintPad(false);
		if (callListener)
			callSelectionListeners();
	}

	/**
	 * Set the actually selected color using the RGB format. the selection is
	 * reflected also into the square and rectangle area, showing the correct
	 * selected point
	 * 
	 * @param color the color as RGB
	 * @param callListener true if the selection listeners registered should be
	 * called, false otherwise
	 */
	public void setSelectedColor(RGB color, boolean callListener) {
		if (color == null)
			return;
		int[] relativePositions = governor.getXYSlider(color);
		int relativeSlide = relativePositions[2];
		int newSliderPosition = getAbsoluteSliderFromRelative(relativeSlide);
		if (sliderPosition != newSliderPosition) {
			sliderPosition = newSliderPosition;
		}
		int relativeX = relativePositions[0];
		int relativeY = relativePositions[1];
		Point newCirclePosition = new Point(getAbsoluteXFromRelative(relativeX), getAbsoluteYFromRelative(relativeY));
		if (circlePosition != newCirclePosition) {
			circlePosition = new Point(getAbsoluteXFromRelative(relativeX), getAbsoluteYFromRelative(relativeY));
		}
		selectedColorRGB = color;
		selectedColorHSB = color.getHSB();
		paintSlider(false);
		paintPad(false);
		if (callListener)
			callSelectionListeners();
	}

	/**
	 * Return the actual selected color as RGB
	 * 
	 * @return an RGB color
	 */
	public RGB getSelectedColorRGB() {
		return selectedColorRGB;
	}

	/**
	 * Return the actual selected color as HSB
	 * 
	 * @return an array of 3 float representing the hsb values of the color
	 */
	public float[] getSelectedColorHSB() {
		return selectedColorHSB;
	}

	/**
	 * Set a new governor and refresh the content of the square and rectangle
	 * area. Since the color under the selection change all the selection
	 * listener are called
	 * 
	 * @param newGovernor the new governor
	 */
	public void setGovernor(IWidgetGovernor newGovernor) {
		this.governor = newGovernor;
		paintPad(true);
		paintSlider(true);
		callSelectionListeners();
	}

	/**
	 * Set the selection point in the rectangle area with a relative value. the
	 * value must be between the range of max and min of the actual governor
	 * 
	 * @param slider
	 */
	public void setSlider(int slider) {
		this.sliderPosition = slider;
		paintPad(true);
		paintSlider(false);
	}

	/**
	 * Add a selection listener that will be called when the user click the
	 * square area, passing in the event this widget and the new color selected
	 * by the user as RGB (inside the data field of the event)
	 */
	public void addSelectionListener(SelectionListener listener) {
		selListeners.add(listener);
	}

	/**
	 * dispose the control and the last image used into the square and rectangle
	 * area
	 */
	@Override
	public void dispose() {
		super.dispose();
		imagesCache.dispose();
		if (canvasCache != null && !canvasCache.isDisposed()){
			canvasCache.dispose();
		}
		if (sliderCache != null && !sliderCache.isDisposed()){
			sliderCache.dispose();
		}
	}

	/**
	 * Handler called when the square area is clicked or the mouse is moved with
	 * the left button clicked
	 * 
	 * @param e mouse move event
	 */
	private void padClicked(MouseEvent e) {
		updatePosition(e);
		paintPad(false);
		paintSlider(false);
		setSelectedColorFromImage();
		callSelectionListeners();
	}

	/**
	 * Handler called when the rectangle area is clicked or the mouse is moved
	 * with the left button clicked
	 * 
	 * @param e mouse move event
	 */
	private void sliderClicked(MouseEvent e) {
		updateSlider(e);
		setSelectedColorFromImage();
		paintSlider(false);
		paintPad(true);
		callSelectionListeners();
	}

	/**
	 * Set the actual selected color taking it from the last point clicked by
	 * the user in the square area
	 */
	private void setSelectedColorFromImage() {
		Rectangle bounds = colorComposite.getBounds();
		float factorX = (float)governor.getPadMaxX() / bounds.width;
		float factorY = (float)governor.getPadMaxY() / bounds.height;
		int slider = Math.round(((float) governor.getSliderMax() / getSliderHeight()) * sliderPosition);
		RGB color = governor.getPadColor(Math.round(circlePosition.x * factorX), Math.round(circlePosition.y * factorY), slider);
		selectedColorRGB = color;
		selectedColorHSB = selectedColorRGB.getHSB();	
	}

	/**
	 * When the user click the square are this call all the register listeners,
	 * passing in the event this widget and the new color selected by the user
	 * as RGB (inside the data field of the event)
	 */
	private void callSelectionListeners() {
		Event e = new Event();
		e.widget = this;
		SelectionEvent event = new SelectionEvent(e);
		event.data = selectedColorRGB;
		for (SelectionListener listener : selListeners) {
			listener.widgetSelected(event);
		}
	}

	/**
	 * Called when the square area is clicked, register the click position
	 * 
	 * @param e MouseEvent
	 */
	private void updatePosition(MouseEvent e) {
		Rectangle rect = colorComposite.getClientArea();
		int x = Math.max(0, e.x);
		x = Math.min(x, rect.width - 1);
		int y = Math.max(0, e.y);
		y = Math.min(y, rect.height - 1);
		circlePosition = new Point(x, y);
	}

	/**
	 * Called when the rectangle area is clicked, register the click position
	 * 
	 * @param e MouseEvent
	 */
	private void updateSlider(MouseEvent e) {
		Rectangle rect = slider.getClientArea();
		int y = Math.max(0, e.y);
		y = Math.min(y, rect.height);
		sliderPosition = y;
	}

	/**
	 * Translate a relative x position from the governor to an x coordinate
	 * inside the square component
	 * 
	 * @param relativeX x provided from the governor
	 * @return x coordinate inside the square area
	 */
	private int getAbsoluteXFromRelative(int relativeX) {
		Rectangle rect = colorComposite.getClientArea();
		if (rect.width == 0)
			return 0;
		int padWidth = governor.getPadMaxX() - governor.getPadMinX();
		return (relativeX * (rect.width - 1)) / padWidth;
	}

	/**
	 * Translate a relative y position from the governor to an y coordinate
	 * inside the square component
	 * 
	 * @param relativeY y provided from the governor
	 * @return y coordinate inside the square area
	 */
	private int getAbsoluteYFromRelative(int relativeY) {
		Rectangle rect = colorComposite.getClientArea();
		if (rect.height == 0)
			return 0;
		int padHeight = governor.getPadMaxY() - governor.getPadMinY();
		return (relativeY * (rect.height - 1)) / padHeight;
	}

	/**
	 * Translate a relative y position from the governor to an y coordinate
	 * inside the rectangle component. There is no need for x since it is
	 * constant inside this area
	 * 
	 * @param relativeSlider y provided from the governor
	 * @return y coordinate inside the rectangle area
	 */
	private int getAbsoluteSliderFromRelative(int relativeSlider) {
		Rectangle rect = slider.getClientArea();
		if (rect.height == 0)
			return 0;
		int sliderHeight = governor.getSliderMax() - governor.getSliderMin();
		return (relativeSlider * (rect.height - 1)) / sliderHeight;
	}


	/**
	 * Return the height of the rectangle area
	 * 
	 * @return int representing the actual height of the rectangle area
	 */
	private int getSliderHeight() {
		Rectangle sliderRect = slider.getClientArea();
		return sliderRect.height;
	}

	/**
	 * Return the width of the square area
	 * 
	 * @return int representing the actual width of the square area
	 */
	private int getPadWidth() {
		Rectangle padRect = colorComposite.getClientArea();
		return padRect.width;
	}

	/**
	 * Return the height of the square area
	 * 
	 * @return int representing the actual height of the square area
	 */
	private int getPadHeight() {
		Rectangle padRect = colorComposite.getClientArea();
		return padRect.height;
	}
	
	/**
	 * Generate the image for the canvas
	 * 
	 * @param gc
	 * @param width
	 * @param height
	 */
	private void paintCanvas(GC gc, int width, int height) {
		int padWidth = governor.getPadMaxX() - governor.getPadMinX();
		int padHeight = governor.getPadMaxY() - governor.getPadMinY();
		// Calculate the actual slider
		int[] relativePositions = governor.getXYSlider(selectedColorRGB);
		int slider = Math.round(relativePositions[2]);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				float padX = ((float) (i * padWidth)) / (width - 1);
				float padY = ((float) (j * padHeight)) / (height - 1);
				gc.setForeground(imagesCache.getColor(governor.getPadColor(Math.round(padX), Math.round(padY), slider)));
				gc.drawPoint(i, j);
			}
		}
	}
	
	/**
	 * Generate the image for the slider
	 * 
	 * @param gc
	 * @param width
	 * @param height
	 */
	private void paintSlider(GC gc, int width, int height) {
		gc.setForeground(borderColor);
		gc.drawRectangle(arrowWidth, 0, width - arrowWidth * 2, height - 1);
		
		int padWidth = governor.getPadMaxX() - governor.getPadMinX();
		int padHeight = governor.getPadMaxY() - governor.getPadMinY();
		float padX = ((float) (circlePosition.x * padWidth)) / (getPadWidth() - 1);
		float padY = ((float) (circlePosition.y * padHeight)) / (getPadHeight() - 1);
		int sliderHeight = governor.getSliderMax() - governor.getSliderMin();

		for (int y = 0; y < height; y++) {
			float actaulSlider = ((float) sliderHeight / getSliderHeight()) * y;
			RGB actualHueColor = governor.getSliderColor(Math.round(padX), Math.round(padY), Math.round(actaulSlider));
			for (int x = 0; x < width; x++) {
				gc.setForeground(imagesCache.getColor(actualHueColor));
				gc.drawPoint(x, y);
			}
		}
	}

	/**
	 * Put the appropriate image inside the rectangle area. If the image data of
	 * the image it is cached it will be used, otherwise a new image data is
	 * calculated and then cached. On the image is also painted the actual
	 * selected point
	 */
	private void paintSlider(boolean forceRepaint) {
		if (forceRepaint) {
			if (sliderCache != null) {
				sliderCache.dispose();
			}
			Rectangle bound = slider.getBounds();
			if (bound.width > 0 && bound.height > 0) {
				sliderCache = new Image(Display.getCurrent(), bound.width, bound.height);
				GC gc = new GC(sliderCache);
				paintSlider(gc, bound.width, bound.height);
				gc.dispose();	
			}
		}
		slider.redraw();
	}

	/**
	 * Put the appropriate image inside the square area. If the image data of
	 * the image it is cached it will be used, otherwise a new image data is
	 * calculated and then cached. On the image is also painted the actual
	 * selected point
	 */
	private void paintPad(boolean forceRepaint) {
		if (forceRepaint) {
			if (canvasCache != null) {
				canvasCache.dispose();
			}
			Rectangle bound = colorComposite.getBounds();
			if (bound.width > 0 && bound.height > 0) {
				canvasCache = new Image(Display.getCurrent(), bound.width, bound.height);
				GC gc = new GC(canvasCache);
				paintCanvas(gc, bound.width, bound.height);
				gc.dispose();	
			}
		}
		colorComposite.redraw();
	}
}
