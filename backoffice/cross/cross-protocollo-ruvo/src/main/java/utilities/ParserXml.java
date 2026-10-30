package utilities;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParserXml {
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	Document document;
	
	public ParserXml(){
	
	}
	public void parser(String xml) {
		factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
			document = (Document) builder.parse(new InputSource(new StringReader(xml)));
			Element rootElement = document.getRootElement();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
	public static String getTagValue(String xml, String tagName){
	    return xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0];
	}
	
	//Attribute example: "/IdFolder"
	public static String getAttributeValue(String xml, String attribute) {
		String result="";
		/*
		Document doc = Jsoup.parse(xml, "", Parser.xmlParser()); //parse the whole xml doc
		for (Element e : doc.selectNodes("tagName")) {
		    System.out.println(e); //select the specific tag and prints
		}
		*/
		return result;
	}
}
