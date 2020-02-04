/**
 * CrossServicesImplServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.wego.cross.webservices.cxf.interoperability;

public class CrossServicesImplServiceSoapBindingStub extends org.apache.axis.client.Stub implements it.wego.cross.webservices.cxf.interoperability.CrossServices {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[2];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("inserisciEvento");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "evento"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "evento"), it.wego.cross.webservices.cxf.interoperability.Evento.class, false, false);
        //param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "evento"));
        oper.setReturnClass(it.wego.cross.webservices.cxf.interoperability.Evento.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "evento"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "CrossServicesException"),
                      "it.wego.cross.webservices.cxf.interoperability.CrossServicesException",
                      new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "CrossServicesException"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getListaEventi");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idPratica"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), java.lang.Integer.class, false, false);
        //param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "identificativoPratica"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        //param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceEnte"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        //param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "eventi"));
        oper.setReturnClass(it.wego.cross.webservices.cxf.interoperability.Evento[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "eventi"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "evento"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "CrossServicesException"),
                      "it.wego.cross.webservices.cxf.interoperability.CrossServicesException",
                      new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "CrossServicesException"), 
                      true
                     ));
        _operations[1] = oper;

    }

    public CrossServicesImplServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public CrossServicesImplServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public CrossServicesImplServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "allegati");
            cachedSerQNames.add(qName);
            cls = it.wego.cross.webservices.cxf.interoperability.Allegato[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "allegato");
            qName2 = new javax.xml.namespace.QName("", "allegati");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "allegato");
            cachedSerQNames.add(qName);
            cls = it.wego.cross.webservices.cxf.interoperability.Allegato.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "comunicazione");
            cachedSerQNames.add(qName);
            cls = it.wego.cross.webservices.cxf.interoperability.Comunicazione.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "CrossServicesException");
            cachedSerQNames.add(qName);
            cls = it.wego.cross.webservices.cxf.interoperability.CrossServicesException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "eventi");
            cachedSerQNames.add(qName);
            cls = it.wego.cross.webservices.cxf.interoperability.Evento[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "evento");
            qName2 = new javax.xml.namespace.QName("", "evento");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "evento");
            cachedSerQNames.add(qName);
            cls = it.wego.cross.webservices.cxf.interoperability.Evento.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "soggetti");
            cachedSerQNames.add(qName);
            cls = it.wego.cross.webservices.cxf.interoperability.Soggetto[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "soggetto");
            qName2 = new javax.xml.namespace.QName("", "soggetti");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "soggetto");
            cachedSerQNames.add(qName);
            cls = it.wego.cross.webservices.cxf.interoperability.Soggetto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "tipoSoggetto");
            cachedSerQNames.add(qName);
            cls = it.wego.cross.webservices.cxf.interoperability.TipoSoggetto.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public it.wego.cross.webservices.cxf.interoperability.Evento inserisciEvento(it.wego.cross.webservices.cxf.interoperability.Evento evento) throws java.rmi.RemoteException, it.wego.cross.webservices.cxf.interoperability.CrossServicesException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "inserisciEvento"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {evento});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.wego.cross.webservices.cxf.interoperability.Evento) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.wego.cross.webservices.cxf.interoperability.Evento) org.apache.axis.utils.JavaUtils.convert(_resp, it.wego.cross.webservices.cxf.interoperability.Evento.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.wego.cross.webservices.cxf.interoperability.CrossServicesException) {
              throw (it.wego.cross.webservices.cxf.interoperability.CrossServicesException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public it.wego.cross.webservices.cxf.interoperability.Evento[] getListaEventi(java.lang.Integer idPratica, java.lang.String identificativoPratica, java.lang.String codiceEnte) throws java.rmi.RemoteException, it.wego.cross.webservices.cxf.interoperability.CrossServicesException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://interoperability.cxf.webservices.cross.wego.it/", "getListaEventi"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {idPratica, identificativoPratica, codiceEnte});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.wego.cross.webservices.cxf.interoperability.Evento[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.wego.cross.webservices.cxf.interoperability.Evento[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.wego.cross.webservices.cxf.interoperability.Evento[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof it.wego.cross.webservices.cxf.interoperability.CrossServicesException) {
              throw (it.wego.cross.webservices.cxf.interoperability.CrossServicesException) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
