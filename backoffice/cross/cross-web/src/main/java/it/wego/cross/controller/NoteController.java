package it.wego.cross.controller;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.NotaDTO;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.NotaAction;
import it.wego.cross.beans.JsonResponse;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.NoteService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author CS
 */
@Controller
public class NoteController extends AbstractController {

    @Autowired
    protected Validator validator;
    @Autowired
    private Message messaggiJSP;
    @Autowired
    private NotaAction notaAction;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    ErroriAction erroriAction;
    @Autowired
    private NoteService noteService;
    //Pages
    private static final String AJAX_PAGE = "ajax";
    private static final String MODIFICA_NOTA = "nuova_nota";
    public static SimpleDateFormat sdfIt = new SimpleDateFormat("dd/MM/yyyy");
    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

    @RequestMapping("/pratica/note/aggiungi")
    public String aggiungi(Model model, @ModelAttribute NotaDTO nota, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            //????
        } catch (Exception ex) {
            String err = "Si e' verificato errore ricercando le note della pratica";
            Log.APP.error(err, ex);
            messaggiJSP.setMessages(Arrays.asList(err));
            ErroreDTO erro = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_NOTE_AGGIUNGI, "Errore nell'esecuzione del controller /pratica/note/aggiungi", ex, null, utente);
            erroriAction.saveError(erro);
        }
        model.addAttribute("message", messaggiJSP);
        return MODIFICA_NOTA;
    }

    @RequestMapping("/pratica/note/aggiungi/ajax")
    public String aggiungiAjax(Model model, @ModelAttribute NotaDTO nota, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        JsonResponse json = new JsonResponse();
        Pratica pratica = null;
        try {
            pratica = praticheService.getPratica(nota.getIdPratica());
            nota.setDataInserimento(new Date());
            nota.setIdUtente(utente.getIdUtente());
            NotaDTO notaSalvata = notaAction.aggiungiTransactional(nota);
            json.addAttribute("dataInserimento", Utils.dateItalianFormat(notaSalvata.getDataInserimento()));
            json.addAttribute("desUtente", notaSalvata.getDesUtente());
            json.addAttribute("testo", notaSalvata.getTestoBreve());
            json.addAttribute("idNota", String.valueOf(notaSalvata.getIdNota()));
            json.setSuccess(Boolean.TRUE);
        } catch (Exception ex) {
            String err = "Si e' verificato errore ricercando le note della pratica";
            Log.APP.error(err, ex);
            json.setMessage(err);
            json.setSuccess(Boolean.FALSE);
            ErroreDTO erro = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_NOTE_AGGIUNGI_AJAX, "Errore nell'esecuzione del controller /pratica/note/aggiungi/ajax", ex, pratica, utente);
            erroriAction.saveError(erro);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }
    
    @RequestMapping("/pratica/note/get/ajax")
    public String dettaglio(Model model, Integer idNota){
        NotaDTO dettaglioNota = noteService.findNotaById(idNota);
        model.addAttribute("json", gson.toJson(dettaglioNota));
        return AJAX_PAGE;
    } 
}
