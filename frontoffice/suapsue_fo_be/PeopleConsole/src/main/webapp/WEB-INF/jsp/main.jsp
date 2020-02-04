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
<?xml version="1.0" encoding="ISO-8859-15"?>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="pcform" uri="/WEB-INF/peopleconsole.tld" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xml:lang="it" lang="it">
    <head>
        <%@ include file="/WEB-INF/sitemesh/styles/style.jsp"%>
        <%@ include file="/WEB-INF/sitemesh/javascript/javascript.jsp"%>
        <meta http-equiv="Content-Type" content="application/xhtml+xml;charset=ISO-8859-15" />
    </head>
	<body>
		<form:form modelAttribute="consoleInfo">
			
			<div id="maincontainer">
			    <div id="topsection">
				    <%@ include file="/WEB-INF/sitemesh/includes/header.jsp"%>
				</div>
			    <div id="maintopbar">
					<div class="twocolumnswidth topbarButton">
						<div class="rightcol rightalign">
							<a href="logout.do">Logout</a>
						</div>
						<div class="leftcol leftalign version">
							<!--Ver.&nbsp;<pcform:consoleVersion showBuildNumber="false" />-->
						</div>
					</div>
				</div>
			    <div id="maincontent">
					<div id="mainlinks">
						<table>
							<tr>
								<td>
								    <table>
								        <tr>
								            <td>
								                <a class="onMain" href="MessaggiGenerali/elenco.do">
								                	<img src="/PeopleConsole/images/main/admin-messages.png" width="64" height="64"/><br/>
								                    Gestione messaggi e tablevalues
								                </a>
								            </td>
								            <td class="separator">
								            </td>
								            <td>
								                <a class="onMain" href="NodiFe/elenco.do">
								                	<img src="/PeopleConsole/images/main/kalzium.png" width="64" height="64"/><br/>
								                    Gestione nodi
								                </a>
								            </td>
								            <td class="separator">
								            </td>
								            <td>
								                <a class="onMain" href="ServiziFe/elenco.do">
								                    <img src="/PeopleConsole/images/main/resourcespace.png" width="64" height="64"/><br/>
								                    Gestione servizi FE
								                </a>
								            </td>
								            <td class="separator">
								            </td>
								            <td>
								    			<a class="onMain" href="ServiziBe/elenco.do">
								                	<img src="/PeopleConsole/images/main/internet-connection_tools.png" width="64" height="64"/><br/>
								        			Gestione servizi BE
								                </a>
								            </td>
								        </tr>
								    </table>
								</td>
							</tr>
					            <td class="separator">
					            </td>
							<tr>
								<td>
								    <table>
								        <tr>
								        	<sec:authorize access="hasRole('CONSOLE_ADMIN')">
								            <td>
								                <a class="onMain" href="Amministrazione/paginaPrincipale.do">
								                	<img src="/PeopleConsole/images/main/admin-icon.png" width="64" height="64"/><br/>
								                    Amministrazione console
								                </a>
								            </td>
								            <td class="separator">
								            </td>
								            </sec:authorize>
								            <td>
								    			<a class="onMain" href="Accreditamenti/accreditamentiManagement.do">
								                	<img src="/PeopleConsole/images/main/users-icon.png" width="64" height="64"/><br/>
								        			Accreditamenti
								                </a>
								            </td>
								            <td class="separator">
				   				            <td>
								    			<a class="onMain" href="Monitoraggio/paginaPrincipale.do">
								                	<img src="/PeopleConsole/images/main/monitor.png" width="64" height="64"/><br/>
								        			Osservatorio
								                </a>
								            </td>
								        </tr>
								    </table>
								</td>
							</tr>
						</table>
					</div>
					
				</div>
			
				<!-- PeopleConsole Stats -->
				<div id="mainstat" class="panel">

					<fieldset>
						<legend>Informazioni della People Console</legend>
							
							<div id="beAvailability">
								<script type="text/javascript">
								Ext.onReady(function(){
									createUnavaliableBeGrid('beAvailability', 'beAvailabilityGrid');
									startBeAvailabilityUpdaterTask('beAvailabilityGrid', 60000);  
								});
								</script>
							</div>

							<!-- No Script version of BE availability -->
 							<noscript>
 								<div class="buttonsbar">
									<input type="submit" id="infoUpdate" name="update" value="Aggiorna info" class="button" />
								</div>
 							
 								<!-- Be Service availability cecking.. -->	
								<c:if test="${!empty consoleInfo.beServicesDown}">
									<div id="beAvailability">
										<h1 class="attention">Servizi di Back-end non raggiungibili</h1>
			
										<c:forEach var="beServiceInfo" items="${consoleInfo.beServicesDown}">
												<div  id="mainstatRow" class="panelRow nofloat">
													<label>Servizio: </label> ${beServiceInfo.logicalServiceName}<br />
													<label>URL: </label> ${beServiceInfo.backEndURL} <br />
													<label> Nodi di Front-end su cui il servizio &egrave; dispiegato: </label> 
														<c:forEach var="beServiceNode" items="${beServiceInfo.affectedNodes}">
														${beServiceNode}
														</c:forEach>
												</div>
										</c:forEach>
									</div>
								</c:if>
							</noscript>
							
					</fieldset>
				</div>
	            
			    <div id="footer">
		        	<%@ include file="/WEB-INF/sitemesh/includes/footer.jsp"%>
			    </div>
			</div>
		</form:form>
    </body>
</html>
