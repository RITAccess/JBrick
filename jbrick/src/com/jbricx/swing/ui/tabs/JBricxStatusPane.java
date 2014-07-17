package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
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
	JTextPane messagePane;
	private JBricxEditorTabFolder tab;
	// Using this as a hack because preferences update too many times(one update
	// fired per preference changed.)
	private int scrollIncrease = 10;
	private MainWindow main;

	public JBricxStatusPane(MainWindow main) {
		this.main = main;
		
		//returns the editor pane as a whole
		tab = main.getTabFolder();
		messagePane = new JTextPane();
		messagePane.setEditable(false);
		messagePane.setBackground(new Color(Integer.parseInt(PreferenceStore.getString(Preference.BACKGROUND))));
		messagePane.setContentType("text/html");
		
		// set up the scrollpane
		JScrollPane status = new JScrollPane(messagePane,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
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
	public void pushMessage(HashMap<String, ArrayList<String>> map, boolean download) {
		messagePane.setText("");
		StringBuffer sb = new StringBuffer();
		
		Color color = new Color(Integer.parseInt(PreferenceStore.getString(Preference.CONSTANT)));
		String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
		
		for (String file : map.keySet()){
			File programFile = new File(file);
			if (programFile.exists()){
				sb.append(String.format(
						"<a style=\"color:" + hex  + "\" href=\"%s\">%s</a><br>", 
						programFile.getAbsolutePath(), programFile.getName() // file path, file name
				));
			} else {
				sb.append(file + "\n"); // not a file, some other message or error
			}
			for (String error : map.get(file)){
				Matcher match = Pattern.compile("(Error line ([0-9]*)): (.*)").matcher(error);
				if (match.matches()) {
					sb.append(String.format(
							"<a style=\"color:" + hex  + "\" href=\"%s,%s\">%s</a> %s <br>", 
							programFile, match.group(2), match.group(1), match.group(3)
					));
				}
			}
		}
		if (map.keySet().size() == 0){
			sb.append((download ? "Download" : "Compile") + " Successful");
		}
		messagePane.addHyperlinkListener(this);
		Font newFont = Font.decode(PreferenceStore.getString(Preference.FONT));
		color = new Color(Integer.parseInt(PreferenceStore.getString(Preference.FOREGROUND)));
		hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
		
		messagePane.setText("<p style=\"font-size:" + newFont.getSize()
				+ "px; font-family:" + newFont.getFamily() + "; color:" + hex + "\">" + sb.toString() + "</p>");
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
		messagePane.setBackground(new Color(Integer.parseInt(PreferenceStore.getString(Preference.BACKGROUND))));
	}
	

	@Override
	public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent) {
		HyperlinkEvent.EventType type = hyperlinkEvent.getEventType();
		if (type == HyperlinkEvent.EventType.ACTIVATED){
			// if the hyperlink / text is an int
			String desc = hyperlinkEvent.getDescription();
			int split = desc.indexOf(",");
			// open file. (if a file is all that is given, the split int will be -1)
			this.main.openTab(desc.substring(0,split == -1 ? desc.length() : split));	
			// go to line in file
			if (desc.matches(".*,\\d+")){
				int ln = Integer.parseInt(desc.substring(split + 1));

				JBricxTabItem tab = (JBricxTabItem) ((JScrollPane) this.tab
						.getSelectedComponent()).getViewport().getView();
				try {
					ln -= 1;
					if (ln >= 0 && ln < tab.getLineCount()) {
						tab.scrollRectToVisible(tab.modelToView(tab
								.getLineStartOffset(ln)));
						tab.setCaretPosition(tab.getLineStartOffset(ln));
					}
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
