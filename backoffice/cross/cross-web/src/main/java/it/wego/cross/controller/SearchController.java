/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import com.google.gson.Gson;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.beans.grid.GridEntiBean;
import it.wego.cross.beans.grid.GridEventoTemplateBean;
import it.wego.cross.beans.lookup.LookupCittadinanza;
import it.wego.cross.beans.lookup.LookupComuni;
import it.wego.cross.beans.lookup.LookupFormeGiuridiche;
import it.wego.cross.beans.lookup.LookupNazionalita;
import it.wego.cross.beans.lookup.LookupProvince;
import it.wego.cross.beans.lookup.LookupTipoCollegio;
import it.wego.cross.beans.search.SearchProcedimenti;
import it.wego.cross.beans.search.SearchProcedimentiComuni;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dto.CittadinanzaDTO;
import it.wego.cross.dto.ComuneDTO;
import it.wego.cross.dto.EnteDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.dto.NazionalitaDTO;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.ProvinciaDTO;
import it.wego.cross.dto.UtenteMinifiedDTO;
import it.wego.cross.dto.search.DestinatariDTO;
import it.wego.cross.dto.search.FormaGiuridicaDTO;
import it.wego.cross.dto.search.TipoCollegioDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.ComuniService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.SearchService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author giuseppe TODO: DA RISCRIVERE!!!! <<-- scritto da CS
 */
@Controller
public class SearchController extends AbstractController {

    @Autowired
    private SearchService searchService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private EntiService entiService;
    @Autowired
    PraticheService praticheService;
    @Autowired
    private MessageSource messageSource;
    private static final String AJAX = "ajax";
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private AnagraficheService anagraficheService;

    @RequestMapping(value = "/search/destinatari")
    public String searchDestinatari(Model model, HttpServletRequest request, @RequestParam(value = "id_pratica", required = false) Integer idPratica, @RequestParam(value = "id_evento", required = false) Integer idEvento, @RequestParam("query") String query) throws Exception {
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            if (idPratica == null) {
                idPratica = (Integer) request.getSession().getAttribute(SessionConstants.ID_PRATICA_SELEZIONATA);
            }

            if (idEvento == null) {
                idEvento = (Integer) request.getSession().getAttribute(SessionConstants.EVENTO_SELEZIONATO);
            }
            EventoDTO evento = praticheService.getDettaglioProcessoEvento(idEvento);
            pratica = praticheService.getPratica(idPratica);
            List<DestinatariDTO> destinatari = searchService.getDestinatari(pratica, query, evento);
            //Individuo destinatari privi di email gestione posta ordinaria
            for (DestinatariDTO destinatario : destinatari) {
                if (Utils.e(destinatario.getEmail())) {
                    String msg = messageSource.getMessage("evento.posta.ordinaria", null, Locale.getDefault());
                    destinatario.setEmail(msg);
                }
            }
            Gson gson = new Gson();
            model.addAttribute("json", gson.toJson(destinatari));
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca dei destinatari.";
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_DESTINATARI, "Errore nell'esecuzione del controller /search/destinatari", e, pratica, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX;
    }

    @RequestMapping(value = "/search/utenti")
    public String searchUtenti(Model model, HttpServletRequest request, @RequestParam("query") String query) throws Exception {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            List<UtenteMinifiedDTO> utenti = searchService.findUtenti(query);
            Gson gson = new Gson();
            model.addAttribute("json", gson.toJson(utenti));
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca degli utenti.";
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_UTENTI, "Errore nell'esecuzione del controller /search/utenti", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX;
    }

    @RequestMapping(value = "/search/province")
    public String searchProvince(Model model,
            HttpServletRequest request,
            @RequestParam("description") String description,
            @RequestParam("flgInfocamere") String flgInfocamere) throws ParseException {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Boolean displayOnlyProvinceInfocamere = flgInfocamere != null && flgInfocamere.equalsIgnoreCase("S");
            List<ProvinciaDTO> provincie = searchService.findProvincie(description, displayOnlyProvinceInfocamere);
            LookupProvince json = new LookupProvince();
            json.setRows(provincie);
            Gson gson = new Gson();
            model.addAttribute("json", gson.toJson(provincie));
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca delle province.";
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_PROVINCE, "Errore nell'esecuzione del controller /search/province", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX;
    }

    @RequestMapping("/search/formagiuridica")
    public String searchFormaGiuridica(Model model, HttpServletRequest request, @RequestParam("description") String description) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            LookupFormeGiuridiche json = new LookupFormeGiuridiche();
            List<FormaGiuridicaDTO> formeGiuridiche = searchService.findFormeGiuridiche(description);
            json.setRows(formeGiuridiche);
            Gson gson = new Gson();
            model.addAttribute("json", gson.toJson(formeGiuridiche));
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca della formagiuridica.";
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_FORMAGIURIDICA, "Errore nell'esecuzione del controller /search/formagiuridica", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }

        return AJAX;
    }

    @RequestMapping(value = "/search/cittadinanza")
    public String searchCittadinanza(Model model,
            HttpServletRequest request,
            @RequestParam("description") String description) throws ParseException {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            LookupCittadinanza json = new LookupCittadinanza();
            List<CittadinanzaDTO> cittadinanze = searchService.findCittadinanze(description);
            json.setRows(cittadinanze);
            Gson gson = new Gson();
            model.addAttribute("json", gson.toJson(cittadinanze));
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca della cittadinanza.";
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_CITTADINANZA, "Errore nell'esecuzione del controller /search/cittadinanza", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX;
    }

    @RequestMapping(value = "/search/nazionalita")
    public String searchNazionalita(Model model,
            HttpServletRequest request,
            @RequestParam("description") String description) throws ParseException {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            LookupNazionalita json = new LookupNazionalita();
            List<NazionalitaDTO> nazionalita = anagraficheService.findNazionalita(description);
            json.setRows(nazionalita);
            model.addAttribute("json", new Gson().toJson(nazionalita));
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca della cittadinanza.";
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_CITTADINANZA, "Errore nell'esecuzione del controller /search/cittadinanza", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX;
    }

    @RequestMapping(value = "/search/tipocollegio")
    public String searchTipoCollegio(Model model,
            HttpServletRequest request,
            @RequestParam("description") String description) throws ParseException {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            LookupTipoCollegio json = new LookupTipoCollegio();
            List<TipoCollegioDTO> collegi = searchService.findTipoCollegio(description);
            json.setRows(collegi);
            Gson gson = new Gson();
            model.addAttribute("json", gson.toJson(collegi));
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca del tipocollegio.";
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_TIPOCOLLEGIO, "Errore nell'esecuzione del controller /search/tipocollegio", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX;
    }

    @RequestMapping(value = "/search/comune")
    public String searchComune(Model model,
            HttpServletRequest request,
            @RequestParam("description") String description,
            @RequestParam("dataValidita") String dataValidita) throws ParseException {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            LookupComuni json = new LookupComuni();
            Date data;
            if (dataValidita != null && !"".equals(dataValidita)) {
                data = Utils.italianFormatToDate(dataValidita);
            } else {
                data = new Date();
            }
            List<ComuneDTO> comuni = new ArrayList();
            if ((!("".equals(description))) && (description != null)) {
                comuni = searchService.findComuni(description, data);
            }
            json.setRows(comuni);
            Gson gson = new Gson();
            model.addAttribute("json", gson.toJson(comuni));
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca del comune.";
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_COMUNE, "Errore nell'esecuzione del controller /search/comune", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX;
    }

    @RequestMapping(value = "/search/procedimentiComuni")
    public String searchProcedimentiComuni(Model model,
            HttpServletRequest request,
            @RequestParam("idEnte") String idEnte) throws ParseException {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            SearchProcedimentiComuni json = new SearchProcedimentiComuni();
            Integer id = Integer.valueOf(idEnte);
            String comune = request.getParameter("descrizione");
            List<ComuneDTO> comuni = comuniService.findComuniByEnte(id, comune);
            json.setComuni(comuni);
            Gson gson = new Gson();
            model.addAttribute("json", gson.toJson(comuni));
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca dei procedimentiComuni.";
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_PROCEDIMENTICOMUNI, "Errore nell'esecuzione del controller /search/procedimentiComuni", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX;
    }

    @RequestMapping(value = "/search/procedimentiCollegati", method = RequestMethod.GET, headers = "Accept=*/*")
    public String searchProcedimenti(Model model,
            HttpServletRequest request,
            @RequestParam("idEnte") Integer idEnte,
            @RequestParam("description") String description) throws ParseException {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            SearchProcedimenti json = new SearchProcedimenti();
            List<ProcedimentoDTO> procedimenti = procedimentiService.findProcedimenti(idEnte, description);
            json.setRows(procedimenti);
            Gson gson = new Gson();
            model.addAttribute("json", gson.toJson(procedimenti));
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca dei procedimentiCollegati.";
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_PROCEDIMENTICOLLEGATI, "Errore nell'esecuzione del controller /search/procedimentiCollegati", e, null, utente);
            erroriAction.saveError(errore);
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, e);
        }
        return AJAX;
    }

    @RequestMapping(value = "/search/ente", method = RequestMethod.GET, headers = "Accept=*/*")
    public ResponseEntity<String> searchEnti(HttpServletRequest request,
            @ModelAttribute("comune") ComuneDTO comune,
            BindingResult result) throws ParseException {
        GridEntiBean json = new GridEntiBean();;
        Gson gson = new Gson();
        HttpHeaders responseHeaders = new HttpHeaders();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            EnteDTO ente = entiService.findEnte(comune);
            json.setRows(Arrays.asList(ente));
            responseHeaders.add("Content-Type", "text/html; charset=utf-8");
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca dei procedimentiCollegati.";
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_ENTE, "Errore nell'esecuzione del controller /search/ente", e, null, utente);
            erroriAction.saveError(errore);
            Log.APP.error(msg, e);
        }
        return new ResponseEntity<String>(gson.toJson(json), responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/search/comuniEnti", method = RequestMethod.GET, headers = "Accept=*/*")
    public ResponseEntity<String> searchComuniEnti(HttpServletRequest request,
            @ModelAttribute("descrizione") String descrizione,
            BindingResult result) throws ParseException {
        Utente utente = utentiService.getUtenteConnesso(request);
        LookupComuni json = new LookupComuni();
        try {
            if (!result.hasErrors()) {
                List<ComuneDTO> comuni = comuniService.findComuniEnti(descrizione);
                json.setRows(comuni);
            } else {
                json.setErrors(Arrays.asList("Inserire correttamente il nome del comune"));
            }

        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca dei comuniEnti.";
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_COMUNIENTI, "Errore nell'esecuzione del controller /search/comuniEnti", e, null, utente);
            erroriAction.saveError(errore);
            Log.APP.error(msg, e);
        }
        Gson gson = new Gson();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/html; charset=utf-8");
        return new ResponseEntity<String>(gson.toJson(json), responseHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/search/entiProcedimenti", method = RequestMethod.GET, headers = "Accept=*/*")
    public ResponseEntity<String> searchEntiProcedimenti(HttpServletRequest request,
            @ModelAttribute("comune") ProcedimentoDTO proc,
            BindingResult result) throws ParseException {
        SearchProcedimenti json = new SearchProcedimenti();
        String idente = request.getParameter("idente");
        String issuap = request.getParameter("issuap");
        Utente utente = utentiService.getUtenteConnesso(request);
        //Di default si pone l'id ente suap affinché funzioni il codice scritto prima
        if (issuap == null) {
            issuap = "suap";
        }
        try {
            if (!result.hasErrors()) {
                List<ProcedimentoDTO> procedimento = procedimentiService.findEntiProcedimenti(proc);
                List<ProcedimentoDTO> procedimentiDaVisualizzare = new ArrayList<ProcedimentoDTO>();
                if (!Utils.e(issuap) && issuap.equals("notsuap")) {
                    for (ProcedimentoDTO proce : procedimento) {
                        if (proce.getIdEnteDestinatario().equals(Integer.parseInt(idente))) {
                            procedimentiDaVisualizzare.add(proce);
                        }
                    }
                }
                if (!Utils.e(issuap) && issuap.equals("suap")) {
                    //TO DO: qui va la logica che fa visualizzare solo i procedimenti associati all'ente suap selezionato
                    procedimentiDaVisualizzare = procedimento;
                }
                if (Utils.e(idente)) {
                    //nel caso di ente nullo vengono  di default visualizzati tutti i procedimenti la situazione in cui l'ente  è nullo va gestita di volta in volta in base all'utilizzo
                    procedimentiDaVisualizzare = procedimento;
                }
                json.setRows(procedimentiDaVisualizzare);
            } else {
                json.setErrors(Arrays.asList("Inserire correttamente il nome dell'ente"));
            }
        } catch (Exception e) {
            String msg = "Impossibile effettuare la ricerca dei procedimenti relativi agli enti.";
            json.setErrors(Arrays.asList(msg));
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_SEARCH_ENTIPROCEDIMENTI, "Errore nell'esecuzione del controller /search/centiProcedimenti", e, null, utente);
            erroriAction.saveError(errore);
            Log.APP.error(msg, e);
        }
        Gson gson = new Gson();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/html; charset=utf-8");
        return new ResponseEntity<String>(gson.toJson(json), responseHeaders, HttpStatus.CREATED);
    }
}
