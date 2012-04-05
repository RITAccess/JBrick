package com.jbricx.swing.actions;

import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.printpreview.PrintPreview;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import com.jbricx.swing.ui.JBricxManager;

/**
 * This class shows an About box
 */
public class PrintPreviewAction extends JBricxAbstractAction {

  /**
   * AboutAction constructor
   */
  public PrintPreviewAction(final JBricxManager manager) {
	  super("", new ImageIcon("./resources/images/document-printpreview.png"), manager);
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

    String fontProp = PreferenceStore.getPrefs().get(PreferenceStore.FONT, PreferenceStore.FONT_DEFAULT);
    Font.decode(fontProp);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setSize(800, 600);
    f.add(new JScrollPane(ep));
    f.setVisible(true);
    pp.showPreview(ep);
    f.dispose();
    
  }
}
