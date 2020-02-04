/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.dao.MessaggiDao;
import it.wego.cross.entity.Messaggio;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.Utente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuseppe
 */
@Service
public class MessaggiServiceImpl implements MessaggiService {

    @Autowired
    private MessaggiDao messaggiDao;
    
    @Override
    public Long countMessaggiDaLeggere(Utente connectedUser) {
        Long messaggiDaLeggere = messaggiDao.countMessaggiDaLeggere(connectedUser);
        return messaggiDaLeggere;
    }

    @Override
    public void salvaMessaggio(Messaggio messaggio) throws Exception {
        messaggiDao.insert(messaggio);
    }

    @Override
    public List<Messaggio> findMessaggiPratica(Pratica pratica) {
        List<Messaggio> messaggi = messaggiDao.findMessaggiByPratica(pratica);
        return messaggi;
    }
}
