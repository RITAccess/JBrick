package com.jbricx.help;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XMLSWTListBuilder{

	private class SAX extends DefaultHandler{
	
		@Override
		public void startElement(String uri, String localName, String rawName, Attributes attributes){
			if(rawName.equals("toc")){
				System.out.println("Inside TOC");
			}
			else if(rawName.equals("item")){
				System.out.println("Inside Item");
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String qname){
			if(qname.equals("toc")){
				System.out.println("Leaving TOC");
			}
			else if(qname.equals("item")){
				System.out.println("Leaving Item");
			}
		}
		
		@Override
		public void endDocument(){
			System.out.println("End of doc");
		}
		
		@Override
		public void characters(char[] ch, int start, int length) {
			System.out.println("Characters: " + new String(ch, start, length));
		}
	}
	
	public void readXML(String[] args){

            SAX SAXHandler = new SAX();

            SAXParserFactory factory = SAXParserFactory.newInstance();
            try {

                 
                  SAXParser saxParser = factory.newSAXParser();
                  if(  System.getProperty("os.name").contains("indow") ){
                	  //windows
                	  saxParser.parse( new File(System.getProperty("user.dir")+"\\help\\toc.xml"), SAXHandler );

                  }
                  else{
                	  //linux
                	  saxParser.parse( new File(System.getProperty("user.dir")+"/help/toc.xml"), SAXHandler );

                  }
                  
            } catch (Throwable err) {
                  err.printStackTrace ();
            }

	}

	public static void main(String[] args){
		System.out.println(System.getProperty("user.dir"));
		XMLSWTListBuilder xmlb = new XMLSWTListBuilder();
		xmlb.readXML(args);
	}

	
}
