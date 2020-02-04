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
	Il frammento di jsp include gli script e l'active-x necessari al procedimento 
	di firma.

	Può essere incluso nei servizi che richiedono un utilizzo custom del componente 
	di firma, non previsto dalle normali funzioni del framework.	
	
	ATTENZIONE: è necessario anche includere il frammento signFooter.jsp
*/%>

	<script type="text/javascript" src="./framework/sign/generic/default/html/<%=it.people.process.sign.ConcreteSign.getScriptFileName(request)%>"></script>
	<script type="text/javascript" src="./framework/sign/generic/default/html/iiFSMain.js"></script>
	<script type="text/javascript" >
		// Inizializza le variabili per la firma
		var remoteSignEnabled = false;
		<c:if test="${initParam.remoteSignEnabled}">remoteSignEnabled = true;</c:if>
				
		var weakSignEnabled = false;
		<c:if test="${initParam.weakSignEnabled}">weakSignEnabled = true;</c:if>

		var weakSignDisclaimer = "<bean:message key="label.sign.weakSignDisclaimer"/>";
		var remoteSignDisclaimer= "<bean:message key="label.sign.remoteSignDisclaimer"/>";	
	</script>			
