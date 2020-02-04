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
<html>
	<head>
		<!-- link href="/SpringMVCStructure/css/base.css" rel="stylesheet" media="screen" type="text/css" /-->
		<title><spring:message code="fenodes.nodeDetails.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="beService">
		<fieldset>
			<div class="panel">
				<div class="panelRow">
		            <label class="label" for="logicalServiceName">
		            	<spring:message code="beservices.list.logicalName" />
		            </label>
					<form:input id="logicalServiceName" cssClass="input" path="logicalServiceName" readonly="true" />
					<br>
					<form:errors path="logicalServiceName" cssClass="errors"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="backEndURL">
		            	<spring:message code="beservices.list.beUrl" />
		            </label>
					<form:input id="backEndURL" cssClass="input" path="backEndURL" />
					<br>
					<form:errors path="backEndURL" cssClass="errors"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="transportEnvelopeEnabled">
		            	<spring:message code="beservices.list.useEnvelope" />
		            </label>
					<form:checkbox id="transportEnvelopeEnabled" cssClass="input" path="transportEnvelopeEnabled" />
					<br>
					<form:errors path="transportEnvelopeEnabled" cssClass="errors"/>
				</div>
				<div class="panelRow">
		            <label class="label" for="delegationControlForbidden">
		            	<spring:message code="beservices.list.denyDelegationControl" />
		            </label>
					<form:checkbox id="delegationControlForbidden" cssClass="input" path="delegationControlForbidden" />
					<br>
					<form:errors path="delegationControlForbidden" cssClass="errors"/>
				</div>
			</div>
			<div class="buttonsbar">
				<input type="submit" id="updateBeService" name="updateBeService" value="Aggiorna" class="button" />
				<input type="submit" id="anulla" name="cancel" value="Annulla" class="button" />
			</div>
		</fieldset>
		</form:form>
	</body>
</html>
