/**
 * WSRicercaFascicoloServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.client.stub.ricercafascicolo;

public class WSRicercaFascicoloServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.client.stub.ricercafascicolo.WSRicercaFascicoloService {

    public WSRicercaFascicoloServiceLocator() {
    }


    public WSRicercaFascicoloServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSRicercaFascicoloServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSRicercaFascicolo
    private java.lang.String WSRicercaFascicolo_address = "http://protocollotest.comune.genova.it/axis/services/WSRicercaFascicolo";

    public java.lang.String getWSRicercaFascicoloAddress() {
        return WSRicercaFascicolo_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSRicercaFascicoloWSDDServiceName = "WSRicercaFascicolo";

    public java.lang.String getWSRicercaFascicoloWSDDServiceName() {
        return WSRicercaFascicoloWSDDServiceName;
    }

    public void setWSRicercaFascicoloWSDDServiceName(java.lang.String name) {
        WSRicercaFascicoloWSDDServiceName = name;
    }

    public it.wego.cross.client.stub.ricercafascicolo.WSRicercaFascicolo_PortType getWSRicercaFascicolo() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSRicercaFascicolo_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSRicercaFascicolo(endpoint);
    }

    public it.wego.cross.client.stub.ricercafascicolo.WSRicercaFascicolo_PortType getWSRicercaFascicolo(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.client.stub.ricercafascicolo.WSRicercaFascicoloSoapBindingStub _stub = new it.wego.cross.client.stub.ricercafascicolo.WSRicercaFascicoloSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSRicercaFascicoloWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSRicercaFascicoloEndpointAddress(java.lang.String address) {
        WSRicercaFascicolo_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.client.stub.ricercafascicolo.WSRicercaFascicolo_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.client.stub.ricercafascicolo.WSRicercaFascicoloSoapBindingStub _stub = new it.wego.cross.client.stub.ricercafascicolo.WSRicercaFascicoloSoapBindingStub(new java.net.URL(WSRicercaFascicolo_address), this);
                _stub.setPortName(getWSRicercaFascicoloWSDDServiceName());
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
        if ("WSRicercaFascicolo".equals(inputPortName)) {
            return getWSRicercaFascicolo();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://protocollotest.comune.genova.it/axis/services/WSRicercaFascicolo", "WSRicercaFascicoloService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://protocollotest.comune.genova.it/axis/services/WSRicercaFascicolo", "WSRicercaFascicolo"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSRicercaFascicolo".equals(portName)) {
            setWSRicercaFascicoloEndpointAddress(address);
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
