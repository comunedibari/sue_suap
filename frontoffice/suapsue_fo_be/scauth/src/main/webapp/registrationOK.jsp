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
<%@ page import="it.people.sirac.idp.beans.RegBean" %>
    
<%
	RegBean userData = (RegBean)request.getAttribute("registrationData");
	String ca_domain_suffix = (String)request.getSession().getAttribute("CaDomainSuffix");
	boolean showPasswordAndPin = new Boolean((String)request.getSession().getServletContext().getInitParameter("showPasswordAndPin")).booleanValue();
	String mode = (String)request.getAttribute("mode");
	String target = (String)request.getSession().getAttribute("TARGET");

	if((userData!=null) && (target!=null) && (mode!=null)){
	
%>
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	
		<title>CA-People Demo - Servizio di registrazione utenti</title>
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
	
			<%
			  if("registration".equalsIgnoreCase(mode)) {
			%>
				<h2>Registrazione al portale avvenuta con successo.</h2>
 	 		<% 
 	 		  } else {
 	 		%>
 				<h2>Modifica dei dati avvenuta con successo.</h2>
			<%
			  }
			%>
			Di seguito sono riassunti i dati anagrafici dell'utente
	
			<table cellspacing="0" border="1" width="100%">
			<tr class="tblRowHeader">
				<th>Dati anagrafici</th>
			</tr>
			<tr>
				<td>
					<table border="0" width="100%">
					<tr>
						<td class="tblRowSubHeader" width="50%">Nome</td>
						<td class="tblRowSubHeader" width="50%">Cognome</td>
					</tr>
					<tr>
						<td><%=userData.getNome()%></td>
						<td><%=userData.getCognome()%></td>
					</tr>
					<tr>
						<td class="tblRowSubHeader">Codice fiscale</td>
						<td class="tblRowSubHeader">Sesso</td>
					</tr>
					<tr>
						<td><%=userData.getCodiceFiscale()%></td>
						<td><%=userData.getSesso()%></td>
					</tr>
					<tr>
						<td class="tblRowSubHeader">e-mail/Domicilio elettronico</td>
						<%if(!("".equalsIgnoreCase(userData.getCartaIdentita()))) { %>
							<td colspan="2" class="tblRowSubHeader">Carta d'identit&agrave;</td>
						<% } %>		
					</tr>
					<tr>
						<td><%=userData.getEmail()%></td>
						<%if(!("".equalsIgnoreCase(userData.getCartaIdentita()))) { %>
							<td colspan="2"><%=userData.getCartaIdentita()%></td>
						<% } %>		
					</tr>
					</table>
				</td>
			</tr>
			<tr class="tblRowHeader">
				<th>Dati di nascita</th>
			</tr>
			<tr>
				<td>
					<table border="0" width="100%">
					<tr>
						<td class="tblRowSubHeader">Data di nascita</td>
						<td class="tblRowSubHeader">Comune</td>
						<td class="tblRowSubHeader">Provincia</td>
						<td class="tblRowSubHeader">Stato</td>						
					</tr>
					<tr>
						<td><%=userData.getDataNascita()%></td>
						<td><%=userData.getLuogoNascita()%></td>
						<td><%=userData.getProvinciaNascita()%></td>
						<td><%=userData.getStatoNascita()%></td>								
					</tr>
					</table>
				</td>
			</tr>
			<tr class="tblRowHeader">
				<th>Dati di residenza</th>
			</tr>
			<tr>
				<td>
					<table border="0" width="100%">
					<tr>
						<td colspan="4" class="tblRowSubHeader">Indirizzo</td>
					</tr>
					<tr>
						<td colspan="4" ><%=userData.getIndirizzoResidenza()%></td>
					</tr>
					<tr>
						<td class="tblRowSubHeader">Comune</td>
						<td class="tblRowSubHeader">CAP</td>
						<td class="tblRowSubHeader">Provincia</td>
						<td class="tblRowSubHeader">Stato</td>						
					</tr>
					<tr>
						<td><%=userData.getCittaResidenza()%></td>
						<td><%=userData.getCapResidenza()%></td>
						<td><%=userData.getProvinciaResidenza()%></td>		
						<td><%=userData.getStatoResidenza()%></td>								
					</tr>
					</table>
				</td>
			</tr>
			<tr class="tblRowHeader">
				<th>Dati di domicilio</th>
			</tr>
			<tr>
				<td>
					<table border="0" width="100%">
					<tr>
						<td colspan="4" class="tblRowSubHeader">Indirizzo</td>
					</tr>
					<tr>
						<td colspan="4" ><%=userData.getIndirizzoDomicilio()%></td>
					</tr>
					<tr>
						<td class="tblRowSubHeader">Comune</td>
						<td class="tblRowSubHeader">CAP</td>
						<td class="tblRowSubHeader">Provincia</td>
						<td class="tblRowSubHeader">Stato</td>						
					</tr>
					<tr>
						<td><%=userData.getCittaDomicilio()%></td>
						<td><%=userData.getCapDomicilio()%></td>
						<td><%=userData.getProvinciaDomicilio()%></td>		
						<td><%=userData.getStatoDomicilio()%></td>														
					</tr>
					</table>
				</td>
			</tr>
			<tr class="tblRowHeader">
				<th>Altri dati</th>
			</tr>
			<tr>
				<td>
					<table border="0" width="100%">
			     	<tr>
						<td class="tblRowSubHeader">Titolo</td>
						<td class="tblRowSubHeader">Professione</td>
						<td class="tblRowSubHeader">Telefono</td>
						<td class="tblRowSubHeader">Cellulare</td>						
					</tr>
					<%
						String titolo = userData.getTitolo();
						if("".equalsIgnoreCase(titolo)) {
							titolo = "N/D";
						}
						String lavoro = userData.getLavoro();
						if("".equalsIgnoreCase(lavoro)) {
							lavoro = "N/D";
						}
						String telefono = userData.getTelefono();
						if("".equalsIgnoreCase(telefono)) {
							telefono = "N/D";
						}
						String cellulare = userData.getCellulare();
						if("".equalsIgnoreCase(cellulare)) {
							cellulare = "N/D";
						}
						
					%>
					
					<tr>
						<td><%=titolo%></td>
						<td><%=lavoro%></td>
						<td><%=telefono%></td>		
						<td><%=cellulare%></td>														
					</tr>
					</table>
				</td>
			</tr>
			</table>
			<br>
			
			<%
			if("registration".equalsIgnoreCase(mode)) {
			%>
				<table class="tblImportant" width="100%">
				<tr class="tblRowHeader">
					<th>Credenziali di accesso</th>
				</tr>
				<tr>
					<td>
						<table border="0" width="100%">
						<tr class="tblRowImportant">
							<td class="tblRowSubHeader">ID Utente</td>
							<td class="tblRowSubHeader">Password</td>
							<td class="tblRowSubHeader">PIN</td>
						</tr>
						<tr class="tblRowImportant">
							<td><b><%=userData.getCodiceFiscale() + "@" + ca_domain_suffix%></b></td>
							<td><b><%=userData.getPassword()%></b></td>
							<td><b><%=userData.getPin()%></b></td>
						</tr>
						<tr class="tblRowImportant">
							<td colspan="3" align="center"><b>CONSERVI CON CURA LE CREDENZIALI SOPRA RIPORTATE IN QUANTO LE SERVIRANNO PER ACCEDERE AI SERVIZI DEL PORTALE QUALORA NON DISPONGA DELLA SUA CRS</b></td>
						</tr>
						</table>
					</td>
				</tr>
				</table>
			<%
			} else if(showPasswordAndPin){
			%>
				<table class="tblRowHeader" width="100%">
				<tr class="tblRowHeader">
					<th>Credenziali di accesso</th>
				</tr>
				<tr>
					<td>
						<table border="0" width="100%">
						<tr class="tblRowHeader">
							<td class="tblRowSubHeader">ID Utente</td>
							<td class="tblRowSubHeader">Password</td>
							<td class="tblRowSubHeader">PIN</td>
						</tr>
						<tr class="tblRowHeader">
							<td><b><%=userData.getCodiceFiscale() + "@" + ca_domain_suffix%></b></td>
							<td><b><%=userData.getPassword()%></b></td>
							<td><b><%=userData.getPin()%></b></td>
						</tr>
						</table>
					</td>
				</tr>
				</table>
			<%
			}
			%>
			
			<br>
			<table border="0" align="center" width="100%">
			<tr class="tblRowButtons">
				<td align="center">
					<form action="<%=target%>" method="POST">
					  <input type="submit" value="Continua"/>
					</form>
				</td>
			</tr>
			</table>
		</div>
	
	</body>

</html>

<%
	}
%>
