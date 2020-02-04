
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import groovy.json.JsonOutput;
import it.wego.cross.actions.DatiEstesiAction;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.beans.grid.GridDefDatiEstesiBean;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.dao.AuthorizationDao;
import it.wego.cross.dao.DefDatiEstesiDao;
import it.wego.cross.dao.LookupDao;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.dozer.DefDatiEstesiDTO;
import it.wego.cross.dto.dozer.LkTipoOggettoDTO;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.dto.filters.GestioneDatiEstesiFilter;
import it.wego.cross.entity.DefDatiEstesi;
import it.wego.cross.entity.LkTipoOggetto;
import it.wego.cross.service.DefDatiEstesiService;
import it.wego.cross.utils.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author giuseppe
 */
@Controller
public class DatiEstesiController extends AbstractController {

    //variabili relative alle comunicazione utente sulle operazioni effettuate
    private final Character OPERAZIONE_MODIFICA = 'M';
    private final Character OPERAZIONE_AGGIUNTA = 'A';
    private final String OPERAZIONE_EFFETTUATA = "operazioneeffettuata";
    //
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private DatiEstesiAction datiEstesiAction;
    @Autowired
    private DefDatiEstesiDao defDatiEstesiDao;
    @Autowired
    private Mapper mapper;
    @Autowired
    private LookupDao lookupDao;
    @Autowired
    private DefDatiEstesiService defDatiEstesiService;

    private static final String AJAX = "ajax";
    private static final String RICERCA_DATIESTESI = "datiestesi_ricerca";

    @RequestMapping("/datiestesi/lista")
    public String cercaDatiEstesi(Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(OPERAZIONE_EFFETTUATA) String operazioneEffettuataatt) {
        GestioneDatiEstesiFilter filter = (GestioneDatiEstesiFilter) request.getSession().getAttribute("gestioneDefDatiEstesiList");
        if (filter == null) {
            filter = new GestioneDatiEstesiFilter();
        }
        List<LkTipoOggettoDTO> lista = new ArrayList<LkTipoOggettoDTO>();
        for (LkTipoOggetto l : lookupDao.findAllTipoOggetto()) {
            LkTipoOggettoDTO dto = mapper.map(l, LkTipoOggettoDTO.class);
            lista.add(dto);
        }
        model.addAttribute("filtroRicerca", filter);
        model.addAttribute("tipiOggetto", lista);
        return RICERCA_DATIESTESI;
    }

    @RequestMapping("/datiestesi/lista/ajax")
    public String cercaDatiEstesiAjax(Model model, HttpServletRequest request, HttpServletResponse response, @ModelAttribute("paginator") JqgridPaginator paginator) {
        GridDefDatiEstesiBean json;
        try {
            json = defDatiEstesiService.findDefDatiEstesi(request, paginator, "gestioneDefDatiEstesiList");
            model.addAttribute("json", json.toString());
            return AJAX;
        } catch (Exception e) {
            Log.APP.error("Errore reperendo i dati estesi", e);
            HashMap<String, String> result = new HashMap<String, String>();
            result.put("success", "true");
            result.put("msg", messageSource.getMessage("datiestesi.error.find", null, Locale.getDefault()));
            model.addAttribute("json", gson.toJson(result));
        }
        return AJAX;
    }

    @RequestMapping("/datiestesi/azioni")
    public String dettaglioDatoEsteso(Model model,
            @ModelAttribute DefDatiEstesiDTO defDatiEstesiDTO,
            @RequestParam(value = "action", required = true) String action) {
        String msg = messageSource.getMessage("datiestesi.error.find", null, Locale.getDefault());
        try {
            if (action.equalsIgnoreCase("cerca")) {
                DefDatiEstesiDTO dto = defDatiEstesiService.getDefDatiEstesi(defDatiEstesiDTO.getIdDatiEstesi());
                model.addAttribute("json", gson.toJson(dto));
            }
            if (action.equalsIgnoreCase("modifica")) {
                msg = messageSource.getMessage("datiestesi.error.update", null, Locale.getDefault());
                DefDatiEstesiDTO dto = defDatiEstesiService.getDefDatiEstesi(defDatiEstesiDTO.getIdDatiEstesi());
                dto.setValue(defDatiEstesiDTO.getValue());
                datiEstesiAction.aggiornaDatoEsteso(dto);
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("success", "true");
                result.put("msg", messageSource.getMessage("datiestesi.success.update", null, Locale.getDefault()));
                model.addAttribute("json", gson.toJson(result));
            }
            if (action.equalsIgnoreCase("cancella")) {
                msg = messageSource.getMessage("datiestesi.error.delete", null, Locale.getDefault());
                datiEstesiAction.cancellaDatoEsteso(defDatiEstesiDTO);
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("success", "true");
                result.put("msg", messageSource.getMessage("datiestesi.success.delete", null, Locale.getDefault()));
                model.addAttribute("json", gson.toJson(result));
            }
            if (action.equalsIgnoreCase("inserisci")) {
                msg = messageSource.getMessage("datiestesi.error.istanza.null", null, Locale.getDefault());
                Preconditions.checkArgument(!Strings.isNullOrEmpty(defDatiEstesiDTO.getIdIstanza()), msg);
                msg = messageSource.getMessage("datiestesi.error.tipooggetto.null", null, Locale.getDefault());
                Preconditions.checkArgument(!Strings.isNullOrEmpty(defDatiEstesiDTO.getCodTipoOggetto()), msg);
                msg = messageSource.getMessage("datiestesi.error.codvalore.null", null, Locale.getDefault());
                Preconditions.checkArgument(!Strings.isNullOrEmpty(defDatiEstesiDTO.getCodValue()), msg);
                DefDatiEstesiDTO dto = defDatiEstesiService.getDefDatiEstesiUnique(defDatiEstesiDTO);
                msg = messageSource.getMessage("datiestesi.error.duplicato", null, Locale.getDefault());
                Preconditions.checkArgument(dto == null, msg);
                msg = messageSource.getMessage("datiestesi.error.insert", null, Locale.getDefault());
                datiEstesiAction.salvaDatoEsteso(defDatiEstesiDTO);
                HashMap<String, String> result = new HashMap<String, String>();
                result.put("success", "true");
                result.put("msg", messageSource.getMessage("datiestesi.success.insert", null, Locale.getDefault()));
                model.addAttribute("json", gson.toJson(result));
            }
        } catch (Exception e) {
            Map<String, String> result = ImmutableMap.of("success", "false", "msg", msg);
            Log.APP.error(msg, e);
            model.addAttribute("json", gson.toJson(result));
        }
        return AJAX;
    }
}
