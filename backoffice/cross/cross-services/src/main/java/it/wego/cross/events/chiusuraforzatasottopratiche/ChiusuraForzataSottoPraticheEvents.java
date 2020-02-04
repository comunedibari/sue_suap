/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.chiusuraforzatasottopratiche;

import it.wego.cross.actions.EventiAction;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.events.AbstractEvent;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author piergiorgio effettua la chiusura di tutte le sottopratiche
 * eventualmente aperte ricerca tutte le sotopratiche ed attiva l'evento
 */
@Repository("chiusuraforzatasottopratiche")
public class ChiusuraForzataSottoPraticheEvents extends AbstractEvent {
    @Autowired
    private EventiAction eventiAction;

    @Override
    public void execute(EventoBean eb) throws Exception {
        Pratica praticaPadre = praticheService.getPratica(eb.getIdPratica());

        // ricerca le sottopratiche collegate alla pratica, scende solo di un livello.
        List<Pratica> listPraticheFiglie = praticaDao.findAllPraticheFiglie(praticaPadre);
        for (Pratica pratica : listPraticheFiglie) {
            // ricerco se il processo della pratica figlia prevede l'evento di chiusura forzata
            ProcessiEventi ev = processiDao.findProcessiEventiByCodEventoIdProcesso(AnaTipiEvento.EVENTO_CHIUSURA_FORZATA_DA_PRATICA_PADRE, pratica.getIdProcEnte().getIdProcesso().getIdProcesso());
            if (ev != null) {
                eventiAction.inserisciEventoFiglia(eb, ev, pratica);
            }
        }
    }
}
