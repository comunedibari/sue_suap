/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.printlog;

import com.google.common.base.Strings;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.dto.AllegatoDTO;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.events.AbstractEvent;
import it.wego.cross.service.EventiService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Giuseppe
 */
@Repository("printlogevent")
public class PrintLogEvent extends AbstractEvent {

    @Autowired
    private EventiService eventiService;

    @Override
    public void execute(EventoBean eb) throws Exception {
        List<AllegatoDTO> allegatiPratica = praticheService.getAllegatiPratica(eb.getIdPratica());
        ProcessiEventi step = eventiService.findProcessiEventiById(eb.getIdEventoProcesso());
        
        for (AllegatoDTO allegato : allegatiPratica) {
            if (!Strings.isNullOrEmpty(allegato.getPathFile())) {

            } else if (allegato.getFileContent() != null) {

            }
        }
    }

}
