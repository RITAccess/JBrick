/**
 * 
 */
package com.jbricx.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;

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
import com.jbricx.actions.PianoAction;
import com.jbricx.actions.PreferencesAction;
import com.jbricx.actions.PrintAction;
import com.jbricx.actions.PrintPreviewAction;
import com.jbricx.actions.RedoAction;
import com.jbricx.actions.SaveAction;
import com.jbricx.actions.SaveAsAction;
import com.jbricx.actions.SelectAllAction;
import com.jbricx.actions.UndoAction;
import com.jbricx.communications.NXTManager;

/**
 * The purpose is to have the {@link Action}s in a different class other than
 * the {@link MainWindow} because everything is clogged up in there. This class
 * works in a factory-pattern sort of way.
 * 
 * We can decouple this a little more by creating individual Manager classes,
 * each one implementing the same set of attributes we're using here. I'm not
 * doing it right now just to avoid the copy+paste.
 * 
 * @see MainWindow
 * @see MenuManager
 * @see ToolBarManager
 * @see JBrickMenuManager
 * @see JBrickToolBarManager
 * @author byktol
 */
public class MenuAndToolBarManagerDelegate {

  private AboutAction aboutAction;
  private HelpContentAction helpContentAction = new HelpContentAction();
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

  public MenuAndToolBarManagerDelegate(final JBrickManager manager) {
    copyAction = new CopyAction(manager);
    cutAction = new CutAction(manager);
    selectAllAction = new SelectAllAction(manager);
    pasteAction = new PasteAction(manager);
    aboutAction = new AboutAction(manager);
    exitAction = new ExitAction(manager);
    findAction = new FindAction(manager);
    gotoAction = new GotoAction(manager);
    newAction = new NewAction(manager);
    openAction = new OpenAction(manager);
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

    nxtManager.register(joystickAction);
    nxtManager.register(pianoAction);
    nxtManager.register(directControlAction);    
  }

  /**
   * Creates a {@link MenuManager} based on the {@link Action}s attributes.
   * 
   * @return a MenuManager containing the options for the system operations.
   */
  public MenuManager createMenuManager() {
    return new JBrickMenuManager();
  }

  /**
   * Creates a {@link ToolBarManager} based on the {@link Action}s attributes.
   * 
   * @param style
   *          an {@link SWT} property describing the toolbar style
   * @return a MenuManager containing the options for the system operations.
   */
  public ToolBarManager createToolBarManager(int style) {
    return new JBrickToolBarManager(style);
  }

  /**
   * Contains the main menu used in the application.
   * 
   * It's been nested to avoid a different file somewhere and because it's more
   * of a data/wrapper class with no additional behavior.
   * 
   * @author byktol
   */
  public class JBrickMenuManager extends MenuManager {

    public JBrickMenuManager() {
      MenuManager fileMenu = new MenuManager("&File");
      MenuManager editMenu = new MenuManager("&Edit");
      MenuManager compileMenu = new MenuManager("&Compile");
      MenuManager toolMenu = new MenuManager("&Tools");
      MenuManager downloadMenu = new MenuManager("&Download");

      MenuManager helpMenu = new MenuManager("&Help");

      add(fileMenu);
      add(editMenu);
      add(compileMenu);
      add(toolMenu);
      add(helpMenu);
      add(downloadMenu);

      fileMenu.add(newAction);
      fileMenu.add(openAction);
      fileMenu.add(saveAction);
      fileMenu.add(saveAsAction);
      fileMenu.add(new Separator());
      fileMenu.add(printAction);
      fileMenu.add(printPreviewAction);
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
      toolMenu.add(pianoAction);

      helpMenu.add(aboutAction);
      helpMenu.add(helpContentAction);
    }
  }

  /**
   * Contains the main toolbar used in the application.
   * 
   * It's been nested to avoid a different file somewhere and because it's more
   * of a data/wrapper class with no additional behavior.
   * 
   * @author byktol
   */
  public class JBrickToolBarManager extends ToolBarManager {

    public JBrickToolBarManager(int style) {
      super(style);

      add(newAction);
      add(openAction);
      add(saveAction);
      add(saveAsAction);
      add(new Separator());
      add(printAction);
      add(new Separator());
      add(undoAction);
      add(redoAction);
      add(new Separator());
      add(cutAction);
      add(copyAction);
      add(pasteAction);
      add(new Separator());
      add(findAction);
      add(new Separator());
      add(prefsAction);
      add(new Separator());
      add(compileAction);

      add(new Separator());
      //add(downloadAction);

      add(new Separator());
      add(aboutAction);

      add(new Separator());
      //add(directControlAction);
      add(joystickAction);
      add(pianoAction);

      add(new Separator());
      add(findBrickAction);
    }
  }
}
