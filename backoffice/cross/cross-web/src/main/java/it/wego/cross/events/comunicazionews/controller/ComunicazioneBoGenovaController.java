/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazionews.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import it.wego.cross.constants.GenovaConstants;
import it.wego.cross.controller.AbstractController;
import it.wego.cross.dao.AllegatiDao;
import it.wego.cross.dto.AllegatoComunicazioneGenovaDTO;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.ComunicazioneGenovaDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.genova.actions.PluginGenovaAction;
import it.wego.cross.genova.edilizia.client.xml.Allegati;
import it.wego.cross.genova.edilizia.client.xml.Allegato;
import it.wego.cross.genova.edilizia.client.xml.Evt;
import it.wego.cross.genova.edilizia.client.xml.RispostaBO;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.service.EventiService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.utils.Utils;

/**
 *
 * @author giuseppe
 */
@Controller
public class ComunicazioneBoGenovaController extends AbstractController {

    private static final String COMUNICAZIONE_AVANZAMENTO = "comunicazioneevanzamento";
    private static final String REDIRECT_GESTISCI_PRATICA = "redirect:/pratiche/gestisci.htm";
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private EventiService eventiService;
    @Autowired
    private AllegatiDao allegatiDao;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private PluginGenovaAction pluginGenovaAction;    
    @Autowired
    private ComunicazioneWsGenovaClient client;
    private static final Logger log = LoggerFactory.getLogger("plugin");

    @RequestMapping("/pratica/comunicazionebogenova/index")
    public String index(@ModelAttribute("id_pratica_selezionata") Integer idPratica, @ModelAttribute("id_evento") Integer idEvento, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().setAttribute(GenovaConstants.EVENTO_SELEZIONATO, idEvento);
            Pratica pratica = praticheService.getPratica(idPratica);
            List<AllegatoDTO> allegati = praticheService.getAllegatiPratica(pratica);
            ComunicazioneGenovaDTO comunicazione = new ComunicazioneGenovaDTO();
            if (allegati != null && !allegati.isEmpty()) {
                List<AllegatoComunicazioneGenovaDTO> allegatiGenova = new ArrayList<AllegatoComunicazioneGenovaDTO>();
                for (AllegatoDTO a : allegati) {
                    AllegatoComunicazioneGenovaDTO dto = new AllegatoComunicazioneGenovaDTO();
                    dto.setDescrizione(a.getDescrizione());
                    dto.setIdFile(a.getIdAllegato());
                    dto.setIdFileEsterno(a.getIdFileEsterno());
                    dto.setNomeFile(a.getNomeFile());
                    allegatiGenova.add(dto);
                }
                comunicazione.setAllegati(allegatiGenova);
            }
            model.addAttribute("comunicazione", comunicazione);

        } catch (Exception ex) {
            log.error("Si è verificato un errore cercando di recuperare la pratica", ex);
        }
        return COMUNICAZIONE_AVANZAMENTO;
    }

    @RequestMapping("/pratica/comunicazionebogenova/salva")
    public String salva(Model model, @ModelAttribute("comunicazione") ComunicazioneGenovaDTO comunicazione, HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer idPratica = (Integer) request.getSession().getAttribute(GenovaConstants.ID_PRATICA_SELEZIONATA);
            Integer idEvento = (Integer) request.getSession().getAttribute(GenovaConstants.EVENTO_SELEZIONATO);
            Utente utente = utentiService.getUtenteConnesso(request);
            RispostaBO esito = salvaEvento(idPratica, idEvento, utente, comunicazione);
            if (esito.getEsito().equalsIgnoreCase("KO")) {

            }
            return REDIRECT_GESTISCI_PRATICA;
        } catch (Exception ex) {
            log.error("Si è verificato un errore contattando il servizio", ex);
            model.addAttribute("comunicazione", comunicazione);
            return COMUNICAZIONE_AVANZAMENTO;
        }
    }

    private RispostaBO salvaEvento(Integer idPratica, Integer idEvento, Utente utente, ComunicazioneGenovaDTO comunicazione) throws Exception {

        Pratica pratica = praticheService.getPratica(idPratica);
        ProcessiEventi pe = eventiService.findProcessiEventiById(idEvento);
        Evt evento = new Evt();
        evento.setTipoPraticaEdilizia("");
        evento.setNumeroPraticaEdilizia(comunicazione.getNumeroPraticaEdilizia());
        evento.setAnnoPraticaEdilizia(comunicazione.getAnnoPraticaEdilizia());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dataProtocolloGenerale = df.format(comunicazione.getDataProtocolloGenerale());
        evento.setDataProtocolloGenerale(dataProtocolloGenerale);
        evento.setNumeroProtocolloGenerale(comunicazione.getNumeroProtocolloGenerale());
        evento.setCodiceEvento(comunicazione.getCodiceEvento());
        XMLGregorianCalendar dataInizio = Utils.dateToXmlGregorianCalendar(comunicazione.getDataInizio());
        evento.setDataInizio(dataInizio);
        if (comunicazione.getDataFine() != null) {
            XMLGregorianCalendar dataFine = Utils.dateToXmlGregorianCalendar(comunicazione.getDataFine());
            evento.setDataFine(dataFine);
        }
        evento.setNote(comunicazione.getNote());
        try {
            List<it.wego.cross.entity.Allegati> allegati = null;
            if (comunicazione.getAllegati() != null && !comunicazione.getAllegati().isEmpty()) {
                allegati = getAllegati(comunicazione, pratica);
                Allegati allegatiXml = getAllegatiPerEvento(allegati);
                evento.setAllegati(allegatiXml);
            }
            RispostaBO esito = callWebService(evento, pratica);
            PraticheEventi eventoPratica = new PraticheEventi();
            eventoPratica.setIdPratica(pratica);
            eventoPratica.setIdEvento(pe);
            eventoPratica.setDataEvento(new Date());
            eventoPratica.setIdUtente(utente);
            eventoPratica.setNote(evento.getNote());
            eventoPratica.setVerso(pe.getVerso().toString());
            eventoPratica.setVisibilitaCross("S");
            eventoPratica.setVisibilitaUtente("N");
            eventoPratica.setProtocollo(evento.getNumeroProtocolloGenerale());
            pluginGenovaAction.salvaEvento(pratica,eventoPratica,allegati);            
            return esito;
        } catch (Exception ex) {
            log.error("Si è verificato un errore", ex);
            throw new Exception("Errore nel plugin", ex);
        }
    }

    private List<it.wego.cross.entity.Allegati> getAllegati(ComunicazioneGenovaDTO comunicazione, Pratica pratica) throws Exception {
        List<it.wego.cross.entity.Allegati> allegati = new ArrayList<it.wego.cross.entity.Allegati>();
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        LkComuni comune = pratica.getIdComune();
        Integer idEnte = ente == null ? null : ente.getIdEnte();
        Integer idComune = comune == null ? null : comune.getIdComune();
        
        GestioneAllegati gestioneAllegati = pluginService.getGestioneAllegati(idEnte, idComune);
        int i = 0;
        for (AllegatoComunicazioneGenovaDTO a : comunicazione.getAllegati()) {
            it.wego.cross.entity.Allegati adb = new it.wego.cross.entity.Allegati();
            if (a.getFile() != null) {
                adb.setNomeFile(i + "_" + a.getFile().getOriginalFilename());
                adb.setIdFileEsterno(null);
                adb.setTipoFile(a.getFile().getContentType());
                adb.setFile(a.getFile().getBytes());
                adb.setDescrizione(a.getDescrizione());
                allegati.add(adb);
                i++;
            } else {
                if (a.getIdFile() != null && a.getIdFile() > 0) {
                    adb = allegatiDao.getAllegato(a.getIdFile());
                    adb.setNomeFile(adb.getNomeFile());
                    if (adb.getIdFileEsterno() == null || adb.getIdFileEsterno().trim().equals("")) {
                        //Aggiorno i riferimenti al documentale (se è stata fatta la sincronizzazione da egrammata ad auriga)
                        gestioneAllegati.getFileContent(adb, pratica.getIdProcEnte().getIdEnte(), pratica.getIdComune());
                    }
                    allegati.add(adb);
                }
            }
        }
        String idUD = gestioneAllegati.uploadFile(pratica, allegati);
        for (it.wego.cross.entity.Allegati allegato : allegati) {
            if (allegato.getIdFileEsterno() != null) {
                //DO NOTHING
            } else {
                String fileEsterno = "D|" + idUD + "|" + allegato.getNomeFile();
                allegato.setIdFileEsterno(fileEsterno);
            }
        }
        return allegati;
    }

    private Allegati getAllegatiPerEvento(List<it.wego.cross.entity.Allegati> allegati) {
        Allegati allegatiXml = new Allegati();
        for (it.wego.cross.entity.Allegati adb : allegati) {
            Allegato a = new Allegato();
            a.setDescrizione(adb.getDescrizione());
            if (adb.getIdFileEsterno() != null) {
                a.setFile(null);
                a.setIdFileEsterno(adb.getIdFileEsterno());
            } else {
                if (adb.getFile() != null) {
                    String file = Base64.encodeBase64String(adb.getFile());
                    a.setFile(file);
                } else if (adb.getPathFile() != null) {
                    InputStream stream = null;
                    try {
                        File f = new File(adb.getPathFile());
                        stream = new FileInputStream(f);
                        byte[] file = IOUtils.toByteArray(stream);
                        String fileString = Base64.encodeBase64String(file);
                        a.setFile(fileString);
                    } catch (IOException ex) {
                        log.error("Non è stato trovato il file " + adb.getPathFile(), ex);
                    } finally {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (IOException ex) {
                                log.error("Errore chiudendo lo stream. Non è stato trovato il file " + adb.getPathFile(), ex);
                            }
                        }
                    }
                }
                a.setIdFileEsterno(null);
            }
            a.setNomeFile(adb.getNomeFile());
            a.setTipoFile(adb.getTipoFile());
            allegatiXml.getAllegato().add(a);
        }
        return allegatiXml;
    }

    private RispostaBO callWebService(Evt evento, Pratica pratica) throws Exception {
        String xmlEvento = Utils.marshall(evento);
        log.debug("Richiesta da inviare al webservice");
        log.debug(xmlEvento);
        Enti ente = pratica.getIdProcEnte().getIdEnte();
        LkComuni comune = pratica.getIdComune();
        
        RispostaBO esito = client.inoltraComunicazione(xmlEvento, ente, comune);
        return esito;
    }
}
