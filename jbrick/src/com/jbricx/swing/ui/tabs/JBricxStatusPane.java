package com.jbricx.swing.ui.tabs;

import java.awt.Color;
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

import javax.swing.AbstractButton;
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
public class JBricxStatusPane extends JTabbedPane implements ActionListener {
	JPanel messagePane;
	JScrollPane status;
	List<String> errorLine;
	private MainWindow main;
	Color altBackground;
	Color background;

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
	 * Set the status pane back to black 
	 * 
	 */
	public void refresh() {
		this.background = new Color(Integer.parseInt(PreferenceStore.getString(Preference.BACKGROUND)));
		Color foreground = new Color(Integer.parseInt(PreferenceStore.getString(Preference.FOREGROUND)));
		this.altBackground = new Color(
				(foreground.getRed() + background.getRed()*3)/4,
				(foreground.getGreen() + background.getGreen()*3)/4,
				(foreground.getBlue() + background.getBlue()*3)/4);
		messagePane.setBackground(this.background);
	}
	
	Color color = new Color(Integer.parseInt(PreferenceStore.getString(Preference.CONSTANT)));
	String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
	
	public void pushMessage(HashMap<String, ArrayList<String>> map, boolean download) {
		messagePane.removeAll();
		StringBuffer sb = new StringBuffer();
		List<String> messages = null;
		if (map.keySet().size() == 0){
			System.out.println(sb.toString());
			sb.append((download ? "Download" : "Compile") + " Successful");
		}
		else{
			color = new Color(Integer.parseInt(PreferenceStore.getString(Preference.CONSTANT)));
			hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
			messages = errorMessage(map, sb);	
		}
		
		//messagePane.addHyperlinkListener(this);
		Font newFont = Font.decode(PreferenceStore.getString(Preference.FONT));
		color = new Color(Integer.parseInt(PreferenceStore.getString(Preference.FOREGROUND)));
		hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
		String[] stringBuffList = sb.toString().split("\n");
		for (int i = 0; i < stringBuffList.length; i++){
			String str = stringBuffList[i];
			final JButton button = new JButton(
					"<html><p style=\"font-size:" + newFont.getSize()
					+ "px; font-family:" + newFont.getFamily() + "; color:" + hex + "\">" + str.toString() + "</p></html>"
				);
			button.setBorder(BorderFactory.createEmptyBorder());
			button.setHorizontalAlignment(SwingConstants.LEFT);
			button.setBackground(altBackground);
			button.setContentAreaFilled(false);
			if (messages != null){
				button.getAccessibleContext().setAccessibleName(messages.get(i));
			}
			button.addActionListener(this);
			button.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (arg0.getClickCount() == 2){
						main.getTabFolder().getSelection().requestFocus();
					}
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					button.setContentAreaFilled(true);
					button.setCursor(new Cursor(Cursor.HAND_CURSOR));
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					button.setContentAreaFilled(false);
				}

				@Override
				public void mousePressed(MouseEvent arg0) {}

				@Override
				public void mouseReleased(MouseEvent arg0) {}
				
			});
			button.addKeyListener(new KeyListener(){

				@Override
				public void keyPressed(KeyEvent arg0) {
					if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
						// TODO go to line in doc.
						button.doClick();
						main.getTabFolder().getSelection().requestFocus();
					}
				}

				@Override
				public void keyReleased(KeyEvent arg0) {}

				@Override
				public void keyTyped(KeyEvent arg0) {}
				
			});
			button.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
					button.setContentAreaFilled(true);
				}

				@Override
				public void focusLost(FocusEvent e) {
					button.setContentAreaFilled(false);
				}
				
			});
			messagePane.add(button);
		}
		messagePane.repaint();
		messagePane.validate();
	}

	public List<String> errorMessage(HashMap<String, ArrayList<String>> map, StringBuffer sb) {
		List<String> errorFile = new ArrayList<String>();
		List<String> messages = new ArrayList<String>();
		for (String file : map.keySet()){
			File programFile = new File(file);
			if (programFile.exists()){
				sb.append(String.format(
						"<a style=\"color:" + hex  + "\" href=\"%s\">%s</a><br>\n", 
						programFile.getAbsolutePath(), programFile.getName()  // file path, file name
				));
				messages.add(programFile.getName());
				//System.out.println(programFile.getName());
			} else {
				sb.append(file + "\n"); // not a file, some other message or error
			}
			for (String error : map.get(file)){
				Matcher match = Pattern.compile("(Error line ([0-9]*)): (.*)").matcher(error);
				if (match.matches()) {
					sb.append(String.format(
							"<a style=\"color:" + hex  + "\" href=\"%s,%s\">%s</a> %s<br>\n", 
							programFile, match.group(2), match.group(1), match.group(3)
					));
					errorFile.add(match.group(2));
					messages.add(error);
				}
			}
		} return messages;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// if the hyperlink / text is an int
		String desc = ((AbstractButton) arg0.getSource()).getText();
		Matcher match = Pattern.compile("href=\"(.+)\">").matcher(desc);
		if (match.find()) { desc = match.group(1); }
		int split = desc.indexOf(",");
		
		// open file. (if a file is all that is given, the split int will be -1)
		this.main.openTab(desc.substring(0,split == -1 ? desc.length() : split));
		
		// go to line in file
		if (desc.matches(".*,\\d+")){
			final int ln = Integer.parseInt(desc.substring(split + 1)) - 1;
			// open file with linenumber
			this.main.openTab(desc.substring(0,split == -1 ? desc.length() : split), ln);
		} else {
			// open file. (if a file is all that is given, the split int will be -1)
			this.main.openTab(desc.substring(0,split == -1 ? desc.length() : split));
		}
	}
}
