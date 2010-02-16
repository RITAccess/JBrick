package  com.jbricx.actions;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;

import com.jbricx.pjo.JBrickEditor;
import com.jbricx.preferences.EditorPreferencePage;
import com.jbricx.preferences.TextPreferencePage;


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
    mgr.addToRoot(new PreferenceNode("editor", "Editor", null,
    		EditorPreferencePage.class.getName()));

    PreferenceDialog dlg = new PreferenceDialog(JBrickEditor.getMainWindow().getShell(), mgr);
    dlg.setPreferenceStore(JBrickEditor.getApp().getPreferences());
    dlg.open();
    PreferenceStore store= (PreferenceStore) dlg.getPreferenceStore();
    JBrickEditor.getApp().setPrefs(store);
        
  }
}
