/**
 * 
 */
package com.jbricx.ui;

import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.jbricx.ui.tabs.JBrickTabItem;

/**
 * @author byktol
 */
public interface JBrickManager {

  SourceViewer getCurrentTabItemSourceViewer();

  Shell getShell();

  //TODO: Remove.
  // This creates a dependency.
  JBrickTabItem getCurrentTabItem();

  //TODO: Remove, Rename, Refactor.
  // It's been kept to not break anything, but makes no sense.
  boolean isAutoCompile();

  void setStatus(String string);

  void openFile(String fileName);

  void openNewFile();

  //TODO: Rename method name.
  // Even though it's invoked when the editor window is closed, it shouldn't be called close(), don't you think?
  boolean close();

  void undo();

  void redo();

  //TODO: Remove.
  // WHY? You shouldn't be doing this.
  Composite getTable();

  void cut();
  void copy();
  void paste();
  void selectAll();
}
