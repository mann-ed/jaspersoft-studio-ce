/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.wb.swt.ResourceManager;

/**
 * Widget that fakes a writable swt combo using a styled text or a label. The widget doesn't 
 * implement what is shown when the arrow button is pressed but it fire a listener that
 * can be used to show what is desired. Common implementation provide a contextual menu
 * opened programmatically. It allow also to optionally show the image of the combo aside
 * to the text area. The text control is a styledtext if the item is writable or a label if it
 * is readonly. This was done this way because a styled text can always receive focus and show
 * the cursor, even if it is set as read only
 * 
 * @author Orlandin Marco
 *
 */
public class WritableComboButton extends Composite {

	/**
	 * Rounded corner of the widgets
	 */
	protected static final int CORNER_SIZE = 5;
	
	/**
	 * Arrow width
	 */
	protected static final int ARROW_WIDTH = 7;
	
	/**
	 * Arrow height
	 */
	protected static final int ARROW_HEIGHT = 4;
	
	/**
	 * Width of the area is shown
	 */
	protected static final int ARROW_AREA_WIDTH = 15;
	
	/**
	 * Style bit: Don't show image.
	 */
	public static final int NO_IMAGE = 1 << 1;
	
	/**
	 * The background color of all the widgets
	 */
	private Color defaultBackgroundColor;
	
	/**
	 * Text area where the text is typed/shown
	 */
	private Control textArea;
	
	/**
	 * Canvas where the arrow is painted
	 */
	private Canvas arrow;
	
	/**
	 * Image that is shown when present and the flag NO_IMAGE is not used
	 */
	private Image displayedImage;
	
	/**
	 * Filed to keep cached the size of the currently loaded image
	 */
	private Point cachedImageSize = null;
	
	/**
	 * Control where the image is displayed when needed 
	 */
	private Label imageLabel;
	
	/**
	 * Listeners associated to the click event on the arrow button
	 */
	private List<SelectionListener> openListeners = null;
	
	/**
	 * Listeners associated to the modify event of the text area
	 */
	private List<ModifyListener> modifyListeners = null;
	
	/**
	 * Text modify event called when the text area is modified, it fire
	 * all external events.
	 */
	protected ModifyListener textModifyListener = new ModifyListener() {
		
		@Override
		public void modifyText(ModifyEvent e) {
			if (modifyListeners == null || modifyListeners.isEmpty()) return;
			StyledText textControl = (StyledText)textArea;
			Point selection = textControl.getSelection();
			for(ModifyListener listener : modifyListeners){
				listener.modifyText(e);
			}
			textControl.setSelection(selection);
		}
	};
	
	/**
	 * Mouse listener used on the arrow to fire all the open listeners
	 */
	protected MouseListener mousePressListener = new MouseAdapter() {
		
		public void mouseDown(MouseEvent e) {
			handleMousePress(e);
		}
	};
	
	public WritableComboButton(Composite parent, int style) {
		super(parent, style);
		imageLabel = new Label(this, SWT.NONE);
		
		boolean isReadOnly = (getStyle() & SWT.READ_ONLY) != 0;
		if (isReadOnly){
			textArea = new Label(this, SWT.NONE);
			textArea.setBackground(ResourceManager.getColor(SWT.COLOR_WHITE));
		} else {
			textArea = new StyledText(this, SWT.NONE);	
			((StyledText)textArea).addModifyListener(textModifyListener);
		}
		
		defaultBackgroundColor = textArea.getBackground();
		arrow = new Canvas(this, SWT.NONE);
		
		//define a custom layout to dispose and size correctly all the elements
		setLayout(new Layout() {
			
			@Override
			protected void layout(Composite composite, boolean flushCache) {
				Rectangle availableSize = composite.getClientArea();
				int xOffsetLeft = 3;
				int xOffsetRight = 1;
				int imageWidth = 0;
				int imageYOffset = 1;
				int imageHeight = 0;
				if (displayedImage != null){
					int maxAvailableWidth = (availableSize.width + xOffsetLeft + xOffsetRight)/2;
					imageWidth = Math.min(getImageSize().x, maxAvailableWidth);
					if (getImageSize().y > availableSize.height - 2){
						imageHeight = availableSize.height - 2;
					} else {
						imageYOffset = (availableSize.height - getImageSize().y)/2;
						imageHeight = getImageSize().y;
					}
				}
				int textMaxWidth = availableSize.width - ARROW_AREA_WIDTH - imageWidth - xOffsetLeft - xOffsetRight; 
				imageLabel.setBounds(xOffsetLeft, imageYOffset, imageWidth, imageHeight);
				xOffsetLeft += imageWidth;
				textArea.setBounds(xOffsetLeft, 1, textMaxWidth, availableSize.height - 2);
				arrow.setBounds(textMaxWidth + xOffsetLeft, 1, ARROW_AREA_WIDTH - xOffsetRight, availableSize.height - 2);
			
				//set the background colors
				Color background = getWidgetsBackground(getDisplay());
				setCompositeBackgroundColor(background);
				imageLabel.setBackground(background);
				textArea.setBackground(background);
				
				redraw();
			}
			
			@Override
			protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
				Point textAreaSize = textArea.computeSize(wHint, hHint);
				if (displayedImage != null){
					textAreaSize.x += getImageSize().x;
					if (textAreaSize.y < getImageSize().y) {
						textAreaSize.y = getImageSize().y;
					}
				}
				if (textAreaSize.y < 20) textAreaSize.y = 20;
				return textAreaSize;
			}
		});
		
		arrow.addMouseListener(mousePressListener);
		
		//Paint listenr to paint the arrow inside the canvas
		arrow.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				Rectangle bounds = arrow.getBounds();
				e.gc.setBackground(getWidgetsBackground(e.display));
				e.gc.fillRectangle(0, 0,  bounds.width, bounds.height);
				e.gc.setBackground(ResourceManager.getColor(0, 0, 0));
				int y = bounds.height / 2;
				int x = bounds.width / 2;
				int y1 = y - ARROW_HEIGHT / 2;
				int y2 = y + ARROW_HEIGHT / 2;
				int x3 = x;
				int x1 = x - ARROW_WIDTH / 2; 
				int x2 = x + ARROW_WIDTH / 2;
				e.gc.fillPolygon(new int[] { x1, y1, x2, y1, x3, y2 });
				
			}
		});
		createBorderPaintListener();
	}
	
	@Override
	public void addListener(int eventType, Listener listener) {
		if (eventType == SWT.Paint){
			super.addListener(eventType, listener);
		} else {
			textArea.addListener(eventType, listener);
		}
	}
	
	@Override
	public void removeListener(int eventType, Listener listener) {
		if (eventType == SWT.Paint){
			super.removeListener(eventType, listener);
		} else {
			textArea.removeListener(eventType, listener);
		}
	}
	
	protected void createBorderPaintListener(){
		PaintListener borderPainter = new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				Rectangle bounds = getBounds();
				int w = bounds.width - 1;
				int h = bounds.height- 1;
				boolean focused = isFocusControl() || textArea.isFocusControl() || arrow.isFocusControl();
				e.gc.setForeground(getBorderForeground(e.display, focused));
				e.gc.drawRoundRectangle(0, 0, w, h, CORNER_SIZE, CORNER_SIZE);
				
			}
		};
		addPaintListener(borderPainter);
	}
	
	/**
	 * Call the setBackground of the composite, since the method
	 * is hidden by the override this is the only way to call
	 * only the super one
	 * 
	 * @param color the color
	 */
	protected void setCompositeBackgroundColor(Color color){
		super.setBackground(color);
	}
	
	/**
	 * Return the background color of the widgets
	 *
	 * @param display display to create the color
	 * @return a color 
	 */
	protected Color getWidgetsBackground(Display display) {
		return defaultBackgroundColor;
	}
	
	/**
	 * Return the color of the border
	 *  
	 * @param focused true if the widget is currently focused, false otherwise
	 * @param display display to create the color
	 * @return a color 
	 */
	protected Color getBorderForeground(Display display, boolean focused) {
		if (focused)
			return display.getSystemColor(SWT.COLOR_WIDGET_BORDER);
		return display.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
	}
	
	@Override
	public void setBackground(Color color) {
		checkWidget();
		defaultBackgroundColor = color;
		layout();
	}
	
	/**
	 * Set the foreground color of the text area
	 * 
	 * @param color the color
	 */
	@Override
	public void setForeground(Color color) {
		checkWidget();
		textArea.setForeground(color);
	}
	
	/**
	 * Add a focus listener to the text area
	 * 
	 * @param listener the listener
	 */
	@Override
	public void addFocusListener(FocusListener listener) {
		textArea.addFocusListener(listener);
	}

	/**
	 * Set the text inside the text area, this doesn't fire the modify listener
	 * 
	 * @param text the text to set
	 */
	public void setText(String text){
		if (textArea.getClass().equals(Label.class)){
			((Label)textArea).setText(text);
		} else {
			StyledText textControl = (StyledText)textArea;
			textControl.removeModifyListener(textModifyListener);
			textControl.setText(text);
			textControl.addModifyListener(textModifyListener);
		}
	}
	
	/**
	 * Insert a string in the text widget
	 * 
	 * @param text the string to insert
	 */
	public void insert(String text){
		if (textArea.getClass().equals(Label.class)){
			((Label)textArea).setText(text);
		} else {
			StyledText textControl = (StyledText)textArea;
			textControl.removeModifyListener(textModifyListener);
			textControl.insert(text);
			textControl.addModifyListener(textModifyListener);
		}
	}
	
	/**
	 * Return the line height in pixels
	 * 
	 * @return the line height
	 */
	public int getLineHeight(){
		if (textArea.getClass().equals(Label.class)){
			return ((Label)textArea).getFont().getFontData()[0].getHeight();
		} else {
			StyledText textControl = (StyledText)textArea;
			return textControl.getLineHeight();
		}
	}

	/**
	 * Return the text inside the area
	 * 
	 * @return a not null text
	 */
	public String getText(){
		if (textArea.getClass().equals(Label.class)){
			return ((Label)textArea).getText();
		} else {
			StyledText textControl = (StyledText)textArea;
			return textControl.getText();
		}
	}

	/**
	 * Add a new listener to run when the open action is fired
	 * 
	 * @param listener
	 */
	public void addOpenListener(SelectionListener listener) {
		if (openListeners == null)
			openListeners = new ArrayList<SelectionListener>();
		openListeners.add(listener);
	}
	
	/**
	 * Remove a previous added listener
	 * 
	 * @param listener
	 */
	public void removeOpenListener(SelectionListener listener) {
		if (openListeners == null)
			return;
		openListeners.remove(listener);
	}
	
	/**
	 * Add a new listener to run when the modify action  on the text area is fired
	 * 
	 * @param listener
	 */
	public void addModifyListener(ModifyListener listener) {
		if (modifyListeners == null)
			modifyListeners = new ArrayList<ModifyListener>();
		modifyListeners.add(listener);
	}
	
	/**
	 * Remove a previous added listener
	 * 
	 * @param listener
	 */
	public void removeModifyListener(ModifyListener listener) {
		if (modifyListeners == null)
			modifyListeners = new ArrayList<ModifyListener>();
		modifyListeners.remove(listener);
	}

	/**
	 * Method called when the arrow button is clicked, fire all
	 * the open listener
	 * 
	 * @param mouseEvent the event happened on the button
	 */
	protected void fireOpen(MouseEvent mouseEvent) {
		if (openListeners == null || openListeners.isEmpty())
			return;
		imageLabel.setFocus();
		final Event e = new Event();
		e.widget = this;
		e.button = mouseEvent.button;
		e.x = mouseEvent.x;
		e.y = mouseEvent.y;
		for (final SelectionListener l : openListeners) {
			SafeRunner.run(new SafeRunnable() {
				public void run() throws Exception {
					l.widgetSelected(new SelectionEvent(e));
				}
			});
		}
	}
	
	/**
	 * Enable or disable all the controls inside the composite
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		arrow.setEnabled(enabled);
		textArea.setEnabled(enabled);
		imageLabel.setEnabled(enabled);
	}
	
	/**
	 * Handle for the mouse press, if the menu is closed an open action will be fired for every listener, otherwise it
	 * will be considered as a focus out
	 */
	protected void handleMousePress(MouseEvent e) {
		if (isDisposed() || !isEnabled()) return;
		fireOpen(e);
	}
	
	/**
	 * Set the image inside the control, then it will be refreshed, but only 
	 * if the style bit is not NO_IMAGE
	 * @param image the new image
	 */
	public void setImage(Image image) {
		if (image == this.displayedImage)
			return;
		this.displayedImage = image;
		cachedImageSize = null;
		imageLabel.setImage(displayedImage);
		layout();
	}

	/**
	 * Set the image inside the control, then it will be refreshed, but only 
	 * if the style bit is not NO_IMAGE
	 * @param image an image descriptor of the new image
	 */
	public void setImage(ImageDescriptor imageDesc) {
		if (imageDesc != null) {
			Image image = ResourceManager.getImage(imageDesc);
			setImage(image);
		}
	}
	
	/**
	 * Return the selection of the text area
	 * 
	 * @return a point representing the selection
	 */
	public Point getSelection(){
		if (textArea.getClass().equals(StyledText.class)){
			return ((StyledText)textArea).getSelection();
		} else {
			return new Point(0, 0);
		}
	}
	
	/**
	 * Set the selection of the text area
	 * 
	 * @param value the selection value
	 */
	public void setSelection(Point value){
		if (textArea.getClass().equals(StyledText.class)){
			((StyledText)textArea).setSelection(value);
		}
	}

	/**
	 * Set the selection of the text area
	 * 
	 * @param start the start position
	 * @param end the end position
	 */
	public void setSelection(int start, int end){
		if (textArea.getClass().equals(StyledText.class)){
			((StyledText)textArea).setSelection(start, end);
		}
	}
	
	/**
	 * Check if the elements has and image
	 * 
	 * @return False if the image is not null or the style bit is set to NO_IMAGE, true otherwise
	 */
	public boolean hasImage() {
		return displayedImage != null && (getStyle() & NO_IMAGE) == 0;
	}

	/**
	 * Return the size of the image
	 * @return
	 */
	protected Point getImageSize() {
		if (cachedImageSize == null) {
			cachedImageSize = calcImageSize();
		}
		return cachedImageSize;
	}
	
	/**
	 * Return the image size
	 * 
	 * @return a not null point where x is the width and y the height
	 */
	protected Point calcImageSize() {
		Point size = new Point(0, 0);
		if (hasImage()) {
			Rectangle bounds = displayedImage.getBounds();
			size.x = Math.max(size.x, bounds.width);
			size.y = Math.max(size.y, bounds.height);
		}
		return size;
	}
	
	@Override
	public boolean setFocus() {
		return textArea.setFocus();
	}
	
	/**
	 * Set the color of the text in the text area
	 */
	public void setTextColor(Color color){
		textArea.setForeground(color);
	}
	
	/**
	 * Set the contextual menu, the menu is set on the text area
	 */
	@Override
	public Menu getMenu() {
		return textArea.getMenu();
	}
	
	/**
	 * Return the contextual menu of the text area
	 */
	@Override
	public void setMenu(Menu menu) {
		textArea.setMenu(menu);
	}
}
