/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.serializer;

import it.wego.cross.dto.MessaggioDTO;
import it.wego.cross.entity.Messaggio;

/**
 *
 * @author giuseppe
 */
public class MessaggiSerializer {

    public static MessaggioDTO serialize(Messaggio messaggio) {
        MessaggioDTO dto = new MessaggioDTO();
        dto.setCognomeMittente(messaggio.getIdUtenteMittente().getCognome());
        dto.setIdDestinatario(messaggio.getIdUtenteDestinatario().getIdUtente());
        dto.setIdMessaggio(messaggio.getIdMessaggio());
        dto.setIdMittente(messaggio.getIdUtenteMittente().getIdUtente());
        dto.setNomeMittente(messaggio.getIdUtenteMittente().getNome());
        dto.setTesto(messaggio.getTesto());
        dto.setTimestamp(messaggio.getDataMessaggio());
        return dto;
    }
}
