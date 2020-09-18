/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.custom.adapter.export;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.messages.Messages;

/**
 * Custom page to provide the informations to export a data adapter
 * plugin project as a jar file.
 * 
 * @author Orlandin Marco
 *
 */
public class ExportAdapterWizardPage extends WizardPage  {
	
	private Object[] fSelection;
	
	protected TabFolder fTabFolder;
	
	private HashSet<IProject> selectedElements = new HashSet<IProject>();
	
	private String destination = "";
	
	public ExportAdapterWizardPage(IStructuredSelection selection) {
		super("aaaa");
		fSelection = selection.toArray();
		setTitle(Messages.ExportAdapterWizardPage_title);
		setDescription(Messages.ExportAdapterWizardPage_description);
	}

	
	public Object[] getListElements() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		return projects;
	}
	
	public Collection<IProject> getSelectedItems() {
		return selectedElements;
	}
	
	public String getDestination() {
		return destination;
	}
	
	private void chooseDestination(Text combo) {
		DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SAVE);
		String path = combo.getText();
		if (path.trim().length() == 0) {
			path = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		}
		dialog.setFilterPath(path);
		dialog.setText(Messages.ExportAdapterWizardPage_title);
		dialog.setMessage("Select a destination directory for the export operation");
		String res = dialog.open();
		if (res != null) {
			combo.setText(res);
		}
	}
	
	protected void validate () {
		if (selectedElements.size() == 0) {
			setErrorMessage("No items selected");
			setPageComplete(false);
		} else if (destination.trim().length() == 0) {
			setErrorMessage("Destination directory must be specified");
			setPageComplete(false);			
		} else {
			setPageComplete(true);
			setErrorMessage(null);
		}
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		new Label(container, SWT.NONE).setText("Available Plug-Ins:");
		Composite plugins = new Composite(container, SWT.NONE);
		plugins.setLayoutData(new GridData(GridData.FILL_BOTH));
		plugins.setBackground(ColorConstants.white);
		plugins.setLayout(new GridLayout(2, false));
		for(Object selection : fSelection) {
			if (selection instanceof IProject) {
				try {
					IProject project = (IProject)selection;
					Properties prop = new Properties();
					prop.load(project.getFolder("META-INF").getFile("MANIFEST.MF").getContents());
					Button checkBox = new Button(plugins, SWT.CHECK);
					checkBox.setSelection(selectedElements.contains(selection));
					checkBox.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							if (selectedElements.contains(selection)) {
								selectedElements.remove(selection);
							} else {
								selectedElements.add((IProject)selection);
							}
							validate();
						}
					});
					String name = prop.getProperty("Bundle-SymbolicName").split(";")[0] + " (" + prop.getProperty("Bundle-Version") + ")";
					new Label(plugins, SWT.NONE).setText(name);
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		fTabFolder = new TabFolder(container, SWT.NONE);
		fTabFolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    TabItem destinationTab = new TabItem(fTabFolder, SWT.NONE);
	    destinationTab.setText("Destination");
	    Composite tabContent = new Composite(fTabFolder, SWT.NONE);
	    tabContent.setLayout(new GridLayout(2, false));
	    Text destinationText = new Text(tabContent, SWT.BORDER);
	    destinationText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    Button browseButton = new Button(tabContent, SWT.PUSH);
	    browseButton.setText(Messages.common_browse);
	    browseButton.addSelectionListener(new SelectionAdapter() {
	    	public void widgetSelected(SelectionEvent e) {
	    		chooseDestination(destinationText);
	    	};
	    	
	    });
	    destinationText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				destination = ((Text)e.widget).getText();
				validate();
			}
		});
	    destinationTab.setControl(tabContent);
		setControl(container);
		validate();
	}
}
