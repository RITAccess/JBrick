package com.jbricx.swing.ui.browser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import com.jbricx.swing.ui.JBricxManager;

@SuppressWarnings("serial")
public class Browser extends JDialog implements ActionListener
{
	//Main window
	JFrame jWindow;
	JScrollPane scrollPane;
	
	JEditorPane jHelpPanel; //Page
	JPanel jLeftPanel; //Sidebar
	
	//Buttons
	JButton homeButton;
	JButton basicsButton;
	JButton tutorialButton;
	JButton joystickButton;
	JButton pianoButton;
	JButton legalButton;
	JButton shortcutsButton;
	
	//HTML stuff
	HTMLEditorKit kit;
	Font font;
	
  /**
   * A browser for the help documents.
   */
  public Browser(JBricxManager manager){
		super(manager.getShell(),"Preferences",true);
		
	  font = Font.decode("Consolas-plain-20");
	  
      // now add it all to a frame
      jWindow = new JFrame("HtmlEditorKit Test");
      // display the frame
      jWindow.setSize(new Dimension(800,600));

      makeComponents();
      loadPage("Home");
      loadPage("Home");
      
      jWindow.setDefaultCloseOperation(HIDE_ON_CLOSE);;
      
      // center the jframe, then make it visible
      jWindow.setLocationRelativeTo(null);
      jWindow.setVisible(true);
  }
  
  /**
   * Loads a HTML and CSS file to the page.
   * @param pageName
   */
  private void loadPage(String pageName)
  {
      // create a document, set it on the jeditorpane, then add the html
	  try
	  {
	      jHelpPanel.setDocument(getHTMLFromFile("help/html/" + pageName + ".html"));
	  }
	  catch(Exception e)
	  {
    	  System.err.print("HTML file not found!");
	  }
      
      // add some styles to the html
      StyleSheet sheet = kit.getStyleSheet();
      try
      {
          sheet.addStyleSheet(getCSSFromFile("help/css/" + pageName + ".css"));
      }
      catch(Exception e)
      {
    	  System.err.print("CSS file not found!");
      }
      kit.setStyleSheet(sheet);
  }
  
  private void makeComponents()
  {
	  makePageView();
	  makeSideBar();
	  makeButtons();
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
  }
  
  /**
   * Creates the sidebar.
   */
  private void makeSideBar()
  {
	  jLeftPanel = new JPanel();
      jLeftPanel.setLayout(new BoxLayout(jLeftPanel, BoxLayout.Y_AXIS));
      jWindow.getContentPane().add(jLeftPanel, BorderLayout.WEST);
  }
  
  /**
   * Sets a button to a set of default values.
   * @param button
   * @param name of button
   */
  private void createButton(JButton button, String name)
  {
	  button.setActionCommand(name);
	  button.addActionListener(this);
	  button.setBorderPainted(false);
	  button.setForeground(Color.blue);
	  button.setFont(font);
	  button.getAccessibleContext().setAccessibleName(name + " page. Click to go to the " + name + " page");
      jLeftPanel.add(button);
  }

  /**
   * Loads a Style sheet from a .css file.
   * @param filePath
   * @return StyleSheet
   */
  private static StyleSheet getCSSFromFile(String filePath)
  {
	try 
	{
		File file = new File(filePath);
		URL url = file.toURI().toURL();
		Reader reader = new InputStreamReader(new FileInputStream(file));
		StyleSheet sheet = new StyleSheet();
		sheet.loadRules(reader, url);
		return sheet;
		
	} 
	catch (Exception e) 
	{
		System.err.println(e.getLocalizedMessage());
	}
	return null;
  }

  /**
   * Sets up buttons.
   */
  private void makeButtons()
  {
	  homeButton = new JButton("Home");
	  createButton(homeButton, homeButton.getText());

      basicsButton = new JButton("Basics");
      createButton(basicsButton, basicsButton.getText());

      tutorialButton = new JButton("Tutorial");
      createButton(tutorialButton, tutorialButton.getText());

      joystickButton = new JButton("Joystick");
      createButton(joystickButton, joystickButton.getText());

      pianoButton = new JButton("Piano");
      createButton(pianoButton, pianoButton.getText());

      shortcutsButton = new JButton("Shortcuts");
      createButton(shortcutsButton, shortcutsButton.getText());
      
      legalButton = new JButton("Legal");
      createButton(legalButton, legalButton.getText());
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
		try
		{
			Reader reader = new InputStreamReader(new FileInputStream(file));
			kit.read(reader, doc, 0);
			return doc;
		}
		catch(Exception e)
		{
			System.err.println(e.getLocalizedMessage());
		}
		return null;
  }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Home"))
		{
			loadPage("Home");
		}
		else if(arg0.getActionCommand().equals("Basics"))
		{
			loadPage("Basics");
		}
		else if(arg0.getActionCommand().equals("Tutorial"))
		{
			loadPage("Tutorial");
		}
		else if(arg0.getActionCommand().equals("Joystick"))
		{
			loadPage("Joystick");
		}
		else if(arg0.getActionCommand().equals("Piano"))
		{
			loadPage("Piano");
		}
		else if(arg0.getActionCommand().equals("Shortcuts"))
		{
			loadPage("Shortcuts");
		}
		else if(arg0.getActionCommand().equals("Legal"))
		{
			loadPage("Legal");
		}
	}
	
	public void reOpen(){
		jWindow.toFront();
		jWindow.setFocusable(true);
		jWindow.setFocusableWindowState(true);
	    jWindow.setVisible(true);
		jWindow.requestFocus();
		jWindow.repaint();
	}
}