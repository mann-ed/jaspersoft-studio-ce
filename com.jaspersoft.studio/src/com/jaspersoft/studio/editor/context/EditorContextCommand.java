/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.context;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;

import com.jaspersoft.studio.JaspersoftStudioPlugin;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.Misc;

/**
 * Command to switch context type for the resource selected.
 */
public class EditorContextCommand extends AbstractHandler {

	public EditorContextCommand() {
		UIUtils.getDisplay().syncExec(this::listenSelection);
	}

	private void listenSelection() {
		IWorkbenchWindow w = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ICommandService service = w.getService(ICommandService.class);
		Command cmd = service.getCommand("com.jaspersoft.studio.editor.context.type");
		ISelectionService ss = w.getSelectionService();
		ss.addSelectionListener((part, selection) -> {
			if (selection instanceof IStructuredSelection && ((IStructuredSelection) selection).size() == 1) {
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				if (obj instanceof IJavaProject) {
					obj = ((IJavaProject) obj).getProject();
				}
				if (obj instanceof IResource && isSelectable((IResource) obj)) {
					setBaseEnabled(true);
					cmd.setEnabled(true);
					try {
						String p = ((IResource) obj).getPersistentProperty(EditorContextUtil.EC_KEY);
						State state = cmd.getState(RadioState.STATE_ID);
						if (state != null) {
							if (p == null) {
								state.setValue("reset");
							}
							else {
								state.setValue(Misc.nvl(p, AEditorContext.NAME));
							}
						}
					} catch (CoreException e) {
						JaspersoftStudioPlugin.getInstance().logError(e);
					}
					return;
				}
			}
			setBaseEnabled(false);
			cmd.setEnabled(false);
		});
	}

	/*
	 * Verify if the resource can be selected.
	 */
	private boolean isSelectable(IResource obj) {
		// avoid selection of JR-INF folders (JRIO configuration)
		return !((obj instanceof IFolder && obj.getName().equals("JR-INF"))
				|| (obj.getParent() != null && !isSelectable(obj.getParent())));
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection sel = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().getSelection();
		if (sel instanceof IStructuredSelection) {
			Object obj = ((IStructuredSelection) sel).getFirstElement();
			if (obj instanceof IJavaProject) {
				obj = ((IJavaProject) obj).getProject();
			}
			if (obj instanceof IResource) {
				try {
					if (HandlerUtil.matchesRadioState(event)) {
						return null;
					}
					String state = event.getParameter(RadioState.PARAMETER_ID);
					((IResource) obj).setPersistentProperty(EditorContextUtil.EC_KEY, getPersistentState(state));
					HandlerUtil.updateRadioState(event.getCommand(), state);
					EditorContextUtil.fireContextChanged(((IResource) obj), state);
				} catch (CoreException e) {
					UIUtils.showError(e);
				}
			}
		}
		return null;
	}
	
	/*
	 * Returns the persisted state for the context type.
	 */
	private String getPersistentState(String state) {
		if ("reset".equals(state)) {
			// in case of "reset" we will end-up removing the property
			return null;
		}
		return state;
	}

}
