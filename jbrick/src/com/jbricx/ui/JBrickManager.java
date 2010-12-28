/**
 * 
 */
package com.jbricx.ui;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.preferences.JBrickObserver;
import com.jbricx.ui.tabs.JBrickTabItem;
import com.jbricx.ui.tabs.TabFolder;

/**
 * @author byktol
 */
public interface JBrickManager extends JBrickStatusUpdater {

  Shell getShell();

  //TODO: Remove.
  // This creates a dependency.
  JBrickTabItem getCurrentTabItem();

  //TODO: Remove, Rename, Refactor.
  // It's been kept to not break anything, but makes no sense.
  boolean isAutoCompile();

  //TODO: Rename method name.
  // Even though it's invoked when the editor window is closed, it shouldn't be called close(), don't you think?
  boolean close();

  //TODO: Remove.
  // WHY? You shouldn't be doing this.
  Composite getTable();

  TabFolder getTabFolder();

  void refreshExplorerContent();

  String getWorkPath();

  //TODO: remove
  // I don't like it.
  PreferenceStore getPreferences();
  void setPreferences(PreferenceStore store);

  void registerObserver(JBrickObserver o);
}
