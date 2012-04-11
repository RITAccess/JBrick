package com.jbricx.swing.ui;

//import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;

import com.jbricx.swing.actions.AboutAction;
import com.jbricx.swing.actions.CompileAction;
import com.jbricx.swing.actions.CopyAction;
import com.jbricx.swing.actions.CutAction;
import com.jbricx.swing.actions.DirectControlAction;
import com.jbricx.swing.actions.DownloadAction;
import com.jbricx.swing.actions.ExitAction;
import com.jbricx.swing.actions.FindAction;
import com.jbricx.swing.actions.FindBrickAction;
import com.jbricx.swing.actions.GotoAction;
import com.jbricx.swing.actions.HelpContentAction;
import com.jbricx.swing.actions.MaxEditorAction;
import com.jbricx.swing.actions.MaxStatusAction;
import com.jbricx.swing.actions.MaxViewerAction;
import com.jbricx.swing.actions.NewAction;
import com.jbricx.swing.actions.OpenAction;
import com.jbricx.swing.actions.PasteAction;
import com.jbricx.swing.actions.PianoAction;
import com.jbricx.swing.actions.PreferencesAction;
import com.jbricx.swing.actions.PrintAction;
import com.jbricx.swing.actions.PrintPreviewAction;
import com.jbricx.swing.actions.RedoAction;
import com.jbricx.swing.actions.ResetViewAction;
import com.jbricx.swing.actions.SaveAction;
import com.jbricx.swing.actions.SaveAsAction;
import com.jbricx.swing.actions.SelectAllAction;
import com.jbricx.swing.actions.ShowHideFileViewerAction;
import com.jbricx.swing.actions.UndoAction;

public class JBricxMenuAndToolBarDelegate {

	JBricxManager manager;

	// TODO Alphabetize as you add more!!!
	private AboutAction aboutAction;
	private CompileAction compileAction;
	private CopyAction copyAction;
	private CutAction cutAction;
	private DirectControlAction directControlAction;
	private DownloadAction downloadAction;
	private ExitAction exitAction;
	private FindAction findAction;
	private FindBrickAction findBrickAction;
	private GotoAction gotoAction;
	private HelpContentAction helpContentAction;
	private MaxEditorAction maxEditorAction;
	private MaxStatusAction maxStatusAction;
	private MaxViewerAction maxViewerAction;
	private NewAction newAction;
	private OpenAction openAction;
	private PasteAction pasteAction;
	private PianoAction pianoAction;
	private PreferencesAction prefsAction;
	private PrintAction printAction;
	private PrintPreviewAction printPreviewAction;
	private RedoAction redoAction;
	private ResetViewAction resetViewAction;
	private SaveAction saveAction;
	private SaveAsAction saveAsAction;
	private SelectAllAction selectAllAction;
	private ShowHideFileViewerAction showHideFileViewerAction;
	private UndoAction undoAction;

	// private JoyStickAction joystickAction;
	// private MethodTemplateAction methodTemplateAction;

	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu compileMenu;
	private JMenu toolsMenu;
	private JMenu viewMenu;
	private JMenu helpMenu;

	public JBricxMenuAndToolBarDelegate(JBricxManager manager) {
		this.manager = manager;

		// TODO Alphabetize as you add more!!!
		aboutAction = new AboutAction(manager);
		compileAction = new CompileAction(manager);
		copyAction = new CopyAction(manager);
		cutAction = new CutAction(manager);
		directControlAction = new DirectControlAction(manager);
		downloadAction = new DownloadAction(manager);
		exitAction = new ExitAction(manager);
		findAction = new FindAction(manager);
		findBrickAction = new FindBrickAction(manager);
		gotoAction = new GotoAction(manager);
		helpContentAction = new HelpContentAction(manager);
		maxEditorAction = new MaxEditorAction(manager);
		maxStatusAction = new MaxStatusAction(manager);
		maxViewerAction = new MaxViewerAction(manager);
		newAction = new NewAction(manager);
		openAction = new OpenAction(manager);
		pasteAction = new PasteAction(manager);
		pianoAction = new PianoAction(manager);
		prefsAction = new PreferencesAction(manager);
		printAction = new PrintAction(manager);
		printPreviewAction = new PrintPreviewAction(manager);
		redoAction = new RedoAction(manager);
		resetViewAction = new ResetViewAction(manager);
		saveAction = new SaveAction(manager);
		saveAsAction = new SaveAsAction(manager);
		selectAllAction = new SelectAllAction(manager);
		showHideFileViewerAction = new ShowHideFileViewerAction(manager);
		undoAction = new UndoAction(manager);
 
		// methodTemplateAction = new MethodTemplateAction(manager);
		// joystickAction = new JoyStickAction(manager);
		// NXTManager nxtManager = NXTManager.getInstance();
	}

	public JToolBar getToolBar() {
		JToolBar mainToolBar = new JToolBar();
		mainToolBar.setFloatable(false);

		// Cut Button
		JButton cutButton = new JButton();
		cutButton.getAccessibleContext().setAccessibleName("Cut");
		cutButton.getAccessibleContext().setAccessibleDescription("Cut text");
		cutButton.setAction(cutAction);
		cutButton.setToolTipText("Cut");

		// Copy Button
		JButton copyButton = new JButton();
		copyButton.getAccessibleContext().setAccessibleName("Copy");
		copyButton.getAccessibleContext().setAccessibleDescription("Copy text");
		copyButton.setAction(copyAction);
		copyButton.setToolTipText("Copy");

		// find Button
		JButton findButton = new JButton();
		findButton.getAccessibleContext().setAccessibleName("Find");
		findButton.getAccessibleContext().setAccessibleDescription("Find text");
		findButton.setAction(findAction);
		findButton.setToolTipText("Find and Replace");

		// Compile Button
		JButton compileButton = new JButton();
		compileButton.getAccessibleContext().setAccessibleName("Compile");
		compileButton.getAccessibleContext().setAccessibleDescription("Compile program");
		compileButton.setAction(compileAction);
		compileButton.setToolTipText("Compile");

		// Download Button
		JButton dlButton = new JButton();
		dlButton.getAccessibleContext().setAccessibleName("Download");
		dlButton.getAccessibleContext().setAccessibleDescription("Download program on to brick");
		dlButton.setAction(downloadAction);
		dlButton.setToolTipText("Download");

		// Preferences Button
		JButton preferencesButton = new JButton();
		preferencesButton.getAccessibleContext().setAccessibleName("Preferences");
		preferencesButton.getAccessibleContext().setAccessibleDescription("Preferences window");
		preferencesButton.setAction(prefsAction);
		preferencesButton.setToolTipText("Preferences");
		
		// GoTo Button
		JButton gotoButton = new JButton();
		gotoButton.getAccessibleContext().setAccessibleName("Go to");
		gotoButton.getAccessibleContext().setAccessibleDescription("Go to a specific location");
		gotoButton.setAction(gotoAction);
		gotoButton.setToolTipText("GoTo");
		
		// Help Content Button
		JButton helpContentButton = new JButton();
		helpContentButton.getAccessibleContext().setAccessibleName("Help Content");
		helpContentButton.getAccessibleContext().setAccessibleDescription("Opens help content");
		helpContentButton.setAction(helpContentAction);
		helpContentButton.setToolTipText("Help Content");
		
		// New Button
		JButton newButton = new JButton();
		newButton.getAccessibleContext().setAccessibleName("New File");
		newButton.getAccessibleContext().setAccessibleDescription("Opens a new file");
		newButton.setAction(newAction);
		newButton.setToolTipText("New");
		
		// Open Button
		JButton openButton = new JButton();
		openButton.getAccessibleContext().setAccessibleName("Open file");
		openButton.getAccessibleContext().setAccessibleDescription("Opens the chosen file");
		openButton.setAction(openAction);
		openButton.setToolTipText("Open");
		
		// Paste Button
		JButton pasteButton = new JButton();
		pasteButton.getAccessibleContext().setAccessibleName("Paste");
		pasteButton.getAccessibleContext().setAccessibleDescription("Paste text");
		pasteButton.setAction(pasteAction);
		pasteButton.setToolTipText("Paste");
		
		// Print Button
		JButton printButton = new JButton();
		printButton.getAccessibleContext().setAccessibleName("Print");
		printButton.getAccessibleContext().setAccessibleDescription("Print file");
		printButton.setAction(printAction);
		printButton.setToolTipText("Print");
		
		// redo Button
		JButton redoButton = new JButton();
		redoButton.getAccessibleContext().setAccessibleName("Redo");
		redoButton.getAccessibleContext().setAccessibleDescription("Redo");
		redoButton.setAction(redoAction);
		redoButton.setToolTipText("Redo");
		
		// Save Button
		JButton saveButton = new JButton();
		saveButton.getAccessibleContext().setAccessibleName("Save");
		saveButton.getAccessibleContext().setAccessibleDescription("Save");
		saveButton.setAction(saveAction);
		saveButton.setToolTipText("Save");
		
		// Save As Button
		JButton saveAsButton = new JButton();
		saveAsButton.getAccessibleContext().setAccessibleName("Save As");
		saveAsButton.getAccessibleContext().setAccessibleDescription("Save As");
		saveAsButton.setAction(saveAsAction);
		saveAsButton.setToolTipText("Save As");
		
		// Undo Button
		JButton undoButton = new JButton();
		undoButton.getAccessibleContext().setAccessibleName("Undo");
		undoButton.getAccessibleContext().setAccessibleDescription("Undo");
		undoButton.setAction(undoAction);
		undoButton.setToolTipText("Undo");

		// Add all the buttons to the tool bar
		mainToolBar.add(newButton);
		mainToolBar.add(openButton);
		mainToolBar.add(saveButton);
		mainToolBar.add(saveAsButton);
		mainToolBar.add(undoButton);
		mainToolBar.add(redoButton);		
		mainToolBar.add(cutButton);
		mainToolBar.add(copyButton);
		mainToolBar.add(pasteButton);
		mainToolBar.add(findButton);
		mainToolBar.add(gotoButton);
		mainToolBar.add(printButton);
		mainToolBar.add(compileButton);
		mainToolBar.add(dlButton);
		mainToolBar.add(preferencesButton);
		mainToolBar.add(helpContentButton);

		return mainToolBar;
	}

	public JMenuBar getMenuBar() {
		makeMainMenus();
		makeSubMenus();

		JMenuBar mainMenuBar = new JMenuBar();
		mainMenuBar.requestFocusInWindow();
		mainMenuBar.add(fileMenu);
		mainMenuBar.add(editMenu);
		mainMenuBar.add(compileMenu);
		mainMenuBar.add(toolsMenu);
		mainMenuBar.add(viewMenu);
		mainMenuBar.add(helpMenu);
		return mainMenuBar;
	}

	private void makeMainMenus() {
		fileMenu = new JMenu("File");
		fileMenu.getAccessibleContext().setAccessibleName("File");
		fileMenu.getAccessibleContext().setAccessibleDescription("File");
		
		editMenu = new JMenu("Edit");
		compileMenu = new JMenu("Compile");
		toolsMenu = new JMenu("Tools");
		viewMenu = new JMenu("View");
		helpMenu = new JMenu("Help");
	}

	private void makeSubMenus() {
		// File	
		JMenuItem newDoc = new JMenuItem(newAction);
		newDoc.getAccessibleContext().setAccessibleName("New Document");
		newDoc.getAccessibleContext().setAccessibleDescription("Open a new document");
		newDoc.setText("New");
		fileMenu.add(newDoc);
		
		JMenuItem open = new JMenuItem(openAction);
		open.setText("Open");
		fileMenu.add(open);

		JMenuItem save = new JMenuItem(saveAction);
		save.setText("Save");
		fileMenu.add(save);
		
		JMenuItem saveAs = new JMenuItem(saveAsAction);
		saveAs.setText("Save As");
		fileMenu.add(saveAs);
		
		JMenuItem printPreview = new JMenuItem(printPreviewAction);
		printPreview.setText("Print Preview");
		fileMenu.add(printPreview);
		
		JMenuItem print = new JMenuItem(printAction);
		print.setText("Print");
		fileMenu.add(print);
		
		JMenuItem exit = new JMenuItem(exitAction);
		exit.setText("Quit");
		exit.getAccessibleContext().setAccessibleName("Quit");
		exit.getAccessibleContext().setAccessibleDescription("Exit the program");
		fileMenu.add(exit);

		// Edit

		JMenuItem undo = new JMenuItem(undoAction);
		undo.setText("Undo");
		editMenu.add(undo);

		JMenuItem redo = new JMenuItem(redoAction);
		redo.setText("Redo");
		editMenu.add(redo);
		
		JMenuItem cut = new JMenuItem(cutAction);
		cut.setText("Cut");
		editMenu.add(cut);

		JMenuItem copy = new JMenuItem(copyAction);
		copy.setText("Copy");
		editMenu.add(copy);
		
		JMenuItem paste = new JMenuItem(pasteAction);
		paste.setText("Paste");
		editMenu.add(paste);

		JMenuItem selectAll = new JMenuItem(selectAllAction);
		selectAll.setText("Select All");
		editMenu.add(selectAll);
				
		JMenuItem find = new JMenuItem(findAction);
		find.setText("Find and Replace");
		editMenu.add(find);

		JMenuItem prefs = new JMenuItem(prefsAction);
		prefs.setText("Preferences");
		editMenu.add(prefs);	

		// Compile
		JMenuItem compile = new JMenuItem(compileAction);
		compile.setText("Compile");
		compileMenu.add(compile);

		JMenuItem dl = new JMenuItem(downloadAction);
		dl.setText("Download");
		compileMenu.add(dl);

		// Tools
		JMenuItem gt = new JMenuItem(gotoAction);
		gt.setText("GoTo");
		toolsMenu.add(gt);
		
		JMenuItem piano = new JMenuItem(pianoAction);
		piano.setText("Piano");
		toolsMenu.add(piano);
		
		JMenuItem directControl = new JMenuItem(directControlAction);
		directControl.setText("Direct Control");
		toolsMenu.add(directControl);

		// View
		JMenuItem showHideViewer = new JMenuItem(showHideFileViewerAction);
		showHideViewer.setText("Show/Hide File Viewer");
		viewMenu.add(showHideViewer);
		
		JMenuItem maxViewer = new JMenuItem(maxViewerAction);
		maxViewer.setText("Maximize File Viewer");
		viewMenu.add(maxViewer);
		
		JMenuItem maxEditor = new JMenuItem(maxEditorAction);
		maxEditor.setText("Maximize File Editor");
		viewMenu.add(maxEditor);
		
		JMenuItem maxStatus = new JMenuItem(maxStatusAction);
		maxStatus.setText("Maximize Status");
		viewMenu.add(maxStatus);
		
		JMenuItem resetView = new JMenuItem(resetViewAction);
		resetView.setText("Reset View");
		viewMenu.add(resetView);

		// Help
		JMenuItem about = new JMenuItem(aboutAction);
		about.setText("About");
		helpMenu.add(about);
		
		JMenuItem help = new JMenuItem(helpContentAction);
		help.setText("Help Content");
		helpMenu.add(help);

	}

}
