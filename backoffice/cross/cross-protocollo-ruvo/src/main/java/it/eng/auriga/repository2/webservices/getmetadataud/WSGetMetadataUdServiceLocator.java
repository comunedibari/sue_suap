/**
 * WSGetMetadataUdServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.auriga.repository2.webservices.getmetadataud;

public class WSGetMetadataUdServiceLocator extends org.apache.axis.client.Service implements it.eng.auriga.repository2.webservices.getmetadataud.WSGetMetadataUdService {

    public WSGetMetadataUdServiceLocator() {
    }


    public WSGetMetadataUdServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSGetMetadataUdServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSGetMetadataUdPort
    private java.lang.String WSGetMetadataUdPort_address = "http://10.10.1.13:8080/AurigaBusiness/soap/WSGetMetadataUd";

    public java.lang.String getWSGetMetadataUdPortAddress() {
        return WSGetMetadataUdPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSGetMetadataUdPortWSDDServiceName = "WSGetMetadataUdPort";

    public java.lang.String getWSGetMetadataUdPortWSDDServiceName() {
        return WSGetMetadataUdPortWSDDServiceName;
    }

    public void setWSGetMetadataUdPortWSDDServiceName(java.lang.String name) {
        WSGetMetadataUdPortWSDDServiceName = name;
    }

    public it.eng.auriga.repository2.webservices.getmetadataud.WSIGetMetadataUd getWSGetMetadataUdPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSGetMetadataUdPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSGetMetadataUdPort(endpoint);
    }

    public it.eng.auriga.repository2.webservices.getmetadataud.WSIGetMetadataUd getWSGetMetadataUdPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eng.auriga.repository2.webservices.getmetadataud.WSGetMetadataUdPortBindingStub _stub = new it.eng.auriga.repository2.webservices.getmetadataud.WSGetMetadataUdPortBindingStub(portAddress, this);
            _stub.setPortName(getWSGetMetadataUdPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSGetMetadataUdPortEndpointAddress(java.lang.String address) {
        WSGetMetadataUdPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.eng.auriga.repository2.webservices.getmetadataud.WSIGetMetadataUd.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eng.auriga.repository2.webservices.getmetadataud.WSGetMetadataUdPortBindingStub _stub = new it.eng.auriga.repository2.webservices.getmetadataud.WSGetMetadataUdPortBindingStub(new java.net.URL(WSGetMetadataUdPort_address), this);
                _stub.setPortName(getWSGetMetadataUdPortWSDDServiceName());
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
        if ("WSGetMetadataUdPort".equals(inputPortName)) {
            return getWSGetMetadataUdPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://getmetadataud.webservices.repository2.auriga.eng.it", "WSGetMetadataUdService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://getmetadataud.webservices.repository2.auriga.eng.it", "WSGetMetadataUdPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSGetMetadataUdPort".equals(portName)) {
            setWSGetMetadataUdPortEndpointAddress(address);
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
