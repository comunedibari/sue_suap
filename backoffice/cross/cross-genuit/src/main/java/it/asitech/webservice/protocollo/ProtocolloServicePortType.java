/**
 * ProtocolloServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asitech.webservice.protocollo;

import java.util.Collection;

import org.apache.axis.attachments.AttachmentPart;

public interface ProtocolloServicePortType extends java.rmi.Remote {
    public it.asitech.webservice.protocollo.PRIGetSidResponse PRIGetSid(java.lang.String WF_USER, java.lang.String WF_PASSWORD) throws java.rmi.RemoteException;
    public it.asitech.webservice.protocollo.ResponseMessage PRIEndSession(java.lang.String sid) throws java.rmi.RemoteException;
    public it.asitech.webservice.protocollo.PRIGetClassInfoResponse PRIGetClassInfo(java.lang.String sid, java.lang.String user, java.lang.String className) throws java.rmi.RemoteException;
    public it.asitech.webservice.protocollo.PRIObjectOut PRIGetObject(java.lang.String sid, java.lang.String user, int id, java.lang.String className) throws java.rmi.RemoteException;
    public it.asitech.webservice.protocollo.PRIObjectOut PRISaveObject(java.lang.String sid, java.lang.String user, it.asitech.webservice.protocollo.PriObject priObj) throws java.rmi.RemoteException;
    public it.asitech.webservice.protocollo.PRIObjectOut PRIProtocollaDoc(java.lang.String sid, java.lang.String user, int annoFascicolo, java.lang.String codiceFascicolo, it.asitech.webservice.protocollo.PriObject priObj, Collection<AttachmentPart> attach) throws java.rmi.RemoteException;
    public it.asitech.webservice.protocollo.PRIObjectOut PRIRegistraDoc(java.lang.String sid, java.lang.String user, it.asitech.webservice.protocollo.PriObject priObj, int annoFascicolo, java.lang.String codiceFascicolo, Collection<AttachmentPart> attach) throws java.rmi.RemoteException;
    public it.asitech.webservice.protocollo.PRIQueryObjectOut PRIQuery(java.lang.String sid, java.lang.String user, java.lang.String className, java.lang.String queryString, java.lang.String[] elencoCampiOut, int page) throws java.rmi.RemoteException;
    public it.asitech.webservice.protocollo.PRIQueryObjectOut PRIDocumentiFascicolo(java.lang.String sid, java.lang.String user, int anno, java.lang.String codice, java.lang.String[] elencoCampiOut) throws java.rmi.RemoteException;
    public it.asitech.webservice.protocollo.PRIObjectOut PRIGetFile(java.lang.String sid, java.lang.String user, int idFile) throws java.rmi.RemoteException;    
    public it.asitech.webservice.protocollo.PRIObjectOut PRIGetFileOrig(java.lang.String sid, java.lang.String user, int idDoc) throws java.rmi.RemoteException;
}
