/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazione.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.handler.RequestData;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.bouncycastle.cms.CMSException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;

import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneEnteSUAP;
import it.gov.impresainungiorno.suap.scrivania.PddServiceSUAP;
import it.gov.impresainungiorno.suap.scrivania.PddServiceSUAP_Service;
//import it.gov.impresainungiorno.suap.scrivania.suap.PddServiceSUAP_ServiceLocator;
import it.wego.cross.actions.AllegatiAction;
import it.wego.cross.actions.ComunicazioneAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.beans.grid.GridEventiBean;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.ConfigurationConstants;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.FileTypes;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.controller.AbstractController;
import it.wego.cross.dto.AllegatoDTO;
//import it.wego.cross.dto.ComunicazioneDTO;
import it.wego.cross.dto.EmailDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.ScadenzaDTO;
import it.wego.cross.dto.TemplateDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.dozer.PraticaDTO;
import it.wego.cross.dto.dozer.ProcessoEventoDTO;
import it.wego.cross.dto.dozer.forms.ComunicazioneDTO;
import it.wego.cross.dto.search.DestinatariDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.LkTipiAttore;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.PraticheProtocollo;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.protocollo.GestioneProtocollo;
import it.wego.cross.plugins.protocollo.beans.DocumentoProtocolloResponse;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.ComunicazioniService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheProtocolloService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.RegistroImpreseService;
import it.wego.cross.service.SearchService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.FileUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.MultiPartFileTempPath;
import it.wego.cross.utils.Utils;
import it.wego.cross.validator.IsValid;

@Controller
@Scope("session")
public class ComunicazioneController extends AbstractController {

	private static final String DETTAGLIO_EVENTO = "comunicazione_dettaglio_evento";
	private static final String EVENTO_SCELTA = "evento_scelta";
	private static final String REDIRECT_EVENTO_SCELTA = "redirect:/pratica/evento/index.htm";
	private static final String REDIRECT_DETTAGLIO_PRATICA = "redirect:/pratiche/dettaglio.htm";
	private static final String REDIRECT_COMUNICAZIONE_INDEX = "redirect:/pratica/comunicazione/index.htm";
	private static final String AJAX_PAGE = "ajax";
	public static final String CODICE_PROCESSO_SUAP = "SUAP";
	public static final String CODICE_PROCESSO_SUAP_SCIA = "SUAP_SCIA";
	public static final String CODICE_EVENTO_RILASCIO_PROVVEDIMENTO = "rilascio-provvedimen";
	public static final String CODICE_EVENTO_ALTRO = "altro";
	
	@Autowired
	private PraticheService praticheService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ComunicazioneAction comunicazioneAction;
	@Autowired
	private PraticheProtocolloService praticheProtocolloService;
	@Autowired
	protected Validator validator;
	@Autowired
	protected SearchService searchService;
	@Autowired
	private WorkFlowService workflowService;
	@Autowired
	private PluginService pluginService;
	@Autowired
	private IsValid isValid;
	@Autowired
	protected HttpServletRequest currentRequest;
	@Autowired
	@Qualifier("user")
	private UtenteDTO loggedUser;
	@Autowired
	private Mapper mapper;
	@Autowired
	FileUtils fileUtils;
	@Autowired
	ComunicazioniService comunicazioneService;
	@Autowired
	private AllegatiAction allegatiAction;
	@Autowired
	ErroriAction erroriAction;
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
    private AllegatiService allegatiService;
	@Autowired
	private RegistroImpreseService registroImpreseService;

	@RequestMapping(value = "ajax_upload", method = RequestMethod.POST)
	public @ResponseBody
	LinkedList<MultiPartFileTempPath> upload(MultipartHttpServletRequest request, HttpServletResponse response) {
		Integer idEnte = null;
		Integer idComune = null;
		Utente utente = utentiService.getUtenteConnesso(request);
		Pratica pratica = null;
		try {
			Integer idPratica = Integer.parseInt(request.getParameter("idPratica"));
			pratica = praticheService.getPratica(idPratica);
			if (pratica.getIdComune() != null) {
				idComune = pratica.getIdComune().getIdComune();
			}
			if (pratica.getIdProcEnte() != null) {
				if (pratica.getIdProcEnte().getIdEnte() != null) {
					idEnte = pratica.getIdProcEnte().getIdEnte().getIdEnte();
				}
			}
			return fileUtils.saveTempCopy(1, request, idEnte, idComune);
		} catch (Exception e) {
			Log.APP.error("Si e' verificato un errore nel salvataggio del file upload temporaneo", e);
			ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_AJAX_UPLOAD, "Errore nell'esecuzione del controller ajax_upload", e, pratica, utente);
			erroriAction.saveError(err);
			LinkedList<MultiPartFileTempPath> error = new LinkedList<MultiPartFileTempPath>();
			MultiPartFileTempPath fakeFile = new MultiPartFileTempPath();
			fakeFile.setName("_ERRORE_ERRORE_");
			error.add(fakeFile);
			return error;
		}
	}

	@RequestMapping("/pratica/comunicazione/index")
	public String index(
			@RequestParam(value = "id_pratica_selezionata", required = true) Integer idPratica,
			@RequestParam(value = "id_evento", required = true) Integer idEvento,
			@RequestParam(value = "verso", required = false) String verso,
			@RequestParam(value = "id_protocollo", required = false) Integer idPraticaProtocollo,
			@ModelAttribute("comunicazione") ComunicazioneDTO comunicazione,
			@ModelAttribute("message") Message message,
			Model model,
			HttpServletRequest request) {
		Utente utente = utentiService.getUtente(loggedUser.getIdUtente());
		Pratica pratica = null;
		try {
			pratica = praticheService.getPratica(idPratica);
			ProcessiEventi processoEvento = praticheService.findProcessiEventi(idEvento);
			EventoDTO evento = praticheService.getDettaglioProcessoEvento(idEvento);
			String tipoprotocollo = processoEvento.getFlgProtocollazione();
			it.wego.cross.dto.dozer.UtenteDTO utenteDTO = mapper.map(utente, it.wego.cross.dto.dozer.UtenteDTO.class);
			PraticaDTO praticaDTO = mapper.map(pratica, PraticaDTO.class);
			ProcessoEventoDTO processoEventoDTO = mapper.map(praticheService.findProcessiEventi(idEvento), ProcessoEventoDTO.class);
			processoEventoDTO.setFlgVisualizzaProcedimenti(processoEventoDTO.getFlgVisualizzaProcedimenti() == null ? "N" : processoEventoDTO.getFlgVisualizzaProcedimenti());

			List<ScadenzaDTO> scadenzeDaChiudere = praticheService.getScadenzeDaChiudereDTOList(praticaDTO.getIdPratica(), processoEventoDTO.getIdEvento());
			List<TemplateDTO> templates = comunicazioneService.getTemplates(processoEventoDTO.getIdEvento(), praticaDTO.getEnte().getIdEnte(), praticaDTO.getProcedimento().getIdProc());
			List<AllegatoDTO> allegatiPratica = praticheService.getAllegatiPratica(praticaDTO.getIdPratica());
			List<DestinatariDTO> destinatari = new ArrayList<DestinatariDTO>();
			
			if(praticaDTO.getProt_suap()!=null &&  !praticaDTO.getProt_suap().isEmpty()) {
				if(processoEventoDTO.getCodEvento().equals("TRA_SCHE_SORV")) {
					//destinatario è polizia municipale
					destinatari = new ArrayList<DestinatariDTO>();
					DestinatariDTO destinatarioDTO = new DestinatariDTO();
					destinatarioDTO.setDescription("Polizia municipale");
					destinatarioDTO.setType(Constants.ANAGRAFICA_ENTE);
					destinatarioDTO.setEmail("segreteria.annona@comune.bari.it");
					destinatari.add(destinatarioDTO);
				}else if(processoEventoDTO.getCodEvento().equals("INVIO_AVVIO_PROC")){
				//destinatario è polizia municipale pec, asl, l'istante
					destinatari = new ArrayList<DestinatariDTO>();
					DestinatariDTO destinatarioDTO = new DestinatariDTO();
					destinatarioDTO.setDescription("Polizia municipale");
					destinatarioDTO.setType(Constants.ANAGRAFICA_ENTE);
					destinatarioDTO.setEmail("poliziamunicipale.comunebari@pec.rupar.puglia.it");
					destinatari.add(destinatarioDTO);
					//Dipartimento di Prevenzione - ASL - dipartimentoprevenzione.aslbari@pec.rupar.puglia.it
					destinatarioDTO = new DestinatariDTO();
					destinatarioDTO.setDescription("Dipartimento di Prevenzione - ASL");
					destinatarioDTO.setType(Constants.ANAGRAFICA_ENTE);
					destinatarioDTO.setEmail("dipartimentoprevenzione.aslbari@pec.rupar.puglia.it");
					destinatari.add(destinatarioDTO);
					
/*					destinatarioDTO = new DestinatariDTO();
					destinatarioDTO.setDescription("Raffaele Russo");
					destinatarioDTO.setType(Constants.ANAGRAFICA_ENTE);
					destinatarioDTO.setEmail("raffaele.russo@clebari.com");
					destinatari.add(destinatarioDTO);*/
					
				}else {
					destinatari = comunicazioneService.getListDestinatari(processoEventoDTO.getIdEvento(), praticaDTO.getIdPratica(), comunicazione.getDestinatariIds());
				}
			}else {
				 destinatari = comunicazioneService.getListDestinatari(processoEventoDTO.getIdEvento(), praticaDTO.getIdPratica(), comunicazione.getDestinatariIds());
				}
			/* patch disperata */
			if (idPratica==11025 && processoEventoDTO.getIdEvento()==686) {
				destinatari = new ArrayList<DestinatariDTO>();
				DestinatariDTO destinatarioDTO = new DestinatariDTO();
				destinatarioDTO.setDescription("Giusta Eugenia");
				destinatarioDTO.setType(Constants.ANAGRAFICA_GENERICA);
				destinatarioDTO.setEmail("arch.eugeniagiusta@gmail.com");
				destinatarioDTO.setId("5760");
				destinatari.add(destinatarioDTO);
			}
			
			List<DestinatariDTO> mittenti = comunicazioneService.getListMittenti(processoEventoDTO.getIdEvento(), praticaDTO.getIdPratica(), comunicazione.getMittentiIds());
			for (AllegatoDTO allegato : allegatiPratica) {
				String code = FileTypes.myMap.get(allegato.getTipoFile());
				allegato.setTipoFileCode(FileTypes.myMap.get(allegato.getTipoFile()));
				if (code == null) {
					allegato.setTipoFileCode(FileTypes.myMap.get("default"));
				}
			}
			if (!comunicazione.getInitialized()) {
				comunicazioneService.initializeComunicazione(comunicazione, pratica, processoEvento, utente);
			}
			List<String> erroreparsing = new ArrayList<String>();
			if (comunicazione.getOggetto() != null) {
				if (comunicazione.getOggetto().contains("#ERRORE#")) {
					erroreparsing.add("La visualizzazione dell'oggetto della mail non e' corretta a causa di un errore di parsing. Controllare la correttezza del template:\n"
							+ comunicazione.getOggetto().substring(0, comunicazione.getOggetto().length() - 8));
					comunicazione.setOggetto("");

				}
			}
			if (comunicazione.getContenuto() != null) {
				if (comunicazione.getContenuto().contains("#ERRORE#")) {
					erroreparsing.add("La visualizzazione del contenuto della mail non e' corretta a causa di un errore di parsing. Controllare la correttezza del template:\n"
							+ comunicazione.getContenuto().substring(0, comunicazione.getContenuto().length() - 8));
					comunicazione.setContenuto("");
				}
			}
			if (erroreparsing.size() > 0) {
				model.addAttribute("message", new Message(erroreparsing));
			}
			if (processoEvento.getFlgPortale().equalsIgnoreCase("S")) {
				//Se viene data la possibilità di pubblicare un evento sul portale, di default il flg viene impostato a checked
				comunicazione.setVisualizzaEventoPortale(Boolean.TRUE);
			}
			//Verifico se arrivo da un docarr
			if (idPraticaProtocollo != null) {
				PraticheProtocollo praticaProtocollo = praticheProtocolloService.findPraticaProtocolloById(idPraticaProtocollo);
				GestioneProtocollo gp = pluginService.getGestioneProtocollo(pratica.getIdProcEnte().getIdEnte().getIdEnte(), null);
				DocumentoProtocolloResponse documentiCaricati = gp.findByProtocollo(praticaProtocollo.getAnnoRiferimento(), praticaProtocollo.getNProtocollo(), pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune());
				Allegato documentoPrincipale = documentiCaricati.getAllegatoOriginale();
				List<Allegato> allegati = new ArrayList<Allegato>();
				if (documentiCaricati.getAllegati() != null && !documentiCaricati.getAllegati().isEmpty()) {
					allegati = documentiCaricati.getAllegati();
				}
				if (!allegati.contains(documentoPrincipale)) {
					allegati.add(documentoPrincipale);
				}
				List<AllegatoDTO> toAdd = new ArrayList<AllegatoDTO>();
				for (Allegato allegato : allegati) {
					Allegati a = AllegatiSerializer.serialize(allegato);
					allegatiAction.inserisciAllegato(a);
					AllegatoDTO allegatoDaAggiungere = AllegatiSerializer.serialize(a);
					allegatoDaAggiungere.setDaAggiungere(Boolean.TRUE);
					toAdd.add(allegatoDaAggiungere);
				}
				if (comunicazione.getAllegatiDaProtocollo() == null) {
					comunicazione.setAllegatiDaProtocollo(new ArrayList<AllegatoDTO>());
				}
				for (AllegatoDTO dto : toAdd) {
					comunicazione.getAllegatiDaProtocollo().add(dto);
				}
				comunicazione.setNumeroDiProtocollo(praticaProtocollo.getIdentificativoPratica());
				comunicazione.setDataDiProtocollo(praticaProtocollo.getDataProtocollazione());
				comunicazione.setIdPraticaProtocollo(idPraticaProtocollo);
			}

			model.addAttribute("pratica", praticaDTO);
			model.addAttribute("utente", utenteDTO);
			model.addAttribute("processo_evento", processoEventoDTO);
			model.addAttribute("scadenze_da_chiudere", scadenzeDaChiudere);
			model.addAttribute("templates", templates);
			model.addAttribute("allegatiPratica", allegatiPratica);
			model.addAttribute("destinatari", destinatari);
			model.addAttribute("mittenti", mittenti);
			model.addAttribute("verso", verso);
			//            model.addAttribute("id_protocollo", idPraticaProtocollo);
			model.addAttribute("utenteConnesso", loggedUser);
			model.addAttribute("tipoprotocollo", tipoprotocollo);
			if (comunicazione.getAllegatoOriginale() != null) {
				model.addAttribute("allegatoOriginale", comunicazione.getAllegatoOriginale());
			}
			if (comunicazione.getAllegatiManuali() != null) {
				model.addAttribute("allegatiManuali", comunicazione.getAllegatiManuali());
			}
			if (comunicazione.getAllegatiDaProtocollo() != null) {
				model.addAttribute("allegatiDaProtocollo", comunicazione.getAllegatiDaProtocollo());
			}

			return "comunicazione_nuovo_evento";
		} catch (Exception ex) {
			Log.APP.error("Si e' verificato un errore cercando di visualizzare il dettaglio dell'evento da aprire", ex);
			List<String> errors = new ArrayList<String>();
			errors.add(ex.getMessage());
			model.addAttribute("message", new Message(errors));
			ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONE_INDEX, "Errore nell'esecuzione del controller /pratica/comunicazione/index", ex, pratica, utente);
			erroriAction.saveError(err);
			return EVENTO_SCELTA;
		}
	}

	@RequestMapping("/pratica/comunicazione/azione/salva")
	public ModelAndView salvaEvento(
			@ModelAttribute("comunicazione") ComunicazioneDTO comunicazione,
			RedirectAttributes redirectAttributes,
			BindingResult result) throws IOException {
		List<String> errors = new ArrayList<String>();
		Integer idPratica = comunicazione.getIdPratica();
		Integer idEvento = comunicazione.getIdEvento();
		ProcessiEventi eventoProcesso = praticheService.findProcessiEventi(idEvento);
		Utente utente = utentiService.getUtente(loggedUser.getIdUtente());
		Pratica pratica = null;
		Message msg = new Message();
		String resultUrl;
		Integer idComune = null;
		//        Integer idEnte = null;
		try {
			Log.APP.info("Recupero la pratica con id " + idPratica);
			pratica = praticheService.getPratica(idPratica);
			if (comunicazione.getIdPraticaProtocollo() != null) {
				Log.APP.info("Comunicazione con pratica da protocollo " + comunicazione.getIdPraticaProtocollo());
				PraticheProtocollo praticaProtocollo = praticheProtocolloService.findPraticaProtocolloById(comunicazione.getIdPraticaProtocollo());
				comunicazione.setNumeroDiProtocollo(praticaProtocollo.getCodRegistro() + "/" + praticaProtocollo.getAnnoRiferimento() + "/" + praticaProtocollo.getNProtocollo());
				comunicazione.setDataDiProtocollo(praticaProtocollo.getDataProtocollazione());
			}

			//Riordino gli allegati eliminando quelli nulli che si sono caricati a seguito dell'eliminazione e del successivo errore
			if (comunicazione.getAllegatiManuali() != null) {
				comunicazione.getAllegatiManuali().removeAll(Collections.singleton(null));
				List<AllegatoDTO> newlist = new ArrayList<AllegatoDTO>();
				for (AllegatoDTO allegato : comunicazione.getAllegatiManuali()) {
					if (allegato.getPathFile() != null) {
						newlist.add(allegato);
					}
				}
				comunicazione.setAllegatiManuali(newlist);
			}

			//SAL 4 - R14 Commento if successivo
			//Carico i file dal path temporaneo
//			if (comunicazione.getAllegatoOriginale() != null) {
//				if (comunicazione.getAllegatoOriginale().getPathFile() != null) {
//					MultipartFile file = fileUtils.getMultipartFile(comunicazione.getAllegatoOriginale().getNomeFile(), comunicazione.getAllegatoOriginale().getPathFile(), "allegatoOriginale.file", comunicazione.getAllegatoOriginale().getTipoFile(), false, 1024);
//					comunicazione.getAllegatoOriginale().setFile(file);
//				}
//
//			}
			if (comunicazione.getAllegatiManuali() != null) {
				for (AllegatoDTO allegato : comunicazione.getAllegatiManuali()) {
					String path = allegato.getPathFile();
					MultipartFile filea = fileUtils.getMultipartFile(allegato.getNomeFile(), path, "allegatoOriginale.file", allegato.getTipoFile(), false, 1024);
					allegato.setFile(filea);
				}
			}
			//SAL 4 - R14 Vedo se tra gli allegati sull'oggetto comunicazione c'è uno flaggato con 'principale' che sarà il nostro allegatoOriginale 
			int i=0;
			if(comunicazione.getAllegatiPresenti() != null) {
				List<AllegatoDTO> lista = new ArrayList<AllegatoDTO>();
				for (AllegatoDTO alleg : comunicazione.getAllegatiPresenti()) {
					if (alleg.isPrincipale()) {
						AllegatoDTO findAllegatoByIdNullSafe = allegatiService
								.findAllegatoByIdNullSafe(alleg.getIdAllegato(), pratica.getIdProcEnte().getIdEnte());
						if (findAllegatoByIdNullSafe != null) {
							alleg = allegatiService.findAllegatoByIdNullSafe(alleg.getIdAllegato(),
									pratica.getIdProcEnte().getIdEnte());
							MultipartFile filea = new MockMultipartFile(alleg.getNomeFile(), alleg.getNomeFile(),
									alleg.getTipoFile(), alleg.getFileContent());
							// MultipartFile filea = fileUtils.getMultipartFile(alleg.getNomeFile(),
							// alleg.getPathFile(), "allegatoOriginale.file", alleg.getTipoFile(), false,
							// 1024);
							alleg.setFile(filea);
							comunicazione.setAllegatoOriginale(alleg);
							i++;
						}
						else {
							errors.add(messageSource.getMessage("error.comunicazione.allegatoNonSpuntato", null, Locale.getDefault()));
						}
					}
					else {
						lista.add(alleg);
					}
				}
				comunicazione.setAllegatiPresenti(lista);
			}
			if(comunicazione.getAllegatiManuali() != null) {
				List<AllegatoDTO> lista = new ArrayList<AllegatoDTO>();
				for (AllegatoDTO alleg : comunicazione.getAllegatiManuali()) {
					if(alleg.isPrincipale()) {
						comunicazione.setAllegatoOriginale(alleg);
						i++;
					}
					else {
						lista.add(alleg);
					}
				}
				comunicazione.setAllegatiManuali(lista);
			}
			if(comunicazione.getAllegatiDaProtocollo() != null) {
				List<AllegatoDTO> lista = new ArrayList<AllegatoDTO>();
				for (AllegatoDTO alleg : comunicazione.getAllegatiDaProtocollo()) {
					if(alleg.isPrincipale()) {
						AllegatoDTO findAllegatoByIdNullSafe = allegatiService.findAllegatoByIdNullSafe(alleg.getIdAllegato(), pratica.getIdProcEnte().getIdEnte());
						if (findAllegatoByIdNullSafe != null) {
							alleg = allegatiService.findAllegatoByIdNullSafe(alleg.getIdAllegato(),	pratica.getIdProcEnte().getIdEnte());
							MultipartFile filea = new MockMultipartFile(alleg.getNomeFile(), alleg.getNomeFile(), alleg.getTipoFile(), alleg.getFileContent());
							// MultipartFile filea = fileUtils.getMultipartFile(alleg.getNomeFile(),
							// alleg.getPathFile(), "allegatoOriginale.file", alleg.getTipoFile(), false,
							// 1024);
							alleg.setFile(filea);
							comunicazione.setAllegatoOriginale(alleg);
							i++;
						}
						else {
							errors.add(messageSource.getMessage("error.comunicazione.allegatoNonSpuntato", null, Locale.getDefault()));
						}
					}
					else {
						lista.add(alleg);
					}
				}
				comunicazione.setAllegatiDaProtocollo(lista);
			}
			
			// MEV - Comunicazione SUAP ENTE
			//il processo evento per cui creare la comunicazione deve essere di tipo 'COMAE' e l'ente deve essere SUAP
			LkTipiAttore ta = new LkTipiAttore("ENTE");
			if (eventoProcesso.getIdTipoDestinatario() != null) {
				if (eventoProcesso.getIdTipoDestinatario().equals(ta)
						&& pratica.getIdProcEnte().getIdEnte().getDescrizione().contains("SUAP")
						&& eventoProcesso.getCodEvento().contains("COMAE")
						&& eventoProcesso.getFlgMail().equalsIgnoreCase("S")) {

					AllegatoDTO comunicazioneSuapEnte = allegatiService.creaFileSuapEnte(pratica, comunicazione);

					List<AllegatoDTO> allegatiManuali = new ArrayList<AllegatoDTO>();
					if (comunicazione.getAllegatiManuali() != null)
						allegatiManuali.addAll(comunicazione.getAllegatiManuali());
					allegatiManuali.add(comunicazioneSuapEnte);
					comunicazione.setAllegatiManuali(allegatiManuali);
				}
			}
			// MEV - Comunicazione SUAP ENTE - Fine

			Log.APP.info("Dump Submit Evento:\n " + gson.toJson(comunicazione));
			List<ProcessiEventi> steps = workflowService.findAvailableEvents(pratica.getIdPratica());
			if (!steps.contains(eventoProcesso)) {
				String error = messageSource.getMessage("error.comunicazione.eventoNonAmmesso", null, Locale.getDefault());
				errors.add(error);
				return new ModelAndView(REDIRECT_EVENTO_SCELTA);
			}

			validator.validate(comunicazione, result);
			errors.addAll(isValid.Evento(result));

			if ("S".equals(eventoProcesso.getFlgVisualizzaProcedimenti())) {
				if (comunicazione.getProcedimentiIds() == null || comunicazione.getProcedimentiIds().isEmpty()) {
					errors.add(messageSource.getMessage("error.comunicazione.procedimento", null, Locale.getDefault()));
				}
				valorizzaDestinatariMittenti(eventoProcesso, comunicazione);
			}

			if (comunicazione.getInviaEmail() && "S".equalsIgnoreCase(eventoProcesso.getFlgMail())) {
				if (comunicazione.getDestinatariIds() == null || comunicazione.getDestinatariIds().isEmpty()) {
					errors.add(messageSource.getMessage("error.destinatariEmpty", null, Locale.getDefault()));
				}
				if (Utils.e(comunicazione.getOggetto())) {
					errors.add(messageSource.getMessage("error.comunicazione.oggettoEmpty", null, Locale.getDefault()));
				}
				if (Utils.e(comunicazione.getContenuto())) {
					errors.add(messageSource.getMessage("error.comunicazione.contenutoEmpty", null, Locale.getDefault()));
				}
			}
			//Viene richiesta la visualizzazione dei destinatari/mittenti per l'evento
			//L'evento e' di INPUT
			//Verifico se sono valorizzati i destinatari e in caso negativo visualizzo l'errore
			if ("S".equalsIgnoreCase(eventoProcesso.getFlgDestinatari())
					&& "I".equalsIgnoreCase(String.valueOf(eventoProcesso.getVerso()))
					&& (comunicazione.getMittentiIds() == null || comunicazione.getMittentiIds().isEmpty())) {
				errors.add(messageSource.getMessage("error.comunicazione.mittentiEmpty", null, Locale.getDefault()));
			}
			if ("S".equalsIgnoreCase(eventoProcesso.getFlgProtocollazione())
					&& (comunicazione.getAllegatoOriginale() == null
					|| (comunicazione.getAllegatoOriginale() != null && (comunicazione.getAllegatoOriginale().getFile() == null || comunicazione.getAllegatoOriginale().getFile().getSize() == 0))
					|| (comunicazione.getAllegatoOriginale() != null && (Utils.e(comunicazione.getAllegatoOriginale().getDescrizione()))))) {
				errors.add(messageSource.getMessage("error.comunicazione.allegatoOriginaleEmpty", null, Locale.getDefault()));
			}
			//SAL 4 - R14
			if ("S".equalsIgnoreCase(eventoProcesso.getFlgProtocollazione())
					&& i>1) {
				errors.add(messageSource.getMessage("error.comunicazione.allegatoOriginaleMore", null, Locale.getDefault()));
			}
			
			if ("S".equalsIgnoreCase(eventoProcesso.getFlgFirmato())) {
				if (comunicazione.getAllegatoOriginale() == null) {
					errors.add(messageSource.getMessage("error.comunicazione.allegatoOriginaleNotPresent", null, Locale.getDefault()));
				} else if ("application/pdf".equals(comunicazione.getAllegatoOriginale().getFile().getContentType())) {
					CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) comunicazione.getAllegatoOriginale().getFile();
					
					InputStream is = commonsMultipartFile.getInputStream();
					
		            PdfReader pdfReader = new PdfReader(is);
					
		            AcroFields acroFields = pdfReader.getAcroFields();
		            List<String> signatureNames = acroFields.getSignatureNames();
		            boolean isSigned = false;
/*		            if (!signatureNames.isEmpty()) {
		                for (String name : signatureNames) {
		                	isSigned = acroFields.signatureCoversWholeDocument(name);
		                	if(!isSigned) {
		                		errors.add(messageSource.getMessage("error.comunicazione.allegatoOriginaleNotSigned", null, Locale.getDefault()));	
		                	}
		                }
		            } else {
		            	errors.add(messageSource.getMessage("error.comunicazione.allegatoOriginaleNotSigned", null, Locale.getDefault()));
		            }*/
		            if(signatureNames.size()==0) {
		            	errors.add(messageSource.getMessage("error.comunicazione.allegatoOriginaleNotSigned", null, Locale.getDefault()));
		            }
				}
				else if (!FileUtils.isSigned(comunicazione.getAllegatoOriginale().getFile())) {
					errors.add(messageSource.getMessage("error.comunicazione.allegatoOriginaleNotSigned", null, Locale.getDefault()));
				}
			}
			
			
			
			if (pratica.getIdComune() != null) {
				idComune = pratica.getIdComune().getIdComune();
			}

			GestioneProtocollo gp = pluginService.getGestioneProtocollo(pratica.getIdProcEnte().getIdEnte().getIdEnte(), idComune);

			if ("S".equalsIgnoreCase(eventoProcesso.getFlgProtocollazione()) && gp == null && Utils.e(comunicazione.getNumeroDiProtocollo())) {
				errors.add(messageSource.getMessage("error.comunicazione.numeroProtocolloMancante", null, Locale.getDefault()));
			}

			Boolean numProtoPresente = !Utils.e(comunicazione.getNumeroDiProtocollo());
			Boolean dataProtoPresente = !Utils.e(comunicazione.getDataDiProtocollo());
			if (numProtoPresente ^ dataProtoPresente) {
				errors.add(messageSource.getMessage("error.comunicazione.numeroDataProtocolloIncoerente", null, Locale.getDefault()));
			}
//			if (eventoProcesso.getIdTipoDestinatario() != null && pratica.getIdProcesso().getCodProcesso().equals(CODICE_PROCESSO_SUAP_SCIA) 
//					&& (eventoProcesso.getCodEvento().equals(CODICE_EVENTO_RILASCIO_PROVVEDIMENTO) 
//							|| eventoProcesso.getCodEvento().equals(CODICE_EVENTO_ALTRO) ) ) {
//				inviaEnteSUAP(pratica,comunicazione,utente);
//			}
			if (errors.isEmpty()) {
				Log.APP.info("Chiamo la funzione Gestisci Evento ...");
				ComunicazioneBean comunicazioneBean = comunicazioneAction.gestisciEvento(comunicazione, loggedUser);
				PraticheEventi praticaEvento = praticheService.getPraticaEvento(comunicazioneBean.getIdEventoPratica());
				if (eventoProcesso.getIdTipoDestinatario() != null && pratica.getIdProcesso().getCodProcesso().equals(CODICE_PROCESSO_SUAP_SCIA) 
						&& (eventoProcesso.getCodEvento().equals(CODICE_EVENTO_RILASCIO_PROVVEDIMENTO) 
								|| eventoProcesso.getCodEvento().equals(CODICE_EVENTO_ALTRO) ) ) {
					inviaEnteSUAP(pratica,comunicazione,utente,praticaEvento.getPraticheEventiAllegatiList());
				}
				praticheService.startCommunicationProcess(pratica, praticaEvento, comunicazioneBean);
				//                for (Map.Entry<String, Object> entry : comunicazioneBean.getMessages().entrySet()) {
				//                    if (entry.getKey().startsWith("EVENTO_SOTTO_PRATICA_")) {
				//                        Integer idPraticaEventoSottoPratica = (Integer) entry.getValue();
				//                        PraticheEventi praticaEventoSottoPratica = praticheService.getPraticaEvento(idPraticaEventoSottoPratica);
				//                        praticheService.startCommunicationProcess(praticaEventoSottoPratica.getIdPratica(), praticaEventoSottoPratica, null);
				//                    }
				//                }

				Log.APP.info("... evento gestito correttamente");
			}
			
		} catch (Exception ex) {
			ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONE_AZIONE_SALVA, "Errore nell'esecuzione del controller /pratica/comunicazione/azione/salva", ex, pratica, utente);
			erroriAction.saveError(err);
			if (ex instanceof CMSException) {
				errors.add(messageSource.getMessage("error.noAllegatiFirmati", null, Locale.getDefault()));
			} else if (ex instanceof CrossException) {
				errors.add(ex.getMessage());
			} else {
				Log.APP.error("Si e' verificato un errore. Ritorno alla schermata della Comunicazione.", ex);
				errors.add(messageSource.getMessage("error.nongestito", null, null, Locale.getDefault()));
			}
		}
		if (errors.isEmpty()) {
			redirectAttributes.addAttribute("id_pratica", idPratica);
			redirectAttributes.addAttribute("active_tab", "#frame6");
			//cancello il path temporaneo dai documenti  che vado a caricare
			if (comunicazione.getAllegatiManuali() != null) {
				for (AllegatoDTO allegato : comunicazione.getAllegatiManuali()) {
					allegato.setPathFile(null);
				}
			}
			if (comunicazione.getAllegatoOriginale() != null) {
				comunicazione.getAllegatoOriginale().setPathFile(null);
			}
			//            fileUtils.cleanTempDirectory(idEnte, idComune);
			msg.setError(Boolean.FALSE);
			resultUrl = REDIRECT_DETTAGLIO_PRATICA;
		} else {
			redirectAttributes.addAttribute("id_pratica_selezionata", idPratica);
			redirectAttributes.addAttribute("id_evento", eventoProcesso.getIdEvento());
			if (comunicazione.getAllegatiManuali() != null) {
				for (AllegatoDTO allegato : comunicazione.getAllegatiManuali()) {
					allegato.setTipoFileCode(FileTypes.myMap.get(allegato.getTipoFile()));
					if (allegato.getTipoFileCode() == null) {
						allegato.setTipoFileCode(FileTypes.myMap.get("default"));
					}
				}
			}
			redirectAttributes.addFlashAttribute("comunicazione", comunicazione);
			redirectAttributes.addAttribute("id_protocollo", comunicazione.getIdPraticaProtocollo());
			msg.setMessages(errors);
			msg.setError(Boolean.TRUE);
			resultUrl = REDIRECT_COMUNICAZIONE_INDEX;
		}
		redirectAttributes.addFlashAttribute("message", msg);
		return new ModelAndView(resultUrl);
	}

	
	private void inviaEnteSUAP( Pratica pratica,ComunicazioneDTO comunicazione,Utente utente,List<PraticheEventiAllegati> peAllegati) throws IOException {
		List<String> errors = new ArrayList<String>();
		Integer idPratica = pratica.getIdPratica();
		//        Integer idEnte = null;
		try {
			Log.APP.info("Recupero la pratica con id " + idPratica);
			String file = configurationService
					.findByName(SessionConstants.KEYSTORE_PATH).get(0).getValue();
			String password = configurationService
					.findByName(SessionConstants.KEYSTORE_PASSWORD).get(0).getValue();
			String endPoint = configurationService
					.findByName(SessionConstants.SUAP_ENDPOINT).get(0).getValue();
			String username = configurationService
					.findByName(SessionConstants.KEYSTORE_ALIAS).get(0).getValue();
			System.setProperty("javax.net.ssl.trustStore",file);
			System.setProperty("javax.net.ssl.trustStorePassword",password);
			System.setProperty("javax.net.ssl.keyStore",file);
			System.setProperty("javax.net.ssl.keyStorePassword",password);
			System.setProperty("http.keepAlive", "true");
			System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
			System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
			System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
			System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
			System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
			CooperazioneEnteSUAP ces = registroImpreseService.creaCES3(pratica, comunicazione);
			List<AllegatoCooperazione> temp = new ArrayList<AllegatoCooperazione>();
			for(PraticheEventiAllegati pe:peAllegati) {
				if(pe.getAllegati()!=null) {
					Allegati allegato = pe.getAllegati();
					AllegatoCooperazione e = AllegatiSerializer.serializeAllegatoCooperazione(allegato);
					temp.add(e);
					
				}
			}
//			List<AllegatoDTO> allegatiManuali = comunicazione.getAllegatiManuali();
//			if(allegatiManuali!=null) {
//				for (AllegatoDTO allegato : allegatiManuali) {
//					AllegatoCooperazione e = serializeAllegatoCooperazione(allegato);
//		    		temp.add(e);
//		        }
//			}
			ces.getAllegato().addAll(temp);
			setAutenticator();
			Log.APP.info("Prima creazione  servizio SUAP");
			PddServiceSUAP_Service service = new PddServiceSUAP_Service(new URL(endPoint),
					PddServiceSUAP_Service.SERVICE);
			PddServiceSUAP stub;
			stub = service.getPddPortSUAP();
			
			String storeType = "jks";
			KeyStore keyStore = KeyStore.getInstance(storeType);
			keyStore.load(new FileInputStream(file), password.toCharArray());
			KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			factory.init(keyStore, password.toCharArray());
			KeyManager[] keyManagers = factory.getKeyManagers();
			Client client = ClientProxy.getClient(stub);
			HTTPConduit conduit = (HTTPConduit) client.getConduit();
			conduit.setTlsClientParameters(new TLSClientParameters());
			conduit.getTlsClientParameters().setKeyManagers(keyManagers);
			
			Endpoint cxfEndpoint = client.getEndpoint();
			 
			Map<String,Object> outProps = new HashMap<String,Object>();
			
			Properties cxfProps = new Properties();
			cxfProps.setProperty("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
			cxfProps.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", storeType);
			cxfProps.setProperty("org.apache.ws.security.crypto.merlin.keystore.alias", username);
			cxfProps.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", password);
			cxfProps.setProperty("org.apache.ws.security.crypto.merlin.file", file);
			final Crypto crypto = CryptoFactory.getInstance(cxfProps);
			outProps.put("action", "Timestamp Signature");
			outProps.put("signaturePropFile", "signprop.properties");
			outProps.put("signatureKeyIdentifier", "DirectReference");
			outProps.put("passwordCallbackClass", "test.security.KeystorePasswordCallbackComplete");
			outProps.put("signatureParts", "{Element}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp;{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body");
			outProps.put("encryptionPropFile", "signprop.properties");
			outProps.put("encryptionSymAlgorithm", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc");
			outProps.put("encryptionKeyTransportAlgorithm", "http://www.w3.org/2001/04/xmlenc#rsa-1_5");
			outProps.put(WSHandlerConstants.USER, username);
			WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps) {
				  @Override
				  protected Crypto loadCryptoFromPropertiesFile(String propFilename, RequestData reqData) {
				    return crypto;
				  }
				};
			
			
			cxfEndpoint.getOutInterceptors().add(wssOut);
			String esito = stub.inviaEnteSUAP(ces);
			Log.APP.info("dopo chiamata inviaEnteSUAP");
			Log.APP.info("Esito chiamata inviaEnteSUAP");
			Log.APP.info("Esito = " + esito);
		
		}catch (Exception ex) {
				ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONE_AZIONE_SALVA, "Errore nell'esecuzione del controller /pratica/comunicazione/azione/salva", ex, pratica, utente);
				erroriAction.saveError(err);
				if (ex instanceof CMSException) {
					errors.add(messageSource.getMessage("error.noAllegatiFirmati", null, Locale.getDefault()));
				} else if (ex instanceof CrossException) {
					errors.add(ex.getMessage());
				} else {
					Log.APP.error("Si e' verificato un errore. Ritorno alla schermata della Comunicazione.", ex);
					errors.add(messageSource.getMessage("error.nongestito", null, null, Locale.getDefault()));
				}
			}
		
	}
	
	
	@RequestMapping("/pratica/comunicazione/dettaglio_evento")
	public String dettaglioEvento(Model model, @ModelAttribute EmailDTO email, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		Message msg = new Message();
		String page = DETTAGLIO_EVENTO;
		Utente utente = utentiService.getUtenteConnesso(request);
		Pratica pratica = null;
		try {
			Integer idPraticaEvento = Integer.valueOf(request.getParameter("id_pratica_evento"));
			Integer idPratica = (Integer) request.getSession().getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA);

			pratica = praticheService.getPratica(idPratica);
			EventoDTO evento = praticheService.getDettaglioPraticaEvento(idPraticaEvento);
			//TODO: CORREGGERE CREANDO UN EVENTO AD HOC PER LA GENERAZIONE DEL TRACCIATO SURI
			if (evento.getDescrizione() != null) {
				if (evento.getDescrizione().contains("REA")) {
					model.addAttribute("abilitaXMLSuri", "true");
				}
			}
			for (AllegatoDTO allegato : evento.getAllegati()) {
				String code = FileTypes.myMap.get(allegato.getTipoFile());
				allegato.setTipoFileCode(FileTypes.myMap.get(allegato.getTipoFile()));
				if (code == null) {
					allegato.setTipoFileCode(FileTypes.myMap.get("default"));
				}
			}
			//model.addAttribute("evento", evento);
			model.addAttribute("id_pratica", pratica.getIdPratica());
			model.addAttribute("pratica", pratica);
			boolean couldCreateNewEvent = utentiService.couldCreateNewEvent(utente, pratica);
			model.addAttribute("couldCreateNewEvent", couldCreateNewEvent);
			//evento = praticheService.getDettaglioPraticaEvento(idPraticaEvento);
			if (evento.getPubblicazionePortale() == null) {
				evento.setPubblicazionePortale("S");
			}
			for (AllegatoDTO allegato : evento.getAllegati()) {
				String code = FileTypes.myMap.get(allegato.getTipoFile());
				allegato.setTipoFileCode(FileTypes.myMap.get(allegato.getTipoFile()));
				if (code == null) {
					allegato.setTipoFileCode(FileTypes.myMap.get("default"));
				}
			}
			String isEventoEditable = configurationService.getCachedConfiguration(ConfigurationConstants.IS_PRATICA_EVENTO_MODIFICABILE, pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());
			model.addAttribute("isEventoEditable", "TRUE".equalsIgnoreCase(isEventoEditable));
			model.addAttribute("evento", evento);
			if (evento.getFunzioneApplicativa() != null) {
				page = evento.getFunzioneApplicativa();
			}
		} catch (FileNotFoundException ex) {
			//TODO: visualizza su interfaccia l'errore
			Log.APP.error("Errore cercando di inviare l'email. Errore cercando di recuperare il file", ex);
			msg.setMessages(Arrays.asList("Impossibile rispedire l'email. Contattare l'amministratore"));
			ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONE_DETTAGLIO_EVENTO, "Errore nell'esecuzione del controller /pratica/comunicazione/dettaglio_evento", ex, pratica, utente);
			erroriAction.saveError(err);
		} catch (Exception ex) {
			Log.APP.error("Si e' verificato un errore cercando di visualizzare il dettaglio dell'evento", ex);
			msg.setMessages(Arrays.asList("Si e' verificato un errore cercando il dettaglio dell'evento. Contattare l'amministratore"));
			ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONE_DETTAGLIO_EVENTO, "Errore nell'esecuzione del controller /pratica/comunicazione/dettaglio_evento", ex, pratica, utente);
			erroriAction.saveError(err);
		}
		if (msg.getMessages() != null && msg.getMessages().size() > 0) {
			model.addAttribute("message", msg);
		}
		return page;
	}

	@RequestMapping("/pratica/comunicazione/dettaglio_evento/ajax")
	public String dettaglioEventoAjax(Model model, @ModelAttribute("evento") EventoDTO evento, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		GridEventiBean json = new GridEventiBean();
		Utente utente = utentiService.getUtenteConnesso(request);
		Pratica pratica = null;
		try {
			if (evento.getIdPratica() != null) {
				pratica = praticheService.getPratica(evento.getIdPratica());
			}
			String pubblicazionePortale = request.getParameter("pubblicazionePortale");
			evento.setPubblicazionePortale(pubblicazionePortale);
			comunicazioneAction.cambiaFlag(evento);
		} catch (Exception ex) {
			String err = "Si e' verificato un errore visualizzando il dettaglio dell'evento";
			Log.APP.error(err, ex);
			json.setErrors(Arrays.asList(err));
			ErroreDTO erro = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONE_DETTAGLIO_EVENTO_AJAX, "Errore nell'esecuzione del controller /pratica/comunicazione/dettaglio_evento/ajax", ex, pratica, utente);
			erroriAction.saveError(erro);
		}
		model.addAttribute("json", json.toString());
		return AJAX_PAGE;
	}

	private void valorizzaDestinatariMittenti(ProcessiEventi eventoProcesso, ComunicazioneDTO comunicazione) throws Exception {
		comunicazione.setMittentiIds(null);
		comunicazione.setDestinatariIds(null);
		List<String> lista = new ArrayList<String>();
		for (String id : comunicazione.getProcedimentiIds()) {
			PraticaProcedimenti praticaProcedimento = praticheService.findEnteDaPraticaProcedimento(comunicazione.getIdPratica(), Integer.valueOf(id));
			if (!lista.contains("E|" + String.valueOf(praticaProcedimento.getEnti().getIdEnte()))) {
				lista.add("E|" + String.valueOf(praticaProcedimento.getEnti().getIdEnte()));
			}
		}
		if ("I".equalsIgnoreCase(String.valueOf(eventoProcesso.getVerso()))) {
			comunicazione.setMittentiIds(lista);
		} else {
			comunicazione.setDestinatariIds(lista);
		}
	}
	
	private void setAutenticator() {
		Authenticator myAuth = new Authenticator() 
		{
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication()
		    {
		        return new PasswordAuthentication("suap", "suap2011".toCharArray());
		    }
		};
		Authenticator.setDefault(myAuth);
	}
	
	
	public  AllegatoCooperazione serializeAllegatoCooperazione(AllegatoDTO allegato) throws IOException {
		AllegatoCooperazione ac = new AllegatoCooperazione();
		ac.setCod("ALLEG");
		ac.setDescrizione(allegato.getDescrizione());
		String path = allegato.getPathFile();
		 MultipartFile filea = fileUtils.getMultipartFile(allegato.getNomeFile(), path, "allegatoOriginale.file", allegato.getTipoFile(), false, 1024);
		 List<Configuration> attachmentsFolderConfig = configurationService
					.findByName(SessionConstants.ATTACHMENTS_FOLDER);
			Configuration config = attachmentsFolderConfig.get(0);
			String attachmentsFolder = config.getValue();	
		 File fileb = new File(attachmentsFolder + "/" + filea.getOriginalFilename());
			filea.transferTo(fileb);
		if(fileb != null) {
			ac.setDimensione(BigInteger.valueOf(fileb.length()));
		}
		ac.setMime(allegato.getTipoFile());
		ac.setNomeFile(allegato.getNomeFile());
		ac.setNomeFileOriginale(allegato.getNomeFile());
		
		
		//ac.setEmbeddedFileRef(AllegatiSerializer.getDataHandler(fileb.getPath()));
		return ac;
	}
	
	
	
	
	
	
	private static void disableSslVerification() {
	    try
	    {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
					// TODO Auto-generated method stub
					
				}
				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
					// TODO Auto-generated method stub
					
				}
	        }
	        };

	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };

	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    }
	}
}
