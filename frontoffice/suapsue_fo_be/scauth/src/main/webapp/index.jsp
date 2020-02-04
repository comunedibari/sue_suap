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
<%@ include file="./include/siracTop.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String smartCardRegistrationUrl = request.getSession().getServletContext().getInitParameter("smartCardRegistrationURL");
%>

<html lang="it" dir="ltr">
  <head>
	<c:out value="${it_css}" escapeXml="false" />
	<c:out value="${it_meta}" escapeXml="false" />
	<title><c:out value="${it_pageTitle}" /></title>

	 <link rel="stylesheet" href="styles/style2.css" />
  </head>

  <body>
	<div style="top: 0;left: 0;     
					 font-weight: bold; font-size: 18pt;
					 background-color: #E5E8EC;">
	    <%@ include file="./include/siracHeader.jsp" %>
	</div>
	
	<div align="center">
	    <h1>CA-People Demo</h1>
        <p>Esempio di integrazione di sistema di autenticazione esterno</p>
	</div>
    <br>

  </body>
</html>
