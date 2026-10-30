package it.wego.cross.mailservices.utilities;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParserXml {
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	private Element rootElement;

	public ParserXml(){

	}
	public void parser(String xml) {
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
			document = (Document) builder.parse(new InputSource(new StringReader(xml)));
			rootElement = document.getDocumentElement();
			((org.w3c.dom.Document) document).getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected String getString(String tagName, Element element) {
		NodeList list = ((org.w3c.dom.Document) element).getElementsByTagName(tagName);
		if (list != null && list.getLength() > 0) {
			NodeList subList = list.item(0).getChildNodes();

			if (subList != null && subList.getLength() > 0) {
				return subList.item(0).getNodeValue();
			}
		}

		return null;
	}

	public String getTagValue(String xml, String tagName){
		return xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0];
	}
	public String extractXml(String xml, String tagstart, String tagEnd){
		return xml.split("<"+tagstart+">")[1].split("</"+tagEnd+">")[0];
	}
	public String getAttributeValue(String node, String tagname) {
		String result="";

		try {
			NodeList nList = ((org.w3c.dom.Document) document).getElementsByTagName(node);
			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("Tagname : " + ((DocumentBuilderFactory) eElement).getAttribute(tagname));
					System.out.println("Valore : "  + ((org.w3c.dom.Document) eElement).getElementsByTagName(tagname).item(0).getTextContent());
					result = ((org.w3c.dom.Document) eElement).getElementsByTagName(tagname).item(0).getTextContent();
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getCharacterDataFromElement(Element e) {
		Node child = ((Node) e).getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}
	
	public static String escapeHTML(String s) {
	    StringBuilder out = new StringBuilder(Math.max(16, s.length()));
	    for (int i = 0; i < s.length(); i++) {
	        char c = s.charAt(i);
	        if (c > 127 || c == '"' || c == '\'' || c == '<' || c == '>' || c == '&') {
	            out.append("&#");
	            out.append((int) c);
	            out.append(';');
	        } else {
	            out.append(c);
	        }
	    }
	    return out.toString();
	}
	
	public String parseString(String xml, String tagnode, String tagname) throws Exception {

		String result = "";
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder  = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName(tagnode);
			
			int totalnode = nList.getLength();
			
			for (int s = 0; s < nList.getLength(); s++) {
				Node nNode = nList.item(s);
				Element eElement = (Element) nNode;
								
				NodeList nStatus = eElement.getElementsByTagName(tagname);
				Element eStatus = (Element) nStatus.item(0);
				NodeList textLNList = eStatus.getChildNodes();
				
				result = ((Node) textLNList.item(0)).getNodeValue().trim();
				System.out.println("Tagnode : "+ tagnode);
				System.out.println("Tagname : "+ tagname);
				System.out.println("Tagvalue : "+ ((Node) textLNList.item(0)).getNodeValue().trim());
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		String xml = "<data><employee><name>A</name>" + 
				"<title>Manager</title></employee></data>";

		ParserXml p = new ParserXml();
		p.parser(xml);
		String valore = p.getAttributeValue( "data", "title");
		try {
			valore = p.parseString(xml, "data", "title" );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Valore:" + valore);				
	}

}
