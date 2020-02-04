/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.dao.AllegatiDao;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.EventoDTO;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.PraticheEventiAllegati;
import it.wego.cross.entity.PraticheEventiAllegatiPK;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.entity.Utente;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.service.PluginService;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.ProcessiService;
import it.wego.cross.service.UsefulService;
import it.wego.cross.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GabrieleM
 */
@Component
public class AllegatiAction {

    @Autowired
    private AllegatiDao allegatiDao;
    @Autowired
    private PluginService pluginService;
    @Autowired
    private UsefulService usefulService;
    @Autowired
    private PraticheService praticheService;
    @Autowired
    private ProcessiService processiService;

    @Transactional
    public void inserisciAllegato(Allegati allegato) throws Exception {
        allegatiDao.insertAllegato(allegato);
    }
    @Transactional
    public void salvaAllegato(Allegati allegato) throws Exception {
        allegatiDao.mergeAllegato(allegato);
    }
    @Transactional(rollbackFor = Exception.class)
    public void salvaAllegati(EventoDTO eventoDaGestire, Utente utenteConnesso, Pratica pratica, List<AllegatoDTO> allegatiDto) throws Exception {
        Integer idComune = pratica.getIdComune() != null ? pratica.getIdComune().getIdComune() : null;
        List<Allegati> allegati = getAllegati(allegatiDto);
        GestioneAllegati ga = pluginService.getGestioneAllegati(pratica.getIdProcEnte().getIdEnte().getIdEnte(), idComune);
        ga.uploadFile(pratica, allegati);
        ProcessiEventi processoEvento = processiService.findProcessiEventiByIdEvento(eventoDaGestire.getIdEvento());
        PraticheEventi evento = new PraticheEventi();
        evento.setIdPratica(pratica);
        String desEvento = eventoDaGestire.getDescrizione();
        ProcessiEventi evt = processiService.requireEventoDiSistema(processoEvento.getCodEvento(), desEvento, pratica.getIdProcesso());
        evento.setIdEvento(evt);
        evento.setDataEvento(new Date());
        evento.setIdUtente(utenteConnesso);
        evento.setVisibilitaCross("S");
        evento.setVisibilitaUtente("N");
        evento.setDescrizioneEvento(desEvento);
        evento.setNote("Caricamento allegati su sitema documentale");
        praticheService.saveProcessoEvento(evento);
        usefulService.flush();
        for (Allegati allegato : allegati){
            PraticheEventiAllegati pea = new PraticheEventiAllegati();
            PraticheEventiAllegatiPK key = new PraticheEventiAllegatiPK();
            key.setIdAllegato(allegato.getId());
            key.setIdPraticaEvento(evento.getIdPraticaEvento());
            pea.setPraticheEventiAllegatiPK(key);
            pea.setFlgIsPrincipale("N");
            pea.setFlgIsToSend("N");
            allegatiDao.inserPraticheEventiAllegato(pea);
        }
    }

    private List<Allegati> getAllegati(List<AllegatoDTO> allegatiDto) throws Exception {
        List<Allegati> allegati = new ArrayList<Allegati>();
        if (allegatiDto != null && !allegatiDto.isEmpty()) {
            for (AllegatoDTO dto : allegatiDto) {
                Allegati allegato = new Allegati();
                allegato.setDescrizione(dto.getDescrizione());
                allegato.setFile(dto.getFile().getBytes());
                allegato.setNomeFile(Utils.normalizeFileName(dto.getNomeFile()));
                allegato.setTipoFile(dto.getFile().getContentType());
                allegatiDao.insertAllegato(allegato);
                usefulService.flush();
                allegati.add(allegato);
            }
        }
        return allegati;
    }

    public void salvaAllegati(EventoDTO eventoDaGestire, Pratica pratica, List<AllegatoDTO> allegatiManuali) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
