/**
 * ProtocolloServicePortTypeServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asitech.webservice.protocollo;
import org.apache.axis.client.Service;

public class ProtocolloServicePortTypeServiceLocator extends Service implements ProtocolloServicePortTypeService {

    public ProtocolloServicePortTypeServiceLocator() {
    }


    public ProtocolloServicePortTypeServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ProtocolloServicePortTypeServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ProtocolloService
    //private java.lang.String ProtocolloService_address = "http://genuit-priw/priw092012/services/ProtocolloService";
    //private java.lang.String ProtocolloService_address = "http://88.41.241.124/priw092012/services/ProtocolloService";
    //private java.lang.String ProtocolloService_address = "http://localhost:8080/genuit-pri/services/ProtocolloService";
    private java.lang.String ProtocolloService_address = "http://192.168.1.13:7080/priw020030/services/ProtocolloService";

    public java.lang.String getProtocolloServiceAddress() {
        return this.ProtocolloService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ProtocolloServiceWSDDServiceName = "ProtocolloService";

    public java.lang.String getProtocolloServiceWSDDServiceName() {
        return this.ProtocolloServiceWSDDServiceName;
    }

    public void setProtocolloServiceWSDDServiceName(java.lang.String name) {
        this.ProtocolloServiceWSDDServiceName = name;
    }

    public it.asitech.webservice.protocollo.ProtocolloServicePortType getProtocolloService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(this.ProtocolloService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getProtocolloService(endpoint);
    }

    public it.asitech.webservice.protocollo.ProtocolloServicePortType getProtocolloService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.asitech.webservice.protocollo.ProtocolloServiceSoapBindingStub _stub = new it.asitech.webservice.protocollo.ProtocolloServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getProtocolloServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setProtocolloServiceEndpointAddress(java.lang.String address) {
        this.ProtocolloService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.asitech.webservice.protocollo.ProtocolloServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.asitech.webservice.protocollo.ProtocolloServiceSoapBindingStub _stub = new it.asitech.webservice.protocollo.ProtocolloServiceSoapBindingStub(new java.net.URL(this.ProtocolloService_address), this);
                _stub.setPortName(getProtocolloServiceWSDDServiceName());
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
    @Override
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ProtocolloService".equals(inputPortName)) {
            return getProtocolloService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ProtocolloServicePortTypeService");
    }

    private java.util.HashSet ports = null;

    @Override
    public java.util.Iterator getPorts() {
        if (this.ports == null) {
            this.ports = new java.util.HashSet();
            this.ports.add(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ProtocolloService"));
        }
        return this.ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

if ("ProtocolloService".equals(portName)) {
            setProtocolloServiceEndpointAddress(address);
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
