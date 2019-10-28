/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.editor.gef.parts;

import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.jface.util.Util;

/**
 * fix for community issue 12061, on linux due to gtk the editor is not refreshed correctly during
 * many operations like selection, resizing and dragging. The only way to fix this is manually
 * triggering a redraw of the editor to paint the feedbacks, selections and so on. This superclass
 * take care of some of the problems related to moving and resizing of elements. However this fix
 * needed other redraw in other places, but in the end the problem was always the same.
 * 
 * @author Orlandin Marco
 */
public abstract class RedrawingEditPolicy extends ResizableEditPolicy {
	
	@Override
	protected void showChangeBoundsFeedback(ChangeBoundsRequest request) {
		if (Util.isLinux()) {
			getHost().getViewer().getControl().redraw();
		}
	}

}
