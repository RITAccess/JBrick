/**
 * 
 */
package com.jbricx.ui;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.preferences.JBrickObserver;
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
