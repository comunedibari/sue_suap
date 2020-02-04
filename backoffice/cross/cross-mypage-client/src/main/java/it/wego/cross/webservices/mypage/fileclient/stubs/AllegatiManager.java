/**
 * AllegatiManager.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.webservices.mypage.fileclient.stubs;

public interface AllegatiManager extends java.rmi.Remote {
    public java.lang.String getFile(java.lang.String codiceCommune, java.lang.String idPratica, java.lang.String idFile) throws java.rmi.RemoteException;
    public java.lang.String putFile(java.lang.String codiceCommune, java.lang.String idPratica, java.lang.String nomeFile, java.lang.String contentFile, boolean isCertificato) throws java.rmi.RemoteException;
    public BaseBean[] getListaAllegati(java.lang.String codiceCommune, java.lang.String idPratica) throws java.rmi.RemoteException;
    public BaseBean[] getListaCertificati(java.lang.String codiceCommune, java.lang.String idPratica) throws java.rmi.RemoteException;
}
