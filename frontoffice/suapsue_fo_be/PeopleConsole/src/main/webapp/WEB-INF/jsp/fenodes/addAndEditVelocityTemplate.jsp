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
		<title><spring:message code="addOrEditVelocityTemplate.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="velocityTemplate">

			<div class="buttonsbar">
				<input type="submit" id="cancel" name="cancel" value="Indietro" class="button" />
			</div>
			
			<div class="panel">
			
				<div class="panelFieldset panelrowclearer">
					<fieldset>
						<legend><spring:message code="addOrEditVelocityTemplate.templateScopeTitle" /></legend>
					
						<div class="panelRow2column">
							<label for="serviceName"><spring:message code="addOrEditVelocityTemplate.serviceName" /> </label>
							<div id="serviceName" class="textBlack">${velocityTemplate.serviceName}</div>
						</div>
						
						<div class="panelRow2column">
							<label for="nodeName"><spring:message code="addOrEditVelocityTemplate.nodeName" /> </label>
							<div id="nodeName" class="textBlack">${velocityTemplate.nodeName}</div>
						</div>
					</fieldset>
				</div>
			
				<fieldset>
					<div class="panelRow">
						<label for="templateKey"> <spring:message code="addOrEditVelocityTemplate.templateKey" /> </label>
						<form:input id="templateKey" path="key" readonly="true" />
					</div>
					
					<div class="panelRow">
						<label for="templateDesc"> <spring:message code="addOrEditVelocityTemplate.templateDesc" /> </label>
						<form:input id="templateDesc" path="description" />
					</div>
					<br />
					
					<div class="panelRow">
			            <label class="label" for="templateSubject"> <spring:message code="addOrEditVelocityTemplate.templateSubject" /> </label>
						<form:textarea id="templateSubject" path="templateSubject" rows="5" cols="30"/>
						<form:errors path="templateSubject" cssClass="error"/>
					</div>
					
					<div class="panelRow">
			            <label class="label" for="templateBody">
			            	<spring:message code="addOrEditVelocityTemplate.templateBody" />
			            </label>
						<form:textarea id="templateBody" path="templateBody" rows="5" cols="30"/>
						<form:errors path="templateBody" cssClass="error"/>
					</div>
					
					<div class="panelRow">
			            <label class="label" for="templateSubjectMapper">
			            	<spring:message code="addOrEditVelocityTemplate.templateSubjectMapper" />
			            </label>
						<form:textarea id="templateSubjectMapper" path="templateSubjectMapper" rows="5" cols="30"/>
						<form:errors path="templateSubjectMapper" cssClass="error"/>
					</div>
					
					<div class="panelRow">
			            <label class="label" for="templateBodyMapper">
			            	<spring:message code="addOrEditVelocityTemplate.templateBodyMapper" />
			            </label>
						<form:textarea id="templateBodyMapper" path="templateBodyMapper" rows="5" cols="30"/>
						<form:errors path="templateBodyMapper" cssClass="error"/>
					</div>
					
					<div class="buttonsbar">
						<input type="submit" id="updateVelocityTemplate" name="updateVelocityTemplate" value="Aggiorna" class="button" />
					</div>
				</fieldset>
			</div>
			
			<div class="buttonsbar">
				<input type="submit" id="cancel" name="cancel" value="Indietro" class="button" />
			</div>

		</form:form>
	</body>
</html>
