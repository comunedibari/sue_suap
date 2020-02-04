/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.dao.LookupDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dto.PraticaDTO;
import it.wego.cross.dto.ProcessoEventoDTO;
import it.wego.cross.dto.ScadenzaEventoDTO;
import it.wego.cross.dto.dozer.PraticaEventoDTO;
import it.wego.cross.dto.dozer.ProcessoEventoAnagraficaDTO;
import it.wego.cross.dto.dozer.ProcessoEventoEnteDTO;
import it.wego.cross.entity.LkStatiScadenze;
import it.wego.cross.entity.LkStatoPratica;
import it.wego.cross.entity.LkTipiAttore;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.entity.Processi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.ProcessiEventiAnagrafica;
import it.wego.cross.entity.ProcessiEventiAnagraficaPK;
import it.wego.cross.entity.ProcessiEventiEnti;
import it.wego.cross.entity.ProcessiEventiEntiPK;
import it.wego.cross.entity.ProcessiEventiScadenze;
import it.wego.cross.entity.ProcessiEventiScadenzePK;
import it.wego.cross.entity.ProcessiSteps;
import it.wego.cross.exception.CrossException;
import it.wego.cross.service.EventiService;
import it.wego.cross.service.LookupService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcessiService;
import it.wego.cross.service.UsefulService;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GabrieleM
 */
@Component
public class ProcessiAction {

    @Autowired
    ProcessiDao processiDao;
    @Autowired
    UsefulService usefulService;
    @Autowired
    ProcessiService processiService;
    @Autowired
    private LookupService lookupService;
    @Autowired
    LookupDao lookupDao;
    @Autowired
    ProcedimentiDao procedimentiDao;
    @Autowired
    EventiService eventiService;
    @Autowired
    PraticheService praticheService;

    @Transactional(rollbackFor = Exception.class)
    public void inserisciProcesso(String codice, String descrizione) throws Exception {
        Processi processo = new Processi();
        processo.setCodProcesso(codice);
        processo.setDesProcesso(descrizione);
        processiDao.insert(processo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void aggiornaProcesso(Integer idProcesso, String codice, String descrizione) throws Exception {
        Processi processo = processiDao.findProcessiById(idProcesso);
        processo.setCodProcesso(codice);
        processo.setDesProcesso(descrizione);
        processiDao.update(processo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancellaProcesso(Integer idProcesso) throws Exception {
        Processi processo = processiDao.findProcessiById(idProcesso);
        processiDao.delete(processo);
    }

    private String verificaPraticheCollegate(Integer idProcesso) throws Exception {
        List<PraticheEventi> praticheCollegate = new ArrayList<PraticheEventi>();
        Processi processo = processiDao.findProcessiById(idProcesso);
        List<ProcessiEventi> processiEventi = processiDao.findProcessiEventiByIdProcesso(idProcesso);
        for (ProcessiEventi processoEvento : processiEventi) {
            praticheCollegate.addAll(processoEvento.getPraticheEventiList());
        }
        String descrizionePraticheCollegate = null;
        if (!praticheCollegate.isEmpty()) {
            descrizionePraticheCollegate = "Non è stato possibile eliminare il processo poiché esistono i seguenti collegamenti con pratiche presenti in database: ";
        }
        for (PraticheEventi praticaEvento : praticheCollegate) {
            descrizionePraticheCollegate += "Evento " + praticaEvento.getIdEvento().getDesEvento() + " collegato alla pratica " + praticaEvento.getIdPratica().getIdentificativoPratica() + ",";
        }
        if ((descrizionePraticheCollegate != null) && (!descrizionePraticheCollegate.isEmpty())) {
            descrizionePraticheCollegate = descrizionePraticheCollegate.substring(0, descrizionePraticheCollegate.length() - 1);
        }
        return descrizionePraticheCollegate;
    }

    private String verificaEntiCollegati(Integer idProcesso) throws Exception {
        List<ProcedimentiEnti> procedimentiEnti = procedimentiDao.findProcedimentiEntiByProcesso(idProcesso);
        String descrizioneEntiCollegati = null;
        if (!procedimentiEnti.isEmpty()) {
            descrizioneEntiCollegati = "Non è stato possibile eliminare il processo e tutti i suo eventi poichè esso è in relazione con i seguenti procedimenti-enti: ";
        }
        for (ProcedimentiEnti procedimentoEnte : procedimentiEnti) {
            descrizioneEntiCollegati += " " + procedimentoEnte.getIdProc().getCodProc() + "-" + procedimentoEnte.getIdEnte().getDescrizione() + ",";
        }
        if ((descrizioneEntiCollegati != null) && (!descrizioneEntiCollegati.isEmpty())) {
            descrizioneEntiCollegati = descrizioneEntiCollegati.substring(0, descrizioneEntiCollegati.length() - 1);
        }
        return descrizioneEntiCollegati;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<String> cancellaProcessoCascata(Integer idProcesso) throws Exception {
        List<String> elementiCollegati = new ArrayList<String>();
        elementiCollegati.add(verificaPraticheCollegate(idProcesso));
        elementiCollegati.add(verificaEntiCollegati(idProcesso));
        Processi processo = processiDao.findProcessiById(idProcesso);
        List<ProcessiEventi> processiEventi = processiDao.findProcessiEventiByIdProcesso(idProcesso);
        elementiCollegati.removeAll(Collections.singleton(null));
        if (elementiCollegati.isEmpty()) {
            for (ProcessiEventi processoEvento : processiEventi) {
                processiDao.delete(processoEvento);
            }
            processiDao.delete(processo);
        }

        return elementiCollegati;
    }

    @Transactional(rollbackFor = Exception.class)
    public void inserisciEvento(ProcessoEventoDTO evento) throws Exception {
        ProcessiEventi processoEvento = inserisciProcessoEvento(evento);
        usefulService.flush();
        evento.setIdEvento(processoEvento.getIdEvento());
        gestisciScadenze(evento);
    }

    @Transactional(rollbackFor = Exception.class)
    public void aggiornaEvento(ProcessoEventoDTO evento) throws Exception {
        aggiornaProcessoEvento(evento);
        gestisciScadenze(evento);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancellaEvento(ProcessoEventoDTO evento) throws Exception {
        ProcessiEventi processoEvento = processiService.findProcessiEventiByIdEvento(evento.getIdEvento());

        if (!processoEvento.getPraticheEventiList().isEmpty()) {
            StringBuilder sb = new StringBuilder("Impossibile eliminare l'evento, perchè già utilizzato nelle seguenti pratiche(eventi):<br/>");
            for (PraticheEventi praticaEvento : processoEvento.getPraticheEventiList()) {
                sb.append(praticaEvento.getIdPratica().getIdPratica()).append(" (").append(praticaEvento.getIdPraticaEvento()).append(")<br/>");
            }
            throw new CrossException(sb.toString());
        }
        if (processoEvento.getProcessiEventiAnagraficaList() != null) {
            for (ProcessiEventiAnagrafica pea : processoEvento.getProcessiEventiAnagraficaList()) {
                processiDao.delete(pea);
            }
        }
        if (processoEvento.getProcessiEventiEntiList() != null) {
            for (ProcessiEventiEnti pea : processoEvento.getProcessiEventiEntiList()) {
                processiDao.delete(pea);
            }
        }
        eventiService.cancellaEvento(processoEvento);
    }

    @Transactional(rollbackFor = Exception.class)
    public void aggiornaStep(ProcessiSteps step) throws Exception {
        processiDao.update(step);
    }

    @Transactional(rollbackFor = Exception.class)
    public void inserisciStep(ProcessiSteps step) throws Exception {
        processiDao.insert(step);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancellaStep(Integer idEventoResult, Integer idEvento) throws Exception {
        ProcessiSteps step = processiService.findStepByKey(idEvento, idEventoResult);
        processiDao.delete(step);
    }

    private void rimuoviProcessoEventoScadenza(ProcessiEventiScadenze pes) throws Exception {
        processiDao.delete(pes);
    }

    private void inserisciScadenza(ProcessiEventiScadenze pes) throws Exception {
        processiDao.update(pes);
    }

    private ProcessiEventi inserisciProcessoEvento(ProcessoEventoDTO eventoDto) throws Exception {
        ProcessiEventi evento = new ProcessiEventi();
        ProcessiEventi p = serializeProcessoEvento(eventoDto, evento);
        processiDao.insert(p);
        aggiornaProcessoEventoAnagrafica(eventoDto, p);
        aggiornaProcessoEventoEnte(eventoDto, p);
        return p;
    }

    private void aggiornaProcessoEvento(ProcessoEventoDTO eventoDto) throws Exception {
        ProcessiEventi processoEvento = processiService.findProcessiEventiByIdEvento(eventoDto.getIdEvento());
        ProcessiEventi p = serializeProcessoEvento(eventoDto, processoEvento);
        aggiornaProcessoEventoAnagrafica(eventoDto, p);
        aggiornaProcessoEventoEnte(eventoDto, p);
        processiDao.update(p);
    }

    private void gestisciScadenze(ProcessoEventoDTO evento) throws Exception {
        List<ProcessiEventiScadenze> scadenzeEntity = processiService.findScadenzaProcessoEventoByIdEvento(evento.getIdEvento());
        List<ScadenzaEventoDTO> scadenzeDto = evento.getScadenze();
        List<String> scadenzeAttuali = new ArrayList<String>();
        List<String> scadenzeDaForm = new ArrayList<String>();
        List<String> scadenzeDaEliminare = new ArrayList<String>();
        List<String> scadenzeDaAggiungere = new ArrayList<String>();
        if (scadenzeEntity != null && !scadenzeEntity.isEmpty()) {
            for (ProcessiEventiScadenze s : scadenzeEntity) {
                scadenzeAttuali.add(s.getProcessiEventiScadenzePK().getIdAnaScadenza());
                scadenzeDaEliminare.add(s.getProcessiEventiScadenzePK().getIdAnaScadenza());
            }
        }
        if (scadenzeDto != null && !scadenzeDto.isEmpty()) {
            for (ScadenzaEventoDTO dto : scadenzeDto) {
                if (dto.getIdAnaScadenza() != null) {
                    scadenzeDaForm.add(dto.getIdAnaScadenza());
                }
            }
        }

        for (String s : scadenzeDaForm) {
            if (!scadenzeAttuali.contains(s)) {
                scadenzeDaEliminare.add(s);
            } else {
                scadenzeDaAggiungere.add(s);
                scadenzeDaEliminare.remove(s);
            }
        }

        if (scadenzeDto != null && !scadenzeDto.isEmpty()) {
            for (ScadenzaEventoDTO dto : scadenzeDto) {
                if (dto.getIdAnaScadenza() != null && scadenzeDaAggiungere.contains(dto.getIdAnaScadenza())) {
                    ProcessiEventiScadenze pes = processiService.findScadenzaByIdEventoIdAnaScadenza(evento.getIdEvento(), dto.getIdAnaScadenza());
                    if (pes == null) {
                        pes = new ProcessiEventiScadenze();
                        ProcessiEventiScadenzePK key = new ProcessiEventiScadenzePK();
                        key.setIdEvento(evento.getIdEvento());
                        key.setIdAnaScadenza(dto.getIdAnaScadenza());
                        pes.setProcessiEventiScadenzePK(key);
                    }
                    LkStatiScadenze statoScadenza = lookupService.findStatoScadenza(dto.getIdStatoScadenza());
                    pes.setIdStatoScadenza(statoScadenza);
                    pes.setTerminiScadenza(dto.getTerminiScadenza());
                    pes.setScriptScadenza(dto.getScriptScadenza());
                    pes.setFlgVisualizzaScadenza(dto.getFlgVisualizzaScadenza());
                    inserisciScadenza(pes);
                }
            }
        }

        if (!scadenzeDaEliminare.isEmpty()) {
            for (String scadenza : scadenzeDaEliminare) {
                ProcessiEventiScadenze pes = processiService.findScadenzaByIdEventoIdAnaScadenza(evento.getIdEvento(), scadenza);
                if (pes != null) {
                    rimuoviProcessoEventoScadenza(pes);
                } else {
                    //E' una scadenza nuova
                    for (ScadenzaEventoDTO dto : scadenzeDto) {
                        if (dto.getIdAnaScadenza().equals(scadenza)) {
                            ProcessiEventiScadenze p = new ProcessiEventiScadenze();
                            ProcessiEventiScadenzePK key = new ProcessiEventiScadenzePK();
                            key.setIdEvento(evento.getIdEvento());
                            key.setIdAnaScadenza(dto.getIdAnaScadenza());
                            p.setProcessiEventiScadenzePK(key);
                            LkStatiScadenze statoScadenza = lookupService.findStatoScadenza(dto.getIdStatoScadenza());
                            p.setIdStatoScadenza(statoScadenza);
                            p.setTerminiScadenza(dto.getTerminiScadenza());
                            p.setScriptScadenza(dto.getScriptScadenza());
                            p.setFlgVisualizzaScadenza(dto.getFlgVisualizzaScadenza());
                            inserisciScadenza(p);
                        }
                    }
                }
            }
        }
    }

    private ProcessiEventi serializeProcessoEvento(ProcessoEventoDTO evento, ProcessiEventi p) throws Exception {
        p.setIdEvento(evento.getIdEvento());
        if (evento.getIdProcesso() != null) {
            Processi processo = processiDao.findProcessiById(evento.getIdProcesso());
            p.setIdProcesso(processo);
        }
        p.setCodEvento(evento.getCodiceEvento());
        p.setDesEvento(evento.getDescrizioneEvento());
        LkStatoPratica statoPratica = lookupDao.findLkStatoPraticaByIdStatoPratica(evento.getStatoPost());
        p.setStatoPost(statoPratica);
        if (!Utils.e(evento.getTipoMittente())) {
            LkTipiAttore mittente = lookupDao.findLkTipiAttoreById(evento.getTipoMittente());
            p.setIdTipoMittente(mittente);
        }else{
        	//Modifica aggiunta il 24/05/2016
        	 p.setIdTipoMittente(null);
        }
        if (!Utils.e(evento.getTipoDestinatario())) {
            LkTipiAttore destinatario = lookupDao.findLkTipiAttoreById(evento.getTipoDestinatario());
            p.setIdTipoDestinatario(destinatario);
        }else{
        	//Modifica aggiunta il 24/05/2016
            p.setIdTipoDestinatario(null);
        }
        p.setScriptScadenzaEvento(evento.getScriptEvento().trim().equals("") ? null : evento.getScriptEvento());
        p.setVerso(evento.getVerso().charAt(0));
        p.setFlgPortale(evento.getFlgPortale());
        p.setFlgMail(evento.getFlgMail());
        p.setFlgAllMail(evento.getFlgAllegatiEmail());
        p.setFlgProtocollazione(evento.getFlgProtocollazione());
        p.setFlgRicevuta(evento.getFlgRicevuta());
        p.setFlgDestinatari(evento.getFlgDestinatari());
        p.setFlgFirmato(evento.getFlgFirmato());
        p.setFlgApriSottopratica(evento.getFlgApriSottoPratica());
        p.setFlgDestinatariSoloEnti(evento.getFlgDestinatariSoloEnti());
        p.setFlgVisualizzaProcedimenti(evento.getFlgVisualizzaProcedimenti());
        if (evento.getIdProcedimentoRiferimento() != null) {
            Procedimenti procedimentoRiferimento = procedimentiDao.findProcedimentoByIdProc(evento.getIdProcedimentoRiferimento());
            p.setIdProcedimentoRiferimento(procedimentoRiferimento);
        }
        p.setIdScriptEvento(evento.getScriptEvento().trim().equals("") ? null : evento.getScriptEvento());
        p.setIdScriptProtocollo(evento.getScriptProtocollo().trim().equals("") ? null : evento.getScriptProtocollo());
        p.setMaxDestinatari(evento.getMaxDestinatari());
        if (!Utils.e(evento.getOggettoMail())) {
            byte[] oggetto = Base64.encodeBase64(evento.getOggettoMail().getBytes());
            p.setOggettoEmail(new String(oggetto));
        }
        if (!Utils.e(evento.getCorpoMail())) {
            byte[] corpo = Base64.encodeBase64(evento.getCorpoMail().getBytes());
            p.setCorpoEmail(new String(corpo));
        }
        p.setFunzioneApplicativa(evento.getFunzioneApplicativa().trim().equals("") ? null : evento.getFunzioneApplicativa());
        p.setFlgAutomatico(evento.getFlgAutomatico());
        p.setForzaChiusuraScadenze(evento.getForzaChiusuraScadenze());
        return p;
    }

    private void aggiornaProcessoEventoAnagrafica(ProcessoEventoDTO evento, ProcessiEventi p) throws Exception {
        if (p.getProcessiEventiAnagraficaList() != null) {
            for (ProcessiEventiAnagrafica pea : p.getProcessiEventiAnagraficaList()) {
                processiDao.delete(pea);
            }
        }
        p.setProcessiEventiAnagraficaList(null);
        usefulService.flush();
        if (evento.getAnagraficaDestinatari() != null) {
            for (ProcessoEventoAnagraficaDTO aDTO : evento.getAnagraficaDestinatari()) {
                ProcessiEventiAnagraficaPK pk = new ProcessiEventiAnagraficaPK();
                pk.setIdAnagrafica(aDTO.getIdAnagrafica());
                pk.setIdEvento(p.getIdEvento());
                ProcessiEventiAnagrafica pea = new ProcessiEventiAnagrafica();
                pea.setProcessiEventiAnagraficaPK(pk);
                processiDao.insert(pea);
            }
        }
    }

    private void aggiornaProcessoEventoEnte(ProcessoEventoDTO evento, ProcessiEventi p) throws Exception {
        if (p.getProcessiEventiEntiList() != null) {
            for (ProcessiEventiEnti pee : p.getProcessiEventiEntiList()) {
                processiDao.delete(pee);
            }
        }
        p.setProcessiEventiEntiList(null);
        usefulService.flush();
        if (evento.getEnteDestinatari() != null) {
            for (ProcessoEventoEnteDTO aDTO : evento.getEnteDestinatari()) {
                ProcessiEventiEntiPK pk = new ProcessiEventiEntiPK();
                pk.setIdEnte(aDTO.getIdEnte());
                pk.setIdEvento(aDTO.getIdEvento());
                ProcessiEventiEnti pee = new ProcessiEventiEnti();
                pee.setProcessiEventiEntiPK(pk);
                processiDao.insert(pee);
            }
        }
    }
}
