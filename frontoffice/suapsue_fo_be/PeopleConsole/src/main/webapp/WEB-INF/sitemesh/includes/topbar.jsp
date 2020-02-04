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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pcform" uri="/WEB-INF/peopleconsole.tld" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="topbarButton">

	<div class="leftcol leftalign version">
		<!--Ver.&nbsp;<pcform:consoleVersion numbersSeparator="." suffix="" showBuildNumber="false" />-->
	</div>
	
	<div class="centercol centeralign">
		<c:if test="${includeTopbarLinks}">
				<div id="toolbar"></div>
			<sec:authorize access="hasRole('CONSOLE_ADMIN')">
				<input type="hidden" id="hasRoleConsoleAdmin_menu" value="true">
			</sec:authorize>
			<c:if test="${includeLogout}">
				<input type="hidden" id="includeLogout_menu" value="true">
			</c:if>
		</c:if>
		<noscript>
			<c:if test="${includeTopbarLinks}">
				<a title="Pagina principale" href="/PeopleConsole/paginaPrincipale.mdo">Pagina principale</a>
	        	<sec:authorize access="hasRole('CONSOLE_ADMIN')">
					<a title="Amministrazione" href="/PeopleConsole/Amministrazione/paginaPrincipale.do">Amministrazione</a>
				</sec:authorize>
				<a title="Gestione messaggi" href="/PeopleConsole/MessaggiGenerali/elenco.do">Gestione messaggi</a>
				<a title="Nodi di FE" href="/PeopleConsole/NodiFe/elenco.do">Gestione Nodi</a>
				<a title="Servizi di FE" href="/PeopleConsole/ServiziFe/elenco.do">Servizi di FE</a>
				<a title="Servizi di BE" href="/PeopleConsole/ServiziBe/elenco.do">Servizi di BE</a>
				<a title="Accreditamenti" href="/PeopleConsole/Accreditamenti/accreditamentiManagement.do">Accreditamenti</a>
				<a title="Osservatorio" href="/PeopleConsole/Monitoraggio/paginaPrincipale.do">Osservatorio</a>
				
			</c:if>
			<c:if test="${includeLogout}">
				<pcform:logout logoutAction="/PeopleConsole/newLogin.nldo" excludeActions="nldo" linkTitle="Logout" />
			</c:if>
		</noscript>
		
	</div>

</div>
