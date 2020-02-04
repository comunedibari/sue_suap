/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import it.wego.cross.actions.ComunicazioneAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.beans.grid.GridComunicazioniBean;
import it.wego.cross.beans.grid.GridPraticaNuovaBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.entity.Utente;
import it.wego.cross.utils.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author giuseppe
 */
@Controller
public class ComunicazioniController extends AbstractController {

    private static final String INDEX = "comunicazione_index";
    private static final String AJAX = "ajax";
    private static final String COMUNICAZIONE_SELEZIONATA = "id_comunicazione_selezionata";
    @Autowired
    private ComunicazioneAction comunicazioneAction;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ErroriAction erroriAction;

    @RequestMapping("/comunicazioni")
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        return INDEX;
    }

    @RequestMapping("/comunicazioni/ajax")
    public String indexAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        GridComunicazioniBean json = new GridComunicazioniBean();
        try {
            json = comunicazioneAction.getComunicazioni(request, paginator);
        } catch (Exception ex) {
            List<Message> errori = new ArrayList<Message>();
            Log.APP.error("Errore reperendo le comunicazioni", ex);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICAZIONI_AJAX, "Errore nell'esecuzione del controller /comunicazioni/ajax", ex, null, utente);
            erroriAction.saveError(err);
        }
        model.addAttribute("json", json.toString());
        return AJAX;
    }

    @RequestMapping("/comunicazioni/gestisci/ajax")
    public String gestisciAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        Utente utente = utentiService.getUtenteConnesso(request);
        GridPraticaNuovaBean json = new GridPraticaNuovaBean();
        try {
            Integer idComunicazione = (Integer) request.getSession().getAttribute(COMUNICAZIONE_SELEZIONATA);
            json = comunicazioneAction.getPraticheUtente(request, idComunicazione, paginator);
        } catch (Exception ex) {
            List<Message> errori = new ArrayList<Message>();
            Log.APP.error("Errore reperendo le comunicazioni", ex);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICAZIONI_GESTISCI_AJAX, "Errore nell'esecuzione del controller /comunicazioni/gestisci/ajax", ex, null, utente);
            erroriAction.saveError(err);
        }
        model.addAttribute("json", json.toString());
        return AJAX;
    }
}
