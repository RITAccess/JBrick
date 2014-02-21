package com.jbricx.swing.ui;

//import java.util.prefs.Preferences;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import com.jbricx.swing.actions.AboutAction;
import com.jbricx.swing.actions.CloseAction;
import com.jbricx.swing.actions.CompileAction;
import com.jbricx.swing.actions.CopyAction;
import com.jbricx.swing.actions.CutAction;
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
import com.jbricx.swing.communications.NXTManager;

public class JBricxMenuAndToolBarDelegate {

	JBricxManager manager;

	// TODO Alphabetize as you add more!!!
	private AboutAction aboutAction;
	private CompileAction compileAction;
	private CopyAction copyAction;
	private CutAction cutAction;
	private CloseAction closeAction;
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
		closeAction = new CloseAction(manager);
		// directControlAction = new DirectControlAction(manager);
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
		// pianoAction = new PianoAction(manager);
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
		NXTManager nxtManager = NXTManager.getInstance();

		nxtManager.register(downloadAction);
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
		
		/////////////////////////////////////////////////
		JButton compileButton = new JButton();
		compileButton.setAction(compileAction);
		compileButton.setToolTipText("Compile");
		
		// Find Brick Button
		JButton fbButton = new JButton();
		fbButton.setAction(findBrickAction);
		fbButton.setToolTipText("Find Brick");

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
		newButton.setToolTipText("New File");
		
		// Open Button
		JButton openButton = new JButton();
		openButton.setAction(openAction);
		openButton.setToolTipText("Open File");
		
		// Paste Button
		JButton pasteButton = new JButton();
		pasteButton.setAction(pasteAction);
		pasteButton.setToolTipText("Paste");
		
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
		
		// if mac add the accessible content
		if(System.getProperty("os.name").contains("OS X")){

			// Cut Button
			cutButton.getAccessibleContext().setAccessibleName("Cut");
			cutButton.getAccessibleContext().setAccessibleDescription("Cut text");

			// Copy Button
			copyButton.getAccessibleContext().setAccessibleName("Copy");
			copyButton.getAccessibleContext().setAccessibleDescription("Copy text");

			// find Button
			findButton.getAccessibleContext().setAccessibleName("Find");
			findButton.getAccessibleContext().setAccessibleDescription("Find text");

			// Compile Button
			
			/////////////////////////////////////////////////
			compileButton.getAccessibleContext().setAccessibleName("Compile");
			compileButton.getAccessibleContext().setAccessibleDescription("Compile program");
			
			// Find Brick Button
			fbButton.getAccessibleContext().setAccessibleName("Find Brick");
			fbButton.getAccessibleContext().setAccessibleDescription("connect the brick to the system");

			// Download Button
			dlButton.getAccessibleContext().setAccessibleName("Download");
			dlButton.getAccessibleContext().setAccessibleDescription("Download program on to brick");

			// Preferences Button
			preferencesButton.getAccessibleContext().setAccessibleName("Preferences");
			preferencesButton.getAccessibleContext().setAccessibleDescription("Preferences window");
			
			// GoTo Button
			gotoButton.getAccessibleContext().setAccessibleName("Go to");
			gotoButton.getAccessibleContext().setAccessibleDescription("Go to a specific location");
			
			// Help Content Button
			helpContentButton.getAccessibleContext().setAccessibleName("Help Content");
			helpContentButton.getAccessibleContext().setAccessibleDescription("Opens help content");
			
			// New Button
			newButton.getAccessibleContext().setAccessibleName("New File");
			newButton.getAccessibleContext().setAccessibleDescription("Opens a new file");
			
			// Open Button
			openButton.getAccessibleContext().setAccessibleName("Open file");
			openButton.getAccessibleContext().setAccessibleDescription("Opens the chosen file");
			
			// Paste Button
			pasteButton.getAccessibleContext().setAccessibleName("Paste");
			pasteButton.getAccessibleContext().setAccessibleDescription("Paste text");
			
			// redo Button
			redoButton.getAccessibleContext().setAccessibleName("Redo");
			redoButton.getAccessibleContext().setAccessibleDescription("Redo");
			
			// Save Button
			saveButton.getAccessibleContext().setAccessibleName("Save");
			saveButton.getAccessibleContext().setAccessibleDescription("Save");
			
			// Save As Button
			saveAsButton.getAccessibleContext().setAccessibleName("Save As");
			saveAsButton.getAccessibleContext().setAccessibleDescription("Save As");
			
			// Undo Button
			undoButton.getAccessibleContext().setAccessibleName("Undo");
			undoButton.getAccessibleContext().setAccessibleDescription("Undo");
		}

		// Add all the buttons to the tool bar
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(newButton);
		mainToolBar.add(openButton);
		mainToolBar.add(saveButton);
		mainToolBar.add(saveAsButton);
		mainToolBar.add( new JToolBar.Separator());
		//mainToolBar.add(Box.createHorizontalStrut(45));
		mainToolBar.add(Box.createHorizontalGlue());
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(undoButton);
		mainToolBar.add(redoButton);
		mainToolBar.add( new JToolBar.Separator());
		//mainToolBar.add(Box.createHorizontalStrut(45));
		mainToolBar.add(Box.createHorizontalGlue());
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(cutButton);
		mainToolBar.add(copyButton);
		mainToolBar.add(pasteButton);
		mainToolBar.add( new JToolBar.Separator());
		//mainToolBar.add(Box.createHorizontalStrut(45));
		mainToolBar.add(Box.createHorizontalGlue());
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(findButton);
		mainToolBar.add(gotoButton);
		mainToolBar.add( new JToolBar.Separator());
		//mainToolBar.add(Box.createHorizontalStrut(45));
		mainToolBar.add(Box.createHorizontalGlue());
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(compileButton);
		mainToolBar.add(fbButton);
		mainToolBar.add(dlButton);
		mainToolBar.add( new JToolBar.Separator());
		//mainToolBar.add(Box.createHorizontalStrut(45));
		mainToolBar.add(Box.createHorizontalGlue());
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(preferencesButton);
		mainToolBar.add(helpContentButton);
		mainToolBar.add( new JToolBar.Separator());
		
		// Set the tab order
		Vector<JButton> order = new Vector<JButton>();
        order.add(newButton);
        order.add(openButton);
        order.add(saveButton);
        order.add(saveAsButton);
        order.add(undoButton);
        order.add(redoButton);
        order.add(cutButton);
        order.add(copyButton);
        order.add(pasteButton);
        order.add(findButton);
        order.add(gotoButton);
        order.add(compileButton);
        order.add(fbButton);
        order.add(dlButton);
        order.add(preferencesButton);
        order.add(helpContentButton);
        mainToolBar.setFocusTraversalPolicy(new ToolBarFocusTraversalPolicy(order));
        
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
		
		// check if we're on a mac
		//String os = System.getProperty("os.name").toLowerCase();
		//boolean isMac = os.indexOf("mac") >= 0;
		
		// the value to use for shortcuts
		int ctrl_or_command_mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		
		// pick between ctrl or command key
		/*if(isMac)
			ctrl_or_command_mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(); // command key
		else
			ctrl_or_command_mask = ActionEvent.CTRL_MASK; // ctrl key*/
		
		// File
		JMenuItem newDoc = new JMenuItem(newAction);
		newDoc.getAccessibleContext().setAccessibleName("New Document shortcut control + n");
		newDoc.getAccessibleContext().setAccessibleDescription(
				"Open a new document");
		newDoc.setText("New");
		newDoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ctrl_or_command_mask));
		fileMenu.add(newDoc);

		JMenuItem open = new JMenuItem(openAction);
		open.getAccessibleContext().setAccessibleName("Open shortcut control + o");
		open.setText("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ctrl_or_command_mask));
		fileMenu.add(open);

		JMenuItem save = new JMenuItem(saveAction);
		save.getAccessibleContext().setAccessibleName("Save shortcut control + s");
		save.setText("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ctrl_or_command_mask));
		fileMenu.add(save);

		JMenuItem saveAs = new JMenuItem(saveAsAction);
		saveAs.setText("Save As");
		fileMenu.add(saveAs);

		JMenuItem printPreview = new JMenuItem(printPreviewAction);
		printPreview.setText("Print Preview");
		fileMenu.add(printPreview);

		JMenuItem print = new JMenuItem(printAction);
		print.getAccessibleContext().setAccessibleName("Print shortcut control + p");
		print.setText("Print");
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ctrl_or_command_mask));
		
		fileMenu.add(print);

		JMenuItem close = new JMenuItem(closeAction);
		close.setText("Close");
		close.getAccessibleContext().setAccessibleName("Close File shortcut control + w");
		close.getAccessibleContext().setAccessibleDescription(
				"Close the current File");
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ctrl_or_command_mask));
		fileMenu.add(close);

		JMenuItem exit = new JMenuItem(exitAction);
		exit.setText("Quit");
		exit.getAccessibleContext().setAccessibleName("Quit shortcut control + q");
		exit.getAccessibleContext()
				.setAccessibleDescription("Exit the program");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ctrl_or_command_mask));
		fileMenu.add(exit);

		// Edit

		JMenuItem undo = new JMenuItem(undoAction);
		undo.setText("Undo");
		editMenu.add(undo);

		JMenuItem redo = new JMenuItem(redoAction);
		redo.setText("Redo");
		editMenu.add(redo);

		JMenuItem cut = new JMenuItem(cutAction);
		cut.getAccessibleContext().setAccessibleName("Cut shortcut control + x");
		cut.setText("Cut");
		editMenu.add(cut);

		JMenuItem copy = new JMenuItem(copyAction);
		copy.getAccessibleContext().setAccessibleName("Copy shortcut control + c");
		copy.setText("Copy");
		editMenu.add(copy);

		JMenuItem paste = new JMenuItem(pasteAction);
		paste.getAccessibleContext().setAccessibleName("Paste shortcut control + p");
		paste.setText("Paste");
		editMenu.add(paste);

		JMenuItem selectAll = new JMenuItem(selectAllAction);
		selectAll.setText("Select All");
		editMenu.add(selectAll);

		JMenuItem find = new JMenuItem(findAction);
		find.getAccessibleContext().setAccessibleName("Find and Replace shortcut control + f");
		find.setText("Find and Replace");
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ctrl_or_command_mask));
		editMenu.add(find);

		JMenuItem prefs = new JMenuItem(prefsAction);
		prefs.getAccessibleContext().setAccessibleName("Preferences shortcut control + r");
		prefs.setText("Preferences");
		prefs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ctrl_or_command_mask));
		editMenu.add(prefs);

		// Compile
		JMenuItem compile = new JMenuItem(compileAction);
		compile.getAccessibleContext().setAccessibleName("Compile shortcut F5");
		compile.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
		compile.setText("Compile");
		compileMenu.add(compile);

		JMenuItem fb = new JMenuItem(findBrickAction);
		fb.getAccessibleContext().setAccessibleName("Find Brick shortcut control + shift + f");
		fb.setText("Find Brick");
		fb.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ctrl_or_command_mask + KeyEvent.SHIFT_MASK));
		compileMenu.add(fb);

		JMenuItem dl = new JMenuItem(downloadAction);
		dl.getAccessibleContext().setAccessibleName("Download shortcut F6");
		dl.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
		dl.setText("Download");
		compileMenu.add(dl);

		// Tools
		JMenuItem gt = new JMenuItem(gotoAction);
		gt.getAccessibleContext().setAccessibleName("Go To shortcut control g");
		gt.setText("GoTo");
		gt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ctrl_or_command_mask));
		toolsMenu.add(gt);

		// JMenuItem piano = new JMenuItem(pianoAction);
		// piano.setText("Piano");
		// toolsMenu.add(piano);
		//
		// JMenuItem directControl = new JMenuItem(directControlAction);
		// directControl.setText("Direct Control");
		// toolsMenu.add(directControl);

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
		help.getAccessibleContext().setAccessibleName("Help Content shortcut F1");
		help.setText("Help Content");
		help.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
		helpMenu.add(help);
	}

	public static class ToolBarFocusTraversalPolicy extends FocusTraversalPolicy {
		Vector<JButton> order;

		public ToolBarFocusTraversalPolicy(Vector<JButton> order) {
			this.order = new Vector<JButton>(order.size());
			this.order.addAll(order);
		}

		public Component getComponentAfter(Container focusCycleRoot,
				Component aComponent) {
			int idx = (order.indexOf(aComponent) + 1) % order.size();
			return order.get(idx);
		}

		public Component getComponentBefore(Container focusCycleRoot,
				Component aComponent) {
			int idx = order.indexOf(aComponent) - 1;
			if (idx < 0) {
				idx = order.size() - 1;
			}
			return order.get(idx);
		}

		public Component getDefaultComponent(Container focusCycleRoot) {
			return order.get(0);
		}

		public Component getLastComponent(Container focusCycleRoot) {
			return order.lastElement();
		}

		public Component getFirstComponent(Container focusCycleRoot) {
			return order.get(0);
		}
	}
}
