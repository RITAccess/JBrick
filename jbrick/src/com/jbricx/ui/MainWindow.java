package com.jbricx.ui;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.actions.AboutAction;
import com.jbricx.actions.CompileAction;
import com.jbricx.actions.CopyAction;
import com.jbricx.actions.CutAction;
import com.jbricx.actions.DirectControlAction;
import com.jbricx.actions.DownloadAction;
import com.jbricx.actions.ExitAction;
import com.jbricx.actions.FindAction;
import com.jbricx.actions.FindBrickAction;
import com.jbricx.actions.GotoAction;
import com.jbricx.actions.HelpContentAction;
import com.jbricx.actions.JoyStickAction;
import com.jbricx.actions.MethodTemplateAction;
import com.jbricx.actions.NewAction;
import com.jbricx.actions.OpenAction;
import com.jbricx.actions.PasteAction;
import com.jbricx.actions.PianoAction;
import com.jbricx.actions.PreferencesAction;
import com.jbricx.actions.PrintAction;
import com.jbricx.actions.PrintPreviewAction;
import com.jbricx.actions.RedoAction;
import com.jbricx.actions.SaveAction;
import com.jbricx.actions.SaveAsAction;
import com.jbricx.actions.SelectAllAction;
import com.jbricx.actions.UndoAction;
import com.jbricx.communications.AbstractNXTBrick;
import com.jbricx.communications.WindowsNXTBrick;
import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.pjo.JBrickEditor;
import com.jbricx.preferences.TextPreferencePage;

/**
 * This class provides the main window of JBrickEditor
 */
public class MainWindow extends ApplicationWindow implements
		IPropertyChangeListener {
	/*
	 * // The viewer private SourceViewer viewer;
	 */
	// The actions

	private AboutAction aboutAction = new AboutAction();
	private HelpContentAction helpContentAction = new HelpContentAction();
	private CopyAction copyAction = new CopyAction();
	private CutAction cutAction = new CutAction();
	private SelectAllAction selectAllAction = new SelectAllAction();
	private ExitAction exitAction = new ExitAction();
	private FindAction findAction = new FindAction();
	private GotoAction gotoAction = new GotoAction();
	private NewAction newAction = new NewAction();
	private OpenAction openAction = new OpenAction();
	private PasteAction pasteAction = new PasteAction();
	private PreferencesAction prefsAction = new PreferencesAction();
	private PrintAction printAction = new PrintAction();
	private PrintPreviewAction printPreviewAction = new PrintPreviewAction();
	private RedoAction redoAction = new RedoAction();
	private SaveAction saveAction = new SaveAction();
	private SaveAsAction saveAsAction = new SaveAsAction();
	private UndoAction undoAction = new UndoAction();
	private DownloadAction downloadAction = new DownloadAction();
	private DirectControlAction directControlAction = new DirectControlAction();
	private JoyStickAction joystickAction = new JoyStickAction();
	private PianoAction pianoAction = new PianoAction();
	private FindBrickAction findBrickAction = new FindBrickAction();
	private CompileAction compileAction = new CompileAction();
	private MethodTemplateAction methodTemplateAction = new MethodTemplateAction();
	SourceViewerConfiguration configuration = new SourceViewerConfiguration();
	// The font
	private Font font;
	public static FileExplorerTabItem explorer_1 = null; 
	 

	/*
	 * // The undo manager private IUndoManager undoManager;
	 */
	// Right Click Menu
	private MenuManager menuManager;
	File treeRootFile;
	JBrickEditorTabFolder tabFolder;
	private StatusTabItem statusTabItem;

	/* ArrayList<SourceViewer> viewersList; */
	/**
	 * MainWindow constructor
	 */
	public MainWindow() {
		super(null);
		addMenuBar();
		addToolBar(SWT.FLAT);
		addStatusLine();

		/* viewersList = new ArrayList<SourceViewer>(); */
	}

	/**
	 * Runs the application
	 */
	public void run() {

		setBlockOnOpen(true);
		open();
		Display.getCurrent().dispose();

	}

	/**
	 * Configures the shell
	 *
	 * @param shell
	 *            the shell
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("JBrick Editor");
	}

	/**
	 * Creates the main window's contents
	 *
	 * @param parent
	 *            the main window
	 * @return Control
	 */
	protected Control createContents(final Composite parent) {

		String workspacePath = getWorkspacePath();
		if (workspacePath == null) {
			workspacePath = setWorkspacePath(parent);
		}
		this.treeRootFile = new File(workspacePath);
		setStatus("Successfully Lauched!");

		// Someday we'll know what these three lines are for.
		final Label l = new Label(parent, SWT.NONE);
		l.setToolTipText("helllllll oooooo Jaws:");
		l.setVisible(false);

		/* Divide the main window in three sections: Explorer, Editor and Status.
		 * The first SashForm contains the Explorer and the second SashForm.
		 * The second SashForm contains the Editor and the Status.
		 */

		// Create the first SashForm to hold the Explorer and second SashForm.
		final SashForm sashForm1 = new SashForm(parent, SWT.HORIZONTAL);
		sashForm1.setLayout(new FillLayout());

		// Create the tree viewer to display the file tree.
		final CTabFolder explorerTabFolder = new CTabFolder(sashForm1, SWT.LEFT);
		final FileExplorerTabItem explorer = new FileExplorerTabItem(explorerTabFolder, SWT.FILL, workspacePath);
		explorer_1 = explorer;
		
		explorer.addTreeListener(SWT.DefaultSelection, new Listener() {

			public void handleEvent(Event e) {
				IStructuredSelection selection = (IStructuredSelection) explorer.getSelection();
				File file = (File) selection.getFirstElement();

				if (!file.isDirectory()) {
					getTabFolder().open(file.getAbsolutePath());
				}
			}
		});

		// Create the second SashForm to hold the editor and status.
		final SashForm sashForm2 = new SashForm(sashForm1, SWT.VERTICAL);
		sashForm1.setLayout(new FillLayout());

		// Create the panel for the editor (JBrickEditorTabFolder)
		tabFolder = new JBrickEditorTabFolder(sashForm2, SWT.PUSH);

		// Create the status panel.
		// TODO: Resolve code tangling. This is kept just to avoid breaking something.
		final CTabFolder statusTabFolder = new CTabFolder(sashForm2, SWT.PUSH);
		statusTabFolder.setMaximizeVisible(true);
		System.out.println("cargando el contenido del explorador");
		
		statusTabItem = new StatusTabItem(statusTabFolder, SWT.FILL) {
			@Override
			protected IDocument getDocument() {
				return tabFolder.getSelection().getDocument();
			};

			@Override
			protected void setSelectedRange(int offset, int lineLength) {
				tabFolder.getSelection().getViewer().setSelectedRange(offset, lineLength);
			}

			@Override
			protected StatusLineManager getStatusLineManager() {
				return MainWindow.this.getStatusLineManager();
			}

			;
		};

		// Add the listener the Editor Panel
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {

			@Override
			public void restore(CTabFolderEvent event) {
				tabFolder.setMaximized(false);
				tabFolder.setMinimized(false);
				sashForm2.setMaximizedControl(null);
				sashForm1.setMaximizedControl(null);
			}

			@Override
			public void maximize(CTabFolderEvent event) {
				tabFolder.setMaximized(true);
				sashForm2.setMaximizedControl(tabFolder);
				sashForm1.setMaximizedControl(sashForm2);
			}

			@Override
			public void minimize(CTabFolderEvent event) {
				tabFolder.setMinimized(true);
				statusTabFolder.setMaximized(true);
				sashForm2.setMaximizedControl(statusTabFolder);
			}
		});

		// Add the listener for the Status Panel
		statusTabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {

			@Override
			public void restore(CTabFolderEvent event) {
				statusTabFolder.setMaximized(false);
				statusTabFolder.setMinimized(false);
				tabFolder.setMinimized(false);
				sashForm2.setMaximizedControl(null);
				sashForm1.setMaximizedControl(null);
			}

			@Override
			public void maximize(CTabFolderEvent event) {
				statusTabFolder.setMaximized(true);
				sashForm2.setMaximizedControl(statusTabFolder);
				sashForm1.setMaximizedControl(sashForm2);
			}
		});

		// Set the different weights for both panels. This affect their0 size.
		sashForm1.setWeights(new int[]{20, 80});
		sashForm2.setWeights(new int[]{80, 20});

		getMenuBarManager().updateAll(true);
		l.setFocus();

		return parent;
	}

	public void TabItemClosed(CTabFolderEvent event) {
		if (event.item.equals(this)) {
			event.doit = false;
		}
	}

	protected void loadPreferences(JBrickTabItem tabItem) {
		IPreferenceStore ps = JBrickEditor.getInstance().getPreferences();

		String fontProp = ps.getString(FileExtensionConstants.FONT);
		if (fontProp.length() > 0) {
			FontData[] fd = new FontData[1];
			fd[0] = new FontData(fontProp);
			// setFont(fd, tabItem);
			this.getCurrentTabItem().setFont(fd);
		}
	}

	/**
	 * Updates the view with the preferences
	 *
	 */
	public void propertyChange(PropertyChangeEvent event) {
		/*if (FileExtensionConstants.FONT.equals(event.getProperty()))
		this.getCurrentTabItem().setFont((FontData[]) event.getNewValue());

		/*
		 * if (FileExtensionConstants.WRAP.equals(event.getProperty()))
		 * setWrap(((Boolean) event.getNewValue()).booleanValue()); else if
		 * (FileExtensionConstants.FONT.equals(event.getProperty()))
		 * this.getCurrentTabItem().setFont((FontData[]) event.getNewValue());
		 */
	}

	/**
	 * Creates the menu manager
	 *
	 * @return MenuManager
	 */
	protected MenuManager createMenuManager() {
		MenuManager mm = new MenuManager();
		MenuManager fileMenu = new MenuManager("&File");
		MenuManager editMenu = new MenuManager("&Edit");
		MenuManager compileMenu = new MenuManager("&Compile");
		MenuManager toolMenu = new MenuManager("&Tools");
		MenuManager downloadMenu = new MenuManager("&Download");

		MenuManager helpMenu = new MenuManager("&Help");

		mm.add(fileMenu);
		mm.add(editMenu);
		mm.add(compileMenu);
		mm.add(toolMenu);
		mm.add(helpMenu);
		mm.add(downloadMenu);

		fileMenu.add(newAction);
		fileMenu.add(openAction);
		fileMenu.add(saveAction);
		fileMenu.add(saveAsAction);
		fileMenu.add(new Separator());
		fileMenu.add(printAction);
		fileMenu.add(printPreviewAction);
		fileMenu.add(new Separator());
		fileMenu.add(exitAction);

		editMenu.add(undoAction);
		editMenu.add(redoAction);
		editMenu.add(new Separator());
		editMenu.add(cutAction);
		editMenu.add(copyAction);
		editMenu.add(pasteAction);
		editMenu.add(new Separator());
		editMenu.add(findAction);
		editMenu.add(gotoAction);
		editMenu.add(new Separator());
		editMenu.add(selectAllAction);
		editMenu.add(prefsAction);
		editMenu.add(methodTemplateAction);

		compileMenu.add(compileAction);
		compileMenu.add(downloadAction);
		compileMenu.add(findBrickAction);

		toolMenu.add(directControlAction);
		toolMenu.add(joystickAction);
		toolMenu.add(pianoAction);

		helpMenu.add(aboutAction);
		helpMenu.add(helpContentAction);

		return mm;
	}

	/**
	 * Creates the toolbar
	 *
	 * @param style
	 *            the style for the toolbar
	 * @return ToolBarManager
	 */
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager tm = new ToolBarManager(style);

		// Add all the actions
		tm.add(newAction);
		tm.add(openAction);
		tm.add(saveAction);
		tm.add(saveAsAction);
		tm.add(new Separator());
		tm.add(printAction);
		tm.add(new Separator());
		tm.add(undoAction);
		tm.add(redoAction);
		tm.add(new Separator());
		tm.add(cutAction);
		tm.add(copyAction);
		tm.add(pasteAction);
		tm.add(new Separator());
		tm.add(findAction);
		tm.add(new Separator());
		tm.add(prefsAction);
		tm.add(new Separator());
		tm.add(compileAction);

		tm.add(new Separator());
		tm.add(downloadAction);

		tm.add(new Separator());
		tm.add(aboutAction);

		AbstractNXTBrick nxt = new WindowsNXTBrick();
		if (!nxt.isConnected()) {
			tm.add(new Separator());
			//directControlAction.setEnabled(false);
			tm.add(directControlAction);
			joystickAction.setEnabled(false);
			tm.add(joystickAction);
			pianoAction.setEnabled(false);
			tm.add(pianoAction);

		}
		tm.add(new Separator());
		tm.add(findBrickAction);
		return tm;
	}

	/**
	 * Gets the text viewer
	 *
	 * @return ITextViewer
	 */
	/*
	 * public ITextViewer getViewer() { return viewer; }
	 */
	/**
	 * Sets the font
	 *
	 * @param fontData
	 */
	/*
	 * public void setFont(FontData[] fontData, JBrickTabItem tabItem) { //
	 * Create the font Font temp = new Font(getShell().getDisplay(), fontData);
	 *
	 * // If creation succeeded, dispose the old font if (font != null)
	 * font.dispose();
	 *
	 * // Use the new font font = temp;
	 * tabItem.getViewer().getTextWidget().setFont(font); }
	 */
	/**
	 * Turns on/off word wrap
	 *
	 * @param wrap
	 *            true to wrap
	 */
	/*
	 * public void setWrap(boolean wrap) {
	 * viewer.getTextWidget().setWordWrap(wrap); }
	 */
	/**
	 * Gets the undo manager
	 *
	 * @return IUndoManager
	 */
	/*
	 * public IUndoManager getUndoManager() { return undoManager; }
	 */
	/**
	 * Closes the main window
	 */
	public boolean close() {
		boolean close = false;
		if (getTabFolder().checkOverwrite()) {
			close = super.close();
			if (close) {
				if (font != null) {
					font.dispose();
				}

				String recentfiles = "";
				for (CTabItem t : tabFolder.getItems()) {
					JBrickTabItem i = (JBrickTabItem) t;

					recentfiles += i.getDocument().getFileName() + ";";
				}
				PreferenceManager mgr = new PreferenceManager();
				mgr.addToRoot(new PreferenceNode("text", "Text", null,
						TextPreferencePage.class.getName()));
				PreferenceStore ps = JBrickEditor.getInstance().getPreferences();
				ps.putValue(FileExtensionConstants.RECENTFILES, recentfiles);

				try {
					ps.save();
				} catch (IOException e) {
					System.out.println("Error Saving Preferences: "
							+ e.getMessage());
				}
				System.out.println(recentfiles);
			}
		}
		return close;
	}

	public File getTreeRootFile() {
		return treeRootFile;
	}

	public void setTreeRootFile(File treeRootFile) {
		this.treeRootFile = treeRootFile;
	}

	// Going to modify this to request preferences
	public String setWorkspacePath(Composite parent) {
		String workspace = null;
		String path;
		PreferenceStore ps = JBrickEditor.getInstance().getPreferences();
		do {
			DirectoryDialog dialog = new DirectoryDialog(parent.getShell());
			dialog.setText("Workspace Selection");
			path = dialog.open();
		} while (path == null);
		ps.putValue(FileExtensionConstants.WRKSPC, path);
		try {
			ps.save();
		} catch (IOException e) {
			System.out.println("Error Saving Preferences: " + e.getMessage());
		}
		workspace = path;
		return workspace;

	}

	// Going to modify this to request preferences
	public String getWorkspacePath() {
		// Get the preference store
		PreferenceManager mgr = new PreferenceManager();
		mgr.addToRoot(new PreferenceNode("text", "Text", null,
				TextPreferencePage.class.getName()));
		PreferenceStore ps = JBrickEditor.getInstance().getPreferences();
		String workspace = ps.getString(FileExtensionConstants.WRKSPC);

		// Check if directory exists
		File file = new File(workspace);
		boolean exists = file.exists();
		if (workspace.equals("") || !exists) {
			return null;
		} else {
			return workspace;
		}
	}

	// Going to modify this to request preferences
	public boolean isAutoCompile() {
		// Get the preference store
		PreferenceManager mgr = new PreferenceManager();
		mgr.addToRoot(new PreferenceNode("text", "Text", null,
				TextPreferencePage.class.getName()));
		PreferenceStore ps = JBrickEditor.getInstance().getPreferences();
		Boolean autoCompile = ps.getBoolean(FileExtensionConstants.AUTOCOMPILE);
		return autoCompile;
	}

	public void addFolderInNewTab(String path) {
	}

	public void openFile(String filename) {
		//getTabFolder().setFocus();
		getTabFolder().open(filename);
	}

	public void openNewFile() {
		getTabFolder().openNewFile();
	}

	public void saveFile(String filename) {
		getTabFolder().save(filename);
	}

	public JBrickEditorTabFolder getTabFolder() {
		return tabFolder;
	}

	public JBrickTabItem getCurrentTabItem() {
		return getTabFolder().getSelection();
	}

	public int getCurrentTabIndex() {
		return getTabFolder().getSelectionIndex();
	}

	public void refreshCurrentTabItem(){
		int selectedIndex = getCurrentTabIndex();
		CTabItem tabItems[] = tabFolder.getItems();
		
		int valor = tabItems.length;
		String valor2 =String.valueOf(valor);
		System.out.println("refresh refreshCurrentTabItem = " + valor2);
		
		for (CTabItem tbItem : tabItems) {
			System.out.println(":tabItems:");
			if (tbItem != null) {
				System.out.println("tbItem != null");
				JBrickTabItem tabItem = (JBrickTabItem) tbItem;
				String currentString = tabItem.getViewer().getTextWidget().getText();
				String currentSaveString = tabItem.getDocument().getFileName();
				JBrickEditor.removeObserver(tabItem);
				tabItem.dispose();
				System.out.println("tbItem.dispose...");
				if (currentSaveString != null) {
					openFile(currentSaveString);
					System.out.println("openFile");
					
				} else {
					openNewFile();
					System.out.println("openNewFile");
				}
				tabItem = getCurrentTabItem();
				tabItem.getViewer().getTextWidget().setText(currentString);
				
				
			}
		}
		if (0 <= selectedIndex){
			tabFolder.setSelection(selectedIndex);
		}
		
		
		
		
		
		
		/*final FileExplorerTabItem explorer = new FileExplorerTabItem(explorerTabFolder, SWT.FILL, workspacePath);
		explorer.addTreeListener(SWT.DefaultSelection, new Listener() {

			public void handleEvent(Event e) {
				IStructuredSelection selection = (IStructuredSelection) explorer.getSelection();
				File file = (File) selection.getFirstElement();

				if (!file.isDirectory()) {
					getTabFolder().open(file.getAbsolutePath());
				}
			}
		});*/
	}
	
	public void refresh_2() {
		System.out.println("refresh_2");
		explorer_1.refreshView();
	}

	public void setTabFolder(JBrickEditorTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	public Composite getTable() {
		return statusTabItem.getTable();
	}
}
