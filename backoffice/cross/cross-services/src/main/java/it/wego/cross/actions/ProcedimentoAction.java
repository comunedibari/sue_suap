/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.constants.Constants;
import it.wego.cross.dao.EntiDao;
import it.wego.cross.dao.PraticaDao;
import it.wego.cross.dao.ProcedimentiDao;
import it.wego.cross.dao.ProcessiDao;
import it.wego.cross.dao.TemplateDao;
import it.wego.cross.dto.ProcedimentoDTO;
import it.wego.cross.entity.Enti;
import it.wego.cross.entity.EventiTemplate;
import it.wego.cross.entity.Lingue;
import it.wego.cross.entity.PraticaProcedimenti;
import it.wego.cross.entity.Procedimenti;
import it.wego.cross.entity.ProcedimentiTesti;
import it.wego.cross.entity.ProcedimentiTestiPK;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.serializer.ProcedimentiSerializer;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.ProcedimentiService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author giuseppe
 */
@Component
public class ProcedimentoAction {

    @Autowired
    ProcedimentiDao procedimentiDao;
    @Autowired
    ProcessiDao processiDao;
    @Autowired
    TemplateDao templateDao;
    @Autowired
    PraticaDao praticaDao;
    @Autowired
    EntiDao entiDao;
    @Autowired
    ConfigurationService configurationService;

    @Autowired
    private ProcedimentiService procedimentiService;

    private String verificaEntiCollegati(Integer idProcedimento) throws Exception {
        List<Enti> procedimentiEnti = procedimentiDao.findEntiFromProcedimento(idProcedimento);
        String descrizioneEntiCollegati = null;
        if (!procedimentiEnti.isEmpty()) {
            descrizioneEntiCollegati = "Non è stato possibile eliminare il procedimento poichè esso è in relazione con i seguenti enti: ";
        }
        for (Enti procedimentoEnte : procedimentiEnti) {
            descrizioneEntiCollegati += procedimentoEnte.getDescrizione() + ",";
        }
        if ((descrizioneEntiCollegati != null) && (!descrizioneEntiCollegati.isEmpty())) {
            descrizioneEntiCollegati = descrizioneEntiCollegati.substring(0, descrizioneEntiCollegati.length() - 1);
        }
        return descrizioneEntiCollegati;
    }

    private String verificaEventiTemplateCollegati(Integer idProcedimento) throws Exception {
        List<EventiTemplate> procedimentiEnti = templateDao.getEventiTemplatesPerProcedimento(idProcedimento);
        String descrizioneEventiTemplate = null;
        if (!procedimentiEnti.isEmpty()) {
            descrizioneEventiTemplate = "Non è stato possibile eliminare il procedimento poichè esso è in relazione con i seguenti eventi-templates: ";
        }
        for (EventiTemplate eventoTemplate : procedimentiEnti) {
            descrizioneEventiTemplate += eventoTemplate.getIdEvento().getDesEvento() + "-" + eventoTemplate.getIdTemplate().getDescrizione() + ",";
        }
        if ((descrizioneEventiTemplate != null) && (!descrizioneEventiTemplate.isEmpty())) {
            descrizioneEventiTemplate = descrizioneEventiTemplate.substring(0, descrizioneEventiTemplate.length() - 1);
        }
        return descrizioneEventiTemplate;
    }

    private String verificaPraticheCollegate(Integer idProcedimento) throws Exception {
        List<PraticaProcedimenti> praticaProcedimenti = praticaDao.findPraticheProcedimentiByIdProcedimento(idProcedimento);
        String descrizionePratica = null;
        if (!praticaProcedimenti.isEmpty()) {
            descrizionePratica = "Non è stato possibile eliminare il procedimento poichè esso è in relazione con le seguenti pratiche: ";
        }
        for (PraticaProcedimenti praticaDescrizione : praticaProcedimenti) {
            descrizionePratica += praticaDescrizione.getPratica().getIdentificativoPratica() + ",";
        }
        if ((descrizionePratica != null) && (!descrizionePratica.isEmpty())) {
            descrizionePratica = descrizionePratica.substring(0, descrizionePratica.length() - 1);
        }
        return descrizionePratica;
    }

    private String verificaProcessiEventiCollegati(Integer idProcedimento) throws Exception {
        List<ProcessiEventi> processiEventi = processiDao.findProcessiEventiByIdProcedimento(idProcedimento);
        String descrizioniProcessiEventi = null;
        if (!processiEventi.isEmpty()) {
            descrizioniProcessiEventi = "Non è stato possibile eliminare il procedimento poichè esso è in relazione con le seguenti processi-eventi: ";
        }
        for (ProcessiEventi processoEvento : processiEventi) {
            descrizioniProcessiEventi += processoEvento.getIdProcesso().getDesProcesso() + "-" + processoEvento.getDesEvento() + ",";
        }
        if ((descrizioniProcessiEventi != null) && (!descrizioniProcessiEventi.isEmpty())) {
            descrizioniProcessiEventi = descrizioniProcessiEventi.substring(0, descrizioniProcessiEventi.length() - 1);
        }
        return descrizioniProcessiEventi;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<String> elimina(Integer idProcedimento) throws Exception {
        List<String> elementiCollegati = new ArrayList<String>();
        elementiCollegati.add(verificaEntiCollegati(idProcedimento));
        elementiCollegati.add(verificaEventiTemplateCollegati(idProcedimento));
        elementiCollegati.add(verificaPraticheCollegate(idProcedimento));
        elementiCollegati.add(verificaProcessiEventiCollegati(idProcedimento));
        elementiCollegati.removeAll(Collections.singleton(null));
        if (elementiCollegati.isEmpty()) {
            Procedimenti procedimento = procedimentiService.findProcedimentoByIdProc(idProcedimento);
            eliminaProcTextList(procedimento.getProcedimentiTestiList());
            eliminaProc(procedimento);
        }
        return elementiCollegati;
    }

    private void eliminaProc(Procedimenti procedimento) {
        procedimentiDao.delete(procedimento);
    }

    private void eliminaProcTextList(List<ProcedimentiTesti> testi) throws Exception {
        if (testi != null && !testi.isEmpty()) {
            for (ProcedimentiTesti testo : testi) {
                procedimentiDao.delete(testo);
            }
        }
    }

    @Transactional
    public ProcedimentoDTO inserisci(ProcedimentoDTO procedimentoDTO) throws Exception {
        Procedimenti p = ProcedimentiSerializer.serialize(procedimentoDTO);
        procedimentiDao.inserisciProcedimento(p);
        procedimentiDao.flush();
        //TODO: forzato ad italiano
        Lingue lingua = procedimentiDao.findLinguaByCodLang(Constants.DEFAULT_LANGAUGE);
        ProcedimentiTesti pr = new ProcedimentiTesti();
        ProcedimentiTestiPK procedimentiTestiPK = new ProcedimentiTestiPK();
        procedimentiTestiPK.setIdLang(lingua.getIdLang());
        procedimentiTestiPK.setIdProc(p.getIdProc());
        pr.setDesProc(procedimentoDTO.getDesProcedimento());
        pr.setProcedimentiTestiPK(procedimentiTestiPK);
        procedimentiDao.inserisciProcedimentiTesti(pr);
        return procedimentoDTO;
    }

    @Transactional
    public ProcedimentoDTO aggiorna(ProcedimentoDTO procedimentoDTO) throws Exception {
        Procedimenti p = procedimentiDao.findProcedimentoByIdProc(procedimentoDTO.getIdProcedimento());
        ProcedimentiSerializer.serialize(procedimentoDTO, p);
        //TODO: forzato ad italiano
        Lingue lingua = procedimentiDao.findLinguaByCodLang(Constants.DEFAULT_LANGAUGE);
        ProcedimentiTesti pr = procedimentiDao.findProcedimentiTestiByPK(procedimentoDTO.getIdProcedimento(), lingua.getIdLang());
        pr.setDesProc(procedimentoDTO.getDesProcedimento());
        procedimentiDao.aggiornaProcedimentiTesti(pr);
        procedimentiDao.aggiornaProcedimento(p);
        return procedimentoDTO;
    }
}
