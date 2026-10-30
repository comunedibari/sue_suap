package it.wego.cross.cross_tomcat_services.utils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JNDINodeReader {

	private String driver;
	private String url;
	private String user;
	private String pwd;
	
	public JNDINodeReader(File file) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();

		NodeList list = doc.getElementsByTagName(Constants.JNDI_NODE_RESOURCE);

		for (int temp = 0; temp < list.getLength(); temp++) {

			Node node = list.item(temp);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String databaseName = node.getAttributes().getNamedItem(Constants.JNDI_ATTRIBUTE_DATABASE_NAME).getTextContent();
				if(Constants.JNDI_DATABASE_NAME.equals(databaseName)) {
					driver = node.getAttributes().getNamedItem(Constants.JNDI_ATTRIBUTE_DATABASE_DRIVER).getTextContent();
					url = node.getAttributes().getNamedItem(Constants.JNDI_ATTRIBUTE_DATABASE_URL).getTextContent();
					user = node.getAttributes().getNamedItem(Constants.JNDI_ATTRIBUTE_DATABASE_USER).getTextContent();
					pwd = node.getAttributes().getNamedItem(Constants.JNDI_ATTRIBUTE_DATABASE_PWD).getTextContent();
					
				}
			}
		}
	}

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPwd() {
		return pwd;
	}	
}
