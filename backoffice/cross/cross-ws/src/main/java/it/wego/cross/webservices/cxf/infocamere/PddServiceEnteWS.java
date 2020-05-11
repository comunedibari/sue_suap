package it.wego.cross.webservices.cxf.infocamere;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.datatype.DatatypeConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;

import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte.Intestazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gov.impresainungiorno.schema.suap.ente.OggettoCooperazione;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaImpresa;
import it.gov.impresainungiorno.schema.suap.pratica.EstremiSuap;
import it.gov.impresainungiorno.schema.suap.pratica.OggettoComunicazione;
import it.gov.impresainungiorno.schema.suap.pratica.ProtocolloSUAP;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.Error;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dao.TemplateDao;
import it.wego.cross.dto.AllegatoRicezioneDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Staging;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.serializer.ProcedimentiSerializer;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Allegato;
import it.wego.cross.xml.Anagrafica;
import it.wego.cross.xml.Anagrafiche;
import it.wego.cross.xml.Ente;
import it.wego.cross.xml.Pratica;
import it.wego.cross.xml.Procedimenti;
import it.wego.cross.xml.Procedimento;
import it.wego.cross.xml.Recapiti;
import it.wego.cross.xml.Recapito;
import it.wego.cross.xml.Scadenze;

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

	// @XmlAttachmentRef
	public java.lang.String inviaSUAPEnte(it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte cooperazioneSUAPEnte) throws Exception {

		Intestazione intestazione = cooperazioneSUAPEnte.getIntestazione();
		String identificativoPratica = intestazione.getCodicePratica();
		String esito = "";
		it.wego.cross.xml.Pratica praticaCross = new Pratica();
		praticaCross = creazionePraticaCross(cooperazioneSUAPEnte, intestazione, identificativoPratica);

		it.wego.cross.entity.Pratica praticaEsistente = praticheService.getPraticaByIdentificativo(identificativoPratica);
		if (praticaEsistente != null) {
			inserimentoEventoCooperazione(praticaEsistente, praticaCross,cooperazioneSUAPEnte);
			esito = "Pratica aggiornata";
		} else {
			inserimentoPratica(cooperazioneSUAPEnte, intestazione, identificativoPratica, praticaCross);
			esito = "Ok";
		}

		return esito;

	}

	private Pratica creazionePraticaCross(CooperazioneSUAPEnte cooperazioneSUAPEnte, Intestazione intestazione,
			String identificativoPratica) throws DatatypeConfigurationException {
		EstremiSuap suapCompetente = intestazione.getSuapCompetente();

		Enti enteSuap = entiService.findByIdentificativoSuap(String.valueOf(suapCompetente.getIdentificativoSuap()));

		AnagraficaImpresa impresa = intestazione.getImpresa();
		OggettoComunicazione oggettoPratica = intestazione.getOggettoPratica();
		OggettoCooperazione oggettoComunicazione = intestazione.getOggettoComunicazione();
		ProtocolloSUAP protocolloPraticaSuap = intestazione.getProtocolloPraticaSuap();
		String testoComunicazione = intestazione.getTestoComunicazione();
		it.wego.cross.xml.Pratica praticaCross = new Pratica();
		praticaCross.setIdentificativoPratica(identificativoPratica);

		List<Anagrafiche> anagraficheList = recuperoAnagrafica(impresa);
		praticaCross.setAnagrafiche(anagraficheList);
		praticaCross.setDataRicezione(Utils.dateToXmlGregorianCalendar(new Date()));
		it.wego.cross.entity.Procedimenti procedimento = procedimentiService.getProcedimento("IMPRESA_IN_UN_GIORNO");
		ProcedimentiEnti pe = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(enteSuap.getIdEnte(), procedimento.getIdProc());
		Procedimenti procedimentoXml = new Procedimenti();
		List<Procedimento> procedimentiList = new ArrayList<Procedimento>();
		Procedimento proc = new Procedimento();
		proc = ProcedimentiSerializer.serializeXML(pe);
//		proc.setCodEnteDestinatario(enteSuap.getCodEnte());
		proc.setCodProcedimento(procedimento.getCodProc());
		procedimentiList.add(proc);
		procedimentoXml.setProcedimento(procedimentiList );
		praticaCross.setProcedimenti(procedimentoXml);
		
		//allegati da recuperare dalla request
		//allegato moccato
		it.wego.cross.xml.Allegati allegatiXml = new it.wego.cross.xml.Allegati();
		List<Allegato> list = allegatiXml.getAllegato();
		Allegato allegato = new Allegato();
		allegato.setDescrizione("prova");
		allegato.setNomeFile("93296140721-13122019-1749.001.PDF.P7M");
		allegato.setPathFile("/usr/local/apache-tomcat-6.0.41/cross_files/documents\\277a9214-8f58-49d4-9d87-298cee6e7d33_93296140721-13122019-1749.001.PDF.P7M");
		allegato.setIdAllegato(new BigInteger("57907"));
		allegato.setRiepilogoPratica("S");
		list.add(allegato);
		allegatiXml.setAllegato(list);
		praticaCross.setAllegati(allegatiXml);
		return praticaCross;
	}

	private void inserimentoEventoCooperazione(it.wego.cross.entity.Pratica praticaEsistente, it.wego.cross.xml.Pratica praticaCross, CooperazioneSUAPEnte cooperazioneSUAPEnte) throws Exception {
		Log.WS.info("Inserisco evento Copperazione");
		List<String> destinatari = new ArrayList<String>();
		Log.WS.info("Trovati " + destinatari.size() + " destinatari");
		Log.WS.info("Inserisco evento di ricezione");
		ProcessiEventi eventoProcesso = processiDao.findProcessiEventiByCodEventoIdProcesso("AGGSUAP", praticaEsistente.getIdProcesso().getIdProcesso());
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

	private void inserimentoPratica(it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte cooperazioneSUAPEnte, Intestazione intestazione, String identificativoPratica, Pratica praticaCross)
			throws DatatypeConfigurationException, PropertyException, JAXBException, Exception {
		EstremiSuap suapCompetente = intestazione.getSuapCompetente();
		Enti enteSuap = entiService.findByIdentificativoSuap(String.valueOf(suapCompetente.getIdentificativoSuap()));
		it.wego.cross.entity.Procedimenti procedimento = procedimentiService.getProcedimento("IMPRESA_IN_UN_GIORNO");
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
			it.gov.impresainungiorno.schema.suap.ente.ObjectFactory of = new it.gov.impresainungiorno.schema.suap.ente.ObjectFactory();
			JAXBElement<CooperazioneSUAPEnte> root = of.createCooperazioneSuapEnte(cooperazioneSUAPEnte);
			xmlRicevuto = Utils.marshall(root, CooperazioneSUAPEnte.class);
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

		pratica.setIdProcEnte(pe);
		Processi processo = getIdProcesso(enteSuap, procedimento);
		pratica.setIdProcesso(processo);
		Log.WS.info("Salvo la pratica");
		praticheAction.salvaPratica(pratica);
		Log.WS.info("Popolo i procedimenti");
		praticheAction.popolaProcedimenti(pratica, praticaCross);

		// Inserisco le anagrafiche, per quanto possibile, collegate alla pratica
		if (praticaCross.getAnagrafiche() != null && !praticaCross.getAnagrafiche().isEmpty()) {
			try {
				praticheAction.insertAnagrafiche(praticaCross.getAnagrafiche(), pratica);
				xmlPratica = PraticaUtils.getXmlFromPratica(praticaCross);
				staging.setXmlPratica(xmlPratica.getBytes());
				praticheAction.updateStaging(staging, pratica);
			} catch (Exception ex) {
				Log.WS.error("Non è stato possibile salvare l'anagrafica", ex);
			}
		}
		
		pratica.setData_prot_suap(intestazione.getProtocolloPraticaSuap().getDataRegistrazione().toGregorianCalendar().getTime());
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
		
		ComunicazioneBean cb = praticheAction.inserisciEventoRicezione(praticaCross, pratica, eventoProcesso,destinatari, allegati);
		PraticheEventi praticaEvento = praticheService.getPraticaEvento(cb.getIdEventoPratica());
		Log.WS.info("Operazione terminata correttamente");

		Log.WS.info("Elaborazione post creazione pratica");
//	        gp.postCreazionePratica(pratica, data);
		Log.WS.info("Elaborazione post creazione pratica terminata");

		praticheService.startCommunicationProcess(pratica, praticaEvento, cb);

		Log.WS.info("Inserisco evento ricezione");
//	        gp.notifica(pratica, "Descrizione evento: ricezione pratica");
		Log.WS.info("Operazione terminata correttamente");
	}

	private List<Anagrafiche> recuperoAnagrafica(AnagraficaImpresa impresa) {
		List<Anagrafiche> anagraficheList = new ArrayList<Anagrafiche>();
		Anagrafiche anagrafica = new Anagrafiche();
		Anagrafica anagraficaDaInserire = new Anagrafica();

		anagraficaDaInserire.setDenominazione(impresa.getRagioneSociale());
		anagraficaDaInserire.setCodiceFiscale(impresa.getCodiceFiscale());
		anagraficaDaInserire.setPartitaIva(impresa.getPartitaIva());
		anagraficaDaInserire.setNome(impresa.getLegaleRappresentante().getNome());
		anagraficaDaInserire.setCognome(impresa.getLegaleRappresentante().getCognome());

		Recapiti recapito = new Recapiti();
		List<Recapito> recapitoList = new ArrayList<Recapito>();
		Recapito rec = new Recapito();
		rec.setDesProvincia(impresa.getIndirizzo().getProvincia().getValue());
		rec.setCap(impresa.getIndirizzo().getCap());
		rec.setDesComune(impresa.getIndirizzo().getComune().getValue());
		rec.setCodiceCivico(impresa.getIndirizzo().getNumeroCivico());
		rec.setIndirizzo(impresa.getIndirizzo().getDenominazioneStradale());
		rec.setIdTipoIndirizzo(BigInteger.valueOf(3));
		recapitoList.add(rec);
		recapito.setRecapito(recapitoList);
		anagraficaDaInserire.setRecapiti(recapito);
		anagraficaDaInserire.setDesFormaGiuridica(impresa.getFormaGiuridica().getValue());
		anagraficaDaInserire.setTipoAnagrafica("F");
		anagrafica.setAnagrafica(anagraficaDaInserire);
		anagrafica.setCodTipoRuolo("B");
		anagraficheList.add(anagrafica);
		return anagraficheList;
	}
	
	private Processi getIdProcesso(Enti ente, it.wego.cross.entity.Procedimenti procedimentoSuap) throws Exception {
        Processi processo = workflowService.getProcessToUse(ente.getIdEnte(), procedimentoSuap.getIdProc());
        return processo;
    }

}
