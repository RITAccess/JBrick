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
import com.jbricx.swing.actions.GotoAction;
import com.jbricx.swing.actions.HelpContentAction;
import com.jbricx.swing.actions.NewAction;
import com.jbricx.swing.actions.OpenAction;
import com.jbricx.swing.actions.PasteAction;
import com.jbricx.swing.actions.PianoAction;
import com.jbricx.swing.actions.PreferencesAction;
import com.jbricx.swing.actions.PrintAction;
import com.jbricx.swing.actions.PrintPreviewAction;
import com.jbricx.swing.actions.RedoAction;
import com.jbricx.swing.actions.SaveAction;
import com.jbricx.swing.actions.SaveAsAction;
import com.jbricx.swing.actions.SelectAllAction;
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
	private GotoAction gotoAction;
	private HelpContentAction helpContentAction;
	private NewAction newAction;
	private OpenAction openAction;
	private PasteAction pasteAction;
	private PianoAction pianoAction;
	private PreferencesAction prefsAction;
	private PrintAction printAction;
	private PrintPreviewAction printPreviewAction;
	private RedoAction redoAction;
	private SaveAction saveAction;
	private SaveAsAction saveAsAction;
	private SelectAllAction selectAllAction;
	private UndoAction undoAction;

	// private JoyStickAction joystickAction;
	// private FindBrickAction findBrickAction;
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
		gotoAction = new GotoAction(manager);
		helpContentAction = new HelpContentAction(manager);
		newAction = new NewAction(manager);
		openAction = new OpenAction(manager);
		pasteAction = new PasteAction(manager);
		pianoAction = new PianoAction(manager);
		prefsAction = new PreferencesAction(manager);
		printAction = new PrintAction(manager);
		printPreviewAction = new PrintPreviewAction(manager);
		redoAction = new RedoAction(manager);
		saveAction = new SaveAction(manager);
		saveAsAction = new SaveAsAction(manager);
		selectAllAction = new SelectAllAction(manager);
		undoAction = new UndoAction(manager);
 
		// methodTemplateAction = new MethodTemplateAction(manager);
		// findBrickAction = new FindBrickAction(manager);
		// joystickAction = new JoyStickAction(manager);
		// NXTManager nxtManager = NXTManager.getInstance();
	}

	public JToolBar getToolBar() {
		JToolBar mainToolBar = new JToolBar();
		mainToolBar.setFloatable(false);

		// Cut Button
		JButton cutButton = new JButton();
		cutButton.setAction(cutAction);
		cutButton.setToolTipText("Cut");

		// Copy Button
		JButton copyButton = new JButton();
		copyButton.setAction(copyAction);
		copyButton.setToolTipText("Copy");

		// find Button
		JButton findButton = new JButton();
		findButton.setAction(findAction);
		findButton.setToolTipText("Find and Replace");

		// Compile Button
		JButton compileButton = new JButton();
		compileButton.setAction(compileAction);
		compileButton.setToolTipText("Compile");

		// Download Button
		JButton dlButton = new JButton();
		dlButton.setAction(downloadAction);
		dlButton.setToolTipText("Download");

		// Preferences Button
		JButton preferencesButton = new JButton();
		preferencesButton.setAction(prefsAction);
		preferencesButton.setToolTipText("Preferences");
		
		// GoTo Button
		JButton gotoButton = new JButton();
		gotoButton.setAction(gotoAction);
		gotoButton.setToolTipText("GoTo");
		
		// Help Content Button
		JButton helpContentButton = new JButton();
		helpContentButton.setAction(helpContentAction);
		helpContentButton.setToolTipText("Help Content");
		
		// New Button
		JButton newButton = new JButton();
		newButton.setAction(newAction);
		newButton.setToolTipText("New");
		
		// Open Button
		JButton openButton = new JButton();
		openButton.setAction(openAction);
		openButton.setToolTipText("Open");
		
		// Paste Button
		JButton pasteButton = new JButton();
		pasteButton.setAction(pasteAction);
		pasteButton.setToolTipText("Paste");
		
		// Print Button
		JButton printButton = new JButton();
		printButton.setAction(printAction);
		printButton.setToolTipText("Print");
		
		// redo Button
		JButton redoButton = new JButton();
		redoButton.setAction(redoAction);
		redoButton.setToolTipText("Redo");
		
		// Save Button
		JButton saveButton = new JButton();
		saveButton.setAction(saveAction);
		saveButton.setToolTipText("Save");
		
		// Save As Button
		JButton saveAsButton = new JButton();
		saveAsButton.setAction(saveAsAction);
		saveAsButton.setToolTipText("Save As");
		
		// Undo Button
		JButton undoButton = new JButton();
		undoButton.setAction(undoAction);
		undoButton.setToolTipText("Undo");


		// Add all the buttons to the tool bar
		mainToolBar.add(cutButton);
		mainToolBar.add(copyButton);
		mainToolBar.add(findButton);
		mainToolBar.add(compileButton);
		mainToolBar.add(dlButton);
		mainToolBar.add(preferencesButton);
		mainToolBar.add(gotoButton);
		mainToolBar.add(helpContentButton);
		mainToolBar.add(newButton);
		mainToolBar.add(openButton);
		mainToolBar.add(pasteButton);
		mainToolBar.add(printButton);
		mainToolBar.add(redoButton);
		mainToolBar.add(saveButton);
		mainToolBar.add(saveAsButton);
		mainToolBar.add(undoButton);

		return mainToolBar;
	}

	public JMenuBar getMenuBar() {
		makeMainMenus();
		makeSubMenus();

		JMenuBar mainMenuBar = new JMenuBar();
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
		editMenu = new JMenu("Edit");
		compileMenu = new JMenu("Compile");
		toolsMenu = new JMenu("Tools");
		viewMenu = new JMenu("View");
		helpMenu = new JMenu("Help");
	}

	private void makeSubMenus() {
		// File
		JMenuItem exit = new JMenuItem(exitAction);
		exit.setText("Quit");
		fileMenu.add(exit);
		
		JMenuItem newDoc = new JMenuItem(newAction);
		newDoc.setText("New");
		fileMenu.add(newDoc);
		
		JMenuItem open = new JMenuItem(openAction);
		open.setText("Open");
		fileMenu.add(open);
		
		JMenuItem print = new JMenuItem(printAction);
		print.setText("Print");
		fileMenu.add(print);
		
		JMenuItem printPreview = new JMenuItem(printPreviewAction);
		printPreview.setText("Print Preview");
		fileMenu.add(printPreview);
		
		JMenuItem save = new JMenuItem(saveAction);
		save.setText("Save");
		fileMenu.add(save);
		
		JMenuItem saveAs = new JMenuItem(saveAsAction);
		saveAs.setText("Save As");
		fileMenu.add(saveAs);

		// Edit
		JMenuItem cut = new JMenuItem(cutAction);
		cut.setText("Cut");
		editMenu.add(cut);

		JMenuItem copy = new JMenuItem(copyAction);
		copy.setText("Copy");
		editMenu.add(copy);
		
		JMenuItem paste = new JMenuItem(pasteAction);
		paste.setText("Paste");
		editMenu.add(paste);
		
		JMenuItem find = new JMenuItem(findAction);
		find.setText("Find and Replace");
		editMenu.add(find);

		JMenuItem prefs = new JMenuItem(prefsAction);
		prefs.setText("Preferences");
		editMenu.add(prefs);
		
		JMenuItem redo = new JMenuItem(redoAction);
		redo.setText("Redo");
		editMenu.add(redo);
		
		JMenuItem selectAll = new JMenuItem(selectAllAction);
		selectAll.setText("Select All");
		editMenu.add(selectAll);
		
		JMenuItem undo = new JMenuItem(undoAction);
		undo.setText("Undo");
		editMenu.add(undo);

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

		// Help
		JMenuItem about = new JMenuItem(aboutAction);
		about.setText("About");
		helpMenu.add(about);
		
		JMenuItem help = new JMenuItem(helpContentAction);
		help.setText("Help Content");
		helpMenu.add(help);

	}

}
