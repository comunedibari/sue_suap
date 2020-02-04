/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import com.google.common.base.Strings;
import it.wego.cross.beans.layout.Message;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.constants.Constants;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.StagingDao;
import it.wego.cross.dto.ErroreDTO;
import it.wego.cross.dto.comunica.AllegatoComunicaDTO;
import it.wego.cross.dto.comunica.InterventoDTO;
import it.wego.cross.dto.comunica.PraticaComunicaDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.LkComuni;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.PraticaProcedimentiPK;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Staging;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.plugins.pratica.GestionePratica;
import it.wego.cross.serializer.PraticheSerializer;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.EntiService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcedimentiService;
import it.wego.cross.service.ProcessiService;
import it.wego.cross.service.UsefulService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GabrieleM
 */
@Component
public class ComunicaAction {

    @Autowired
    ProcedimentiService procedimentiService;
    @Autowired
    PraticheSerializer praticheSerializer;
    @Autowired
    PraticheService praticheService;
    @Autowired
    EntiService entiService;
    @Autowired
    UsefulService usefulService;
    @Autowired
    PluginService pluginService;
    @Autowired
    LookupService lookupService;
    @Autowired
    WorkFlowService workflowService;
    @Autowired
    MessageSource messageSource;
    @Autowired
    ProcessiService processiService;
    @Autowired
    ErroriAction erroriAction;
    @Autowired
    PraticaDao praticaDao;
    @Autowired
    StagingDao stagingDao;
    @Autowired
    AllegatiService allegatiService;

    @Transactional(rollbackFor = Exception.class)
    public ErroreDTO salvaPraticaComunica(PraticaComunicaDTO comunica, Utente utente) throws Exception {
        Message error = new Message();
        Enti ente = entiService.findByIdEnte(comunica.getSportello().getId());
        List<Procedimenti> procedimenti = getProcedimentiList(comunica);
        Staging staging;
        try {
            staging = inserisciStaging(comunica);
        } catch (Exception ex) {
            //Errore inserendo lo staging
            String e = messageSource.getMessage("error.comunica.inserimentostaging", null, Locale.getDefault());
            Log.APP.error(e, ex);
            List<String> errore = new ArrayList<String>();
            errore.add(e);
            error.setMessages(errore);
            error.setError(Boolean.TRUE);
            ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_SALVA, e, ex, null, utente);
            return erroredto;
        }
        Pratica pratica;
        try {
            pratica = inserisciPratica(comunica, utente, ente, procedimenti, staging);
        } catch (Exception ex) {
            String e = messageSource.getMessage("error.comunica.inserimentopratica", null, Locale.getDefault());
            Log.APP.error(e, ex);
            List<String> errore = new ArrayList<String>();
            errore.add(e);
            error.setMessages(errore);
            error.setError(Boolean.TRUE);
            ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_SALVA, e, ex, null, utente);
            return erroredto;
        }
        try {
            popolaProcedimenti(pratica, procedimenti);
        } catch (Exception ex) {
            //Errore inserendo i procedimenti
            String e = messageSource.getMessage("error.comunica.inserimentoprocedimenti", null, Locale.getDefault());
            Log.APP.error(e, ex);
            List<String> errore = new ArrayList<String>();
            errore.add(e);
            error.setMessages(errore);
            error.setError(Boolean.TRUE);
            ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_SALVA, e, ex, pratica, utente);
            return erroredto;
        }
        try {
            inserisciEvento(comunica, pratica);
        } catch (Exception ex) {
            //Errore inserendo l'evento
            String e = messageSource.getMessage("error.comunica.inserimentoevento", null, Locale.getDefault());
            Log.APP.error(e, ex);
            List<String> errore = new ArrayList<String>();
            errore.add(e);
            error.setMessages(errore);
            error.setError(Boolean.TRUE);
            ErroreDTO erroredto = erroriAction.getError(it.wego.cross.constants.Error.ERRORE_COMUNICA_SALVA, e, ex, pratica, utente);
            return erroredto;
        }
        return new ErroreDTO();
    }

    private List<Procedimenti> getProcedimentiList(PraticaComunicaDTO comunica) throws Exception {
        List<Procedimenti> procedimenti = new ArrayList<Procedimenti>();
        Log.APP.info("Sono stati inseriti " + comunica.getInterventi().size() + " procedimenti. Cerco quello di riferimento ...");
        for (InterventoDTO intervento : comunica.getInterventi()) {
            Procedimenti procedimento = procedimentiService.findProcedimentoByIdProc(intervento.getId());
            procedimenti.add(procedimento);
        }
        Procedimenti procedimentoRiferimento = procedimentiService.findProcedimentoByIdProc(comunica.getIdProcedimentoSuap());
        procedimenti.add(procedimentoRiferimento);
        return procedimenti;
    }

    private Staging inserisciStaging(PraticaComunicaDTO comunica) throws Exception {
        List<Procedimenti> procedimenti = new ArrayList<Procedimenti>();
        Log.APP.info("Sono stati inseriti " + comunica.getInterventi().size() + " procedimenti. Cerco quello di riferimento ...");
        for (InterventoDTO intervento : comunica.getInterventi()) {
            Procedimenti procedimento = procedimentiService.findProcedimentoByIdProc(intervento.getId());
            procedimenti.add(procedimento);
        }
        Procedimenti procedimentoRiferimento = procedimentiService.findProcedimentoByIdProc(comunica.getIdProcedimentoSuap());
        procedimenti.add(procedimentoRiferimento);
        Staging staging = new Staging();
        Log.WS.info("Preparo i dati per salvare l'area di staging");
        it.wego.cross.xml.Pratica praticaXml = praticheSerializer.serialize(comunica);
        String xmlPratica = Utils.marshall(praticaXml);
        Log.WS.info("Dump XML pratica");
        Log.WS.info(xmlPratica);
        staging.setOggetto(comunica.getOggetto());
        Log.WS.info("Data ricezione: " + Utils.dateItalianFormat(comunica.getDataRicezionePratica()));
        staging.setDataRicezione(comunica.getDataRicezionePratica());
        Log.WS.info("Tipo messaggio: " + Constants.COMUNICA);
        staging.setTipoMessaggio(Constants.COMUNICA);
        staging.setXmlRicevuto(null);
        staging.setXmlPratica(xmlPratica.getBytes());
        Enti ente = entiService.findByIdEnte(comunica.getSportello().getId());
        staging.setIdEnte(ente);
        stagingDao.insert(staging);
        usefulService.flush();
        return staging;
    }

    private Pratica inserisciPratica(PraticaComunicaDTO comunica, Utente utente, Enti ente, List<Procedimenti> procedimenti, Staging staging) throws Exception {
        Pratica pratica = new Pratica();
        Log.APP.info("Preparo i dati per il salvataggio della pratica COMUNICA");
        Log.APP.info("Serializzo i dati per la creazione della pratica");
        LkComuni comune = lookupService.findComuneById(comunica.getComuneRiferimento().getId());
        String identificativoPratica = comunica.getProtocollo().getRegistro() + "/" + comunica.getProtocollo().getAnno() + "/" + comunica.getProtocollo().getNumero();
        Log.APP.info("Identificativo pratica: " + identificativoPratica);
        pratica.setIdentificativoPratica(identificativoPratica);
        Log.APP.info("Anno di riferimento: " + comunica.getProtocollo().getAnno());
        pratica.setAnnoRiferimento(Integer.valueOf(comunica.getProtocollo().getAnno()));
        Log.APP.info("Registro: " + comunica.getProtocollo().getRegistro());
        pratica.setCodRegistro(comunica.getProtocollo().getRegistro());
        Log.APP.info("Numero di protocollo: " + comunica.getProtocollo().getNumero());
        pratica.setProtocollo(comunica.getProtocollo().getNumero());
        Log.APP.info("Fascicolo: " + comunica.getProtocollo().getFascicolo());

//TODO: protocollo        
        //pratica.setCodFascicolo(comunica.getProtocollo().getFascicolo());
        String dataricezione = Utils.dateItalianFormat(comunica.getDataRicezionePratica());
        Log.APP.info("Data ricezione: " + dataricezione);
        pratica.setDataRicezione(comunica.getDataRicezionePratica());
        pratica.setIdStaging(staging);
        Log.APP.info("Comune di riferimento: " + comune.getDescrizione());
        pratica.setIdComune(comune);
        Log.APP.info("Sportello di riferimento: " + comunica.getSportello().getDescrizione());
        GestionePratica gp = pluginService.getGestionePratica(ente.getIdEnte());
        Procedimenti procedimentoSuap = gp.getProcedimentoRiferimento(procedimenti);
        Log.APP.info("Procedimento di riferimento: " + procedimentoSuap.getCodProc());
        ProcedimentiEnti pe = procedimentiService.findProcedimentiEnti(ente.getIdEnte(), procedimentoSuap.getIdProc());
        if (Strings.isNullOrEmpty(pratica.getResponsabileProcedimento())) {
            pratica.setResponsabileProcedimento(pe.getResponsabileProcedimento());
        }
        pratica.setIdProcEnte(pe);
//            pratica.setIdEnte(ente);
//            pratica.setIdProc(procedimentoSuap);
        //workflowService.getProcessToUse(ente.getIdEnte(), procedimentoSuap.getIdProc());
        Processi processo = workflowService.getProcessToUse(ente.getIdEnte(), procedimentoSuap.getIdProc());
        Log.APP.info("Processo (flusso) di riferimento: " + processo.getDesProcesso());
        pratica.setIdProcesso(processo);
        Log.APP.info("Oggetto della pratica: " + comunica.getOggetto());
        pratica.setOggettoPratica(comunica.getOggetto());
        pratica.setDataApertura(new Date());
        pratica.setIdUtente(utente);
        praticaDao.insert(pratica);
        usefulService.flush();
        return pratica;
    }

    private void popolaProcedimenti(Pratica pratica, List<Procedimenti> procedimenti) throws Exception {
        List<PraticaProcedimenti> praticaProcedimentiList = new ArrayList<PraticaProcedimenti>();
        for (Procedimenti procedimento : procedimenti) {
            PraticaProcedimenti praticaProcedimenti = new PraticaProcedimenti();
            //Presuppongo che coincida con l'ID inserito nell'anagrafica enti di CROSS
            PraticaProcedimentiPK key = new PraticaProcedimentiPK();
            key.setIdEnte(pratica.getIdProcEnte().getIdEnte().getIdEnte());
            key.setIdPratica(pratica.getIdPratica());
            key.setIdProcedimento(procedimento.getIdProc());
            praticaProcedimenti.setPraticaProcedimentiPK(key);
            praticaDao.insert(praticaProcedimenti);
            usefulService.flush();
            usefulService.refresh(praticaProcedimenti);
            praticaProcedimentiList.add(praticaProcedimenti);
        }
        pratica.setPraticaProcedimentiList(praticaProcedimentiList);
        praticaDao.update(pratica);
        usefulService.flush();

    }

    private void inserisciEvento(PraticaComunicaDTO comunica, Pratica pratica) throws Exception {
        ProcessiEventi eventoProcesso = processiService.findProcessiEventiByCodEventoIdProcesso(AnaTipiEvento.RICEZIONE_PRATICA, pratica.getIdProcesso());
        ComunicazioneBean cb = new ComunicazioneBean();
        Log.WS.info("Cerco evento di ricezione");
        Log.WS.info("Setto la pratica");
        cb.setIdPratica(pratica.getIdPratica());
        Log.WS.info("Setto l'evento");
        cb.setIdEventoProcesso(eventoProcesso.getIdEvento());
        //Non invio la comunicazione
        cb.setInviaMail(Boolean.FALSE);
        cb.setVisibilitaCross(Boolean.TRUE);
        cb.setVisibilitaUtente(Boolean.TRUE);
        AllegatoComunicaDTO allegatoPratica = new AllegatoComunicaDTO();
        for (AllegatoComunicaDTO allegato : comunica.getAllegati()) {
            Log.WS.info("Aggiungo il file " + allegato.getFile().getOriginalFilename() + " all'evento");
            Allegato a = new Allegato();
            if (eventoProcesso.getFlgProtocollazione().equalsIgnoreCase("S")) {
                a.setProtocolla(Boolean.TRUE);
            } else {
                a.setProtocolla(Boolean.FALSE);
            }
            a.setDescrizione(allegato.getDescrizione());
            a.setFile(allegato.getFile().getBytes());
            a.setNomeFile(allegato.getFile().getOriginalFilename());
            a.setTipoFile(allegato.getFile().getContentType());
            if (comunica.getIdAllegatoPratica().equals(allegato.getIdAllegato())) {
                allegatoPratica = allegato;
                a.setFileOrigine(Boolean.TRUE);
            }
            cb.addAllegato(a);
        }
        cb.setOggettoProtocollo(pratica.getOggettoPratica());
        String protocollo = pratica.getCodRegistro() + "/" + pratica.getAnnoRiferimento() + "/" + pratica.getProtocollo();
        cb.setNumeroProtocollo(protocollo);
        cb.setDataProtocollo(pratica.getDataProtocollazione());
        praticaDao.update(pratica);
        Log.WS.info("Gestisco l'evento");
        workflowService.gestisciProcessoEvento(cb);
        Log.WS.info("Salvo il modello della pratica");
        for (Integer idAllegato : cb.getIdAllegati()) {
            Allegati allegato = allegatiService.findAllegatoById(idAllegato);
            if (allegato != null && allegato.getNomeFile().equals(allegatoPratica.getFile().getOriginalFilename())) {
                pratica.setIdModello(allegato);
                praticaDao.update(pratica);
                usefulService.flush();
                break;
            }
        }
    }
}
