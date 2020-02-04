package it.wego.cross.events.aggiungiallegati;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.entity.Allegati;
import it.wego.cross.entity.Pratica;
import it.wego.cross.events.AbstractEvent;
import it.wego.cross.plugins.documenti.GestioneAllegati;
import it.wego.cross.service.AllegatiService;
import it.wego.cross.service.UsefulService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CS
 */
@Repository("aggiungiallegati")
public class AggiungiAllegatiEvent extends AbstractEvent {

    @Autowired
    AllegatiService allegatiService;
    @Autowired
    UsefulService usefulService;

    @Override
    public void execute(EventoBean eb) throws Exception {
        Pratica pratica = praticheService.getPratica(eb.getIdPratica());
        Integer idComune = null;
        if (pratica.getIdComune() != null) {
            idComune = pratica.getIdComune().getIdComune();
        }
        GestioneAllegati gp = pluginService.getGestioneAllegati(pratica.getIdProcEnte().getIdEnte().getIdEnte(), idComune);
        List<Allegati> allegatiEntity = new ArrayList<Allegati>();
        for (Integer idAllegato : eb.getIdAllegati()){
            Allegati allegato = allegatiService.findAllegatoById(idAllegato);
            allegatiEntity.add(allegato);
        }
        gp.uploadFile(pratica, allegatiEntity);
        eb.setNumeroProtocollo(null);
        eb.setDataProtocollo(null);
    }
}
