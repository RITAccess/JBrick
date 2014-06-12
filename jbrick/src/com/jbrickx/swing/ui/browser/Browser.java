package com.jbrickx.swing.ui.browser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class Browser
{
  public static void main(String[] args)
  {
    new Browser();
  }
  
  public Browser()
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        // create jeditorpane
        JEditorPane jEditorPane = new JEditorPane();
        
        // make it read-only
        jEditorPane.setEditable(false);
        
        // create a scrollpane; modify its attributes as desired
        JScrollPane scrollPane = new JScrollPane(jEditorPane);
        
        // add an html editor kit
        HTMLEditorKit kit = new HTMLEditorKit();
        jEditorPane.setEditorKit(kit);
        
        // create a document, set it on the jeditorpane, then add the html
        jEditorPane.setDocument(getHTMLFromFile("help/html/help.html"));
        
        // add some styles to the html
        StyleSheet sheet = kit.getStyleSheet();
        sheet.addStyleSheet(getCSSFromFile("help/html/help.css"));
        kit.setStyleSheet(sheet);
        
        System.out.println(kit.getActions()[0].toString());

        // now add it all to a frame
        JFrame j = new JFrame("HtmlEditorKit Test");
        j.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // make it easy to close the application
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // display the frame
        j.setSize(new Dimension(800,600));
        
        // pack it, if you prefer
        //j.pack();
        
        // center the jframe, then make it visible
        j.setLocationRelativeTo(null);
        j.setVisible(true);
      }
    });
  }

  public static StyleSheet getCSSFromFile(String filePath)
  {
	try 
	{
		File file = new File(filePath);
		System.out.println(file.getAbsolutePath() + " , " + file.exists());
		URL url = file.toURI().toURL();
		Reader reader = new InputStreamReader(new FileInputStream(file));
		StyleSheet sheet = new StyleSheet();
		sheet.loadRules(reader, url);
		return sheet;
		
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	return null;
  }
  
  public static Document getHTMLFromFile(String filePath)
  {
		File file = new File(filePath);
		HTMLEditorKit kit = new HTMLEditorKit();
		Document doc = kit.createDefaultDocument();
		doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
		
		System.out.println(file.getAbsolutePath() + " , " + file.exists());
		try
		{
			Reader reader = new InputStreamReader(new FileInputStream(file));
			kit.read(reader, doc, 0);
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			doc = null;
		}
		
		return doc;   
  }
}