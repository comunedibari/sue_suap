/**
 * CancellazionePraticaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.plugins.genova.commercio.cancellazionepratica;

public class CancellazionePraticaServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.plugins.genova.commercio.cancellazionepratica.CancellazionePraticaService {

    public CancellazionePraticaServiceLocator() {
    }


    public CancellazionePraticaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CancellazionePraticaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CancellazionePratica
    private java.lang.String CancellazionePratica_address = "http://vm-tomcat.comune.genova.it:8080/Commercio/services/CancellazionePratica";

    public java.lang.String getCancellazionePraticaAddress() {
        return CancellazionePratica_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CancellazionePraticaWSDDServiceName = "CancellazionePratica";

    public java.lang.String getCancellazionePraticaWSDDServiceName() {
        return CancellazionePraticaWSDDServiceName;
    }

    public void setCancellazionePraticaWSDDServiceName(java.lang.String name) {
        CancellazionePraticaWSDDServiceName = name;
    }

    public it.wego.cross.plugins.genova.commercio.cancellazionepratica.CancellazionePratica_PortType getCancellazionePratica() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CancellazionePratica_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCancellazionePratica(endpoint);
    }

    public it.wego.cross.plugins.genova.commercio.cancellazionepratica.CancellazionePratica_PortType getCancellazionePratica(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.plugins.genova.commercio.cancellazionepratica.CancellazionePraticaSoapBindingStub _stub = new it.wego.cross.plugins.genova.commercio.cancellazionepratica.CancellazionePraticaSoapBindingStub(portAddress, this);
            _stub.setPortName(getCancellazionePraticaWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCancellazionePraticaEndpointAddress(java.lang.String address) {
        CancellazionePratica_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.plugins.genova.commercio.cancellazionepratica.CancellazionePratica_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.plugins.genova.commercio.cancellazionepratica.CancellazionePraticaSoapBindingStub _stub = new it.wego.cross.plugins.genova.commercio.cancellazionepratica.CancellazionePraticaSoapBindingStub(new java.net.URL(CancellazionePratica_address), this);
                _stub.setPortName(getCancellazionePraticaWSDDServiceName());
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
        if ("CancellazionePratica".equals(inputPortName)) {
            return getCancellazionePratica();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://vm-tomcat.comune.genova.it:8080/Commercio/services/CancellazionePratica", "CancellazionePraticaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://vm-tomcat.comune.genova.it:8080/Commercio/services/CancellazionePratica", "CancellazionePratica"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CancellazionePratica".equals(portName)) {
            setCancellazionePraticaEndpointAddress(address);
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
