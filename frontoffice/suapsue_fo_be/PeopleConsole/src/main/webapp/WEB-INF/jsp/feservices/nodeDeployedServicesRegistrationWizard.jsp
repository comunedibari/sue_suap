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
		<title><spring:message code="feservice.nodeDeployedServiceRegistration.page${page}.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="feNodeDeployedServicesRegistration">
			<form:errors path="selectedServicesPackages" cssClass="error"/>
			<c:if test="${page == 1}">
				<div class="buttonsbar">
					<input type="submit" id="cancel1" name="cancel1" value="Annulla" class="button" />
					<input type="submit" id="nextPage1" name="nextPage1" value="Avanti" class="button" />
				</div>
				<div class="panel">
				<fieldset>
					<div class="panelRow panelRowTitle">
			            <label class="label" for="selectedNodeId">
			            	<spring:message code="feservice.nodeDeployedServiceRegistration.page1.nodesList" />
			            </label>
						<form:select path="selectedNodeId" id="selectedNodeId">
							<form:options items="${feNodesList}" itemLabel="label" itemValue="value" />
						</form:select>
					</div>
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
				&nbsp;
				<fieldset>
				<div class="panel">
					<div class="panelRow panelrowclearer ">
						<label for="selectedNodeName"><spring:message code="feservice.nodeDeployedServiceRegistration.page2.selectedNode" /></label>
			            <div id="selectedNodeName">${feNodeDeployedServicesRegistration.selectedNodeName}</div>
					</div>
					<div class="panelRow panelrowclearer ">
						<label class="label" for="backEndURL">
			            	<spring:message code="feservice.nodeDeployedServiceRegistration.page2.beUrl" />
			            </label>
						<form:input id="backEndURL" path="backEndURL" />
						<form:errors path="backEndURL" cssClass="errors"/>
					</div>
					<div class="panelRow panelrowclearer ">
						<label class="label" for="logicalNamesPrefix">
			            	<spring:message code="feservice.nodeDeployedServiceRegistration.page2.logicalNamesPrefix" />
			            </label>
						<form:input id="logicalNamesPrefix" path="logicalNamesPrefix" />
						<form:errors path="logicalNamesPrefix" cssClass="errors"/>
					</div>
					<div class="panelRow panelrowclearer ">
						<label class="label" for="logicalNamesSuffix">
			            	<spring:message code="feservice.nodeDeployedServiceRegistration.page2.logicalNamesSuffix" />
			            </label>
						<form:input id="logicalNamesSuffix" path="logicalNamesSuffix" />
						<form:errors path="logicalNamesSuffix" cssClass="errors"/>
					</div>
					<div class="panelRow panelrowclearer ">
						<label class="label" for="servicesPrefix">
			            	<spring:message code="feservice.nodeDeployedServiceRegistration.page2.servicesPrefix" />
			            </label>
						<form:input id="servicesPrefix" path="servicesPrefix" />
						<form:errors path="servicesPrefix" cssClass="errors"/>
					</div>
					<div class="panelRow panelrowclearer ">
						<label class="label" for="servicesSuffix">
			            	<spring:message code="feservice.nodeDeployedServiceRegistration.page2.servicesSuffix" />
			            </label>
						<form:input id="servicesSuffix" path="servicesSuffix" />
						<form:errors path="servicesSuffix" cssClass="errors"/>
					</div>
					<div class="clearer"></div>
				</div>
				
				<div class="clearer"></div>
				
				<c:if test="${fn:length(feNodeDeployedServicesRegistration.alreadyRegisteredServices) > 0}">
					<div class="panel">
						<div class="panelRowService panelrowclearer panelTitle panelRowServiceTitle">
							<spring:message code="feservice.nodeDeployedServiceRegistration.page2.registeredServices" />
						</div>
					</div>
					<div class="panel">
						<c:forEach var="alreadyRegisteredService" items="${feNodeDeployedServicesRegistration.alreadyRegisteredServices}">
							<div class="panelRowService panelrowleftmargin-20px panelrowtopmargin-5px">
								<label>${alreadyRegisteredService.serviceName}</label>
								<span class="availableservices-areaandsubarea">(${alreadyRegisteredService.activity} -&gt; ${alreadyRegisteredService.subActivity})</span>
							</div>
						</c:forEach>
					</div>
				</c:if>
				</fieldset>
				<div class="clearer"></div>
				
				<fieldset>
				<div class="panel">
					<div class="panelRowService panelrowclearer panelTitle panelRowServiceTitle">
						<spring:message code="feservice.nodeDeployedServiceRegistration.page2.availableServices" />
					</div>					
				</div>
				</fieldset>
				
				<div class="panel1">
					<c:set var="area" scope="page" value="" />
					<c:forEach var="availableService" items="${feNodeDeployedServicesRegistration.availableServices}">
						<c:if test="${area ne availableService.activity}">
							<div class="nofloat panelRow1 panelrowclearer">
								<div class="text1 ">
								<%--
									<form:checkbox class="lmargin20" path="selectedAreas" 
										value="${availableService.activity}" 
										id="${availableService.activity}" /> 
								--%>
									<ul><li>
										<label class="colorBlue textBolder text" for="${availableService.activity}">${availableService.activity}</label>
									</li></ul>
								</div>
							</div>
						</c:if>
						<div class="nofloat panelrowclearer">
							<div class="text1">
								<form:checkbox class="lmargin60" path="selectedServicesPackages" 
									value="${availableService._package}" 
									id="${availableService._package}" />
								<label class="colorBlue textSmall" for="${availableService._package}">${availableService.serviceName}</label>
							</div>
						</div>
						<c:set var="area" scope="page" value="${availableService.activity}" />
					</c:forEach>
				</div> 
			<%-- versione precedente 
				
				<div class="panel">
					<c:forEach var="availableService" items="${feNodeDeployedServicesRegistration.availableServices}">
						<div class="panelRowService panelrowleftmargin-20px panelrowtopmargin-5px">
							<form:checkbox path="selectedServicesPackages" label="${availableService.serviceName}" 
								value="${availableService._package}" id="${availableService.serviceConfigFilePath}" />
							<span class="availableservices-areaandsubarea">(${availableService.activity} -&gt; ${availableService.subActivity})</span>
						</div>
					</c:forEach>
				</div>
			 --%>
				
				<div class="buttonsbar">
					<input type="submit" id="cancel" name="cancel" value="Annulla" class="button" />
					<input type="submit" id="previousPage" name="previousPage" value="Indietro" class="button" />
					<input type="submit" id="nextPage" name="nextPage" value="Avanti" class="button" />
				</div>
			</c:if>

			<c:if test="${page == 3}">
				<div class="buttonsbar">
					<input type="submit" id="close1" name="close1" value="Chiudi" class="button" />
				</div>
				<fieldset>
				<div class="panel">
					<div class="panelRowService panelTitle">
						<spring:message code="feservice.nodeDeployedServiceRegistration.page3.log" />
					</div>
				</div>
				<div class="panel">
					<c:forEach items="${registrationLog}" var="log">
						<div class="panelRowUsers">
							<c:if test="${log.hasErrors}">
								<label>Errore: </label>${log.servicePackage}
							</c:if>
							<c:if test="${!log.hasErrors}">
								<label>Registrato: </label>${log.servicePackage}
							</c:if>
						</div>
					</c:forEach>
				</div>
				</fieldset>
				<div class="buttonsbar">
					<input type="submit" id="close" name="close" value="Chiudi" class="button" />
				</div>
			</c:if>
			
		</form:form>
	</body>
</html>
