/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import it.wego.cross.actions.ErroriAction;
import it.wego.cross.beans.grid.GridScadenzeBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.ScadenzaDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkStatiScadenze;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.FilterSerializer;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
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
public class ScadenzarioController extends AbstractController {

    @Autowired
    private PraticheService praticheService;
    @Autowired
    private FilterSerializer filterSerializer;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private EntiService entiService;
    @Autowired
    private LookupService lookupService;
    private static final String HOME_COMUNICAZIONE = "scadenzario_index";
    private List<Message> errori = new ArrayList<Message>();
    private static final String AJAX_PAGE = "ajax";

    @RequestMapping("/pratiche/scadenzario")
    public String gestione(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<LkStatiScadenze> lookup = praticheService.findAllLookupScadenze();
        model.addAttribute("tipoScadenzeSearch", lookup);
        Filter filter = (Filter) request.getSession().getAttribute("praticheScadenzarioLista");
        if (filter == null) {
            filter = new Filter();
        }
        UtenteDTO utente = utentiService.getUtenteConnessoDTO(request);
        List<Enti> enti = entiService.getListaEntiPerRicerca(utente);
        List<String> anniRiferimento = praticheService.popolaListaAnni();
        List<LkStatoPratica> lookupStato = praticheService.findAllLookupStatoPratica();
        model.addAttribute("statoPraticaRicerca", lookupStato);
        model.addAttribute("anniRiferimento", anniRiferimento);
        model.addAttribute("entiRicerca", enti);
        model.addAttribute("filtroRicerca", filter);
        return HOME_COMUNICAZIONE;
    }

    @RequestMapping("/pratiche/scadenzario/ajax")
    public String gestioneAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridScadenzeBean json = new GridScadenzeBean();
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            json = getElencoScadenze(request, paginator);
        } catch (Exception e) {
            Log.APP.error("Errore reperendo le pratiche", e);
            String msgError = messageSource.getMessage("error.search.failure", null, Locale.getDefault());
            Message error = new Message();
            error.setMessages(Arrays.asList(msgError));
            request.setAttribute("message", errori);
            ErroreDTO errore = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICHE_SCADENZARIO_AJAX, "Errore nell'esecuzione del controller /pratiche/scadenzario/ajax", e, null, utente);
            erroriAction.saveError(errore);
        }
        model.addAttribute("json", json.toString());
        return AJAX_PAGE;
    }

//    @-Transactional
    private GridScadenzeBean getElencoScadenze(HttpServletRequest request, JqgridPaginator paginator) throws Exception {
        GridScadenzeBean json = new GridScadenzeBean();
        Integer maxResult = Integer.parseInt(paginator.getRows());
        Integer page = Integer.parseInt(paginator.getPage());
        String column = paginator.getSidx();
        String order = paginator.getSord();
        Integer firstRecord = (page * maxResult) - maxResult;
        Filter filter = (Filter) request.getSession().getAttribute("praticheScadenzarioLista");
        if (filter == null) {
            filter = filterSerializer.getSearchFilter(request);
        }
        filter.setPage(page);
        filter.setLimit(maxResult);
        filter.setOffset(firstRecord);
        filter.setOrderColumn(column);
        filter.setOrderDirection(order);

        String tipoFiltro = (String) request.getParameter("tipoFiltro");
        if ((!Utils.e(tipoFiltro) && tipoFiltro.toUpperCase().equals("UTENTE"))) {
            Filter filtertmp = filterSerializer.getSearchFilter(request);
            filtertmp.setPage(page);
            filtertmp.setLimit(maxResult);
            filtertmp.setOffset(firstRecord);
            filtertmp.setOrderColumn(column);
            filtertmp.setOrderDirection(order);
            filter = filtertmp;
        }
        if ((filter.getDesStatoPratica() == null && filter.getIdStatoPratica() == null) || (filter.getDesStatoPratica() != null && filter.getDesStatoPratica().equals("ATTIVE"))) {
            //Escludo le pratiche ricevute e quelle chiuse
            List<LkStatoPratica> daEscludere = new ArrayList<LkStatoPratica>();
            LkStatoPratica ricevuta = lookupService.findStatoPraticaByCodice("R");
            daEscludere.add(ricevuta);
            List<LkStatoPratica> chiuse = lookupService.findStatiPraticaByGruppo("C");
            daEscludere.addAll(chiuse);
            filter.setPraticheDaEscludere(daEscludere);
        } else if (filter.getDesStatoPratica() != null && filter.getDesStatoPratica().equals("CHIUSE")) {
            //Escludo tutte le pratiche diverse da chiusa
            List<LkStatoPratica> daEscludere = new ArrayList<LkStatoPratica>();
            List<LkStatoPratica> toExclude = lookupService.findStatoPraticaByGruppoDiversoDa("C");
            daEscludere.addAll(toExclude);
            filter.setPraticheDaEscludere(daEscludere);
        } else if (filter.getDesStatoPratica() != null && filter.getDesStatoPratica().equals("ALL")) {
            //Escludo sempre le pratiche ricevute per 2 motivi:
            //1. performance: devono andarsi a leggere l'xml pratica cross per trovare il richiedente.
            //2. c'è la sezione dedicata.
            List<LkStatoPratica> daEscludere = new ArrayList<LkStatoPratica>();
            LkStatoPratica ricevuta = lookupService.findStatoPraticaByCodice("R");
            daEscludere.add(ricevuta);
            filter.setPraticheDaEscludere(daEscludere);
        }
        request.getSession().setAttribute("praticheScadenzarioLista", filter);
        Long countRighe = praticheService.countScadenze(filter);
        List<ScadenzaDTO> pratiche = praticheService.findScadenze(filter);
        Integer totalRecords = countRighe.intValue();
        totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
        json.setPage(page);
        json.setRecords(countRighe.intValue());
        json.setTotal(totalRecords);
        json.setRows(pratiche);
        return json;
    }

}
