/**
 * WSExtractOneServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.auriga.repository2.webservices.extractone;

public class WSExtractOneServiceLocator extends org.apache.axis.client.Service implements it.eng.auriga.repository2.webservices.extractone.WSExtractOneService {

    public WSExtractOneServiceLocator() {
    }


    public WSExtractOneServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSExtractOneServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSExtractOnePort
    private java.lang.String WSExtractOnePort_address = "http://10.10.1.13:8080/AurigaBusiness/soap/WSExtractOne";

    public java.lang.String getWSExtractOnePortAddress() {
        return WSExtractOnePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSExtractOnePortWSDDServiceName = "WSExtractOnePort";

    public java.lang.String getWSExtractOnePortWSDDServiceName() {
        return WSExtractOnePortWSDDServiceName;
    }

    public void setWSExtractOnePortWSDDServiceName(java.lang.String name) {
        WSExtractOnePortWSDDServiceName = name;
    }

    public it.eng.auriga.repository2.webservices.extractone.WSIExtractOne getWSExtractOnePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSExtractOnePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSExtractOnePort(endpoint);
    }

    public it.eng.auriga.repository2.webservices.extractone.WSIExtractOne getWSExtractOnePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eng.auriga.repository2.webservices.extractone.WSExtractOnePortBindingStub _stub = new it.eng.auriga.repository2.webservices.extractone.WSExtractOnePortBindingStub(portAddress, this);
            _stub.setPortName(getWSExtractOnePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSExtractOnePortEndpointAddress(java.lang.String address) {
        WSExtractOnePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.eng.auriga.repository2.webservices.extractone.WSIExtractOne.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eng.auriga.repository2.webservices.extractone.WSExtractOnePortBindingStub _stub = new it.eng.auriga.repository2.webservices.extractone.WSExtractOnePortBindingStub(new java.net.URL(WSExtractOnePort_address), this);
                _stub.setPortName(getWSExtractOnePortWSDDServiceName());
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
        if ("WSExtractOnePort".equals(inputPortName)) {
            return getWSExtractOnePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://extractone.webservices.repository2.auriga.eng.it", "WSExtractOneService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://extractone.webservices.repository2.auriga.eng.it", "WSExtractOnePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSExtractOnePort".equals(portName)) {
            setWSExtractOnePortEndpointAddress(address);
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
