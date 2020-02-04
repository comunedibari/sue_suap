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
<%@ page contentType="text/html; charset=iso-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="pcfn" uri="/WEB-INF/peopleconsole-fn.tld" %>
<%@ taglib prefix="pcform" uri="/WEB-INF/peopleconsole.tld" %>
<html>
	<head>
		<title><spring:message code="root.login.title" /></title>
	</head>
	<body>
		<form:form modelAttribute="rootLogin">
			
			<c:choose>
				<c:when test="${rootLogin.needUpdate}">
					<p>
						<spring:message code="root.login.update.message" />
					</p>	
					<div class="panelrowclearer" ></div>	

					<fieldset>
					
						<div class="panel">
							<div class="panelRow">
								<pcform:label for="firstName" cssClass="label" required="*">
									<spring:message code="root.login.update.firstName.label" />
								</pcform:label>
								<form:input id="firstName" path="firstName" />
								<form:errors path="firstName" cssClass="error"/>
							</div>
							<div class="panelRow">
								<pcform:label for="lastName" cssClass="label" required="*">
									<spring:message code="root.login.update.lastName.label" />
								</pcform:label>
								<form:input id="lastName" path="lastName" />
								<form:errors path="lastName" cssClass="error"/>
							</div>
							<div class="panelRow">
								<pcform:label for="eMail" cssClass="label" required="*">
									<spring:message code="root.login.update.eMail.label" />
								</pcform:label>
								<form:input id="eMail" path="eMail" />
								<form:errors path="eMail" cssClass="error"/>
							</div>
							<div class="panelRow">
								<pcform:label for="password" cssClass="label" required="*">
									<spring:message code="root.login.password.label" />
								</pcform:label>
								<form:password id="password" path="password" />
								<form:errors path="password" cssClass="error"/>
							</div>
							<div class="panelRow">
								<pcform:label for="passwordCheck" cssClass="label" required="*">
									<spring:message code="root.login.passwordCheck.label" />
								</pcform:label>
								<form:password id="passwordCheck" path="passwordCheck" />
								<form:errors path="passwordCheck" cssClass="error"/>
							</div>
						</div>
						<div class="buttonsbar">
							<form:hidden path="needUpdate" value="true" />
							<form:hidden path="needLogin" value="true" />
							<form:hidden path="defaultRoot" value="true" />
							<input type="submit" id="doLogin" name="doLogin" value="Login" class="button" />
						</div>
					</fieldset>
				</c:when>
				<c:otherwise>
			
					<c:choose>
						<c:when test="${rootLogin.defaultRoot}">
							<c:choose>
								<c:when test="${rootLogin.needLogin}">
									<p>
										<spring:message code="root.login.defaultRoot.update.message" />
									</p>
								</c:when>
								<c:otherwise>
									<p>
										<spring:message code="root.login.defaultRoot.message" />
									</p>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<p>
								<spring:message code="root.login.message" />
							</p>
						</c:otherwise>
					</c:choose>
					<div class="panelrowclearer" ></div>					
					
					<fieldset>
					
						<div class="panel">
							<div class="compactPanelRow">
								<pcform:label for="password" cssClass="label" required="*">
									<spring:message code="root.login.password.label" />
								</pcform:label>
								<form:password id="password" path="password" />
								<form:errors path="password" cssClass="error"/>
							</div>
						</div>
						<div class="buttonsbar">
							<input type="submit" id="doLogin" name="doLogin" value="Login" class="button" />
						</div>
					
					</fieldset>
				</c:otherwise>
			</c:choose>
			
		</form:form>
	</body>
</html>
