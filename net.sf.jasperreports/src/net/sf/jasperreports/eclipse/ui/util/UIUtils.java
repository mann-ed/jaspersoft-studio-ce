/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.eclipse.core.commands.operations.OperationStatus;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGBA;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wb.swt.ResourceManager;

import net.sf.jasperreports.eclipse.JasperReportsPlugin;
import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.eclipse.ui.util.RunnableCancelQuestion.RESPONSE_TYPE;
import net.sf.jasperreports.eclipse.util.Pair;

public class UIUtils {
	
	public static final Color INHERITED_COLOR = ResourceManager.getColor(new RGBA(125, 125, 125, 125));

	// Placeholder for the SWT.SPACE constant, since it is not available in
	// 3.6.x
	public static final char SWT_SPACE = ' ';

	/**
	 * Shell provider to get the current shell
	 */
	private static final IShellProvider SHELL_PROVIDER = new IShellProvider() {

		@Override
		public Shell getShell() {
			return getShell();
		}
	};

	public static void showError(final String message, final Throwable t) {
		t.printStackTrace();
		getDisplay().asyncExec(new Runnable() {
			public void run() {

				showErrorDialog(message, t);
			}

		});

	}

	public static void showErrorDialog(final String message, final Throwable t) {
		IStatus status = new OperationStatus(IStatus.ERROR, JasperReportsPlugin.getDefault().getPluginID(),
				OperationStatus.NOTHING_TO_REDO, message, t);
		ExceptionDetailsErrorDialog exceptionDialog = new ExceptionDetailsErrorDialog(getShell(),
				Messages.UIUtils_ExceptionTitle, Messages.UIUtils_ExceptionDetailsMsg, status,
				IStatus.OK | IStatus.INFO | IStatus.WARNING | IStatus.ERROR) {
			
			@Override
			protected void setShellStyle(int newShellStyle) {
				super.setShellStyle(newShellStyle | SWT.SHEET);
			}

			@Override
			protected void populateList(Text listToPopulate, IStatus buildingStatus, int nesting,
					boolean includeStatus) {
				super.populateList(listToPopulate, buildingStatus, nesting, includeStatus);
				Throwable t = buildingStatus.getException();
				// Try to print the cause also in cases of CoreException when
				// there is
				// not details text
				if (listToPopulate.getText().isEmpty() && t instanceof CoreException) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < nesting; i++) {
						sb.append(" "); //$NON-NLS-1$
					}

					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					t.printStackTrace(pw);

					String message = sw.getBuffer().toString();
					if (message == null) {
						message = t.toString();
					}
					int causeIndex = message.indexOf("Caused by:"); //$NON-NLS-1$
					if (causeIndex != -1) {
						sb.append(message.substring(causeIndex));
						listToPopulate.append(sb.toString());
					}
				}
			}
		};
		exceptionDialog.open();
	}

	public static void showError(Throwable t) {
		showError(t.getMessage(), t);
	}

	public static void showWarning(final String message) {
		showWarning(Messages.UIUtils_Warning, message);
	}

	public static void showWarning(final String title, final String message) {
		getDisplay().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.open(MessageDialog.WARNING, getShell(), title, message, SWT.SHEET);
			}
		});
	}

	/**
	 * Show a confirmation dialog with the buttons yes and no. It is executed in
	 * the graphic thread so this method dosen't need to be called inside a the
	 * display thread.
	 * 
	 * @param title
	 *            the title of the dialog
	 * @param the
	 *            text of the dialog, c
	 * @return true if the user clicked the yes button, false otherwise
	 */
	public static boolean showConfirmation(String title, String message) {
		RunnableQuestion questionMessage = new RunnableQuestion(title, message);
		getDisplay().syncExec(questionMessage);
		return questionMessage.getResult();
	}

	/**
	 * Show a confirmation dialog with the buttons yes, no and cancel. It is
	 * executed in the graphic thread so this method dosen't need to be called
	 * inside a the display thread.
	 * 
	 * @param title
	 *            the title of the dialog
	 * @param the
	 *            text of the dialog, c
	 * @return an enum that can be YES, NO or CANCEL
	 */
	public static RESPONSE_TYPE showCancellableConfirmation(String title, String message) {
		RunnableCancelQuestion questionMessage = new RunnableCancelQuestion(title, message);
		getDisplay().syncExec(questionMessage);
		return questionMessage.getResponse();
	}

	public static Pair<RESPONSE_TYPE, Boolean> showCancellableConfirmation(String title, String message,
			String checkBoxMessage) {
		RunnableCancelQuestion questionMessage = new RunnableCancelQuestion(title, message, checkBoxMessage);
		getDisplay().syncExec(questionMessage);
		return new Pair<RESPONSE_TYPE, Boolean>(questionMessage.getResponse(), questionMessage.getCheckboxResult());
	}

	/**
	 * Show a confirmation dialog with the buttons yes and no. It can also
	 * provide and additional checkbox and return when the one of the button was
	 * pressed if the checkbox was selected or not. It is executed in the
	 * graphic thread so this method dosen't need to be called inside a the
	 * display thread.
	 * 
	 * @param title
	 *            the title of the dialog
	 * @param the
	 *            text of the dialog
	 * @param checkBox
	 *            the message of the checkbox
	 * @return and array with two booleans, the first represent if the user has
	 *         clicked the button yes or no. The second represent the value of
	 *         the checkbox when the dialog was closed
	 */
	public static Boolean[] showConfirmation(String title, String message, String checkBox) {
		RunnableQuestion questionMessage = new RunnableQuestion(title, message, checkBox);
		getDisplay().syncExec(questionMessage);
		return new Boolean[] { questionMessage.getResult(), questionMessage.getCheckboxResult() };
	}

	/**
	 * @return true if yes
	 */
	public static boolean showDeleteConfirmation() {
		RunnableQuestion questionMessage = new RunnableQuestion(Messages.UIUtils_DeleteConfirmation.replace("&", ""), //$NON-NLS-1$//$NON-NLS-2$
				Messages.UIUtils_ResourceDeleteConfirmationMsg);
		questionMessage.setDefaultButton(1);
		getDisplay().syncExec(questionMessage);
		return questionMessage.getResult();
	}

	public static boolean showDeleteConfirmation(Shell shell) {
		return showDeleteConfirmation(shell, Messages.UIUtils_DeleteConfirmation.replace("&", ""));
	}

	/**
	 * @return true if yes
	 */
	public static boolean showDeleteConfirmation(String msg) {
		RunnableQuestion questionMessage = new RunnableQuestion(Messages.UIUtils_2, msg);
		questionMessage.setDefaultButton(1);
		getDisplay().syncExec(questionMessage);
		return questionMessage.getResult();
	}

	/**
	 * @return true if yes
	 */
	public static boolean showDeleteConfirmation(Shell shell, String msg) {
		ExtendedMessageDialog dialog = new ExtendedMessageDialog(shell, "Question", null, msg, MessageDialog.QUESTION,
				new String[] { Messages.UIUtils_AnswerYes, Messages.UIUtils_AnswerNo }, 1, null);
		dialog.open();
		return dialog.getResult();
	}

	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	public static void showInformation(final String message) {
		showInformation(Messages.UIUtils_InformationTitle, message);
	}

	public static void showInformation(final String title, final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.open(MessageDialog.INFORMATION, getShell(), title, message, SWT.SHEET);
			}
		});
	}

	/**
	 * Gets a valid {@link Display} instance trying the following steps:
	 * <ol>
	 * <li>get the current display from the UI thread if any;</li>
	 * <li>get the display from the running workbench;</li>
	 * <li>get a default display instance;</li>
	 * </ol>
	 * 
	 * @return a valid {@link Display} instance
	 */
	public static Display getDisplay() {
		// If we are in the UI Thread use that
		Display d = Display.getCurrent();
		if (d != null)
			return d;
		if (PlatformUI.isWorkbenchRunning())
			return PlatformUI.getWorkbench().getDisplay();
		d = Display.getDefault();
		if (d != null)
			return d;

		// Invalid thread access if it is not the UI Thread
		// and the workbench is not created.
		throw new SWTError(SWT.ERROR_THREAD_INVALID_ACCESS);
	}

	/**
	 * Gets a valid {@link Shell} instance trying the following steps:
	 * <ol>
	 * <li>get shell from the current active workbench window;</li>
	 * <li>get active shell from the display instance returned by
	 * {@link getDisplay};</li>
	 * </ol>
	 * 
	 * @return a valid {@link Shell} instance
	 */
	public static Shell getShell() {
		Shell shell = null;
		if (Util.isWindows()) {
			// return activeShell by default, because key handling is transfered
			// to the main window
			shell = getDisplay().getActiveShell();
			if (shell != null)
				return shell;
		}

		IWorkbenchWindow window = null;
		if (PlatformUI.isWorkbenchRunning()) {
			window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		}

		if (window != null) {
			shell = window.getShell();
		} else {
			shell = getDisplay().getActiveShell();
		}

		return shell;
	}

	/**
	 * Truncates a string to the specified max number of characters. Useful when
	 * composing a human-readable text for a {@link LabelProvider}.
	 * 
	 * @param str
	 *            the string to truncate
	 * @param maxChars
	 *            max chars
	 * @param suffix
	 *            the suffix to add if any, can be <code>null</code>
	 * @return
	 */
	public static String truncateStringForLabel(String str, int maxChars, String suffix) {
		Assert.isNotNull(str);
		String result = str.substring(0, Math.min(str.length(), maxChars));
		if (str.length() > maxChars) {
			result += (suffix != null) ? suffix : ""; //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Resize the input shell and re-locates it on the center of the screen.
	 * 
	 * @param shell
	 *            the shell instance
	 * @param newWidth
	 *            the new width in px or -1 to keep the actual width
	 * @param newHeight
	 *            the new height in px or -1 to keep the actual height
	 */
	public static void resizeAndCenterShell(Shell shell, int newWidth, int newHeight) {
		newWidth = newWidth == -1 ? shell.getSize().x : newWidth;
		newHeight = newHeight == -1 ? shell.getSize().y : newHeight;
		shell.setSize(newWidth, newHeight);
		centerDialog(shell);
	}

	public static void relayoutDialog(Shell shell, int maxWidth, int maxHeight) {
		Point oldsize = shell.getSize();
		shell.layout();
		shell.pack();
		Point sizeAfterPack = shell.getSize();
		Point sizeToSet = new Point(sizeAfterPack.x, sizeAfterPack.y);

		Rectangle r = Display.getCurrent().getClientArea();
		sizeToSet.x = getMaxSize(maxWidth, oldsize.x, r.width, sizeAfterPack.x);
		sizeToSet.y = getMaxSize(maxHeight, oldsize.y, r.height, sizeAfterPack.y);

		shell.setSize(sizeToSet.x, sizeToSet.y);
		shell.layout();
	}

	public static void relayoutDialogHeight(Shell shell, int maxHeight) {
		Point oldsize = shell.getSize();
		shell.layout();
		shell.pack();
		Point sizeAfterPack = shell.getSize();
		Point sizeToSet = new Point(sizeAfterPack.x, sizeAfterPack.y);

		Rectangle r = Display.getCurrent().getClientArea();
		sizeToSet.y = getMaxSize(maxHeight, oldsize.y, r.height, sizeAfterPack.y);

		shell.setSize(oldsize.x, sizeToSet.y);
		shell.layout();
	}

	public static void centerDialog(Shell shell) {
		Point clocation = getShellCenterLocation(shell);
		shell.setLocation(clocation.x, clocation.y);
	}

	public static Point getShellCenterLocation(Shell shell) {
		// Center the dialog always in the application monitor
		Shell mainApplicationShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		Rectangle bounds = mainApplicationShell.getMonitor().getClientArea();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		return new Point(x, y);
	}

	private static int getMaxSize(int max, int old, int val, int afterPack) {
		switch (max) {
		case -1:
			return afterPack > val ? val : afterPack;
		case 0:
			return old;
		default:
			return afterPack > max ? max : afterPack;
		}
	}

	private static final PaletteData palette = new PaletteData(0x00FF0000, 0x0000FF00, 0x000000FF);

	public static Image awt2Swt(BufferedImage img) {
		DataBuffer buffer = img.getData().getDataBuffer();
		if (buffer instanceof DataBufferInt) {
			int[] data = ((DataBufferInt) buffer).getData();
			ImageData imageData = new ImageData(img.getWidth(), img.getHeight(), 32, palette);
			imageData.setPixels(0, 0, data.length, data, 0);
			return new Image(getDisplay(), imageData);
		} else if (buffer instanceof DataBufferByte) {
			byte[] data = ((DataBufferByte) buffer).getData();
			return new Image(getDisplay(), new ImageData(img.getWidth(), img.getHeight(), 32, palette, 4, data));
		}
		return null;
	}

	/**
	 * Return a provider for the current shell
	 */
	public static IShellProvider getShellProvider() {
		return SHELL_PROVIDER;
	}

	public static void setEnabled(Control c, boolean enabled) {
		c.setEnabled(enabled);
		if (c instanceof Composite) {
			Control[] children = ((Composite) c).getChildren();
			if (children != null)
				for (Control child : children)
					setEnabled(child, enabled);
		}
	}

	public static void setEnabled(Control c, Map<Control, Boolean> map, boolean enabled) {
		if (c instanceof CTabFolder) {
			CTabFolder tf = (CTabFolder) c;
			for (CTabItem it : tf.getItems())
				setEnabled(it.getControl(), map, enabled);
		} else if (c instanceof TabFolder) {
			TabFolder tf = (TabFolder) c;
			for (TabItem it : tf.getItems())
				setEnabled(it.getControl(), map, enabled);
		} else if (c instanceof Composite) {
			Control[] children = ((Composite) c).getChildren();
			if (children != null)
				for (Control child : children)
					setEnabled(child, map, enabled);
		}
		if (enabled) {
			Boolean en = map.get(c);
			if (en != null)
				c.setEnabled(en.booleanValue());
			else
				c.setEnabled(true);
		} else {
			map.put(c, c.getEnabled());
			c.setEnabled(false);
		}

	}

	/**
	 * Checks if the current thread is the UI one.
	 * 
	 * @return <code>true</code> if the the current thread is the UI one of the
	 *         current display, <code>false</code> otherwise
	 */
	public static synchronized boolean isUIThread() {
		Display currDisplay = Display.getCurrent();
		return currDisplay != null && currDisplay.getThread() == Thread.currentThread();
	}

	/**
	 * Checks if the running thread is the UI one and if not raises an
	 * exception.
	 */
	public static synchronized void checkUIThread() {
		if (!isUIThread()) {
			SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);
		}
	}

	/**
	 * Checks if the "devmode" flag is set.
	 * 
	 * @return <code>true</code> if devmode is set,<code>false</code> otherwise
	 */
	public static boolean isDevMode() {
		String devmode = System.getProperty("devmode"); //$NON-NLS-1$
		return devmode != null && devmode.equals("true"); //$NON-NLS-1$
	}

	/**
	 * Simple debug method to print out a bunch of details about the specified
	 * composite.
	 * 
	 * @param cmp
	 *            the composite instance
	 * @param cmpName
	 *            a human-readable name for debug purposes
	 */
	public static void debugCompositeInfo(Composite cmp, String cmpName) {
		System.out.println("|========================|");
		System.out.println(NLS.bind("Composite {0} details: ", cmpName));
		System.out.println("Actual size: " + cmp.getSize());
		System.out.println("Client area:" + cmp.getClientArea());
		System.out.println("Bounds: " + cmp.getBounds());
	}
	
	public static boolean isMacOSX() {
		return Platform.OS_MACOSX.equals(Platform.getOS());
	}
	
	public static boolean isLinux() {
		return Platform.OS_LINUX.equals(Platform.getOS());
	}
	
	public static boolean isWindows() {
		return Platform.OS_WIN32.equals(Platform.getOS());
	}
}
