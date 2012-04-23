package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.util.prefs.Preferences;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.jbricx.swing.ui.preferences.PreferenceStore;

@SuppressWarnings("serial")
public class JBricxStatusPane extends JTabbedPane {
	JEditorPane messagePane;
	private Preferences prefs;
	
	public JBricxStatusPane(){
		messagePane = new JEditorPane();
		messagePane.setEnabled(false);
		messagePane.setBackground(Color.WHITE);
		messagePane.setDisabledTextColor(Color.BLACK);
		prefs = PreferenceStore.getPrefs();
		messagePane.setFont(Font.decode(prefs.get(PreferenceStore.FONT,
				PreferenceStore.FONT_DEFAULT)));
		
		this.addTab("Status", new JScrollPane(messagePane,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
	}

	/**
	 * Proposed function for pushing a message to the console. 
	 * Not tested yet how to put links from compiler errors to jump to line. 
	 * @param message Message to **append** to the current text.
	 */
	public void pushMessage(String message){
		Document doc = messagePane.getDocument();
		try {
			System.out.println("\nMessage: " + message);
			if(message.contains("Error on line")) {
				for(String s : message.split(" ")) 
					System.out.println(s);
//				int ln = Integer.parseInt(message.split(" ")[5]);
//				System.out.println(ln);
			}
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
		messagePane.setFont(Font.decode(prefs.get(PreferenceStore.FONT,
				PreferenceStore.FONT_DEFAULT)));
	}
}
