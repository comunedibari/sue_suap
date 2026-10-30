/**
 * WSCheckOutServiceLocator.java
 *
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006
 * (06:55:48 PDT) WSDL2Java emitter.
 */
package it.wego.cross.client.stub.checkout;

public class WSCheckOutServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.client.stub.checkout.WSCheckOutService {

    public WSCheckOutServiceLocator() {
    }

    public WSCheckOutServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSCheckOutServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }
    // Use to get a proxy class for WSCheckOut
    private java.lang.String WSCheckOut_address = "http://192.168.187.111:8080/aurigarepository/services/WSCheckOut";

    public java.lang.String getWSCheckOutAddress() {
        return WSCheckOut_address;
    }
    // The WSDD service name defaults to the port name.
    private java.lang.String WSCheckOutWSDDServiceName = "WSCheckOut";

    public java.lang.String getWSCheckOutWSDDServiceName() {
        return WSCheckOutWSDDServiceName;
    }

    public void setWSCheckOutWSDDServiceName(java.lang.String name) {
        WSCheckOutWSDDServiceName = name;
    }

    public it.wego.cross.client.stub.checkout.WSCheckOut getWSCheckOut() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSCheckOut_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSCheckOut(endpoint);
    }

    public it.wego.cross.client.stub.checkout.WSCheckOut getWSCheckOut(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.client.stub.checkout.WSCheckOutSoapBindingStub _stub = new it.wego.cross.client.stub.checkout.WSCheckOutSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSCheckOutWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSCheckOutEndpointAddress(java.lang.String address) {
        WSCheckOut_address = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.client.stub.checkout.WSCheckOut.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.client.stub.checkout.WSCheckOutSoapBindingStub _stub = new it.wego.cross.client.stub.checkout.WSCheckOutSoapBindingStub(new java.net.URL(WSCheckOut_address), this);
                _stub.setPortName(getWSCheckOutWSDDServiceName());
                return _stub;
            }
        } catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSCheckOut".equals(inputPortName)) {
            return getWSCheckOut();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://checkout.webservices.repository2.auriga.eng.it", "WSCheckOutService");
    }
    private java.util.HashSet ports = null;

    @Override
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://checkout.webservices.repository2.auriga.eng.it", "WSCheckOut"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("WSCheckOut".equals(portName)) {
            setWSCheckOutEndpointAddress(address);
        } else { // Unknown Port Name
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
