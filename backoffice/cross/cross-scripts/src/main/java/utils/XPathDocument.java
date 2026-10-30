package utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XPathDocument {

	private XPath xPath;
	private Document document;
	
	public XPathDocument(String data){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(data));
			document =  builder.parse(is);
			xPath = XPathFactory.newInstance().newXPath();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Map<String, String>> read(String nodePattern, String... childPatterns) {
		if(document == null)
			return Collections.emptyList();
		List<Map<String, String>> ret = new ArrayList<>();
		NodeList nodeList;
		try {
			nodeList = (NodeList) xPath.compile(nodePattern).evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Map<String, String> subMap = new HashMap<>();
				ret.add(subMap);
				Node currentNode = nodeList.item(i);
				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
					for(int j = 0; j < childPatterns.length; j++) {
						Object result = xPath.compile(childPatterns[j]).evaluate(currentNode, XPathConstants.NODE);
						if(result != null) {
							Node node = (Node)result;
							subMap.put(childPatterns[j], node.getTextContent());
						}
					}
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public List<String> read(String nodePattern, String childPattern) {
		if(document == null)
			return Collections.emptyList();
		List<String> ret = new ArrayList<>();
		NodeList nodeList;
		try {
			nodeList = (NodeList) xPath.compile(nodePattern).evaluate(document, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node currentNode = nodeList.item(i);
				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
					Object result = xPath.compile(childPattern).evaluate(currentNode, XPathConstants.NODE);
					if(result != null) {
						Node node = (Node)result;
						ret.add(node.getTextContent());
					}
					
				}
			}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String read(String pattern) throws XPathExpressionException {
		if(document == null)
			return null;
		Object result = xPath.compile(pattern).evaluate(document, XPathConstants.NODE);
		Node node = (Node)result;
		return (node.getTextContent());
	}
}
