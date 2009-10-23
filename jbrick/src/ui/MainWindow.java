package ui;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.DefaultUndoManager;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.VerticalRuler;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import pjo.FileExtensionConstants;
import pjo.JBrickEditor;
import source.JBrickEditorSourceViewerConfiguration;
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

/**
 * This class provides the main window of JBrickEditor
 */
public class MainWindow extends ApplicationWindow implements
		IPropertyChangeListener {
	// The viewer
	private SourceViewer viewer;

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

	// The undo manager
	private IUndoManager undoManager;

	// Right Click Menu
	private MenuManager menuManager;

	/**
	 * MainWindow constructor
	 */
	public MainWindow() {
		super(null);
		addMenuBar();
		addToolBar(SWT.FLAT);
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
		// Create the viewer
		CompositeRuler ruler = new CompositeRuler();
		LineNumberRulerColumn lnrc = new LineNumberRulerColumn();
        ruler.addDecorator(0,lnrc);
        
		viewer = new SourceViewer(parent, ruler , SWT.V_SCROLL
				| SWT.H_SCROLL);

		// Configure it and set the document
		viewer.configure(new JBrickEditorSourceViewerConfiguration());
		viewer.setDocument(JBrickEditor.getApp().getDocument());

		// Menu manager initialize
		menuManager = new MenuManager();
		menuManager.add(cutAction);
		menuManager.add(cutAction);
		menuManager.add(cutAction);
		menuManager.add(cutAction);
		Menu menu = menuManager.createContextMenu(this.viewer.getTextWidget());
		// Right Click Attach
		this.viewer.getTextWidget().setMenu(menu);

		// Set any preferences
		loadPreferences();

		// Create the undo manager
		undoManager = new DefaultUndoManager(100);
		undoManager.connect(viewer);

		viewer.getTextWidget().setFocus();

		// Return the StyledText
		return viewer.getTextWidget();
	}

	protected void loadPreferences() {
		IPreferenceStore ps = JBrickEditor.getApp().getPreferences();
		setWrap(ps.getBoolean(FileExtensionConstants.WRAP));

		String fontProp = ps.getString(FileExtensionConstants.FONT);
		if (fontProp.length() > 0) {
			FontData[] fd = new FontData[1];
			fd[0] = new FontData(fontProp);
			setFont(fd);
		}
	}

	/**
	 * Updates the view with the preferences
	 * 
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (FileExtensionConstants.WRAP.equals(event.getProperty()))
			setWrap(((Boolean) event.getNewValue()).booleanValue());
		else if (FileExtensionConstants.FONT.equals(event.getProperty()))
			setFont((FontData[]) event.getNewValue());
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
	public ITextViewer getViewer() {
		return viewer;
	}

	/**
	 * Sets the font
	 * 
	 * @param fontData
	 */
	public void setFont(FontData[] fontData) {
		// Create the font
		Font temp = new Font(getShell().getDisplay(), fontData);

		// If creation succeeded, dispose the old font
		if (font != null)
			font.dispose();

		// Use the new font
		font = temp;
		viewer.getTextWidget().setFont(font);
	}

	/**
	 * Turns on/off word wrap
	 * 
	 * @param wrap
	 *            true to wrap
	 */
	public void setWrap(boolean wrap) {
		viewer.getTextWidget().setWordWrap(wrap);
	}

	/**
	 * Gets the undo manager
	 * 
	 * @return IUndoManager
	 */
	public IUndoManager getUndoManager() {
		return undoManager;
	}

	/**
	 * Closes the main window
	 */
	public boolean close() {
		boolean close = false;
		if (JBrickEditor.getApp().checkOverwrite()) {
			close = super.close();
			if (close) {
				if (font != null)
					font.dispose();
			}
		}
		return close;
	}
}
