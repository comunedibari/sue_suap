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
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.people.sirac.idp.beans.ResRegBean" %>
    
<%
	ResRegBean res = (ResRegBean)request.getAttribute("regResponse");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	
		<title>CA-People Demo - Errore durante la registrazione</title>
	    <link rel="stylesheet" href="./styles/signIn.css" />
	
	</head>
	
	<body>
	    <span style="top: 0;left: 0;     
					 font-weight: bold; font-size: 18pt;
					 color: #3300FF;background-color: #FFFFFF;">
	    <%@ include file="./include/siracHeader.jsp" %>
	    </span>
	
	    <div id="signInBox2" align="center">
	    	<div id="header">
	        	<div id="logo">
	              <img src="./images/logo.png" width="200" height="40" alt="PeopleImg" />
	       	 	</div>
	      	</div>
	
			<h2>Errore durante la registrazione al portale.</h2>
	
			<%=res.getMessaggio()%>
			<br><br>
			<form action="" method="POST">
				<input type="button" value="Fine procedura" onclick="window.location.href='<%=returnURL %>';"/>
			</form>
			<br>
		</div>

	</body>
</html>
