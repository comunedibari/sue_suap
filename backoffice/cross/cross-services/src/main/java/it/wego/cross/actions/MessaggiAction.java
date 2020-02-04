/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.dto.MessaggioDTO;
import it.wego.cross.entity.Messaggio;
import it.wego.cross.entity.Pratica;
import it.wego.cross.serializer.MessaggiSerializer;
import it.wego.cross.service.MessaggiService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author giuseppe
 */
@Component
public class MessaggiAction {

    @Autowired
    private MessaggiService messaggiService;

    @Transactional(rollbackFor = Exception.class)
    public void salvaMessaggio(Messaggio messaggio) throws Exception {
        messaggiService.salvaMessaggio(messaggio);
    }

    public List<MessaggioDTO> findConnectedMessages(Pratica pratica) {
        List<Messaggio> messaggi = messaggiService.findMessaggiPratica(pratica);
        List<MessaggioDTO> dtos = new ArrayList<MessaggioDTO>();
        if (messaggi != null && !messaggi.isEmpty()) {
            for (Messaggio messaggio : messaggi) {
                MessaggioDTO dto = MessaggiSerializer.serialize(messaggio);
                dtos.add(dto);
            }
        }
        return dtos;
    }
}
