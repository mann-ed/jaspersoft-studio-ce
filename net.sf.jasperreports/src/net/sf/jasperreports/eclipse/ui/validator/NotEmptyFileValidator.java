/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.ui.validator;

import java.io.File;
import java.io.InputStream;

import net.sf.jasperreports.eclipse.messages.Messages;
import net.sf.jasperreports.eclipse.util.CacheMap;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.repo.RepositoryUtil;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class NotEmptyFileValidator implements IValidator {
	private static final IStatus ERROR_STATUS = ValidationStatus.warning(Messages.NotEmptyFileValidator_filenotexists);
	private RepositoryUtil rutil;
	private Binding binding;
	private CacheMap<String, IStatus> cacheMap = new CacheMap<>(1000);
	private boolean empty = false;

	public NotEmptyFileValidator(JasperReportsContext jrContext) {
		super();
		rutil = RepositoryUtil.getInstance(jrContext);
	}

	public NotEmptyFileValidator(JasperReportsContext jrContext, boolean empty) {
		this(jrContext);
		this.empty = empty;
	}

	public void setBinding(Binding binding) {
		this.binding = binding;
	}

	public IStatus validate(Object value) {
		IStatus s = Status.OK_STATUS;
		if (!(value instanceof String))
			return empty ? s : ValidationStatus.error(Messages.NotEmptyFileValidator_0);
		String v = ((String) value).trim();
		if (v.isEmpty())
			return empty ? s : ValidationStatus.error(Messages.NotEmptyFileValidator_0);
		if (rutil != null) {
			if (binding == null)
				runValidator(v);
			else {
				IStatus status = cacheMap.get(v);
				if (status != null)
					return status;
				runValidator(v);
				return ERROR_STATUS;
			}
		} else {
			File f = new File(v);
			if (!f.exists() || !f.isFile())
				return ERROR_STATUS;
		}
		return s;
	}

	protected void runValidator(String v) {
		if (validator != null)
			validator.setCanceled(true);
		validator = new Validate(v);
		if (thread != null && !thread.isAlive() && !thread.isInterrupted())
			thread.interrupt();
		try {
			thread = new Thread(validator);
			thread.start();
		} catch (Throwable e) {
			// ignore error
		}
	}

	private Thread thread;
	private Validate validator;

	class Validate implements Runnable {
		private boolean canceled = false;
		private String v;

		public Validate(String v) {
			this.v = v;
		}

		public void setCanceled(boolean canceled) {
			this.canceled = canceled;
		}

		@Override
		public void run() {
			IStatus s = validate2Repository(v);
			if (!canceled) {
				cacheMap.put(v, s);
				if (binding != null && binding.getTarget() != null && binding.getModel() != null)
					binding.validateTargetToModel();
				thread = null;
			}
		}

		private IStatus validate2Repository(String v) {
			IStatus s = Status.OK_STATUS;
			InputStream is = null;
			try {
				is = rutil.getInputStreamFromLocation(v);
				if (is == null)
					s = ERROR_STATUS;
			} catch (Exception e) {
				s = ERROR_STATUS;
			} finally {
				FileUtils.closeStream(is);
			}
			return s;
		}
	}

}
