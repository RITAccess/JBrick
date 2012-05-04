package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;

import org.fife.ui.rtextarea.RTextAreaEditorKit;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.fife.ui.rtextarea.RecordableTextAction;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * This action undoes the last action
 */
public class UndoAction extends JBricxAbstractAction {

  /**
   * UndoAction constructor
   */
  public UndoAction(final JBricxManager manager) {
	  super("", new ImageIcon("./resources/images/edit-undo.png"), manager);
  }

  /**
   * Runs the action
   */
  public void actionPerformed(ActionEvent e){
    JBricxTabItem tab =(JBricxTabItem)((RTextScrollPane)getManager().getTabFolder().getSelectedComponent()).getViewport().getView();
    tab.undoLastAction();
  }
}
