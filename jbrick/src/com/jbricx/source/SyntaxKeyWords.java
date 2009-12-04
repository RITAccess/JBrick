package com.jbricx.source;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This class contains the syntax keywords
 */
public class SyntaxKeyWords {
	public static  ArrayList<String> KEYWORDS ;

	/**
	 * lazy creation of the constant list. if the list is already populated just
	 * return the list else populate the list
	 * 
	 * @return
	 */
	public static ArrayList<String> getKeyWords() {
		if (KEYWORDS == null) {
			KEYWORDS = new ArrayList<String>();

			try {
				String xmlFilePath = "src\\config\\KeyWords.xml";
				File file = new File(xmlFilePath);
				if (file.exists()) {
					// Create a factory
					DocumentBuilderFactory factory = DocumentBuilderFactory
							.newInstance();
					// Use the factory to create a builder
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document doc = builder.parse(xmlFilePath);
					// Get a list of all elements in the document
					NodeList list = doc.getElementsByTagName("word");
					
					for (int i = 0; i < list.getLength(); i++) {
						// Add elements from xml file to the list of constant
						// words


						KEYWORDS.add(((Element) list.item(i)).getTextContent());
					}
				} else {
					System.out.print("File not found!");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return KEYWORDS;
	}
}
