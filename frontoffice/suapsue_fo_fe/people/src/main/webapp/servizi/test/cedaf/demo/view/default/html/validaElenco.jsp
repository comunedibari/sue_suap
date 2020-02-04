<%@ page language="java" %>
<%@ page import="it.people.layout.*"%>
<%@ page import="it.people.fsl.servizi.test.cedaf.demo.model.*"%>
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