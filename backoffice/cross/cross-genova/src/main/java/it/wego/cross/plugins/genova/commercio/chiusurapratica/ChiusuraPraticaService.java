/**
 * ChiusuraPraticaService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.plugins.genova.commercio.chiusurapratica;

public interface ChiusuraPraticaService extends javax.xml.rpc.Service {
    public java.lang.String getChiusuraPraticaAddress();

    public it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPratica_PortType getChiusuraPratica() throws javax.xml.rpc.ServiceException;

    public it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPratica_PortType getChiusuraPratica(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
