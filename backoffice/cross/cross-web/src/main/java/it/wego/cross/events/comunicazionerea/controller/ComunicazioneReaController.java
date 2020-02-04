/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazionerea.controller;

import it.wego.cross.actions.ErroriAction;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.beans.layout.Select;
import it.wego.cross.controller.AbstractController;
import it.wego.cross.controller.EventoController;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.ProcessiSteps;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazionerea.action.ComunicazioneReaAction;
import it.wego.cross.events.comunicazionerea.bean.AnagraficaPraticaDTO;
import it.wego.cross.events.comunicazionerea.bean.ComunicazioneReaDTO;
import it.wego.cross.events.comunicazionerea.entity.RiIntegrazioneRea;
import it.wego.cross.events.comunicazionerea.service.ComunicazioneReaService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Beppe
 */
@Controller
public class ComunicazioneReaController extends AbstractController {

    private static final String PRATICA_ISTRUTTORIA = "comunicazionerea_istruttoria_view";
    private static final String PRATICA_EVASA = "comunicazionerea_evasa_view";
    private static final String PRATICA_RIFIUTATA = "comunicazionerea_rifiutata_view";
    private static final String PRATICA_SOSPESA = "comunicazionerea_sospesa_view";
    @Autowired
    private ComunicazioneReaService comunicazioneReaService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private ErroriAction erroriAction;
    @Autowired
    private ComunicazioneReaAction comunicazioneReaAction;

    @RequestMapping("/pratica/comunicazionerea_istruttoria/index")
    public String indexIstruttoria(Model model, @ModelAttribute("id_pratica_selezionata") Integer idPratica, @ModelAttribute("id_evento") Integer idEvento, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        String index;
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            pratica = praticheService.getPratica(idPratica);
            index = indexComunicazioneRea(model, idPratica, idEvento, "istruttoria", result, request, response);
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di visualizzare l'evento di comunicazione", ex);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            Message msg = new Message();
            msg.setMessages(errors);
            model.addAttribute("message", msg);
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONEREA_ISTRUTTORIA_INDEX, "Errore nell'esecuzione del controller /pratica/comunicazionerea_istruttoria/index", ex, pratica, utente);
            erroriAction.saveError(err);
            index = EventoController.EVENTO_SCELTA;
        }
        if (index != null) {
            return index;
        } else {
            return PRATICA_ISTRUTTORIA;
        }
    }

    @RequestMapping("/pratica/comunicazionerea_evasa/index")
    public String indexEvasione(Model model, @ModelAttribute("id_pratica_selezionata") Integer idPratica, @ModelAttribute("id_evento") Integer idEvento, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        String index;
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            pratica = praticheService.getPratica(idPratica);
            index = indexComunicazioneRea(model, idPratica, idEvento, "evasa", result, request, response);
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di visualizzare l'evento di comunicazione", ex);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            Message msg = new Message();
            msg.setMessages(errors);
            model.addAttribute("message", msg);
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONEREA_EVASA_INDEX, "Errore nell'esecuzione del controller /pratica/comunicazionerea_evasa/index", ex, pratica, utente);
            erroriAction.saveError(err);
            index = EventoController.EVENTO_SCELTA;
        }
        if (index != null) {
            return index;
        } else {
            return PRATICA_EVASA;
        }
    }

    @RequestMapping("/pratica/comunicazionerea_rifiutata/index")
    public String indexRifiuto(Model model, @ModelAttribute("id_pratica_selezionata") Integer idPratica, @ModelAttribute("id_evento") Integer idEvento, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        String index;
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            pratica = praticheService.getPratica(idPratica);
            index = indexComunicazioneRea(model, idPratica, idEvento, "rifiutata", result, request, response);
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di visualizzare l'evento di comunicazione", ex);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            Message msg = new Message();
            msg.setMessages(errors);
            model.addAttribute("message", msg);
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONEREA_RIFIUTATA_INDEX, "Errore nell'esecuzione del controller /pratica/comunicazionerea_evasa/rifiutata", ex, pratica, utente);
            erroriAction.saveError(err);
            index = EventoController.EVENTO_SCELTA;
        }
        if (index != null) {
            return index;
        } else {
            return PRATICA_RIFIUTATA;
        }
    }

    @RequestMapping("/pratica/comunicazionerea_sospesa/index")
    public String indexSospensione(Model model, @ModelAttribute("id_pratica_selezionata") Integer idPratica, @ModelAttribute("id_evento") Integer idEvento, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        String index;
        Utente utente = utentiService.getUtenteConnesso(request);
        Pratica pratica = null;
        try {
            pratica = praticheService.getPratica(idPratica);
            index = indexComunicazioneRea(model, idPratica, idEvento, "sospesa", result, request, response);
        } catch (Exception ex) {
            Log.APP.error("Si è verificato un errore cercando di visualizzare l'evento di comunicazione", ex);
            List<String> errors = new ArrayList<String>();
            errors.add(ex.getMessage());
            Message msg = new Message();
            msg.setMessages(errors);
            model.addAttribute("message", msg);
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONEREA_SOSPESA_INDEX, "Errore nell'esecuzione del controller /pratica/comunicazionerea_sospesa/index", ex, pratica, utente);
            erroriAction.saveError(err);
            index = EventoController.EVENTO_SCELTA;
        }
        if (index != null) {
            return index;
        } else {
            return PRATICA_SOSPESA;
        }
    }

    public String indexComunicazioneRea(Model model,
            Integer idPratica,
            Integer idEvento,
            String tipologiaComunicazioneRea,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Pratica pratica = praticheService.getPratica(idPratica);
        ProcessiEventi processoEvento = workFlowService.findProcessiEventiById(idEvento);

        RiIntegrazioneRea integrazioneRea = comunicazioneReaService.getIntegrazioneRea(pratica);

        ComunicazioneReaDTO comunicazioneRea = new ComunicazioneReaDTO();
        comunicazioneRea.setIdPratica(idPratica);
        comunicazioneRea.setIdEvento(idEvento);
        comunicazioneRea.setTipologiaComunicazioneRea(tipologiaComunicazioneRea);
        if (integrazioneRea != null) {
            comunicazioneRea.setAziendaRiferimento(integrazioneRea.getIdAziendaRiferimento().getIdAnagrafica());
            comunicazioneRea.setCaricaRappresentanteAzienda(integrazioneRea.getCaricaLegaleRapp());
            comunicazioneRea.setDichiarantePratica(integrazioneRea.getIdAnagraficaDichiarante().getIdAnagrafica());
            comunicazioneRea.setFormaGiuridicaAzienda(integrazioneRea.getFormaGiuridicaImpresa());
            comunicazioneRea.setQualificaDichiarantePratica(integrazioneRea.getQualificaDichiarantePratica());
            comunicazioneRea.setRappresentanteAzienda(integrazioneRea.getIdAnagraficaLegaleRapp().getIdAnagrafica());
            comunicazioneRea.setTipologiaIntervento(integrazioneRea.getTipoIntervento());
            comunicazioneRea.setTipologiaProcedimento(integrazioneRea.getTipoProcedimento());
        }

        List<AllegatoDTO> allegati = comunicazioneReaService.getAllegati(pratica);
        List<AnagraficaPraticaDTO> anagraficheFisichePraticaList = comunicazioneReaService.getAnagraficheFisichePraticaList(pratica);
        List<AnagraficaPraticaDTO> anagraficheGiuridichePraticaList = comunicazioneReaService.getAnagraficheGiuridichePraticaList(pratica);

        model.addAttribute("allegati", allegati);

        model.addAttribute("titoloPagina", processoEvento.getDesEvento());

        model.addAttribute("eventiPartenzaList", getPropertiesSelect(pratica, processoEvento));

        model.addAttribute("tipologieProcedimento", getPropertiesSelect("tipologie_procedimento"));
        model.addAttribute("tipologieIntervento", getPropertiesSelect("tipologie_intervento"));

        model.addAttribute("formeGiuridicaAzienda", getPropertiesSelect("forme_giuridica_azienda"));
        model.addAttribute("qualificheDichiarantePratica", getPropertiesSelect("qualifiche_dichiarante_pratica"));
        model.addAttribute("caricheRappresentanteAzienda", getPropertiesSelect("cariche_rappresentante_azienda"));

        model.addAttribute("anagraficheFisichePraticaList", anagraficheFisichePraticaList);
        model.addAttribute("anagraficheGiuridichePraticaList", anagraficheGiuridichePraticaList);
        model.addAttribute("comunicazioneRea", comunicazioneRea);

        return null;
    }

    @RequestMapping(value = "/pratica/comunicazionerea/submit", method = RequestMethod.POST)
    public String comunicazioneReaSubmit(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            ComunicazioneReaDTO comunicazioneRea,
            BindingResult result) throws Exception {
        Utente utente = utentiService.getUtenteConnesso(request);
        try {
            UtenteDTO utenteConnesso = utentiService.getUtenteConnessoDTO(request);
            comunicazioneReaAction.comunicazioneReaSubmitAction(comunicazioneRea, utenteConnesso.getIdUtente(), comunicazioneRea.getTipologiaComunicazioneRea());
        } catch (Exception e) {
            Pratica pratica = praticheService.getPratica(comunicazioneRea.getIdPratica());
            ErroreDTO err = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_PRATICA_COMUNICAZIONEREA_SUBMIT, "Errore nell'esecuzione del controller /pratica/comunicazionerea/submit", e, pratica, utente);
            erroriAction.saveError(err);
            ProcessiEventi processoEvento = workFlowService.findProcessiEventiById(comunicazioneRea.getIdEvento());
            List<AllegatoDTO> allegati = comunicazioneReaService.getAllegati(pratica);
            List<AnagraficaPraticaDTO> anagraficheFisichePraticaList = comunicazioneReaService.getAnagraficheFisichePraticaList(pratica);
            List<AnagraficaPraticaDTO> anagraficheGiuridichePraticaList = comunicazioneReaService.getAnagraficheGiuridichePraticaList(pratica);
            model.addAttribute("allegati", allegati);
            model.addAttribute("titoloPagina", processoEvento.getDesEvento());

            model.addAttribute("eventiPartenzaList", getPropertiesSelect(pratica, processoEvento));

            model.addAttribute("tipologieProcedimento", getPropertiesSelect("tipologie_procedimento"));
            model.addAttribute("tipologieIntervento", getPropertiesSelect("tipologie_intervento"));

            model.addAttribute("formeGiuridicaAzienda", getPropertiesSelect("forme_giuridica_azienda"));
            model.addAttribute("qualificheDichiarantePratica", getPropertiesSelect("qualifiche_dichiarante_pratica"));
            model.addAttribute("caricheRappresentanteAzienda", getPropertiesSelect("cariche_rappresentante_azienda"));

            model.addAttribute("anagraficheFisichePraticaList", anagraficheFisichePraticaList);
            model.addAttribute("anagraficheGiuridichePraticaList", anagraficheGiuridichePraticaList);
            model.addAttribute("comunicazioneRea", comunicazioneRea);

            Message error = new Message();
            error.setError(Boolean.TRUE);
            error.setMessages(Arrays.asList("Si è verificato un errore durante la Comunicazione Rea."));
            request.setAttribute("message", error);
            Log.APP.error("/pratica/comunicazionerea/submit", e);

            if (comunicazioneRea.getTipologiaComunicazioneRea().equalsIgnoreCase("istruttoria")) {
                return PRATICA_ISTRUTTORIA;
            }
            if (comunicazioneRea.getTipologiaComunicazioneRea().equalsIgnoreCase("evasa")) {
                return PRATICA_EVASA;
            }
            if (comunicazioneRea.getTipologiaComunicazioneRea().equalsIgnoreCase("rifiutata")) {
                return PRATICA_RIFIUTATA;
            }
            if (comunicazioneRea.getTipologiaComunicazioneRea().equalsIgnoreCase("sospesa")) {
                return PRATICA_SOSPESA;
            }
        }
        return EventoController.REDIRECT_HOME_PAGE;
    }

    private List<Select> getPropertiesSelect(Pratica pratica, ProcessiEventi processoEvento) throws Exception {
        List<Select> elencoEventiPartenza = new ArrayList<Select>();
        Select eventoPartenzaLoop;

        for (ProcessiSteps processoStep : processoEvento.getIdEventoResultList()) {
            if (processoStep.getTipoOperazione().equalsIgnoreCase("ADD")) {
                for (PraticheEventi praticaEvento : pratica.getPraticheEventiList()) {
                    if (praticaEvento.getIdEvento().equals(processoStep.getIdEventoTrigger())) {
                        eventoPartenzaLoop = new Select();
                        eventoPartenzaLoop.setItemValue(praticaEvento.getIdPraticaEvento().toString());
                        eventoPartenzaLoop.setItemLabel(processoStep.getIdEventoTrigger().getDesEvento() + "( " + Utils.convertDataToString(praticaEvento.getDataEvento()) + " )");
                        elencoEventiPartenza.add(eventoPartenzaLoop);
                    }
                }
            }
        }
        return elencoEventiPartenza;
    }

    private List<Select> getPropertiesSelect(String filename) throws Exception {
        URL resource = this.getClass().getResource("../properties/" + filename + ".properties");
        return getPropertiesSelect(resource);
    }

    private List<Select> getPropertiesSelect(URL fileUrl) throws Exception {
        List<Select> optionList = new ArrayList<Select>();
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(fileUrl.toURI())));

        Select optionLoop;
        for (String key : properties.stringPropertyNames()) {
            optionLoop = new Select();
            optionLoop.setItemValue(key);
            optionLoop.setItemLabel(properties.getProperty(key));
            optionList.add(optionLoop);
        }
        Collections.sort(optionList);
        return optionList;
    }

}
