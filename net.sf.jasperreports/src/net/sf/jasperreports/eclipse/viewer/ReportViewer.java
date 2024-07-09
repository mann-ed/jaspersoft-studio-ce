/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.viewer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.PrintPart;
import net.sf.jasperreports.view.JRHyperlinkListener;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ReportViewer implements IReportViewer {

	private JasperPrint document;
	private int pageIndex = -1;
	private int style;

	private ViewerCanvas viewerCanvas;
	private Composite viewerComposite;

	private List<JRHyperlinkListener> hyperlinkListeners;
	private JasperReportsContext jContext;

	public ReportViewer(JasperReportsContext jContext) {
		this(SWT.NONE, jContext);
	}

	public ReportViewer(int style, JasperReportsContext jContext) {
		this.style = style;
		this.jContext = jContext;
	}

	public void exportImage(String file, int width, int height) {
		ImageLoader loader = new ImageLoader();
		Image actualImage = viewerCanvas.getActualImage();
		int resizeWidth = width > 0 ? width : actualImage.getBounds().width;
		int resizeHeight = height > 0 ? height : actualImage.getBounds().height;
		loader.data = new ImageData[] { actualImage.getImageData().scaledTo(
				resizeWidth, resizeHeight) };
		loader.save(file, SWT.IMAGE_PNG);
	}

	public String getReportPath() {
		IFile reportFile = (IFile) jContext.getValue(FileUtils.KEY_FILE);
		if (reportFile != null) {
			String fileName = reportFile.getName();
			String path = reportFile.getLocation().toPortableString();
			path = path.substring(0, path.lastIndexOf(fileName));
			return path;
		}
		return null;
	}

	public String getReportName() {
		IFile reportFile = (IFile) jContext.getValue(FileUtils.KEY_FILE);
		if (reportFile != null) {
			String fileName = reportFile.getName();
			String extension = reportFile.getFileExtension();
			String path = reportFile.getLocation().toPortableString();
			path = path.substring(0, path.lastIndexOf(fileName));
			fileName = fileName.substring(0,
					fileName.lastIndexOf(extension) - 1);
			return fileName;
		}
		return null;
	}

	public void setReport(JasperPrint document) {
		setReport(document, pageIndex);
	}

	public void setReport(JasperPrint document, int page) {
		try {
			this.document = document;
			setupTabs();
			this.pageIndex = Math.min(Math.max(0, pageIndex),
					getPageCount() - 1);
			if (viewerCanvas != null)
				viewerCanvas.setZoomInternal(viewerCanvas.computeZoom());
			if (getPageCount() == 0)
				viewerCanvas.refresh();
			fireViewerModelChanged(page != pageIndex);
		} catch (OutOfMemoryError e) {
			this.document = null;
		}
	}

	private void setupTabs() {
		if (tFolder == null)
			return;
		for (CTabItem c : tFolder.getItems())
			c.dispose();
		if (document == null)
			return;
		if (document.hasParts()) {
			// System.out.println("TABURi: " + document.getParts().partCount());
			tFolder.setLayoutData(tFolderFD);
			startsAtZero = false;

			for (Iterator<Map.Entry<Integer, PrintPart>> it = document
					.getParts().partsIterator(); it.hasNext() && !startsAtZero;)
				startsAtZero = it.next().getKey() == 0;

			if (!startsAtZero)
				setupTab(document.getName(), null);

			for (Iterator<Map.Entry<Integer, PrintPart>> it = document
					.getParts().partsIterator(); it.hasNext();) {
				Entry<Integer, PrintPart> p = it.next();
				PrintPart printPart = p.getValue();
				setupTab(printPart.getName(), p);
			}
		} else {
			FormData fd = new FormData();
			fd.height = 0;
			fd.bottom = new FormAttachment(0, 0);
			// fd.left = new FormAttachment(0, 0);
			// fd.right = new FormAttachment(100, 0);
			tFolder.setLayoutData(fd);

		}
		viewerComposite.layout(true);
	}

	private static final int TABNAMELENGHT = 30;

	private void setupTab(String name, Object data) {
		CTabItem tab = new CTabItem(tFolder, SWT.NONE);
		tab.setData(data);
		tab.setToolTipText(name);
		name = name.replaceAll("(\\r|\\n)+", " ");
		if (name.length() > TABNAMELENGHT)
			name = name.substring(0, TABNAMELENGHT) + " ..."; //$NON-NLS-1$
		tab.setText(name);
	}

	public boolean hasReport() {
		return document != null;
	}

	public JasperPrint getReport() {
		return document;
	}

	public void setZoom(float zoom) {
		viewerCanvas.setZoom(zoom);
	}

	public boolean canChangeZoom() {
		return viewerCanvas.hasReport();
	}

	public int getZoomMode() {
		return viewerCanvas.getZoomMode();
	}

	public void setZoomMode(int zoomMode) {
		viewerCanvas.setZoomMode(zoomMode);
	}

	public float[] getZoomLevels() {
		return viewerCanvas.getZoomLevels();
	}

	public void zoomIn() {
		viewerCanvas.zoomIn();
	}

	public boolean canZoomIn() {
		return viewerCanvas.canZoomIn();
	}

	public float getZoom() {
		return viewerCanvas.getZoom();
	}

	public void zoomOut() {
		viewerCanvas.zoomOut();
	}

	public boolean canZoomOut() {
		return viewerCanvas.canZoomOut();
	}

	public int getPageCount() {
		return document == null ? 0 : document.getPages().size();
	}

	public int getPageIndex() {
		return Math.max(0, pageIndex);
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex != getPageIndex()) {
			this.pageIndex = Math.min(Math.max(0, pageIndex),
					getPageCount() - 1);
			fireViewerModelChanged(false);
			if (tFolder != null && document != null && document.hasParts()) {
				int ind = 0;
				for (Iterator<Map.Entry<Integer, PrintPart>> it = document
						.getParts().partsIterator(); it.hasNext();) {
					int index = it.next().getKey();
					if (index <= pageIndex)
						ind++;
					else
						break;
				}
				if (!startsAtZero && ind > 0)
					ind++;
				tFolder.setSelection(ind - 1);
			}
		}
	}

	public boolean canGotoFirstPage() {
		return hasReport() && pageIndex > 0;
	}

	public void gotoFirstPage() {
		if (this.pageIndex != 0 && canGotoFirstPage())
			setPageIndex(0);
	}

	public boolean canGotoLastPage() {
		return hasReport() && pageIndex < getPageCount() - 1;
	}

	public void gotoLastPage() {
		if (canGotoLastPage()) {
			setPageIndex(getPageCount() - 1);
		}
	}

	public boolean canGotoNextPage() {
		return hasReport() && pageIndex < getPageCount() - 1;
	}

	public void gotoNextPage() {
		if (canGotoNextPage()) {
			setPageIndex(pageIndex + 1);
		}
	}

	public boolean canGotoPreviousPage() {
		return hasReport() && pageIndex > 0;
	}

	public void gotoPreviousPage() {
		if (canGotoPreviousPage()) {
			setPageIndex(pageIndex - 1);
		}
	}

	private Set<IReportViewerListener> listenerSet = new LinkedHashSet<IReportViewerListener>();

	public void addReportViewerListener(IReportViewerListener listener) {
		listenerSet.add(listener);
	}

	public void removeReportViewerListener(IReportViewerListener listener) {
		listenerSet.remove(listener);
	}

	public void fireViewerModelChanged(boolean isCurrentPage) {
		ReportViewerEvent e = new ReportViewerEvent(this, isCurrentPage);
		for (IReportViewerListener l : listenerSet)
			l.viewerStateChanged(e);
	}

	private CTabFolder tFolder;
	private boolean startsAtZero;
	private FormData tFolderFD;

	public Control createControl(Composite parent) {
		if (viewerComposite == null) {
			viewerComposite = new Composite(parent, SWT.NONE);
			FormLayout layout = new FormLayout();
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			viewerComposite.setLayout(layout);

			tFolder = new CTabFolder(viewerComposite, SWT.TOP | SWT.FLAT);
			tFolderFD = new FormData();
			tFolderFD.height = 0;
			tFolderFD.left = new FormAttachment(0, 0);
			tFolderFD.right = new FormAttachment(100, 0);
			tFolder.setLayoutData(tFolderFD);
			tFolder.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					CTabItem tItem = tFolder.getSelection();
					Object obj = tItem.getData();
					if (obj != null) {
						Entry<Integer, PrintPart> p = (Entry<Integer, PrintPart>) obj;
						setPageIndex(p.getKey());
					} else
						gotoFirstPage();
				}
			});

			viewerCanvas = new ViewerCanvas(viewerComposite, style, jContext);
			viewerCanvas.setReportViewer(this);
			FormData fd = new FormData();
			fd.top = new FormAttachment(tFolder, 0);
			fd.bottom = new FormAttachment(100, 0);
			fd.left = new FormAttachment(0, 0);
			fd.right = new FormAttachment(100, 0);
			viewerCanvas.setLayoutData(fd);
		}
		return viewerComposite;
	}

	public Control getControl() {
		return viewerComposite;
	}

	public void addHyperlinkListener(JRHyperlinkListener listener) {
		if (hyperlinkListeners == null) {
			hyperlinkListeners = new ArrayList<JRHyperlinkListener>();
		} else {
			hyperlinkListeners.remove(listener); // add once
		}

		hyperlinkListeners.add(listener);
	}

	public void removeHyperlinkListener(JRHyperlinkListener listener) {
		if (hyperlinkListeners != null)
			hyperlinkListeners.remove(listener);
	}

	public JRHyperlinkListener[] getHyperlinkListeners() {
		return hyperlinkListeners == null ? new JRHyperlinkListener[0]
				: (JRHyperlinkListener[]) hyperlinkListeners
						.toArray(new JRHyperlinkListener[hyperlinkListeners
								.size()]);
	}
}
