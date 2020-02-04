/**
 * ReceivedProcessImplWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.webservices.mypage.mypageclient.stubs;

public class ReceivedProcessImplWSServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.webservices.mypage.mypageclient.stubs.ReceivedProcessImplWSService {

    public ReceivedProcessImplWSServiceLocator() {
    }


    public ReceivedProcessImplWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ReceivedProcessImplWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for setEventService
    private java.lang.String setEventService_address = "http://vm-crosstest.comune.genova.it:8080/BEService/services/setEventService";

    public java.lang.String getsetEventServiceAddress() {
        return setEventService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String setEventServiceWSDDServiceName = "setEventService";

    public java.lang.String getsetEventServiceWSDDServiceName() {
        return setEventServiceWSDDServiceName;
    }

    public void setsetEventServiceWSDDServiceName(java.lang.String name) {
        setEventServiceWSDDServiceName = name;
    }

    public it.wego.cross.webservices.mypage.mypageclient.stubs.ReceivedProcessImplWS getsetEventService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(setEventService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getsetEventService(endpoint);
    }

    public it.wego.cross.webservices.mypage.mypageclient.stubs.ReceivedProcessImplWS getsetEventService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.webservices.mypage.mypageclient.stubs.SetEventServiceSoapBindingStub _stub = new it.wego.cross.webservices.mypage.mypageclient.stubs.SetEventServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getsetEventServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setsetEventServiceEndpointAddress(java.lang.String address) {
        setEventService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.webservices.mypage.mypageclient.stubs.ReceivedProcessImplWS.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.webservices.mypage.mypageclient.stubs.SetEventServiceSoapBindingStub _stub = new it.wego.cross.webservices.mypage.mypageclient.stubs.SetEventServiceSoapBindingStub(new java.net.URL(setEventService_address), this);
                _stub.setPortName(getsetEventServiceWSDDServiceName());
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
        if ("setEventService".equals(inputPortName)) {
            return getsetEventService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.backend.people.it", "ReceivedProcessImplWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.backend.people.it", "setEventService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("setEventService".equals(portName)) {
            setsetEventServiceEndpointAddress(address);
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
