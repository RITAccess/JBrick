package com.jbricx.tools;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
	
	public static void overwrite(Document doc, Node newNode) {
		// root elements
		Element rootElement = doc.createElement("properties");
		doc.appendChild(rootElement);
				
		Element elementNode = doc.createElement(newNode.getNodeName());
		elementNode.appendChild(doc.createTextNode(newNode.getTextContent()));
		rootElement.appendChild(elementNode);
	}
	
	public static void writeToFile(Document doc, String filepath){
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			//StreamResult result = new StreamResult(new File(filepath));
	 
			// Output to console for testing
			StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String argv[]){
		
		Document newDoc = XMLParser.xmlParse("resources/config/Properties.xml");
		String query = "font";
		Node value = XMLParser.retrieve(newDoc,query,"size",0);
		System.out.println(query + ": " + value.getTextContent());
		query = "icon";
		Node value1 = XMLParser.retrieve(newDoc,query,"size");
		System.out.println(query + ": " + value1.getTextContent());
		
		Document newDoc2 = XMLParser.xmlParse("resources/config/Autocomplete.xml");
		Node value2 = XMLParser.retrieve(newDoc2,"keyWords","word",14);
		System.out.println("List: "+ value2.getTextContent());	
		XMLParser.overwrite(newDoc, value);
		XMLParser.writeToFile(newDoc, "");
	}
}
