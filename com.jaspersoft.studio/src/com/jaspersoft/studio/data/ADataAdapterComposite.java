/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.swt.widgets.Composite;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.engine.JasperReportsContext;

public abstract class ADataAdapterComposite extends Composite {

	public final static String PREFIX = "com.jaspersoft.studio.doc.";
	
	protected DataAdapterDescriptor dataAdapterDesc;
	
	protected JasperReportsContext jrContext;
	
	protected DataBindingContext bindingContext;


	protected IChangeListener listener = new IChangeListener() {

		public void handleChange(ChangeEvent event) {
			pchangesuport.firePropertyChange("dirty", false, true);
		}
	};
	
	public ADataAdapterComposite(Composite parent, int style, JasperReportsContext jrContext) {
		super(parent, style);
		this.jrContext = jrContext;
		bindingContext = new DataBindingContext();
	}

	public DataBindingContext getBindingContext() {
		return bindingContext;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public String getHelpContextId() {
		return PREFIX.concat("dataAdapters_wizard_list");
	}

	public void removeBindings() {
		IObservableList<Binding> bindings = bindingContext.getBindings();
		for (Binding o : bindings) {
			bindingContext.removeBinding(o);
		}
	}

	public void removeDirtyListenersToContext() {
		IObservableList<Binding> bindings = bindingContext.getBindings();
		for (Binding o : bindings) {
			o.getTarget().removeChangeListener(listener);
		}
	}

	public void addDirtyListenersToContext() {
		IObservableList<Binding> bindings = bindingContext.getBindings();
		for (Binding o : bindings) {
			o.getTarget().addChangeListener(listener);
		}
	}

	@Override
	public void dispose() {
		IObservableList<Binding> bindings = bindingContext.getBindings();
		for (Binding o : bindings) {
			o.getTarget().removeChangeListener(listener);
		}
		super.dispose();
	}

	public void setDataAdapter(DataAdapterDescriptor dataAdapterDesc) {
		this.dataAdapterDesc = dataAdapterDesc;
		DataAdapter dataAdapter = dataAdapterDesc.getDataAdapter();

		removeDirtyListenersToContext();
		removeBindings();

		bindWidgets(dataAdapter);

		// bindingContext.updateTargets();

		addDirtyListenersToContext();
	}

	public JasperReportsContext getJrContext() {
		return jrContext;
	}

	protected abstract void bindWidgets(DataAdapter dataAdapter);

	public abstract DataAdapterDescriptor getDataAdapter();

	protected PropertyChangeSupport pchangesuport = new PropertyChangeSupport(this);

	public void addModifyListener(PropertyChangeListener listener) {
		pchangesuport.addPropertyChangeListener(listener);
	}

	public void removeModifyListener(PropertyChangeListener listener) {
		pchangesuport.removePropertyChangeListener(listener);
	}

	/**
	 * This generic method should be used by clients in order to perform additional custom updates involving the UI
	 * components.
	 */
	public void performAdditionalUpdates() {
		// Default: do nothing - subclasses should override if needed
	}

}
