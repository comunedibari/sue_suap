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
<%@ page language="java" %>
<%@ page import="it.people.layout.*"%>

<%@ taglib uri="/WEB-INF/app.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<h1><bean:message key="allegati.titolo"/></h1>
<br />
<br />
<ppl:errors/>

<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<html:xhtml/>
<table summary="<bean:message key="allegati.tableSummary"/>">
	<tr>
		<td id="nome"><bean:message key="allegati.nome"/></td>
		<td id="descrizione"><bean:message key="allegati.descrizione"/></td>
	</tr>
	<%int i = 0;%>
	<logic:iterate id="allegato" name="pplProcess" property="data.allegati">
		<tr>
			<td headers="nome">
				<bean:write name="allegato" property="name"/>
			</td>
			<td headers="descrizione">
				<bean:write name="allegato" property="descrizione"/>
			</td>
			<td>
				<!-- esempio di utilizzo del tag di rimozione con l'indice
					indicato esplicitamente, per vedere un esempio di utilizzo
					con l'indice implicito si veda lo step di gestione degli oggetti -->
				<ppl:commandRemoveObject property="data.allegati" propertyIndex="<%=i++%>" styleClass="btn">
					<bean:message key="allegati.elimina"/>
				</ppl:commandRemoveObject>
			</td>
		</tr>
	</logic:iterate>
	<tr>
		<td><input name="uploadName"/></td>	
		<td><input name="uploadDescription"/></td>	
		<td><html:file styleId="nomeallegato" property="data.uploadFile"/></td>
		<td>
			<ppl:commandUpload styleClass="btn" property="data.allegati">
				<bean:message key="allegati.carica"/>
			</ppl:commandUpload>
		</td>
	</tr>
</table>   
