package it.wego.cross.plugins;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.AttachmentPart;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.axis.client.Stub;
import org.apache.axis.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.common.base.Strings;

import it.eng.auriga.repository2.webservices.getmetadataud.WSGetMetadataUdServiceLocator;
import it.wego.auriga.xml.AssegnatarioEffType;
import it.wego.auriga.xml.AssegnazioneInternaType;
import it.wego.auriga.xml.ClassificazioneType;
import it.wego.auriga.xml.ComuneItalianoType;
import it.wego.auriga.xml.DestinatarioEsternoType;
import it.wego.auriga.xml.EmailType;
import it.wego.auriga.xml.FascDiTitolarioType;
import it.wego.auriga.xml.GetIdMetadati;
import it.wego.auriga.xml.LivelloGerarchiaType;
import it.wego.auriga.xml.MetadatiAllegati;
import it.wego.auriga.xml.NewFolder;
import it.wego.auriga.xml.NewUD;
import it.wego.auriga.xml.SoggettoEsternoType;
import it.wego.auriga.xml.UOType;
import it.wego.auriga.xml.UserType;
import it.wego.auriga.xml.allegati.AllegatiProtocollati;
import it.wego.cross.constants.AvRuvoConstants;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloRequest;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.plugins.protocollo.beans.FilterProtocollo;
import it.wego.cross.plugins.protocollo.beans.Protocollo;
import it.wego.cross.plugins.protocollo.beans.SoggettoProtocollo;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.RuvoUtils;
import it.wego.cross.utils.Utils;
import utilities.ParserXml;
import utilities.Query;
import utilities.myUtility;

@Component
public class GestioneProtocolloRuvo implements GestioneProtocollo, GestioneAllegati {


	@PersistenceContext
	private EntityManager em;

	private String classificazione;

	private String protocollo_input;

	private String id_utente_protocollatore;

	private static final Logger log = LoggerFactory.getLogger("plugin");

	private myUtility utl;

	public GestioneProtocolloRuvo(){
		utl = new myUtility("protocollo_ruvo.properties", "protocollo_ruvo.log4j.properties");	
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

	public DocumentoProtocolloResponse protocolla(DocumentoProtocolloRequest documentoProtocollo, Pratica pratica, List<Allegati> allegati, PraticheEventi praticaEvento) throws ServiceException, RemoteException, NoSuchAlgorithmException, JAXBException, Exception {
		DocumentoProtocolloResponse protocolloResponse = new DocumentoProtocolloResponse();

		myUtility utl = new myUtility("protocollo_ruvo.properties", "protocollo_ruvo.log4j.properties");;

		Enti ente = pratica.getIdProcEnte().getIdEnte();		
		LkComuni comune = null;

		if (pratica.getIdComune() != null) {
			comune = pratica.getIdComune();
		}

		documentoProtocollo.setClassifica(this.getClassificazione());

		String fascicolo = documentoProtocollo.getCodiceFascicolo();

		if (Strings.isNullOrEmpty(fascicolo)) {
			fascicolo = protocollaCreaFascicolo(documentoProtocollo);

			// aggiorno il fascicolo sulla pratica            
			documentoProtocollo.setCodiceFascicolo(fascicolo);
		}

		if (documentoProtocollo.getDirezione().equals(AvRuvoConstants.PROTOCOLLO_INPUT)) {
			protocolloResponse = protocollaIngressoFascicolo(documentoProtocollo, pratica, allegati,  praticaEvento);
			
			protocolloResponse.setFascicolo(fascicolo);
		} else {
			protocolloResponse = protocollaUscitaFascicolo(documentoProtocollo, pratica, allegati,  praticaEvento);

			protocolloResponse.setFascicolo(fascicolo);
		}

		return protocolloResponse;
	}

	public String protocollaCreaFascicolo(DocumentoProtocolloRequest documentoProtocollo) throws ServiceException, RemoteException, NoSuchAlgorithmException, JAXBException, Exception {
		String fascicolo = "";
		myUtility utl = new myUtility("protocollo_ruvo.properties", "protocollo_ruvo.log4j.properties");;

		NewFolder newfolder = new NewFolder();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dataF = sdf.format(new Date());

		//FASCICOLAZIONE BARI creazioneRequest.setDescrizione("Fascicolo - " + documentoProtocollo.getIdPratica() + " - " + dataF);
		newfolder.setOggetto("Fascicolo - " + documentoProtocollo.getIdPratica() + " - " + dataF);

		FascDiTitolarioType fascdititolario = new FascDiTitolarioType();

		fascdititolario.setFlagTipo("F");

		ClassificazioneType classificazione = new ClassificazioneType();

		String codice_classificazione = documentoProtocollo.getClassifica();
		if(codice_classificazione == null) {codice_classificazione="6.11";}

		String[] split_classificazione = codice_classificazione.split("\\.");

		List<LivelloGerarchiaType> livelloClassificazione = new ArrayList<LivelloGerarchiaType>();


		LivelloGerarchiaType lvg1 = new LivelloGerarchiaType();

		lvg1.setNro(1);		
		lvg1.setCodice(split_classificazione[0]);
		livelloClassificazione.add(lvg1);

		LivelloGerarchiaType lvg2 = new LivelloGerarchiaType();
		lvg2.setNro(2);		
		lvg2.setCodice(split_classificazione[1]);
		livelloClassificazione.add(lvg2);
		classificazione.setLivelloClassificazione(livelloClassificazione);

		fascdititolario.setClassificazione(classificazione);

		AssegnatarioEffType assegnatario = new AssegnatarioEffType();
		UserType userAssegnatario = new UserType();

		String stringUtenteProtocollazione = this.getId_utente_protocollatore(); 

		if (StringUtils.isEmpty(stringUtenteProtocollazione)) {
			throw new Exception("Sulla tabella di configurazione non e' stata valorizzata la chiave '" + AvRuvoConstants.ID_UTENTE_PROTOCOLLATORE + "'");
		}

		newfolder.setParoleChiave("Fascicolo di collaudo 6.11");
		newfolder.setLibreria("Fondo Unico");

		NewFolder.DatiFolderCustom fascFolderCustom = new NewFolder.DatiFolderCustom();
		fascFolderCustom.setNomeFolder(documentoProtocollo.getIdentificativoPratica());

		newfolder.setDatiFascDiTitolario(fascdititolario);

		UOType uo = new UOType();		

		// Collaudo uo.setIdUO(BigInteger.valueOf(Integer.valueOf(4048)));
		uo.setIdUO(BigInteger.valueOf(Integer.valueOf(399)));
		assegnatario.setUO(uo);
		newfolder.setInCaricoA(assegnatario);

		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		Date data = new Date();
		gregorianCalendar.setTime(data);
		XMLGregorianCalendar startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);

		String Url = utl.getValueProperties("urlFascicolo");
		String endpointCreazioneFolder = Url;

		it.eng.auriga.repository2.webservices.addfolder.WSAddFolderServiceLocator 
		wsLocator = new it.eng.auriga.repository2.webservices.addfolder.WSAddFolderServiceLocator();

		wsLocator.setWSAddFolderPortEndpointAddress(endpointCreazioneFolder);

		it.eng.auriga.repository2.webservices.addfolder.WSIAddFolder wsAddFolder = wsLocator.getWSAddFolderPort();   

		String codiceApplicazione = utl.getValueProperties("cod.applicazione");
		String codiceIstanza = utl.getValueProperties("istanza.applicazione");
		String username = utl.getValueProperties("username");
		String password = utl.getValueProperties("password");
		String xmlRichiesta = Utils.marshall(newfolder);
		String xmlHash = RuvoUtils.hashXML(xmlRichiesta, "");

		log.debug("Protocollo Ruvo:creazioneFascicolo:Hash dell'XML da inviare al Documentale: \n" + xmlHash);

		it.eng.auriga.repository2.webservices.addfolder.Service service = new it.eng.auriga.repository2.webservices.addfolder.Service();  
		service.setCodApplicazione(codiceApplicazione);
		service.setIstanzaApplicazione(codiceIstanza);
		service.setUserName(username);
		service.setPassword(password);
		service.setXml(xmlRichiesta);
		service.setHash(xmlHash);

		it.eng.auriga.repository2.webservices.addfolder.ServiceResponse response = wsAddFolder.serviceOperation(service);
		String risultato = response.getServiceReturn();

		risultato = RuvoUtils.decodeXml(risultato);

		log.debug("Protocollo Ruvo:creazioneFascicolo:Risposta del documentale: \n" + risultato);
		GestioneProtocolloUtility.checkResult(risultato);

		Object[] allegatiResponse = ((Stub) wsAddFolder).getAttachments();
		log.debug("Protocollo Ruvo:creazioneFascicolo:Numero di allegati: " + allegatiResponse.length);

		if (allegatiResponse.length == 0) {
			log.debug("Protocollo Ruvo:creazioneFascicolo:Numero di allegati: " + allegatiResponse.length);
			throw new Exception("Non è stato creata nessun fascicolo");
		} else {
			//Il secondo attachment è l'esito della chiamata
			DataHandler esitoCreazioneFascicolo = ((AttachmentPart) allegatiResponse[0]).getDataHandler();
			if (esitoCreazioneFascicolo != null) {
				log.debug("Protocollo Ruvo:creazioneFascicolo: Esito creazione del fascicolo");
				InputStream inputStream = esitoCreazioneFascicolo.getInputStream();
				byte[] content = IOUtils.toByteArray(inputStream);
				String esitoXml = new String(content);

				String esitoXmlApp = utilities.StringEscapeUtils.unescapeXml(esitoXml);

				esitoXml = esitoXmlApp.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();

				esitoXml = esitoXml.replaceAll("\\p{Zs}+", " ");

				String IdFolder = ParserXml.getTagValue(esitoXml, "IdFolder");

				System.out.println(" IdFolder:" + IdFolder );
				fascicolo = IdFolder;
				System.out.println("------------- RECUPERO INFORMAZIONI SUL FASCICOLO ---------------");
			}
		}
		return fascicolo;
	}

	public DocumentoProtocolloResponse protocollaIngressoFascicolo(DocumentoProtocolloRequest documentoProtocollo, Pratica pratica, List<Allegati> allegati, PraticheEventi praticaEvento) throws ServiceException, RemoteException, NoSuchAlgorithmException, JAXBException, Exception {
		DocumentoProtocolloResponse protocolloResponse = new DocumentoProtocolloResponse();

		//String xmlRichiestaEntrata = getXmlCreazioneUdEntrata(documentoProtocollo, pratica, allegati, praticaEvento);

		RequestProtocollo req = getXmlCreazioneUdEntrata(documentoProtocollo, pratica, allegati, praticaEvento);
		
		log.info("Protocollo Ruvo:protocollaIngressoFascicolo xml input: \n" + req.getXmlRequest());

		//protocolloResponse = protocollaRuvo( documentoProtocollo, xmlRichiestaEntrata, pratica, allegati, praticaEvento);		
		protocolloResponse = protocollaRuvo( documentoProtocollo, req.getXmlRequest(), pratica, req.getAttachment(), praticaEvento);		

		return protocolloResponse;
	}

	public DocumentoProtocolloResponse protocollaUscitaFascicolo(DocumentoProtocolloRequest documentoProtocollo, Pratica pratica, List<Allegati> allegati, PraticheEventi praticaEvento) throws ServiceException, RemoteException, NoSuchAlgorithmException, JAXBException, Exception {
		DocumentoProtocolloResponse protocolloResponse = new DocumentoProtocolloResponse();


//		String xmlRichiestaUscita = getXmlCreazioneUdUscita(documentoProtocollo, pratica, allegati, praticaEvento);
		RequestProtocollo req = getXmlCreazioneUdUscita(documentoProtocollo, pratica, allegati, praticaEvento);

		log.info("Protocollo Ruvo:protocollaUscitaFascicolo xml input: \n" + req.getXmlRequest());

		protocolloResponse = protocollaRuvo( documentoProtocollo, req.getXmlRequest(), pratica, req.getAttachment(), praticaEvento);		

		return protocolloResponse;
	}

	public DocumentoProtocolloResponse protocollaRuvo(
			DocumentoProtocolloRequest documentoProtocollo,
			String xmlRichiesta,
			Pratica pratica, 
			//List<Allegati> allegati,
			Object[] attachment,
			PraticheEventi praticaEvento) throws ServiceException, RemoteException, NoSuchAlgorithmException, JAXBException, Exception {

		DocumentoProtocolloResponse protocolloResponse = new DocumentoProtocolloResponse();

		myUtility utl = new myUtility("protocollo_ruvo.properties", "protocollo_ruvo.log4j.properties");;

		Enti ente = pratica.getIdProcEnte().getIdEnte();		
		LkComuni comune = null;

		if (pratica.getIdComune() != null) {
			comune = pratica.getIdComune();
		}
		Integer idComune = comune == null ? null : comune.getIdComune();
		Integer idEnte = ente == null ? null : ente.getIdEnte();

		String Url = utl.getValueProperties("url");
		String endpointCreazioneUd = Url;

		it.eng.auriga.repository2.webservices.addunitadoc.WSAddUdServiceLocator 
		wsLocator = new it.eng.auriga.repository2.webservices.addunitadoc.WSAddUdServiceLocator();

		wsLocator.setWSAddUdPortEndpointAddress(endpointCreazioneUd);

		it.eng.auriga.repository2.webservices.addunitadoc.WSIAddUd wsAddUd = wsLocator.getWSAddUdPort();   

		String codiceApplicazione = utl.getValueProperties("cod.applicazione");
		String codiceIstanza = utl.getValueProperties("istanza.applicazione");
		String username = utl.getValueProperties("username");
		String password = utl.getValueProperties("password");

		//PROTOCOLLA IN INGRESSO
		{
			log.info("Protocollo Ruvo:creazioneUnitaDocumentaria:XML da inviare al Documentale: \n" + xmlRichiesta);

			String xmlHash = RuvoUtils.hashXML(xmlRichiesta, "");
			log.info("Protocollo Ruvo:creazioneUnitaDocumentaria:Hash dell'XML da inviare al Documentale: \n" + xmlHash);
			/*
			Object[] attachment = null;
			if (allegati != null && !allegati.isEmpty()) {
				attachment = new DataHandler[allegati.size()];
				int i = 0;
				for (Allegati allegato : allegati) {
					attachment[i] = GestioneProtocolloUtility.getAttachment(allegato);
					i++;
				}
			}
			*/
			
			/* Solo x Test Stefania LORENZO 28/12/2023
			 * 
			int sizeAttachments = Array.getLength(attachment);
			log.info("Protocollo Ruvo:creazioneUnitaDocumentaria:numero di attachments da inviare: \n" + sizeAttachments);
			for (int i=0;i<(int) sizeAttachments; i++) {
				Object ds = attachment[i];
				DataHandler dth = (DataHandler) ds;
				ByteArrayDataSource bds = new ByteArrayDataSource();
				InputStream is;
				try {
					is = dth.getInputStream();
					String nomeFileOut = "/tmp/lorenzo/" + dth.getName();
					File fileOut = new File(nomeFileOut);
					GestioneProtocolloUtility.saveContentToFile(dth,fileOut);
					log.info("Protocollo Ruvo:creazioneUnitaDocumentaria:nome file attachments da inviare: \n" + dth.getName());
				} catch (IOException e) {
					e.printStackTrace();
				}														
			}
			 FINE TEST
			 * 
			 */
			it.eng.auriga.repository2.webservices.addunitadoc.Service service = new it.eng.auriga.repository2.webservices.addunitadoc.Service();  
			service.setCodApplicazione(codiceApplicazione);
			service.setIstanzaApplicazione(codiceIstanza);
			service.setUserName(username);
			service.setPassword(password);
			service.setXml(xmlRichiesta);
			service.setHash(xmlHash);
			it.eng.auriga.repository2.webservices.addunitadoc.ServiceResponse response = wsAddUd.serviceOperation(service, attachment);
			String risultato = response.getServiceReturn();

			risultato = RuvoUtils.decodeXml(risultato);

			log.info("Protocollo Ruvo:creazioneUnitaDocumentaria:Risposta del documentale: \n" + risultato);
			GestioneProtocolloUtility.checkResult(risultato);

			Object[] allegatiResponse = ((Stub) wsAddUd).getAttachments();
			log.info("Protocollo Ruvo:creazioneUnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);

			if (allegatiResponse.length == 0) {
				log.info("Protocollo Ruvo:creazioneUnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);
				throw new Exception("Non è stato creata nessuna unita documentale");
			} else {
				//Il secondo attachment è l'esito della chiamata
				DataHandler esitoCreazioneUnitaDocumentale = ((AttachmentPart) allegatiResponse[0]).getDataHandler();
				if (esitoCreazioneUnitaDocumentale != null) {
					log.info("Protocollo Ruvo:creazioneUnitaDocumentaria: Esito della creazione della unita documentale");
					InputStream inputStream = esitoCreazioneUnitaDocumentale.getInputStream();
					byte[] content = IOUtils.toByteArray(inputStream);
					String esitoXml = new String(content);

					String esitoXmlApp = utilities.StringEscapeUtils.unescapeXml(esitoXml);

					esitoXml = esitoXmlApp.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();

					esitoXml = esitoXml.replaceAll("\\p{Zs}+", " ");

					InputSource source = new InputSource(new StringReader(esitoXml));
					XPath xpath = XPathFactory.newInstance().newXPath();
					Object Output_UD_Object = xpath.evaluate("/Output_UD", source, XPathConstants.NODE);
					String IdUD = xpath.evaluate("IdUD", Output_UD_Object);
					System.out.println(" IdUD:" + IdUD );
					System.out.println("----------------------------");

					//Recupero informazioni dal protocollo
					String UrlGetUd = utl.getValueProperties("urlGetUd");
					String endpointGetUd = UrlGetUd;

					WSGetMetadataUdServiceLocator wsLocatorGetUd = new WSGetMetadataUdServiceLocator();
					wsLocatorGetUd.setWSGetMetadataUdPortEndpointAddress(endpointGetUd);

					URL endpointGetUdUrl = new URL(endpointGetUd); 
					it.eng.auriga.repository2.webservices.getmetadataud.WSIGetMetadataUd
					wsGetMetadataUd =  wsLocatorGetUd.getWSGetMetadataUdPort();   

					xmlRichiesta = GestioneProtocolloUtility.getXmlGetMetadata_Ud(IdUD);
					xmlHash = RuvoUtils.hashXML(xmlRichiesta, "");

					it.eng.auriga.repository2.webservices.getmetadataud.Service serviceMeta = new it.eng.auriga.repository2.webservices.getmetadataud.Service();  
					serviceMeta.setCodApplicazione(codiceApplicazione);
					serviceMeta.setIstanzaApplicazione(codiceIstanza);
					serviceMeta.setUserName(username);
					serviceMeta.setPassword(password);
					serviceMeta.setXml(xmlRichiesta);
					serviceMeta.setHash(xmlHash);

					it.eng.auriga.repository2.webservices.getmetadataud.ServiceResponse responseMeta = wsGetMetadataUd.serviceOperation(serviceMeta);
					risultato = responseMeta.getServiceReturn();

					risultato = RuvoUtils.decodeXml(risultato);

					log.info("Protocollo Ruvo:creazioneUnitaDocumentaria:Risposta del documentale: \n" + risultato);
					GestioneProtocolloUtility.checkResult(risultato);

					allegatiResponse = ((Stub) wsGetMetadataUd).getAttachments();
					log.info("Protocollo Ruvo:Metadati:Numero di allegati: " + allegatiResponse.length);

					if (allegatiResponse.length == 0) {
						log.debug("Protocollo Ruvo:Metadati:Numero di allegati: " + allegatiResponse.length);
						throw new Exception("Non è stato creata nessuna unita documentale");
					} else {
						DataHandler esitoGetMetaDati = ((AttachmentPart) allegatiResponse[0]).getDataHandler();
						if (esitoGetMetaDati != null) {
							log.info("Protocollo Ruvo: esitoGetMetaDati: Esito GetMetaDati unita documentale");
							inputStream = esitoGetMetaDati.getInputStream();
							content = IOUtils.toByteArray(inputStream);
							esitoXml = new String(content);

							esitoXmlApp = utilities.StringEscapeUtils.unescapeXml(esitoXml);

							esitoXml = esitoXmlApp.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();

							esitoXml = esitoXml.replaceAll("\\p{Zs}+", " ");

							// System.out.println(">>>>>>>>> esitoXml:" + esitoXml);

							DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
							factory.setNamespaceAware(true); 
							DocumentBuilder builder = factory.newDocumentBuilder();
							source = new InputSource(new StringReader(esitoXml));
							org.w3c.dom.Document doc = builder.parse(source);

							//Create XPath
							XPathFactory xpathfactory = XPathFactory.newInstance();
							xpath = xpathfactory.newXPath();

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

							protocolloResponse.setDataProtocollo(dDataProtocollazione);
							protocolloResponse.setAnnoProtocollo(sAnnoReg);
							protocolloResponse.setCodRegistro(sSiglaReg);
							protocolloResponse.setIdDocumento(IdUD);
							protocolloResponse.setNumeroProtocollo(sNumReg);

							String sResponseProtocolloRuvo = "Response protocollo Ruvo: IdUD:" + IdUD + 
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
							log.info(sResponseProtocolloRuvo);

							protocolloResponse.setNumeroProtocollo(String.valueOf(protocolloResponse.getNumeroProtocollo()));
							protocolloResponse.setAnnoProtocollo(String.valueOf(protocolloResponse.getAnnoProtocollo()));
							protocolloResponse.setCodRegistro(documentoProtocollo.getAoo());
							protocolloResponse.setDataProtocollo(dDataProtocollazione);
							protocolloResponse.setFascicolo(documentoProtocollo.getCodiceFascicolo());

							// response.setCodRegistro(aoo);
							protocolloResponse.setAllegatoOriginale(documentoProtocollo.getAllegatoOriginale());
							protocolloResponse.setAllegati(documentoProtocollo.getAllegati());
						}
					}

					return protocolloResponse;
				} else {
					log.error("Protocollo Ruvo:non è stato trovato nessun allegato");
					throw new Exception("Non è stato creata nessuna unita documentale");
				}
			}
		}
	}

	private RequestProtocollo getXmlCreazioneUdEntrata(DocumentoProtocolloRequest documentoProtocollo, Pratica pratica, List<Allegati> allegati, PraticheEventi praticaEvento) throws Exception {
		RequestProtocollo reqOutput = new RequestProtocollo();

		it.wego.auriga.xml.NewUD newUD = new it.wego.auriga.xml.NewUD();
		String nome = pratica.getIdentificativoPratica();
		newUD.setNomeUD(nome);
		List<NewUD.RegistrazioneDaDare> registrazioneDaDare = new ArrayList<NewUD.RegistrazioneDaDare>();

		NewUD.RegistrazioneDaDare newud = new NewUD.RegistrazioneDaDare();
		newud.setCategoriaReg("PG");
		registrazioneDaDare.add(newud);		
		newUD.setRegistrazioneDaDare(registrazioneDaDare);

		newUD.setOggettoUD(pratica.getOggettoPratica());


		String stringUtenteProtocollazione = this.getId_utente_protocollatore();

		if (StringUtils.isEmpty(stringUtenteProtocollazione)) {
			throw new Exception("Sulla tabella di configurazione non e' stata valorizzata la chiave '" + AvRuvoConstants.ID_UTENTE_PROTOCOLLATORE + "'");
		}
		UserType ut = new UserType();
		ut.setIdInSistemaEsterno(stringUtenteProtocollazione);

		//Tag allegati
		//newUD = GestioneProtocolloUtility.impostazione_tag_allegati(newUD, documentoProtocollo, pratica, allegati, praticaEvento);
		reqOutput = GestioneProtocolloUtility.impostazione_tag_allegati(newUD, documentoProtocollo, pratica, allegati, praticaEvento);
		newUD = reqOutput.getNewud();
	
		
		//Tag <TipoProvenienza>
		newUD.setTipoProvenienza("E");

		//Tag <DatiEntrata>
		NewUD.DatiEntrata d = new NewUD.DatiEntrata();		
		String dateTimeLocalZone = myUtility.getLocalTimeZone();
		System.out.println("TIMEZONE:" + dateTimeLocalZone);
		//d.setDataOraArrivo(myUtility.getXMLGregorianCalendar());
		d.setDataOraArrivo(dateTimeLocalZone);

		List<SoggettoEsternoType> soggetti = new ArrayList<SoggettoEsternoType>();
		for(SoggettoProtocollo soggetto : documentoProtocollo.getSoggetti())
		{
			SoggettoEsternoType soggettoEsterno = new SoggettoEsternoType();
			String fisica = soggetto.getTipoPersona().equals("F") ? "1" : "0";
			soggettoEsterno.setFlagFisica(fisica);
			if(fisica.equals("0")) {
				soggettoEsterno.setDenominazioneCognome(soggetto.getDenominazione());
				soggettoEsterno.setPartitaIva(soggetto.getPartitaIva());
			} else {
				soggettoEsterno.setDenominazioneCognome(soggetto.getCognome());
				soggettoEsterno.setNome(soggetto.getNome());;				
				soggettoEsterno.setCodiceFiscale(soggetto.getCodiceFiscale());

			}
			soggetti.add(soggettoEsterno);		
		} 
		d.setMittenteEsterno(soggetti);
		newUD.setDatiEntrata(d);

		//Tag <AssegnazioneInterna>
		List<AssegnazioneInternaType> assInterna = new ArrayList<AssegnazioneInternaType>();
		AssegnazioneInternaType assType = new AssegnazioneInternaType();

		assType.setFlagPerConoscenza("0");
		UOType uotype = new UOType();

		List<LivelloGerarchiaType> lv_gerarchia = new ArrayList<LivelloGerarchiaType>();		
		LivelloGerarchiaType livelloUO = new LivelloGerarchiaType();

		livelloUO.setNro(1);
		livelloUO.setCodice("4");
		lv_gerarchia.add(livelloUO);

		LivelloGerarchiaType livelloUO2 = new LivelloGerarchiaType();			
		livelloUO2.setNro(2);
		livelloUO2.setCodice("2");
		lv_gerarchia.add(livelloUO2);

		uotype.setLivelloUO(lv_gerarchia);

		assType.setUO(uotype);
		assInterna.add(assType);
		newUD.setAssegnazioneInterna(assInterna);

		//Tag Fascicolo
		newUD = GestioneProtocolloUtility.impostazione_numero_fascicolo(newUD, documentoProtocollo);

		String xml = Utils.marshall(newUD);
		
		reqOutput.setNewud(newUD);
		
		reqOutput.setXmlRequest(xml);
		
		return reqOutput;
	}

	private RequestProtocollo getXmlCreazioneUdUscita(DocumentoProtocolloRequest documentoProtocollo, Pratica pratica, List<Allegati> allegati, PraticheEventi praticaEvento) throws Exception {
		RequestProtocollo reqOutput = new RequestProtocollo();
		
		NewUD newUD = new NewUD();
		
		String nome = pratica.getIdentificativoPratica();
		
		newUD.setNomeUD(nome);
		
		List<NewUD.RegistrazioneDaDare> registrazioneDaDare = new ArrayList<NewUD.RegistrazioneDaDare>();

		NewUD.RegistrazioneDaDare newud = new NewUD.RegistrazioneDaDare();
		newud.setCategoriaReg("PG");
		registrazioneDaDare.add(newud);		
		newUD.setRegistrazioneDaDare(registrazioneDaDare);

		newUD.setOggettoUD(pratica.getOggettoPratica());

		//Tag allegati
		reqOutput = GestioneProtocolloUtility.impostazione_tag_allegati(newUD, documentoProtocollo, pratica, allegati, praticaEvento);

		newUD = reqOutput.getNewud();
		
		//Tag <TipoProvenienza>
		newUD.setTipoProvenienza("U");

		//Tag <DatiUscita>
		NewUD.DatiUscita datiuscita = new NewUD.DatiUscita();
		String dateTimeLocalZone = myUtility.getLocalTimeZone();
		System.out.println("TIMEZONE:" + dateTimeLocalZone);
		System.out.println("getXMLGregorianCalendar:" + myUtility.getXMLGregorianCalendar().toXMLFormat());
		List<DestinatarioEsternoType> listDestEsterni = new ArrayList<DestinatarioEsternoType>();
		List<SoggettoProtocollo> soggetti = documentoProtocollo.getSoggetti();
		if(soggetti != null && soggetti.size()>0) {
			for(SoggettoProtocollo soggetto :  soggetti) {
				DestinatarioEsternoType destEsterni = new DestinatarioEsternoType();

				if(soggetto.getCodiceFiscale() != null && !soggetto.getCodiceFiscale().isEmpty()) {destEsterni.setCodiceFiscale(soggetto.getCodiceFiscale());}

				if(soggetto.getTipoPersona().equals("F")) {
					if(soggetto.getCognome() != null && !soggetto.getCognome().isEmpty()) {destEsterni.setDenominazioneCognome(soggetto.getCognome());}
					destEsterni.setFlagFisica("1");
				} else {
					destEsterni.setFlagFisica("0");
					if(soggetto.getDenominazione() != null && !soggetto.getDenominazione().isEmpty()) {destEsterni.setDenominazioneCognome(soggetto.getDenominazione());}
					if(soggetto.getPartitaIva() != null && !soggetto.getPartitaIva().isEmpty()) {destEsterni.setPartitaIva(soggetto.getPartitaIva());}
				}
				if(soggetto.getNome() != null && !soggetto.getNome().isEmpty()) {destEsterni.setNome(soggetto.getNome());}

				if(soggetto.getComuneNascita()!= null && !soggetto.getComuneNascita().isEmpty()) {
					ComuneItalianoType comuneItaliano = new ComuneItalianoType();
					comuneItaliano.setNomeComune(soggetto.getComuneNascita());
					destEsterni.setComuneNascita(comuneItaliano);
				}
				if(soggetto.getMail()!= null && !soggetto.getMail().isEmpty()) {
					EmailType et = new EmailType();
					et.setIndirizzoEmail(soggetto.getMail());
					destEsterni.setIndirizzoEmailDest(et);
				}
				if(soggetto.getMailPec()!= null && !soggetto.getMailPec().isEmpty()) {
					EmailType et = new EmailType();
					et.setFlagPEC("1");
					et.setIndirizzoEmail(soggetto.getMailPec());
					destEsterni.setIndirizzoEmailDest(et);
				}
				listDestEsterni.add(destEsterni);
			}
		}

		datiuscita.setDestinatarioEsterno(listDestEsterni);
		newUD.setDatiUscita(datiuscita);

		//Tag <AssegnazioneInterna>
		List<AssegnazioneInternaType> assInterna = new ArrayList<AssegnazioneInternaType>();
		AssegnazioneInternaType assType = new AssegnazioneInternaType();

		assType.setFlagPerConoscenza("0");
		UOType uotype = new UOType();

		List<LivelloGerarchiaType> lv_gerarchia = new ArrayList<LivelloGerarchiaType>();		
		LivelloGerarchiaType livelloUO = new LivelloGerarchiaType();

		//documentoProtocollo.getAoo();
		livelloUO.setNro(1);
		livelloUO.setCodice("4");
		lv_gerarchia.add(livelloUO);

		LivelloGerarchiaType livelloUO2 = new LivelloGerarchiaType();			
		livelloUO2.setNro(2);
		livelloUO2.setCodice("2");
		lv_gerarchia.add(livelloUO2);

		uotype.setLivelloUO(lv_gerarchia);

		assType.setUO(uotype);
		assInterna.add(assType);
		newUD.setAssegnazioneInterna(assInterna);

		//Tag fascicolo
		newUD = GestioneProtocolloUtility.impostazione_numero_fascicolo(newUD, documentoProtocollo);

		String xml = Utils.marshall(newUD);

		reqOutput.setNewud(newUD);
		
		reqOutput.setXmlRequest(xml);
		
		return reqOutput;
	}


	public int getIdComuneRuvo()
	{
		String idComune = utl.getValueProperties("IdComune.di.Ruvo");
		return Integer.valueOf(idComune);
	}
	public int getIdEnteRuvo()
	{
		String idEnte = utl.getValueProperties("IdEnte.SUE.Comune.di.Ruvo.di.Puglia");
		return Integer.valueOf(idEnte);
	}

	@Override
	public DocumentoProtocolloRequest getDocumentoProtocolloDaDatabase(Pratica pratica, PraticheEventi praticaEvento,
			List<Allegato> allegatiNuovi, List<SoggettoProtocollo> soggettiProtocollo, String verso, String oggetto)
					throws Exception {
		return null;
	}

	@Override
	public DocumentoProtocolloResponse protocolla(DocumentoProtocolloRequest documentoProtocollo) throws Exception {
		return null;
	}

	@Override
	public Protocollo getProtocolloBean(PraticheEventi praticaEvento) throws Exception {
		return null;
	}

	@Override
	public DocumentoProtocolloResponse findByProtocollo(Integer annoRiferimento, String numeroProtocollo, Enti ente,
			LkComuni comune) throws Exception {
		return null;
	}

	@Override
	public List<DocumentoProtocolloResponse> queryProtocollo(FilterProtocollo filterProtocollo, Enti ente,
			LkComuni comune) throws Exception {
		return null;
	}

	@Override
	public DocumentoProtocolloRequest getDocumentoProtocolloDaXml(it.wego.cross.xml.Pratica praticaCross, String verso)
			throws Exception {
		return null;
	}

	@Override
	public String getTipologiaDocumentoPerPratica(Integer idEnte) {
		return null;
	}

	@Override
	public String getTipologiaDocumentoArrivo(Integer idEnte) {
		return null;
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
	
	public String getNomeFileZippato(Integer idPratica, Integer idPraticaEvento) {
		String filesZippati = "/tmp/protocolloRuvo";
		
		filesZippati = filesZippati + "_" + idPratica.intValue() +  "_" + idPraticaEvento.intValue() + "_" + myUtility.randInt() + ".zip";
		
		return filesZippati;
	}
	public String getAllegatiProtocollatiTimbratiRuvo(String numProtocollo, String filesZippati) {
		ArrayList<String> fileDaZippare = new  ArrayList<String>();
				
		System.out.println("getAllegatiProtocollatiTimbratiRuvo - numero protocollo: " + numProtocollo);

		List<AllegatiProtocollati> lstAllegatiProtocollatiRuvo = new ArrayList<AllegatiProtocollati>();
		
		try {
				
			lstAllegatiProtocollatiRuvo = this.getAllegatiProtocollatiRuvo(numProtocollo);

			for(AllegatiProtocollati allegati: lstAllegatiProtocollatiRuvo) {
				System.out.println("getAllegatiProtocollatiTimbratiRuvo:" + allegati.getNomeFileAllegato());
	
//				String sfileBase64 = this.getFileProtocollatoTimbratoRuvo(numProtocollo, allegati.getNomeFileAllegato(), allegati.getIdAllegato());								
				String sfileBase64 = this.getFileProtocollatoTimbratoRuvo(numProtocollo, allegati.getNomeFileAllegato());								
				
				byte[] decodedString = Base64.getDecoder().decode(new String(sfileBase64).getBytes("UTF-8"));
				   
				String nomeFileAllegatoDaZippare = allegati.getNomeFileAllegato();
				
				// nomeFileAllegatoDaZippare = getWithoutExtension(nomeFileAllegatoDaZippare);
								
				for(int i=0; i<3; i++) {nomeFileAllegatoDaZippare = myUtility.removeExtension(nomeFileAllegatoDaZippare);}

				nomeFileAllegatoDaZippare = nomeFileAllegatoDaZippare + ".pdf";
						
				myUtility.writeFileInBytes(decodedString, nomeFileAllegatoDaZippare);
				
				fileDaZippare.add(nomeFileAllegatoDaZippare);			
			}
		
			myUtility.zipFiles(filesZippati, fileDaZippare);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Exit getAllegatiProtocollatiTimbratiRuvo - numero protocollo: " + filesZippati);

		return filesZippati;
	}
	public List<AllegatiProtocollati> getAllegatiProtocollatiRuvo(String numProtocollo) {

		ArrayList<AllegatiProtocollati> listAllegatiprotocollati = new ArrayList<AllegatiProtocollati>();

		String fileBase64 = "";

		myUtility utl = new myUtility("protocollo_ruvo.properties", "protocollo_ruvo.log4j.properties");

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
		String passwordUnica = utl.getValueProperties("passwordUnica");
		String sIdEnte = utl.getValueProperties("enteIdEnte.SUE.Comune.di.Ruvo.di.Puglia");

		//Estrazione multi allegati
		String endpointExtractMulti = utl.getValueProperties("urlExtractAllegati");

		it.eng.auriga.repository2.webservices.extractmulti.WSExtractMultiServiceLocator
		wsextractMultiService = new it.eng.auriga.repository2.webservices.extractmulti.WSExtractMultiServiceLocator();
		wsextractMultiService.setWSExtractMultiPortEndpointAddress(endpointExtractMulti);

		it.eng.auriga.repository2.webservices.extractmulti.WSIExtractMulti wsExtractMulti;
		try {
			wsExtractMulti = wsextractMultiService.getWSExtractMultiPort();

			String xmlRichiestaExtractMulti = GestioneProtocolloUtility.getXmlExtractMulti(Integer.parseInt(idUdProtocollo),anno);

			String xmlHashExtractMulti = RuvoUtils.hashXML(xmlRichiestaExtractMulti, "");

			it.eng.auriga.repository2.webservices.extractmulti.Service serviceExtractMulti = 
					new it.eng.auriga.repository2.webservices.extractmulti.Service();  

			serviceExtractMulti.setCodApplicazione(codiceApplicazione);
			serviceExtractMulti.setIstanzaApplicazione(codiceIstanza);
			serviceExtractMulti.setUserName(username);
			//serviceExtractMulti.setPassword("#RESERVED_WS_DEM");
			serviceExtractMulti.setPassword(password);

			serviceExtractMulti.setXml(xmlRichiestaExtractMulti);
			serviceExtractMulti.setHash(xmlHashExtractMulti);

			System.out.println("REQUEST WS ExtractMulti: " + xmlRichiestaExtractMulti);

			it.eng.auriga.repository2.webservices.extractmulti.ServiceResponse responseExtractMulti =  
					wsExtractMulti.serviceOperation(serviceExtractMulti);

			String risultatoExtractMulti = responseExtractMulti.getServiceReturn();

			risultatoExtractMulti = RuvoUtils.decodeXml(risultatoExtractMulti);

			System.out.println("RESPONSE WS ExtractMulti: " + risultatoExtractMulti);

			log.info("Estrazione allegati: \n" + risultatoExtractMulti);
			GestioneProtocolloUtility.checkResult(risultatoExtractMulti);

			Object[] allegatiResponse = ((Stub) wsExtractMulti).getAttachments();
			log.info("UnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);

			if (allegatiResponse.length == 0) {
				log.info("UnitaDocumentaria:Numero di allegati: " + allegatiResponse.length);
				throw new Exception("Non è stato creata nessuna unita documentale");
			} else {

				listAllegatiprotocollati = GestioneProtocolloUtility.estraiTuttiGliAllegati(allegatiResponse);

				for(AllegatiProtocollati allegati: listAllegatiprotocollati) {
					System.out.println(" Risultato getAllegatiProtocollatiRuvo:" + allegati.getNomeFileAllegato());
				}

			}
		} catch (Exception e1) {			
			e1.printStackTrace();
		}
		return listAllegatiprotocollati;   
	}

	public List<AllegatiProtocollati> getAllegatiProtocollatiBari(String numProtocollo) {

		ArrayList<AllegatiProtocollati> listAllegatiprotocollati = new ArrayList<AllegatiProtocollati>();

		String fileBase64 = "";

		myUtility utl = new myUtility("protocollo_ruvo.properties", "protocollo_ruvo.log4j.properties");;

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
		//		String passwordUnica = utl.getValueProperties("passwordUnica");

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
			//System.out.println("xmlRichiestaExtractMulti: " + xmltemp);
			String xmlHashExtractMulti = RuvoUtils.hashXML(xmlRichiestaExtractMulti, "");

			it.eng.auriga.repository2.webservices.extractmulti.Service serviceExtractMulti = 
					new it.eng.auriga.repository2.webservices.extractmulti.Service();  

			serviceExtractMulti.setCodApplicazione(codiceApplicazione);
			serviceExtractMulti.setIstanzaApplicazione(codiceIstanza);
			serviceExtractMulti.setUserName(username);
			//serviceExtractMulti.setPassword("#RESERVED_WS_DEM");
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

	private GetIdMetadati getMetadataAllegatiRuvo(int anno, int numProtocollo, String nomeFile) throws Exception {		
		GetIdMetadati responseMetadati = new GetIdMetadati();

		String codiceApplicazione = utl.getValueProperties("cod.applicazione");
		String codiceIstanza = utl.getValueProperties("istanza.applicazione");
		String username = utl.getValueProperties("username");
		String password = utl.getValueProperties("password");

		//Recupero informazioni dal protocollo
		String UrlGetUd = utl.getValueProperties("urlGetUd");
		String endpointGetUd = UrlGetUd;

		String IdUD = "";

		String xmlRichiesta = GestioneProtocolloUtility.getXmlGetMetadata_allegati(anno, numProtocollo);
		//String xmlRichiesta = getXmlGetMetadata_Ud(IdUD);
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

		log.info("Protocollo Ruvo:creazioneUnitaDocumentaria:Risposta del documentale: \n" + risultato);
		GestioneProtocolloUtility.checkResult(risultato);

		Object[] allegatiResponse = ((Stub) wsGetMetadataUd).getAttachments();
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

				log.info("Protocollo Ruvo: esitoGetMetaDati: esitoXml GetMetaDati" + esitoXml);
				
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true); 
				DocumentBuilder builder = factory.newDocumentBuilder();
				InputSource source = new InputSource(new StringReader(esitoXml));
				org.w3c.dom.Document doc = builder.parse(source);

				//Create XPath
				XPathFactory xpathfactory = XPathFactory.newInstance();
				XPath xpath = xpathfactory.newXPath();

				String sIdDocPrincipale = "";
				String sNumReg = "";
				String sDataOraReg = "";
				String sSiglaReg = "";
				String sIdUO = "";
				String sAnnoReg = "";
				String sNomeFileAllegatoPrincipale = "";
				String sNomeFileOriginaleAllegatoPrincipale = "";
				String sNomeFile = "";
				String sNomeFileOriginale = "";
				{ // Valorizzazione risposta protocollazione Ruvo
					XPathExpression expr = xpath.compile("//DatiUD/IdUD/text()");
					Object result = expr.evaluate(doc, XPathConstants.NODESET);
					NodeList nodes = (NodeList) result;

					for (int i = 0; i < nodes.getLength(); i++) {
						IdUD = nodes.item(i).getNodeValue();
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

					expr = xpath.compile("//DatiUD/IdDocPrimario/text()");
					result = expr.evaluate(doc, XPathConstants.NODESET);
					nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						sIdDocPrincipale = nodes.item(i).getNodeValue();
						System.out.println(nodes.item(i).getNodeValue());
					}		
					expr = xpath.compile("//DatiUD/VersioneElettronica/NomeFile/text()");
					result = expr.evaluate(doc, XPathConstants.NODESET);
					nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						sNomeFileAllegatoPrincipale = nodes.item(i).getNodeValue();
						System.out.println(nodes.item(i).getNodeValue());
					}		

					expr = xpath.compile("//DatiUD/VersioneElettronica/NomeFileOriginale/text()");
					result = expr.evaluate(doc, XPathConstants.NODESET);
					nodes = (NodeList) result;
					for (int i = 0; i < nodes.getLength(); i++) {
						sNomeFileOriginaleAllegatoPrincipale = nodes.item(i).getNodeValue();
						System.out.println(nodes.item(i).getNodeValue());
					}		

					System.out.println("------------------------ Dati AllegatoPrincipale ----------------------------");
					System.out.println(" IdUD:" + IdUD);
					System.out.println(" IdDocPrincipale:" + sIdDocPrincipale);
					System.out.println(" NomeFilePrincipale:" + sNomeFileAllegatoPrincipale);
					System.out.println(" NomeFileOriginalePrincipale:" + sNomeFileOriginaleAllegatoPrincipale);	
					System.out.println("---------------------------------------------------------------------");

					NodeList nodeList = (NodeList)xpath.compile("//DatiUD/AllegatoUD").evaluate(doc, XPathConstants.NODESET);
					String[] results = new String[nodeList.getLength()];
					
					ArrayList<MetadatiAllegati> allegati = new ArrayList<MetadatiAllegati>();
					MetadatiAllegati mtPrincipale = new MetadatiAllegati();
					mtPrincipale.setIdDoc(Integer.valueOf(sIdDocPrincipale));
					mtPrincipale.setNomeFile(sNomeFileAllegatoPrincipale);
					mtPrincipale.setNomeFileOriginale(sNomeFileOriginaleAllegatoPrincipale);
					mtPrincipale.setPrincipale(true);
					allegati.add(mtPrincipale);
					
					for (int index = 0; index < nodeList.getLength(); index++) {
						Node AllegatoUD = nodeList.item(index);
												
						String strIdDoc = (String) xpath.evaluate("IdDoc", AllegatoUD, XPathConstants.STRING);
						String strNomeFile = (String) xpath.evaluate("VersioneElettronica/NomeFile", AllegatoUD, XPathConstants.STRING);
						String strNomeFileOriginale = (String) xpath.evaluate("VersioneElettronica/NomeFileOriginale", AllegatoUD, XPathConstants.STRING);
						System.out.println("------------------------ Dati AllegatoUD ----------------------------");
						System.out.println(" IdDocAllegato:" + strIdDoc);
						System.out.println(" NomeFileAllegato:" + strNomeFile);
						System.out.println(" NomeFileOriginaleAllegato:" + strNomeFileOriginale);	
						System.out.println("---------------------------------------------------------------------");
						
						MetadatiAllegati mt = new MetadatiAllegati();
						mt.setIdDoc(Integer.valueOf(strIdDoc));
						mt.setNomeFile(strNomeFile);
						mt.setNomeFileOriginale(strNomeFileOriginale);
						mt.setPrincipale(false);
						allegati.add(mt);
					}
					responseMetadati.setIdUd(Integer.valueOf(IdUD));
					responseMetadati.setListMetadatiAllegati(allegati); 
															
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

				/**
				responseMetadati.setIdUd(Integer.parseInt(IdUD));				
				responseMetadati.setIdDoc(Integer.parseInt(sIdDocPrimario));
				responseMetadati.setNomeFile(sNomeFile);
				responseMetadati.setNomeFileOriginale(sNomeFileOriginale);
				if(nomeFile != null && nomeFile.length()>0) { 
					if (!nomeFile.equals(sNomeFile) && !nomeFile.equals(sNomeFileOriginale) ) {					
						responseMetadati.setIdDoc(0);
						responseMetadati.setNomeFile("");
						responseMetadati.setNomeFileOriginale("");
					}
				} 
				**/
				log.info(sResponse);

			}
		}

		return responseMetadati;
	}
	public String getFileProtocollatoTimbratoRuvo(String protocollo, String nomeFile) {
		Log.APP.info("getFileProtocollatoTimbratoRuvo numero protocollo: " + protocollo + " nomeFile da recuperare timbrato:" + nomeFile);
		String fileTimbrato = "", sIClassificazione = "", sIAnno = "", sIProtocollo = "";

		GetIdMetadati metadati = new GetIdMetadati();
		
		int anno = 0, numRegistro = 0, numProtocollo;

		String[] splitProtocollo = protocollo.split("/");

		String classificazione = splitProtocollo[0]; 

		String sAnno = splitProtocollo[1];

		String sProtocollo = splitProtocollo[2];

		anno = Integer.parseInt(sAnno);

		numProtocollo = Integer.parseInt(sProtocollo); //Numero registro

		try {
			
			metadati = getMetadataAllegatiRuvo(anno, numProtocollo, nomeFile);

			int IdDoc = 0;
			int idx=0;
			Log.APP.info("getFileProtocollatoTimbratoRuvo numero protocollo Controlla allegati");
			for(MetadatiAllegati mt : metadati.getListMetadatiAllegati()) {
				idx++;
				Log.APP.info("getFileProtocollatoTimbratoRuvo allegato(" + idx + ") nomefile:" + mt.getNomeFile());
				Log.APP.info("getFileProtocollatoTimbratoRuvo allegato(" + idx + ") idoc:" + mt.getIdDoc());
				if(mt.getNomeFile().equals(nomeFile)) {
					Log.APP.info("getFileProtocollatoTimbratoRuvo allegato(" + idx + ") idotrovatoc:" + mt.getIdDoc());
					IdDoc = mt.getIdDoc();
				}
			}
			
			fileTimbrato = GestioneProtocolloUtility.getFileTimbrato2(metadati.getIdUd(), IdDoc);
			//fileTimbrato = GestioneProtocolloUtility.getFileTimbrato2(metadati.getIdUd(), metadati.getIdDoc());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileTimbrato;
	}

	public byte[] getFileZippato(File file) {
		return myUtility.readFile(file);
	}	
		
	public byte[] recuperaZipfileTimbrati(String nomefileZippato) {
		Path path = Paths.get(nomefileZippato);
		long bytes = 0;
		try {
			bytes = Files.size(path);
			if(bytes>0) {
				File file = new File(nomefileZippato);
				return myUtility.readFile(file);
			} else return null;
		} catch (IOException e) {
			Log.APP.info("download_from_register_ruvo - preparazione allegati in corso. File zippato non ancora disponibile" + nomefileZippato);  
		}
		return null;		
	}
	public static void main(String[] args) {
		GestioneProtocolloRuvo p = new GestioneProtocolloRuvo();
		String nomeFileZippato = "/tmp/protocolloRuvo_209653_810983_764671387.zip";
		byte[] stampedDocument = p.recuperaZipfileTimbrati(nomeFileZippato);
		Log.APP.info("download_from_register_ruvo - AFTER REFRESH PAGE dim file:" + 
				(stampedDocument != null ? stampedDocument.length : 0));
		
		if(stampedDocument != null) {
			System.out.println("download_from_register_ruvo - AFTER REFRESH PAGE dim file:" + 
					(stampedDocument != null ? stampedDocument.length : 0));
		}
		
	}

}
