package it.wego.cross.plugins;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.client.Stub;
import org.apache.axis.utils.Messages;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import it.eng.auriga.repository2.webservices.cxf.WSGetMetadataUdService;
import it.eng.auriga.repository2.webservices.cxf.WSIGetMetadataUd;
import it.eng.auriga.repository2.webservices.getmetadataud.WSGetMetadataUdServiceLocator;
import it.wego.auriga.xml.AllegatoUDType;
import it.wego.auriga.xml.ClassifFascicoloType;
import it.wego.auriga.xml.EstremiXIdentificazioneFolderNoLibType;
import it.wego.auriga.xml.GetIdMetadati;
import it.wego.auriga.xml.NewUD;
import it.wego.auriga.xml.VersioneElettronicaType;
import it.wego.auriga.xml.allegati.AllegatiProtocollati;
import it.wego.auriga.xml.allegati.EstremiRegNumType;
import it.wego.auriga.xml.allegati.EstremiXIdentificazioneUDType;
import it.wego.auriga.xml.allegati.MyAllegatoBean;
import it.wego.auriga.xml.allegati.MyOutputFileUD;
import it.wego.auriga.xml.allegati.Output_FilesUD.DatiFileEstratto;
import it.wego.cross.beans.AllegatoBean;
import it.wego.cross.client.xml.extractone.BaseOutputWS;
import it.wego.cross.client.xml.getmetadata.EstremiXIdentificazioneUD;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloRequest;
import it.wego.cross.utils.AurigaUtils;
import it.wego.cross.utils.ByteArrayDataSource;
import it.wego.cross.utils.RuvoUtils;
import it.wego.cross.utils.Utils;
import utilities.myUtility;

public class GestioneProtocolloUtility {

	private static final Logger log = LoggerFactory.getLogger("plugin");

	private static final QName SERVICE_NAME = new QName("http://getmetadataud.webservices.repository2.auriga.eng.it", "WSGetMetadataUdService");

	private myUtility utl;
/*
	public static String getContent(Element element) {
		if (element.isTextOnly())
			return element.getText();
		StringBuilder sb = new StringBuilder();
		Element currElement = null;
		for (Iterator<Element> iterator = element.elementIterator() ; iterator.hasNext() ; 
		// Continue till done 
	) {
			currElement = iterator.next();
			sb.append(currElement.asXML());
		}
		return sb.toString();
	}
*/
	public static List<String> getNodeElementValue(String xmlcontent)
	{
		String[] es = xmlcontent.split("(?=<)|(?<=>)");
		List<String> result = new ArrayList<>(es.length);
		for (int i = 0; i < es.length; i++) {
			if (i > 0 && i < es.length - 1)
				result.add(es[i]);
		}
		return result;
	}

	public static Document convertStringToXMLDocument(String xmlString) {
		//Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		//API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			//Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();

			//Parse the content to Document object
			Document doc = (Document) builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}

	public static void checkResult(String risultato) throws Exception, JAXBException {
		JAXBContext context = JAXBContext.newInstance(BaseOutputWS.class);
		Unmarshaller um = context.createUnmarshaller();
		BaseOutputWS output = (BaseOutputWS) um.unmarshal(new StringReader(risultato));
		String result = output.getWSResult();
		if (result.equals("1")) {
			//Tutto OK
			if (output.getWarningMessage() != null) {
				log.warn("Il sistema documentale ha ritornato il seguente messaggio di warning");
				log.warn(output.getWarningMessage());
			}
		} else {
			//Si è verificato un errore
			if (output.getWSError() != null) {
				log.error("Si è verificato un errore sul sistema documentale. Di seguito i dettagli");
				log.error("Contesto: " + output.getWSError().getErrorContext());
				log.error("Codice numerico: " + output.getWSError().getErrorNumber());
				log.error("Messaggio: " + output.getWSError().getErrorMessage());
				throw new Exception(output.getWSError().getErrorMessage());
			}
		}
	}

	public static String getXmlGetMetadata_Ud(String idUd) throws Exception {
		EstremiXIdentificazioneUD metaUD = new EstremiXIdentificazioneUD();
		metaUD.setIdUD(idUd);
		String xml = Utils.marshall(metaUD);
		return xml;
	}

	public static Pratica getPratica(Allegati allegato) {
		List<PraticheEventiAllegati> praticheEventiAllegati = allegato.getPraticheEventiAllegatiList();
		Pratica pratica = null;
		if (praticheEventiAllegati != null && !praticheEventiAllegati.isEmpty()) {
			pratica = praticheEventiAllegati.get(0).getPraticheEventi().getIdPratica();
		}
		return pratica;
	}

	public static String getXmlGetMetadata_allegati(int anno, int numReg) throws Exception {
		it.wego.auriga.xml.allegati.EstremiXIdentificazioneUD metaUD = new it.wego.auriga.xml.allegati.EstremiXIdentificazioneUD();

		EstremiXIdentificazioneUDType udtype = new EstremiXIdentificazioneUDType();

		EstremiRegNumType numRegType = new EstremiRegNumType();

		numRegType.setAnnoReg(anno);

		numRegType.setNumReg(numReg);

		metaUD.setEstremiRegNum(numRegType);

		String xml = Utils.marshall(metaUD);

		return xml;
	}
	/* */
	public static GetIdMetadati getMetadatiCxf(int anno, int numProtocollo) throws Exception {
		GetIdMetadati responseMetadati = new GetIdMetadati();

		myUtility utl = new myUtility("protocollo_ruvo.properties", "protocollo_ruvo.log4j.properties");;
		String protocolloRuvo = "072038/2023/6514";
		String IdUD = "54807";

		//Recupero informazioni dal protocollo
		String endpointGetUd = utl.getValueProperties("urlGetUd");
		URL wsdlURL =  new URL(endpointGetUd+"?wsdl");

		String codiceApplicazione = utl.getValueProperties("cod.applicazione");
		String codiceIstanza = utl.getValueProperties("istanza.applicazione");
		String username = utl.getValueProperties("username");
		String password = utl.getValueProperties("password");

		String xmlRichiesta;
		try {
			xmlRichiesta = getXmlGetMetadata_Ud(IdUD);

			System.out.println("serviceOperation.xmlRichiesta=" + xmlRichiesta);

			String xmlHash = RuvoUtils.hashXML(xmlRichiesta, "");


			WSGetMetadataUdService ss = new WSGetMetadataUdService(wsdlURL, SERVICE_NAME);
			WSIGetMetadataUd port = ss.getWSGetMetadataUdPort();  
			System.out.println("Invoking serviceOperation...");
			it.eng.auriga.repository2.webservices.cxf.Service _serviceOperation_service = new it.eng.auriga.repository2.webservices.cxf.Service();
			_serviceOperation_service.setCodApplicazione(codiceApplicazione);
			_serviceOperation_service.setIstanzaApplicazione(codiceIstanza);
			_serviceOperation_service.setUserName(username);
			_serviceOperation_service.setPassword(password);
			_serviceOperation_service.setXml(xmlRichiesta);
			_serviceOperation_service.setHash(xmlHash);

			it.eng.auriga.repository2.webservices.cxf.ServiceResponse _serviceOperation__return = port.serviceOperation(_serviceOperation_service);
			System.out.println("serviceOperation.result=" + _serviceOperation__return.getServiceReturn());

			String risultato = _serviceOperation__return.getServiceReturn();         
			risultato = RuvoUtils.decodeXml(risultato);

			log.info("Protocollo Ruvo:creazioneUnitaDocumentaria:Risposta del documentale: \n" + risultato);
			checkResult(risultato);

			Object[] allegatiResponse = ((Stub) port).getAttachments();
			log.info("Protocollo Ruvo:Metadati:Numero di allegati: " + allegatiResponse.length);

			if (allegatiResponse.length == 0) {
				log.debug("Protocollo Ruvo:Metadati:Numero di allegati: " + allegatiResponse.length);
				throw new Exception("Non è stato creata nessuna unita documentaria");
			} else {
				DataHandler esitoGetMetaDati = ((AttachmentPart) allegatiResponse[0]).getDataHandler();
				if (esitoGetMetaDati != null) {
					log.info("Protocollo Ruvo: esitoGetMetaDati: Esito GetMetaDati unita documentale");
					InputStream inputStream = esitoGetMetaDati.getInputStream();
					byte[] content = IOUtils.toByteArray(inputStream);
					String esitoXml = new String(content);

					String esitoXmlApp = utilities.StringEscapeUtils.unescapeXml(esitoXml);

					esitoXml = esitoXmlApp.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();

					esitoXml = esitoXml.replaceAll("\\p{Zs}+", " ");

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					factory.setNamespaceAware(true); 
					DocumentBuilder builder = factory.newDocumentBuilder();
					InputSource source = new InputSource(new StringReader(esitoXml));
					org.w3c.dom.Document doc = builder.parse(source);

					//Create XPath
					XPathFactory xpathfactory = XPathFactory.newInstance();
					XPath xpath = xpathfactory.newXPath();

					String sIdDocPrimario = "";
					String sNumReg = "";
					String sDataOraReg = "";
					String sSiglaReg = "";
					String sIdUO = "";
					String sAnnoReg = "";

					{ // Valorizzazione risposta protocollazione Ruvo
						XPathExpression expr = xpath.compile("//DatiUD/IdUD/text()");
						Object result = expr.evaluate(doc, XPathConstants.NODESET);
						NodeList nodes = (NodeList) result;

						for (int i = 0; i < nodes.getLength(); i++) {
							IdUD = nodes.item(i).getNodeValue();
							System.out.println(nodes.item(i).getNodeValue());
						}

						expr = xpath.compile("//DatiUD/IdDocPrimario/text()");
						result = expr.evaluate(doc, XPathConstants.NODESET);
						nodes = (NodeList) result;
						for (int i = 0; i < nodes.getLength(); i++) {
							sIdDocPrimario = nodes.item(i).getNodeValue();
							System.out.println(nodes.item(i).getNodeValue());
						}

						expr = xpath.compile("//DatiUD/RegistrazioneData/NumReg/text()");
						result = expr.evaluate(doc, XPathConstants.NODESET);
						nodes = (NodeList) result;
						for (int i = 0; i < nodes.getLength(); i++) {
							sNumReg = nodes.item(i).getNodeValue();
							System.out.println(nodes.item(i).getNodeValue());
						}

						expr = xpath.compile("//DatiUD/RegistrazioneData/AnnoReg/text()");
						result = expr.evaluate(doc, XPathConstants.NODESET);
						nodes = (NodeList) result;
						for (int i = 0; i < nodes.getLength(); i++) {
							sAnnoReg = nodes.item(i).getNodeValue();
							System.out.println(nodes.item(i).getNodeValue());
						}

						expr = xpath.compile("//DatiUD/RegistrazioneData/DataOraReg/text()");
						result = expr.evaluate(doc, XPathConstants.NODESET);
						nodes = (NodeList) result;
						for (int i = 0; i < nodes.getLength(); i++) {
							sDataOraReg = nodes.item(i).getNodeValue();
							System.out.println(nodes.item(i).getNodeValue());
						}

						expr = xpath.compile("//DatiUD/RegistrazioneData/SiglaReg/text()");
						result = expr.evaluate(doc, XPathConstants.NODESET);
						nodes = (NodeList) result;
						for (int i = 0; i < nodes.getLength(); i++) {
							sSiglaReg = nodes.item(i).getNodeValue();
							System.out.println(nodes.item(i).getNodeValue());
						}

						expr = xpath.compile("//DatiUD/RegistrazioneData/UOReg/IdUO/text()");
						result = expr.evaluate(doc, XPathConstants.NODESET);
						nodes = (NodeList) result;
						for (int i = 0; i < nodes.getLength(); i++) {
							sIdUO = nodes.item(i).getNodeValue();
							System.out.println(nodes.item(i).getNodeValue());
						}
					}

					String dNow = myUtility.getLocalTime();
					Date dDataProtocollazione = myUtility.parseDateTime(dNow);
					if(!sDataOraReg.isEmpty()) {
						dDataProtocollazione = myUtility.parseDateTime(sDataOraReg);							
					}

					Calendar c = Calendar.getInstance();
					c.setTime(dDataProtocollazione);

					String sResponse = "Response protocollo Ruvo: IdUD:" + IdUD + 
							" AnnoReg:" + sAnnoReg + 
							" NumReg:" + sNumReg + 
							" DataOraReg:" + sDataOraReg +
							" SiglaReg:" + sSiglaReg +
							" IdUO:" + sIdUO +
							" data protocollazione:" + dDataProtocollazione;

					System.out.println("------------------------ Dati protocollo Ruvo----------------------------");
					System.out.println(" IdUD:" + IdUD );
					System.out.println(" SiglaReg:" + IdUD );
					System.out.println(" AnnoReg:" + sAnnoReg );
					System.out.println(" NumReg:" + sNumReg );
					System.out.println(" DataOraReg:" + sDataOraReg );
					System.out.println("-------------------------------------------------------------------------");

					responseMetadati.setIdUd(Integer.parseInt(IdUD));

					log.info(sResponse);

				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return responseMetadati;
	}


	/**/

	public static String getXmlExtractOne(String idUd, String nomeFileAllegato) throws Exception {
		it.wego.auriga.xml.allegati.FileUDToExtract fileUD = new it.wego.auriga.xml.allegati.FileUDToExtract();

		it.wego.auriga.xml.allegati.EstremiXIdentificazioneFileUDType fUd = new it.wego.auriga.xml.allegati.EstremiXIdentificazioneFileUDType();

		fUd.setNomeFile(nomeFileAllegato);;
		it.wego.auriga.xml.allegati.EstremiXIdentificazioneUDType Ud = new it.wego.auriga.xml.allegati.EstremiXIdentificazioneUDType();
		BigInteger bigIdUD = new BigInteger(idUd);
		Ud.setIdUD(bigIdUD);

		fileUD.setEstremixIdentificazioneFileUD(fUd);
		fileUD.setEstremiXIdentificazioneUD(Ud);

		String xml = Utils.marshall(fileUD);
		return xml;
	}
	public static String getXmlExtractOne2(int IdUD, int IdDoc) throws Exception {
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		xml.append("<FileUDToExtract>");
		xml.append("<EstremiXIdentificazioneUD><IdUD>" + IdUD +"</IdUD></EstremiXIdentificazioneUD>");
		xml.append("<EstremixIdentificazioneFileUD><IdDoc>" + IdDoc + "</IdDoc></EstremixIdentificazioneFileUD>");
		xml.append("<Timbro><OpzioniTimbro><PosizioneTimbro>ALTO_DX</PosizioneTimbro>");
		xml.append("<RotazioneTimbro>ORIZZONTALE</RotazioneTimbro><IntervalloPagine>");
		xml.append("<da>1</da><a>1</a></IntervalloPagine></OpzioniTimbro></Timbro>");
		xml.append("</FileUDToExtract>");
		return xml.toString();
	}
	public static String getXmlExtractMulti(int sNumRegistro, int anno) throws Exception {
		it.wego.auriga.xml.allegati.EstremiXIdentificazioneUD fileUD = new it.wego.auriga.xml.allegati.EstremiXIdentificazioneUD();
		EstremiRegNumType reg = new EstremiRegNumType();
		reg.setCategoriaReg("PG");
		reg.setAnnoReg(anno);
		reg.setNumReg(sNumRegistro);
		it.wego.auriga.xml.allegati.EstremiXIdentificazioneUDType ud = new it.wego.auriga.xml.allegati.EstremiXIdentificazioneUDType();
		fileUD.setEstremiRegNum(reg);

		String xml = Utils.marshall(fileUD);

		return xml;
	}

	public static DataHandler getAttachment(Allegati allegato) throws Exception {
		ByteArrayDataSource ds = new ByteArrayDataSource();
		ds.setContentType(allegato.getTipoFile());
		ds.setName(allegato.getNomeFile());

		if (allegato.getFile() != null) {
			ds.setBytes(allegato.getFile());
		} else if (allegato.getIdFileEsterno() != null) {
			System.err.println("Couldn't get file \"" + allegato.getNomeFile() + "\" skipping...");
			throw new Exception("Couldn't get file \"" + allegato.getNomeFile() + "\" skipping...");

		} else if (allegato.getPathFile() != null) {
			File f = new File(allegato.getPathFile());
			InputStream stream = new FileInputStream(f);
			byte[] file = IOUtils.toByteArray(stream);
			ds.setBytes(file);
		}

		DataHandler dh = new DataHandler(ds);
		if (!(dh instanceof javax.activation.DataHandler)) {
			throw new org.apache.axis.AxisFault(
					Messages.getMessage(
							"unsupportedAttach", dh.getClass().getName(),
							javax.activation.DataHandler.class.getName()));
		}

		return dh;
	}

	public static DataHandler[] getAttachmentsDataHandlers(List<Allegati> allegati) {
		java.util.LinkedList retList = new java.util.LinkedList();
		DataHandler[] ret = new DataHandler[0];// empty

		int size_allegati = allegati.size();
		java.io.File[] files = new java.io.File[size_allegati];

		for(int j=0;j<size_allegati;j++)
		{
			Allegati allegato = allegati.get(j);
			files[j] = new java.io.File(allegato.getPathFile()+"/"+allegato.getNomeFile());
		}

		for (int i = files.length - 1; i >= 0; --i) {
			java.io.File cf = files[i];

			boolean dh_isFile = cf.isFile();
			boolean dh_canRead = cf.canRead();
			String fname = null;

			try {
				fname = cf.getAbsoluteFile().getCanonicalPath();
			} catch (java.io.IOException e) {
				System.err.println("Couldn't get file \"" + fname + "\" skipping...");
				continue;
			}
			retList.add(new DataHandler(new FileDataSource(fname)));
		}
		if (!retList.isEmpty()) {
			ret = new DataHandler[retList.size()];
			ret = (DataHandler[]) retList.toArray(ret);
		}

		return ret;
	}
	
	public static long saveContentToFile(DataHandler content, File outFile) throws IOException {
        long size = 0;
        byte[] buffer = new byte[1024];
        try (InputStream is = content.getInputStream()) {
            try (OutputStream outStream = new FileOutputStream(outFile)) {
                for (int readBytes; (readBytes = is.read(buffer, 0, buffer.length)) > 0;) {
                    size += readBytes;
                    outStream.write(buffer, 0, readBytes);
                }
            }
        }
        return size;
    }
	public static int saveDataHandlerToFile(DataHandler content) throws IOException {
	int result=0;
	 ByteArrayOutputStream output = new ByteArrayOutputStream();
	    try {
	    	content.writeTo(output);
	    	result=1;
	    } catch (IOException e) {
	        String msg = "Failed to read the input";
	        log.error(msg, e);
	    }
	    return result;
	}
	public static String getXmlRecuperoAllegatiDaProtocollo(String numero, Integer anno) throws Exception {
		it.wego.cross.client.xml.extractmulti.EstremiXIdentificazioneUDType estremiIdentificazioneUD = new it.wego.cross.client.xml.extractmulti.EstremiXIdentificazioneUDType();
		it.wego.cross.client.xml.extractmulti.EstremiRegNumType estremiReg = new it.wego.cross.client.xml.extractmulti.EstremiRegNumType();
		estremiReg.setAnnoReg(anno);
		estremiReg.setNumReg(Integer.valueOf(numero));
		estremiIdentificazioneUD.setEstremiRegNum(estremiReg);
		it.wego.cross.client.xml.extractmulti.ObjectFactory of = new it.wego.cross.client.xml.extractmulti.ObjectFactory();
		JAXBElement<it.wego.cross.client.xml.extractmulti.EstremiXIdentificazioneUDType> root = of.createEstremiXIdentificazioneUD(estremiIdentificazioneUD);
		String xml = Utils.marshall(root, it.wego.cross.client.xml.extractmulti.EstremiXIdentificazioneUDType.class);
		return xml;
	}

	public static String getTagValue(org.w3c.dom.Document xmlDoc, String tagName) throws Exception {
        xmlDoc.getDocumentElement().normalize();

        NodeList nodeList = xmlDoc.getElementsByTagName(tagName);

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node nNode = nodeList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element eElement = (org.w3c.dom.Element) nNode;
                return eElement.getFirstChild().getNodeValue();
            }
        }
        return "-1";
    }
	private static it.wego.auriga.xml.allegati.MyOutputFileUD getDatiFileEstratto(String esitoXml) {
		it.wego.auriga.xml.allegati.MyOutputFileUD outputAllegati = new it.wego.auriga.xml.allegati.MyOutputFileUD();
		ArrayList<MyOutputFileUD.MyDatiFileEstratto> listDatiFileEstratto = new ArrayList<MyOutputFileUD.MyDatiFileEstratto>();
		int idDoc = 0;

        // start parse XML
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
	    try {
	
	        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
	
	        // parse XML string
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        
	        InputSource source = new InputSource(new StringReader(esitoXml));
	        
	        Document doc = db.parse(source);
	
	        doc.getDocumentElement().normalize();
	
	        System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
	        System.out.println("------");
	
	        // get <DatiFileEstratto>
	        NodeList list = doc.getElementsByTagName("DatiFileEstratto");
	
	        for (int temp = 0; temp < list.getLength(); temp++) {
	
	            Node node = list.item(temp);
	
	            if (node.getNodeType() == Node.ELEMENT_NODE) {
	            	MyOutputFileUD.MyDatiFileEstratto dfe = new MyOutputFileUD.MyDatiFileEstratto();

	                Element element = (Element) node;
	
	                String sNroAttachment = element.getElementsByTagName("NroAttachment").item(0).getTextContent();
	                String sIdDoc = element.getElementsByTagName("IdDoc").item(0).getTextContent();
	                String sNomeFile = element.getElementsByTagName("NomeFile").item(0).getTextContent();
	                String sNroVersione = element.getElementsByTagName("NroVersione").item(0).getTextContent();
	                String sCodId = element.getElementsByTagName("CodId").item(0).getTextContent();
	                String sDesOggetto = element.getElementsByTagName("DesOggetto").item(0).getTextContent();
	
	                System.out.println("Current Element :" + node.getNodeName());
	                System.out.println("sNroAttachment : " + sNroAttachment);
	                System.out.println("sDesOggetto : " + sDesOggetto);
	                System.out.println("IdDoc : " + sIdDoc);
	                System.out.println("NomeFile : " + sNomeFile);
	                System.out.println("NroVersione : " + sNroVersione);
	                System.out.println("CodId : " + sCodId);
	                

	                it.wego.auriga.xml.allegati.MyOutputFileUD.RelazioneVsUD relazioneVsUD = new it.wego.auriga.xml.allegati.MyOutputFileUD.RelazioneVsUD();
	    		    relazioneVsUD.setCodId(sCodId);

	    		    dfe.setDesOggetto(sDesOggetto);
	    		    dfe.setNroAttachment(Integer.valueOf(sNroAttachment).intValue());
	    		    dfe.setIdDoc(Integer.valueOf(sIdDoc).intValue());
	    		    dfe.setNomeFile(sNomeFile);
	    		    dfe.setNroVersione(Integer.valueOf(sNroVersione).intValue());
	    		    dfe.setRelazioneVsUD(relazioneVsUD);
	    		    listDatiFileEstratto.add(dfe);

	            }
	        }
	
	    } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	    }
	    outputAllegati.setDatiFileEstratto(listDatiFileEstratto);
	    return outputAllegati;
	}    
	public static ArrayList<AllegatiProtocollati> estraiTuttiGliAllegati(Object[] allegatiResponse) throws Exception {
		String allegatoBase64 = null;
		ArrayList<AllegatiProtocollati> listAllegatiprotocollati = new ArrayList<AllegatiProtocollati>();

		List<Allegato> allegati = new ArrayList<Allegato>();

		try {
			if (allegatiResponse == null || allegatiResponse.length == 0) {
				log.info("ProtocolloRuvo:estraiTuttiGliAllegati: Nessun allegato trovato associato alla pratica");
				return null;
			} else {
				log.debug("ProtocolloRuvo:estraiTuttiGliAllegati: Numero di allegati trovato:" + allegatiResponse.length);

				//Il secondo attachment è l'esito della chiamata
				DataHandler elencoDeiFileEstratti; 

				elencoDeiFileEstratti = ((AttachmentPart) allegatiResponse[0]).getDataHandler();

				Map<Integer, MyAllegatoBean> allegatiIndividuati = new HashMap<Integer, MyAllegatoBean>();
				if (elencoDeiFileEstratti != null) {

					log.debug("ProtocolloRuvo:getAllAttachments");

					InputStream inputStream = elencoDeiFileEstratti.getInputStream();

					byte[] content = IOUtils.toByteArray(inputStream);

					String esitoXml = new String(content);

					String esitoXmlApp = utilities.StringEscapeUtils.unescapeXml(esitoXml);

					esitoXml = esitoXmlApp.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();

					esitoXml = esitoXml.replaceAll("\\p{Zs}+", " ");

					esitoXml = esitoXml.replaceAll("\\r\\n", "");

					log.debug("estraiTuttiGliAllegati esitoXml2:" + esitoXml);

					System.out.println("estraiTuttiGliAllegati esitoXml2:" + esitoXml);

					it.wego.auriga.xml.allegati.MyOutputFileUD elencoAllegati = getDatiFileEstratto(esitoXml);

					int idx = 0;
					for (it.wego.auriga.xml.allegati.MyOutputFileUD.MyDatiFileEstratto datiSingoloFile : elencoAllegati.getDatiFileEstratto()) {					
						MyAllegatoBean allegato = new MyAllegatoBean();
						idx++;
						log.debug("estraiTuttiGliAllegati DatiFileEstratto(" + idx + ") getDesOggetto:" + datiSingoloFile.getDesOggetto());
						log.debug("estraiTuttiGliAllegati DatiFileEstratto(" + idx + ") getIdDoc:" + datiSingoloFile.getIdDoc());
						log.debug("estraiTuttiGliAllegati DatiFileEstratto(" + idx + ") getNomeFile:" + datiSingoloFile.getNomeFile());
						log.debug("estraiTuttiGliAllegati DatiFileEstratto(" + idx + ") getNroVersione:" + datiSingoloFile.getNroVersione());
						log.debug("estraiTuttiGliAllegati DatiFileEstratto(" + idx + ") getNroAttachment:" + datiSingoloFile.getNroAttachment());

						allegato.setDescrizioneOggetto(datiSingoloFile.getDesOggetto());
						allegato.setIdAllegato(datiSingoloFile.getIdDoc());
						allegato.setNomeFile(String.valueOf(datiSingoloFile.getNomeFile()));
						allegato.setVersione(String.valueOf(datiSingoloFile.getNroVersione()));
						allegato.setPrimario(datiSingoloFile.getRelazioneVsUD().getCodId().equalsIgnoreCase("P"));
						allegatiIndividuati.put(datiSingoloFile.getNroAttachment(), allegato);
					}
					/* */

				} else {
					log.error("ProtocolloRuvo:non è stato possibile recuperare il file XML con l'elenco dei file allegati");
					throw new Exception("Non è stato possibile recuperare l'elenco dei file allegati");
				}
				//Il valore 1 coincide con il file di riepilogo, quindi inizio a recuperare gli allegati dal 2 in poi
				for (int i = 1; i < allegatiResponse.length; i++) {
					AllegatiProtocollati allegatiprotocollati = new AllegatiProtocollati();

					Allegato allegato = new Allegato();
					DataHandler attach = ((AttachmentPart) allegatiResponse[i]).getDataHandler();
					//devo prendere l'ellegato con id da 2 in poi
					MyAllegatoBean bean = allegatiIndividuati.get(i + 1);
					allegato.setDescrizione(bean.getDescrizioneOggetto());
					allegato.setNomeFile(bean.getNomeFile());
					byte[] file = AurigaUtils.toBytes(attach);
					allegato.setFile(file);
					allegato.setFileOrigine(bean.isPrimario());
					//allegato.setIdEsterno(bean.getIdAllegato());
					allegati.add(allegato);

					try( OutputStream stream = new FileOutputStream("/tmp/" + bean.getNomeFile()) ) 
					{
						stream.write(file);

						allegatiprotocollati.setNomeFileAllegato(bean.getNomeFile());

						allegatiprotocollati.setContentFileBase64(file);

						allegatiprotocollati.setIdAllegato(bean.getIdAllegato());

						log.info("ProtocolloRuvo - estraiTuttiGliAllegati - allegato: " + bean.getNomeFile() + " idDoc:" + bean.getIdAllegato());

						listAllegatiprotocollati.add(allegatiprotocollati);
					}
					catch (Exception e) 
					{
						System.err.println("Couldn't write to file...");
					}
				}
			}
		} catch (SOAPException | IOException | JAXBException e) {
			e.printStackTrace();
		}
		return listAllegatiprotocollati;
	}

	public static String estraiAllegato(Object[] allegatiResponse) throws Exception {
		String allegatoBase64 = null;

		ArrayList<AllegatiProtocollati> listAllegatiprotocollati = new ArrayList<AllegatiProtocollati>();

		List<Allegato> allegati = new ArrayList<Allegato>();

		try {
			if (allegatiResponse == null || allegatiResponse.length == 0) {
				log.info("ProtocolloRuvo:estraiTuttiGliAllegati: Nessun allegato trovato associato alla pratica");
				return null;
			} else {
				log.debug("ProtocolloRuvo:estraiTuttiGliAllegati: Numero di allegati trovato:" + allegatiResponse.length);

				//Il secondo attachment è l'esito della chiamata
				DataHandler elencoDeiFileEstratti; 
				elencoDeiFileEstratti = ((AttachmentPart) allegatiResponse[0]).getDataHandler();

				Map<Integer, MyAllegatoBean> allegatiIndividuati = new HashMap<Integer, MyAllegatoBean>();
				if (elencoDeiFileEstratti != null) {
					log.debug("ProtocolloRuvo:getAllAttachments: Esito della creazione della unita documentale");
					InputStream inputStream = elencoDeiFileEstratti.getInputStream();

					byte[] content = IOUtils.toByteArray(inputStream);

					String esitoXml = new String(content);

					log.debug("esitoXml2:" + esitoXml);

					String esitoXmlApp = utilities.StringEscapeUtils.unescapeXml(esitoXml);

					esitoXml = esitoXmlApp.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();

					esitoXml = esitoXml.replaceAll("\\p{Zs}+", " ");

					log.debug("esitoXml2:" + esitoXml);

					System.out.println("esitoXml2:" + esitoXml);

					JAXBContext context = JAXBContext.newInstance(it.wego.auriga.xml.fileallegati.MyOutputFileUDToExtract.class);

					Unmarshaller um = context.createUnmarshaller();

					it.wego.auriga.xml.fileallegati.MyOutputFileUDToExtract allegatoEstratto = (it.wego.auriga.xml.fileallegati.MyOutputFileUDToExtract) um.unmarshal(new StringReader(esitoXml));

					MyAllegatoBean allegato = new MyAllegatoBean();
					allegato.setNomeFile(String.valueOf(allegatoEstratto.getNomeFile()));
					allegato.setVersione(String.valueOf(allegatoEstratto.getNroVersione()));
					allegato.setPrimario(true);
					allegatiIndividuati.put(0, allegato);
				} else {
					log.error("ProtocolloRuvo:non è stato possibile recuperare il file XML con l'elenco dei file allegati");
					throw new Exception("Non è stato possibile recuperare l'elenco dei file allegati");
				}
				for (int i = 1; i < allegatiResponse.length; i++) {
					AllegatiProtocollati allegatiprotocollati = new AllegatiProtocollati();

					Allegato allegato = new Allegato();
					DataHandler attach = ((AttachmentPart) allegatiResponse[i]).getDataHandler();
					MyAllegatoBean bean = allegatiIndividuati.get(0);
					allegato.setNomeFile(bean.getNomeFile());
					byte[] file = AurigaUtils.toBytes(attach);
					allegato.setFile(file);
					allegato.setFileOrigine(bean.isPrimario());
					allegati.add(allegato);

					try( OutputStream stream = new FileOutputStream("/tmp/" + bean.getNomeFile()) ) 
					{
						stream.write(file);

						allegatiprotocollati.setNomeFileAllegato(bean.getNomeFile());

						allegatiprotocollati.setContentFileBase64(file);

						listAllegatiprotocollati.add(allegatiprotocollati);

						allegatoBase64 = Base64.getEncoder().encodeToString(file);
					}
					catch (Exception e) 
					{
						System.err.println("Couldn't write to file...");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allegatoBase64;
	}

	public static String getFileTimbrato(int IdUD, int IdDoc) {
		ArrayList<AllegatiProtocollati> listAllegatiprotocollati = new ArrayList<AllegatiProtocollati>();

		String fileBase64 = "";

		myUtility utl = new myUtility("protocollo_bari.properties", "protocollo_bari.log4j.properties");

		String idUdProtocollo = "", sIClassificazione = ""; 

		int anno = 0, numRegistro = 0;

		String codiceApplicazione = utl.getValueProperties("cod.applicazione");
		String codiceIstanza = utl.getValueProperties("istanza.applicazione");
		String username = utl.getValueProperties("username");
		String password = utl.getValueProperties("password");
		String passwordUnica = utl.getValueProperties("passwordUnica");
		String sIdEnte = utl.getValueProperties("enteIdEnte.SUE.Comune.di.Ruvo.di.Puglia");

		//Estrazione multi allegati
		String endpointExtractOne = utl.getValueProperties("urlExtractAllegato");

		it.eng.auriga.repository2.webservices.extractone.WSExtractOneServiceLocator
		wsextractOneService = new it.eng.auriga.repository2.webservices.extractone.WSExtractOneServiceLocator();
		wsextractOneService.setWSExtractOnePortEndpointAddress(endpointExtractOne);

		it.eng.auriga.repository2.webservices.extractone.WSIExtractOne wsExtractOne;
		try {
			wsExtractOne = wsextractOneService.getWSExtractOnePort();

			String xmlRichiestaExtractOne = getXmlExtractOne2(IdUD, IdDoc);

			String xmlHashExtractOne = RuvoUtils.hashXML(xmlRichiestaExtractOne, "");

			it.eng.auriga.repository2.webservices.extractone.Service serviceExtractOne = 
					new it.eng.auriga.repository2.webservices.extractone.Service();  

			serviceExtractOne.setCodApplicazione(codiceApplicazione);
			serviceExtractOne.setIstanzaApplicazione(codiceIstanza);
			serviceExtractOne.setUserName(username);
			//serviceExtractOne.setPassword("#RESERVED_WS_DEM");
			serviceExtractOne.setPassword(password);

			serviceExtractOne.setXml(xmlRichiestaExtractOne);
			serviceExtractOne.setHash(xmlHashExtractOne);

			System.out.println("REQUEST WS ExtractOne: " + xmlRichiestaExtractOne);

			it.eng.auriga.repository2.webservices.extractone.ServiceResponse responseExtractOne =  
					wsExtractOne.serviceOperation(serviceExtractOne);

			String risultatoExtractOne = responseExtractOne.getServiceReturn();

			risultatoExtractOne = RuvoUtils.decodeXml(risultatoExtractOne);

			System.out.println("RESULT: " + risultatoExtractOne);

			log.info("Estrazione allegati: \n" + risultatoExtractOne);
			checkResult(risultatoExtractOne);

			Object[] allegatiResponse = ((Stub) wsExtractOne).getAttachments();
			log.info("UnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);

			if (allegatiResponse.length == 0) {
				log.info("UnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);
				throw new Exception("Non è stato creata nessuna unita documentale");
			} else {

				fileBase64 = estraiAllegato(allegatiResponse);

				for(AllegatiProtocollati allegati: listAllegatiprotocollati) {
					System.out.println(" Risultato getAllegatiProtocollatiRuvo:" + allegati.getNomeFileAllegato());
				}				
			}
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
		return fileBase64;   
	}

	public static String getFileTimbrato2(int idUO, int IdDoc) {

		ArrayList<AllegatiProtocollati> listAllegatiprotocollati = new ArrayList<AllegatiProtocollati>();

		String fileBase64 = "";

		myUtility utl = new myUtility("protocollo_ruvo.properties", "protocollo_ruvo.log4j.properties");

		String idUdProtocollo = "", sIClassificazione = ""; 

		int anno = 0, numRegistro = 0;

		String codiceApplicazione = utl.getValueProperties("cod.applicazione");
		String codiceIstanza = utl.getValueProperties("istanza.applicazione");
		String username = utl.getValueProperties("username");
		String password = utl.getValueProperties("password");
		String passwordUnica = utl.getValueProperties("passwordUnica");
		String sIdEnte = utl.getValueProperties("enteIdEnte.SUE.Comune.di.Ruvo.di.Puglia");

		//Estrazione multi allegati
		String endpointExtractOne = utl.getValueProperties("urlExtractAllegato");

		it.eng.auriga.repository2.webservices.extractone.WSExtractOneServiceLocator
		wsextractOneService = new it.eng.auriga.repository2.webservices.extractone.WSExtractOneServiceLocator();
		wsextractOneService.setWSExtractOnePortEndpointAddress(endpointExtractOne);

		it.eng.auriga.repository2.webservices.extractone.WSIExtractOne wsExtractOne;
		try {
			wsExtractOne = wsextractOneService.getWSExtractOnePort();

			String xmlRichiestaExtractOne = getXmlExtractOne2(idUO, IdDoc);

			String xmlHashExtractOne = RuvoUtils.hashXML(xmlRichiestaExtractOne, "");

			it.eng.auriga.repository2.webservices.extractone.Service serviceExtractOne = 
					new it.eng.auriga.repository2.webservices.extractone.Service();  

			serviceExtractOne.setCodApplicazione(codiceApplicazione);
			serviceExtractOne.setIstanzaApplicazione(codiceIstanza);
			serviceExtractOne.setUserName(username);
			//serviceExtractOne.setPassword("#RESERVED_WS_DEM");
			serviceExtractOne.setPassword(password);

			serviceExtractOne.setXml(xmlRichiestaExtractOne);
			serviceExtractOne.setHash(xmlHashExtractOne);

			System.out.println("REQUEST WS ExtractOne: " + xmlRichiestaExtractOne);

			it.eng.auriga.repository2.webservices.extractone.ServiceResponse responseExtractOne =  
					wsExtractOne.serviceOperation(serviceExtractOne);

			String risultatoExtractOne = responseExtractOne.getServiceReturn();

			risultatoExtractOne = RuvoUtils.decodeXml(risultatoExtractOne);

			System.out.println("RESULT: " + risultatoExtractOne);

			//log.info("Estrazione allegati: \n" + risultatoExtractOne);
			checkResult(risultatoExtractOne);

			Object[] allegatiResponse = ((Stub) wsExtractOne).getAttachments();
			log.info("UnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);

			if (allegatiResponse.length == 0) {
				log.info("UnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);
				throw new Exception("Non è stato creata nessuna unita documentale");
			} else {

				fileBase64 = estraiAllegato(allegatiResponse);

				for(AllegatiProtocollati allegati: listAllegatiprotocollati) {
					System.out.println(" Risultato getAllegatiProtocollatiRuvo:" + allegati.getNomeFileAllegato());
				}				
			}
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
		return fileBase64;   
	}

	public static it.wego.auriga.xml.NewUD impostazione_numero_fascicolo(it.wego.auriga.xml.NewUD newUDInput, DocumentoProtocolloRequest documentoProtocollo) {
		it.wego.auriga.xml.NewUD newUDOutput = new it.wego.auriga.xml.NewUD();
		newUDOutput = newUDInput;

		NewUD.CollocazioneClassificazioneUD cl_fasc = new NewUD.CollocazioneClassificazioneUD();				
		ClassifFascicoloType classeFascicolo = new ClassifFascicoloType();
		EstremiXIdentificazioneFolderNoLibType exidFolder = new EstremiXIdentificazioneFolderNoLibType();
		exidFolder.setIdFolder(BigInteger.valueOf(Long.parseLong(documentoProtocollo.getCodiceFascicolo())));
		classeFascicolo.setFolderCustom(exidFolder);
		cl_fasc.getClassifFascicolo().add(classeFascicolo);		
		cl_fasc.setLibreriaUD("Fondo Unico");
		newUDOutput.setCollocazioneClassificazioneUD(cl_fasc);

		return newUDOutput;
	}
//	public static it.wego.auriga.xml.NewUD impostazione_tag_allegati(
	public static RequestProtocollo impostazione_tag_allegati(
			it.wego.auriga.xml.NewUD newUDInput, 
			DocumentoProtocolloRequest documentoProtocollo, 
			Pratica pratica, 
			List<Allegati> allegati, 
			PraticheEventi praticaEvento) throws Exception {

		RequestProtocollo reqOutput = new RequestProtocollo();  
		
		reqOutput.setAttachment(null);
		
		it.wego.auriga.xml.NewUD newUDOutput = new it.wego.auriga.xml.NewUD();
		newUDOutput = newUDInput;

		//Cerco allegato principale
		int idAllegatoPrincipale = 0; 
		List<PraticheEventiAllegati> lstEventiAllegati = praticaEvento.getPraticheEventiAllegatiList();
		
		for (PraticheEventiAllegati ev : lstEventiAllegati) {	
			if(ev.getFlgIsPrincipale().endsWith("S")) {
				idAllegatoPrincipale = ev.getAllegati().getId();
				break;
			}
		}

		//Tag <VersioneElettronica>
		int numeroAttachmentAssociato = 0;		
		int numeroAllegato = 1;		

		System.out.println(" Gestione allegati protocollazione in ingresso:");

		//Tag <VersioneElettronica>   
		List<AllegatoUDType> listaAllegati = new ArrayList<AllegatoUDType>();
		if (allegati != null && !allegati.isEmpty()) {

			Object[] attachment = new DataHandler[allegati.size()];
			
			// Imposto l'allegato principale
			for (Allegati a : allegati) {					
				if(a.getId().equals( Integer.valueOf(idAllegatoPrincipale)) ) {
					VersioneElettronicaType ve = new VersioneElettronicaType();
					ve.setNomeFile(a.getNomeFile());
					ve.setNroAttachmentAssociato(BigInteger.valueOf(numeroAllegato));
					ve.setFlagVersPubblicata("1");
					newUDOutput.setVersioneElettronica(ve);

					attachment[numeroAttachmentAssociato] = GestioneProtocolloUtility.getAttachment(a);

					numeroAllegato++;
					numeroAttachmentAssociato++;
					break;
				}				
			}

			for (Allegati a : allegati) {
				if(!a.getId().equals( Integer.valueOf(idAllegatoPrincipale)) ) {

					AllegatoUDType allegato = new AllegatoUDType();	
					VersioneElettronicaType ve = new VersioneElettronicaType();

					ve.setNomeFile(a.getNomeFile());
					System.out.println(" ALLEGATO da inviare nome:" + a.getNomeFile());					
					ve.setNroAttachmentAssociato(BigInteger.valueOf(numeroAllegato));

					System.out.println(" ALLEGATO da inviare numero:" + numeroAllegato);		

					ve.setFlagVersPubblicata("1");

					allegato.setVersioneElettronica(ve);
					allegato.setTipoDocAllegato(null);
					listaAllegati.add(allegato);
					newUDOutput.getAllegatoUD().add(allegato);
					
					attachment[numeroAttachmentAssociato] = GestioneProtocolloUtility.getAttachment(a);
					numeroAttachmentAssociato++;
					numeroAllegato++;
				}
			}
			
			newUDOutput.setNroAllegati(BigInteger.valueOf(numeroAllegato - 1));
			
			reqOutput.setNewud(newUDOutput);
			
			reqOutput.setAttachment(attachment);
		}

		return reqOutput;
	}
	public static void main(String[] args) {
		GestioneProtocolloUtility p = new GestioneProtocolloUtility();
	
		String xml = "<Output_FilesUD>"
				+ "	<DatiFileEstratto>"
				+ "		<NroAttachment>2</NroAttachment>"
				+ "		<IdDoc>82689</IdDoc>"
				+ "		<NomeFile>1000_riepilogo.pdf.p7m</NomeFile>"
				+ "		<NroVersione>1</NroVersione>"
				+ "		<NroUltimaVersione>1</NroUltimaVersione>"
				+ "		<RelazioneVsUD>"
				+ "			<CodId>P</CodId>"
				+ "			<Decodifica_Nome>Primario</Decodifica_Nome>"
				+ "		</RelazioneVsUD>"
				+ "		<TipoDoc>"
				+ "			<CodId/>"
				+ "			<Decodifica_Nome/>"
				+ "		</TipoDoc>"
				+ "		<DesOggetto>PDC</DesOggetto>"
				+ "	</DatiFileEstratto>"
				+ "	<DatiFileEstratto>"
				+ "		<NroAttachment>3</NroAttachment>"
				+ "		<IdDoc>82690</IdDoc>"
				+ "		<NomeFile>0_Documenti_identita.pdf.p7m</NomeFile>"
				+ "		<NroVersione>1</NroVersione>"
				+ "		<NroUltimaVersione>1</NroUltimaVersione>"
				+ "		<RelazioneVsUD>"
				+ "			<CodId>ALL</CodId>"
				+ "			<Decodifica_Nome>Allegato</Decodifica_Nome>"
				+ "		</RelazioneVsUD>"
				+ "		<NroAllegato>1</NroAllegato>"
				+ "		<TipoDoc>"
				+ "			<CodId/>"
				+ "			<Decodifica_Nome/>"
				+ "		</TipoDoc>"
				+ "		<DesOggetto/>"
				+ "	</DatiFileEstratto>"
				+ "	<DatiFileEstratto>"
				+ "		<NroAttachment>4</NroAttachment>"
				+ "		<IdDoc>82691</IdDoc>"
				+ "		<NomeFile>36_Progetto_della_qualita.pdf.p7m</NomeFile>"
				+ "		<NroVersione>1</NroVersione>"
				+ "		<NroUltimaVersione>1</NroUltimaVersione>"
				+ "		<RelazioneVsUD>"
				+ "			<CodId>ALL</CodId>"
				+ "			<Decodifica_Nome>Allegato</Decodifica_Nome>"
				+ "		</RelazioneVsUD>"
				+ "		<NroAllegato>2</NroAllegato>"
				+ "		<TipoDoc>"
				+ "			<CodId/>"
				+ "			<Decodifica_Nome/>"
				+ "		</TipoDoc>"
				+ "		<DesOggetto/>"
				+ "	</DatiFileEstratto></Output_FilesUD>";
		
		System.out.println("XML:" + xml);
		it.wego.auriga.xml.allegati.MyOutputFileUD f = GestioneProtocolloUtility.getDatiFileEstratto(xml);
	
	}
}
