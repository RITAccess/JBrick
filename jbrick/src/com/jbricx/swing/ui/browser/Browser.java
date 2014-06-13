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
	JButton page1Button;
	
	HTMLEditorKit kit;
	Font font;
	
  public static void main(String[] args)
  {
    new Browser();
  }
  /**
   * A browser for the help documents.
   */
  public Browser()
  {
	  font = Font.decode("Consolas-plain-20");
	  
      // now add it all to a frame
      jWindow = new JFrame("HtmlEditorKit Test");
      // make it easy to close the application
      jWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // display the frame
      jWindow.setSize(new Dimension(800,600));

      makeComponents();
      loadPage("Home");
      
      // pack it, if you prefer
      //j.pack();
      
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
          sheet.addStyleSheet(getCSSFromFile("help/html/" + pageName + ".css"));
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
   * Sets up buttons.
   */
  private void makeButtons()
  {
	  homeButton = new JButton("Home");
	  createButton(homeButton, homeButton.getText());
	  
      page1Button = new JButton("Help");
      createButton(page1Button, page1Button.getText());
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
		System.out.println(e.getLocalizedMessage());
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
		try
		{
			System.out.println("File Path: " + filePath);
			Reader reader = new InputStreamReader(new FileInputStream(file));
			kit.read(reader, doc, 0);
			return doc;
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		return null;
  }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Home"))
		{
			loadPage("Home");
			System.out.println("Home");
		}
		else if(arg0.getActionCommand().equals("Help"))
		{
			loadPage("Help");
			System.out.println("Help");
		}
	}
}