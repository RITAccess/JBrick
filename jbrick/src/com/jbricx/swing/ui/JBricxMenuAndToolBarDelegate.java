package com.jbricx.swing.ui;

import java.util.prefs.Preferences;

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
import com.jbricx.swing.actions.JoyStickAction;
import com.jbricx.swing.actions.MethodTemplateAction;
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
import com.jbricx.actions.HelpContentAction;
import com.jbricx.communications.NXTManager;

public class JBricxMenuAndToolBarDelegate {
	
	JBricxManager manager;
	private AboutAction aboutAction;
	private HelpContentAction helpContentAction;
	private CopyAction copyAction;
	private CutAction cutAction;
	private SelectAllAction selectAllAction;
	private ExitAction exitAction;
	private FindAction findAction;
	private GotoAction gotoAction;
	private NewAction newAction;
	private OpenAction openAction;
	private PasteAction pasteAction;
	private PreferencesAction prefsAction;
	private PrintAction printAction;
	private PrintPreviewAction printPreviewAction;
	private RedoAction redoAction;
	private SaveAction saveAction;
	private SaveAsAction saveAsAction;
	private UndoAction undoAction;
	private DownloadAction downloadAction;
	private DirectControlAction directControlAction;
	private JoyStickAction joystickAction;
	private PianoAction pianoAction;
	private FindBrickAction findBrickAction;
	private CompileAction compileAction;
	private MethodTemplateAction methodTemplateAction;
	
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu compileMenu;
	private JMenu toolsMenu;
	private JMenu viewMenu;
	private JMenu helpMenu;
	

	public JBricxMenuAndToolBarDelegate(JBricxManager manager) {
		this.manager = manager;

		aboutAction = new AboutAction(manager);
		/*
		copyAction = new CopyAction(manager);
		cutAction = new CutAction(manager);
		selectAllAction = new SelectAllAction(manager);
		pasteAction = new PasteAction(manager);
		
		exitAction = new ExitAction(manager);
		helpAction = new HelpAction();
		findAction = new FindAction(manager);
		gotoAction = new GotoAction(manager);
		newAction = new NewAction(manager);
		penAction = new OpenAction(manager);
		prefsAction = new PreferencesAction(manager);
		printAction = new PrintAction(manager);
		printPreviewAction = new PrintPreviewAction(manager);
		redoAction = new RedoAction(manager);
		saveAction = new SaveAction(manager);
		saveAsAction = new SaveAsAction(manager);
		undoAction = new UndoAction(manager);
		downloadAction = new DownloadAction(manager);
		compileAction = new CompileAction(manager);
		methodTemplateAction = new MethodTemplateAction(manager);
		findBrickAction = new FindBrickAction(manager);
		joystickAction = new JoyStickAction(manager);
		pianoAction = new PianoAction(manager);
		directControlAction = new DirectControlAction(manager);
		NXTManager nxtManager = NXTManager.getInstance();
		*/
	}
	
	public JToolBar getToolBar(){
		JToolBar mainToolBar = new JToolBar();
		mainToolBar.setFloatable(false);
		
		JButton aboutButton = new JButton();
		aboutButton.setAction(aboutAction);
		aboutButton.setToolTipText("About");
		
		mainToolBar.add(aboutButton);
		
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
		
		
		//Edit
		
		
		//Compile
		
		
		//Tools
		
		
		//View
		
		
		//Help
		JMenuItem about = new JMenuItem(aboutAction);
		about.setText("About");
		
		
		helpMenu.add(about);
		
		
	}

}
