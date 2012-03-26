/**
 * 
 */
package com.jbricx.ui.tabs;

import com.jbricx.filters.FolderFilter;
import com.jbricx.treeProviders.FileTreeContentProvider;
import com.jbricx.treeProviders.FileTreeLabelProvider;

/**
 * @author byktol
 */
public class FileExplorerTabItem extends CTabItem {

  private final TreeViewer tv;
  private String workspacePath = "";

  /**
   * @param parent
   * @param style
   */
  public FileExplorerTabItem(CTabFolder parent, int style, String workspacePath) {
    super(parent, style);
    this.workspacePath = workspacePath;
    setText("Explorer");

    tv = new TreeViewer(parent);
    setControl(tv.getControl());
    parent.setSelection(this);
    tv.getTree().setLayoutData(new GridData(GridData.BEGINNING));
    tv.setContentProvider(new FileTreeContentProvider(workspacePath));
    tv.setLabelProvider(new FileTreeLabelProvider());
    tv.addFilter(new FolderFilter());
    tv.setInput("root"); // pass a non-null that will be ignored
  }

  public void addTreeListener(int object, Listener object2) {
    tv.getTree().addListener(object, object2);
  }

  public ISelection getSelection() {
    return tv.getSelection();
  }

  public void refreshView() {
	  tv.setContentProvider(new FileTreeContentProvider(this.workspacePath));
 }

}
