/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.comunicazionerea.action;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.constants.SessionConstants;
import it.wego.cross.entity.Anagrafica;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.events.comunicazionerea.bean.ComunicazioneReaDTO;
import it.wego.cross.events.comunicazionerea.dao.ComunicazioneReaDao;
import it.wego.cross.events.comunicazionerea.entity.RiIntegrazioneRea;
import it.wego.cross.events.comunicazionerea.service.ComunicazioneReaService;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.service.AnagraficheService;
import it.wego.cross.service.ConfigurationService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Utils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GabrieleM
 */
@Component
public class ComunicazioneReaAction {
 
    @Autowired
    ComunicazioneReaDao comunicazioneReaDao;
    @Autowired
    PraticheService praticheService;
    @Autowired
    UtentiService utentiService;
    @Autowired
    WorkFlowService workFlowService;
    @Autowired
    ComunicazioneReaService comunicazioneReaService;
    @Autowired
    AnagraficheService anagraficheService;
    @Autowired
    ConfigurationService configurationService;
    
        @Transactional(rollbackFor = Exception.class)
    public void comunicazioneReaSubmitAction(ComunicazioneReaDTO istruttoria, Integer idUser, String tipologiaComunicazione) throws Exception {
        Pratica pratica = praticheService.getPratica(istruttoria.getIdPratica());
        Utente user = utentiService.findUtenteByIdUtente(idUser);
        ProcessiEventi processoEvento = workFlowService.findProcessiEventiById(istruttoria.getIdEvento());

        RiIntegrazioneRea integrazioneReaEntity = comunicazioneReaService.getIntegrazioneRea(pratica);
        if (integrazioneReaEntity == null) {
            integrazioneReaEntity = new RiIntegrazioneRea();
        }

        Anagrafica dichiarantePratica = anagraficheService.findAnagraficaById(istruttoria.getDichiarantePratica());
        Anagrafica aziendaRiferimento = anagraficheService.findAnagraficaById(istruttoria.getAziendaRiferimento());
        Anagrafica rappresentanteAzienda = anagraficheService.findAnagraficaById(istruttoria.getRappresentanteAzienda());

        integrazioneReaEntity.setIdPratica(pratica);
        integrazioneReaEntity.setTipoProcedimento(istruttoria.getTipologiaProcedimento());
        integrazioneReaEntity.setTipoIntervento(istruttoria.getTipologiaIntervento());

        integrazioneReaEntity.setIdAnagraficaDichiarante(dichiarantePratica);
        integrazioneReaEntity.setQualificaDichiarantePratica(istruttoria.getQualificaDichiarantePratica());

        integrazioneReaEntity.setIdAziendaRiferimento(aziendaRiferimento);
        integrazioneReaEntity.setFormaGiuridicaImpresa(istruttoria.getFormaGiuridicaAzienda());

        integrazioneReaEntity.setIdAnagraficaLegaleRapp(rappresentanteAzienda);
        integrazioneReaEntity.setCaricaLegaleRapp(istruttoria.getCaricaRappresentanteAzienda());

      merge(integrazioneReaEntity);
        EventoBean eventoBean = new EventoBean();
        String conf = configurationService.getCachedConfiguration(SessionConstants.REGISTRO_IMPRESE_COMUNICAZIONE_SUAP_URL, pratica.getIdProcEnte().getIdEnte().getIdEnte(), pratica.getIdComune().getIdComune());
        if (!Utils.e(conf)) {
            List<Allegato> allegatiComunicazioneRea = comunicazioneReaService.comunicazioneArrivoPraticaSuap(pratica, istruttoria, user, tipologiaComunicazione);
            eventoBean.setAllegati(allegatiComunicazioneRea);
        }
        eventoBean.setIdPratica(pratica.getIdPratica());
        eventoBean.setIdEventoProcesso(processoEvento.getIdEvento());
        eventoBean.setIdUtente(user.getIdUtente());
        workFlowService.gestisciProcessoEvento(eventoBean);
    }

        //inutilizzato anche prima
    private void insert(RiIntegrazioneRea integrazioneReaBean) throws Exception {
        comunicazioneReaDao.insert(integrazioneReaBean);
    }


    private void merge(RiIntegrazioneRea integrazioneReaBean) throws Exception {
        comunicazioneReaDao.merge(integrazioneReaBean);
    }
    
}
