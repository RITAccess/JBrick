package com.jbricx.swing.actions;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import org.fife.ui.rtextarea.RTextScrollPane;

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
	  super("", new ImageIcon("./resources/images/edit-find.png"), manager);
  }

  /**
   * Runs the action
   */
  public void actionPerformed(ActionEvent arg0) {
	  JBricxTabItem tab = getTab();
	  GoToDialog dlg = new GoToDialog(tab.getLineCount(),this,getManager().getShell());
	  dlg.setVisible(true);
	  dlg.pack();
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
