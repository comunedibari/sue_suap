package it.wego.cross.actions;

import it.wego.cross.events.comunicazione.bean.ComunicazioneBean;
import it.wego.cross.service.WorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CS
 */
@Component
public class PraticaManualeAction {

    @Autowired
    private WorkFlowService workflowService;

    @Transactional
    public void gestisciProcessoEvento(ComunicazioneBean cb) throws Exception {
        workflowService.gestisciProcessoEvento(cb);
    }
}
