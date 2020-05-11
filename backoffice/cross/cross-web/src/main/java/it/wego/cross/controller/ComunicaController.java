/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.eclipse.persistence.internal.xr.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.InputSource;

import com.google.common.base.Strings;

import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.wego.cross.actions.ComunicaAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.actions.RisultatoCaricamentoPraticheAction;
import it.wego.cross.beans.grid.GridRisultatoCaricamentoPraticaBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.Error;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.constants.StatoPratica;
import it.wego.cross.dao.AllegatiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dao.TemplateDao;
import it.wego.cross.dao.UsefulDao;
import it.wego.cross.dto.AllegatoRicezioneDTO;
import it.wego.cross.dto.ComboDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.comunica.ComuneRiferimentoDTO;
import it.wego.cross.dto.comunica.PraticaComunicaDTO;
import it.wego.cross.dto.comunica.SportelloDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkFormeGiuridiche;
import it.wego.cross.entity.LkNazionalita;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.plugins.aec.AeCGestionePratica;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.serializer.PraticaComunicaSerializer;
import it.wego.cross.service.ComuniService;
import it.wego.cross.service.ComunicaService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.PraticaUtils;
import it.wego.cross.utils.Utils;
import it.wego.cross.xml.Allegato;
import it.wego.cross.xml.Eventi;

import it.wego.cross.service.UtentiService;

/**
 *
 * @author giuseppe
 */
@Controller
public class ComunicaController extends AbstractController {

    private static final String AJAX_PAGE = "ajax";
    private static final String INDEX = "comunica_index";
    private static final String RESULT_ERRORI = "comunica_riepilogo_error";
    private static final String STEP1 = "comunica_step1";
    private static final String APERTURA_PRATICHE = "redirect:/pratiche/nuove/apertura.htm";
    private static final String ESITO_POSITIVO = "Positivo";
    private static final String ESITO_NEGATIVO = "Negativo";
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private EntiService entiService;
    @Autowired
    protected Validator validator;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private ComunicaAction comunicaAction;
    @Autowired
    private ComunicaService comunicaService;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private WorkFlowService workflowService;
    
    @Autowired
    private ProcessiDao processiDao;
    @Autowired
    private PraticheAction praticheAction;
    
    @Autowired
    private LookupDao lookupDao;
    
    @Autowired
    private ProcedimentiDao procedimentiDao;
    
    @Autowired
    protected PraticaDao praticaDao;
    @Autowired
    protected UsefulDao usefulDao;
    @Autowired
    private TemplateDao templateDao;
    
    @Autowired
    private AllegatiDao allegatiDao;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private RisultatoCaricamentoPraticheAction risultatoCaricamentoPraticheAction;
    //private static long maxsize = 2097152;
    private static long maxsize = 209715200;

    @RequestMapping("/comunica/index")
    public String nuove(Model model, HttpServletRequest request, HttpServletResponse response) {
        return INDEX;
    }

    @RequestMapping("/comunica/upload")
    public String upload(Model model, @RequestParam("filePratica") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            List<LkProvincie> provincie = comuniService.findAllProvincieAttive(new Date());
            List<LkFormeGiuridiche> formeGiuridiche = lookupService.findAllLkFormeGiuridiche();
            List<LkNazionalita> nazionalita = lookupService.findAllLkNazionalita();
            List<Enti> enti = entiService.findAll(new Filter());
            List<ComboDTO> provincieDto = comunicaService.serializeProvincie(provincie);
            List<ComboDTO> formeGiuridicheDto = comunicaService.serializeFormeGiuridiche(formeGiuridiche);
            List<ComboDTO> nazionalitaDto = comunicaService.serializeNazionalita(nazionalita);
            List<ComboDTO> entiDto = comunicaService.serializeEnti(enti);
            String xmlPratica = new String(file.getBytes());
            JAXBContext context = JAXBContext.newInstance(RiepilogoPraticaSUAP.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader reader = new StringReader(xmlPratica);
            RiepilogoPraticaSUAP riepilogoPraticaSuap = (RiepilogoPraticaSUAP) unmarshaller.unmarshal(reader);
            PraticaComunicaDTO praticaComunicaDto = PraticaComunicaSerializer.serialize(riepilogoPraticaSuap);
            ComuneRiferimentoDTO comuneRiferimento = comunicaService.getComuneRiferimento(riepilogoPraticaSuap);
            praticaComunicaDto.setComuneRiferimento(comuneRiferimento);
            SportelloDTO sportelloDestinazione = comunicaService.getSportelloDestinazione(riepilogoPraticaSuap);
            praticaComunicaDto.setSportello(sportelloDestinazione);
            Filter filter = new Filter();
            filter.setIdEnte(sportelloDestinazione.getId());
            filter.setStatoProcedimento("abilitato");
            List<ProcedimentiLocalizzatiView> procedimentiSportello = procedimentiService.getProcedimentiLocalizzati("it", new Filter());
            List<ComboDTO> procedimentiDto = comunicaService.serializeProcedimenti(procedimentiSportello);
            model.addAttribute("comunica", praticaComunicaDto);
            model.addAttribute("provincie", provincieDto);
            model.addAttribute("nazionalita", nazionalitaDto);
            model.addAttribute("formaGiuridica", formeGiuridicheDto);
            model.addAttribute("enti", entiDto);
            model.addAttribute("procedimentiSuap", procedimentiDto);
            return STEP1;
        } catch (JAXBException ex) {
            Log.APP.error("Errore cercando di generare l'oggetto dall'XML", ex);
            String msgError = messageSource.getMessage("error.comunica.fileerrato", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", error);
            ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_UPLOAD, "Errore nell'esecuzione del controller /comunica/upload", ex, null, utente);
            erroriAction.saveError(erroredto);
            return INDEX;
        } catch (IOException ex) {
            Log.APP.error("Si è verificato un errore cercando di recuperare l'XML della pratica", ex);
            String msgError = messageSource.getMessage("error.comunica.fileerrato", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", error);
            ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_UPLOAD, "Errore nell'esecuzione del controller /comunica/upload", ex, null, utente);
            erroriAction.saveError(erroredto);
            return INDEX;
        } catch (Exception ex) {
            Log.APP.error("Errore cercando di generare l'oggetto dall'XML", ex);
            Message error = new Message();
            error.setMessages(Arrays.asList("Si è verificato un errore nell'upload del file. Il file potrebbe essere errato o incompleto. L'errore è il seguente: " + ex.getMessage()));
            request.setAttribute("message", error);
            ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_UPLOAD, "Errore nell'esecuzione del controller /comunica/upload", ex, null, utente);
            erroriAction.saveError(erroredto);
            return INDEX;
        }
    }

    @RequestMapping("/comunica/uploadSue")
	public String uploadSue(Model model, @RequestParam("filePratica") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Utente utente = utentiService.getUtenteConnesso(request);
		String identificativoPratica = "";
		String pathZipCaricato = "";
		List<String> identificativiPraticaXml = new ArrayList<String>();
		String nomeFileCaricato = "";
		boolean esitoPositivo = true;
		try {
			// JAXBContext context = JAXBContext.newInstance(RiepilogoPraticaSUAP.class);
			/*
			 * 26/08/2019 inserito metodo per unzippare la cartella con i file xml da
			 * inseire sotto attachments
			 */
			if (!file.getOriginalFilename().endsWith(".zip")) {
				Log.WS.error("Vengono accettati solo file zip!");
				throw new Exception("Vengono accettati solo file zip!");
			}
			if (file.getSize() > this.maxsize) {
				throw new Exception("Superato limite massimo consentito per un file (2 mb)!");
			}
			nomeFileCaricato = file.getOriginalFilename();
			File fileConv = Utils.convert(file);
			List<Configuration> attachmentsFolderConfig = configurationService
					.findByName(SessionConstants.ATTACHMENTS_FOLDER);
			Configuration config = attachmentsFolderConfig.get(0);
			String attachmentsFolder = config.getValue();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String identificativoFolder = timestamp.getTime() + "";
			pathZipCaricato = attachmentsFolder + "/unzip" + identificativoFolder;
			Utils.unzip(fileConv.getAbsolutePath(), pathZipCaricato);
			/*
			 * 26/08/2019 vengono letti i file xml presenti sotto la cartella unzip e
			 * trasformati in bytearray per creare la pratica e le tabelle annesse dopo
			 * averli unzippati sarebbe utile metterli in una cartella separata per evitare
			 * di rileggerli ogni volta
			 */
			File[] folder = Utils.getFolderFile(pathZipCaricato);
			//File[] xmlFiles = Utils.getFileXml(folder);
			boolean xmlTrovato = false;
					//if(folder.length > xmlFiles.length) {
						
					//}
			for(File dir : folder) {
				Pratica pratica = null;
				try {
					File[] xmlFiles = Utils.getFileXml(folder);
		    		//File dir = new File(folder[i].getCanonicalPath());
		    		xmlTrovato = Utils.searchXml(xmlFiles, dir.getName());
	    		if(!xmlTrovato) {
	    			identificativoPratica = dir.getName();
	    			throw new IOException("L'xml della pratica non esiste!");
	    		}else {
						
					//for (File fileXml : xmlFiles) {
	    			
						File fileXml = Utils.getFileXmlTrovato(xmlFiles, dir.getName());
						
						
		
							byte[] bytesArray = Utils.readByteArrayFromFile(fileXml);
							String xmlPratica = new String(bytesArray);
							identificativoPratica = fileXml.getName();
							
							JAXBContext context = JAXBContext.newInstance(it.wego.cross.xml.Pratica.class);
							Unmarshaller unmarshaller = context.createUnmarshaller();
							StringReader reader = new StringReader(xmlPratica);
							it.wego.cross.xml.Pratica praticaCross = (it.wego.cross.xml.Pratica) unmarshaller.unmarshal(reader);
							// it.wego.cross.xml.Pratica praticaCross = getPratica(gp, xmlPratica);
							identificativoPratica = praticaCross.getIdentificativoPratica();
							identificativiPraticaXml.add(identificativoPratica);
		
							if (praticaCross == null) {
								Log.WS.error(
										"Non � stato possibile generare la pratica a partire dall'XML ricevuto il cui nome e': "
												+ file.getOriginalFilename());
								throw new IOException("Non � stato possibile generare la pratica");
							}
							XPath xpath = XPathFactory.newInstance().newXPath();
							InputSource source = new InputSource(new StringReader(xmlPratica));
							String codiceNodo = xpath.evaluate(AeCGestionePratica.XPATH_IDENTIFICATIVO_NODO, source);
		
							// String codEnte = xpath.evaluate(AeCGestionePratica.XPATH_ID_ENTE, source);
							String codEnte = "2001";
							// controlla che esiste già una pratica con identificativo pratica uguale
							Enti ente = entiService.findByCodEnte(codEnte);
		
							GestionePratica gp = pluginService.getGestionePratica(ente.getIdEnte());
		
							 Pratica praticaEsistente = praticheService.getPraticaByIdentificativo(identificativoPratica);
							
							  if (praticaEsistente != null) { 
								  //Pratica già inbviata: traccio l'evento ma ritorno success all'utente 
								  Log.WS.warn("La pratica � gi� presente in CROSS, la ignoro silenziosamente...");
								  ErroreDTO errore = erroriAction.getError(Error.ERRORE_PARSING_PRATICA,
								  "La pratica '" + identificativoPratica + "' inviata è già presente in CROSS",
								  null, praticaEsistente, null); 
								  erroriAction.saveError(errore); 
								  //xmlDump.delete();
								  
								  throw new IOException("La pratica � gi� presente in CROSS"); 
							  }
							  
							/*if(praticaCross.getCodStatoPratica()==null || praticaCross.getCodStatoPratica().isEmpty())
									throw new IOException("Lo stato della pratica è null!");
							*/
							  /*
							 * String oggetto = gp.getOggettoPratica(xmlPratica);
							 * Log.WS.info("Oggetto pratica: " + oggetto);
							 */
							String oggetto = praticaCross.getOggetto();
							Procedimenti procedimentoSuap = procedimentiService
									.getProcedimento(praticaCross.getIdProcedimentoSuap());
							/*
							 * 28/08/2019 aggiunto attributo desprocedimentosuap perchè presente negli xml
							 * forniti e corrispondente all'idprocedimentosuap
							 */
							if (procedimentoSuap == null) {
								procedimentoSuap = procedimentiService.getProcedimento(praticaCross.getDesProcedimentoSuap());
							}
							Log.WS.info("Procedimento SUAP: " + procedimentoSuap.getCodProc());
							Enti enteSuap = entiService.findByCodEnte(praticaCross.getCodEnte());
							Log.WS.info("Ente SUAP: " + enteSuap.getDescrizione());
							Processi processo = getIdProcesso(enteSuap, procedimentoSuap);
							Log.WS.info("ID processo: " + processo.getIdProcesso() + " " + processo.getDesProcesso());
							ProcessiEventi eventoProcesso = processiDao.findProcessiEventiByCodEventoIdProcesso(
									AnaTipiEvento.RICEZIONE_PRATICA, processo.getIdProcesso());
		
							// ProcessiEventi eventoProcesso =
							// processiDao.findProcessiEventiByCodEventoIdProcesso("ASPRAT",
							// processo.getIdProcesso());
		
							// 3. Recupero gli allegati
		
							File[] fileAllegati = Utils.getFileList(identificativoPratica,
									attachmentsFolder + "/unzip" + identificativoFolder);
							// File destFile = new File(dirDest);
							it.wego.cross.xml.Allegati allegatiXml = praticaCross.getAllegati();
							
							List<AllegatoRicezioneDTO> allegati  = new ArrayList<AllegatoRicezioneDTO>();
							if (allegatiXml != null) {
								List<Allegato> allegatiArray = allegatiXml.getAllegato();
								if (fileAllegati != null) {
									for (Allegato allegatoxml : allegatiArray) {
										byte[] documentBody = null;
										for (File fileSys : fileAllegati) {
											if (fileSys.getName().equals(allegatoxml.getNomeFile())) {
												documentBody = Utils.fileToByteArray(fileSys);
		
												AllegatoRicezioneDTO allegato = new AllegatoRicezioneDTO();
												Allegati a = new Allegati();
												a.setDescrizione(allegatoxml.getDescrizione());
												a.setNomeFile(allegatoxml.getNomeFile());
												a.setFile(documentBody);
												a.setTipoFile(allegatoxml.getTipoFile());
		
												allegato.setAllegato(a);
												allegato.setModelloDomanda(allegatoxml.getRiepilogoPratica());
												// Lo imposto come da inviare, in quanto è una ricevuta dell'evento
		
												allegato.setSend(Boolean.TRUE);
		
												allegati.add(allegato);
												// allegatiDao.insertAllegato(a);
											}
										}
		
									}
								} else {
									Log.WS.info("Non sono presenti gli allegati per la pratica : " + identificativoPratica);
								}
		
							}
							//22.11.2019 inserita gestione allegati fuori xml da eliminare per primo test
							if(allegati.isEmpty()) {
								allegati = praticheAction.gestioneAllegatiFuoriXml(praticaCross, dir.getCanonicalPath());
							}
							
		
							// 4. Salvo l'xml nell'area di staging
							Log.WS.info("Preparo i dati per salvare l'area di staging");
							Log.WS.info("Data ricezione " + praticaCross.getDataRicezione()
									+ " della pratica con identificativo " + praticaCross.getIdentificativoPratica());
		
							// Date dataRicezione =
							// Utils.xmlGregorianCalendarToDate(praticaCross.getDataRicezione());
//							String dataRicezioneString = praticaCross.getDataRicezione();
							XMLGregorianCalendar dataRicezioneGref = praticaCross.getDataRicezione();
							Date dataRicezione = null;
							if(dataRicezioneGref !=null)
								dataRicezione = dataRicezioneGref.toGregorianCalendar().getTime();
							//Date dataRicezione = Utils.getFormattedDateString(dataRicezioneString);
							if (dataRicezione == null) {
								dataRicezione = new Date();
							}
							String xmlPraticaCross = PraticaUtils.getXmlFromPratica(praticaCross);
							Log.WS.info("Dump XML pratica");
							// Log.WS.info(xmlPraticaCross);
							Staging staging = new Staging();
							staging.setIdentificativoProvenienza(codiceNodo);
							staging.setOggetto(oggetto);
							Log.WS.info("Data ricezione: " + dataRicezione);
							staging.setDataRicezione(dataRicezione);
							Log.WS.info("Tipo messaggio: " + Constants.WEBSERVICE_AEC);
							staging.setTipoMessaggio(Constants.WEBSERVICE_AEC);
							staging.setXmlRicevuto(xmlPratica.getBytes());
							staging.setXmlPratica(xmlPraticaCross.getBytes());
							staging.setIdEnte(enteSuap);
							praticheAction.salvaStaging(staging);
							// Cancello il dump dell'XML
							// xmlDump.delete();
		
							// 5. Creo la pratica
							LkComuni comune = getComune(praticaCross.getCodCatastaleComune());
							Log.WS.info("Preparo i dati per il salvataggio della pratica");
							pratica = new Pratica();
							
							Log.WS.info("Identificativo pratica: " + praticaCross.getIdentificativoPratica());
							pratica.setIdentificativoPratica(praticaCross.getIdentificativoPratica());
							Log.WS.info("Protocollo: " + praticaCross.getProtocollo());
							pratica.setProtocollo(praticaCross.getProtocollo());
							Log.WS.info("Oggetto: " + oggetto);
							oggetto = Utils.decodingAccentati(oggetto);
							
							pratica.setOggettoPratica(oggetto);
							Log.WS.info("Responsabile del procedimento: " + praticaCross.getResponsabileProcedimento());
							pratica.setResponsabileProcedimento(praticaCross.getResponsabileProcedimento());
							Log.WS.info("Data ricezione: " + dataRicezione);
							pratica.setDataRicezione(dataRicezione);
							Log.WS.info("Id staging: " + staging.getIdStaging());
							pratica.setIdStaging(staging);
							Log.WS.info("Id Comune: " + comune.getIdComune());
							
							pratica.setIdComune(comune);
							LkStatoPratica statoPratica = null;
							if(praticaCross.getCodStatoPratica()!=null && !praticaCross.getCodStatoPratica().isEmpty()) {
								Log.WS.info("Stato pratica: " + praticaCross.getdesStatoPratica());
								statoPratica = lookupDao.findStatoPraticaByCodice(praticaCross.getCodStatoPratica());
								
							}else {
								statoPratica = lookupDao.findStatoPraticaByCodice(StatoPratica.CHIUSA);
							}
							pratica.setIdStatoPratica(statoPratica);
							Utente idUtente = new Utente(250);
		
							pratica.setIdUtente(idUtente);
							
							Log.WS.info("ID ente SUAP: " + enteSuap.getIdEnte());
							Log.WS.info("ID procedimento: " + procedimentoSuap.getIdProc());
							ProcedimentiEnti pe = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(enteSuap.getIdEnte(),
									procedimentoSuap.getIdProc());
							Log.WS.info("Se responsabile vuoto metto quello che sta sul procedimenti ente");
							if (Strings.isNullOrEmpty(pratica.getResponsabileProcedimento())) {
								pratica.setResponsabileProcedimento(pe.getResponsabileProcedimento());
							}
							pratica.setIdProcEnte(pe);
							// pratica.setIdEnte(enteSuap);
							// pratica.setIdProc(procedimentoSuap);
							Log.WS.info("ID processo: " + processo.getIdProcesso());
							pratica.setIdProcesso(processo);
							Log.WS.info("Salvo la pratica");
							praticheAction.salvaPratica(pratica);
							Log.WS.info("Popolo i procedimenti");
							praticheAction.popolaProcedimentiUpload(pratica, praticaCross);
							// Dati catastali
							Log.WS.info("Popolo i dati catastali");
							praticheAction.popolaDatiCatastali(pratica, praticaCross);
							//Indirizzi Intervento
							praticheAction.popolaIndirizziIntervento(pratica,praticaCross);
							// 6. Inserisco le anagrafiche, per quanto possibile, collegate alla pratica
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
		
							// 7. Creo l'evento di ricezione
							Log.WS.info("Inserisco evento ricezione");
							Log.WS.info(
									"Poiché non ho ancora anagrafiche presenti in banca dati, forzo i destinatari alle anagrafiche presenti nell'XML della pratica");
							List<String> destinatari = getDestinatariEmail(praticaCross);
							Log.WS.info("Trovati " + destinatari.size() + " destinatari");
							Log.WS.info("Inserisco evento di ricezione");
							/*
							 * La pratica nell’xml potrebbe contenere eventi, se non li contiene verrà
							 * impostato un evento generico far in modo che venga creato l'evento senza
							 * protocollazione, per ora non lo fa
							 */
							PraticheEventi praticaEvento = null;
							ComunicazioneBean cb = null;
		
							Eventi eventi = praticaCross.getEventi();
							if (eventi != null) {
								if (eventi.getEvento() != null) {
									for (it.wego.cross.xml.Evento evento : eventi.getEvento()) {
										if (evento != null && evento.getIdEvento() != null)
											eventoProcesso = processiDao.findByIdEvento(evento.getIdEvento().intValue());
		
									}
									it.wego.cross.xml.Evento eventiCorrente = praticaCross.getEventoCorrente();
									if (eventiCorrente != null && eventiCorrente.getIdEvento() != null) {
										eventoProcesso = processiDao.findProcessiEventiByCodEventoIdProcesso(
												eventiCorrente.getDescrizioneEvento(), eventiCorrente.getIdEvento().intValue());
									}
								}
							}
							if (eventoProcesso != null) {
								cb = praticheAction.popolaEventoRicezione(praticaCross, pratica, eventoProcesso, destinatari,
										allegati);
								praticaEvento = praticheService.getPraticaEvento(cb.getIdEventoPratica());
								Log.WS.info("Operazione terminata correttamente");
		
							}
		
							// praticheAction.
							Log.WS.info("Elaborazione post creazione pratica");
							gp.postCreazionePratica(pratica, xmlPratica);
							Log.WS.info("Elaborazione post creazione pratica terminata");
							// praticheService.startCommunicationProcess(pratica, praticaEvento, cb);
							esitoPositivo = true;
							// praticheAction.assegnaUtenteAPratica(250, pratica.getIdPratica().intValue(),
							// utente);
		
							Log.WS.info("Inserisco evento ricezione");
							// gp.notifica(pratica, "Descrizione evento: ricezione pratica");
							Log.WS.info("Operazione terminata correttamente");
	    				}
					} catch (JAXBException ex) {
						esitoPositivo = false;
						String msgError = "Errore cercando di generare l'oggetto dall'XML: " + ex.getCause().getMessage();
						Log.APP.error("Errore cercando di generare l'oggetto dall'XML con pratica identificativo "
								+ identificativoPratica, ex);
						/*
						 * Message error = new Message(); error.setMessages(Arrays.asList(msgError));
						 * request.setAttribute("message", error);
						 */
						ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_UPLOAD,
								"Errore nell'esecuzione del controller /comunica/upload", ex, pratica, utente);
						erroriAction.saveError(erroredto);
						risultatoCaricamentoPraticheAction.popolaRisultatoCaricamentoPratiche(ESITO_NEGATIVO, msgError,
								pratica, utente, identificativoPratica, nomeFileCaricato);
						// return INDEX;
					} catch (IOException ex) {
						esitoPositivo = false;
						String msgError = "Errore cercando di inserire la pratica:\n " + ex.getMessage();
						Log.APP.error(
								"Si è verificato un errore cercando di recuperare l'XML della pratica con identificativo "
										+ identificativoPratica,
								ex);
						// String msgError = messageSource.getMessage("error.comunica.fileerrato", null,
						// Locale.getDefault());
						/*
						 * Message error = new Message(); error.setMessages(Arrays.asList(msgError));
						 * request.setAttribute("message", error);
						 */
						ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_UPLOAD,
								"Errore nell'esecuzione del controller /comunica/upload", ex, pratica, utente);
						erroriAction.saveError(erroredto);
						risultatoCaricamentoPraticheAction.popolaRisultatoCaricamentoPratiche(ESITO_NEGATIVO, msgError,
								pratica, utente, identificativoPratica, nomeFileCaricato);
						// return INDEX;
	
					} catch (XPathExpressionException ex) {
						esitoPositivo = false;
						String msgError = "Errore cercando di generare l'oggetto dall'XML\n " + ex.getMessage();
						Log.APP.error("Errore cercando di generare l'oggetto dall'XML", ex);
						/*
						 * Message error = new Message(); error.setMessages(Arrays.
						 * asList("Si è verificato un errore nell'upload del file. Il file potrebbe essere errato o incompleto. L'errore è il seguente: "
						 * + ex.getMessage())); request.setAttribute("message", error);
						 */
						ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_UPLOAD,
								"Errore nell'esecuzione del controller /comunica/upload con pratica con identificativo "
										+ identificativoPratica,
								ex, pratica, utente);
						erroriAction.saveError(erroredto);
						risultatoCaricamentoPraticheAction.popolaRisultatoCaricamentoPratiche(ESITO_NEGATIVO, msgError,
								pratica, utente, identificativoPratica, nomeFileCaricato);
						// return INDEX;
					} catch (ParseException ex) {
						esitoPositivo = false;
						String msgError = "Errore cercando di generare l'oggetto dall'XML:\n " + ex.getMessage();
						Log.APP.error("Errore cercando di generare l'oggetto dall'XML con pratica identificativo "
								+ identificativoPratica, ex);
						/*
						 * Message error = new Message(); error.setMessages(Arrays.asList(msgError));
						 * request.setAttribute("message", error);
						 */
						ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_UPLOAD,
								"Errore nell'esecuzione del controller /comunica/upload", ex, pratica, utente);
						erroriAction.saveError(erroredto);
						risultatoCaricamentoPraticheAction.popolaRisultatoCaricamentoPratiche(ESITO_NEGATIVO, msgError,
								pratica, utente, identificativoPratica, nomeFileCaricato);
						// return INDEX;
					}
					/* INSERISCO I DATI NELLA TABELLA RISULTATO CARICAMENTO PRATICHE */
					if (esitoPositivo) {
						risultatoCaricamentoPraticheAction.popolaRisultatoCaricamentoPratiche(ESITO_POSITIVO, "", pratica,
								utente, identificativoPratica, nomeFileCaricato);
	
					}
				
    		//}
			}
		} catch (Exception e) {
			Log.APP.error("Errore cercando di unzippare il file caricato in upload ", e);
			Message error = new Message();
			error.setMessages(Arrays.asList(
					"Si è verificato un errore nell'upload del file.\n Il file potrebbe essere errato o incompleto.\n L'errore è il seguente: "
							+ e.getMessage()));
			request.setAttribute("message", error);
			ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_UPLOAD,
					"Errore nell'esecuzione del controller /comunica/upload con pratica con identificativo "
							+ identificativoPratica,
					e, null, utente);
			erroriAction.saveError(erroredto);
			return INDEX;
			// praticheAction.popolaRisultatoCaricamentoPratiche(ESITO_NEGATIVO,msgError,
			// pratica, utente);
		}
		// FINE

		String dbColumnModel = IOUtils
				.toString(ConfigurationService.class.getResourceAsStream("gestione_caricamento_pratiche.js"));
		model.addAttribute("gestioneCaricamentoPraticheColumnModel", dbColumnModel);
		model.addAttribute("nomeFileCaricato", nomeFileCaricato);
		return RESULT_ERRORI;

	}
    
    @RequestMapping("/comunica/uploadSue/ajax")
    public String gestioneAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridRisultatoCaricamentoPraticaBean json = new GridRisultatoCaricamentoPraticaBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json = risultatoCaricamentoPraticheAction.getListaCaricamentoPratiche(request, paginator);
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", error);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI_AJAX, "Errore nell'esecuzione del controller /pratiche/gestisci/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }
    
    private LkComuni getComune(String codiceCatastale) {
        LkComuni comune = lookupDao.findComuneByCodCatastale(codiceCatastale);
        return comune;
    }

	private Processi getIdProcesso(Enti ente, Procedimenti procedimentoSuap) throws Exception {
        Processi processo = workflowService.getProcessToUse(ente.getIdEnte(), procedimentoSuap.getIdProc());
        return processo;
    }
    private it.wego.cross.xml.Pratica getPratica(GestionePratica gp, String data) throws Exception {
        try {
            it.wego.cross.xml.Pratica praticaCross = gp.execute(data);
            return praticaCross;
        } catch (Exception ex) {
            String err = "Errore interpretando la pratica ricevuta";
            ErroreDTO errore = erroriAction.getError(Error.ERRORE_PARSING_PRATICA, err, ex, null, null);
            erroriAction.saveError(errore);
            throw new Exception(ex);
        }
    }
    
    @RequestMapping("/comunica/salva")
    public String salva(@Valid PraticaComunicaDTO comunica, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        if (result.hasErrors()) {
            List<LkProvincie> provincie = comuniService.findAllProvincieAttive(new Date());
            List<LkFormeGiuridiche> formeGiuridiche = lookupService.findAllLkFormeGiuridiche();
            List<LkNazionalita> nazionalita = lookupService.findAllLkNazionalita();
            List<Enti> enti = entiService.findAll(new Filter());
            Filter filter = new Filter();
            filter.setIdEnte(comunica.getSportello().getId());
            filter.setStatoProcedimento("abilitato");
            List<ProcedimentiLocalizzatiView> procedimentiSportello = procedimentiService.getProcedimentiLocalizzati("it", new Filter());
            List<ComboDTO> procedimentiDto = comunicaService.serializeProcedimenti(procedimentiSportello);
            List<ComboDTO> provincieDto = comunicaService.serializeProvincie(provincie);
            List<ComboDTO> formeGiuridicheDto = comunicaService.serializeFormeGiuridiche(formeGiuridiche);
            List<ComboDTO> nazionalitaDto = comunicaService.serializeNazionalita(nazionalita);
            List<ComboDTO> entiDto = comunicaService.serializeEnti(enti);
            model.addAttribute("comunica", comunica);
            model.addAttribute("provincie", provincieDto);
            model.addAttribute("nazionalita", nazionalitaDto);
            model.addAttribute("formaGiuridica", formeGiuridicheDto);
            model.addAttribute("enti", entiDto);
            model.addAttribute("procedimentiSuap", procedimentiDto);
            List<FieldError> errors = result.getFieldErrors();
            Message error = new Message();
            List<String> errore = new ArrayList<String>();
            for (FieldError oe : errors) {
                String codiceErrore = result.getFieldErrors(oe.getField()).get(0).getDefaultMessage();
                String e = messageSource.getMessage(codiceErrore, null, Locale.getDefault());
                errore.add(e);
            }
            error.setMessages(errore);
            request.setAttribute("message", error);
            return STEP1;
        } else {
            try {
                //Salva in banca dati
                ErroreDTO erroreDto = comunicaAction.salvaPraticaComunica(comunica, utenteConnesso);
                List<String> messlist = new ArrayList<String>();
                if (erroreDto != null && erroreDto.getDescrizione() != null && !erroreDto.getDescrizione().equals("")) {
                    messlist.add(erroreDto.getDescrizione());
                }
                Message message = new Message(messlist);
                //Message message = salvaPraticaComunica(comunica, utenteConnesso);
                if (message.getMessages() != null && !message.getMessages().isEmpty()) {
                    List<LkProvincie> provincie = comuniService.findAllProvincieAttive(new Date());
                    List<LkFormeGiuridiche> formeGiuridiche = lookupService.findAllLkFormeGiuridiche();
                    List<LkNazionalita> nazionalita = lookupService.findAllLkNazionalita();
                    List<Enti> enti = entiService.findAll(new Filter());
                    Filter filter = new Filter();
                    filter.setIdEnte(comunica.getSportello().getId());
                    filter.setStatoProcedimento("abilitato");
                    List<ProcedimentiLocalizzatiView> procedimentiSportello = procedimentiService.getProcedimentiLocalizzati("it", new Filter());
                    List<ComboDTO> procedimentiDto = comunicaService.serializeProcedimenti(procedimentiSportello);
                    List<ComboDTO> provincieDto = comunicaService.serializeProvincie(provincie);
                    List<ComboDTO> formeGiuridicheDto = comunicaService.serializeFormeGiuridiche(formeGiuridiche);
                    List<ComboDTO> nazionalitaDto = comunicaService.serializeNazionalita(nazionalita);
                    List<ComboDTO> entiDto = comunicaService.serializeEnti(enti);
                    model.addAttribute("comunica", comunica);
                    model.addAttribute("provincie", provincieDto);
                    model.addAttribute("nazionalita", nazionalitaDto);
                    model.addAttribute("formaGiuridica", formeGiuridicheDto);
                    model.addAttribute("enti", entiDto);
                    model.addAttribute("procedimentiSuap", procedimentiDto);
                    request.setAttribute("message", message);
                    erroriAction.saveError(erroreDto);
                    return STEP1;
                } else {
                    return APERTURA_PRATICHE;
                }
            } catch (Exception ex) {
                Log.APP.error("Errore salvando la pratica Comunica in banca dati", ex);
                List<LkProvincie> provincie = comuniService.findAllProvincieAttive(new Date());
                List<LkFormeGiuridiche> formeGiuridiche = lookupService.findAllLkFormeGiuridiche();
                List<LkNazionalita> nazionalita = lookupService.findAllLkNazionalita();
                List<Enti> enti = entiService.findAll(new Filter());
                Filter filter = new Filter();
                filter.setIdEnte(comunica.getSportello().getId());
                filter.setStatoProcedimento("abilitato");
                List<ProcedimentiLocalizzatiView> procedimentiSportello = procedimentiService.getProcedimentiLocalizzati("it", new Filter());
                List<ComboDTO> procedimentiDto = comunicaService.serializeProcedimenti(procedimentiSportello);
                List<ComboDTO> provincieDto = comunicaService.serializeProvincie(provincie);
                List<ComboDTO> formeGiuridicheDto = comunicaService.serializeFormeGiuridiche(formeGiuridiche);
                List<ComboDTO> entiDto = comunicaService.serializeEnti(enti);
                List<ComboDTO> nazionalitaDto = comunicaService.serializeNazionalita(nazionalita);
                model.addAttribute("comunica", comunica);
                model.addAttribute("provincie", provincieDto);
                model.addAttribute("nazionalita", nazionalitaDto);
                model.addAttribute("formaGiuridica", formeGiuridicheDto);
                model.addAttribute("enti", entiDto);
                model.addAttribute("procedimentiSuap", procedimentiDto);
                Message error = new Message();
                List<String> errore = new ArrayList<String>();
                String e = messageSource.getMessage("error.comunica.salvataggio", null, Locale.getDefault());
                errore.add(e);
                error.setMessages(errore);
//                try {
                ErroreDTO dto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_SALVA, " Errore nell'esecuzione del controller /comunica/salva ", ex, null, utenteConnesso);
                erroriAction.saveError(dto);
//                } catch (Exception ex1) {
//                    Log.WS.error("Errore salvando in tabella errori", ex1);
//                }
                request.setAttribute("message", error);
                return STEP1;
            }
        }
    }

    private List<String> getDestinatariEmail(it.wego.cross.xml.Pratica praticaCross) {
        List<String> email = new ArrayList<String>();
        try {
            it.wego.cross.xml.Recapito notifica = praticaCross.getNotifica();

            if (notifica != null) {
                if (!Utils.e(notifica.getPec())) {
                    email.add(notifica.getPec());
                    return email;
                } else if (!Utils.e(notifica.getEmail())) {
                    email.add(notifica.getEmail());
                    return email;
                }
            }
            if (praticaCross.getAnagrafiche() != null) {
                for (it.wego.cross.xml.Anagrafiche anagrafiche : praticaCross.getAnagrafiche()) {
                    //Verifica se è il richiedente
                	if(anagrafiche.getCodTipoRuolo()!=null) {
	                    if (anagrafiche.getCodTipoRuolo().equalsIgnoreCase("R") || anagrafiche.getCodTipoRuolo().equalsIgnoreCase("B")) {
	                        it.wego.cross.xml.Recapiti recapitiXml = anagrafiche.getAnagrafica().getRecapiti();
	                        if (recapitiXml != null && recapitiXml.getRecapito() != null && !recapitiXml.getRecapito().isEmpty()) {
	                            for (it.wego.cross.xml.Recapito r : recapitiXml.getRecapito()) {
	                                if (!Utils.e(r.getPec())) {
	                                    email.add(r.getPec());
	                                } else if (!Utils.e(r.getEmail())) {
	                                    email.add(r.getEmail());
	                                }
	                            }
	                        }
	                    }
                    }
                }
            }
        } catch (Exception ex) {
            //Non bloccante
            try {
                String err = "Errore cercando i destinatari della comunicazione";
                ErroreDTO errore = erroriAction.getError(Error.ERRORE_PARSING_PRATICA, err, ex, null, null);
                erroriAction.saveError(errore);
            } catch (Exception ex1) {
                Log.APP.error("getDestinatariEmail", ex1);
            }
        }
        return email;
    }
 private static File[] getFileList(String dirPath) {
     File dir = new File(dirPath);   

     File[] fileList = dir.listFiles();
     return fileList;
 }
 
 
 
 private static void copyFile(File sourceFile, File destFile) throws IOException { 
	    if (!sourceFile.exists()) { 
	     return; 
	    } 
	    if (!destFile.exists()) { 
	     destFile.createNewFile(); 
	    } 
	    FileChannel source = null; 
	    FileChannel destination = null; 
	    source = new FileInputStream(sourceFile).getChannel(); 
	    destination = new FileOutputStream(destFile).getChannel(); 
	    if (destination != null && source != null) { 
	     destination.transferFrom(source, 0, source.size()); 
	    } 
	    if (source != null) { 
	     source.close(); 
	    } 
	    if (destination != null) { 
	     destination.close(); 
	    } 

	} 
}
