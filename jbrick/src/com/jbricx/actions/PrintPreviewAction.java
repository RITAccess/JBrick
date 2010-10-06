package com.jbricx.actions;

/*
 * @author Yuji Fujiki
 */
import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.FontData;

import com.jbricx.pjo.FileExtensionConstants;
import com.jbricx.pjo.JBrickEditor;
import com.jbricx.printpreview.PrintPreview;

/**
 * This class shows an About box
 */
public class PrintPreviewAction extends Action {
	/**
	 * AboutAction constructor
	 */
	public PrintPreviewAction() {
		super("&PrintPreview@Ctrl+Shift+P", ImageDescriptor.createFromFile(
				PrintAction.class, "/images/document-printpreview.png"));
		setToolTipText("Print Preview");
	}

	/**
	 * Shows an about box
	 */
	public void run() {
        final PrintPreview pp = new PrintPreview();
        final JEditorPane ep = new JEditorPane();
        final JFrame f = new JFrame("Print Preview");
        ep.setEditable(false);
        ep.setText(JBrickEditor.getInstance().getMainWindow().getCurrentTabItem().getViewer().getTextWidget().getText());

        PreferenceStore store =  JBrickEditor.getInstance().getPreferences();
        String fontProp = store.getString(FileExtensionConstants.FONT);
		if (fontProp.length() > 0) { /* Check if the font is available */
			FontData[] fd = new FontData[1];
			fd[0] = new FontData(fontProp);
			Font toAwtFont = new java.awt.Font(fd[0].getName(), fd[0].getStyle(), fd[0].getHeight());
			ep.setFont(toAwtFont) ;
		}        
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(800, 600);
        f.add(new JScrollPane(ep));
        f.setVisible(true);
        pp.showPreview(ep);
        f.dispose() ;
	}
	
}
