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
		<title><spring:message code="feservice.${operation}.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="feServicesMassiveChange">
			<c:if test="${page == 1}">
				<div class="buttonsbar">
					<input type="submit" id="cancel1" name="cancel1" value="Annulla" class="button" />
					<input type="submit" id="nextPage1" name="nextPage1" value="Avanti" class="button" />
				</div>
				<div class="panel">
					<fieldset class="panelFieldset">
						<legend><spring:message code="feservice.${operation}.page1.nodesSelectionFieldset" /></legend>
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
							<spring:message code="feservice.massiveChange.page2.availableServices" />
							<form:errors path="errorSelectedServices" cssClass="error"/>
						</div>
						<div class="panel1">
							<c:forEach var="node" items="${feServicesMassiveChange.availableServices}">
								<c:set var="communeKey" scope="page" value="" />
								<c:set var="area" scope="page" value="" />
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
									<c:if test="${area ne availableService.activity}">
										<div class="nofloat panelRow1 panelrowclearer">
											<div class="text1">
												<form:checkbox class="lmargin60" path="selectedAreas" 
													value="${availableService.nodeId}.${availableService.communeKey}.${availableService.activity}" 
													id="${availableService.activity}" />
												<label class="colorBlue textBolder text" for="${availableService.activity}">${availableService.activity}</label>
											</div>
										</div>
									</c:if>
									<div class="nofloat panelRow1 panelrowclearer">
										<div class="text1">
											<form:checkbox class="lmargin100" path="selectedServicesPackages" 
												value="${availableService.nodeId}.${availableService.communeKey}.${availableService.activity}.${availableService.subActivity}.${availableService._package}" 
												id="${availableService._package}" />
											<label class="colorBlue textSmall" for="${availableService._package}">${availableService.serviceName}</label>
										</div>
									</div>
									<c:set var="communeKey" scope="page" value="${availableService.communeKey}" />
									<c:set var="area" scope="page" value="${availableService.activity}" />
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
								<label for="logLevel"><spring:message code="feservice.details.logLevel" /></label>
								<form:select path="feServiceMassiveParametersModifiable.logLevel" id="logLevel">
									<form:options items="${logLevels}" itemLabel="label" itemValue="value" />
								</form:select>
							</div>
							<div class="panelRow2column">
								<label for="serviceStatus"><spring:message code="feservice.details.state" /></label>
								<form:select path="feServiceMassiveParametersModifiable.serviceStatus" id="serviceStatus">
									<form:options items="${statusTypes}" itemLabel="label" itemValue="value" />
								</form:select>
							</div>	
							<div class="panelRow2column">
								<label for="processSignEnabled"><spring:message code="feservice.details.signProcess" /></label>
								<form:select path="feServiceMassiveParametersModifiable.processSignEnabled" id="processSignEnabled">
									<form:options items="${signProcessTypes}" itemLabel="label" itemValue="value" />
								</form:select>
							</div>	
							<div class="panelRow2column">
								<label for="sendmailtoowner"><spring:message code="feservice.details.sendmailtoowner" /></label>
								<form:select path="feServiceMassiveParametersModifiable.sendmailtoowner" id="sendmailtoowner">
									<form:options items="${sendMailToOwnerTypes}" itemLabel="label" itemValue="value" />
								</form:select>
							</div>
							<div class="panelRow2column">
								<label for="attachmentsInCitizenReceipt"><spring:message code="feservice.details.includeAttachmentsInReceipt" /></label>
								<form:select path="feServiceMassiveParametersModifiable.attachmentsInCitizenReceipt" id="attachmentsInCitizenReceipt">
									<form:options items="${includeAttachmentsInReceiptTypes}" itemLabel="label" itemValue="value" />
								</form:select>
							</div>
							<div class="text panelFieldset panelrowclearer">
								<fieldset>
									<legend><spring:message code="feservice.details.processActivationType" /></legend>
									<form:radiobutton path="feServiceMassiveParametersModifiable.activationType" value="1"/><span class="text"><spring:message code="feservice.details.processActivationType.webService" /></span>
									<br/>
									<form:radiobutton path="feServiceMassiveParametersModifiable.activationType" value="2"/><span class="text"><spring:message code="feservice.details.processActivationType.eMail" /></span>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="text"><spring:message code="feservice.details.processActivationType.eMail.address" /></span>&nbsp;<form:input path="feServiceMassiveParametersModifiable.processActivationType.eMailAddress"/>
									<form:errors path="feServiceMassiveParametersModifiable.processActivationType.eMailAddress" cssClass="errors" />
									<br/>
									<form:radiobutton path="feServiceMassiveParametersModifiable.activationType" value="3"/><span class="text"><spring:message code="feservice.details.processActivationType.none" /></span>
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



				<c:choose>
					<c:when test="${action == 'elimina'}">

						<c:forEach var="message" items="${registrationLog.messages}">
							<li>${message}</li>					
						</c:forEach>
					
						<c:forEach var="nodeOperationResult" items="${registrationLog.operationResults}">


							<div class="title">
								Nodo: ${nodeOperationResult.name}
							</div>
							<div class="text nofloat panelRow1 panelrowclearer">
								<div class="text09 marginTop10">
									<c:if test="${!empty nodeOperationResult.messages}">
										<ul>
									</c:if>
									<c:forEach var="message" items="${nodeOperationResult.messages} ">
										<li>${message}</li>
									</c:forEach>
									<c:if test="${!empty nodeOperationResult.messages}">
										</ul>
									</c:if>
								</div>
							</div>


							<c:forEach var="servicesRegistrationResult" items="${nodeOperationResult.feServiceOperationResults}">
			
								<div class="panel1">
									<div class="text lmargin60 nofloat panelRow1 panelrowclearer">
										<div class="title textBolder colorBlue">
										<c:choose>
											<c:when test="${!empty servicesRegistrationResult.errors}">
												Il servizio ${servicesRegistrationResult.serviceName} non è stato 
												<spring:message code="feservice.${operation}.page${page}.lowerCaseAction" />
											</c:when>
											<c:otherwise>
												<spring:message code="feservice.${operation}.page${page}.action" /> il servizio ${servicesRegistrationResult.serviceName}
											</c:otherwise>
										</c:choose>
										</div>
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
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="servicesRegistrationResult" items="${registrationLog.operationResults}">
		
							<div class="panel1">
								<div class="text nofloat panelRow1 panelrowclearer">
									<div class="title textBolder colorBlue">
									<c:choose>
										<c:when test="${!empty servicesRegistrationResult.errors}">
											Il servizio ${servicesRegistrationResult.serviceName} non è stato 
											<spring:message code="feservice.${operation}.page${page}.lowerCaseAction" />
										</c:when>
										<c:otherwise>
											<spring:message code="feservice.${operation}.page${page}.action" /> il servizio ${servicesRegistrationResult.serviceName}
										</c:otherwise>
									</c:choose>
									</div>
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
					</c:otherwise>
				</c:choose>



				
				<div class="buttonsbar">
					<input type="submit" id="close" name="close" value="Chiudi" class="button" />
				</div>
			</c:if>
			
		</form:form>
	</body>
</html>
