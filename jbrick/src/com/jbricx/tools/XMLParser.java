package com.jbricx.tools;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

// Parser for XML documents 
// Version 2.0
 
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
			//System.err.println("Document could't be created");
			//e.printStackTrace();
		}
		
		return doc;   
	}
	
	public static Node retrieve(Document doc, String query, String tag) {
		return retrieve(doc, query, tag, 0);
	}
	
	// Retrieve information from an xml 
	public static Node retrieve(Document doc, String query, String tag, int item) {
		
		Node node = null;
		NodeList listDoc = doc.getElementsByTagName(query);
		
		for (int count = 0; count < listDoc.getLength(); count++){
			node = listDoc.item(count);
			Element eDoc = (Element)node;
			node = eDoc.getElementsByTagName(tag).item(item);
			
		}
		
		return node;
	}
	
	public static void main(String argv[]){
		
		Document newDoc = XMLParser.xmlParse("resources/config/Properties.xml");
		String query = "icon";
		Node value = XMLParser.retrieve(newDoc,query,"size",0);
		System.out.println(query + ": " + value.getTextContent());
		value = XMLParser.retrieve(newDoc,query,"size");
		System.out.println(query + ": " + value.getTextContent());
		
		Document newDoc2 = XMLParser.xmlParse("resources/config/Autocomplete.xml");
		Node value2 = XMLParser.retrieve(newDoc2,"keyWords","word",14);
		System.out.println("List: "+ value2.getTextContent());	
	}
}
