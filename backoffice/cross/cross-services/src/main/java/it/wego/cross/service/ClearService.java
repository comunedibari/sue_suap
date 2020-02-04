/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.service;

import it.wego.cross.entity.Pratica;
import it.wego.cross.entity.PraticheEventi;
import it.wego.cross.entity.ProcedimentiEnti;
import it.wego.cross.webservices.client.clear.stubs.Log;
import it.wego.cross.webservices.client.clear.stubs.PraticaSimoExtended;
import it.wego.cross.webservices.client.clear.stubs.SimoAssociaEventoExtended;
import it.wego.cross.webservices.client.clear.stubs.SimoCreaProcedimento;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriele
 */
@Service
public interface ClearService {

    public Log creaProcedimento(ProcedimentiEnti procedimento) throws Exception;

    public Log creaProcedimentoRaw(SimoCreaProcedimento procedimento, Integer idEnte) throws Exception;

    public Log creaPraticaRaw(PraticaSimoExtended pratica, Integer idEnte) throws Exception;

    public Log creaEventoRaw(SimoAssociaEventoExtended evento, Integer idEnte) throws Exception;

    public Log creaPratica(Pratica pratica) throws Exception;

    public Log creaEvento(PraticheEventi eventoCross) throws Exception;
}
