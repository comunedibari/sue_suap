/**
 * WSExtractMultiServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.auriga.repository2.webservices.extractmulti;

public class WSExtractMultiServiceLocator extends org.apache.axis.client.Service implements it.eng.auriga.repository2.webservices.extractmulti.WSExtractMultiService {

    public WSExtractMultiServiceLocator() {
    }


    public WSExtractMultiServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSExtractMultiServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSExtractMultiPort
    private java.lang.String WSExtractMultiPort_address = "http://10.10.1.13:8080/AurigaBusiness/soap/WSExtractMulti";

    public java.lang.String getWSExtractMultiPortAddress() {
        return WSExtractMultiPort_address;
    }

    // The WSDD service name defaults to the port name.
//LOR    private java.lang.String WSExtractMultiPortWSDDServiceName = "WSExtractMultiPort";
    private java.lang.String WSExtractMultiPortWSDDServiceName = "WSExtractMultiPort";

    public java.lang.String getWSExtractMultiPortWSDDServiceName() {
        return WSExtractMultiPortWSDDServiceName;
    }

    public void setWSExtractMultiPortWSDDServiceName(java.lang.String name) {
        WSExtractMultiPortWSDDServiceName = name;
    }

    public it.eng.auriga.repository2.webservices.extractmulti.WSIExtractMulti getWSExtractMultiPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSExtractMultiPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSExtractMultiPort(endpoint);
    }

    public it.eng.auriga.repository2.webservices.extractmulti.WSIExtractMulti getWSExtractMultiPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eng.auriga.repository2.webservices.extractmulti.WSExtractMultiPortBindingStub _stub = new it.eng.auriga.repository2.webservices.extractmulti.WSExtractMultiPortBindingStub(portAddress, this);
            _stub.setPortName(getWSExtractMultiPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSExtractMultiPortEndpointAddress(java.lang.String address) {
        WSExtractMultiPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.eng.auriga.repository2.webservices.extractmulti.WSIExtractMulti.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eng.auriga.repository2.webservices.extractmulti.WSExtractMultiPortBindingStub _stub = new it.eng.auriga.repository2.webservices.extractmulti.WSExtractMultiPortBindingStub(new java.net.URL(WSExtractMultiPort_address), this);
                _stub.setPortName(getWSExtractMultiPortWSDDServiceName());
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
        if ("WSExtractMultiPort".equals(inputPortName)) {
            return getWSExtractMultiPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://extractmulti.webservices.repository2.auriga.eng.it", "WSExtractMultiService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://extractmulti.webservices.repository2.auriga.eng.it", "WSExtractMultiPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSExtractMultiPort".equals(portName)) {
            setWSExtractMultiPortEndpointAddress(address);
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
