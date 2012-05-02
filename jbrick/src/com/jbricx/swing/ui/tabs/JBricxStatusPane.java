package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.jbricx.swing.communications.CompilerError;
import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.preferences.PreferenceStore;

@SuppressWarnings("serial")
public class JBricxStatusPane extends JTabbedPane implements HyperlinkListener {
	JEditorPane messagePane;
	private Preferences prefs;
	private JBricxEditorTabFolder tab;

	public JBricxStatusPane(MainWindow main) {
		tab = main.getTabFolder();
		messagePane = new JEditorPane();
		messagePane.setEditable(false);
		messagePane.setBackground(Color.WHITE);
		messagePane.setDisabledTextColor(Color.BLACK);
		prefs = PreferenceStore.getPrefs();
		messagePane.setFont(Font.decode(prefs.get(PreferenceStore.FONT,
				PreferenceStore.FONT_DEFAULT)));
		messagePane.setContentType("text/html");

		this.addTab("Status", new JScrollPane(messagePane,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
	}

	/**
	 * Proposed function for pushing a message to the console. Not tested yet
	 * how to put links from compiler errors to jump to line.
	 * 
	 * @param list
	 *            Message to **append** to the current text.
	 */
	public void pushMessage(List<CompilerError> list) {
		StringBuffer sb = new StringBuffer();
		for (CompilerError ce : list) {
			String m = ce.toString();
			String[] mess = m.split(":");
			m = "<a href=\"" + mess[0] + "\">" + mess[0] + "</a>:";
			for (int i = 1; i < mess.length; ++i) {
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
			e.printStackTrace();
		}
	}

	public void refresh() {
		messagePane.setFont(Font.decode(prefs.get(PreferenceStore.FONT,
				PreferenceStore.FONT_DEFAULT)));
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent arg0) {
		int ln = Integer.parseInt(arg0.getDescription().split(" ")[3]
				.split(":")[0]);

		JBricxTabItem tab = (JBricxTabItem) ((JScrollPane) this.tab
				.getSelectedComponent()).getViewport().getView();
		try {
			ln -= 1;
			if (ln >= 0 && ln < tab.getLineCount()) {
				tab.scrollRectToVisible(tab.modelToView(tab
						.getLineStartOffset(ln)));
				tab.setCaretPosition(tab.getLineStartOffset(ln));

				// TODO this will not jump to the right file if it isn't currently open
				this.tab.getSelection().requestFocusInWindow();
			}
		} catch (Exception e1) {
		}
	}
}
