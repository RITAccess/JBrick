package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.util.prefs.Preferences;

import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

import com.jbricx.swing.ui.preferences.PreferenceStore;

public class JBricxStatusPane extends JTabbedPane {
	JEditorPane messagePane;
	private Preferences prefs;
	
	public JBricxStatusPane(){
		messagePane = new JEditorPane();
		messagePane.setEnabled(false);
		messagePane.setBackground(Color.WHITE);
		messagePane.setDisabledTextColor(Color.BLACK);
		prefs = PreferenceStore.getPrefs();
		messagePane.setFont(Font.decode(prefs.get(PreferenceStore.FONT, PreferenceStore.FONT_DEFAULT)));
		
		this.addTab("Status", messagePane);
	}

	/**
	 * Proposed function for pushing a message to the console. 
	 * Not tested yet how to put links from compiler errors to jump to line. 
	 * @param message Message to **append** to the current text.
	 */
	public void pushMessage(String message){
		Document doc = messagePane.getDocument();
		try {
			doc.insertString(doc.getLength(), message + "\n", null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clearOldMessages() {
		Document doc = messagePane.getDocument();
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void refresh(){
		messagePane.setFont(Font.decode(prefs.get(PreferenceStore.FONT, PreferenceStore.FONT_DEFAULT)));
	}
}
