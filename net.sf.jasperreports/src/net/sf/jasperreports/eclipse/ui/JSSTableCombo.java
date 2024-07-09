/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.accessibility.AccessibleTextAdapter;
import org.eclipse.swt.accessibility.AccessibleTextEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.ITheme;
import org.eclipse.wb.swt.ResourceManager;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.Pair;

/**
 * The TableCombo class represents a selectable user interface object that
 * combines a label, textfield, and a table and issues notification when an item
 * is selected from the table.
 * 
 * Note: This widget is basically a extension of the CCombo widget. The list
 * control was replaced by a Table control and a Label control was added so that
 * images can be displayed when a value from the drop down items has a image
 * associated to it.
 * 
 * <p>
 * TableCombo was written to allow the user to be able to display multiple
 * columns of data in the "Drop Down" portion of the combo.
 * </p>
 * <p>
 * Special Note: Although this class is a subclass of <code>Composite</code>, it
 * does not make sense to add children to it, or set a layout on it.
 * </p>
 * <dl>
 * <dt><b>Styles:</b>
 * <dd>BORDER, READ_ONLY, FLAT</dd>
 * <dt><b>Events:</b>
 * <dd>DefaultSelection, Modify, Selection, Verify</dd>
 * </dl>
 *
 */

public class JSSTableCombo extends Composite {

	/**
	 * Style bit: Don't show image.
	 */
	public static final int NO_IMAGE = 1 << 1;

	public static final int STRIGHT_CORNER = 1 << 2;

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

	private Shell popup;
	private Canvas arrow;
	private Label imageLabel;
	private StyledText text;
	private Table table;
	private Font font;
	private boolean hasFocus;
	private int visibleItemCount = 7;
	private Listener listener;
	private Listener focusFilter;
	private int displayColumnIndex = 0;
	private int[] columnWidths;
	private String[] columnHeaders;
	private int totalColumns;
	private int tableWidthPercentage = 100;
	private boolean showColorWithinSelection = true;
	private boolean showFontWithinSelection = true;

	/**
	 * The background color of all the widgets
	 */
	private Color defaultBackgroundColor;

	/**
	 * Width of the area is shown
	 */
	protected static final int ARROW_AREA_WIDTH = 15;

	/**
	 * Image that is shown when present and the flag NO_IMAGE is not used
	 */
	private Image displayedImage;

	/**
	 * Listeners associated to the modify event of the text area
	 */
	private List<ValueChangedListener> modifyListeners = null;

	/**
	 * Filed to keep cached the size of the currently loaded image
	 */
	private Point cachedImageSize = null;

	/**
	 * Flag to know if the shown value is inherited or not
	 */
	private boolean isInherited = false;

	/**
	 * The color used for the text to mark show the value is inherited
	 */
	protected Color inheritedColor = UIUtils.INHERITED_COLOR;

	/**
	 * Store the original color of the widget
	 */
	private Color standardColor;

	/**
	 * Flag used to full select the text on the first click on it if it is inherited
	 */
	private boolean fullSelectionHappened = false;

	/**
	 * Map used to keep track of the modify and verify listeners on the text. This
	 * is used to remove them when calculating the size and restoring them when the
	 * calculation is done. Doing this we don't trigger the event during the
	 * calculation
	 */
	private LinkedHashMap<Listener, Pair<Integer, Listener>> textListeners = new LinkedHashMap<>();

	/**
	 * Text modify event called when the text area is modified, it fire all external
	 * events.
	 */
	protected ModifyListener textModifyListener = new ModifyListener() {

		@Override
		public void modifyText(ModifyEvent e) {
			if (modifyListeners == null || modifyListeners.isEmpty())
				return;
			Point selection = text.getSelection();
			ValueChangedEvent event = new ValueChangedEvent(text.getText(), null, true);
			for (ValueChangedListener listener : modifyListeners) {
				listener.valueChanged(event);
			}
			text.setSelection(selection);
		}
	};

	/**
	 * Mouse listener used on the arrow to fire all the open listeners
	 */
	protected MouseListener mousePressListener = new MouseAdapter() {

		public void mouseDown(MouseEvent e) {
			text.setFocus();
			dropDown(!isDropped());
		}
	};

	/**
	 * Constructs a new instance of this class given its parent and a style value
	 * describing its behavior and appearance.
	 * <p>
	 * The style value is either one of the style constants defined in class
	 * <code>SWT</code> which is applicable to instances of this class, or must be
	 * built by <em>bitwise OR</em>'ing together (that is, using the
	 * <code>int</code> "|" operator) two or more of those <code>SWT</code> style
	 * constants. The class description lists the style constants that are
	 * applicable to the class. Style bits are also inherited from superclasses.
	 * </p>
	 *
	 * @param parent a widget which will be the parent of the new instance (cannot
	 *               be null)
	 * @param style  the style of widget to construct
	 *
	 * @exception IllegalArgumentException
	 *                                     <ul>
	 *                                     <li>ERROR_NULL_ARGUMENT - if the parent
	 *                                     is null</li>
	 *                                     </ul>
	 * @exception SWTException
	 *                                     <ul>
	 *                                     <li>ERROR_THREAD_INVALID_ACCESS - if not
	 *                                     called from the thread that created the
	 *                                     parent</li>
	 *                                     </ul>
	 *
	 * @see SWT#BORDER
	 * @see SWT#READ_ONLY
	 * @see SWT#FLAT
	 * @see Widget#getStyle()
	 */
	public JSSTableCombo(Composite parent, int style) {
		super(parent, style);

		boolean readOnly = (style & SWT.READ_ONLY) == SWT.READ_ONLY;
		imageLabel = new Label(this, SWT.NONE);

		text = new StyledText(this, readOnly ? SWT.READ_ONLY : SWT.NONE) {

			@Override
			protected void checkSubclass() {

			}

			// KEEP TRACK OF THE LISTENERS ON THE TEXT AND ALSO AVOID TO ADD TWICE THE SAME
			// LISTENER
			@Override
			public void addListener(int eventType, Listener listener) {
				if (!textListeners.containsKey(listener)) {
					super.addListener(eventType, listener);
					if (eventType == SWT.Modify || eventType == SWT.Verify) {
						textListeners.put(listener, new Pair<Integer, Listener>(eventType, listener));
					}
				}
			}

			@Override
			protected void removeListener(int eventType, SWTEventListener handler) {
				super.removeListener(eventType, handler);
				if (eventType == SWT.Modify || eventType == SWT.Verify) {
					textListeners.remove(handler);
				}
			}

			@Override
			public void removeListener(int eventType, Listener listener) {
				super.removeListener(eventType, listener);
				if (eventType == SWT.Modify || eventType == SWT.Verify) {
					textListeners.remove(listener);
				}
			}
		};
		standardColor = text.getForeground();
		ITheme currentTheme = PlatformUI.getWorkbench().getThemeManager().getCurrentTheme(); 
		defaultBackgroundColor = currentTheme.getColorRegistry().get(JFacePreferences.CONTENT_ASSIST_BACKGROUND_COLOR);
		//defaultBackgroundColor = text.getBackground();
		arrow = new Canvas(this, SWT.NONE);

		// define a custom layout to dispose and size correctly all the elements
		super.setLayout(new Layout() {

			@Override
			protected void layout(Composite composite, boolean flushCache) {
				Rectangle availableSize = composite.getClientArea();
				int xOffsetLeft = 3;
				int xOffsetRight = 1;
				int imageWidth = 0;
				int imageYOffset = 1;
				int imageHeight = 0;
				if (displayedImage != null) {
					int maxAvailableWidth = (availableSize.width + xOffsetLeft + xOffsetRight) / 2;
					imageWidth = Math.min(getImageSize().x, maxAvailableWidth);
					if (getImageSize().y > availableSize.height - 2) {
						imageHeight = availableSize.height - 2;
					} else {
						imageYOffset = (availableSize.height - getImageSize().y) / 2;
						imageHeight = getImageSize().y;
					}
				}

				int textMaxWidth = availableSize.width - ARROW_AREA_WIDTH - imageWidth - xOffsetLeft - xOffsetRight;

				// CALCUALTE THE TEXT, IT TAKES ALWAYS THE MINIMUM SPACE BETWEEN THE REQUIRED
				// SPACE AND THE MAXIMUM
				List<Pair<Integer, Listener>> textListeners = removeTextListeners(true);
				String oldString = text.getText();
				text.setText(getLongestText());
				Point textAreaSize = text.computeSize(textMaxWidth, availableSize.height - 2);
				textAreaSize.x = Math.min(textMaxWidth, textAreaSize.x);
				text.setText(oldString);
				restoreTextListeners(textListeners);

				imageLabel.setBounds(xOffsetLeft, imageYOffset, imageWidth, imageHeight);
				xOffsetLeft += imageWidth;
				text.setBounds(xOffsetLeft, 1, textAreaSize.x, availableSize.height - 2);
				arrow.setBounds(textMaxWidth + xOffsetLeft, 1, ARROW_AREA_WIDTH - xOffsetRight,
						availableSize.height - 2);

				// set the background colors
				Color background = getWidgetsBackground(getDisplay());
				setCompositeBackgroundColor(background);
				imageLabel.setBackground(background);
				text.setBackground(background);

				redraw();
			}

			@Override
			protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
				Point textAreaSize = null;
				List<Pair<Integer, Listener>> textListeners = removeTextListeners(true);
				String oldString = text.getText();
				text.setText(getLongestText());
				textAreaSize = text.computeSize(wHint, hHint);
				text.setText(oldString);
				restoreTextListeners(textListeners);
				int xOffsetLeft = 3;
				int xOffsetRight = 1;
				textAreaSize.x += ARROW_AREA_WIDTH + xOffsetLeft + xOffsetRight;
				if (displayedImage != null) {
					textAreaSize.x += getImageSize().x;
					textAreaSize.y += getImageSize().y;
				}
				if (textAreaSize.y < 20)
					textAreaSize.y = 20;
				Point tableSize = table != null ? table.computeSize(SWT.DEFAULT, SWT.DEFAULT, flushCache)
						: new Point(0, 0);
				return new Point(Math.max(tableSize.x, textAreaSize.x), textAreaSize.y);
			}
		});

		arrow.addMouseListener(mousePressListener);

		// Paint listenr to paint the arrow inside the canvas
		arrow.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				Rectangle bounds = arrow.getBounds();
				if (e.display != null) {
					Color wb = getWidgetsBackground(e.display);
					if (wb != null)
						e.gc.setBackground(wb);
				}
				e.gc.fillRectangle(0, 0, bounds.width, bounds.height);
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
		text.addModifyListener(textModifyListener);

		// add the listener to change control with TAB
		text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.TAB) {
					e.doit = false;
					boolean isShiftPressed = (e.stateMask & SWT.SHIFT) != 0;
					if (isShiftPressed) {
						focusPreviousControl(JSSTableCombo.this);
					} else {
						focusNextControl(JSSTableCombo.this);
					}
				}
			}
		});

		// Listener to avoid to add the tabulation on the text
		text.addVerifyKeyListener(new VerifyKeyListener() {

			@Override
			public void verifyKey(VerifyEvent event) {
				if (event.keyCode == SWT.TAB) {
					event.doit = false;
				}
			}
		});

		// allow to fully select the text on the first click on the control if it is
		// inherithed
		text.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				if (isInherited && !fullSelectionHappened) {
					text.selectAll();
					fullSelectionHappened = true;
				}
			}
		});

		text.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				fullSelectionHappened = false;
				text.setSelection(0, 0);
			}

		});

		// now add a listener to listen to the events we are interested in.
		listener = new Listener() {
			public void handleEvent(Event event) {
				if (isDisposed())
					return;

				// check for a popup event
				if (popup == event.widget) {
					popupEvent(event);
					return;
				}

				// check for a table event
				if (table == event.widget) {
					tableEvent(event);
					return;
				}

				// check for shell event
				if (getShell() == event.widget) {
					getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (isDisposed())
								return;
							handleFocus(SWT.FocusOut);
						}
					});
				}
			}
		};

		// create new focus listener
		focusFilter = new Listener() {
			public void handleEvent(Event event) {
				if (isDisposed())
					return;
				Shell shell = ((Control) event.widget).getShell();

				if (shell == JSSTableCombo.this.getShell()) {
					handleFocus(SWT.FocusOut);
				}
			}
		};

		// initialize the drop down
		createPopup(-1);
	}

	/**
	 * Remove all the modify and verify listeners from the text and return them
	 * 
	 * @param removeVerify true if also the verify listener should be removed, false
	 *                     otherwise. The verify listener should not be removed when
	 *                     setting the text because of a combo selection
	 * @return a not null list of listeners with their event type
	 */
	protected List<Pair<Integer, Listener>> removeTextListeners(boolean removeVerify) {
		ArrayList<Pair<Integer, Listener>> listeners = new ArrayList<Pair<Integer, Listener>>();
		listeners.addAll(textListeners.values());
		for (Pair<Integer, Listener> listener : listeners) {
			if (removeVerify || listener.getKey() != SWT.Verify) {
				text.removeListener(listener.getKey(), listener.getValue());
			}
		}
		return listeners;
	}

	/**
	 * Add all the listener passed to the text
	 * 
	 * @param listeners a not null list of listeners
	 */
	protected void restoreTextListeners(List<Pair<Integer, Listener>> listeners) {
		for (Pair<Integer, Listener> listener : listeners) {
			text.addListener(listener.getKey(), listener.getValue());
		}
	}

	/**
	 * Recursively search a control after this to focus
	 * 
	 * @param currentControl the current control
	 */
	private void focusPreviousControl(Control currentControl) {
		Control[] children = currentControl.getParent().getChildren();
		int index = ArrayUtils.indexOf(children, currentControl);
		if (index > 0) {
			boolean hasFocused = children[index - 1].forceFocus();
			if (!hasFocused) {
				focusPreviousControl(currentControl.getParent());
			}
		} else {
			focusPreviousControl(currentControl.getParent());
		}
	}

	/**
	 * Recursively search a control before this to focus
	 * 
	 * @param currentControl the current control
	 */
	private void focusNextControl(Control currentControl) {
		Control[] children = currentControl.getParent().getChildren();
		int index = ArrayUtils.indexOf(children, currentControl);
		if (index != -1 && index < children.length - 1) {
			boolean hasFocused = children[index + 1].forceFocus();
			if (!hasFocused) {
				focusNextControl(currentControl.getParent());
			}
		} else {
			focusNextControl(currentControl.getParent());
		}
	}

	/**
	 * Call the setBackground of the composite, since the method is hidden by the
	 * override this is the only way to call only the super one
	 * 
	 * @param color the color
	 */
	protected void setCompositeBackgroundColor(Color color) {
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

	@Override
	public void addListener(int eventType, Listener listener) {
		if (eventType == SWT.Paint) {
			super.addListener(eventType, listener);
		} else {
			text.addListener(eventType, listener);
		}
	}

	@Override
	public void removeListener(int eventType, Listener listener) {
		if (eventType == SWT.Paint) {
			super.removeListener(eventType, listener);
		} else {
			text.removeListener(eventType, listener);
		}
	}

	/**
	 * Add a new listener to run when the modify action on the text area is fired
	 * 
	 * @param listener
	 */
	public void addModifyListener(ValueChangedListener listener) {
		if (modifyListeners == null)
			modifyListeners = new ArrayList<ValueChangedListener>();
		modifyListeners.add(listener);
	}

	/**
	 * Remove a previous added listener
	 * 
	 * @param listener
	 */
	public void removeModifyListener(ValueChangedListener listener) {
		if (modifyListeners == null)
			modifyListeners = new ArrayList<ValueChangedListener>();
		modifyListeners.remove(listener);
	}

	/**
	 * Check if the elements has and image
	 * 
	 * @return False if the image is not null or the style bit is set to NO_IMAGE,
	 *         true otherwise
	 */
	public boolean hasImage() {
		return displayedImage != null && (getStyle() & NO_IMAGE) == 0;
	}

	public boolean hasStrightCorner() {
		return (getStyle() & STRIGHT_CORNER) != 0;
	}

	/**
	 * Return the size of the image
	 * 
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
		standardColor = color;
		if (!isInherited)
			text.setForeground(color);
	}

	@Override
	public Color getForeground() {
		checkWidget();
		return text.getForeground();
	}

	/**
	 * Add a focus listener to the text area
	 * 
	 * @param listener the listener
	 */
	@Override
	public void addFocusListener(FocusListener listener) {
		text.addFocusListener(listener);
	}

	/**
	 * Set the contextual menu, the menu is set on the text area
	 */
	@Override
	public Menu getMenu() {
		return text.getMenu();
	}

	/**
	 * Return the contextual menu of the text area
	 */
	@Override
	public void setMenu(Menu menu) {
		text.setMenu(menu);
	}

	public void addVerifyListener(VerifyListener listener) {
		text.addVerifyListener(listener);
	}

	public void removeVerifyListener(VerifyListener listener) {
		text.removeVerifyListener(listener);
	}

	/**
	 * Set the text inside the text area, this doesn't fire the modify listener
	 * 
	 * @param text the text to set
	 */
	public synchronized void setText(String textValue) {
		int index = indexOf(textValue);
		if (index == -1) {
			if (table != null)
				table.deselectAll();
			List<Pair<Integer, Listener>> textListeners = removeTextListeners(true);
			text.setText(textValue);
			restoreTextListeners(textListeners);
		} else {
			List<Pair<Integer, Listener>> textListeners = removeTextListeners(true);
			text.setText(textValue);
			select(index, false, true);
			restoreTextListeners(textListeners);
		}
	}

	/**
	 * Insert a string in the text widget
	 * 
	 * @param text the string to insert
	 */
	public void insert(String textValue) {
		text.removeModifyListener(textModifyListener);
		text.insert(textValue);
		text.addModifyListener(textModifyListener);
	}

	protected void createBorderPaintListener() {
		PaintListener borderPainter = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				Rectangle bounds = getBounds();
				int w = bounds.width - 1;
				int h = bounds.height - 1;
				e.gc.setForeground(getBorderForeground(e.display, false));
				if (hasStrightCorner()) {
					e.gc.drawRectangle(0, 0, w, h);
				} else {
					e.gc.drawRoundRectangle(0, 0, w, h, CORNER_SIZE, CORNER_SIZE);
				}
			}
		};
		addPaintListener(borderPainter);
	}

	/**
	 * Sets the selection in the receiver's text field to an empty selection
	 * starting just before the first character. If the text field is editable, this
	 * has the effect of placing the i-beam at the start of the text.
	 * <p>
	 * Note: To clear the selected items in the receiver's list, use
	 * <code>deselectAll()</code>.
	 * </p>
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 *
	 * @see #deselectAll
	 */
	public void clearSelection() {
		checkWidget();
		text.setSelection(0);
		table.deselectAll();
	}

	/**
	 * Copies the selected text.
	 * <p>
	 * The current selection is copied to the clipboard.
	 * </p>
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 * 
	 * @since 3.3
	 */
	public void copy() {
		checkWidget();
		text.copy();
	}

	/**
	 * creates the popup shell.
	 * 
	 * @param selectionIndex
	 */
	protected void createPopup(int selectionIndex) {
		// create shell and table
		popup = new Shell(getShell(), SWT.NO_TRIM | SWT.ON_TOP);

		// create table
		table = new Table(popup, SWT.SINGLE | SWT.FULL_SELECTION);

		if (font != null)
			table.setFont(font);
		if (defaultBackgroundColor != null)
			table.setBackground(defaultBackgroundColor);

		// Add popup listeners
		int[] popupEvents = { SWT.Close, SWT.Paint, SWT.Deactivate, SWT.Help };
		for (int i = 0; i < popupEvents.length; i++) {
			popup.addListener(popupEvents[i], listener);
		}

		// add table listeners
		int[] tableEvents = { SWT.MouseUp, SWT.Selection, SWT.Traverse, SWT.KeyDown, SWT.KeyUp, SWT.FocusIn,
				SWT.Dispose };
		for (int i = 0; i < tableEvents.length; i++) {
			table.addListener(tableEvents[i], listener);
		}

		defineColumnsInternal(columnHeaders, columnWidths, totalColumns);
		setTableData(table);

		// set the selection
		if (selectionIndex != -1) {
			table.setSelection(selectionIndex);
		}
	}

	/**
	 * This method should be implemented by who is using this widget and it allow to
	 * define the {@link TableItem} of the table
	 * 
	 * @param table the table widget
	 */
	protected void setTableData(Table table) {

	}

	/**
	 * Return the longest string the combo should display. Used in the calculation
	 * of the size
	 * 
	 * @return a not null string
	 */
	protected String getLongestText() {
		return text.getText();
	}

	/**
	 * Return the index of the top item, this is not implemented to do anything and
	 * is due to who use the control provide an implementation
	 * 
	 * @param table the table widget
	 * @return the index of the top element or -1 if there is no particular top
	 *         element
	 */
	protected int getTopItem(Table table) {
		return -1;
	}

	/**
	 * Cuts the selected text.
	 * <p>
	 * The current selection is first copied to the clipboard and then deleted from
	 * the widget.
	 * </p>
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 * 
	 * @since 3.3
	 */
	public void cut() {
		checkWidget();
		text.cut();
	}

	/**
	 * Clear the old popup menu and regenerate it. This will trigger a recreation of
	 * the elements inside it, and should be called to update them
	 */
	public void refreshItems() {
		int selectionIndex = table.getSelectionIndex();
		table.removeListener(SWT.Dispose, listener);
		if (popup != null && !popup.isDisposed())
			popup.dispose();
		popup = null;
		table = null;
		createPopup(selectionIndex);
	}

	/**
	 * handle DropDown request
	 * 
	 * @param drop
	 */
	protected void dropDown(boolean drop) {

		// if already dropped then return
		if (drop == isDropped())
			return;

		// closing the dropDown
		if (!drop && popup != null && !popup.isDisposed()) {
			if (popup.getDisplay() != null)
				popup.setVisible(false);
			if (!isDisposed() && isFocusControl()) {
				text.setFocus();
			}
			return;
		}

		// if not visible then return
		if (!isVisible())
			return;

		// create a new popup if needed.
		if (popup == null || getShell() != popup.getParent()) {
			int selectionIndex = table.getSelectionIndex();
			table.removeListener(SWT.Dispose, listener);
			if (popup != null && !popup.isDisposed())
				popup.dispose();
			popup = null;
			table = null;
			createPopup(selectionIndex);
		}

		// get the size of the TableCombo.
		Point tableComboSize = getSize();

		// calculate the table height.
		int itemCount = table.getItemCount();
		itemCount = (itemCount == 0) ? visibleItemCount : Math.min(visibleItemCount, itemCount) - 1;
		int itemHeight = (table.getItemHeight() * itemCount);

		// add 1 to the table height if the table item count is less than the visible
		// item count.
		if (table.getItemCount() <= visibleItemCount) {
			itemHeight += 1;
		}

		// add height of header if the header is being displayed.
		if (table.getHeaderVisible()) {
			itemHeight += table.getHeaderHeight();
		}

		// get table column references
		TableColumn[] tableColumns = table.getColumns();
		int totalColumns = (tableColumns == null ? 0 : tableColumns.length);

		// check to make sure at least one column has been specified. if it hasn't
		// then just create a blank one.
		if (table.getColumnCount() == 0) {
			new TableColumn(table, SWT.NONE);
			totalColumns = 1;
			tableColumns = table.getColumns();
		}

		int totalColumnWidth = 0;
		// now pack any columns that do not have a explicit value set for them.
		for (int colIndex = 0; colIndex < totalColumns; colIndex++) {
			if (!wasColumnWidthSpecified(colIndex)) {
				tableColumns[colIndex].pack();
			}
			totalColumnWidth += tableColumns[colIndex].getWidth();
		}

		// reset the last column's width to the preferred size if it has a explicit
		// value.
		int lastColIndex = totalColumns - 1;
		if (wasColumnWidthSpecified(lastColIndex)) {
			tableColumns[lastColIndex].setWidth(columnWidths[lastColIndex]);
		}

		// calculate the table size after making adjustments.
		Point tableSize = table.computeSize(SWT.DEFAULT, itemHeight, false);

		// calculate the table width and table height.
		double pct = tableWidthPercentage / 100d;
		int tableWidth = (int) (Math.max(tableComboSize.x - 2, tableSize.x) * pct);
		int tableHeight = tableSize.y;

		// add the width of a horizontal scrollbar to the table height if we are
		// not viewing the full table.
		if (tableWidthPercentage < 100) {
			tableHeight += table.getHorizontalBar().getSize().y;
		}

		// set the bounds on the table.
		table.setBounds(1, 1, tableWidth, tableHeight);

		// check to see if we can adjust the table width to by the amount the vertical
		// scrollbar would have taken since the table auto allocates the space whether
		// it is needed or not.
		if (!table.getVerticalBar().getVisible()
				&& tableSize.x - table.getVerticalBar().getSize().x >= tableComboSize.x - 2) {

			tableWidth = tableWidth - table.getVerticalBar().getSize().x;

			// reset the bounds on the table.
			table.setBounds(1, 1, tableWidth, tableHeight);
		}

		// adjust the last column to make sure that there is no empty space.
		autoAdjustColumnWidthsIfNeeded(tableColumns, tableWidth, totalColumnWidth);

		// set the table top index if there is a valid selection.
		int index = table.getSelectionIndex();
		if (index != -1) {
			table.setTopIndex(index);
		}

		// calculate popup dimensions.
		Display display = getDisplay();
		Rectangle tableRect = table.getBounds();
		Rectangle parentRect = display.map(getParent(), null, getBounds());
		Point comboSize = getSize();
		Rectangle displayRect = getMonitor().getClientArea();

		int overallWidth = 0;

		// now set what the overall width should be.
		if (tableWidthPercentage < 100) {
			overallWidth = tableRect.width + 2;
		} else {
			overallWidth = Math.max(comboSize.x, tableRect.width + 2);
		}

		int overallHeight = tableRect.height + 2;
		int x = parentRect.x;
		int y = parentRect.y + comboSize.y;
		if (y + overallHeight > displayRect.y + displayRect.height)
			y = parentRect.y - overallHeight;
		if (x + overallWidth > displayRect.x + displayRect.width)
			x = displayRect.x + displayRect.width - tableRect.width;

		// set the bounds of the popup
		popup.setBounds(x, y, overallWidth, overallHeight);

		// set the popup visible
		popup.setVisible(true);

		// set focus on the table.
		table.setFocus();

		int topItem = getTopItem(table);
		if (topItem != -1) {
			table.setTopIndex(topItem);
		}
	}

	@Override
	public void dispose() {
		if (popup != null && !popup.isDisposed()) {
			popup.setVisible(false);
			popup.dispose();
			popup = null;
		}
		if (table != null) {
			table.dispose();
			table = null;
		}
		super.dispose();
		standardColor = null;
		inheritedColor = null;
	}

	/*
	 * Return the Label immediately preceding the receiver in the z-order, or null
	 * if none.
	 */
	private Label getAssociatedLabel() {
		Control[] siblings = getParent().getChildren();
		for (int i = 0; i < siblings.length; i++) {
			if (siblings[i] == JSSTableCombo.this) {
				if (i > 0 && siblings[i - 1] instanceof Label) {
					return (Label) siblings[i - 1];
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Control[] getChildren() {
		checkWidget();
		return new Control[0];
	}

	/**
	 * Gets the editable state.
	 *
	 * @return whether or not the receiver is editable
	 * 
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 * 
	 * @since 3.0
	 */
	public boolean getEditable() {
		checkWidget();
		return text.getEditable();
	}

	/**
	 * Returns the item at the given, zero-relative index in the receiver's list.
	 * Throws an exception if the index is out of range.
	 *
	 * @param index the index of the item to return
	 * @return the item at the given index
	 *
	 * @exception IllegalArgumentException
	 *                                     <ul>
	 *                                     <li>ERROR_INVALID_RANGE - if the index is
	 *                                     not between 0 and the number of elements
	 *                                     in the list minus 1 (inclusive)</li>
	 *                                     </ul>
	 * @exception SWTException
	 *                                     <ul>
	 *                                     <li>ERROR_WIDGET_DISPOSED - if the
	 *                                     receiver has been disposed</li>
	 *                                     <li>ERROR_THREAD_INVALID_ACCESS - if not
	 *                                     called from the thread that created the
	 *                                     receiver</li>
	 *                                     </ul>
	 */
	public String getItem(int index) {
		checkWidget();
		return table.getItem(index).getText(getDisplayColumnIndex());
	}

	/**
	 * Returns the number of items contained in the receiver's list.
	 *
	 * @return the number of items
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 */
	public int getItemCount() {
		checkWidget();
		return table.getItemCount();
	}

	/**
	 * Returns the height of the area which would be used to display <em>one</em> of
	 * the items in the receiver's list.
	 *
	 * @return the height of one item
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 */
	public int getItemHeight() {
		checkWidget();
		return table.getItemHeight();
	}

	/**
	 * Returns an array of <code>String</code>s which are the items in the
	 * receiver's list.
	 * <p>
	 * Note: This is not the actual structure used by the receiver to maintain its
	 * list of items, so modifying the array will not affect the receiver.
	 * </p>
	 *
	 * @return the items in the receiver's list
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 */
	public String[] getItems() {
		checkWidget();

		// get a list of the table items.
		TableItem[] tableItems = table.getItems();

		int totalItems = (tableItems == null ? 0 : tableItems.length);

		// create string array to hold the total number of items.
		String[] stringItems = new String[totalItems];

		int colIndex = getDisplayColumnIndex();

		// now copy the display string from the tableitems.
		for (int index = 0; index < totalItems; index++) {
			stringItems[index] = tableItems[index].getText(colIndex);
		}

		return stringItems;
	}

	/**
	 * Returns a <code>Point</code> whose x coordinate is the start of the selection
	 * in the receiver's text field, and whose y coordinate is the end of the
	 * selection. The returned values are zero-relative. An "empty" selection as
	 * indicated by the the x and y coordinates having the same value.
	 *
	 * @return a point representing the selection start and end
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 */
	public Point getSelection() {
		checkWidget();
		return text.getSelection();
	}

	/**
	 * Returns the zero-relative index of the item which is currently selected in
	 * the receiver's list, or -1 if no item is selected.
	 *
	 * @return the index of the selected item
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 */
	public int getSelectionIndex() {
		checkWidget();
		return table.getSelectionIndex();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getStyle() {
		checkWidget();

		int style = super.getStyle();
		style &= ~SWT.READ_ONLY;
		if (!text.getEditable())
			style |= SWT.READ_ONLY;
		return style;
	}

	/**
	 * Returns a string containing a copy of the contents of the receiver's text
	 * field.
	 *
	 * @return the receiver's text
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 */
	public String getText() {
		checkWidget();
		return text.getText();
	}

	/**
	 * Returns the height of the receivers's text field.
	 *
	 * @return the text height
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 */
	public int getTextHeight() {
		checkWidget();
		return text.getLineHeight();
	}

	/**
	 * Gets the number of items that are visible in the drop down portion of the
	 * receiver's list.
	 *
	 * @return the number of items that are visible
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 * 
	 * @since 3.0
	 */
	public int getVisibleItemCount() {
		checkWidget();
		return visibleItemCount;
	}

	/**
	 * Handle Focus event
	 * 
	 * @param type
	 */
	private void handleFocus(int type) {
		switch (type) {
		case SWT.FocusIn: {
			if (hasFocus)
				return;
			hasFocus = true;
			Shell shell = getShell();
			shell.removeListener(SWT.Deactivate, listener);
			shell.addListener(SWT.Deactivate, listener);
			Display display = getDisplay();
			display.removeFilter(SWT.FocusIn, focusFilter);
			display.addFilter(SWT.FocusIn, focusFilter);
			Event e = new Event();
			notifyListeners(SWT.FocusIn, e);
			break;
		}
		case SWT.FocusOut: {
			if (!hasFocus)
				return;
			Control focusControl = getDisplay().getFocusControl();
			if (focusControl == arrow || focusControl == table)
				return;
			hasFocus = false;
			Shell shell = getShell();
			shell.removeListener(SWT.Deactivate, listener);
			Display display = getDisplay();
			display.removeFilter(SWT.FocusIn, focusFilter);
			Event e = new Event();
			notifyListeners(SWT.FocusOut, e);
			break;
		}
		}
	}

	/**
	 * Searches the receiver's list starting at the first item (index 0) until an
	 * item is found that is equal to the argument, and returns the index of that
	 * item. If no item is found, returns -1.
	 *
	 * @param string the search item
	 * @return the index of the item
	 *
	 * @exception IllegalArgumentException
	 *                                     <ul>
	 *                                     <li>ERROR_NULL_ARGUMENT - if the string
	 *                                     is null</li>
	 *                                     </ul>
	 * @exception SWTException
	 *                                     <ul>
	 *                                     <li>ERROR_WIDGET_DISPOSED - if the
	 *                                     receiver has been disposed</li>
	 *                                     <li>ERROR_THREAD_INVALID_ACCESS - if not
	 *                                     called from the thread that created the
	 *                                     receiver</li>
	 *                                     </ul>
	 */
	public int indexOf(String string) {
		checkWidget();
		if (string == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		if (table == null)
			return -1;
		// get a list of the table items.
		TableItem[] tableItems = table.getItems();

		int totalItems = (tableItems == null ? 0 : tableItems.length);
		int colIndex = getDisplayColumnIndex();

		// now copy the display string from the tableitems.
		for (int index = 0; index < totalItems; index++) {
			if (string.equals(tableItems[index].getText(colIndex))) {
				return index;
			}
		}

		return -1;
	}

	/**
	 * Searches the receiver's list starting at the given, zero-relative index until
	 * an item is found that is equal to the argument, and returns the index of that
	 * item. If no item is found or the starting index is out of range, returns -1.
	 *
	 * @param string the search item
	 * @param start  the zero-relative index at which to begin the search
	 * @return the index of the item
	 *
	 * @exception IllegalArgumentException
	 *                                     <ul>
	 *                                     <li>ERROR_NULL_ARGUMENT - if the string
	 *                                     is null</li>
	 *                                     </ul>
	 * @exception SWTException
	 *                                     <ul>
	 *                                     <li>ERROR_WIDGET_DISPOSED - if the
	 *                                     receiver has been disposed</li>
	 *                                     <li>ERROR_THREAD_INVALID_ACCESS - if not
	 *                                     called from the thread that created the
	 *                                     receiver</li>
	 *                                     </ul>
	 */
	public int indexOf(String string, int start) {
		checkWidget();
		if (string == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);

		// get a list of the table items.
		TableItem[] tableItems = table.getItems();

		int totalItems = (tableItems == null ? 0 : tableItems.length);

		if (start < totalItems) {

			int colIndex = getDisplayColumnIndex();

			// now copy the display string from the tableitems.
			for (int index = start; index < totalItems; index++) {
				if (string.equals(tableItems[index].getText(colIndex))) {
					return index;
				}
			}
		}

		return -1;
	}

	public int getCaretPosition() {
		return text.getSelection().x;
	}

	/**
	 * sets whether or not to show table lines
	 * 
	 * @param showTableLines
	 */
	public void setShowTableLines(boolean showTableLines) {
		checkWidget();
		if (table != null)
			table.setLinesVisible(showTableLines);
	}

	/**
	 * sets whether or not to show table header.
	 * 
	 * @param showTableHeader
	 */
	public void setShowTableHeader(boolean showTableHeader) {
		checkWidget();
		if (table != null)
			table.setHeaderVisible(showTableHeader);
	}

	/**
	 * Add Accessbile listeners to label and table.
	 */
	void initAccessible() {
		AccessibleAdapter accessibleAdapter = new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				String name = null;
				Label label = getAssociatedLabel();
				if (label != null) {
					name = stripMnemonic(text.getText());
				}
				e.result = name;
			}

			public void getKeyboardShortcut(AccessibleEvent e) {
				String shortcut = null;
				Label label = getAssociatedLabel();
				if (label != null) {
					String text = label.getText();
					if (text != null) {
						char mnemonic = _findMnemonic(text);
						if (mnemonic != '\0') {
							shortcut = "Alt+" + mnemonic; //$NON-NLS-1$
						}
					}
				}
				e.result = shortcut;
			}

			public void getHelp(AccessibleEvent e) {
				e.result = getToolTipText();
			}
		};

		getAccessible().addAccessibleListener(accessibleAdapter);
		text.getAccessible().addAccessibleListener(accessibleAdapter);
		table.getAccessible().addAccessibleListener(accessibleAdapter);

		arrow.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = isDropped() ? SWT.getMessage("SWT_Close") : SWT.getMessage("SWT_Open"); //$NON-NLS-1$ //$NON-NLS-2$
			}

			public void getKeyboardShortcut(AccessibleEvent e) {
				e.result = "Alt+Down Arrow"; //$NON-NLS-1$
			}

			public void getHelp(AccessibleEvent e) {
				e.result = getToolTipText();
			}
		});

		getAccessible().addAccessibleTextListener(new AccessibleTextAdapter() {
			public void getCaretOffset(AccessibleTextEvent e) {
				e.offset = text.getSelection().y;
			}

			public void getSelectionRange(AccessibleTextEvent e) {
				Point sel = text.getSelection();
				e.offset = sel.x;
				e.length = sel.y - sel.x;
			}
		});

		getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {
			public void getChildAtPoint(AccessibleControlEvent e) {
				Point testPoint = toControl(e.x, e.y);
				if (getBounds().contains(testPoint)) {
					e.childID = ACC.CHILDID_SELF;
				}
			}

			public void getLocation(AccessibleControlEvent e) {
				Rectangle location = getBounds();
				Point pt = getParent().toDisplay(location.x, location.y);
				e.x = pt.x;
				e.y = pt.y;
				e.width = location.width;
				e.height = location.height;
			}

			public void getChildCount(AccessibleControlEvent e) {
				e.detail = 0;
			}

			public void getRole(AccessibleControlEvent e) {
				e.detail = ACC.ROLE_COMBOBOX;
			}

			public void getState(AccessibleControlEvent e) {
				e.detail = ACC.STATE_NORMAL;
			}

			public void getValue(AccessibleControlEvent e) {
				e.result = text.getText();
			}
		});

		text.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {
			public void getRole(AccessibleControlEvent e) {
				e.detail = text.getEditable() ? ACC.ROLE_TEXT : ACC.ROLE_LABEL;
			}
		});

		arrow.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter() {
			public void getDefaultAction(AccessibleControlEvent e) {
				e.result = isDropped() ? SWT.getMessage("SWT_Close") : SWT.getMessage("SWT_Open"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		});
	}

	/**
	 * returns if the drop down is currently open
	 * 
	 * @return
	 */
	private boolean isDropped() {
		return popup.getVisible();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isFocusControl() {
		if (isDisposed())
			return false;
		// if (label.isFocusControl () || arrow.isFocusControl () ||
		// table.isFocusControl () || popup.isFocusControl ()) {
		if (arrow.isFocusControl() || table.isFocusControl() || popup.isFocusControl()) {
			return true;
		}
		return super.isFocusControl();
	}

	/**
	 * Handles Table Events.
	 * 
	 * @param event
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void tableEvent(Event event) {
		switch (event.type) {
		case SWT.Dispose:
			if (popup == null || getShell() != popup.getParent()) {
				int selectionIndex = table.getSelectionIndex();
				if (popup != null && !popup.isDisposed())
					popup.dispose();
				popup = null;
				table = null;
				createPopup(selectionIndex);
			}
			break;
		case SWT.FocusIn: {
			handleFocus(SWT.FocusIn);
			break;
		}
		case SWT.MouseUp: {
			if (event.button != 1)
				return;
			dropDown(false);
			break;
		}
		case SWT.Selection: {
			int index = table.getSelectionIndex();
			if (index == -1)
				return;

			// refresh the text.
			refreshText(index, true, true);

			// set the selection in the table.
			table.setSelection(index);

			Event e = new Event();
			e.time = event.time;
			e.stateMask = event.stateMask;
			e.doit = event.doit;
			notifyListeners(SWT.Selection, e);
			event.doit = e.doit;
			break;
		}
		case SWT.KeyUp: {
			Event e = new Event();
			e.time = event.time;
			e.character = event.character;
			e.keyCode = event.keyCode;
			e.stateMask = event.stateMask;
			notifyListeners(SWT.KeyUp, e);
			break;
		}
		case SWT.KeyDown: {
			if (event.character == SWT.ESC) {
				// Escape key cancels popup list
				dropDown(false);
			}
			if ((event.stateMask & SWT.ALT) != 0
					&& (event.keyCode == SWT.ARROW_UP || event.keyCode == SWT.ARROW_DOWN)) {
				dropDown(false);
			}
			if (event.character == SWT.CR) {
				// Enter causes default selection
				dropDown(false);
				Event e = new Event();
				e.time = event.time;
				e.stateMask = event.stateMask;
				notifyListeners(SWT.DefaultSelection, e);
			}
			// At this point the widget may have been disposed.
			// If so, do not continue.
			if (isDisposed())
				break;
			Event e = new Event();
			e.time = event.time;
			e.character = event.character;
			e.keyCode = event.keyCode;
			e.stateMask = event.stateMask;
			notifyListeners(SWT.KeyDown, e);
			break;

		}
		}
	}

	/**
	 * Pastes text from clipboard.
	 * <p>
	 * The selected text is deleted from the widget and new text inserted from the
	 * clipboard.
	 * </p>
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 * 
	 * @since 3.3
	 */
	public void paste() {
		checkWidget();
		text.paste();
	}

	/**
	 * Handles Popup Events
	 * 
	 * @param event
	 */
	private void popupEvent(Event event) {
		switch (event.type) {
		case SWT.Paint:
			// draw rectangle around table
			Rectangle tableRect = table.getBounds();
			event.gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION));
			event.gc.drawRectangle(0, 0, tableRect.width + 1, tableRect.height + 1);
			break;
		case SWT.Close:
			event.doit = false;
			dropDown(false);
			break;
		case SWT.Deactivate:
			if (!isDisposed()) {
				if (!"carbon".equals(SWT.getPlatform())) {
					Point point = arrow.toControl(getDisplay().getCursorLocation());
					Point size = arrow.getSize();
					Rectangle rect = new Rectangle(0, 0, size.x, size.y);
					if (!rect.contains(point))
						dropDown(false);
				} else {
					dropDown(false);
				}
			}
			break;
		case SWT.Help:
			if (isDropped()) {
				dropDown(false);
			}
			Composite comp = JSSTableCombo.this;
			do {
				if (comp.getListeners(event.type) != null && comp.getListeners(event.type).length > 0) {
					comp.notifyListeners(event.type, event);
					break;
				}
				comp = comp.getParent();
			} while (null != comp);
			break;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void redraw() {
		super.redraw();
		text.redraw();
		arrow.redraw();
		if (popup != null && popup.isVisible())
			table.redraw();
	}

	/**
	 * {@inheritDoc}
	 */
	public void redraw(int x, int y, int width, int height, boolean all) {
		super.redraw(x, y, width, height, true);
	}

	/**
	 * Selects the item at the given zero-relative index in the receiver's list. If
	 * the item at the index was already selected, it remains selected. Indices that
	 * are out of range are ignored. Doesn't fire events
	 *
	 * @param index the index of the item to select
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 */
	public void select(int index) {
		select(index, false, true);
	}

	protected void select(int index, boolean fireEvent, boolean disableTextEvent) {
		checkWidget();

		// deselect if a value of -1 is passed in.
		if (index == -1) {
			table.deselectAll();
			text.setText("");
			imageLabel.setImage(null);
			return;
		}

		if (0 <= index && index < table.getItemCount()) {
			if (index != getSelectionIndex()) {

				// refresh the text field and image label
				refreshText(index, fireEvent, disableTextEvent);

				// select the row in the table.
				table.setSelection(index);
			}
		}
	}

	/**
	 * Sets the editable state.
	 *
	 * @param editable the new editable state
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 * 
	 * @since 3.0
	 */
	public void setEditable(boolean editable) {
		checkWidget();
		text.setEditable(editable);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (popup != null)
			popup.setVisible(false);
		if (imageLabel != null)
			imageLabel.setEnabled(enabled);
		if (text != null)
			text.setEnabled(enabled);
		if (arrow != null)
			arrow.setEnabled(enabled);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean setFocus() {
		checkWidget();
		if (!isEnabled() || !isVisible())
			return false;
		if (isFocusControl())
			return true;

		return text.setFocus();
	}

	@Override
	public boolean forceFocus() {
		checkWidget();
		if (!isEnabled() || !isVisible())
			return false;
		if (isFocusControl())
			return true;

		return text.forceFocus();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFont(Font font) {
		super.setFont(font);
		this.font = font;
		text.setFont(font);
		table.setFont(font);
		layout(true, true);
	}

	/**
	 * Sets the layout which is associated with the receiver to be the argument
	 * which may be null.
	 * <p>
	 * Note : No Layout can be set on this Control because it already manages the
	 * size and position of its children.
	 * </p>
	 *
	 * @param layout the receiver's new layout or null
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 */
	public void setLayout(Layout layout) {
		checkWidget();
		return;
	}

	/**
	 * Marks the receiver's list as visible if the argument is <code>true</code>,
	 * and marks it invisible otherwise.
	 * <p>
	 * If one of the receiver's ancestors is not visible or some other condition
	 * makes the receiver not visible, marking it visible may not actually cause it
	 * to be displayed.
	 * </p>
	 *
	 * @param visible the new visibility state
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 * 
	 * @since 3.4
	 */
	public void setTableVisible(boolean visible) {
		checkWidget();
		dropDown(visible);
	}

	/**
	 * Sets the selection in the receiver's text field to the range specified by the
	 * argument whose x coordinate is the start of the selection and whose y
	 * coordinate is the end of the selection.
	 *
	 * @param selection a point representing the new selection start and end
	 *
	 * @exception IllegalArgumentException
	 *                                     <ul>
	 *                                     <li>ERROR_NULL_ARGUMENT - if the point is
	 *                                     null</li>
	 *                                     </ul>
	 * @exception SWTException
	 *                                     <ul>
	 *                                     <li>ERROR_WIDGET_DISPOSED - if the
	 *                                     receiver has been disposed</li>
	 *                                     <li>ERROR_THREAD_INVALID_ACCESS - if not
	 *                                     called from the thread that created the
	 *                                     receiver</li>
	 *                                     </ul>
	 */
	public void setSelection(Point selection) {
		checkWidget();
		if (selection == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		text.setSelection(selection.x, selection.y);
	}

	/**
	 * Selects all the text.
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 */
	public void selectAll() {
		checkWidget();
		text.selectAll();
	}

	/**
	 * Sets the maximum number of characters that the receiver's text field is
	 * capable of holding to be the argument.
	 *
	 * @param limit new text limit
	 *
	 * @exception IllegalArgumentException
	 *                                     <ul>
	 *                                     <li>ERROR_CANNOT_BE_ZERO - if the limit
	 *                                     is zero</li>
	 *                                     </ul>
	 * @exception SWTException
	 *                                     <ul>
	 *                                     <li>ERROR_WIDGET_DISPOSED - if the
	 *                                     receiver has been disposed</li>
	 *                                     <li>ERROR_THREAD_INVALID_ACCESS - if not
	 *                                     called from the thread that created the
	 *                                     receiver</li>
	 *                                     </ul>
	 */
	public void setTextLimit(int limit) {
		checkWidget();
		text.setTextLimit(limit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setToolTipText(String tipText) {
		checkWidget();
		super.setToolTipText(tipText);
		if (imageLabel != null)
			imageLabel.setToolTipText(tipText);
		if (text != null)
			text.setToolTipText(tipText);
		if (arrow != null)
			arrow.setToolTipText(tipText);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		/*
		 * At this point the widget may have been disposed in a FocusOut event. If so
		 * then do not continue.
		 */
		if (isDisposed())
			return;
		// TEMPORARY CODE
		if (popup == null || popup.isDisposed())
			return;
		if (!visible)
			popup.setVisible(false);
	}

	/**
	 * Sets the number of items that are visible in the drop down portion of the
	 * receiver's list.
	 *
	 * @param count the new number of items to be visible
	 *
	 * @exception SWTException
	 *                         <ul>
	 *                         <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                         disposed</li>
	 *                         <li>ERROR_THREAD_INVALID_ACCESS - if not called from
	 *                         the thread that created the receiver</li>
	 *                         </ul>
	 * 
	 * @since 3.0
	 */
	public void setVisibleItemCount(int count) {
		checkWidget();
		if (count > 0) {
			visibleItemCount = count;
		}
	}

	/**
	 * @param string
	 * @return
	 */
	private String stripMnemonic(String string) {
		int index = 0;
		int length = string.length();
		do {
			while ((index < length) && (string.charAt(index) != '&'))
				index++;
			if (++index >= length)
				return string;
			if (string.charAt(index) != '&') {
				return string.substring(0, index - 1) + string.substring(index, length);
			}
			index++;
		} while (index < length);
		return string;
	}

	/**
	 * Defines what columns the drop down table will have.
	 * 
	 * Use this method when you don't care about the column headers and you want the
	 * columns to be automatically sized based upon their content.
	 * 
	 * @param columnHeaders
	 * @param columnWidths
	 */

	public void defineColumns(int numberOfColumnsToCreate) {
		if (numberOfColumnsToCreate > 0) {
			totalColumns = numberOfColumnsToCreate;
			columnHeaders = null;
			columnWidths = null;
			defineColumnsInternal(null, null, numberOfColumnsToCreate);
		}

	}

	/**
	 * Defines what columns the drop down table will have.
	 * 
	 * Use this method when you want to specify the column header text and the
	 * column widths.
	 * 
	 * @param columnHeaders
	 * @param columnBounds
	 */
	public void defineColumns(String[] columnHeaders, int[] columnBounds) {
		if (columnHeaders != null || columnBounds != null) {
			int total = columnHeaders == null ? 0 : columnHeaders.length;
			if (columnBounds != null && columnBounds.length > total) {
				total = columnBounds.length;
			}

			this.columnWidths = columnBounds;
			this.columnHeaders = columnHeaders;
			this.totalColumns = total;

			// define the columns
			defineColumnsInternal(columnHeaders, columnBounds, total);
		}
	}

	/**
	 * Defines what columns the drop down table will have.
	 * 
	 * @param columnHeaders
	 * @param columnBounds
	 */
	private void defineColumnsInternal(String[] columnHeaders, int[] columnBounds, int totalColumnsToBeCreated) {

		if (table == null)
			return;
		checkWidget();

		int totalColumnHeaders = columnHeaders == null ? 0 : columnHeaders.length;
		int totalColBounds = columnBounds == null ? 0 : columnBounds.length;

		if (totalColumnsToBeCreated > 0) {

			for (int index = 0; index < totalColumnsToBeCreated; index++) {
				TableColumn column = new TableColumn(table, SWT.NONE);

				if (index < totalColumnHeaders) {
					column.setText(columnHeaders[index]);
				}

				if (index < totalColBounds) {
					column.setWidth(columnBounds[index]);
				}

				column.setResizable(true);
				column.setMoveable(true);
			}
		}
	}

	/**
	 * Sets the table width percentage in relation to the width of the label
	 * control.
	 * 
	 * The default value if 100% which means that it will be the same size as the
	 * label control. If you want the table to be wider than the label then just
	 * display a value higher than 100%.
	 * 
	 * @param ddWidthPct
	 */
	public void setTableWidthPercentage(int ddWidthPct) {
		checkWidget();

		// don't accept invalid input.
		if (ddWidthPct > 0 && ddWidthPct <= 100) {
			this.tableWidthPercentage = ddWidthPct;
		}
	}

	/**
	 * Sets the zero-relative column index that will be used to display the
	 * currently selected item in the label control.
	 * 
	 * @param displayColumnIndex
	 */
	public void setDisplayColumnIndex(int displayColumnIndex) {
		checkWidget();

		if (displayColumnIndex >= 0) {
			this.displayColumnIndex = displayColumnIndex;
		}
	}

	/**
	 * returns the column index of the TableColumn to be displayed when selected.
	 * 
	 * @return
	 */
	private int getDisplayColumnIndex() {
		// make sure the requested column index is valid.
		return (displayColumnIndex <= (table.getColumnCount() - 1) ? displayColumnIndex : 0);
	}

	/*
	 * Return the lowercase of the first non-'&' character following an '&'
	 * character in the given string. If there are no '&' characters in the given
	 * string, return '\0'.
	 */
	private char _findMnemonic(String string) {
		if (string == null)
			return '\0';
		int index = 0;
		int length = string.length();
		do {
			while (index < length && string.charAt(index) != '&')
				index++;
			if (++index >= length)
				return '\0';
			if (string.charAt(index) != '&')
				return Character.toLowerCase(string.charAt(index));
			index++;
		} while (index < length);
		return '\0';
	}

	/**
	 * Refreshes the label control with the selected object's details.
	 */
	private synchronized void refreshText(int index, boolean fireEvent, boolean disableTextEvent) {

		// get a reference to the selected TableItem
		TableItem tableItem = table.getItem(index);

		// get the TableItem index to use for displaying the text.
		int colIndexToUse = getDisplayColumnIndex();

		// set color if requested
		if (showColorWithinSelection && !isInherited) {
			text.setForeground(tableItem.getForeground(colIndexToUse));
		}

		// set font if requested
		if (showFontWithinSelection) {
			// set the selected font
			text.setFont(tableItem.getFont(colIndexToUse));
		}

		// set the label text and fire the selection event.
		List<Pair<Integer, Listener>> textListeners = null;
		if (disableTextEvent)
			textListeners = removeTextListeners(false);
		String textualValue = tableItem.getText(colIndexToUse);
		text.setText(textualValue);
		if (disableTextEvent)
			restoreTextListeners(textListeners);
		if (fireEvent) {
			ValueChangedEvent event = new ValueChangedEvent(textualValue, tableItem, false);
			for (ValueChangedListener listener : modifyListeners) {
				listener.valueChanged(event);
			}
		}
	}

	/**
	 * @param showColorWithinSelection
	 */
	public void setShowColorWithinSelection(boolean showColorWithinSelection) {
		checkWidget();
		this.showColorWithinSelection = showColorWithinSelection;
	}

	/**
	 * @param showFontWithinSelection
	 */
	public void setShowFontWithinSelection(boolean showFontWithinSelection) {
		checkWidget();
		this.showFontWithinSelection = showFontWithinSelection;
	}

	/**
	 * returns the Table reference.
	 * 
	 * NOTE: the access is public for now but will most likely be changed in a
	 * future release.
	 * 
	 * @return
	 */
	public Table getTable() {
		checkWidget();
		return table;
	}

	/**
	 * Set if the value shown is inherited or not (an inherited value uses a
	 * different font color)
	 * 
	 * @param value true if the value is inherited, false otherwise
	 */
	public void setInherithed(boolean isInherited) {
		this.isInherited = isInherited;
		if (isInherited) {
			text.setForeground(inheritedColor);
		} else {
			text.setForeground(standardColor);
		}
	}

	/**
	 * determines if the user explicitly set a column width for a given column
	 * index.
	 * 
	 * @param columnIndex
	 * @return
	 */
	private boolean wasColumnWidthSpecified(int columnIndex) {
		return (columnWidths != null && columnWidths.length > columnIndex && columnWidths[columnIndex] != SWT.DEFAULT);
	}

	/**
	 * adjusts the last table column width to fit inside of the table if the table
	 * column data does not fill out the table area.
	 */
	private void autoAdjustColumnWidthsIfNeeded(TableColumn[] tableColumns, int totalAvailWidth,
			int totalColumnWidthUsage) {

		int scrollBarSize = 0;
		int totalColumns = (tableColumns == null ? 0 : tableColumns.length);

		// determine if the vertical scroll bar needs to be taken into account
		if (table.getVerticalBar().getVisible()) {
			scrollBarSize = (table.getVerticalBar() == null ? 0 : table.getVerticalBar().getSize().x);
		}

		// is there any extra space that the table is not using?
		if (totalAvailWidth > totalColumnWidthUsage + scrollBarSize) {
			int totalAmtToBeAllocated = (totalAvailWidth - totalColumnWidthUsage - scrollBarSize);

			// add unused space to the last column.
			if (totalAmtToBeAllocated > 0) {
				tableColumns[totalColumns - 1]
						.setWidth(tableColumns[totalColumns - 1].getWidth() + totalAmtToBeAllocated);
			}
		}
	}

	/**
	 * Returns the Text control reference.
	 * 
	 * @return
	 */
	public StyledText getTextControl() {
		checkWidget();
		return text;
	}
}
