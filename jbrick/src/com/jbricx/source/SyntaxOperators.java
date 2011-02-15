package com.jbricx.source;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jbricx.pjo.FileExtensionConstants;

/**
 * This class contains the syntax keywords
 */
public class SyntaxOperators {
	public static  ArrayList<String> OPERATORS ;

	/**
	 * lazy creation of the constant list. if the list is already populated just
	 * return the list else populate the list
	 * 
	 * @return
	 */
	public static ArrayList<String> getKeyWords() {
		if (OPERATORS == null) {
			OPERATORS = new ArrayList<String>();

			try {
				final InputStream operatorStream = ClassLoader.getSystemResourceAsStream(
                FileExtensionConstants.OPERATORS_FILE);

        // Create a factory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Use the factory to create a builder
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(operatorStream);
        // Get a list of all elements in the document
        NodeList list = doc.getElementsByTagName("operator");

        for (int i = 0; i < list.getLength(); i++) {
          // Add elements from xml file to the list of constant
          // words
          OPERATORS.add(((Element) list.item(i)).getTextContent());
        }

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return OPERATORS;
	}
}
