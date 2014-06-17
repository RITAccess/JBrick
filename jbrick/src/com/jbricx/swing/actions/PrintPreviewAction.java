package com.jbricx.swing.actions;

import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.jbricx.printpreview.PrintPreview;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This class shows an About box
 */
@SuppressWarnings(value = { "serial" }) 
public class PrintPreviewAction extends JBricxAbstractAction {

  /**
   * AboutAction constructor
   */
  public PrintPreviewAction(final JBricxManager manager) {
	  super("", new ImageIcon(PrintPreviewAction.class.getResource("/images/document-printpreview.png")), manager);
  }

  /**
   * Shows an about box
   */
  public void actionPerformed(ActionEvent e) {
    final PrintPreview pp = new PrintPreview();
    final JEditorPane ep = new JEditorPane();
    final JFrame f = new JFrame("Print Preview");
    ep.setEditable(false);
    ep.setText(getManager().getTabFolder().getSelection().getText());

    Font.decode(PreferenceStore.getString(Preference.FONT));
    f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    f.setSize(800, 600);
    f.add(new JScrollPane(ep));
    f.setVisible(true);
    pp.showPreview(ep);
    f.dispose();
    
  }
}
