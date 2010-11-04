/**
 * 
 */
package com.jbricx.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.LineNumberChangeRulerColumn;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;

import annotation.AnnotationMarkerAccess;
import annotation.ColorCache;

import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.pjo.JBrickEditor;
import com.jbricx.preferences.TextPreferencePage;

/**
 * @author byktol
 */
public class JBrickEditorTabFolder extends CTabFolder implements TabFolder {

	public LineNumberChangeRulerColumn lnrc;
	private final List<String> filenamesList;

	public JBrickEditorTabFolder(final Composite parent, final int style) {
		super(parent, style);
		filenamesList = new ArrayList<String>();

		setMinimizeVisible(true);
		setMaximizeVisible(true);
		addCTabFolder2Listener(new CTabFolder2Adapter() {

			@Override
			public void close(CTabFolderEvent event) {
				JBrickTabItem tabItem = (JBrickTabItem) event.item;
				if (askCloseWithoutSaving(tabItem)) {
					JBrickEditor.getInstance().getMainWindow().setStatus("Closed");
					
					try {
						File file = new File(tabItem.getFilename());
						filenamesList.remove(file.getName());						
					} catch (NullPointerException ne) {
						// the file has not been saved yet so ignore
					}
				} else {
					event.doit = false;
				}
			}
		});

		/* Construction time */
		long start = System.currentTimeMillis();
		System.out.println("start ");

		// Composite rightPanel = new Composite(sashForm, SWT.NONE);


		/*
		 * GridLayout gridLayout = new GridLayout(); gridLayout.numColumns = 1;
		 */

		// ******** top part of the right panel **********************
		// Create the viewer
		CompositeRuler ruler = new CompositeRuler(10);

		/*
		 * LineNumberRulerColumn lnrc = new LineNumberRulerColumn(); lnrc.setForeground(new
		 * Color(parent.getShell().getDisplay(), new RGB( 255, 0, 0)));
		 */
		lnrc = new LineNumberChangeRulerColumn(new ColorCache());
		lnrc.setForeground(new Color(parent.getShell().getDisplay(), new RGB(255, 0, 0)));
		// lnrc.getLineOfLastMouseButtonActivity();

		// lnrc.getControl().getAccessible().textSelectionChanged()
		ruler.addDecorator(0, lnrc);

		// tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		setSimple(false);
		setUnselectedImageVisible(false);
		setUnselectedCloseVisible(false);
		Color titleForeColor = parent.getShell().getDisplay().getSystemColor(SWT.COLOR_TITLE_FOREGROUND);

		Color titleBackColor1 = parent.getShell().getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND);

		// Color titleBackColor1 = parent.getShell().getDisplay().getSystemColor(
		// SWT.COLOR_TITLE_FOREGROUND);

		Color titleBackColor2 = parent.getShell().getDisplay().getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
		setSelectionForeground(titleForeColor);
		setSelectionBackground(new Color[]{titleBackColor1, titleBackColor2}, new int[]{100}, true);

		// TODO: change tabs names and content, not byktol's
		// tab1
		// JBrickTabItem tabItem = new JBrickTabItem(tabFolder, SWT.CLOSE,
		// null);
		// tabFolder.setSelection(tabItem);

		ArrayList<String> recentfiles = getRecentFiles(parent);
		System.out.println(recentfiles);

		boolean openedfile = false;

		for (String file : recentfiles) {
			File f = new File(file);
			boolean exists = f.exists();
			if (exists) {
				open(file);
				openedfile = true;
			}

		}

		if (!openedfile) {
			JBrickTabItem tabItem = new JBrickTabItem(this, SWT.CLOSE, null);
			setSelection(tabItem);
		}

		// ///////////////////////////////////////////////////////////////

		// rulers
		AnnotationModel fAnnotationModel = new AnnotationModel();
		IAnnotationAccess fAnnotationAccess = new AnnotationMarkerAccess();

		ColorCache cc = new ColorCache();
		CompositeRuler fCompositeRuler = new CompositeRuler();
		OverviewRuler fOverviewRuler = new OverviewRuler(fAnnotationAccess, 12, cc);
		AnnotationRulerColumn annotationRuler = new AnnotationRulerColumn(fAnnotationModel, 16, fAnnotationAccess);
		fCompositeRuler.setModel(fAnnotationModel);
		fOverviewRuler.setModel(fAnnotationModel);

		// annotation ruler is decorating our composite ruler
		fCompositeRuler.addDecorator(0, annotationRuler);
		// ///////////////////////////////////////////////////////////////

		long end = System.currentTimeMillis();
		start = end - start;
		System.out.println("it took : " + start);
	}

	public boolean contains(final String filename) {
		return filenamesList.contains(filename);
	}

	@Override
	public boolean open(String filepath) {
		File file = new File(filepath);
		String filename = file.getName();

		boolean isAlreadyOpen = false;

		// check if the file exists in the list of opened file
		for (int i = 0; i < filenamesList.size(); i++) {
			if (filename.equals(filenamesList.get(i))) {
				// so the file is already opened in one of the tab
				JBrickTabItem tabItem = getItem(i);
				this.setSelection(tabItem);

				isAlreadyOpen = true;
				break;
			}
		}
		if (!isAlreadyOpen) {
			JBrickTabItem newTabItem = new JBrickTabItem(this, SWT.CLOSE, new File(filepath));
			this.setSelection(newTabItem);
			System.out.println("adding: " + filename);
			filenamesList.add(filename);
		}
		return true;
	}

	@Override
	public boolean openNewFile() {
		JBrickTabItem newTabItem = new JBrickTabItem(this, SWT.CLOSE, null);
		this.setSelection(newTabItem);
		return true;
	}

	public void save(String filename) {
		if (!filenamesList.contains(filename)) {
			filenamesList.add(filename);
		}
	}

	@Override
	public JBrickTabItem getSelection() {
		return (JBrickTabItem) super.getSelection();
	}

	@Override
	public JBrickTabItem getItem(int index) {
		return (JBrickTabItem) super.getItem(index);
	}

	/**
	 * Checks the current file for unsaved changes. If it has unsaved changes, confirms that user wants to overwrite
	 *
	 * @return boolean
	 */
	public boolean checkOverwrite() {
		boolean proceed = true;

		for (CTabItem tab : getItems()) {
			JBrickTabItem tabItem = (JBrickTabItem) tab;
			proceed = askCloseWithoutSaving(tabItem);
		}
		return proceed;
	}

	/**
	 * Performs check on a tab item's on close event if its document has any unsaved changes
	 *
	 * @param tabItem
	 *          the JBrickTabItem which is clicked for close
	 * @return true if user confirms to proceed without closing else false
	 */
	private boolean askCloseWithoutSaving(JBrickTabItem tabItem) {
		boolean proceed = true;
		if (tabItem.getDocument().isDirty()) {
			proceed = MessageDialog.openConfirm(null, "Close without saving!",
					"You have unsaved file(s) in the document. Are you sure you want to proceed without saving them?");
		}
		return proceed;
	}

	public ArrayList<String> getRecentFiles(Composite parent) {
		// Get the preference store
		PreferenceManager mgr = new PreferenceManager();
		mgr.addToRoot(new PreferenceNode("text", "Text", null, TextPreferencePage.class.getName()));
		PreferenceStore ps = JBrickEditor.getInstance().getPreferences();
		Boolean loadrecent = ps.getBoolean(FileExtensionConstants.BOOLRECENTFILES);

		ArrayList<String> recentfiles = new ArrayList<String>();
		if (loadrecent) {
			for (String s : ps.getString(FileExtensionConstants.RECENTFILES).split(";")) {
				recentfiles.add(s);
			}
		}

		return recentfiles;
	}
}
