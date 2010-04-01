package com.jbricx.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.LineNumberChangeRulerColumn;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import annotation.AnnotationMarkerAccess;
import annotation.ColorCache;

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
import com.jbricx.actions.PreferencesAction;
import com.jbricx.actions.PrintAction;
import com.jbricx.actions.RedoAction;
import com.jbricx.actions.SaveAction;
import com.jbricx.actions.SaveAsAction;
import com.jbricx.actions.SelectAllAction;
import com.jbricx.actions.UndoAction;
import com.jbricx.filters.FolderFilter;
import com.jbricx.pjo.ActionControlClass;
import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.pjo.JBrickEditor;
import com.jbricx.preferences.TextPreferencePage;
import com.jbricx.treeProviders.FileTreeContentProvider;
import com.jbricx.treeProviders.FileTreeLabelProvider;

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
	private RedoAction redoAction = new RedoAction();
	private SaveAction saveAction = new SaveAction();
	private SaveAsAction saveAsAction = new SaveAsAction();
	private UndoAction undoAction = new UndoAction();
	private DownloadAction downloadAction = new DownloadAction();
	private DirectControlAction directControlAction = new DirectControlAction();
	private JoyStickAction joystickAction = new JoyStickAction();
	private FindBrickAction findBrickAction = new FindBrickAction();
	private CompileAction compileAction = new CompileAction();
	private MethodTemplateAction methodTemplateAction = new MethodTemplateAction();
	SourceViewerConfiguration configuration = new SourceViewerConfiguration();

	public Table table;
	// The font
	private Font font;

	public LineNumberChangeRulerColumn lnrc;

	/*
	 * // The undo manager private IUndoManager undoManager;
	 */
	// Right Click Menu
	private MenuManager menuManager;

	File treeRootFile;

	CTabFolder tabFolder;

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
	protected Control createContents(Composite parent) {
		long start = System.currentTimeMillis();
		System.out.println("start ");
		String workspacePath = getWorkspacePath();
		if (workspacePath == null)
			workspacePath = setWorkspacePath(parent);
		this.treeRootFile = new File(workspacePath);
		setStatus("Successfully Lauched!");

		// divide the main window
		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		sashForm.setLayout(new RowLayout());

		final Label l = new Label(parent, SWT.NONE);
		l.setToolTipText("helllllll oooooo Jaws:");
		l.setVisible(false);
		// //////// Left panel ////////////////
		// Create the tree viewer to display the file tree
		// Composite treePanel = new Composite(sashForm, SWT.BOTTOM);
		final TreeViewer tv = new TreeViewer(sashForm, SWT.LEFT);
		tv.getTree().setLayoutData(new GridData(GridData.BEGINNING));
		tv.setContentProvider(new FileTreeContentProvider(workspacePath));
		tv.setLabelProvider(new FileTreeLabelProvider());
		tv.addFilter(new FolderFilter());

		tv.setInput("root"); // pass a non-null that will be ignored

		tv.getTree().addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				IStructuredSelection selection = (IStructuredSelection) tv
						.getSelection();
				File file = (File) selection.getFirstElement();

				if (!file.isDirectory()) {
					JBrickTabItem tabItem = new JBrickTabItem(tabFolder,
							SWT.CLOSE, file);
					tabFolder.setSelection(tabItem);
				}
			}
		});

		// /////////////////////// right panel //////////////////
		// parent panel containing both the editing area and debugging area
		SashForm rightPanel = new SashForm(sashForm, SWT.VERTICAL);

		// Composite rightPanel = new Composite(sashForm, SWT.NONE);
		GridLayout fLayout = new GridLayout();

		/*
		 * GridLayout gridLayout = new GridLayout(); gridLayout.numColumns = 1;
		 */
		rightPanel.setLayout(fLayout);

		// ******** top part of the right panel **********************
		// Create the viewer
		CompositeRuler ruler = new CompositeRuler(10);

		/*
		 * LineNumberRulerColumn lnrc = new LineNumberRulerColumn();
		 * lnrc.setForeground(new Color(parent.getShell().getDisplay(), new RGB(
		 * 255, 0, 0)));
		 */
		lnrc = new LineNumberChangeRulerColumn(new ColorCache());
		lnrc.setForeground(new Color(parent.getShell().getDisplay(), new RGB(
				255, 0, 0)));
		// lnrc.getLineOfLastMouseButtonActivity();

		// lnrc.getControl().getAccessible().textSelectionChanged()
		ruler.addDecorator(0, lnrc);

		tabFolder = new CTabFolder(rightPanel, SWT.TOP);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		tabFolder.setMinimizeVisible(true);
		tabFolder.setMaximizeVisible(true);
		tabFolder.setSimple(false);
		tabFolder.setUnselectedImageVisible(false);
		tabFolder.setUnselectedCloseVisible(false);
		Color titleForeColor = parent.getShell().getDisplay().getSystemColor(
				SWT.COLOR_TITLE_FOREGROUND);
		Color titleBackColor1 = parent.getShell().getDisplay().getSystemColor(
				SWT.COLOR_TITLE_BACKGROUND);
		Color titleBackColor2 = parent.getShell().getDisplay().getSystemColor(
				SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
		tabFolder.setSelectionForeground(titleForeColor);
		tabFolder.setSelectionBackground(new Color[] { titleBackColor1,
				titleBackColor2 }, new int[] { 100 }, true);
		// TODO: change tabs names and content
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
				openFile(file);
				openedfile = true;
			}

		}

		if (!openedfile) {
			JBrickTabItem tabItem = new JBrickTabItem(tabFolder, SWT.CLOSE,
					null);
			tabFolder.setSelection(tabItem);
		}

		// Add close event for tab close
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			public void close(CTabFolderEvent event) {
				if (checkOverwrite()) {
				} else {
					event.doit = false;
				}
			}
		});


		// ///////////////////////////////////////////////////////////////

		// rulers
		AnnotationModel fAnnotationModel = new AnnotationModel();
		IAnnotationAccess fAnnotationAccess = new AnnotationMarkerAccess();

		ColorCache cc = new ColorCache();
		CompositeRuler fCompositeRuler = new CompositeRuler();
		OverviewRuler fOverviewRuler = new OverviewRuler(fAnnotationAccess, 12,
				cc);
		AnnotationRulerColumn annotationRuler = new AnnotationRulerColumn(
				fAnnotationModel, 16, fAnnotationAccess);
		fCompositeRuler.setModel(fAnnotationModel);
		fOverviewRuler.setModel(fAnnotationModel);

		// annotation ruler is decorating our composite ruler
		fCompositeRuler.addDecorator(0, annotationRuler);
		// ///////////////////////////////////////////////////////////////

		// ******** bottom part of the right panel **********************

		table = new Table(rightPanel, SWT.BORDER);
		table.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				IDocument document = getCurrentTabItem().getDocument();
				/*
				 * String txt =
				 * getCurrentTabItem().getViewer().getTextWidget().getText();
				 * System.out.println("txt"+ txt);
				 * getCurrentTabItem().getViewer().setSelectedRange(12, 2);
				 * System.out.println("Item Text is:  " + ((TableItem)e.item
				 * ).getText());
				 */
				try {

					String errorMessageText = ((TableItem) e.item).getText();
					System.out.println("Item Text is:  " + errorMessageText);
					String strLineNumber = errorMessageText.substring(
							errorMessageText.indexOf("Line:") + 5,
							errorMessageText.indexOf("Error"));

					int errorLineNumber = Integer
							.parseInt(strLineNumber.trim()) - 1;

					int offset = document.getLineOffset(errorLineNumber);
					int lineLength = document.getLineLength(errorLineNumber);
					getCurrentTabItem().getViewer().setSelectedRange(offset,
							lineLength);
					setStatus(" status bar Line " + strLineNumber);

					// System.out.println("Info is: "+configuration.getInformationPresenter(getCurrentTabItem().getViewer())
					// );
					System.out.println("Info is: "
							+ getCurrentTabItem().getViewer().getTextWidget()
									.getSelection());

					/*
					 * if(lnrc != null){
					 * System.out.println("mouse thing -- "+lnrc
					 * .getLineOfLastMouseButtonActivity()); } else{
					 * System.out.println("it is null"); }
					 */

					getStatusLineManager().getControl().setFocus();

				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				l.setFocus();

			}

		});
		rightPanel.setWeights(new int[] { 80, 20 });
		sashForm.setWeights(new int[] { 20, 80 });

		getMenuBarManager().updateAll(true);

		long end = System.currentTimeMillis();
		start = end - start;
		System.out.println("it took : " + start);
		return parent;

	}

	public void TabItemClosed(CTabFolderEvent event) {
		if (event.item.equals(this)) {
			event.doit = false;
		}
	}

	protected void loadPreferences(JBrickTabItem tabItem) {
		IPreferenceStore ps = JBrickEditor.getApp().getPreferences();

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
		 */}

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

		tm.add(new Separator());
		tm.add(directControlAction);
		tm.add(joystickAction);

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
		if (checkOverwrite()) {
			close = super.close();
			if (close) {
				if (font != null)
					font.dispose();

				String recentfiles = "";
				for (CTabItem t : tabFolder.getItems()) {
					JBrickTabItem i = (JBrickTabItem) t;

					recentfiles += i.getDocument().getFileName() + ";";
				}
				PreferenceManager mgr = new PreferenceManager();
				mgr.addToRoot(new PreferenceNode("text", "Text", null,
						TextPreferencePage.class.getName()));
				PreferenceStore ps = JBrickEditor.getApp().getPreferences();
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

	public ArrayList<String> getRecentFiles(Composite parent) {
		// Get the preference store
		PreferenceManager mgr = new PreferenceManager();
		mgr.addToRoot(new PreferenceNode("text", "Text", null,
				TextPreferencePage.class.getName()));
		PreferenceStore ps = JBrickEditor.getApp().getPreferences();
		Boolean loadrecent = ps
				.getBoolean(FileExtensionConstants.BOOLRECENTFILES);

		ArrayList<String> recentfiles = new ArrayList<String>();
		if (loadrecent) {
			for (String s : ps.getString(FileExtensionConstants.RECENTFILES)
					.split(";")) {
				recentfiles.add(s);
			}
		}

		return recentfiles;
	}

	// Going to modify this to request preferences
	public String setWorkspacePath(Composite parent) {
		String workspace = null;
		String path;
		PreferenceStore ps = JBrickEditor.getApp().getPreferences();
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
		PreferenceStore ps = JBrickEditor.getApp().getPreferences();
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

	public void addFolderInNewTab(String path) {

	}

	public void openFile(String fileName) {
		JBrickTabItem newTabItem = new JBrickTabItem(tabFolder, SWT.CLOSE,
				new File(fileName));
		tabFolder.setSelection(newTabItem);
	}

	public void openNewFile() {
		System.out.println("opening new file");
		JBrickTabItem newTabItem = new JBrickTabItem(tabFolder, SWT.CLOSE, null);
		tabFolder.setSelection(newTabItem);
	}

	public CTabFolder getTabFolder() {
		return tabFolder;
	}

	public JBrickTabItem getCurrentTabItem() {
		CTabItem currentTabItem;
		int currentIndex = getCurrentTabIndex();
		if (0 <= currentIndex) {
			currentTabItem = tabFolder.getItem(currentIndex);
			return (JBrickTabItem) currentTabItem;
		} else {
			return null;
		}
	}
	public int getCurrentTabIndex() {
		int currentIndex = tabFolder.getSelectionIndex();
		if (0 <= currentIndex) {
			return currentIndex;
		} else {
			return -1;
		}
	}
		
	public void refreshCurrentTabItem() {
		int selectedIndex = getCurrentTabIndex() ;
		CTabItem tabItems[] = tabFolder.getItems();		
		for(CTabItem tbItem : tabItems) {
			if (tbItem != null) {
				JBrickTabItem tabItem = (JBrickTabItem)tbItem ; 
				String currentString = tabItem.getViewer().getTextWidget().getText() ;
				String currentSaveString = tabItem.getDocument().getFileName() ;			
				JBrickEditor.removeObserver(tabItem);
				tabItem.dispose() ;
				if (currentSaveString != null){
					openFile(currentSaveString) ;
				}
				else{
					openNewFile() ;
				}
				tabItem = getCurrentTabItem() ;
				tabItem.getViewer().getTextWidget().setText(currentString) ;
			}
		}
		if (0 <= selectedIndex){
			tabFolder.setSelection(selectedIndex);
		}
	}
	
	public void setTabFolder(CTabFolder tabFolder) {
		this.tabFolder = tabFolder;
	}

	/**
	 * Checks the current file for unsaved changes. If it has unsaved changes,
	 * confirms that user wants to overwrite
	 * 
	 * @return boolean
	 */
	public boolean checkOverwrite() {
		boolean proceed = true;

		for (CTabItem tab : tabFolder.getItems()) {
			JBrickTabItem tabItem = (JBrickTabItem) tab;
			if (tabItem.getDocument().isDirty()) {
				proceed = MessageDialog
						.openConfirm(this.getShell(), "Are you sure?",
								"You have unsaved changes--are you sure you want to lose them?");
			}
		}
		return proceed;
	}

}
