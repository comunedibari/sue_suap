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
		<title><spring:message code="beservices.massiveParametersSettings.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="beServicesMassiveParametersSettings">
			<fieldset>
				<div class="panel">
					<div class="panelRow">
			            <label class="label" for=newUrlSchema>
			            	<spring:message code="beservices.massiveParametersSettings.page3.beUrlSchema" />
			            </label>
						<form:select path="newUrlSchema" id="newUrlSchema">
							<form:options items="${allowedSchemas}" itemLabel="label" itemValue="value" />
						</form:select>
					</div>
					<div class="panelRow">
			            <label class="label" for=newUrlHost>
			            	<spring:message code="beservices.massiveParametersSettings.page3.beUrlHost" />
			            </label>
			            <form:input id="newUrlHost" path="newUrlHost"/>
						<form:errors path="newUrlHost" cssClass="errors"/>
					</div>
					<div class="panelRow">
			            <label class="label" for=newUrlPort>
			            	<spring:message code="beservices.massiveParametersSettings.page3.beUrlPort" />
			            </label>
			            <form:input id="newUrlPort" path="newUrlPort"/>
						<form:errors path="newUrlPort" cssClass="errors"/>
					</div>
					<div class="panelRow">
			            <label class="label" for=transportEnvelopeEnabled>
			            	<spring:message code="beservices.massiveParametersSettings.page3.useEnvelope" />
			            </label>
			            <form:checkbox id="transportEnvelopeEnabled" path="transportEnvelopeEnabled"/>
						<form:errors path="transportEnvelopeEnabled" cssClass="errors"/>
					</div>
					<div class="panelRow">
			            <label class="label" for=delegationControlForbidden>
			            	<spring:message code="beservices.massiveParametersSettings.page3.denyDelegationControl" />
			            </label>
			            <form:checkbox id="delegationControlForbidden" path="delegationControlForbidden"/>
						<form:errors path="delegationControlForbidden" cssClass="errors"/>
					</div>
				</div>
			</fieldset>
			
			<div class="buttonsbar">
				<input type="submit" id="cancel" name="cancel" value="Annulla" class="button" />
				<input type="submit" id="previousPage" name="previousPage" value="Indietro" class="button" />
				<input type="submit" id="nextPage" name="nextPage" value="Avanti" class="button" />
			</div>
		</form:form>
	</body>
</html>
