package it.wego.cross.client.stub.extractone;

public class WSExtractOneServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.client.stub.extractone.WSExtractOneService {

    public WSExtractOneServiceLocator() {
    }

    public WSExtractOneServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSExtractOneServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }
    // Use to get a proxy class for WSExtractOne
    private java.lang.String WSExtractOne_address = "http://vm-testdoc:8080/aurigarepository/services/WSExtractOne";

    public java.lang.String getWSExtractOneAddress() {
        return WSExtractOne_address;
    }
    // The WSDD service name defaults to the port name.
    private java.lang.String WSExtractOneWSDDServiceName = "WSExtractOne";

    public java.lang.String getWSWSExtractOneWSDDServiceName() {
        return WSExtractOneWSDDServiceName;
    }

    public void setWSExtractOneWSDDServiceName(java.lang.String name) {
        WSExtractOneWSDDServiceName = name;
    }

    public it.wego.cross.client.stub.extractone.WSExtractOne getWSExtractOne() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSExtractOne_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSExtractOne(endpoint);
    }

    public it.wego.cross.client.stub.extractone.WSExtractOne getWSExtractOne(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.client.stub.extractone.WSExtractOneSoapBindingStub _stub = new it.wego.cross.client.stub.extractone.WSExtractOneSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSWSExtractOneWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSExtractOneEndpointAddress(java.lang.String address) {
        WSExtractOne_address = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.client.stub.extractone.WSExtractOne.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.client.stub.extractone.WSExtractOneSoapBindingStub _stub = new it.wego.cross.client.stub.extractone.WSExtractOneSoapBindingStub(new java.net.URL(WSExtractOne_address), this);
                _stub.setPortName(getWSWSExtractOneWSDDServiceName());
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
        if ("WSExtractOne".equals(inputPortName)) {
            return getWSExtractOne();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://checkout.webservices.repository2.auriga.eng.it", "WSExtractOneService");
    }
    private java.util.HashSet ports = null;

    @Override
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://checkout.webservices.repository2.auriga.eng.it", "WSExtractOne"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("WSExtractOne".equals(portName)) {
            setWSExtractOneEndpointAddress(address);
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
