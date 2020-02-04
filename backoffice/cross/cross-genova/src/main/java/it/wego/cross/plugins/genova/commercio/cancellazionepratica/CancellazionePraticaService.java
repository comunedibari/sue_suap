/**
 * CancellazionePraticaService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.plugins.genova.commercio.cancellazionepratica;

public interface CancellazionePraticaService extends javax.xml.rpc.Service {
    public java.lang.String getCancellazionePraticaAddress();

    public it.wego.cross.plugins.genova.commercio.cancellazionepratica.CancellazionePratica_PortType getCancellazionePratica() throws javax.xml.rpc.ServiceException;

    public it.wego.cross.plugins.genova.commercio.cancellazionepratica.CancellazionePratica_PortType getCancellazionePratica(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
