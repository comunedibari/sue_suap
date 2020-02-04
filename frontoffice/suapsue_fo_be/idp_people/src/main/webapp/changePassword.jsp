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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.people.sirac.core.UserProfileConstants" %>
    
<%
	String nextStep = (String)request.getAttribute("nextStep");
	String message = (String)request.getAttribute("message");
	String target = (String)request.getAttribute("target");
	if((nextStep!=null) && (target!=null)){
	    
%>
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	
		<title>IDP-People Demo - Servizio di cambio password</title>
	    <link rel="stylesheet" href="./styles/signIn.css" />
	</head>

	<body>
		<span style="top: 0;left: 0;     
					 font-weight: bold; font-size: 18pt;
					 color: #3300FF;background-color: #E5E8EC;">
	    	<%@ include file="./include/siracHeader.jsp" %>
	    </span>
	
	    <div id="signInBox2" align="center">
	      <div id="header">
	        <div id="logo">
	          <img src="./images/logo.png" width="200" height="40" alt="PeopleImg" />
	        </div>
	      </div>

		 
		  <h2>Modifica della password</h2>
		  <%
		  if(message != null){
		  %>
		  		<p class="alert"><%=message%></p>
		  <%
		  }
		  %>
	   
		  <form action="<%=nextStep%>" method="post" name="frmRCambioPassword">
			<input type="hidden" name="hasPasswordData" value="true"/>
	    	<input type="hidden" name="TARGET" value="<%=target%>"/>
			<table cellspacing="0" border="1" class="tblSignup">
			<tr class="tblRowHeader">
				<th>Credenziali di accesso</th>
			</tr>
			<tr>
				<td>
					<table border="0" width="100%">
					<tr>
						<td class="tblRowSubHeader" width="50%">Codice fiscale:</td>
						<td><input type="text" name="<%=UserProfileConstants.CODICE_FISCALE%>" size="20"></td>
					</tr>
					<tr>
						<td class="tblRowSubHeader">Vecchia password:</td>
						<td><input type="password" name="oldPwd" size="20"></td>
					</tr>
					<tr>
						<td class="tblRowSubHeader">Nuova password:</td>
						<td><input type="password" name="newPwd" size="20"></td>
					</tr>
					<tr>
						<td class="tblRowSubHeader">Nuova password (re-inserire):</td>
						<td><input type="password" name="confirmPwd" size="20"></td>
					</tr>
			   		</table>
			    </td>
			</tr>
		 	</table>
			<br>
			<table border="0" align="center" width="100%">
			<tr class="tblRowButtons">
				<td align="center">
		 	 		<input type="submit" id="btn1" value="Conferma">
		 	 		<input type="button" id="btn2" value="Annulla" onclick="history.go(-1);">
				</td>
		 	</tr>
			</table>
	       </form>

	    </div>
	
	</body>

</html>

<%
	}
%>
