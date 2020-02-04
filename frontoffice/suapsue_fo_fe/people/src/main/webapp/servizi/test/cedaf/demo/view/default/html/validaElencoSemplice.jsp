<%@ page language="java" %>
<%@ page import="it.people.layout.*"%>
<%@ page import="it.people.fsl.servizi.test.cedaf.demo.model.*"%>
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