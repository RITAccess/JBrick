package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.jbricx.swing.communications.CompilerError;
import com.jbricx.swing.ui.preferences.PreferenceStore;

@SuppressWarnings("serial")
public class JBricxStatusPane extends JTabbedPane implements HyperlinkListener {
	JEditorPane messagePane;
	private Preferences prefs;
	
	public JBricxStatusPane(){
		messagePane = new JEditorPane();
		messagePane.setEditable(false);
		messagePane.setBackground(Color.WHITE);
		messagePane.setDisabledTextColor(Color.BLACK);
		prefs = PreferenceStore.getPrefs();
		messagePane.setFont(Font.decode(prefs.get(PreferenceStore.FONT,
				PreferenceStore.FONT_DEFAULT)));
		messagePane.setContentType("text/html");
		
		this.addTab("Status", new JScrollPane(messagePane,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
	}

	/**
	 * Proposed function for pushing a message to the console. 
	 * Not tested yet how to put links from compiler errors to jump to line. 
	 * @param list Message to **append** to the current text.
	 */
	public void pushMessage(List<CompilerError> list){
		Document doc = messagePane.getDocument();
		StringBuffer sb = new StringBuffer();
		for(CompilerError ce : list) {
			String m = ce.toString();
			int ln = Integer.parseInt(m.split(" ")[3].split(":")[0]);
			String[] mess = m.split(":");
			m = "<a href=\"error\">"+ mess[0] + "</a>:";
			for(int i = 1; i < mess.length; ++i) {
				m += mess[i];
				if (i == 1)
					m += ":";
			}
			m += "<br>";
			sb.append(m);
		}
		messagePane.addHyperlinkListener(this);
		messagePane.setText(sb.toString());
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
	
	class StatusPaneHyperlinkListener implements HyperlinkListener {
	    public void hyperlinkUpdate(HyperlinkEvent e) {
//	      if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
//	        StringTokenizer st = new StringTokenizer(e.getDescription(), " ");
//	        if (st.hasMoreTokens()) {
//	          String s = st.nextToken();
//	          System.err.println("token: " + s);
//	        }
//	      }
	    	System.out.println("Hyperlink event");
	    }
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent arg0) {
		// TODO Auto-generated method stub
    	System.out.println("Hyperlink event");
	}
}
