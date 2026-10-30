package it.wego.cross.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.codehaus.jackson.map.ObjectMapper;
import org.dozer.Mapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import it.eng.tz.avtmb.wsdl.stampsign.DocumentStampSignAuthPt;
import it.eng.tz.avtmb.wsdl.stampsign.DocumentStampSignPt;
import it.eng.tz.avtmb.wsdl.stampsign.Stampsign;
import it.eng.tz.avtmb.xsd.stampsign.RequestType;
import it.eng.tz.avtmb.xsd.stampsign.Risultato;
import it.eng.tz.avtmb.xsd.stampsign.Sign;
import it.eng.tz.avtmb.xsd.stampsign.StampSignRequest;
import it.eng.tz.avtmb.xsd.stampsign.StampSignResponse;
import it.linksmt.protocollo.richiestasoap.Mittente;
import it.linksmt.protocollo.ws.server.GetProtocollo.ProtocolloInformazioniRequest;
import it.linksmt.protocollo.ws.server.GetProtocolloResponse.Return;
import it.linksmt.protocollo.ws.server.ProtocolloServer;
import it.linksmt.protocollo.ws.server.impl.ProtocolloServerImplService;
import it.wego.cross.actions.AllegatiAction;
import it.wego.cross.actions.AnagraficaAction;
import it.wego.cross.actions.EmailAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.MessaggiAction;
import it.wego.cross.actions.PraticheAction;
import it.wego.cross.actions.SystemEventAction;
import it.wego.cross.actions.WorkflowAction;
import it.wego.cross.annotations.RequiresGrant;
import it.wego.cross.beans.JsonResponse;
import it.wego.cross.beans.grid.GridAnagraficaBean;
import it.wego.cross.beans.grid.GridAssociaUtentiBean;
import it.wego.cross.beans.grid.GridCittadinanzaBean;
import it.wego.cross.beans.grid.GridComuneBean;
import it.wego.cross.beans.grid.GridDatiCatastaliBean;
import it.wego.cross.beans.grid.GridDatoCatastaleBean;
import it.wego.cross.beans.grid.GridEntiBean;
import it.wego.cross.beans.grid.GridEstrazioniAGIBBean;
import it.wego.cross.beans.grid.GridEstrazioniCILABean;
import it.wego.cross.beans.grid.GridEstrazioniPDCBean;
import it.wego.cross.beans.grid.GridEstrazioniSCIABean;
import it.wego.cross.beans.grid.GridIdStringListBean;
import it.wego.cross.beans.grid.GridIndirizziInterventoBean;
import it.wego.cross.beans.grid.GridIndirizzoInterventoBean;
import it.wego.cross.beans.grid.GridNazionalitaBean;
import it.wego.cross.beans.grid.GridPraticaEventoBean;
import it.wego.cross.beans.grid.GridPraticaNuovaBean;
import it.wego.cross.beans.grid.GridProcedimenti;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.constants.CartiglioConstants;
import it.wego.cross.constants.ConfigurationConstants;
import it.wego.cross.constants.Constants;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.constants.StatoPratica;
//import it.wego.cross.cross_dms.DMSClientDao;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.AnagraficaDaCollegareDTO;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.EstrazioniAgibDTO;
import it.wego.cross.dto.EstrazioniCilaDTO;
import it.wego.cross.dto.EstrazioniPdcDTO;
import it.wego.cross.dto.EstrazioniSciaDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.MessaggioDTO;
import it.wego.cross.dto.NazionalitaDTO;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.PraticaNuova;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.TipoIndirizzoDTO;
import it.wego.cross.dto.TipoRuoloDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.UtenteRuoloEnteDTO;
import it.wego.cross.dto.comunicazione.AnagraficaMinifiedDTO;
import it.wego.cross.dto.comunicazione.DettaglioPraticaDTO;
import it.wego.cross.dto.dozer.DatiCatastaliDTO;
import it.wego.cross.dto.dozer.EnteDTO;
import it.wego.cross.dto.dozer.IndirizzoInterventoDTO;
import it.wego.cross.dto.dozer.PraticaCollegataDTO;
import it.wego.cross.dto.dozer.ProcedimentoDTO;
import it.wego.cross.dto.dozer.ProcedimentoEnteDTO;
import it.wego.cross.dto.dozer.StatoPraticaDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Configuration;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkClassificazioneProcedimento;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.LkTipoInterventoSuap;
import it.wego.cross.entity.LkTipoParticella;
import it.wego.cross.entity.LkTipoPratica;
import it.wego.cross.entity.LkTipoProcedimentoSuap;
import it.wego.cross.entity.LkTipoQualifica;
import it.wego.cross.entity.LkTipoRuolo;
import it.wego.cross.entity.LkTipoSistemaCatastale;
import it.wego.cross.entity.LkTipoUnita;
import it.wego.cross.entity.Messaggio;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaAnagrafica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.ProcedimentiTesti;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.entity.view.ProcedimentiLocalizzatiView;
import it.wego.cross.events.comunicazione.controller.ComunicazioneController;
import it.wego.cross.events.notification.NotificationEngine;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.anagrafe.GestioneAnagrafePersonaFisica;
import it.wego.cross.plugins.anagrafe.GestioneAnagrafePersonaGiuridica;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.plugins.views.CustomViews;
import it.wego.cross.serializer.AnagraficheSerializer;
import it.wego.cross.serializer.EntiSerializer;
import it.wego.cross.serializer.FilterSerializer;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.CartiglioService;
import it.wego.cross.service.ComuniService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.FormService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.ProcessiService;
import it.wego.cross.service.UsefulService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.session.MultiSubSessionManager;
import it.wego.cross.utils.ComponentUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utilities;
import it.wego.cross.utils.Utils;
import it.wego.cross.validator.IsValid;
import it.wego.cross.validator.impl.AlphabeticValidatorImpl;
import it.wego.cross.validator.impl.CodiceFiscaleValidatorImpl;
import it.wego.cross.validator.impl.PartitaIvaValidatorImpl;

/**
 *
 * @author CS
 * @original_author : Gabriele Muscas [gabriele.muscas@wego.it]
 */
@Controller
public class Pratiche extends AbstractController {

	@Autowired
	private PraticaDao praticaDao;
    @Autowired
    private EntiService entiService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    protected Validator validator;
    @Autowired
    private ComuniService comuneService;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private IsValid isValid;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private MessaggiAction messaggiAction;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private LookupService lookupService;
    @Autowired
    private UsefulService usefulService;
    @Autowired
    private FormService formService;
    @Autowired
    private PraticheAction praticheAction;
    @Autowired
    private Mapper mapper;
    @Autowired
    private AnagraficaAction anagraficaAction;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private SystemEventAction systemEventAction;
    @Autowired
    private NotificationEngine notificationEngine;
    @Autowired
    private EmailAction emailAction;
    @Autowired
    private WorkflowAction workflowAction;
    @Autowired
    private AllegatiService allegatiService;
    @Autowired
    private AllegatiAction allegatiAction;
    @Autowired
    private FilterSerializer filterSerializer;
    @Autowired
    private EntiDao entiDao;
    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    private ProcessiService processiService;
    @Autowired
    private CartiglioService cartiglioService;
    @Autowired
    private ComponentUtils componentUtils;

    //Pages
    private static final String AJAX_PAGE = "ajax";
    private static final String PRATICHE_NUOVE = "pratiche_nuove";
    private static final String ASSEGNA_UTENTE = "pratica_assegna_utente";
    private static final String VISUALIZZA_PRATICHE = "pratiche_apertura_nuove";
    private static final String DETTAGLIO_PRATICA_APERTURA = "pratiche_apertura_nuove_dettaglio";
    private static final String DETTAGLIO_PRATICA = "pratica_dettaglio";
    private static final String REDIRECT_DETTAGLIO_PRATICA = "redirect:/pratiche/dettaglio.htm";
    private static final String REDIRECT_DETTAGLIO_PRATICA_WITH_SESSION_ID = "redirect:/pratiche/dettaglio.htm?"+MultiSubSessionManager.SESSION_PARAMETER+"=";
    private static final String REDIRECT_DETTAGLIO_PRATICA_WITH_ID = "redirect:/pratiche/dettaglio.htm?id_pratica=";
    private static final String COLLEGA_ANAGRAFICA = "pratica_collega_anagrafica";
    private static final String COLLEGA_PROCEDIMENTO = "pratica_collega_procedimento";
    private static final String COLLEGA_PROCEDIMENTO_LISTA_ENTI = "pratica_collega_procedimento_lista_enti";
    private static final String REDIRECT_COLLEGA_PROCEDIMENTO_LISTA_ENTI = "redirect:/pratiche/dettaglio/aggiungiProcedimento/listaEnti.htm";
    private static final String ASSEGNA_RUOLO_ANAGRAFICA = "pratica_collega_anagrafica_ruolo";
    private static final String VISUALIZZA_ASSEGNA="viasualizza.assegna";
    private static final String SELEZIONE_UTENTE_RIASSEGNAZIONE_PRATICA = "pratica_riassegnazione_selezione_utente";
    private static final String DEFAULT_PATH_DATI_CATASTALI = "/themes/default/views/main/body/pratiche/apertura/dati_catastali.jsp";
    private static final String DEFAULT_PATH_DATI_INDIRIZZI_INTERVENTO = "/themes/default/views/main/body/pratiche/apertura/indirizzi_intervento.jsp";
    private static final String HOME_PRATICA = "pratica_gestione";
    private static final String ESTRAZIONI_CILA = "estrazione_cila";
    private static final String ESTRAZIONI_SCIA = "estrazione_scia";
    private static final String ESTRAZIONI_PDC = "estrazione_pdc";
    private static final String ESTRAZIONI_AGIB = "estrazione_agib";
    private static final String TAB_DEFAULT = "frame7";
    private static final String TAB_DEFAULT_APERTURA = "frame1";
    private static final String TAB_ANAGRAFICHE_APERTURA = "frame1";
    private static final String TAB_ENDOPROCEDIMENTI_APERTURA = "frame2";
    private static final String TAB_ALLEGATI_APERTURA = "frame3";
    private static final String TAB_CATASTALI_APERTURA = "frame4";
    private static final String TAB_INDIRIZZI_APERTURA = "frame5";
    private static final String ESTRAZIONI_CILA_TO_DO = "estrazione_cila_to_do";
    private static final String ESTRAZIONI_AGIB_TO_DO = "estrazione_agib_to_do";
    private final String OPERAZIONE_EFFETTUATA = "operazioneeffettuata";
    public static SimpleDateFormat sdfIt = new SimpleDateFormat("dd/MM/yyyy");
    private final Message errori = new Message();
    private static String TOWN_WITH_GRANTS = "bari";
    public static Set<Integer> SUAP_INSTITUTION_XML_TECHNICIAN = new HashSet<>();
    static{
    	SUAP_INSTITUTION_XML_TECHNICIAN.add(Constants.SUAP_BARI);
    	SUAP_INSTITUTION_XML_TECHNICIAN.add(Constants.SUAP_GIOIA_DEL_COLLE);
    }
    
    private static DataHandler getDataHandler(String filePath) {
		 javax.activation.FileDataSource fileDataSource =new javax.activation.FileDataSource(filePath);
		 javax.activation.DataHandler dataHandler = new javax.activation.DataHandler(fileDataSource);
		 return dataHandler;
	 }

    @RequestMapping("/pratiche/nuove")
    public String nuove(Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        try {
            UtenteDTO utente = utentiService.getUtenteConnessoDTO(request);
            List<Enti> enti = entiService.getListaEntiPerRicerca(utente);
            List<String> anniRiferimento = praticheService.popolaListaAnni();
            List<LkStatoPratica> lookup = praticheService.getLookupsStatoPratica(StatoPratica.RICEVUTA);
            List<LkTipoUnita> lkTipoUnita = lookupDao.findAllLkTipoUnita();
            List<LkTipoPratica> lkTipoPratica = lookupDao.findSortedTipoPraticaByFlags(isRequestFrom(enti, TOWN_WITH_GRANTS));
            List<Utente> operatori = utentiService.findUtentiByEnte(enti);
            
            model.addAttribute("anniRiferimento", anniRiferimento);
            model.addAttribute("entiRicerca", enti);
            model.addAttribute("statoPraticaRicerca", lookup);
            model.addAttribute("lkTipiUnita", lkTipoUnita);
            model.addAttribute("lkTipoPratica", lkTipoPratica);
            model.addAttribute("operatoriRicerca", operatori);
            
            Filter filter = (Filter) request.getSession().getAttribute("praticheNuoveDaAssegnare");
            if (filter == null) {
                filter = new Filter();
            }
            model.addAttribute("filtroRicerca", filter);
            Integer enteAcasoPerColumnModel = null;
            for (UtenteRuoloEnteDTO ruoloEnte : utente.getUtenteRuoloEnte()) {
                enteAcasoPerColumnModel = ruoloEnte.getIdEnte();
                break;
            }
            model.addAttribute("gestionePraticheColumnModel", configurationService.getGestionePraticheColumnModel(enteAcasoPerColumnModel, null));
            Boolean isProd =  configurationService.findByName(SessionConstants.IS_PRODUCTION, Boolean.class);
            model.addAttribute("isProd", isProd);
            model.addAttribute("paperworkWithoutProtocol", praticaDao.findPaperworkWithoutProtocolCount());
        } catch (Exception e) {
            Log.APP.error("Errore visualizzando le pratiche  nuove", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE, "Errore nell'esecuzione del controller /pratiche/nuove", e, null, utenteConnesso);
            erroriAction.saveError(errore);
        }
        return PRATICHE_NUOVE;
    }

    @RequestMapping("/pratiche/nuove/ajax")
    public String nuoveAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        GridPraticaNuovaBean json = new GridPraticaNuovaBean();
        try {
            json = praticheAction.getPraticheDaAssegnare(request, paginator);
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", error);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_AJAX, "Errore nell'esecuzione del controller /pratiche/nuove/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequiresGrant
    @RequestMapping("/pratiche/nuove/assegna")
    public String assegnaUtente(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Integer idPratica = Integer.valueOf(request.getParameter("id_pratica"));
        Pratica pratica = null;
        request.getSession().setAttribute("id_pratica", idPratica);
        try {
            if (request.getParameter("id_utente") != null && (!request.getParameter("id_utente").isEmpty())) {
                Integer idUtente = Integer.parseInt(request.getParameter("id_utente"));
                if (idUtente > 0) {
                    praticheAction.assegnaUtenteAPratica(idUtente, idPratica, utente);
                    return "redirect:/pratiche/nuove.htm";
                } else {
                    String msgError = messageSource.getMessage("error.user.wrong", null, Locale.getDefault());
                    Message error = new Message();
                    error.setMessages(Arrays.asList(msgError));
                    error.setError(Boolean.TRUE);
                    request.setAttribute("message", error);
                    Log.SQL.error(msgError);
                }
            }
            pratica = praticheService.getPratica(idPratica);
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            String abilitato = utentiService.couldCreateNewEvent(utenteConnesso, pratica).toString();
            model.addAttribute("abilitato", abilitato);
            String descrizionepratica = "Pratica selezionata: " + pratica.getIdentificativoPratica() + (StringUtils.isNotBlank(pratica.getProtocollo()) ? "  Fascicolo: " + pratica.getProtocollo() + "-" + pratica.getAnnoRiferimento() : "") + "  Oggetto: " + pratica.getOggettoPratica();
            model.addAttribute("descrizionepratica", descrizionepratica);
        } catch (Exception e) {
            String msgError = messageSource.getMessage("error.pratiche.assegnazione.utente", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            error.setError(Boolean.TRUE);
            request.setAttribute("message", error);
            Log.SQL.error(msgError, e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_ASSEGNA, "Errore nell'esecuzione del controller /pratiche/nuove/assegna", e, pratica, utente);
            erroriAction.saveError(errore);
        }
        return ASSEGNA_UTENTE;
    }

    @RequiresGrant
    @RequestMapping("/pratiche/nuove/assegna/ajax")
    public String assegnaUtenteAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        GridAssociaUtentiBean json = new GridAssociaUtentiBean();
        try {
            json = praticheAction.getUtentiDaAssociareAllaPratica(request, paginator);
        } catch (Exception e) {
            String msgError = messageSource.getMessage("error.pratiche.assegnazione.utente", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            error.setError(Boolean.TRUE);
            request.setAttribute("message", error);
            Log.SQL.error(msgError, e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_ASSEGNA_AJAX, "Errore nell'esecuzione del controller /pratiche/nuove/assegna/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequestMapping("/pratiche/nuove/apertura")
    public String apertura(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        try {
            UtenteDTO utente = utentiService.getUtenteConnessoDTO(request);
            List<Enti> enti = entiService.getListaEntiPerRicerca(utente);
            List<String> anni = praticheService.popolaListaAnni();
            List<LkStatoPratica> lookup = praticheService.getLookupsStatoPratica(StatoPratica.RICEVUTA);
            List<LkTipoUnita> lkTipoUnita = lookupDao.findAllLkTipoUnita();
            List<LkTipoPratica> lkTipoPratica = lookupDao.findSortedTipoPraticaByFlags(isRequestFrom(enti, TOWN_WITH_GRANTS));
            List<Utente> operatori = utentiService.findUtentiByEnte(enti);
            
            model.addAttribute("anniRiferimento", anni);
            model.addAttribute("entiRicerca", enti);
            model.addAttribute("statoPraticaRicerca", lookup);
            model.addAttribute("lkTipiUnita", lkTipoUnita);
            model.addAttribute("lkTipoPratica", lkTipoPratica);
            model.addAttribute("operatoriRicerca", operatori);
            
            Filter filter = (Filter) request.getSession().getAttribute("praticheNuoveApertura");
            if (filter == null) {
                filter = new Filter();
            }

            model.addAttribute("filtroRicerca", filter);
            Integer enteAcasoPerColumnModel = null;
            for (UtenteRuoloEnteDTO ruoloEnte : utente.getUtenteRuoloEnte()) {
                enteAcasoPerColumnModel = ruoloEnte.getIdEnte();
                break;
            }
            model.addAttribute("gestionePraticheColumnModel", configurationService.getGestionePraticheColumnModel(enteAcasoPerColumnModel, null));
        } catch (Exception e) {
            Log.APP.error("Errore nell'apertura delle pratiche", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_APERTURA, "Errore nell'esecuzione del controller /pratiche/nuove/apertura", e, null, utenteConnesso);
            erroriAction.saveError(errore);
        }

        return VISUALIZZA_PRATICHE;
    }

    @RequestMapping("/pratiche/nuove/apertura/ajax")
    public String aperturaAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridPraticaNuovaBean json = new GridPraticaNuovaBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json = praticheAction.getElencoPraticheDaAprire(request, paginator);
        } catch (Exception e) {
            String msgError = messageSource.getMessage("error.pratiche.visualizza.ricevute", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            Log.SQL.error(msgError, e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_APERTURA_AJAX, "Errore nell'esecuzione del controller /pratiche/nuove/apertura/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequestMapping("/pratiche/nuove/apertura/dettaglio")
    public String aperturaDettaglio(
            Model model,
            @RequestParam(value = "idPratica", required = false) Integer idPratica,
            @RequestParam(value = "fromGestioneEventi", required = false) boolean fromGestioneEventi,
            HttpServletRequest request,
            HttpServletResponse response) {
        String tab = request.getParameter("currentTab");
        Utente user = utentiService.getUtenteConnesso(request);
        Pratica praticafull = null;
        try {
            if (idPratica == null) {
                String idPraticaString = request.getParameter("id_pratica");
                if (idPraticaString != null) {
                    idPratica = Integer.valueOf(request.getParameter("id_pratica"));
                } else {
                    idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
                }
            }
            model.addAttribute("fromGestioneEventi", fromGestioneEventi);
            model.addAttribute("idPratica", idPratica);
            PraticaDTO pratica = praticheService.dettaglioXmlPratica(idPratica);
            praticafull = praticheService.getPratica(idPratica);
            it.wego.cross.dto.dozer.PraticaDTO praticaDTO = mapper.map(praticafull, it.wego.cross.dto.dozer.PraticaDTO.class);
            praticaDTO.setCartiglio(cartiglioService.createCartiglio(praticafull));
            //TODO: Lo mantengo ancora per tracciare le pratiche confermate e le anagrafiche.
            DettaglioPraticaDTO dettaglio = praticheService.getDettaglioPratica(praticafull);
            model.addAttribute("dettaglio", dettaglio);
            List<AnagraficaMinifiedDTO> anagraficheCollegate = dettaglio.getAnagrafiche();
            for (AnagraficaDTO an : pratica.getAnagraficaList()) {
                if ("S".equals(an.getDaRubrica())) {
                    an.setConfermata("S");
                }
                for (AnagraficaMinifiedDTO anagraficacoll : anagraficheCollegate) {
                    String codicefiscale = anagraficacoll.getCodiceFiscale();
                    //Risoluzione JIRA #789 - alla pratica è associata una anagrafica con CF e PIVA nulle
                    if (org.springframework.util.StringUtils.hasText(codicefiscale) && (codicefiscale.equals(an.getCodiceFiscale())) && (anagraficacoll.getIdRuolo().equals(an.getIdTipoRuolo()))) {
                        if (an.getIdAnagrafica() == null) {
                            an.setIdAnagrafica(anagraficacoll.getIdAnagrafica());
                        }
                        an.setCollegata("yes");
                        an.setDataInizioValidita(anagraficacoll.getDataInizioValidita());
                    }
                }
                if (an.getCollegata() == null || !an.getCollegata().equalsIgnoreCase("yes")) {
                    an.setConfermata(null);
                }
                if (an.getIdAnagrafica() == null) {
                    Anagrafica anagrafica = anagraficheService.findAnagraficaByCodFiscale(an.getCodiceFiscale());
                    if (anagrafica != null) {
                        an.setIdAnagrafica(anagrafica.getIdAnagrafica());
                    }
                }
            }
            MultiSubSessionManager.setAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA, idPratica);
            List<ProcessiEventi> eventi = workFlowService.findAvailableEvents(idPratica);
//            Map<Integer, String> data = lookupService.getTipoUnita();
//            model.addAttribute("TipoUnitaList", data);
            model.addAttribute("tipoSistemaCatastaleDtoList", lookupService.getTipoSistemaCatastaleDto());
            model.addAttribute("tipoUnitaDtoList", lookupService.getTipoUnitaDto());
            model.addAttribute("tipoParticellaDtoList", lookupService.getTipoParticellaDto());
            Map<Integer, String> data = lookupService.getDug();
            data = lookupService.getDug();
            model.addAttribute("DugList", data);
            List<TipoRuoloDTO> listruoli = lookupService.getTipoRuolo();
            model.addAttribute("tipoRuoloList", listruoli);
            List<TipoIndirizzoDTO> listTipiIndirizzo = lookupService.findAllLkTipoIndirizzo();
            model.addAttribute("tipoIndirizzoList", listTipiIndirizzo);
            data = lookupService.getTipoSistemaCatastale();
            model.addAttribute("gestioneAnagrafe", false);
            model.addAttribute("gestioneAnagrafePG", false);
            GestionePratica gestionePratica = pluginService.getGestionePratica(praticafull.getIdProcEnte().getIdEnte().getIdEnte());
            String esistenzaStradario = gestionePratica.getEsistenzaStradario(praticafull);
            String esistenzaRicercaCatasto = gestionePratica.getEsistenzaRicercaCatasto(praticafull);
            model.addAttribute("esistenzaStradario", esistenzaStradario);
            model.addAttribute("esistenzaRicercaCatasto", esistenzaRicercaCatasto);
            GestioneAnagrafePersonaFisica pf = pluginService.getGestioneAnagrafePersonaFisica(praticafull.getIdProcEnte().getIdEnte().getIdEnte(), praticafull.getIdComune().getIdComune());
            GestioneAnagrafePersonaGiuridica pg = pluginService.getGestioneAnagrafePersonaGiuridica(praticafull.getIdProcEnte().getIdEnte().getIdEnte(), praticafull.getIdComune().getIdComune());
            if (pf != null) {
                model.addAttribute("gestioneAnagrafe", pf.existRicercaAnagraficaPersonaFisica(praticafull));
            }
            if (pg != null) {
                model.addAttribute("gestioneAnagrafePG", pg.existRicercaAnagraficaPersonaGiuridica(praticafull));
            }

            UtenteDTO utente = utentiService.getUtenteConnessoDTO(request);
            List<Enti> enti = entiService.getListaEntiPerRicerca(utente);
            model.addAttribute("entiRicerca", enti);
            List<LkStatoPratica> lookup = praticheService.findAllLookupStatoPratica();
            model.addAttribute("statoPraticaRicerca", lookup);
            model.addAttribute("TipoSistemaCatastaleList", data);
            if (eventi != null && !eventi.isEmpty()) {
                model.addAttribute("idEvento", eventi.get(0).getIdEvento());
            } else {
                model.addAttribute("idEvento", null);
            }
            Pratica praticaSalvata = praticheService.getPratica(idPratica);
            if (!Utils.e(praticaSalvata.getResponsabileProcedimento())) {
                pratica.setResponsabileProcedimento(praticaSalvata.getResponsabileProcedimento());
            }

            List<it.wego.cross.dto.StatoPraticaDTO> findAllStatiPratica = lookupService.findAllStatiPratica();
            List<StatoPraticaDTO> statiPratica = usefulService.map(findAllStatiPratica, StatoPraticaDTO.class);
            model.addAttribute("statiPratica", statiPratica);

            model.addAttribute("ente_pratica", EntiSerializer.serializer(entiService.findByIdEnte(pratica.getIdEnte())));
            model.addAttribute("praticaDettaglio", pratica);
            
          //inserimento dataEvento per ogni allegato Sal2
            List<it.wego.cross.dto.dozer.AllegatoDTO> listaAllegatiNuovo = new ArrayList<it.wego.cross.dto.dozer.AllegatoDTO>();
            List<it.wego.cross.dto.dozer.AllegatoDTO> allegati = praticaDTO.getAllegati();
            for (it.wego.cross.dto.dozer.AllegatoDTO allegatoDTO : allegati) {
            	Integer idAllegato = allegatoDTO.getId();
            	PraticheEventiAllegati pea = new PraticheEventiAllegati();
            	pea = praticheService.findPraticheEventiAllegatiByIdAllegato(idAllegato);
            	PraticheEventi praticheEventi = pea.getPraticheEventi();
            	Date dataEvento = praticheEventi.getDataEvento();
            	allegatoDTO.setDataEvento(dataEvento);
            	allegatoDTO.setDescrizioneEvento(praticheEventi.getDescrizioneEvento());
            	listaAllegatiNuovo.add(allegatoDTO);
			}
            praticaDTO.setAllegati(listaAllegatiNuovo);
            //fine inserimento
            
            model.addAttribute("pratica", praticaDTO);
            List<AnagraficaDTO> anagrafiche = pratica.getAnagraficaList();
            List<AnagraficaDTO> richiedenti = new ArrayList<AnagraficaDTO>();
            List<AnagraficaDTO> beneficiari = new ArrayList<AnagraficaDTO>();
            for (AnagraficaDTO anagrafica : anagrafiche) {
                if ("Richiedente".equalsIgnoreCase(anagrafica.getDesTipoRuolo())) {
                    richiedenti.add(anagrafica);

                } else if ("Beneficiario".equalsIgnoreCase(anagrafica.getDesTipoRuolo())) {
                    beneficiari.add(anagrafica);
                }
            }
            model.addAttribute("richiedenti", richiedenti);
            model.addAttribute("beneficiari", beneficiari);
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            String abilitato = utentiService.couldCreateNewEvent(utenteConnesso, praticafull).toString();
            Integer idComune = null;
            if (praticaSalvata.getIdComune() != null) {
                idComune = praticaSalvata.getIdComune().getIdComune();
            }
            CustomViews datiCatastali = pluginService.getCustomViews(praticaSalvata.getIdProcEnte().getIdEnte().getIdEnte(), idComune);
            String datiCatastaliCustom = DEFAULT_PATH_DATI_CATASTALI;
            if (datiCatastali != null) {
                String custom = datiCatastali.getCustomDatiCatastaliAperturaPratica();
                if (custom != null && !custom.trim().equals("")) {
                    datiCatastaliCustom = custom;
                }
            }
            model.addAttribute("abilitato", abilitato);
            model.addAttribute("attivaRegistroImprese", Utils.True(configurationService.getCachedConfiguration(SessionConstants.ABILITAZIONE_REGISTRO_IMPRESE, praticaSalvata.getIdProcEnte().getIdEnte().getIdEnte(), idComune)));

            model.addAttribute("dati_catastali_custom", datiCatastaliCustom);
            model.addAttribute("indirizzi_intervento", DEFAULT_PATH_DATI_INDIRIZZI_INTERVENTO);

            String idCoverEditable = configurationService.getCachedConfiguration(ConfigurationConstants.IS_PRATICA_COPERTINA_MODIFICABILE, praticaDTO.getEnte().getIdEnte(), praticaDTO.getIdComune().getIdComune());
            model.addAttribute("abilitato", "TRUE".equalsIgnoreCase(abilitato));
            model.addAttribute("isCoverEditable", "TRUE".equalsIgnoreCase(abilitato) && "TRUE".equalsIgnoreCase(idCoverEditable));
            String isAllegatoModificabile = configurationService.getCachedConfiguration(ConfigurationConstants.IS_DESCRIZIONE_ALLEGATO_MODIFICABILE, praticaDTO.getEnte().getIdEnte(), praticaDTO.getIdComune().getIdComune());
            model.addAttribute("isAllegatoModificabile", "TRUE".equalsIgnoreCase(abilitato) && "TRUE".equalsIgnoreCase(isAllegatoModificabile));
            model.addAttribute("associatedTechnicianAgentTaxCode", praticheService.readAssociatedTechnicianAgentTaxCode(pratica.getIdPratica()));
        } catch (Exception e) {
            String msgError = messageSource.getMessage("error.pratiche.visualizza.ricevute", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            Log.SQL.error(msgError, e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_APERTURA_DETTAGLIO, "Errore nell'esecuzione del controller /pratiche/nuove/apertura/dettaglio", e, praticafull, user);
            erroriAction.saveError(errore);
        }
        if (!"".equals(tab) && tab != null) {
            model.addAttribute("currentTab", tab);
        } else {
            model.addAttribute("currentTab", TAB_DEFAULT_APERTURA);
        }
        String azione = request.getParameter("a");
        if (!"".equals(azione) && azione != null) {
            String messaggio = praticheNuoveAnagraficaMessaggi(azione.charAt(0));
            model.addAttribute("messaggiooperazioneeffettuata", messaggio);
        }
        return DETTAGLIO_PRATICA_APERTURA;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        // true passed to CustomDateEditor constructor means convert empty String to null
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("/pratiche/nuove/apertura/dettaglio/formDatiCatastaliAjax")
    public String formDatiCatastaliAjax(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "action", required = true) String azione,
            @ModelAttribute DatiCatastaliDTO datiCatastaliDTO,
            BindingResult result,
            ModelMap modelMap) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            GridDatoCatastaleBean json = new GridDatoCatastaleBean();
            pratica = praticheService.getPratica(datiCatastaliDTO.getIdPratica());
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            GestionePratica gp = pluginService.getGestionePratica(pratica.getIdProcEnte().getIdEnte().getIdEnte());
            if (!Utils.e(azione)) {
                if (azione.equals("leggiDatoCatastale")) {
                    json = praticheAction.findDatoCatastaleDaXML(datiCatastaliDTO);
                    model.addAttribute("json", json.toString());
                } else if (azione.equals("eliminaDatoCatastale")) {
                    systemEventAction.eliminaDatiCatastale(datiCatastaliDTO, utenteConnesso);
                    json.setMessages(Arrays.asList("Dato Catastale eliminato correttamente."));
                    model.addAttribute("json", json.toString());
                } else if (azione.equals("salvaDatoCatastale") || azione.equals("inserisciDatoCatastale")) {
                    if (Utils.e(datiCatastaliDTO.getIdTipoSistemaCatastale())) {
                        json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.idTipoSistemaCatastale", null, Locale.getDefault())));
                    }
                    if (!Utils.e(datiCatastaliDTO.getIdTipoSistemaCatastale())) {
                        LkTipoSistemaCatastale lk = lookupDao.findIdTipoCatastaleById(datiCatastaliDTO.getIdTipoSistemaCatastale());
                        if (lk != null) {
                            if (Constants.IDENTIFICATIVO_CATASTO.equals(lk.getIdTipoSistemaCatastale())) {
                                if (Utils.e(datiCatastaliDTO.getIdTipoUnita())) {
                                    json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.idTipoUnita", null, Locale.getDefault())));
                                }
                                if (Utils.e(datiCatastaliDTO.getFoglio())) {
                                    json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.foglio", null, Locale.getDefault())));
                                }
                                if (Utils.e(datiCatastaliDTO.getMappale())) {
                                    json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.mappale", null, Locale.getDefault())));
                                }
                                //Aggiunto il 22/06/2016
                                if (Utils.e(datiCatastaliDTO.getCategoria())) {
                                    json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.categoria", null, Locale.getDefault())));
                                }
                            }
                            if (Constants.IDENTIFICATIVO_TAVOLARE.equals(lk.getIdTipoSistemaCatastale())) {
                                if (Utils.e(datiCatastaliDTO.getIdTipoParticella())) {
                                    json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.idTipoParticella", null, Locale.getDefault())));
                                }
                            }
                        }
                    }
                    List<String> err = gp.controllaDatoCatastale(datiCatastaliDTO, pratica.getIdProcEnte().getIdEnte().getIdEnte());
                    if (err != null) {
                        json.setErrors(err);
                    }
                    if (json.getErrors() == null) {
                        datiCatastaliDTO.setIdTipoSistemaCatastale(datiCatastaliDTO.getIdTipoSistemaCatastale());
//                        json = praticheAction.salvaDatoCatastale(datiCatastaliDTO);
                        json.setDatoCatastale(systemEventAction.salvaDatoCatastale(datiCatastaliDTO, utenteConnesso));
                        json.setMessages(Arrays.asList("Dati Catastali salvati correttamente."));
                    }
                    model.addAttribute("json", json.toString());
                }
            } else {
                json = new GridDatoCatastaleBean();
                json.setErrors(Arrays.asList("Impossibile salvare i dati."));
                model.addAttribute("json", json.toString());
            }
        } catch (Exception e) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Impossibile salvare i dati."));
            request.setAttribute("message", errori);
            Log.SQL.error("/pratiche/nuove/apertura/dettaglio/formDatiCatastaliAjax", e);
            GridDatoCatastaleBean json = new GridDatoCatastaleBean();
            json.setErrors(Arrays.asList("Impossibile salvare i dati."));
            model.addAttribute("json", json.toString());
        }
        return AJAX_PAGE;
    }

    @RequestMapping("/pratiche/nuove/apertura/dettaglio/formIndirizziInterventoAjax")
    public String formIndirizziInterventoAjax(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "action", required = true) String azione,
            @ModelAttribute IndirizzoInterventoDTO indirizzoInterventoDTO,
            BindingResult result,
            ModelMap modelMap) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            GridIndirizzoInterventoBean json = new GridIndirizzoInterventoBean();
            pratica = praticheService.getPratica(indirizzoInterventoDTO.getIdPratica());
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            GestionePratica gp = pluginService.getGestionePratica(pratica.getIdProcEnte().getIdEnte().getIdEnte());
            if (!Utils.e(azione)) {
                if (azione.equals("leggiIndirizzoIntervento")) {
                    json = praticheAction.findIndirizzoInterventoDaXML(indirizzoInterventoDTO);
                    model.addAttribute("json", json.toString());
                } else if (azione.equals("eliminaIndirizzoIntervento")) {
                    systemEventAction.eliminaIndirizziIntervento(indirizzoInterventoDTO, utenteConnesso);
                    json.setMessages(Arrays.asList("Indirizzo eliminato correttamente."));
//                    json = praticheAction.eliminaIndirizziIntervento(indirizzoInterventoDTO, utenteConnesso);
                    model.addAttribute("json", json.toString());
                } else if (azione.equals("salvaIndirizzoIntervento") || azione.equals("inserisciIndirizzoIntervento")) {
                    List<String> err = gp.controllaIndirizzoIntervento(indirizzoInterventoDTO, pratica.getIdProcEnte().getIdEnte().getIdEnte());
                    if (err != null && err.size() > 0) {
                        json.setErrors(err);
                    }
                    if (json.getErrors() == null) {
                        json.setIndirizzo(systemEventAction.salvaIndirizziIntervento(indirizzoInterventoDTO, utenteConnesso));
                        json.setMessages(Arrays.asList("Indirizzo Intervento salvato correttamente."));
//                        json = praticheAction.salvaIndirizziIntervento(indirizzoInterventoDTO);
                    }
                    model.addAttribute("json", json.toString());
                } else {
                    json = new GridIndirizzoInterventoBean();
                    json.setErrors(Arrays.asList("Impossibile salvare i dati."));
                    model.addAttribute("json", json.toString());
                }
            } else {
                json = new GridIndirizzoInterventoBean();
                json.setErrors(Arrays.asList("Impossibile salvare i dati."));
                model.addAttribute("json", json.toString());
            }
        } catch (Exception e) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Impossibile salvare i dati."));
            request.setAttribute("message", errori);
            Log.SQL.error("/pratiche/nuove/apertura/dettaglio/formIndirizziInterventoAjax", e);
            GridIndirizzoInterventoBean json = new GridIndirizzoInterventoBean();
            json.setErrors(Arrays.asList("Impossibile salvare i dati."));
            model.addAttribute("json", json.toString());
        }
        return AJAX_PAGE;
    }

    @RequestMapping("/pratiche/nuove/apertura/dettaglio/ajax")
    public String aperturaDettaglioAjax(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            //            @ModelAttribute("paginator") JqgridPaginator paginator,
            @ModelAttribute("anagrafica") AnagraficaDTO anagrafica,
            @ModelAttribute PraticaDTO pratica,
            BindingResult result,
            ModelMap modelMap) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica praticaDb = null;
        try {
            String action = request.getParameter("action");
            List<String> jsonError = new ArrayList<String>();
            Integer idPratica = pratica.getIdPratica();
            if (idPratica != null) {
                praticaDb = praticheService.getPratica(idPratica);
            }
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            if (action != null && !action.equals("") && action.equals("trovaCittadinanza")) {
                String descrizione = request.getParameter("descrizione");
                GridCittadinanzaBean json = new GridCittadinanzaBean();
                AlphabeticValidatorImpl alphaValid = new AlphabeticValidatorImpl();
                if (alphaValid.Controlla(descrizione)) {
                    List<CittadinanzaDTO> cittadinanze = anagraficheService.findCittadinanze(descrizione);
                    json.setRows(cittadinanze);
                } else {
                    jsonError.add("ERROR");
                    json.setErrors(jsonError);
                }
                model.addAttribute("json", json.toString());
            } else if (action != null && !action.equals("") && action.equals("trovaNazionalita")) {
                String descrizione = request.getParameter("descrizione");
                GridNazionalitaBean json = new GridNazionalitaBean();
                AlphabeticValidatorImpl alphaValid = new AlphabeticValidatorImpl();
                if (alphaValid.Controlla(descrizione)) {
                    List<NazionalitaDTO> nazionalita = anagraficheService.findNazionalita(descrizione);
                    json.setRows(nazionalita);
                } else {
                    jsonError.add("ERROR");
                    json.setErrors(jsonError);
                }
                model.addAttribute("json", json.toString());
            } else if (action != null && !action.equals("") && action.equals("trovaProvincia")) {
                String descrizione = request.getParameter("descrizione");
                GridIdStringListBean json = new GridIdStringListBean();
                AlphabeticValidatorImpl alphaValid = new AlphabeticValidatorImpl();
                if (alphaValid.Controlla(descrizione)) {
                    Map<Integer, String> province = comuneService.trovaProvince(descrizione);
                    json.setRows(province);
                } else {
                    jsonError.add("ERROR");
                    json.setErrors(jsonError);
                }
                model.addAttribute("json", json.toString());

            } else if (action != null && !action.equals("") && action.equals("trovaComune")) {
                String descrizione = request.getParameter("descrizione");
                String date = request.getParameter("date");
                GridComuneBean json = new GridComuneBean();
                AlphabeticValidatorImpl alphaValid = new AlphabeticValidatorImpl();
                if (alphaValid.Controlla(descrizione)) {
                    List<ComuneDTO> comuni = comuneService.trovaComune(descrizione, date);
                    json.setRows(comuni);
                } else {
                    jsonError.add("ERROR");
                    json.setErrors(jsonError);
                }
                model.addAttribute("json", json.toString());

            } else if (action != null && !action.equals("") && action.equals("trovaFormaGiuridica")) {
                request.setCharacterEncoding("UTF-8");
                String descrizione = request.getParameter("descrizione");
                GridIdStringListBean json = new GridIdStringListBean();
                AlphabeticValidatorImpl alphaValid = new AlphabeticValidatorImpl();
                if (alphaValid.Controlla(descrizione)) {
                    Map<Integer, String> formeGiuridica = lookupService.getLookupFormaGiuridica(descrizione);
                    json.setRows(formeGiuridica);
                } else {
                    String msgError = messageSource.getMessage("error.formaGiuridica", null, Locale.getDefault());
                    jsonError.add(msgError);
                    json.setErrors(jsonError);
                }
                model.addAttribute("json", json.toString());

            } else if (action != null && !action.equals("") && action.equals("trovaTipoCollegio")) {
                String descrizione = request.getParameter("descrizione");
                GridIdStringListBean json = new GridIdStringListBean();
                AlphabeticValidatorImpl alphaValid = new AlphabeticValidatorImpl();
                if (alphaValid.Controlla(descrizione)) {
                    Map<Integer, String> formeGiuridica = lookupService.getLookupTipoCollegio(descrizione);
                    json.setRows(formeGiuridica);
                } else {
                    String msgError = messageSource.getMessage("error.formaGiuridica", null, Locale.getDefault());
                    jsonError.add(msgError);
                    json.setErrors(jsonError);
                }
                model.addAttribute("json", json.toString());

            } else if (action != null && !action.equals("") && action.equals("salvaRecapitoSingolo")) {
                JsonResponse json = new JsonResponse();
                BeanPropertyBindingResult br = new BeanPropertyBindingResult(pratica.getNotifica(), "recapito");
                validator.validate(pratica.getNotifica(), br);
                List<String> validatorErrors = isValid.recapito(br, pratica.getNotifica());
                if (validatorErrors != null) {
                    json.setSuccess(Boolean.FALSE);
                    json.setMessage(Joiner.on("<br />").join(validatorErrors));
                } else {
                    try {
                        praticheAction.salvaNotificaPratica(pratica);
                        json.setSuccess(Boolean.TRUE);
                        json.setMessage("Operazione eseguita correttamente");
                    } catch (Exception ex) {
                        json.setSuccess(Boolean.FALSE);
                        json.setMessage("Impossibile salvare l'indirizzo di notifica");
                    }
                }
                model.addAttribute("json", json.toString());
            } else if (action != null && !action.equals("") && action.equals("eliminaAnagraficaInXML")) {
                List<String> validatorErrors = new ArrayList<String>();
                GridIdStringListBean json = new GridIdStringListBean();
                String collegata = request.getParameter("collegata");
                if (anagrafica.getCounter() == null) {
                    validatorErrors.add("Errore anagrafica inesistente");
                    json.setErrors(validatorErrors);
                } else {
                    anagraficaAction.eliminaAnagraficadXML(anagrafica);
                }
                Log.APP.info("Collego l'attributo:" + json.toString());
                model.addAttribute("json", json.toString());
                if (collegata.equals("yes")) {
                    try {
                        Integer idAnagrafica = Integer.parseInt(request.getParameter("id"));
                        Integer idRuolo = Integer.parseInt(request.getParameter("ruolo"));
                        PraticaAnagrafica pa = praticheService.findPraticaAnagraficaByKey(idPratica, idAnagrafica, idRuolo);
                        systemEventAction.scollegaAnagraficaTransactional(pa, utenteConnesso);
                    } catch (Exception e) {
                        Log.APP.error("Impossibile scollegare la pratica", e);
                    }
                }
            } else if (action != null && !action.equals("") && action.equals("salvaAnagraficaInXML")) {
                Log.APP.info("Salvo anagrafica in XML:" + anagrafica);
                CodiceFiscaleValidatorImpl cfValid = new CodiceFiscaleValidatorImpl();
                PartitaIvaValidatorImpl ivaValid = new PartitaIvaValidatorImpl();
                validator.validate(anagrafica, result);
                List<String> validatorErrors = new ArrayList<String>();
                GridIdStringListBean json = new GridIdStringListBean();
                if ((!cfValid.Controlla(anagrafica.getCodiceFiscale())) && (!ivaValid.Controlla(anagrafica.getCodiceFiscale()))) {
                    validatorErrors.add("Codice Fiscale errato");
                    if (!Utils.e(anagrafica.getPartitaIva()) && !cfValid.Controlla(anagrafica.getPartitaIva())) {
                        validatorErrors.add("PartitaIva errata");
                    }
                    json.setErrors(validatorErrors);
                } else {
                    Integer idcount = anagraficaAction.aggiungiAnagraficadXMLTransactional(anagrafica);
                    Map<Integer, String> counter = new HashMap<Integer, String>();
                    counter.put(0, "" + idcount);
                    json.setRows(counter);
                }
                model.addAttribute("json", json.toString());
            } else if (action != null && !action.equals("") && action.equals("salvaAnagrafica")) {
                Log.APP.info("Salvo anagrafica:" + anagrafica);
                validator.validate(anagrafica, result);
                List<String> errors = new ArrayList<String>();
                List<FieldError> fe = result.getFieldErrors("tipoAnagrafica");
                JsonResponse json = new JsonResponse();
                if (fe.size() > 0) {
                    errors.add(result.getFieldErrors("tipoAnagrafica").get(0).getDefaultMessage());
                } else {
                    List<String> validatorErrors;

                    /**
                     * non salvare i recapiti che vengono aggiunti solo per
                     * incolonnare i recapiti vuoti.
                     */
                    List<RecapitoDTO> recapitiTMP = new ArrayList<RecapitoDTO>();
                    for (RecapitoDTO recapito : anagrafica.getRecapiti()) {
                        if (recapito != null && recapito.getIdTipoIndirizzo() != null) {
                            recapitiTMP.add(recapito);
                        }
                    }
                    anagrafica.setRecapiti(recapitiTMP);
                    validatorErrors = isValid.Anagrafica(result, anagrafica);
                    if (!validatorErrors.isEmpty()) {
                        errors.addAll(validatorErrors);
                    }

                    for (RecapitoDTO recapito : anagrafica.getRecapiti()) {
                        BeanPropertyBindingResult br = new BeanPropertyBindingResult(recapito, "recapito");
                        validator.validate(recapito, br);
                        validatorErrors = isValid.recapito(br, recapito);
                        if (validatorErrors != null) {
                            errors.addAll(validatorErrors);
                        }
                    }
                }
                AnagraficaDTO anagraficaDuplicata = new AnagraficaDTO();
                if (anagrafica.getIdAnagrafica() == null) {
                    anagraficaDuplicata = anagraficheService.cercaAnagraficaDuplicata(anagrafica);
                }
                if (anagraficaDuplicata.getIdAnagrafica() != null) {
                    String error = "Impossibile salvare l'anagrafica. La partita iva e' gia utilizzata da ";
                    if (anagraficaDuplicata.getTipoAnagrafica().equals(Constants.PERSONA_FISICA)) {
                        error += anagraficaDuplicata.getCodiceFiscale() + ": " + anagraficaDuplicata.getCognome() + " " + anagraficaDuplicata.getNome() + " ";
                    } else {
                        error += anagraficaDuplicata.getCodiceFiscale() + ": " + anagraficaDuplicata.getDenominazione();
                    }
                    errors.add(error);
                }
                if (errors.isEmpty()) {
                    Log.APP.info("Salva Anagrafica id:" + anagrafica.getIdAnagrafica() + " counter:" + anagrafica.getCounter());
                    anagrafica.setConfermata("1");
                    anagraficaAction.aggiornaAnagraficaStgTransactional(anagrafica, utenteConnesso);
                    json.setMessage("Anagrafica salvata correttamente");
                    json.setSuccess(Boolean.TRUE);
                } else {
                    json.setMessage(Joiner.on("<br/>").join(errors));
                    json.setSuccess(Boolean.FALSE);
                }
                model.addAttribute("json", json.toString());
            } else if (action != null && action.equals("salvaCFAnagraficaXML")) {
                GridAnagraficaBean json = new GridAnagraficaBean();
                CodiceFiscaleValidatorImpl cfValid = new CodiceFiscaleValidatorImpl();
                PartitaIvaValidatorImpl ivaValid = new PartitaIvaValidatorImpl();

                if ((!cfValid.Controlla(anagrafica.getCodiceFiscale())) && (!ivaValid.Controlla(anagrafica.getCodiceFiscale()))) {

                    json.setErrors(Arrays.asList("Codice fiscale errato"));
                } else {
                    anagraficaAction.salvaCFAnagraficaXML(anagrafica);
                }
                model.addAttribute("json", json.toString());
            } else if (action != null && action.equals("checkCF")) {
                CodiceFiscaleValidatorImpl cfValid = new CodiceFiscaleValidatorImpl();
                JsonResponse json = new JsonResponse();
                List<String> errors = new ArrayList<String>();
                if ((!cfValid.Controlla(anagrafica.getCodiceFiscale()))) {
                    errors.add("Codice fiscale errato");
                }
                if (!Strings.isNullOrEmpty(anagrafica.getPartitaIva())) {
                    if ("G".equals(anagrafica.getTipoAnagrafica())
                            || ("F".equals(anagrafica.getTipoAnagrafica()) && "S".equals(anagrafica.getFlgIndividuale()))) {
                        //La validazione sulla partita IVA la faccio solo se sono persona giuridica o solo se sono persona fisica 
                        //che partecipa come ditta individuale
                        PartitaIvaValidatorImpl ivaValid = new PartitaIvaValidatorImpl();
                        if ((!ivaValid.Controlla(anagrafica.getPartitaIva()))) {
                            errors.add("Partita IVA errata");
                        }
                    }
                }

                /* Requisito R1*/
						
                Anagrafica anag = anagraficheService.findAnagraficaByCodFiscale(anagrafica.getCodiceFiscale());
                /*if (anag != null && anagrafica.getIdAnagrafica() != null && !anag.getIdAnagrafica().equals(anagrafica.getIdAnagrafica())) {
                    errors.add("Esiste un'altra anagrafica con lo stesso codice fiscale. Scollega e ricollega la nuova anagrafica");
                }*/
                /* Requisito R1*/
/*                if (!Strings.isNullOrEmpty(anagrafica.getPartitaIva())) {
                    anag = anagraficheService.findAnagraficaByPartitaIva(anagrafica.getPartitaIva());
                    if (anag != null && anagrafica.getIdAnagrafica() != null && !anag.getIdAnagrafica().equals(anagrafica.getIdAnagrafica())) {
							 
                        errors.add("Esiste un'altra anagrafica con la stessa Partita IVA. Scollega e ricollega la nuova anagrafica");
																																																		  
								
                    }
                }*/
                /*Fine R1*/

                json.setSuccess(Boolean.TRUE);
                if (!errors.isEmpty()) {
                    json.setSuccess(Boolean.FALSE);
                    json.setMessage(Joiner.on(",").join(errors));
                }
						
															   
												
												 
				 
						
                model.addAttribute("json", json.toString());
            } else if (action != null && action.equals("getAnagrafica")) {
                try {
//                    validator.validate(anagrafica, result);
                    AnagraficaDTO anagraficaDB = anagraficheService.getAnagrafica(anagrafica);
                    if (anagraficaDB == null) {
                        Message error = new Message();
                        error.setMessages(Arrays.asList("Anagrafica non presente nella Banca Dati."));
                        request.setAttribute("message", errori);
                    }
                    GridAnagraficaBean json = anagraficaAction.getListaAnagraficheDaConfrontare(request, anagrafica, anagraficaDB);
                    model.addAttribute("json", json.toString());
                } catch (Exception ex) {
                    Log.APP.error("Non è stato possibile reperire le anagrafiche per il confronto", ex);
                    JsonResponse json = new JsonResponse();
                    json.setSuccess(Boolean.FALSE);
                    json.setMessage("Non è stato possibile reperire le anagrafiche per il confronto. Contattare un amministratore");
                    model.addAttribute("json", json.toString());
                }
            } else if (action != null && action.equals("isTipoPersonaModificabile")) {
                String tipoPersona = anagrafica.getIdTipoPersona();
                String codiceFiscale = anagrafica.getCodiceFiscale();
                Integer idAnagrafica = anagrafica.getIdAnagrafica();
                boolean isTipoPersonaModificabile = anagraficheService.isTipoPersonaModificabile(tipoPersona, idAnagrafica, codiceFiscale);
                JsonResponse json = new JsonResponse();
                json.setSuccess(isTipoPersonaModificabile);
                json.setMessage(isTipoPersonaModificabile ? "E' possibile modificare il tipo di persona" : "Non è possibile modificare il tipo di persona");
                model.addAttribute("json", json.toString());
            }
        } catch (Exception e) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Impossibile salvare i dati."));
            request.setAttribute("message", errori);
            Log.SQL.error("/pratiche/nuove/apertura/dettaglio/ajax", e);
            GridAnagraficaBean json = new GridAnagraficaBean();
            json.setErrors(Arrays.asList("Impossibile salvare i dati."));
            model.addAttribute("json", json.toString());
        }
        return AJAX_PAGE;
    }

    /**
     *
     * apertura pratica assegnata ad utente ma non caricata nel db static mode
     *
     * @author CS
     * @param model
     * @param request
     * @param paginator
     * @param idPratica
     * @param response
     * @param pratica
     * @param redirectAttributes
     * @return
     */
    @RequiresGrant
    @RequestMapping("/pratiche/nuove/apertura/crea")
    public ModelAndView aperturaCrea(
            Model model, HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "id_pratica", required = true) Integer idPratica,
            @ModelAttribute("paginator") JqgridPaginator paginator,
            @ModelAttribute("pratica") PraticaDTO pratica,
            RedirectAttributes redirectAttributes) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica praticaDb = null;
        redirectAttributes.addAttribute(MultiSubSessionManager.SESSION_PARAMETER, MultiSubSessionManager.getSessionId(request));
        try {
            praticaDb = praticheService.getPratica(idPratica);
            model.addAttribute("id_pratica", request.getParameter("id_pratica"));
            model.addAttribute("id_evento", request.getParameter("id_evento"));

// MODIFICO LO STATO DELLA PRATICA AD UN NUOVO STATO "ELABORAZIONE IN CORSO" PER BYPASSARE IL PROBLEMA DEL CAMBIO STATO ALLA FINE DEL PROCESSO            
            LkStatoPratica lkStatoPratica = lookupDao.findStatoPraticaByCodice(Constants.STATO_PRATICA_IN_PROGRESS);

            praticheAction.aggiornaPratica(pratica, lkStatoPratica);
            MultiSubSessionManager.setAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA, idPratica);
            return new ModelAndView("redirect:/pratica/evento/index.htm");
        } catch (Exception e) {
            Message error = new Message();
            error.setError(Boolean.TRUE);
            error.setMessages(Arrays.asList("Impossibile compilare la pratica per l'evento di apertura."));
            request.setAttribute("message", errori);
            Log.SQL.error("/pratiche/nuove/apertura/dettaglio/ajax", e);
        }
        redirectAttributes.addAttribute("idPratica", idPratica);
        
        return new ModelAndView("redirect:/pratiche/nuove/apertura/dettaglio.htm");
//        return aperturaDettaglio(model, request, response);
    }

    //in caso di errore all'apertura della pratica.
    @RequiresGrant
    @RequestMapping("/pratiche/nuove/apertura/crea/ajax")
    public String aperturaCrea(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("paginator") JqgridPaginator paginator,
            @ModelAttribute("anagrafica") AnagraficaDTO anagrafica,
            @ModelAttribute PraticaDTO pratica,
            BindingResult result,
            ModelMap modelMap) {
        return aperturaAjax(model, request, response, paginator);
    }

    @RequestMapping("/pratiche/gestisci")
    public String gestione(Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
            
            List<UtenteRuoloEnteDTO> utenteRuoloEnte = connectedUser.getUtenteRuoloEnte();
            List<Enti> enti = entiService.getListaEntiPerRicerca(connectedUser);
            model.addAttribute("entiRicerca", enti);
            List<Utente> operatori = utentiService.findUtentiByEnte(enti);
            model.addAttribute("operatoriRicerca", operatori);
            List<LkStatoPratica> lookup = praticheService.findAllLookupStatoPratica();
            List<LkTipoUnita> lkTipoUnita = lookupDao.findAllLkTipoUnita();
            List<LkTipoParticella> lkTipoParticella = lookupDao.findAllLkTipoParticella();
            List<LkTipoSistemaCatastale> lkTipoSistemaCatastale = lookupDao.findAllTipoCatastale();
            List<LkTipoProcedimentoSuap> lkTipoProcedimentoSUAP = lookupDao.findAllTipoProcedimentoSuap();
            List<LkTipoInterventoSuap> lkTipoInterventoSuap = lookupDao.findAllTipoInterventoSuap();
            List<LkClassificazioneProcedimento> lkClassificazioneProcedimento = lookupDao.findAllClassificazioneProcedimento();
            List<LkTipoPratica> lkTipoPratica = lookupDao.findSortedTipoPraticaByFlags(isRequestFrom(enti, TOWN_WITH_GRANTS));
            List<String> anniRiferimento = praticheService.popolaListaAnni();
            model.addAttribute("utenteConnesso", connectedUser);
            model.addAttribute("statoPraticaRicerca", lookup);
            model.addAttribute("anniRiferimento", anniRiferimento);
            model.addAttribute("lkTipiCatasto", lkTipoSistemaCatastale);
            model.addAttribute("lkTipiUnita", lkTipoUnita);
            model.addAttribute("lkTipiParticella", lkTipoParticella);
            model.addAttribute("lkTipoProcedimentoSUAP", lkTipoProcedimentoSUAP);
            model.addAttribute("lkTipoInterventoSUAP", lkTipoInterventoSuap);
            model.addAttribute("lkTipoClassificazioneProcedimento", lkClassificazioneProcedimento);
            model.addAttribute("lkTipoPratica", lkTipoPratica);

            Integer enteAcasoPerColumnModel = null;
            for (UtenteRuoloEnteDTO ruoloEnte : connectedUser.getUtenteRuoloEnte()) {
                enteAcasoPerColumnModel = ruoloEnte.getIdEnte();
                break;
            }
            model.addAttribute("gestionePraticheColumnModel", configurationService.getGestionePraticheColumnModel(enteAcasoPerColumnModel, null));
            Filter filter = (Filter) request.getSession().getAttribute("praticheGestisci");
            if (filter == null) {
                filter = new Filter();
            }
            if ("statoPratica".equalsIgnoreCase(filter.getOrderColumn())) {
                filter.setOrderColumn("idStatoPratica");
            }
            model.addAttribute("filtroRicerca", filter);
            String back = request.getParameter("back");
            model.addAttribute("back", back != null && back.length() > 0);
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI, "Errore nell'esecuzione del controller /pratiche/gestisci", e, null, utente);
            erroriAction.saveError(errore);

        }
        return HOME_PRATICA;
    }

    @RequestMapping("/pratiche/gestisci/ajax")
    public String gestioneAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridPraticaNuovaBean json = new GridPraticaNuovaBean();
        //LOR
      //  Filter filterDMS = new Filter();
        //END
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json = praticheAction.getPraticheDaGestire(request, paginator);
            
         //   filterDMS = (Filter) request.getSession().getAttribute("praticheGestisci");
            
           // DMSClientDao daoDms = new DMSClientDao();
            
            ArrayList<PraticaNuova> rows_Cross_DMS = new ArrayList<PraticaNuova>();
            
           //  List<PraticaNuova> rowsPraticheDMS = daoDms.ricercaPratiche(filterDMS);
            
            List<PraticaNuova> rowsPraticheCross = json.getRows();
            
            rows_Cross_DMS.addAll(rowsPraticheCross);

        	/*for(PraticaNuova praDms : rowsPraticheDMS) {

        		int praticaNuovaDms_gia_esistente_in_cross = 0;

        		for(PraticaNuova praCross : rowsPraticheCross) {
            		if(praCross.getIdentificativoPratica().toLowerCase() ==  praDms.getIdentificativoPratica().toLowerCase()) {
            			praticaNuovaDms_gia_esistente_in_cross = 1;
            		}        			
        		}
        		if(praticaNuovaDms_gia_esistente_in_cross == 0) {
            		rows_Cross_DMS.add(praDms);	
            	}
        	}       */     
            
            json.setRows(rows_Cross_DMS);
            
            
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI_AJAX, "Errore nell'esecuzione del controller /pratiche/gestisci/ajax", e, null, utente);
            erroriAction.saveError(errore);
            throw new RuntimeException(e);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    private Integer getIdPratica(String idPraticaString) throws ParseException
	{
		try
		{
			return Integer.parseInt(idPraticaString);
		}
		catch (NumberFormatException nfe) {
			Log.APP.warn("NumberFormatException nel recupero ID pratica {}", nfe.getMessage());
			return getIdFromString(idPraticaString);
		}
	}
	private Integer getIdFromString( String idPraticaString ) throws ParseException
	{
		NumberFormat nf = null;
		if( idPraticaString.indexOf(",") > -1 )
		{
			nf = NumberFormat.getInstance(Locale.ENGLISH);
		}
		else
		{
			nf = NumberFormat.getInstance(Locale.ITALIAN);
		}
		return nf.parse(idPraticaString).intValue();
	}
	
	@RequiresGrant
    @RequestMapping("/pratica/comunicazione/cambia_tecnico_procuratore")
    public ModelAndView changeAssociatedTechnicianAgent(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
    	Pratica pratica = null;
    	Integer idPratica = null;
    	Integer idTechnicianAgent = null;
    	boolean canContinue = true;
    	PraticheEventi praticheUtenti = null;
    	String taxCodeTechnicianAgent = request.getParameter("technician_tax_code");
    	String idTechnicianAgentString = request.getParameter("technician_id");
		String idPraticaString = request.getParameter("id_pratica");
		
		Log.APP.info("ID pratica ricevuto " + idPraticaString);
        Log.APP.info("Codice fiscale tecnico procuratore associato ricevuto " + taxCodeTechnicianAgent);
        Log.APP.info("ID anagrafica tecnico procuratore associato ricevuto " + idTechnicianAgentString);
        
        try {
        	idPratica = Integer.parseInt(idPraticaString);
        	idTechnicianAgent = Integer.parseInt(idTechnicianAgentString);
        	pratica = praticheService.getPratica(idPratica);
        	String oldTechnician = praticheService.readAssociatedTechnicianAgentTaxCode(pratica.getIdPratica());
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        	ProcessiEventi eventoProcesso = processiService.findProcessiEventiByCodEventoIdProcesso(AnaTipiEvento.MODIFICA_TECNICO_PROCURATORE, pratica.getIdProcesso());
        	
        	Log.APP.info("add PraticaEvento: " + eventoProcesso);
        	praticheUtenti = praticheAction.addEvent(utenteConnesso, pratica, eventoProcesso, oldTechnician, taxCodeTechnicianAgent);
        } catch (Exception e) {
            Log.APP.error("Errore nella creazione evento per la modifica del tecnico procuratore associato", e);
            ErroreDTO error = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_DETTAGLIO_MESSAGGI, "Errore nell'esecuzione del controller /pratica/comunicazione/cambia_tecnico_procuratore", e, null, null);
            erroriAction.saveError(error);
            redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, "E#Errore durante la creazione evento per la modifica del tecnico procuratore associato, riprovare; se il problema persiste contattare l'assistenza");
            canContinue = false;
        }

        if(canContinue) {
	    	try { 
	            praticheService.changeAssociatedTechnicianAgent(pratica.getIdentificativoPratica(), taxCodeTechnicianAgent);
	            praticheService.changeAssociatedTechnicianAgentBackOffice(pratica.getIdPratica(), idTechnicianAgent);
	        	redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, "O#Modifica del tecnico operatore associato effettuata");
	        } catch (Exception e) {
	            Log.APP.error("Errore nella modifica del tecnico procuratore associato", e);
	            ErroreDTO error = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_DETTAGLIO_MESSAGGI, "Errore nell'esecuzione del controller /pratica/comunicazione/cambia_tecnico_procuratore", e, null, null);
	            erroriAction.saveError(error);
	            redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, "E#Errore durante la modifica del tecnico procuratore associato, riprovare; se il problema persiste contattare l'assistenza");
	            try {
					praticheAction.deleteEvent(praticheUtenti);
				} catch (Exception e1) {
					Log.APP.error("Errore Cancellazione PraticaEvento: " +e.getMessage());
				}
	        }
        }
        redirectAttributes.addAttribute(MultiSubSessionManager.SESSION_PARAMETER, MultiSubSessionManager.getSessionId(request));
        
    	return new ModelAndView(REDIRECT_DETTAGLIO_PRATICA_WITH_ID + idPratica);
    }

    @RequestMapping("/pratiche/dettaglio")
    public String dettaglio(Model model,
            HttpServletRequest request,
            HttpServletResponse response, 
            @ModelAttribute("currentTab") String tab, 
            @ModelAttribute(OPERAZIONE_EFFETTUATA) String operazioneEffettuata) throws Exception {
    	String idPraticaString = request.getParameter("id_pratica");
    	String fromImportXmlString = request.getParameter("fromImportXml");
        boolean fromImportXml = false;
		fromImportXml=(fromImportXmlString!=null);																   		  
        HttpSession session = request.getSession();
        Integer idPratica;
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        if (idPraticaString != null) {
            idPratica = getIdPratica(idPraticaString);
            MultiSubSessionManager.setAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA, idPratica);
        } else {
            idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
        }
        try {
            model.addAttribute("idPatica", idPratica);
            pratica = praticheService.getPratica(idPratica);
            if (pratica.getIdStatoPratica().getGrpStatoPratica().equals(StatoPratica.RICEVUTA)) {
                request.setAttribute("idPratica", idPratica);
                return "redirect:/pratiche/nuove/apertura/dettaglio.htm?fromGestioneEventi=true&"+MultiSubSessionManager.SESSION_PARAMETER+"="+MultiSubSessionManager.getSessionId(request);
            } else {
                DettaglioPraticaDTO dettaglio = praticheService.getDettaglioPratica(pratica);
                List<MessaggioDTO> messaggi = messaggiAction.findConnectedMessages(pratica);
                boolean couldCreateNewEvent = utentiService.couldCreateNewEvent(utente, pratica);
                
                it.wego.cross.dto.dozer.PraticaDTO praticaDTO = mapper.map(pratica, it.wego.cross.dto.dozer.PraticaDTO.class);
                praticaDTO.setCartiglio(cartiglioService.createCartiglio(pratica));
                /*ArrayList<it.wego.cross.dto.dozer.NotaDTO> praNoteDms = new ArrayList<it.wego.cross.dto.dozer.NotaDTO>();
                for(NotaDTO note : dettaglio.getNotePratica()) {
                    it.wego.cross.dto.dozer.NotaDTO notaDozerDTO = mapper.map(note, it.wego.cross.dto.dozer.NotaDTO.class);
                	praNoteDms.add(notaDozerDTO);
                }
                praticaDTO.setNotePratica(praNoteDms);*/
                
                //List<ProcedimentoEnteDTO> procedimentoEnteDTO = praticaDTO.getEndoProcedimentiList();
                
                List<it.wego.cross.dto.dozer.ProcedimentoEnteDTO> procedimentoEnteDTO = new ArrayList<it.wego.cross.dto.dozer.ProcedimentoEnteDTO>();
                for (it.wego.cross.dto.ProcedimentoDTO pp : dettaglio.getProcedimenti())
                {
                	it.wego.cross.dto.dozer.ProcedimentoEnteDTO procEnteDTO = new ProcedimentoEnteDTO();
                	it.wego.cross.dto.dozer.ProcedimentoDTO p2dto = new it.wego.cross.dto.dozer.ProcedimentoDTO();
                	p2dto.setDescrizione(pp.getDesProcedimento());			                
	                p2dto.setTermini(pp.getTermini());	                
	                procEnteDTO.setProcedimento(p2dto);
	                String provenienza = pp.getProvenienza();
	                procEnteDTO.setProvenienza(provenienza);
	                procedimentoEnteDTO.add(procEnteDTO);
                }
                praticaDTO.setEndoProcedimentiList(procedimentoEnteDTO);

                List<AnagraficaMinifiedDTO> richiedenti = new ArrayList<AnagraficaMinifiedDTO>();
                List<AnagraficaMinifiedDTO> beneficiari = new ArrayList<AnagraficaMinifiedDTO>();
                for (AnagraficaMinifiedDTO anagrafica : dettaglio.getAnagrafiche()) {
                    String ruolo = anagrafica.getRuolo();
                    if (ruolo.equals("Richiedente")) {
                        richiedenti.add(anagrafica);
                    } else if (ruolo.equals("Beneficiario")) {
                        beneficiari.add(anagrafica);
                    }
                }
                Utente utenteConnesso = utentiService.getUtenteConnesso(request);
                String abilitato = utentiService.couldCreateNewEvent(utenteConnesso, pratica).toString();
                model.addAttribute("beneficiari", beneficiari);
                model.addAttribute("richiedenti", richiedenti);
                
                //inserimento dataEvento per ogni allegato Sal2
                List<it.wego.cross.dto.dozer.AllegatoDTO> listaAllegatiNuovo = new ArrayList<it.wego.cross.dto.dozer.AllegatoDTO>();
                List<it.wego.cross.dto.dozer.AllegatoDTO> allegati = praticaDTO.getAllegati();
                for (it.wego.cross.dto.dozer.AllegatoDTO allegatoDTO : allegati) {
                	Boolean isConsultazione = ((boolean) request.getSession().getAttribute(SessionConstants.ABILITAZIONE_CONSULTAZIONE));
                	if (!isConsultazione || !Utils.e(allegatoDTO.getProtocollo())) {
	                	Integer idAllegato = allegatoDTO.getId();
	                	PraticheEventiAllegati pea = new PraticheEventiAllegati();
	                	pea = praticheService.findPraticheEventiAllegatiByIdAllegato(idAllegato);
	                	PraticheEventi praticheEventi = pea.getPraticheEventi();
	                	Date dataEvento = praticheEventi.getDataEvento();
	                	allegatoDTO.setDataEvento(dataEvento);
	                	allegatoDTO.setDescrizioneEvento(praticheEventi.getDescrizioneEvento());
	                	listaAllegatiNuovo.add(allegatoDTO);
                	}
				}
                //Allegati Civilia
               /* List<AllegatoDTO> allegati_civilia = dettaglio.getAllegati();
                for (AllegatoDTO allegatoDTO : allegati_civilia) {
                	if(allegatoDTO.getProvenienza()!=null) {
                		if(allegatoDTO.getProvenienza().equals(it.wego.cross.civilia.Costants.CIVILIA)) {
                        	it.wego.cross.dto.dozer.AllegatoDTO allegatNuovo = new it.wego.cross.dto.dozer.AllegatoDTO();
                        	allegatNuovo.setId(1);
                        	allegatNuovo.setProvenienza(it.wego.cross.civilia.Costants.CIVILIA);
                        	allegatNuovo.setDescrizione(allegatoDTO.getDescrizione());
                        	allegatNuovo.setNomeFile(allegatoDTO.getNomeFile());
                        	allegatNuovo.setTipoFile(allegatoDTO.getTipoFile());
                        	allegatNuovo.setPathFile(allegatoDTO.getPathFile());
                        	allegatNuovo.setIdFileEsterno(allegatoDTO.getIdFileEsterno());
                        	//allegatNuovo.setFile(allegatoDTO.getFile());          	
                        	//allegatNuovo.setDataEvento(dataEvento);
                        	//allegatNuovo.setDescrizioneEvento(praticheEventi.getDescrizioneEvento());
                        	listaAllegatiNuovo.add(allegatNuovo);                			
                		}
                	}
                }*/
              //Allegati DMS
             /*   List<AllegatoDTO> allegati_dms = dettaglio.getAllegati();
                for (AllegatoDTO allegatoDTO : allegati_dms) {
                	if(allegatoDTO.getProvenienza()!=null) {
                		if(allegatoDTO.getProvenienza().equals(it.wego.cross.civilia.Costants.DMS)) {
                        	it.wego.cross.dto.dozer.AllegatoDTO allegatNuovo = new it.wego.cross.dto.dozer.AllegatoDTO();
                        	allegatNuovo.setId(allegatoDTO.getIdAllegato());
                        	allegatNuovo.setProvenienza(it.wego.cross.civilia.Costants.DMS);
                        	allegatNuovo.setDescrizione(allegatoDTO.getDescrizione());
                        	allegatNuovo.setNomeFile(allegatoDTO.getNomeFile());
                        	allegatNuovo.setTipoFile(allegatoDTO.getTipoFile());
                        	allegatNuovo.setPathFile(allegatoDTO.getPathFile());
                        	allegatNuovo.setIdFileEsterno(allegatoDTO.getIdFileEsterno());
                        	//allegatNuovo.setFile(allegatoDTO.getFile());          	
                        	//allegatNuovo.setDataEvento(dataEvento);
                        	//allegatNuovo.setDescrizioneEvento(praticheEventi.getDescrizioneEvento());
                        	listaAllegatiNuovo.add(allegatNuovo);                			
                		}
                	}
                }*/
                
                // gestione con prot_suap
               if (pratica.getProt_suap() !=null && !(pratica.getProt_suap()).isEmpty()) {
            	   if(praticaDTO.getEndoProcedimentiList()!=null) {
            		   praticaDTO.getEndoProcedimentiList().clear();
            	   }
            	   if (pratica.getPraticaProcedimentiList()!=null){
               	   	
                   	int numeroProcedimenti = pratica.getPraticaProcedimentiList().size();
                   	for (int i=0;i<numeroProcedimenti;i++) {
                   		PraticaProcedimenti pp = pratica.getPraticaProcedimentiList().get(i);
                   		Procedimenti procedimenti = procedimentiDao.findProcedimentoByIdProc(pp.getProcedimenti().getIdProc());
                   		ProcedimentoDTO procedimento = new ProcedimentoDTO();
                   		procedimento.setTermini(procedimenti.getTermini());
                   		ProcedimentoEnteDTO endoProcedimento = new ProcedimentoEnteDTO();
                   		EnteDTO ente = new EnteDTO();
                   		Enti e = entiDao.findByIdEnte(pratica.getPraticaProcedimentiList().get(i).getEnti().getIdEnte());
                   		ente.setIdEnte(pratica.getPraticaProcedimentiList().get(i).getEnti().getIdEnte());
                   		ente.setDescrizione(e.getDescrizione());
                   		
                   		procedimento.setIdProc(pratica.getPraticaProcedimentiList().get(i).getProcedimenti().getIdProc());
                   		ProcedimentiTesti pt = procedimentiDao.findProcedimentiTestiByPK(pratica.getPraticaProcedimentiList().get(i).getProcedimenti().getIdProc(), 1);
                   		procedimento.setDescrizione(pt.getDesProc());
                   		
                   		endoProcedimento.setProcedimento(procedimento);
                   		endoProcedimento.setEnte(ente);
                   		if(pp.getLkTipoEndoProcedimentoSuap() != null)
                   			endoProcedimento.setPraticaProcedimentoDescrizione(pp.getLkTipoEndoProcedimentoSuap().getDescrizioneEndoProcedimento());
                   		praticaDTO.getEndoProcedimentiList().add(endoProcedimento);
                   	}
                   }
            	   if(pratica.getIdTipoInterventoSuap()!=null) {
                	   praticaDTO.setTipoInterventoSuap(pratica.getIdTipoInterventoSuap().getDescrizioneTipoInterventoSuap());
                	   praticaDTO.setOggettoPratica(pratica.getOggettoPratica() + "-"+pratica.getIdTipoInterventoSuap().getDescrizioneTipoInterventoSuap());
            	   }
                   if(pratica.getIdTipoProcedimentoSuap()!=null)
                   		praticaDTO.setTipoProcedimentoSuap(pratica.getIdTipoProcedimentoSuap().getDescrizioneCrossSuap());
                }
                	
                praticaDTO.setAllegati(listaAllegatiNuovo);
                //fine inserimento
               
                model.addAttribute("associatedTechnicianAgentTaxCode", praticheService.readAssociatedTechnicianAgentTaxCode(pratica.getIdPratica()));
                model.addAttribute("pratica", praticaDTO);
                model.addAttribute("couldCreateNewEvent", couldCreateNewEvent);
                model.addAttribute("dettaglio", dettaglio);
                model.addAttribute("messaggi", messaggi);
				model.addAttribute("fromImportXml",fromImportXml);												   
                model.addAttribute("messaggio", new MessaggioDTO());
                model.addAttribute("ritornaAidPratica", request.getParameter("ritornaAidPratica"));
                GestionePratica gestionePratica = pluginService.getGestionePratica(pratica.getIdProcEnte().getIdEnte().getIdEnte());
                String esistenzaStradario = gestionePratica.getEsistenzaStradario(pratica);
                model.addAttribute("esistenzaStradario", esistenzaStradario);
                String esistenzaRicercaCatasto = gestionePratica.getEsistenzaRicercaCatasto(pratica);
                model.addAttribute("esistenzaRicercaCatasto", esistenzaRicercaCatasto);
//                Map<Integer, String> data = lookupService.getTipoUnita();
//                model.addAttribute("TipoUnitaList", data);
                model.addAttribute("tipoSistemaCatastaleDtoList", lookupService.getTipoSistemaCatastaleDto());
                model.addAttribute("tipoUnitaDtoList", lookupService.getTipoUnitaDto());
                model.addAttribute("tipoParticellaDtoList", lookupService.getTipoParticellaDto());
                Map<Integer, String> data = lookupService.getDug();
                model.addAttribute("DugList", data);
//                model.addAttribute("formdata", ImmutableMap.of("pratica", praticaDTO));
                List<it.wego.cross.dto.StatoPraticaDTO> findAllStatiPratica = lookupService.findAllStatiPratica();
                List<StatoPraticaDTO> statiPratica = usefulService.map(findAllStatiPratica, StatoPraticaDTO.class);
                model.addAttribute("statiPratica", statiPratica);

                String idCoverEditable = configurationService.getCachedConfiguration(ConfigurationConstants.IS_PRATICA_COPERTINA_MODIFICABILE, praticaDTO.getEnte().getIdEnte(), praticaDTO.getIdComune().getIdComune());
                String isAllegatoModificabile = configurationService.getCachedConfiguration(ConfigurationConstants.IS_DESCRIZIONE_ALLEGATO_MODIFICABILE, praticaDTO.getEnte().getIdEnte(), praticaDTO.getIdComune().getIdComune());

                model.addAttribute("abilitato", "TRUE".equalsIgnoreCase(abilitato));
                model.addAttribute("isCoverEditable", "TRUE".equalsIgnoreCase(abilitato) && "TRUE".equalsIgnoreCase(idCoverEditable));
                model.addAttribute("isAllegatoModificabile", "TRUE".equalsIgnoreCase(abilitato) && "TRUE".equalsIgnoreCase(isAllegatoModificabile));

                String gestioneDirittiSegreteriaFormContent = formService.getFormContent("it.wego.cross.forms.common.GestioneDirittiSegreteria", request, model, null, Boolean.FALSE, Boolean.TRUE);
                model.addAttribute("gestioneDirittiSegreteriaForm", gestioneDirittiSegreteriaFormContent);

                
                //Visualizza tasto assegna
                boolean visualizzaAssegnazione=false;
                String visualizza=configurationService.getCachedPluginConfiguration(VISUALIZZA_ASSEGNA, praticaDTO.getEnte().getIdEnte(), praticaDTO.getIdComune().getIdComune());
                if(org.springframework.util.StringUtils.hasText(visualizza) && visualizza.equals("TRUE")){
                	visualizzaAssegnazione=true;
                }
                model.addAttribute("visualizzaAssegnazione", visualizzaAssegnazione);
                Boolean isProd =  configurationService.findByName(SessionConstants.IS_PRODUCTION, Boolean.class);
                model.addAttribute("isProd", isProd);
            }
        } catch (Exception ex) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Si sono verificati degli errori"));
            request.setAttribute("message", errori);
            Log.APP.error("Si sono verificati degli errori", ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_DETTAGLIO, "Errore nell'esecuzione del controller /pratiche/dettaglio", ex, pratica, utente);
            erroriAction.saveError(errore);
        }
        String ttab = request.getParameter("currentTab");
        if (!"".equals(ttab) && ttab != null) {
            model.addAttribute("currentTab", ttab);
        } else {
        	if ((boolean) request.getSession().getAttribute(SessionConstants.ABILITAZIONE_CONSULTAZIONE)) {
        		model.addAttribute("currentTab", TAB_DEFAULT_APERTURA);
        	}else if ((!"".equals(tab) && tab != null)) {
                model.addAttribute("currentTab", tab);
            } else {
                model.addAttribute("currentTab", TAB_DEFAULT);
            }
        }
        String azione = request.getParameter("a");
        if (!"".equals(azione) && azione != null) {
            String messaggio = praticheNuoveAnagraficaMessaggi(azione.charAt(0));
            model.addAttribute("messaggiooperazioneeffettuata", messaggio);
        }
        model.addAttribute("id_pratica", idPratica);
        if(!Utils.e(operazioneEffettuata)) {
        	Message message = decompileStringMessage(operazioneEffettuata);
        	if(message.getError()) {
        		model.addAttribute("message", message);
        	}
        	else {
        		model.addAttribute("messaggiooperazioneeffettuata", message.getMessages().isEmpty()? "": message.getMessages().get(0));
        	}
        }
        return DETTAGLIO_PRATICA;
    }

    @RequestMapping("/pratiche/dettaglio/eventi/ajax")
    public String listAjax(Model model,
            HttpServletRequest request,
            HttpServletResponse response, 
            @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridPraticaEventoBean json = new GridPraticaEventoBean();
        try {
        	json = praticheAction.getPraticaEventoGrid(request, paginator, MultiSubSessionManager.getSessionId(request));
            String jsonString = json.toString();
            model.addAttribute("json", jsonString);
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca.";
            json.setErrors(Arrays.asList(msg));
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX_PAGE;
    }

    @RequiresGrant
    @RequestMapping(value = "/pratiche/dettaglio/aggiungiProcedimento/inserisci", method = RequestMethod.POST)
    public String aggiungiProcedimentoInserisci(Model model, HttpServletRequest request, HttpServletResponse response, final RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        redirectAttributes.addAttribute(MultiSubSessionManager.SESSION_PARAMETER, MultiSubSessionManager.getSessionId(request));
        Integer idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        String ret = REDIRECT_COLLEGA_PROCEDIMENTO_LISTA_ENTI;
        Pratica pratica = null;
        try {
            pratica = praticheService.getPratica(idPratica);
            List<String> erroriList = new ArrayList<String>();
            Message error = new Message();
            error.setError(Boolean.FALSE);
            if (Utils.e(request.getParameter("idProcedimento"))) {
                String msgError = messageSource.getMessage("error.select.procedimento", null, Locale.getDefault());
                error.setError(Boolean.TRUE);
                erroriList.add(msgError);
            }
            if (Utils.e(request.getParameter("idEnte"))) {
                String msgError = messageSource.getMessage("error.select.ente", null, Locale.getDefault());
                error.setError(Boolean.TRUE);
                erroriList.add(msgError);
            }
            if (!error.getError()) {
                erroriList = systemEventAction.VerifyInsertProcedimentoEnte(idPratica, Integer.parseInt(request.getParameter("idProcedimento")), Integer.parseInt(request.getParameter("idEnte")), erroriList, utenteConnesso);
                if (!erroriList.isEmpty()) {
                    error.setError(Boolean.TRUE);
                }
            }
            if (error.getError()) {
                error.setMessages(erroriList);
                redirectAttributes.addFlashAttribute("messaggi", error);
            } else {
                redirectAttributes.addFlashAttribute("currentTab", "frame2");
                ret = REDIRECT_DETTAGLIO_PRATICA;
            }

        } catch (Exception ex) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Si sono verificati degli errori"));
            request.setAttribute("message", errori);
            Log.APP.error("Si sono verificati degli errori", ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_DETTAGLIO_AGGIUNGIPROCEDIMENTO_INSERISCI, "Errore nell'esecuzione del controller /pratiche/dettaglio/aggiungiProcedimento/inserisci", ex, pratica, utenteConnesso);
            erroriAction.saveError(errore);
        }
        return ret;
    }

    @RequiresGrant
    @RequestMapping("/pratiche/dettaglio/aggiungiProcedimento")
    public String aggiungiProcedimento(Model model, HttpServletRequest request, HttpServletResponse response) {
        Filter filter = (Filter) request.getSession().getAttribute("aggiungProcedimentoList");
        if (filter == null) {
            filter = new Filter();
        }
        model.addAttribute("ricerca", filter);
        return COLLEGA_PROCEDIMENTO;
    }

    @RequestMapping("/pratiche/dettaglio/aggiungiProcedimento/ajax")
    public String aggiungiProcedimentoAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridProcedimenti json;
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json = procedimentiService.getProcedimentiLocalizzati(request, paginator, "aggiungProcedimentoList");
            //json = anagraficaAction.getAnagrafiche(request, paginator, "collegaAnagraficheList");
            model.addAttribute("json", json.toString());
            return AJAX_PAGE;
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", error);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_DETTAGLIO_AGGIUNGIPROCEDIMENTO_AJAX, "Errore nell'esecuzione del controller /pratiche/dettaglio/aggiungiProcedimento/ajax", e, null, utente);
            erroriAction.saveError(errore);
            return COLLEGA_PROCEDIMENTO;
        }
    }

    @RequestMapping("/pratiche/dettaglio/aggiungiProcedimento/listaEnti")
    public String aggiungiProcedimentoListaEnti(@ModelAttribute("messaggi") Message error, Model model, HttpServletRequest request, HttpServletResponse response) {
        Filter filter = (Filter) request.getSession().getAttribute("aggiungProcedimentoList");
        if (filter == null) {
            filter = new Filter();
        }
        Utente utente = utentiService.getUtenteConnesso(request);
        ProcedimentiLocalizzatiView p = null;
        try {
            if (request.getParameter("search_idProcedimento") != null && !request.getParameter("search_idProcedimento").trim().equals("")) {
                filter.setIdProcedimento(Integer.parseInt(request.getParameter("search_idProcedimento")));
                p = procedimentiService.getProcedimentoLocalizzato("it", filter.getIdProcedimento());
            }
            model.addAttribute("procedimento", p);
            model.addAttribute("ricerca", filter);
        } catch (Exception e) {
            Log.APP.error("Errore reperendo i procedimenti", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_DETTAGLIO_AGGIUNGIPROCEDIMENTO_LISTAENTI, "Errore nell'esecuzione del controller /pratiche/dettaglio/aggiungiProcedimento/listaEnti", e, null, utente);
            erroriAction.saveError(errore);
        } finally {
            if (!error.getMessages().isEmpty()) {
                model.addAttribute("message", error);
            }
        }
        return COLLEGA_PROCEDIMENTO_LISTA_ENTI;
    }

    @RequiresGrant
    @RequestMapping("/pratiche/dettaglio/aggiungiProcedimento/listaEnti/ajax")
    public String aggiungiProcedimentoListaEntiAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridEntiBean json;
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json = entiService.getEnti(request, paginator, "aggiungProcedimentoList");
            //json = anagraficaAction.getAnagrafiche(request, paginator, "collegaAnagraficheList");
            model.addAttribute("json", json.toString());
            return AJAX_PAGE;
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", error);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_DETTAGLIO_AGGIUNGIPROCEDIMENTO_LISTAENTI_AJAX, "Errore nell'esecuzione del controller /pratiche/dettaglio/aggiungiProcedimento/listaEnti/ajax", e, null, utente);
            erroriAction.saveError(errore);
            return COLLEGA_PROCEDIMENTO;
        }
    }

    @RequestMapping("/pratiche/dettaglio/formDatiCatastaliAjax")
    public String aggiungiDatoCatastale(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "action", required = true) String azione,
            @ModelAttribute DatiCatastaliDTO datiCatastaliDTO) {
        GridDatoCatastaleBean json = new GridDatoCatastaleBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            pratica = praticheService.getPratica(datiCatastaliDTO.getIdPratica());
            GestionePratica gp = pluginService.getGestionePratica(pratica.getIdProcEnte().getIdEnte().getIdEnte());
            if (!Utils.e(azione)) {
                if (azione.equals("leggiDatoCatastale")) {
                    json = praticheAction.findDatoCatastaleDaBancaDati(datiCatastaliDTO.getIdImmobile());
                    model.addAttribute("json", json.toString());
                } else if (azione.equals("eliminaDatoCatastale")) {
                    systemEventAction.eliminaDatiCatastaleBancaDati(datiCatastaliDTO, utente);
                    json.setMessages(Arrays.asList("Dato Catastale eliminato correttamente."));
                    model.addAttribute("json", json.toString());
                } else if (azione.equals("salvaDatoCatastale") || azione.equals("inserisciDatoCatastale")) {
                    if (Utils.e(datiCatastaliDTO.getIdTipoSistemaCatastale())) {
                        json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.idTipoSistemaCatastale", null, Locale.getDefault())));
                    }
                    if (!Utils.e(datiCatastaliDTO.getIdTipoSistemaCatastale())) {
                        LkTipoSistemaCatastale lk = lookupDao.findIdTipoCatastaleById(datiCatastaliDTO.getIdTipoSistemaCatastale());
                        if (lk != null) {
                            if (Constants.IDENTIFICATIVO_CATASTO.equals(lk.getIdTipoSistemaCatastale())) {
                                if (Utils.e(datiCatastaliDTO.getIdTipoUnita())) {
                                    json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.idTipoUnita", null, Locale.getDefault())));
                                }
                                if (Utils.e(datiCatastaliDTO.getFoglio())) {
                                    json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.foglio", null, Locale.getDefault())));
                                }
                                if (Utils.e(datiCatastaliDTO.getMappale())) {
                                    json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.mappale", null, Locale.getDefault())));
                                }
                            }
                            if (Constants.IDENTIFICATIVO_TAVOLARE.equals(lk.getIdTipoSistemaCatastale())) {
                                if (Utils.e(datiCatastaliDTO.getIdTipoParticella())) {
                                    json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.idTipoParticella", null, Locale.getDefault())));
                                }
                            }
                        }
                    }
                    List<String> err = gp.controllaDatoCatastale(datiCatastaliDTO, pratica.getIdProcEnte().getIdEnte().getIdEnte());
                    if (err != null) {
                        json.setErrors(err);
                    }
                    if (json.getErrors() == null) {
                        datiCatastaliDTO.setIdTipoSistemaCatastale(datiCatastaliDTO.getIdTipoSistemaCatastale());
//                        json = praticheAction.salvaDatoCatastaleBancaDati(datiCatastaliDTO);
                        json.setDatoCatastale(systemEventAction.salvaDatiCatastaliBancaDati(datiCatastaliDTO, utente));
                        json.setMessages(Arrays.asList("Dati Catastali salvati correttamente."));
                    }
                    model.addAttribute("json", json.toString());
                } else {
                    json.setErrors(Arrays.asList("Impossibile salvare i dati."));
                    model.addAttribute("json", json.toString());
                }
            } else {
                json.setErrors(Arrays.asList("Impossibile salvare i dati."));
                model.addAttribute("json", json.toString());
            }
        } catch (Exception e) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Impossibile salvare i dati."));
            request.setAttribute("message", error);
            Log.SQL.error("/pratiche/dettaglio/formDatiCatastaliAjax", e);
            json = new GridDatoCatastaleBean();
            json.setErrors(Arrays.asList("Impossibile salvare i dati."));
            model.addAttribute("json", json.toString());
        }
        return AJAX_PAGE;
    }

    @RequiresGrant
    @RequestMapping("/pratiche/mail/resend")
    public String rispedisciEmail(
            Model model,
            HttpServletRequest request,
            @RequestParam(value = "emailId", required = true) Integer emailId,
            @RequestParam(value = "taskId", required = false) String taskId,
            @RequestParam(value = "destinatario", required = true) String destinatario) {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        JsonResponse json = new JsonResponse();
        try {
            emailAction.rispedisciEmailInviata(emailId, taskId, destinatario, utenteConnesso);
            json.setSuccess(Boolean.TRUE);
            json.setMessage("Email rispedita");
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di rispedire l'email", ex);
            json.setSuccess(Boolean.FALSE);
            json.setMessage(ex.getMessage());
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequestMapping("/pratiche/dettaglio/formIndirizziInterventoAjax")
    public String aggiungiIndirizzoIntervento(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "action", required = true) String azione,
            @ModelAttribute IndirizzoInterventoDTO indirizzoInterventoDTO) {
        GridIndirizzoInterventoBean json = new GridIndirizzoInterventoBean();
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            pratica = praticheService.getPratica(indirizzoInterventoDTO.getIdPratica());
            GestionePratica gp = pluginService.getGestionePratica(pratica.getIdProcEnte().getIdEnte().getIdEnte());
            if (!Utils.e(azione)) {
                if (azione.equals("leggiIndirizzoIntervento")) {
                    json = praticheAction.findIndirizzoIntervento(indirizzoInterventoDTO.getIdIndirizzoIntervento());
                    model.addAttribute("json", json.toString());
                } else if (azione.equals("eliminaIndirizzoIntervento")) {
                    systemEventAction.eliminaIndirizzoIntervento(indirizzoInterventoDTO.getIdIndirizzoIntervento(), utenteConnesso);
                    json.setMessages(Arrays.asList("Indirizzo intervento eliminato correttamente."));
                    model.addAttribute("json", json.toString());
                } else if (azione.equals("salvaIndirizzoIntervento")) {
                    List<String> err = gp.controllaIndirizzoIntervento(indirizzoInterventoDTO, pratica.getIdProcEnte().getIdEnte().getIdEnte());
                    if (err != null) {
                        json.setErrors(err);
                    }
                    if (json.getErrors() == null) {
//                        json = praticheAction.salvaIndirizzoInterventoBancaDati(indirizzoInterventoDTO);
                        json.setIndirizzo(systemEventAction.salvaIndirizziInterventoBancaDati(indirizzoInterventoDTO, utenteConnesso));
                    }
                    model.addAttribute("json", json.toString());
                } else if (azione.equals("inserisciIndirizzoIntervento")) {
                    if (Utils.e(indirizzoInterventoDTO.getIndirizzo())) {
                        json.setErrors(Arrays.asList(messageSource.getMessage("error.datiCatastali.indirizzo", null, Locale.getDefault())));
                    }
                    List<String> err = gp.controllaIndirizzoIntervento(indirizzoInterventoDTO, pratica.getIdProcEnte().getIdEnte().getIdEnte());
                    if (err != null) {
                        json.setErrors(err);
                    }
                    if (json.getErrors() == null) {
//                        json = praticheAction.inserisciIndirizziInterventoBancaDati(indirizzoInterventoDTO);
                        json.setIndirizzo(systemEventAction.inserisciIndirizzoInterventoBancaDati(indirizzoInterventoDTO, utenteConnesso));
                        json.setMessages(Arrays.asList("Indirizzo Intervento salvato correttamente."));
                    }
                    model.addAttribute("json", json.toString());
                } else {
                    json.setErrors(Arrays.asList("Impossibile salvare i dati."));
                    model.addAttribute("json", json.toString());
                }
            } else {
                json.setErrors(Arrays.asList("Impossibile salvare i dati."));
                model.addAttribute("json", json.toString());
            }
        } catch (Exception e) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Impossibile salvare i dati."));
            request.setAttribute("message", error);
            Log.SQL.error("/pratiche/dettaglio/formIndirizziInterventoAjax", e);
            json = new GridIndirizzoInterventoBean();
            json.setErrors(Arrays.asList("Impossibile salvare i dati."));
            model.addAttribute("json", json.toString());
        }
        return AJAX_PAGE;
    }

    @RequestMapping("/pratiche/dettaglio/formAllegatiProtocolloAjax")
    public String cercaAllegatiProtocollo(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "action", required = true) String azione,
            @ModelAttribute AllegatoDTO allegatoDTO) {
    	JsonResponse json = new JsonResponse();
        try {
        	Utente utente = utentiService.getUtenteConnesso(request);
            String anno =request.getParameter("anno");
       		String idProtocollo = request.getParameter("idProtocollo");
       		long nProtocollo = Long.parseLong(idProtocollo);
               
            File[] allegatiZipF = null;
            List<String> allegatiZip = new ArrayList<String>();
            QName SERVICE_NAME = new QName("http://impl.server.ws.protocollo.linksmt.it/", "ProtocolloServerImplService");
        	URL wsdlURL = new URL("http://10.10.1.4:8081/adoc/protocollo?wsdl");
    		ProtocolloServerImplService ss = new ProtocolloServerImplService(wsdlURL, SERVICE_NAME);
    	    ProtocolloServer port = ss.getProtocolloServerImplPort(); 
    	    Map<String, Object> requestContext2 = ((BindingProvider)port).getRequestContext();
    	    Map<String,List<String>> headers = new HashMap<String,List<String>>();
    	    headers.put("Username", Collections.singletonList(""));
    	    headers.put("Password", Collections.singletonList(""));
    	    requestContext2.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
    		ProtocolloInformazioniRequest requestProtocollo = new ProtocolloInformazioniRequest();
    		
    		requestProtocollo.setAnno(anno);
    		requestProtocollo.setNumeroProtocollo(nProtocollo);
    		Return r = port.getProtocollo(requestProtocollo);
    		XMLGregorianCalendar dataProtocollo = r.getDataProtocollo();
    		Mittente mittente = r.getMittente();
    		Long numeroProtocollo = r.getNumeroProtocollo();
    		String oggetto = r.getOggetto();
    		if (r.getErrore() != null) {
//    			throw new Error(r.getErrore().getDescrizione());
    			json.setSuccess(Boolean.FALSE);
                json.setMessage("protocollo non trovato");
                Log.SQL.error("/pratiche/dettaglio/formAllegatiAjax", r.getErrore().getDescrizione());
                model.addAttribute("json", json.toString());
                return AJAX_PAGE;
    		} else {
    			if (r.getDocumento() != null) {
    				byte[] decodedContenuto = r.getDocumento().getContenuto();
    				File zipFile = new File(r.getDocumento().getNomeFile());
    				FileUtils.writeByteArrayToFile(zipFile,decodedContenuto);
    				String attachmentsFolder = getTempFolder();
    				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    				String identificativoFolder = timestamp.getTime() + "";
    				String pathZipCaricato = attachmentsFolder + File.separator + Constants.UNZIP_FOLDER + identificativoFolder;
    				//Utils.copyFile(fileConv, dest);
    				Utils.unzip(zipFile.getAbsolutePath(), pathZipCaricato);
    				allegatiZipF = Utils.getFolderFile(pathZipCaricato);
    				for (File f : allegatiZipF) {
    					allegatiZip.add(identificativoFolder + ";" + f.getName() + ";" + f.length()/1024);
    		
    				}
    			}
    		}

    		ObjectMapper mapper = new ObjectMapper();
    		String allegatiZipJ = mapper.writeValueAsString(allegatiZip);
    		json.addAttribute("allegati", allegatiZipJ);
    		json.addAttribute("dataProtocollo", dataProtocollo.getDay() + 
    				"-" + dataProtocollo.getMonth() + "-" + dataProtocollo.getYear());
    		json.addAttribute("oggetto", oggetto);
    		json.addAttribute("numeroProtocollo", numeroProtocollo.toString());
    		json.addAttribute("ente", mittente.getPersonaGiuridica().getRagioneSociale());

    		json.setSuccess(Boolean.TRUE);
            model.addAttribute("json", json.toString());
        } catch (Exception e) {
            json.setSuccess(Boolean.FALSE);
            json.setMessage("Protocollo non trovato");
            Log.SQL.error("/pratiche/dettaglio/formAllegatiAjax", e);
            model.addAttribute("json", json.toString());
            return AJAX_PAGE;
        }
        
        return AJAX_PAGE;
    }
    

    @RequiresGrant
    @RequestMapping("/pratiche/dettaglio/formAllegatiProtocolloSalvaAjax")
    public String salvaAllegatiProtocollo(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "action", required = true) String azione,
            @ModelAttribute AllegatoDTO allegatoDTO) throws JSONException {
        Utente utente = utentiService.getUtenteConnesso(request);
        String praticaId =request.getParameter("pratica");
        String res = request.getParameter("res");
		JSONObject resO = new JSONObject(res);
		JSONArray allegatiPath = resO.getJSONArray("allegati");
		String numeroProtocollo = resO.getString("numeroProtocollo");
		String dataProtocollo = resO.getString("dataProtocollo");
		Integer idPratica = Integer.valueOf(praticaId);
		 Log.APP.error("numero protocollo " + numeroProtocollo);
		 Log.APP.error("data protocollo " + dataProtocollo);
		 Log.APP.error("allegati " + allegatiPath);
        JsonResponse json = new JsonResponse();
        String tempFolder = getTempFolder();
		String cartellaUnzip = tempFolder + Constants.UNZIP_FOLDER;
        try {
        	Pratica pratica = praticheService.getPratica(idPratica);
    		ProcessiEventi processoEvento = praticaDao.getProcessiEventi(pratica.getIdProcesso().getIdProcesso(), SessionConstants.CODICE_EVENTO_CARICA_DOC_PROT);
    		Integer idEvento = processoEvento.getIdEvento();
    		String cartellaAllegatiPratica = cartellaUnzip + File.separator + pratica.getIdentificativoPratica();
        	copiaAllegati(allegatiPath,cartellaUnzip,cartellaAllegatiPratica);
        	ProcessiEventi pe = praticheService.findProcessiEventi(idEvento);
        	EventoDTO eventoDTO = new EventoDTO();
        	eventoDTO.setIdEvento(idEvento);
        	eventoDTO.setDescrizione(pe.getDesEvento());
        	SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
        	Date date1 = dateformat.parse(dataProtocollo);
        	eventoDTO.setDataProtocollo(date1);
        	eventoDTO.setNumProtocollo(numeroProtocollo);
        	eventoDTO.setCodEvento(SessionConstants.CODICE_EVENTO_CARICA_DOC_PROT);
        	List<AllegatoDTO> allegatiListDTO = caricaListAllegatiDTO(allegatiPath,cartellaAllegatiPratica);
        	allegatiAction.salvaAllegati(eventoDTO, utente, pratica, allegatiListDTO);
    		json.setSuccess(Boolean.TRUE);
            model.addAttribute("json", json.toString());
        } catch (Exception e) {
            json.setSuccess(Boolean.FALSE);
            json.setMessage("Impossibile salvare i dati.");
            Log.SQL.error("/pratiche/dettaglio/formAllegatiAjax", e);
            model.addAttribute("json", json.toString());
        }
        
        return AJAX_PAGE;
    }

    private List<PraticheEventiAllegati> caricaListAllegati(JSONArray allegatiPath,String cartellaAllegatiPratica) throws JSONException, IOException {
    	List<PraticheEventiAllegati> listPel = new ArrayList<PraticheEventiAllegati>();
    	for (int i=0; i< allegatiPath.length();i++) {
    		PraticheEventiAllegati pel = new PraticheEventiAllegati();
    		Allegati all = new Allegati();
    		String nomeFile = ((String)allegatiPath.get(i)).split("\\/")[1].replaceAll("%20", " ");
    		String tipoFile = nomeFile.split("\\.")[1];
    		String pathFile = cartellaAllegatiPratica + File.separator + nomeFile ;
        	all.setDescrizione(nomeFile);
        	all.setFile(FileUtils.readFileToByteArray(new File(pathFile)));
        	all.setNomeFile(nomeFile);
        	all.setTipoFile(tipoFile);
        	all.setPathFile(pathFile); 
        	pel.setAllegati(all);
        	listPel.add(pel);
    	}
    	return listPel;
	}
    
    
    private List<AllegatoDTO> caricaListAllegatiDTO(JSONArray allegatiPath,String cartellaAllegatiPratica) throws JSONException, IOException {
    	List<AllegatoDTO> listPel = new ArrayList<AllegatoDTO>();
    	for (int i=0; i< allegatiPath.length();i++) {
    		PraticheEventiAllegati pel = new PraticheEventiAllegati();
    		String nomeFile = ((String)allegatiPath.get(i)).split("\\/")[1].replaceAll("%20", " ");
    		String tipoFile = nomeFile.split("\\.")[1];
    		String pathFile = cartellaAllegatiPratica + File.separator + nomeFile ;
    		AllegatoDTO allegatoDTO = new AllegatoDTO();
        	allegatoDTO.setDescrizione(nomeFile);
        	allegatoDTO.setNomeFile(nomeFile);
        	allegatoDTO.setPathFile(pathFile);
        	byte[] content = FileUtils.readFileToByteArray(new File(pathFile));
        	MultipartFile result = new MockMultipartFile(nomeFile,
        			nomeFile, tipoFile, content);
        	allegatoDTO.setFile(result);
        	allegatoDTO.setFileContent(FileUtils.readFileToByteArray(new File(pathFile)));
        	listPel.add(allegatoDTO);
    	}
    	return listPel;
	}


	private void copiaAllegati(JSONArray allegatiPath, String cartellaUnzip, String cartellaAllegatiPratica) throws IOException, JSONException {
	    	for (int i=0; i< allegatiPath.length();i++) {
	    		Utils.copyFile(
	    				new File(cartellaUnzip + ((String)allegatiPath.get(i)).replaceAll("%20", " ")),
	    				new File(cartellaAllegatiPratica + File.separator + ((String)allegatiPath.get(i)).split("\\/")[1].replaceAll("%20", " ")));
	    	}
	}

	private String getTempFolder() {
    	List<Configuration> attachmentsFolderConfig = configurationService
				.findByName(SessionConstants.TEMP_FOLDER);
		Configuration config = attachmentsFolderConfig.get(0);
		return config.getValue();
	}

	private String getAttachmentsFolder() {
		List<Configuration> attachmentsFolderConfig = configurationService
				.findByName(SessionConstants.ATTACHMENTS_FOLDER);
		Configuration config = attachmentsFolderConfig.get(0);
		return config.getValue();
	}

	@RequestMapping("/pratiche/dettaglio/formAllegatiAjax")
    public String modificaAllegato(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "action", required = true) String azione,
            @ModelAttribute AllegatoDTO allegatoDTO) {
        Utente utente = utentiService.getUtenteConnesso(request);
        JsonResponse json = new JsonResponse();
        try {
            Allegati allegato = allegatiService.findAllegatoById(allegatoDTO.getIdAllegato());
            if (!Utils.e(azione)) {
                if (azione.equals("modificaDescrizione")) {
                    if (Strings.isNullOrEmpty(allegatoDTO.getDescrizione())) {
                        json.setSuccess(Boolean.FALSE);
                        json.setMessage(messageSource.getMessage("error.allegati.descrizione", null, Locale.getDefault()));
                    } else {
                        allegato.setDescrizione(allegatoDTO.getDescrizione());
                        allegatiAction.salvaAllegato(allegato);
                        json.setSuccess(Boolean.TRUE);
                        json.setMessage("Aggiornamento effettuato");
                    }
                    model.addAttribute("json", json.toString());
                } else {
                    json.setSuccess(Boolean.FALSE);
                    json.setMessage("Impossibile salvare i dati.");
                    model.addAttribute("json", json.toString());
                }
            } else {
                json.setSuccess(Boolean.FALSE);
                json.setMessage("Impossibile salvare i dati.");
                model.addAttribute("json", json.toString());
            }
        } catch (Exception e) {
            json.setSuccess(Boolean.FALSE);
            json.setMessage("Impossibile salvare i dati.");
            Log.SQL.error("/pratiche/dettaglio/formAllegatiAjax", e);
            model.addAttribute("json", json.toString());
        }
        return AJAX_PAGE;
    }
    
	
	
	@RequestMapping("/pratiche/dettaglio/formFirmaAllegatoAjax")
	 public String firmaAllegato(Model model,
	            HttpServletRequest request,
	            HttpServletResponse response,
	            @RequestParam(value = "cades") String cadesS,
	            @RequestParam(value = "otp") String otp,
	            @RequestParam(value = "username") String username,
	            @ModelAttribute AllegatoDTO allegatoDTO) {
	        Utente utente = utentiService.getUtenteConnesso(request);
	        System.out.println("cadesS : " + cadesS);
	        Boolean cades = cadesS.equals("cades") ? true : false;
	        String praticaId =request.getParameter("pratica");
	        Integer idPratica = Integer.valueOf(praticaId);
	        JsonResponse json = new JsonResponse();
        	Date date = new Date();

	        	
	        try { 
	        	 if (Strings.isNullOrEmpty(username)) {
	                 json.setSuccess(Boolean.FALSE);
	                 json.setMessage(messageSource.getMessage("error.firma.username", null, Locale.getDefault()));
	                 model.addAttribute("json", json.toString());
	        	 }else
	        	 if (Strings.isNullOrEmpty(otp)) {
	                 json.setSuccess(Boolean.FALSE);
	                 json.setMessage(messageSource.getMessage("error.firma.otp", null, Locale.getDefault()));
	                 model.addAttribute("json", json.toString());
	        	 }else {
	        	Pratica pratica = praticheService.getPratica(idPratica);
	            Allegati allegato = allegatiService.findAllegatoById(allegatoDTO.getIdAllegato());
	            String folder = configurationService.getCachedConfiguration(SessionConstants.DOCUMENTS_FOLDER, null, null);
	            folder = componentUtils.generateFilePath(folder, pratica.getIdComune(), date);
		        String cartellaAllegatiPratica = folder + File.separator + UUID.randomUUID() + "_";
		        System.out.println("cartella allegati pratica : " + cartellaAllegatiPratica);
	            //autenticazione al servizio di firma per ottenere il token
		        System.out.println("VERIFICA id allegato DTO : " + allegatoDTO.getIdAllegato());
	            System.out.println("VERIFICA allegato pathfile : " + allegato.getPathFile());
	            System.out.println("VERIFICA nome file : " + allegato.getNomeFile());
	            System.out.println("VERIFICA descrizione file : " + allegato.getDescrizione());
	              QName SERVICE_NAME = new QName("http://stampsign.wsdl.avtmb.tz.eng.it", "stampsign");
	        		  URL url = null;
//	        	            url = new URL("file:resources/firma/stampsignwsdl.wsdl");
	        	            url = ComunicazioneController.class.getResource("/wsdl/firma/stampsignwsdl.wsdl");
	        	            System.out.println("URL LEGGI: " + url);
	        	            URL   url2 = ComunicazioneController.class.getResource("authentication.properties");
	        	            System.out.println("URL LEGGI: " + url2);
	        		 URL wsdlURL = url;
	        	      
	        	        Stampsign ss = new Stampsign(wsdlURL, SERVICE_NAME);
	        	        DocumentStampSignAuthPt port = ss.getDocumentStampSignAuthBinding();  
	        	        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
	        	        String endPoint = configurationService
	        					.findByName(SessionConstants.ENDPOINT_FIRMA_URL).get(0).getValue();
	        	    	String endPointA = configurationService
	        					.findByName(SessionConstants.ENDPOINT_FIRMA_URL_AUTH).get(0).getValue();
	        	    	String endPointU = configurationService
	        					.findByName(SessionConstants.ENDPOINT_FIRMA_USERNAME).get(0).getValue();
	        	    	String endPointP = configurationService
	        					.findByName(SessionConstants.ENDPOINT_FIRMA_PASSWORD).get(0).getValue();
	        	    	String clientId = configurationService
	        					.findByName(SessionConstants.ENDPOINT_FIRMA_CLIENTID).get(0).getValue();
	        	    	String accessToken = configurationService
	        					.findByName(SessionConstants.ENDPOINT_FIRMA_ACCESSTOKEN).get(0).getValue();
	        	        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointA);
	        	        requestContext.put(BindingProvider.USERNAME_PROPERTY, endPointU);
	        	        requestContext.put(BindingProvider.PASSWORD_PROPERTY, endPointP);
	        	        Map<String, List<String>> headers = new HashMap<String, List<String>>();
	        	        requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
	        	        
	      					 StampSignRequest stampRequest = new StampSignRequest();
	      				        stampRequest.setRequestType(RequestType.SIGN);
	      				        stampRequest.setId("id_9c716376-a0d5-4a0b-ac92-4a8987189d96");
	      				        Sign sign = new Sign();
	      				        sign.setClientId(clientId);
	      				        sign.setTransactionId("83247823794");
	      				        sign.setSigner(username);
	      				        if (cades)
	      				        	sign.setTipoFirma("CADES");
	      				        else
	      				        	sign.setTipoFirma("PADES");
	      				        sign.setOtp(otp);
	      				        DataHandler dh = getDataHandler(allegato.getPathFile());
	      				        sign.setFileDocumento(dh);
	      				        stampRequest.setSign(sign);
	      				        DocumentStampSignPt portSign = ss.getDocumentStampSignBinding();
	      				        Map<String, Object> requestContext2 = ((BindingProvider) portSign).getRequestContext();
	      				        Map<String, List<String>> headersSign = new HashMap<String, List<String>>();
	      				        headersSign.put("Authorization", Collections.singletonList("Bearer " + accessToken));
	      				        requestContext2.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint);
	      				        requestContext2.put(MessageContext.HTTP_REQUEST_HEADERS, headersSign);
//	      				        String fileName = allegato.getNomeFile(); 
//	      				        InputStream is = new FileInputStream(allegato.getPathFile());
	      				        StampSignResponse stampResponse = portSign.documentStampSign(stampRequest);
	      				      if (stampResponse.getErrore() != null) {
	 				        	 json.setSuccess(Boolean.FALSE);
	         	                 json.setMessage(stampResponse.getErrore().getMessaggio());
	         	                 model.addAttribute("json", json.toString());
	         	                Log.SQL.error(stampResponse.getErrore().getMessaggio());
	 				        }else {
	      				        Risultato risultato = stampResponse.getRisultato();
	      				        String fileName = risultato.getDownloadFileName();
	      				        InputStream is = risultato.getDownloadFileContent().getInputStream(); 
	      				        OutputStream os;
	      				        if (cades)
	      				        	os = new FileOutputStream(new File(cartellaAllegatiPratica +  (allegato.getNomeFile().toLowerCase().endsWith(".p7m") ? allegato.getNomeFile() : allegato.getNomeFile() + ".p7m")));
	      				        else
	      				        	os = new FileOutputStream(new File(cartellaAllegatiPratica +  allegato.getNomeFile()));
	      				        // This will copy the file from the two streams
	      				        IOUtils.copy(is, os);
	      				        //salva file firmato nella cartella degli allegati
	      				        //dovresti salvare evento di firma
	      				        List<AllegatoDTO> listPel = new ArrayList<AllegatoDTO>();
	      				    		PraticheEventiAllegati pel = new PraticheEventiAllegati();
	      				    		String nomeFile = "";
	      				    		if (cades)
	      				    			nomeFile =  (allegato.getNomeFile().toLowerCase().endsWith(".p7m") ? allegato.getNomeFile() : allegato.getNomeFile() + ".p7m");
	      				    		else
	      				    			nomeFile =  allegato.getNomeFile();
	      				    		String descFile = allegato.getDescrizione();
	      				    		if(!descFile.endsWith("(Firmato) "))
	      				    			descFile = descFile + " (Firmato) ";
	      				    		String tipoFile = "";
	      				    		if (cades)
	      				    			tipoFile = "application/pkcs7-mime";
	      				    		else
	      				    			tipoFile = "application/pdf";
	      				    		String pathFile = cartellaAllegatiPratica  + nomeFile ;
	      				    		System.out.println("pathFile da salvare " + pathFile);
	      				    		AllegatoDTO allegatofDTO = new AllegatoDTO();
	      				        	allegatofDTO.setDescrizione(descFile);
	      				        	allegatofDTO.setNomeFile(nomeFile);
	      				        	allegatofDTO.setPathFile(pathFile);
	      				        	allegatofDTO.setTipoFile(tipoFile);
	      				        	System.out.println(" allegatofDTO pathFile da salvare " + allegatofDTO.getPathFile());
//	      				        	byte[] content = org.apache.commons.io.FileUtils.readFileToByteArray(new File(pathFile));
//	      				        	MultipartFile result = new MockMultipartFile(nomeFile,
//	      				        			nomeFile, tipoFile, content);
//	      				        	allegatofDTO.setFile(result);
//	      				        	allegatofDTO.setFileContent(org.apache.commons.io.FileUtils.readFileToByteArray(new File(pathFile)));
	      				        	listPel.add(allegatofDTO); 
	      				        	EventoDTO eventoDTO = new EventoDTO();
	      				        	//dubbi su come recuperare l id evento credo vada creaton evento nella tipologia associato alla firma
	      				        	ProcessiEventi processoEvento = praticaDao.getProcessiEventi(pratica.getIdProcesso().getIdProcesso(), SessionConstants.CODICE_EVENTO_FIRMA_ALLEGATO);
	      				    		Integer idEvento = processoEvento.getIdEvento();
	      				        	eventoDTO.setDataEvento(Utils.dateTimeItalianFormat(date));
	      				        	eventoDTO.setIdEvento(idEvento);
	      				        	ProcessiEventi pe = praticheService.findProcessiEventi(idEvento);
	      				        	eventoDTO.setDescrizione(pe.getDesEvento());
	      				        	SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
	      				        	eventoDTO.setCodEvento(SessionConstants.CODICE_EVENTO_FIRMA_ALLEGATO);
//	      				    		praticheService.startCommunicationProcess(pratica, praticaEvento, comunicazioneBean);
	      				        	allegatiAction.salvaAllegati(eventoDTO, utente, pratica, listPel);
	      				        	List<AllegatoDTO> allegatiPratica = praticheService.getAllegatiPratica(idPratica);
	      				        	Integer idAllegato =0;
	      				        	for(AllegatoDTO all : allegatiPratica) {
	      				        		if (idAllegato < all.getIdAllegato())
	      				        			idAllegato = all.getIdAllegato();
	      				        	}
	      				        	Map<String,String> allegatoF = new HashMap<String,String>();
	      				        	allegatoF.put("nome", allegatofDTO.getNomeFile());
	      				        	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	      				          String today = formatter.format(new Date());
	      				        	allegatoF.put("data", today);
	      				        	allegatoF.put("descrizione", allegatofDTO.getDescrizione());
	      				        	allegatoF.put("id", idAllegato.toString());
	      				        	if (cades)
	      				        		allegatoF.put("tipoFileCode","filefirmato");
	      				        	else
	      				        		allegatoF.put("tipoFileCode","filepdf");
	      				        	 json.setSuccess(Boolean.TRUE);
	      				        	 json.setAttributes(allegatoF);
	      				        	model.addAttribute("json", json.toString());
	      				        	 
	      				}
	        				}
//	             }
//	        	 }
	        				
	            
	        } catch (Exception e) {
	            json.setSuccess(Boolean.FALSE);
	            json.setMessage("Impossibile firmare."); 
	            Log.SQL.error("/pratiche/dettaglio/formFirmaAllegatoAjax", e);
	            model.addAttribute("json", json.toString());
	        }
	        return AJAX_PAGE;
	    }
	
    
    @RequestMapping("/pratica/dettaglio/messaggi")
    public String messaggi(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("messaggio") MessaggioDTO messaggio) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            HttpSession session = request.getSession();
            Integer idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
            pratica = praticheService.getPratica(idPratica);
            Messaggio toSave = new Messaggio();
            toSave.setDataMessaggio(new Date());
            toSave.setIdPratica(pratica);
            toSave.setIdUtenteMittente(utente);
            Utente destinatario = utentiService.findUtenteByIdUtente(messaggio.getIdDestinatario());
            toSave.setIdUtenteDestinatario(destinatario);
            toSave.setStatus("U");
            toSave.setTesto(messaggio.getTesto());
            messaggiAction.salvaMessaggio(toSave);
            DettaglioPraticaDTO dettaglio = praticheService.getDettaglioPratica(pratica);
            boolean couldCreateNewEvent = utentiService.couldCreateNewEvent(utente, pratica);
            List<MessaggioDTO> messaggi = messaggiAction.findConnectedMessages(pratica);
            model.addAttribute("section", "messaggi");
            model.addAttribute("couldCreateNewEvent", couldCreateNewEvent);
            model.addAttribute("dettaglio", dettaglio);
            model.addAttribute("messaggi", messaggi);
            model.addAttribute("messaggio", new MessaggioDTO());
            model.addAttribute("ritornaAidPratica", request.getParameter("ritornaAidPratica"));
        } catch (Exception ex) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Si sono verificati degli errori"));
            request.setAttribute("message", errori);
            Log.APP.error("Si sono verificati degli errori", ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_DETTAGLIO_MESSAGGI, "Errore nell'esecuzione del controller /pratica/dettaglio/messaggi", ex, pratica, utente);
            erroriAction.saveError(errore);
        }
        return DETTAGLIO_PRATICA;
    }

    @RequiresGrant
    @RequestMapping("/pratica/dettaglio/collegaAnagrafica")
    public String collegaAnagrafica(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "daPraticaNuova", defaultValue = "no") String daPraticaNuova) {
        Filter filter = (Filter) request.getSession().getAttribute("collegaAnagraficheList");
        if (filter == null) {
            filter = new Filter();
        }
        model.addAttribute("idPratica", (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA));
        model.addAttribute("filtroRicerca", filter);
        if (daPraticaNuova != null) {
            model.addAttribute("daPraticaNuova", daPraticaNuova);
        } else {
            model.addAttribute("daPraticaNuova", "no");
        }
        return COLLEGA_ANAGRAFICA;
    }

    @RequiresGrant
    @RequestMapping("/pratica/dettaglio/collegaAnagrafica/ajax")
    public String collegaAnagraficaAjax(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("paginator") JqgridPaginator paginator,
            @RequestParam(value = "daPraticaNuova", defaultValue = "no") String daPraticaNuova) {
        Utente utente = utentiService.getUtenteConnesso(request);
        GridAnagraficaBean json;
        try {
            json = anagraficaAction.getAnagrafiche(request, paginator, "collegaAnagraficheList");
            model.addAttribute("json", json.toString());
            model.addAttribute("daPraticaNuova", daPraticaNuova);
            return AJAX_PAGE;
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", error);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_DETTAGLIO_COLLEGAANAGRAFICA_AJAX, "Errore nell'esecuzione del controller /pratica/dettaglio/collegaAnagrafica/ajax", e, null, utente);
            erroriAction.saveError(errore);
            return COLLEGA_ANAGRAFICA;
        }
    }

    @RequiresGrant
    @RequestMapping("/pratica/dettaglio/collegaAnagrafica/seleziona")
    public String collegaAnagrafica(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("idAnagrafica") Integer idAnagrafica,
            @RequestParam(value = "daPraticaNuova", defaultValue = "no") String daPraticaNuova) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            List<LkTipoRuolo> lkruoli = lookupDao.findAllTipoRuolo();
            Map<Integer, String> ruoli = new LinkedHashMap<Integer, String>();
            for (LkTipoRuolo ruolo : lkruoli) {
                ruoli.put(ruolo.getIdTipoRuolo(), ruolo.getDescrizione());
            }
            List<LkTipoQualifica> qualifichelk = lookupDao.findAllTipoQualifica();
            Map<Integer, String> qualificheRichiedente = new LinkedHashMap<Integer, String>();
            Map<Integer, String> qualificheTecnico = new LinkedHashMap<Integer, String>();
            for (LkTipoQualifica qualifica : qualifichelk) {
                if (qualifica.getCondizione().equalsIgnoreCase("RICHIEDENTE")) {
                    qualificheRichiedente.put(qualifica.getIdTipoQualifica(), qualifica.getDescrizione());
                } else {
                    qualificheTecnico.put(qualifica.getIdTipoQualifica(), qualifica.getDescrizione());
                }
            }
            model.addAttribute("qualificheTecnico", qualificheTecnico);
            model.addAttribute("qualificheRichiedente", qualificheRichiedente);
            model.addAttribute("ruoli", ruoli);
            model.addAttribute("daPraticaNuova", daPraticaNuova);
            Anagrafica anagrafica = anagraficheService.findAnagraficaById(idAnagrafica);
            AnagraficaDaCollegareDTO dto = AnagraficheSerializer.serializeAnagraficaDaCollegare(anagrafica);
            model.addAttribute("anagrafica", dto);
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le informazioni necessarie", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_DETTAGLIO_COLLEGAANAGRAFICA, "Errore nell'esecuzione del controller /pratica/dettaglio/collegaAnagrafica/seleziona", e, null, utente);
            erroriAction.saveError(errore);
        }
        return ASSEGNA_RUOLO_ANAGRAFICA;
    }

    @RequiresGrant
    @RequestMapping("/pratica/dettaglio/collegaAnagrafica/conferma")
    public ModelAndView salvaCollegamentoAnagrafica(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("anagrafica") AnagraficaDaCollegareDTO anagrafica,
            @ModelAttribute("daPraticaNuova") String daPraticaNuova,
            RedirectAttributes redirectAttributes) {
        Utente utente = utentiService.getUtenteConnesso(request);
        redirectAttributes.addAttribute(MultiSubSessionManager.SESSION_PARAMETER, MultiSubSessionManager.getSessionId(request));
        Pratica pratica = null;
        try {
            Integer idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
            pratica = praticheService.getPratica(idPratica);
            PraticaAnagrafica collegamentoEsistente = praticheService.findPraticaAnagraficaByKey(idPratica, anagrafica.getIdAnagrafica(), anagrafica.getRuolo());
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            if (collegamentoEsistente != null) {
                throw new Exception("Esiste già un collegamento tra la pratica e l'anagrafica selezionata");
            } else {
                if (daPraticaNuova.equals("si")) {
                    systemEventAction.inserisciCollegamentoPraticaAnagrafica(anagrafica, idPratica, utenteConnesso, true);
                    redirectAttributes.addAttribute("idPratica", (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA));
                    redirectAttributes.addAttribute("currentTab", TAB_ANAGRAFICHE_APERTURA);
                    redirectAttributes.addAttribute("a", "A");
                    redirectAttributes.addAttribute(MultiSubSessionManager.SESSION_PARAMETER, MultiSubSessionManager.getSessionId(request));
                    return new ModelAndView("redirect:/pratiche/nuove/apertura/dettaglio.htm");
                } else {
                    systemEventAction.inserisciCollegamentoPraticaAnagrafica(anagrafica, idPratica, utenteConnesso, true);
                }
                redirectAttributes.addAttribute("currentTab", TAB_ANAGRAFICHE_APERTURA);
                redirectAttributes.addAttribute("a", "A");
                return new ModelAndView("redirect:/pratiche/dettaglio.htm");
            }
        } catch (Exception ex) {
            Log.APP.error("Errore reperendo le pratiche", ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_DETTAGLIO_COLLEGAANAGRAFICA_CONFERMA, "Errore nell'esecuzione del controller /pratica/dettaglio/collegaAnagrafica/conferma", ex, pratica, utente);
            erroriAction.saveError(errore);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError, ex.getMessage()));
            request.setAttribute("message", error);
            List<LkTipoRuolo> lkruoli = lookupDao.findAllTipoRuolo();
            Map<Integer, String> ruoli = new LinkedHashMap<Integer, String>();
            for (LkTipoRuolo ruolo : lkruoli) {
                ruoli.put(ruolo.getIdTipoRuolo(), ruolo.getDescrizione());
            }
            List<LkTipoQualifica> qualifichelk = lookupDao.findAllTipoQualifica();
            Map<Integer, String> qualificheRichiedente = new LinkedHashMap<Integer, String>();
            Map<Integer, String> qualificheTecnico = new LinkedHashMap<Integer, String>();
            for (LkTipoQualifica qualifica : qualifichelk) {
                if (qualifica.getCondizione().equalsIgnoreCase("RICHIEDENTE")) {
                    qualificheRichiedente.put(qualifica.getIdTipoQualifica(), qualifica.getDescrizione());
                } else {
                    qualificheTecnico.put(qualifica.getIdTipoQualifica(), qualifica.getDescrizione());
                }
            }
            model.addAttribute("qualificheTecnico", qualificheTecnico);
            model.addAttribute("qualificheRichiedente", qualificheRichiedente);
            model.addAttribute("ruoli", ruoli);
            model.addAttribute("anagrafica", anagrafica);
            model.addAttribute("daPraticaNuova", daPraticaNuova);
            return new ModelAndView(ASSEGNA_RUOLO_ANAGRAFICA);
        }
    }

    @RequiresGrant
    @RequestMapping("/pratica/dettaglio/collegaAnagrafica/scollega")
    public String scollegaAnagrafica(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("idAnagrafica") Integer idAnagrafica,
            @ModelAttribute("idRuolo") Integer idRuolo, RedirectAttributes redirectAttributes) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            Integer idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
            pratica = praticheService.getPratica(idPratica);
            PraticaAnagrafica pa = praticheService.findPraticaAnagraficaByKey(idPratica, idAnagrafica, idRuolo);
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            systemEventAction.scollegaAnagraficaTransactional(pa, utenteConnesso);
        } catch (Exception e) {
            Log.APP.error("Errore scollegando l'anagrafica dalla pratica", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_DETTAGLIO_COLLEGAANAGRAFICA_SCOLLEGA, "Errore nell'esecuzione del controller /pratica/dettaglio/collegaAnagrafica/scollega", e, pratica, utente);
            erroriAction.saveError(errore);
        }
        redirectAttributes.addAttribute("currentTab", "frame1");
        redirectAttributes.addAttribute("a", "E");
        redirectAttributes.addAttribute(MultiSubSessionManager.SESSION_PARAMETER, MultiSubSessionManager.getSessionId(request));
        return "redirect:/pratiche/dettaglio.htm";
    }

    @RequestMapping("/pratica/attributi/aggiorna")
    public String aperturaDettaglio(
            Model model,
            @RequestParam(value = "idPratica", required = true) Integer idPratica,
            @RequestParam(value = "name", required = true) String attributeName,
            @RequestParam(value = "value", required = true) String attributeValue,
            @RequestParam(value = "pk", required = true) String pk,
            HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, String> result;
        try {
        	LkStatoPratica oldStatus = lookupDao.findStatusPraticaFromPratica(idPratica);
        	result = new HashMap<String, String>();
            result.put("success", "true");
            praticheAction.aggiornaAttributiPratica(idPratica, PraticheAction.AttributiPratica.valueOf(attributeName.toUpperCase()), attributeValue);
            switch (PraticheAction.AttributiPratica.valueOf(attributeName.toUpperCase())) {
            	case PRATICA_STATO:
	                LkStatoPratica statoPratica = lookupDao.findLkStatoPraticaByIdStatoPratica(Integer.valueOf(attributeValue));
	            	Pratica pratica = praticheService.getPratica(idPratica);

	                if (!statoPratica.getGrpStatoPratica().equals("C")) {
	                    result.put("bloccaChiusura", "true");
	                } else {
	                    result.put("bloccaChiusura", "false");
	                }
	                ProcessiEventi eventoProcesso = processiService.findProcessiEventiByCodEventoIdProcesso(AnaTipiEvento.CHANGE_STATUS, pratica.getIdProcesso());
	                Utente utenteConnesso = utentiService.getUtenteConnesso(request);
	                praticheAction.addEvent(utenteConnesso, pratica, eventoProcesso, oldStatus, statoPratica);
	                
            }

        } catch (Exception ex) {
            String messaggio = (ex instanceof CrossException) ? ex.getMessage() : "Impossibile aggiornare il valore";
            Log.APP.error("Impossibile aggiornare l'attributo della pratica", ex);
            result = ImmutableMap.of("success", "false", "msg", messaggio);
        }
        model.addAttribute("json", gson.toJson(result));
        return AJAX_PAGE;

    }

    @RequestMapping("/pratica/evento/attributi/aggiorna")
    public String aggiornaAttributiEvento(
            Model model,
            @RequestParam(value = "idPraticaEvento", required = true) Integer idPraticaEvento,
            @RequestParam(value = "name", required = true) String attributeName,
            @RequestParam(value = "value", required = true) String attributeValue,
            HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, String> result;
        try {
            result = new HashMap<String, String>();
            result.put("success", "true");
            praticheAction.aggiornaAttributiPraticaEvento(idPraticaEvento, PraticheAction.AttributiPraticaEvento.valueOf(attributeName.toUpperCase()), attributeValue);
        } catch (Exception ex) {
            String messaggio = (ex instanceof CrossException) ? ex.getMessage() : "Impossibile aggiornare il valore";
            Log.APP.error("Impossibile aggiornare l'attributo della pratica", ex);
            result = ImmutableMap.of("success", "false", "msg", messaggio);
        }
        model.addAttribute("json", gson.toJson(result));
        return AJAX_PAGE;

    }

    @RequestMapping("/pratica/endoprocedimenti/gestisci")
    public String praticaGestisciEndoprocedimenti(
            Model model,
            @RequestParam(value = "idPratica", required = true) Integer idPratica,
            @RequestParam(value = "idProcedimentoEnte", required = true) Integer idProcedimentoEnte,
            @RequestParam(value = "operation", required = true) String operation,
            HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, String> result;
        try {
            result = new HashMap<String, String>();
            result.put("success", "true");
            result.put("msg", "Procedimento " + ("ADD".equalsIgnoreCase(operation) ? "aggiunto" : "rimosso") + " correttamente");
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            PraticaProcedimenti praticaEndoprocedimento = systemEventAction.gestisciEndoprocedimenti(idPratica, idProcedimentoEnte, operation, utenteConnesso);
            result.put("ente", praticaEndoprocedimento.getEnti().getDescrizione());
            it.wego.cross.dto.dozer.ProcedimentoDTO procedimentoDto = mapper.map(praticaEndoprocedimento.getProcedimenti(), it.wego.cross.dto.dozer.ProcedimentoDTO.class);
            result.put("procedimento", procedimentoDto.getDescrizione());
            result.put("termini", praticaEndoprocedimento.getProcedimenti().getTermini().toString());

        } catch (Exception ex) {
            Log.APP.error("Impossibile " + ("ADD".equalsIgnoreCase(operation) ? "aggiungere" : "rimuovere") + " il procedimento", ex);
            result = ImmutableMap.of("success", "false", "msg", "Impossibile aggiornare il valore");
        }
        model.addAttribute("json", gson.toJson(result));
        return AJAX_PAGE;
    }

    @RequestMapping("/pratica/ricercatoponomastica")
    public String ricercaToponomastica(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute IndirizzoInterventoDTO indirizzoInterventoDTO,
            BindingResult result) throws Exception {
        GridIndirizziInterventoBean json = new GridIndirizziInterventoBean();
        try {
            json = praticheAction.ricercaToponomastica(indirizzoInterventoDTO);
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore contattando il servizio di toponomastica", ex);
            json.setRows(new ArrayList<IndirizzoInterventoDTO>());
            json.setTotal(0);
            json.setError("Errore contattando il servizio: " + ex.getMessage());
        }
        model.addAttribute("json", json);
        return AJAX_PAGE;
    }

    @RequestMapping("/pratica/ricercatocatasto")
    public String ricercatocatasto(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute DatiCatastaliDTO datiCatastaliDTO,
            BindingResult result) throws Exception {
        GridDatiCatastaliBean json = new GridDatiCatastaliBean();
        try {
            json = praticheAction.ricercaCatasto(datiCatastaliDTO);
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore contattando il servizio di ricerca catasto", ex);
            json.setRows(new ArrayList<DatiCatastaliDTO>());
            json.setTotal(0);
            json.setError("Errore contattando il servizio: " + ex.getMessage());
        }
        model.addAttribute("json", json);
        return AJAX_PAGE;
    }

    @RequiresGrant
    @RequestMapping("/pratica/riassegna")
    public String riassegnaPratica(Model model,
            @RequestParam(value = "from", required = true) String from,
            @RequestParam(value = "idPratica", required = true) Integer idPratica,
            @RequestParam(value = "idPraticaEvento", required = false) Integer idPraticaEvento,
            HttpServletRequest request) {
        try {
            request.getSession().removeAttribute("id_pratica");
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            Pratica pratica = praticheService.getPratica(idPratica);
            ProcedimentiEnti idProcEnte = pratica.getIdProcEnte();
            boolean isAdminOnProcEnte = utentiService.isAdminOnProcEnte(utenteConnesso, idProcEnte);
            if (isAdminOnProcEnte) {
                request.getSession().setAttribute("id_pratica", idPratica);
                model.addAttribute("idPratica", idPratica);
                model.addAttribute("idPraticaEvento", idPraticaEvento);
                model.addAttribute("from", from);
                return SELEZIONE_UTENTE_RIASSEGNAZIONE_PRATICA;
            }
            PraticheEventi evento = systemEventAction.riassegnaPratica(pratica, pratica.getIdUtente(), utenteConnesso, utenteConnesso);
            String notifica = messageSource.getMessage("pratica.riassegna.notifica", new Object[]{
                pratica.getIdentificativoPratica(),
                utenteConnesso.getNome(),
                utenteConnesso.getCognome()
            }, Locale.getDefault());
//            workflowAction.riassegnaTaskAssociatiAllaPratica(idPratica, utenteConnesso);
            List<String> notificationUserList = new ArrayList<String>();
            notificationUserList.add(utenteConnesso.getUsername());
            notificationEngine.createNotification(evento, notificationUserList, notifica);
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di riassegnare la pratica", ex);
        }
        return "redirect:/pratiche/" + from + ".htm";
    }

    @RequiresGrant
    @RequestMapping("/pratica/riassegna/ajax")
    public String riassegnaPratica(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        GridAssociaUtentiBean json = new GridAssociaUtentiBean();
        try {
            Integer idPratica = (Integer) request.getSession().getAttribute("id_pratica");
            pratica = praticheService.getPratica(idPratica);
            json = praticheAction.getUtentiDaAssociareAllaPratica(request, paginator);
        } catch (Exception e) {
            String msgError = messageSource.getMessage("error.pratiche.assegnazione.utente", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            error.setError(Boolean.TRUE);
            request.setAttribute("message", error);
            Log.SQL.error(msgError, e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_ASSEGNA_AJAX, "Errore nell'esecuzione del controller /pratiche/nuove/assegna/ajax", e, pratica, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequiresGrant
    @RequestMapping("/pratiche/riassegna/selezione")
    public String riassegnaPraticaDaAdmin(Model model,
            @RequestParam(value = "from", required = true) String from,
            @RequestParam(value = "idUtenteAssegnatario", required = true) Integer idUtenteAssegnatario,
            @RequestParam(value = "idPratica", required = true) Integer idPratica,
            @RequestParam(value = "idPraticaEvento", required = false) Integer idPraticaEvento,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        try {
            request.getSession().removeAttribute("id_pratica");
            Utente utenteConnesso = utentiService.getUtenteConnesso(request);
            praticheService.reassignPaperworkFromAdmin(idUtenteAssegnatario, idPratica, utenteConnesso);
        } catch (Exception ex) {
            String msgError = messageSource.getMessage("error.pratiche.assegnazione.utente", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            error.setError(Boolean.TRUE);
            request.setAttribute("message", error);
            Log.SQL.error(msgError, ex);
            Log.APP.error("Si è verificato un errore cercando di riassegnare la pratica", ex);
        }
        String redirect = "redirect:/pratiche/" + from + ".htm";
        redirectAttributes.addAttribute(MultiSubSessionManager.SESSION_PARAMETER, MultiSubSessionManager.getSessionId(request));
        switch (from) {
        	case CartiglioConstants.FROM_DETAIL:
        		redirectAttributes.addAttribute("back", true);
        		redirect = "redirect:/pratiche/gestisci.htm";
        		break;
        	case CartiglioConstants.FROM_OPENING:
        		redirect = "redirect:/pratiche/nuove/apertura.htm";
	        	break;
	        case CartiglioConstants.FROM_EVENT:
	        	redirectAttributes.addAttribute("id_pratica", idPratica);
	        	redirectAttributes.addAttribute("id_pratica_evento", idPraticaEvento);
	        	redirect = "redirect:/pratica/" + from + ".htm";
	        	break;
	        default:
	    }
        return redirect;
    }

    @RequiresGrant
    @RequestMapping("/pratiche/pratiche_collegate/delete")
    public String deletePraticheCollegate(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("idPratica") String idPratica,
            @ModelAttribute("idPraticaCollegata") String idPraticaCollegata) {
        Utente utente = utentiService.getUtenteConnesso(request);
        JsonResponse json = new JsonResponse();
        try {
        	/*
        	 * Risoluzione defintiva JIRA https://production.eng.it/jira/browse/AVM_BARI-1111
        	 * In alcuni casi ID PRatica e ID PRatica collegata arrivano con una virgola o un punto
        	 * recupero l'integer da una stringa
        	*/
        	Integer idPrat = getIdPratica(idPratica);
        	Integer idPraticaColl = getIdPratica(idPraticaCollegata);
            praticheAction.scollegaPratica(idPrat, idPraticaColl, utente);
            json.setSuccess(Boolean.TRUE);
        } catch (Exception e) {
            Log.APP.error("Errore scollegando le pratiche", e);
            json.setSuccess(Boolean.FALSE);
            json.setMessage(e.getMessage());
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequiresGrant
    @RequestMapping("/pratiche/pratiche_collegate/add")
    public String addPraticheCollegate(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("idPratica") String idPratica,
            @ModelAttribute("idPraticaCollegata") String idPraticaCollegata) {
        Utente utente = utentiService.getUtenteConnesso(request);
        JsonResponse json = new JsonResponse();
        try {
        	/*
        	 * Risoluzione defintiva JIRA https://production.eng.it/jira/browse/AVM_BARI-1111
        	 * In alcuni casi ID PRatica e ID PRatica collegata arrivano con una virgola o un punto
        	 * recupero l'integer da una stringa
        	*/
        	Integer idPrat = getIdPratica(idPratica);
        	Integer idPratColl = getIdPratica(idPraticaCollegata);
            praticheAction.collegaPratica(idPrat, idPratColl, utente);
            Pratica praticaCollegata = praticheService.getPratica(idPratColl);
            json.setSuccess(Boolean.TRUE);
            json.addAttribute("idPraticaCollegata", String.valueOf(praticaCollegata.getIdPratica()));
            json.addAttribute("oggettoPraticaCollegata", praticaCollegata.getOggettoPratica());
            json.addAttribute("identificativoPraticaCollegata", praticaCollegata.getIdentificativoPratica());
            json.addAttribute("dataRicezionePraticaCollegata", Utils.convertDataToString(praticaCollegata.getDataRicezione()));
            String protocollo = "";
            if (!Strings.isNullOrEmpty(praticaCollegata.getProtocollo())) {
                String annoRiferimento = praticaCollegata.getAnnoRiferimento() != null ? String.valueOf(praticaCollegata.getAnnoRiferimento()) : "";
                protocollo = Strings.nullToEmpty(praticaCollegata.getCodRegistro()) + "/" + annoRiferimento + "/" + Strings.nullToEmpty(praticaCollegata.getProtocollo());
            }
            json.addAttribute("protocolloPraticaCollegata", protocollo);
            json.addAttribute("statoPraticaCollegata", praticaCollegata.getIdStatoPratica().getDescrizione());
            PraticaCollegataDTO praticaCollegataDTO = PraticheSerializer.map(praticaCollegata);
            praticaCollegataDTO.setNumeroProtocollo(protocollo);
            json.addAttribute("praticaCollegata", (new Gson()).toJson(praticaCollegataDTO));
        } catch (Exception e) {
            Log.APP.error("Errore collegando le pratiche", e);
            json.setSuccess(Boolean.FALSE);
            json.setMessage(e.getMessage());
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    private String praticheNuoveAnagraficaMessaggi(Character azione) {
        switch (azione) {
            case 'E':
                return "Scollegamento anagrafica selezionata.";
            case 'C':
                return "L'anagrafica è stata aggiornata in database.";
            case 'A':
                return "L'anagrafica è stata correttamente collegata alla pratica.";
        }
        return "";
    }
    
    @RequestMapping("/estrazioni/cila")
    public String estrazioniCILA(Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
            List<Enti> enti = entiService.getListaEntiPerRicerca(connectedUser);
            model.addAttribute("entiRicerca", enti);
									 
            Integer enteAcasoPerColumnModel = null;
            for (UtenteRuoloEnteDTO ruoloEnte : connectedUser.getUtenteRuoloEnte()) {
                enteAcasoPerColumnModel = ruoloEnte.getIdEnte();
                break;
            }
            model.addAttribute("estrazioniCilaColumnModel", configurationService.getEstrazioniCilaColumnModel(enteAcasoPerColumnModel, null));
            Filter filter = (Filter) request.getSession().getAttribute("estrazioniCilaFilter");
            if (filter == null) {
                filter = new Filter();
            }
            model.addAttribute("filtroRicerca", filter);

        } catch (Exception e) {
            Log.APP.error("Errore estrazioni CILA", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI, "Errore nell'esecuzione del controller /estrazioni/cila", e, null, utente);
            erroriAction.saveError(errore);

        }
        return ESTRAZIONI_CILA;
    }
    
    @RequestMapping("/estrazioni/cila/ajax")
    public String estrazioniCILAAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridEstrazioniCILABean json = new GridEstrazioniCILABean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json = praticheAction.getEstrazioneCILA(request, paginator);
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI_AJAX, "Errore nell'esecuzione del controller /pratiche/gestisci/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }
    
    
	@RequestMapping("/excel/cila")
	public void exportCILA(Model model, HttpServletRequest request, HttpServletResponse response,  @RequestParam(value = "xls", required = false) Integer xls) throws Exception {
		Utente utente = utentiService.getUtenteConnesso(request);
		OutputStream out = null;
		try {
			UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
			Filter filter = (Filter) request.getSession().getAttribute("estrazioniCilaFilter");
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("elenco_pratiche_Cila");

			// Create a row and put some cells in it. Rows are 0 based.
			HSSFRow row = sheet.createRow(0);

			// Create a cell and put a value in it.
			HSSFCell cell = row.createCell(0);

			cell.setCellValue("IdPratica");
			row.createCell(1).setCellValue("Identificativo Pratica");
			row.createCell(2).setCellValue("Stato");
			row.createCell(3).setCellValue("Protocollo");
			row.createCell(4).setCellValue("In carico a");
			row.createCell(5).setCellValue("Istruttore");
			row.createCell(6).setCellValue("Data Ricezione");
			row.createCell(7).setCellValue("Data Protocollazione");
			row.createCell(8).setCellValue("Data Richiesta Integrazione");
			row.createCell(9).setCellValue("Data Sospensione");
			row.createCell(10).setCellValue("Data Parere Contrario");
			row.createCell(11).setCellValue("Data Chiusura");
			row.createCell(12).setCellValue("Procedimenti in corso");
			// Write the output
			
            List<EstrazioniCilaDTO> listPraticheCILA = praticheService.listPraticheCILA(filter); 
            int i = 1;
            for(EstrazioniCilaDTO cila : listPraticheCILA) {
            	HSSFRow riga = sheet.createRow(i);
            	riga.createCell(0).setCellValue(cila.getId_Pratica());
            	riga.createCell(1).setCellValue(cila.getIdentificativo_pratica());
            	riga.createCell(2).setCellValue(cila.getStato());
            	riga.createCell(3).setCellValue(cila.getProtocollo());
            	riga.createCell(4).setCellValue(cila.getIn_carico_a());
            	riga.createCell(5).setCellValue(cila.getIstruttore());
            	riga.createCell(6).setCellValue(Utilities.dateToString(cila.getData_ricezione()));
            	riga.createCell(7).setCellValue(Utilities.dateToString(cila.getData_protocollazione()));
            	riga.createCell(8).setCellValue(Utilities.dateToString(cila.getData_ric_integrazione()));
            	riga.createCell(9).setCellValue(Utilities.dateToString(cila.getData_sospensione()));
            	riga.createCell(10).setCellValue(Utilities.dateToString(cila.getData_parere_contrario()));
            	riga.createCell(11).setCellValue(Utilities.dateToString(cila.getData_chiusura()));
            	
            	String formulaProc = "IF(AND(I"+(i+1)+"=\"\",J"+(i+1)+"=\"\",K"+(i+1)+"=\"\",L"+(i+1)+"=\"\"),\"SI\",\"\")";
            	riga.createCell(12).setCellFormula(formulaProc);
            	
            	i++;
            }
            
            HSSFSheet riepilogo = wb.createSheet("Riepilogo");
            HSSFRow riga1 = riepilogo.createRow(0);
            HSSFRow riga2 = riepilogo.createRow(1);
            HSSFRow riga3 = riepilogo.createRow(2);
            riga1.createCell(0).setCellValue("Numero Pratiche");
            riga1.createCell(1).setCellFormula("COUNTA(elenco_pratiche_Cila!A2:A"+i+")");
        	riga2.createCell(0).setCellValue("Numero procedimenti in corso");
        	riga2.createCell(1).setCellFormula("COUNTIF(elenco_pratiche_Cila!M2:M"+i+",\"SI\")");
        	riga3.createCell(0).setCellValue("Numero procedimenti sospesi");
        	riga3.createCell(1).setCellFormula("B1-COUNTBLANK(elenco_pratiche_Cila!J2:J"+i+") ");
        	
			//response.setContentType("application/vnd.ms-excel");
//			response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
//			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_CILA.ods");
			if(xls != null) {
    			response.setContentType("application/vnd.ms-excel");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_CILA.xls");
            }
            else {
    			response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_CILA.ods");
            	
            }
			out = response.getOutputStream();
			wb.write(out);
			wb.close();

			out.flush();

		} catch (Exception e) {
			Log.APP.error("Errore estrazioni CILA", e);
			ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI,
					"Errore nell'esecuzione del controller /estrazioni/cila", e, null, utente);
			erroriAction.saveError(errore);

		}

		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
    @RequestMapping("/estrazioni/scia")
    public String estrazioniSCIA(Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
            List<Enti> enti = entiService.getListaEntiPerRicerca(connectedUser);
            model.addAttribute("entiRicerca", enti);
            Integer enteAcasoPerColumnModel = null;
            for (UtenteRuoloEnteDTO ruoloEnte : connectedUser.getUtenteRuoloEnte()) {
                enteAcasoPerColumnModel = ruoloEnte.getIdEnte();
                break;
            }
            model.addAttribute("estrazioniSciaColumnModel", configurationService.getEstrazioniSciaColumnModel(enteAcasoPerColumnModel, null));
            Filter filter = (Filter) request.getSession().getAttribute("estrazioniSciaFilter");
            if (filter == null) {
                filter = new Filter();
            }
            model.addAttribute("filtroRicerca", filter);

        } catch (Exception e) {
            Log.APP.error("Errore estrazioni SCIA", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI, "Errore nell'esecuzione del controller /estrazioni/scia", e, null, utente);
            erroriAction.saveError(errore);

        }
        return ESTRAZIONI_SCIA;
    }
    
    @RequestMapping("/estrazioni/scia/ajax")
    public String estrazioniSCIAAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
    	GridEstrazioniSCIABean json = new GridEstrazioniSCIABean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json = praticheAction.getEstrazioneSCIA(request, paginator);
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI_AJAX, "Errore nell'esecuzione del controller /estrazioni/scia/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }
    
    
	@RequestMapping("/excel/scia")
	public void exportSCIA(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "xls", required = false) Integer xls) throws Exception {
		Utente utente = utentiService.getUtenteConnesso(request);
		OutputStream out = null;
		try {
			UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
			Filter filter = (Filter) request.getSession().getAttribute("estrazioniSciaFilter");
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("elenco_pratiche_Scia");
			

			// Create a row and put some cells in it. Rows are 0 based.
			HSSFRow row = sheet.createRow(0);

			// Create a cell and put a value in it.
			HSSFCell cell = row.createCell(0);

			cell.setCellValue("IdPratica");
			row.createCell(1).setCellValue("Identificativo Pratica");
			row.createCell(2).setCellValue("Stato");
			row.createCell(3).setCellValue("Protocollo");
			row.createCell(4).setCellValue("In carico a");
			row.createCell(5).setCellValue("Istruttore");
			row.createCell(6).setCellValue("Data Ricezione");
			row.createCell(7).setCellValue("Data Protocollazione");
			row.createCell(8).setCellValue("Data Richiesta Integrazione");
			row.createCell(9).setCellValue("Data Divieto");
			row.createCell(10).setCellValue("Data Integrazione");
			row.createCell(11).setCellValue("Data Parere Favorevole");
			row.createCell(12).setCellValue("Data Parere Contrario");
			row.createCell(13).setCellValue("Data Chiusura");
			row.createCell(14).setCellValue(" ");
			row.createCell(15).setCellValue("Tempi Procedimenti");
			row.createCell(16).setCellValue("Sospesa per integrazione");
			row.createCell(17).setCellValue("Procedimenti in corso");
			// Write the output
            List<EstrazioniSciaDTO> listPraticheSCIA = praticheService.listPraticheSCIA(filter); 
            int i = 1;
            CellStyle cellStyle = wb.createCellStyle();
    		CreationHelper createHelper = wb.getCreationHelper();
    		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy"));
            for(EstrazioniSciaDTO scia : listPraticheSCIA) {
            	HSSFRow riga = sheet.createRow(i);
            	riga.createCell(0).setCellValue(scia.getId_Pratica());
            	riga.createCell(1).setCellValue(scia.getIdentificativo_pratica());
            	riga.createCell(2).setCellValue(scia.getStato());
            	riga.createCell(3).setCellValue(scia.getProtocollo());
            	riga.createCell(4).setCellValue(scia.getIn_carico_a());
            	riga.createCell(5).setCellValue(scia.getIstruttore());
//            	riga.createCell(6).setCellValue(Utilities.dateToString(scia.getData_ricezione()));
//            	riga.createCell(7).setCellValue(Utilities.dateToString(scia.getData_protocollazione()));
//            	riga.createCell(8).setCellValue(Utilities.dateToString(scia.getData_ric_integrazione()));
//            	riga.createCell(9).setCellValue(Utilities.dateToString(scia.getData_divieto()));
//            	riga.createCell(10).setCellValue(Utilities.dateToString(scia.getData_Integrazione()));
//            	riga.createCell(11).setCellValue(Utilities.dateToString(scia.getData_parere_favorevole()));
//            	riga.createCell(12).setCellValue(Utilities.dateToString(scia.getData_parere_contrario()));
//            	riga.createCell(13).setCellValue(Utilities.dateToString(scia.getData_chiusura()));
            	HSSFCell cell6 = riga.createCell(6);
        		cell6.setCellStyle(cellStyle);
            	if(scia.getData_ricezione() != null) {
            		cell6.setCellValue(scia.getData_ricezione());
            	}
            	HSSFCell cell7 = riga.createCell(7);
        		cell7.setCellStyle(cellStyle);
            	if(scia.getData_protocollazione() != null)
            		cell7.setCellValue(scia.getData_protocollazione());
            	
            	HSSFCell cell8 = riga.createCell(8);
        		cell8.setCellStyle(cellStyle);
            	if(scia.getData_ric_integrazione() != null)
            		cell8.setCellValue(scia.getData_ric_integrazione());
            	
            	HSSFCell cell9 = riga.createCell(9);
        		cell9.setCellStyle(cellStyle);
            	if(scia.getData_divieto()!= null)
            		cell9.setCellValue(scia.getData_divieto());
            	
            	HSSFCell cell10 = riga.createCell(10);
            	cell10.setCellStyle(cellStyle);
            	if(scia.getData_Integrazione() != null)
            		cell10.setCellValue(scia.getData_Integrazione());
            	
            	HSSFCell cell11 = riga.createCell(11);
            	cell11.setCellStyle(cellStyle);
            	if(scia.getData_parere_favorevole() != null)
            		cell11.setCellValue(scia.getData_parere_favorevole());
            	
            	HSSFCell cell12 = riga.createCell(12);
            	cell12.setCellStyle(cellStyle);
            	if(scia.getData_parere_contrario() != null)
            		cell12.setCellValue(scia.getData_parere_contrario());
            	
            	HSSFCell cell13 = riga.createCell(13);
            	cell13.setCellStyle(cellStyle);
            	if(scia.getData_chiusura() != null)
            		cell13.setCellValue(scia.getData_chiusura());
            	
            	
            	String formulaApp = "IF(I"+(i+1)+"=\"\",J"+(i+1)+",I"+(i+1)+")"; 
            	String formulaTempi ="IF(O"+(i+1)+"=0,0,O"+(i+1)+"-H"+(i+1)+")";
            	String formulaSosp ="IF(AND(J"+(i+1)+"=\"\",M"+(i+1)+"=\"\",K"+(i+1)+"=\"\",L"+(i+1)+"=\"\",O"+(i+1)+"<>0),\"SI\",\"\")";
            	String formulaProc = "IF(AND(J"+(i+1)+"=\"\",M"+(i+1)+"=\"\",K"+(i+1)+"=\"\",L"+(i+1)+"=\"\",O"+(i+1)+"=0),\"SI\",\"\")";
            	
            	HSSFCell cell14 = riga.createCell(14);
            	cell14.setCellStyle(cellStyle);
            	cell14.setCellFormula(formulaApp);
            	riga.createCell(15).setCellFormula(formulaTempi);
            	riga.createCell(16).setCellFormula(formulaSosp);
            	riga.createCell(17).setCellFormula(formulaProc);
            	
            	i++;
            }
            sheet.setColumnHidden(14, true);
            
            HSSFSheet riepilogo = wb.createSheet("Riepilogo");
            HSSFRow riga1 = riepilogo.createRow(0);
            HSSFRow riga2 = riepilogo.createRow(1);
            HSSFRow riga3 = riepilogo.createRow(2);
            HSSFRow riga4 = riepilogo.createRow(3);
            HSSFRow riga5 = riepilogo.createRow(4);
            HSSFRow riga6 = riepilogo.createRow(5);
            HSSFRow riga7 = riepilogo.createRow(6);
            riga1.createCell(0).setCellValue("Numero procedimenti avviati");
            riga2.createCell(0).setCellValue("Numero procedimenti in corso");
            riga3.createCell(0).setCellValue("Numero procedimenti chiusi nei termini");
            riga4.createCell(0).setCellValue("Numero procedimenti protratti oltre il termine");
            riga5.createCell(0).setCellValue("Durata media dei procedimenti chiusi nei termini");
            riga6.createCell(0).setCellValue("Numero procedimenti sospesi per integrazione");
            riga7.createCell(0).setCellValue("Numero procedimenti chiusi con diniego");
            riga1.createCell(1).setCellFormula("COUNTA(elenco_pratiche_Scia!A2:A"+i+")");
        	riga2.createCell(1).setCellFormula("COUNTIF(elenco_pratiche_Scia!R2:R"+i+",\"SI\")");
        	riga3.createCell(1).setCellFormula("B1-B2-B4-B6");
        	riga4.createCell(1).setCellFormula("COUNTIF(elenco_pratiche_Scia!P2:P"+i+",\">30\") ");
        	riga5.createCell(1).setCellFormula("AVERAGE(elenco_pratiche_Scia!P2:P"+i+") ");
        	riga6.createCell(1).setCellFormula("COUNTIF(elenco_pratiche_Scia!Q2:Q"+i+",\"SI\")");
        	riga7.createCell(1).setCellFormula("B1-COUNTBLANK(elenco_pratiche_Scia!M2:M"+i+") ");
            
        	if(xls != null) {
    			response.setContentType("application/vnd.ms-excel");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_SCIA.xls");
            }
            else {
    			response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_SCIA.ods");
            	
            }
        	
			
			out = response.getOutputStream();
			wb.write(out);
			wb.close();

			out.flush();

		} catch (Exception e) {
			Log.APP.error("Errore estrazioni SCIA", e);
			ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI,
					"Errore nell'esecuzione del controller /estrazioni/scia", e, null, utente);
			erroriAction.saveError(errore);

		}

		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
    @RequestMapping("/estrazioni/pdc")
    public String estrazioniPDC(Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
            List<Enti> enti = entiService.getListaEntiPerRicerca(connectedUser);
            model.addAttribute("entiRicerca", enti);
            Integer enteAcasoPerColumnModel = null;
            for (UtenteRuoloEnteDTO ruoloEnte : connectedUser.getUtenteRuoloEnte()) {
                enteAcasoPerColumnModel = ruoloEnte.getIdEnte();
                break;
            }
            model.addAttribute("estrazioniPdcColumnModel", configurationService.getEstrazioniPdcColumnModel(enteAcasoPerColumnModel, null));
            Filter filter = (Filter) request.getSession().getAttribute("estrazioniSciaFilter");
            if (filter == null) {
                filter = new Filter();
            }
            model.addAttribute("filtroRicerca", filter);

        } catch (Exception e) {
            Log.APP.error("Errore estrazioni PDC", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI, "Errore nell'esecuzione del controller /estrazioni/pdc", e, null, utente);
            erroriAction.saveError(errore);

        }
        return ESTRAZIONI_PDC;
    }
    
    @RequestMapping("/estrazioni/pdc/ajax")
    public String estrazioniPDCAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
    	GridEstrazioniPDCBean json = new GridEstrazioniPDCBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json = praticheAction.getEstrazionePDC(request, paginator);
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI_AJAX, "Errore nell'esecuzione del controller /estrazioni/pdc/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }
    
    
	@RequestMapping("/excel/pdc")
	public void exportPDC(Model model, HttpServletRequest request, HttpServletResponse response,  @RequestParam(value = "xls", required = false) Integer xls) throws Exception {
		Utente utente = utentiService.getUtenteConnesso(request);
		OutputStream out = null;
		try {
			UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
			Filter filter = (Filter) request.getSession().getAttribute("estrazioniPdcFilter");
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("elenco_pratiche_Pdc");

			// Create a row and put some cells in it. Rows are 0 based.
			HSSFRow row = sheet.createRow(0);

			// Create a cell and put a value in it.
			HSSFCell cell = row.createCell(0);

			cell.setCellValue("IdPratica");
			row.createCell(1).setCellValue("Identificativo Pratica");
			row.createCell(2).setCellValue("Stato");
			row.createCell(3).setCellValue("Protocollo");
			row.createCell(4).setCellValue("In carico a");
			row.createCell(5).setCellValue("Istruttore");
			row.createCell(6).setCellValue("Data Ricezione");
			row.createCell(7).setCellValue("Data Protocollazione");
			row.createCell(8).setCellValue("Data Avvio pre diniego");
			row.createCell(9).setCellValue("Data ricezione controdeduzione pre diniego");
			row.createCell(10).setCellValue("Data richiesta integrazione art20");
			row.createCell(11).setCellValue("Data ricezione integrazione art20");
			row.createCell(12).setCellValue("Data richiesta adempimenti");
			row.createCell(13).setCellValue("Data ricezione adempimenti");
			row.createCell(14).setCellValue("Data rilascio");
			row.createCell(15).setCellValue("Data diniego definitivo");
			row.createCell(16).setCellValue("Data Chiusura");
			row.createCell(17).setCellValue("Tempo reale");
			row.createCell(18).setCellValue("Sosp art10");
			row.createCell(19).setCellValue("Sosp art20");
			row.createCell(20).setCellValue("Sosp adempimenti");
			row.createCell(21).setCellValue("Tempo Procedimento");
			// Write the output
			
			CellStyle cellStyle = wb.createCellStyle();
    		CreationHelper createHelper = wb.getCreationHelper();
    		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy"));
			
            List<EstrazioniPdcDTO> listPratichePDC = praticheService.listPratichePDC(filter); 
            int i = 1;
            for(EstrazioniPdcDTO pdc : listPratichePDC) {
            	HSSFRow riga = sheet.createRow(i);
            	riga.createCell(0).setCellValue(pdc.getId_Pratica());
            	riga.createCell(1).setCellValue(pdc.getIdentificativo_pratica());
            	riga.createCell(2).setCellValue(pdc.getStato());
            	riga.createCell(3).setCellValue(pdc.getProtocollo());
            	riga.createCell(4).setCellValue(pdc.getIn_carico_a());
            	riga.createCell(5).setCellValue(pdc.getIstruttore());
            	
            	HSSFCell cell6 = riga.createCell(6);
        		cell6.setCellStyle(cellStyle);
            	if(pdc.getData_ricezione() != null) {
            		cell6.setCellValue(pdc.getData_ricezione());
            	}
            	
            	HSSFCell cell7 = riga.createCell(7);
        		cell7.setCellStyle(cellStyle);
            	if(pdc.getData_protocollazione() != null) {
            		cell7.setCellValue(pdc.getData_protocollazione());
            	}
            	
            	HSSFCell cell8 = riga.createCell(8);
        		cell8.setCellStyle(cellStyle);
            	if(pdc.getData_avvio_pre_diniego() != null) {
            		cell8.setCellValue(pdc.getData_avvio_pre_diniego());
            	}
            	
            	HSSFCell cell9 = riga.createCell(9);
        		cell9.setCellStyle(cellStyle);
            	if(pdc.getData_rice_contrDeduz_pre_diniego() != null) {
            		cell9.setCellValue(pdc.getData_rice_contrDeduz_pre_diniego());
            	}
            	
            	HSSFCell cell10 = riga.createCell(10);
        		cell10.setCellStyle(cellStyle);
            	if(pdc.getData_rich_integr_art20() != null) {
            		cell10.setCellValue(pdc.getData_rich_integr_art20());
            	}
            	
            	HSSFCell cell11 = riga.createCell(11);
        		cell11.setCellStyle(cellStyle);
            	if(pdc.getData_rice_integr_art20() != null) {
            		cell11.setCellValue(pdc.getData_rice_integr_art20());
            	}
            	
            	HSSFCell cell12 = riga.createCell(12);
        		cell12.setCellStyle(cellStyle);
            	if(pdc.getData_rich_adempimenti()!= null) {
            		cell12.setCellValue(pdc.getData_rich_adempimenti());
            	}
            	
            	HSSFCell cell13 = riga.createCell(13);
        		cell13.setCellStyle(cellStyle);
            	if(pdc.getData_rice_adempimenti() != null) {
            		cell13.setCellValue(pdc.getData_rice_adempimenti());
            	}
            	
            	HSSFCell cell14 = riga.createCell(14);
        		cell14.setCellStyle(cellStyle);
            	if(pdc.getData_rilascio() != null) {
            		cell14.setCellValue(pdc.getData_rilascio());
            	}
            	
            	HSSFCell cell15 = riga.createCell(15);
        		cell15.setCellStyle(cellStyle);
            	if(pdc.getData_diniego_def() != null) {
            		cell15.setCellValue(pdc.getData_diniego_def());
            	}
            	
            	HSSFCell cell16 = riga.createCell(16);
        		cell16.setCellStyle(cellStyle);
            	if(pdc.getData_chiusura() != null) {
            		cell16.setCellValue(pdc.getData_chiusura());
            	}
            	
            	String formulaTr ="IF(Q"+(i+1)+"=0,0,Q"+(i+1)+"-H"+(i+1)+")";
            	riga.createCell(17).setCellFormula(formulaTr);
            	
            	String formulaSosp10 ="IF(J"+(i+1)+"=0,0,J"+(i+1)+"-I"+(i+1)+")";
            	riga.createCell(18).setCellFormula(formulaSosp10);
            	
            	String formulaSosp20 ="IF(L"+(i+1)+"=0,0,L"+(i+1)+"-K"+(i+1)+")";
            	riga.createCell(19).setCellFormula(formulaSosp20);
            	
            	String formulaSospAd ="IF(N"+(i+1)+"=0,0,N"+(i+1)+"-M"+(i+1)+")";
            	riga.createCell(20).setCellFormula(formulaSospAd);
            	
            	String formulaTp ="IF(Q"+(i+1)+"=0,"+"\"IN CORSO"+"\""+",R"+(i+1)+"-S"+(i+1)+"-T"+(i+1)+"-U"+(i+1)+")";
            	riga.createCell(21).setCellFormula(formulaTp);
            	
            	i++;
            }
            
            HSSFSheet riepilogo = wb.createSheet("Riepilogo");
            HSSFRow riga1 = riepilogo.createRow(0);
            HSSFRow riga2 = riepilogo.createRow(1);
            HSSFRow riga3 = riepilogo.createRow(2);
            HSSFRow riga4 = riepilogo.createRow(3);
            HSSFRow riga5 = riepilogo.createRow(4);
            HSSFRow riga6 = riepilogo.createRow(5);
            HSSFRow riga7 = riepilogo.createRow(6);
            riga1.createCell(0).setCellValue("Numero procedimenti avviati");
        	riga1.createCell(1).setCellFormula("COUNTA(elenco_pratiche_Pdc!A2:A"+i+")");
        	
        	riga2.createCell(0).setCellValue("Numero procedimenti in corso");
        	riga2.createCell(1).setCellFormula("COUNTIF(elenco_pratiche_Pdc!V2:V"+i+",\"IN CORSO\")");
        	
        	riga3.createCell(0).setCellValue("Numero procedimenti chiusi nei termini");
        	riga3.createCell(1).setCellFormula("COUNTIF(elenco_pratiche_Pdc!V2:V"+i+",\"<120\") ");
        	
        	riga4.createCell(0).setCellValue("Numero procedimenti protratti oltre il termine");
        	riga4.createCell(1).setCellFormula("COUNTIF(elenco_pratiche_Pdc!V2:V"+i+",\">120\") ");
        	
        	riga5.createCell(0).setCellValue("Durata media dei procedimenti chiusi nei termini");
        	riga5.createCell(1).setCellFormula("AVERAGE(elenco_pratiche_Pdc!V2:V"+i+") ");
        	
        	riga6.createCell(0).setCellValue("Numero procedimenti sospesi per integrazione");
        	riga6.createCell(1).setCellFormula("B1-COUNTBLANK(elenco_pratiche_Pdc!K2:K"+i+") ");
        	
        	riga7.createCell(0).setCellValue("Numero procedimenti chiusi con diniego");
        	riga7.createCell(1).setCellFormula("B1-COUNTBLANK(elenco_pratiche_Pdc!P2:P"+i+") ");
        	
        	if(xls != null) {
    			response.setContentType("application/vnd.ms-excel");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_PDC.xls");
            }
            else {
    			response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_PDC.ods");
            	
            }
//			response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
//			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_PDC.ods");
			out = response.getOutputStream();
			wb.write(out);
			wb.close();

			out.flush();

		} catch (Exception e) {
			Log.APP.error("Errore estrazioni PDC", e);
			ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI,
					"Errore nell'esecuzione del controller /estrazioni/pdc", e, null, utente);
			erroriAction.saveError(errore);

		}

		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
    @RequestMapping("/estrazioni/agib")
    public String estrazioniAGIB(Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
            List<Enti> enti = entiService.getListaEntiPerRicerca(connectedUser);
            model.addAttribute("entiRicerca", enti);
            Integer enteAcasoPerColumnModel = null;
            for (UtenteRuoloEnteDTO ruoloEnte : connectedUser.getUtenteRuoloEnte()) {
                enteAcasoPerColumnModel = ruoloEnte.getIdEnte();
                break;
            }
            model.addAttribute("estrazioniAgibColumnModel", configurationService.getEstrazioniAgibColumnModel(enteAcasoPerColumnModel, null));
            Filter filter = (Filter) request.getSession().getAttribute("estrazioniSciaFilter");
            if (filter == null) {
                filter = new Filter();
            }
            model.addAttribute("filtroRicerca", filter);

        } catch (Exception e) {
            Log.APP.error("Errore estrazioni PDC", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI, "Errore nell'esecuzione del controller /estrazioni/agib", e, null, utente);
            erroriAction.saveError(errore);

        }
        return ESTRAZIONI_AGIB;
    }
    
    @RequestMapping("/estrazioni/agib/ajax")
    public String estrazioniAGIBAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
    	GridEstrazioniAGIBBean json = new GridEstrazioniAGIBBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json = praticheAction.getEstrazioneAGIB(request, paginator);
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI_AJAX, "Errore nell'esecuzione del controller /estrazioni/agib/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }
    
    
	@RequestMapping("/excel/agib")
	public void exportAGIB(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "xls", required = false) Integer xls) throws Exception {
		Utente utente = utentiService.getUtenteConnesso(request);
		OutputStream out = null;
		try {
			UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
			Filter filter = (Filter) request.getSession().getAttribute("estrazioniAgibFilter");
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("elenco_pratiche_Agib");

			// Create a row and put some cells in it. Rows are 0 based.
			HSSFRow row = sheet.createRow(0);

			// Create a cell and put a value in it.
			HSSFCell cell = row.createCell(0);

			cell.setCellValue("IdPratica");
			row.createCell(1).setCellValue("Identificativo Pratica");
			row.createCell(2).setCellValue("Stato");
			row.createCell(3).setCellValue("Protocollo");
			row.createCell(4).setCellValue("In carico a");
			row.createCell(5).setCellValue("Istruttore");
			row.createCell(6).setCellValue("Data Ricezione");
			row.createCell(7).setCellValue("Data Protocollazione");
			row.createCell(8).setCellValue("Data richiesta integrazione");
			row.createCell(9).setCellValue("Data integrazione");
			row.createCell(10).setCellValue("Data parere positivo");
			row.createCell(11).setCellValue("Data parere contrario");
			row.createCell(12).setCellValue("Data Chiusura");
			row.createCell(13).setCellValue("Tempi procedimenti");
			row.createCell(14).setCellValue("Sospesa per integrazione");
			row.createCell(15).setCellValue("Procedimenti in corso");
			
			// Write the output
			
			CellStyle cellStyle = wb.createCellStyle();
    		CreationHelper createHelper = wb.getCreationHelper();
    		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yy"));
			
            List<EstrazioniAgibDTO> listPraticheAGIB = praticheService.listPraticheAGIB(filter); 
            int i = 1;
            for(EstrazioniAgibDTO agib : listPraticheAGIB) {
            	HSSFRow riga = sheet.createRow(i);
            	riga.createCell(0).setCellValue(agib.getId_Pratica());
            	riga.createCell(1).setCellValue(agib.getIdentificativo_pratica());
            	riga.createCell(2).setCellValue(agib.getStato());
            	riga.createCell(3).setCellValue(agib.getProtocollo());
            	riga.createCell(4).setCellValue(agib.getIn_carico_a());
            	riga.createCell(5).setCellValue(agib.getIstruttore());
            	
            	HSSFCell cell6 = riga.createCell(6);
        		cell6.setCellStyle(cellStyle);
            	if(agib.getData_ricezione() != null) {
            		cell6.setCellValue(agib.getData_ricezione());
            	}
            	
            	HSSFCell cell7 = riga.createCell(7);
        		cell7.setCellStyle(cellStyle);
            	if(agib.getData_protocollazione() != null) {
            		cell7.setCellValue(agib.getData_protocollazione());
            	}
            	
            	HSSFCell cell8 = riga.createCell(8);
        		cell8.setCellStyle(cellStyle);
            	if(agib.getData_ric_integrazione() != null) {
            		cell8.setCellValue(agib.getData_ric_integrazione());
            	}
            	
            	HSSFCell cell9 = riga.createCell(9);
        		cell9.setCellStyle(cellStyle);
            	if(agib.getData_Integrazione() != null) {
            		cell9.setCellValue(agib.getData_Integrazione());
            	}
            	
            	HSSFCell cell10 = riga.createCell(10);
        		cell10.setCellStyle(cellStyle);
            	if(agib.getData_parere_positivo() != null) {
            		cell10.setCellValue(agib.getData_parere_positivo());
            	}
            	
            	HSSFCell cell11 = riga.createCell(11);
        		cell11.setCellStyle(cellStyle);
            	if(agib.getData_parere_contrario() != null) {
            		cell11.setCellValue(agib.getData_parere_contrario());
            	}
            	
            	HSSFCell cell12 = riga.createCell(12);
        		cell12.setCellStyle(cellStyle);
            	if(agib.getData_chiusura() != null) {
            		cell12.setCellValue(agib.getData_chiusura());
            	}
            	
            	String formulaTr ="IF(I"+(i+1)+"=\"\",0,I"+(i+1)+"-H"+(i+1)+")";
            	riga.createCell(13).setCellFormula(formulaTr);
            	
            	String formulaSospInt = "IF(AND(J"+(i+1)+"=\"\",K"+(i+1)+"=\"\",L"+(i+1)+"=\"\",M"+(i+1)+"=\"\",I"+(i+1)+"<>\"\"),\"SI\",\"\")";
            	riga.createCell(14).setCellFormula(formulaSospInt);
            	
            	String formulaProc = "IF(AND(J"+(i+1)+"=\"\",K"+(i+1)+"=\"\",L"+(i+1)+"=\"\",M"+(i+1)+"=\"\",I"+(i+1)+"=\"\"),\"SI\",\"\")";
            	riga.createCell(15).setCellFormula(formulaProc);
            	
            	i++;
            }
            
            HSSFSheet riepilogo = wb.createSheet("Riepilogo");
            HSSFRow riga1 = riepilogo.createRow(0);
            HSSFRow riga2 = riepilogo.createRow(1);
            HSSFRow riga3 = riepilogo.createRow(2);
            HSSFRow riga4 = riepilogo.createRow(3);
            HSSFRow riga5 = riepilogo.createRow(4);
            HSSFRow riga6 = riepilogo.createRow(5);
            HSSFRow riga7 = riepilogo.createRow(6);
            riga1.createCell(0).setCellValue("Numero procedimenti avviati");
        	riga1.createCell(1).setCellFormula("COUNTA(elenco_pratiche_Agib!A2:A"+i+")");
        	
        	riga2.createCell(0).setCellValue("Numero procedimenti in corso");
        	riga2.createCell(1).setCellFormula("COUNTIF(elenco_pratiche_Agib!P2:P"+i+",\"SI\")");
        	
        	riga3.createCell(0).setCellValue("Numero procedimenti chiusi nei termini");
        	riga3.createCell(1).setCellFormula("B1-B2-B4-B6");
        	
        	riga4.createCell(0).setCellValue("Numero procedimenti protratti oltre il termine");
        	riga4.createCell(1).setCellFormula("COUNTIF(elenco_pratiche_Agib!N2:N"+i+",\">30\") ");
        	
        	riga5.createCell(0).setCellValue("Durata media dei procedimenti chiusi nei termini");
        	riga5.createCell(1).setCellFormula("AVERAGE(elenco_pratiche_Agib!N2:N"+i+") ");
        	
        	riga6.createCell(0).setCellValue("Numero procedimenti sospesi per integrazione");
        	riga6.createCell(1).setCellFormula("COUNTIF(elenco_pratiche_Agib!O2:O"+i+",\"SI\")");
        	
        	riga7.createCell(0).setCellValue("Numero procedimenti chiusi con diniego");
        	riga7.createCell(1).setCellFormula("B1-COUNTBLANK(elenco_pratiche_Agib!L2:L"+i+") ");

            
//			response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
//			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_AGIB.ods");
			if(xls != null) {
    			response.setContentType("application/vnd.ms-excel");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_AGIB.xls");
            }
            else {
    			response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_AGIB.ods");
            	
            }
			
			out = response.getOutputStream();
			wb.write(out);
			wb.close();

			out.flush();

		} catch (Exception e) {
			Log.APP.error("Errore estrazioni PDC", e);
			ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI,
					"Errore nell'esecuzione del controller /estrazioni/pdc", e, null, utente);
			erroriAction.saveError(errore);

		}

		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	@RequestMapping("/estrazioni/cilaToDo")
    public String estrazioniCILAToDo(Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
            List<Enti> enti = entiService.getListaEntiPerRicerca(connectedUser);
            model.addAttribute("entiRicerca", enti);
            Integer enteAcasoPerColumnModel = null;
            for (UtenteRuoloEnteDTO ruoloEnte : connectedUser.getUtenteRuoloEnte()) {
                enteAcasoPerColumnModel = ruoloEnte.getIdEnte();
                break;
            }
            Filter filter = (Filter) request.getSession().getAttribute("estrazioniCilaFilter");
            if (filter == null) {
                filter = new Filter();
            }
            model.addAttribute("filtroRicerca", filter);

        } catch (Exception e) {
            Log.APP.error("Errore estrazioni CILA TODO", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI, "Errore nell'esecuzione del controller /estrazioni/cilaToDo", e, null, utente);
            erroriAction.saveError(errore);

        }
        return ESTRAZIONI_CILA_TO_DO;
    }
	
	@RequestMapping("/estrazioni/agibToDo")
    public String estrazioniAGIBToDo(Model model, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
            List<Enti> enti = entiService.getListaEntiPerRicerca(connectedUser);
            model.addAttribute("entiRicerca", enti);
            Integer enteAcasoPerColumnModel = null;
            for (UtenteRuoloEnteDTO ruoloEnte : connectedUser.getUtenteRuoloEnte()) {
                enteAcasoPerColumnModel = ruoloEnte.getIdEnte();
                break;
            }
            Filter filter = (Filter) request.getSession().getAttribute("estrazioniAgibFilter");
            if (filter == null) {
                filter = new Filter();
            }
            model.addAttribute("filtroRicerca", filter);

        } catch (Exception e) {
            Log.APP.error("Errore estrazioni AGIB TODO", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI, "Errore nell'esecuzione del controller /estrazioni/cilaToDo", e, null, utente);
            erroriAction.saveError(errore);

        }
        return ESTRAZIONI_AGIB_TO_DO;
    }
	
	  @Transactional
	    public void salvaEvento(PraticheEventi praticaEvento) throws Exception {
	        praticaDao.insert(praticaEvento);
	    }
	
	@RequestMapping("/excel/agibToDo")
	public void exportAGIBToDo(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Utente utente = utentiService.getUtenteConnesso(request);
		OutputStream out = null;
		try {
			UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
			String xls = request.getParameter("xls");
			Filter filter = filterSerializer.getSearchFilter(request);
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("elenco_pratiche");

			// Create a row and put some cells in it. Rows are 0 based.
			HSSFRow row = sheet.createRow(0);

			// Create a cell and put a value in it.
			HSSFCell cell = row.createCell(0);

			cell.setCellValue("IdPratica");
			row.createCell(1).setCellValue("Identificativo Pratica");
			row.createCell(2).setCellValue("Stato");
			row.createCell(3).setCellValue("Protocollo");
			row.createCell(4).setCellValue("In carico a");
			row.createCell(5).setCellValue("Istruttore");
			row.createCell(6).setCellValue("Data Ricezione");
			row.createCell(7).setCellValue("Data Protocollazione");
			// Write the output
			
            List<EstrazioniAgibDTO> listPraticheCILA = praticheService.listPraticheAGIBToDo(filter); 
            int i = 1;
            for(EstrazioniAgibDTO cila : listPraticheCILA) {
            	HSSFRow riga = sheet.createRow(i);
            	riga.createCell(0).setCellValue(cila.getId_Pratica());
            	riga.createCell(1).setCellValue(cila.getIdentificativo_pratica());
            	riga.createCell(2).setCellValue(cila.getStato());
            	riga.createCell(3).setCellValue(cila.getProtocollo());
            	riga.createCell(4).setCellValue(cila.getIn_carico_a());
            	riga.createCell(5).setCellValue(cila.getIstruttore());
            	riga.createCell(6).setCellValue(Utilities.dateToString(cila.getData_ricezione()));
            	riga.createCell(7).setCellValue(Utilities.dateToString(cila.getData_protocollazione()));
            	i++;
            }
        	
			//response.setContentType("application/vnd.ms-excel");
//			response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
//			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_CILA_ToDo.ods");
            if(xls.equals("1")) {
    			response.setContentType("application/vnd.ms-excel");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_AGIB_ToDo.xls");
            }
            else {
    			response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_AGIB_ToDo.ods");
            	
            }
			out = response.getOutputStream();
			wb.write(out);
			wb.close();

			out.flush();

		} catch (Exception e) {
			Log.APP.error("Errore estrazioni AGIBtodo", e);
			ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI,
					"Errore nell'esecuzione del controller /estrazioni/agibtodo", e, null, utente);
			erroriAction.saveError(errore);

		}

		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	
	@RequestMapping("/excel/cilaToDo")
	public void exportCILAToDo(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Utente utente = utentiService.getUtenteConnesso(request);
		OutputStream out = null;
		try {
			UtenteDTO connectedUser = utentiService.getUtenteConnessoDTO(request);
			String xls = request.getParameter("xls");
			Filter filter = filterSerializer.getSearchFilter(request);
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("elenco_pratiche");

			// Create a row and put some cells in it. Rows are 0 based.
			HSSFRow row = sheet.createRow(0);

			// Create a cell and put a value in it.
			HSSFCell cell = row.createCell(0);

			cell.setCellValue("IdPratica");
			row.createCell(1).setCellValue("Identificativo Pratica");
			row.createCell(2).setCellValue("Stato");
			row.createCell(3).setCellValue("Protocollo");
			row.createCell(4).setCellValue("In carico a");
			row.createCell(5).setCellValue("Istruttore");
			row.createCell(6).setCellValue("Data Ricezione");
			row.createCell(7).setCellValue("Data Protocollazione");
			// Write the output
			
            List<EstrazioniCilaDTO> listPraticheCILA = praticheService.listPraticheCILAToDo(filter); 
            int i = 1;
            for(EstrazioniCilaDTO cila : listPraticheCILA) {
            	HSSFRow riga = sheet.createRow(i);
            	riga.createCell(0).setCellValue(cila.getId_Pratica());
            	riga.createCell(1).setCellValue(cila.getIdentificativo_pratica());
            	riga.createCell(2).setCellValue(cila.getStato());
            	riga.createCell(3).setCellValue(cila.getProtocollo());
            	riga.createCell(4).setCellValue(cila.getIn_carico_a());
            	riga.createCell(5).setCellValue(cila.getIstruttore());
            	riga.createCell(6).setCellValue(Utilities.dateToString(cila.getData_ricezione()));
            	riga.createCell(7).setCellValue(Utilities.dateToString(cila.getData_protocollazione()));
            	i++;
            }
        	
			//response.setContentType("application/vnd.ms-excel");
//			response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
//			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_CILA_ToDo.ods");
            if(xls.equals("1")) {
    			response.setContentType("application/vnd.ms-excel");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_CILA_ToDo.xls");
            }
            else {
    			response.setContentType("application/vnd.oasis.opendocument.spreadsheet");
    			response.addHeader("Content-disposition", "attachment; filename=" + "estrazione_CILA_ToDo.ods");
            	
            }
			out = response.getOutputStream();
			wb.write(out);
			wb.close();

			out.flush();

		} catch (Exception e) {
			Log.APP.error("Errore estrazioni CILA", e);
			ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_GESTISCI,
					"Errore nell'esecuzione del controller /estrazioni/cila", e, null, utente);
			erroriAction.saveError(errore);

		}

		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
    @RequestMapping("pratiche/integrazione/lavorata")
    public ModelAndView integrazioneLavorata(Model model,
            HttpServletRequest request,
            HttpServletResponse response, 
            @ModelAttribute("currentTab") String tab) throws Exception {
        String idPraticaString = request.getParameter("id_pratica");
        HttpSession session = request.getSession();
        Integer idPratica;
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        
        if (idPraticaString != null) {
            idPratica = getIdPratica(idPraticaString);
            MultiSubSessionManager.setAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA, idPratica);
        } else {
            idPratica = (Integer) MultiSubSessionManager.getAttribute(request, SessionConstants.ID_PRATICA_SELEZIONATA);
        }
        try {
            pratica = praticheService.getPratica(idPratica);
            pratica.setIntegrazione("N");
            praticheAction.aggiornaPratica(pratica);
        } catch (Exception ex) {
            Message error = new Message();
            error.setMessages(Arrays.asList("Si sono verificati degli errori"));
            request.setAttribute("message", errori);
            Log.APP.error("Si sono verificati degli errori", ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_DETTAGLIO, "Errore nell'esecuzione del controller pratiche/integrazione/lavorata", ex, pratica, utente);
            erroriAction.saveError(errore);
        }
        
        return new ModelAndView(REDIRECT_DETTAGLIO_PRATICA_WITH_SESSION_ID+MultiSubSessionManager.getSessionId(request));
    }


    @RequestMapping("/pratica/tecnico_procuratore")
    public String readTechnicianAgent(Model model,
            @RequestParam(value = "identificativoPratica", required = true) String identifier) throws Exception {
    	AnagraficaDTO anagraficaDTO  = null;
    	Pratica pratica = praticaDao.requirePraticaByCodicePratica(identifier);
    	if(SUAP_INSTITUTION_XML_TECHNICIAN.contains(pratica.getIdProcEnte().getIdEnte().getIdEnte())) 
    		anagraficaDTO = readTechnicianAgentFromXml(pratica);    		
    	else 
    		anagraficaDTO = readTechnicianAgentFromPaperwork(pratica);
    	
    	model.addAttribute("json", gson.toJson(anagraficaDTO));
    	return AJAX_PAGE;
    }

	@RequestMapping("/pratica/procuratore_per_evento_esterno")
    public String readExternalOperator(Model model,
    		@RequestParam(value = "idPratica", required = true) String idPratica,
    		@RequestParam(value = "idPraticaEvento", required = true) String idPraticaEvento) throws Exception {
    	
    	Log.APP.info("Ricerca procuratore evento esterno: idPratica " + idPratica);
    	Log.APP.info("Ricerca procuratore evento esterno: idPraticaEvento " + idPraticaEvento);

    	Integer paperworkId = Integer.parseInt(idPratica);
    	Integer paperworkEventId = Integer.parseInt(idPraticaEvento);
    	
    	List<PraticheEventi> events = praticheService.findSortedPraticheEventi(paperworkId);
    	
    	String taxCode = findTaxCodeTechnicianFromIntegrationEvents(events, paperworkEventId);
    	AnagraficaDTO anagraficaDTO =null;
    	
    	if(taxCode == null) {
    		Pratica pratica = praticaDao.requirePraticaByIdPratica(paperworkId);
    		anagraficaDTO = readTechnicianAgentFromPaperwork(pratica);
    	}else {
	    	Anagrafica personalData = anagraficheService.findAnagraficaByCodFiscale(taxCode);
	    	anagraficaDTO = mapper.map(personalData, AnagraficaDTO.class);
    	}

    	model.addAttribute("json", gson.toJson(anagraficaDTO));
    	return AJAX_PAGE;
    }
    
	private Message decompileStringMessage(String input) {
    	Message message = new Message();
        if (input != null && input.length() != 0) {
            Matcher m = Pattern.compile("(\\w)#(.+)").matcher(input);
            if(m.find()) {
            	String status = m.group(1);
            	String msg = m.group(2);
        		message.setError("E".equalsIgnoreCase(status));
        		message.setMessages(Arrays.asList(msg));
            }
        } 
        return message;
    }
    
    private boolean isRequestFrom(List<Enti> enti, String requestedTown) {
        boolean isFromGrantedTown = false;
        int i = 0;
        while(!isFromGrantedTown && i < enti.size()) {
        	isFromGrantedTown = requestedTown.equalsIgnoreCase(enti.get(i++).getCitta());
        }
        return isFromGrantedTown;
    }
    
    private String findTaxCodeTechnicianFromIntegrationEvents(List<PraticheEventi> events, Integer idPraticaEvento) {
    	boolean foundTargetEvent = false;
    	
    	PraticheEventi modProcAfter = null;
    	PraticheEventi modProcBefore = null;
    	PraticheEventi targetEvent = null;
    	
    	for(PraticheEventi event: events) {
    		boolean isModProc = "MODPROC".equals(event.getIdEvento().getCodEvento());
    		foundTargetEvent = foundTargetEvent || idPraticaEvento.equals(event.getIdPraticaEvento());
    		targetEvent = targetEvent == null ? (idPraticaEvento.equals(event.getIdPraticaEvento())?event:null): targetEvent;
    		
    		if(isModProc) {
    			if(foundTargetEvent) 
    				modProcBefore = modProcBefore == null ? event : modProcBefore;
    			else 
    				modProcAfter = event;
    		}
    	}

    	String note = null; 
    	String taxCode = null;
    	if(modProcAfter != null) {
    		note = modProcAfter.getNote();
    		Log.APP.debug("Ricerca procuratore evento esterno: modifica procuratore successiva operazione indicata");
    	}
    	else if(modProcBefore != null) {
    		note = modProcBefore.getNote();
    		Log.APP.debug("Ricerca procuratore evento esterno: modifica procuratore precedente operazione indicata");
    	}
    	Log.APP.debug("Ricerca procuratore evento esterno: Nota estratta: " + note);
    	if(note != null) {
	    	Matcher m = Pattern.compile("^"+Constants.CHANGE_TECHNICIAN_AGENT_1+"(\\w+?)"+Constants.CHANGE_TECHNICIAN_AGENT_2+"(\\w+)$").matcher(note);
	    	if(m.find()) {
	    		if(modProcAfter != null)
	    			taxCode = m.group(1);  	
	        	else if(modProcBefore != null)
	    			taxCode = m.group(2);  	
	    	}
	    	Log.APP.debug("Ricerca procuratore evento esterno: Codice fiscale estratto: " + taxCode);
    	}
    	return taxCode;
    	
	}
    
    private AnagraficaDTO readTechnicianAgentFromPaperwork(Pratica pratica) throws Exception {
    	Anagrafica anagrafica = praticheService.readTechnicianAgentFromPaperwork(pratica);
    	AnagraficaDTO anagraficaDTO = null; 
    	if(anagrafica != null)
    		anagraficaDTO = AnagraficheSerializer.serializeAnagrafica(anagrafica);
		return anagraficaDTO;
	}
    
    
    private AnagraficaDTO readTechnicianAgentFromXml(Pratica pratica) throws Exception {
    	Anagrafica anagrafica = praticheService.readTechnicianAgentFromXml(pratica);
    	AnagraficaDTO anagraficaDTO = null; 
    	if(anagrafica != null)
    		anagraficaDTO = mapper.map(anagrafica, AnagraficaDTO.class);
		
		return anagraficaDTO;
    }
    
    
}

