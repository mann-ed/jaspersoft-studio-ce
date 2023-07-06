/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.jface.dialogs;

import org.eclipse.swt.widgets.Shell;

import com.jaspersoft.studio.messages.Messages;

/**
 * Dialog proposed when an image needs to be selected.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class ImageSelectionDialog extends FilePreviewSelectionDialog {

	private static final String SVG_TYPE = ".svg"; //$NON-NLS-1$
	private static final String GIF_TYPE = "*.gif"; //$NON-NLS-1$
	private static final String JPEG_JPG_TYPE = "*.jpeg; *.jpg"; //$NON-NLS-1$
	private static final String ALL_IMAGES_TYPE = "*.png;*.jpeg;*.jpg;*.gif;*.svg"; //$NON-NLS-1$
	private static final String PNG_TYPE = "*.png"; //$NON-NLS-1$

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public ImageSelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * @return the title for the dialog
	 */
	protected String getDialogTitle() {
		return Messages.ImageSelectionDialog_0;
	}

	/**
	 * Returns an array of strings containing the title for the modes section, plus the title of every mode.
	 * <p>
	 * 
	 * Default implementation would return 6 strings, including 1 title and the following 5 modes:
	 * <ol>
	 * <li>workspace resource;</li>
	 * <li>absolute path in filesystem;</li>
	 * <li>URL;</li>
	 * <li>no image;</li>
	 * <li>custom expression</li>
	 * </ol>
	 * 
	 * @return the title and labels for the group of modes
	 */
	protected String[] getImageModesAndHeaderTitles() {
		return new String[] { Messages.ImageSelectionDialog_1, Messages.ImageSelectionDialog_2,
				Messages.ImageSelectionDialog_3,
				Messages.ImageSelectionDialog_4,
				Messages.ImageSelectionDialog_5,
				Messages.ImageSelectionDialog_6 };
	}

	@Override
	protected String getDefaultResourcesPattern() {
		return PNG_TYPE; //$NON-NLS-1$
	}

	@Override
	public String getSupportedTypeName(String type) {
		switch (type) {
		case ALL_IMAGES_TYPE:
			return Messages.ImageSelectionDialog_AllImagesTxt;
		case PNG_TYPE:
			return Messages.ImageSelectionDialog_PngTxt;
		case JPEG_JPG_TYPE:
			return Messages.ImageSelectionDialog_JpegTxt;
		case GIF_TYPE:
			return Messages.ImageSelectionDialog_GifTxt;
		case SVG_TYPE:
			return Messages.ImageSelectionDialog_SvgTxt;
		case ALL_FILES_TYPE:
			return Messages.ImageSelectionDialog_AllFilesTxt;
		default:
			return super.getSupportedTypeName(type);
		}
	}
	
	

	@Override
	public String[] getSupportedTypes() {
		return new String[] {
				ALL_IMAGES_TYPE, PNG_TYPE, JPEG_JPG_TYPE, GIF_TYPE, SVG_TYPE, ALL_FILES_TYPE }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
}
