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
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page language="java" %>
<%@ page import="java.util.*, it.people.sirac.authentication.beans.*" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="authRequest"   scope="request" type="it.people.sirac.authentication.beans.AuthenticationRequestBean" />

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>PostAuthRequest.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="PostResponse page">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  
    <body onload="javascript:document.forms[0].submit()">
        <form method="post" action="<c:out value='${authRequest.formAction}'/>">
            <input type="hidden" name="TARGET" value="<c:out value='${authRequest.target}' />">
            <input type="hidden" name="AuthRequest" value="<c:out value='${authRequest.request}' />">
            <input type="submit" value="Click here if you are not automatically redirected in 10 seconds"/>
        </form>
    </body>
<!--    
    <body>
        <form method="post" action="<c:out value='${authRequest.formAction}'/>">
            <textarea rows=10 cols="80" name="formAction"><c:out value='${authRequest.formAction}' /></textarea><br>
            <textarea rows=10 cols="80" name="TARGET"><c:out value='${authRequest.target}' /></textarea><br>
            <textarea rows=10 cols="80" name="AuthRequest"><c:out value='${authRequest.request}' /></textarea><br>
            <input type="submit" value="Click here if you are not automatically redirected in 10 seconds"/>
        </form>
    </body>
-->    
</html>

