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
    
    <title>PostResponse.jsp</title>
    
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
        <form method="post" action="<c:out value='${authRequest.formAction}' />">
            <input type="hidden" name="TARGET" value="<c:out value='${authRequest.target}' />">
            <input type="hidden" name="AuthRequest" value="<c:out value='${authRequest.request}' />">
            <input type="submit" value="Click here if you are not automatically redirected in 10 seconds"/>
        </form>
    </body>
</html>

