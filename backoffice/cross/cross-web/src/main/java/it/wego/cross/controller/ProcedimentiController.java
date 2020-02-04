/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.controller;

/**
 *
 * @author CS
 */
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.TipoProcedimentoDTO;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.actions.ErroriAction;
import it.wego.cross.actions.ProcedimentoAction;
import it.wego.cross.dto.filters.Filter;
import it.wego.cross.beans.grid.*;
import it.wego.cross.beans.layout.JqgridPaginator;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dto.filters.ComunicazioneFilter;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Utente;
import it.wego.cross.serializer.FilterSerializer;
import it.wego.cross.service.ClearService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.EntiService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import it.wego.cross.validator.IsValid;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProcedimentiController extends AbstractController {

    //variabili relative alle comunicazione utente sulle operazioni effettuate
    private final Character OPERAZIONE_MODIFICA = 'M';
    private final Character OPERAZIONE_AGGIUNTA = 'A';
    private final Character OPERAZIONE_ELIMINA = 'E';
    private final String OPERAZIONE_EFFETTUATA = "messaggiooperazioneeffettuata";
    //
    @Autowired
    protected Validator validator;
    @Autowired
    private ProcedimentiService procedimentiService;
    @Autowired
    private ProcedimentoAction procedimentoAction;
    @Autowired
    private IsValid isValid;
    @Autowired
    private FilterSerializer filterSerializer;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    ClearService clearService;
    @Autowired
    private ProcedimentiDao procedimentiDao;
    @Autowired
    EntiService entiService;
    @Autowired
    private ConfigurationService configurationService;

    private static final String AJAX_PAGE = "ajax";
    public static SimpleDateFormat sdfIt = new SimpleDateFormat("dd/MM/yyyy");

    @RequestMapping("/gestione/procedimenti/lista")
    public String lista(Model model,
            @ModelAttribute ProcedimentoDTO procedimento,
            BindingResult result, HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(OPERAZIONE_EFFETTUATA) String operazioneEffettuataatt,
            @ModelAttribute("errori") String errori) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            String messaggiooperazioneeffettuata = messaggioOperazioneEffettuata(operazioneEffettuataatt);
            model.addAttribute("messaggiooperazioneeffettuata", messaggiooperazioneeffettuata);
            List<TipoProcedimentoDTO> tipologiaProcedimento = procedimentiService.findDistinctTipoProcedimento();
            model.addAttribute("tipoProcedimento", tipologiaProcedimento);
            if (!errori.isEmpty()) {
                Message msg = new Message();
                msg.setError(true);
                msg.setMessages(Arrays.asList(errori.split("#")));
                model.addAttribute("message", msg);
            }
        } catch (Exception ex) {
            Message msg = new Message();
            msg.setMessages(Arrays.asList("Impossibile effettuare la ricerca."));
            Log.APP.error(msg.toString(), ex);
            response.setHeader("HTTP/1.1", "500 Internal Server Error");
        }
        return "procedimenti_lista_index";
    }

    @RequestMapping("/gestione/procedimenti/lista/ajax")
    public String listaAjax(Model model, @ModelAttribute ProcedimentoDTO procedimento, BindingResult result, JqgridPaginator paginator, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            Integer maxResult = Integer.parseInt(paginator.getRows());
            Integer page = Integer.parseInt(paginator.getPage());
            String column = paginator.getSidx();
            String order = paginator.getSord();
            Integer firstRecord = (page * maxResult) - maxResult;
            Filter filter = filterSerializer.getSearchFilter(request);
            filter.setPage(page);
            filter.setLimit(maxResult);
            filter.setOffset(firstRecord);
            filter.setOrderColumn(column);
            filter.setOrderDirection(order);
            Long count = procedimentiService.countProcedimenti(filter, "it");
            List<ProcedimentoDTO> lista = procedimentiService.getProcedimenti(filter);
            Integer totalRecords = count.intValue();
            totalRecords = (totalRecords / maxResult == 0) ? 1 : ((totalRecords % maxResult == 0) ? (totalRecords / maxResult) : (totalRecords / maxResult + 1));
            GridProcedimenti json = new GridProcedimenti();
            json.setPage(page);
            json.setRows(lista);
            json.setTotal(totalRecords);
            json.setRecords(count.intValue());
            model.addAttribute("json", json.toString());
        } catch (Exception ex) {
            String msg = "Impossibile effettuare la ricerca.";
            GridEventoTemplateBean json = new GridEventoTemplateBean();
            json.setErrors(Arrays.asList(msg));
            model.addAttribute("json", json.toString());
            Log.APP.error(msg, ex);
        }
        return AJAX_PAGE;
    }

    @RequestMapping("/gestione/procedimenti/elimina")
    public String elimina(Model model,
            @ModelAttribute("idProcedimento") Integer idProcedimento,
            HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            List<String> elementiCollegati = procedimentoAction.elimina(idProcedimento);
            if (elementiCollegati.isEmpty()) {
                redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_ELIMINA + "#" + " selezionato");
            } else {
                String elementiCollegatiString = "";
                for (String e : elementiCollegati) {
                    elementiCollegatiString += e + "#";
                }
                redirectAttributes.addFlashAttribute("errori", elementiCollegatiString);
            }
        } catch (Exception ex) {
            String error = "gestione/procedimenti/elimina";
            Log.APP.error(error, ex);
        }
        return "redirect:/gestione/procedimenti/lista.htm";
    }

    @RequestMapping("/gestione/procedimenti/modifica")
    public String modifica(Model model, @ModelAttribute ProcedimentoDTO procedimento, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            model.addAttribute("procedimento", procedimentiService.getByID(procedimento));
            model.addAttribute("clearActive", Utils.True(configurationService.getCachedConfiguration(SessionConstants.CLEAR_CLIENT_ENABLED, null, null)));
        } catch (Exception ex) {
            String error = "gestione/procedimenti/modifica";
            Log.APP.error(error, ex);
        }
        return "procedimenti_lista_modifica";
    }

    @RequestMapping("/gestione/procedimenti/salva")
    public String salva(Model model,
            @ModelAttribute ProcedimentoDTO procedimento,
            BindingResult result, HttpServletRequest request,
            HttpServletResponse response,
            final RedirectAttributes redirectAttributes) {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            if (request.getParameter("allineaClear") != null) {
                List<Enti> entiList = entiService.findAll(new ComunicazioneFilter());
                for (Enti ente : entiList) {
                    ProcedimentiEnti procedimentoEnte = procedimentiDao.findProcedimentiEntiByProcedimentoEnte(ente.getIdEnte(), procedimento.getIdProcedimento());
                    if (procedimentoEnte != null) {
                        it.wego.cross.webservices.client.clear.stubs.Log creaProcedimentoResponse = clearService.creaProcedimento(procedimentoEnte);
                    }
                }
            } else {
                validator.validate(procedimento, result);
                List<String> errors = isValid.Procedimento(result, procedimento);
                if (errors != null && errors.size() > 0) {
                    Message msg = new Message();
                    msg.setMessages(errors);
                    model.addAttribute("message", msg);
                    model.addAttribute("procedimento", procedimento);
                    return "procedimenti_lista_modifica";
                }
                if (procedimento.getPeso() == null || procedimento.getPeso() == 0) {
                    procedimento.setPeso(1);
                }
                if (procedimento.getIdProcedimento() != null) {
                    //modifica
                    procedimentoAction.aggiorna(procedimento);
                    redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_MODIFICA + "#" + procedimento.getDesProcedimento());
                } else {
                    //salva
                    procedimentoAction.inserisci(procedimento);
                    redirectAttributes.addFlashAttribute(OPERAZIONE_EFFETTUATA, OPERAZIONE_AGGIUNTA + "#" + procedimento.getDesProcedimento());
                }
            }
        } catch (Exception ex) {
            Log.APP.error("Errore salvando il procedimento", ex);
            model.addAttribute("procedimento", procedimento);
            return "procedimenti_lista_modifica";
        }
        return "redirect:/gestione/procedimenti/lista.htm";

    }

    private String messaggioOperazioneEffettuata(String input) {
        if (input != null && input.length() != 0) {
            List<String> inputlist = Arrays.asList(input.split("#"));
            Character tipoOperazione = inputlist.get(0).charAt(0);
            if ((inputlist.size() == 2) && (inputlist.get(0).length() == 1)) {
                String parametri = inputlist.get(1);
                switch (tipoOperazione) {
                    case 'M':
                        return "Salvataggio delle modifiche apportate al procedimento " + parametri;
                    case 'A':
                        return "Salvataggio in database del nuovo procedimento: " + parametri;
                    case 'E':
                        return "Rimozione del procedimento " + parametri;
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
