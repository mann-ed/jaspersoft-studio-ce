/*******************************************************************************
 * Copyright © 2010-2023. Cloud Software Group, Inc. All rights reserved.
 *******************************************************************************/
package com.jaspersoft.studio.font;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.commands.operations.OperationStatus;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;

import com.jaspersoft.studio.JaspersoftStudioPlugin;
import com.jaspersoft.studio.editor.preview.ABasicEditor;
import com.jaspersoft.studio.messages.Messages;
import com.jaspersoft.studio.preferences.fonts.FontPathWizard;
import com.jaspersoft.studio.preferences.fonts.FontSetDialog;
import com.jaspersoft.studio.preferences.fonts.FontSetFamilyDialog;
import com.jaspersoft.studio.preferences.fonts.FontURLWizard;
import com.jaspersoft.studio.preferences.fonts.SelectFontSetSetDialog;
import com.jaspersoft.studio.preferences.fonts.wizard.FontConfigWizard;
import com.jaspersoft.studio.utils.ModelUtils;

import net.sf.jasperreports.eclipse.builder.Markers;
import net.sf.jasperreports.eclipse.ui.util.UIUtils;
import net.sf.jasperreports.eclipse.util.FileUtils;
import net.sf.jasperreports.eclipse.util.Misc;
import net.sf.jasperreports.eclipse.util.StringUtils;
import net.sf.jasperreports.engine.JRCloneable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.fonts.FontExtensionsCollector;
import net.sf.jasperreports.engine.fonts.FontFace;
import net.sf.jasperreports.engine.fonts.FontFamily;
import net.sf.jasperreports.engine.fonts.FontSet;
import net.sf.jasperreports.engine.fonts.FontSetFamily;
import net.sf.jasperreports.engine.fonts.SimpleFontExtensionHelper;
import net.sf.jasperreports.engine.fonts.SimpleFontExtensionsContainer;
import net.sf.jasperreports.engine.fonts.SimpleFontFace;
import net.sf.jasperreports.engine.fonts.SimpleFontFamily;
import net.sf.jasperreports.engine.fonts.SimpleFontSet;
import net.sf.jasperreports.engine.fonts.SimpleFontSetFamily;

public class FontEditor extends ABasicEditor {
	public static final String ID = "com.jaspersoft.studio.fontextension.editor";
	public static final String DEFAULT_FILENAME = "fonts.xml";
	private static String lastLocation;
	private FontExtensionsCollector container;
	private boolean saving = false;
	private TreeViewer tree;
	private Button addURLButton;
	private Button addPathButton;
	private Button addButton;
	private Button duplicateButton;
	private Button editButton;
	private Button removeButton;
	private Button upButton;
	private Button downButton;
	private Button addSetButton;
	private Button addToSetButton;
	private Button exportButton;

	public FontEditor() {
		super(true);
	}

	@Override
	protected void setInput(IEditorInput input) {
		if (saving)
			return;
		super.setInput(input);
		InputStream in = null;
		try {
			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			in = file.getContents(true);

			container = new FontExtensionsCollector();
			SimpleFontExtensionHelper.getInstance().loadFontExtensions(getJrContext(), in, container, false);
		} catch (Exception e) {
			UIUtils.showError(e);
		} finally {
			FileUtils.closeStream(in);
		}
		if (container == null)
			container = new FontExtensionsCollector();
		if (tree != null) {
			tree.setInput(container);
			tree.refresh(true);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite cmp = new Composite(parent, SWT.NONE);
		cmp.setLayout(new GridLayout(2, false));

		tree = new TreeViewer(cmp, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.setContentProvider(new ITreeContentProvider() {

			@Override
			public void dispose() {
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof FontExtensionsCollector) {
					List<Object> lst = new ArrayList<>();
					lst.addAll(((FontExtensionsCollector) inputElement).getFontFamilies());
					lst.addAll(((FontExtensionsCollector) inputElement).getFontSets());
					return lst.toArray();
				}
				if (inputElement instanceof List<?>)
					return ((List<?>) inputElement).toArray();
				return new Object[0];
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof SimpleFontSet)
					return ((SimpleFontSet) parentElement).getFamilies().toArray();
				return null;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public boolean hasChildren(Object element) {
				if (element instanceof SimpleFontSet && !Misc.isNullOrEmpty(((SimpleFontSet) element).getFamilies()))
					return true;
				return false;
			}

		});
		tree.setLabelProvider(new DelegatingStyledCellLabelProvider(new FontLabelProvider()));
		tree.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		tree.setInput(container);
		tree.addDoubleClickListener(event -> {
			if (editButton.isEnabled())
				editPressed();
		});
		tree.getTree().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectionChanged();
			}
		});

		createButtons(cmp);
	}

	private void createButtons(Composite cmp) {
		Composite c = new Composite(cmp, SWT.NONE);
		c.setLayout(new GridLayout());
		c.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		addURLButton = createPushButton(c, Messages.JRVersionPage_3);

		addPathButton = createPushButton(c, Messages.JRVersionPage_4);

		addButton = createPushButton(c, Messages.common_add);
		((GridData) addButton.getLayoutData()).verticalIndent = 20;

		duplicateButton = createPushButton(c, Messages.common_duplicate);

		editButton = createPushButton(c, Messages.FontListFieldEditor_editButton);

		removeButton = createPushButton(c, Messages.common_delete);
		upButton = createPushButton(c, Messages.common_up);
		downButton = createPushButton(c, Messages.common_down);

		addSetButton = createPushButton(c, Messages.FontListFieldEditor_6);
		((GridData) addSetButton.getLayoutData()).verticalIndent = 20;

		addToSetButton = createPushButton(c, "Add To Set");

		exportButton = createPushButton(c, Messages.FontListFieldEditor_exportButton);
		((GridData) exportButton.getLayoutData()).verticalIndent = 20;
	}

	public void setEnabled(boolean enabled, Composite parent) {
		editButton.setEnabled(enabled);
		addPathButton.setEnabled(enabled);
		addURLButton.setEnabled(enabled);
		addSetButton.setEnabled(enabled);
		addToSetButton.setEnabled(enabled);
	}

	private Button createPushButton(Composite box, String title) {
		Button b = new Button(box, SWT.PUSH);
		b.setText(title);
		b.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		b.addSelectionListener(getSelectionListener());
		return b;
	}

	private SelectionListener selectionListener;

	private SelectionListener getSelectionListener() {
		if (selectionListener == null)
			selectionListener = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					Widget widget = event.widget;
					if (widget == addButton)
						addPressed();
					else if (widget == addSetButton)
						addSetPressed();
					else if (widget == addToSetButton)
						add2SetPressed();
					else if (widget == duplicateButton)
						duplicatePressed();
					else if (widget == removeButton)
						removePressed();
					else if (widget == upButton)
						upPressed();
					else if (widget == downButton)
						downPressed();
					else if (widget == editButton)
						editPressed();
					else if (widget == exportButton)
						exportPressed();
					else if (widget == tree.getTree())
						selectionChanged();
					else if (widget == addURLButton)
						addURLPressed();
					else if (widget == addPathButton)
						addPathPressed();
				}
			};
		return selectionListener;
	}

	protected void selectionChanged() {
		StructuredSelection sel = (StructuredSelection) tree.getSelection();
		if (addButton != null)
			addButton.setEnabled(sel.isEmpty()
					|| (!sel.isEmpty() && sel.size() == 1 && !(sel.getFirstElement() instanceof SimpleFontSetFamily)));
		if (editButton != null)
			editButton.setEnabled(!sel.isEmpty() && sel.size() == 1);
		if (duplicateButton != null)
			duplicateButton.setEnabled(!sel.isEmpty() && !(sel.getFirstElement() instanceof SimpleFontSetFamily));
		if (exportButton != null) {
			boolean en = !sel.isEmpty();
			if (en)
				for (Object obj : sel.toList())
					if (obj instanceof FontSetFamily) {
						en = false;
						break;
					}
			exportButton.setEnabled(en);
		}
		if (addSetButton != null) {
			boolean en = !sel.isEmpty();
			if (en)
				for (Object obj : sel.toList())
					if (!(obj instanceof FontFamily)) {
						en = false;
						break;
					}
			addSetButton.setEnabled(en);
		}
		if (addToSetButton != null) {
			boolean en = !sel.isEmpty();
			if (en)
				for (Object obj : sel.toList())
					if (!(obj instanceof FontFamily)) {
						en = false;
						break;
					}
			addToSetButton.setEnabled(en);
		}
		if (duplicateButton != null)
			duplicateButton.setEnabled(!sel.isEmpty());
		if (removeButton != null)
			removeButton.setEnabled(!sel.isEmpty() && isRemovable(sel));
		if (upButton != null)
			upButton.setEnabled(!sel.isEmpty() && isSortable(sel) && canGoUp(sel));
		if (downButton != null)
			downButton.setEnabled(!sel.isEmpty() && isSortable(sel) && canGoDown(sel));
	}

	protected boolean canGoUp(StructuredSelection sel) {
		Object obj = sel.getFirstElement();
		if (obj instanceof FontFamily)
			return container.getFontFamilies().indexOf(obj) > 0;
		else if (obj instanceof FontSet) {
			return container.getFontSets().indexOf(obj) > 0;
		} else if (obj instanceof FontSetFamily) {
			for (FontSet fs : container.getFontSets())
				if (fs.getFamilies().contains(obj))
					return fs.getFamilies().indexOf(obj) > 0;
		}
		return false;
	}

	protected boolean canGoDown(StructuredSelection sel) {
		Object obj = sel.getFirstElement();
		if (obj instanceof FontFamily)
			return container.getFontFamilies().indexOf(obj) < container.getFontFamilies().size() - 1;
		else if (obj instanceof FontSet) {
			return container.getFontSets().indexOf(obj) < container.getFontSets().size() - 1;
		} else if (obj instanceof FontSetFamily) {
			for (FontSet fs : container.getFontSets())
				if (fs.getFamilies().contains(obj))
					return fs.getFamilies().indexOf(obj) < fs.getFamilies().size() - 1;
		}
		return false;
	}

	protected boolean isSortable(StructuredSelection sel) {
		return sel.size() == 1;
	}

	protected boolean isRemovable(StructuredSelection sel) {
		return true;
	}

	class FontLabelProvider extends ColumnLabelProvider implements IStyledLabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof SimpleFontSet)
				return ((SimpleFontSet) element).getName();
			else if (element instanceof SimpleFontFamily)
				return ((SimpleFontFamily) element).getName();
			else if (element instanceof SimpleFontSetFamily)
				return ((SimpleFontSetFamily) element).getFamilyName();
			return ""; //$NON-NLS-1$
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof SimpleFontSetFamily && ((SimpleFontSetFamily) element).isPrimary())
				return JaspersoftStudioPlugin.getInstance().getImage("icons/resources/check-16.png"); //$NON-NLS-1$
			return null;
		}

		@Override
		public StyledString getStyledText(Object element) {
			StyledString ss = new StyledString();
			if (element instanceof SimpleFontSet)
				ss.append(((SimpleFontSet) element).getName(), StyledString.QUALIFIER_STYLER);
			else if (element instanceof SimpleFontFamily)
				ss.append(((SimpleFontFamily) element).getName());
			else if (element instanceof SimpleFontSetFamily)
				ss.append(((SimpleFontSetFamily) element).getFamilyName(), StyledString.DECORATIONS_STYLER);
			return ss;
		}

	}

	@Override
	public void setFocus() {
		tree.getTree().setFocus();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			IResource resource = ((IFileEditorInput) getEditorInput()).getFile();
			IFile file = ((IFileEditorInput) getEditorInput()).getFile();

			saving = true;
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			SimpleFontExtensionHelper.writeFontExtensionsXml(os,
					new SimpleFontExtensionsContainer(container.getFontFamilies(), container.getFontSets()));
			file.setContents(new ByteArrayInputStream(os.toByteArray()), true, true, monitor);
			Markers.deleteMarkers(resource);
		} catch (CoreException | JRException e) {
			UIUtils.showError(e);
		} finally {
			saving = false;
		}
		isDirty = false;

		firePropertyChange(PROP_DIRTY);
	}

	@Override
	public void doSaveAs() {
		SaveAsDialog saveAsDialog = new SaveAsDialog(getSite().getShell());
		saveAsDialog.setOriginalFile(((FileEditorInput) getEditorInput()).getFile());
		saveAsDialog.open();
		IPath path = saveAsDialog.getResult();
		if (path != null) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			if (file != null) {
				IFileEditorInput modelFile = new FileEditorInput(file);
				setInputWithNotify(modelFile);
				setInput(modelFile);
				setPartName(file.getName());
				IProgressMonitor progressMonitor = getEditorSite().getActionBars().getStatusLineManager()
						.getProgressMonitor();
				doSave(progressMonitor);
			}
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	protected void upPressed() {
		StructuredSelection sel = (StructuredSelection) tree.getSelection();
		Object obj = sel.getFirstElement();
		if (obj instanceof FontFamily) {
			int ind = container.getFontFamilies().indexOf(obj);
			container.getFontFamilies().remove(ind);
			container.getFontFamilies().add(ind - 1, (FontFamily) obj);
		} else if (obj instanceof FontSet) {
			int ind = container.getFontSets().indexOf(obj);
			container.getFontSets().remove(ind);
			container.getFontSets().add(ind - 1, (FontSet) obj);
		} else if (obj instanceof FontSetFamily) {
			for (FontSet fs : container.getFontSets())
				if (fs.getFamilies().contains(obj)) {
					int ind = fs.getFamilies().indexOf(obj);
					fs.getFamilies().remove(ind);
					fs.getFamilies().add(ind - 1, (FontSetFamily) obj);
					break;
				}
		}
		handleValueChanged();
		tree.refresh(true);
		tree.setSelection(new StructuredSelection(obj), true);
		selectionChanged();
	}

	protected void downPressed() {
		StructuredSelection sel = (StructuredSelection) tree.getSelection();
		Object obj = sel.getFirstElement();
		if (obj instanceof FontFamily) {
			int ind = container.getFontFamilies().indexOf(obj);
			container.getFontFamilies().remove(ind);
			container.getFontFamilies().add(ind + 1, (FontFamily) obj);
		} else if (obj instanceof FontSet) {
			int ind = container.getFontSets().indexOf(obj);
			container.getFontSets().remove(ind);
			container.getFontSets().add(ind + 1, (FontSet) obj);
		} else if (obj instanceof FontSetFamily) {
			for (FontSet fs : container.getFontSets())
				if (fs.getFamilies().contains(obj)) {
					int ind = fs.getFamilies().indexOf(obj);
					fs.getFamilies().remove(ind);
					fs.getFamilies().add(ind + 1, (FontSetFamily) obj);
					break;
				}
		}
		handleValueChanged();
		tree.refresh(true);
		tree.setSelection(new StructuredSelection(obj), true);
		selectionChanged();
	}

	protected void addPressed() {
		SimpleFontFamily ff = new SimpleFontFamily();
		ff.setName(Messages.FontListFieldEditor_newFontSuggestedName);
		FontFamily font = runDialog(ff);
		if (font != null) {
			container.getFontFamilies().add(ff);
			handleValueChanged();
			tree.refresh(true);
			tree.setSelection(new StructuredSelection(ff), true);
			selectionChanged();
		}
	}

	protected void removePressed() {
		if (UIUtils.showDeleteConfirmation()) {
			StructuredSelection sel = (StructuredSelection) tree.getSelection();
			for (Object obj : sel.toList()) {
				if (obj instanceof FontFamily)
					container.getFontFamilies().remove(obj);
				else if (obj instanceof FontSet)
					container.getFontSets().remove(obj);
				else if (obj instanceof FontSetFamily) {
					for (FontSet fs : container.getFontSets())
						fs.getFamilies().remove(obj);
				}
			}
			handleValueChanged();
			tree.refresh(true);
			selectionChanged();
		}
	}

	protected void duplicatePressed() {
		StructuredSelection sel = (StructuredSelection) tree.getSelection();
		for (Object obj : sel.toList()) {
			if (obj instanceof FontFamily) {
				SimpleFontFamily clone = (SimpleFontFamily) ((SimpleFontFamily) obj).clone();
				String newname = ((FontFamily) obj).getName() + "_copy"; // $NON-NLS-1$
				for (int i = 1; i < 1000; i++) {
					boolean exists = false;
					for (FontFamily f : container.getFontFamilies()) {
						if (f.getName().equals(newname)) {
							exists = true;
							break;
						}
					}
					if (!exists)
						break;
					newname = ((FontFamily) obj).getName() + "_copy" + i; // $NON-NLS-1$
				}

				clone.setName(newname);
				container.getFontFamilies().add(clone);
				handleValueChanged();
				tree.refresh(true);
				tree.setSelection(new StructuredSelection(clone), true);
				selectionChanged();
			} else if (obj instanceof FontSet) {
				SimpleFontSet clone = (SimpleFontSet) ((SimpleFontSet) obj).clone();
				String newname = ((FontSet) obj).getName() + "_copy"; // $NON-NLS-1$
				for (int i = 1; i < 1000; i++) {
					boolean exists = false;
					for (FontSet f : container.getFontSets()) {
						if (f.getName().equals(newname)) {
							exists = true;
							break;
						}
					}
					if (!exists)
						break;
					newname = ((FontSet) obj).getName() + "_copy" + i; // $NON-NLS-1$
				}
				clone.setName(newname); // $NON-NLS-1$
				container.getFontSets().add(clone);
				handleValueChanged();
				tree.refresh(true);
				tree.setSelection(new StructuredSelection(clone), true);
				selectionChanged();
			}
		}
	}

	protected void editPressed() {
		StructuredSelection sel = (StructuredSelection) tree.getSelection();
		if (sel.getFirstElement() instanceof SimpleFontFamily) {
			FontFamily font = (SimpleFontFamily) sel.getFirstElement();
			int index = container.getFontFamilies().indexOf(font);
			font = FontEditor.runDialog((FontFamily) ((SimpleFontFamily) font).clone());
			if (font != null) {
				container.getFontFamilies().set(index, font);
				handleValueChanged();
				tree.refresh(true);
				tree.setSelection(new StructuredSelection(font), true);
				selectionChanged();
			}
		} else if (sel.getFirstElement() instanceof SimpleFontSet) {
			SimpleFontSet fset = (SimpleFontSet) sel.getFirstElement();
			FontSetDialog d = new FontSetDialog(UIUtils.getShell(), (SimpleFontSet) fset.clone());
			if (d.open() == Dialog.OK) {
				fset.setName(d.getValue().getName());
				handleValueChanged();
				tree.refresh(true);
				tree.setSelection(new StructuredSelection(fset), true);
				selectionChanged();
			}
		} else if (sel.getFirstElement() instanceof SimpleFontSetFamily) {
			SimpleFontSetFamily fsetf = (SimpleFontSetFamily) sel.getFirstElement();
			for (FontSet fs : container.getFontSets()) {
				if (fs.getFamilies().contains(fsetf)) {
					FontSetFamilyDialog d = new FontSetFamilyDialog(UIUtils.getShell(), (SimpleFontSet) fs,
							(SimpleFontSetFamily) fsetf.clone());
					if (d.open() == Dialog.OK) {
						SimpleFontSetFamily v = d.getValue();
						fsetf.setPrimary(v.isPrimary());
						fsetf.setIncludedScripts(v.getIncludedScripts());
						fsetf.setExcludedScripts(v.getExcludedScripts());
						handleValueChanged();
						tree.refresh(true);
						tree.setSelection(new StructuredSelection(fsetf), true);
						selectionChanged();
					}
				}
			}
		}
	}

	protected void addSetPressed() {
		StructuredSelection sel = (StructuredSelection) tree.getSelection();
		SimpleFontSet fs = new SimpleFontSet();
		fs.setName("FontSet"); //$NON-NLS-1$
		FontSetDialog d = new FontSetDialog(UIUtils.getShell(), fs);
		if (d.open() == Dialog.OK) {
			boolean first = true;
			for (Object obj : sel.toList()) {
				if (obj instanceof FontFamily) {
					SimpleFontSetFamily fsf = new SimpleFontSetFamily();
					fsf.setFamilyName(((FontFamily) obj).getName());
					fsf.setPrimary(first);
					first = false;
					fs.addFamily(fsf);
				}
			}
			handleValueChanged();
			container.getFontSets().add(fs);
			tree.refresh(true);
			tree.setSelection(new StructuredSelection(fs), true);
			selectionChanged();
		}
	}

	protected void add2SetPressed() {
		StructuredSelection sel = (StructuredSelection) tree.getSelection();
		SelectFontSetSetDialog d = new SelectFontSetSetDialog(UIUtils.getShell(), container.getFontSets());
		if (d.open() == Dialog.OK) {
			FontSet fs = d.getValue();
			if (fs == null)
				return;
			for (Object obj : sel.toList()) {
				if (obj instanceof FontFamily) {
					SimpleFontSetFamily fsf = new SimpleFontSetFamily();
					fsf.setFamilyName(((FontFamily) obj).getName());
					((SimpleFontSet) d.getValue()).addFamily(fsf);
				}
			}
			handleValueChanged();
			tree.refresh(true);
			tree.setSelection(new StructuredSelection(fs), true);
			selectionChanged();
		}
	}

	protected void addURLPressed() {
		FontURLWizard wiz = new FontURLWizard(new ArrayList<>(container.getFontFamilies()));
		WizardDialog d = new WizardDialog(UIUtils.getShellForWizardDialog(), wiz);
		d.setPageSize(800, 50);
		if (d.open() == Dialog.OK) {
			if (container == null) {
				container = new FontExtensionsCollector();
				tree.setInput(container);
			}
			container.getFontFamilies().clear();
			container.getFontFamilies().addAll(wiz.getFonts());
			handleValueChanged();
			tree.refresh(true);
			selectionChanged();
		}
	}

	protected void addPathPressed() {
		FontPathWizard wiz = new FontPathWizard(new ArrayList<>(container.getFontFamilies()));
		WizardDialog d = new WizardDialog(UIUtils.getShell(), wiz);
		d.setPageSize(800, 50);
		if (d.open() == Dialog.OK) {
			if (container == null) {
				container = new FontExtensionsCollector();
				tree.setInput(container);
			}
			container.getFontFamilies().clear();
			container.getFontFamilies().addAll(wiz.getFonts());
			handleValueChanged();
			tree.refresh(true);
			selectionChanged();
		}
	}

	protected void exportPressed() {
		StructuredSelection sel = (StructuredSelection) tree.getSelection();
		if (sel.isEmpty())
			return;

		List<FontFamily> ff = new ArrayList<>();
		for (Object obj : sel.toList())
			if (obj instanceof FontFamily)
				ff.add((FontFamily) ((JRCloneable) obj).clone());
		List<FontSet> fs = new ArrayList<>();
		for (Object obj : sel.toList())
			if (obj instanceof FontSet) {
				fs.add((FontSet) ((JRCloneable) obj).clone());
				// let's add all fonts from the family
				for (FontSetFamily fsf : ((FontSet) obj).getFamilies()) {
					for (FontFamily f : container.getFontFamilies()) {
						if (f.getName().equals(fsf.getFamilyName())) {
							boolean exists = false;
							for (FontFamily item : ff) {
								if (item.getName().equals(fsf.getFamilyName())) {
									exists = true;
									break;
								}
							}
							if (exists)
								break;
							ff.add((FontFamily) ((JRCloneable) f).clone());
							break;
						}
					}
				}
			}

		final SimpleFontExtensionsContainer c = new SimpleFontExtensionsContainer(ff, fs);

		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
		fd.setText(Messages.FontListFieldEditor_exportToJar);
		setupLastLocation(fd);
		fd.setFilterExtensions(new String[] { "*.jar", "*.zip" }); //$NON-NLS-1$ //$NON-NLS-2$
		final String selected = fd.open();
		setLastLocation(fd, selected);
		if (selected != null) {
			Job job = new Job(Messages.FontListFieldEditor_exportToJar) {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					monitor.beginTask(Messages.FontListFieldEditor_exportToJar, IProgressMonitor.UNKNOWN);
					try {
						exportJAR(c, selected);

						IFile[] resource = root.findFilesForLocationURI(new File(selected).toURI());
						if (resource != null) {
							for (IFile f : resource)
								f.refreshLocal(1, monitor);
						}
					} catch (final Exception e) {
						e.printStackTrace();
						UIUtils.getDisplay().asyncExec(() -> {
							IStatus status = new OperationStatus(IStatus.ERROR,
									JaspersoftStudioPlugin.getUniqueIdentifier(), 1, "Error saving file.", //$NON-NLS-1$
									e.getCause());
							ErrorDialog.openError(Display.getDefault().getActiveShell(),
									Messages.FontListFieldEditor_errorSave, null, status);
						});
					} finally {
						monitor.done();
					}
					return Status.OK_STATUS;
				}
			};
			job.setPriority(Job.LONG);
			job.schedule();
		}
	}

	public static void exportJAR(SimpleFontExtensionsContainer c, String selected) throws IOException, JRException {
		FileOutputStream fos = new FileOutputStream(selected);
		try (ZipOutputStream zipos = new java.util.zip.ZipOutputStream(fos)) {
			zipos.setMethod(ZipOutputStream.DEFLATED);

			String prefix = "family" + (new Date()).getTime(); //$NON-NLS-1$
			String fontXmlFile = "fonts" + prefix + ".xml"; //$NON-NLS-1$ //$NON-NLS-2$

			ZipEntry propsEntry = new ZipEntry("jasperreports_extension.properties"); //$NON-NLS-1$
			zipos.putNextEntry(propsEntry);

			PrintWriter pw = new PrintWriter(zipos);

			pw.println(
					"net.sf.jasperreports.extension.registry.factory.fonts=net.sf.jasperreports.engine.fonts.SimpleFontExtensionsRegistryFactory"); //$NON-NLS-1$
			pw.println(
					"net.sf.jasperreports.extension.simple.font.families.ireport" + prefix + "=fonts/" + fontXmlFile); //$NON-NLS-1$ //$NON-NLS-2$

			pw.flush();
			Set<String> names = new HashSet<>();
			for (FontFamily f : c.getFontFamilies()) {
				writeFont2zip(names, zipos, f, (SimpleFontFace) f.getNormalFace());
				writeFont2zip(names, zipos, f, (SimpleFontFace) f.getBoldFace());
				writeFont2zip(names, zipos, f, (SimpleFontFace) f.getItalicFace());
				writeFont2zip(names, zipos, f, (SimpleFontFace) f.getBoldItalicFace());

				String pdfenc = f.getPdfEncoding();
				if (ModelUtils.getKey4PDFEncoding(pdfenc) == null) {
					pdfenc = ModelUtils.getPDFEncoding2key(pdfenc);
					((SimpleFontFamily) f).setPdfEncoding(pdfenc);
				}
			}

			ZipEntry fontsXmlEntry = new ZipEntry("fonts/" + fontXmlFile); //$NON-NLS-1$
			zipos.putNextEntry(fontsXmlEntry);

			SimpleFontExtensionHelper.writeFontExtensionsXml(zipos, c);

			zipos.finish();
		} finally {
			FileUtils.closeStream(fos);
		}
	}

	public static void writeFont2zip(Set<String> names, ZipOutputStream zipos, FontFamily fontFamily,
			SimpleFontFace font) throws IOException {
		if (font == null)
			return;
		try {
			font.setTtf(writeFont(names, zipos, fontFamily, font, font.getTtf()), false);
		} catch (Exception r) {
			// do nothing here
		}
		font.setPdf(writeFont(names, zipos, fontFamily, font, font.getPdf()));
		font.setEot(writeFont(names, zipos, fontFamily, font, font.getEot()));
		font.setSvg(writeFont(names, zipos, fontFamily, font, font.getSvg()));
		font.setWoff(writeFont(names, zipos, fontFamily, font, font.getWoff()));
	}

	public static String writeFont(Set<String> names, ZipOutputStream zipos, FontFamily fontFamily, FontFace font,
			String fontname) throws IOException {
		if (Misc.isNullOrEmpty(fontname))
			return fontname;
		File file = new File(fontname);
		if (file.exists()) {
			String name = "fonts/" + StringUtils.toPackageName(fontFamily.getName()) + "/" + file.getName(); //$NON-NLS-1$ //$NON-NLS-2$
			if (!names.contains(name)) {
				ZipEntry ttfZipEntry = new ZipEntry(name);
				zipos.putNextEntry(ttfZipEntry);

				FileInputStream in = new FileInputStream(fontname); // Stream to
																	// read file
				try {
					byte[] buffer = new byte[4096]; // Create a buffer for
													// copying
					int bytesRead;
					while ((bytesRead = in.read(buffer)) != -1)
						zipos.write(buffer, 0, bytesRead);
				} finally {
					FileUtils.closeStream(in);
				}
				names.add(name);
			}
			fontname = name;
		}
		return fontname;
	}

	public static FontFamily runDialog(FontFamily font) {
		FontConfigWizard wizard = new FontConfigWizard();
		WizardDialog dialog = new WizardDialog(UIUtils.getShellForWizardDialog(), wizard);
		wizard.setFont(font);
		dialog.create();
		if (dialog.open() == Dialog.OK)
			return wizard.getFont();
		return null;
	}

	public static String setupLastLocation(FileDialog dialog) {
		if (lastLocation == null)
			lastLocation = ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString();
		dialog.setFilterPath(lastLocation);
		return lastLocation;
	}

	public static void setLastLocation(FileDialog dialog, String selected) {
		if (!Misc.isNullOrEmpty(selected))
			lastLocation = selected.substring(0, selected.lastIndexOf(File.separatorChar));
		else if (!Misc.isNullOrEmpty(dialog.getFileName()))
			lastLocation = dialog.getFileName();
	}
}
