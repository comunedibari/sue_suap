package it.wego.cross.webservices.cxf.infocamere;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import it.gov.impresainungiorno.schema.base.Indirizzo;
import it.gov.impresainungiorno.schema.base.Stato;
import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte.Intestazione;
import it.gov.impresainungiorno.schema.suap.ente.OggettoCooperazione;
import it.gov.impresainungiorno.schema.suap.pratica.AdempimentoSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaImpresa;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiDichiarante;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiEnte;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gov.impresainungiorno.schema.suap.pratica.OggettoComunicazione;
import it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.gov.impresainungiorno.schema.suap.ri.spc.IscrizioneImpresaRiSpcResponse;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.cross_tomcat_services.PaperworkNumberManager;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dao.TemplateDao;
import it.wego.cross.dto.AllegatoRicezioneDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.registroimprese.DatiIdentificativiRIDTO;
import it.wego.cross.dto.registroimprese.ErroreRIDTO;
import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkAssociazioneProcedimentoEnteUtenteSuap;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkTipoEndoProcedimentoSuap;
import it.wego.cross.entity.LkTipoInterventoSuap;
import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.LkTipoProcedimentoSuap;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.serializer.ProcedimentiSerializer;
import it.wego.cross.serializer.RegistroImpreseSerializer;
import it.wego.cross.service.ComuniService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.RegistroImpreseService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.ComponentUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Allegato;
import it.wego.cross.xml.Anagrafica;
import it.wego.cross.xml.Anagrafiche;
import it.wego.cross.xml.Immobile;
import it.wego.cross.xml.IndirizziIntervento;
import it.wego.cross.xml.IndirizzoIntervento;
import it.wego.cross.xml.Pratica;
import it.wego.cross.xml.Procedimenti;
import it.wego.cross.xml.Procedimento;
import it.wego.cross.xml.Recapiti;
import it.wego.cross.xml.Recapito;

/**
 *
 * @author leler
 */

@WebService(serviceName = "PddServiceEnte", portName = "PddPortEnte", endpointInterface = "it.gov.impresainungiorno.suap.scrivania.PddServiceEnte", targetNamespace = "http://www.impresainungiorno.gov.it/suap/scrivania", wsdlLocation = "wsdl/infocamere/PddServiceEnte.wsdl")
public class PddServiceEnteWS {

	@Autowired
	private WorkFlowService workflowService;
	@Autowired
	private EntiService entiService;
	@Autowired
	private LookupDao lookupDao;
	@Autowired
	private ProcedimentiDao procedimentiDao;
	@Autowired
	private ProcessiDao processiDao;
	@Autowired
	private PluginService pluginService;
	@Autowired
	private TemplateDao templateDao;
	@Autowired
	private PraticheAction praticheAction;
	@Autowired
	private ProcedimentiService procedimentiService;
	@Autowired
	private ErroriAction erroriAction;
	@Autowired
	private PraticheService praticheService;
	@Autowired
    private ConfigurationService configurationService;
	@Autowired
    protected RegistroImpreseSerializer registroImpreseSerializer;
	@Autowired
    private ComuniService comuniService;
	@Autowired
    private RegistroImpreseService registroImpreseService;
	@Autowired
    private UtentiService utentiService;
	@Autowired
	private ComponentUtils componentUtils;

	// @XmlAttachmentRef
	public java.lang.String inviaSUAPEnte(it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte cooperazioneSUAPEnte) throws Exception {

		Intestazione intestazione = cooperazioneSUAPEnte.getIntestazione();
		String identificativoPratica = intestazione.getCodicePratica();
		List<AllegatoCooperazione> listAllegatoReq = cooperazioneSUAPEnte.getAllegato();
		String esito = "";
		it.wego.cross.xml.Pratica praticaCross = new Pratica();
		List<String> codInterventoList = new ArrayList<>();
		//Outuput Pratica with return; List<String> with output parameter
		praticaCross = creazionePraticaCross(cooperazioneSUAPEnte, intestazione, identificativoPratica,listAllegatoReq, codInterventoList);

		it.wego.cross.entity.Pratica praticaEsistente = praticheService.getPraticaByIdentificativo(identificativoPratica);
		if (praticaEsistente != null) {
			inserimentoEventoCooperazione(praticaEsistente, praticaCross,cooperazioneSUAPEnte);
			esito = "OK: L'integrazione è stata creata correttamente.";
		} else {
			inserimentoPratica(cooperazioneSUAPEnte, intestazione, identificativoPratica, praticaCross, codInterventoList);
			esito = "OK";
		}
		if(!esito.contains("OK")) {
			esito = "KO";
		}

		return esito;

	}

	private Pratica creazionePraticaCross(CooperazioneSUAPEnte cooperazioneSUAPEnte, Intestazione intestazione,
			String identificativoPratica, List<AllegatoCooperazione> listAllegatoReq, List<String> codInterventoList) throws Exception {
		Log.WS.info("Inizio creazione pratica Cross!");
		EstremiSuap suapCompetente = intestazione.getSuapCompetente();

		Enti enteSuap = entiService.findByIdentificativoSuap(String.valueOf(suapCompetente.getIdentificativoSuap()));

		AnagraficaImpresa impresa = intestazione.getImpresa();
		OggettoComunicazione oggettoPratica = intestazione.getOggettoPratica();
		OggettoCooperazione oggettoComunicazione = intestazione.getOggettoComunicazione();
		ProtocolloSUAP protocolloPraticaSuap = intestazione.getProtocolloPraticaSuap();
		String testoComunicazione = intestazione.getTestoComunicazione();
		it.wego.cross.xml.Pratica praticaCross = new Pratica();
		praticaCross.setIdentificativoPratica(identificativoPratica);
		praticaCross.setDataRicezione(Utils.dateToXmlGregorianCalendar(new Date()));
		String tipoInterventoSuap ="";
		if(oggettoPratica.getTipoIntervento()!=null) {
			tipoInterventoSuap = oggettoPratica.getTipoIntervento().value();	
		}
		praticaCross.setDesProcedimentoSuap(oggettoPratica.getTipoProcedimento()+"|"+tipoInterventoSuap);
		Log.WS.info("Inizio Gestione Allegati!");
		/*inizio gestione allegati*/
		List<Configuration> attachmentsFolderConfig = configurationService
				.findByName(SessionConstants.ATTACHMENTS_FOLDER);
		Configuration config = attachmentsFolderConfig.get(0);
		String attachmentsFolder = config.getValue();
		attachmentsFolder = componentUtils.generateFilePath(attachmentsFolder, enteSuap.getLkComuniList().get(0));
		it.wego.cross.xml.Allegati allegatiXml = new it.wego.cross.xml.Allegati();
		List<Allegato> list = allegatiXml.getAllegato();
		EstremiDichiarante ed = null;
		RiepilogoPraticaSUAP riepilogoPraticaSUAP = null;
		IndirizziIntervento indirizziIntervento = null;
		for(AllegatoCooperazione allegatoReq:listAllegatoReq) {
			Allegato allegato = new Allegato();
			allegato.setDescrizione(allegatoReq.getDescrizione());
			//allegato.setNomeFile(allegatoReq.getNomeFileOriginale());
			
			String nomeFile = (allegatoReq.getNomeFileOriginale()==null) ?  allegatoReq.getNomeFile() : allegatoReq.getNomeFileOriginale() ;
            allegato.setNomeFile(nomeFile);
			allegato.setTipoFile(allegatoReq.getMime());
			String directory = attachmentsFolder+ File.separator +identificativoPratica;
			DataHandler dataHandler = allegatoReq.getEmbeddedFileRef();
			File f =  Utils.getFilefromDataHandler(directory, dataHandler,identificativoPratica,nomeFile);
			String pathFile = f.getPath();
			 if(allegatoReq.getDescrizione().equals("Riepilogo Pratica SUAP")) {
				 allegato.setRiepilogoPratica("S");
			 }
			allegato.setPathFile(pathFile);
			list.add(allegato);
			/*se il file allegato e' SUAP.xml si possono estrarre i dati del dichiarante 
			 * e poi compilare l'anagrafica del richiedente e
			 *  prendere i dati dell'intervento per gestire il procedimento*/
			
			
			String nomeFileXML = nomeFile.substring(nomeFile.indexOf("."));
			
			
			Indirizzo indirizzoImpianto = null;
			if(nomeFileXML.equals(".SUAP.XML")) {
				try {
					riepilogoPraticaSUAP = getRiepilogoPraticaSUAP(f);
					ed = getEstremiDichiaranteFromFileXml(riepilogoPraticaSUAP);
					//listaDatiCatastali = riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getDatiCatastali();
					if(riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo()!=null) {
						Log.WS.info("Trovato impianto produttivo");
						indirizzoImpianto = riepilogoPraticaSUAP.getIntestazione().getImpiantoProduttivo().getIndirizzo();
						indirizziIntervento = gestioneIndirizziIntervento(indirizzoImpianto);
						Log.WS.info("Aggiunto impianto produttivo");
					}
				} catch (Exception e) {
					System.out.println("Eccezione nel file .SUAP.XML in  creazionePraticaCross: " +e);
				}

			}
			if(nomeFileXML.contains(".001.MDA.XML")) {
				
				Log.WS.info("chiamata gestione File MDA");
				try {
					praticaCross = gestioneFileMDA(f,praticaCross);
				} catch (Exception e) {
					System.out.println("Eccezione nel file .001.MDA.XML in  creazionePraticaCross: " +e);
				}
				
				Log.WS.info("Fine chiamta gestione File MDA");
			}
			
		}
		if (indirizziIntervento!=null) {
			Log.WS.info("set indirizzo intervento");
			praticaCross.setIndirizziIntervento(indirizziIntervento);	
		}
		
		/*fine gestione allegati*/
//		allegatiXml.setAllegato(list);
		praticaCross.setAllegati(allegatiXml);
		Log.WS.info("Fine Gestione Allegati!");
		Log.WS.info("Inizio Gestione Endoprocedimenti!");
		/*inizio gestione endoprocedimenti*/
		String codProcedimento = "";
		Procedimenti procedimentoXml = null;
		String codiceIntervento = "";
		List<String> listaInterventi = new ArrayList<String>();
		if(riepilogoPraticaSUAP!=null) {
			if(riepilogoPraticaSUAP.getStruttura()!=null) {
				if(!riepilogoPraticaSUAP.getStruttura().getModulo().isEmpty()) {
					List<AdempimentoSUAP> listAdempimenti = riepilogoPraticaSUAP.getStruttura().getModulo();
					
					for(AdempimentoSUAP adempimentoSUAP: listAdempimenti) {
						codiceIntervento = adempimentoSUAP.getCod();
						List<EstremiEnte> enti = adempimentoSUAP.getEnteCoinvolto();
						EstremiEnte estremiEnti = enti.get(0);
						estremiEnti.getCodiceAmministrazione();
						listaInterventi.add(codiceIntervento);
					}
				}
			}
		}
		LkTipoEndoProcedimentoSuap lkTipoEndoProcedimentoSuap = null;
		List<it.wego.cross.entity.Procedimenti> listProcedimenti = null;
		List<Procedimento> procedimentiList = new ArrayList<Procedimento>();
		for(String codIntervento : listaInterventi) {
			//codProcedimento = InterventoProcedimentoDTO.INTERV_PROCEDIMENTI.get(codIntervento);
			lkTipoEndoProcedimentoSuap = lookupDao.findTipoEndoProcedimentoSuapByCod(codIntervento);
			if(lkTipoEndoProcedimentoSuap!=null) {
				codProcedimento = lkTipoEndoProcedimentoSuap.getCodProcedimento();
				if(codProcedimento!=null) {
					it.wego.cross.entity.Procedimenti procedimento = procedimentiService.getProcedimento(codProcedimento);
					if(procedimento!=null) {
						ProcedimentiEnti pe = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(enteSuap.getIdEnte(), procedimento.getIdProc());
						if(pe!=null) {
							procedimentoXml = new Procedimenti();
							
							Procedimento proc = new Procedimento();
							proc = ProcedimentiSerializer.serializeXML(pe);
							proc.setIdEnteDestinatario(BigInteger.valueOf(enteSuap.getIdEnte()));
							proc.setCodEnteDestinatario(enteSuap.getCodEnte());
							proc.setCodProcedimento(procedimento.getCodProc());
							proc.setIdProcedimento(procedimento.getIdProc());
							codInterventoList.add(codIntervento);
							procedimentiList.add(proc); 
						}
					}
				}
			}
		}
		
		if(procedimentiList.size()>0)		
			procedimentoXml.setProcedimento(procedimentiList );
		praticaCross.setProcedimenti(procedimentoXml);
		Log.WS.info("Numero endoprocedimenti : " +procedimentiList.size());
		/*fine gestione endoprocedimenti*/
		Log.WS.info("Fine Gestione Endoprocedimenti!");
		Log.WS.info("Inizio Gestione Anagrafiche!");
		List<Anagrafiche> anagraficheList = new ArrayList<Anagrafiche>();
		Anagrafiche anagrafica = new Anagrafiche();
		anagrafica = recuperoAnagrafica(impresa);
		anagraficheList.add(anagrafica);
		anagrafica = new Anagrafiche();
		anagrafica = recuperaAnagraficaDichiarante(ed);
		anagraficheList.add(anagrafica);
		praticaCross.setAnagrafiche(anagraficheList);
		Log.WS.info("Fine Gestione Anagrafiche!");
		Log.WS.info("Fine creazione pratica Cross!");
		return praticaCross;
	}

	private void inserimentoEventoCooperazione(it.wego.cross.entity.Pratica praticaEsistente, it.wego.cross.xml.Pratica praticaCross, CooperazioneSUAPEnte cooperazioneSUAPEnte) throws Exception {
		Log.WS.info("Inserisco evento Copperazione");
		List<String> destinatari = new ArrayList<String>();
		Log.WS.info("Trovati " + destinatari.size() + " destinatari");
		Log.WS.info("Inserisco evento di ricezione");
		ProcessiEventi eventoProcesso = processiDao.findProcessiEventiByCodEventoIdProcesso("OINOLTRO2", praticaEsistente.getIdProcesso().getIdProcesso());
		List<AllegatoRicezioneDTO> allegati = new ArrayList<AllegatoRicezioneDTO>(); 
		allegati = getAllegati(praticaCross);
		
		ComunicazioneBean cb = praticheAction.inserisciEventoAggiornamentoSuap(praticaCross, praticaEsistente, eventoProcesso,destinatari, allegati, cooperazioneSUAPEnte);
		PraticheEventi praticaEvento = praticheService.getPraticaEvento(cb.getIdEventoPratica());
		Log.WS.info("Operazione terminata correttamente");

		Log.WS.info("Elaborazione post creazione pratica");
//	        gp.postCreazionePratica(pratica, data);
		Log.WS.info("Elaborazione post creazione pratica terminata");

		praticheService.startCommunicationProcess(praticaEsistente, praticaEvento, cb);

		Log.WS.info("Inserisco evento ricezione");
//	        gp.notifica(pratica, "Descrizione evento: ricezione pratica");
		Log.WS.info("Operazione terminata correttamente");
		
	}

	private List<AllegatoRicezioneDTO> getAllegati(it.wego.cross.xml.Pratica praticaCross) {
		List<AllegatoRicezioneDTO> allegati = new ArrayList<AllegatoRicezioneDTO>();
		List<Allegato> listAllegatiCross = praticaCross.getAllegati().getAllegato();
		for (Allegato temp : listAllegatiCross) {
			AllegatoRicezioneDTO e = AllegatiSerializer.serializeAllegatoRicezione(temp);
			allegati.add(e);
		}
		return allegati;
	}

	private void inserimentoPratica(
			it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte cooperazioneSUAPEnte, 
			Intestazione intestazione, 
			String identificativoPratica, 
			Pratica praticaCross,
			List<String> codInterventoList)
			throws DatatypeConfigurationException, PropertyException, JAXBException, Exception {
		EstremiSuap suapCompetente = intestazione.getSuapCompetente();
		Enti enteSuap = entiService.findByIdentificativoSuap(String.valueOf(suapCompetente.getIdentificativoSuap()));
		
		String desProcedimento = praticaCross.getDesProcedimentoSuap();
		String procedimentoSuap = desProcedimento.substring(0, desProcedimento.lastIndexOf("|"));
		
		/*if(praticaCross.getProcedimenti()!=null) {
			List<Procedimento> listProcedimenti = praticaCross.getProcedimenti().getProcedimento();
			for(Procedimento proc : listProcedimenti) {
				codProcedimento = proc.getCodProcedimento();
			}
		}*/
		//PER ORA METTIAMO come id_ente 1101(ufficio commerciale) POI dovremmo mappare correttamente secondo i procedimenti/processi
		it.wego.cross.entity.Procedimenti procedimento = procedimentiService.getProcedimento(procedimentoSuap);
		ProcedimentiEnti pe = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(enteSuap.getIdEnte(), procedimento.getIdProc());
		
		// Salvo l'xml nell'area di staging
		Log.WS.info("Preparo i dati per salvare l'area di staging");
		Date dataRicezione = new Date();
		String xmlPratica = PraticaUtils.getXmlFromPratica(praticaCross);
		Log.WS.info("Dump XML pratica");
		Log.WS.info(xmlPratica);
		Staging staging = new Staging();
		staging.setIdentificativoProvenienza(null);
		staging.setOggetto(intestazione.getOggettoPratica().getValue());
		Log.WS.info("Data ricezione: " + dataRicezione);
		staging.setDataRicezione(dataRicezione);
		Log.WS.info("Tipo messaggio: " + Constants.WEBSERVICE_CSE);
		staging.setTipoMessaggio(Constants.WEBSERVICE_CSE); // cooperazione suap ente

		String xmlRicevuto = "";
		try {
			/*it.gov.impresainungiorno.schema.suap.ente.ObjectFactory of = new it.gov.impresainungiorno.schema.suap.ente.ObjectFactory();
			JAXBElement<CooperazioneSUAPEnte> root = of.createCooperazioneSuapEnte(cooperazioneSUAPEnte);*/
			xmlRicevuto = Utils.marshall(cooperazioneSUAPEnte, CooperazioneSUAPEnte.class);
			Log.WS.info("XML input "+ xmlRicevuto);
		} catch (PropertyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		staging.setXmlRicevuto(xmlRicevuto.getBytes());
		staging.setXmlPratica(xmlPratica.getBytes());
		staging.setIdEnte(enteSuap);
		praticheAction.salvaStaging(staging);

		// Creo la pratica
		LkComuni comune = enteSuap.getLkComuniList().get(0);
		Log.WS.info("Preparo i dati per il salvataggio della pratica");
		it.wego.cross.entity.Pratica pratica = new it.wego.cross.entity.Pratica();
		Log.WS.info("Identificativo pratica: " + identificativoPratica);
		pratica.setIdentificativoPratica(identificativoPratica);
		Log.WS.info("Protocollo: " + praticaCross.getProtocollo());
		pratica.setProtocollo(praticaCross.getProtocollo());
		Log.WS.info("Oggetto: " + intestazione.getOggettoPratica().getValue());
		pratica.setOggettoPratica(intestazione.getOggettoPratica().getValue());
		
		Log.WS.info("Responsabile del procedimento: " + praticaCross.getResponsabileProcedimento());
		pratica.setResponsabileProcedimento(praticaCross.getResponsabileProcedimento());
		Log.WS.info("Data ricezione: " + dataRicezione);
		pratica.setDataRicezione(dataRicezione);
		Log.WS.info("Id staging: " + staging.getIdStaging());
		pratica.setIdStaging(staging);
		Log.WS.info("Id Comune: " + comune.getIdComune());
		pratica.setIdComune(comune);
//NON SERVE IL PROCESSO LO CAPIAMO DALLE DUE TIPOLOGICHE NUOVE LK_TIPO_PROCEDIMENTO E LK_TIPO_INTERVENTO
		pratica.setIdProcEnte(pe);
		
		Processi processo = getIdProcesso(enteSuap, procedimento);
		pratica.setIdProcesso(processo);
		String interventoSuap = desProcedimento.substring(desProcedimento.lastIndexOf("|")+1, desProcedimento.length());
		LkTipoInterventoSuap lkTipoInterventoSuap = lookupDao.findTipoInterventoSuapByDescrizione(interventoSuap);
		LkTipoProcedimentoSuap lkTipoProcedimentoSuap = lookupDao.findTipoProcedimentoSuapByDescSuap(procedimentoSuap);
		pratica.setIdTipoProcedimentoSuap(lkTipoProcedimentoSuap);
		pratica.setIdTipoInterventoSuap(lkTipoInterventoSuap);
		
		Integer idTipoPratica = lookupDao.findByIdTipoPraticaProcessiByIdCustom(pratica.getIdProcesso().getIdProcesso());
		LkTipoPratica lkTipoPratica = new LkTipoPratica();
		lkTipoPratica.setIdTipoPratica(idTipoPratica);	
		Log.WS.info("Tipo pratica: " + lkTipoPratica);
		pratica.setTipoPratica(lkTipoPratica);
		Integer numeroPratica = -1;
        try {
        	numeroPratica = PaperworkNumberManager.getInstance().getNewNumber(pe.getIdEnte().getIdEnte(), lkTipoPratica.getIdTipoPratica());
        } catch(Exception e) {
        	Log.WS.error("Error generating paperwork progressive number", e);
        }
        Log.WS.info("Numero pratica: " + numeroPratica);
        pratica.setNumeroPratica(numeroPratica);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(pratica.getDataRicezione());
        pratica.setAnno(cal.get(Calendar.YEAR));
        Log.WS.info("Anno: " + pratica.getAnno());
        
		Log.WS.info("Salvo la pratica");
		praticheAction.salvaPratica(pratica);
		Log.WS.info("Popolo i procedimenti");
		//praticheAction.popolaProcedimenti(pratica, praticaCross);
		if(praticaCross.getProcedimenti()!=null) {
			praticheAction.popolaProcedimentiSUAP(pratica, praticaCross, codInterventoList);
		}
		// Dati catastali
		Log.WS.info("Popolo i dati catastali");
		praticheAction.popolaDatiCatastali(pratica, praticaCross);
		//Indirizzi Intervento
		praticheAction.popolaIndirizziInterventoSUAP(pratica,praticaCross);
		
		// Inserisco le anagrafiche, per quanto possibile, collegate alla pratica
		if (praticaCross.getAnagrafiche() != null && !praticaCross.getAnagrafiche().isEmpty()) {
			try {
				praticheAction.insertAnagraficheSUAP(praticaCross.getAnagrafiche(), pratica);
				xmlPratica = PraticaUtils.getXmlFromPratica(praticaCross);
				staging.setXmlPratica(xmlPratica.getBytes());
				praticheAction.updateStaging(staging, pratica);
			} catch (Exception ex) {
				Log.WS.error("Non è stato possibile salvare l'anagrafica", ex);
			}
		}
		
		pratica.setData_prot_suap(intestazione.getProtocolloPraticaSuap().getDataRegistrazione());
		pratica.setProt_suap(intestazione.getProtocolloPraticaSuap().getNumeroRegistrazione());
		// Creo l'evento di ricezione
		Log.WS.info("Inserisco evento ricezione");
		Log.WS.info("Poiché non ho ancora anagrafiche presenti in banca dati, forzo i destinatari alle anagrafiche presenti nell'XML della pratica");
		List<String> destinatari = new ArrayList<String>();
		Log.WS.info("Trovati " + destinatari.size() + " destinatari");
		Log.WS.info("Inserisco evento di ricezione");
		ProcessiEventi eventoProcesso = processiDao.findProcessiEventiByCodEventoIdProcesso(AnaTipiEvento.RICEZIONE_PRATICA, processo.getIdProcesso());
		
		List<AllegatoRicezioneDTO> allegati = new ArrayList<AllegatoRicezioneDTO>(); 
		allegati = getAllegati(praticaCross);
		
		//TODO:IMPLEMENTARE ASSEGNAZIONE COME PREVISTO DA SPECIFICHE ANCORA DA CAPIRE
		Log.WS.info("Inserisco assegnazione utente");
		assegnaPraticaUtente( pratica, codInterventoList);
		
		ComunicazioneBean cb = praticheAction.inserisciEventoRicezione(praticaCross, pratica, eventoProcesso,destinatari, allegati);
		PraticheEventi praticaEvento = praticheService.getPraticaEvento(cb.getIdEventoPratica());
		Log.WS.info("Operazione terminata correttamente");

		Log.WS.info("Elaborazione post creazione pratica");
//	        gp.postCreazionePratica(pratica, data);
		Log.WS.info("Elaborazione post creazione pratica terminata");
		Log.WS.info("Inserisco evento ricezione");
		praticheService.startCommunicationProcess(pratica, praticaEvento, cb);

		
		//TODO:IMPLEMENTARE ASSEGNAZIONE COME PREVISTO DA SPECIFICHE ANCORA DA CAPIRE
		//Log.WS.info("Inserisco assegnazione utente");
		//assegnaPraticaUtente( pratica, codInterventoList);
		//assegnaPraticaUtenteArchivio(pratica);
		//assegnaPraticaUtente(pratica);
		
				
//	        gp.notifica(pratica, "Descrizione evento: ricezione pratica");
		Log.WS.info("Operazione terminata correttamente");
	}
	
	private void assegnaPraticaUtenteArchivio(it.wego.cross.entity.Pratica pratica) throws Exception {
		
		Utente idUtente = null;
		//idUtente = utentiService.findUtenteDaUsername("archivio.suapbari");
		idUtente = new Utente(307);
		pratica.setIdUtente(idUtente);
		
		praticheAction.aggiornaPratica(pratica);
	}
	
	/* patch produzione da rimuovere e da sostituire con il metodo assegnaPraticaUtente(it.wego.cross.entity.Pratica pratica, String procedimentoSuap) */
	private void assegnaPraticaUtente(it.wego.cross.entity.Pratica pratica) throws Exception {
		
		List<PraticaProcedimenti>  list = pratica.getPraticaProcedimentiList();
		for(PraticaProcedimenti pp : list) {
			it.wego.cross.entity.Procedimenti procedimenti = pp.getProcedimenti();
			String codProc = procedimenti.getCodProc();
			
			if (codProc.equalsIgnoreCase("P120")
					|| codProc.equalsIgnoreCase("P121")
					|| codProc.equalsIgnoreCase("P123")
					|| codProc.equalsIgnoreCase("P1313")
					|| codProc.equalsIgnoreCase("P1314")
					|| codProc.equalsIgnoreCase("P1369")
					|| codProc.equalsIgnoreCase("P1382")
					|| codProc.equalsIgnoreCase("P1384")
					|| codProc.equalsIgnoreCase("P1387")
					|| codProc.equalsIgnoreCase("P1388")
					|| codProc.equalsIgnoreCase("P1392")	
					|| codProc.equalsIgnoreCase("P1395")
					|| codProc.equalsIgnoreCase("P1397")
					|| codProc.equalsIgnoreCase("P1418")
					|| codProc.equalsIgnoreCase("P1482")
					|| codProc.equalsIgnoreCase("P1483")	
					|| codProc.equalsIgnoreCase("P1484")
					|| codProc.equalsIgnoreCase("P1485")
					|| codProc.equalsIgnoreCase("P1838")
					|| codProc.equalsIgnoreCase("P1839")
					|| codProc.equalsIgnoreCase("P1866")	
					|| codProc.equalsIgnoreCase("P1867")
					|| codProc.equalsIgnoreCase("P1985")
					|| codProc.equalsIgnoreCase("P258")
					|| codProc.equalsIgnoreCase("P360")
					|| codProc.equalsIgnoreCase("P361")
					|| codProc.equalsIgnoreCase("P495")
					|| codProc.equalsIgnoreCase("P496")
					|| codProc.equalsIgnoreCase("P498")
					|| codProc.equalsIgnoreCase("P529")
					|| codProc.equalsIgnoreCase("P583")
					|| codProc.equalsIgnoreCase("P590")
					|| codProc.equalsIgnoreCase("P592")
					|| codProc.equalsIgnoreCase("P593")
					|| codProc.equalsIgnoreCase("P594")
					|| codProc.equalsIgnoreCase("P629")	
					|| codProc.equalsIgnoreCase("P_16120014")
					|| codProc.equalsIgnoreCase("P_16120020")
					|| codProc.equalsIgnoreCase("P_16120021")
					|| codProc.equalsIgnoreCase("P_16120022")
					|| codProc.equalsIgnoreCase("P_16120023")	
					|| codProc.equalsIgnoreCase("P_16120024")
					|| codProc.equalsIgnoreCase("P_16120025")
					|| codProc.equalsIgnoreCase("P_16120027")
					|| codProc.equalsIgnoreCase("P_16120028")
					|| codProc.equalsIgnoreCase("P_16120029")	
					|| codProc.equalsIgnoreCase("P_16120030")
					|| codProc.equalsIgnoreCase("P_16120041")
					|| codProc.equalsIgnoreCase("P_16120048")
					|| codProc.equalsIgnoreCase("P_16120049")
					|| codProc.equalsIgnoreCase("P_16120050")	
					|| codProc.equalsIgnoreCase("P_16120051")
					|| codProc.equalsIgnoreCase("P_16120052")
					|| codProc.equalsIgnoreCase("P_16120054")
					|| codProc.equalsIgnoreCase("P_16120055")
					|| codProc.equalsIgnoreCase("P_16120056")
					|| codProc.equalsIgnoreCase("P_16120074")
					|| codProc.equalsIgnoreCase("P_16120075")
					|| codProc.equalsIgnoreCase("P_16120076")
					|| codProc.equalsIgnoreCase("P_16120077")
					|| codProc.equalsIgnoreCase("P_16120078")	
					|| codProc.equalsIgnoreCase("P_16120079")
					|| codProc.equalsIgnoreCase("P_16120081")
					|| codProc.equalsIgnoreCase("P_16120090")
					|| codProc.equalsIgnoreCase("P_16120094")
					|| codProc.equalsIgnoreCase("P_16120112")	
					|| codProc.equalsIgnoreCase("P_16120113")
					|| codProc.equalsIgnoreCase("P_16120114")
					|| codProc.equalsIgnoreCase("P_16120116")
					|| codProc.equalsIgnoreCase("P_16120119")
					|| codProc.equalsIgnoreCase("P_16120120")	
					|| codProc.equalsIgnoreCase("P_16120121")
					|| codProc.equalsIgnoreCase("P_16120122")
					|| codProc.equalsIgnoreCase("P_16120123")
					|| codProc.equalsIgnoreCase("P_16120124")
					|| codProc.equalsIgnoreCase("P_16120125")	
					|| codProc.equalsIgnoreCase("P_16120126")
					|| codProc.equalsIgnoreCase("P_16120127")
					|| codProc.equalsIgnoreCase("P_16120135")
					|| codProc.equalsIgnoreCase("P_16120139")
					|| codProc.equalsIgnoreCase("P_16120148")
					|| codProc.equalsIgnoreCase("P_16120153")
					|| codProc.equalsIgnoreCase("P_16120154")
					|| codProc.equalsIgnoreCase("P_16120155")
					|| codProc.equalsIgnoreCase("P_16120156")
					|| codProc.equalsIgnoreCase("P_16120157")	
					|| codProc.equalsIgnoreCase("P_16120158")
					|| codProc.equalsIgnoreCase("P_16120159")
					|| codProc.equalsIgnoreCase("P_16120160")
					|| codProc.equalsIgnoreCase("P_16120161")
					|| codProc.equalsIgnoreCase("P_16120169")	
					|| codProc.equalsIgnoreCase("P_16120178")
					|| codProc.equalsIgnoreCase("P_16120428")
					|| codProc.equalsIgnoreCase("P_16120430")
					|| codProc.equalsIgnoreCase("P_16120431")
					|| codProc.equalsIgnoreCase("P_16120481")	
					|| codProc.equalsIgnoreCase("P_16120489")
					|| codProc.equalsIgnoreCase("P_16120490")
					|| codProc.equalsIgnoreCase("P_16120491")
					|| codProc.equalsIgnoreCase("P_16120492")
					|| codProc.equalsIgnoreCase("P_16120493")	
					|| codProc.equalsIgnoreCase("P_16120617")
					|| codProc.equalsIgnoreCase("P_16120647")
					|| codProc.equalsIgnoreCase("P_16120648")
					|| codProc.equalsIgnoreCase("P_16120649")
					|| codProc.equalsIgnoreCase("P_16120652")
					|| codProc.equalsIgnoreCase("P_16120653")
					|| codProc.equalsIgnoreCase("P_16120874")
					|| codProc.equalsIgnoreCase("P_16120875")
					|| codProc.equalsIgnoreCase("P_16120876")
					|| codProc.equalsIgnoreCase("P_16120877")	
					|| codProc.equalsIgnoreCase("P_16120878")
					|| codProc.equalsIgnoreCase("P_16120924")
					|| codProc.equalsIgnoreCase("P_16120926")
					|| codProc.equalsIgnoreCase("P_16120927")
					|| codProc.equalsIgnoreCase("P_16123507")) {
				
				Utente idUtente = null;
				//idUtente = utentiService.findUtenteDaUsername("archivio.suapbari");
				idUtente = new Utente(323);
				pratica.setIdUtente(idUtente);
				pratica.setResponsabileProcedimento("Silvia Salamina");
				
			} else if (codProc.equalsIgnoreCase("P101") 
					|| codProc.equalsIgnoreCase("P103")
					|| codProc.equalsIgnoreCase("P105")
					|| codProc.equalsIgnoreCase("P106")
					|| codProc.equalsIgnoreCase("P109")
					|| codProc.equalsIgnoreCase("P110")
					|| codProc.equalsIgnoreCase("P115")
					|| codProc.equalsIgnoreCase("P117")
					|| codProc.equalsIgnoreCase("P118")
					|| codProc.equalsIgnoreCase("P1430")
					|| codProc.equalsIgnoreCase("P1440")
					|| codProc.equalsIgnoreCase("P199")
					|| codProc.equalsIgnoreCase("P237")
					|| codProc.equalsIgnoreCase("P260")
					|| codProc.equalsIgnoreCase("P264")
					|| codProc.equalsIgnoreCase("P267")
					|| codProc.equalsIgnoreCase("P271")
					|| codProc.equalsIgnoreCase("P280")
					|| codProc.equalsIgnoreCase("P282")
					|| codProc.equalsIgnoreCase("P287")
					|| codProc.equalsIgnoreCase("P378")
					|| codProc.equalsIgnoreCase("P379")
					|| codProc.equalsIgnoreCase("P390")
					|| codProc.equalsIgnoreCase("P477")
					|| codProc.equalsIgnoreCase("P489")
					|| codProc.equalsIgnoreCase("P499")
					|| codProc.equalsIgnoreCase("P500")
					|| codProc.equalsIgnoreCase("P501")
					|| codProc.equalsIgnoreCase("P502")
					|| codProc.equalsIgnoreCase("P503")
					|| codProc.equalsIgnoreCase("P504")
					|| codProc.equalsIgnoreCase("P505")
					|| codProc.equalsIgnoreCase("P506")
					|| codProc.equalsIgnoreCase("P518")
					|| codProc.equalsIgnoreCase("P519")
					|| codProc.equalsIgnoreCase("P520")
					|| codProc.equalsIgnoreCase("P81")
					|| codProc.equalsIgnoreCase("P84")
					|| codProc.equalsIgnoreCase("P85")
					|| codProc.equalsIgnoreCase("P86")
					|| codProc.equalsIgnoreCase("P91")
					|| codProc.equalsIgnoreCase("P99")
					|| codProc.equalsIgnoreCase("P_16120002")
					|| codProc.equalsIgnoreCase("P_16120042")
					|| codProc.equalsIgnoreCase("P_16120061")
					|| codProc.equalsIgnoreCase("P_16120069")
					|| codProc.equalsIgnoreCase("P_16120095")
					|| codProc.equalsIgnoreCase("P_16120101")
					|| codProc.equalsIgnoreCase("P_16120102")
					|| codProc.equalsIgnoreCase("P_16120103")
					|| codProc.equalsIgnoreCase("P_16120106")
					|| codProc.equalsIgnoreCase("P_16120109")
					|| codProc.equalsIgnoreCase("P_16120110")
					|| codProc.equalsIgnoreCase("P_16120111")
					|| codProc.equalsIgnoreCase("P_16120133")
					|| codProc.equalsIgnoreCase("P_16120140")
					|| codProc.equalsIgnoreCase("P_16120143")
					|| codProc.equalsIgnoreCase("P_16120144")
					|| codProc.equalsIgnoreCase("P_16120145")
					|| codProc.equalsIgnoreCase("P_16120149")
					|| codProc.equalsIgnoreCase("P_16120167")
					|| codProc.equalsIgnoreCase("P_16120176")
					|| codProc.equalsIgnoreCase("P_16120180")
					|| codProc.equalsIgnoreCase("P_16120186")
					|| codProc.equalsIgnoreCase("P_16120187")
					|| codProc.equalsIgnoreCase("P_16120472")
					|| codProc.equalsIgnoreCase("P_16120473")
					|| codProc.equalsIgnoreCase("P_16120474")
					|| codProc.equalsIgnoreCase("P_16120483")
					|| codProc.equalsIgnoreCase("P_16120870")
					|| codProc.equalsIgnoreCase("P_16120871")
					|| codProc.equalsIgnoreCase("P_16120872")) {
				Utente idUtente = null;
				//idUtente = utentiService.findUtenteDaUsername("archivio.suapbari");
				idUtente = new Utente(322);
				pratica.setIdUtente(idUtente);
				pratica.setResponsabileProcedimento("Avv. Roberta Del Core");
			}  else {
				
				Utente idUtente = null;
				//idUtente = utentiService.findUtenteDaUsername("archivio.suapbari");
				idUtente = new Utente(307);
				pratica.setIdUtente(idUtente);
			}

		}
		
		praticheAction.aggiornaPratica(pratica);
	}
	
	private void assegnaPraticaUtente(it.wego.cross.entity.Pratica pratica, List<String> codInterventoList) throws Exception {

		List<LkAssociazioneProcedimentoEnteUtenteSuap> list = new ArrayList<>();
		
		if(pratica.getIdProcEnte()!=null && pratica.getIdProcEnte().getIdEnte()!=null)
			for(String codOperazione: codInterventoList)
				list.add(lookupDao.findAssociazioneProcedimentoEnteUtenteSuap(
						codOperazione, pratica.getIdProcEnte().getIdEnte().getIdEnte()));	
		

		if(!list.isEmpty()) {
			LkAssociazioneProcedimentoEnteUtenteSuap currentAssociationPerUser = null;
			LkAssociazioneProcedimentoEnteUtenteSuap currentAssociationPerSupervisor = null;
			if( list.size() == 1) {
				currentAssociationPerUser = list.get(0);
				currentAssociationPerSupervisor = list.get(0);
			}
			else {
				for(LkAssociazioneProcedimentoEnteUtenteSuap association: list) {
					if(currentAssociationPerUser == null || (currentAssociationPerUser.getPrioritaUtente() < association.getPrioritaResponsabile()))
						currentAssociationPerUser = association;
					if(currentAssociationPerSupervisor == null || (currentAssociationPerSupervisor.getPrioritaUtente() < association.getPrioritaResponsabile()))
						currentAssociationPerSupervisor = association;
				}
			}
			pratica.setIdUtente(currentAssociationPerUser.getIdUtente());
			Utente supervisor = currentAssociationPerSupervisor.getIdResponsabileProcedimento();
			if(supervisor!=null) {
				pratica.setResponsabileProcedimento(supervisor.getCognome() + " " + supervisor.getNome());
			}
			praticheAction.aggiornaPratica(pratica);
		}
	}

	private Anagrafiche recuperoAnagrafica(AnagraficaImpresa impresa) throws DatatypeConfigurationException {
		Anagrafiche anagrafica = new Anagrafiche();
		Anagrafica anagraficaDaInserire = new Anagrafica();

		anagraficaDaInserire.setDenominazione(impresa.getRagioneSociale());
		anagraficaDaInserire.setCodiceFiscale(impresa.getCodiceFiscale());
		anagraficaDaInserire.setPartitaIva(impresa.getPartitaIva()!=null?impresa.getPartitaIva() : impresa.getCodiceFiscale());
		
		anagraficaDaInserire.setNome(impresa.getLegaleRappresentante().getNome());
		anagraficaDaInserire.setCognome(impresa.getLegaleRappresentante().getCognome());
		String cittadinanza = "";
		if(impresa.getIndirizzo().getStato()!=null) {
			if(impresa.getIndirizzo().getStato().getCodice().equals("I")) {
				cittadinanza = "000";
			}/*per lo straniero capire i valori previsti*/
		}
		/*if(impresa.getCodiceREA()!=null) {
			Date dataIscrizioneRea = impresa.getCodiceREA().getDataIscrizione();
			 anagraficaDaInserire.setDataIscrizioneRea(Utils.dateToXmlGregorianCalendar(dataIscrizioneRea));
			 anagraficaDaInserire.setNIscrizioneRea(impresa.getCodiceREA().getValue());
			if(dataIscrizioneRea!=null) {
			 anagraficaDaInserire.setFlgAttesaIscrizioneRea(true);
			}
		}*/
		anagraficaDaInserire.setCodCittadinanza(cittadinanza);
		anagraficaDaInserire.setCodNazionalita(cittadinanza);
		String codProvinciaCciaa = impresa.getIndirizzo().getProvincia().getSigla();
		anagraficaDaInserire.setCodProvinciaCciaa(codProvinciaCciaa);
		anagraficaDaInserire.setCodProvinciaIscrizione(codProvinciaCciaa);
		
		Recapiti recapito = new Recapiti();
		List<Recapito> recapitoList = new ArrayList<Recapito>();
		Recapito rec = new Recapito();
		rec.setDesProvincia(impresa.getIndirizzo().getProvincia().getValue());
		rec.setCap(impresa.getIndirizzo().getCap());
		rec.setDesComune(impresa.getIndirizzo().getComune().getValue());
		rec.setCodiceCivico(impresa.getIndirizzo().getNumeroCivico());
		String indirizzo = impresa.getIndirizzo().getToponimo() + " "+impresa.getIndirizzo().getDenominazioneStradale();
		rec.setIndirizzo(indirizzo);
		rec.setIdTipoIndirizzo(BigInteger.valueOf(3));
		
		recapitoList.add(rec);
		recapito.setRecapito(recapitoList);
		anagraficaDaInserire.setRecapiti(recapito);
		anagraficaDaInserire.setDesFormaGiuridica(impresa.getFormaGiuridica().getValue());
		anagraficaDaInserire.setTipoAnagrafica("G");
		anagrafica.setAnagrafica(anagraficaDaInserire);
		anagrafica.setCodTipoRuolo("B");
		anagrafica.setDesTipoRuolo("Beneficiario");
		
		return anagrafica;
	}
	
	private Processi getIdProcesso(Enti ente, it.wego.cross.entity.Procedimenti procedimentoSuap) throws Exception {
        Processi processo = workflowService.getProcessToUse(ente.getIdEnte(), procedimentoSuap.getIdProc());
        return processo;
    }
	
	
	
	public EstremiDichiarante getEstremiDichiaranteFromFileXml(RiepilogoPraticaSUAP riepilogoPraticaSUAP) throws IOException, JAXBException {
		
		EstremiDichiarante estremiDichiarante = null;
		if(riepilogoPraticaSUAP != null) {
			if(riepilogoPraticaSUAP.getIntestazione()!=null) {
				if(riepilogoPraticaSUAP.getIntestazione().getDichiarante() !=null)
					estremiDichiarante = riepilogoPraticaSUAP.getIntestazione().getDichiarante();
			}
		}
		return estremiDichiarante;
    }
	public Anagrafiche recuperaAnagraficaDichiarante(EstremiDichiarante estremiDichiarante) {
		
		Anagrafiche anagrafica = new Anagrafiche();
		Anagrafica anagraficaDaInserire = new Anagrafica();
		anagraficaDaInserire.setDenominazione(estremiDichiarante.getCodiceFiscale());
		anagraficaDaInserire.setCodiceFiscale(estremiDichiarante.getCodiceFiscale());
		anagraficaDaInserire.setCognome(estremiDichiarante.getCognome());
		anagraficaDaInserire.setNome(estremiDichiarante.getNome());
		
		anagraficaDaInserire.setTipoAnagrafica("F");
		String qualificaEd = estremiDichiarante.getQualifica();
		Stato statoAnagraficaDichiarante = null;
		statoAnagraficaDichiarante = estremiDichiarante.getNazionalita();
		List<Recapito> recapitoList = new ArrayList<Recapito>();
		Recapiti recapito = new Recapiti();
		Recapito rec = new Recapito();
		/*rivedere per capire come gestire la cittadinanza estera*/
		/*if(statoAnagraficaDichiarante!=null) {
			if(statoAnagraficaDichiarante.getCodice().equals("IT")) {
				rec.setIdStato(BigInteger.valueOf(1));
			}
		}*/
		/*rec.setDesProvincia(impresa.getIndirizzo().getProvincia().getValue());
		rec.setCap(impresa.getIndirizzo().getCap());
		rec.setDesComune(impresa.getIndirizzo().getComune().getValue());
		rec.setCodiceCivico(impresa.getIndirizzo().getNumeroCivico());
		rec.setIndirizzo(impresa.getIndirizzo().getDenominazioneStradale());*/
		rec.setIdTipoIndirizzo(BigInteger.valueOf(3));
		recapitoList.add(rec);
		recapito.setRecapito(recapitoList);
		anagraficaDaInserire.setRecapiti(recapito);
		anagrafica.setAnagrafica(anagraficaDaInserire);
		anagrafica.setCodTipoRuolo("R");
		anagrafica.setDesTipoRuolo("Richiedente");
		/*anagraficaDaInserire.set
        estremiDichiarante.setPec(!Utils.e(recapitoDichiarantePratica.getPec()) ? recapitoDichiarantePratica.getPec() : recapitoDichiarantePratica.getEmail());
        estremiDichiarante.setQualifica(integrazioneRea.getQualificaDichiarantePratica());
        estremiDichiarante.setTelefono(recapitoDichiarantePratica.getTelefono());*/
		return anagrafica;
	}
	
	private RiepilogoPraticaSUAP getRiepilogoPraticaSUAP(File fileXml) throws IOException, JAXBException {
		Log.WS.info("Unmarshal suap.xml - start");
		byte[] bytesArray = Utils.readByteArrayFromFile(fileXml);
		String xmlPratica = new String(bytesArray);
		xmlPratica = Utils.encodingXml(xmlPratica);
		JAXBContext context = JAXBContext.newInstance(RiepilogoPraticaSUAP.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		StringReader reader = new StringReader(xmlPratica);
		RiepilogoPraticaSUAP riepilogoPraticaSUAP = null;
		riepilogoPraticaSUAP = (RiepilogoPraticaSUAP) unmarshaller.unmarshal(reader);
		Log.WS.info("Unmarshal suap.xml - end");
		return riepilogoPraticaSUAP; 
	}
	
	private List<String> getCodIntervento(RiepilogoPraticaSUAP riepilogoPraticaSUAP) {
		String codIntervento = "";
		List<String> listaCodici = new ArrayList<String>();
		if(riepilogoPraticaSUAP.getStruttura()!=null) {
			if(!riepilogoPraticaSUAP.getStruttura().getModulo().isEmpty()) {
				List<AdempimentoSUAP> listAdempimenti = riepilogoPraticaSUAP.getStruttura().getModulo();
				for(AdempimentoSUAP adempimentoSUAP: listAdempimenti) {
					codIntervento = adempimentoSUAP.getCod();
					List<EstremiEnte> enti = adempimentoSUAP.getEnteCoinvolto();
					listaCodici.add(codIntervento);
				}
			}
		}
		return listaCodici;
	}
	
	private Pratica gestioneFileMDA(File mdaFile,it.wego.cross.xml.Pratica praticaCross) throws Exception {
		
		String data = Utils.convertFileToString(mdaFile);
		String fileString = Utils.convertXmlCharset(data, "ISO-8859-1", "UTF-8");
		System.out.println("FileMDA: "+fileString);
		/*patch prod*/
		fileString = fileString.replace("é", "e");
		fileString = fileString.replace("è", "e");
		fileString = fileString.replace("à", "a");
		fileString = fileString.replace("°", " ");
		fileString = fileString.replace("B&amp;B","B and B");
		System.out.println("FileMDA pulito: "+fileString);
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource src = new InputSource(new StringReader(fileString));
		Document doc = builder.parse(src);
		doc.getDocumentElement().normalize();
		Element element = doc.getDocumentElement();
		NodeList nodes = element.getChildNodes();
        Node node;
        it.wego.cross.xml.DatiCatastali datiCatastaliList = new it.wego.cross.xml.DatiCatastali();
        
	    List<Immobile> listaImmobili = new ArrayList<Immobile>();
	    Immobile immobile = null;
	    immobile = new Immobile();
	    
	    
//	    IndirizzoIntervento indirizzoIntervento = new IndirizzoIntervento();
       // if(element.getTagName().equalsIgnoreCase("scia:ModuloSCIA")) {
        	for(int i = 0; i < nodes.getLength(); i++) {
                node = nodes.item(i);
               
                	immobile = riempiDatiCatastali(node,3,immobile);
                	
                }
                
           // }
     //   }
        
        Log.WS.info("riempiDatiCatastali - Start");
        
        if(immobile!=null && immobile.getEstensioneParticella()!=null) {
        	System.out.println("riempiDatiCatastali set sistema catastale");
        	immobile.setIdTipoSistemaCatastale(new BigInteger("1"));
        	if(immobile.getDesTipoUnita()!=null) {
        		if(immobile.getDesTipoUnita().equals("FABBRICATI")) {
   				 	immobile.setIdTipoUnita(new BigInteger("1"));
   				 System.out.println("riempiDatiCatastali set fabbricati");
   			 	}else if(immobile.getDesTipoUnita().equals("TERRENI")) {
   			 		immobile.setIdTipoUnita(new BigInteger("2"));
      				 System.out.println("riempiDatiCatastali set terreni");
   			 	} else {
   			 		immobile.setIdTipoUnita(null);
      				 System.out.println("riempiDatiCatastali set tipo unita null");
   			 	}
        	}
			
            listaImmobili.add(immobile);
    		datiCatastaliList.setImmobile(listaImmobili);
            praticaCross.setDatiCatastali(datiCatastaliList);
            
		 } else if (immobile!=null){
			 
			 System.out.println("riempiDatiCatastali set sistema catastale null");
			 
/*			Immobile i = new Immobile();
			immobile.setIdTipoSistemaCatastale(new BigInteger("1"));
			listaImmobili.add(i);
			datiCatastaliList.setImmobile(listaImmobili);*/
	    	
			datiCatastaliList.setImmobile(listaImmobili);
	        praticaCross.setDatiCatastali(datiCatastaliList);
			
		 }

        /*
        listXmlIndirizziIntervento.add(indirizzoIntervento);
        indirizziIntervento.setIndirizzoIntervento(listXmlIndirizziIntervento);
        praticaCross.setIndirizziIntervento(indirizziIntervento);
        */

        Log.WS.info("riempiDatiCatastali - End");

        return praticaCross;

	}
	 public static Immobile riempiDatiCatastali(Node node, int level,Immobile immobile) {
		 
		 
		 if(node.getNodeName().toLowerCase().contains("particella")) {
			 immobile.setEstensioneParticella(node.getTextContent());
		 }
		
		 if(node.getNodeName().toLowerCase().contains("subalterno")) {
			 immobile.setSubalterno(node.getTextContent());
		 }
			 
		 if(node.getNodeName().toLowerCase().contains("foglio")) {
			 immobile.setFoglio( node.getTextContent());
		 }
			
		 if(node.getNodeName().toLowerCase().contains("tipo")) {
			 immobile.setDesTipoUnita(node.getTextContent());			 

		 }
			  NodeList list = node.getChildNodes();
			  for (int i = 0; i < list.getLength(); i++) {
			  Node childNode = list.item(i);
			      riempiDatiCatastali(childNode, level + 1,immobile);
			  }
		 

		 
		 return immobile;
	}
	 
	 public static IndirizzoIntervento riempiIndirizzoIntervento(Node node, int level,IndirizzoIntervento indirizzo) {
		 if(node.getNodeName().equals("aggregate:stato"))
			 indirizzo.setAltreInformazioniIndirizzo(node.getTextContent());
		 
			
		 if(node.getNodeName().equals("aggregate:comune"))
			 indirizzo.setLocalita(node.getTextContent());
		if(node.getNodeName().equals("aggregate:specie")) 
			 indirizzo.setCodVia(node.getTextContent());
			 
		if(node.getNodeName().equals("aggregate:indirizzo")) 
			indirizzo.setIndirizzo(indirizzo.getCodVia()+ " "+ node.getTextContent()); 
			 
			
		 if(node.getNodeName().equals("aggregate:civico"))
			indirizzo.setCivico(node.getTextContent());
		 if(node.getNodeName().equals("aggregate:CAP"))
			 indirizzo.setCap(node.getTextContent());
		 
		 		
		    NodeList list = node.getChildNodes();
		    for (int i = 0; i < list.getLength(); i++) {
		      Node childNode = list.item(i);
		      riempiIndirizzoIntervento(childNode, level + 1,indirizzo);
		    }
		    
		    return indirizzo;
	}
	
	/*public it.wego.cross.xml.DatiCatastali gestioneDatiCatastali(List<DatiCatastali> listaDatiCatastaliInfocamere) {
		it.wego.cross.xml.DatiCatastali datiCatastaliList = new it.wego.cross.xml.DatiCatastali();
        for(DatiCatastali datiCatastali :listaDatiCatastaliInfocamere) {
        	List<Immobile> listaImmobili = new ArrayList<Immobile>();
    	    Immobile immobile = new Immobile();
    	    immobile.setEstensioneParticella(datiCatastali.getS);
    	    listaImmobili.add(immobile);
    		datiCatastaliList.setImmobile(listaImmobili);
            
        }
        return datiCatastaliList;	
	    
	    
	}*/
	public it.wego.cross.xml.IndirizziIntervento gestioneIndirizziIntervento(Indirizzo indirizzoInfocamere) {
		System.out.println("gestioneIndirizziIntervento - start");
		List<it.wego.cross.xml.IndirizzoIntervento> listXmlIndirizziIntervento = new ArrayList<IndirizzoIntervento>();
	    it.wego.cross.xml.IndirizziIntervento indirizziIntervento = new it.wego.cross.xml.IndirizziIntervento();
	    IndirizzoIntervento indirizzoIntervento = new IndirizzoIntervento();
	    indirizzoIntervento.setIndirizzo(indirizzoInfocamere.getToponimo()+" "+ indirizzoInfocamere.getDenominazioneStradale());
	    indirizzoIntervento.setCap(indirizzoInfocamere.getCap());
	    indirizzoIntervento.setCivico(indirizzoInfocamere.getNumeroCivico());
	    indirizzoIntervento.setLocalita(indirizzoInfocamere.getComune().getValue());
	    indirizzoIntervento.setAltreInformazioniIndirizzo(indirizzoInfocamere.getProvincia().getValue());
	    listXmlIndirizziIntervento.add(indirizzoIntervento);
	    indirizziIntervento.setIndirizzoIntervento(listXmlIndirizziIntervento);
	    System.out.println("gestioneIndirizziIntervento - end");
	    return indirizziIntervento;
	}
	
	public void getDatiIdentificativiImpresa(String codiceFiscale) {
        Log.APP.info("Ricerca su registro imprese con il seguente codice fiscale " + codiceFiscale);
        DatiIdentificativiRIDTO datiIdentificativi;
        try {
            IscrizioneImpresaRiSpcResponse dettaglioRichiestaWS = registroImpreseService.richiestaIscrizioneImpresa(codiceFiscale);
            datiIdentificativi = registroImpreseSerializer.serialize(dettaglioRichiestaWS);
            LkProvincie provinciaCCIAA = comuniService.findProvinciaByCodice(datiIdentificativi.getCciaa(), Boolean.TRUE);
            if (provinciaCCIAA != null) {
                datiIdentificativi.setDesCciaa(provinciaCCIAA.getDescrizione());
                datiIdentificativi.setIdCciaa(provinciaCCIAA.getIdProvincie());
            }
        } catch (Exception ex) {
            Log.WS.error("Errore contattanto il webservice di registro imprese", ex);
            datiIdentificativi = new DatiIdentificativiRIDTO();
            ErroreRIDTO errore = new ErroreRIDTO();
            errore.setCodice("Errore webservice Registro Imprese");
            errore.setValore(ex.getMessage());
            datiIdentificativi.setErrore(errore);
            ErroreDTO error = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_GESTIONE_ANAGRAFICHE_PERSONAGIURIDICA, "Errore nell'esecuzione del controller /gestione/anagrafiche/personagiuridica", ex, null, null);
            erroriAction.saveError(error);
        }
    }
}
