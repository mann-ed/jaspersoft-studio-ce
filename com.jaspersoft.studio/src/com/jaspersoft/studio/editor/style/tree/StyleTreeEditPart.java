/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.editor.style.tree;

import org.eclipse.gef.EditPolicy;

import com.jaspersoft.studio.editor.outline.editpolicy.ElementTreeEditPolicy;
import com.jaspersoft.studio.editor.outline.part.TreeEditPart;
import com.jaspersoft.studio.editor.style.editpolicy.StyleElementEditPolicy;

/*
 * The Class ATreeEditPart.
 */
public class StyleTreeEditPart extends TreeEditPart {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new StyleElementEditPolicy());
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ElementTreeEditPolicy());
	}

}
