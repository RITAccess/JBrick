/**
 * 
 */
package com.jbricx.ui;

import java.awt.Composite;

import com.jbricx.preferences.JBrickObserver;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.ui.tabs.TabFolder;

/**
 * @author byktol
 */
public interface JBrickManager extends JBrickStatusUpdater {

  Shell getShell();

  boolean close();

  //TODO: Remove.
  // WHY? You shouldn't be doing this.
  Composite getTable();

  TabFolder getTabFolder();

  void refreshExplorerContent();

  String getWorkspacePath();

  PreferenceStore getPreferences();

  void registerObserver(JBrickObserver o);

  void updatePreferences();
  
  boolean isAutoCompile();
}
