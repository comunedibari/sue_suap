/**
 * AllegatiManagerServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.webservices.mypage.fileclient.stubs;

public class AllegatiManagerServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.webservices.mypage.fileclient.stubs.AllegatiManagerService {

    public AllegatiManagerServiceLocator() {
    }


    public AllegatiManagerServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AllegatiManagerServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for allegatiService
    private java.lang.String allegatiService_address = "http://vm-crosstest.comune.genova.it:8080/AllegatiService/services/allegatiService";

    public java.lang.String getallegatiServiceAddress() {
        return allegatiService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String allegatiServiceWSDDServiceName = "allegatiService";

    public java.lang.String getallegatiServiceWSDDServiceName() {
        return allegatiServiceWSDDServiceName;
    }

    public void setallegatiServiceWSDDServiceName(java.lang.String name) {
        allegatiServiceWSDDServiceName = name;
    }

    public it.wego.cross.webservices.mypage.fileclient.stubs.AllegatiManager getallegatiService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(allegatiService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getallegatiService(endpoint);
    }

    public it.wego.cross.webservices.mypage.fileclient.stubs.AllegatiManager getallegatiService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.webservices.mypage.fileclient.stubs.AllegatiServiceSoapBindingStub _stub = new it.wego.cross.webservices.mypage.fileclient.stubs.AllegatiServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getallegatiServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setallegatiServiceEndpointAddress(java.lang.String address) {
        allegatiService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.webservices.mypage.fileclient.stubs.AllegatiManager.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.webservices.mypage.fileclient.stubs.AllegatiServiceSoapBindingStub _stub = new it.wego.cross.webservices.mypage.fileclient.stubs.AllegatiServiceSoapBindingStub(new java.net.URL(allegatiService_address), this);
                _stub.setPortName(getallegatiServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("allegatiService".equals(inputPortName)) {
            return getallegatiService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.backend.people.it", "AllegatiManagerService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.backend.people.it", "allegatiService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("allegatiService".equals(portName)) {
            setallegatiServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
