/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.fromcommerciogenova;

import it.wego.cross.actions.EventiAction;
import it.wego.cross.beans.EventoBean;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcessiEventi;
import it.wego.cross.events.AbstractEvent;
import it.wego.cross.service.WorkFlowService;
import it.wego.cross.utils.Log;
import it.wego.cross.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author piergiorgio
 */
@Repository("ricezioneEventoComunicazioneCommercioGenova")
public class RicezioneEventoComunicazioneCommercioGenova extends AbstractEvent {

    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private EventiAction eventiAction;

    @Override
    public void execute(EventoBean eb) throws Exception {
        Log.APP.info("Inizio metodo RicezioneEventoCommercioGenova");
        // inserisci lo stesso evento sulla pratica padre
        Pratica pratica = praticheService.getPratica(eb.getIdPratica());
        Pratica praticaPadre = pratica.getIdPraticaPadre();
        // determino il codice evento         
        PraticheEventi eventoPratica = praticheService.getPraticaEvento(eb.getIdEventoPratica());
        String codEvento = eventoPratica.getIdEvento().getCodEvento();
        // cerco lo stesso codice evento sul processo della pratica padre

        ProcessiEventi pe = workFlowService.findProcessiEventiByIdProcessoCodEvento(praticaPadre.getIdProcesso(), codEvento);

        if (pe != null) {
            EventoBean eventoBean = new EventoBean();
            eventoBean.setIdPratica(praticaPadre.getIdPratica());
            eventoBean.setIdEventoProcesso(pe.getIdEvento());
            eventoBean.setIdUtente(null);
            eventoBean.setNote(pe.getDesEvento() + " - " + pratica.getIdProcEnte().getIdProc().getProcedimentiTestiList().get(0).getDesProc());
            if (!Utils.e(eb.getNote())) {
                eventoBean.setNote(eventoBean.getNote() + " - " + eb.getNote());
            }
            // esiste sulla pratica padre l'evento corrispondente e quindi lo inserisco
            eventiAction.gestisciProcessoEvento(eventoBean);
            
            eb.getMessages().put("EVENTO_PRATICA_PADRE", eventoBean.getIdEventoPratica());

        } else {
            throw new Exception("Manca l'evento " + codEvento + " sul processo " + praticaPadre.getIdProcesso().getCodProcesso());
        }

        Log.APP.info("Fine metodo ChiusuraCommercio Genova");
    }

}
