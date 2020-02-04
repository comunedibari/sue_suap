package it.wego.cross.events.conferenzaservizi;

import it.wego.cross.actions.PraticheAction;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.dao.OrganiCollegialiDao;
import it.wego.cross.dao.PraticaOrganiCollegialiDao;
import it.wego.cross.dto.dozer.PraticaOrganoCollegialeDTO;
import it.wego.cross.entity.LkStatiPraticaOrganiCollegiali;
import it.wego.cross.entity.OrganiCollegiali;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticaOrganiCollegiali;
import it.wego.cross.events.AbstractEvent;
import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.service.UsefulService;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("attivaConferenzaServizi")
public class AttivaConferenzaServiziEvent extends AbstractEvent {

    @Autowired
    UsefulService usefulService;
    @Autowired
    private OrganiCollegialiDao organiCollegialiDao;
    @Autowired
    private PraticaOrganiCollegialiDao praticaOrganiCollegialiDao;
    @Autowired
    protected PraticheAction praticheAction;

    @Override
    public void execute(EventoBean eb) throws Exception {
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        Pratica pratica = praticheService.getPratica(eb.getIdPratica());
        OrganiCollegiali organiCollegiali = organiCollegialiDao.findById(Integer.valueOf((String) eb.getMessages().get("idOrganiCollegiali")));
        PraticaOrganiCollegiali praticaOrganiCollegiali = new PraticaOrganiCollegiali();
        praticaOrganiCollegiali.setIdOrganiCollegiali(organiCollegiali);
        praticaOrganiCollegiali.setIdPratica(pratica);
        praticaOrganiCollegiali.setDataRichiesta(dt.parse((String) eb.getMessages().get("dataRichiesta")));
        LkStatiPraticaOrganiCollegiali lk = lookupDao.findStatiPraticaOrganiCollegialiByCodice((String) eb.getMessages().get("codiceStatoPraticaOrganiCollegiali"));
        praticaOrganiCollegiali.setIdStatoPraticaOrganiCollegiali(lk);
        ComunicazioneBean cb = new ComunicazioneBean(eb);
        praticheAction.updateScadenzePratica(pratica, cb);
        praticheAction.updateStatoPratica(pratica, cb);
        praticaOrganiCollegialiDao.insert(praticaOrganiCollegiali);
    }
}
