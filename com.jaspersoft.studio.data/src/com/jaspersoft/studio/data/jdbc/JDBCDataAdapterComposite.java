/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.data.jdbc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.databinding.beans.typed.PojoProperties;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.data.ADataAdapterComposite;
import com.jaspersoft.studio.data.DataAdapterDescriptor;
import com.jaspersoft.studio.data.messages.Messages;
import com.jaspersoft.studio.data.secret.DataAdaptersSecretsProvider;
import com.jaspersoft.studio.property.section.widgets.CustomAutoCompleteField;
import com.jaspersoft.studio.swt.widgets.ClasspathComponent;
import com.jaspersoft.studio.swt.widgets.PropertiesComponent;
import com.jaspersoft.studio.swt.widgets.WSecretText;
import com.jaspersoft.studio.utils.UIUtil;

import net.sf.jasperreports.data.DataAdapter;
import net.sf.jasperreports.data.jdbc.JdbcDataAdapter;
import net.sf.jasperreports.data.jdbc.JdbcDataAdapterImpl;
import net.sf.jasperreports.data.jdbc.TransactionIsolation;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.engine.JasperReportsContext;

public class JDBCDataAdapterComposite extends ADataAdapterComposite {

	private final static JDBCDriverDefinition[] jdbcDefinitions;

	static {
		List<JDBCDriverDefinition> tmpDefinitions = JaspersoftStudioPlugin.getExtensionManager()
				.getJDBCDriverDefinitions();
		jdbcDefinitions = tmpDefinitions.toArray(new JDBCDriverDefinition[tmpDefinitions.size()]);
		Arrays.sort(jdbcDefinitions, new Comparator<JDBCDriverDefinition>() {
			@Override
			public int compare(JDBCDriverDefinition o1, JDBCDriverDefinition o2) {
				return o1.getDbName().compareTo(o2.getDbName());
			}
		});
	}

	public JDBCDriverDefinition[] getDefinitions() {
		return jdbcDefinitions;
	}

	protected Text textJDBCUrl;
	protected Text textServerAddress;
	protected Text textDatabase;
	protected Text textUsername;
	protected WSecretText textPassword;

	// private Button btnSavePassword;
	protected Combo comboJDBCDriver;

	protected JDBCDriverDefinition currentdriver = null;

	private ClasspathComponent cpath;
	private PropertiesComponent cproperties;
	protected JDBCDriverDefinition[] definitions;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public JDBCDataAdapterComposite(Composite parent, int style, JasperReportsContext jrContext) {
		super(parent, style, jrContext);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);

		createPreWidgets(this);

		CTabFolder tabFolder = new CTabFolder(this, SWT.BOTTOM);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		tabFolder.setSelectionBackground(
				Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

		createLocationTab(tabFolder);

		createPropertiesTab(tabFolder);

		createClasspathTab(tabFolder);
		tabFolder.setSelection(0);
		contextId = "adapter_JDBC";
	}

	protected void createPreWidgets(Composite parent) {

	}

	protected void createClasspathTab(CTabFolder tabFolder) {
		CTabItem tbtmNewItem_1 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem_1.setText(Messages.JDBCDataAdapterComposite_classpath);

		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		tbtmNewItem_1.setControl(composite);

		cpath = new ClasspathComponent(composite) {
			@Override
			protected void handleClasspathChanged() {
				pchangesuport.firePropertyChange("dirty", false, true);
			}
		};
		cpath.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	protected void createPropertiesTab(CTabFolder tabFolder) {
		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText(Messages.JDBCDataAdapterComposite_connectionproperties);

		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		tbtmNewItem.setControl(composite);

		new Label(composite, SWT.NONE).setText("Auto Commit");

		bAc = new Combo(composite, SWT.READ_ONLY);
		bAc.setItems(new String[] { "", "True", "False" });

		new Label(composite, SWT.NONE).setText("Read Only");
		bRO = new Combo(composite, SWT.READ_ONLY);
		bRO.setItems(new String[] { "", "True", "False" });

		new Label(composite, SWT.NONE).setText("Transaction Isolation");
		bTI = new Combo(composite, SWT.READ_ONLY);
		bTI.setItems(
				new String[] { "", "None", "Read Uncommitted", "Read Committed", "Repeatable Read", "Serializable" });

		cproperties = new PropertiesComponent(composite) {
			@Override
			protected void handlePropertiesChanged() {
				pchangesuport.firePropertyChange("dirty", false, true);
			}
		};
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		cproperties.getControl().setLayoutData(gd);
	}

	protected void createLocationTab(CTabFolder tabFolder) {
		CTabItem tbtmNewItem_2 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem_2.setText(Messages.JDBCDataAdapterComposite_tablocation);

		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		tbtmNewItem_2.setControl(composite);

		Label lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		lbl.setText(Messages.JDBCDataAdapterComposite_driverlabel);

		comboJDBCDriver = new Combo(composite, SWT.NONE);
		comboJDBCDriver.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		lbl.setText(Messages.JDBCDataAdapterComposite_urllabel);

		textJDBCUrl = new Text(composite, SWT.BORDER);
		textJDBCUrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createURLAssistant(composite);

		createUserPass(composite);

		definitions = getDefinitions();
		String[] items = new String[definitions.length];
		for (int i = 0; i < definitions.length; ++i)
			items[i] = definitions[i].toString();
		comboJDBCDriver.setItems(items);
		final CustomAutoCompleteField driverAutocomplete = new CustomAutoCompleteField(comboJDBCDriver,
				new ComboContentAdapter(), items);
		comboJDBCDriver.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				driverAutocomplete.closeProposalPopup();
			}
		});

		comboJDBCDriver.select(0);
		comboJDBCDriver.addModifyListener(driverModifyListener);
	}

	private ModifyListener driverModifyListener = new ModifyListener() {

		@Override
		public void modifyText(ModifyEvent e) {
			Combo combo = (Combo) e.widget;
			String text = combo.getText();
			int selectionIndex = -1;
			for (int i = 0; i < definitions.length; i++) {
				String definition = definitions[i].toString();
				if (definition.equals(text)) {
					selectionIndex = i;
					break;
				}
			}
			if (selectionIndex != -1) {
				currentdriver = definitions[selectionIndex];
				btnWizardActionPerformed();
			} else {
				currentdriver = new JDBCDriverDefinition("", text, textJDBCUrl.getText());
				pchangesuport.firePropertyChange("dirty", false, true);
			}

		}
	};

	protected void createUserPass(final Composite composite) {
		Label lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		lbl.setText(Messages.JDBCDataAdapterComposite_username);

		textUsername = new Text(composite, SWT.BORDER);
		textUsername.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		lbl.setText(Messages.JDBCDataAdapterComposite_password);

		textPassword = new WSecretText(composite, SWT.BORDER | SWT.PASSWORD);
		textPassword.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// btnSavePassword = new Button(this, SWT.CHECK);
		// btnSavePassword.setText("Save Password");
		// new Label(this, SWT.NONE);

		new Label(composite, SWT.NONE);
		Composite c = new Composite(composite, SWT.NONE);
		c.setLayout(new GridLayout(2, false));

		lbl = new Label(c, SWT.NONE | SWT.BOLD);
		lbl.setText(Messages.JDBCDataAdapterComposite_attentionlable);
		UIUtil.setBold(lbl);

		lbl = new Label(c, SWT.NONE);
		lbl.setText(Messages.JDBCDataAdapterComposite_attention);
	}

	protected void createURLAssistant(final Composite composite) {
		// new Label(composite, SWT.NONE);
		//
		// Section expcmp = new Section(composite,
		// ExpandableComposite.TREE_NODE);
		// expcmp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// expcmp.setTitleBarForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		// UIUtils.setBold(expcmp);
		// expcmp.setText(Messages.JDBCDataAdapterComposite_jdbcurlassistant);
		//
		// final Composite cmp = new Composite(expcmp, SWT.NONE);
		// cmp.setLayout(new GridLayout(2, false));
		//
		// expcmp.setClient(cmp);
		// expcmp.addExpansionListener(new ExpansionAdapter() {
		// public void expansionStateChanged(ExpansionEvent e) {
		// JDBCDataAdapterComposite.this.getParent().layout(true);
		// }
		// });
		//
		// Label lbl = new Label(cmp, SWT.NONE);
		// lbl.setText(Messages.JDBCDataAdapterComposite_notedbname);
		// GridData gd = new GridData();
		// gd.horizontalSpan = 2;
		// lbl.setLayoutData(gd);
		//
		// Label lblServerAddress = new Label(cmp, SWT.NONE);
		// lblServerAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
		// false, false, 1, 1));
		// lblServerAddress
		// .setText(Messages.JDBCDataAdapterComposite_serveraddress);
		//
		// textServerAddress = new Text(cmp, SWT.BORDER);
		// textServerAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
		// true, false, 1, 1));
		//
		// Label lblDatabase = new Label(cmp, SWT.NONE);
		// lblDatabase.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
		// false, 1, 1));
		// lblDatabase.setText(Messages.JDBCDataAdapterComposite_database);
		//
		// textDatabase = new Text(cmp, SWT.BORDER);
		// textDatabase.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
		// false, 1, 1));
		//
		// textServerAddress.addModifyListener(new ModifyListener() {
		//
		// public void modifyText(ModifyEvent e) {
		// btnWizardActionPerformed();
		// }
		// });
		// textDatabase.addModifyListener(new ModifyListener() {
		//
		// public void modifyText(ModifyEvent e) {
		// btnWizardActionPerformed();
		// }
		// });
	}

	/**
	 * @param e
	 */
	protected void btnWizardActionPerformed() {
		if (currentdriver != null) {
			// && textServerAddress != null && textDatabase != null) {
			textJDBCUrl.setText(currentdriver.getUrl("localhost", "database"));
			// textServerAddress.getText(), textDatabase.getText()));
			getDriverProperties();
		}
	}
	// FIXME - Uncomment and improved this debug method when the JIRA #JS-60045 is fixed.
	// SIMBA Impala driver is causing an NPE when trying to get the DriverPropertyInfo items
	protected void getDriverProperties() {
//		JdbcDataAdapter da = (JdbcDataAdapter) getDataAdapter().getDataAdapter();
//		Job job = new Job("Testing driver") {
//			protected IStatus run(IProgressMonitor monitor) {
//				JasperReportsConfiguration jConf = (JasperReportsConfiguration) jrContext;
//
//				JdbcDataAdapterService ds = (JdbcDataAdapterService) DataAdapterServiceUtil
//						.getInstance(new ParameterContributorContext(jConf, null, jConf.getJRParameters()))
//						.getService(da);
//
//				ClassLoader oldThreadClassLoader = Thread.currentThread().getContextClassLoader();
//
//				try {
//					Method m = AbstractClasspathAwareDataAdapterService.class.getDeclaredMethod("getClassLoader",
//							ClassLoader.class);
//					if (m != null) {
//						m.setAccessible(true);
//						Thread.currentThread().setContextClassLoader((ClassLoader) m.invoke(ds, oldThreadClassLoader));
//
//						Class<?> clazz = JRClassLoader.loadClassForRealName(da.getDriver());
//						Driver driver = (Driver) clazz.getDeclaredConstructor().newInstance();
//						if (driver != null) {
//							DriverPropertyInfo[] dpis = driver.getPropertyInfo(da.getUrl(), new Properties());
//							if (dpis != null) {
//								for (DriverPropertyInfo dpi : dpis) {
//									System.out.println(dpi.name + " " + dpi.value + " " + dpi.description);
//								}
//							}
//						}
//					}
//				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
//						| InvocationTargetException | ClassNotFoundException | SQLException
//						| InstantiationException e) {
//
//					e.printStackTrace();
//				} finally {
//					Thread.currentThread().setContextClassLoader(oldThreadClassLoader);
//				}
//
//				return Status.OK_STATUS;
//			}
//		};
//		job.setPriority(Job.SHORT);
//		job.schedule(); // start as soon as possible
	}

	/**
	 * Set the DataAdapter to edit. The UI will be updated with the content of this
	 * adapter
	 * 
	 * @param dataAdapter
	 */
	public void setDataAdapter(JDBCDataAdapterDescriptor editingDataAdapter) {
		super.setDataAdapter(editingDataAdapter);

		JdbcDataAdapter jdbcDataAdapter = (JdbcDataAdapter) dataAdapterDesc.getDataAdapter();
		if (jdbcDataAdapter.getDriver() == null)
			btnWizardActionPerformed();

		if (!textPassword.isWidgetConfigured())
			textPassword.loadSecret(DataAdaptersSecretsProvider.SECRET_NODE_ID, textPassword.getText());

		getDriverProperties();
	}

	@Override
	protected void bindWidgets(DataAdapter dataAdapter) {
		JdbcDataAdapter jdbcDataAdapter = (JdbcDataAdapter) dataAdapter;

		String driverName = Misc.nvl(jdbcDataAdapter.getDriver(), "org.hsqldb.jdbcDriver"); //$NON-NLS-1$
		comboJDBCDriver.removeModifyListener(driverModifyListener);
		comboJDBCDriver.setText(driverName);
		comboJDBCDriver.addModifyListener(driverModifyListener);
		currentdriver = null;
		for (JDBCDriverDefinition d : definitions) {
			if (d.getDriverName().equals(driverName)) {
				currentdriver = d;
				break;
			}
		}
		if (currentdriver == null) {
			currentdriver = new JDBCDriverDefinition("", comboJDBCDriver.getText(),
					((JdbcDataAdapter) dataAdapter).getUrl());
		}

		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(textUsername),
				PojoProperties.value("username").observe(dataAdapter)); //$NON-NLS-1$
		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(textPassword),
				PojoProperties.value("password").observe(dataAdapter)); //$NON-NLS-1$
		bindURLAssistant(dataAdapter);
		bindingContext.bindValue(
				WidgetProperties.text(SWT.Modify).observe(textJDBCUrl),
				PojoProperties.value("url").observe(dataAdapter)); //$NON-NLS-1$

		Proxy p = new Proxy((JdbcDataAdapterImpl) dataAdapter);
		bindingContext.bindValue(
				WidgetProperties.singleSelectionIndex().observe(bAc),
				PojoProperties.value("autoCommit").observe(p)); //$NON-NLS-1$
		bindingContext.bindValue(
				WidgetProperties.singleSelectionIndex().observe(bRO),
				PojoProperties.value("readOnly").observe(p)); //$NON-NLS-1$
		bindingContext.bindValue(
				WidgetProperties.singleSelectionIndex().observe(bTI),
				PojoProperties.value("transactionIsolation").observe(p)); //$NON-NLS-1$				

		cpath.setClasspaths(jdbcDataAdapter.getClasspath());
		cproperties.setProperties(jdbcDataAdapter.getProperties());
	}

	class Proxy {
		private JdbcDataAdapterImpl da;

		public Proxy(JdbcDataAdapterImpl da) {
			this.da = da;
		}

		public int getAutoCommit() {
			if (da.getAutoCommit() == null)
				return 0;
			return da.getAutoCommit() ? 1 : 2;
		}

		public void setAutoCommit(int indx) {
			switch (indx) {
			case 0:
				da.setAutoCommit(null);
				break;
			case 1:
				da.setAutoCommit(true);
				break;
			default:
				da.setAutoCommit(false);
			}
		}

		public int getReadOnly() {
			if (da.getReadOnly() == null)
				return 0;
			return da.getReadOnly() ? 1 : 2;
		}

		public void setReadOnly(int indx) {
			switch (indx) {
			case 0:
				da.setReadOnly(null);
				break;
			case 1:
				da.setReadOnly(true);
				break;
			default:
				da.setReadOnly(false);
			}
		}

		public void setTransactionIsolation(int indx) {
			da.setTransactionIsolation(JDBCDataAdapterComposite.getTransactionIsolation(indx));
		}

		public int getTransactionIsolation() {
			TransactionIsolation ti = da.getTransactionIsolation();
			if (ti != null) {
				if (ti.equals(TransactionIsolation.NONE))
					return 1;
				if (ti.equals(TransactionIsolation.READ_UNCOMMITTED))
					return 2;
				if (ti.equals(TransactionIsolation.READ_COMMITTED))
					return 3;
				if (ti.equals(TransactionIsolation.REPEATABLE_READ))
					return 4;
				if (ti.equals(TransactionIsolation.SERIALIZABLE))
					return 5;
			}
			return 0;
		}
	}

	public static TransactionIsolation getTransactionIsolation(int indx) {
		switch (indx) {
		case 1:
			return TransactionIsolation.NONE;
		case 2:
			return TransactionIsolation.READ_UNCOMMITTED;
		case 3:
			return TransactionIsolation.READ_COMMITTED;
		case 4:
			return TransactionIsolation.REPEATABLE_READ;
		case 5:
			return TransactionIsolation.SERIALIZABLE;
		}
		return null;
	}

	protected void bindURLAssistant(DataAdapter dataAdapter) {
		if (textServerAddress != null) {
			bindingContext.bindValue(
					WidgetProperties.text(SWT.Modify).observe(textServerAddress),
					PojoProperties.value("serverAddress").observe(dataAdapter)); //$NON-NLS-1$
		}
		if (textDatabase != null) {
			bindingContext.bindValue(
					WidgetProperties.text(SWT.Modify).observe(textDatabase),
					PojoProperties.value("database").observe(dataAdapter)); //$NON-NLS-1$
		}
	}

	public DataAdapterDescriptor getDataAdapter() {
		if (dataAdapterDesc == null) {
			dataAdapterDesc = new JDBCDataAdapterDescriptor();
		}

		JdbcDataAdapter jdbcDataAdapter = (JdbcDataAdapter) dataAdapterDesc.getDataAdapter();

		JDBCDriverDefinition currentdriver = null;
		String selectedDriverText = comboJDBCDriver.getText();
		for (JDBCDriverDefinition d : definitions) {
			if (d.toString().equals(selectedDriverText)) {
				currentdriver = d;
				break;
			}
		}
		if (currentdriver == null) {
			jdbcDataAdapter.setDriver(selectedDriverText);
		} else {
			jdbcDataAdapter.setDriver(currentdriver.getDriverName());
		}

		jdbcDataAdapter.setUsername(textUsername.getText());
		jdbcDataAdapter.setPassword(textPassword.getText());
		jdbcDataAdapter.setUrl(textJDBCUrl.getText());
		getDataAdapterURLAssistant(jdbcDataAdapter);
		jdbcDataAdapter.setSavePassword(true);// btnSavePassword.getSelection());

		jdbcDataAdapter.setClasspath(cpath.getClasspaths());
		jdbcDataAdapter.setProperties(cproperties.getProperties());

		return dataAdapterDesc;
	}

	protected void getDataAdapterURLAssistant(JdbcDataAdapter jdbcDataAdapter) {
		if (textDatabase != null)
			jdbcDataAdapter.setDatabase(textDatabase.getText());
		else
			jdbcDataAdapter.setDatabase("");
		if (textServerAddress != null)
			jdbcDataAdapter.setServerAddress(textServerAddress.getText());
		else
			jdbcDataAdapter.setServerAddress("");
	}

	protected String contextId;
	private Combo bAc;
	private Combo bRO;
	private Combo bTI;

	@Override
	public String getHelpContextId() {
		return PREFIX.concat(contextId);
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	@Override
	public void performAdditionalUpdates() {
		if (JaspersoftStudioPlugin.shouldUseSecureStorage()) {
			textPassword.persistSecret();
			// update the "password" replacing it with the UUID key saved in secure
			// preferences
			JdbcDataAdapter jdbcDataAdapter = (JdbcDataAdapter) dataAdapterDesc.getDataAdapter();
			jdbcDataAdapter.setPassword(textPassword.getUUIDKey());
		}
	}
}
