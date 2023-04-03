/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.editor.AbstractJRXMLEditor;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.utils.jasper.JasperReportsConfiguration;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.eclipse.util.KeyValue;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.eclipse.viewer.BrowserUtils;

public class EditorContextUtil {

	public static QualifiedName EC_KEY = 
			new QualifiedName(JaspersoftStudioPlugin.getUniqueIdentifier(),	AEditorContext.EDITOR_CONTEXT);
	private static final boolean EDGE_PROBLEM_FOUND = UIUtils.isWindows() && !BrowserUtils.isEdgeWebViewEnabled();
	public static boolean ENABLEMENU = false;
	private static List<ContextSwitchAction> actions = new ArrayList<>();
	private static Map<AbstractJRXMLEditor, Label> labels = new HashMap<>();

	public static void fireContextChanged(IResource r, String state) {
		if (ENABLEMENU) {
			for (ContextSwitchAction csa : actions) {
				csa.refresh(r);
			}
		}
		else {
			for (AbstractJRXMLEditor editor : labels.keySet()) {
				JasperReportsConfiguration jConf = editor.getJrContext();
				AEditorContext old = jConf.getEditorContext();
				IFile f = (IFile) jConf.get(FileUtils.KEY_FILE);
				AEditorContext ec = getEditorContext(f, jConf);
				if (old.getClass().equals(ec.getClass()))
					continue;
				editor.changeContext(ec.getId(), !r.equals(f));

				Label lbl = labels.get(editor);

				if (lbl.isDisposed())
					continue;
				lbl.setText(editor.getJrContext().getEditorContext().getName());
				lbl.getParent().pack();
				lbl.getParent().getParent().update();
				lbl.getParent().getParent().layout(true);
			}
		}
		Collection<IResourceContextSwitchUtil> allUtils = JaspersoftStudioPlugin.getExtensionManager().getAllEditorContextUtils();
		for(IResourceContextSwitchUtil u : allUtils) {
			u.essentialOperations(r, state);
		}
	}

	public static AEditorContext getEditorContext(IFile f, JasperReportsConfiguration jConf) {
		String ctx = null;
		try {
			if (f != null) {
				if (f.exists() && ENABLEMENU)
					ctx = f.getPersistentProperty(EC_KEY);
				IContainer c = f.getParent();
				while (c != null && Misc.isNullOrEmpty(ctx) && c.exists()) {
					ctx = c.getPersistentProperty(EC_KEY);
					c = c.getParent();
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		AEditorContext ec = JaspersoftStudioPlugin.getExtensionManager().getEditorContext(ctx, f);
		ec.init(f, jConf);
		return ec;
	}

	private static String getParentContext(IResource f) {
		String ctx = null;
		if (f != null) {
			try {
				ctx = f.getPersistentProperty(EC_KEY);
				IContainer c = f.getParent();
				while (c != null && Misc.isNullOrEmpty(ctx)) {
					ctx = c.getPersistentProperty(EC_KEY);
					c = c.getParent();
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return Misc.nvl(ctx, AEditorContext.NAME);
	}

	public static Control createSwitch(Composite cmp, AbstractJRXMLEditor editor) {
		if (ENABLEMENU) {
			ToolBar toolBar = new ToolBar(cmp, SWT.FLAT);
			toolBar.setBackgroundMode(SWT.INHERIT_FORCE);
			ToolBarManager tbManager = new ToolBarManager(toolBar);

			tbManager.add(new ContextSwitchAction(editor, toolBar, tbManager));
			tbManager.update(true);
			toolBar.pack();
			return toolBar;
		}
		
		int numColumns = 1;
		if(EDGE_PROBLEM_FOUND) {
			numColumns = 3;
		}
		
		Composite container = new Composite(cmp,SWT.NONE);
		GridLayout containerGL = new GridLayout(numColumns,false);
		container.setLayout(containerGL);
		container.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
		
		if(EDGE_PROBLEM_FOUND) {
			Label warningLbl = new Label(container, SWT.NONE);
			GridData warningLblGD = new GridData(SWT.FILL,SWT.CENTER,true,false);
			warningLbl.setText(Messages.EditorContextUtil_EdgeWarningMsg);
			warningLbl.setForeground(UIUtils.getDisplay().getSystemColor(SWT.COLOR_RED));
			warningLbl.setLayoutData(warningLblGD);
			
			Label separator = new Label(container,SWT.SEPARATOR | SWT.VERTICAL);
			GridData sepGD = new GridData(SWT.FILL,SWT.FILL,true,false);
			sepGD.heightHint = 20;
			separator.setLayoutData(sepGD);
		}

		Label contextNameLbl = new Label(container, SWT.NONE);
		if (editor.getJrContext() != null) {
			GridData contextNameLblGD = new GridData(SWT.FILL,SWT.CENTER,true,false);
			contextNameLbl.setLayoutData(contextNameLblGD);
			contextNameLbl.setText(editor.getJrContext().getEditorContext().getName());
		}
		labels.put(editor, contextNameLbl);
		contextNameLbl.addDisposeListener(e -> labels.remove(editor));
		return container;
	}

	public static class ContextSwitchAction extends Action implements IMenuCreator {
		private AbstractJRXMLEditor editor;
		private ToolBar toolBar;
		private ToolBarManager tbManager;

		public ContextSwitchAction(AbstractJRXMLEditor editor, ToolBar toolBar, ToolBarManager tbManager) {
			super();
			this.toolBar = toolBar;
			this.tbManager = tbManager;
			setText(Messages.EditorContextUtil_0);
			UIUtils.getDisplay().asyncExec(() -> {
				if (editor.getJrContext() != null)
					setToolBarText(editor.getJrContext().getEditorContext().getName());
				else
					setToolBarText(AEditorContext.NAME);
			});
			setMenuCreator(this);
			this.editor = editor;
			actions.add(this);
			toolBar.addDisposeListener(e -> actions.remove(ContextSwitchAction.this));
		}

		private void setToolBarText(String text) {
			if (toolBar.isDisposed())
				return;
			setText(Misc.nvl(text, AEditorContext.NAME));
			if (editor.getJrContext() != null) {
				IFile f = (IFile) editor.getJrContext().get(FileUtils.KEY_FILE);
				try {
					if (f == null || f.getPersistentProperty(EC_KEY) == null)
						setToolTipText(Messages.EditorContextUtil_1);
					else
						setToolTipText(Messages.EditorContextUtil_2);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
			tbManager.update(true);
			toolBar.pack();
			toolBar.getParent().update();
			toolBar.getParent().layout(true);
		}

		@Override
		public void runWithEvent(Event event) {
			Point point = ((ToolItem) event.widget).getParent().toDisplay(new Point(event.x, event.y));
			menu = getMenu(((ToolItem) event.widget).getParent());
			menu.setLocation(point.x, point.y);
			menu.setVisible(true);
		}

		private Menu menu;

		@Override
		public void dispose() {
			if (menu != null)
				menu.dispose();
		}

		@Override
		public Menu getMenu(Control parent) {
			if (menu == null) {
				menu = new Menu(parent);

				IFile f = (IFile) editor.getJrContext().get(FileUtils.KEY_FILE);
				String ct = null;
				try {
					ct = f.getPersistentProperty(EC_KEY);
				} catch (CoreException e1) {
					e1.printStackTrace();
				}

				List<KeyValue<String, String>> ecs = JaspersoftStudioPlugin.getExtensionManager().getEditorContexts();
				for (KeyValue<String, String> item : ecs) {
					MenuItem mi = new MenuItem(menu, SWT.CHECK);
					mi.setText(item.value);
					mi.setData("name", item.key); //$NON-NLS-1$
					mi.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							String n = (String) mi.getData("name"); //$NON-NLS-1$
							IFile f = (IFile) editor.getJrContext().get(FileUtils.KEY_FILE);
							String ct = null;
							try {
								ct = f.getPersistentProperty(EC_KEY);
							} catch (CoreException e1) {
								e1.printStackTrace();
							}
							if (ct == null || !ct.equals(n)) {
								editor.changeContext(n, false);

								setToolBarText(mi.getText());
							}
							for (MenuItem m : menu.getItems())
								if (m.getData("name") != null) //$NON-NLS-1$
									m.setSelection(false);
							mi.setSelection(true);
						}
					});
					if (ct != null && ct.equals(item.key))
						mi.setSelection(true);
				}
				if (ecs.size() > 1) {
					new MenuItem(menu, SWT.SEPARATOR);

					MenuItem mi = new MenuItem(menu, SWT.PUSH);
					mi.setText(Messages.EditorContextUtil_6);
					mi.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							for (MenuItem m : menu.getItems())
								if (m.getData("name") != null) //$NON-NLS-1$
									m.setSelection(false);

							IFile f = (IFile) editor.getJrContext().get(FileUtils.KEY_FILE);
							editor.changeContext(getParentContext(f.getParent()), true);
							setToolBarText(editor.getJrContext().getEditorContext().getName());
						}
					});
				}

			}
			return menu;
		}

		@Override
		public Menu getMenu(Menu parent) {
			return null;
		}

		public void refresh(IResource resource) {
			JasperReportsConfiguration jConf = editor.getJrContext();
			AEditorContext old = jConf.getEditorContext();
			IFile f = (IFile) jConf.get(FileUtils.KEY_FILE);
			AEditorContext ec = getEditorContext(f, jConf);
			if (old.getClass().equals(ec.getClass())) {
				return;
			}
			editor.changeContext(ec.getId(), !resource.equals(f));
			setToolBarText(jConf.getEditorContext().getName());
		}
	}

}
