package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;



@SuppressWarnings("serial")
public class JBricxStatusPane extends JTabbedPane {
	JPanel messagePane;
	JScrollPane status;
	List<String> errorLine;
	private MainWindow main;
	Color backgroundColor;
	Color altBackgroundColor;

	// Hack created as result of too many preference updates (One fired per change) 
	private int scrollIncrease = 10;
	
	public JBricxStatusPane(MainWindow main) {
		
		this.main = main;
		
		// Create the status pane as a whole
		messagePane = new JPanel();
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.Y_AXIS));
		
		// Set up the scrollpane for the status area 
		status = new JScrollPane(messagePane, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			increaseScrollBarSize(status);
		
		// Add the components to the tabpane
		this.addTab("Status",status);
		refresh();
	}

	/**
	 * Increase the border between the scrollbars and the edge of the screen
	 * @param JScrollPane
	 */
	private void increaseScrollBarSize(JScrollPane sp){
		
		// increase the width of the vertical border
				JScrollBar vScrollBar = sp.getVerticalScrollBar();
		        Dimension vScrollBarDim = new Dimension(vScrollBar.getPreferredSize().width+scrollIncrease,
		        		vScrollBar.getPreferredSize().height);
		        vScrollBar.setPreferredSize(vScrollBarDim);
		        
		        // increase the height of the horizontal border
				JScrollBar hScrollBar = sp.getHorizontalScrollBar();
		        Dimension hScrollBarDim = new Dimension(hScrollBar.getPreferredSize().width,
		        		hScrollBar.getPreferredSize().height + scrollIncrease);
		        hScrollBar.setPreferredSize(hScrollBarDim);
	}

	/**
	 * Set the status pane preferences from Preference Store
	 * 
	 */
	public void refresh() {
		
		this.backgroundColor = new Color(Integer.parseInt(PreferenceStore.getString(Preference.BACKGROUND)));
		Color textColor = new Color(Integer.parseInt(PreferenceStore.getString(Preference.FOREGROUND)));
		
		this.altBackgroundColor = new Color(
				(textColor.getRed() + backgroundColor.getRed()*3)/4,
				(textColor.getGreen() + backgroundColor.getGreen()*3)/4,
				(textColor.getBlue() + backgroundColor.getBlue()*3)/4);
		
		HTMLString.setFontAndColors(
				Font.decode(PreferenceStore.getString(Preference.FONT)),
				new Color(Integer.parseInt(PreferenceStore.getString(Preference.CONSTANT))),
				textColor
			);
		
		// Refresh all the button colors / fonts
		for (Component statusButton : messagePane.getComponents()){
			if (statusButton instanceof StatusButton){
				((StatusButton)statusButton).updateAltBackgroundColor(altBackgroundColor);
				((StatusButton)statusButton).updateTextStyle();
				
			}
		}
		messagePane.setBackground(this.backgroundColor);
	}
	
	/**
	 * push messages from compile errors to status pane
	 * @param map
	 * @param download
	 */
	public void pushMessage(HashMap<String, ArrayList<String>> map, boolean download) {
		messagePane.removeAll();
		StringBuffer sb = new StringBuffer();
		if (map.keySet().size() == 0){
			sb.append((download ? "Download" : "Compile") + " Successful");
		} else {
			errorMessage(map, sb);	
		}
		
		String[] stringBuffList = sb.toString().split("\n");
		for (int i = 0; i < stringBuffList.length; i++){
			String str = stringBuffList[i];
			StatusButton button;
			Matcher match = Pattern.compile("\\[(.+)\\],(.+)\\),?(.*)?").matcher(str.toString());
			if (match.find()){
				button = new StatusButton(
						match.group(1), // hyperlink link
						match.group(2), // hyperlink text
						match.group(3), // text text
						this.altBackgroundColor, // Background color
						main
					);
			} else {
				button = new StatusButton(
						"", // hyperlink text
						"", // hyperlink link
						str.toString(), // text text
						this.altBackgroundColor, // Background color
						main
					);
			}
			messagePane.add(button);
		}
		messagePane.repaint();
		messagePane.validate();
		messagePane.requestFocus();
	}

	public List<String> errorMessage(HashMap<String, ArrayList<String>> map, StringBuffer sb) {
		List<String> errorFile = new ArrayList<String>();
		List<String> messages = new ArrayList<String>();
		for (String file : map.keySet()){
			File programFile = new File(file);
			if (programFile.exists()){
				sb.append(String.format(
						"([%s],%s)\n", // file path, file name
						programFile.getAbsolutePath(), programFile.getName()
				));
				messages.add(programFile.getName());
			} else {
				sb.append(file + "\n"); // not a file, some other message or error
			}
			for (String error : map.get(file)){
				Matcher match = Pattern.compile("(Error line ([0-9]*)): (.*)").matcher(error);
				if (match.matches()) {
					sb.append(String.format(
							"([%s,%s],%s),%s\n", // program, line, hyperlinkText, text
							programFile, match.group(2), match.group(1), match.group(3)
					));
					errorFile.add(match.group(2));
					messages.add(error);
				}
			}
		} return messages;

	}
}

@SuppressWarnings("serial")
class StatusButton extends JButton implements MouseListener, KeyListener, FocusListener, ActionListener {
	
	MainWindow main;
	String hyperlinkText;
	String hyperlinkLink;
	String text;
	Font font;
	String hexColor;
	
	StatusButton(String hyperlinkLink, String hyperlinkText, String text, Color backgroundColor, MainWindow main){
		super(HTMLString.getHTMLString(hyperlinkLink, hyperlinkText, text));
		this.main = main;
		this.hyperlinkText = hyperlinkText;
		this.hyperlinkLink = hyperlinkLink;
		this.text = text;
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setHorizontalAlignment(SwingConstants.LEFT);
		this.setBackground(backgroundColor);
		this.setContentAreaFilled(false);
		this.getAccessibleContext().setAccessibleName(hyperlinkText + text);
		this.addActionListener(this);
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addFocusListener(this);
	}
	
	public void updateAltBackgroundColor(Color altBackground){
		this.setBackground(altBackground);
	}
	
	public void updateTextStyle(){
		this.setText(HTMLString.getHTMLString(hyperlinkLink, hyperlinkText, text));
	}
	
	public void updateText(String hyperlinkLink, String hyperlinkText, String text){
		this.hyperlinkText = hyperlinkText;
		this.hyperlinkLink = hyperlinkLink;
		this.text = text;
		this.updateTextStyle();
	}
	
	public void updateAccessText(String accessText){
		this.getAccessibleContext().setAccessibleName(accessText);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getClickCount() == 2){
			main.getTabFolder().getSelection().requestFocus();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		StatusButton.this.setContentAreaFilled(true);
		StatusButton.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		StatusButton.this.setContentAreaFilled(false);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
			// TODO go to line in doc.
			StatusButton.this.doClick();
			main.getTabFolder().getSelection().requestFocus();
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		StatusButton.this.setContentAreaFilled(true);
	}

	@Override
	public void focusLost(FocusEvent e) {
		StatusButton.this.setContentAreaFilled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// if the hyperlink / text is an int
		String[] link = hyperlinkLink.split(",");
		if (link.length > 1) {
			this.main.openTab(link[0], Integer.parseInt(link[1]) -1);
		} else if(!link[0].equals("")){
			this.main.openTab(link[0]);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
		
	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}

class HTMLString {
	
	static Font font;
	static Color hyperlinkColor;
	static Color textColor;
	
	public static void setFontAndColors(Font font, Color hyperlinkColor, Color textColor){
		HTMLString.font = font;
		HTMLString.hyperlinkColor = hyperlinkColor;
		HTMLString.textColor = textColor;
	}
	
	public static String getHTMLString(String hyperlinkLink, String hyperlinkText, String text) {
		return getHTMLString( font, hyperlinkColor, textColor, hyperlinkLink, hyperlinkText, text );
	}
	
	/**
	 * 
	 * @param font - Font to use
	 * @param hlColor - hyperlink color
	 * @param textColor - text color
	 * @param hyperlinkText - text to be hyperlinked
	 * @param hyperlinkLink - link that will be hyperlinked
	 * @param text - text (no hyperlink)
	 * @return
	 */
	public static String getHTMLString(Font font, Color hlColor, Color textColor,String hyperlinkLink, String hyperlinkText, String text) {
		return String.format(
				"<html>" +
				"<div style=\"font-size:%spx; font-family:%s; color:#%02x%02x%02x\">" +
				"<a style=\"color:#%02x%02x%02x\" href=\"%s\">%s</a> %s" +
				"</div>" +
				"</html>\n",
				font.getSize(), font.getFamily(), textColor.getRed(), textColor.getGreen(), textColor.getBlue(),
				hlColor.getRed(), hlColor.getGreen(), hlColor.getBlue(), hyperlinkLink, hyperlinkText, text
			);
	}
}
