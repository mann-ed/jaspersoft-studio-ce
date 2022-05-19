/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.wizard.resource.page;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Date;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.typed.PojoProperties;
import org.eclipse.core.databinding.conversion.Converter;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.server.messages.Messages;
import com.jaspersoft.studio.server.model.AMResource;
import com.jaspersoft.studio.server.model.MAdHocDataView;
import com.jaspersoft.studio.server.protocol.Feature;
import com.jaspersoft.studio.server.protocol.IConnection;
import com.jaspersoft.studio.server.protocol.restv2.DiffFields;
import com.jaspersoft.studio.server.utils.ValidationUtils;
import com.jaspersoft.studio.server.wizard.permission.PermissionDialog;
import com.jaspersoft.studio.server.wizard.permission.PermissionWizard;
import com.jaspersoft.studio.server.wizard.resource.APageContent;
import com.jaspersoft.studio.utils.UIUtil;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.ui.validator.EmptyStringValidator;
import net.sf.jasperreports.eclipse.ui.validator.IDStringValidator;
import net.sf.jasperreports.eclipse.util.Misc;

public class ResourcePageContent extends APageContent {

	private Text tname;
	private Text tid;
	private Text tudate;
	private Proxy proxy;
	private Text tparent;
	private Text tdesc;
	private Text tcdate;
	private Text ttype;
	private Button bisRef;
	private Button bPerm;

	public ResourcePageContent(ANode parent, AMResource resource, DataBindingContext bindingContext) {
		super(parent, resource, bindingContext);
	}

	public ResourcePageContent(ANode parent, AMResource resource) {
		super(parent, resource);
	}

	@Override
	public String getPageName() {
		return "com.jaspersoft.studio.server.page.resource"; //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return Messages.AResourcePage_title;
	}

	public Control createContent(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(4, false));

		UIUtil.createLabel(composite, Messages.AResourcePage_parentfolder);
		tparent = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		tparent.setLayoutData(gd);

		UIUtil.createLabel(composite, Messages.AResourcePage_type);
		ttype = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		ttype.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		bisRef = new Button(composite, SWT.CHECK);
		bisRef.setText(Messages.ResourcePageContent_isReference);
		bisRef.setEnabled(false);

		if (res.getWsClient() != null && res.getWsClient().isSupported(Feature.PERMISSION)) {
			bPerm = new Button(composite, SWT.PUSH);
			bPerm.setText(Messages.ResourcePageContent_0);
			if (res.getValue().getIsNew())
				bPerm.setEnabled(false);
			else
				bPerm.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						PermissionWizard wizard = new PermissionWizard(res);
						PermissionDialog dialog = new PermissionDialog(UIUtils.getShell(), wizard);
						dialog.addApplyListener(wizard);
						dialog.open();
					}
				});
		} else {
			gd = new GridData();
			gd.horizontalSpan = 2;
			bisRef.setLayoutData(gd);
		}

		UIUtil.createLabel(composite, Messages.AResourcePage_creationdate);
		tcdate = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		tcdate.setLayoutData(gd);

		ResourceDescriptor rd = res.getValue();
		proxy = new Proxy(rd);
		if (res.isSupported(Feature.UPDATEDATE)) {
			UIUtil.createLabel(composite, Messages.ResourcePageContent_UpdateDate);
			tudate = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 3;
			tudate.setLayoutData(gd);
		}

		UIUtil.createSeparator(composite, 4);

		UIUtil.createLabel(composite, Messages.AResourcePage_name);
		tname = new Text(composite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		gd.widthHint = 500;
		tname.setLayoutData(gd);

		UIUtil.createLabel(composite, Messages.AResourcePage_id);
		tid = new Text(composite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		gd.widthHint = 500;
		tid.setLayoutData(gd);

		UIUtil.createLabel(composite, Messages.AResourcePage_description);
		tdesc = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.WRAP);
		gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 100;
		gd.widthHint = 500;
		gd.horizontalSpan = 3;
		tdesc.setLayoutData(gd);

		tid.setEditable(rd.getIsNew());
		if (rd.getIsNew()) {
			rd.setName(rd.getLabel());
			tname.addModifyListener(e -> tid.setText(IDStringValidator.safeChar(Misc.nvl(tname.getText()))));
			tid.addModifyListener(e -> {
				if (refresh)
					return;
				refresh = true;
				String validationError = validateID(tid.getText());
				page.setErrorMessage(validationError);
				setPageComplete(validationError == null);
				refresh = false;
			});
			tid.addVerifyListener(e -> e.text = IDStringValidator.safeChar(e.text));
		}
		rebind();

		tname.setFocus();
		return composite;
	}

	class IDValidator implements IValidator<String> {
		private IDStringValidator validator = new IDStringValidator();

		@Override
		public IStatus validate(String value) {
			IStatus status = validator.validate(value);
			if (status.equals(Status.OK_STATUS)) {
				String err = validateID(value);
				if (err != null)
					status = ValidationStatus.error(err);
			}
			return status;
		}

	}

	private String validateID(String t) {
		String validationError = ValidationUtils.validateName(t);
		if (validationError == null)
			for (INode n1 : pnode.getChildren())
				if (n1 instanceof AMResource && n1 != res && ((AMResource) n1).getValue().getName() != null
						&& ((AMResource) n1).getValue().getName().equals(t))
					return "This id is already used in this folder";
		return validationError;
	}

	@Override
	protected void rebind() {
		ResourceDescriptor rd = res.getValue();
		if (tudate != null) {
			bindingContext.bindValue(
					WidgetProperties.text(SWT.NONE).observe(tudate),
					PojoProperties.value("updateDate").observe(proxy)); //$NON-NLS-1$
		}
		bindingContext.bindValue(
				WidgetProperties.text(SWT.NONE).observe(tparent),
				PojoProperties.value("parentFolder").observe(proxy)); //$NON-NLS-1$
		IConnection c = res.getWsClient();
		final Format f = (c != null ? c.getTimestampFormat() : DateFormat.getTimeInstance());

		IConverter t2mConv = new Converter(String.class, Date.class) {

			public Object convert(Object fromObject) {
				try {
					if (fromObject instanceof String && !((String) fromObject).isEmpty())
						return f.parseObject((String) fromObject);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		IConverter m2tConv = new Converter(Date.class, String.class) {

			public Object convert(Object fromObject) {
				if (fromObject == null)
					return ""; //$NON-NLS-1$
				return f.format(fromObject);
			}
		};
		bindingContext.bindValue(
				WidgetProperties.text(SWT.NONE).observe(tcdate),
				PojoProperties.value("creationDate").observe(rd), //$NON-NLS-1$
				new UpdateValueStrategy().setConverter(t2mConv),
				new UpdateValueStrategy().setConverter(m2tConv));
		bindingContext.bindValue(
				WidgetProperties.text(SWT.NONE).observe(ttype),
				PojoProperties.value("wsType").observe(rd)); //$NON-NLS-1$
		bindingContext.bindValue(
				WidgetProperties.widgetSelection().observe(bisRef),
				PojoProperties.value("isReference").observe(rd)); //$NON-NLS-1$
		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(tid),
				PojoProperties.value("name").observe(rd), //$NON-NLS-1$
				new UpdateValueStrategy().setAfterConvertValidator(new IDValidator()), null);
		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(tname),				
				PojoProperties.value("label").observe(rd), //$NON-NLS-1$
				new UpdateValueStrategy().setAfterConvertValidator(new EmptyStringValidator()), null);
		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(tdesc),	
				PojoProperties.value("description").observe(rd)); //$NON-NLS-1$
		bindingContext.updateTargets();

		final IConnection con = getWsClient();
		if (!rd.getIsNew() && !con.isSupported(Feature.SEARCHREPOSITORY) && res instanceof MAdHocDataView) {
			ttype.setEnabled(false);
			if (tname != null)
				tname.setEnabled(false);
			if (tdesc != null)
				tdesc.setEnabled(false);
			UIUtils.getDisplay().asyncExec(() -> {
				setPageComplete(false);
				page.setDescription(Messages.ResourcePageContent_3);
			});
		}
	}

	@Override
	public String getHelpContext() {
		return "com.jaspersoft.studio.doc.editResource"; //$NON-NLS-1$
	}

	@Override
	public boolean isPageComplete() {
		if (tid.getText().trim().isEmpty() || tname.getText().trim().isEmpty())
			return false;
		return super.isPageComplete();
	}

	class Proxy {
		private ResourceDescriptor rd;

		public Proxy(ResourceDescriptor rd) {
			this.rd = rd;
		}

		public ResourceDescriptor getResourceDescriptor() {
			return rd;
		}

		public String getUpdateDate() {
			return DiffFields.getSoapValue(rd, DiffFields.UPDATEDATE);
		}

		public void setUpdateDate(String name) {
		}

		public String getParentFolder() {
			String p = rd.getParentFolder();
			if (Misc.isNullOrEmpty(p))
				p = "/"; //$NON-NLS-1$
			return p;
		}

		public void setParentFolder(String p) {
			rd.setParentFolder(p);
		}
	}
}
