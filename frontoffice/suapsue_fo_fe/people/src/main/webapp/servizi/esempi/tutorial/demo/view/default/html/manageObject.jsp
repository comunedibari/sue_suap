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

<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html:xhtml/>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<h1><bean:message key="manageObject.titolo"/></h1>

<br />
<br />
<ppl:errors/>

<table summary="<bean:message key="manageObject.tableSummary"/>">
	<tr>
		<td id="nome"><bean:message key="manageObject.nome"/></td>
		<td id="valore"><bean:message key="manageObject.valore"/></td>
	</tr>

<logic:iterate id="oggetto" name="pplProcess" property="data.elencoOggetti">
	<tr>
		<td headers="nome">
			<bean:write name="oggetto" property="nome"/>
		</td>
		<td headers="valore">
			<bean:write name="oggetto" property="valore"/>
		</td>
		<td>
			<!-- esempio di utilizzo del tag di rimozione con l'indice implicito -->		
			<ppl:commandRemoveObject property="data.elencoOggetti" indexed="true" styleClass="btn">
				<bean:message key="manageObject.elimina"/>
			</ppl:commandRemoveObject>
		</td>
	</tr>
</logic:iterate>

	<tr>
		<td>
			<!-- elimina l'ultimo oggetto inserito -->
			<ppl:commandRemoveObject property="data.elencoOggetti" styleClass="btn" >
				<bean:message key="manageObject.eliminaUltimo"/>
			</ppl:commandRemoveObject>
		</td>
	</tr>

	<tr>
		<td><html:text name="pplProcess" property="data.elemento.nome" value="inserisci un nome"></html:text></td>
		<td><html:text name="pplProcess" property="data.elemento.valore" value="inserisci un valore"></html:text></td>
		<td>
			<ppl:commandAddObject styleClass="btn" property="data.elencoOggetti">
				<bean:message key="manageObject.addNewObject"/>
			</ppl:commandAddObject>		
		</td>
	</tr>
</table>
	
