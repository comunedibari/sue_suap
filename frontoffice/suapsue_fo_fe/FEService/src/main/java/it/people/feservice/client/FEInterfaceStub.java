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

import it.people.feservice.beans.CommunePackageVO;
import it.people.feservice.beans.FEServiceReferenceVO;
import it.people.feservice.beans.FeServiceChangeResult;
import it.people.feservice.beans.IndicatorFilter;
import it.people.feservice.beans.IndicatorsVO;
import it.people.feservice.beans.NodeDeployedServices;
import it.people.feservice.beans.ProcessFilter;
import it.people.feservice.beans.ProcessesVO;
import it.people.feservice.beans.ServiceOnlineHelpWorkflowElements;
import it.people.feservice.beans.ServiceVO;
import it.people.feservice.beans.UserNotificationDataVO;
import it.people.feservice.beans.VelocityTemplateDataVO;

import java.rmi.RemoteException;
import java.util.Calendar;

public class FEInterfaceStub extends org.apache.axis.client.Stub implements it.people.feservice.FEInterface {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[46];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
        _initOperationDesc4();
        _initOperationDesc5();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("echo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "word"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "echoReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("registerNode");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "comune"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "descrizione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "announcementMessage"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "showAnnouncement"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), java.lang.Boolean.class, false, false);
        oper.addParameter(param);
        
        //Flag Firma on-line  off-line
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "onlineSign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), java.lang.Boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "offlineSign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), java.lang.Boolean.class, false, false);
        oper.addParameter(param);
        
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("registerNodeWithAoo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "comune"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "codice"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "descrizione"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "aooPrefix"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "announcementMessage"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "showAnnouncement"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), java.lang.Boolean.class, false, false);
        oper.addParameter(param);
        
        //Flag Firma on-line  off-line
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "onlineSign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), java.lang.Boolean.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "offlineSign"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), java.lang.Boolean.class, false, false);
        oper.addParameter(param);
        
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("registerService");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "packageName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ServiceVO"));
        oper.setReturnClass(it.people.feservice.beans.ServiceVO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "registerServiceReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("configureService");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "theService"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ServiceVO"), it.people.feservice.beans.ServiceVO.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllLogs");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "LogBean"));
        oper.setReturnClass(it.people.feservice.beans.LogBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAllLogsReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getLogsForService");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "serviceName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "logLevel"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "LogBean"));
        oper.setReturnClass(it.people.feservice.beans.LogBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getLogsForServiceReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getLogsForDate");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "LogBean"));
        oper.setReturnClass(it.people.feservice.beans.LogBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getLogsForDateReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getLogsForDateAndService");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "serviceName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "logLevel"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "LogBean"));
        oper.setReturnClass(it.people.feservice.beans.LogBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getLogsForDateAndServiceReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deleteServiceByPackage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "packageName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "deleteServiceByPackageReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

   	private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deleteAllServices");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "deleteAllServicesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[10] = oper;

    }

    private static void _initOperationDesc3() {

        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
    	
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNodeDeployedServices");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "NodeDeployedServices"));
        oper.setReturnClass(it.people.feservice.beans.NodeDeployedServices.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getNodeDeployedServices"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("nodeCopy");

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "selectedServices"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"), 
        		java.lang.String[].class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "areasLogicalNamesPrefix"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"), 
        		java.lang.String[].class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "areasLogicalNamesSuffix"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"), 
        		java.lang.String[].class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "servicesLogicalNamesPrefix"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"), 
        		java.lang.String[].class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "servicesLogicalNamesSuffix"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"), 
        		java.lang.String[].class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "fromCommuneId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "toCommuneId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "nodeCopyReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[12] = oper;
        
    }
    
    private static void _initOperationDesc4() {

    	org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("configureServiceParameter");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "parameterVO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ConfigParameterVO"), it.people.feservice.beans.ConfigParameterVO.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("configureServiceReference");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "referenceVO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://beans.feservice.people.it/", "DependentModuleVO"), it.people.feservice.beans.DependentModuleVO.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[14] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAllAuditConversationsForComune");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "startingPoint"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "duration"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditConversationsBean"));
        oper.setReturnClass(it.people.feservice.beans.AuditConversationsBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAllAuditConversationsForComuneReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[15] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAuditConversationsForAllParameters");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "taxCode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "processName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "startingPoint"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "duration"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditConversationsBean"));
        oper.setReturnClass(it.people.feservice.beans.AuditConversationsBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditConversationsForAllParametersReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[16] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAuditConversationsForService");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "taxCode"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "processName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "startingPoint"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "duration"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditConversationsBean"));
        oper.setReturnClass(it.people.feservice.beans.AuditConversationsBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditConversationsForServiceReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[17] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAuditConversationsForDate");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "from"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "to"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "startingPoint"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "duration"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditConversationsBean"));
        oper.setReturnClass(it.people.feservice.beans.AuditConversationsBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditConversationsForDateReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[18] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAuditUsersForComune");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "String"));
        oper.setReturnClass(java.lang.String[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditUsersForComuneReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[19] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAuditUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "userid"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "userAccrId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditUserBean"));
        oper.setReturnClass(it.people.feservice.beans.AuditUserBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditUserReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[20] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAuditConversation");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditConversationsBean"));
        oper.setReturnClass(it.people.feservice.beans.AuditConversationsBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditConversationReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[21] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAuditFeBeXml");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "id"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditFeBeXmlBean"));
        oper.setReturnClass(it.people.feservice.beans.AuditFeBeXmlBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditFeBeXmlReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[22] = oper;
        

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getStatistiche");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "query"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditStatisticheBean"));
        oper.setReturnClass(it.people.feservice.beans.AuditStatisticheBean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getStatisticheReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[23] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAuditConversations");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "query"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "queryCount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditConversationsBean"));
        oper.setReturnClass(it.people.feservice.beans.AuditConversationsBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditConversationsReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateService");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "theService"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ServiceVO"), it.people.feservice.beans.ServiceVO.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getServiceOnlineHelpWorkflowElements");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "servicePackage"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ServiceOnlineHelpWorkflowElements"));
        oper.setReturnClass(it.people.feservice.beans.ServiceOnlineHelpWorkflowElements.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getServiceOnlineHelpWorkflowElements"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateBundle");
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "bundle"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "nodeId"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "locale"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "key"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "value"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "active"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "group"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[27] = oper;


        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("registerBundle");
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "bundle"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "nodeId"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "locale"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "active"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "group"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[28] = oper;
        

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getOrderedLogs");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "serviceName"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "logLevel"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "from"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "to"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "orderBy"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "orderType"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "LogBean"));
        oper.setReturnClass(it.people.feservice.beans.LogBean[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getOrderedLogsReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[29] = oper;

        

        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateFeServices");

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "services"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_ServiceVO"), 
        		it.people.feservice.beans.ServiceVO[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        
        _operations[30] = oper;
        

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deleteFeServicesByPackages");

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "servicesCommunePackage"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_CommunePackageVO"), 
        		it.people.feservice.beans.CommunePackageVO[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "FeServiceChangeResult"));
        oper.setReturnClass(it.people.feservice.beans.FeServiceChangeResult[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "deleteFeServicesByPackagesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        
        _operations[31] = oper;
        
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deleteBeServicesReferencesByPackages");

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "servicesReferences"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_FEServiceReferenceVO"), 
        		it.people.feservice.beans.FEServiceReferenceVO[].class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "deleteBeServicesReferencesByPackagesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        
        _operations[32] = oper;
        
        
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deleteBundle");
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "bundle"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "nodeId"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "locale"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
       
        _operations[33] = oper;
        
        
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("configureTableValueProperty");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "tableValueProperty"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://beans.feservice.people.it/", "TableValuePropertyVO"), it.people.feservice.beans.TableValuePropertyVO.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        
        _operations[34] = oper;
        
        
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("configureServiceAuditProcessor");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "serviceAuditProcessor"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ServiceAuditProcessorVO"), it.people.feservice.beans.ServiceAuditProcessorVO.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        
        _operations[35] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getMonitoringIndicators");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "indicatorFilter"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://beans.feservice.people.it/", "IndicatorFilter"), it.people.feservice.beans.IndicatorFilter.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "lowerPageLimit"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "pageSize"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "selectedEnti"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"), 
        		java.lang.String[].class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "selectedAttivita"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"), 
        		java.lang.String[].class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "retrieveAll"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"),
        		java.lang.Boolean.class, false, false);
        oper.addParameter(param);
        
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "IndicatorsVO"));
        oper.setReturnClass(it.people.feservice.beans.IndicatorsVO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getMonitoringIndicatorsReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
       
        _operations[36] = oper;
        
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getProcesses");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "processFilter"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessFilter"), it.people.feservice.beans.ProcessFilter.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "lowerPageLimit"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "pageSize"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "selectedUsers"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"), 
        		java.lang.String[].class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "selectedNodes"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"), 
        		java.lang.String[].class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "retrieveAll"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"),
        		java.lang.Boolean.class, false, false);
        oper.addParameter(param);
        
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessesVO"));
        oper.setReturnClass(it.people.feservice.beans.ProcessesVO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getProcessesReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
       
        _operations[37] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getProcessUsers");
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "String"));
        oper.setReturnClass(java.lang.String[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getProcessUsersReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[38] = oper;
        
	}
    

    private static void _initOperationDesc5(){
    	
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        
		oper = new org.apache.axis.description.OperationDesc();
		oper.setName("deleteProcesses");
		
		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName(
				"http://feservice.people.it/", "processFilter"),
				org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(
						"http://beans.feservice.people.it/", "ProcessFilter"),
				it.people.feservice.beans.ProcessFilter.class, false, false);
		oper.addParameter(param);

		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName(
				"http://feservice.people.it/", "selectedUsers"),
				org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(
						"http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"),
				java.lang.String[].class, false, false);
		oper.addParameter(param);

		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName(
				"http://feservice.people.it/", "selectedNodes"),
				org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(
						"http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"),
				java.lang.String[].class, false, false);
		oper.addParameter(param);

		param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName(
				"http://feservice.people.it/", "archiveInFile"),
				org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName(
						"http://www.w3.org/2001/XMLSchema", "boolean"), java.lang.Boolean.class, false, false);
		oper.addParameter(param);

		oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessesDeletionResultVO"));
		oper.setReturnClass(it.people.feservice.beans.ProcessesDeletionResultVO.class);
		oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "deleteProcessesReturn"));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
       
        _operations[39] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("setAsPeopleAdministrator");

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "userId"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "eMail"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "userName"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "allowedCommune"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "ArrayOf_soapenc_string"), 
        		java.lang.String[].class, false, false);
        oper.addParameter(param);

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "mailReceiverTypeFlags"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);

        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[40] = oper;
    	
    	
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("removeFromPeopleAdministrator");

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "userId"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[41] = oper;


        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getPeopleAdministrator");

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "userId"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "PeopleAdministratorVO"));
        oper.setReturnClass(it.people.feservice.beans.PeopleAdministratorVO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getPeopleAdministratorReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
       
        _operations[42] = oper;
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getVelocityTemplatesData");
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"),
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
        		java.lang.String.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "servicePackage"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
        		java.lang.String.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "retrieveAll"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), 
        		java.lang.Boolean.class, false, false);
        oper.addParameter(param);
       
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "VelocityTemplateDataVO"));
        oper.setReturnClass(it.people.feservice.beans.VelocityTemplateDataVO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getVelocityTemplatesDataReturn"));
        
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        
        _operations[43] = oper;
        
        
       
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateVelocityTemplatesData");
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "templateDataVO"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://beans.feservice.people.it/", "VelocityTemplateDataVO"), 
        		it.people.feservice.beans.VelocityTemplateDataVO.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "delete"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), 
        		java.lang.Boolean.class, false, false);
        oper.addParameter(param);
       
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "updateVelocityTemplatesDataReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        
        _operations[44] = oper;
        
        
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getUserNotifications");

        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "processFilter"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessFilter"), it.people.feservice.beans.ProcessFilter.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "lowerPageLimit"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "pageSize"), 
        		org.apache.axis.description.ParameterDesc.IN, 
        		new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "type"),
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
        		java.lang.String.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "userId"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
        		java.lang.String.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "firstname"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
        		java.lang.String.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "lastname"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
        		java.lang.String.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "email"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
        		java.lang.String.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "communeId"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
        		java.lang.String.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "from"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), 
        		java.util.Calendar.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "to"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), 
        		java.util.Calendar.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "sortType"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
        		java.lang.String.class, false, false);
        oper.addParameter(param);
        
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://feservice.people.it/", "sortColumn"), 
        		org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), 
        		java.lang.String.class, false, false);
        oper.addParameter(param);
        
        oper.setReturnType(new javax.xml.namespace.QName("http://beans.feservice.people.it/", "UserNotificationDataVO"));
        oper.setReturnClass(it.people.feservice.beans.UserNotificationDataVO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://feservice.people.it/", "getUserNotificationsReturn"));
        
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        
        _operations[45] = oper;
    }
    
    
    
    public FEInterfaceStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public FEInterfaceStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public FEInterfaceStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ConfigParameter");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.ConfigParameter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_soapenc_string");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "DependentModule");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.DependentModule.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "LogBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.LogBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditConversationsBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.AuditConversationsBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditStatisticheBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.AuditStatisticheBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditUserBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.AuditUserBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AuditFeBeXmlBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.AuditFeBeXmlBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "NodeDeployedServices");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.NodeDeployedServices.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AvailableService");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.AvailableService.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "CommuneBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.CommuneBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ServiceVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.ServiceVO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ConfigParameterVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.ConfigParameterVO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_ConfigParameter");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.ConfigParameter[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ConfigParameter");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_DependentModule");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.DependentModule[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "DependentModule");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_CommuneBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.CommuneBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "CommuneBean");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_AvailableService");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.AvailableService[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "AvailableService");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ServiceOnlineHelpWorkflowElements");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.ServiceOnlineHelpWorkflowElements.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "OnlineHelpViewData");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.OnlineHelpViewData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "OnlineHelpActivityData");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.OnlineHelpActivityData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "OnlineHelpStepData");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.OnlineHelpStepData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_OnlineHelpViewData");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.OnlineHelpViewData[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "OnlineHelpViewData");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
            

            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_OnlineHelpActivityData");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.OnlineHelpActivityData[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "OnlineHelpActivityData");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_OnlineHelpStepData");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.OnlineHelpStepData[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "OnlineHelpStepData");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            
          
            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_ServiceVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.ServiceVO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ServiceVO");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
            
            
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "CommunePackageVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.CommunePackageVO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);


            
            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_CommunePackageVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.CommunePackageVO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "CommunePackageVO");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
            
            

            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "FEServiceReferenceVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.FEServiceReferenceVO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            

            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_FEServiceReferenceVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.FEServiceReferenceVO[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "FEServiceReferenceVO");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "FeServiceChangeResult");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.FeServiceChangeResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            
            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_FeServiceChangeResult");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.FeServiceChangeResult[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "FeServiceChangeResult");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
            

            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "TableValuePropertyVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.TableValuePropertyVO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ServiceAuditProcessorVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.ServiceAuditProcessorVO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            
            /// Velocity Templates
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "VelocityTemplateBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.VelocityTemplateBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "VelocityTemplateDataVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.VelocityTemplateDataVO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_VelocityTemplateBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.VelocityTemplateBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "VelocityTemplateBean");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
            

            /// Monitoring:  IndicatorBean, IndicatorBrean[], IndicatorsVO, IndicatorFilter
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "IndicatorBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.IndicatorBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_IndicatorBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.IndicatorBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "IndicatorBean");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "IndicatorsVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.IndicatorsVO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "IndicatorFilter");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.IndicatorFilter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "PeopleAdministratorVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.PeopleAdministratorVO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            
            /// Processes ("pratiche") retrieve and deletion
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.ProcessBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_ProcessBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.ProcessBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessBean");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessesVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.ProcessesVO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "ProcessFilter");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.ProcessFilter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            
         // User Notifications
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "UserNotificationBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.UserNotificationBean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "UserNotificationDataVO");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.UserNotificationDataVO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);
            
            qName = new javax.xml.namespace.QName("http://feservice.people.it/", "ArrayOf_tns2_UserNotificationBean");
            cachedSerQNames.add(qName);
            cls = it.people.feservice.beans.UserNotificationBean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://beans.feservice.people.it/", "UserNotificationBean");
            qName2 = new javax.xml.namespace.QName("http://feservice.people.it/", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
	    
            
            
            
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

    public java.lang.String echo(java.lang.String word) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("echo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "echo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {word});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void registerNode(java.lang.String comune, java.lang.String codice, java.lang.String descrizione, java.lang.String announcementMessage, 
    		java.lang.Boolean showAnnouncement, java.lang.Boolean onlineSign, java.lang.Boolean offlineSign) throws java.rmi.RemoteException {
    	
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[1]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("registerNode");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "registerNode"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {comune, codice, descrizione, announcementMessage, showAnnouncement, onlineSign, offlineSign});

    	if (_resp instanceof java.rmi.RemoteException) {
    		throw (java.rmi.RemoteException)_resp;
    	}
    	extractAttachments(_call);
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
    }

    public void registerNodeWithAoo(java.lang.String comune, java.lang.String codice, java.lang.String descrizione, java.lang.String aooPrefix, 
    		java.lang.String announcementMessage, java.lang.Boolean showAnnouncement, java.lang.Boolean onlineSign, java.lang.Boolean offlineSign) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("registerNodeWithAoo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "registerNodeWithAoo"));
        
        setRequestHeaders(_call);
        setAttachments(_call);
        try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {comune, codice, descrizione, aooPrefix, announcementMessage, showAnnouncement, onlineSign, offlineSign});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.people.feservice.beans.ServiceVO registerService(java.lang.String communeId, java.lang.String packageName) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("registerService");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "registerService"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId, packageName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.people.feservice.beans.ServiceVO) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.people.feservice.beans.ServiceVO) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.ServiceVO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void configureService(it.people.feservice.beans.ServiceVO theService) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("configureService");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "configureService"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {theService});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }
  
    public it.people.feservice.beans.LogBean[] getAllLogs(java.lang.String communeId) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getAllLogs");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAllLogs"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.people.feservice.beans.LogBean[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.people.feservice.beans.LogBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.LogBean[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.people.feservice.beans.LogBean[] getLogsForService(java.lang.String communeId, java.lang.String serviceName, java.lang.String logLevel) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getLogsForService");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getLogsForService"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId, serviceName, logLevel});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.people.feservice.beans.LogBean[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.people.feservice.beans.LogBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.LogBean[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.people.feservice.beans.LogBean[] getLogsForDate(java.lang.String communeId, java.util.Calendar from, java.util.Calendar to) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getLogsForDate");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getLogsForDate"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId, from, to});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.people.feservice.beans.LogBean[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.people.feservice.beans.LogBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.LogBean[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public it.people.feservice.beans.LogBean[] getLogsForDateAndService(java.lang.String communeId, java.lang.String serviceName, java.lang.String logLevel, java.util.Calendar from, java.util.Calendar to) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getLogsForDateAndService");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getLogsForDateAndService"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId, serviceName, logLevel, from, to});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.people.feservice.beans.LogBean[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.people.feservice.beans.LogBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.LogBean[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public boolean deleteServiceByPackage(java.lang.String communeId, java.lang.String packageName) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("deleteServiceByPackage");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "deleteServiceByPackage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId, packageName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Boolean) _resp).booleanValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public boolean deleteAllServices(java.lang.String communeId) throws java.rmi.RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[10]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("deleteAllServices");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "deleteAllServices"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId});

    	if (_resp instanceof java.rmi.RemoteException) {
    		throw (java.rmi.RemoteException)_resp;
    	}
    	else {
    		extractAttachments(_call);
    		try {
    			return ((java.lang.Boolean) _resp).booleanValue();
    		} catch (java.lang.Exception _exception) {
    			return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
    		}
    	}
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
    }

	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterface#getMassiveRegistrationConfigs()
	 */
	public NodeDeployedServices getNodeDeployedServices(String communeId)
			throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getNodeDeployedServices");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getNodeDeployedServices"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.people.feservice.beans.NodeDeployedServices) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.people.feservice.beans.NodeDeployedServices) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.NodeDeployedServices.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
	}


    public boolean nodeCopy(String[] selectedServices, String[] areasLogicalNamesPrefix,
    		String[] areasLogicalNamesSuffix, String[] servicesLogicalNamesPrefix,
    		String[] servicesLogicalNamesSuffix, String fromCommuneId, String toCommuneId) throws java.rmi.RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[12]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("nodeCopy");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "nodeCopy"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {selectedServices, areasLogicalNamesPrefix, 
    			areasLogicalNamesSuffix, 
    			servicesLogicalNamesPrefix, 
    			servicesLogicalNamesSuffix, fromCommuneId, toCommuneId});

    	if (_resp instanceof java.rmi.RemoteException) {
    		throw (java.rmi.RemoteException)_resp;
    	}
    	else {
    		extractAttachments(_call);
    		try {
    			return ((java.lang.Boolean) _resp).booleanValue();
    		} catch (java.lang.Exception _exception) {
    			return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
    		}
    	}
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
    }
	
    
    public void configureServiceParameter(it.people.feservice.beans.ConfigParameterVO parameterVO) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("configureServiceParameter");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "configureServiceParameter"));

        setRequestHeaders(_call);
        setAttachments(_call);
		try {
			java.lang.Object _resp = _call
					.invoke(new java.lang.Object[] { parameterVO });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			}
			extractAttachments(_call);
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
    }

    public void configureServiceReference(it.people.feservice.beans.DependentModuleVO referenceVO) throws java.rmi.RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[14]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("configureServiceReference");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "configureServiceReference"));
    	
    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {
    		java.lang.Object _resp = _call
    		.invoke(new java.lang.Object[] { referenceVO });
    		
    		if (_resp instanceof java.rmi.RemoteException) {
    			throw (java.rmi.RemoteException) _resp;
    		}
    		extractAttachments(_call);
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
    }

	
	public it.people.feservice.beans.AuditConversationsBean[] getAllAuditConversationsForComune(java.lang.String communeId, java.lang.String startingPoint, java.lang.String duration) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getAllAuditConversationsForComune");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAllAuditConversationsForComune"));

        setRequestHeaders(_call);
        setAttachments(_call);
		 try {       
			 java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId, startingPoint, duration});
		
		     if (_resp instanceof java.rmi.RemoteException) {
		         throw (java.rmi.RemoteException)_resp;
		     }
			else {
			    extractAttachments(_call);
			    try {
			        return (it.people.feservice.beans.AuditConversationsBean[]) _resp;
			    } catch (java.lang.Exception _exception) {
			        return (it.people.feservice.beans.AuditConversationsBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.AuditConversationsBean[].class);
			    }
			}
		  } catch (org.apache.axis.AxisFault axisFaultException) {
		  throw axisFaultException;
		}
    }
	
	public it.people.feservice.beans.AuditConversationsBean[] getAuditConversationsForAllParameters(
			java.lang.String communeId, java.lang.String taxCode, java.lang.String processName,
			java.util.Calendar from, java.util.Calendar to, java.lang.String startingPoint, java.lang.String duration)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getAuditConversationsForAllParameters");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditConversationsForAllParameters"));

        setRequestHeaders(_call);
        setAttachments(_call);
		try {        
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId, taxCode, processName, from, to, startingPoint, duration});
		
		        if (_resp instanceof java.rmi.RemoteException) {
		            throw (java.rmi.RemoteException)_resp;
		        }
		        else {
		            extractAttachments(_call);
		            try {
		                return (it.people.feservice.beans.AuditConversationsBean[]) _resp;
		            } catch (java.lang.Exception _exception) {
		                return (it.people.feservice.beans.AuditConversationsBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.AuditConversationsBean[].class);
		            }
		        }
		  } catch (org.apache.axis.AxisFault axisFaultException) {
		  throw axisFaultException;
		}
	}
	
	public it.people.feservice.beans.AuditConversationsBean[] getAuditConversationsForService(
			java.lang.String communeId, java.lang.String taxCode, java.lang.String processName,
			java.lang.String startingPoint, java.lang.String duration)
	throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[17]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("getAuditConversationsForService");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditConversationsForService"));
		
		setRequestHeaders(_call);
		setAttachments(_call);
		try {        
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId, taxCode, processName, startingPoint, duration});
			
			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException)_resp;
			}
			else {
				extractAttachments(_call);
				try {
					return (it.people.feservice.beans.AuditConversationsBean[]) _resp;
				} catch (java.lang.Exception _exception) {
					return (it.people.feservice.beans.AuditConversationsBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.AuditConversationsBean[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}
	
	public it.people.feservice.beans.AuditConversationsBean[] getAuditConversationsForDate(java.lang.String communeId, java.util.Calendar from, java.util.Calendar to,
			java.lang.String startingPoint, java.lang.String duration) 
	throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getAuditConversationsForDate");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditConversationsForDate"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId, from, to, startingPoint, duration});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.people.feservice.beans.AuditConversationsBean[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.people.feservice.beans.AuditConversationsBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.AuditConversationsBean[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }
	
	public java.lang.String[] getAuditUsersForComune(java.lang.String communeId) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getAuditUsersForComune");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditUsersForComune"));

        setRequestHeaders(_call);
        setAttachments(_call);
		 try {       
			 java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId});
		
		     if (_resp instanceof java.rmi.RemoteException) {
		         throw (java.rmi.RemoteException)_resp;
		     }
			else {
			    extractAttachments(_call);
			    try {
			        return (java.lang.String[]) _resp;
			    } catch (java.lang.Exception _exception) {
			        return (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String[].class);
			    }
			}
		  } catch (org.apache.axis.AxisFault axisFaultException) {
		  throw axisFaultException;
		}
    }

	public it.people.feservice.beans.AuditUserBean getAuditUser(String userId, String userAccrId) throws RemoteException {
		if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getAuditUser");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
		 try {       
			 java.lang.Object _resp = _call.invoke(new java.lang.Object[] {userId, userAccrId});
		
		     if (_resp instanceof java.rmi.RemoteException) {
		         throw (java.rmi.RemoteException)_resp;
		     }
			else {
			    extractAttachments(_call);
			    try {
			        return (it.people.feservice.beans.AuditUserBean) _resp;
			    } catch (java.lang.Exception _exception) {
			        return (it.people.feservice.beans.AuditUserBean) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.AuditUserBean.class);
			    }
			}
		  } catch (org.apache.axis.AxisFault axisFaultException) {
		  throw axisFaultException;
		}
	}

	public it.people.feservice.beans.AuditConversationsBean getAuditConversation(String id)
			throws RemoteException {
		if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getAuditConversation");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditConversation"));

        setRequestHeaders(_call);
        setAttachments(_call);
		 try {       
			 java.lang.Object _resp = _call.invoke(new java.lang.Object[] {id});
		
		     if (_resp instanceof java.rmi.RemoteException) {
		         throw (java.rmi.RemoteException)_resp;
		     }
			else {
			    extractAttachments(_call);
			    try {
			        return (it.people.feservice.beans.AuditConversationsBean) _resp;
			    } catch (java.lang.Exception _exception) {
			        return (it.people.feservice.beans.AuditConversationsBean) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.AuditConversationsBean.class);
			    }
			}
		  } catch (org.apache.axis.AxisFault axisFaultException) {
		  throw axisFaultException;
		}
	}
    
	public it.people.feservice.beans.AuditFeBeXmlBean getAuditFeBeXml(String id)
	throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[22]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("getAuditFeBeXml");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getAuditFeBeXml"));
		
		setRequestHeaders(_call);
		setAttachments(_call);
		try {       
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {id});
			
			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException)_resp;
			}
			else {
				extractAttachments(_call);
				try {
					return (it.people.feservice.beans.AuditFeBeXmlBean) _resp;
				} catch (java.lang.Exception _exception) {
					return (it.people.feservice.beans.AuditFeBeXmlBean) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.AuditFeBeXmlBean.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}


	public it.people.feservice.beans.AuditStatisticheBean[] getStatistiche(java.lang.String query) 
	throws java.rmi.RemoteException {
	

        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getStatistiche");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getStatistiche"));

        setRequestHeaders(_call);
        setAttachments(_call);
		 try {       
			 java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query});
		
		     if (_resp instanceof java.rmi.RemoteException) {
		         throw (java.rmi.RemoteException)_resp;
		     }
			else {
			    extractAttachments(_call);
			    try {
			        return (it.people.feservice.beans.AuditStatisticheBean[]) _resp;
			    } catch (java.lang.Exception _exception) {
			        return (it.people.feservice.beans.AuditStatisticheBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.AuditStatisticheBean[].class);
			    }
			}
		  } catch (org.apache.axis.AxisFault axisFaultException) {
		  throw axisFaultException;
		}
    }
	
	
	public it.people.feservice.beans.AuditConversationsBean[] getAuditConversations(
			java.lang.String query, java.lang.String queryCount)
			throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[24]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("getAuditConversations");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName( "http://feservice.people.it/", "getAuditConversations"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {query, queryCount});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (it.people.feservice.beans.AuditConversationsBean[]) _resp;
				} catch (java.lang.Exception _exception) {
					return (it.people.feservice.beans.AuditConversationsBean[]) org.apache.axis.utils.JavaUtils
							.convert(_resp, it.people.feservice.beans.AuditConversationsBean[].class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

    /* (non-Javadoc)
     * @see it.people.feservice.FEInterface#updateService(it.people.feservice.beans.ServiceVO)
     */
    public void updateService(it.people.feservice.beans.ServiceVO theService) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("updateService");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "updateService"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {theService});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    
    /* (non-Javadoc)
     * @see it.people.feservice.FEInterface#getServiceOnlineHelpWorkflowElements(java.lang.String)
     */
    public ServiceOnlineHelpWorkflowElements getServiceOnlineHelpWorkflowElements(String servicePackage)
    throws RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[26]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("getServiceOnlineHelpWorkflowElements");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getServiceOnlineHelpWorkflowElements"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {servicePackage});

    	if (_resp instanceof java.rmi.RemoteException) {
    		throw (java.rmi.RemoteException)_resp;
    	}
    	else {
    		extractAttachments(_call);
    		try {
    			return (it.people.feservice.beans.ServiceOnlineHelpWorkflowElements) _resp;
    		} catch (java.lang.Exception _exception) {
    			return (it.people.feservice.beans.ServiceOnlineHelpWorkflowElements) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.ServiceOnlineHelpWorkflowElements.class);
    		}
    	}
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
    }

    public void updateBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale, 
    		java.lang.String key, java.lang.String value, 
    		java.lang.String active, java.lang.String group) throws java.rmi.RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[27]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("updateBundle");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "updateBundle"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bundle, nodeId, locale, key, value, active, group});

    	if (_resp instanceof java.rmi.RemoteException) {
    		throw (java.rmi.RemoteException)_resp;
    	}
    	extractAttachments(_call);
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
    }

    public void registerBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale, 
    		java.lang.String active, java.lang.String group) throws java.rmi.RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[28]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("registerBundle");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "registerBundle"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bundle, nodeId, locale, active, group});

    	if (_resp instanceof java.rmi.RemoteException) {
    		throw (java.rmi.RemoteException)_resp;
    	}
    	extractAttachments(_call);
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
    }
    
    public it.people.feservice.beans.LogBean[] getOrderedLogs(java.lang.String communeId, java.lang.String serviceName, 
    		java.lang.String logLevel, java.util.Calendar from, java.util.Calendar to, 
    		java.lang.String orderBy, java.lang.String orderType ) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getOrderedLogs");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getOrderedLogs"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId, serviceName, logLevel, from, to, orderBy, orderType});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (it.people.feservice.beans.LogBean[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.people.feservice.beans.LogBean[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.LogBean[].class);
            }
        }  
        } catch (org.apache.axis.AxisFault axisFaultException) {
        	throw axisFaultException;
        }
    }

	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterface#updateFeServices(it.people.feservice.beans.ServiceVO[])
	 */
	public void updateFeServices(ServiceVO[] services) throws RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[30]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("updateFeServices");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "updateFeServices"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {services});

    	if (_resp instanceof java.rmi.RemoteException) {
    		throw (java.rmi.RemoteException)_resp;
    	}
    	extractAttachments(_call);
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
	}

	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterface#deleteFeServicesByPackages(it.people.feservice.beans.CommunePackageVO[])
	 */
	public FeServiceChangeResult[] deleteFeServicesByPackages(
			CommunePackageVO[] servicesCommunePackage) throws RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[31]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("deleteFeServicesByPackages");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "deleteFeServicesByPackages"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {servicesCommunePackage});

    	if (_resp instanceof java.rmi.RemoteException) {
    		throw (java.rmi.RemoteException)_resp;
    	}
    	else {
    		extractAttachments(_call);
    		try {
    			return (it.people.feservice.beans.FeServiceChangeResult[]) _resp;
    		} catch (java.lang.Exception _exception) {
    			return (it.people.feservice.beans.FeServiceChangeResult[]) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.FeServiceChangeResult[].class);
    		}
    	}
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
	}

	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterface#deleteBeServicesReferencesByPackages(it.people.feservice.beans.FEServiceReferenceVO[])
	 */
	public boolean deleteBeServicesReferencesByPackages(
			FEServiceReferenceVO[] servicesReferences) throws RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[32]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("deleteBeServicesReferencesByPackages");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "deleteBeServicesReferencesByPackages"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {servicesReferences});

    	if (_resp instanceof java.rmi.RemoteException) {
    		throw (java.rmi.RemoteException)_resp;
    	}
    	else {
    		extractAttachments(_call);
    		try {
    			return ((java.lang.Boolean) _resp).booleanValue();
    		} catch (java.lang.Exception _exception) {
    			return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
    		}
    	}
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
	}

    /* (non-Javadoc)
     * @see it.people.feservice.FEInterface#deleteBundle(java.lang.String, java.lang.String, java.lang.String)
     */
    public void deleteBundle(java.lang.String bundle, java.lang.String nodeId, java.lang.String locale) throws java.rmi.RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[33]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("deleteBundle");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "deleteBundle"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {bundle, nodeId, locale});

    	if (_resp instanceof java.rmi.RemoteException) {
    		throw (java.rmi.RemoteException)_resp;
    	}
    	extractAttachments(_call);
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
    }
    
    
    /* (non-Javadoc)
     * @see it.people.feservice.FEInterface#configureTableValueProperty(it.people.feservice.beans.TableValuePropertyVO)
     */
    public void configureTableValueProperty(it.people.feservice.beans.TableValuePropertyVO tableValueProperty) throws java.rmi.RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[34]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("configureTableValueProperty");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "configureTableValueProperty"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        
    		java.lang.Object _resp = _call.invoke(new java.lang.Object[] {tableValueProperty});

    		if (_resp instanceof java.rmi.RemoteException) {
    			throw (java.rmi.RemoteException)_resp;
    		}
    		extractAttachments(_call);
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
    }
    
    /* (non-Javadoc)
     * @see it.people.feservice.FEInterface#configureServiceAuditProcessor(it.people.feservice.beans.ServiceAuditProcessorVO)
     */
    public void configureServiceAuditProcessor(it.people.feservice.beans.ServiceAuditProcessorVO serviceAuditProcessor) throws java.rmi.RemoteException {
    	if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[35]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("configureServiceAuditProcessor");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "configureServiceAuditProcessor"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        
    		java.lang.Object _resp = _call.invoke(new java.lang.Object[] {serviceAuditProcessor});

    		if (_resp instanceof java.rmi.RemoteException) {
    			throw (java.rmi.RemoteException)_resp;
    		}
    		extractAttachments(_call);
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
    }

	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterface#getMonitoringIndicators(it.people.feservice.beans.IndicatorFilter, int, int, java.lang.String[], java.lang.String[], boolean)
	 */
	public IndicatorsVO getMonitoringIndicators(IndicatorFilter indicatorFilter, int lowerPageLimit, int pageSize,
			String[] selectedEnti, String[] selectedAttivita, boolean retrieveAll)
			throws RemoteException {
		
		if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[36]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("getMonitoringIndicators");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getMonitoringIndicators"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {
					indicatorFilter, lowerPageLimit, pageSize, selectedEnti,
					selectedAttivita, retrieveAll});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
            extractAttachments(_call);
            try {
                return (it.people.feservice.beans.IndicatorsVO) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.people.feservice.beans.IndicatorsVO) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.IndicatorsVO.class);
            }
        }  
        } catch (org.apache.axis.AxisFault axisFaultException) {
        	throw axisFaultException;
        }
	}

	@Override
	public ProcessesVO getProcesses(ProcessFilter processFilter,
			int lowerPageLimit, int pageSize, String[] selectedUsers, String[] selectedNodes,
			boolean retrieveAll) throws RemoteException {

		if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[37]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("getProcesses");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getProcesses"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {
					processFilter, lowerPageLimit, pageSize, selectedUsers, selectedNodes, retrieveAll});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
            extractAttachments(_call);
            try {
                return (it.people.feservice.beans.ProcessesVO) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.people.feservice.beans.ProcessesVO) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.ProcessesVO.class);
            }
        }  
        } catch (org.apache.axis.AxisFault axisFaultException) {
        	throw axisFaultException;
        }
		
	}

	@Override
	public String[] getProcessUsers() throws RemoteException {
		
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[38]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("getProcessUsers");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getProcessUsers"));

        setRequestHeaders(_call);
        setAttachments(_call);
		 try {       
			 java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});
		
		     if (_resp instanceof java.rmi.RemoteException) {
		         throw (java.rmi.RemoteException)_resp;
		     }
			else {
			    extractAttachments(_call);
			    try {
			        return (java.lang.String[]) _resp;
			    } catch (java.lang.Exception _exception) {
			        return (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String[].class);
			    }
			}
		  } catch (org.apache.axis.AxisFault axisFaultException) {
		  throw axisFaultException;
		}
	    
	}

	@Override
	public it.people.feservice.beans.ProcessesDeletionResultVO deleteProcesses(ProcessFilter processFilter,
			String[] selectedUsers, String[] selectedNodes, boolean archiveInFile) throws RemoteException {
		
		if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[39]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("deleteProcesses");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "deleteProcesses"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {processFilter, selectedUsers,
					selectedNodes, archiveInFile});

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
            extractAttachments(_call);
            try {
                return (it.people.feservice.beans.ProcessesDeletionResultVO) _resp;
            } catch (java.lang.Exception _exception) {
                return (it.people.feservice.beans.ProcessesDeletionResultVO) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.ProcessesDeletionResultVO.class);
            }
        }  
        } catch (org.apache.axis.AxisFault axisFaultException) {
        	throw axisFaultException;
        }
	}
	
	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterface#setAsPeopleAdministrator(java.lang.String, java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
	 */
	public void setAsPeopleAdministrator(java.lang.String userId, java.lang.String eMail, java.lang.String userName, java.lang.String[] allowedCommune, java.lang.String mailReceiverTypeFlags)
			throws RemoteException {
		
		if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[40]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("setAsPeopleAdministrator");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "setAsPeopleAdministrator"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {
					userId, eMail, userName, allowedCommune, mailReceiverTypeFlags});

    		if (_resp instanceof java.rmi.RemoteException) {
    			throw (java.rmi.RemoteException)_resp;
    		}
    		extractAttachments(_call);
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
	}

	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterface#removeFromPeopleAdministrator(java.lang.String)
	 */
	public void removeFromPeopleAdministrator(java.lang.String userId)
			throws RemoteException {
		
		if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[41]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("removeFromPeopleAdministrator");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "removeFromPeopleAdministrator"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {
					userId});

    		if (_resp instanceof java.rmi.RemoteException) {
    			throw (java.rmi.RemoteException)_resp;
    		}
    		extractAttachments(_call);
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
	}

	
	
	/* (non-Javadoc)
	 * @see it.people.feservice.FEInterface#getPeopleAdministrator(java.lang.String)
	 */
	public it.people.feservice.beans.PeopleAdministratorVO getPeopleAdministrator(java.lang.String userId) throws java.rmi.RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[42]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("getPeopleAdministrator");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getPeopleAdministrator"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {userId});

		if (_resp instanceof java.rmi.RemoteException) {
			throw (java.rmi.RemoteException)_resp;
		}
		else {
			extractAttachments(_call);
			try {
				return (it.people.feservice.beans.PeopleAdministratorVO) _resp;
			} catch (java.lang.Exception _exception) {
				return (it.people.feservice.beans.PeopleAdministratorVO) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.PeopleAdministratorVO.class);
			}
		}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	@Override
	public VelocityTemplateDataVO getVelocityTemplatesData(String communeId, String servicePackage, boolean retrieveAll) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[43]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("getVelocityTemplatesData");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "getVelocityTemplatesData"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {communeId, servicePackage, retrieveAll});

		if (_resp instanceof java.rmi.RemoteException) {
			throw (java.rmi.RemoteException)_resp;
		}
		else {
			extractAttachments(_call);
			try {
				return (it.people.feservice.beans.VelocityTemplateDataVO) _resp;
			} catch (java.lang.Exception _exception) {
				return (it.people.feservice.beans.VelocityTemplateDataVO) org.apache.axis.utils.JavaUtils.convert(_resp, it.people.feservice.beans.VelocityTemplateDataVO.class);
			}
		}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
		
	}

	@Override
	public boolean updateVelocityTemplatesData(VelocityTemplateDataVO templateDataVO, boolean delete)
			throws RemoteException {
		
		if (super.cachedEndpoint == null) {
    		throw new org.apache.axis.NoEndPointException();
    	}
    	org.apache.axis.client.Call _call = createCall();
    	_call.setOperation(_operations[44]);
    	_call.setUseSOAPAction(true);
    	_call.setSOAPActionURI("updateVelocityTemplatesData");
    	_call.setEncodingStyle(null);
    	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
    	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    	_call.setOperationName(new javax.xml.namespace.QName("http://feservice.people.it/", "updateVelocityTemplatesData"));

    	setRequestHeaders(_call);
    	setAttachments(_call);
    	try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {templateDataVO, delete});

    	if (_resp instanceof java.rmi.RemoteException) {
    		throw (java.rmi.RemoteException)_resp;
    	}
    	else {
    		extractAttachments(_call);
    		try {
    			return ((java.lang.Boolean) _resp).booleanValue();
    		} catch (java.lang.Exception _exception) {
    			return ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, boolean.class)).booleanValue();
    		}
    	}
    	} catch (org.apache.axis.AxisFault axisFaultException) {
    		throw axisFaultException;
    	}
		
		
	}

    @Override
	public UserNotificationDataVO getUserNotifications(
			ProcessFilter processFilter, int lowerPageLimit, int pageSize,
			String type, String userId, String firstname, String lastname,
			String email, String communeId, Calendar from, Calendar to,
			String sortType, String sortColumn) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call = createCall();
		_call.setOperation(_operations[45]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("getUserNotifications");
		_call.setEncodingStyle(null);
		_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
				Boolean.FALSE);
		_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
				Boolean.FALSE);
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(
				"http://feservice.people.it/", "getUserNotifications"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {
			java.lang.Object _resp = _call.invoke(new java.lang.Object[] {
					processFilter, lowerPageLimit, pageSize, type, userId,
					firstname, lastname, email,
					communeId, from, to, sortType, sortColumn });

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) _resp;
			} else {
				extractAttachments(_call);
				try {
					return (it.people.feservice.beans.UserNotificationDataVO) _resp;
				} catch (java.lang.Exception _exception) {
					return (it.people.feservice.beans.UserNotificationDataVO) org.apache.axis.utils.JavaUtils
							.convert(
									_resp,
									it.people.feservice.beans.UserNotificationDataVO.class);
				}
			}
		} catch (org.apache.axis.AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}
    
}
