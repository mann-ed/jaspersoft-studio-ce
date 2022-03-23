/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.gef.decorator.pdf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.RetargetAction;

import com.jaspersoft.studio.editor.action.pdf.Heading;
import com.jaspersoft.studio.editor.action.pdf.PdfActionHeading;
import com.jaspersoft.studio.editor.action.pdf.PdfActionTable;
import com.jaspersoft.studio.editor.action.pdf.PdfActionTableDetail;
import com.jaspersoft.studio.editor.action.pdf.PdfActionTableHeader;
import com.jaspersoft.studio.editor.action.pdf.PdfActionTableRow;
import com.jaspersoft.studio.editor.action.pdf.PdfFieldAction;
import com.jaspersoft.studio.editor.action.pdf.Position;
import com.jaspersoft.studio.editor.gef.decorator.chainable.ChainableDecorator;
import com.jaspersoft.studio.editor.gef.decorator.chainable.ChainableElementDecorator;
import com.jaspersoft.studio.editor.gef.figures.ComponentFigure;
import com.jaspersoft.studio.editor.gef.parts.FigureEditPart;
import com.jaspersoft.studio.editor.report.AbstractVisualEditor;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.model.MGraphicElement;

public class PDF508ElementDecorator extends ChainableElementDecorator {

	private PDFDecorator decorator = new PDFDecorator();

	private List<String> actionIDs;

	public static final String PDF_MENU_ID = "com.jaspersoft.studio.editor.gef.decorator.pdf.menu"; //$NON-NLS-1$

	@Override
	public void setupFigure(ComponentFigure fig, FigureEditPart editPart) {
		super.setupFigure(fig, editPart);
		ChainableDecorator textDecorator = getDecorator(fig);
		textDecorator.removeDecorator(decorator);
		if (editPart.getjConfig().getPropertyBooleanDef(ShowPDFTagsAction.ID, false)) {
			textDecorator.addDecorator(decorator);
		}
	}

	@Override
	public RetargetAction[] buildMenuActions() {
		return new RetargetAction[] {
				new RetargetAction(ShowPDFTagsAction.ID, Messages.ShowPDFTagsAction_title, IAction.AS_CHECK_BOX) };
	}

	private void registerHeading(ActionRegistry registry, IWorkbenchPart part, List<String> selectionActions) {
		IAction action = new PdfActionHeading(part, Heading.H1);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionHeading(part, Heading.H2);
		registry.registerAction(action);
		selectionActions.add(action.getId());
		
		action = new PdfActionHeading(part, Heading.H3);
		registry.registerAction(action);
		selectionActions.add(action.getId());
		
		action = new PdfActionHeading(part, Heading.H4);
		registry.registerAction(action);
		selectionActions.add(action.getId());
		
		action = new PdfActionHeading(part, Heading.H5);
		registry.registerAction(action);
		selectionActions.add(action.getId());
		
		action = new PdfActionHeading(part, Heading.H6);
		registry.registerAction(action);
		selectionActions.add(action.getId());
	}

	private void registerTable(ActionRegistry registry, IWorkbenchPart part, List<String> selectionActions) {
		IAction action = new PdfActionTable(part, Position.Full);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTable(part, Position.Start);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTable(part, Position.End);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTable(part, Position.None);
		registry.registerAction(action);
		selectionActions.add(action.getId());
	}

	private void registerTableRow(ActionRegistry registry, IWorkbenchPart part, List<String> selectionActions) {
		IAction action = new PdfActionTableRow(part, Position.Full);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTableRow(part, Position.Start);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTableRow(part, Position.End);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTableRow(part, Position.None);
		registry.registerAction(action);
		selectionActions.add(action.getId());
	}

	private void registerTableDetail(ActionRegistry registry, IWorkbenchPart part, List<String> selectionActions) {
		IAction action = new PdfActionTableDetail(part, Position.Full);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTableDetail(part, Position.Start);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTableDetail(part, Position.End);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTableDetail(part, Position.None);
		registry.registerAction(action);
		selectionActions.add(action.getId());
	}

	private void registerTableHeader(ActionRegistry registry, IWorkbenchPart part, List<String> selectionActions) {
		IAction action = new PdfActionTableHeader(part, Position.Full);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTableHeader(part, Position.Start);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTableHeader(part, Position.End);
		registry.registerAction(action);
		selectionActions.add(action.getId());

		action = new PdfActionTableHeader(part, Position.None);
		registry.registerAction(action);
		selectionActions.add(action.getId());
	}

	public void registerFieldActions(ActionRegistry registry, IWorkbenchPart part, List<String> selectionActions) {
		IAction action = new PdfFieldAction(part);
		registry.registerAction(action);
		selectionActions.add(action.getId());
	}

	public void registerActions(ActionRegistry registry, List<String> selectionActions, IWorkbenchPart part) {
		registerHeading(registry, part, selectionActions);
		registerTable(registry, part, selectionActions);
		registerTableRow(registry, part, selectionActions);
		registerTableHeader(registry, part, selectionActions);
		registerTableDetail(registry, part, selectionActions);
		registerFieldActions(registry, part, selectionActions);
	}

	@Override
	public void registerActions(ActionRegistry registry, List<String> selectionActions, GraphicalViewer gviewer,
			AbstractVisualEditor part) {
		gviewer.setProperty(ShowPDFTagsAction.ID, true);
		IAction action = new ShowPDFTagsAction(gviewer, part.getJrContext());
		registry.registerAction(action);
		registerActions(registry, selectionActions, part);
	}

	public void fillContextMenu(ActionRegistry registry, IMenuManager menu, IStructuredSelection sel) {
		// Check if the menu item of the PDF tags is already present, if it is
		// then add this item to
		// it otherwise create a new one
		MenuManager submenu = null;
		for (IContributionItem item : menu.getItems()) {
			if (item.getId() != null && item.getId().equals(PDF508ElementDecorator.PDF_MENU_ID)) {
				submenu = (MenuManager) item;
				break;
			}
		}
		if (submenu == null) {
			submenu = new MenuManager(Messages.PDF508ElementDecorator_Menu_PDF508Tags, null,
					PDF508ElementDecorator.PDF_MENU_ID);
		}

		MenuManager submenuHeading = new MenuManager(Messages.PDF508ElementDecorator_Menu_Heading);
		MenuManager submenuTable = new MenuManager(Messages.PDF508ElementDecorator_Menu_Table);
		MenuManager submenuTableRow = new MenuManager(Messages.PDF508ElementDecorator_Menu_TableRow);
		MenuManager submenuTableHeader = new MenuManager(Messages.PDF508ElementDecorator_Menu_TableHeader);
		MenuManager submenuTableDetails = new MenuManager(Messages.PDF508ElementDecorator_Menu_TableDetails);

		submenu.add(submenuHeading);
		submenu.add(submenuTable);
		submenu.add(submenuTableRow);
		submenu.add(submenuTableHeader);
		submenu.add(submenuTableDetails);

		IAction action;
		// Adding actions for Heading
		action = registry.getAction(PdfActionHeading.ID_TABLE_HEADING_H1);
		submenuHeading.add(action); 
		action = registry.getAction(PdfActionHeading.ID_TABLE_HEADING_H2);
		submenuHeading.add(action); 
		action = registry.getAction(PdfActionHeading.ID_TABLE_HEADING_H3);
		submenuHeading.add(action); 
		action = registry.getAction(PdfActionHeading.ID_TABLE_HEADING_H4);
		submenuHeading.add(action); 
		action = registry.getAction(PdfActionHeading.ID_TABLE_HEADING_H5);
		submenuHeading.add(action); 
		action = registry.getAction(PdfActionHeading.ID_TABLE_HEADING_H6);
		submenuHeading.add(action); 

		// Adding actions for Table
		action = registry.getAction(PdfActionTable.ID_Table_Full);
		submenuTable.add(action);
		action = registry.getAction(PdfActionTable.ID_Table_Start);
		submenuTable.add(action);
		action = registry.getAction(PdfActionTable.ID_Table_End);
		submenuTable.add(action);
		action = registry.getAction(PdfActionTable.ID_Table_None);
		submenuTable.add(action);

		// Adding actions for TableRow
		action = registry.getAction(PdfActionTableRow.ID_TableRow_Full);
		submenuTableRow.add(action);
		action = registry.getAction(PdfActionTableRow.ID_TableRow_Start);
		submenuTableRow.add(action);
		action = registry.getAction(PdfActionTableRow.ID_TableRow_End);
		submenuTableRow.add(action);
		action = registry.getAction(PdfActionTableRow.ID_TableRow_None);
		submenuTableRow.add(action);

		// Adding actions for TableHeader
		action = registry.getAction(PdfActionTableHeader.ID_TableHeader_Full);
		submenuTableHeader.add(action);
		action = registry.getAction(PdfActionTableHeader.ID_TableHeader_Start);
		submenuTableHeader.add(action);
		action = registry.getAction(PdfActionTableHeader.ID_TableHeader_End);
		submenuTableHeader.add(action);
		action = registry.getAction(PdfActionTableHeader.ID_TableHeader_None);
		submenuTableHeader.add(action);

		// Adding actions for TableDetail
		action = registry.getAction(PdfActionTableDetail.ID_TableDetail_Full);
		submenuTableDetails.add(action);
		action = registry.getAction(PdfActionTableDetail.ID_TableDetail_Start);
		submenuTableDetails.add(action);
		action = registry.getAction(PdfActionTableDetail.ID_TableDetail_End);
		submenuTableDetails.add(action);
		action = registry.getAction(PdfActionTableDetail.ID_TableDetail_None);
		submenuTableDetails.add(action);

		action = registry.getAction(PdfFieldAction.ID_PDF_FIELD_ACTION);
		submenu.add(action);

		menu.add(submenu);
	}

	@Override
	public void buildContextMenu(ActionRegistry registry, EditPartViewer viewer, IMenuManager menu) {
		IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();
		if (sel.getFirstElement() instanceof EditPart) {
			EditPart ep = (EditPart) sel.getFirstElement();
			if (!(ep.getModel() instanceof MGraphicElement))
				return;
		}
		fillContextMenu(registry, menu, sel);
	}

	@Override
	public void contribute2Menu(ActionRegistry registry, MenuManager menuManager) {
		menuManager.add(registry.getAction(ShowPDFTagsAction.ID));
	}

	@Override
	public List<String> getActionIDs() {
		if (actionIDs == null) {
			actionIDs = new ArrayList<>(1);
			actionIDs.add(ShowPDFTagsAction.ID);
		}
		return actionIDs;
	}
}
