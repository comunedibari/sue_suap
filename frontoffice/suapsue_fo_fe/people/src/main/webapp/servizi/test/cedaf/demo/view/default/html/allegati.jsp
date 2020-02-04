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