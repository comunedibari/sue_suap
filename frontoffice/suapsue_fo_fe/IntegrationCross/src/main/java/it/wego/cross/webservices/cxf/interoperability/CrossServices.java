/**
 * CrossServices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.webservices.cxf.interoperability;

public interface CrossServices extends java.rmi.Remote {
    public it.wego.cross.webservices.cxf.interoperability.Evento inserisciEvento(it.wego.cross.webservices.cxf.interoperability.Evento evento) throws java.rmi.RemoteException, it.wego.cross.webservices.cxf.interoperability.CrossServicesException;
    public it.wego.cross.webservices.cxf.interoperability.Evento[] getListaEventi(java.lang.Integer idPratica, java.lang.String identificativoPratica, java.lang.String codiceEnte) throws java.rmi.RemoteException, it.wego.cross.webservices.cxf.interoperability.CrossServicesException;
}
