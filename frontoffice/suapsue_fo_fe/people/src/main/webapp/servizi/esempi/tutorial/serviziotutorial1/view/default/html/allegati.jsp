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

<html:xhtml/>
<h1><bean:message key="label.allegati.titolo"/></h1>
<br />
<br />
<ppl:errors/>

<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<table summary="<bean:message key="label.allegati.tableSummary"/>">
	<tr>
		<th id="nome">
			<label for="uploadName"><bean:message key="label.allegati.nome"/></label>
		</th>
		<th id="descrizione">
			<label for="uploadDescription"><bean:message key="label.allegati.descrizione"/></label>
		</th>
		<th id="path">
			<label for="data.uploadFile"><bean:message key="label.allegati.path"/></label>
		</th>
	</tr>
	<logic:iterate id="allegato" name="pplProcess" property="data.allegati">
		<tr>
			<td headers="nome">
				<bean:write name="allegato" property="name"/>
			</td>
			<td headers="descrizione">
				<bean:write name="allegato" property="descrizione"/>
			</td>
			<td headers="path">
				<bean:write name="allegato" property="path"/>
			</td>
			<td>
				<%-- Esempio di utilizzo del tag di rimozione con l'indice indicato implicitamente 	  --%>	
				<%-- Alternativamente è possibile indicare in modo esplicito l'indice della proprietà --%>
				<%-- sostituendo l'attributo indexed="true" con propertyIndex="<%=i++>" (n.b. chiudere bene il --%>
				<ppl:commandRemoveObject property="data.allegati" indexed="true"  styleClass="btn">
					<bean:message key="button.allegati.elimina"/>
				</ppl:commandRemoveObject>
			</td>
		</tr>
	</logic:iterate>
	<tr>
		<td><input name="uploadName" id="uploadName" /></td>	
		<td><input name="uploadDescription" id="uploadDescription" /></td>	
		<td><html:file property="data.uploadFile" /></td>
		<td>
			<ppl:commandUpload styleClass="btn" property="data.allegati">
				<bean:message key="button.allegati.carica"/>
			</ppl:commandUpload>
		</td>
	</tr>
</table>   
