package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;

import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;



@SuppressWarnings("serial")
public class JBricxStatusPane extends JTabbedPane implements HyperlinkListener, KeyListener, ActionListener{
	JPanel messagePane;
	JScrollPane status;
	List<String> errorLine;
	private JBricxEditorTabFolder tab;
	private MainWindow main;

	// Hack created as result of too many preference updates (One fired per change) 
	private int scrollIncrease = 10;
	
	public JBricxStatusPane(MainWindow main) {
		
		this.main = main;
		
		// Create the status pane as a whole
		tab = main.getTabFolder();
		messagePane = new JPanel();
		messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.Y_AXIS));
		
		// Set up the scrollpane for the status area 
		status = new JScrollPane(messagePane, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			increaseScrollBarSize(status);
			
		// Add the components to the tabpane
			this.addTab("Status",status);
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
	 * Set the status pane back to blank 
	 * 
	 */
	public void refresh() {
//		Document doc = messagePane.getDocument();
//		try {
//			
//			doc.remove(0, doc.getLength());
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		}
//		messagePane.setBackground(new Color(Integer.parseInt(PreferenceStore.getString(Preference.BACKGROUND))));
	}
	
	Color color = new Color(Integer.parseInt(PreferenceStore.getString(Preference.CONSTANT)));
	String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
	
	public void pushMessage(HashMap<String, ArrayList<String>> map, boolean download) {
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
			JButton button = new JButton(
					"<html><p style=\"font-size:" + newFont.getSize()
					+ "px; font-family:" + newFont.getFamily() + "; color:" + hex + "\">" + str.toString() + "</p></html>"
				);
			button.setBorder(BorderFactory.createEmptyBorder());
			button.setHorizontalAlignment(SwingConstants.LEFT);
			//button.setBackground(Color.WHITE);
			if (messages != null){
				button.getAccessibleContext().setAccessibleName(messages.get(i));
			}
			button.addActionListener(this);
			
			messagePane.add(button);
			messagePane.validate();
		}
		
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
	public void keyPressed(KeyEvent keyEv) {
		if (keyEv.getKeyCode() == KeyEvent.VK_ENTER) {
			
			//String desc = messagePane.getText();
			//System.out.println(desc);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent keyEv) {
		
	}

	/**
	 * Create the hyperlink for the errors 
	 */
	@Override
	public void hyperlinkUpdate(HyperlinkEvent hyperEv) {
		
		HyperlinkEvent.EventType type = hyperEv.getEventType();
		if (type == HyperlinkEvent.EventType.ACTIVATED){
			// if the hyperlink / text is an int
			String desc = hyperEv.getDescription();
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// if the hyperlink / text is an int
		String desc = ((AbstractButton) arg0.getSource()).getText();
		Matcher match = Pattern.compile("href=\"(.+)\">").matcher(desc);
		if (match.find()) { desc = match.group(1); } 
		System.out.println(desc);
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
