package com.jbricx.swing.ui.preferences;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/* Parser for XML documents 
 * Version 2.0
 */

public class XMLParser {

	// Return document of parsed XML file
	public static Document xmlParse(String xmlFilePath){
		
		Document doc;
		
		// Attempt to parse XML file submitted
		try {
			
			File xmlFile = new File (xmlFilePath);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(xmlFile);
			
			doc.getDocumentElement().normalize();
			
		}
		
		// Print if XML couldn't be parsed
		catch (Exception e){
			doc = null;
			System.err.println("Document could't be created");
			e.printStackTrace();
		}
		return doc;
	    
	}
	
	// Retrieve specific information from the document based on query
	public static Node retrieve(Document doc,String query, String tag){
		
		Node node = null;
		
		NodeList listDoc = doc.getElementsByTagName(query);
		
		for (int count = 0; count < listDoc.getLength(); count++){
			 Node docNode = listDoc.item(count);
			
			if (docNode.getNodeType() == Node.ELEMENT_NODE){
				
				Element eDoc = (Element) docNode;
			    node = (eDoc.getElementsByTagName(tag).item(0));
				
			}
			
		}
		
		return node;
		
		
	}
	
	public static void main(String argv[]){
		
		Document newDoc = XMLParser.xmlParse("resources/config/Properties.xml");
		String query = "font";
		Node value = XMLParser.retrieve(newDoc,query,"name");
		System.out.println(query + ": " + value.getTextContent());
		
	}
}
