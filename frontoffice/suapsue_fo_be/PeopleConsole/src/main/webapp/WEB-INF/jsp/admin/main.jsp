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
		<title><spring:message code="admin.mainPage.title" /></title>
	</head>
	<body>
		<div class="box">
			<div class="column">
				<div class="content">
					<h1>Amministrazione utenti</h1>
					<ul>
						<li><a href="Utenti/accounts.do">Gestione accounts</a></li>
						<li><a href="Utenti/Certificati/certificatiAccessoXML.do">Gestione certificati visualizzazione XML</a></li>
						<!--<li><a href="Utenti/ruoli.do">Gestione ruoli</a></li>-->
					</ul>
				</div>
			</div>
			<div class="column">
				<div class="content">
					<h1>Impostazioni</h1>
					<ul>
						<li><a href="Impostazioni/impostazioni.do">Impostazioni</a></li>
					</ul>
				</div>
			</div>
			
			<div class="column">
				<div class="content">
					<h1>Gestione pratiche</h1>
					<ul>
						<li><a href="Pratiche/cancellazione.do">Archiviazione e cancellazione pratiche</a></li>
					</ul>
				</div>
			</div>
		</div>
	</body>
</html>
