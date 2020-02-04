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
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%/*
	Il frammento di jsp include l'applet di firma remota ed il link alla
	documntazione relativa.

	Può essere incluso nei servizi che richiedono un utilizzo custom del componente 
	di firma, non previsto dalle normali funzioni del framework.	
	
	Il frammento deve essere incluso 
	
	ATTENZIONE: è necessario includere anche il frammento signHeader.jsp
*/%>

	<noscript>
		<p><bean:message key="sign.message.noScriptNoFirma"/></p>
	</noscript>
	<%-- ======================================================= --%>
	<%-- Integrazione per Firma Remota                           --%>
	
	  <c:if test="${initParam.remoteSignEnabled}">				
	    <c:import url="${initParam.remoteSign_pathPrefix}/remoteSignPage.jsp">
	      <c:param name="pathPrefix" value="${initParam.remoteSign_pathPrefix}"/>
	      <c:param name="userID"><%= (String)session.getAttribute("it.people.sirac.authenticated_user") %></c:param>    
	      <c:param name="remoteSignEnabled" value="${initParam.remoteSignEnabled}"/>
	      <c:param name="remoteSignMode" value="${initParam.remoteSignMode}"/>
	    </c:import>
	  </c:if>
	<%-- ======================================================= --%>
