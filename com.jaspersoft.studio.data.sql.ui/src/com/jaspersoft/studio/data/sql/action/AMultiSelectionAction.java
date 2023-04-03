/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.data.sql.action;

import org.eclipse.jface.viewers.TreeViewer;

import com.jaspersoft.studio.model.ANode;

public abstract class AMultiSelectionAction extends AAction {

	public AMultiSelectionAction(String text, TreeViewer treeViewer) {
		super(text, treeViewer);
	}

	@Override
	public boolean calculateEnabled(Object[] selection) {
		super.calculateEnabled(selection);
		if (selection == null || selection.length==0) {
			return false;
		}
		else {
			for (Object s : selection) {
				s = convertObject(s);
				if (s == null)
					return false;
				if (!isGoodNode((ANode) s))
					return false;
			}
		}
		return true;
	}

	protected ANode convertObject(Object obj) {
		if (obj instanceof ANode)
			return (ANode) obj;
		return null;
	}

	protected abstract boolean isGoodNode(ANode element);
}
