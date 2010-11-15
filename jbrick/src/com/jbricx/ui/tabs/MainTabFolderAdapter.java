package com.jbricx.ui.tabs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Control;

import com.jbricx.ui.MainWindow;

/**
 * Contains the methods for handling a tab folder as part of the {@link MainWindow}. I'm assuming too much regarding the
 * architecture of {@link MainWindow} in order to add this listener. I'm aware this is going to be a headache to
 * refactor once it's needed. Hopefully that won't be too soon.
 *
 * The purpose is to have a current CTabFolder and maximize/minimize/restore it based on the it's parent.
 * Parent must be a SashForm because it allows resizing.
 * All the siblings must be of type CTabFolder because the are the components that have the buttons for resizing.
 * The grand parent should also be a SashForm.
 * 
 * Oh, and one more thing: Always check the listeners you're appending to a particular component because of the way SWT
 * handles the events you may end up with a really undesired behavior.
 * 
 * @see CTabFolder
 * @see CTabFolder2Adapter
 * @see CTabFolder2Listener
 * @see SashForm
 * @author byktol
 */
public class MainTabFolderAdapter extends CTabFolder2Adapter {

  final CTabFolder tabFolder;
  final List<CTabFolder> siblings;

  final SashForm parent;

  public MainTabFolderAdapter(final CTabFolder tabFolder, final SashForm parent) {
    super();

    this.tabFolder = tabFolder;
    this.tabFolder.addCTabFolder2Listener(this);

    siblings = new ArrayList<CTabFolder>();
    Control[] children = parent.getChildren();
    for (int i = 0; i < children.length; i++) {
      if (!children[i].equals(tabFolder)) {
        siblings.add((CTabFolder) children[i]);
      }
    }

    this.parent = parent;
  }

  @Override
  public void restore(final CTabFolderEvent event) {
    for (CTabFolder tb : siblings) {
      tb.setMaximized(false);
      tb.setMinimized(false);
    }

    tabFolder.setMaximized(false);
    tabFolder.setMinimized(false);
    parent.setMaximizedControl(null);
    ((SashForm) parent.getParent()).setMaximizedControl(null);
  }
  
  @Override
  public void maximize(final CTabFolderEvent event) {
    tabFolder.setMaximized(true);
    parent.setMaximizedControl(tabFolder);
    ((SashForm) parent.getParent()).setMaximizedControl(parent);
  }

  @Override
  public void minimize(final CTabFolderEvent event) {

    tabFolder.setMinimized(true);
    if (!siblings.isEmpty()) {
      siblings.get(0).setMaximized(true);
      parent.setMaximizedControl(siblings.get(0));
    }
  }
}
