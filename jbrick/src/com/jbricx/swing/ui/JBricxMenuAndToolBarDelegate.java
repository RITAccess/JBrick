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
import com.jbricx.swing.actions.ExitAction;
import com.jbricx.swing.actions.PreferencesAction;

public class JBricxMenuAndToolBarDelegate {
	
	JBricxManager manager;
	
	// TODO Alphabetize as you add more!!!
	private AboutAction aboutAction;
	private CompileAction compileAction;
	private CopyAction copyAction;
	private ExitAction exitAction;
	private PreferencesAction prefsAction;
	
//	private HelpContentAction helpContentAction;
//	private CutAction cutAction;
//	private SelectAllAction selectAllAction;
//	private FindAction findAction;
//	private GotoAction gotoAction;
//	private NewAction newAction;
//	private OpenAction openAction;
//	private PasteAction pasteAction;
//	private PrintAction printAction;
//	private PrintPreviewAction printPreviewAction;
//	private RedoAction redoAction;
//	private SaveAction saveAction;
//	private SaveAsAction saveAsAction;
//	private UndoAction undoAction;
//	private DownloadAction downloadAction;
//	private DirectControlAction directControlAction;
//	private JoyStickAction joystickAction;
//	private PianoAction pianoAction;
//	private FindBrickAction findBrickAction;
//	private MethodTemplateAction methodTemplateAction;
	
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
		exitAction = new ExitAction(manager);
		prefsAction = new PreferencesAction(manager);
		
//		cutAction = new CutAction(manager);
//		selectAllAction = new SelectAllAction(manager);	
//		helpAction = new HelpAction();
//		findAction = new FindAction(manager);
//		gotoAction = new GotoAction(manager);
//		newAction = new NewAction(manager);
//		penAction = new OpenAction(manager);
//		pasteAction = new PasteAction(manager);
//		printAction = new PrintAction(manager);
//		printPreviewAction = new PrintPreviewAction(manager);
//		redoAction = new RedoAction(manager);
//		saveAction = new SaveAction(manager);
//		saveAsAction = new SaveAsAction(manager);
//		undoAction = new UndoAction(manager);
//		downloadAction = new DownloadAction(manager);
//		methodTemplateAction = new MethodTemplateAction(manager);
//		findBrickAction = new FindBrickAction(manager);
//		joystickAction = new JoyStickAction(manager);
//		pianoAction = new PianoAction(manager);
//		directControlAction = new DirectControlAction(manager);
//		NXTManager nxtManager = NXTManager.getInstance();	
	}
	
	public JToolBar getToolBar(){
		JToolBar mainToolBar = new JToolBar();
		mainToolBar.setFloatable(false);

		// Copy Button
		JButton copyButton = new JButton();
		copyButton.setAction(copyAction);
		copyButton.setToolTipText("Copy");
		
		// Compile Button
		JButton compileButton = new JButton();
		compileButton.setAction(compileAction);
		compileButton.setToolTipText("Compile");
		
		// Preferences Button
		JButton preferencesButton = new JButton();
		preferencesButton.setAction(prefsAction);
		preferencesButton.setToolTipText("Preferences");
	
		mainToolBar.add(copyButton);
		mainToolBar.add(compileButton);
		mainToolBar.add(preferencesButton);
		
		return mainToolBar;		
	}
	
	public JMenuBar getMenuBar(){
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
	
	private void makeMainMenus(){
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		compileMenu = new JMenu("Compile");
		toolsMenu = new JMenu("Tools");
		viewMenu = new JMenu("View");
		helpMenu = new JMenu("Help");
	}
	
	private void makeSubMenus(){
		//File
		JMenuItem exit = new JMenuItem(exitAction);
		exit.setText("Quit");
		fileMenu.add(exit);
		
		//Edit
		JMenuItem copy = new JMenuItem(copyAction);
		copy.setText("Copy");
		editMenu.add(copy);
		
		JMenuItem prefs = new JMenuItem(prefsAction);
		prefs.setText("Preferences");
		editMenu.add(prefs);
		
		
		//Compile
		JMenuItem compile = new JMenuItem(compileAction);
		compile.setText("Compile");
		compileMenu.add(compile);
		
		//Tools
		
		
		//View
		
		
		//Help
		JMenuItem about = new JMenuItem(aboutAction);
		about.setText("About");
		
		
		helpMenu.add(about);
		
		
	}

}
