package ui;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import pjo.FileExtensionConstants;
import pjo.JBrickEditor;
import treeProviders.FileTreeContentProvider;
import treeProviders.FileTreeLabelProvider;
import actions.AboutAction;
import actions.CompileAction;
import actions.CopyAction;
import actions.CutAction;
import actions.ExitAction;
import actions.FindAction;
import actions.NewAction;
import actions.OpenAction;
import actions.PasteAction;
import actions.PreferencesAction;
import actions.PrintAction;
import actions.RedoAction;
import actions.SaveAction;
import actions.SaveAsAction;
import actions.UndoAction;
import filters.FolderFilter;

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
	private CopyAction copyAction = new CopyAction();
	private CutAction cutAction = new CutAction();
	private ExitAction exitAction = new ExitAction();
	private FindAction findAction = new FindAction();
	private NewAction newAction = new NewAction();
	private OpenAction openAction = new OpenAction();
	private PasteAction pasteAction = new PasteAction();
	private PreferencesAction prefsAction = new PreferencesAction();
	private PrintAction printAction = new PrintAction();
	private RedoAction redoAction = new RedoAction();
	private SaveAction saveAction = new SaveAction();
	private SaveAsAction saveAsAction = new SaveAsAction();
	private UndoAction undoAction = new UndoAction();

	private CompileAction compileAction = new CompileAction();

	// The font
	private Font font;

	/*
	 * // The undo manager private IUndoManager undoManager;
	 */
	// Right Click Menu
	private MenuManager menuManager;

	File treeRootFile;

	CTabFolder tabFolder;

/*	ArrayList<SourceViewer> viewersList;
*/
	/**
	 * MainWindow constructor
	 */
	public MainWindow() {
		super(null);
		addMenuBar();
		addToolBar(SWT.FLAT);
		addStatusLine();

/*		viewersList = new ArrayList<SourceViewer>();
*/
	}

	/**
	 * Runs the application
	 */
	public void run() {

		setBlockOnOpen(true);
		open();
		System.out.println("7");
		Display.getCurrent().dispose();
		System.out.println("8");
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
		String workspacePath = getWorkspacePath(parent);
		this.treeRootFile = new File(workspacePath);
		setStatus("cool status line");
		// create file tree viewer
		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);

		System.out.println("1");
		// Create the tree viewer to display the file tree
		final TreeViewer tv = new TreeViewer(sashForm, SWT.LEFT);
		tv.getTree().setLayoutData(new GridData(GridData.BEGINNING));
		tv.setContentProvider(new FileTreeContentProvider(workspacePath));
		tv.setLabelProvider(new FileTreeLabelProvider());
		tv.addFilter(new FolderFilter());

		//tv.addFilter(new FileExtensionFilter());
		tv.setInput("root"); // pass a non-null that will be ignored

		tv.getTree().addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				String string = "";
				String parentTxt = "";
				String rootName =  treeRootFile.getName();
				//tv.getTree().getse
		
				
				IStructuredSelection selection = (IStructuredSelection)tv.getSelection();
		        File file = (File)selection.getFirstElement();
		        JBrickTabItem tabItem = new JBrickTabItem(tabFolder, SWT.CLOSE, file);
		        tabFolder.setSelection(tabItem);
		        /*
				//TreeItem[] selection = tv.getTree().getSelection();
				for (int i = 0; i < selection.length; i++) {
					
					//while()
					//string = selection[i]. getText();
					parentTxt = selection[i].getParentItem().getText();
					
					System.out.println(parentTxt);
					new JBrickTabItem(tabFolder, SWT.NULL, string);
				}
*/
				System.out.println("DefaultSelection={" + file.getAbsolutePath() + "}");
			}
		});
		System.out.println("2");

		// Create the viewer
		CompositeRuler ruler = new CompositeRuler(10);

		LineNumberRulerColumn lnrc = new LineNumberRulerColumn();
		lnrc.setForeground(new Color(parent.getShell().getDisplay(), new RGB(
				255, 0, 0)));
		ruler.addDecorator(0, lnrc);
		System.out.println("3");
		tabFolder = new CTabFolder(sashForm, SWT.RIGHT);
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
		System.out.println("4");
		// TODO: change tabs names and content
		// tab1
		JBrickTabItem tabItem = new JBrickTabItem(tabFolder, SWT.CLOSE, null);
		tabFolder.setSelection(tabItem);

		// Add close event for tab close
		tabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
		      public void close(CTabFolderEvent event) {
				if (checkOverwrite()) {
				}
				else{
			          event.doit = false;
				}
		      }
		});

		
		
		return parent;
	
	}
    public void TabItemClosed(CTabFolderEvent event) {
        if (event.item.equals(this)) {
          event.doit = false;
        }
    }

	
	protected void loadPreferences(JBrickTabItem tabItem) {
		IPreferenceStore ps = JBrickEditor.getApp().getPreferences();
		// setWrap(ps.getBoolean(FileExtensionConstants.WRAP));

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
		if (FileExtensionConstants.FONT.equals(event.getProperty()))
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
		MenuManager helpMenu = new MenuManager("&Help");

		mm.add(fileMenu);
		mm.add(editMenu);
		mm.add(compileMenu);
		mm.add(helpMenu);

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
		editMenu.add(new Separator());
		editMenu.add(prefsAction);

		compileMenu.add(compileAction);

		helpMenu.add(aboutAction);

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
		tm.add(aboutAction);

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

	public String getWorkspacePath(Composite parent) {
		String path;
		do {
			DirectoryDialog dialog = new DirectoryDialog(parent.getShell());
			dialog.setText("Workspace Selection");
			path = dialog.open();
		} while (path == null);
		return path;
	}

	public void addFolderInNewTab(String path) {

	}

	public void openFile(String fileName) {
		JBrickTabItem newTabItem = new JBrickTabItem(tabFolder, SWT.CLOSE,
				new File(fileName));
	}

	public void openNewFile() {
		JBrickTabItem newTabItem = new JBrickTabItem(tabFolder, SWT.CLOSE, null);
		tabFolder.setSelection(newTabItem);
	}

	public CTabFolder getTabFolder() {
		return tabFolder;
	}

	public JBrickTabItem getCurrentTabItem() {
		CTabItem currentTabItem;
		int currentIndex = tabFolder.getSelectionIndex();
		currentTabItem = tabFolder.getItem(currentIndex);
		return (JBrickTabItem) currentTabItem;
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
