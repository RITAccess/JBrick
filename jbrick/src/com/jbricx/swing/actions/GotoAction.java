package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.tabs.GoToDialog;
import com.jbricx.swing.ui.tabs.JBricxTabItem;

/**
 * This action searches text
 */
@SuppressWarnings("serial")
public class GotoAction extends JBricxAbstractAction {
  /**
   * FindAction constructor
   */
  public GotoAction(final JBricxManager manager) {
	  super("", new ImageIcon(GotoAction.class.getResource("/icons/goTo.png")), manager);
  }

  /**
   * Get the current tab
   * Create a go to dialog for the current tab
   */
  public void actionPerformed(ActionEvent arg0) {
	  JBricxTabItem tab = getTab();
	  GoToDialog.openGoTo(tab.getLineCount(),this,getManager().getShell());
  }
  
  /**
   * Line number jump function. called by the dialog box above
   * @param n line number to jump to
   */
  public void goTo(int n){
	  JBricxTabItem tab = getTab();
	  try {
			int ln = n - 1;
			if (ln >= 0 && ln < tab.getLineCount()) {
				tab.scrollRectToVisible(tab.modelToView(tab
						.getLineStartOffset(ln)));
				tab.setCaretPosition(tab.getLineStartOffset(ln));
			}
		} catch (Exception e1) {
		}
  }
}
