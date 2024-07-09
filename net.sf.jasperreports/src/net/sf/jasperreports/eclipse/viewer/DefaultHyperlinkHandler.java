/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.viewer;

import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.type.HyperlinkTypeEnum;
import net.sf.jasperreports.view.JRHyperlinkListener;

import org.eclipse.swt.custom.BusyIndicator;

public class DefaultHyperlinkHandler implements JRHyperlinkListener {

	public void gotoHyperlink(final JRPrintHyperlink link) throws JRException {
		if (link == null)
			return;
		BusyIndicator.showWhile(UIUtils.getDisplay(), new Runnable() {
			public void run() {
				HyperlinkTypeEnum linkType = link.getHyperlinkTypeValue();
				if (linkType.equals(HyperlinkTypeEnum.REFERENCE)) {
					openLink(link.getHyperlinkReference());
				} else if (linkType.equals(HyperlinkTypeEnum.REMOTE_ANCHOR)) {
					String href = link.getHyperlinkReference();
					if (href != null) {
						if (link.getHyperlinkAnchor() != null)
							href += "#" + link.getHyperlinkAnchor(); //$NON-NLS-1$
						openLink(href);
					}
				} else if (linkType.equals(HyperlinkTypeEnum.REMOTE_PAGE)) {
					String href = link.getHyperlinkReference();
					if (href != null) {
						if (link.getHyperlinkPage() != null)
							href += "#JR_PAGE_ANCHOR_0_" + link.getHyperlinkPage(); //$NON-NLS-1$
						openLink(href);
					}
				}
			}
		});
	}

	private void openLink(String href) {
		if (href != null && !href.isEmpty())
			BrowserUtils.openLink(href);
	}
}
