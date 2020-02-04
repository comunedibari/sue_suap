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
<%@ page language="java" 
         import="java.util.*,
				 it.people.sirac.accr.beans.*,
                 it.people.sirac.core.*" %>
<%-- JSTL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

pageContext.setAttribute("profiloOperatore", session.getAttribute(it.people.sirac.core.SiracConstants.SIRAC_ACCR_OPERATORE));

Boolean weakAuth = Boolean.valueOf((String)session.getAttribute(it.people.sirac.core.SiracConstants.SIRAC_CUR_SERVICE_WEAK_AUTHENTICATION_COMPLETED));
Boolean strongAuth = Boolean.valueOf((String)session.getAttribute(it.people.sirac.core.SiracConstants.SIRAC_CUR_SERVICE_STRONG_AUTHENTICATION_COMPLETED));


pageContext.setAttribute("weakAuthCompleted", weakAuth);
pageContext.setAttribute("strongAuthCompleted", strongAuth);

%>

<jsp:useBean id="profiloOperatore" 
             scope="page" 
             class="it.people.sirac.accr.beans.ProfiloPersonaFisica" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>JSP 'servizio.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
        <link rel="stylesheet" href="styles/style2.css" />
  </head>
  
  <body>
  
    <span style="top: 0;left: 0;     
         font-size: 18pt;
         color: #3300FF;background-color: #E5E8EC;">
      <%@ include file="/include/header.jsp" %>
    </span>

          <div id="signInBox" align="center">
	          <div id="header">
	              <img src="images/logo.png" width="200" height="40" alt="PeopleImg" />
	          </div>
             <div id="form">
              <b>Autenticazione effettuata con successo</b><br>
              <b>Tipo Autenticazione: </b>
              <c:choose>
                <c:when test="${strongAuthCompleted == true}">
					<b>Autenticazione forte</b>
				</c:when>
                <c:when test="${weakAuthCompleted == true}">
					<b>Autenticazione Debole</b>
				</c:when>
              </c:choose>
              <br><br>
              <table align="center">
                <tr>
                  <th>Nome:</th>
                  <td><c:out value="${profiloOperatore.nome}" /></td>
                </tr>
                <tr>
                  <th>Cognome:</th>
                  <td><c:out value="${profiloOperatore.cognome}" /></td>
                </tr>
                <tr>
                  <th>Codice Fiscale:</th>
                  <td><c:out value="${profiloOperatore.codiceFiscale}" /></td>
                </tr>
                <tr>
                  <th>Data di nascita:</th>
                  <td><c:out value="${profiloOperatore.dataNascitaString}" /></td>
                </tr>
                <tr>
                  <th>Luogo di nascita:</th>
                  <td><c:out value="${profiloOperatore.luogoNascita}" /></td>
                </tr>
                <tr>
                  <th>Provincia di nascita:</th>
                  <td><c:out value="${profiloOperatore.provinciaNascita}" /></td>
                </tr>
              </table>
            </div>
            <div id="actions" align="right">
              <br> 
            </div>
          </div>
   
  </body>
</html>
