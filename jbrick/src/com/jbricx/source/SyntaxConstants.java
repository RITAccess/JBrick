package com.jbricx.source;

import java.io.*;
import java.util.ArrayList;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class SyntaxConstants {
	public static ArrayList<String> CONSTANTS ;

	/**
	 * lazy creation of the constant list. 
	 * if the list is already populated just return the 
	 * list else populate the list
	 * @return
	 */
	public static ArrayList<String> getKeyWords()
	{
		if(CONSTANTS == null){
			CONSTANTS = new ArrayList<String>();
		
		try {
			String xmlFilePath = "src\\config\\Constants.xml";
		       File file = new File(xmlFilePath);
		       if(file.exists()){
		         // Create a factory
		         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		         // Use the factory to create a builder
		         DocumentBuilder builder = factory.newDocumentBuilder();
		         Document doc = builder.parse(xmlFilePath);
		         // Get a list of all elements in the document
		         NodeList list = doc.getElementsByTagName("constant");
		        
		         for (int i=0; i<list.getLength(); i++) {
		           // Add elements from xml file to the list of constant words
		        	CONSTANTS.add(((Element)list.item(i)).getTextContent());
		        	 
		         }
		       }
		       else{
		         System.out.print("File not found!");
		       }
		     }
		     catch (Exception e) {
		       System.out.println(e.getMessage());
		     }
		}
				
		return CONSTANTS;
	}

}
