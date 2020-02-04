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
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<% it.people.fsl.servizi.esempi.tutorial.demo.model.ProcessData data = (it.people.fsl.servizi.esempi.tutorial.demo.model.ProcessData)pplProcess.getData();%>
 
<div>
<ppl:label key="riepilogo.titolo"/>
<h3><bean:message key="riepilogo.titolo"/></h3>

<bean:message key="riepilogo.label.denunciante"/>
<table>
	<tr>
		<td><bean:message key="riepilogo.label.nome"/></td>
		<td><ppl:fieldWrite default="......." name="pplProcess" property="data.richiedente.utenteAutenticato.nome"/></td>
	</tr>
	<tr>
		<td><bean:message key="riepilogo.label.cognome"/></td>
		<td><ppl:fieldWrite default="......." name="pplProcess" property="data.richiedente.utenteAutenticato.cognome"/></td>
	</tr>
</table>

<bean:message key="riepilogo.label.valoriInseriti"/>
<table>
	<tr>
		<td><bean:message key="riepilogo.label.valore1"/></td>
		<td><ppl:fieldWrite default="......." name="pplProcess" property="data.valore1"/></td>
	</tr>
	<tr>
		<td><bean:message key="riepilogo.label.valore2"/></td>
		<td><ppl:fieldWrite default="......." name="pplProcess" property="data.valore2"/></td>
	</tr>
	<tr>
		<td><bean:message key="riepilogo.label.valore3"/></td>
		<td><ppl:fieldWrite default="......." name="pplProcess" property="data.valore3"/></td>
	</tr>
	<tr>
		<td><bean:message key="riepilogo.label.valore4"/></td>
		<td><ppl:fieldWrite default="......." name="pplProcess" property="data.valore4"/></td>
	</tr>
	<tr>
		<td><bean:message key="riepilogo.label.valore5"/></td>
		<td><ppl:fieldWrite default="......." name="pplProcess" property="data.valore5"/></td>
	</tr>
</table>
</div>
