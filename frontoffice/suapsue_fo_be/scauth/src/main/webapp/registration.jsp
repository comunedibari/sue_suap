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
<%@ page import="java.util.Map" %>
<%@ page import="it.people.sirac.core.UserProfileConstants" %>
    
<%

	Map attributesMap = (Map)request.getAttribute("attributesMap");
	String mode = (String)request.getAttribute("mode");
	String target = (String)request.getAttribute("target");
	String error = (String)request.getAttribute("error");

	if(
	   (attributesMap!=null) && 
       (("registration".equalsIgnoreCase(mode)) || ("update".equalsIgnoreCase(mode))) &&
       (target!=null)){
       
		
	    String nome = (attributesMap.get(UserProfileConstants.NOME)==null) ? "" : (String)attributesMap.get(UserProfileConstants.NOME);
	    String cognome = (attributesMap.get(UserProfileConstants.COGNOME)==null) ? "" : (String)attributesMap.get(UserProfileConstants.COGNOME);
	    String codiceFiscale = (attributesMap.get(UserProfileConstants.CODICE_FISCALE)==null) ? "" : (String)attributesMap.get(UserProfileConstants.CODICE_FISCALE);
	    String sesso = (attributesMap.get(UserProfileConstants.SESSO)==null) ? "" : (String)attributesMap.get(UserProfileConstants.SESSO);
	    String dataNascita = (attributesMap.get(UserProfileConstants.DATA_NASCITA)==null) ? "" : (String)attributesMap.get(UserProfileConstants.DATA_NASCITA);
	    String indirizzoResidenza = (attributesMap.get(UserProfileConstants.INDIRIZZO_RESIDENZA)==null) ? "" : (String)attributesMap.get(UserProfileConstants.INDIRIZZO_RESIDENZA);
	    String cittaResidenza = (attributesMap.get(UserProfileConstants.COMUNE_RESIDENZA)==null) ? "" : (String)attributesMap.get(UserProfileConstants.COMUNE_RESIDENZA);
	    String capResidenza = (attributesMap.get(UserProfileConstants.CAP_RESIDENZA)==null) ? "" : (String)attributesMap.get(UserProfileConstants.CAP_RESIDENZA);
	    String provinciaResidenza = (attributesMap.get(UserProfileConstants.PROVINCIA_RESIDENZA)==null) ? "" : (String)attributesMap.get(UserProfileConstants.PROVINCIA_RESIDENZA);
	    String statoResidenza = ("".equalsIgnoreCase((String)attributesMap.get(UserProfileConstants.STATO_RESIDENZA))) ? "Italia" : (String)attributesMap.get(UserProfileConstants.STATO_RESIDENZA);
	    String indirizzoDomicilio = (attributesMap.get(UserProfileConstants.INDIRIZZO_DOMICILIO)==null) ? "" : (String)attributesMap.get(UserProfileConstants.INDIRIZZO_DOMICILIO);
	    String cittaDomicilio = (attributesMap.get(UserProfileConstants.COMUNE_DOMICILIO)==null) ? "" : (String)attributesMap.get(UserProfileConstants.COMUNE_DOMICILIO);
	    String capDomicilio = (attributesMap.get(UserProfileConstants.CAP_DOMICILIO)==null) ? "" : (String)attributesMap.get(UserProfileConstants.CAP_DOMICILIO);
	    String provinciaDomicilio = (attributesMap.get(UserProfileConstants.PROVINCIA_DOMICILIO)==null) ? "" : (String)attributesMap.get(UserProfileConstants.PROVINCIA_DOMICILIO);
	    String statoDomicilio = ("".equalsIgnoreCase((String)attributesMap.get(UserProfileConstants.STATO_DOMICILIO))) ? "" : (String)attributesMap.get(UserProfileConstants.STATO_DOMICILIO);
	    String cittaNascita = (attributesMap.get(UserProfileConstants.COMUNE_NASCITA)==null) ? "" : (String)attributesMap.get(UserProfileConstants.COMUNE_NASCITA);
	    String provinciaNascita = (attributesMap.get(UserProfileConstants.PROVINCIA_NASCITA)==null) ? "" : (String)attributesMap.get(UserProfileConstants.PROVINCIA_NASCITA);
	    String statoNascita = ("".equalsIgnoreCase((String)attributesMap.get(UserProfileConstants.STATO_NASCITA))) ? "Italia" : (String)attributesMap.get(UserProfileConstants.STATO_NASCITA);
	    String email = (attributesMap.get(UserProfileConstants.EMAIL)==null) ? "" : (String)attributesMap.get(UserProfileConstants.EMAIL);
	    String titolo = (attributesMap.get(UserProfileConstants.TITOLO)==null) ? "" : (String)attributesMap.get(UserProfileConstants.TITOLO);
	    String lavoro = (attributesMap.get(UserProfileConstants.LAVORO)==null) ? "" : (String)attributesMap.get(UserProfileConstants.LAVORO);
	    String telefono = (attributesMap.get(UserProfileConstants.TELEFONO)==null) ? "" : (String)attributesMap.get(UserProfileConstants.TELEFONO);
	    String cellulare = (attributesMap.get(UserProfileConstants.CELLULARE)==null) ? "" : (String)attributesMap.get(UserProfileConstants.CELLULARE);
	    String codiceCarta = (attributesMap.get(UserProfileConstants.CARTA_IDENTITA)==null) ? "" : (String)attributesMap.get(UserProfileConstants.CARTA_IDENTITA);

	    String returnURL = session.getServletContext().getInitParameter("sirac.registration.returnURL");
	    
%>
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	
		<title>P.E.O.P.L.E. - Registrazione utenti</title>
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

		  <% 
	      	if("registration".equalsIgnoreCase(mode)){
	      %>
			  <h2>Inserimento dati per la registrazione al portale</h2>
			  <%
			  if(error != null){
			  %>
			  		<p class="alert">Attenzione: i dati inseriti non sono completi!</p>
			  <%
			  }
			  %>
			  
	      <%
		    } else if("update".equalsIgnoreCase(mode)){
	      %>
			  <h2>Aggiornamento del profilo di registrazione</h2>
			  <%
			  if(error != null){
			  %>
			  		<p class="alert">Attenzione: i dati inseriti non sono completi!</p>
			  <%
			  }
			  %>
		  <%
		    } 
		  %>		  
		  <form action="<%=target%>" method="post" name="frmRegistrazione">
			<input type="hidden" name="hasRegistrationData" value="true"/>
		  <% 
	      	if(codiceCarta != null){
	      %>
	    		  <input type="hidden" name="<%=UserProfileConstants.CARTA_IDENTITA%>" value="<%=codiceCarta%>"/>
	      <%
		    }
	      %>
			<table cellspacing="0" border="1" class="tblSignup">
			<tr class="tblRowHeader">
				<th>Dati anagrafici <i>non modificabili</i></th>
			</tr>
			<tr>
				<td>
					<table border="0" width="100%">
					<tr>
						<td class="tblRowSubHeader" width="50%">Nome:</td>
						<td class="tblRowSubHeader" width="50%">Cognome:</td>
					</tr>
					<tr>
						<td><%=nome%></td>
						<td><%=cognome%></td>
					</tr>
					<tr>
						<td class="tblRowSubHeader">Data di nascita:</td>
						<td class="tblRowSubHeader">Sesso:</td>
					</tr>
					<tr>
						<td><%=dataNascita%></td>
						<td><%=sesso%></td>
					</tr>
					<tr>
			            <td class="tblRowSubHeader">Codice fiscale:</td>
			        </tr>
			        <tr>
			            <td><%=codiceFiscale%></td>
			        </tr>
			   		</table>
			    </td>
			</tr>
			<tr class="tblRowHeader">
				<th>Dati di residenza (*)</th>
			</tr>
			<tr>
				<td>
					<table border="0" width="100%">
					<tr>
						<td colspan="5" class="tblRowSubHeader">Indirizzo</td>
					</tr>
					<tr>
							<td colspan="5" ><input type="text" name="<%=UserProfileConstants.INDIRIZZO_RESIDENZA%>" size="80" value="<%=indirizzoResidenza%>"></td>
					</tr>
					<tr>
						<td class="tblRowSubHeader">Comune</td>
						<td class="tblRowSubHeader">Provincia</td>
						<td class="tblRowSubHeader">CAP</td>
						<td class="tblRowSubHeader">Stato</td>
					</tr>
					<tr>
							<td><input type="text" name="<%=UserProfileConstants.COMUNE_RESIDENZA%>" size="40" value="<%=cittaResidenza%>"></td>
		   				    <td><input type="text" name="<%=UserProfileConstants.PROVINCIA_RESIDENZA%>" size="2" maxlength="2" value="<%=provinciaResidenza%>"></td>
		   				    <td><input type="text" name="<%=UserProfileConstants.CAP_RESIDENZA%>" size="5" maxlength="5" value="<%=capResidenza%>"></td>
		   				    <td><input type="text" name="<%=UserProfileConstants.STATO_RESIDENZA%>" size="30" value="<%=statoResidenza%>"></td>
					</tr>
					</table>
				</td>
			</tr>
			<tr class="tblRowHeader">
				<th>Dati di domicilio (solo se diversi da quelli di residenza) (*)</th>
			</tr>
			<tr>
				<td>
					<table border="0" width="100%">
					<tr>
						<td colspan="5" class="tblRowSubHeader">Indirizzo</td>
					</tr>
					<tr>
						<td colspan="5" ><input type="text" name="<%=UserProfileConstants.INDIRIZZO_DOMICILIO%>" size="80" value="<%=indirizzoDomicilio%>"></td>
					</tr>
					<tr>
						<td class="tblRowSubHeader">Comune</td>
						<td class="tblRowSubHeader">Provincia</td>
						<td class="tblRowSubHeader">CAP</td>
						<td class="tblRowSubHeader">Stato</td>
					</tr>
					<tr>
						<td><input type="text" name="<%=UserProfileConstants.COMUNE_DOMICILIO%>" size="40" value="<%=cittaDomicilio%>"></td>
	   				    <td><input type="text" name="<%=UserProfileConstants.PROVINCIA_DOMICILIO%>" size="2" maxlength="2" value="<%=provinciaDomicilio%>"></td>
	   				    <td><input type="text" name="<%=UserProfileConstants.CAP_DOMICILIO%>" size="5" maxlength="5" value="<%=capDomicilio%>"></td>
	   				    <td><input type="text" name="<%=UserProfileConstants.STATO_DOMICILIO%>" size="30" value="<%=statoDomicilio%>"></td>
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
	    	        	<td class="tblRowSubHeader">Comune</td>
						<td class="tblRowSubHeader">Provincia</td>
						<td class="tblRowSubHeader">Stato</td>
	            	</tr>
	            	<tr>
						<td>
							<input type="hidden" name="<%=UserProfileConstants.COMUNE_NASCITA%>" size="40" value="<%=cittaNascita%>">
						    <%=cittaNascita%>
						</td>
						<td>
							<input type="hidden" name="<%=UserProfileConstants.PROVINCIA_NASCITA%>" size="40" value="<%=provinciaNascita%>">
						    <%=provinciaNascita%>
						</td>
						<td>
							<input type="hidden" name="<%=UserProfileConstants.STATO_NASCITA%>" size="40" value="<%=statoNascita%>">
						    <%=statoNascita%>
						</td>
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
	            	</tr>
	            	<tr>
	              		<td><input type="text" name="<%=UserProfileConstants.TITOLO%>" size="40" value="<%=titolo%>"></td>
	              		<td><input type="text" name="<%=UserProfileConstants.LAVORO%>" size="40" value="<%=lavoro%>"></td>
	            	</tr>
	              	<tr>
	               		<td class="tblRowSubHeader">e-mail/domicilio elettronico (*)</td>
	                	<td class="tblRowSubHeader">Telefono</td>
	                	<td class="tblRowSubHeader">Cellulare</td>
	              	</tr>
	              	<tr>
	                	<td><input type="text" name="<%=UserProfileConstants.EMAIL%>" size="40" value="<%=email%>"></td>
	                	<td><input type="text" name="<%=UserProfileConstants.TELEFONO%>" size="40" value="<%=telefono%>"></td>
	                	<td><input type="text" name="<%=UserProfileConstants.CELLULARE%>" size="40" value="<%=cellulare%>"></td>
	              	</tr>
           			</table>
          		</td>
         	</tr>
 	  	 	<tr class="tblRowHeader">
				<th>Autorizzazione al trattamento dei dati</th>
		 	</tr>
		 	<tr>
				<td align="center" class="tblPrivacy">Autorizzo al trattamento dei miei dati personali ai sensi del D. L. 30 giugno 2003 n. 196.</td>
		 	</tr>
		 	<tr class="tblRowNote">
		 		<td>(*) informazione obbligatoria</td>
		 	</tr>
		 	<tr class="tblRowNote">
		 		<td>Con la conferma dei dati l'utente attesta l'esistenza dell'indirizzo email indicato e ne accetta l'utilizzo per l'invio delle comunicazioni da parte dell'ente erogatore dei servizi.</td>
		 	</tr>
		 	<tr class="tblRowButtons">
				<td align="center">
					<%
					  if("registration".equalsIgnoreCase(mode)) {
					%>
		 	 		<input type="submit" id="btn1" value="Conferma registrazione">
		 	 		<input type="button" id="btn2" value="Annulla registrazione" onclick="history.go(-2);">
		 	 		<% 
		 	 		  } else {
		 	 		%>
		 	 		<input type="submit" id="btn1" value="Conferma modifica">
		 	 		<input type="button" id="btn2" value="Annulla modifica" onclick="window.location.href='<%=returnURL %>';">
					<%
					  }
					%>
			 		
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
