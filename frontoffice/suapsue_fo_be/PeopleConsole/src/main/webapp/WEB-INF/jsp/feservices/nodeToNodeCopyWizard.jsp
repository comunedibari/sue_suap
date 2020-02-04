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
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="pcfn" uri="/WEB-INF/peopleconsole-fn.tld" %>
<%@ taglib prefix="pcform" uri="/WEB-INF/peopleconsole.tld" %>
<html>
	<head>
		<title><spring:message code="feservice.nodeToNodeCopy.page${page}.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="feNodeToNodeCopy">
			<form:errors path="selectedServicesPackages" cssClass="error"/>
			<c:if test="${page == 1}">
				<div class="buttonsbar">
					<input type="submit" id="cancel1" name="cancel1" value="Annulla" class="button" />
					<input type="submit" id="nextPage1" name="nextPage1" value="Avanti" class="button" />
				</div>
				<div class="panel">
					<form:errors path="invalidChosedNodes" cssClass="error"/>
					<fieldset class="panelFieldset">
					<legend><spring:message code="feservice.nodeToNodeCopy.page1.fromNodeFieldset" /></legend>
					<div class="panelRow">
			            <label for="selectedFromNodeId">
			            	<spring:message code="feservice.nodeToNodeCopy.page1.fromNodeSelection" />
			            </label>
						<form:select path="selectedFromNodeId" id="selectedFromNodeId">
							<form:options items="${feNodesList}" itemLabel="label" itemValue="value" />
						</form:select>
					</div>
					</fieldset>
					<div class="clearer"></div>
					
					<fieldset class="panelFieldset">
						<legend><spring:message code="feservice.nodeToNodeCopy.page1.toNodeFieldset" /></legend>
						<div class="panelRow">
				            <label for="toNewNode">
				            	<spring:message code="feservice.nodeToNodeCopy.page1.toNewNodeSelection" />
				            </label>
						    <form:checkbox path="toNewNode" id="toNewNode" onclick="javascript:submit();" />
						    <noscript>
						    	<input type="submit" id="refresh" name="refresh" value="Aggiorna" class="button" />
						    </noscript>
						</div>
						<div class="clearer"></div>
						<c:choose>
							<c:when test="${!feNodeToNodeCopy.toNewNode}">
								<div class="panelRow">
						            <label for="selectedToNodeId">
						            	<spring:message code="feservice.nodeToNodeCopy.page1.toNodeSelection" />
						            </label>
									<form:select path="selectedToNodeId" id="selectedToNodeId">
										<form:options items="${feNodesList}" itemLabel="label" itemValue="value" />
									</form:select>
								</div>
							</c:when>
							<c:otherwise>
								<div class="panelRow">
						            <label for="name">
						            	<spring:message code="fenodes.listAndAdd.nodeName" />
						            </label>
									<form:input id="name" path="newFeNode.name" maxlength="255" />
									<form:errors path="newFeNode.name" cssClass="error"/>
								</div>
								<div class="panelRow">
						            <label for="feServiceURL">
						            	<spring:message code="fenodes.listAndAdd.nodeFEWSURL" />
						            </label>
									<form:input id="feServiceURL" path="newFeNode.feServiceURL" maxlength="255" />
									<form:errors path="newFeNode.feServiceURL" cssClass="error"/>
								</div>
								<div class="panelRow">
						            <label for="municipality">
						            	<spring:message code="fenodes.listAndAdd.communeName" />
						            </label>
									<form:input id="municipality" path="newFeNode.municipality" maxlength="255" />
									<form:errors path="newFeNode.municipality" cssClass="error"/>
								</div>
								<div class="panelRow">
						            <label for="municipalityCode">
						            	<spring:message code="fenodes.listAndAdd.communeCode" />
						            </label>
									<form:input id="municipalityCode" path="newFeNode.municipalityCode" maxlength="100" />
									<form:errors path="newFeNode.municipalityCode" cssClass="error"/>
								</div>
								<div class="panelRow">
						            <label for="delegationControlEnabled">
						            	<spring:message code="fenodes.listAndAdd.delegationControlEnabled" />
						            </label>
						            <form:checkbox id="delegationControlEnabled" path="newFeNode.delegationControlEnabled" />
									<form:errors path="newFeNode.delegationControlEnabled" cssClass="error"/>
								</div>
								<div class="panelRow">
						            <label for="delegationControlServiceURL">
						            	<spring:message code="fenodes.listAndAdd.delegationControlWSURL" />
						            </label>
									<form:input id="delegationControlServiceURL" path="newFeNode.delegationControlServiceURL" maxlength="255" />
									<form:errors path="newFeNode.delegationControlServiceURL" cssClass="error"/>
								</div>
								<div class="panelRow">
						            <label for="aooPrefix">
						            	<spring:message code="fenodes.listAndAdd.aooPrefix" />
						            </label>
									<form:input id="aooPrefix" path="newFeNode.aooPrefix" maxlength="4" />
									<form:errors path="newFeNode.aooPrefix" cssClass="error"/>
								</div>
							</c:otherwise>
						</c:choose>
					</fieldset>
					
				</div>
				<div class="buttonsbar">
					<input type="submit" id="cancel" name="cancel" value="Annulla" class="button" />
					<input type="submit" id="nextPage" name="nextPage" value="Avanti" class="button" />
				</div>
			</c:if>

			<c:if test="${page == 2}">
			
				<div class="buttonsbar">
					<input type="submit" id="cancel1" name="cancel1" value="Annulla" class="button" />
					<input type="submit" id="previousPage1" name="previousPage1" value="Indietro" class="button" />
					<input type="submit" id="nextPage1" name="nextPage1" value="Avanti" class="button" />
				</div>
				<div class="panel">

				<fieldset>
					<div class="panelRow">
			            <label for="selectedFromNodeName2"><spring:message code="feservice.nodeToNodeCopy.page2.fromNode" /></label>
			            <div id="selectedFromNodeName2" class="textBlack">${feNodeToNodeCopy.selectedFromNodeName}</div>
					</div>
					<div class="clearer"></div>
					<div class="panelRow">
			            <label for="selectedToNodeName2"><spring:message code="feservice.nodeToNodeCopy.page2.toNode" /></label>
			            <div id="selectedToNodeName2" class="textBlack">
			            	<c:choose>
			            		<c:when test="${!feNodeToNodeCopy.toNewNode}">
					            	${feNodeToNodeCopy.selectedToNodeName}
			            		</c:when>
			            		<c:otherwise>
					            	${feNodeToNodeCopy.newFeNode.name}
			            		</c:otherwise>
			            	</c:choose>
			            </div>
					</div>
					<div class="clearer"></div>
					<div class="panelRow">
			            <label for="beServicesUrl">Indirizzo BE:</label>
						<form:input id="beServicesUrl" path="beServicesUrl" />
						<form:errors path="beServicesUrl" cssClass="error"/>
					</div>
				</fieldset>

				</div>
				<div class="clearer"></div>
				
				<c:if test="${fn:length(feNodeToNodeCopy.alreadyRegisteredServices) > 0}">
					<div class="panel">
					<fieldset class="panelFieldset">
						<div class="panelRowService panelrowclearer panelTitle panelRowServiceTitle">
							<spring:message code="feservice.nodeToNodeCopy.page2.registeredServices" />
						</div>
						<c:forEach var="alreadyRegisteredService" items="${feNodeToNodeCopy.alreadyRegisteredServices}">
							<div class="panelRowService panelrowleftmargin-20px panelrowtopmargin-5px">
								<label>${alreadyRegisteredService.serviceName}</label>
								<span class="availableservices-areaandsubarea">(${alreadyRegisteredService.activity} -&gt; ${alreadyRegisteredService.subActivity})</span>
							</div>
						</c:forEach>
					</fieldset>
					</div>
				</c:if>
				<div class="clearer"></div>
				
				<div class="panel">
				<fieldset class="panelFieldset">
					<div class="panelRowService panelrowclearer panelTitle panelRowServiceTitle">
						<spring:message code="feservice.nodeToNodeCopy.page2.availableServices" />
					</div>
				</div>
				<div class="panel1">
					<c:set var="area" scope="page" value="" />
					<c:forEach var="availableService" items="${feNodeToNodeCopy.availableServiceCollection}">
						<c:if test="${area ne availableService.activity}">
							<div class="nofloat panelRow1 panelrowclearer">
								<div class="text1 underlined">
									<form:checkbox class="lmargin20" path="selectedAreas" 
										value="${availableService.activity}" 
										id="${availableService.activity}" />
									<label class="colorBlue textBolder text" for="${availableService.activity}">${availableService.activity}</label>
								</div>
								<div class="text09">
									<label class="lmargin60 textSmall" for="${availableService.activity}_beAreaUrl">Indirizzo BE:</label>
									<input id="${availableService.activity}_beAreaUrl" class="lmargin60 textSmall" 
										name="${availableService.activity}_beAreaUrl" type="text" />
								</div>
								<div class="text09">
								<label class="lmargin60 textSmall" for="${availableService.activity}_areaServicesNamePrefix">Prefisso servizi:</label>
								<input id="${availableService.activity}_areaServicesNamePrefix" class="lmargin60 textSmall" 
									name="${availableService.activity}_areaServicesNamePrefix" type="text" />
								<label class="lmargin60 textSmall" for="${availableService.activity}_areaServicesNameSuffix">Suffisso servizi:</label>
								<input id="${availableService.activity}_areaServicesNameSuffix" class="lmargin60 textSmall" 
									name="${availableService.activity}_areaServicesNameSuffix" type="text" />
								</div>
								<div class="text09">
								<label class="lmargin60 textSmall" for="${availableService.activity}_areaServicesLogicalNamePrefix">Prefisso nomi logici:</label>
								<input id="${availableService.activity}_areaServicesLogicalNamePrefix" class="lmargin60 textSmall" 
									name="${availableService.activity}_areaServicesLogicalNamePrefix" type="text" />
								<label class="lmargin60 textSmall" for="${availableService.activity}_areaServicesLogicalNameSuffix">Suffisso nomi logici:</label>
								<input id="${availableService.activity}_areaServicesLogicalNameSuffix" class="lmargin60 textSmall" 
									name="${availableService.activity}_areaServicesLogicalNameSuffix" type="text" />
								</div>
							</div>
						</c:if>
						<div class="nofloat panelRow1 panelrowclearer">
							<div class="text1">
								<form:checkbox class="lmargin60" path="selectedServicesPackages" 
									value="${availableService._package}" 
									id="${availableService._package}" />
								<label class="colorBlue textSmall" for="${availableService._package}">${availableService.serviceName}</label>
							</div>
							<div class="text09 textColor">
							<label class="lmargin100 textSmall" for="${availableService._package}_beServicesUrl">Indirizzo BE:</label>
							<input id="${availableService._package}_beServiceUrl" class="lmargin100 textSmall" 
								name="${availableService._package}_beServiceUrl" type="text" />
							</div>
							<div class="text09">
							<label class="lmargin100 textSmall" for="${availableService._package}_serviceNamePrefix">Prefisso servizi:</label>
							<input id="${availableService._package}_serviceNamePrefix" class="lmargin100 textSmall" 
								name="${availableService._package}_serviceNamePrefix" type="text" />
							<label class="lmargin100 textSmall" for="${availableService._package}_serviceNameSuffix">Suffisso servizi:</label>
							<input id="${availableService._package}_serviceNameSuffix" class="lmargin100 textSmall" 
								name="${availableService._package}_serviceNameSuffix" type="text" />
							</div>
							<div class="text09">
							<label class="lmargin100 textSmall" for="${availableService._package}_serviceLogicalNamePrefix">Prefisso nomi logici:</label>
							<input id="${availableService._package}_serviceLogicalNamePrefix" class="lmargin100 textSmall" 
								name="${availableService._package}_serviceLogicalNamePrefix" type="text" />
							<label class="lmargin100 textSmall" for="${availableService._package}_serviceLogicalNameSuffix">Suffisso nomi logici:</label>
							<input id="${availableService._package}_serviceLogicalNameSuffix" class="lmargin100 textSmall" 
								name="${availableService._package}_serviceLogicalNameSuffix" type="text" />
							</div>
						</div>
						<c:set var="area" scope="page" value="${availableService.activity}" />
					</c:forEach>
				</fieldset>
				</div>
				<div class="buttonsbar">
					<input type="submit" id="cancel" name="cancel" value="Annulla" class="button" />
					<input type="submit" id="previousPage" name="previousPage" value="Indietro" class="button" />
					<input type="submit" id="nextPage" name="nextPage" value="Avanti" class="button" />
				</div>
				
			</c:if>
			<c:if test="${page == 3}">
				<div class="buttonsbar">
					<input type="submit" id="close" name="close" value="Chiudi" class="button" />
				</div>
				<div class="panel">
					<div class="panelRow panelrowclearer panelTitle">
						Risultato della copia:
					</div>
				</div>
				
				
				<div class="panel1">
					<div class="text nofloat panelRow1 panelrowclearer">
						<ul>
						<c:forEach var="message" items="${registrationLog.messages}">
							<li>${message}</li>					
						</c:forEach>
						</ul>
					</div>
				</div>
				
				<c:forEach var="servicesRegistrationResult" items="${registrationLog.servicesRegistrationResult}">

				<div class="panel1">
					<div class="text nofloat panelRow1 panelrowclearer">
					Registrato il servizio ${servicesRegistrationResult.serviceName}
						<div class="text09 lmargin40 marginTop10">
							Package: ${servicesRegistrationResult.servicePackage}
						</div>
						<div class="text09 lmargin40">
							Area: ${servicesRegistrationResult.serviceActivity}
						</div>
						<div class="text09 lmargin40">
							Sotto area: ${servicesRegistrationResult.serviceSubActivity}
						</div>
						<div class="text09 lmargin40">
							Messaggi:
						</div>
						<div class="text09 lmargin100 marginTop10">
							<c:forEach var="message" items="${servicesRegistrationResult.messages} ">
								${message}
							</c:forEach>
						</div>
					</div>
				</div>
						
				</c:forEach>
				<div class="buttonsbar">
					<input type="submit" id="close" name="close" value="Chiudi" class="button" />
				</div>
			</c:if>
			
		</form:form>
	</body>
</html>
