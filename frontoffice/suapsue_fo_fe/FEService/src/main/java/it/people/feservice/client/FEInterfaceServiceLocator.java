/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 *  
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://joinup.ec.europa.eu/software/page/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * 
 * This product includes software developed by Yale University
 * 
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.feservice.client;

public class FEInterfaceServiceLocator extends org.apache.axis.client.Service implements it.people.feservice.client.FEInterfaceService {

    public FEInterfaceServiceLocator() {
    }


    public FEInterfaceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public FEInterfaceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for FEInterface
    private java.lang.String FEInterface_address = "http://localhost:8080/FEService/services/FEInterface";

    public java.lang.String getFEInterfaceAddress() {
        return FEInterface_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String FEInterfaceWSDDServiceName = "FEInterface";

    public java.lang.String getFEInterfaceWSDDServiceName() {
        return FEInterfaceWSDDServiceName;
    }

    public void setFEInterfaceWSDDServiceName(java.lang.String name) {
        FEInterfaceWSDDServiceName = name;
    }

    public it.people.feservice.FEInterface getFEInterface() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(FEInterface_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getFEInterface(endpoint);
    }

    public it.people.feservice.FEInterface getFEInterface(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.people.feservice.client.FEInterfaceStub _stub = new it.people.feservice.client.FEInterfaceStub(portAddress, this);
            _stub.setPortName(getFEInterfaceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setFEInterfaceEndpointAddress(java.lang.String address) {
        FEInterface_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.people.feservice.FEInterface.class.isAssignableFrom(serviceEndpointInterface)) {
                it.people.feservice.client.FEInterfaceStub _stub = new it.people.feservice.client.FEInterfaceStub(new java.net.URL(FEInterface_address), this);
                _stub.setPortName(getFEInterfaceWSDDServiceName());
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
        if ("FEInterface".equals(inputPortName)) {
            return getFEInterface();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://feservice.people.it/", "FEInterfaceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://feservice.people.it/", "FEInterface"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("FEInterface".equals(portName)) {
            setFEInterfaceEndpointAddress(address);
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
