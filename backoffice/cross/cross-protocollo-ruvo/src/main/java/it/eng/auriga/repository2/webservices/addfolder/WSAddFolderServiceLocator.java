/**
 * WSAddFolderServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.auriga.repository2.webservices.addfolder;

public class WSAddFolderServiceLocator extends org.apache.axis.client.Service implements it.eng.auriga.repository2.webservices.addfolder.WSAddFolderService {

    public WSAddFolderServiceLocator() {
    }


    public WSAddFolderServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSAddFolderServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSAddFolderPort
    private java.lang.String WSAddFolderPort_address = "http://10.10.1.13:8080/AurigaBusiness/soap/WSAddFolder";

    public java.lang.String getWSAddFolderPortAddress() {
        return WSAddFolderPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSAddFolderPortWSDDServiceName = "WSAddFolderPort";

    public java.lang.String getWSAddFolderPortWSDDServiceName() {
        return WSAddFolderPortWSDDServiceName;
    }

    public void setWSAddFolderPortWSDDServiceName(java.lang.String name) {
        WSAddFolderPortWSDDServiceName = name;
    }

    public it.eng.auriga.repository2.webservices.addfolder.WSIAddFolder getWSAddFolderPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSAddFolderPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSAddFolderPort(endpoint);
    }

    public it.eng.auriga.repository2.webservices.addfolder.WSIAddFolder getWSAddFolderPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eng.auriga.repository2.webservices.addfolder.WSAddFolderPortBindingStub _stub = new it.eng.auriga.repository2.webservices.addfolder.WSAddFolderPortBindingStub(portAddress, this);
            _stub.setPortName(getWSAddFolderPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSAddFolderPortEndpointAddress(java.lang.String address) {
        WSAddFolderPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.eng.auriga.repository2.webservices.addfolder.WSIAddFolder.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eng.auriga.repository2.webservices.addfolder.WSAddFolderPortBindingStub _stub = new it.eng.auriga.repository2.webservices.addfolder.WSAddFolderPortBindingStub(new java.net.URL(WSAddFolderPort_address), this);
                _stub.setPortName(getWSAddFolderPortWSDDServiceName());
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
        if ("WSAddFolderPort".equals(inputPortName)) {
            return getWSAddFolderPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://addfolder.webservices.repository2.auriga.eng.it", "WSAddFolderService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://addfolder.webservices.repository2.auriga.eng.it", "WSAddFolderPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSAddFolderPort".equals(portName)) {
            setWSAddFolderPortEndpointAddress(address);
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
