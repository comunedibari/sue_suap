package it.wego.cross.client.stub.trovadocfolder;

public class WSTrovaDocFolderServiceLocator extends org.apache.axis.client.Service implements it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolderService {

    public WSTrovaDocFolderServiceLocator() {
    }

    public WSTrovaDocFolderServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSTrovaDocFolderServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }
    private java.lang.String WSTrovaDocFolder_address = "http://vm-testdoc:8080/aurigarepository/services/WSTrovaDocFolder";

    public java.lang.String getWSTrovaDocFolderAddress() {
        return WSTrovaDocFolder_address;
    }
    // The WSDD service name defaults to the port name.
    private java.lang.String WSTrovaDocFolderWSDDServiceName = "WSTrovaDocFolder";

    public java.lang.String getWSTrovaDocFolderWSDDServiceName() {
        return WSTrovaDocFolderWSDDServiceName;
    }

    public void setWSTrovaDocFolderWSDDServiceName(java.lang.String name) {
        WSTrovaDocFolderWSDDServiceName = name;
    }

    public it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolder getWSTrovaDocFolder() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSTrovaDocFolder_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSTrovaDocFolder(endpoint);
    }

    public it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolder getWSTrovaDocFolder(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolderSoapBindingStub _stub = new it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolderSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSTrovaDocFolderWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSTrovaDocFolderEndpointAddress(java.lang.String address) {
        WSTrovaDocFolder_address = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolder.class.isAssignableFrom(serviceEndpointInterface)) {
                it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolderSoapBindingStub _stub = new it.wego.cross.client.stub.trovadocfolder.WSTrovaDocFolderSoapBindingStub(new java.net.URL(WSTrovaDocFolder_address), this);
                _stub.setPortName(getWSTrovaDocFolderWSDDServiceName());
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
        if ("WSTrovaDocFolder".equals(inputPortName)) {
            return getWSTrovaDocFolder();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://checkout.webservices.repository2.auriga.eng.it", "WSTrovaDocFolderService");
    }
    private java.util.HashSet ports = null;

    @Override
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://checkout.webservices.repository2.auriga.eng.it", "WSTrovaDocFolder"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("WSTrovaDocFolder".equals(portName)) {
            setWSTrovaDocFolderEndpointAddress(address);
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
