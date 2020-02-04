/**
 * CrossServicesImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.webservices.cxf.interoperability;


public class CrossServicesImplServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.webservices.cxf.interoperability.CrossServicesImplService {

    public CrossServicesImplServiceLocator() {
    }


    public CrossServicesImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CrossServicesImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }
    
    // Use to get a proxy class for CrossServicesImplPort
    //private java.lang.String CrossServicesImplPort_address = getEndpoint();
    private java.lang.String CrossServicesImplPort_address = "http://localhost:8080/cross-ws/services/CrossServices";

    public java.lang.String getCrossServicesImplPortAddress() {
        return CrossServicesImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CrossServicesImplPortWSDDServiceName = "CrossServicesImplPort";

    public java.lang.String getCrossServicesImplPortWSDDServiceName() {
        return CrossServicesImplPortWSDDServiceName;
    }

    public void setCrossServicesImplPortWSDDServiceName(java.lang.String name) {
        CrossServicesImplPortWSDDServiceName = name;
    }

    public it.wego.cross.webservices.cxf.interoperability.CrossServices getCrossServicesImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CrossServicesImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCrossServicesImplPort(endpoint);
    }

    public it.wego.cross.webservices.cxf.interoperability.CrossServices getCrossServicesImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.webservices.cxf.interoperability.CrossServicesImplServiceSoapBindingStub _stub = new it.wego.cross.webservices.cxf.interoperability.CrossServicesImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCrossServicesImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCrossServicesImplPortEndpointAddress(java.lang.String address) {
        CrossServicesImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.webservices.cxf.interoperability.CrossServices.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.webservices.cxf.interoperability.CrossServicesImplServiceSoapBindingStub _stub = new it.wego.cross.webservices.cxf.interoperability.CrossServicesImplServiceSoapBindingStub(new java.net.URL(CrossServicesImplPort_address), this);
                _stub.setPortName(getCrossServicesImplPortWSDDServiceName());
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
        if ("CrossServicesImplPort".equals(inputPortName)) {
            return getCrossServicesImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "CrossServicesImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "CrossServicesImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CrossServicesImplPort".equals(portName)) {
            setCrossServicesImplPortEndpointAddress(address);
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
