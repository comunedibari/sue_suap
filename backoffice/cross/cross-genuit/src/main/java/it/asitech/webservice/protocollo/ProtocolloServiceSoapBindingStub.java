/**
 * ProtocolloServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.asitech.webservice.protocollo;

import java.util.Collection;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.attachments.AttachmentPart;
import org.apache.axis.client.Call;

public class ProtocolloServiceSoapBindingStub extends org.apache.axis.client.Stub implements it.asitech.webservice.protocollo.ProtocolloServicePortType {
    private final java.util.Vector cachedSerClasses = new java.util.Vector();
    private final java.util.Vector cachedSerQNames = new java.util.Vector();
    private final java.util.Vector cachedSerFactories = new java.util.Vector();
    private final java.util.Vector cachedDeserFactories = new java.util.Vector();

    private boolean debug = false;
    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[11];
        _initOperationDesc1();
        _initOperationDesc2();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PRIGetSid");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "WF_USER"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "WF_PASSWORD"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIGetSidResponse"));
        oper.setReturnClass(it.asitech.webservice.protocollo.PRIGetSidResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "PRIGetSidReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PRIEndSession");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ResponseMessage"));
        oper.setReturnClass(it.asitech.webservice.protocollo.ResponseMessage.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "PRIEndSessionReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PRIGetClassInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "className"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIGetClassInfoResponse"));
        oper.setReturnClass(it.asitech.webservice.protocollo.PRIGetClassInfoResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "PRIGetClassInfoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PRIGetObject");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "className"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIObjectOut"));
        oper.setReturnClass(it.asitech.webservice.protocollo.PRIObjectOut.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "PRIGetObjectReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PRISaveObject");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "priObj"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PriObject"), it.asitech.webservice.protocollo.PriObject.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIObjectOut"));
        oper.setReturnClass(it.asitech.webservice.protocollo.PRIObjectOut.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "PRISaveObjectReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PRIProtocollaDoc");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "annoFascicolo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceFascicolo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "priObj"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PriObject"), it.asitech.webservice.protocollo.PriObject.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIObjectOut"));
        oper.setReturnClass(it.asitech.webservice.protocollo.PRIObjectOut.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "PRIProtocollaDocReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PRIRegistraDoc");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "priObj"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PriObject"), it.asitech.webservice.protocollo.PriObject.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "annoFascicolo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codiceFascicolo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIObjectOut"));
        oper.setReturnClass(it.asitech.webservice.protocollo.PRIObjectOut.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "PRIRegistraDocReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PRIQuery");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "className"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "queryString"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "elencoCampiOut"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ArrayOf_soapenc_string"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "page"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIQueryObjectOut"));
        oper.setReturnClass(it.asitech.webservice.protocollo.PRIQueryObjectOut.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "PRIQueryReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PRIDocumentiFascicolo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "anno"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "elencoCampiOut"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ArrayOf_soapenc_string"), java.lang.String[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIQueryObjectOut"));
        oper.setReturnClass(it.asitech.webservice.protocollo.PRIQueryObjectOut.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "PRIDocumentiFascicoloReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PRIGetFile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idFile"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIObjectOut"));
        oper.setReturnClass(it.asitech.webservice.protocollo.PRIObjectOut.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "PRIGetFileReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PRIGetFileOrig");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "user"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "idDoc"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIObjectOut"));
        oper.setReturnClass(it.asitech.webservice.protocollo.PRIObjectOut.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "PRIGetFileOrigReturn"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[10] = oper;

    }

    public ProtocolloServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ProtocolloServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ProtocolloServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", ">ErrorMessage>level");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.ErrorMessageLevel.class;
            this.cachedSerClasses.add(cls);
            this.cachedSerFactories.add(enumsf);
            this.cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ArrayOf_soapenc_string");
            this.cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            this.cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string");
            qName2 = null;
            this.cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            this.cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ArrayOf_xsd_anyType");
            this.cachedSerQNames.add(qName);
            cls = java.lang.Object[].class;
            this.cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType");
            qName2 = null;
            this.cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            this.cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ArrayOfErrorMessage");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.ErrorMessage[].class;
            this.cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ErrorMessage");
            qName2 = null;
            this.cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            this.cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ArrayOfField");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.Field[].class;
            this.cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "Field");
            qName2 = null;
            this.cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            this.cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ArrayOfFieldDef");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.FieldDef[].class;
            this.cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "FieldDef");
            qName2 = null;
            this.cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            this.cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ArrayOfPriObject");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.PriObject[].class;
            this.cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PriObject");
            qName2 = null;
            this.cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            this.cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ClassDef");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.ClassDef.class;
            this.cachedSerClasses.add(cls);
            this.cachedSerFactories.add(beansf);
            this.cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ErrorMessage");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.ErrorMessage.class;
            this.cachedSerClasses.add(cls);
            this.cachedSerFactories.add(beansf);
            this.cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "Field");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.Field.class;
            this.cachedSerClasses.add(cls);
            this.cachedSerFactories.add(beansf);
            this.cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "FieldDef");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.FieldDef.class;
            this.cachedSerClasses.add(cls);
            this.cachedSerFactories.add(beansf);
            this.cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIGetClassInfoResponse");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.PRIGetClassInfoResponse.class;
            this.cachedSerClasses.add(cls);
            this.cachedSerFactories.add(beansf);
            this.cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIGetSidResponse");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.PRIGetSidResponse.class;
            this.cachedSerClasses.add(cls);
            this.cachedSerFactories.add(beansf);
            this.cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PriObject");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.PriObject.class;
            this.cachedSerClasses.add(cls);
            this.cachedSerFactories.add(beansf);
            this.cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIObjectOut");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.PRIObjectOut.class;
            this.cachedSerClasses.add(cls);
            this.cachedSerFactories.add(beansf);
            this.cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIQueryObjectOut");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.PRIQueryObjectOut.class;
            this.cachedSerClasses.add(cls);
            this.cachedSerFactories.add(beansf);
            this.cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "ResponseMessage");
            this.cachedSerQNames.add(qName);
            cls = it.asitech.webservice.protocollo.ResponseMessage.class;
            this.cachedSerClasses.add(cls);
            this.cachedSerFactories.add(beansf);
            this.cachedDeserFactories.add(beandf);

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
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < this.cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) this.cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) this.cachedSerQNames.get(i);
                        java.lang.Object x = this.cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 this.cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 this.cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 this.cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 this.cachedDeserFactories.get(i);
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

    public it.asitech.webservice.protocollo.PRIGetSidResponse PRIGetSid(java.lang.String WF_USER, java.lang.String WF_PASSWORD) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIGetSid"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {WF_USER, WF_PASSWORD});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            this.printMsg( _call );
            extractAttachments(_call);
            try {
                return (it.asitech.webservice.protocollo.PRIGetSidResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.asitech.webservice.protocollo.PRIGetSidResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.asitech.webservice.protocollo.PRIGetSidResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.asitech.webservice.protocollo.ResponseMessage PRIEndSession(java.lang.String sid) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIEndSession"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sid});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            this.printMsg( _call );
            extractAttachments(_call);
            try {
                return (it.asitech.webservice.protocollo.ResponseMessage) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.asitech.webservice.protocollo.ResponseMessage) org.apache.axis.utils.JavaUtils.convert(_resp, it.asitech.webservice.protocollo.ResponseMessage.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.asitech.webservice.protocollo.PRIGetClassInfoResponse PRIGetClassInfo(java.lang.String sid, java.lang.String user, java.lang.String className) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIGetClassInfo"));


        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sid, user, className});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            this.printMsg( _call );

            extractAttachments(_call);
            try {
                return (it.asitech.webservice.protocollo.PRIGetClassInfoResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.asitech.webservice.protocollo.PRIGetClassInfoResponse) org.apache.axis.utils.JavaUtils.convert(_resp, it.asitech.webservice.protocollo.PRIGetClassInfoResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.asitech.webservice.protocollo.PRIObjectOut PRIGetObject(java.lang.String sid, java.lang.String user, int id, java.lang.String className) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIGetObject"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sid, user, new java.lang.Integer(id), className});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            this.printMsg( _call );
            extractAttachments(_call);
            try {
                return (it.asitech.webservice.protocollo.PRIObjectOut) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.asitech.webservice.protocollo.PRIObjectOut) org.apache.axis.utils.JavaUtils.convert(_resp, it.asitech.webservice.protocollo.PRIObjectOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.asitech.webservice.protocollo.PRIObjectOut PRISaveObject(java.lang.String sid, java.lang.String user, it.asitech.webservice.protocollo.PriObject priObj) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRISaveObject"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sid, user, priObj});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            this.printMsg( _call );
            extractAttachments(_call);
            try {
                return (it.asitech.webservice.protocollo.PRIObjectOut) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.asitech.webservice.protocollo.PRIObjectOut) org.apache.axis.utils.JavaUtils.convert(_resp, it.asitech.webservice.protocollo.PRIObjectOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public PRIObjectOut PRIProtocollaDoc(String sid, String user, int annoFascicolo, String codiceFascicolo, PriObject priObj, Collection<AttachmentPart> attach)
    throws java.rmi.RemoteException
    {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIProtocollaDoc"));

        //setRequestHeaders(_call);
        //setAttachments(_call);

        for( AttachmentPart part : attach ) {
            _call.addAttachmentPart(part);
            //log.info( "fileNAme="+part.getAttachmentFile() );
        }

 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sid, user, new java.lang.Integer(annoFascicolo), codiceFascicolo, priObj});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            this.printMsg( _call );
            extractAttachments(_call);
            try {
                return (it.asitech.webservice.protocollo.PRIObjectOut) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.asitech.webservice.protocollo.PRIObjectOut) org.apache.axis.utils.JavaUtils.convert(_resp, it.asitech.webservice.protocollo.PRIObjectOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public PRIObjectOut PRIRegistraDoc(String sid, String user, PriObject priObj, int annoFascicolo, String codiceFascicolo, Collection<AttachmentPart> attach)
    throws java.rmi.RemoteException
    {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://protocollo.webservice.genuit.asimantova.it/PRIRegistraDoc");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIRegistraDoc"));

        //_call.setProperty( Call.ATTACHMENT_ENCAPSULATION_FORMAT,
        //                 Call.ATTACHMENT_ENCAPSULATION_FORMAT_DIME );

      //AttachmentUtil au = new AttachmentUtil();
      //au.addFile( this, new String[]{"D:\\workspace\\genuit-pri-ws-client\\src\\test\\Test.java","D:\\workspace\\genuit-pri-ws-client\\src\\test\\log4j.properties"});

      //MessageContext msgContext = MessageContext.getCurrentContext();
      //Message reqMsg = msgContext.getRequestMessage();

        for( AttachmentPart part : attach ) {
            _call.addAttachmentPart(part);
            //log.info( "fileNAme="+part.getAttachmentFile() );
        }
      //setRequestHeaders(_call);
      //setAttachments(_call);

 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sid, user, priObj, new java.lang.Integer(annoFascicolo), codiceFascicolo});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            this.printMsg( _call );
            extractAttachments(_call);
            try {
                return (it.asitech.webservice.protocollo.PRIObjectOut) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.asitech.webservice.protocollo.PRIObjectOut) org.apache.axis.utils.JavaUtils.convert(_resp, it.asitech.webservice.protocollo.PRIObjectOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.asitech.webservice.protocollo.PRIQueryObjectOut PRIQuery(java.lang.String sid, java.lang.String user, java.lang.String className, java.lang.String queryString, java.lang.String[] elencoCampiOut, int page) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIQuery"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sid, user, className, queryString, elencoCampiOut, new java.lang.Integer(page)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            this.printMsg( _call );
            extractAttachments(_call);
            try {
                return (it.asitech.webservice.protocollo.PRIQueryObjectOut) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.asitech.webservice.protocollo.PRIQueryObjectOut) org.apache.axis.utils.JavaUtils.convert(_resp, it.asitech.webservice.protocollo.PRIQueryObjectOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.asitech.webservice.protocollo.PRIQueryObjectOut PRIDocumentiFascicolo(java.lang.String sid, java.lang.String user, int anno, java.lang.String codice, java.lang.String[] elencoCampiOut) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIDocumentiFascicolo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sid, user, new java.lang.Integer(anno), codice, elencoCampiOut});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            this.printMsg( _call );
            extractAttachments(_call);
            try {
                return (it.asitech.webservice.protocollo.PRIQueryObjectOut) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.asitech.webservice.protocollo.PRIQueryObjectOut) org.apache.axis.utils.JavaUtils.convert(_resp, it.asitech.webservice.protocollo.PRIQueryObjectOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.asitech.webservice.protocollo.PRIObjectOut PRIGetFile(java.lang.String sid, java.lang.String user, int idFile) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIGetFile"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sid, user, new java.lang.Integer(idFile)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            this.printMsg( _call );
            extractAttachments(_call);
            /*
            Object o[] = this.getAttachments();
            if( o!=null ){
                AttachmentPart ap[] = new AttachmentPart[o.length];
                for( int i=0; i<o.length; i++ ) {
                    ap[i] = (AttachmentPart)o[i];
                }
                exp.setAttachs( ap );
            }
            */

            try {
                return (it.asitech.webservice.protocollo.PRIObjectOut) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.asitech.webservice.protocollo.PRIObjectOut) org.apache.axis.utils.JavaUtils.convert(_resp, it.asitech.webservice.protocollo.PRIObjectOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.asitech.webservice.protocollo.PRIObjectOut PRIGetFileOrig(java.lang.String sid, java.lang.String user, int idDoc) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://protocollo.webservice.genuit.asimantova.it", "PRIGetFileOrig"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sid, user, new java.lang.Integer(idDoc)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            //this.printMsg( _call );
            extractAttachments(_call);
            try {
                return (it.asitech.webservice.protocollo.PRIObjectOut) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.asitech.webservice.protocollo.PRIObjectOut) org.apache.axis.utils.JavaUtils.convert(_resp, it.asitech.webservice.protocollo.PRIObjectOut.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }


    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug( boolean debug ) {
        this.debug = debug;
    }

    private void printMsg(Call _call) throws java.rmi.RemoteException
    {
        if( true || !this.debug ) return;
        MessageContext mc = _call.getMessageContext();
        Message ms = mc.getRequestMessage();

        System.out.println("request:");
        org.apache.axis.SOAPPart sp = (org.apache.axis.SOAPPart)ms.getSOAPPart();
        //System.out.println("SOAPPart.getAsString");
        System.out.println(sp.getAsString());
        //System.out.println("SOAPPart.toString");
        //System.out.println(sp.toString());

        //org.apache.axis.message.SOAPEnvelope se = sp.getAsSOAPEnvelope();
        //System.out.println("SOAPEnvelope ");
        //System.out.println(se.toString());

        System.out.println("response:");
        ms = mc.getResponseMessage();
        sp = (org.apache.axis.SOAPPart)ms.getSOAPPart();
        System.out.println(sp.getAsString());
    }

}
