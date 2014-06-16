package com.jbricx.swing.ui;

//import java.util.prefs.Preferences;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import com.jbricx.swing.actions.JBricxAbstractAction;
import com.jbricx.swing.actions.KeyboardShortcutReferencesAction;
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
import com.jbricx.swing.communications.NXTObserver;
import com.sun.jna.Platform;

public class JBricxMenuAndToolBarDelegate {

	private static JBricxManager manager;

	static String modifierStr = Platform.getOSType() == Platform.MAC ? "command" : "control";
	static Integer modifier = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

	private static JMenu fileMenu;
	private static JMenu editMenu;
	private static JMenu compileMenu;
	private static JMenu toolsMenu;
	private static JMenu viewMenu;
	private static JMenu helpMenu;
	
	enum ActionSet{
		NEW(NewAction.class, "New File", KeyStroke.getKeyStroke(KeyEvent.VK_N, modifier), fileMenu),
		OPEN(OpenAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_O, modifier), fileMenu),
		SAVE(SaveAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_S, modifier), fileMenu),
		SAVEAS(SaveAsAction.class, "Save As", fileMenu),
		PRINTPREVIEW(PrintPreviewAction.class, "Print Preview", fileMenu),
		PRINT(PrintAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_P, modifier), fileMenu),
		CLOSE(CloseAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_W, modifier), fileMenu),
		EXIT(ExitAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_Q, modifier), fileMenu),
		
		UNDO(UndoAction.class, editMenu),
		REDO(RedoAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_Z, modifier + KeyEvent.SHIFT_MASK), editMenu),
		CUT(CutAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_X, modifier), editMenu),
		COPY(CopyAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_C, modifier), editMenu),
		PASTE(PasteAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_V, modifier), editMenu),
		SELECTALL(SelectAllAction.class, "Select All", KeyStroke.getKeyStroke(KeyEvent.VK_A, modifier), editMenu),
		FIND(FindAction.class, "Find and Replace", editMenu),
		PREFERENCES(PreferencesAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_R, modifier), editMenu),
		GOTO(GotoAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_G, modifier), toolsMenu),
		
		COMPILE(CompileAction.class, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0), compileMenu),
		FINDBRICK(FindBrickAction.class, "Find Brick", KeyStroke.getKeyStroke(KeyEvent.VK_F, modifier + KeyEvent.SHIFT_MASK), compileMenu),
		DOWNLOAD(DownloadAction.class, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0), compileMenu),
		
		MAXEDITOR(MaxEditorAction.class, "Maximize Editor", viewMenu),
		MAXSTATUS(MaxStatusAction.class, "Maximize Status", viewMenu),
		MAXVIEWER(MaxViewerAction.class, "Max Viewer", viewMenu),
		RESETVIEW(ResetViewAction.class, "Reset View", viewMenu),
		SHOWHIDEFILEVIEWER(ShowHideFileViewerAction.class, "Toggle File Viewer", viewMenu),
		
		ABOUT(AboutAction.class, helpMenu),
		KEYSHORTCUT(KeyboardShortcutReferencesAction.class, "Keyboard Shortcut Reference", helpMenu),
		HELP(HelpContentAction.class, "Help Content", KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0), helpMenu),
		
		;
		
		Object action;
		JButton button;
		Class<? extends JBricxAbstractAction> actionClass;
		JMenuItem mi;
		String name;
		ActionSet(Class<? extends JBricxAbstractAction> c, JComponent...parents) {
			this(c, null, null, parents);
		}
		ActionSet(Class<? extends JBricxAbstractAction> c, String name, JComponent...parents) {
			this(c, name, null, parents);
		}
		ActionSet(Class<? extends JBricxAbstractAction> c, KeyStroke eventKeys, JComponent...parents) {
			this(c, null, eventKeys, parents);
		}
		ActionSet(Class<? extends JBricxAbstractAction> c, String name, KeyStroke eventKeys, JComponent...parents){
			
			this.actionClass = c;
			this.name = name == null ? (this.toString().substring(0,1) + this.toString().substring(1).toLowerCase()) : name;
			try {
				action = c.getDeclaredConstructor(JBricxManager.class).newInstance(manager);
			} catch (IllegalArgumentException e1) {e1.printStackTrace();
			} catch (SecurityException e1) {e1.printStackTrace();
			} catch (InstantiationException e1) {e1.printStackTrace();
			} catch (IllegalAccessException e1) {e1.printStackTrace();
			} catch (InvocationTargetException e1) {e1.printStackTrace();
			} catch (NoSuchMethodException e1) {e1.printStackTrace();
			}
			
			button = new JButton();
			button.setAction((JBricxAbstractAction) action);
			button.setToolTipText(this.name);
			button.getAccessibleContext().setAccessibleName(this.name);
			mi = new JMenuItem((JBricxAbstractAction) action);
			mi.setText(this.name);
			if (eventKeys != null){
				String modStr = (eventKeys.getModifiers() == modifier) ? modifierStr + " + " : "";
				modStr = (eventKeys.getModifiers() == KeyEvent.SHIFT_MASK + modifier) ? modStr + "shift + " : modStr;
				mi.setAccelerator(eventKeys);
				mi.getAccessibleContext().setAccessibleName(this.name + " shortcut " + modStr + eventKeys.getKeyChar());
			}
			for (JComponent p : parents) { if (p != null) { p.add(mi); } }
		}
	}

	private Vector<JButton> order;

	public JBricxMenuAndToolBarDelegate(final JBricxManager manager) {
		JBricxMenuAndToolBarDelegate.manager = manager;
		this.makeMainMenus();
		NXTManager nxtManager = NXTManager.getInstance();
		nxtManager.register((NXTObserver) ActionSet.DOWNLOAD.action); 
	}

	public JToolBar getToolBar() {
		JToolBar mainToolBar = new JToolBar();
		mainToolBar.setFloatable(false);

		// Add all the buttons to the tool bar
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(ActionSet.NEW.button);
		mainToolBar.add(ActionSet.OPEN.button);
		mainToolBar.add(ActionSet.SAVE.button);
		mainToolBar.add(ActionSet.SAVEAS.button);
		mainToolBar.add( new JToolBar.Separator());
		//mainToolBar.add(Box.createHorizontalStrut(45));
		mainToolBar.add(Box.createHorizontalGlue());
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(ActionSet.UNDO.button);
		mainToolBar.add(ActionSet.REDO.button);
		mainToolBar.add( new JToolBar.Separator());
		//mainToolBar.add(Box.createHorizontalStrut(45));
		mainToolBar.add(Box.createHorizontalGlue());
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(ActionSet.CUT.button);
		mainToolBar.add(ActionSet.COPY.button);
		mainToolBar.add(ActionSet.PASTE.button);
		mainToolBar.add( new JToolBar.Separator());
		//mainToolBar.add(Box.createHorizontalStrut(45));
		mainToolBar.add(Box.createHorizontalGlue());
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(ActionSet.FIND.button);
		mainToolBar.add(ActionSet.GOTO.button);
		mainToolBar.add( new JToolBar.Separator());
		//mainToolBar.add(Box.createHorizontalStrut(45));
		mainToolBar.add(Box.createHorizontalGlue());
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(ActionSet.COMPILE.button);
		mainToolBar.add(ActionSet.FINDBRICK.button);
		mainToolBar.add(ActionSet.DOWNLOAD.button);
		mainToolBar.add( new JToolBar.Separator());
		//mainToolBar.add(Box.createHorizontalStrut(45));
		mainToolBar.add(Box.createHorizontalGlue()); 
		mainToolBar.add( new JToolBar.Separator());
		mainToolBar.add(ActionSet.PREFERENCES.button);
		mainToolBar.add(ActionSet.HELP.button);
		mainToolBar.add( new JToolBar.Separator());

		setOrder();
        mainToolBar.setFocusTraversalPolicy(new ToolBarFocusTraversalPolicy(order));
        
		return mainToolBar;
	}
	
	private void setOrder(JButton...buttons){
		if (order == null) {
			order = new Vector<JButton>();
		} else {
			order.clear();
		}
		for (JButton b : buttons){
			order.add(b);
		}
	}
	
	private void setOrder(){
		setOrder(
			ActionSet.NEW.button,
	        ActionSet.OPEN.button,
	        ActionSet.SAVE.button,
	        ActionSet.SAVEAS.button,
	        ActionSet.PRINTPREVIEW.button,
	        ActionSet.PRINT.button,
	        ActionSet.CLOSE.button,
	        ActionSet.EXIT.button,
	        ActionSet.UNDO.button,
	        ActionSet.REDO.button,
	        ActionSet.CUT.button,
	        ActionSet.COPY.button,
	        ActionSet.PASTE.button,
	        ActionSet.SELECTALL.button,
	        ActionSet.FIND.button,
	        ActionSet.GOTO.button,
	        ActionSet.COMPILE.button,
	        ActionSet.FINDBRICK.button,
	        ActionSet.DOWNLOAD.button,
	        ActionSet.SHOWHIDEFILEVIEWER.button,
	        ActionSet.MAXVIEWER.button,
	        ActionSet.MAXEDITOR.button,
	        ActionSet.MAXSTATUS.button,
	        ActionSet.PREFERENCES.button,
	        ActionSet.HELP.button
		);
	}

	public JMenuBar getMenuBar() {
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
		editMenu = new JMenu("Edit");
		compileMenu = new JMenu("Compile");
		toolsMenu = new JMenu("Tools");
		viewMenu = new JMenu("View");
		helpMenu = new JMenu("Help");
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
