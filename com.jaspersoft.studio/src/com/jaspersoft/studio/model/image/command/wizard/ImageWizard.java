/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.model.image.command.wizard;

import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.eclipse.jface.wizard.Wizard;

import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.image.MImage;

public class ImageWizard extends Wizard {
	private MImage mimage;
	private WizardImagePage page5;

	public ImageWizard() {
		super();
		setWindowTitle(Messages.common_image);
	}

	@Override
	public void addPages() {

		mimage = new MImage(null, new JRDesignImage(jasperDesign), -1);

		page5 = new WizardImagePage();
		addPage(page5);
		page5.setMImage(mimage);
	}

	public MImage getImage() {
		return mimage;
	}

	@Override
	public boolean performFinish() {
		return true;
	}

	private JasperDesign jasperDesign;

	public void init(JasperDesign jd) {
		this.jasperDesign = jd;
	}
}
