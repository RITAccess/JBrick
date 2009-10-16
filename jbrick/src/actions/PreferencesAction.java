package  actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.resource.ImageDescriptor;

import pjo.JBrickEditor;
import preferences.TextPreferencePage;

/**
 * This action displays the preferences dialog
 */
public class PreferencesAction extends Action {
  /**
   * PreferencesAction constructor
   */
  public PreferencesAction() {
//    super("P&references...@Ctrl+R");
    super("P&references...@Ctrl+R", ImageDescriptor.createFromFile(PasteAction.class,
    "/images/preferences-desktop.png"));
    setToolTipText("Preferences");
  }

  /**
   * Runs the action
   */
  public void run() {
    PreferenceManager mgr = new PreferenceManager();
    mgr.addToRoot(new PreferenceNode("text", "Text", null,
        TextPreferencePage.class.getName()));

    PreferenceDialog dlg = new PreferenceDialog(JBrickEditor.getApp()
        .getMainWindow().getShell(), mgr);
    dlg.setPreferenceStore(JBrickEditor.getApp().getPreferences());
    dlg.open();
  }
}
