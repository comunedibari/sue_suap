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
<%@ page language="java" import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%
  String path     = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
  
  String queryString = request.getQueryString();
  String ca_domain_suffix = getServletContext().getInitParameter("IDP-People.domain.suffix");
  String smartCardLoginRedirectURL = (String)request.getAttribute("smartcardLoginRedirectURL");

%>
  <!--DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"-->
  <html>
    <head>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <title>IDP-People Demo - Servizio di Autenticazione</title>
        <link rel="stylesheet" href="styles/signIn.css" />
    </head>
    <body>
	    <span style="top: 0;left: 0;     
					 font-weight: bold; font-size: 18pt;
					 color: #3300FF;background-color: #E5E8EC;">
	    <%@ include file="./include/siracHeader.jsp" %>
	    </span>

<%

  // Legge i parametri passati nel caso la form di autenticazione 
  // venga presentata dal SIRAC Gateway. In questo caso l'IDP-People deve fare solo l'autenticazione  
  String userID = (String)request.getParameter("uid");
  String userpwd = (String)request.getParameter("upwd");
  // Legge il parametro con la pagina target su cui fare il post dei dati di autenticazione
  String targetRedirect = (String)request.getParameter("TARGET");
  String authType = (String)request.getParameter("authType");
  String upwdValue = null;
  
  if (authType==null) authType = (String) request.getAttribute("authType");  // FIX 2005-05-30
  
  String errorMessage = (String)request.getAttribute("errorMessage");
 
%>
        <br><br><br><br>
        <div id="signInBox" align="center">
          <div id="header">
            <div id="logo">
              <img src="images/logo.png" width="200" height="40" alt="PeopleImg" />
            </div>
          </div>
          <form action="<c:url value="/login"/>" method="post">
            <input type="hidden" name="hasLoginData" value="true"/>
          
            <% String authTypeValue = ("strong".equalsIgnoreCase(authType)) ? "strong" : "weak"; %>
            <input type="hidden" name="authType" value="<%=authTypeValue%>"/>
            <% String targetValue  = (targetRedirect==null) ? "" : targetRedirect; %>
            <input type="hidden" name="TARGET" value="<%=targetRedirect%>"/>
            <div id="form">
              <table align="center">
                <tr>
                  <th>
                    Username:
                  </th>
                  <td>
		            <% String uidValue = (userID==null) ? "" : userID; %>
                    <input type="text" name="uid" size="52" value="<%=uidValue%>"/>
                  </td>
                </tr>
                <tr>
                <% if ("weak".equalsIgnoreCase(authType)) {%>
                  <th>
                    Password:
                  </th>
                  <td>
                  	<% if (userpwd==null)  %>
		            <%   upwdValue = (userpwd==null) ? "" : userpwd; %>
                    <input type="password" name="upwd" size="52" value="<%=upwdValue%>"/>
                  </td>
                <% } else { %>
                  <th>
                    PIN:
                  </th>
                  <td>
                    <input type="password" name="upwd" size="52" />
                  </td>
                <% } //endif %>
                </tr>
              </table>
            </div>
            <div id="actions" align="right">
              <input type="image" src="images/signInButton.png" height="22" width="66" border="0" alt="Login" value="Login" />
              <!--<a href="links.html"><img src="images/signInButton.png" height="22" width="66" alt="Login" border="0" /></a> -->
            </div>
          </form>
           <%
          	if (errorMessage != null) {
          %>
            <div align="center"><%=errorMessage%></div>
          <%
          	}
          %>
        </div>
  
<%
	request.removeAttribute("errorMessage");
%>
      </body>
    </html>
