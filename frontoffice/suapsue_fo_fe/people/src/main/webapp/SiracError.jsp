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
<%@ page isErrorPage="true" %>
<html lang="it" dir="ltr">
<head>
	<c:out value="${it_people_css}" escapeXml="false" />
	<c:out value="${it_people_meta}" escapeXml="false" />
	<title><c:out value="${it_people_pageTitle}" /></title>
</head>

<body>
<%--@ include file="/WEB-INF/jsp/include/menu.jsp" --%>
<%
	String errorMessage = (String)request.getAttribute("errorMessage");
 %>
<%-- <jsp:useBean id="errorMessage"   scope="request" type="java.lang.String" /> --%>

<div id="content">

<h1>Errore</h1>

<%--
<p>Si è verificato un errore di tipo
    <b><%= exception.getClass().getName() %></b> durante l'elaborazione di 
    <b><%= request.getAttribute("javax.servlet.error.request_uri") %></b>
</p>
--%>    

<%-- SPOSTATO NEI DETTAGLI
<p>Si è verificato un errore durante l'elaborazione di 
    <b><%= request.getAttribute("javax.servlet.error.request_uri") %></b>
</p>
--%>
<p>
  <%--<b><%= exception.getMessage() %></b><br>--%>
  <c:if test="${errorMessage != null}">
    <b><c:out value='${errorMessage}'/></b><br>
  </c:if>
</p>

<%   
   String returnURL = (String)application.getInitParameter("people.sirac.error.returnURL");   
   if(returnURL != null)   {
 %>
 
 </p>

</div> 

 	<br><br>
	<div align="center">
		<input type="button" style="width:100px" name="return" value="OK" onclick="location='<%=returnURL%>'"/>
    </div>
 <%
   }
 %>

   
</body>
</html>
