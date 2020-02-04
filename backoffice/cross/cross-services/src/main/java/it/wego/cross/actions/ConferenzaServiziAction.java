/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.dao.UtentiDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import it.wego.cross.dto.UtenteDTO;
import it.wego.cross.dto.dozer.PraticaOrganoCollegialeDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.service.OrganiCollegialiService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.WorkFlowService;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author piergiorgio
 */
@Component
public class ConferenzaServiziAction {

    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private UtentiDao utentiDao;
    @Autowired
    OrganiCollegialiService organiCollegialiService;


    @Transactional(rollbackFor = Exception.class)
    public EventoBean gestisciEvento(PraticaOrganoCollegialeDTO praticaOrganoCollegialeDTO, UtenteDTO utenteConnesso) throws Exception {
        Pratica pratica = praticheService.getPratica(praticaOrganoCollegialeDTO.getIdPratica());
        ProcessiEventi processoEvento = praticheService.findProcessiEventi(praticaOrganoCollegialeDTO.getIdEvento());
        EventoBean eb = new EventoBean();
        Utente utente = utentiDao.findUtenteByIdUtente(utenteConnesso.getIdUtente());
        eb.setIdPratica(pratica.getIdPratica());
        eb.setIdEventoProcesso(processoEvento.getIdEvento());
        eb.setIdUtente(utente.getIdUtente());
        eb.setVisibilitaCross(Boolean.TRUE);
        eb.setVisibilitaUtente(Boolean.TRUE);

        eb.getMessages().put("idOrganiCollegiali", String.valueOf(praticaOrganoCollegialeDTO.getIdOrganiCollegiali()));
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
        eb.getMessages().put("dataRichiesta", dt.format(praticaOrganoCollegialeDTO.getDataRichiesta()));
        eb.getMessages().put("codiceStatoPraticaOrganiCollegiali", praticaOrganoCollegialeDTO.getCodiceStatoPraticaOrganiCollegiali());
        
        workFlowService.gestisciProcessoEvento(eb);
        return eb;
    }
}
