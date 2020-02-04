<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page language="java" %>
<%@ page import="java.util.*, it.people.sirac.authentication.beans.*" %>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:useBean id="authResponse"   scope="request" type="it.people.sirac.authentication.beans.AuthenticationResponseBean" />

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>PostAuthResponse</title>
    
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
        <form method="post" action="<c:out value='${authResponse.formAction}' />">
            <input type="hidden" name="authResponse" value="<c:out value='${authResponse.response}' />" />
            <input type="hidden" name="expiresOn" value="<c:out value='${authResponse.expirationDateTime}' />" />
            <input type="submit" value="Click here if you are not automatically redirected in 10 seconds"/>
        </form>
    </body>
<%--  
    <body>
        <form method="post" action="<c:out value='${authResponse.formAction}' />">
            Form Action:<br>
            <textarea rows=10 cols="80" name="formAction"><c:out value='${authResponse.formAction}' /></textarea><br>
            Auth Response :<br>
            <textarea rows=10 cols="80" name="authResponse"><c:out value='${authResponse.response}' /></textarea><br>
            Expires on: 
            <input type="text" name="expiresOn" size="80" value="<c:out value='${authResponse.expirationDateTime}' />"</input><br>
            Encrypted: <c:out value='${authResponse.encrypted}' /><br>
                <% 
                  if (authResponse.isEncrypted()) {
                   StringBuffer authResponseDecrypted;
                   StringBuffer authResponseExpirationDateTimeDecrypted;
                   authResponseDecrypted = SymmetricCryptoManager.decrypt(new StringBuffer(authResponse.getResponse()), keyFilePath, new StringBuffer(passphrase));
                   authResponseExpirationDateTimeDecrypted = 
                        SymmetricCryptoManager.decrypt(new StringBuffer(authResponse.getExpirationDateTime()), keyFilePath, new StringBuffer(passphrase));
                %>
                    Auth Response Expires on (Decrypted):<br>
                    <input type="text" name="expiresOn" size="40" value="<%= authResponseExpirationDateTimeDecrypted.toString() %>"</input><br>
                    Auth Response (Decrypted):<br>
                    <textarea rows=10 cols="80" name="authResponseB64Decrypted"><%= authResponseDecrypted.toString() %></textarea><br>
                <%
                   } else {
                
                     String authResponseB64Decoded = "";
                     if (Base64.isArrayByteBase64(authResponse.getResponse().getBytes())) {
                       authResponseB64Decoded = new String(Base64.decodeBase64(authResponse.getResponse().getBytes()));
                     }
                %>
                       Auth Response (Base 64 decoded):<br>
                       <textarea rows=10 cols="80" name="authResponseB64Decoded"><%= authResponseB64Decoded %></textarea><br>
                <%
                   }
                %>
            
            <input type="submit" value="Click here if you are not automatically redirected in 10 seconds"/>
        </form>
    </body>
--%>
   
</html>

