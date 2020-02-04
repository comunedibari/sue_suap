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
		<title><spring:message code="beservice.${operation}.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="beServicesMassiveChange">
			<c:if test="${page == 1}">
				<div class="buttonsbar">
					<input type="submit" id="cancel1" name="cancel1" value="Annulla" class="button" />
					<input type="submit" id="nextPage1" name="nextPage1" value="Avanti" class="button" />
				</div>
				<div class="panel">
					<fieldset class="panelFieldset">
						<legend><spring:message code="beservice.${operation}.page1.nodesSelectionFieldset" /></legend>
						<div class="panelRowCentered">
							<form:select path="selectedNodesToShow" id="selectedNodesToShow">
								<form:options items="${feNodesList}" itemLabel="label" itemValue="value" />
							</form:select>
						</div>
						<form:errors path="errorSelectedNodesToShow" cssClass="error"/>
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
					<fieldset class="panelFieldset">
						<div class="panelRowService panelrowclearer panelTitle panelRowServiceTitle">
							<spring:message code="beservice.massiveChange.page2.availableServices" />
							<form:errors path="selectedServicesId" cssClass="error"/>
						</div>
						<div class="panel1">
							<c:forEach var="node" items="${beServicesMassiveChange.availableServices}">
								<c:set var="communeKey" scope="page" value="" />
								<c:forEach var="availableService" items="${node.value}">
									<c:if test="${communeKey ne availableService.communeKey}">
										<div class="underlined">
										</div>
										<div class="nofloat panelrowclearer panelTitle panelRowServiceTitle">
											<form:checkbox path="selectedNodes" 
												value="${availableService.nodeId}" 
												id="${availableService.nodeId}" />
											<label class="colorBlue textBolder text" for="${availableService.nodeId}">${availableService.nodeName} (${availableService.commune} ${availableService.communeKey})</label>
										</div>
									</c:if>
									<div class="nofloat panelRow1 panelrowclearer">
										<div class="text1">
											<form:checkbox class="lmargin100" path="selectedServicesId" 
												value="${availableService.nodeId}.${availableService.id}" 
												id="${availableService.id}" />
											<label class="colorBlue textSmall" for="${availableService.id}">${availableService.logicalServiceName}</label>
										</div>
									</div>
									<c:set var="communeKey" scope="page" value="${availableService.communeKey}" />
								</c:forEach>
							</c:forEach>
						</div>
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
					<input type="submit" id="cancel1" name="cancel1" value="Annulla" class="button" />
					<input type="submit" id="previousPage1" name="previousPage1" value="Indietro" class="button" />
					<input type="submit" id="nextPage1" name="nextPage1" value="Avanti" class="button" />
				</div>
				
				<c:if test="${action == 'modifica'}">
					<div class="panel">
						<fieldset>
							<div class="panelRow2column">
								<form:checkbox class="lmargin100" path="transportEnvelopeEnabled" id="transportEnvelopeEnabled" />
								<label for="transportEnvelopeEnabled"><spring:message code="beservices.list.useEnvelope" /></label>
							</div>
							<div class="panelRow2column panelrowclearer">
								<form:checkbox class="lmargin100" path="delegationControlForbidden" id="delegationControlForbidden" />
								<label for="delegationControlForbidden"><spring:message code="beservices.list.denyDelegationControl" /></label>
							</div>
							
							<div class="panelFieldset panelrowclearer">
								<fieldset>
									<legend><spring:message code="beservices.list.beUrl" /></legend>
									<div class="panelRow2column panelrowclearer">
										<form:checkbox class="lmargin90" path="advancedUrlSubstitution" id="advancedUrlSubstitution" onClick="javascript:submit();"/>
										<label for="advancedUrlSubstitution"><spring:message code="beservice.massiveChange.page3.advancedUrlSubstitution" /></label>
									</div>
									<c:choose>
										<c:when test="${!beServicesMassiveChange.advancedUrlSubstitution}">
											<div class="panelRow2column panelrowclearer">
												<label for="protocol"><spring:message code="beservice.massiveChange.page3.protocol" /></label>
												<form:select class="lmargin90" path="protocol" id="protocol">
													<form:options items="${allowedProtocolsList}" itemLabel="label" itemValue="value" />
												</form:select>
											</div>
											<div class="panelRow2column panelrowclearer">
												<form:input class="lmargin90" path="host" id="host" />
												<label for="host"><spring:message code="beservice.massiveChange.page3.hostName" /></label>
												<form:errors path="host" cssClass="error"/>
											</div>
											<div class="panelRow2column panelrowclearer">
												<form:input class="lmargin90" path="port" id="port" />
												<label for="port"><spring:message code="beservice.massiveChange.page3.port" /></label>
												<form:errors path="port" cssClass="error"/>
											</div>
										</c:when>
										<c:otherwise>
											<div class="panelRow2column panelrowclearer">
												<form:input class="lmargin90" path="find" id="find" />
												<label for="find"><spring:message code="beservice.massiveChange.page3.find" /></label>
												<form:errors path="find" cssClass="error"/>
											</div>
											<div class="panelRow2column panelrowclearer">
												<form:input class="lmargin90" path="replace" id="replace" />
												<label for="replace"><spring:message code="beservice.massiveChange.page3.replace" /></label>
												<form:errors path="replace" cssClass="error"/>
											</div>
										</c:otherwise>
									</c:choose>
								</fieldset>
							</div>
						</fieldset>
					</div>
				</c:if>

				<c:if test="${action == 'elimina'}">
				<div class="panel">
					<fieldset class="panelFieldset">
						<div class="panelRowService panelrowclearer panelTitle panelRowServiceTitle">
							Cliccando su Avanti verrà confermata l'eliminazione dei servizi selezionati.
						</div>
					</fieldset>
				</div>
				</c:if>
				
				<div class="buttonsbar">
					<input type="submit" id="cancel" name="cancel" value="Annulla" class="button" />
					<input type="submit" id="previousPage" name="previousPage" value="Indietro" class="button" />
					<input type="submit" id="nextPage" name="nextPage" value="Avanti" class="button" />
				</div>
			</c:if>
			
			<c:if test="${page == 4}">				
				
				<div class="buttonsbar">
					<input type="submit" id="close" name="close" value="Chiudi" class="button" />
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
				
				<c:forEach var="servicesRegistrationResult" items="${registrationLog.operationResults}">

					<div class="panel1">
						<div class="text nofloat panelRow1 panelrowclearer">
							<div class="title textBolder colorBlue">
							<c:choose>
								<c:when test="${!empty servicesRegistrationResult.errors}">
									Il servizio ${servicesRegistrationResult.serviceName} non è stato 
									<spring:message code="beservice.${operation}.page${page}.lowerCaseAction" />
								</c:when>
								<c:otherwise>
									<spring:message code="beservice.${operation}.page${page}.action" /> il servizio ${servicesRegistrationResult.serviceName}
								</c:otherwise>
							</c:choose>
							</div>
							<c:if test="${!empty servicesRegistrationResult.messages}">
								<div class="text09 lmargin40">
									Messaggi:
								</div>
								<div class="text09 lmargin100 marginTop10">
									<c:if test="${!empty servicesRegistrationResult.messages}">
										<ul>
									</c:if>
									<c:forEach var="message" items="${servicesRegistrationResult.messages} ">
										<li>${message}</li>
									</c:forEach>
									<c:if test="${!empty servicesRegistrationResult.messages}">
										</ul>
									</c:if>
								</div>
							</c:if>
	
							<c:if test="${!empty servicesRegistrationResult.errors}">
							<div class="text09 lmargin40">
								Errori:
							</div>
							<div class="text09 lmargin100 marginTop10">
								<c:if test="${!empty servicesRegistrationResult.errors}">
									<ul>
								</c:if>
								<c:forEach var="error" items="${servicesRegistrationResult.errors} ">
									<li>${error}</li>
								</c:forEach>
								<c:if test="${!empty servicesRegistrationResult.errors}">
									</ul>
								</c:if>
							</div>
							</c:if>
	
							
						</div>
					</div>
						
				</c:forEach>
				<div class="buttonsbar">
					<input type="submit" id="close" name="close" value="Chiudi" class="button" />
				</div>
			</c:if>

<!-- the tooltip markup looks like this   
  
<div id="aToolTip" class="defaultTheme">  
    <p class="aToolTipContent">Your tooltip content</p>  
</div>  
  
-->  
			
		</form:form>
	</body>
</html>
