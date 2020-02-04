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
<html>
	<head>
		<title>Test filters</title>
	</head>
	<body>
		<form:form modelAttribute="p12UploadItem" method="post" enctype="multipart/form-data">
			
			<div>
				<p>Gli unici certificati abilitati (quindi presenti nel trust store della People Console) sono quelli di Riccardo Foraf&ograve; e Mario Rossi.</p>
				<p>Il certificato di Luigi Bianchi non &egrave; abilitato.</p>
				<p>
					I certificati P12 di test sono reperibili nella cartella <code>test/it/people/console/security/keys</code>.
				</p>
			</div>
			
			<div>
				<spring:hasBindErrors name="p12UploadItem">
					<c:forEach var="error" items="${errors.allErrors}">
						<li>
							<spring:message code="${error.code}" text="${error.defaultMessage}"/>
						</li>
					</c:forEach>
				</spring:hasBindErrors>
			</div>
			
            <fieldset>
                <legend>P12 Authorization Fields</legend>
 
                <p>
                    <form:label for="password" path="password">Password</form:label><br/>
                    <form:password path="password"/>
                </p>
 
                <p>
                    <form:label for="fileData" path="fileData">P12 Certificate</form:label><br/>
                    <form:input path="fileData" type="file"/>
                </p>
 
                <p>
                    <input type="submit" />
                </p>
 
            </fieldset>
		</form:form>
	</body>
</html>
