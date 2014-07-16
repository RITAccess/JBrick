package com.jbricx.swing.ui.browser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import com.jbricx.swing.ui.preferences.PreferenceStore;

@SuppressWarnings("serial")
public class Browser extends JDialog
{
	//Main window
	static JFrame jWindow;
	JScrollPane scrollPane;
	
	static JEditorPane jHelpPanel; //Page
	JPanel jLeftPanel; //Sidebar
	static Boolean isOpen = false;
	
	//Buttons
	enum HelpButtons {
		HOME,
		BASICS,
		TUTORIAL,
		JOYSTICK,
		PIANO,
		LEGAL,
		SHORTCUTS,
		;
		
		JButton button;
		
		HelpButtons(){
			button = new JButton(toString().charAt(0) + toString().substring(1).toLowerCase());
			button.setActionCommand(button.getText());
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			//button.setForeground(Color.black);
			button.setFont(Font.decode("Consolas=plain-24"));
			button.setMargin(new Insets(0,0,0,0));
			button.getAccessibleContext().setAccessibleName(
					String.format("%s page. Click to go to the %s page.", button.getText(), button.getText())
				);
		}
	}
	
	//HTML stuff
	static HTMLEditorKit kit;
	Font font;
	
	/**
	 * A browser for the help documents.
	 */
	private Browser(JFrame window, String startingWindow){
		super(window,"Help",true);
		font = Font.decode(PreferenceStore.getString(PreferenceStore.Preference.FONT));
		  
		// now add it all to a frame
		jWindow = new JFrame("Help");
		// display the frame
		jWindow.setSize(new Dimension(800,600));
	
		makePageView();

		jLeftPanel = new JPanel();
		jLeftPanel.setLayout(new GridLayout(14, 1));
		for (final HelpButtons b : HelpButtons.values()){
			b.button.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Browser.this.loadPage(b.toString());
				}
				
			});
			jLeftPanel.add(b.button);
		}
		jWindow.getContentPane().add(jLeftPanel, BorderLayout.WEST);
		loadPage(startingWindow);
	    
		jWindow.setDefaultCloseOperation(HIDE_ON_CLOSE);
	    
		// center the jframe, then make it visible
		jWindow.setLocationRelativeTo(null);
		jWindow.setVisible(true);
		isOpen = true;
	}
  
  /**
   * Loads a HTML and CSS file to the page.
   * @param pageName
   */
  private static void loadPage(String pageName)
  {
	  kit.setStyleSheet(null);
      // create a document, set it on the jeditorpane, then add the html
	  try {
	      jHelpPanel.setDocument(getHTMLFromFile("help/html/" + pageName + ".html"));
	  } catch(Exception e) {
    	  System.err.print("HTML file not found!");
	  }
      
      // add some styles to the html
      StyleSheet sheet = kit.getStyleSheet();
      try {
          sheet.addStyleSheet(getCSSFromFile("help/css/" + pageName + ".css"));
      } catch(Exception e) {
    	  System.err.print("CSS file not found!");
      }

      kit.setStyleSheet(sheet);
  }
  
  /**
   * Creates the center field where the Help page will be displayed.
   */
  private void makePageView()
  {
      // create jeditorpane
      jHelpPanel = new JEditorPane();
      // make it read-only
      jHelpPanel.setEditable(false);
      // add an html editor kit
      kit = new HTMLEditorKit();
      jHelpPanel.setEditorKit(kit);
      // create a scrollpane; modify its attributes as desired
      scrollPane = new JScrollPane(jHelpPanel);
      jWindow.getContentPane().add(scrollPane, BorderLayout.CENTER);
      jHelpPanel.addHyperlinkListener(new HyperlinkListener(){

		@Override
		public void hyperlinkUpdate(HyperlinkEvent arg0) {
			if (arg0.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
				try {
					Desktop.getDesktop().browse(URI.create(arg0.getDescription()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    	  
      });
  }

  /**
   * Loads a Style sheet from a .css file.
   * @param filePath
   * @return StyleSheet
   */
  private static StyleSheet getCSSFromFile(String filePath)
  {
	try {
		File file = new File(filePath);
		URL url = file.toURI().toURL();
		Reader reader = new InputStreamReader(new FileInputStream(file));
		StyleSheet sheet = new StyleSheet();
		sheet.loadRules(reader, url);
		int fontSize = (int) (Font.decode(PreferenceStore.getString(PreferenceStore.Preference.FONT)).getSize()*.8); //The *.8 is to reduce the size to more closely match whats in the editor
		//apply font size to everything
		sheet.addRule("span {font-size: " + fontSize + "pt;}");
		sheet.addRule("p {font-size: " + fontSize + "pt;}");
		sheet.addRule("div {font-size: " + fontSize + "pt;}");
		sheet.addRule("h1 {font-size: " + fontSize + "pt;}");
		sheet.addRule("h2 {font-size: " + fontSize + "pt;}");
		sheet.addRule("h3 {font-size: " + fontSize + "pt;}");
		return sheet;
		
	} catch (Exception e) {
		System.err.println(e.getLocalizedMessage());
	}
	return null;
  }
  
  /**
   * Loads a HTML file into a document.
   * @param filePath
   * @return Document
   */
  public static Document getHTMLFromFile(String filePath)
  {
		File file = new File(filePath);
		HTMLEditorKit kit = new HTMLEditorKit();
		Document doc = kit.createDefaultDocument();
		doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
		try {
			Reader reader = new InputStreamReader(new FileInputStream(file));
			kit.read(reader, doc, 0);
			return doc;
		} catch(Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
		return null;
  }
	
  public static void openBrowser(JFrame window, String startingWindow){
		if (isOpen) {
			jWindow.toFront();
			jWindow.setFocusable(true);
			jWindow.setFocusableWindowState(true);
			loadPage(startingWindow);
		    jWindow.setVisible(true);
			jWindow.requestFocus();
			jWindow.repaint();
		} else {
			new Browser(window, startingWindow);
		}
		
	}
}