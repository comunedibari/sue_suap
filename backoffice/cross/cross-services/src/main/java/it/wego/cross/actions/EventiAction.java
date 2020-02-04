package it.wego.cross.actions;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.dto.ComunicazioneDTO;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.plugins.commons.beans.Allegato;
import it.wego.cross.serializer.AllegatiSerializer;
import it.wego.cross.service.PraticheService;
import it.wego.cross.service.UtentiService;
import it.wego.cross.service.WorkFlowService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gabriele
 */
@Component
public class EventiAction {

    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    PraticheService praticheService;
    @Autowired
    UtentiService utentiService;

    @Transactional(rollbackFor = Exception.class)
    public void gestisciProcessoEvento(EventoBean eventoBean) throws Exception {
        workFlowService.gestisciProcessoEvento(eventoBean);
    }

    @Transactional
    public void salvaEvento(ComunicazioneDTO comunicazione) throws Exception {
        EventoBean eb = new EventoBean();
//        Pratica pratica = praticheService.getPratica(comunicazione.getIdPratica());
//        ProcessiEventi pevento = workFlowService.findProcessiEventiById(comunicazione.getEvento().getIdEvento());
//        Utente utente = utentiService.findUtenteByIdUtente(comunicazione.getUtente().getIdUtente());
        List<Allegato> listaAllegati = new ArrayList<Allegato>();
        for (AllegatoDTO allegatoDTO : comunicazione.getAllegatiManuali()) {
            Allegato allegato = AllegatiSerializer.serializePlugin(allegatoDTO);
            allegato.setProtocolla(Boolean.FALSE);
            listaAllegati.add(allegato);
        }
        eb.setIdPratica(comunicazione.getIdPratica());
        eb.setIdEventoProcesso(comunicazione.getEvento().getIdEvento());
        eb.setNumeroProtocollo(null);
        eb.setDataProtocollo(null);
        eb.setDataEvento(new Date());
        eb.setIdUtente(comunicazione.getUtente().getIdUtente());
        eb.setVisibilitaCross(Boolean.FALSE);
        eb.setVisibilitaUtente(Boolean.FALSE);
        eb.setVisibilitaUtente(Boolean.FALSE);
        eb.setAllegati(listaAllegati);
        workFlowService.gestisciProcessoEvento(eb);
    }

    @Transactional
    public PraticheEventi inserisciEventoFiglia(EventoBean eb, ProcessiEventi ev, Pratica pratica) throws Exception {
        ComunicazioneBean cbRice = new ComunicazioneBean();
        cbRice.setIdPratica(pratica.getIdPratica());
        cbRice.setIdEventoProcesso(ev.getIdEvento());
        cbRice.setIdUtente(eb.getIdUtente());
        //cbRice.setAllegati(cb.getAllegati());
        cbRice.setVisibilitaCross(Boolean.TRUE);
        cbRice.setVisibilitaUtente(Boolean.FALSE);
        workFlowService.gestisciProcessoEvento(cbRice);
        return praticheService.getPraticaEvento(cbRice.getIdEventoPratica());
    }

}
