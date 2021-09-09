
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.gov.impresainungiorno.schema.suap.ri.spc.IscrizioneImpresaRiSpcResponse;
import it.wego.cross.actions.AnagraficaAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.UtentiAction;
import it.wego.cross.beans.grid.GridAnagraficaBean;
import it.wego.cross.beans.grid.GridIdStringListBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.Constants;
import it.wego.cross.dto.AnagraficaDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.RecapitoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.registroimprese.DatiIdentificativiRIDTO;
import it.wego.cross.dto.registroimprese.ErroreRIDTO;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.AnagraficaRecapiti;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.LkProvincie;
import it.wego.cross.entity.Pratica;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.anagrafe.GestioneAnagrafePersonaFisica;
import it.wego.cross.serializer.AnagraficheSerializer;
import it.wego.cross.serializer.RecapitiSerializer;
import it.wego.cross.serializer.RegistroImpreseSerializer;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.ComuniService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.RegistroImpreseService;
import it.wego.cross.utils.AnagraficaUtils;
import it.wego.cross.utils.Log;
import it.wego.cross.validator.IsValid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import it.wego.cross.constants.SessionConstants;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 *
 * @author giuseppe
 */
@Controller
public class AnagraficaController extends AbstractController {

    //variabili relative alle comunicazione utente sulle operazioni effettuate
    private final Character OPERAZIONE_MODIFICA = 'M';
    private final Character OPERAZIONE_AGGIUNTA = 'A';
    private final String OPERAZIONE_EFFETTUATA = "operazioneeffettuata";
    //
    @Autowired
    private AnagraficheService anagraficheService;
    @Autowired
    private AnagraficaAction anagraficaAction;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private RegistroImpreseService registroImpreseService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    protected Validator validator;
    @Autowired
    protected RegistroImpreseSerializer registroImpreseSerializer;
    @Autowired
    protected IsValid valid;
    @Autowired
    protected UtentiAction utentiAction;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private AnagraficaUtils anagraficaUtils;
    private static final String AJAX = "ajax";
    private static final String DETTAGLIO_ANAGRAFICA = "anagrafica_dettaglio";
    private static final String DETTAGLIO_AZIENDA = "azienda_dettaglio";
    private static final String NUOVA_ANAGRAFICA = "anagrafica_nuova";
    private static final String RICERCA_ANAGRAFICA = "anagrafica_ricerca";
    private static final String REDIRECT_RICERCA_ANAGRAFICA = "redirect:/gestione/anagrafiche/list.htm";
    private static final String MODIFICA_ANAGRAFICA = "anagrafica_modifica";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        // true passed to CustomDateEditor constructor means convert empty String to null
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("/gestione/anagrafiche/list")
    public String cercaAnagrafica(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(OPERAZIONE_EFFETTUATA) String operazioneEffettuataatt) {
        String messaggiOperazioneEffettuata = messaggioOperazioneEffettuata(operazioneEffettuataatt);
        model.addAttribute("messaggiooperazioneeffettuata", messaggiOperazioneEffettuata);
        Filter filter = (Filter) request.getSession().getAttribute("gestioneAnagraficheList");
        if (filter == null) {
            filter = new Filter();
        }
        model.addAttribute("filtroRicerca", filter);
        return RICERCA_ANAGRAFICA;
    }

    @RequestMapping("/gestione/anagrafiche/list/ajax")
    public String cercaAnagraficaAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridAnagraficaBean json;
        try {
            json = anagraficaAction.getAnagrafiche(request, paginator, "gestioneAnagraficheList");
            model.addAttribute("json", json.toString());
            return AJAX;
        } catch (Exception e) {
            Message errori = new Message();
            Log.APP.error("Errore reperendo le anagrafiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_GESTIONE_ANAGRAFICHE_LIST_AJAX, "Errore nell'esecuzione del controller /gestione/anagrafiche/list/ajax", e, null, null);
            erroriAction.saveError(errore);
            return RICERCA_ANAGRAFICA;
        }
    }

    @RequestMapping("/gestione/anagrafiche/nuova")
    public String inserisciAnagrafica(Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("anagrafica", new AnagraficaDTO());
        return NUOVA_ANAGRAFICA;
    }

    @RequestMapping("/gestione/anagrafiche/salva")
    public String salva(Model model, HttpServletRequest request,
            @ModelAttribute("anagrafica") AnagraficaDTO anagrafica,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        Message messaggi = new Message();
        try {
            List<String> errori = validaAnagrafica(result, anagrafica, "INSERIMENTO");
            if (!errori.isEmpty()) {
                messaggi.getMessages().addAll(errori);
                model.addAttribute("error", errori);
                model.addAttribute("message", messaggi);
                model.addAttribute("anagrafica", anagrafica);
                return NUOVA_ANAGRAFICA;
            } else {
                anagraficaAction.salvaAnagrafica(anagrafica, new Anagrafica());
                if ("G".equals(anagrafica.getIdTipoPersona())) {
                    redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_AGGIUNTA + "#persona giuridica " + anagrafica.getDenominazione());
                } else if ("F".equals(anagrafica.getIdTipoPersona())) {
                    redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_AGGIUNTA + "#persona fisica " + anagrafica.getCognome() + " " + anagrafica.getNome());
                }

                return REDIRECT_RICERCA_ANAGRAFICA;
            }
        } catch (Exception ex) {
            messaggi.getMessages().add("Errore salvando l'anagrafica");
            model.addAttribute("message", messaggi);
            model.addAttribute("anagrafica", anagrafica);
            Log.APP.error("Errore salvando l'anagrafica", ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_GESTIONE_ANAGRAFICHE_SALVA, "Errore nell'esecuzione del controller /gestione/anagrafiche/salva", ex, null, null);
            erroriAction.saveError(errore);
            return NUOVA_ANAGRAFICA;
        }
    }

    private boolean checkCF(AnagraficaDTO anagrafica, String operazione) {
        boolean result = false;
        String codFiscale = anagrafica.getCodiceFiscale();
        Anagrafica a;
        if (codFiscale != null) {
            if (operazione.equals("INSERIMENTO")) {
                a = anagraficheService.findAnagraficaByCodFiscale(codFiscale);
            } else {
                a = anagraficheService.findAnagraficaByCodFiscaleNotId(codFiscale, anagrafica.getIdAnagrafica());
            }
            if (a != null) {
                result = true;
            }
        }
        return result;
    }

    private boolean checkPartitaIva(AnagraficaDTO anagrafica, String operazione) {
        boolean result = false;
        String partitaIva = anagrafica.getPartitaIva();
        Anagrafica a;
        if (partitaIva != null) {
            if (operazione.equals("INSERIMENTO")) {
                a = anagraficheService.findAnagraficaByPartitaIva(partitaIva);
            } else {
                a = anagraficheService.findAnagraficaByPartitaIvaNotId(partitaIva, anagrafica.getIdAnagrafica());
            }
            if (a != null) {
                result = true;
            }
        }
        return result;
    }

    @RequestMapping("/gestione/anagrafiche/modifica")
    public String modifica(Model model, HttpServletRequest request, HttpServletResponse response) {
        AnagraficaDTO dto = null;
        Map<Integer, String> lista = new HashMap<Integer, String>();
        try {
            Integer idAnagrafica = Integer.valueOf(request.getParameter("idAnagrafica"));
            Anagrafica anagrafica = anagraficheService.findAnagraficaById(idAnagrafica);
            dto = AnagraficheSerializer.serializeAnagrafica(anagrafica);
            Map<Integer, String> listaDB = anagraficheService.getListaTipoIndirizzo();

            for (AnagraficaRecapiti recapito : anagrafica.getAnagraficaRecapitiList()) {
                if (recapito.getIdTipoIndirizzo() != null && recapito.getIdTipoIndirizzo().getIdTipoIndirizzo() != null && listaDB.containsKey(recapito.getIdTipoIndirizzo().getIdTipoIndirizzo())) {
                    listaDB.remove(recapito.getIdTipoIndirizzo().getIdTipoIndirizzo());
                }
            }
            if (anagraficaUtils.getTipoAnagrafica(anagrafica).equals(Constants.PERSONA_FISICA)
                    || anagraficaUtils.getTipoAnagrafica(anagrafica).equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
                for (Map.Entry<Integer, String> entry : listaDB.entrySet()) {
                    if (!entry.getValue().equals(Constants.INDIRIZZO_SEDE)) {
                        lista.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            if (anagraficaUtils.getTipoAnagrafica(anagrafica).equals(Constants.PERSONA_GIURIDICA)) {
                for (Map.Entry<Integer, String> entry : listaDB.entrySet()) {
                    if (!entry.getValue().equals(Constants.INDIRIZZO_DOMICILIO)
                            && !entry.getValue().equals(Constants.INDIRIZZO_RESIDENZA)) {
                        lista.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            // TODO 
            model.addAttribute("attivaRegistroImprese", (Boolean) request.getSession().getAttribute(SessionConstants.ABILITAZIONE_REGISTRO_IMPRESE));

        } catch (Exception e) {
            Log.APP.error("Errore caricando l'anagrafica da modificare", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_GESTIONE_ANAGRAFICHE_MODIFICA, "Errore nell'esecuzione del controller /gestione/anagrafiche/modifica", e, null, null);
            erroriAction.saveError(errore);
        }
        model.addAttribute("ListaTipoIndirizzo", lista);
        model.addAttribute("anagrafica", dto);

        return MODIFICA_ANAGRAFICA;
    }

    @RequestMapping("/gestione/anagrafiche/modifica/ajax")
    public String modificaAjax(Model model, HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("anagrafica") AnagraficaDTO anagrafica,
            BindingResult result) {
        GridIdStringListBean json = new GridIdStringListBean();
        String action = (String) request.getParameter("action");
        List<String> err = new ArrayList<String>();
        try {
            if (action != null && action.equals("eliminaRecapito")) {
                Integer idTipoRecapito = Integer.parseInt(request.getParameter("idTipoRecapito"));
                Integer idAnagrafica = Integer.parseInt(request.getParameter("idAnagrafica"));
                anagraficaAction.eliminaRecapito(idTipoRecapito, idAnagrafica);
                json.setMessages(Arrays.asList("OK"));
            } else {

                if (anagrafica.getRecapiti() != null && anagrafica.getRecapiti().size() > 0) {
                    if (anagrafica.getRecapiti().get(0).getIdRecapito() == null) {
                        //E' un nuovo recapito, non sono presenti altri recapiti, lo forzo a 1 per la validazione. Poiché nuovo indirizzo verrà ignorato
                        anagrafica.getRecapiti().get(0).setIdRecapito(1);
                    }
                    //Viene eseguito il valide per controllare se ci sono problemi su alcuni campi del recapito
                    validator.validate(anagrafica.getRecapiti().get(0), result);
                    err = valid.recapito(result, anagrafica.getRecapiti().get(0));
                    if (err != null) {
                        json.setErrors(err);
                    } else {
                        anagrafica.getRecapiti().get(0).setIdAnagrafica(anagrafica.getIdAnagrafica());//^^CS NON ELIMINARE!!
                        anagraficaAction.aggiungiRecapito(anagrafica.getRecapiti().get(0));
                    }
                } else {
                    String msgError = messageSource.getMessage("error.recapitoNonEsiste", null, Locale.getDefault());
                    err.add(msgError);
                    json.setErrors(err);
                }
            }
        } catch (Exception ex) {
            //String msgError = messageSource.getMessage("error.generale", null, Locale.getDefault());
            String msgError = "Si è generato un errore durante il procedimento di modifica del racapito";
            Log.APP.error(msgError, ex);
            err.add(msgError);
            json.setErrors(err);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_GESTIONE_ANAGRAFICHE_MODIFICA_AJAX, "Errore nell'esecuzione del controller /gestione/anagrafiche/modifica/ajax", ex, null, null);
            erroriAction.saveError(errore);
        }
        String error = json.toString();
        model.addAttribute("json", error);
        return AJAX;
    }

    @RequestMapping("/gestione/anagrafiche/aggiorna")
    public String aggiorna(Model model,
            HttpServletRequest request,
            @ModelAttribute("anagrafica") AnagraficaDTO anagrafica,
            BindingResult result,
            final RedirectAttributes redirectAttributes) {
        Map<Integer, String> lista = new HashMap<Integer, String>();
        try {
            Integer idAnagrafica = Integer.valueOf(request.getParameter("idAnagrafica"));
            Map<Integer, String> listaDB = anagraficheService.getListaTipoIndirizzo();

            for (RecapitoDTO recapito : anagrafica.getRecapiti()) {
                if (recapito.getIdTipoIndirizzo() != null && recapito.getIdTipoIndirizzo() != null && listaDB.containsKey(recapito.getIdTipoIndirizzo())) {
                    listaDB.remove(recapito.getIdTipoIndirizzo());
                }
            }
            if (anagraficaUtils.getTipoAnagrafica(anagrafica).equals(Constants.PERSONA_FISICA)
                    || anagraficaUtils.getTipoAnagrafica(anagrafica).equals(Constants.PERSONA_DITTAINDIVIDUALE)) {
                for (Map.Entry<Integer, String> entry : listaDB.entrySet()) {
                    if (!entry.getValue().equals(Constants.INDIRIZZO_SEDE)) {

                        lista.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            if (anagraficaUtils.getTipoAnagrafica(anagrafica).equals(Constants.PERSONA_GIURIDICA)) {
                for (Map.Entry<Integer, String> entry : listaDB.entrySet()) {
                    if (!entry.getValue().equals(Constants.INDIRIZZO_DOMICILIO)
                            && !entry.getValue().equals(Constants.INDIRIZZO_RESIDENZA)) {
                        lista.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        } catch (Exception e) {
            Log.APP.error("Errore caricando l'anagrafica da modificare", e);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_GESTIONE_ANAGRAFICHE_MODIFICA, "Errore nell'esecuzione del controller /gestione/anagrafiche/modifica", e, null, null);
            erroriAction.saveError(errore);
        }

        Message messaggi = new Message();
        Boolean presenteRecapitoObbligatorio = false;
        List<RecapitoDTO> recapiti = anagrafica.getRecapiti();
        if (!anagrafica.getRecapiti().isEmpty()) {
            for (Integer i = 0; i < anagrafica.getRecapiti().size(); i++) {
                RecapitoDTO recapito = anagrafica.getRecapiti().get(i);
                if (("F".equals(anagrafica.getTipoAnagrafica())) && ("RESIDENZA".equals(recapito.getDescTipoIndirizzo()))) {
                    presenteRecapitoObbligatorio = true;
                }
                if (("G".equals(anagrafica.getTipoAnagrafica())) && ("SEDE".equals(recapito.getDescTipoIndirizzo()))) {
                    presenteRecapitoObbligatorio = true;
                }
                if (!"".equals(recapito.getDescTipoIndirizzo())) {
                } else {
                    recapiti.remove(i.intValue());
                }
            }
        }
        anagrafica.setRecapiti(recapiti);
        try {
            List<String> errori = validaAnagrafica(result, anagrafica, "MODIFICA");
            if (!presenteRecapitoObbligatorio) {
                errori.add("L'anagrafica correntemente visualizzata non possiede il recapito obbligatorio (sede per una persona giuridica, residenza per una persona fisica). Si prega di specificarlo.");
            }
            if (!errori.isEmpty()) {
                messaggi.getMessages().addAll(errori);
                model.addAttribute("error", errori);
                model.addAttribute("message", messaggi);
                model.addAttribute("anagrafica", anagrafica);
                Map<Integer, String> listaDB = anagraficheService.getListaTipoIndirizzo();
                model.addAttribute("ListaTipoIndirizzo", lista);
                return MODIFICA_ANAGRAFICA;
            } else {
                Anagrafica anagraficaEsistente = anagraficheService.findAnagraficaById(anagrafica.getIdAnagrafica());
                //L'aggiornamento del tipo di anagrafica avviene tramite una chiamata ajax quindi non è necessario che venga fatto qui, il tipo di anagrafica non è presente nella request.
                anagrafica.setVarianteAnagrafica(anagraficaEsistente.getVarianteAnagrafica());
                anagraficaAction.aggiornaAnagrafica(anagrafica, anagraficaEsistente);
                if (anagrafica.getTipoAnagrafica().equals("G")) {
                    redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_MODIFICA + "#persona giuridica " + anagrafica.getDenominazione());
                } else if (anagrafica.getTipoAnagrafica().equals("F")) {
                    redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_MODIFICA + "#persona fisica " + anagrafica.getCognome() + " " + anagrafica.getNome());
                }
                model.addAttribute("anagrafica", anagrafica);
            }
            return REDIRECT_RICERCA_ANAGRAFICA;
        } catch (Exception ex) {
            Log.APP.error("Errore salvando l'anagrafica", ex);
            messaggi.getMessages().add("Errore nel salvare l'anagrafica");
            model.addAttribute("message", messaggi);
            model.addAttribute("anagrafica", anagrafica);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_GESTIONE_ANAGRAFICHE_AGGIORNA, "Errore nell'esecuzione del controller /gestione/anagrafiche/aggiorna", ex, null, null);
            erroriAction.saveError(errore);
            Map<Integer, String> listaDB = anagraficheService.getListaTipoIndirizzo();
            model.addAttribute("ListaTipoIndirizzo", lista);
            return MODIFICA_ANAGRAFICA;
        }
    }

    @RequestMapping("/gestione/anagrafiche/aggiornaVariante")
    public String aggiorna(Model model, @ModelAttribute("idAnagrafica") Integer idAnagrafica, @ModelAttribute("nuovaVariante") String nuovaVariante) {
        try {
            Anagrafica anagrafica = anagraficheService.findAnagraficaById(idAnagrafica);
            anagrafica.setVarianteAnagrafica(nuovaVariante);
            anagrafica.getCodiceFiscale();
            anagraficaAction.aggiornaAnagrafica(anagrafica);
            model.addAttribute("json", gson.toJson("L'aggiornamento è stato eseguito con successo"));
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di aggiornare la variante dell'anagrafica", ex);
            model.addAttribute("json", gson.toJson("Non è stato possibile aggiornare la variante dell'anagrafica"));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_GESTIONE_ANAGRAFICHE_AGGIORNAVARIANTE, "Errore nell'esecuzione del controller /gestione/anagrafiche/aggiornaVariante", ex, null, null);
            erroriAction.saveError(errore);
        }
        return AJAX;
    }

    public List<String> validaAnagrafica(BindingResult result, AnagraficaDTO anagrafica, String operazione) throws Exception {
        anagraficaUtils.ripulisci(anagrafica);
        validator.validate(anagrafica, result);
        List<String> errori = new ArrayList<String>();
        if (anagrafica.getRecapiti() != null) {
            if (operazione.equals("MODIFICA") && isAggiuntoPrimoRecapito(anagrafica)) {
                RecapitoDTO recapito = anagraficheService.getPrimoRecapito(anagrafica.getIdAnagrafica());
                anagrafica.getRecapiti().add(recapito);
            } else {
                for (RecapitoDTO recapito : anagrafica.getRecapiti()) {
                    BeanPropertyBindingResult br = new BeanPropertyBindingResult(recapito, "recapito");
                    validator.validate(recapito, br);
                    List<String> validatorErrors = valid.recapito(br, recapito);
                    if (validatorErrors != null) {
                        errori.addAll(validatorErrors);
                    }
                }
            }
        }
        errori.addAll(valid.Anagrafica(result, anagrafica));
        if (checkCF(anagrafica, operazione)) {
            errori.add(messageSource.getMessage("error.codiceFiscaleDuplicate", null, Locale.getDefault()));
        }
        if (checkPartitaIva(anagrafica, operazione)) {
            errori.add(messageSource.getMessage("error.partitaIvaDuplicate", null, Locale.getDefault()));
        }
        return errori;
    }

    @RequestMapping("/gestione/anagrafiche/personafisica")
    public String dettaglioPersonaFisica(Model model, HttpServletRequest request, HttpServletResponse response) {
        String codiceFiscale = request.getParameter("codiceFiscale");
        String idPratica = request.getParameter("idPratica");
        try {

            Pratica pratica = praticheService.getPratica(Integer.valueOf(idPratica));

            Enti ente = pratica.getIdProcEnte().getIdEnte();
            LkComuni comune = pratica.getIdComune();

            GestioneAnagrafePersonaFisica gestioneAnagrafica = pluginService.getGestioneAnagrafePersonaFisica(ente.getIdEnte(), comune.getIdComune());
            it.wego.cross.plugins.commons.beans.Anagrafica anagrafica = gestioneAnagrafica.search(codiceFiscale, ente, comune);
            AnagraficaDTO ana = AnagraficheSerializer.serializeAnagrafica(anagrafica);
            List<RecapitoDTO> recapiti = new ArrayList<RecapitoDTO>();
            RecapitoDTO r = RecapitiSerializer.serialize(anagrafica.getRecapito());
            recapiti.add(r);
            model.addAttribute("recapiti", recapiti);
            model.addAttribute("anagrafica", ana);
        } catch (Exception ex) {
            Log.WS.error("Errore contattanto il webservice di registro imprese", ex);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_GESTIONE_ANAGRAFICHE_PERSONAFISICA, "Errore nell'esecuzione del controller /gestione/anagrafiche/personafisica", ex, null, null);
            erroriAction.saveError(errore);
        }
        return DETTAGLIO_ANAGRAFICA;
    }

    @RequestMapping("/gestione/anagrafiche/personagiuridica")
    public String dettaglioPersonaGiuridica(Model model, HttpServletRequest request, HttpServletResponse response) {
        String codiceFiscale = request.getParameter("codiceFiscale");
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
        model.addAttribute("datiIdentificativi", datiIdentificativi);
        return DETTAGLIO_AZIENDA;
    }

    @RequestMapping("/pratica/comunicazione/dettaglio/anagrafica")
    public String dettaglioAnagrafica(Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            Log.APP.info("ID anagrafica ricevuto " + request.getParameter("id_anagrafica"));
            Integer idAnagrafica = Integer.valueOf(request.getParameter("id_anagrafica"));
            Anagrafica anagrafica = anagraficheService.findAnagraficaById(idAnagrafica);
            AnagraficaDTO ana = AnagraficheSerializer.serializeAnagrafica(anagrafica);
            List<RecapitoDTO> recapiti = ana.getRecapiti();
            model.addAttribute("recapiti", recapiti);
            model.addAttribute("anagrafica", ana);
        } catch (Exception e) {
            Log.WS.error("Errore nel ricercare l'anagrafica selezionata", e);
            ErroreDTO error = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONE_DETTAGLIO_ANAGRAFICA, "Errore nell'esecuzione del controller /pratica/comunicazione/dettaglio/anagrafica", e, null, null);
            erroriAction.saveError(error);
        }
        return DETTAGLIO_ANAGRAFICA;
    }

    @RequestMapping("/gestione/anagrafiche/controlla/ajax")
    public String controllaAnagrafica(Model model, @ModelAttribute("anagrafica") AnagraficaDTO anagrafica, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        GridAnagraficaBean json = new GridAnagraficaBean();
        try {

            Filter filter = new Filter();
            filter.setCodiceFiscale(anagrafica.getCodiceFiscale());
            filter.setOrderColumn("codiceFiscale");
            List<AnagraficaDTO> anagrfiche = anagraficheService.searchAnagrafiche(filter);
            List<String> errors = new ArrayList<String>();
            if (anagrfiche != null && anagrfiche.size() > 0) {
                Boolean fisica = Boolean.FALSE;
                Boolean giuridica = Boolean.FALSE;

                for (AnagraficaDTO anagraficaDB : anagrfiche) {
                    if (anagraficaDB.getTipoAnagrafica().equals(Constants.PERSONA_FISICA)) {
                        fisica = Boolean.TRUE;
                    } else if (anagraficaDB.getTipoAnagrafica().equals(Constants.PERSONA_GIURIDICA)) {
                        giuridica = Boolean.TRUE;
                    }
                }

                if (fisica && !giuridica) {
                    errors.add("Errore grave: " + anagrafica.getCodiceFiscale() + " presente piu volte.");
                    json.setRows(null);
                } else if (fisica && giuridica) {
                    errors.add("Errore molto grave: " + anagrafica.getCodiceFiscale() + " presente piu volte come Giruidica e Fisica.");
                    json.setRows(null);
                } else if (!fisica && giuridica) {
                    json.setRows(anagrfiche);
                }
            }
            if (errors.size() > 0) {
                json.setErrors(errors);
            }

        } catch (Exception e) {
            String error = "errore reperendo le anagrafiche";
            if (e instanceof CrossException) {
                error = e.getMessage();
            }
            Log.APP.error(error, e);
            json.setErrors(Arrays.asList(error));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_GESTIONE_ANAGRAFICHE_CONTROLLA_AJAX, "Errore nell'esecuzione del controller /gestione/anagrafiche/controlla/ajax", e, null, null);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX;

    }

    private boolean isAggiuntoPrimoRecapito(AnagraficaDTO anagrafica) {
        List<RecapitoDTO> recapiti = anagrafica.getRecapiti();
        if (recapiti != null && !recapiti.isEmpty()) {
            return recapiti.size() == 1 && recapiti.get(0).getIdRecapito() == null;
        } else {
            return false;
        }
    }

    private String messaggioOperazioneEffettuata(String input) {
        if (input != null && input.length() != 0) {
            List<String> inputlist = Arrays.asList(input.split("#"));
            Character tipoOperazione = inputlist.get(0).charAt(0);
            if ((inputlist.size() == 2) && (inputlist.get(0).length() == 1)) {
                String parametri = inputlist.get(1);
                switch (tipoOperazione) {
                    case 'M':
                        return "Salvataggio delle modifiche apportate all'anagrafica relativa alla " + parametri;
                    case 'A':
                        return "Salvataggio in database della nuova anagrafica relativa alla " + parametri;
                    default:
                        return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
    
    

}
