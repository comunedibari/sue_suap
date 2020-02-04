/**
 * ChiusuraPraticaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.plugins.genova.commercio.chiusurapratica;

public class ChiusuraPraticaServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPraticaService {

    public ChiusuraPraticaServiceLocator() {
    }


    public ChiusuraPraticaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ChiusuraPraticaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ChiusuraPratica
    private java.lang.String ChiusuraPratica_address = "http://vm-tomcat.comune.genova.it:8080/Commercio/services/ChiusuraPratica";

    public java.lang.String getChiusuraPraticaAddress() {
        return ChiusuraPratica_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ChiusuraPraticaWSDDServiceName = "ChiusuraPratica";

    public java.lang.String getChiusuraPraticaWSDDServiceName() {
        return ChiusuraPraticaWSDDServiceName;
    }

    public void setChiusuraPraticaWSDDServiceName(java.lang.String name) {
        ChiusuraPraticaWSDDServiceName = name;
    }

    public it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPratica_PortType getChiusuraPratica() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ChiusuraPratica_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getChiusuraPratica(endpoint);
    }

    public it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPratica_PortType getChiusuraPratica(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPraticaSoapBindingStub _stub = new it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPraticaSoapBindingStub(portAddress, this);
            _stub.setPortName(getChiusuraPraticaWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setChiusuraPraticaEndpointAddress(java.lang.String address) {
        ChiusuraPratica_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPratica_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPraticaSoapBindingStub _stub = new it.wego.cross.plugins.genova.commercio.chiusurapratica.ChiusuraPraticaSoapBindingStub(new java.net.URL(ChiusuraPratica_address), this);
                _stub.setPortName(getChiusuraPraticaWSDDServiceName());
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
        if ("ChiusuraPratica".equals(inputPortName)) {
            return getChiusuraPratica();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://vm-tomcat.comune.genova.it:8080/Commercio/services/ChiusuraPratica", "ChiusuraPraticaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://vm-tomcat.comune.genova.it:8080/Commercio/services/ChiusuraPratica", "ChiusuraPratica"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ChiusuraPratica".equals(portName)) {
            setChiusuraPraticaEndpointAddress(address);
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
