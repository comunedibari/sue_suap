/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.actions;

import it.wego.cross.entity.Pratica;
import it.wego.cross.service.PraticheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Giuseppe
 */
@Component
public class ConsoleAction {

    @Autowired
    private PraticheService praticheService;

    @Transactional(rollbackFor = Exception.class)
    public void cancellaPratica(String identificativoPratica) throws Exception {
        praticheService.cancellaPratica(identificativoPratica);
    }

    @Transactional(rollbackFor = Exception.class)
    public void aggiornaPratica(Pratica pratica) throws Exception {
        praticheService.aggiornaPratica(pratica);
    }
}
