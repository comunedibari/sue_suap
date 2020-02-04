/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 **/
/*
 * Created on 24-gen-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.people.security.xml;

/**
 * Constructs a DOM document corresponding to sendData.dtd (for further
 * inforamtions consult relative documentation).
 *
 * @author 	Nunzio Ingraffia
 */

import it.people.security.Exception.XMLPacketException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLPacket extends Object implements Serializable {
    /**
     * Dati singoli gestiti nel gruppo INFO_DATA
     */
    public static final String INFO_POLCY = "PolicyID";
    public static final String INFO_USER = "UserID";
    public static final String INFO_EMAIL_RICEVUTE = "email_ricevuta";
    public static final String INFO_SESSION = "overwrite_session";

    /**
     * Tipi di dati gestiti
     */
    public static final String INFO_DATA = "Identification";
    public static final String INPUT_DATA = "InputData";
    public static final String OUTPUT_DATA = "OutputData";
    public static final String SESSION_DATA = "SessionData";
    public static final String ATTACH_DATA = "AttachData";
    // public static final String ATTACH_P7_DATA = "AttachP7Data";

    /**
     * Etichette per i dati di output predefiniti
     */
    public static final String OUT_STATE = "state";
    public static final String OUT_ID = "idtransaction";
    public static final String OUT_ESITO = "esito";
    public static final String OUT_DATE = "data";
    public static final String OUT_SERIAL = "serial";
    public static final String OUT_USERDN = "userDN";
    public static final String OUT_ISSUERDN = "issuerDN";
    public static final String OUT_DATI = "dati";
    public static final String OUT_P7NAME = "p7m_filename";
    public static final String OUT_P7M = "p7m_b64";
    public static final String OUT_RECNAME = "ricp7m_filename";
    public static final String OUT_REC = "ricp7m_b64";
    public static final String OUT_TSTOKEN = "tstoken_b64";
    public static final String OUT_MIME = "mime";

    /**
     * Indicates that to have to remove a DOM node.
     */
    private static final short REMOVE_NODE = 0;

    /**
     * Indicates that to have to add a DOM node.
     */
    private static final short ADD_NODE = 1;

    /**
     * Indicates that to have to get a value from a DOM node.
     */
    private static final short GET_VALUE = 2;

    /**
     * Root node of the DOM document.
     */
    private Element rootNode;

    /**
     * Entire DOM document.
     */
    private Document doc;

    /**
     * Creates new empty XMLDataConstructor
     */
    public XMLPacket() {
	this(0, " ");
    }

    /**
     * Cronstructs a new XMLDataConstructor
     * 
     * @param codeXMLData
     *            Policy id to connect at DOM document.
     * @param userID
     *            Client user id that makes the subscription.
     * @param userAddr
     *            Remote IP address of the client user.
     */
    public XMLPacket(long policyId, String userID) {
	if (userID == null) {
	    userID = "";
	}

	doc = new DocumentImpl();

	// root
	rootNode = doc.createElement("DataToSend");
	// Identification
	Element aEle = doc.createElement(INFO_DATA);
	Attr aAtt = doc.createAttribute(INFO_POLCY);
	aAtt.setValue(Long.toString(policyId));
	aEle.setAttributeNode(aAtt);
	aAtt = doc.createAttribute(INFO_USER);
	aAtt.setValue(userID);
	aEle.setAttributeNode(aAtt);
	rootNode.appendChild(aEle);
	// Input
	aEle = doc.createElement(INPUT_DATA);
	rootNode.appendChild(aEle);
	// output
	aEle = doc.createElement(OUTPUT_DATA);
	rootNode.appendChild(aEle);
	// session
	aEle = doc.createElement(SESSION_DATA);
	rootNode.appendChild(aEle);
	// attach
	aEle = doc.createElement(ATTACH_DATA);
	rootNode.appendChild(aEle);
	// attach p7
	// aEle = doc.createElement( ATTACH_P7_DATA );
	// rootNode.appendChild( aEle );

	doc.appendChild(rootNode); // Add Root to Document
    }

    /**
     * Constructs an XMLDataConstructor from an xml buffer.
     * 
     * @param XMLBuffer
     *            A string containing the DOM document in accord to sendData.dtd
     * @throws XMLPacketException
     *             When a DOM construction occurs.
     */
    public XMLPacket(String XMLBuffer) throws XMLPacketException {
	setXMLString(XMLBuffer);
    }

    /**
     * Creates a DOM document from an xml buffer.
     * 
     * @param XMLBuffer
     *            A string containing the DOM document in accord to sendData.dtd
     * @throws XMLPacketException
     *             When a DOM construction occurs.
     */
    public void setXMLString(String XMLBuffer) throws XMLPacketException {
	DOMParser parser = new DOMParser();

	try {
	    parser.parse(new InputSource(new StringReader(XMLBuffer)));
	} catch (IOException ioe) {
	    throw new XMLPacketException(ioe.toString());
	} catch (SAXException saxE) {
	    throw new XMLPacketException(saxE.toString());
	} catch (Exception exc) {
	    throw new XMLPacketException(exc.toString());
	}

	doc = parser.getDocument();
	rootNode = doc.getDocumentElement();
    }

    /**
     * Gets a nodeList containing a list of nodes with tag <code>strType</code>.
     * 
     * @param strType
     *            tag name desired. #return A NodeList object (for further
     *            informations see Apache xerces documentation.
     */
    public NodeList getNodeList(String strType) {
	return doc.getElementsByTagName(strType);
    }

    public boolean haveOutputData() {
	NodeList list = doc.getElementsByTagName(OUTPUT_DATA);
	Node node = list.item(0);
	list = node.getChildNodes();
	int size = list.getLength();
	return (size > 0);
    }

    /**
     * Empty value of <code>strType</code> tag.
     * 
     * @param strType
     *            tag name desired.
     * @throws XMLPacketException
     *             When a DOM research fails.
     */
    public void emptyNode(String strType) throws XMLPacketException {
	NodeList nl = doc.getElementsByTagName(strType);
	if (nl == null)
	    throw new XMLPacketException("Input data not found.");
	for (int i = 0; i < nl.getLength(); ++i) {
	    Node n = nl.item(i);
	    Node cloneN = n.cloneNode(false);
	    rootNode.replaceChild(cloneN, n);
	}
    }

    /**
     * Manage DOM node of type "InputData", "OutputData", "SessionData".
     * <p>
     * Depending from <code>typeOfManagement</code> value, it can modify, adds
     * or gets a "InputData", "SessionData" or "OutputData" node value from the
     * DOM document.
     * 
     * @param typeOfManagement
     *            Reasonable values are: <code>REMOVE_NODE</code> to remove a
     *            node. <code>ADD_NODE</code> to add a node.
     *            <code>GET_VALUE</code> to get value's node.
     * @param strType
     *            tag name for node to manage. Reasonable values are:
     *            "InputData", "SessionData" or "OutputData".
     * @param strName
     *            Name of the unitElement to manage (see sendData.dtd).
     * @param strValue
     *            Value of the unitElement to manage (see sendData.dtd).
     * @return if <code>typeOfManagement</code> is <code>GET_VALUE</code> then
     *         return the corresponding value else return <code>null</code>.
     * @throws XMLPacketException
     *             When a DOM manage fails.
     * 
     */
    private String manageData(short typeOfManagement, String strType,
	    String strName, String strValue) throws XMLPacketException {
	if (strName == null || strName.equals(""))
	    throw new XMLPacketException("Unknown type of data");
	if (strName == null)
	    throw new XMLPacketException("Name of input data is Null");
	if (strValue == null)
	    throw new XMLPacketException("Value of input data is Null");

	NodeList nl = doc.getElementsByTagName(strType);

	if (nl == null || nl.getLength() > 1)
	    throw new XMLPacketException("Wrong xml format");
	Node n = nl.item(0);

	nl = n.getChildNodes();
	if (nl.getLength() == 0) {
	    if (typeOfManagement == ADD_NODE) {
		Element aEle = doc.createElement("UnitElement");
		aEle.setAttribute("Name", strName);
		// if ((strType.equals(OUTPUT_DATA)) ||
		// (strType.equals(ATTACH_DATA)))
		// {
		// Encoding per evitare problemi con gli \n e altri char
		strValue = java.net.URLEncoder.encode(strValue);
		// }
		aEle.setAttribute("Value", strValue);
		n.appendChild(aEle);
	    }
	} else {
	    for (int k = 0; k < nl.getLength(); ++k) {
		Node aNode = nl.item(k);
		if (aNode.getNodeType() == Node.ELEMENT_NODE) {
		    NamedNodeMap nMap = aNode.getAttributes();

		    Node attNameNode = nMap.getNamedItem("Name");

		    if (attNameNode.getNodeValue().equalsIgnoreCase(strName)) {
			Node attValueNode = null;
			switch (typeOfManagement) {
			case REMOVE_NODE:
			    doc.removeChild(aNode);
			    return null;
			case ADD_NODE:
			    attValueNode = nMap.getNamedItem("Value");
			    // if ((strType.equals(OUTPUT_DATA)) ||
			    // (strType.equals(ATTACH_DATA)))
			    // {
			    // Encoding per evitare problemi con gli \n e altri
			    // char
			    strValue = java.net.URLEncoder.encode(strValue);
			    // }
			    attValueNode.setNodeValue(strValue);
			    return null;
			case GET_VALUE:
			    attValueNode = nMap.getNamedItem("Value");
			    // if ((strType.equals(OUTPUT_DATA)) ||
			    // (strType.equals(ATTACH_DATA)))
			    return java.net.URLDecoder.decode(attValueNode
				    .getNodeValue());
			    // else
			    // return attValueNode.getNodeValue();
			default:
			    throw new XMLPacketException("Unknown what to do");
			}
		    }
		}
	    }
	    if (typeOfManagement == ADD_NODE) {
		Element newElement = doc.createElement("UnitElement");
		newElement.setAttribute("Name", strName);
		// if ((strType.equals(OUTPUT_DATA)) ||
		// (strType.equals(ATTACH_DATA)))
		// {
		// Encoding per evitare problemi con gli \n e altri char
		strValue = java.net.URLEncoder.encode(strValue);
		// }
		newElement.setAttribute("Value", strValue);
		n.appendChild(newElement);
	    }
	}

	return null;
    }

    /**
     * Sets InputData key values (<code>strName</code>, <code>strValue</code>)
     * 
     * @param strName
     *            Name of the unitElement to set (see sendData.dtd).
     * @param strValue
     *            Value of the unitElement to set (see sendData.dtd).
     * @throws XMLPacketException
     *             When a DOM manage fails.
     */
    public void setInputData(String strName, String strValue)
	    throws XMLPacketException {
	manageData(ADD_NODE, INPUT_DATA, strName, strValue);
    }

    /**
     * Sets OutputData key values (<code>strName</code>, <code>strValue</code>)
     * 
     * @param strName
     *            Name of the unitElement to set (see sendData.dtd).
     * @param strValue
     *            Value of the unitElement to set (see sendData.dtd).
     * @throws XMLPacketException
     *             When a DOM manage fails.
     */
    public void setOutputData(String strName, String strValue)
	    throws XMLPacketException {
	manageData(ADD_NODE, OUTPUT_DATA, strName, strValue);
    }

    /**
     * Sets sessionData key values (<code>strName</code>, <code>strValue</code>)
     * 
     * @param strName
     *            Name of the unitElement to set (see sendData.dtd).
     * @param strValue
     *            Value of the unitElement to set (see sendData.dtd).
     * @throws XMLPacketException
     *             When a DOM manage fails.
     */
    public void setSessionData(String strName, String strValue)
	    throws XMLPacketException {
	manageData(ADD_NODE, SESSION_DATA, strName, strValue);
    }

    public void setFileData(String strName, String strValue)
	    throws XMLPacketException {
	manageData(ADD_NODE, ATTACH_DATA, strName, strValue);
    }

    /**
     * Removes an InputData node.
     * 
     * @param strName
     *            Name of the unitElement to remove (see sendData.dtd).
     * @throws XMLPacketException
     *             When a DOM manage fails.
     */
    public void removeInputData(String strName) throws XMLPacketException {
	manageData(REMOVE_NODE, INPUT_DATA, strName, "");
    }

    /**
     * Removes an OutputData node.
     * 
     * @param strName
     *            Name of the unitElement to remove (see sendData.dtd).
     * @throws XMLPacketException
     *             When a DOM manage fails.
     */
    public void removeOutputData(String strName) throws XMLPacketException {
	manageData(REMOVE_NODE, OUTPUT_DATA, strName, "");
    }

    /**
     * Removes sessionData node.
     * 
     * @param strName
     *            Name of the unitElement to remove (see sendData.dtd).
     * @throws XMLPacketException
     *             When a DOM manage fails.
     */
    public void removeSessionData(String strName) throws XMLPacketException {
	manageData(REMOVE_NODE, SESSION_DATA, strName, "");
    }

    /**
     * Gets an InputData value.
     * 
     * @param strName
     *            Name of the unitElement to consider (see sendData.dtd).
     * @throws XMLPacketException
     *             When a DOM manage fails.
     */
    public String getInputValue(String strName) throws XMLPacketException {
	return manageData(GET_VALUE, INPUT_DATA, strName, "");
    }

    /**
     * Gets an OutputData value.
     * 
     * @param strName
     *            Name of the unitElement to consider (see sendData.dtd).
     * @throws XMLPacketException
     *             When a DOM manage fails.
     */
    public String getOutputValue(String strName) throws XMLPacketException {
	return manageData(GET_VALUE, OUTPUT_DATA, strName, "");
    }

    /**
     * Gets a sessionData value.
     * 
     * @param strName
     *            Name of the unitElement to consider (see sendData.dtd).
     * @throws XMLPacketException
     *             When a DOM manage fails.
     */
    public String getSessionValue(String strName) throws XMLPacketException {
	return manageData(GET_VALUE, SESSION_DATA, strName, "");
    }

    public String getFileValue(String strName) throws XMLPacketException {
	return manageData(GET_VALUE, ATTACH_DATA, strName, "");
    }

    /**
     * Sets the policy id.
     * 
     * @param lCode
     *            Rapresent the policy id.
     * @throws XMLPacketException
     *             When a DOM manage fails.
     */
    public void setPolicyId(long lCode) throws XMLPacketException {
	manageKeys(ADD_NODE, INFO_POLCY, Long.toString(lCode));
    }

    /**
     * Gets the policy id.
     * 
     * @return The policy id.
     * @throws XMLPacketException
     *             When a DOM manage fails.
     */
    public int getPolicyId() throws XMLPacketException {
	String strCode = manageKeys(GET_VALUE, INFO_POLCY, "");
	int lCode = -1;
	try {
	    lCode = Integer.parseInt(strCode);
	} catch (Exception e) {
	    throw new XMLPacketException(e.toString());
	}
	return lCode;
    }

    /**
     * UserID
     */
    public void setUserID(String strUserID) throws XMLPacketException {
	manageKeys(ADD_NODE, INFO_USER, strUserID);
    }

    public String getUserID() throws XMLPacketException {
	return manageKeys(GET_VALUE, INFO_USER, "");
    }

    /**
     * Rec_email
     */
    public void setRecEmail(String email) throws XMLPacketException {
	manageKeys(ADD_NODE, INFO_EMAIL_RICEVUTE, email);
    }

    public String getRecEmail() throws XMLPacketException {
	return manageKeys(GET_VALUE, INFO_EMAIL_RICEVUTE, "");
    }

    /**
     * info_session
     */
    public void setOverwriteSession() throws XMLPacketException {
	manageKeys(ADD_NODE, INFO_SESSION, "true");
    }

    public boolean getOverwriteSession() throws XMLPacketException {
	String value = manageKeys(GET_VALUE, INFO_SESSION, "false");
	return (new Boolean(value)).booleanValue();
    }

    /**
     * Manage DOM attributes for "Identification" DOM Node.
     * <p>
     * Depending from <code>typeOfManagement</code> value, it can adds or gets
     * the value an attribute of "Identification" node.
     * 
     * @param typeOfManagement
     *            Reasonable values are: <code>ADD_NODE</code> to add a node.
     *            <code>GET_VALUE</code> to get value's node.
     * @param strType
     *            tag name for the attribute to manage. Reasonable values are:
     *            "CodeXMLData", "UserID" or "UserAddr".
     * @param strValue
     *            Value of the attribute to manage (see sendData.dtd).
     * @return if <code>typeOfManagement</code> is <code>GET_VALUE</code> then
     *         return the corresponding value else return <code>null</code>.
     * @throws XMLPacketException
     *             When a DOM manage fails.
     * 
     */
    private String manageKeys(short typeOfManagement, String strType,
	    String strValue) throws XMLPacketException {

	if (strValue == null)
	    throw new XMLPacketException("Value of input data is Null");

	NodeList nIdEle = doc.getElementsByTagName(INFO_DATA);

	if (nIdEle == null || nIdEle.getLength() == 0)
	    throw new XMLPacketException("Wrong xml format");
	Element idEle = ((Element) nIdEle.item(0));

	switch (typeOfManagement) {
	case ADD_NODE:
	    idEle.setAttribute(strType, strValue);
	    return null;
	case GET_VALUE:
	    return idEle.getAttribute(strType);
	default:
	    throw new XMLPacketException("Unknown what to do");
	}
    }

    /**
     * Serves for serialize.
     * 
     * @param out
     *            The ObjectOutputStream.
     * @throws IOException
     *             When a serialization error occurs.
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
	out.writeObject(rootNode);
	out.writeObject(doc);
    }

    /**
     * Serves for serialize.
     * 
     * @param in
     *            The ObjectInputStream.
     * @throws IOException
     *             When a serialization error occurs.
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException,
	    ClassNotFoundException {
	rootNode = (ElementImpl) in.readObject();
	doc = (DocumentImpl) in.readObject();
    }

    /**
     * Returns the DOM document into a string.
     * 
     * @return A string containing the DOM.
     */
    public String toString() {
	try {
	    OutputFormat format = new OutputFormat(doc); // Serialize DOM
	    StringWriter stringOut = new StringWriter(); // Writer will be a
							 // String
	    XMLSerializer serial = new XMLSerializer(stringOut, format);
	    serial.asDOMSerializer(); // As a DOM Serializer

	    serial.serialize(doc.getDocumentElement());

	    return stringOut.toString(); // Spit out DOM as a String
	} catch (IOException ioe) {
	}
	return null;
    }

    /**
     * Returns the entire tree DOM document.
     * 
     * @return The entire tree DOM document.
     */
    public Document getDOMData() {
	return doc;
    }

    public String postXmlData(String urlToPost) {
	String noProxy = null;
	String toReturn = postXmlData(urlToPost, noProxy);
	return toReturn;
    }

    /**
     * Send a <code>urlToPost</code> an http post request with the DOM document
     * and return a string containing the redirect url.
     * 
     * @param urlToPost
     *            URL to post xml data.
     * @return A string containing the redirect url
     */
    public String postXmlData(String urlToPost, String proxy) {
	StringBuffer sb = null;

	try {
	    URL url = null;
	    if (proxy != null) {
		URL proxyUrl = new URL(proxy);
		url = new URL(proxyUrl.getProtocol(), proxyUrl.getHost(),
			proxyUrl.getPort(), urlToPost);
	    } else
		url = new URL(urlToPost);

	    URLConnection connection = url.openConnection();

	    connection.setDoOutput(true);
	    PrintStream outStream = new PrintStream(
		    connection.getOutputStream());
	    outStream.print("xmlDataConstructor=");

	    String xmlString = URLEncoder.encode(this.toString());
	    outStream.print(xmlString);

	    outStream.flush();
	    outStream.close();

	    BufferedReader d = new BufferedReader(new InputStreamReader(
		    connection.getInputStream()));
	    String inputLine;

	    sb = new StringBuffer();
	    while ((inputLine = d.readLine()) != null) {
		sb.append(inputLine + "\n");
	    }
	    d.close();
	} catch (MalformedURLException me) {
	    return me.toString() + " - demo.signweb.servlets.url = "
		    + urlToPost;
	} catch (IOException ioe) {
	    return ioe.toString() + " - demo.signweb.servlets.url = "
		    + urlToPost;
	}

	return sb.toString();
    }

    public String[] getFileTagsName() {
	NodeList list = this.getNodeList(ATTACH_DATA);
	Node sessionNode = list.item(0);
	list = sessionNode.getChildNodes();
	int size = list.getLength();
	String sessionData = "";
	Node nodo;
	NamedNodeMap nMap;
	Node nome;
	String[] nodi = new String[size];
	for (int i = 0; i < size; i++) {
	    nodo = list.item(i);
	    nMap = nodo.getAttributes();
	    nodi[i] = nMap.getNamedItem("Name").getNodeValue();
	}
	return nodi;
    }

    public String[] getInputTagsName() {
	NodeList list = this.getNodeList(INPUT_DATA);
	Node inputNode = list.item(0); // ???
	list = inputNode.getChildNodes();
	int size = list.getLength();
	String inputData = "";
	Node nodo;
	NamedNodeMap nMap;
	Node nome;
	String[] nodi = new String[size];
	for (int i = 0; i < size; i++) {
	    nodo = list.item(i);
	    nMap = nodo.getAttributes();
	    nodi[i] = nMap.getNamedItem("Name").getNodeValue();
	}
	return nodi;
    }

    public String getSessionDataString() {
	NodeList list = this.getNodeList(SESSION_DATA);
	Node sessionNode = list.item(0);
	list = sessionNode.getChildNodes();
	int size = list.getLength();
	String sessionData = "";
	Node nodo;
	NamedNodeMap nMap;
	Node nome;
	Node valore;
	for (int i = 0; i < size; i++) {
	    nodo = list.item(i);
	    nMap = nodo.getAttributes();
	    nome = nMap.getNamedItem("Name");
	    valore = nMap.getNamedItem("Value");
	    if ((nome != null) && (valore != null))
		sessionData += nome.getNodeValue() + "="
			+ valore.getNodeValue() + "&";
	}

	if (!sessionData.equals(""))
	    sessionData = sessionData.substring(0, sessionData.length() - 1);

	return sessionData;
    }
}
