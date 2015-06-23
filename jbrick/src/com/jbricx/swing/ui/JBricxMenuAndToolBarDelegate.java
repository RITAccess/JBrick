package com.jbricx.swing.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import com.jbricx.swing.actions.AboutAction;
import com.jbricx.swing.actions.AudioBreakAction;
import com.jbricx.swing.actions.CloseAction;
import com.jbricx.swing.actions.CompileAction;
import com.jbricx.swing.actions.CopyAction;
import com.jbricx.swing.actions.CutAction;
import com.jbricx.swing.actions.DecreaseFontSizeAction;
import com.jbricx.swing.actions.DownloadAction;
import com.jbricx.swing.actions.DownloadDebugAction;
import com.jbricx.swing.actions.ExitAction;
import com.jbricx.swing.actions.FindAction;
import com.jbricx.swing.actions.GotoAction;
import com.jbricx.swing.actions.HelpContentAction;
import com.jbricx.swing.actions.IncreaseFontSizeAction;
import com.jbricx.swing.actions.JBricxAbstractAction;
import com.jbricx.swing.actions.KeyboardShortcutReferencesAction;
import com.jbricx.swing.actions.LowerToneAction;
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
import com.jbricx.swing.actions.RaiseToneAction;
//import com.jbricx.swing.actions.ReadLineInfoAction;
import com.jbricx.swing.actions.RedoAction;
import com.jbricx.swing.actions.ResetViewAction;
import com.jbricx.swing.actions.RunAction;
import com.jbricx.swing.actions.SaveAction;
import com.jbricx.swing.actions.SaveAsAction;
import com.jbricx.swing.actions.SelectAllAction;
import com.jbricx.swing.actions.ShowHideFileViewerAction;
import com.jbricx.swing.actions.UndoAction;
import com.sun.jna.Platform;

public class JBricxMenuAndToolBarDelegate {

	private static JBricxManager manager;

	static String modifierStr = Platform.getOSType() == Platform.MAC ? "command" : "control";
	static Integer modifier = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
	static int toolbarSize = 4;
	static HashMap<Integer, ArrayList<ActionSet>> toolbarMap = new HashMap<Integer, ArrayList<ActionSet>>();
	
	/**
	 * MenuEnum - contains all the information for the top menus
	 * This allows us to iterate over static components (by calling MenuEnum.values()) 
	 * @author Ethan Jurman
	 *
	 */
	enum MenuEnum{
		FILE("File"),
		EDIT("Edit"),
		COMPILE("Compile"),
		TOOLS("Tools"),
		VIEW("View"),
		HELP("Help"),
		;
		
		JMenu menu;
		
		MenuEnum(String name){
			this.menu = new JMenu(name);
		}
	}
	
	/**
	 * Enum structured to hold all the actions, tip text, event keys, etc. for the various actions for jbrick
	 * @author Ethan Jurman (ehj2229@rit.edu)
	 *
	 */
	enum ActionSet{
		NEW(NewAction.class, "New File", KeyStroke.getKeyStroke(KeyEvent.VK_N, modifier), 0, MenuEnum.FILE),
		OPEN(OpenAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_O, modifier), 0, MenuEnum.FILE),
		SAVE(SaveAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_S, modifier), 0, MenuEnum.FILE),
		SAVEAS(SaveAsAction.class, "Save As", 0, MenuEnum.FILE),
		PRINTPREVIEW(PrintPreviewAction.class, "Print Preview", -1, MenuEnum.FILE),
		PRINT(PrintAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_P, modifier), -1, MenuEnum.FILE),
		CLOSE(CloseAction.class, "Close File", KeyStroke.getKeyStroke(KeyEvent.VK_W, modifier), -1, MenuEnum.FILE),
		EXIT(ExitAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_Q, modifier), -1, MenuEnum.FILE),
		
		UNDO(UndoAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_Z, modifier), 1, MenuEnum.EDIT),
		REDO(RedoAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_Z, modifier + KeyEvent.SHIFT_MASK), 1, MenuEnum.EDIT),
		CUT(CutAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_X, modifier), 1, MenuEnum.EDIT),
		COPY(CopyAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_C, modifier), 1, MenuEnum.EDIT),
		PASTE(PasteAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_V, modifier), 1, MenuEnum.EDIT),
		SELECTALL(SelectAllAction.class, "Select All", KeyStroke.getKeyStroke(KeyEvent.VK_A, modifier), -1, MenuEnum.EDIT),
		FIND(FindAction.class, "Find and Replace", KeyStroke.getKeyStroke(KeyEvent.VK_F, modifier), 1, MenuEnum.EDIT),
		PREFERENCES(PreferencesAction.class, KeyStroke.getKeyStroke(KeyEvent.VK_R, modifier), 3, MenuEnum.EDIT),
		
		GOTO(GotoAction.class, "Go-to line", KeyStroke.getKeyStroke(KeyEvent.VK_G, modifier + KeyEvent.SHIFT_MASK), 1, MenuEnum.TOOLS),
		AUDIOBREAK(AudioBreakAction.class, "Audio Break", KeyStroke.getKeyStroke(KeyEvent.VK_B, modifier), 2, MenuEnum.TOOLS),
		RAISETONE(RaiseToneAction.class, "Raise Break Tone", KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, modifier + KeyEvent.SHIFT_MASK), -1, MenuEnum.TOOLS),
		LOWERTONE(LowerToneAction.class, "Lower Break Tone", KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, modifier + KeyEvent.SHIFT_MASK), -1, MenuEnum.TOOLS),
		//READLINEINFO(ReadLineInfoAction.class, "Read Line Info", KeyStroke.getKeyStroke(KeyEvent.VK_G, modifier), -1, MenuEnum.TOOLS),
		PIANO(PianoAction.class,"Piano Composer",KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M,modifier), 3, MenuEnum.TOOLS),
		
		COMPILE(CompileAction.class, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0), 2, MenuEnum.COMPILE),
		DOWNLOAD(DownloadAction.class, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0), 2, MenuEnum.COMPILE),
		DOWNLOADDEBUG(DownloadDebugAction.class, "Download Debug", KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0), 2, MenuEnum.COMPILE),
		RUN(RunAction.class, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0), -1, MenuEnum.COMPILE),
		
		MAXEDITOR(MaxEditorAction.class, "Maximize Editor", -1, MenuEnum.VIEW),
		MAXSTATUS(MaxStatusAction.class, "Maximize Status Panel", -1, MenuEnum.VIEW),
		MAXVIEWER(MaxViewerAction.class, "Maximize File Viewer", -1, MenuEnum.VIEW),
		SHOWHIDEFILEVIEWER(ShowHideFileViewerAction.class, "Toggle File Viewer", -1, MenuEnum.VIEW),
		RESETVIEW(ResetViewAction.class, "Reset View", -1, MenuEnum.VIEW),
		INCREASEFONT(IncreaseFontSizeAction.class, "Increase Font Size", KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, modifier), -1, MenuEnum.VIEW),
		DECREASEFONT(DecreaseFontSizeAction.class, "Decrease Font Size", KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, modifier), -1, MenuEnum.VIEW),
		
		ABOUT(AboutAction.class, -1, MenuEnum.HELP),
		KEYSHORTCUT(KeyboardShortcutReferencesAction.class, "Keyboard Shortcut Reference", 3, MenuEnum.HELP),
		HELP(HelpContentAction.class, "Help Content", KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0), 3, MenuEnum.HELP),
		
		;

		Object action;
		JButton button;
		Class<? extends JBricxAbstractAction> actionClass;
		JMenuItem mi;
		String name;
		
		ActionSet(Class<? extends JBricxAbstractAction> c, int toolbarIndex, MenuEnum...parents) {
			this(c, null, null, toolbarIndex, parents);
		}
		ActionSet(Class<? extends JBricxAbstractAction> c, String name, int toolbarIndex, MenuEnum...parents) {
			this(c, name, null, toolbarIndex, parents);
		}
		ActionSet(Class<? extends JBricxAbstractAction> c, KeyStroke eventKeys, int toolbarIndex, MenuEnum...parents) {
			this(c, null, eventKeys, toolbarIndex, parents);
		}
		/**
		 * ActionSet constructor - This allows the creation of a action to be placed in a Menu bar and/ or Tool bar
		 * 
		 * @param c - an action that gets called when button gets hit in the tool / menu bar.
		 * @param name - name that is shown in the menu bar and as a tool tip text in the tool bar. (if null, will attempt to grab enum name)
		 * @param eventKeys - the keyStroke that will trigger the button.
		 * @param toolbarIndex - where to place the item in the tool bar. if -1 the item will not be placed.
		 * @param parents - the menu(s) that the item will be placed in.
		 */
		ActionSet(Class<? extends JBricxAbstractAction> c, String name, KeyStroke eventKeys, int toolbarIndex, MenuEnum...parents){
			
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
			for (MenuEnum p : parents) { p.menu.add(mi); }
			if (!toolbarMap.containsKey(toolbarIndex)){
				toolbarMap.put(toolbarIndex, new ArrayList<ActionSet>());
			}
			toolbarMap.get(toolbarIndex).add(this);
		}
		public static ArrayList<ActionSet> values(int i) {
			return toolbarMap.get(i);
		}
	}

	private Vector<JButton> order;

	public JBricxMenuAndToolBarDelegate(final JBricxManager manager) {
		JBricxMenuAndToolBarDelegate.manager = manager;
	}

	/**
	 * generates the toolbar from values of ActionSet enum
	 * @return main tool bar
	 */
	public JToolBar getToolBar() {
		JToolBar mainToolBar = new JToolBar();
		mainToolBar.setFloatable(false);
		ActionSet.values();
		for(int i = 0; i < toolbarSize; i++){
			mainToolBar.add( new JToolBar.Separator());
			for(ActionSet action : ActionSet.values(i)){
				mainToolBar.add(action.button);
			}
			mainToolBar.add( new JToolBar.Separator());
			// if it's not the last menu
			if (i != toolbarSize -1){
				mainToolBar.add(Box.createHorizontalGlue());
			}
		}

		setOrder();
        mainToolBar.setFocusTraversalPolicy(new ToolBarFocusTraversalPolicy(order));
        
		return mainToolBar;
	}
	
	/**
	 * set the order for all the toolbars and menubars from a button list
	 * @param buttons
	 */
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
	
	/**
	 * sets the order for all the tool bars and menu bars with predefined
	 * settings
	 */
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
	        ActionSet.AUDIOBREAK.button,
	        ActionSet.COMPILE.button,
	        ActionSet.DOWNLOAD.button,
	        ActionSet.DOWNLOADDEBUG.button,
	        ActionSet.RUN.button,
	        ActionSet.SHOWHIDEFILEVIEWER.button,
	        ActionSet.MAXVIEWER.button,
	        ActionSet.MAXEDITOR.button,
	        ActionSet.MAXSTATUS.button,
	        ActionSet.PIANO.button,
	        ActionSet.PREFERENCES.button,
	        ActionSet.HELP.button
		);
	}

	/**
	 * builds the menu bar and returns the completed filled menu bar
	 * @return - main menu bar
	 */
	public JMenuBar getMenuBar() {
		JMenuBar mainMenuBar = new JMenuBar();
		mainMenuBar.requestFocusInWindow();
		for(MenuEnum menu : MenuEnum.values()){
			mainMenuBar.add(menu.menu);
		}
		return mainMenuBar;
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
