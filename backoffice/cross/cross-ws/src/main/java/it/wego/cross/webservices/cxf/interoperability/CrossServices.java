/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.webservices.cxf.interoperability;

import it.wego.cross.exception.CrossServicesException;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * @author giuseppe
 */
@WebService
public interface CrossServices {
    @WebMethod(operationName="inserisciEvento")
    @WebResult(name="evento")
    public Evento inserisciEvento(@WebParam(name="evento")Evento evento) throws CrossServicesException;

    @WebResult(name="eventi")
    public Eventi getListaEventi(@WebParam(name="idPratica")Integer idPratica, 
            @WebParam(name="identificativoPratica")String identificativoPratica, 
            @WebParam(name="codiceEnte")String codiceEnte) throws CrossServicesException;
}
