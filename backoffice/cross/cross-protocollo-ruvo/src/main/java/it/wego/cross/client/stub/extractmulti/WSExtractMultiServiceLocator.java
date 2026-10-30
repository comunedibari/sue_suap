/**
 * WSExtractMultiServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.client.stub.extractmulti;

public class WSExtractMultiServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.client.stub.extractmulti.WSExtractMultiService {

    public WSExtractMultiServiceLocator() {
    }


    public WSExtractMultiServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSExtractMultiServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSExtractMulti
    private java.lang.String WSExtractMulti_address = "http://vm-testdoc:8080/aurigarepository/services/WSExtractMulti";

    public java.lang.String getWSExtractMultiAddress() {
        return WSExtractMulti_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSExtractMultiWSDDServiceName = "WSExtractMulti";

    public java.lang.String getWSExtractMultiWSDDServiceName() {
        return WSExtractMultiWSDDServiceName;
    }

    public void setWSExtractMultiWSDDServiceName(java.lang.String name) {
        WSExtractMultiWSDDServiceName = name;
    }

    public it.wego.cross.client.stub.extractmulti.WSExtractMulti_PortType getWSExtractMulti() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSExtractMulti_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSExtractMulti(endpoint);
    }

    public it.wego.cross.client.stub.extractmulti.WSExtractMulti_PortType getWSExtractMulti(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.client.stub.extractmulti.WSExtractMultiSoapBindingStub _stub = new it.wego.cross.client.stub.extractmulti.WSExtractMultiSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSExtractMultiWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSExtractMultiEndpointAddress(java.lang.String address) {
        WSExtractMulti_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.client.stub.extractmulti.WSExtractMulti_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.client.stub.extractmulti.WSExtractMultiSoapBindingStub _stub = new it.wego.cross.client.stub.extractmulti.WSExtractMultiSoapBindingStub(new java.net.URL(WSExtractMulti_address), this);
                _stub.setPortName(getWSExtractMultiWSDDServiceName());
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
        if ("WSExtractMulti".equals(inputPortName)) {
            return getWSExtractMulti();
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
            ports.add(new javax.xml.namespace.QName("http://extractmulti.webservices.repository2.auriga.eng.it", "WSExtractMulti"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSExtractMulti".equals(portName)) {
            setWSExtractMultiEndpointAddress(address);
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
