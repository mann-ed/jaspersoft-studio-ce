/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.model;

import com.jaspersoft.studio.editor.gef.rulers.ReportRulerGuide;

public interface IGuidebleElement {

	public abstract ReportRulerGuide getVerticalGuide();

	public abstract void setVerticalGuide(ReportRulerGuide verticalGuide);

	public abstract ReportRulerGuide getHorizontalGuide();

	public abstract void setHorizontalGuide(ReportRulerGuide horizontalGuide);

}
