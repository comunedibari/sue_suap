/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import com.google.common.base.Strings;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import it.wego.cross.beans.AttoriComunicazione;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.beans.MailContentBean;
import it.wego.cross.comparators.ProcessiEventiComparator;
import it.wego.cross.constants.AnaTipiDestinatario;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.Workflow;
import it.wego.cross.dao.*;
import it.wego.cross.entity.*;
import it.wego.cross.events.AbstractEvent;
import it.wego.cross.freemarker.DateDirective;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.*;
import org.activiti.engine.task.Task;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

/**
 *
 * @author Gabri
 */
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

	@Autowired
	private PraticaDao praticaDao;
	@Autowired
	private DocEngineService docEngineService;
	@Autowired
	private AnagraficheService anagraficheService;
	@Autowired
	private ProcessiDao processiDao;
	@Autowired
	private TemplateDao templateDao;
	@Autowired
	private ApplicationContext appContext;
	@Autowired
	private UtentiService utentiService;
	@Autowired
	private PraticheService praticheService;
	@Autowired
	private PraticheSerializer praticheSerializer;
	@Autowired
	private ProcedimentiService procedimentiService;
	@Autowired
	private PraticheProtocolloService praticheProtocolloService;

	@Override
	public List<ProcessiEventi> findAvailableEvents(Integer idPratica) throws Exception {
		List<ProcessiEventi> processiEventiResultList = new LinkedList<ProcessiEventi>();
		Log.APP.info("Ricerco la pratica con id " + idPratica);
		Pratica pratica = praticaDao.findPratica(idPratica);
		if (pratica == null) {
			throw new Exception("Impossibile trovare la pratica richiesta.");
		}
		Log.APP.info("Ricerco tutti gli eventi possibili sulla pratica");
		List<PraticheEventi> eventiPassati = praticaDao.findAllEventsForPratica(idPratica);
		Log.APP.info("Trovati " + eventiPassati.size() + " eventi");
		Processi processo = pratica.getIdProcesso();
		if (eventiPassati.isEmpty() || processo == null) {
			Log.APP.info("Sulla pratica non è ancora stato attivato un Processo.");
			return processiEventiResultList;
		}
		Log.APP.info("Ricerco i prossimi eventi sulla pratica");
		List<ProcessiSteps> processiSteps = praticaDao.findNextSteps(idPratica);
		if( processiSteps == null || processiSteps.isEmpty() )
		{
			//Controllo se siamo nel caso dello sportello SUE di Bitonto
			Enti ente = pratica.getIdProcEnte().getIdEnte();
			if( ente.getIdEnte() == 2249 )
			{
				Log.PRATICHE_SPURIE.warn("PER LA PRATICA CON ID [{}] ED ASSOCIATA ALL'ENTE CON ID [{}] E DESCRIZIONE [{}] NESSUNO STEP TROVATO. PROCEDO ALLA ESECUZIONE DELLA QUERY TRALASCIANDO LA CONFIGURAZIONE DELL'ENTE", pratica.getIdPratica(), ente.getIdEnte(), ente.getDescrizione());
				processiSteps = praticaDao.findNextStepsFixed(idPratica, pratica.getIdProcesso().getIdProcesso());
			}
		}
		Log.APP.info("Trovati " + processiSteps.size() + " eventi totali. Filtro solo quelli attivi ...");
		for (ProcessiSteps step : processiSteps) {
			ProcessiEventi pe = step.getIdEventoResult();
			if (step.getTipoOperazione().equalsIgnoreCase("ADD")) {
				processiEventiResultList.add(pe);
			} else {
				if (processiEventiResultList.contains(pe)) {
					processiEventiResultList.remove(pe);
				}
			}
		}
		processiEventiResultList = eliminaDuplicati(processiEventiResultList);
		Log.APP.info("Trovati " + processiEventiResultList.size() + " eventi attivi");
		return processiEventiResultList;
	}

	@Override
	public Processi getProcessToUse(Integer idEnte, Integer idProcedimento) throws Exception {
		//        ProcedimentiEnti procedimentiEnti = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(idEnte, idProcedimento);
		ProcedimentiEnti procedimentiEnti = procedimentiService.requireProcedimentoEnte(idProcedimento, idEnte, null, null);
		//        if (procedimentiEnti == null) {
		//            return null;
		//        }
		return procedimentiEnti.getIdProcesso();
	}

	@Override
	public void gestisciProcessoEvento(EventoBean eb) throws Exception {
		ProcessiEventi processoEvento = praticheService.findProcessiEventi(eb.getIdEventoProcesso());
		String funzioneApplicativaEvento = processoEvento.getFunzioneApplicativa();
		if (eb.getDataEvento() == null) {
			eb.setDataEvento(Utils.now());
		}

		AbstractEvent eventoRepo;
		if (funzioneApplicativaEvento == null) {
			eventoRepo = (AbstractEvent) appContext.getBean("nop_event");
		} else {
			try {
				eventoRepo = (AbstractEvent) appContext.getBean(funzioneApplicativaEvento);
			} catch (BeansException e) {
				Log.APP.warn("ATTENZIONE NON HO TROVATO LA FUNZIONE APPLICATIVA '" + funzioneApplicativaEvento + "' USO QUELLA DI DEFAULT");
				eventoRepo = (AbstractEvent) appContext.getBean("nop_event");
			}
		}

		eventoRepo.insertEvento(eb);

		Pratica pratica = praticheService.getPratica(eb.getIdPratica());
		Enti entePratica = pratica.getIdProcEnte().getIdEnte();
		LkComuni comunePratica = pratica.getIdComune();
		for (PraticaProcedimenti praticaProcedimento : pratica.getPraticaProcedimentiList()) {
			//Richiamo il metodo per essere sicuro che esista il procedimento-ente
			procedimentiService.requireProcedimentoEnte(praticaProcedimento.getPraticaProcedimentiPK().getIdProcedimento(), praticaProcedimento.getPraticaProcedimentiPK().getIdEnte(), entePratica, comunePratica);
		}
		//        //Se arrivo da una pratica-protocollo devo aggiornare il relativo record in banca dati e valorizzare i dati relativi agli estremi di protocollazione, se non mi sono stati passati
		//        if (eb.getIdPraticaProtocollo() != null) {
		//            PraticheProtocollo praticaProtocollo = praticheProtocolloService.findPraticaProtocolloById(eb.getIdPraticaProtocollo());
		//            //Aggiorno le informazioni relative alla pratica-protocollo. Mi serve per non rendere più visibile il documento dalla sezione Comunicazioni in ingresso
		//            praticaProtocollo.setIdPratica(pratica);
		//            praticaProtocollo.setDataPresaInCaricoCross(new Date());
		//            Utente utente = utentiService.findUtenteByIdUtente(eb.getIdUtente());
		//            praticaProtocollo.setIdUtentePresaInCarico(utente);
		//            praticheProtocolloService.aggiorna(praticaProtocollo);
		//        }
		//        eventoRepo.execute(eb);

		//        for (ProcessiEventi processoEvento : findAvailableEvents(eb.getPratica().getIdPratica())) {
		//            if ("S".equalsIgnoreCase(processoEvento.getFlgAutomatico())) {
		//                EventoBean eventoBean = eb.getClass().newInstance();
		//                eventoBean.setEventoAutomatico(Boolean.TRUE);
		//                eventoBean.setPratica(eb.getPratica());
		//                eventoBean.setUtenteEvento(eb.getUtenteEvento());
		//                eventoBean.setEventoProcesso(processoEvento);
		//
		//                if ("S".equalsIgnoreCase(processoEvento.getFlgAllMail())) {
		//                    if (!eb.getAllegati().isEmpty()) {
		//                        eventoBean.setAllegati(eb.getAllegati());
		//                    } else {
		//                        for (EventiTemplate eventoTemplate : processoEvento.getEventiTemplateList()) {
		//                            byte[] allegatoContent = this.generaDocumento(eventoTemplate, eb.getPratica(), eb.getUtenteEvento());
		//                            Allegato allegato = new Allegato();
		//                            allegato.setDescrizione(eventoTemplate.getIdTemplate().getDescrizione());
		//                            allegato.setFile(allegatoContent);
		//                            allegato.setFileOrigine(eb.getAllegati().isEmpty());
		//                            allegato.setNomeFile(eventoTemplate.getIdTemplate().getNomeFile());
		//                            allegato.setProtocolla(Boolean.TRUE);
		//                            allegato.setSpedisci(Boolean.TRUE);
		//                            allegato.setTipoFile(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		//                            eventoBean.addAllegato(allegato);
		//                        }
		//                    }
		//                }
		//
		//                gestisciProcessoEvento(eventoBean);
		//
		//                eb.getMessages().putAll(eventoBean.getMessages());
		//            }
		//        }
	}

	@Override
	public AttoriComunicazione getDestinatariDefaultEvento(Pratica pratica, ProcessiEventi eventoProcesso, Enti enteSelezionato) throws Exception {
		AttoriComunicazione destinatari = new AttoriComunicazione();

		if (eventoProcesso.getIdTipoDestinatario() == null) {
			return destinatari;
		}
		List<Anagrafica> anagrafiche = new ArrayList<Anagrafica>();
		if (eventoProcesso.getProcessiEventiAnagraficaList() != null && !eventoProcesso.getProcessiEventiAnagraficaList().isEmpty()) {
			for (ProcessiEventiAnagrafica pea : eventoProcesso.getProcessiEventiAnagraficaList()) {
				pea.getAnagrafica().setAnagraficaDaEnte(true);
				anagrafiche.add(pea.getAnagrafica());
			}
		}
		if (eventoProcesso.getProcessiEventiEntiList() != null && !eventoProcesso.getProcessiEventiEntiList().isEmpty()) {
			for (ProcessiEventiEnti pee : eventoProcesso.getProcessiEventiEntiList()) {
				pee.getEnti().setEnteDaEvento(true);
				destinatari.addEnte(pee.getEnti().getIdEnte());
			}
		}
		String idTipoAttore = eventoProcesso.getIdTipoDestinatario().getIdTipoAttore();

		if (idTipoAttore.equalsIgnoreCase(AnaTipiDestinatario.UTENTE)
				|| idTipoAttore.equalsIgnoreCase(AnaTipiDestinatario.AZIENDA)
				|| idTipoAttore.equalsIgnoreCase(AnaTipiDestinatario.AZIENDA_UTENTE)) {
			//Ho l'indirizzo di notifica della pratica
			if (pratica.getIdRecapito() != null) {
				destinatari.setIdRecapitoNotifica(pratica.getIdRecapito().getIdRecapito());
			} else {
				if (idTipoAttore.equalsIgnoreCase(AnaTipiDestinatario.UTENTE)) {
					praticaDao.getAnagrafichePratica(pratica, AnaTipiDestinatario.UTENTE, anagrafiche);
				}
				if (idTipoAttore.equalsIgnoreCase(AnaTipiDestinatario.AZIENDA)) {
					praticaDao.getAnagrafichePratica(pratica, AnaTipiDestinatario.AZIENDA, anagrafiche);
				}
				if (idTipoAttore.equalsIgnoreCase(AnaTipiDestinatario.AZIENDA_UTENTE)) {
					praticaDao.getAnagrafichePratica(pratica, AnaTipiDestinatario.AZIENDA_UTENTE, anagrafiche);
				}
			}
		}
		if (idTipoAttore.equalsIgnoreCase(AnaTipiDestinatario.ENTE)) {
			if (enteSelezionato != null) {
				destinatari.addEnte(enteSelezionato.getIdEnte());
			} else {
				//SE E LA PRATICA DI PRIMO LIVELLO => ALLORA L'UTENTE E UNO SPORTELLO
				if (pratica.getIdPraticaPadre() == null) {
					for (PraticaProcedimenti praticaProcedimento : pratica.getPraticaProcedimentiList()) {
						destinatari.addEnte(praticaProcedimento.getEnti().getIdEnte());
					}
				}
			}
		}

		if (idTipoAttore.equalsIgnoreCase(AnaTipiDestinatario.MITTENTE)) {
			if (pratica.getIdPraticaPadre() != null) {
				destinatari.addEnte(pratica.getIdPraticaPadre().getIdProcEnte().getIdEnte().getIdEnte());
			} else {
				//Priorità all'indirizzo di notifica
				if (pratica.getIdRecapito() != null) {
					destinatari.setIdRecapitoNotifica(pratica.getIdRecapito().getIdRecapito());
				} else {
					praticaDao.getAnagrafichePratica(pratica, AnaTipiDestinatario.AZIENDA_UTENTE, anagrafiche);
				}
			}
		}
		List<AnagraficaRecapiti> anagraficaRecapiti = findAnagraficheRecapiti(anagrafiche);
		if (anagraficaRecapiti != null && !anagraficaRecapiti.isEmpty()) {
			for (AnagraficaRecapiti recapito : anagraficaRecapiti) {
				destinatari.addAnagrafica(recapito);
			}
		}
		return destinatari;
	}

	@Override
	public byte[] generaDocumento(it.wego.cross.xml.Pratica praticaCross, EventiTemplate et) throws Exception {
		Templates template = et.getIdTemplate();
		if (template == null) {
			return null;
		}
		byte[] templateByteArray = template.getTemplate().getBytes();

		String xmlStaticData = Utils.marshall(praticaCross);
		//        xmlStaticData = xmlStaticData.replaceAll("&amp;", "&amp;amp;");
		xmlStaticData = xmlStaticData.replaceAll("xmlns=\"http://www.wego.it/cross\"", "");

		//Formato di default PDF
		byte[] documentoGenerato = docEngineService.createDocument(xmlStaticData, templateByteArray, !Utils.e(template.getFileOutput()) ? template.getFileOutput() : "PDF");
		return documentoGenerato;
	}

	@Override
	public EventiTemplate findEventiTemplateById(Integer idEventoTemplate) {
		EventiTemplate evento = processiDao.findEventiTemplateById(idEventoTemplate);
		return evento;
	}

	@Override
	public byte[] generaDocumento(EventiTemplate eventoTemplate, Pratica pratica, Utente utente) throws Exception {
		it.wego.cross.xml.Pratica praticaXml = praticheSerializer.serialize(pratica, null, utente);
		//Serializzo l'evento corrente
		it.wego.cross.xml.Evento eventoXml = new it.wego.cross.xml.Evento();
		eventoXml.setIdEvento(Utils.bi(eventoTemplate.getIdEvento().getIdEvento()));
		eventoXml.setDescrizioneEvento(eventoTemplate.getIdEvento().getDesEvento());
		if (utente != null) {
			eventoXml.setIdUtente(Utils.bi(utente.getIdUtente()));
			eventoXml.setCognome(utente.getCognome());
			eventoXml.setNome(utente.getNome());
		}
		eventoXml.setDataEvento(Utils.dateToXmlGregorianCalendar(new Date()));
		eventoXml.setVerso(String.valueOf(eventoTemplate.getIdEvento().getVerso()));
		praticaXml.setEventoCorrente(eventoXml);

		Templates template = eventoTemplate.getIdTemplate();
		if (template == null) {
			return null;
		}
		byte[] templateByteArray = template.getTemplate().getBytes();

		String xmlStaticData = Utils.marshall(praticaXml);
		//        xmlStaticData = xmlStaticData.replaceAll("&amp;", "&amp;amp;");
		xmlStaticData = xmlStaticData.replaceAll("xmlns=\"http://www.wego.it/cross\"", "");

		byte[] documentoGenerato = docEngineService.createDocument(xmlStaticData, templateByteArray, template.getFileOutput());
		return documentoGenerato;
	}

	@Override
	public MailContentBean getMailContent(it.wego.cross.xml.Pratica praticaCross, ProcessiEventi pe) throws Exception {
		return generaMail(praticaCross, pe);
	}

	@Override
	public Templates generaTemplate(Integer id) throws Exception {
		Templates template = templateDao.getTemplatesPerID(id);
		return template;
	}

	@Override
	public ProcessiEventi findProcessiEventiById(Integer idEvento) {
		ProcessiEventi pe = praticaDao.findByIdEvento(idEvento);
		return pe;
	}

	@Override
	public ProcessiEventi findProcessiEventiByIdProcessoCodEvento(Processi idProcesso, String codEvento) {
		ProcessiEventi processoEvento = processiDao.findProcessiEventiByCodEventoIdProcesso(codEvento, idProcesso.getIdProcesso());
		return processoEvento;
	}

	@Override
	public byte[] generaDocumento(Integer idEventoTemplate, Integer idPratica, Utente utente) throws Exception {
		EventiTemplate eventoTemplate = findEventiTemplateById(idEventoTemplate);
		Pratica pratica = praticheService.getPratica(idPratica);
		byte[] documentBody = generaDocumento(eventoTemplate, pratica, utente);
		return documentBody;
	}

	@Override
	public boolean isInManualProtocolStatus(Task task) {
		return task.getTaskDefinitionKey().equals(Workflow.PROTOCOLLO_TASK_PROTOCOLLO_MANUALE);
	}

	@Override
	public Map<String, Object> getVariablesForProtocolTask(Email email) {
		Map<String, Object> variables = new HashMap<String, Object>();
		PraticheEventi evento = email.getIdPraticaEvento();
		if (Strings.isNullOrEmpty(evento.getProtocollo())) {
			variables.put("evento_protocollo_riprova", true);
			variables.put("evento_protocollo_segnatura", "");
			variables.put("evento_protocollo_data", "");
		} else {
			variables.put("evento_protocollo_riprova", false);
			variables.put("evento_protocollo_segnatura", evento.getProtocollo());
			variables.put("evento_protocollo_data", evento.getDataProtocollo() != null ? Utils.dateItalianFormat(evento.getDataProtocollo()) : null);
		}
		return variables;
	}

	//<editor-fold defaultstate="collapsed" desc="METODI PRIVATI">
	private String getContenutoDaTemplate(Document praticaXmlDocumentNoNamespace, byte[] template) throws IOException, TemplateException {
		//        Base64 decoder = new Base64();
		//        byte[] templateByteArray = decoder.decode(template);
		String decodedTemplate = new String(Base64.decodeBase64(new String(template)), "UTF-8");
		freemarker.template.Configuration freeMarkerConfig = new freemarker.template.Configuration();
		freeMarkerConfig.setSharedVariable("addDays", new DateDirective());
		InputStream freeMarkerInputStream = new ByteArrayInputStream(decodedTemplate.getBytes("UTF-8"));
		try {
			Template freeMarkerTemplate = new Template("mailTemplate", new InputStreamReader(freeMarkerInputStream, Charset.forName("UTF-8")), freeMarkerConfig);
			ByteArrayOutputStream freeMarkerOutputStream = new ByteArrayOutputStream();
			freeMarkerTemplate.process(praticaXmlDocumentNoNamespace, new OutputStreamWriter(freeMarkerOutputStream, Charset.forName("UTF-8")));
			String content = freeMarkerOutputStream.toString("UTF-8");
			return content;
		} catch (Exception e) {
			String errorMessage = "Si è verificato un errore eseguendo il parsing del template in freemarker.";
			Log.APP.error(errorMessage, e);
			return errorMessage + "\n" + e.getMessage() + "\n" + decodedTemplate + "#ERRORE#";
		}

	}
	//MODIFICA DI GIUSEPPE

	@Override
	public MailContentBean getMailContent(Pratica pratica, ProcessiEventi eventoProcesso) throws Exception {
		it.wego.cross.xml.Pratica praticaXml = praticheSerializer.serialize(pratica, null, null);
		return generaMail(praticaXml, eventoProcesso);
	}

	@Override
	public MailContentBean getMailContent(Pratica pratica, PraticheEventi praticaEvento) throws Exception {
		it.wego.cross.xml.Pratica praticaXml = praticheSerializer.serialize(pratica, praticaEvento, null);
		return generaMail(praticaXml, praticaEvento.getIdEvento());
	}

	private MailContentBean generaMail(it.wego.cross.xml.Pratica praticaXml, ProcessiEventi eventoProcesso) throws Exception {
		Document praticaXmlDocument = Utils.marshallToDocument(praticaXml);
		Document praticaXmlDocumentNoNamespace = Utils.removeNamespacesFromDocument(praticaXmlDocument);
		MailContentBean bean = new MailContentBean();
		if (Log.APP.isTraceEnabled()) {
			FileUtils.writeStringToFile(Utils.getTempFile(".xml"), Utils.documentToString(praticaXmlDocumentNoNamespace));
		}

		if (eventoProcesso.getCorpoEmail() != null) {
			byte[] templateCorpoEmail = eventoProcesso.getCorpoEmail().getBytes("UTF-8");
			if (templateCorpoEmail != null) {
				String corpoEmail = getContenutoDaTemplate(praticaXmlDocumentNoNamespace, templateCorpoEmail);
				bean.setContenuto(corpoEmail);
			} else {
				bean.setContenuto("");
			}
		}
		if (eventoProcesso.getOggettoEmail() != null) {
			byte[] templateOggettoEmail = eventoProcesso.getOggettoEmail().getBytes("UTF-8");
			if (templateOggettoEmail != null) {
				String oggetto = getContenutoDaTemplate(praticaXmlDocumentNoNamespace, templateOggettoEmail);
				bean.setOggetto(oggetto);
			} else {
				bean.setOggetto("");
			}
		}
		return bean;
	}

	private Allegati getNuovoAllegato(Allegato allegato) {
		Allegati a = new Allegati();
		a.setDescrizione(allegato.getDescrizione());
		a.setFile(allegato.getFile());
		a.setNomeFile(allegato.getNomeFile());
		a.setIdFileEsterno(allegato.getIdEsterno());
		a.setTipoFile(allegato.getTipoFile());
		return a;
	}

	private List<ProcessiEventi> eliminaDuplicati(List<ProcessiEventi> processiEventiResultList) {
		List<ProcessiEventi> result = new ArrayList<ProcessiEventi>();
		for (ProcessiEventi evento : processiEventiResultList) {
			if (!result.contains(evento)) {
				result.add(evento);
			}
		}
		Collections.sort(result, new ProcessiEventiComparator());
		return result;
	}

	private List<AnagraficaRecapiti> findAnagraficheRecapiti(List<Anagrafica> anagrafiche) throws Exception {
		List<AnagraficaRecapiti> anagraficaRecapiti = new ArrayList<AnagraficaRecapiti>();
		if (anagrafiche != null && !anagrafiche.isEmpty()) {
			for (Anagrafica a : anagrafiche) {
				AnagraficaRecapiti ar = anagraficheService.getAnagraficaRecapitoRiferimentoAnagrafica(a);
				anagraficaRecapiti.add(ar);
			}
		}
		return anagraficaRecapiti;
	}
	//
	//</editor-fold>

	@Override
	public void aggiornaUtentePraticaPerChiusura(Pratica pratica, PraticheEventi praticaEvento) {
		ProcessiEventi processoEvento = praticheService.findProcessiEventi(praticaEvento.getIdEvento().getIdEvento());
		String funzioneApplicativaEvento = processoEvento.getFunzioneApplicativa();
		
		AbstractEvent eventoRepo;
		if (funzioneApplicativaEvento == null) {
			eventoRepo = (AbstractEvent) appContext.getBean("nop_event");
		} else {
			try {
				eventoRepo = (AbstractEvent) appContext.getBean(funzioneApplicativaEvento);
			} catch (BeansException e) {
				Log.APP.warn("ATTENZIONE NON HO TROVATO LA FUNZIONE APPLICATIVA '" + funzioneApplicativaEvento + "' USO QUELLA DI DEFAULT");
				eventoRepo = (AbstractEvent) appContext.getBean("nop_event");
			}
		}
		eventoRepo.aggiornaUtentePraticaPerChiusura(pratica, praticaEvento);
		
	}
	
	@Override
	public void aggiornaUtentePraticaIntegrazione(Pratica pratica, EventoBean eb) throws Exception {
		ProcessiEventi processoEvento = praticheService.findProcessiEventi(eb.getIdEventoProcesso());
		String funzioneApplicativaEvento = processoEvento.getFunzioneApplicativa();
		if (eb.getDataEvento() == null) {
			eb.setDataEvento(Utils.now());
		}

		AbstractEvent eventoRepo;
		if (funzioneApplicativaEvento == null) {
			eventoRepo = (AbstractEvent) appContext.getBean("nop_event");
		} else {
			try {
				eventoRepo = (AbstractEvent) appContext.getBean(funzioneApplicativaEvento);
			} catch (BeansException e) {
				Log.APP.warn("ATTENZIONE NON HO TROVATO LA FUNZIONE APPLICATIVA '" + funzioneApplicativaEvento + "' USO QUELLA DI DEFAULT");
				eventoRepo = (AbstractEvent) appContext.getBean("nop_event");
			}
		}
		pratica = eventoRepo.aggiornamentoUtentePratica(eb);
		
	}
}
