package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JEditorPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.plaf.FontUIResource;
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
	// Using this as a hack because preferences update too many times(one update
	// fired per preference changed.)
	private int timesRefreshed = 0;
	private int scrollIncrease = 5;
	private List<CompilerError> errorList;

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
		messagePane.getCaret().setVisible(true);
		
		// set up the scrollpane
		JScrollPane status = new JScrollPane(messagePane,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		increaseScrollBarSize(status);
		
		this.addTab("Status", status);
	}
	
	/**
	 * Increase the size of the scrollbars on a scrollpane
	 */
	private void increaseScrollBarSize(JScrollPane sp)
	{
		// increase the width of the vertical scroll bar
		JScrollBar vScrollBar = sp.getVerticalScrollBar();
        Dimension vScrollBarDim = new Dimension(vScrollBar.getPreferredSize().width+scrollIncrease,
        		vScrollBar.getPreferredSize().height);
        vScrollBar.setPreferredSize(vScrollBarDim);
        
        // increase the height of the horizontal scrollbar
		JScrollBar hScrollBar = sp.getVerticalScrollBar();
        Dimension hScrollBarDim = new Dimension(hScrollBar.getPreferredSize().width,
        		hScrollBar.getPreferredSize().height + scrollIncrease);
        hScrollBar.setPreferredSize(hScrollBarDim);
	}

	/**
	 * Proposed function for pushing a message to the console. Not tested yet
	 * how to put links from compiler errors to jump to line.
	 * 
	 * @param list
	 *            Message to **append** to the current text.
	 */
	public void pushMessage(List<CompilerError> list) {
		if (list != null) {
			errorList = list;
			StringBuffer sb = new StringBuffer();
			for (CompilerError ce : errorList) {
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
			Font newFont = Font.decode(prefs.get(PreferenceStore.FONT,
					PreferenceStore.FONT_DEFAULT));
			messagePane.setText("<p style=\"font-size:" + newFont.getSize()
					+ "px\">" + sb.toString() + "</p>");
		}
	}

	public void clearOldMessages() {
		Document doc = messagePane.getDocument();
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * We clear the list, then repush it with the existing error set. Easiest
	 * way to do this and change the font with html in mind.
	 */
	public void refresh() {
		clearOldMessages();
		pushMessage(errorList);
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

				// TODO this will not jump to the right file if it isn't
				// currently open
				this.tab.getSelection().requestFocusInWindow();
			}
		} catch (Exception e1) {
		}
	}
}
