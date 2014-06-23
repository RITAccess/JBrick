package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JEditorPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;

@SuppressWarnings("serial")
public class JBricxStatusPane extends JTabbedPane implements HyperlinkListener {
	JEditorPane messagePane;
	private Preferences prefs;
	private JBricxEditorTabFolder tab;
	// Using this as a hack because preferences update too many times(one update
	// fired per preference changed.)
	private int timesRefreshed = 0;
	private int scrollIncrease = 10;

	public JBricxStatusPane(MainWindow main) {
		tab = main.getTabFolder();
		messagePane = new JEditorPane();
		messagePane.setEditable(false);
		messagePane.setBackground(Color.WHITE);
		messagePane.setDisabledTextColor(Color.BLACK);
		prefs = PreferenceStore.getPrefs();
		messagePane.setFont(Font.decode(PreferenceStore.getString(Preference.FONT)));
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
		JScrollBar hScrollBar = sp.getHorizontalScrollBar();
        Dimension hScrollBarDim = new Dimension(hScrollBar.getPreferredSize().width,
        		hScrollBar.getPreferredSize().height + scrollIncrease);
        hScrollBar.setPreferredSize(hScrollBarDim);
	}

	/**
	 * Proposed function for pushing a message to the console. Not tested yet
	 * how to put links from compiler errors to jump to line.
	 * 
	 * @param strings
	 *            Message to **append** to the current text.
	 */
	public void pushMessage(String[] strings) {
		messagePane.setText("");
		StringBuffer sb = new StringBuffer();
		if (strings.length > 0) {
			for (String m : strings) {
				System.out.println(m);
				Matcher match = Pattern.compile("(Error line ([0-9]*)): (.*)").matcher(m);
				if (match.matches()) {
					sb.append(String.format(
							"<a href=\"%s\">%s</a> %s <br>", 
							match.group(2), match.group(1), match.group(3)
					));
				}
			}
		} else {
			sb.append("Compile Successful");
		}
		messagePane.addHyperlinkListener(this);
		Font newFont = Font.decode(PreferenceStore.getString(Preference.FONT));
		messagePane.setText("<p style=\"font-size:" + newFont.getSize()
				+ "px\">" + sb.toString() + "</p>");
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
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent lineNumberLink) {
		int ln = Integer.parseInt(lineNumberLink.getDescription());

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
