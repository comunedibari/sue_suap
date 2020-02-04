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
<html:xhtml/>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<h1><bean:message key="validaElenco.titolo"/></h1>

<br />
<br />
<ppl:errors/>

<table summary="<bean:message key="validaElenco.tableSummary"/>">
<% 
    ProcessData pData = ((ProcessData)pplProcess.getData());
	List gruppi = pData.getGruppi();		
	for(int iGruppo = 0; iGruppo < gruppi.size(); iGruppo++) {
        Gruppo gruppo = (Gruppo)gruppi.get(iGruppo);
%>
	<tr><td>
		<html:text property='<%="data.gruppi[" + iGruppo + "].nome"%>' />
	</td></tr>
	<tr><td>
		<table>
			<tr>
				<td><bean:message key="label.validaElenco.nome"/></td>
				<td><bean:message key="label.validaElenco.cognome"/></td>
			</tr>
			<%
	        	List utenti = gruppo.getUtenti();
				for(int iUtente = 0; iUtente < utenti.size(); iUtente++) {
			%>
				<tr>
					<td><html:text property='<%="data.gruppi[" + iGruppo + "].utenti[" + iUtente + "].nome"%>' /></td>
					<td><html:text property='<%="data.gruppi[" + iGruppo + "].utenti[" + iUtente + "].cognome"%>' /></td>
				</tr>
			<%}%>
		</table>
	</td></tr>
<%}%>
</table>
