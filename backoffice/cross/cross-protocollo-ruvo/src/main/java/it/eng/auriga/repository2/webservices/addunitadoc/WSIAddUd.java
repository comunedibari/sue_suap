/**
 * WSIAddUd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.auriga.repository2.webservices.addunitadoc;

import java.rmi.RemoteException;

import javax.activation.DataHandler;

public interface WSIAddUd extends java.rmi.Remote {
    
//LOR	public it.eng.auriga.repository2.webservices.addunitadoc.ServiceResponse serviceOperation(it.eng.auriga.repository2.webservices.addunitadoc.Service service) throws java.rmi.RemoteException;

	public it.eng.auriga.repository2.webservices.addunitadoc.ServiceResponse serviceOperation(Service service, Object[] attachment) throws java.rmi.RemoteException;
//	public it.eng.auriga.repository2.webservices.addunitadoc.ServiceResponse serviceOperation(Service service, DataHandler[] attachment) throws java.rmi.RemoteException;
	
}
