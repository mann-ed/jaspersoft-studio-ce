/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.wizard.resource.page.selector;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.server.WSClientHelper;
import com.jaspersoft.studio.server.model.AFileResource;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.wizard.resource.page.AFileResourcePageContent;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.FileUtils;

public abstract class ATextFileResourcePageContent extends AFileResourcePageContent {
	public ATextFileResourcePageContent(ANode parent, AMResource resource, DataBindingContext bindingContext) {
		super(parent, resource, bindingContext);
	}

	public ATextFileResourcePageContent(ANode parent, AMResource resource) {
		super(parent, resource);
	}

	private Text txt;
	private boolean refresh = false;

	@Override
	protected void createFileTab(Composite composite) {
		txt = new Text(composite, SWT.BORDER | SWT.V_SCROLL | SWT.WRAP);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		txt.setLayoutData(gd);
		txt.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				if (refresh)
					return;
				try {
					File f = ((AFileResource) res).getFile();
					if (f == null) {
						f = File.createTempFile("jrsimgfile", ".properties"); //$NON-NLS-1$ //$NON-NLS-2$
						f.deleteOnExit();
						f.createNewFile();
						((AFileResource) res).setFile(f);
					}
					FileUtils.writeFile(f, txt.getText());
				} catch (IOException e1) {
					UIUtils.showError(e1);
				}
			}
		});
	}

	@Override
	protected void handleFileChange() {
		super.handleFileChange();
		UIUtils.getDisplay().asyncExec(new Runnable() {

			public void run() {
				try {
					refresh = true;
					File f = ((AFileResource) res).getFile();
					if (f == null && !res.getValue().getIsNew()) {
						f = File.createTempFile("jrsimgfile", ".properties"); //$NON-NLS-1$ //$NON-NLS-2$
						f.deleteOnExit();
						f.createNewFile();
						WSClientHelper.getResource(new NullProgressMonitor(), res.getRoot() != null ? res : pnode,
								res.getValue(), f);
					}
					if (f != null && f.exists())
						txt.setText(FileUtils.readFileAsAString(f));
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					refresh = false;
				}
			}
		});
	}
}
