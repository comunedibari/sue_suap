<%--
Copyright (c) 2011, Regione Emilia-Romagna, Italy
 
Licensed under the EUPL, Version 1.1 or - as soon they
will be approved by the European Commission - subsequent
versions of the EUPL (the "Licence");
You may not use this work except in compliance with the
Licence.

For convenience a plain text copy of the English version
of the Licence can be found in the file LICENCE.txt in
the top-level directory of this software distribution.

You may obtain a copy of the Licence in any of 22 European
Languages at:

http://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.

This product includes software developed by Yale University

See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<% String ws_remoteSign_address = request.getSession().getServletContext().getInitParameter("ws_remoteSign_address"); 
   String ws_remoteSign_timeout = request.getSession().getServletContext().getInitParameter("ws_remoteSign_timeout");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
  <style type="text/css">
     body {background-color: rgb(239,235,222); margin-left: 0px}
  </style>
    <%--
    <base href="<%=basePath%>">
    --%>
    <title>Firma remota</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <SCRIPT language="JavaScript" type="text/javascript">
        function getFieldValue(fieldName){
          var coll = window.opener.document.getElementsByName(fieldName);
          return coll[0].value;
        }

        function setFieldValue(fieldName, fieldValue){
          var coll = window.opener.document.getElementsByName(fieldName);
          coll[0].value = fieldValue;
        }
        
        function send(formName){ 
          var coll = window.opener.document.getElementsByName(formName);       
          //coll[0].submit(); 
          window.opener.document.forms[0].action="signStepComplete.do";
          window.opener.document.forms[0].submit();
        }
    </SCRIPT>
    
    
  </head>
  
  <body>
   <jsp:plugin 
      type="applet" 
      code="it.cefriel.people.remotesign.RemoteSignClientApplet.class" 
      codebase="./archive/"
      archive="RemoteSignAppletSigned.jar,bcmail-jdk15-130.jar,bcprov-jdk15-130.jar,ws_support_signed.jar"
      jreversion="1.5"
      width="210"
      height="95"
      nspluginurl="http://java.sun.com/products/plugin/index.html#download"
      iepluginurl="http://java.sun.com/update/1.5.0/jinstall-1_5-windows-i586.cab#Version=1,5,0,0">
      <jsp:params>
              <jsp:param name="IRemoteSignWSURL" value="<%=ws_remoteSign_address%>"/>
              <jsp:param name="IRemoteSignWSTimeout" value="<%=ws_remoteSign_timeout%>"/>
              <jsp:param name="IRemoteSignWSUnsignedDataInputField" value="<%=request.getParameter("IRemoteSignWSUnsignedDataInputField")%>"/>
              <jsp:param name="IRemoteSignWSSignedDataOutputField" value="<%=request.getParameter("IRemoteSignWSSignedDataOutputField")%>"/>
              <jsp:param name="IRemoteSignWSUsernameInputField" value="<%=request.getParameter("IRemoteSignWSUsernameInputField")%>"/>
              <jsp:param name="IRemoteSignWSSignModeInputField" value="<%=request.getParameter("IRemoteSignWSSignModeInputField")%>"/>
              <jsp:param name="IRemoteSignWSSignedDataOutputFormName" value="<%=request.getParameter("IRemoteSignWSSignedDataOutputFormName")%>"/>
              <jsp:param name="TrustoreFilename" value="<%=request.getParameter("TrustoreFilename")%>"/>
              <jsp:param name="TrustorePassword" value="<%=request.getParameter("TrustorePassword")%>"/>
              <jsp:param name="MAYSCRIPT" value="true"/>
              <jsp:param name="SCRIPTABLE" value="true" />
      </jsp:params>
      <jsp:fallback>
        <p>Impossibile attivare Java Plugin</p>
      </jsp:fallback>
     
    </jsp:plugin>
  </body>
</html>

<!-- nspluginurl="http://java.sun.com/update/1.4.2/j2re-1_4_2_07-windows-i586.xpi" -->
<!-- iepluginurl="http://java.sun.com/products/plugin/autodl/jinstall-1_4_2_07-windows-i586.cab" >-->
<!-- archive="RemoteSignAppletSigned.jar,bcmail-jdk15-130.jar,bcprov-jdk15-130.jar,commons-codec-1.3.jar,jaxrpc.jar,axis.jar,commons-logging.jar,commons-discovery.jar" -->
