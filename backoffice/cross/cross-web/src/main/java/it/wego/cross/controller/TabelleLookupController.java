/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import it.wego.cross.actions.ErroriAction;
import it.wego.cross.beans.grid.GridDatiLkTipoParticellaBean;
import it.wego.cross.beans.grid.GridDatiLkTipoUnitaBean;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.LookupService;
import it.wego.cross.utils.Log;
import java.util.Arrays;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author piergiorgio
 */
@Controller
public class TabelleLookupController extends AbstractController {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private LookupService lookupService;
    //Pages
    private static final String AJAX_PAGE = "ajax";

    @RequestMapping("/tabelle/lookup/tipounitaAjax")
    public String tipoUnitaAjax(Model model, HttpServletRequest request, HttpServletResponse response) {
        GridDatiLkTipoUnitaBean json = new GridDatiLkTipoUnitaBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json.setRows(lookupService.findAllTipoUnita());
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", error);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_AJAX, "Errore nell'esecuzione del controller /tabelle/lookup/tipounitaAjax", e, null, utente);
            erroriAction.saveError(errore);
        }

        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

    @RequestMapping("/tabelle/lookup/tipoparticellaAjax")
    public String tipoParicellaAjax(Model model, HttpServletRequest request, HttpServletResponse response) {
        GridDatiLkTipoParticellaBean json = new GridDatiLkTipoParticellaBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json.setRows(lookupService.findAllTipoParticella());
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", error);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_NUOVE_AJAX, "Errore nell'esecuzione del controller tabelle/lookup/tipoparticellaAjax", e, null, utente);
            erroriAction.saveError(errore);
        }

        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }
}
