package com.jbricx.swing.ui.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;

import com.jbricx.swing.ui.JBricxManager;
import com.jbricx.swing.ui.MainWindow;
import com.jbricx.swing.ui.preferences.PreferenceStore;
import com.jbricx.swing.ui.preferences.PreferenceStore.Preference;



@SuppressWarnings("serial")
public class JBricxStatusPane extends JTabbedPane implements HyperlinkListener, KeyListener{
	JTextPane messagePane;
	JScrollPane status;
	List<String> errorLine;
	private JBricxEditorTabFolder tab;
	private MainWindow main;

	// Hack created as result of too many preference updates (One fired per change) 
	private int scrollIncrease= 10;
	
	public JBricxStatusPane(MainWindow main) {
		
		this.main = main;
		
		// Create the status pane as a whole
		tab = main.getTabFolder();
		messagePane = new JTextPane();
		messagePane.setEditable(false);
		messagePane.setBackground(new Color(Integer.parseInt(PreferenceStore.getString(Preference.BACKGROUND))));
		messagePane.setContentType("text/html");
		
		// Set the caret as visible in the statsu area without it being editable and only when focused
		messagePane.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				messagePane.getCaret().setVisible(true);
			}
			@Override
			public void focusLost(FocusEvent e){
				messagePane.getCaret().setVisible(false);
			}
		});
		
		// Set up the scrollpane for the status area 
		status = new JScrollPane(messagePane, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			increaseScrollBarSize(status);
			
		// Add the components to the tabpane
			this.addTab("Status",status);
	}
	
	/**
	 * Check for a change in the caret position
	 */
	private void caretListener(){
		messagePane.addCaretListener(new CaretListener(){
			
			@Override
			public void caretUpdate(CaretEvent ev) {
				caretRow(ev.getDot(),(JTextComponent)ev.getSource());
			}
		});
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
	 * Return the row number that the caret is occupying
	 * @param position
	 * @param area
	 * @return
	 */
	private int caretRow(int position, JTextComponent area) {
		int rn = (position == 0)? 1:0;
		try {
			int offs = position;
			while (offs > 0) {
				offs = Utilities.getRowStart(area, offs)-1;
				rn++;
			}
		} catch (BadLocationException ev) {
		 ev.printStackTrace();
		}
		
		return rn;
	}

	/**
	 * Set the status pane back to blank 
	 * 
	 */
	public void refresh() {
		Document doc = messagePane.getDocument();
		try {
			
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		messagePane.setBackground(new Color(Integer.parseInt(PreferenceStore.getString(Preference.BACKGROUND))));
	}
	
	StringBuffer sb = new StringBuffer();
	Color color = new Color(Integer.parseInt(PreferenceStore.getString(Preference.CONSTANT)));
	String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
	
	public void pushMessage(HashMap<String, ArrayList<String>> map, boolean download) {
		messagePane.setText("");
		caretListener();
		
		errorMessage(map);
		
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

	public List<String> errorMessage(HashMap<String, ArrayList<String>> map) {
		List<String> errorFile = new ArrayList<String>();

		for (String file : map.keySet()){
			File programFile = new File(file);
			if (programFile.exists()){
				sb.append(String.format(
						"<a style=\"color:" + hex  + "\" href=\"%s\">%s</a><br>", 
						programFile.getAbsolutePath(), programFile.getName()  // file path, file name
				));
				//System.out.println(programFile.getName());
			} else {
				sb.append(file + "\n"); // not a file, some other message or error
			}
			for (String error : map.get(file)){
				Matcher match = Pattern.compile("(Error line ([0-9]*)): (.*)").matcher(error);
				if (match.matches()) {
					sb.append(String.format(
							"<a style=\"color:" + hex  + "\" href=\"%s,%s\">%s</a> %s" + "<br>", 
							programFile, match.group(2), match.group(1), match.group(3)
					));
					errorFile.add(match.group(2));
				}
			}	
			//System.out.println(errorFile);
		} return errorFile;

	}
	
	@Override
	public void keyPressed(KeyEvent keyEv) {
		if (keyEv.getKeyCode() == KeyEvent.VK_ENTER) {
			
			String desc = messagePane.getText();
			System.out.println(desc);
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
}
