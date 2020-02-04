<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>

<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />
<% it.people.fsl.servizi.test.cedaf.demo.model.ProcessData data = (it.people.fsl.servizi.test.cedaf.demo.model.ProcessData)pplProcess.getData();%>
 
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