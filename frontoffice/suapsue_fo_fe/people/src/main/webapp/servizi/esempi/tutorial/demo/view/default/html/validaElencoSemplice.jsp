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
<%@ page import="it.people.fsl.servizi.esempi.tutorial.demo.model.*"%>
<%@ page import="java.util.*"%>

<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="people" %>
<html:xhtml/>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<h1><bean:message key="validaElenco.titolo"/></h1>

<br />
<br />
<ppl:errors/>

<table summary="<bean:message key="validaElenco.tableSummary"/>">	
	<tr>
		<td><ppl:fieldLabel key="label.validaElenco.data" fieldName="data.dataDaValidare" /></td>
		<td><html:text property="data.dataDaValidare" /></td>
	</tr>

	<tr>
		<td><ppl:fieldLabel key="label.validaElenco.codiceFiscale" fieldName="data.codiceFiscale" /></td>
		<td><html:text property="data.codiceFiscale" /></td>
	</tr>
	
	<tr>
		<td><ppl:fieldLabel key="label.validaElenco.partitaIVA" fieldName="data.partitaIVA" /></td>
		<td><html:text property="data.partitaIVA" /></td>
	</tr>

	<tr>
		<td><bean:message key="label.validaElenco.nome"/></td>
		<td><bean:message key="label.validaElenco.cognome"/></td>
	</tr>
<% 
    ProcessData pData = ((ProcessData)pplProcess.getData());
	List utenti = pData.getUtenti();
	for(int iUtente = 0; iUtente < utenti.size(); iUtente++) {
%>
	<tr>
		<td><html:text property='<%="data.utenti[" + iUtente + "].nome"%>' /></td>
		<td><html:text property='<%="data.utenti[" + iUtente + "].cognome"%>' /></td>
		<td><people:linkLoopback property="utenti" propertyIndex="<%=iUtente%>">Maiuscolo</people:linkLoopback></td>
	</tr>
<%}%>

	<tr><td>
		<%@ include file="/framework/sign/generic/default/html/signFooter.jsp" %>
	
	</td></tr>
</table>
