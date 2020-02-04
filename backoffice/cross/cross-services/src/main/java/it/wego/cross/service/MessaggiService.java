/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.entity.Messaggio;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public interface MessaggiService {

    public Long countMessaggiDaLeggere(Utente connectedUser);

    public void salvaMessaggio(Messaggio messaggio) throws Exception;

    public List<Messaggio> findMessaggiPratica(Pratica pratica);
}
