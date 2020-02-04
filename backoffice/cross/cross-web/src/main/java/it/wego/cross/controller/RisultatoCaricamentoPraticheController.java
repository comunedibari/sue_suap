package it.wego.cross.controller;

import java.util.Arrays;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.RisultatoCaricamentoPraticheAction;
import it.wego.cross.beans.grid.GridRisultatoCaricamentoPraticaBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.utils.Log;

@Controller
@RequestMapping("/caricamentopratiche")
public class RisultatoCaricamentoPraticheController extends AbstractController  {
	
	private static final String INDEX = "risultato_caricamento_pratiche";
	private static final String AJAX_PAGE = "ajax";
	@Autowired
    private ErroriAction erroriAction;
	@Autowired
    private RisultatoCaricamentoPraticheAction risultatoCaricamentoPraticheAction;
	@Autowired
    private MessageSource messageSource;
	@RequestMapping("/index")
    public String index(ModelMap model, HttpServletRequest request) throws Exception {
        Utente utenteConnesso = utentiService.getUtenteConnesso(request);
        Filter filter = new Filter();
        filter.setConnectedUser(utenteConnesso);
        boolean isSuperuser = String.valueOf(utenteConnesso.getSuperuser()).equalsIgnoreCase("S");
        String dbColumnModel = IOUtils.toString(ConfigurationService.class.getResourceAsStream("gestione_caricamento_pratiche.js"));
        model.addAttribute("gestioneCaricamentoPraticheColumnModel", dbColumnModel);
        return INDEX;
    }
	
	 @RequestMapping("/index/ajax")
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
	   


}
