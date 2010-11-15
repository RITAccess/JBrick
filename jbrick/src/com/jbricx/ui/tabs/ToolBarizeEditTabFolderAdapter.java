package com.jbricx.ui.tabs;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.SashForm;

/**
 * @author byktol
 */
public class ToolBarizeEditTabFolderAdapter extends CTabFolder2Adapter {

  private final ICoolBarManager coolBarmanager;
  private final ToolBarContributionItem ic;

  public ToolBarizeEditTabFolderAdapter(final CTabFolder tabFolder, final ICoolBarManager icool, final SashForm parent) {
    this.coolBarmanager = icool;
    tabFolder.addCTabFolder2Listener(this);

    final ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
    ic = new ToolBarContributionItem(toolBarManager);

    final Action action = new Action() {
      @Override
      public void run() {
        coolBarmanager.remove(ic);
        coolBarmanager.update(true);

        tabFolder.setMaximized(false);
        tabFolder.setMinimized(false);
        parent.setMaximizedControl(null);
        ((SashForm) parent.getParent()).setMaximizedControl(null);
      }
    };

    final ActionContributionItem aci = new ActionContributionItem(action);
    toolBarManager.add(aci);
  }

  @Override
  public void minimize(final CTabFolderEvent event) {

    coolBarmanager.add(ic);
    coolBarmanager.update(true);
  }

  @Override
  public void restore(final CTabFolderEvent event) {

    coolBarmanager.remove(ic);
    coolBarmanager.update(true);
  }
}