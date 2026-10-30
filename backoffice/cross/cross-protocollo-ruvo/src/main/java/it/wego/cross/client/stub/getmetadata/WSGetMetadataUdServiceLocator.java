/**
 * WSGetMetadataUdServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.client.stub.getmetadata;

public class WSGetMetadataUdServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.client.stub.getmetadata.WSGetMetadataUdService {

    public WSGetMetadataUdServiceLocator() {
    }


    public WSGetMetadataUdServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSGetMetadataUdServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSGetMetadataUd
    private java.lang.String WSGetMetadataUd_address = "http://vm-testdoc:8080/aurigarepository/services/WSGetMetadataUd";

    public java.lang.String getWSGetMetadataUdAddress() {
        return WSGetMetadataUd_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSGetMetadataUdWSDDServiceName = "WSGetMetadataUd";

    public java.lang.String getWSGetMetadataUdWSDDServiceName() {
        return WSGetMetadataUdWSDDServiceName;
    }

    public void setWSGetMetadataUdWSDDServiceName(java.lang.String name) {
        WSGetMetadataUdWSDDServiceName = name;
    }

    public it.wego.cross.client.stub.getmetadata.WSGetMetadataUd_PortType getWSGetMetadataUd() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSGetMetadataUd_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSGetMetadataUd(endpoint);
    }

    public it.wego.cross.client.stub.getmetadata.WSGetMetadataUd_PortType getWSGetMetadataUd(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.client.stub.getmetadata.WSGetMetadataUdSoapBindingStub _stub = new it.wego.cross.client.stub.getmetadata.WSGetMetadataUdSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSGetMetadataUdWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSGetMetadataUdEndpointAddress(java.lang.String address) {
        WSGetMetadataUd_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.client.stub.getmetadata.WSGetMetadataUd_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.client.stub.getmetadata.WSGetMetadataUdSoapBindingStub _stub = new it.wego.cross.client.stub.getmetadata.WSGetMetadataUdSoapBindingStub(new java.net.URL(WSGetMetadataUd_address), this);
                _stub.setPortName(getWSGetMetadataUdWSDDServiceName());
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
        if ("WSGetMetadataUd".equals(inputPortName)) {
            return getWSGetMetadataUd();
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
            ports.add(new javax.xml.namespace.QName("http://getmetadataud.webservices.repository2.auriga.eng.it", "WSGetMetadataUd"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSGetMetadataUd".equals(portName)) {
            setWSGetMetadataUdEndpointAddress(address);
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
