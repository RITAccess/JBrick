/**
 * 
 */
package com.jbricx.ui;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Listener;

import com.jbricx.filters.FolderFilter;
import com.jbricx.treeProviders.FileTreeContentProvider;
import com.jbricx.treeProviders.FileTreeLabelProvider;

/**
 * @author byktol
 */
public class FileExplorerTabItem extends CTabItem {

  final TreeViewer tv;

  /**
   * @param parent
   * @param style
   */
  public FileExplorerTabItem(CTabFolder parent, int style, String workspacePath) {
    super(parent, style);

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
  }
}
