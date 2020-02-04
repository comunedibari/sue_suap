/**
 * WSCheckInServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */


package it.wego.cross.client.stub.checkin;

public class WSCheckInServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.client.stub.checkin.WSCheckInService {

    public WSCheckInServiceLocator() {
    }


    public WSCheckInServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSCheckInServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSCheckIn
    private java.lang.String WSCheckIn_address = "http://192.168.187.111:8080/aurigarepository/services/WSCheckIn";

    public java.lang.String getWSCheckInAddress() {
        return WSCheckIn_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSCheckInWSDDServiceName = "WSCheckIn";

    public java.lang.String getWSCheckInWSDDServiceName() {
        return WSCheckInWSDDServiceName;
    }

    public void setWSCheckInWSDDServiceName(java.lang.String name) {
        WSCheckInWSDDServiceName = name;
    }

    public it.wego.cross.client.stub.checkin.WSCheckIn getWSCheckIn() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSCheckIn_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSCheckIn(endpoint);
    }

    public it.wego.cross.client.stub.checkin.WSCheckIn getWSCheckIn(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.client.stub.checkin.WSCheckInSoapBindingStub _stub = new it.wego.cross.client.stub.checkin.WSCheckInSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSCheckInWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSCheckInEndpointAddress(java.lang.String address) {
        WSCheckIn_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.client.stub.checkin.WSCheckIn.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.client.stub.checkin.WSCheckInSoapBindingStub _stub = new it.wego.cross.client.stub.checkin.WSCheckInSoapBindingStub(new java.net.URL(WSCheckIn_address), this);
                _stub.setPortName(getWSCheckInWSDDServiceName());
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
        if ("WSCheckIn".equals(inputPortName)) {
            return getWSCheckIn();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://checkin.webservices.repository2.auriga.eng.it", "WSCheckInService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://checkin.webservices.repository2.auriga.eng.it", "WSCheckIn"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSCheckIn".equals(portName)) {
            setWSCheckInEndpointAddress(address);
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
