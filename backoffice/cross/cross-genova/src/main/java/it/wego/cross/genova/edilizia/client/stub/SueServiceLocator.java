/**
 * SueServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.genova.edilizia.client.stub;

public class SueServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.genova.edilizia.client.stub.SueService {

    public SueServiceLocator() {
    }


    public SueServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SueServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Sue
    private java.lang.String Sue_address = "http://172.19.52.223:8080/SUE/Sue.jws";

    public java.lang.String getSueAddress() {
        return Sue_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SueWSDDServiceName = "Sue";

    public java.lang.String getSueWSDDServiceName() {
        return SueWSDDServiceName;
    }

    public void setSueWSDDServiceName(java.lang.String name) {
        SueWSDDServiceName = name;
    }

    public it.wego.cross.genova.edilizia.client.stub.Sue_PortType getSue() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Sue_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSue(endpoint);
    }

    public it.wego.cross.genova.edilizia.client.stub.Sue_PortType getSue(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.genova.edilizia.client.stub.SueSoapBindingStub _stub = new it.wego.cross.genova.edilizia.client.stub.SueSoapBindingStub(portAddress, this);
            _stub.setPortName(getSueWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSueEndpointAddress(java.lang.String address) {
        Sue_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.genova.edilizia.client.stub.Sue_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.genova.edilizia.client.stub.SueSoapBindingStub _stub = new it.wego.cross.genova.edilizia.client.stub.SueSoapBindingStub(new java.net.URL(Sue_address), this);
                _stub.setPortName(getSueWSDDServiceName());
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
        if ("Sue".equals(inputPortName)) {
            return getSue();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://172.19.52.223:8080/SUE/Sue.jws", "SueService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://172.19.52.223:8080/SUE/Sue.jws", "Sue"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Sue".equals(portName)) {
            setSueEndpointAddress(address);
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
