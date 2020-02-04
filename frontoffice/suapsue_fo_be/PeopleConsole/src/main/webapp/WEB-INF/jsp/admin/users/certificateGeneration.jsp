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
		<title><spring:message code="admin.certificates.generation.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="certificateAccountDTO">
			<h1>Generazione certificato</h1>
			<fieldset>
				<div class="panel">
					<div class="panelRow">
						<pcform:label required="*" for="alias">
							<spring:message code="admin.certificates.generation.alias.label" />
						</pcform:label>
						<form:input id="alias" path="alias" size="20"/>
						<form:errors path="alias" cssClass="error"/>
					</div>
					<fieldset>
						<div class="panelRow">
							<pcform:label for="passwordAutogeneration">
								<spring:message code="admin.certificates.generation.passwordAutogeneration.label" />
							</pcform:label>
							<form:checkbox id="passwordAutogeneration" path="passwordAutogeneration"/>
						</div>
						<div class="panelRow">
							<pcform:label required="*" for="password">
								<spring:message code="admin.certificates.generation.password.label" />
							</pcform:label>
							<form:password id="password" path="password" size="20"/>
							<form:errors path="password" cssClass="error"/>
						</div>
						<div class="panelRow">
							<pcform:label required="*" for="passwordCheck">
								<spring:message code="admin.certificates.generation.passwordCheck.label" />
							</pcform:label>
							<form:password id="passwordCheck" path="passwordCheck" size="20"/>
							<form:errors path="passwordCheck" cssClass="error"/>
						</div>
					</fieldset>
					<div class="panelRow">
						<pcform:label required="*" for="commonName">
							<spring:message code="admin.certificates.generation.commonName.label" />
						</pcform:label>
						<form:input id="commonName" path="commonName" size="20"/>
						<form:errors path="commonName" cssClass="error"/>
					</div>
					<div class="panelRow">
						<pcform:label required="*" for="organizationUnit">
							<spring:message code="admin.certificates.generation.organizationUnit.label" />
						</pcform:label>
						<form:input id="organizationUnit" path="organizationUnit" size="20"/>
						<form:errors path="organizationUnit" cssClass="error"/>
					</div>
					<div class="panelRow">
						<pcform:label required="*" for="organizationName">
							<spring:message code="admin.certificates.generation.organizationName.label" />
						</pcform:label>
						<form:input id="organizationName" path="organizationName" size="20"/>
						<form:errors path="organizationName" cssClass="error"/>
					</div>
					<div class="panelRow">
						<pcform:label required="*" for="locality">
							<spring:message code="admin.certificates.generation.locality.label" />
						</pcform:label>
						<form:input id="locality" path="locality" size="20"/>
						<form:errors path="locality" cssClass="error"/>
					</div>
					<div class="panelRow">
						<pcform:label required="*" for="stateName">
							<spring:message code="admin.certificates.generation.stateName.label" />
						</pcform:label>
						<form:input id="stateName" path="stateName" size="20"/>
						<form:errors path="stateName" cssClass="error"/>
					</div>
					<div class="panelRow">
						<pcform:label required="*" for="country">
							<spring:message code="admin.certificates.generation.country.label" />
						</pcform:label>
						<form:input id="country" path="country" size="20"/>
						<form:errors path="country" cssClass="error"/>
					</div>
					<div class="panelRow">
						<pcform:label required="*" for="eMail">
							<spring:message code="admin.certificates.generation.eMail.label" />
						</pcform:label>
						<form:input id="eMail" path="eMail" size="20"/>
						<form:errors path="eMail" cssClass="error"/>
					</div>
					<div class="panelRow">
						<pcform:label required="*" for="validity">
							<spring:message code="admin.certificates.generation.validity.label" />
						</pcform:label>
						<form:input id="validity" path="validity" size="20"/>
						<form:errors path="validity" cssClass="error"/>
					</div>
					<div class="panelRow">
						<pcform:label required="*" for="allowedNodes">
							<spring:message code="admin.certificates.generation.allowedNodes.label" />
						</pcform:label>
						<form:select path="allowedNodes" items="${nodesList}" itemValue="value" itemLabel="label" multiple="true" />
						<form:errors path="allowedNodes" cssClass="error"/>
					</div>
				</div>
				<div class="buttonsbar">
					<input type="submit" id="cancel" name="cancel" value="Annulla" class="button" />
					<input type="submit" id="reset" name="reset" value="Azzera form" class="button" />
					<input type="submit" id="generateCertificate" name="generateCertificate" value="Genera" class="button" />
				</div>
			</fieldset>
		</form:form>
	</body>
</html>
