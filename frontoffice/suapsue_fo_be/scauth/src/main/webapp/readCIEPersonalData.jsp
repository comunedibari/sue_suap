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
<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>SmartCard personal data reader</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
        
    <SCRIPT language="JavaScript" type="text/javascript"><!--
	      function send() { document.CardData.submit(); }//-->
	</SCRIPT>
	
	<link rel="stylesheet" href="styles/signIn.css" />
  </head>
  
  <body>
	<span style="top: 0;left: 0;     
					 font-weight: bold; font-size: 18pt;
					 color: #3300FF;background-color: #E5E8EC;">
	    <%@ include file="./include/siracHeader.jsp" %>
	</span>
  
	<div id="signInBox" align="center">
          <div id="header">
            <div id="logo">
              <img src="images/logo.png" width="200" height="40" alt="PeopleImg" />
            </div>
          </div>
      
	  	  <div id="form">
		    <table align="center">
		   	  <tr>
            	<td><img src="images/smartcard.jpg" height="30" width="50"/></td>
        		<td><b>Accesso con Smartcard</b><br><i>Lettura dati personali da smart card in corso...</i></td>
              </tr>
		  	</table>

		   	  <FORM name="CardData" action="SmartCardPersonalDataConsumerServlet" method="POST">
			      <input type="hidden" name="CodiceFiscale" value=""/>
			      <input type="hidden" name="Nome" value=""/>
			      <input type="hidden" name="Cognome" value=""/>
			      <input type="hidden" name="Sesso" value=""/>
			      <input type="hidden" name="Statura" value=""/>
			      <input type="hidden" name="ComuneEmittente" value=""/>
			      <input type="hidden" name="ComuneResidenza" value=""/>
			      <input type="hidden" name="ComuneNascita" value=""/>
			      <input type="hidden" name="Indirizzo" value=""/>
			      <input type="hidden" name="DataNascita" value=""/>
			      <input type="hidden" name="DataEmissione" value=""/>
			      <input type="hidden" name="DataScadenza" value=""/>
			      <input type="hidden" name="Cittadinanza" value=""/>
			      <input type="hidden" name="AttoNascita" value=""/>
			      <input type="hidden" name="StatoEsteroNascita" value=""/>
			      <input type="hidden" name="Espatrio" value=""/>
			      <input type="hidden" name="DatiPersonaliRaw" value=""/>
				  <input type="hidden" name="cardType" value=" <%=request.getAttribute("cardType")%>" />
			  </FORM>
		    
			  <jsp:plugin 
			       type="applet" 
			       code="it.cefriel.people.smartcard.SmartCardApplet.class" 
			       codebase="https://peopleserver.people.it:9443/idp-people/archive/"
			       archive="SmartCardAppletSigned.jar"
			       jreversion="1.5"
			       width="0"
			       height="0"
			       nspluginurl="http://java.sun.com/products/plugin/index.html#download"
			       iepluginurl="http://java.sun.com/update/1.5.0/jinstall-1_5-windows-i586.cab#Version=1,5,0,0">
			    	<jsp:params>
			    	  <jsp:param name="showGui" value="<%=request.getAttribute("alwaysShowAppletGUI")%>"/>
			    	  <jsp:param name="cardType" value="<%=request.getAttribute("cardType")%>"/>
  		    	  <jsp:param name="MAYSCRIPT" value="true"/>
              <jsp:param name="SCRIPTABLE" value="true" />
			    	</jsp:params>
			    	<jsp:fallback>
			    	  <p>Impossibile attivare Java Plugin</p>
			    	</jsp:fallback>
			  </jsp:plugin>
		  </div>
	</div>
  </body>
</html>

<!-- nspluginurl="http://java.sun.com/update/1.4.2/j2re-1_4_2_07-windows-i586.xpi" -->
<!--iepluginurl="http://java.sun.com/products/plugin/autodl/jinstall-1_4_2_07-windows-i586.cab" >-->
