/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.events.invioBoCommercioGenova;

import it.wego.cross.beans.EventoBean;
import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.dao.DefDatiEstesiDao;
import it.wego.cross.entity.DefDatiEstesi;
import it.wego.cross.entity.LkTipoOggetto;
import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.events.AbstractEvent;
import it.wego.cross.exception.CrossException;
import it.wego.cross.plugins.genova.commercio.actions.AvvioPraticaAction;
import it.wego.cross.plugins.genova.commercio.actions.Constants;
import it.wego.cross.plugins.genova.commercio.bean.ResponseWsBean;
import it.wego.cross.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author piergiorgio
 */
@Repository("invioBoCommercioGenova")
public class InvioBoCommercioGenova extends AbstractEvent {

    @Autowired
    private AvvioPraticaAction avvioPraticaAction;
    @Autowired
    private DefDatiEstesiDao defDatiEstesiDao;

    @Override
    public void execute(EventoBean eb) throws Exception {
        Log.APP.info("Inizio metodo InvioBoCommercio Genova");
        LkTipoOggetto tipoOggetto = lookupDao.findTipoOggettoByCodice(Constants.CODICE_OGGETTO_DATO_ESTESO_COMMERCIO);
        if (tipoOggetto != null) {
            Pratica pratica = praticheService.getPratica(eb.getIdPratica());
            Pratica praticaPadre = pratica.getIdPraticaPadre();
            PraticheEventi eventoProtocollo = trovaProtocollo(praticaPadre);
            if (eventoProtocollo != null && eventoProtocollo.getDataProtocollo() != null && eventoProtocollo.getProtocollo() != null) {
                String codiceProcedimento = pratica.getIdProcEnte().getIdProc().getCodProc();
                DefDatiEstesi defTipologiaCommercio = defDatiEstesiDao.findByTipoOggettoIstanzaCodice(tipoOggetto, codiceProcedimento, Constants.CODICE_TIPOLOGIA_COMMERCIO);
                if (defTipologiaCommercio != null) {
                    String tipologiaCommercio = defTipologiaCommercio.getValue();
                    DefDatiEstesi defTipoProcdimento = defDatiEstesiDao.findByTipoOggettoIstanzaCodice(tipoOggetto, codiceProcedimento, Constants.CODICE_TIPO_PROCEDIMENTO);
                    if (defTipoProcdimento != null) {
                        String tipoProcedimento = defTipoProcdimento.getValue();
                        ResponseWsBean res = avvioPraticaAction.execute(pratica, tipologiaCommercio, tipoProcedimento, eventoProtocollo);
                        if (res.isOperazioneOK()) {
                            pratica.setIdentificativoEsterno(res.getIdPraticaBO());
                        } else {
                            throw new CrossException(res.getMessaggio());
                        }
                    } else {
                        Log.APP.info("InvioBoCommercio Genova - non trovata tipo procedimento : " + codiceProcedimento);
                    }
                } else {
                    Log.APP.info("InvioBoCommercio Genova - non trovata tipologia commercio : " + codiceProcedimento);
                }
            } else {
                Log.APP.info("InvioBoCommercio Genova - non trovato evento di ricezione della pratica padre o data protocollo non valorizzata");
                throw new CrossException("InvioBoCommercio - non trovato evento di ricezione della pratica padre o dati protocollo non valorizzati");
            }
        } else {
            Log.APP.info("InvioBoCommercio Genova - non trovata la pratica padre : " + Constants.CODICE_OGGETTO_DATO_ESTESO_COMMERCIO);
        }
        Log.APP.info("Fine metodo InvioBoCommercio Genova");
    }

    private PraticheEventi trovaProtocollo(Pratica pratica) {
        PraticheEventi evento = null;
        for (PraticheEventi ev : pratica.getPraticheEventiList()) {
            if (ev.getIdEvento() != null && ev.getIdEvento().getCodEvento().equals(AnaTipiEvento.RICEZIONE_PRATICA)) {
                evento = ev;
            }
        }
        return evento;
    }
}
