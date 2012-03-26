package com.jbricx.actions;

import java.io.IOException;

import com.jbricx.preferences.BrickToolsPreferencePage;
import com.jbricx.preferences.TextPreferencePage;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.ui.JBrickManager;

/**
 * This action displays the preferences dialog
 */
public class PreferencesAction extends AbstractAction {

  /**
   * PreferencesAction constructor
   */
  public PreferencesAction(final JBrickManager manager) {
    super("P&references...@Ctrl+R", ImageDescriptor.createFromFile(PasteAction.class, "/images/preferences-desktop.png"), manager);
    setToolTipText("Preferences");
  }

  /**
   * Runs the action
   */
  public void run() {
    PreferenceManager mgr = new PreferenceManager();
    mgr.addToRoot(new PreferenceNode("text", "Text", null, TextPreferencePage.class.getName()));
    mgr.addToRoot(new PreferenceNode("tool", "Tools", null, BrickToolsPreferencePage.class.getName()));
    // mgr.addToRoot(new PreferenceNode("editor", "Editor", null,
    // EditorPreferencePage.class.getName()));

    PreferenceDialog dlg = new PreferenceDialog(getManager().getShell(), mgr);
    PreferenceStore ps = getManager().getPreferences();
    dlg.setPreferenceStore(ps);
    dlg.open();
    try {
      ps.save();
    } catch (IOException e) {
      e.printStackTrace();
    }
    getManager().updatePreferences();
  }
}
