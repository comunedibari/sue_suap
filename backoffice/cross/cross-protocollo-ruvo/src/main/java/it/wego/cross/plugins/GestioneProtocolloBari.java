package it.wego.cross.plugins;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.AttachmentPart;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.client.Stub;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import it.eng.auriga.repository2.webservices.getmetadataud.WSGetMetadataUdServiceLocator;
import it.wego.auriga.xml.GetIdMetadati;
import it.wego.auriga.xml.allegati.AllegatiProtocollati;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.utils.RuvoUtils;
import utilities.Query;
import utilities.myUtility;

@Component
public class GestioneProtocolloBari implements GestioneAllegati {


	@PersistenceContext
    private EntityManager em;

	private String classificazione;

	private String protocollo_input;
	
	private String id_utente_protocollatore;
	
	private static final QName SERVICE_NAME = new QName("http://getmetadataud.webservices.repository2.auriga.eng.it", "WSGetMetadataUdService");

	private static final Logger log = LoggerFactory.getLogger("plugin");

	private myUtility utl;

	public GestioneProtocolloBari(){
		utl = new myUtility("protocollo_bari.properties", "protocollo_bari.log4j.properties");	
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public String getClassificazione() {
		return classificazione;
	}

	public void setClassificazione(String classificazione) {
		this.classificazione = classificazione;
	}

	public String getProtocollo_input() {
		return protocollo_input;
	}

	public void setProtocollo_input(String protocollo_input) {
		this.protocollo_input = protocollo_input;
	}

	public String getId_utente_protocollatore() {
		return id_utente_protocollatore;
	}

	public void setId_utente_protocollatore(String id_utente_protocollatore) {
		this.id_utente_protocollatore = id_utente_protocollatore;
	}
	
	
	public List<AllegatiProtocollati> getAllegatiProtocollatiBari(String numProtocollo) {
		
		ArrayList<AllegatiProtocollati> listAllegatiprotocollati = new ArrayList<AllegatiProtocollati>();
				
		String fileBase64 = "";
		
		myUtility utl = new myUtility("protocollo_bari.properties", "protocollo_bari.log4j.properties");
				
		String idUdProtocollo = "", sIClassificazione = ""; 
		
		int anno = 0, numRegistro = 0;
		
		String[] splitProtocollo = numProtocollo.split("/");
		
		String classificazione = splitProtocollo[0]; 
		
		anno = Integer.parseInt(splitProtocollo[1]);
		
		idUdProtocollo = splitProtocollo[2]; //Numero registro

	    Pattern p = Pattern.compile("(\\d+)");
	    Matcher m = p.matcher(classificazione);
	    while(m.find())
	    {
	    	sIClassificazione = m.group(1);
	    }
		
		String codiceApplicazione = utl.getValueProperties("cod.applicazione");
		String codiceIstanza = utl.getValueProperties("istanza.applicazione");
		String username = utl.getValueProperties("username");
		String password = utl.getValueProperties("password");
		Query q = new Query();
		int result = q.setUsernameAndPassword(2001);
		if(result == 0) {
			username = q.getsUserNameComune();
			password = q.getsPasswordComune();
		}		
		System.out.println("USERNAME: " + username);
		System.out.println("PASSWORD: " + password);

		//Estrazione multi allegati
		String endpointExtractMulti = utl.getValueProperties("urlExtractAllegati");

		it.eng.auriga.repository2.webservices.extractmulti.WSExtractMultiServiceLocator
		wsextractMultiService = new it.eng.auriga.repository2.webservices.extractmulti.WSExtractMultiServiceLocator();
		wsextractMultiService.setWSExtractMultiPortEndpointAddress(endpointExtractMulti);
		
		it.eng.auriga.repository2.webservices.extractmulti.WSIExtractMulti wsExtractMulti;
		try {
			wsExtractMulti = wsextractMultiService.getWSExtractMultiPort();
					
			String xmlRichiestaExtractMulti = GestioneProtocolloUtility.getXmlExtractMulti(Integer.parseInt(idUdProtocollo), anno);
			String xmltemp = utilities.StringEscapeUtils.unescapeXml(xmlRichiestaExtractMulti);
			System.out.println("xmlRichiestaExtractMulti: " + xmltemp);
			String xmlHashExtractMulti = RuvoUtils.hashXML(xmlRichiestaExtractMulti, "");

			it.eng.auriga.repository2.webservices.extractmulti.Service serviceExtractMulti = 
					new it.eng.auriga.repository2.webservices.extractmulti.Service();  

			serviceExtractMulti.setCodApplicazione(codiceApplicazione);
			serviceExtractMulti.setIstanzaApplicazione(codiceIstanza);
			serviceExtractMulti.setUserName(username);
			serviceExtractMulti.setPassword(password);
	
			serviceExtractMulti.setXml(xmlRichiestaExtractMulti);
			serviceExtractMulti.setHash(xmlHashExtractMulti);

			it.eng.auriga.repository2.webservices.extractmulti.ServiceResponse responseExtractMulti =  
					wsExtractMulti.serviceOperation(serviceExtractMulti);

			String risultatoExtractMulti = responseExtractMulti.getServiceReturn();
	
			risultatoExtractMulti = RuvoUtils.decodeXml(risultatoExtractMulti);
			
			System.out.println("RESULT: " + risultatoExtractMulti);
			
			log.info("Estrazione allegati: \n" + risultatoExtractMulti);
			
			GestioneProtocolloUtility.checkResult(risultatoExtractMulti);
	
			Object[] allegatiResponse = ((Stub) wsExtractMulti).getAttachments();
			log.info("creazioneUnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);
	
			if (allegatiResponse.length == 0) {
				log.info("creazioneUnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);
				throw new Exception("Non è stato creata nessuna unita documentale");
			} else {
				
				listAllegatiprotocollati = GestioneProtocolloUtility.estraiTuttiGliAllegati(allegatiResponse);
			
				for(AllegatiProtocollati allegati: listAllegatiprotocollati) {
					System.out.println(" Risultato getAllegatiProtocollatiBari:" + allegati.getNomeFileAllegato());
				}

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return listAllegatiprotocollati;   
	}
	
	
	@Override
	public void add(Allegati allegato, Enti ente, LkComuni comune) throws Exception {		
	}


	@Override
	public void add(Allegati allegato, Enti ente, LkComuni comune, Date date) throws Exception {
	}

	@Override
	public byte[] getFileContent(Allegati allegato, Enti ente, LkComuni comune) throws Exception {
		return null;
	}

	@Override
	public Allegato getFile(String idFileEsterno, Enti ente, LkComuni comune) throws Exception {
		return null;
	}

	@Override
	public String uploadFile(Pratica pratica, List<Allegati> allegati) throws Exception {
		return null;
	}

	@Override
	public String uploadAttachmentsOnMyPage(Pratica pratica, List<Allegati> allegati) throws Exception {
		return null;
	}
	public String getFileProtocollatoTimbrato(String protocollo, String nomeFile) {
		String fileTimbrato = "", sIClassificazione = "", sIAnno = "", sIProtocollo = "";
		
		GetIdMetadati metadati = new GetIdMetadati();
				
		int anno = 0, numRegistro = 0, numProtocollo;
		
		String[] splitProtocollo = protocollo.split("/");
		
		String classificazione = splitProtocollo[0]; 
		
		String sAnno = splitProtocollo[1];
		
		String sProtocollo = splitProtocollo[2];
		
		anno = Integer.parseInt(sAnno);
		
		numProtocollo = Integer.parseInt(sProtocollo); //Numero registro

	    Pattern p = Pattern.compile("(\\d+)");
	    Matcher m = p.matcher(classificazione);
	    while(m.find()) { sIClassificazione = m.group(1); }
	    
		try {
			metadati = getMetadataAllegatiBari(anno, numProtocollo, nomeFile);

			fileTimbrato = GestioneProtocolloUtility.getFileTimbrato(metadati.getIdUd(), metadati.getIdDoc());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileTimbrato;
	}

	private GetIdMetadati getMetadataAllegatiBari(int anno, int numProtocollo, String nomeFile) throws Exception {		
		GetIdMetadati responseMetadati = new GetIdMetadati();

		String codiceApplicazione = utl.getValueProperties("cod.applicazione");
		String codiceIstanza = utl.getValueProperties("istanza.applicazione");
		String username = utl.getValueProperties("username");
		String password = utl.getValueProperties("password");

		//Recupero informazioni dal protocollo
		String UrlGetUd = utl.getValueProperties("urlGetUd");
		String endpointGetUd = UrlGetUd;

		String IdUD = "54807";

		String xmlRichiesta = GestioneProtocolloUtility.getXmlGetMetadata_allegati(anno, numProtocollo);
		String xmlHash = RuvoUtils.hashXML(xmlRichiesta, "");

		it.eng.auriga.repository2.webservices.getmetadataud.Service serviceMeta = new it.eng.auriga.repository2.webservices.getmetadataud.Service();  
		serviceMeta.setCodApplicazione(codiceApplicazione);
		serviceMeta.setIstanzaApplicazione(codiceIstanza);
		serviceMeta.setUserName(username);
		serviceMeta.setPassword(password);
		serviceMeta.setXml(xmlRichiesta);
		serviceMeta.setHash(xmlHash);

		WSGetMetadataUdServiceLocator wsLocatorGetUd = new WSGetMetadataUdServiceLocator();
		wsLocatorGetUd.setWSGetMetadataUdPortEndpointAddress(endpointGetUd);
		URL endpointGetUdUrl = new URL(endpointGetUd); 
		it.eng.auriga.repository2.webservices.getmetadataud.WSIGetMetadataUd
		wsGetMetadataUd =  wsLocatorGetUd.getWSGetMetadataUdPort();   

		it.eng.auriga.repository2.webservices.getmetadataud.ServiceResponse responseMeta = wsGetMetadataUd.serviceOperation(serviceMeta);
		String risultato = responseMeta.getServiceReturn();

		risultato = RuvoUtils.decodeXml(risultato);

		log.info("Protocollo Bari:creazioneUnitaDocumentaria:Risposta del documentale: \n" + risultato);
		GestioneProtocolloUtility.checkResult(risultato);

		Object[] allegatiResponse = ((Stub) wsGetMetadataUd).getAttachments();
		log.info("Protocollo Bari:Metadati:Numero di allegati: " + allegatiResponse.length);

		if (allegatiResponse.length == 0) {
			log.debug("Protocollo Bari:Metadati:Numero di allegati: " + allegatiResponse.length);
			throw new Exception("Non è stato creata nessuna unita documentaria");
		} else {
			DataHandler esitoGetMetaDati = ((AttachmentPart) allegatiResponse[0]).getDataHandler();
			if (esitoGetMetaDati != null) {
				log.info("Protocollo Bari: esitoGetMetaDati: Esito GetMetaDati unita documentale");
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
				String sNomeFile = "";
				String sNomeFileOriginale = "";

				{ // Valorizzazione risposta protocollazione Bari
					XPathExpression expr = xpath.compile("//DatiUD/IdUD/text()");
					Object result = expr.evaluate(doc, XPathConstants.NODESET);
					NodeList nodes = (NodeList) result;

					for (int i = 0; i < nodes.getLength(); i++) {
						IdUD = nodes.item(i).getNodeValue();
						System.out.println("DatiUD/IdUD:" + nodes.item(i).getNodeValue());
					}

					expr = xpath.compile("//DatiUD/IdDocPrimario/text()");
					result = expr.evaluate(doc, XPathConstants.NODESET);
					nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						sIdDocPrimario = nodes.item(i).getNodeValue();
						System.out.println("DatiUD/IdDocPrimario:" + nodes.item(i).getNodeValue());
					}

					expr = xpath.compile("//DatiUD/RegistrazioneData/NumReg/text()");
					result = expr.evaluate(doc, XPathConstants.NODESET);
					nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						sNumReg = nodes.item(i).getNodeValue();
						System.out.println("DatiUD/RegistrazioneData/NumReg:" + nodes.item(i).getNodeValue());
					}

					expr = xpath.compile("//DatiUD/RegistrazioneData/AnnoReg/text()");
					result = expr.evaluate(doc, XPathConstants.NODESET);
					nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						sAnnoReg = nodes.item(i).getNodeValue();
						System.out.println("DatiUD/RegistrazioneData/AnnoReg:" + nodes.item(i).getNodeValue());
					}

					expr = xpath.compile("//DatiUD/RegistrazioneData/DataOraReg/text()");
					result = expr.evaluate(doc, XPathConstants.NODESET);
					nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						sDataOraReg = nodes.item(i).getNodeValue();
						System.out.println("DatiUD/RegistrazioneData/DataOraReg:" + nodes.item(i).getNodeValue());
					}

					expr = xpath.compile("//DatiUD/RegistrazioneData/SiglaReg/text()");
					result = expr.evaluate(doc, XPathConstants.NODESET);
					nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						sSiglaReg = nodes.item(i).getNodeValue();
						System.out.println("DatiUD/RegistrazioneData/SiglaReg:" + nodes.item(i).getNodeValue());
					}

					expr = xpath.compile("//DatiUD/RegistrazioneData/UOReg/IdUO/text()");
					result = expr.evaluate(doc, XPathConstants.NODESET);
					nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						sIdUO = nodes.item(i).getNodeValue();
						System.out.println("DatiUD/RegistrazioneData/UOReg/IdUO:" + nodes.item(i).getNodeValue());
					}

					expr = xpath.compile("//DatiUD/VersioneElettronica/NomeFile/text()");
					result = expr.evaluate(doc, XPathConstants.NODESET);
					nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						sNomeFile = nodes.item(i).getNodeValue();
						System.out.println("DatiUD/VersioneElettronica/NomeFile:" + nodes.item(i).getNodeValue());
					}		

					expr = xpath.compile("//DatiUD/VersioneElettronica/NomeFileOriginale/text()");
					result = expr.evaluate(doc, XPathConstants.NODESET);
					nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						sNomeFileOriginale = nodes.item(i).getNodeValue();
						System.out.println("DatiUD/VersioneElettronica/NomeFileOriginale:" + nodes.item(i).getNodeValue());
					}		

				}

				String dNow = myUtility.getLocalTime();
				Date dDataProtocollazione = myUtility.parseDateTime(dNow);
				if(!sDataOraReg.isEmpty()) {
					dDataProtocollazione = myUtility.parseDateTime(sDataOraReg);							
				}

				Calendar c = Calendar.getInstance();
				c.setTime(dDataProtocollazione);

				String sResponse = "Response protocollo Bari: IdUD:" + IdUD + 
						" IdDocPrimario:" + sIdDocPrimario +
						" AnnoReg:" + sAnnoReg + 
						" NumReg:" + sNumReg + 
						" DataOraReg:" + sDataOraReg +
						" SiglaReg:" + sSiglaReg +
						" IdUO:" + sIdUO +
						" data protocollazione:" + dDataProtocollazione;

				System.out.println("------------------------ Dati protocollo Bari ----------------------------");
				System.out.println(" IdUD:" + IdUD );
				System.out.println(" SiglaReg:" + IdUD );
				System.out.println(" AnnoReg:" + sAnnoReg );
				System.out.println(" NumReg:" + sNumReg );
				System.out.println(" DataOraReg:" + sDataOraReg );
				System.out.println("-------------------------------------------------------------------------");

				responseMetadati.setIdUd(Integer.parseInt(IdUD));				
				responseMetadati.setIdDoc(Integer.parseInt(sIdDocPrimario));
				responseMetadati.setNomeFile(sNomeFile);
				responseMetadati.setNomeFileOriginale(sNomeFileOriginale);
				
        		String nomefileCrossConUndescore = nomeFile;
        		
        		//LOR Fix anomalia doppio blank
        		//nomefileCrossConUndescore = nomefileCrossConUndescore.replaceAll("\\s+", "_");
    			nomefileCrossConUndescore = myUtility.replaceAllSpaces(nomefileCrossConUndescore);
				if(nomefileCrossConUndescore != null && nomefileCrossConUndescore.length()>0) { 
					if (!nomefileCrossConUndescore.equals(sNomeFile) && !nomeFile.equals(sNomeFileOriginale) ) {					
						responseMetadati.setIdDoc(0);
						responseMetadati.setNomeFile("");
						responseMetadati.setNomeFileOriginale("");
					}
				} 

				log.info(sResponse);

			}
		}

		return responseMetadati;
	}
	public static void main(String[] args) {

		it.wego.cross.plugins.GestioneProtocolloBari p = new it.wego.cross.plugins.GestioneProtocolloBari();

		try {
			myUtility utl = new myUtility("protocollo_bari.properties", "protocollo_bari.log4j.properties");
			// cf_232/2022/1234 Ricezione pratica (Comune di Bari) 

	
			String nomeFile = "PCT  55-2024.pdf";
			String nomefileCrossConUndescore = nomeFile;
			String sNomeFile = "PCT__55-2024.pdf";
			String sNomeFileOriginale = "PCT__55-2024.pdf";
//ORI			nomefileCrossConUndescore = nomefileCrossConUndescore.replaceAll("\\s+", "_");
			nomefileCrossConUndescore = myUtility.replaceAllSpaces(nomefileCrossConUndescore);
			if(nomefileCrossConUndescore != null && nomefileCrossConUndescore.length()>0) { 
				if (!nomefileCrossConUndescore.equals(sNomeFile) && !nomeFile.equals(sNomeFileOriginale) ) {	
					int i=0;
					/*
					responseMetadati.setIdDoc(0);
					responseMetadati.setNomeFile("");
					responseMetadati.setNomeFileOriginale("");
					*/
				}
			} 
			
			String protocolloBari = "c_a662/2024/105447";
			
			String nomeFile1 = "180812-2022 02.CILA con adempimenti SUAP _MOD.SUAP-24_.pdf";
			
			String nomefiletest = nomeFile1.replaceAll("\\s+", "_");
			
			System.out.println(" nomefiletest1:" + nomefiletest);

			String nomeFile2 = "11_Documentazione_Fotografica_Cila_via_Grotta_della_Regina_60.pdf.p7m";
			
			nomefiletest = nomeFile2.replaceAll("\\s+", "_");
			
			System.out.println(" nomefiletest2:" + nomefiletest);
			
			nomeFile = "180812-2022 02.CILA con adempimenti SUAP _MOD.SUAP-24_.pdf";
			
			String fileBase64 = p.getFileProtocollatoTimbrato(protocolloBari, nomeFile);
						
			byte[] body =  Base64.getDecoder().decode(fileBase64);
			Allegato ret = new Allegato();		
			ret.setFile(body);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
